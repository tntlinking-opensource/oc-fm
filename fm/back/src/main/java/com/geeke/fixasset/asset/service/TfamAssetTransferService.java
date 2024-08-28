package com.geeke.fixasset.asset.service;

import com.alibaba.fastjson.JSONObject;
import com.geeke.admin.dao.UserDao;
import com.geeke.admin.entity.User;
import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.fixasset.asset.dao.TfamAssetDao;
import com.geeke.fixasset.asset.dao.TfamAssetTransferDao;
import com.geeke.fixasset.asset.dto.SaveTransferDto;
import com.geeke.fixasset.asset.entity.TfamAsset;
import com.geeke.fixasset.asset.entity.TfamAssetTransfer;
import com.geeke.org.dao.DepartmentDao;
import com.geeke.org.entity.Department;
import com.geeke.utils.DateUtils;
import com.geeke.utils.Reflections;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 卡片管理Service
 * @author
 * @version
 */

@Service("tfamAssetTransferService")
@Transactional(readOnly = true)
public class TfamAssetTransferService extends CrudService<TfamAssetTransferDao, TfamAssetTransfer> {

    @Autowired
    private TfamAssetDao tfamAssetDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public Page<TfamAssetTransfer> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
        String filter =
            "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        Page<TfamAssetTransfer> result = super.listPage(super.addFilter(parameters, filter), offset, limit, orderby);

        return result;
    }

    @Override
    public List<TfamAssetTransfer> listAll(List<Parameter> parameters, String orderby) {
        String filter =
            "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        PageRequest pageRequest = new PageRequest(super.addFilter(parameters, filter), orderby);

        List<TfamAssetTransfer> result = dao.listAll(pageRequest);

        return result;
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public void saves(SaveTransferDto saveTransferDto){
        TfamAsset newAsset = saveTransferDto.getAsset();
        TfamAsset oldAsset = tfamAssetDao.get(newAsset.getId());

        TfamAssetTransfer tfamAssetTransfer = new TfamAssetTransfer();
        tfamAssetTransfer.setCard(newAsset);
        tfamAssetTransfer.setAfterTransferOwner(newAsset.getUserid().getName());
        tfamAssetTransfer.setAfterTransferDepartment(newAsset.getUsedeptid().getName());
        tfamAssetTransfer.setBeforeTransferOwner(oldAsset.getUserid().getName());
        tfamAssetTransfer.setBeforeTransferDepartment(oldAsset.getUsedeptid().getName());
        tfamAssetTransfer.setCompanyId(newAsset.getCompanyId());
        tfamAssetTransfer.setTransferReason(saveTransferDto.getTransferReason());
        tfamAssetTransfer.setTransferDate(DateUtils.getDateTime());
        save(tfamAssetTransfer);
    }

    public JSONObject getVoucherId(String id) {
        List<JSONObject> list = tfamAssetDao.getVoucherId(id);
        JSONObject entityMap = new JSONObject();
        if(list.size( )>0 )
        {
            entityMap = list.get(0);
        }else
        {
            entityMap.put("alterid","");
            entityMap.put("voucherid","");
            entityMap.put("intvouchernum","");
        }
        return entityMap;

    }
}
