package com.geeke.sys.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import cn.hutool.json.JSONObject;
import com.geeke.feign.FileService;
import com.geeke.sys.entity.*;
import com.geeke.sys.utils.FileUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.entity.ContentType;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.sys.dao.SysFileDao;
import com.geeke.sys.dao.SysFileContentDao;
import com.geeke.utils.Reflections;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

/**
 * 系统附件Service
 * @author szy
 * @version 2021-09-22
 */
 
@Service("sysFileService")
@Transactional(readOnly = true)
public class SysFileService extends CrudService<SysFileDao, SysFile>{

    @Autowired
    private SysFileContentDao sysFileContentDao;
    @Autowired
    private FileService fileService;
    @Override
    public SysFile get(String id) {
        SysFile sysFile = super.get(id);

        List<Parameter> params = null;
        PageRequest pageRequest;
        /*获取子表列表   附件字节表*/
        params = Lists.newArrayList();
        params.add(new Parameter("id", "=", sysFile.getId()));
        pageRequest = new PageRequest(params);
        sysFile.setSysFileContentList(sysFileContentDao.listAll(pageRequest));
        //这里循环看子表状态  看是否使用minio服务  如果用了就去调用获取 base64
        for (int i = 0; i < sysFile.getSysFileContentList().size(); i++) {
            if (sysFile.getSysFileContentList().get(i).getDelFlag().equals("2")){
                JSONObject viewBase64 = fileService.getViewBase64(sysFile.getSysFileContentList().get(i).getId());
                if (viewBase64.get("code").equals(500)){
                    throw new RuntimeException("上传到文件服务失败！");
                }
                String data = viewBase64.get("data").toString();
                byte[] decode= DatatypeConverter.parseBase64Binary(data);
                sysFile.getSysFileContentList().get(i).setFileByte(decode);
            }
        }
        return sysFile;
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public List<String> saves(List<SysFile> entitys){
        ArrayList<String> objects = new ArrayList<>();
        for (int i = 0; i < entitys.size(); i++) {
            String id = save(entitys.get(i)).getId();
            objects.add(id);
        }
        return objects;
    }
    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public SysFile save(SysFile sysFile) {
	    //2023年2月14日 17:09:03 头脑一热突然明白，撤销全部，重新编写

        SysFile sysFileTemp = super.save(sysFile);
        if (StringUtils.isNoneBlank(sysFileTemp.getId())) {

            List<Parameter> params = null;
            PageRequest pageRequest;
            /* 处理子表     附件字节表 */
            params = Lists.newArrayList();
            params.add(new Parameter("id", "=", sysFile.getId()));
            pageRequest = new PageRequest(params);
            //逻辑其实就是查数据库 吧id一样的全部查出来
            //然后对比如果有有的就修改 ，没有的就是就是删除但是俺不懂为啥子不在一个for里面搞定
            List<SysFileContent> list_SysFileContent = sysFileContentDao.listAll(pageRequest);            
            List<SysFileContent> deleteSysFileContents = Lists.newArrayList(); // 删除列表
            List<SysFileContent> insertSysFileContents = Lists.newArrayList(); // 添加列表
            List<SysFileContent> updateSysFileContents = Lists.newArrayList(); // 更新列表
            for(SysFileContent sysFileContentSaved: list_SysFileContent) {
                boolean found = false;   
                for (SysFileContent sysFileContent : sysFile.getSysFileContentList()){
                   if(sysFileContentSaved.getId().equals(sysFileContent.getId())){
                       found = true;
                       break;
                   }
                }
                if(!found) {
                   deleteSysFileContents.add(sysFileContentSaved);
                }
            }
            //通过筛选 查出删除的内容
            if(deleteSysFileContents.size() > 0) {
                for (int i = 0; i < deleteSysFileContents.size(); i++) {
                    //判断 如果正常的话 就执行以前的逻辑
                    if (deleteSysFileContents.get(i).getDelFlag().equals("0")){
                        sysFileContentDao.bulkDelete(deleteSysFileContents);
                    }else if(deleteSysFileContents.get(i).getDelFlag().equals("2")){
                        //代表运用了minio文件服务 那么调用minio file
                       fileService.fdeleteById(deleteSysFileContents.get(i).getRemarks());
                    }
                }
            }
            //这里通过对比赛选出对比文件
            for (SysFileContent sysFileContent : sysFile.getSysFileContentList()){
            

                if (StringUtils.isBlank(sysFileContent.getId())) {
                    sysFileContent.setId(sysFileTemp.getId());  // 子表id与父表id一致
                    //- -其实这写一个主表id和附表id一致就很迷惑
                    //如果一样的话 那tm不就一对一的关系了嘛 ，这里循环爪子
                    preInsert(sysFile,sysFileContent);
                    insertSysFileContents.add(sysFileContent);
                } else {
                    preInsert(sysFile,sysFileContent);
                    updateSysFileContents.add(sysFileContent);
                }

            }
            if(updateSysFileContents.size() > 0) {
                for (int i = 0; i < updateSysFileContents.size(); i++) {
                //这里更新就调用那边先删除 在添加了 //后续有版本控制的时候可以考虑源文件更新
                    String id = updateSysFileContents.get(i).getId();
                    JSONObject entries = fileService.fdeleteById(id);

                    insertSysFileContents.get(i).setDelFlag("2");
                    byte[] fileByte = insertSysFileContents.get(i).getFileByte();
                    MultipartFile multipartFile = null;
                    InputStream inputStream = new ByteArrayInputStream(fileByte);
                    try {
                        multipartFile = new MockMultipartFile(sysFile.getName(),sysFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
                        SysFileMinIO sysFileMinIO = new SysFileMinIO();
                        sysFileMinIO.setObjectId(insertSysFileContents.get(i).getId());
                        sysFileMinIO.setBucket("dcode-cloud");
                        sysFileMinIO.setAppName("dcode-cloud");
                        sysFileMinIO.setObjectName("dcode-cloud");
                        JSONObject entriess = fileService.uploadSingle(multipartFile,com.alibaba.fastjson.JSONObject.toJSONString(sysFileMinIO));
                        if (entries.get("code").equals(500)){
                            throw new RuntimeException("上传到文件服务失败！");
                        }
                        SysFileMinIO data = entriess.get("data", SysFileMinIO.class);
                        insertSysFileContents.get(i).setRemarks(data.getId());
                        insertSysFileContents.get(i).setFileByte(null);
                    } catch (IOException e) {
                        throw new RuntimeException("文件转化失败！");
                    }
                }
            	sysFileContentDao.bulkUpdate(updateSysFileContents);
            }
            if(insertSysFileContents.size() > 0) {
                //处理数据 - -这里的文件上传改动 我真的想在这里一对一的格式写 打老壳
                for (int i = 0; i < insertSysFileContents.size(); i++) {
                    // 更新代码后续内容全部改为minio存储
                    insertSysFileContents.get(i).setDelFlag("2");
                    byte[] fileByte = insertSysFileContents.get(i).getFileByte();
//                    File file = FileUtils.fileToBytes(fileByte,insertSysFileContents.get(i).getId() +insertSysFileContents.get(i).getFileType());
//                    MultipartFile multipartFile = FileUtils.getMultipartFile(file);
                    MultipartFile multipartFile = null;
                    InputStream inputStream = new ByteArrayInputStream(fileByte);
                    try {
                         multipartFile = new MockMultipartFile(sysFile.getName(),sysFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
                        SysFileMinIO sysFileMinIO = new SysFileMinIO();
                        sysFileMinIO.setObjectId(insertSysFileContents.get(i).getId());
                        sysFileMinIO.setBucket("dcode-cloud");
                        sysFileMinIO.setAppName("dcode-cloud");
                        sysFileMinIO.setObjectName("dcode-cloud");
                        JSONObject entries = fileService.uploadSingle(multipartFile,com.alibaba.fastjson.JSONObject.toJSONString(sysFileMinIO));
                        if (entries.get("code").equals(500)){
                            throw new RuntimeException("上传到文件服务失败！");
                        }
                        SysFileMinIO data = entries.get("data", SysFileMinIO.class);
                        insertSysFileContents.get(i).setRemarks(data.getId());
                        insertSysFileContents.get(i).setFileByte(null);
                    } catch (IOException e) {
                       throw new RuntimeException("文件转化失败！");
                    }
                }
                sysFileContentDao.bulkInsert(insertSysFileContents);
            }
        }
        return sysFileTemp;
    }

    /**
     * multipartFiles文件转为SysFile集合对象
     * @param multipartFiles
     * @param objectId
     * @return
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public List<SysFile> changeAndSaveSysFileList(MultipartFile[] multipartFiles, String objectId) throws IOException {
        List<SysFile> fileList = new ArrayList<>(); // 文件集合对象
        if(Objects.nonNull(multipartFiles)){
            for(MultipartFile file : multipartFiles) {
                List<SysFileContent> fileContentList = new ArrayList<>(); // 文件内容集合对象
                SysFile sysFile = new SysFile();
                String originalFilename = file.getOriginalFilename();
                int begin = originalFilename.indexOf(".");
                int last = originalFilename.length();
                //获得文件后缀名
                String suffix = originalFilename.substring(begin, last);
                sysFile.setFileType(suffix); // 文件类型

                sysFile.setObjectId(Long.parseLong(objectId)); // 业务表ID
                sysFile.setName(file.getOriginalFilename()); // 文件名
                sysFile.setFileSize(file.getSize()); // 文件大小

                SysFileContent fileContent = new SysFileContent();
                InputStream is = file.getInputStream();
                byte[] fContent = new byte[(int) file.getSize()];
                is.read(fContent);
                fileContent.setFileByte(fContent); // 字节
                fileContent.setFileType(suffix); // 文件类型
                fileContentList.add(fileContent); // 文件存储内容
                sysFile.setSysFileContentList(fileContentList);
                fileList.add(sysFile);
                save(sysFile);
            }
        }
        return fileList;
    }

    /**
     * 根据objectId删除附件和附件存储表信息信息
     * @param objectId
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public void deleteByObjectId(String objectId) {
        List<Parameter> params = null;
        PageRequest pageRequest;
        params = Lists.newArrayList();
        params.add(new Parameter("object_id", "=", objectId));
        pageRequest = new PageRequest(params);
        List<SysFile> sysFiles = dao.listAll(pageRequest);
        for (SysFile file : sysFiles) {
            delete(file); // 删除附件包含存储表信息
        }
        /*if (sysFiles.size() > 0) {
            dao.bulkDelete(sysFiles); // 删除附件表信息
        }*/

    }


    /**
     * @param sysFile
     * @param sysFileContent
     * 为了保存系统附件和系统字节表更新时间一致，用户回显图片做浏览器缓存使用
     * 用原来的创建附件、更改附件用的是preInsert ,preUpdate方法 每张表更新都会new date() 会导致时间不同
     */

    private void preInsert(SysFile sysFile, SysFileContent sysFileContent) {
        sysFileContent.setCreateDate(sysFile.getCreateDate());
        sysFileContent.setUpdateDate(sysFile.getUpdateDate());
        sysFileContent.setCreateBy(sysFile.getCreateBy());
        sysFileContent.setUpdateBy(sysFile.getUpdateBy());
    }

    /**
     * 删除
     * @param sysFile
     */
    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public int delete(SysFile sysFile) {
        List<Parameter> params = null;
        PageRequest pageRequest;
        /* 处理子表     附件字节表 */
        params = Lists.newArrayList();
        params.add(new Parameter("id", "=", sysFile.getId()));
        pageRequest = new PageRequest(params);
        sysFile.setSysFileContentList(sysFileContentDao.listAll(pageRequest));        

        if(sysFile.getSysFileContentList() != null && sysFile.getSysFileContentList().size() > 0) {
            List<SysFileContent> sysFileContentList = sysFile.getSysFileContentList();
            //这里循环主要判断是否是minio服务用过的 ，用过就去调minio服务
            for (int i = 0; i < sysFileContentList.size(); i++) {
                if (sysFileContentList.get(i).getDelFlag().equals("2")){
                    JSONObject entries = fileService.fdeleteById(sysFileContentList.get(i).getId());
                }
            }
            sysFileContentDao.bulkDelete(sysFile.getSysFileContentList());
        }

        int rows = super.delete(sysFile);
        return rows;
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public void delete(String[] ids) {
        //直接调用循环删除 可优化
        for (String id : ids) {
            SysFileContent sysFileContent = sysFileContentDao.get(id);
            JSONObject entries = fileService.fdeleteById(sysFileContent.getRemarks());
        }
        // 根据数组ID，删除附件
        dao.deleteByIds(ids);
        // 根据数组ID，删除附件存储表
        sysFileContentDao.deleteByIds(ids);

    }

    /**
     * 生成操作日志
     * @param actionTypeId  操作类型Id
     * @param entity        操作的实体对象
     * @return
     */
    @Override
    protected Action createAction(String actionTypeId, SysFile entity) {
        Action action = super.createAction(actionTypeId, entity);
        if(action == null) {
            return null;
        }
        // 删除时记录把保存的数据保存到回收站
        if(ActionConstants.ACTION_DELETED.equals(actionTypeId)) {   
            for(SysFileContent child: entity.getSysFileContentList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String)Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);            
            }
        }
        return action;
    }
}