package com.geeke.fixasset.usingstatus.service;

import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.fixasset.usingstatus.dao.TfamUsingstatusDao;
import com.geeke.fixasset.usingstatus.entity.TfamUsingstatus;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 卡片使用状态Service
 * @author
 * @version
 */

@Service("tfamUsingstatusService")
@Transactional(readOnly = true)
public class TfamUsingstatusService extends CrudService<TfamUsingstatusDao, TfamUsingstatus> {

    @Override
    public Page<TfamUsingstatus> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
        String filter =
            "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        Page<TfamUsingstatus> result = super.listPage(super.addFilter(parameters, filter), offset, limit, orderby);

        return result;
    }

    @Override
    public List<TfamUsingstatus> listAll(List<Parameter> parameters, String orderby) {
        String filter =
            "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        PageRequest pageRequest = new PageRequest(super.addFilter(parameters, filter), orderby);

        List<TfamUsingstatus> result = dao.listAll(pageRequest);

        return result;
    }
}
