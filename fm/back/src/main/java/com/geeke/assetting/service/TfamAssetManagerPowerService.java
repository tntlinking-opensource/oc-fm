package com.geeke.assetting.service;

import com.geeke.assetting.dao.TfamAssetManagerPowerDao;
import com.geeke.assetting.entity.TfamAssetManagerPower;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 资产权限表Service
 * @author
 * @version
 */

@Service("tfamAssetManagerPowerService")
@Transactional(readOnly = true)
public class TfamAssetManagerPowerService extends CrudService<TfamAssetManagerPowerDao, TfamAssetManagerPower> {

    @Override
    public Page<TfamAssetManagerPower> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
        String filter =
            "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        Page<TfamAssetManagerPower> result = super.listPage(super.addFilter(parameters, filter), offset, limit, orderby);

        return result;
    }

    @Override
    public List<TfamAssetManagerPower> listAll(List<Parameter> parameters, String orderby) {
        String filter =
            "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        PageRequest pageRequest = new PageRequest(super.addFilter(parameters, filter), orderby);

        List<TfamAssetManagerPower> result = dao.listAll(pageRequest);

        return result;
    }

    public Boolean checkPower(String switchName,String userid,String companyId){
        return this.checkPower(switchName,userid,companyId,0);
    }

    public Boolean checkPower(String switchName,String userid,String companyId,Integer open){
        List<Parameter> parameters = new ArrayList<>();
        String filter1 =
                "{'columnName':'company_id', 'queryType': '=', 'value': "+companyId+"}";
        Parameter parameter = new Parameter();
        parameter.setColumnName("switch_name");
        parameter.setQueryType("=");
        parameter.setValue(switchName);
        parameters.add(parameter);
        parameter.setColumnName("userid");
        parameter.setQueryType("=");
        parameter.setValue(userid);
        parameters.add(parameter);
        super.addFilter(parameters, filter1);
        PageRequest pageRequest = new PageRequest(parameters, null);
        List<TfamAssetManagerPower> result = dao.listAll(pageRequest);
        if (result!=null && result.size()>0){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
