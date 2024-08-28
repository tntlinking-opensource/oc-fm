package com.geeke.fixasset.category.service;

import com.alibaba.fastjson.JSON;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.fixasset.category.dao.TfamCategoryDao;
import com.geeke.fixasset.category.entity.TfamCategory;
import com.geeke.fixasset.changeway.entity.TfamChangeWay;
import com.geeke.org.entity.Company;
import com.geeke.utils.EasyPoiUtil;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.InputStream;
import java.math.BigDecimal;
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
 * 资产类别Service
 * @author
 * @version
 */

@Service("tfamCategoryService")
@Transactional(readOnly = false)
public class TfamCategoryService extends CrudService<TfamCategoryDao, TfamCategory> {
    private List<Map<String, Object>> g_list =  new ArrayList<>();
    @Override
    public Page<TfamCategory> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
        String filter =
            "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        Page<TfamCategory> result = super.listPage(super.addFilter(parameters, filter), offset, limit, orderby);

        return result;
    }

    @Override
    public List<TfamCategory> listAll(List<Parameter> parameters, String orderby) {
        String filter =
            "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        PageRequest pageRequest = new PageRequest(super.addFilter(parameters, filter), orderby);

        List<TfamCategory> result = dao.listAll(pageRequest);

        return result;
    }
    public List<Map<String, Object>> getTree(String parentid, int level) {
        String companyId = SessionUtils.getUser().getCompanyId().toString();
        level++;
        List<Map<String, Object>> list = this.dao.getTree(parentid,companyId);
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> entity = list.get(i);
            entity.put("level", level);
            g_list.add(entity);
//			System.out.println("----isleaf----"+entity.getInteger("isleaf"));
            if (entity.get("isleaf").toString().equals("0")) {
                String pid = entity.get("id").toString();
                entity.put("children", getTree(pid, level));
            }
        }
