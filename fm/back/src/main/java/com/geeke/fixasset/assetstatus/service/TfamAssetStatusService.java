package com.geeke.fixasset.assetstatus.service;

import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.fixasset.assetstatus.dao.TfamAssetStatusDao;
import com.geeke.fixasset.assetstatus.entity.TfamAssetStatus;
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
 * 卡片盘点状态Service
 * @author
 * @version
 */

@Service("tfamAssetStatusService")
@Transactional(readOnly = true)
public class TfamAssetStatusService extends CrudService<TfamAssetStatusDao, TfamAssetStatus> {

    @Override
    public Page<TfamAssetStatus> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
        String filter =
            "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        Page<TfamAssetStatus> result = super.listPage(super.addFilter(parameters, filter), offset, limit, orderby);

        return result;
    }

    @Override
    public List<TfamAssetStatus> listAll(List<Parameter> parameters, String orderby) {
        String filter =
            "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        PageRequest pageRequest = new PageRequest(super.addFilter(parameters, filter), orderby);

        List<TfamAssetStatus> result = dao.listAll(pageRequest);

        return result;
    }
}
