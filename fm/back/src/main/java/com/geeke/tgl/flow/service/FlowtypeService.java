package com.geeke.tgl.flow.service;

import com.alibaba.fastjson.JSON;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.TreeService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.org.entity.Company;
import com.geeke.sys.utils.SessionUtils;
import com.geeke.tgl.flow.dao.FlowtypeDao;
import com.geeke.tgl.flow.entity.Flowtype;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.*;

import jxl.Sheet;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 现金流量类别Service
 * @author
 * @version
 */

@Service("flowtypeService")
@Transactional(readOnly = true)
public class FlowtypeService extends TreeService<FlowtypeDao, Flowtype> {

    @Override
    public Page<Flowtype> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
        // 租户过滤
        String filter = "{'columnName':'tenant_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        Page<Flowtype> result = super.listPage(super.addFilter(parameters, filter), offset, limit, orderby);
        return result;
    }

    @Override
    public List<Flowtype> listAll(List<Parameter> parameters, String orderby) {
        // 租户过滤
        String filter = "{'columnName':'tenant_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        PageRequest pageRequest = new PageRequest(super.addFilter(parameters, filter), orderby);
        List<Flowtype> result = dao.listAll(pageRequest);
        return result;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public Flowtype save(Flowtype flowtype) {
        // 约束条件处理
        Map<String, String> colMaps = Maps.newHashMap();
        // 编号不可重复
        colMaps.clear();
        colMaps.put("uqparentid", "uqparentid.uqflowtypeid");
        colMaps.put("varcode", "varcode");
        colMaps.put("del_flag", "delFlag");
        if (exists(dao, flowtype, colMaps)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "新增失败: 该现金流量类别的编号已存在"));
        }
        // 名称不可重复
        colMaps.clear();
        colMaps.put("uqparentid", "uqparentid.uqflowtypeid");
        colMaps.put("varname", "varname");
        colMaps.put("del_flag", "delFlag");
        if (exists(dao, flowtype, colMaps)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "新增失败: 该现金流量类别名称已存在"));
        }
        // 判断该公司下是否已存在数据
        PageRequest pageRequest;
        List<Parameter> params = Lists.newArrayList();
        params.add(new Parameter("tenant_id", "=", flowtype.getTenantId()));
        pageRequest = new PageRequest(params);
        int count = dao.count(pageRequest);
        if (count <= 0) { // 首次添加
            Flowtype parentType = saveRootNode(flowtype.getTenantId()); // 根节点
            // 根节点作为本次添加的父级
            flowtype.setParent(parentType);
            flowtype.setUqparentid(parentType);
            flowtype.setVarfullname(parentType.getVarname() + "-" + flowtype.getVarname());
            flowtype.setVarfullcode(parentType.getVarcode() + "-" + flowtype.getVarcode());
        }
        if (StringUtils.isBlank(flowtype.getId())) { // 新增
            flowtype.setUqflowtypeid(UUID.randomUUID().toString());
        }
        Flowtype flowtypeTemp = super.save(flowtype);
        return flowtypeTemp;
    }

    /**
     * 保存现金流量类别根节点
     * @param tenantId
     */
    public Flowtype saveRootNode(String tenantId) {
        Flowtype flowtype = new Flowtype();
        flowtype.setTenantId(tenantId);
        flowtype.setUqflowtypeid("00000000-0000-0000-0000-000000000000");
        flowtype.setVarcode("0000");
        flowtype.setVarname("现金流量分类");
        flowtype.setVarfullcode("0000");
        flowtype.setVarfullname("现金流量分类");
        Flowtype parent = new Flowtype();
        parent.setUqflowtypeid("0");
        flowtype.setUqparentid(parent);
        flowtype.setIntlevel(0); // 级次从0开始
        flowtype.setIntislastlevel(0);
        flowtype.setSort(0);
        return super.save(flowtype);
    }

    public int importFlowtype(Sheet st) {
        //检查表格1格式是否正确
        checkFile(st);
        //获取表格数据的行数
        int rowlength = st.getRows();
        List<Flowtype> list = new ArrayList<Flowtype>();
        if (Objects.isNull(SessionUtils.getUserJson())) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "获取当前登录人公司信息失败！"));
        }
        Company company = JSON.parseObject(SessionUtils.getUserJson().getJSONObject("company").toJSONString(), Company.class);
        // 获取上级类别，默认为根目录
        List<Parameter> typeParams = Lists.newArrayList();
        typeParams.add(new Parameter("uqflowtypeid", "=", "00000000-0000-0000-0000-000000000000"));
        typeParams.add(new Parameter("tenant_id", "=", company.getId()));
        List<Flowtype> flowtypeList = super.listAll(typeParams, "");
        Flowtype flowtype = new Flowtype();
        if (null != flowtypeList && flowtypeList.size() > 0) {
            flowtype = flowtypeList.get(0);
        } else { // 不存在根目录，首次导入
            flowtype = saveRootNode(company.getId());
        }
        //第一行是列名 循环获得数据从1开始
        for (int row = 1; row < rowlength; row++) {
            //获得每行各列的值
            String vartypecode = ObjectUtils.toString(st.getCell(0, row).getContents().trim());
            String vartypename = ObjectUtils.toString(st.getCell(1, row).getContents().trim());
            //放入EntityMap中
            Flowtype entity = new Flowtype();
            entity.setUqflowtypeid(UUID.randomUUID().toString());
            entity.setVarcode(vartypecode);
            entity.setVarname(vartypename);
            entity.setUqparentid(flowtype);
            entity.setParent(flowtype);
            entity.setIntislastlevel(1); // 是否末级
            entity.setIntlevel(1); // 级次
            entity.setTenantId(company.getId());
            entity.setVarfullname(flowtype.getVarname() + "-" + entity.getVarname());
            entity.setVarfullcode(flowtype.getVarcode() + "-" + entity.getVarcode());
            list.add(entity);
        }
        //检查数据关系(包括行的每个格子是否有值，且导入的整个sheet里面是否有存在相同类别编码的数据)
        chackTypeData(list);
        // 执行批量保存操作
        List<String> strings = super.bulkInsert(list);
        return strings.size();
    }

    /**
     * 检查类别数据
     * @param list 检验数据
     * @throws Exception
     */
    public void chackTypeData(List<Flowtype> list) {
        StringBuffer errormsg = new StringBuffer();
        Map<String, Integer> map = new HashMap<String, Integer>();
        for(int i = 0; i < list.size(); i++ ) {
            // 获取上级类别，默认为根目录
            // Flowtype flowtype = flowtypeService.get("00000000-0000-0000-0000-000000000000");
            //表中真实行数
            int j = i + 2;
            Flowtype entity = list.get(i);
            String vartypecode = entity.getVarcode();
            String vartypename = entity.getVarname();
            //检查非空的字段
            boolean istrue = true;
            if(StringUtils.isBlank(entity.getVarcode())) {
                errormsg.append("表一中第 "+ j + " 行[现金流量类别编码]为空|");
                istrue = false;
            }
            if(StringUtils.isBlank(entity.getVarname())) {
                errormsg.append("表一中第 \"+ j + \" 行[现金流量类别名称]为空|");
                istrue = false;
            }
            // 约束条件处理
            Map<String, String> colMaps = Maps.newHashMap();
            // 编号不可重复
            colMaps.clear();
            colMaps.put("uqparentid", "uqparentid.uqflowtypeid");
            colMaps.put("varcode", "varcode");
            colMaps.put("del_flag", "delFlag");
            if (exists(dao, entity, colMaps)) {
                errormsg.append("表一中第 "+ j + " 行[现金流量类别编码]已经存在|");
                istrue = false;
            }
            // 名称不可重复
            colMaps.clear();
            colMaps.put("uqparentid", "uqparentid.uqflowtypeid");
            colMaps.put("varname", "varname");
            colMaps.put("del_flag", "delFlag");
            if (exists(dao, entity, colMaps)) {
                errormsg.append("表一中第 "+ j + " 行[现金流量类别名称]已经存在|");
                istrue = false;
            }
            if(istrue) {
                //判断code是否重复
                if (map.containsKey(vartypecode)) {
                    //检验现金流量类别编码重复记录
                    map.put(vartypecode, 1);
                    errormsg.append("表一中第"+ j + "行数据的现金流量类别编码存在重复|");
                    istrue = false;
                } else {
                    map.put(vartypecode, 0);
                }
                //判断名称是否重复
                if (map.containsKey(vartypename)) {
                    //检验现金流量类别编码重复记录
                    map.put(vartypename, 1);
                    errormsg.append("表一中第"+ j + "行数据的现金流量类别名称存在重复|");
                    istrue = false;
                } else {
                    map.put(vartypename, 0);
                }
            }
            if (!istrue) {
                throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, errormsg.toString()));
            }
        }
    }

    /**
     * 检查导入文件表格的格式是否正确
     * @param st	表格
     * @throws Exception
     */
    public void checkFile(Sheet st) {
        //对比列名是否正确
        if ("现金流量类别编码".equals(st.getCell(0, 0).getContents())
                && "现金流量类别名称".equals(st.getCell(1, 0).getContents())) {
        } else {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "模板表格一格式不正确，请检查!"));
        }
    }
}