//		g_list.addAll(list);
        return list;
    }
    public int exportTfamCategory(HttpServletResponse response) throws Exception {
        String companyId = com.geeke.utils.SessionUtils.getUser().getCompanyId().toString();
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter("company_id", "=",companyId ));
        PageRequest pageRequest = new PageRequest(parameters);
        List<TfamCategory> tfamSuppliers = dao.listAll(pageRequest);
        List<Map<String, Object>>  mapList = new ArrayList<>();
        Map<String, String> title = new LinkedHashMap<>();
        title.put("类别编码","fullcode");
        title.put("类别名称","fullname");
        title.put("折旧方法","depmethod");
        title.put("预计使用月数","servicemonth");
        title.put("残值率","salvagerate");
        title.put("计量单位","unit");
        title.put("上级类别编码","parentcode");
        for (int i = 0; i < tfamSuppliers.size(); i++) {
            TfamCategory tfamChangeWay = tfamSuppliers.get(i);

            Map<String,Object> infoMap = new HashMap<>();
            infoMap.put("fullcode",tfamChangeWay.getFullcode());
            infoMap.put("fullname",tfamChangeWay.getFullname());
            infoMap.put("depmethod",tfamChangeWay.getDepmethod()==1?"年平均法":"其他");
            infoMap.put("servicemonth",tfamChangeWay.getServicemonth());
            infoMap.put("salvagerate",tfamChangeWay.getSalvagerate());
            infoMap.put("unit",tfamChangeWay.getUnit());
            infoMap.put("parentcode",tfamChangeWay.getParentcode());

            mapList.add(infoMap);
        }
        EasyPoiUtil.DownloadExcel(response,"类别管理",mapList,title);
        return tfamSuppliers.size();
    }
    private void checkFile(Sheet sheet) throws Exception {
        if (        "类别编码".equals(sheet.getCell(0, 0).getContents())
                && "类别名称".equals(sheet.getCell(1, 0).getContents())
                && "折旧方法".equals(sheet.getCell(2, 0).getContents())
                && "预计使用月数".equals(sheet.getCell(3, 0).getContents())
                && "残值率".equals(sheet.getCell(4, 0).getContents())
                && "计量单位".equals(sheet.getCell(5, 0).getContents())
                && "上级类别编码".equals(sheet.getCell(6, 0).getContents())
        )
        {}
        else
        {
            throw new Exception("模版不正确!请重新选择");
        }
    }
    public int importTfamCategory(InputStream is) throws Exception {
        String companyId = com.geeke.utils.SessionUtils.getUser().getCompanyId().toString();
        Workbook wk = Workbook.getWorkbook(is);
        Sheet st = wk.getSheet(0);  //读取第一个表格
        Company company = JSON.parseObject(com.geeke.sys.utils.SessionUtils.getUserJson().getJSONObject("company").toJSONString(), Company.class);
        int rowlength= st.getRows();  //获取表格数据的行数

        this.checkFile(st); //检查导入数据的字段是否匹配
        List<TfamCategory> list = new ArrayList<>();
        //因为第一行是字段名，所以循环从1开始
        for (int row = 1; row < rowlength; row++) {
            String fullcode = ObjectUtils.toString(st.getCell(0, row).getContents().trim());
            String fullname = ObjectUtils.toString(st.getCell(1, row).getContents().trim());
            String depmethod = ObjectUtils.toString(st.getCell(2, row).getContents().trim());
            String servicemonth = ObjectUtils.toString(st.getCell(3, row).getContents().trim());
            String salvagerate = ObjectUtils.toString(st.getCell(4, row).getContents().trim());
            String unit = ObjectUtils.toString(st.getCell(5, row).getContents().trim());
            String parentcode = ObjectUtils.toString(st.getCell(6, row).getContents().trim());
            TfamCategory tfamCategory = new TfamCategory();
            tfamCategory.setFullcode(fullcode);
            if (depmethod.equals("平均年限")){
                tfamCategory.setDepmethod(1);
            }else {
                tfamCategory.setDepmethod(1);
            }
            tfamCategory.setFullname(fullname);
            tfamCategory.setName(fullname);
            tfamCategory.setServicemonth(Integer.valueOf(servicemonth));
            tfamCategory.setSalvagerate(new BigDecimal(salvagerate));
            tfamCategory.setUnit(unit);
            tfamCategory.setParentcode(parentcode);
            tfamCategory.setCompanyId(companyId);
            tfamCategory.setIsleaf(1);
            list.add(tfamCategory);
        }

        //如果文件的数据都正确，则保存数据
        List<TfamCategory> inserts = Lists.newArrayList(); // 添加列表
        List<TfamCategory> updates = Lists.newArrayList(); // 更新列表
        for (int i = 0; i < list.size(); i++) {
            TfamCategory tfamSupplier = list.get(i);
            // 根据现有实体信息查询，判断数据库是否已存在记录
            List<Parameter> params = null;
            params = Lists.newArrayList();
            params.add(new Parameter("company_id", "=",companyId));
            params.add(new Parameter("fullcode", "=",tfamSupplier.getFullcode()));
            List<TfamCategory> tfamCategorys = this.listAll(params, "");
            if (!tfamSupplier.getParentcode().equals("0-0")){
                params = Lists.newArrayList();
                params.add(new Parameter("company_id", "=",companyId));
                params.add(new Parameter("fullcode", "=",tfamSupplier.getParentcode()));
                List<TfamCategory> tfamCategorys1 = this.listAll(params, "");
                if (tfamCategorys1.size()!=0) {
                    TfamCategory tfamCategory = tfamCategorys1.get(0);
                    tfamSupplier.setParentcode(tfamCategory.getFullcode());
                    tfamSupplier.setParentid(tfamCategory.getId());
                    if (tfamCategorys.size()==0){
                        params = Lists.newArrayList();
                        params.add(new Parameter("company_id", "=",companyId));
                        params.add(new Parameter("id", "=",tfamSupplier.getParentid()));
                        List<TfamCategory> tfamCategories = this.listAll(params, "");
                        if (tfamCategories.size()!=0){
                            TfamCategory tfamCategory3 = tfamCategories.get(0);
                            tfamCategory3.setIsleaf(0);
                            this.save(tfamCategory3);
                        }
                        inserts.add(tfamSupplier);
                        this.save(tfamSupplier);
                    }else {
                        TfamCategory tfamCategory1 = tfamCategorys.get(0);
                       tfamCategory1.setFullname(tfamSupplier.getFullname());
                       tfamCategory1.setName(tfamSupplier.getFullname());
                       tfamCategory1.setServicemonth(tfamSupplier.getServicemonth());
                       tfamCategory1.setSalvagerate(tfamSupplier.getSalvagerate());
                       tfamCategory1.setUnit(tfamSupplier.getUnit());
                       tfamCategory1.setParentcode(tfamSupplier.getParentcode());
                       tfamCategory1.setCompanyId(companyId);
                       tfamCategory1.setIsleaf(1);
                        this.save(tfamCategory1);
                    }
                }else {
                    throw new RuntimeException("父类编码"+tfamSupplier.getParentcode()+"不存在");
                }
            }else {
                tfamSupplier.setParentid("0");
                if (tfamCategorys.size()==0){
                    this.save(tfamSupplier);
                }else {
                    TfamCategory tfamCategory1 = tfamCategorys.get(0);
                    tfamCategory1.setFullname(tfamSupplier.getFullname());
                    tfamCategory1.setName(tfamSupplier.getFullname());
                    tfamCategory1.setServicemonth(tfamSupplier.getServicemonth());
                    tfamCategory1.setSalvagerate(tfamSupplier.getSalvagerate());
                    tfamCategory1.setUnit(tfamSupplier.getUnit());
                    tfamCategory1.setParentcode(tfamSupplier.getParentcode());
                    tfamCategory1.setCompanyId(companyId);
                    tfamCategory1.setIsleaf(1);
                    this.save(tfamCategory1);
                }
            }


        }
        return inserts.size() + updates.size();
    }
}
