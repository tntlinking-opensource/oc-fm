package com.geeke.fixasset.producer.service;

import com.alibaba.fastjson.JSON;
import com.geeke.assetting.entity.TfamProductSwitch;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.fixasset.producer.dao.TfamSupplierDao;
import com.geeke.fixasset.producer.entity.TfamSupplier;
import com.geeke.org.entity.Company;
import com.geeke.sys.utils.SessionUtils;
import com.geeke.tgl.global.entity.GlobalPeriods;
import com.geeke.utils.EasyPoiUtil;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.vouchermanager.entity.CompanyLedgerPeriods;
import com.geeke.vouchermanager.entity.TglAccounts;
import com.geeke.vouchermanager.entity.TglLedger;
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
 * 供应商管理Service
 * @author
 * @version
 */

@Service("tfamSupplierService")
@Transactional(readOnly = false)
public class TfamSupplierService extends CrudService<TfamSupplierDao, TfamSupplier> {

    @Override
    public Page<TfamSupplier> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
        String filter =
            "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        Page<TfamSupplier> result = super.listPage(super.addFilter(parameters, filter), offset, limit, orderby);

        return result;
    }

    @Override
    public List<TfamSupplier> listAll(List<Parameter> parameters, String orderby) {
        String filter =
            "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        PageRequest pageRequest = new PageRequest(super.addFilter(parameters, filter), orderby);

        List<TfamSupplier> result = dao.listAll(pageRequest);

        return result;
    }

    public int exportTfamSupplier(HttpServletResponse response) throws Exception {
        String companyId = com.geeke.utils.SessionUtils.getUser().getCompanyId().toString();
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter("company_id", "=",companyId ));
        PageRequest pageRequest = new PageRequest(parameters);
        List<TfamSupplier> tfamSuppliers = dao.listAll(pageRequest);
        List<Map<String, Object>>  mapList = new ArrayList<>();
        Map<String, String> title = new LinkedHashMap<>();
        title.put("编号","code");
        title.put("供应商名称","name");
        for (int i = 0; i < tfamSuppliers.size(); i++) {
            TfamSupplier tglLedger = tfamSuppliers.get(i);

            Map<String,Object> infoMap = new HashMap<>();
            infoMap.put("code",tglLedger.getCode());
            infoMap.put("name",tglLedger.getName());
            mapList.add(infoMap);
        }
        EasyPoiUtil.DownloadExcel(response,"供应商管理",mapList,title);
        return tfamSuppliers.size();
    }
    private void checkFile(Sheet sheet) throws Exception {
        if ("编号".equals(sheet.getCell(0, 0).getContents())
                && "供应商名称".equals(sheet.getCell(1, 0).getContents()))
        {}
        else
        {
            throw new Exception("模版不正确!请重新选择");
        }
    }
    public int importTfamSupplier(InputStream is) throws Exception {
        String companyId = com.geeke.utils.SessionUtils.getUser().getCompanyId().toString();
        Workbook wk = Workbook.getWorkbook(is);
        Sheet st = wk.getSheet(0);  //读取第一个表格
        Company company = JSON.parseObject(SessionUtils.getUserJson().getJSONObject("company").toJSONString(), Company.class);
        int rowlength= st.getRows();  //获取表格数据的行数

        this.checkFile(st); //检查导入数据的字段是否匹配
        List<TfamSupplier> list = new ArrayList<>();
        //因为第一行是字段名，所以循环从1开始
        for (int row = 1; row < rowlength; row++) {
            String code = ObjectUtils.toString(st.getCell(0, row).getContents().trim());
            String name = ObjectUtils.toString(st.getCell(1, row).getContents().trim());

            TfamSupplier tfamSupplier = new TfamSupplier();
            tfamSupplier.setCode(Integer.valueOf(code));
            tfamSupplier.setName(name);
            tfamSupplier.setCompanyId(companyId);
            list.add(tfamSupplier);
        }

        //如果文件的数据都正确，则保存数据
        List<TfamSupplier> inserts = Lists.newArrayList(); // 添加列表
        List<TfamSupplier> updates = Lists.newArrayList(); // 更新列表
        for (int i = 0; i < list.size(); i++) {
            TfamSupplier tfamSupplier = list.get(i);
            // 根据现有实体信息查询，判断数据库是否已存在记录
            List<Parameter> params = null;
            params = Lists.newArrayList();
            params.add(new Parameter("company_id", "=",companyId));
            params.add(new Parameter("code", "=",tfamSupplier.getCode()));
            List<TfamSupplier> tfamProductSwitches = this.listAll(params, "");
            if (tfamProductSwitches.size()==0){
                inserts.add(tfamSupplier);
            }else {
                TfamSupplier tfamSupplier1 = tfamProductSwitches.get(0);
                tfamSupplier1.setCode(tfamSupplier.getCode());
                tfamSupplier1.setName(tfamSupplier.getName());
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
