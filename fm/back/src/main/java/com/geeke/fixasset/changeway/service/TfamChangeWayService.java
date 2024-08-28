package com.geeke.fixasset.changeway.service;

import com.alibaba.fastjson.JSON;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.fixasset.changeway.dao.TfamChangeWayDao;
import com.geeke.fixasset.changeway.entity.TfamChangeWay;
import com.geeke.fixasset.producer.entity.TfamSupplier;
import com.geeke.org.entity.Company;
import com.geeke.sys.utils.SessionUtils;
import com.geeke.utils.EasyPoiUtil;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
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
 * 变动方式Service
 * @author
 * @version
 */

@Service("tfamChangeWayService")
@Transactional(readOnly = false)
public class TfamChangeWayService extends CrudService<TfamChangeWayDao, TfamChangeWay> {

    @Override
    public Page<TfamChangeWay> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
        String filter =
            "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        Page<TfamChangeWay> result = super.listPage(super.addFilter(parameters, filter), offset, limit, orderby);

        return result;
    }

    @Override
    public List<TfamChangeWay> listAll(List<Parameter> parameters, String orderby) {
        String filter =
            "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        PageRequest pageRequest = new PageRequest(super.addFilter(parameters, filter), orderby);

        List<TfamChangeWay> result = dao.listAll(pageRequest);

        return result;
    }
    public int exportTfamChangeWay(HttpServletResponse response) throws Exception {
        String companyId = com.geeke.utils.SessionUtils.getUser().getCompanyId().toString();
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter("company_id", "=",companyId ));
        PageRequest pageRequest = new PageRequest(parameters);
        List<TfamChangeWay> tfamSuppliers = dao.listAll(pageRequest);
        List<Map<String, Object>>  mapList = new ArrayList<>();
        Map<String, String> title = new LinkedHashMap<>();
        title.put("编号","code");
        title.put("方式名称","name");
        title.put("方式类型","type");
        for (int i = 0; i < tfamSuppliers.size(); i++) {
            TfamChangeWay tfamChangeWay = tfamSuppliers.get(i);

            Map<String,Object> infoMap = new HashMap<>();
            infoMap.put("code",tfamChangeWay.getCode());
            infoMap.put("name",tfamChangeWay.getName());
            if (tfamChangeWay.getType().equals("增加")){
                infoMap.put("type","增加");
            }else {
                infoMap.put("type","减少");
            }

            mapList.add(infoMap);
        }
        EasyPoiUtil.DownloadExcel(response,"增减方式管理",mapList,title);
        return tfamSuppliers.size();
    }
    private void checkFile(Sheet sheet) throws Exception {
        if ("编号".equals(sheet.getCell(0, 0).getContents())
                && "方式名称".equals(sheet.getCell(1, 0).getContents())&& "方式类型".equals(sheet.getCell(2, 0).getContents()))
        {}
        else
        {
            throw new Exception("模版不正确!请重新选择");
        }
    }
    public int importTfamChangeWay(InputStream is) throws Exception {
        String companyId = com.geeke.utils.SessionUtils.getUser().getCompanyId().toString();
        Workbook wk = Workbook.getWorkbook(is);
        Sheet st = wk.getSheet(0);  //读取第一个表格
        Company company = JSON.parseObject(SessionUtils.getUserJson().getJSONObject("company").toJSONString(), Company.class);
        int rowlength= st.getRows();  //获取表格数据的行数

        this.checkFile(st); //检查导入数据的字段是否匹配
        List<TfamChangeWay> list = new ArrayList<>();
        //因为第一行是字段名，所以循环从1开始
        for (int row = 1; row < rowlength; row++) {
            String code = ObjectUtils.toString(st.getCell(0, row).getContents().trim());
            String name = ObjectUtils.toString(st.getCell(1, row).getContents().trim());
            String type = ObjectUtils.toString(st.getCell(2, row).getContents().trim());
            TfamChangeWay tfamSupplier = new TfamChangeWay();
            tfamSupplier.setCode(Integer.valueOf(code));
            tfamSupplier.setName(name);
            if (type.equals("增加")){
                tfamSupplier.setType(1);
            }else {
                tfamSupplier.setType(2);
            }
            tfamSupplier.setCompanyId(companyId);
            list.add(tfamSupplier);
        }

        //如果文件的数据都正确，则保存数据
        List<TfamChangeWay> inserts = Lists.newArrayList(); // 添加列表
        List<TfamChangeWay> updates = Lists.newArrayList(); // 更新列表
        for (int i = 0; i < list.size(); i++) {
            TfamChangeWay tfamSupplier = list.get(i);
            // 根据现有实体信息查询，判断数据库是否已存在记录
            List<Parameter> params = null;
            params = Lists.newArrayList();
            params.add(new Parameter("company_id", "=",companyId));
            params.add(new Parameter("code", "=",tfamSupplier.getCode()));
            List<TfamChangeWay> tfamProductSwitches = this.listAll(params, "");
            if (tfamProductSwitches.size()==0){
                inserts.add(tfamSupplier);
            }else {
                TfamChangeWay tfamSupplier1 = tfamProductSwitches.get(0);
                tfamSupplier1.setCode(tfamSupplier.getCode());
                tfamSupplier1.setName(tfamSupplier.getName());
                tfamSupplier1.setType(tfamSupplier.getType());
                updates.add(tfamSupplier1);
            }

        }
        if (updates.size() > 0) {
            this.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            this.bulkInsert(inserts);
        }
        return inserts.size() + updates.size();
    }
}
