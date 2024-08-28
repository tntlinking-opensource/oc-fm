package com.geeke.checkplan.checkplanmain.service;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.geeke.admin.entity.User;
import com.geeke.checkplan.attach.entity.TfamAssetCheckplanMainAttach;
import com.geeke.checkplan.attach.service.TfamAssetCheckplanMainAttachService;
import com.geeke.checkplan.checkplanmain.dao.TfamAssetCheckplanMainDao;
import com.geeke.checkplan.checkplanmain.entity.TfamAssetCheckplanMain;
import com.geeke.checkplan.dept.entity.TfamAssetCheckplanMainDept;
import com.geeke.checkplan.dept.service.TfamAssetCheckplanMainDeptService;
import com.geeke.checkplan.detail.entity.TfamAssetCheckplanDetail;
import com.geeke.checkplan.detail.service.TfamAssetCheckplanDetailService;
import com.geeke.checkplan.image.service.TfamAssetCheckimageService;
import com.geeke.checkplan.req.CheckplanMainSaveReq;
import com.geeke.checkplan.req.DownloadReq;
import com.geeke.checkplan.req.PersonCheckReq;
import com.geeke.checkplan.resp.*;
import com.geeke.checkplan.result.entity.TfamAssetCheckresult;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.fixasset.alter.service.TfamAlterService;
import com.geeke.fixasset.asset.dao.TfamAssetDao;
import com.geeke.fixasset.asset.dto.ConditionDto;
import com.geeke.fixasset.asset.entity.TfamAsset;
import com.geeke.fixasset.asset.req.SaveAndUpdateReq;
import com.geeke.fixasset.asset.service.TfamAssetService;
import com.geeke.fixasset.assetstatus.entity.TfamAssetStatus;
import com.geeke.fixasset.category.entity.TfamCategory;
import com.geeke.fixasset.enums.OperatetAlterTypeEnum;
import com.geeke.org.entity.Department;
import com.geeke.utils.DateUtils;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 盘点主表Service
 *
 * @author
 */

@Service("tfamAssetCheckplanMainService")
@Transactional(readOnly = true)
public class TfamAssetCheckplanMainService extends CrudService<TfamAssetCheckplanMainDao, TfamAssetCheckplanMain> {

    String savePath = "/data/soft/fm/upload/asset/check/img";

    @Autowired
    private TfamAlterService tfamAlterService;

    @Autowired
    private TfamAssetDao tfamAssetDao;

    @Autowired
    private TfamAssetService tfamAssetService;

    @Autowired
    private TfamAssetCheckplanMainDeptService checkDeptService;

    @Autowired
    private TfamAssetCheckplanDetailService checkplanDetailService;

    @Autowired
    private TfamAssetCheckplanMainAttachService attachService;

    @Autowired
    private TfamAssetCheckimageService checkimageService;

    @Override
    public TfamAssetCheckplanMain get(String id) {
        TfamAssetCheckplanMain checkplanMain = super.get(id);

        TfamAssetCheckplanMainDto dto = new TfamAssetCheckplanMainDto();
        BeanUtils.copyProperties(checkplanMain, dto);

        List<CheckByDeptResp> depts = getByDept(dto.getId());
        dto.setDepts(depts);
        List<CheckByCategoryResp> catagorys = getByCatagory(dto.getId());
        dto.setCategorys(catagorys);

        List<TfamAssetCheckplanMainAttach> attachs = getAttach(id);
        List<AttachResp> attachResps = buildAttach(attachs);
        dto.setAttachResps(attachResps);
        return dto;
    }

    private List<AttachResp> buildAttach(List<TfamAssetCheckplanMainAttach> attachs) {
        if (null != attachs && attachs.size() > 0) {
            List<AttachResp> attachResps = new ArrayList<>();
            for (TfamAssetCheckplanMainAttach attach : attachs) {
                String attachCategory = attach.getId();
                String mainId = attach.getMain().getId();
                Integer num = checkimageService.getNum(attachCategory, mainId);
                AttachResp attachResp = new AttachResp();
                BeanUtils.copyProperties(attach, attachResp);
                attachResp.setNum(num);
                attachResps.add(attachResp);
            }

            return attachResps;
        }
        return null;
    }

