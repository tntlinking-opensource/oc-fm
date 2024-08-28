package com.geeke.tgl.flow.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.org.entity.Company;
import com.geeke.sys.entity.DictItem;
import com.geeke.sys.utils.SessionUtils;
import com.geeke.tgl.flow.dao.FlowitemsDao;
import com.geeke.tgl.flow.dao.FlowtypeDao;
import com.geeke.tgl.flow.entity.Flowitems;
import com.geeke.tgl.flow.entity.Flowtype;
import com.geeke.utils.EasyPoiUtil;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;

import java.io.InputStream;
import java.util.*;

import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

/**
 * 现金流量项目Service
 * @author
 * @version
 */

@Service("flowitemsService")
@Transactional(readOnly = true)
public class FlowitemsService extends CrudService<FlowitemsDao, Flowitems> {

    @Autowired
    private FlowtypeService flowtypeService;
    @Autowired
    private FlowtypeDao flowtypeDao;

    @Override
    public Page<Flowitems> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
        String filter = "{columnName: 'uqflowitemid', queryType: '!=', value: '00000000-0000-0000-0000-000000000000'},{'columnName':'tenant_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        Page<Flowitems> result = super.listPage(super.addFilter(parameters, filter), offset, limit, orderby);
        return result;
    }

    @Override
    public List<Flowitems> listAll(List<Parameter> parameters, String orderby) {
        String filter = "{columnName: 'uqflowitemid', queryType: '!=', value: '00000000-0000-0000-0000-000000000000'},{'columnName':'tenant_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        PageRequest pageRequest = new PageRequest(super.addFilter(parameters, filter), orderby);
        List<Flowitems> result = dao.listAll(pageRequest);
        return result;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public Flowitems save(Flowitems flowitems) {
        // 约束条件处理
        Map<String, String> colMaps = Maps.newHashMap();
        // 编号唯一
        colMaps.clear();
        colMaps.put("uqflowtypeid", "uqflowtypeid.uqflowtypeid");
        colMaps.put("varcode", "varcode");
        colMaps.put("del_flag", "delFlag");
        if (exists(dao, flowitems, colMaps)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "新增失败: 该现金流量项目的编号已存在"));
        }
        // 名称唯一
        colMaps.clear();
        colMaps.put("uqflowtypeid", "uqflowtypeid.uqflowtypeid");
        colMaps.put("varname", "varname");
        colMaps.put("del_flag", "delFlag");
        if (exists(dao, flowitems, colMaps)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "新增失败: 该现金流量项目名称已存在"));
        }
        if (StringUtils.isBlank(flowitems.getId())) { // 新增
            flowitems.setUqflowitemid(UUID.randomUUID().toString());
        }
        Flowitems flowitemsTemp = super.save(flowitems);
        return flowitemsTemp;
    }

    public int exportCashFlowInfo(HttpServletResponse response) throws Exception {
        List<Parameter> parameters = new ArrayList<>();
        List<Flowitems> list = dao.listAll(new PageRequest(parameters, ""));
        List<Map<String, Object>>  mapList = new ArrayList<>();
        Map<String, String> title = new LinkedHashMap<>();
        title.put("现金流量项目编码","varcode");
        title.put("现金流量项目名称","varname");
        title.put("所属类别编码","typeVarcode");
        title.put("所属类别名称","typeVarname");
        title.put("状态","intstatus");
        for(int i=0; i < list.size(); i++){
            Integer intstatus = list.get(i).getIntstatus();
            Map<String,Object> map = new HashMap<>();
            map.put("typeVarcode", list.get(i).getUqflowtypeid().getVarcode());
            map.put("typeVarname", list.get(i).getUqflowtypeid().getVarname());
            map.put("varcode", list.get(i).getVarcode());
            map.put("varname", list.get(i).getVarname());
            if (intstatus == 0) {
                map.put("intstatus", "新增");
            } else if (intstatus == 1) {
                map.put("intstatus", "启用");
            } else {
                map.put("intstatus", "停用");
            }
            mapList.add(map);
        }
        EasyPoiUtil.DownloadExcel(response,"现金流量",mapList,title);
        return list.size();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int importCashFlowFile(InputStream is) throws Exception {
        //读取需要导入的文件
        Workbook wk = Workbook.getWorkbook(is);
        //读取第一个 sheet 表
        Sheet st = wk.getSheet(0);
        //读取第二个 sheet表
        Sheet st2 = wk.getSheet(1);
        //获得 第一个sheet的行数
        int rowlength = st.getRows();
        //获得 第二个sheet的行数
        int rowlength2 = st2.getRows();
        //判断表格是否有数据 如果两个表格的行数都小于等于1则无数据
        if (rowlength <= 1 && rowlength2 <= 1) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "表格中没有数据!"));
        } else {
            //如果第一个sheet有数据
            int flowtype = 0; // 现金流量类别保存条数
            int flowitems = 0; // 现金流量项目保存条数
            if (rowlength > 1) {
                flowtype = flowtypeService.importFlowtype(st);
            }
            if(rowlength2 > 1) {
                flowitems = importFlowItems(st2);
            }
            return flowtype + flowitems;
        }
    }

    public int importFlowItems(Sheet st) throws Exception {
        //检查表格2格式是否正确
        checkFile(st);
        //获取表格数据的行数
        int rowlength = st.getRows();
        List<Flowitems> list = new ArrayList<>();
        if (Objects.isNull(SessionUtils.getUserJson())) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "获取当前登录人公司信息失败！"));
        }
        Company company = JSON.parseObject(SessionUtils.getUserJson().getJSONObject("company").toJSONString(), Company.class);
        for (int row = 1; row < rowlength; row++) {
            int j = row + 1;
            Flowitems entity = new Flowitems();
            String varitemscode = ObjectUtils.toString(st.getCell(0, row).getContents().trim());
            String varitemsname = ObjectUtils.toString(st.getCell(1, row).getContents().trim());
            String vartypecode = ObjectUtils.toString(st.getCell(2, row).getContents().trim());
            String vartype=ObjectUtils.toString(st.getCell(3, row).getContents().trim());
            //根据所属的类别code  获得所属类别的实体
            Flowtype flowType = flowtypeDao.getFlowTypeByCode(vartypecode);
            if (StringUtils.isNullOrEmpty(flowType)) {
                throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "表二中第 "+ j + "行[所属类别编号]填写错误！"));
            }
            entity.setVarcode(varitemscode);
            entity.setVarname(varitemsname);
            entity.setUqflowitemid(UUID.randomUUID().toString());
            entity.setUqflowtypeid(flowType);
            entity.setUqflowtypecode(vartypecode);
            DictItem dictItem = new DictItem();
            dictItem.setValue(vartype);
            entity.setDirect(dictItem); // 流向
            entity.setIntstatus(0);
            entity.setTenantId(company.getId());
            list.add(entity);
            if(varitemscode.equals("") && varitemscode.equals("") && vartypecode.equals("") && vartype.equals("")) {
                //excle 读取bug，会读取全部为空的数据，实际无数据，则直接跳过循环
                continue;
            }
        }
        //检查数据关系(包括行的每个格子是否有值，且导入的整个sheet里面是否有存在相同类别编码的数据)
        chackTypeData(list);
        // 执行批量保存操作
        List<String> strings = super.bulkInsert(list);
        return strings.size();
    }

    /**
     * 检查导入文件表格的格式是否正确
     * @param st	表格
     * @throws Exception
     */
    public void checkFile(Sheet st) {
        if("现金流量项目编码".equals(st.getCell(0, 0).getContents())
                && "现金流量项目名称".equals(st.getCell(1, 0).getContents())
                && "所属类别编码".equals(st.getCell(2, 0).getContents())
                && "类型".equals(st.getCell(3,0).getContents())) {
        } else {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "模板表格二格式不正确，请检查!"));
        }
    }

    /**
     * 检查类别数据
     * @param list 检验数据
     * @throws Exception
     */
    public void chackTypeData(List<Flowitems> list) {
        StringBuffer errormsg = new StringBuffer();
        Map<String, Integer> map = new HashMap<String, Integer>();
        for(int i = 0; i < list.size(); i++ ) {
            //表中真实行数
            int j = i + 2;
            Flowitems entity = list.get(i);
            String varitemscode = entity.getVarcode();
            String varitemsname = entity.getVarname();
            String vartypecode = entity.getUqflowtypecode();
            String vartype = entity.getDirect().getValue();
            //检验各项非空字段；
            boolean istrue = true;
            if (StringUtils.isBlank(varitemscode)) {
                errormsg.append("表二中第 "+ j + "行[现金流量项目编码]为空|");
                istrue = false;
            }
            if (StringUtils.isBlank(varitemsname)) {
                errormsg.append("表二中第 "+ j + "行[现金流量项目名称]为空|");
                istrue = false;
            }
            if (StringUtils.isBlank(vartypecode)) {
                errormsg.append("表二中第 "+ j + "行[所属类别编码]为空|");
                istrue = false;
            }
            if (StringUtils.isBlank(vartype)) {
                errormsg.append("表二中第 "+ j + "行[类型]为空|");
                istrue = false;
            }
            // 约束条件处理
            Map<String, String> colMaps = Maps.newHashMap();
            // 编号唯一
            colMaps.clear();
            colMaps.put("uqflowtypeid", "uqflowtypeid.uqflowtypeid");
            colMaps.put("varcode", "varcode");
            colMaps.put("del_flag", "delFlag");
            if (exists(dao, entity, colMaps)) {
                errormsg.append("表二中第"+ j + "条数据的项目编号与现有的项目编号重复|");
                istrue = false;
            } else {
                //不重复 还要判断是否与导入的是否重复
                if (map.containsKey(varitemscode)) {
                    map.put(varitemscode, 1);
                    errormsg.append("表二中第"+ j + "条数据的项目编号与本次导入的数据存在重复|");
                    istrue = false;
                } else {
                    map.put(varitemscode, 0);
                }
            }
            // 名称唯一
            colMaps.clear();
            colMaps.put("uqflowtypeid", "uqflowtypeid.uqflowtypeid");
            colMaps.put("varname", "varname");
            colMaps.put("del_flag", "delFlag");
            if (exists(dao, entity, colMaps)) {
                errormsg.append("表二中第"+ j + "条数据的项目名称与现有的项目名称重复|");
                istrue = false;
            } else {
                //不重复 还要判断是否与导入的是否重复
                if (map.containsKey(varitemsname)) {
                    map.put(varitemsname, 1);
                    errormsg.append("表二中第"+ j + "条数据的项目名称与本次导入的数据存在重复|");
                    istrue = false;
                } else {
                    map.put(varitemsname, 0);
                }
            }
            if (!istrue) {
                throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, errormsg.toString()));
            }
        }
    }
}
