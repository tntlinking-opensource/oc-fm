package com.geeke.assetting.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.geeke.assetting.dao.TfamAssetManagerDeptDao;
import com.geeke.assetting.entity.TfamAssetManagerDept;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 卡片所属部门Service
 * @author
 * @version
 */

@Service("tfamAssetManagerDeptService")
@Transactional(readOnly = false)
public class TfamAssetManagerDeptService extends CrudService<TfamAssetManagerDeptDao, TfamAssetManagerDept> {

    @Override
    public Page<TfamAssetManagerDept> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
        String filter =
            "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        Page<TfamAssetManagerDept> result = super.listPage(super.addFilter(parameters, filter), offset, limit, orderby);

        return result;
    }

    @Override
    public List<TfamAssetManagerDept> listAll(List<Parameter> parameters, String orderby) {
        String filter =
            "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        PageRequest pageRequest = new PageRequest(super.addFilter(parameters, filter), orderby);

        List<TfamAssetManagerDept> result = dao.listAll(pageRequest);

        return result;
    }
    public void addAssetManagerDept(JSONObject list) {
        JSONArray multipleSelection = list.getJSONArray("multipleSelection");
        String companyId = list.getString("companyId");
        int i = dao.deleteAssetManagerDept(list.getString("userid"));
        for (Object o : multipleSelection) {
            JSONObject jsonObject = JSONObject.parseObject(String.valueOf(o));
            TfamAssetManagerDept tfamAssetManagerDept = new TfamAssetManagerDept();
            tfamAssetManagerDept.setCompanyId(companyId);
            tfamAssetManagerDept.setUserid(list.getString("userid"));
            tfamAssetManagerDept.setDeptid(jsonObject.getString("id"));
            this.save(tfamAssetManagerDept);
        }

    }
}