    private List<CheckByDeptResp> getByDept(String mainId) {
        List<CheckByDeptResp> depts = checkplanDetailService.getByDept(mainId);
        if (null != depts && depts.size() > 0) {
            for (CheckByDeptResp de : depts) {
                String percenage = getPercenage(de.getAlreadlyCkeck(), de.getTotal());
                de.setPercent(percenage);
            }
        }
        return depts;
    }

    private List<CheckByCategoryResp> getByCatagory(String mainId) {
        List<CheckByCategoryResp> catagorys = checkplanDetailService.getByCatagory(mainId);
        if (null != catagorys && catagorys.size() > 0) {
            for (CheckByCategoryResp ca : catagorys) {
                String percenage = getPercenage(ca.getAlreadlyCkeck(), ca.getTotal());
                ca.setPercent(percenage);
            }
        }
        return catagorys;
    }

    @Override
    public Page<TfamAssetCheckplanMain> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
        String filter =
                "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";


        Page<TfamAssetCheckplanMain> result = super.listPage(super.addFilter(parameters, filter), offset, limit, orderby);
        ArrayList<TfamAssetCheckplanMainDto> dtos = new ArrayList<TfamAssetCheckplanMainDto>();
        List<TfamAssetCheckplanMain> rows = result.getRows();
        if (null != rows && rows.size() > 0) {
            for (TfamAssetCheckplanMain checkplanMain : rows) {
                Integer alreadyNum = checkplanMain.getAlreadyNum();
                Integer totalAsset = checkplanMain.getTotalAsset();
                String percenage = getPercenage(alreadyNum, totalAsset);
                String id = checkplanMain.getId();
                int attachNum = getAttachNum(id);
                String status = getStatus(percenage);

                TfamAssetCheckplanMainDto dto = new TfamAssetCheckplanMainDto();
                BeanUtils.copyProperties(checkplanMain, dto);
                dto.setPercentage(percenage);
                dto.setAttachNum(attachNum + "");
                dto.setExcuteStatus(status);
                dtos.add(dto);
            }
        }


        Page page = new Page(result.getTotal(), dtos);

