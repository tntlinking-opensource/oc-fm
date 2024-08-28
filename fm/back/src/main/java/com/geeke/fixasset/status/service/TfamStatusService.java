package com.geeke.fixasset.status.service;

import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.fixasset.status.dao.TfamStatusDao;
import com.geeke.fixasset.status.entity.TfamStatus;
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
 * 卡片状态Service
 * @author
 * @version
 */

@Service("tfamStatusService")
@Transactional(readOnly = true)
public class TfamStatusService extends CrudService<TfamStatusDao, TfamStatus> {

//    @Override
//    public Page<TfamStatus> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
//        String filter =
//            "'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
//        Page<TfamStatus> result = super.listPage(super.addFilter(parameters, filter), offset, limit, orderby);
//
//        return result;
//    }
//
//    @Override
//    public List<TfamStatus> listAll(List<Parameter> parameters, String orderby) {
//        String filter =
//            "'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
//        PageRequest pageRequest = new PageRequest(super.addFilter(parameters, filter), orderby);
//
//        List<TfamStatus> result = dao.listAll(pageRequest);
//
//        return result;
//    }
}