        return page;
    }

    public String getStatus(String percenage) {
        String excute = "未执行";
        if (percenage.equals("0%")) {
            excute = "未执行";
        } else if (percenage.equals("100%")) {
            excute = "已结束";
        } else {
            excute = "执行中";
        }
        return excute;
    }

    public String getPercenage(int a, int b) {
        double sum = (double) a / b;
        NumberFormat nt = NumberFormat.getPercentInstance();
        return nt.format(sum);
    }

    public String getPercenage(String a, String b) {
        int a1 = Integer.valueOf(a);
        int b1 = Integer.valueOf(b);
        return getPercenage(a1, b1);
    }

    private List<TfamAssetCheckplanMainAttach> getAttach(String id) {
        ArrayList<Parameter> params = new ArrayList<>();
        Parameter parameter = new Parameter();
        parameter.setColumnName("main_id");
        parameter.setValue(id);
        parameter.setQueryType("=");
        params.add(parameter);
        List<TfamAssetCheckplanMainAttach> tfamAssetCheckplanMainAttaches = attachService.listAll(params, null);

        return tfamAssetCheckplanMainAttaches;

    }

    private int getAttachNum(String id) {

        List<TfamAssetCheckplanMainAttach> tfamAssetCheckplanMainAttaches = getAttach(id);
        if (tfamAssetCheckplanMainAttaches == null) {
            return 0;
        } else {
            return tfamAssetCheckplanMainAttaches.size();
        }
    }

    public static String randomOrderCode() {
        SimpleDateFormat dmDate = new SimpleDateFormat("yyMMddHHmmss");
        String randata = getRandom(6);
        Date date = new Date();
        String dateran = dmDate.format(date);
        String Xsode = "PD" + dateran + randata;
        if (Xsode.length() < 24) {
            Xsode = Xsode + 0;
        }
        return Xsode;
    }

    public static String getRandom(int len) {
        Random r = new Random();
        StringBuilder rs = new StringBuilder();
        for (int i = 0; i < len; i++) {
            rs.append(r.nextInt(10));
        }
        return rs.toString();
    }


    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public TfamAssetCheckplanMain save(CheckplanMainSaveReq req) {

        User user = SessionUtils.getUser();
        TfamAssetCheckplanMain tfamAssetCheckplanMain = new TfamAssetCheckplanMain();
        BeanUtils.copyProperties(req, tfamAssetCheckplanMain);
        if (StringUtils.isBlank(req.getId())) {
            tfamAssetCheckplanMain.setRegistryDate(DateUtils.getDateTime());
            tfamAssetCheckplanMain.setRegistryDept(user.getDepartment());
            tfamAssetCheckplanMain.setRegistryPerson(user);
            tfamAssetCheckplanMain.setApplyNo(randomOrderCode());
            tfamAssetCheckplanMain.setCompanyId(user.getCompanyId());
            tfamAssetCheckplanMain.setAlreadyNum(0);
        }
        TfamAssetCheckplanMain main = save(tfamAssetCheckplanMain);
        //
        if (StringUtils.isBlank(req.getId())) {
            //部门范围
            List<String> deptIds = req.getDeptIds();
            if (null != deptIds && deptIds.size() > 0) {
                for (String deptId : deptIds) {
                    TfamAssetCheckplanMainDept tfamAssetCheckplanMainDept = new TfamAssetCheckplanMainDept();
                    tfamAssetCheckplanMainDept.setMain(tfamAssetCheckplanMain);
                    tfamAssetCheckplanMainDept.setDeptId(deptId);
                    checkDeptService.save(tfamAssetCheckplanMainDept);
                }
            }

            //查询每个部门下对应状态的和类别的资产 入库
            List<String> categoryIds = req.getCategoryIds();
            List<String> statusIds = req.getStatusIds();
            List<TfamAsset> assetList = this.tfamAssetDao.selectStatusAndCategoryItem(statusIds, categoryIds, deptIds);
            if (null != assetList && assetList.size()>0){
                for (TfamAsset asset:assetList) {
                    TfamAssetCheckplanDetail detail = new TfamAssetCheckplanDetail();
                    detail.setMain(main.getId());
                    detail.setCardId(asset.getId());
                    detail.setCardModel(asset.getCardModel());
                    detail.setCardNo(asset.getCardCode());
                    detail.setAssetName(asset.getAssetName());
                    detail.setNetvalue(asset.getNetvalue()!=null?asset.getNetvalue().toString():"");
                    detail.setNum(asset.getNum());
                    detail.setUnit(asset.getUnit());
                    detail.setCheckStatus(0);
                    checkplanDetailService.save(detail);
                }
                main.setTotalAsset(assetList.size());
                save(tfamAssetCheckplanMain);

            }

            List<TfamAssetCheckplanMainAttach> attachs = req.getAttachs();
            if (null != attachs && attachs.size() > 0) {
                tfamAssetCheckplanMain.setId(main.getId());
                for (TfamAssetCheckplanMainAttach attach : attachs) {
                    attach.setMain(tfamAssetCheckplanMain);
                    attachService.save(attach);

                }
            }

        }
        return main;
    }

    @Override
    public List<TfamAssetCheckplanMain> listAll(List<Parameter> parameters, String orderby) {
        String filter =
                "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        PageRequest pageRequest = new PageRequest(super.addFilter(parameters, filter), orderby);

        List<TfamAssetCheckplanMain> result = dao.listAll(pageRequest);

        return result;
    }

    public List<StatusAndCategoryResp> getByStatusAndCategory(CheckplanMainSaveReq req) {
        List<String> deptIds = req.getDeptIds();
        List<String> categoryIds = req.getCategoryIds();
        List<String> statusIds = req.getStatusIds();
        List<StatusAndCategoryResp> statusAndCategoryResps = tfamAssetDao.selectStatusAndCategory(statusIds, categoryIds, deptIds);
        return statusAndCategoryResps;
    }

    public List<TfamAsset> getByStatusAndCategoryItem(CheckplanMainSaveReq req) {
        List<String> deptIds = req.getDeptIds();
        List<String> categoryIds = req.getCategoryIds();
        List<String> statusIds = req.getStatusIds();
        List<TfamAsset> assetList = tfamAssetDao.selectStatusAndCategoryItem(statusIds, categoryIds, deptIds);
        return assetList;
    }

    public TfamAssetCheckplanMainDto detail(String id) {
        //如果已开始执行则不允许修改
        TfamAssetCheckplanMain checkplanMain = this.dao.get(id);
        if(checkplanMain.getAlreadyNum()>0){
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_100004, ErrorEnum.E_100004.getErrorMsg()));
        }
        TfamAssetCheckplanMainDto dto = new TfamAssetCheckplanMainDto();
        BeanUtils.copyProperties(checkplanMain,dto);
        List<TfamAssetCheckplanMainDept> deptList  = checkDeptService.getByMainId(checkplanMain.getId());
        dto.setDeptList(deptList);

        List<StatusAndCategoryResp> statusAndCategorys = checkplanDetailService.selectStatusAndCategory(checkplanMain.getId());
        dto.setStatusAndCategorys(statusAndCategorys);

        List<TfamAssetCheckplanMainAttach> attaches = attachService.selectByMainId(checkplanMain.getId());
        dto.setAttaches(attaches);
        return dto;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int update(CheckplanMainSaveReq req) {
        String mainId = req.getId();
        checkDeptService.deleteByMainId(mainId);
        checkplanDetailService.deleteByMainId(mainId);
        attachService.deleteByMainId(mainId);

        TfamAssetCheckplanMain checkplanMain = get(mainId);

        TfamAssetCheckplanMain tfamAssetCheckplanMain = new TfamAssetCheckplanMain();
        BeanUtils.copyProperties(req, tfamAssetCheckplanMain);
        tfamAssetCheckplanMain.setRegistryDate(DateUtils.getDateTime());
        tfamAssetCheckplanMain.setUpdateDate(checkplanMain.getUpdateDate());
        TfamAssetCheckplanMain main = save(tfamAssetCheckplanMain);

        List<String> deptIds = req.getDeptIds();
        if (null != deptIds && deptIds.size() > 0) {
            for (String deptId : deptIds) {
                TfamAssetCheckplanMainDept tfamAssetCheckplanMainDept = new TfamAssetCheckplanMainDept();
                tfamAssetCheckplanMainDept.setMain(tfamAssetCheckplanMain);
                tfamAssetCheckplanMainDept.setDeptId(deptId);
                checkDeptService.save(tfamAssetCheckplanMainDept);
            }
        }

        //查询每个部门下对应状态的和类别的资产 入库
        List<String> categoryIds = req.getCategoryIds();
        List<String> statusIds = req.getStatusIds();
        List<TfamAsset> assetList = this.tfamAssetDao.selectStatusAndCategoryItem(statusIds, categoryIds, deptIds);
        if (null != assetList && assetList.size()>0){
            for (TfamAsset asset:assetList) {
                TfamAssetCheckplanDetail detail = new TfamAssetCheckplanDetail();
                detail.setMain(main.getId());
                detail.setCardId(asset.getId());
                detail.setCardModel(asset.getCardModel());
                detail.setCardNo(asset.getCardCode());
                detail.setAssetName(asset.getAssetName());
                detail.setNetvalue(asset.getNetvalue()!=null?asset.getNetvalue().toString():"");
                detail.setNum(asset.getNum());
                checkplanDetailService.save(detail);
            }
            main.setTotalAsset(assetList.size());
            save(tfamAssetCheckplanMain);

        }

        List<TfamAssetCheckplanMainAttach> attachs = req.getAttachs();
        if (null != attachs && attachs.size() > 0) {
            tfamAssetCheckplanMain.setId(main.getId());
            for (TfamAssetCheckplanMainAttach attach : attachs) {
                attach.setMain(tfamAssetCheckplanMain);
                attachService.save(attach);

            }
        }

        return 1;
    }

    public List<TfamAssetCheckplanMainDto> selectPlanByPerson() {
        User user = SessionUtils.getUser();
        String companyId = user.getCompanyId();
        Department department = user.getDepartment();
        String deptId = department.getId();
        ArrayList<TfamAssetCheckplanMainDto> visablePlan = new ArrayList<>();

        List<TfamAssetCheckplanMain> mainList = this.dao.selectVisablePlan(companyId);
        if (null != mainList && mainList.size()>0){
            for (TfamAssetCheckplanMain mainPlan:mainList) {
                String mainId = mainPlan.getId();
                TfamAssetCheckplanMainDept planDept = checkDeptService.selectVisable(mainId,deptId);
                if (null != planDept){
                    List<TfamCategory> categories = checkplanDetailService.selectCatagory(mainId);
                    TfamAssetCheckplanMainDto dto = new TfamAssetCheckplanMainDto();
                    BeanUtils.copyProperties(mainPlan,dto);
                    dto.setCategoryImages(categories);
                    visablePlan.add(dto);

                }
            }
        }


        return visablePlan;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean personCheck(PersonCheckReq req) {
        if (null == req.getAssetId() || null == req.getPlanId()){
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_100005, ErrorEnum.E_100005.getErrorMsg()));
        }
        String assetId = req.getAssetId();
        String planId = req.getPlanId();
        TfamAsset asset = tfamAssetDao.get(assetId);
        if (null == asset){
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_100005, ErrorEnum.E_100005.getErrorMsg()));
        }

        TfamAssetCheckplanDetail detail = checkplanDetailService.selectPlan(assetId,planId);
        if (null == detail){
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_100005, ErrorEnum.E_100005.getErrorMsg()));
        }
        Integer checkStatus = detail.getCheckStatus();
        if (checkStatus == 1){
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_100006, ErrorEnum.E_100006.getErrorMsg()));
        }

        TfamAssetCheckresult tfamAssetCheckresult = new TfamAssetCheckresult();
        tfamAssetCheckresult.setId(req.getCheckResult());
        detail.setCheckResult(tfamAssetCheckresult);
        detail.setCheckDate(DateUtils.getDateTime());
        detail.setCheckStatus(1);
        detail.setRemark(req.getRemark());
        detail.setActNum(req.getActNum());
        String assetStatus = req.getAssetStatus();
        TfamAssetStatus tfamAssetStatus = new TfamAssetStatus();
        tfamAssetStatus.setId(assetStatus);
        detail.setAssetStatus(tfamAssetStatus);
        detail.setUserid(req.getUserId());
        checkplanDetailService.save(detail);



        TfamAsset newAsset = new TfamAsset();
        BeanUtils.copyProperties(asset,newAsset);

        newAsset.setPlace(req.getAddress());
        newAsset.setEmployeeno(req.getEmployeeno());
        User user = new User();
        user.setId(req.getUserId());
        newAsset.setUserid(user);
        Department department = new Department();
        department.setId(req.getDeptId());
        newAsset.setUsedeptid(department);
        if (null != req.getActNum()){
            newAsset.setNum(req.getActNum());
        }
        SaveAndUpdateReq saveAndUpdateReq = new SaveAndUpdateReq();
        BeanUtils.copyProperties(newAsset,saveAndUpdateReq);
        saveAndUpdateReq.setOperateType(OperatetAlterTypeEnum.UPDATE.getCode());
        saveAndUpdateReq.setReason("盘点调整");
        tfamAssetService.saveAndUpdate(saveAndUpdateReq);


        Integer alreadyNum = checkplanDetailService.selectAlreadyCheckNum(planId);
        TfamAssetCheckplanMain checkplanMain = get(planId);
        checkplanMain.setAlreadyNum(alreadyNum+1);
        save(checkplanMain);
        return Boolean.TRUE;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean saveCheckAttach(ConditionDto conditionDto, MultipartFile[] files) throws Exception {
        User user = SessionUtils.getUser();

        List<TfamAssetCheckplanMainAttach> attachList = conditionDto.getAttachList();
        String mainId = conditionDto.getId();
        List<TfamAssetCheckplanMain> tfamAssetCheckplanMains = dao.MainById(mainId);
        String  mainIds="";
        if (tfamAssetCheckplanMains.size()!=0){
             mainIds = tfamAssetCheckplanMains.get(0).getId();
            attachList = attachService.selectByMainId(mainIds);
        }
        String cardId = conditionDto.getId();
        String cardCode = conditionDto.getCardCode();
        //判断盘点任务是否有效
        boolean fl = dao.checkMainById(mainId) != 0;
        if (fl){
            if (attachList.size()==files.length && !StringUtils.isEmpty(mainId)
                    && !StringUtils.isEmpty(cardId) && !StringUtils.isEmpty(cardCode)) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String dateText = format.format(new Date());
                conditionDto.setReason(dateText+"盘点资产时上传");
                //文件夹设置
                String basePath = savePath + File.separator + user.getCompanyId();
                File directory = new File(basePath);
                if(!directory.exists()){
                    directory.mkdirs();
                }
                for (int i = 0; i < files.length; i++) {
                    String originalFilename = files[i].getOriginalFilename();
                    String typeId = null;
//                    for (TfamAssetCheckplanMainAttach attach : attachList) {
//                        if (attach.getName().equals(originalFilename)){
//                            typeId = attach.getId();
//                            break;
//                        }
//                    }
                    typeId = attachList.get(i).getId();
                    if(!StringUtils.isEmpty(typeId)) {
                        //开始保存附件
                        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                        String pathname = basePath + File.separator + UUID.randomUUID().toString() + suffix;
                        files[i].transferTo(new File(pathname));
//                        ImageUtils.imageResize(new File(pathname),new File(pathname),
//                                700,1500,0.6f);
                        //插入附件信息到数据库
                        conditionDto.setAttachCategory(typeId);
                        conditionDto.setImagePath(pathname);
                        conditionDto.setOriginalFilename(originalFilename);
                        checkimageService.addAttachInfo(conditionDto);
                    }else {
                        throw new Exception("未标注附件类型");
                    }
                }
            }else {
                throw new Exception("上传参数异常");
            }
        }else {
            throw new Exception("当前盘点任务已结束或不存在！");
        }

        return false;
    }

    public void download(DownloadReq req ,HttpServletRequest request, HttpServletResponse response) throws Exception {
        String mainId = req.getMainId();
        String attach_category = req.getAttachCategory();
        if (!StringUtils.isEmpty(mainId) && !StringUtils.isEmpty(attach_category)){
            List<DownloadResp> list = this.dao.queryAttachZip(mainId,attach_category);
            if (!CollectionUtils.isEmpty(list)) {
                String task_name = list.get(0).getTaskName();
                String attach_title = list.get(0).getAttachTitle();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String zipName = format.format(new Date())+"_"+task_name+"_"+attach_title+".zip";
                response.setContentType("APPLICATION/OCTET-STREAM");
                //已解决标题为中文出现乱码
                response.setHeader("Content-Disposition","attachment; filename="+new String(zipName.getBytes("UTF-8"),"iso-8859-1"));
                try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())){
                    int tmpSort = 0;
                    String tmpStr = "";
                    for (DownloadResp downloadResp : list) {
                        String asset_code = downloadResp.getAssetCode();
                        if (StringUtils.equals(tmpStr,asset_code)){
                            tmpSort+=1;
                        }else {
                            tmpSort=1;
                            tmpStr = asset_code;
                        }
                        String path = downloadResp.getImagePath();
                        if (!StringUtils.isEmpty(path)){
                            String suffix = path.substring(path.lastIndexOf("."));
                            //下载后的新文件名称
                            String fileName = tmpStr+"("+tmpSort+")"+suffix;
                            ZipEntry zipEntry = new ZipEntry(fileName);
                            zos.putNextEntry(zipEntry);
                            try (FileInputStream inputStream = new FileInputStream(path)){
                                IOUtils.copy(inputStream,zos);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                    zos.closeEntry();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
