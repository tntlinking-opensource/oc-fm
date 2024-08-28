package com.geeke.fixasset.asset.service;

import com.geeke.admin.dao.UserDao;
import com.geeke.admin.entity.User;
import com.geeke.assetting.service.TfamAssetManagerPowerService;
import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.sequence.service.SequenceService;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.fixasset.alter.dto.SaveAlterDto;
import com.geeke.fixasset.alter.entity.TfamAlter;
import com.geeke.fixasset.alter.service.TfamAlterService;
import com.geeke.fixasset.asset.dao.TfamAccessoryDao;
import com.geeke.fixasset.asset.dao.TfamAsset2qrcodeDao;
import com.geeke.fixasset.asset.dao.TfamAssetDao;
import com.geeke.fixasset.asset.dao.TfamAssetHistroyDao;
import com.geeke.fixasset.asset.dao.TfamAssetTransferDao;
import com.geeke.fixasset.asset.dao.TfamDepsubDao;
import com.geeke.fixasset.asset.dto.*;
import com.geeke.fixasset.asset.entity.TfamAccessory;
import com.geeke.fixasset.asset.entity.TfamAsset;
import com.geeke.fixasset.asset.entity.TfamAsset2qrcode;
import com.geeke.fixasset.asset.entity.TfamAssetHistroy;
import com.geeke.fixasset.asset.entity.TfamAssetTransfer;
import com.geeke.fixasset.asset.entity.TfamDepsub;
import com.geeke.fixasset.asset.req.*;
import com.geeke.fixasset.asset.resp.*;
import com.geeke.fixasset.category.dao.TfamCategoryDao;
import com.geeke.fixasset.category.entity.TfamCategory;
import com.geeke.fixasset.depmethod.dao.TfamDepmethodDao;
import com.geeke.fixasset.depmethod.entity.TfamDepmethod;
import com.geeke.fixasset.enums.AuditStatusEnum;
import com.geeke.fixasset.enums.GroupConditionEnum;
import com.geeke.fixasset.enums.OperatetAlterTypeEnum;
import com.geeke.fixasset.nature.entity.TfamNature;
import com.geeke.fixasset.producer.dao.TfamSupplierDao;
import com.geeke.fixasset.producer.entity.TfamSupplier;
import com.geeke.fixasset.status.dao.TfamStatusDao;
import com.geeke.fixasset.status.entity.TfamStatus;
import com.geeke.fixasset.usingstatus.dao.TfamUsingstatusDao;
import com.geeke.fixasset.usingstatus.entity.TfamUsingstatus;
import com.geeke.org.dao.DepartmentDao;
import com.geeke.org.entity.Department;
import com.geeke.sys.entity.Action;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.utils.*;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.vouchermanager.entity.TglAccounts;
import com.geeke.vouchermanager.service.TglAccountsService;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 卡片管理Service
 * @author
 * @version
 */

@Service("tfamAssetService")
@Transactional(readOnly = true)
public class TfamAssetService extends CrudService<TfamAssetDao, TfamAsset> {

    public  String qrcode_path = "/data/soft/fm/upload/asset/qrcode/img";

    private String combine_path = "/data/soft/fm/upload/asset/qrcode/combine";

    private String pdf_path="/data/soft/fm/upload/asset/qrcode/pdf";

    private String blankPath = "/data/soft/fm/upload/asset/qrcode/blank/blank.jpg";

    @Autowired
    private TfamDepsubDao tfamDepsubDao;

    @Autowired
    private TfamDepsubService tfamDepsubService;

    @Autowired
    private TfamAssetTransferDao tfamAssetTransferDao;

    @Autowired
    private TfamAssetTransferService tfamAssetTransferService;

    @Autowired
    private TfamAccessoryDao tfamAccessoryDao;

    @Autowired
    private TfamAccessoryService tfamAccessoryService;

    @Autowired
    private TfamAsset2qrcodeDao tfamAsset2qrcodeDao;

    @Autowired
    private TfamAsset2qrcodeService tfamAsset2qrcodeService;

    @Autowired
    private TfamAssetHistroyDao tfamAssetHistroyDao;

    @Autowired
    private TfamAssetHistroyService tfamAssetHistroyService;

    @Autowired
    SequenceService sequenceService;

    @Autowired
    private TfamAsset2qrcodeService qrcodeService;

    @Autowired
    private TglAccountsService tglAccountsService;

    @Autowired
    private TfamCategoryDao tfamCategoryDao;

    @Autowired
    private TfamSupplierDao tfamSupplierDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private TfamUsingstatusDao tfamUsingstatusDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TfamStatusDao statusDao;

    @Autowired
    private TfamAssetManagerPowerService managerPowerService;

    @Autowired
    private TfamAlterService tfamAlterService;

    @Autowired
    private TfamDepmethodDao depmethodDao;


    @Override
    public Page<TfamAsset> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
        Iterator<Parameter> iterator = parameters.iterator();
        while (iterator.hasNext()){
            Parameter next = iterator.next();

            if ("userstatus".equals(next.getColumnName())){
                if (Objects.isNull(next.getValue())){
                    iterator.remove();
                }
            }
        }

//        parameters.removeAll(parameters);
        String filter =
            "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        Page<TfamAsset> result = super.listPage(super.addFilter(parameters, filter), offset, limit, orderby);
        List<TfamAsset> assetList = result.getRows();
        if (assetList != null){
            assetList.stream().forEach(asset->{
                asset.setAccudepAccountid(getAccountById(asset.getAccudepAccountid()));
                asset.setAssetAccountid(getAccountById(asset.getAssetAccountid()));
                asset.setDepcostAccountid(getAccountById(asset.getDepcostAccountid()));
                asset.setPredevaluateAccountid(getAccountById(asset.getPredevaluateAccountid()));
                asset.setPredevaluateCoraccountid(getAccountById(asset.getPredevaluateCoraccountid()));
                asset.setTaxAccountid(getAccountById(asset.getTaxAccountid()));
                asset.setProjectAccountid(getAccountById(asset.getProjectAccountid()));
            });
        }

        return result;
    }

    public TglAccounts getAccountById(TglAccounts tglAccounts){
        if (tglAccounts == null){
            return null;
        }
        return tglAccountsService.getAccountById(tglAccounts.getUqaccountid());
    }

    @Override
    public List<TfamAsset> listAll(List<Parameter> parameters, String orderby) {
        if(null != parameters){
            Iterator<Parameter> iterator = parameters.iterator();
            while (iterator.hasNext()){
                Parameter next = iterator.next();

                if ("userstatus".equals(next.getColumnName())){
                    if (Objects.isNull(next.getValue())){
                        iterator.remove();
                    }
                }
            }
        }
        String filter =
            "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        PageRequest pageRequest = new PageRequest(super.addFilter(parameters, filter), orderby);

        List<TfamAsset> result = dao.listAll(pageRequest);

        return result;
    }

    public CountAssetResp count(List<Parameter> parameters, String orderby) {

        if(null != parameters){
            Iterator<Parameter> iterator = parameters.iterator();
            while (iterator.hasNext()){
                Parameter next = iterator.next();

                if ("userstatus".equals(next.getColumnName())){
                    if (Objects.isNull(next.getValue())){
                        iterator.remove();
                    }
                }
            }
        }
        String filter =
                "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        PageRequest pageRequest = new PageRequest(super.addFilter(parameters, filter), orderby);

        CountAssetResp result = dao.countAssetTotal(pageRequest);
        return result;
    }

    public List<TfamAssetDto> listAlls(List<Parameter> parameters, String orderby) {
        String filter =
                "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        PageRequest pageRequest = new PageRequest(super.addFilter(parameters, filter), orderby);

        List<TfamAssetDto> result = dao.listAlls(pageRequest);

        return result;
    }

    @Override
    public TfamAsset get(String id) {
        TfamAsset tfamAsset = super.get(id);

        List<Parameter> params = null;

        /*获取子表列表   折旧明细表*/
        params = Lists.newArrayList();

        params.add(new Parameter("asset_id", "=", tfamAsset.getId()));
        tfamAsset.setTfamDepsubList(tfamDepsubService.listAll(params, ""));

        /*获取子表列表   资产盘点图片保存表-由company_id+card_no确定唯一。image存储-新增卡片-实物图片处上传的图片*/
        params = Lists.newArrayList();

        String filter =
            "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        params = super.addFilter(params, filter);

        /*获取子表列表   资产调拨表*/
        params = Lists.newArrayList();

        params = super.addFilter(params, filter);

        params.add(new Parameter("card_id", "=", tfamAsset.getId()));
        tfamAsset.setTfamAssetTransferList(tfamAssetTransferService.listAll(params, ""));

        /*获取子表列表   附属设备表*/
        params = Lists.newArrayList();

        params.add(new Parameter("card_id", "=", tfamAsset.getId()));
        tfamAsset.setTfamAccessoryList(tfamAccessoryService.listAll(params, ""));

        /*获取子表列表   存放资产二维码*/
        params = Lists.newArrayList();

        params.add(new Parameter("card_id", "=", tfamAsset.getId()));
        tfamAsset.setTfamAsset2qrcodeList(tfamAsset2qrcodeService.listAll(params, ""));

        /*获取子表列表   卡片历史表*/
        params = Lists.newArrayList();

        params.add(new Parameter("card_code", "=", tfamAsset.getId()));
        tfamAsset.setTfamAssetHistroyList(tfamAssetHistroyService.listAll(params, ""));

        return tfamAsset;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public TfamAsset save(TfamAsset tfamAsset) {
        // 新增时, 处理自动编号字段
        if (StringUtils.isBlank(tfamAsset.getId())) {
            String sn = sequenceService.generate(SessionUtils.getUser().getCompanyId(), "1234", tfamAsset);
            TfamStatus status = new TfamStatus();
            status.setId("1");
            tfamAsset.setStatus(status);
            tfamAsset.setCardCode(sn);
            tfamAsset.setYear(DateUtils.getYear());
            tfamAsset.setMonth(DateUtils.getMonth());
            tfamAsset.setBusinessDate(DateUtils.getDateTime());
            tfamAsset.setCompanyId(SessionUtils.getUser().getCompanyId());
            tfamAsset.setWorkloanunit("1");
            tfamAsset.setRemarks("无");


        }

        TfamAsset tfamAssetTemp = super.save(tfamAsset);
        if(StringUtils.isBlank(tfamAsset.getId())){
            //新增审核数据 入alter
            tfamAsset.setId(tfamAssetTemp.getId());
            SaveAlterDto saveAlterDto = new SaveAlterDto();
            saveAlterDto.setAsset(tfamAsset);
            saveAlterDto.setReason(OperatetAlterTypeEnum.ADD.getName());
            saveAlterDto.setOperatetType(OperatetAlterTypeEnum.ADD.getCode());
            tfamAlterService.save(saveAlterDto);
        }

        /* 保存子表数据     折旧明细表 */
//        saveTfamDepsubList(tfamAssetTemp);

        /* 保存子表数据     资产盘点图片保存表-由company_id+card_no确定唯一。image存储-新增卡片-实物图片处上传的图片 */
//        saveTfamAssetCheckimageList(tfamAssetTemp);

        /* 保存子表数据     资产调拨表 */
//        saveTfamAssetTransferList(tfamAssetTemp);

        /* 保存子表数据     附属设备表 */
        saveTfamAccessoryList(tfamAssetTemp);

        /* 保存子表数据     存放资产二维码 */
//        saveTfamAsset2qrcodeList(tfamAssetTemp);

        /* 保存子表数据     卡片历史表 */
//        saveTfamAssetHistroyList(tfamAssetTemp);

        return tfamAssetTemp;
    }

    /**
     * 删除
     * @param tfamAsset
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int delete(TfamAsset tfamAsset) {
        List<Parameter> params = null;
        PageRequest pageRequest;

        /* 处理子表     折旧明细表 */
//        params = Lists.newArrayList();
//
//        params.add(new Parameter("asset_id", "=", tfamAsset.getId()));
//        pageRequest = new PageRequest(params);
//        tfamAsset.setTfamDepsubList(tfamDepsubDao.listAll(pageRequest));
//
//        if (tfamAsset.getTfamDepsubList() != null && tfamAsset.getTfamDepsubList().size() > 0) {
//            tfamDepsubService.bulkDelete(tfamAsset.getTfamDepsubList());
//        }
//
//        /* 处理子表     资产盘点图片保存表-由company_id+card_no确定唯一。image存储-新增卡片-实物图片处上传的图片 */
//        params = Lists.newArrayList();
//
//        params.add(new Parameter("card_no", "=", tfamAsset.getId()));
//        pageRequest = new PageRequest(params);
//        tfamAsset.setTfamAssetCheckimageList(tfamAssetCheckimageDao.listAll(pageRequest));
//
//        if (tfamAsset.getTfamAssetCheckimageList() != null && tfamAsset.getTfamAssetCheckimageList().size() > 0) {
//            tfamAssetCheckimageService.bulkDelete(tfamAsset.getTfamAssetCheckimageList());
//        }
//
//        /* 处理子表     资产调拨表 */
//        params = Lists.newArrayList();
//
//        params.add(new Parameter("card_id", "=", tfamAsset.getId()));
//        pageRequest = new PageRequest(params);
//        tfamAsset.setTfamAssetTransferList(tfamAssetTransferDao.listAll(pageRequest));
//
//        if (tfamAsset.getTfamAssetTransferList() != null && tfamAsset.getTfamAssetTransferList().size() > 0) {
//            tfamAssetTransferService.bulkDelete(tfamAsset.getTfamAssetTransferList());
//        }
//
//        /* 处理子表     附属设备表 */
//        params = Lists.newArrayList();
//
//        params.add(new Parameter("card_id", "=", tfamAsset.getId()));
//        pageRequest = new PageRequest(params);
//        tfamAsset.setTfamAccessoryList(tfamAccessoryDao.listAll(pageRequest));
//
//        if (tfamAsset.getTfamAccessoryList() != null && tfamAsset.getTfamAccessoryList().size() > 0) {
//            tfamAccessoryService.bulkDelete(tfamAsset.getTfamAccessoryList());
//        }
//
//        /* 处理子表     存放资产二维码 */
//        params = Lists.newArrayList();
//
//        params.add(new Parameter("card_id", "=", tfamAsset.getId()));
//        pageRequest = new PageRequest(params);
//        tfamAsset.setTfamAsset2qrcodeList(tfamAsset2qrcodeDao.listAll(pageRequest));
//
//        if (tfamAsset.getTfamAsset2qrcodeList() != null && tfamAsset.getTfamAsset2qrcodeList().size() > 0) {
//            tfamAsset2qrcodeService.bulkDelete(tfamAsset.getTfamAsset2qrcodeList());
//        }
//
//        /* 处理子表     卡片历史表 */
//        params = Lists.newArrayList();
//
//        params.add(new Parameter("card_code", "=", tfamAsset.getId()));
//        pageRequest = new PageRequest(params);
//        tfamAsset.setTfamAssetHistroyList(tfamAssetHistroyDao.listAll(pageRequest));
//
//        if (tfamAsset.getTfamAssetHistroyList() != null && tfamAsset.getTfamAssetHistroyList().size() > 0) {
//            tfamAssetHistroyService.bulkDelete(tfamAsset.getTfamAssetHistroyList());
//        }

        int rows = super.delete(tfamAsset);
        return rows;
    }

    /**
     * 批量删除
     * @param tfamAssetList
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int bulkDelete(List<TfamAsset> tfamAssetList) {
        for (TfamAsset tfamAsset : tfamAssetList) {
            delete(tfamAsset);
        }

        return tfamAssetList.size();
    }

    /**
     * 生成操作日志
     * @param actionTypeId  操作类型Id
     * @param entity        操作的实体对象
     * @return
     */
    @Override
    protected Action createAction(String actionTypeId, TfamAsset entity) {
        Action action = super.createAction(actionTypeId, entity);
        if (action == null) {
            return null;
        }
        // 删除时记录把保存的数据保存到回收站
        if (ActionConstants.ACTION_DELETED.equals(actionTypeId)) {
            for (TfamDepsub child : entity.getTfamDepsubList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }


            for (TfamAssetTransfer child : entity.getTfamAssetTransferList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TfamAccessory child : entity.getTfamAccessoryList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TfamAsset2qrcode child : entity.getTfamAsset2qrcodeList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TfamAssetHistroy child : entity.getTfamAssetHistroyList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }
        }
        return action;
    }

    /* 保存子表数据     折旧明细表 */
    private void saveTfamDepsubList(TfamAsset tfamAsset) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("asset_id", "=", tfamAsset.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TfamDepsub> list_TfamDepsub = tfamDepsubDao.listAll(pageRequest);

        List<TfamDepsub> deletes = Lists.newArrayList(); // 删除列表
        List<TfamDepsub> inserts = Lists.newArrayList(); // 添加列表
        List<TfamDepsub> updates = Lists.newArrayList(); // 更新列表
        for (TfamDepsub tfamDepsubSaved : list_TfamDepsub) {
            boolean found = false;
            for (TfamDepsub tfamDepsub : tfamAsset.getTfamDepsubList()) {
                if (tfamDepsubSaved.getId().equals(tfamDepsub.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tfamDepsubSaved);
            }
        }
        if (deletes.size() > 0) {
            tfamDepsubService.bulkDelete(deletes);
        }
        for (TfamDepsub tfamDepsub : tfamAsset.getTfamDepsubList()) {
            if (StringUtils.isBlank(tfamDepsub.getId())) {
                tfamDepsub.setAsset(tfamAsset);

                inserts.add(tfamDepsub);
            } else {
                updates.add(tfamDepsub);
            }
        }
        if (updates.size() > 0) {
            tfamDepsubService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tfamDepsubService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     资产盘点图片保存表-由company_id+card_no确定唯一。image存储-新增卡片-实物图片处上传的图片 */
    private void saveTfamAssetCheckimageList(TfamAsset tfamAsset) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("card_no", "=", tfamAsset.getId()));
        PageRequest pageRequest = new PageRequest(params);
    }
    /* 保存子表数据     资产调拨表 */
    private void saveTfamAssetTransferList(TfamAsset tfamAsset) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("card_id", "=", tfamAsset.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TfamAssetTransfer> list_TfamAssetTransfer = tfamAssetTransferDao.listAll(pageRequest);

        List<TfamAssetTransfer> deletes = Lists.newArrayList(); // 删除列表
        List<TfamAssetTransfer> inserts = Lists.newArrayList(); // 添加列表
        List<TfamAssetTransfer> updates = Lists.newArrayList(); // 更新列表
        for (TfamAssetTransfer tfamAssetTransferSaved : list_TfamAssetTransfer) {
            boolean found = false;
            for (TfamAssetTransfer tfamAssetTransfer : tfamAsset.getTfamAssetTransferList()) {
                if (tfamAssetTransferSaved.getId().equals(tfamAssetTransfer.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tfamAssetTransferSaved);
            }
        }
        if (deletes.size() > 0) {
            tfamAssetTransferService.bulkDelete(deletes);
        }
        for (TfamAssetTransfer tfamAssetTransfer : tfamAsset.getTfamAssetTransferList()) {
            if (StringUtils.isBlank(tfamAssetTransfer.getId())) {
                tfamAssetTransfer.setCard(tfamAsset);

                inserts.add(tfamAssetTransfer);
            } else {
                updates.add(tfamAssetTransfer);
            }
        }
        if (updates.size() > 0) {
            tfamAssetTransferService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tfamAssetTransferService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     附属设备表 */
    private void saveTfamAccessoryList(TfamAsset tfamAsset) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("card_id", "=", tfamAsset.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TfamAccessory> list_TfamAccessory = tfamAccessoryDao.listAll(pageRequest);

        List<TfamAccessory> deletes = Lists.newArrayList(); // 删除列表
        List<TfamAccessory> inserts = Lists.newArrayList(); // 添加列表
        List<TfamAccessory> updates = Lists.newArrayList(); // 更新列表
        for (TfamAccessory tfamAccessorySaved : list_TfamAccessory) {
            boolean found = false;
            for (TfamAccessory tfamAccessory : tfamAsset.getTfamAccessoryList()) {
                if (tfamAccessorySaved.getId().equals(tfamAccessory.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tfamAccessorySaved);
            }
        }
        if (deletes.size() > 0) {
            tfamAccessoryService.bulkDelete(deletes);
        }
        for (TfamAccessory tfamAccessory : tfamAsset.getTfamAccessoryList()) {
            if (StringUtils.isBlank(tfamAccessory.getId())) {
                tfamAccessory.setCard(tfamAsset);

                inserts.add(tfamAccessory);
            } else {
                updates.add(tfamAccessory);
            }
        }
        if (updates.size() > 0) {
            tfamAccessoryService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tfamAccessoryService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     存放资产二维码 */
    private void saveTfamAsset2qrcodeList(TfamAsset tfamAsset) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("card_id", "=", tfamAsset.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TfamAsset2qrcode> list_TfamAsset2qrcode = tfamAsset2qrcodeDao.listAll(pageRequest);

        List<TfamAsset2qrcode> deletes = Lists.newArrayList(); // 删除列表
        List<TfamAsset2qrcode> inserts = Lists.newArrayList(); // 添加列表
        List<TfamAsset2qrcode> updates = Lists.newArrayList(); // 更新列表
        for (TfamAsset2qrcode tfamAsset2qrcodeSaved : list_TfamAsset2qrcode) {
            boolean found = false;
            for (TfamAsset2qrcode tfamAsset2qrcode : tfamAsset.getTfamAsset2qrcodeList()) {
                if (tfamAsset2qrcodeSaved.getId().equals(tfamAsset2qrcode.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tfamAsset2qrcodeSaved);
            }
        }
        if (deletes.size() > 0) {
            tfamAsset2qrcodeService.bulkDelete(deletes);
        }
        for (TfamAsset2qrcode tfamAsset2qrcode : tfamAsset.getTfamAsset2qrcodeList()) {
            if (StringUtils.isBlank(tfamAsset2qrcode.getId())) {
                tfamAsset2qrcode.setCard(tfamAsset);

                inserts.add(tfamAsset2qrcode);
            } else {
                updates.add(tfamAsset2qrcode);
            }
        }
        if (updates.size() > 0) {
            tfamAsset2qrcodeService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tfamAsset2qrcodeService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     卡片历史表 */
    private void saveTfamAssetHistroyList(TfamAsset tfamAsset) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("card_code", "=", tfamAsset.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TfamAssetHistroy> list_TfamAssetHistroy = tfamAssetHistroyDao.listAll(pageRequest);

        List<TfamAssetHistroy> deletes = Lists.newArrayList(); // 删除列表
        List<TfamAssetHistroy> inserts = Lists.newArrayList(); // 添加列表
        List<TfamAssetHistroy> updates = Lists.newArrayList(); // 更新列表
        for (TfamAssetHistroy tfamAssetHistroySaved : list_TfamAssetHistroy) {
            boolean found = false;
            for (TfamAssetHistroy tfamAssetHistroy : tfamAsset.getTfamAssetHistroyList()) {
                if (tfamAssetHistroySaved.getId().equals(tfamAssetHistroy.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tfamAssetHistroySaved);
            }
        }
        if (deletes.size() > 0) {
            tfamAssetHistroyService.bulkDelete(deletes);
        }
        for (TfamAssetHistroy tfamAssetHistroy : tfamAsset.getTfamAssetHistroyList()) {
            if (StringUtils.isBlank(tfamAssetHistroy.getId())) {
                tfamAssetHistroy.setCardCode(tfamAsset);

                inserts.add(tfamAssetHistroy);
            } else {
                updates.add(tfamAssetHistroy);
            }
        }
        if (updates.size() > 0) {
            tfamAssetHistroyService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tfamAssetHistroyService.bulkInsert(inserts);
        }
    }

    public int exportAsset(List<Parameter> parameters, String orderby, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderby", orderby);

        List<TfamAsset> assetList = listAll(parameters, orderby);
        List<Map<String, Object>>  mapList = new ArrayList<>();
        Map<String, String> title = new LinkedHashMap<>();
        title.put("资产编号","assetCode");
        title.put("资产类别","category");
        title.put("资产名称","assetName");
        title.put("型号","cardModel");
        title.put("品牌","assetbrand");
        title.put("所在地点","place");
        title.put("供应商","producer");
        title.put("规格","spec");
        title.put("MAC地址","macAddress");
        title.put("所属部门-简称","usedept");
        title.put("所属事业部-简称","businessdept");
        title.put("负责人姓名","user");
        title.put("工号","employeeno");
        title.put("员工隶属","userAscription");
        title.put("设备隶属","assetAscription");
        title.put("数量","num");
        title.put("计量单位","unit");
        title.put("使用状况","usingstatus");
        title.put("原值","value");
        title.put("净值","netvalue");
        title.put("残值","salvage");
        title.put("累计折旧","accudep");
        title.put("购置日期","startupDate");
        title.put("启用日期","enableDate");
        title.put("预计使用月数","naturemonth");
        title.put("备注","memo");
        for(int i=0; i < assetList.size(); i++){
            TfamAsset tfamAsset = assetList.get(i);
            Map<String,Object> infoMap = new HashMap<>();
            infoMap.put("assetCode",assetList.get(i).getAssetCode());
            infoMap.put("category",assetList.get(i).getCategory().getName());
            infoMap.put("assetName",tfamAsset.getAssetName());
            infoMap.put("cardModel",tfamAsset.getCardModel());
            infoMap.put("assetbrand",tfamAsset.getAssetbrand());
            infoMap.put("place",tfamAsset.getPlace());
            infoMap.put("producer",tfamAsset.getProducer().getName());
            infoMap.put("spec",tfamAsset.getSpec());
            infoMap.put("macAddress",tfamAsset.getMacAddress());
            infoMap.put("usedept",tfamAsset.getUsedeptid().getName());
            infoMap.put("businessdept",tfamAsset.getBusinessdept());
            infoMap.put("user",tfamAsset.getUserid().getName());
            infoMap.put("employeeno",tfamAsset.getEmployeeno());
            infoMap.put("userAscription",tfamAsset.getUserAscription());
            infoMap.put("assetAscription",tfamAsset.getAssetAscription());
            infoMap.put("num",tfamAsset.getNum());
            infoMap.put("unit",tfamAsset.getUnit());
            infoMap.put("usingstatus",tfamAsset.getUsingstatus().getName());
            infoMap.put("value",tfamAsset.getValue());
            infoMap.put("netvalue",tfamAsset.getNetvalue());
            infoMap.put("salvage",tfamAsset.getSalvage());
            infoMap.put("accudep",tfamAsset.getAccudep());
            infoMap.put("startupDate",tfamAsset.getStartupDate());
            infoMap.put("enableDate",tfamAsset.getEnableDate());
            infoMap.put("naturemonth",tfamAsset.getNaturemonth());
            infoMap.put("memo",tfamAsset.getMemo());
            mapList.add(infoMap);
        }
        EasyPoiUtil.DownloadExcel(response,"固定资产卡片新增导出数据",mapList,title);
        return assetList.size();
    }

    private void checkFile(Sheet sheet) throws Exception {
        if (
                "资产编号".equals(sheet.getCell(0, 0).getContents())
                && "资产类别".equals(sheet.getCell(1, 0).getContents())
                && "资产名称".equals(sheet.getCell(2, 0).getContents())
                && "型号".equals(sheet.getCell(3, 0).getContents())
                && "品牌".equals(sheet.getCell(4, 0).getContents())
                && "所在地点".equals(sheet.getCell(5, 0).getContents())
                && "供应商".equals(sheet.getCell(6, 0).getContents())
                && "规格".equals(sheet.getCell(7, 0).getContents())
                && "MAC地址".equals(sheet.getCell(8, 0).getContents())
                && "所属部门-简称".equals(sheet.getCell(9, 0).getContents())
                && "所属事业部-简称".equals(sheet.getCell(10, 0).getContents())
                && "负责人姓名".equals(sheet.getCell(11, 0).getContents())
                && "工号".equals(sheet.getCell(12, 0).getContents())
                && "员工隶属".equals(sheet.getCell(13, 0).getContents())
                && "设备隶属".equals(sheet.getCell(14, 0).getContents())
                && "数量".equals(sheet.getCell(15, 0).getContents())
                && "计量单位".equals(sheet.getCell(16, 0).getContents())
                && "使用状况".equals(sheet.getCell(17, 0).getContents())
                && "原值".equals(sheet.getCell(18, 0).getContents())
                && "购置日期".equals(sheet.getCell(19, 0).getContents())
                && "启用日期".equals(sheet.getCell(20, 0).getContents())
                && "备注".equals(sheet.getCell(21, 0).getContents())
        )
        {}
        else
        {
            throw new Exception("模版不正确!请重新选择");
        }
    }

    @Transactional(readOnly = false)
    public List importTfamAsset(InputStream is) throws Exception{
        User loginUser = SessionUtils.getUser();
        String companyId = loginUser.getCompanyId();
        Workbook wk = Workbook.getWorkbook(is);
        Sheet st = wk.getSheet(0);  //读取第一个表格
        int rowlength= st.getRows();  //获取表格数据的行数

        this.checkFile(st); //检查导入数据的字段是否匹配
        List<TfamAsset> list = new ArrayList<>();

        HashMap<String,TfamCategory> categoryMap = new HashMap<>();
        HashMap<String,TfamSupplier> producerMap = new HashMap<>();
        HashMap<String, Department> usedeptMap = new HashMap<>();
        HashMap<String,User> employeenoMap = new HashMap<>();
        HashMap<String, TfamUsingstatus> usingstatusMap = new HashMap<>();
        ArrayList<String> result = new ArrayList<>();
        //因为第一行是字段名，所以循环从1开始
        for (int row = 1; row < rowlength; row++) {
            StringBuilder builder = new StringBuilder();
            builder.append("警告.在第"+row+"行附近,有如下错误:");
            String assetCode = ObjectUtils.toString(st.getCell(0, row).getContents().trim());
            String categoryName = ObjectUtils.toString(st.getCell(1, row).getContents().trim());
            TfamCategory category = categoryMap.get(categoryName);
            if (category== null){
                TfamCategory categoryNow = tfamCategoryDao.getCategoryByName(categoryName, companyId);
                if (categoryNow !=null){
                    categoryMap.put(categoryName,categoryNow);
                    category = categoryNow;
                }else {
                    builder.append("资产类别:"+categoryName+"不存在,请联系管理员新增;");
                }

            }

            String assetName = ObjectUtils.toString(st.getCell(2, row).getContents().trim());
            String cardModel = ObjectUtils.toString(st.getCell(3, row).getContents().trim());
            String assetbrand = ObjectUtils.toString(st.getCell(4, row).getContents().trim());
            String place = ObjectUtils.toString(st.getCell(5, row).getContents().trim());
            String producerName = ObjectUtils.toString(st.getCell(6, row).getContents().trim());
            TfamSupplier supplier = producerMap.get(producerName);
            if (supplier == null){
                TfamSupplier supplierNow = tfamSupplierDao.getSupplierByName(producerName, companyId);
                if (supplierNow!=null){
                    producerMap.put(producerName,supplierNow);
                    supplier = supplierNow;
                }else {
                    builder.append("供应商:"+producerName+"不存在,请联系管理员新增;");
                }

            }
            String spec = ObjectUtils.toString(st.getCell(7, row).getContents().trim());
            String macAddress = ObjectUtils.toString(st.getCell(8, row).getContents().trim());
            String usedeptName = ObjectUtils.toString(st.getCell(9, row).getContents().trim());
            Department department = usedeptMap.get(usedeptName);
            if (department == null){
                Department departmentNow = departmentDao.getDeptByName(usedeptName, companyId);
                if (departmentNow!=null){
                    usedeptMap.put(producerName,departmentNow);
                    department = departmentNow;
                }else {
                    builder.append("部门:"+usedeptName+"不存在,请联系管理员新增;");
                }
            }

            String businessdept = ObjectUtils.toString(st.getCell(10, row).getContents().trim());
            String userid = ObjectUtils.toString(st.getCell(6, row).getContents().trim());
            String employeeno = ObjectUtils.toString(st.getCell(12, row).getContents().trim());
            User user = employeenoMap.get("employeeno");
            if (user == null){
                User usernow = userDao.getUserByemployeeNo(employeeno,companyId);
                if (usernow !=null){
                    employeenoMap.put(employeeno,usernow);
                    user = usernow;
                }else {
                    builder.append("员工编号:"+employeeno+"不存在,请联系管理员核实;");
                }
            }

            String userAscription = ObjectUtils.toString(st.getCell(13, row).getContents().trim());
            String assetAscription = ObjectUtils.toString(st.getCell(14, row).getContents().trim());
            String num = ObjectUtils.toString(st.getCell(15, row).getContents().trim());
            String unit = ObjectUtils.toString(st.getCell(16, row).getContents().trim());
            String usingstatusName = ObjectUtils.toString(st.getCell(17, row).getContents().trim());
            TfamUsingstatus tfamUsingstatus = usingstatusMap.get(usingstatusName);
            if (tfamUsingstatus == null){
                TfamUsingstatus usingstatusNow = tfamUsingstatusDao.getStatusByName(usingstatusName, companyId);
                if (usingstatusNow != null){
                    usingstatusMap.put(usingstatusName,usingstatusNow);
                    tfamUsingstatus = usingstatusNow;
                }else {
                    builder.append("使用状态:"+usingstatusName+"不存在,请联系管理员新增;");
                }
            }

            String value = ObjectUtils.toString(st.getCell(18, row).getContents().trim());
            String startupDate = ObjectUtils.toString(st.getCell(19, row).getContents().trim());
            String enableDate = ObjectUtils.toString(st.getCell(20, row).getContents().trim());
            String memo = ObjectUtils.toString(st.getCell(21, row).getContents().trim());
            TfamAsset tfamAsset = new TfamAsset();
            tfamAsset.setAssetCode(assetCode);
            tfamAsset.setCategory(category);
            tfamAsset.setAssetName(assetName);
            tfamAsset.setCardModel(cardModel);
            tfamAsset.setAssetbrand(assetbrand);
            tfamAsset.setPlace(place);
            tfamAsset.setProducer(supplier);
            tfamAsset.setSpec(spec);
            tfamAsset.setMacAddress(macAddress);
            tfamAsset.setUsedeptid(department);
            tfamAsset.setUserid(user);
            tfamAsset.setBusinessdept(businessdept);
            tfamAsset.setEmployeeno(employeeno);
            tfamAsset.setUserAscription(userAscription);
            tfamAsset.setAssetAscription(assetAscription);
            tfamAsset.setNum(Integer.valueOf(num));
            tfamAsset.setUnit(unit);
            tfamAsset.setUsingstatus(tfamUsingstatus);
            tfamAsset.setValue(new BigDecimal(value));
            tfamAsset.setStartupDate(startupDate);
            tfamAsset.setEnableDate(enableDate);
            tfamAsset.setMemo(memo);
            tfamAsset.setWorkloanunit("1");

            Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            tfamAsset.setYear(year+"");
            tfamAsset.setMonth(month+"");
            String cardCode = this.dao.getMaxCardCode(companyId);
            Integer cardNum = Integer.valueOf(cardCode)+1;
            tfamAsset.setCardCode(String.valueOf(cardNum));
            TfamNature tfamNature = new TfamNature();
            tfamNature.setId("1");
            tfamAsset.setNature(tfamNature);
            tfamAsset.setBusinessDate(DateUtils.getDate());
            tfamAsset.setCompanyId(companyId);
            tfamAsset.setUserid(loginUser);
            tfamAsset.setUsedeptid(loginUser.getDepartment());
            list.add(tfamAsset);
            String s = builder.toString();
            boolean contains = s.contains(";");
            if(contains){
                result.add(s);
            }

        }

        if (result.size()>0){
            return result;
        }



        for (int i = 0; i < list.size(); i++) {
            TfamAsset tfamAsset = list.get(i);
            tfamAsset.preInsert();
            this.dao.insert(tfamAsset);
        }
        return new ArrayList<String>();
    }
    @Transactional(readOnly = false)
    public Boolean copy(CopyAssetReq copyAssetReq) {
        String assetId = copyAssetReq.getAssetId();
        TfamAsset tfamAsset = this.dao.get(assetId);
        String name = tfamAsset.getStatus().getName();
        if (!AuditStatusEnum.ADDPREAUDIT.getName().equals(name)){
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_100003, ErrorEnum.E_100003.getErrorMsg()));
        }

        String assetCode = tfamAsset.getAssetCode();
        for (int i=1;i<=copyAssetReq.getCopyTimes();i++) {
            String maxAssetCode = getMaxAssetCode(assetCode);
            String finalyCode = buildAssetCode(maxAssetCode);
            tfamAsset.setId(null);
            tfamAsset.setAssetCode(finalyCode);
            tfamAsset.preInsert();
            this.dao.insert(tfamAsset);
        }
        return Boolean.TRUE;
    }

    private String buildAssetCode(String maxAssetCode){
        String flag = "_";
        String numFinaly;
        StringBuilder builder = new StringBuilder();

        String[] codeArray = maxAssetCode.split("_");
        if (codeArray.length == 1){
            numFinaly = "01";
            builder.append(maxAssetCode).append(flag).append(numFinaly);
        }else {
            String codeFinalyValue = codeArray[codeArray.length - 1];
            Integer num = Integer.valueOf(codeFinalyValue);
            if(num+1<10){
                numFinaly = "0"+String.valueOf(num+1);
            }else {
                numFinaly = String.valueOf(num+1);
            }

            for (int i=0;i<codeArray.length-1;i++){
                String var = codeArray[i];
                builder.append(var).append(flag);
            }
            builder.append(numFinaly);

        }

        return builder.toString();
    }

    private String getMaxAssetCode(String assetCode){
        return this.dao.getMaxAssetCode(assetCode);
    }

    @Transactional(readOnly = false)
    public AuditAssetInfoResp audit(AuditAssetReq auditAssetReq) {
        User user = SessionUtils.getUser();
        Boolean power = managerPowerService.checkPower("switch_3", user.getId(), user.getCompanyId());
        if (!power){
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_502, "无操作权限，请联系管理员进行配置"));
        }


        List<AuditAssetResp> noNeedAuditAssets = new ArrayList<>();
        List<String> ids = auditAssetReq.getIds();
        Iterator<String> iterator = ids.iterator();
        while (iterator.hasNext()){
            String id = iterator.next();
            TfamAsset tfamAsset = super.get(id);
            TfamStatus sourceStatus = tfamAsset.getStatus();
            //卡片编号为202108009421的卡片为已审核状态，不需要审核!
            if(!checkStatus(sourceStatus,auditAssetReq.getMoveType(),tfamAsset,auditAssetReq.getScrapeReason())){
                AuditAssetResp assetResp = AuditAssetResp.builder()
                        .cardCode(tfamAsset.getCardCode())
                        .msg("卡片编号:" + tfamAsset.getCardCode() + "的卡片为"+sourceStatus.getName()+"状态,无法变更")
                        .build();
                noNeedAuditAssets.add(assetResp);
                continue;
            }
            tfamAsset.preUpdate();
            this.dao.update(tfamAsset);
        }
        AuditAssetInfoResp auditAssetInfoResp = new AuditAssetInfoResp();
        auditAssetInfoResp.buildSets(noNeedAuditAssets);

        return auditAssetInfoResp;
    }



    private boolean checkStatus(TfamStatus sourceStatus,Integer moveType,TfamAsset asset,String reason){
        TfamStatus objStatus = null;
        if (sourceStatus.getId() .equals(AuditStatusEnum.ADDPREAUDIT.getCode().toString())){
            if (moveType ==1 ){
                //新增待审核 -> 新增已审核 新增只能前进
                objStatus = statusDao.getStatusByName(AuditStatusEnum.ADDADUITED.getName());
                if (objStatus != null){
                    asset.setStatus(objStatus);
//                    //新增审核数据 入alter
//                    SaveAlterDto saveAlterDto = new SaveAlterDto();
//                    TfamAsset tfamAsset = new TfamAsset();
//                    BeanUtils.copyProperties(asset,tfamAsset);
//                    tfamAsset.setStatus(objStatus);
//                    saveAlterDto.setAsset(tfamAsset);
//                    saveAlterDto.setReason(OperatetAlterTypeEnum.ADD.getName());
//                    saveAlterDto.setOperatetType(OperatetAlterTypeEnum.ADD.getCode());
//                    tfamAlterService.save(saveAlterDto);
                }else {
                    throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_100001, ErrorEnum.E_100001.getErrorMsg()));
                }
                return true;
            }
        }
        if (sourceStatus.getId() .equals(AuditStatusEnum.ADDADUITED.getCode().toString())){
            if (moveType == 1 ){
                if (org.apache.commons.lang3.StringUtils.isBlank(reason)){
                    return false;
                }
                //新增已审核 ->报废待审核  前进
                objStatus = statusDao.getStatusByName(AuditStatusEnum.SCRAPPREAUDIT.getName());
                if (objStatus != null){
                    asset.setStatus(objStatus);
                    asset.setReduceReason(reason);
//                    //新增审核数据 入alter
//                    SaveAlterDto saveAlterDto = new SaveAlterDto();
//                    TfamAsset tfamAsset = new TfamAsset();
//                    BeanUtils.copyProperties(asset,tfamAsset);
//                    tfamAsset.setStatus(objStatus);
//                    saveAlterDto.setAsset(tfamAsset);
//                    saveAlterDto.setReason(OperatetAlterTypeEnum.DELETE.getName());
//                    saveAlterDto.setOperatetType(OperatetAlterTypeEnum.DELETE.getCode());
//                    tfamAlterService.save(saveAlterDto);
                }else {
                    throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_100001, ErrorEnum.E_100001.getErrorMsg()));
                }
                return true;
            }
            if (moveType == 2){
                //新增已审核 ->新增待审核 后退 ==新增反审核
                objStatus = statusDao.getStatusByName(AuditStatusEnum.ADDPREAUDIT.getName());
                if (objStatus != null){
                    asset.setStatus(objStatus);
//                   //新增审核数据 入alter
                    SaveAlterDto saveAlterDto = new SaveAlterDto();
                    TfamAsset tfamAsset = new TfamAsset();
                    BeanUtils.copyProperties(asset,tfamAsset);
                    tfamAsset.setStatus(objStatus);
                    saveAlterDto.setAsset(tfamAsset);
                    saveAlterDto.setReason(OperatetAlterTypeEnum.ADVERSE_ADD.getName());
                    saveAlterDto.setOperatetType(OperatetAlterTypeEnum.ADVERSE_ADD.getCode());
                    tfamAlterService.save(saveAlterDto);
                }else {
                    throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_100001, ErrorEnum.E_100001.getErrorMsg()));
                }
                return true;
            }
        }
        if (sourceStatus.getId() .equals(AuditStatusEnum.SCRAPPREAUDIT.getCode().toString())){
            if (moveType ==1 ){
                //报废待审核 -> 报废已审核  只能前进 ==报废审核
                objStatus = statusDao.getStatusByName(AuditStatusEnum.SCRAPAUDITED.getName());
                if (objStatus != null){
                    asset.setStatus(objStatus);
                    //报废审核数据 入alter
                    SaveAlterDto saveAlterDto = new SaveAlterDto();
                    TfamAsset tfamAsset = new TfamAsset();
                    BeanUtils.copyProperties(asset,tfamAsset);
                    tfamAsset.setStatus(objStatus);
                    saveAlterDto.setAsset(tfamAsset);
                    saveAlterDto.setReason(asset.getReduceReason());
                    saveAlterDto.setOperatetType(OperatetAlterTypeEnum.DELETE.getCode());
                    tfamAlterService.save(saveAlterDto);

                }else {
                    throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_100001, ErrorEnum.E_100001.getErrorMsg()));
                }
                return true;
            }

        }


        return false;
    }

    /**
     * 报废审核申请
     * @param auditAssetReq
     * @return
     */
    public List<AuditAssetResp> scrapAuditApply(AuditAssetReq auditAssetReq) {
        User user = SessionUtils.getUser();
        TfamStatus status = statusDao.getStatusByName(AuditStatusEnum.SCRAPPREAUDIT.getName());
        if (status == null){
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_100001, ErrorEnum.E_100001.getErrorMsg()));
        }
        List<AuditAssetResp> noNeedAuditAssets = new ArrayList<>();
        List<String> ids = auditAssetReq.getIds();
        Iterator<String> iterator = ids.iterator();
        while (iterator.hasNext()) {
            String id = iterator.next();
            TfamAsset tfamAsset = super.get(id);
            TfamStatus statusNow = tfamAsset.getStatus();
            if (!AuditStatusEnum.ADDADUITED.getName().equals(statusNow.getName())){
                String name = statusNow.getName();
                String msg = "卡片编号为"+tfamAsset.getCardCode()+"的卡片为"+name+"状态,无法进行报废!";
                AuditAssetResp auditAssetResp = AuditAssetResp.builder()
                        .cardCode(tfamAsset.getCardCode())
                        .msg(msg)
                        .build();
                noNeedAuditAssets.add(auditAssetResp);
            }
            tfamAsset.setStatus(status);
            tfamAsset.preUpdate();
            this.dao.update(tfamAsset);

        }
        return noNeedAuditAssets;
    }

    /**
     * 获取二维码
     * @param cardIds
     * @param request
     * @param response
     * @return
     * @throws Exception
     */

    public static Integer PDFPERIMAGES = 18;

    @Transactional(readOnly = false)
    public String printQRCode(List<String> cardIds, HttpServletRequest request, HttpServletResponse response) throws  Exception{
        String path;

        // 先取出资产卡片id，根据id取出二维码路径,如果没有二维码、则重新生成
        List<TfamAsset2qrcode> qrcodeList = qrcodeService.getQRCode(cardIds,qrcode_path);
        // 将图片合并
        List<String> realQRPath = new ArrayList<>();
        qrcodeList.stream().forEach(a->realQRPath.add(a.getQrcodePath()));
        String[] realQRPathArray = new String[qrcodeList.size()];
        realQRPath.toArray(realQRPathArray);
        int batch = 0;
        // 合并后的图片路径
        ArrayList<String> afterCombinePath = new ArrayList<>();
        // 创建存放合并后图片的文件夹
        File combineDirectory = new File(combine_path);
        if(!combineDirectory.exists()){
            combineDirectory.mkdirs();
        }
        // 如果选择多个资产，则生成多张图片。
        if (realQRPathArray.length > PDFPERIMAGES) {
            ArrayList<String> arrayList = Lists.newArrayList(realQRPathArray);
            List<List<String>> lists = ListUtils.subListByLength(arrayList, PDFPERIMAGES);
            for(List<String> list:lists){
                batch++;
                String fileName = UUID.randomUUID().toString() + "_" + batch + ".jpg";
                String afterCombine = combine_path+ File.separator + fileName;
                afterCombinePath.add(afterCombine);
                String [] toArray = new String[list.size()];
                String [] combine = list.toArray(toArray);
                /**
                 * 2.排版修改2021/9/17 BY LJ (2=>3)
                 */
                ImageUtils.doSpecialCombine(combine, afterCombine, 3,blankPath);
            }

        } else {
            String [] combine = realQRPathArray;
            String fileName = UUID.randomUUID().toString() + "_" + batch + ".jpg";
            String afterCombine = combine_path + File.separator + fileName;
            afterCombinePath.add(afterCombine);
            /**
             * 2.排版修改2021/9/17 BY LJ (2=>3)
             */
            ImageUtils.doSpecialCombine(combine, afterCombine, 3,blankPath);
        }
        //  将合并 后的图片转化为pdf
        String pdfName = UUID.randomUUID().toString()+".pdf";

        String realPath = pdf_path;
//        String realPath = pdf_path;
        String responseUrl = realPath+File.separator+pdfName;
        ImageUtils.imageToPdf(afterCombinePath,responseUrl);

        //  返回生成的pdf路径
        return pdfName;
//        return null;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public TfamAsset saveAndUpdate(SaveAndUpdateReq saveAndUpdateReq) {
        Integer operateType = saveAndUpdateReq.getOperateType();
        TfamAsset tfamAsset = new TfamAsset();
        BeanUtils.copyProperties(saveAndUpdateReq,tfamAsset);
//        TfamAsset tfamAsset1 = get(tfamAsset.getId());
//        tfamAsset.setUpdateDate(tfamAsset1.getUpdateDate());
        if (operateType == 1){
            // 新增alter
        }else if (operateType == 2){
            //插入调整记录表
            SaveAlterDto saveAlterDto = new SaveAlterDto();
            saveAlterDto.setAsset(tfamAsset);
            saveAlterDto.setReason(saveAndUpdateReq.getReason());
            saveAlterDto.setOperatetType(OperatetAlterTypeEnum.UPDATE.getCode());
            tfamAlterService.save(saveAlterDto);

        }else if (operateType == 3){

            User user = userDao.get(tfamAsset.getUserid().getId());
            Department department = departmentDao.get(tfamAsset.getUsedeptid().getId());
            tfamAsset.setUsedeptid(department);
            tfamAsset.setUserid(user);
            //插入调整记录表
            SaveAlterDto saveAlterDto = new SaveAlterDto();
            saveAlterDto.setAsset(tfamAsset);
            saveAlterDto.setReason(saveAndUpdateReq.getReason());
            saveAlterDto.setOperatetType(OperatetAlterTypeEnum.TRANSFER.getCode());
            tfamAlterService.save(saveAlterDto);

            //插入调拨记录
            SaveTransferDto saveTransferDto = new SaveTransferDto();
            saveTransferDto.setAsset(tfamAsset);
            saveTransferDto.setTransferReason(saveAndUpdateReq.getReason());
            tfamAssetTransferService.saves(saveTransferDto);
        }

        return save(tfamAsset);
    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void assetTransfer(AssetTransferReq transferReq) {
        String deptId = transferReq.getDeptId();
        Department department = new Department();
        department.setId(deptId);
        department.setName(deptId);
        String userId = transferReq.getUserId();
        User user = new User();
        user.setId(userId);
        String reason = transferReq.getReason();

        List<String> ids = transferReq.getIds();
        ids.stream().forEach(id->{
            TfamAsset tfamAsset = get(id);
            SaveAndUpdateReq saveAndUpdateReq = new SaveAndUpdateReq();
            BeanUtils.copyProperties(tfamAsset,saveAndUpdateReq);

            saveAndUpdateReq.setReason(reason);
            saveAndUpdateReq.setUsedeptid(department);
            saveAndUpdateReq.setUserid(user);
            saveAndUpdateReq.setOperateType(3);
            saveAndUpdate(saveAndUpdateReq);
        });
    }

    public LifeCricleResp lifeCircle(CopyAssetReq copyAssetReq) {
        String assetId = copyAssetReq.getAssetId();
        TfamAsset tfamAsset = get(assetId);
        String startupDate = tfamAsset.getStartupDate();
        Integer naturemonth = tfamAsset.getNaturemonth();
        String endTime = addMonths(startupDate, naturemonth);
        LifeCricleResp lifeCricleResp = new LifeCricleResp();
        lifeCricleResp.setStartTime(startupDate);
        lifeCricleResp.setEndTime(endTime);
        List<TfamAlter> tfamAlters = tfamAlterService.listAllByAsset(assetId);
        lifeCricleResp.setAlters(tfamAlters);
        return lifeCricleResp;
    }

    private static String addMonths(String dateStr,Integer months){
        String pattern="yyyy-MM-dd hh:mm:ss";
        DateFormat dateFormat= new SimpleDateFormat(pattern);
        Date date= null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        SimpleDateFormat format = new SimpleDateFormat(pattern);


        return format.format(cal.getTime());
    }

    public CountAssetResp countAsset(CountAssetReq countAssetReq) {
        String groupCondition = countAssetReq.getGroupCondition();
//        List<CountAssetDto> countAssetDtos = this.dao.countAsset(groupCondition);
        List<CountAssetDto> countAssetDtos = null;

        List<CountAssetDto> totalList =  dao.countAsset(groupCondition,0,99999);

        if(totalList.size() > 0) {
            countAssetDtos = this.dao.countAsset(groupCondition,countAssetReq.getOffset(),countAssetReq.getLimit());
        }


        Integer num =0;
        BigDecimal value =new BigDecimal(0);
        BigDecimal accudep =new BigDecimal(0);
        BigDecimal netvalue =new BigDecimal(0);

        if(countAssetDtos !=null && countAssetDtos.size()>0){
            for (int i =0;i<countAssetDtos.size();i++) {
                CountAssetDto a = countAssetDtos.get(i);
                if(GroupConditionEnum.CATEGORYID.getName().equals(groupCondition)){
                    TfamCategory category = tfamCategoryDao.get(a.getCountItemId());
                    if (category !=null){
                        a.setCountItemName(category.getFullname());
                    }
                }else if (GroupConditionEnum.ASSETACCOUNTID.getName().equals(groupCondition)){
                    TglAccounts account = tglAccountsService.getAccountById(a.getCountItemId());
                    if (account!=null){
                        a.setCountItemName(account.getVaraccountfullname());
                    }
                }else if (GroupConditionEnum.USEDEPTID.getName().equals(groupCondition)){
                    Department department = departmentDao.get(a.getCountItemId());
                    if (department!=null){
                        a.setCountItemName(department.getName());
                    }
                }else if (GroupConditionEnum.DEPMETHOD.getName().equals(groupCondition)){
                    TfamDepmethod tfamDepmethod = depmethodDao.get(a.getCountItemId());
                    if (tfamDepmethod!=null){
                        a.setCountItemName(tfamDepmethod.getName());
                    }
                }else if (GroupConditionEnum.USINGSTATUS.getName().equals(groupCondition)){
                    TfamUsingstatus tfamUsingstatus = tfamUsingstatusDao.get(a.getCountItemId());
                    if (tfamUsingstatus!=null){
                        a.setCountItemName(tfamUsingstatus.getName());
                    }
                }

                num += a.getNum();
                value = value.add(a.getValue()!=null?new BigDecimal(a.getValue()):new BigDecimal(0));
                netvalue = netvalue.add(a.getNetvalue()!=null?new BigDecimal(a.getNetvalue()):new BigDecimal(0));
                accudep = accudep.add(a.getAccudep()!=null?new BigDecimal(a.getAccudep()):new BigDecimal(0));
            }
        }
        CountAssetResp countAssetResp = CountAssetResp.builder()
                .countAssetDtos(countAssetDtos)
                .num(num)
                .value(value.toString())
                .netvalue(netvalue.toString())
                .accudep(accudep.toString())
                .offset(countAssetReq.getOffset())
                .limit(countAssetReq.getLimit())
                .total(totalList.size())
                .build();
        return countAssetResp;
    }

    public int countAssetExport(CountAssetReq copyAssetReq, HttpServletResponse response) {
        copyAssetReq.setLimit(99999);
        copyAssetReq.setOffset(0);
        CountAssetResp countAssetResp = countAsset(copyAssetReq);
        List<CountAssetDto> countAssetDtos = countAssetResp.getCountAssetDtos();
        List<Map<String, Object>>  mapList = new ArrayList<>();
        Map<String, String> title = new LinkedHashMap<>();
        title.put("汇总项目","countItemName");
        title.put("数量","num");
        title.put("原值","value");
        title.put("累计折旧","accudep");
        title.put("净值","netvalue");

        for(int i=0; i < countAssetDtos.size(); i++){
            CountAssetDto countAssetDto = countAssetDtos.get(i);
            Map<String,Object> infoMap = new HashMap<>();
            infoMap.put("countItemName",countAssetDto.getCountItemName());
            infoMap.put("num",countAssetDto.getNum());
            infoMap.put("value",countAssetDto.getValue());
            infoMap.put("accudep",countAssetDto.getAccudep());
            infoMap.put("netvalue",countAssetDto.getNetvalue());
            mapList.add(infoMap);
        }
        String date = DateUtils.getDate();
        EasyPoiUtil.DownloadExcel(response,"卡片汇总查询（"+date+"）",mapList,title);
        return countAssetDtos.size();
    }

    private void checkCountAssetFile(Sheet sheet) throws Exception {
        if (
                "资产编号".equals(sheet.getCell(0, 0).getContents())
                        && "资产类别".equals(sheet.getCell(1, 0).getContents())
                        && "资产名称".equals(sheet.getCell(2, 0).getContents())
                        && "型号".equals(sheet.getCell(3, 0).getContents())
                        && "品牌".equals(sheet.getCell(4, 0).getContents())
                        && "所在地点".equals(sheet.getCell(5, 0).getContents())
                        && "供应商".equals(sheet.getCell(6, 0).getContents())
                        && "规格".equals(sheet.getCell(7, 0).getContents())
                        && "MAC地址".equals(sheet.getCell(8, 0).getContents())
                        && "所属部门-简称".equals(sheet.getCell(9, 0).getContents())
                        && "所属事业部-简称".equals(sheet.getCell(10, 0).getContents())
                        && "负责人姓名".equals(sheet.getCell(11, 0).getContents())
                        && "工号".equals(sheet.getCell(12, 0).getContents())
                        && "员工隶属".equals(sheet.getCell(13, 0).getContents())
                        && "设备隶属".equals(sheet.getCell(14, 0).getContents())
                        && "数量".equals(sheet.getCell(15, 0).getContents())
                        && "计量单位".equals(sheet.getCell(16, 0).getContents())
                        && "使用状况".equals(sheet.getCell(17, 0).getContents())
                        && "原值".equals(sheet.getCell(18, 0).getContents())
                        && "购置日期".equals(sheet.getCell(19, 0).getContents())
                        && "启用日期".equals(sheet.getCell(20, 0).getContents())
                        && "备注".equals(sheet.getCell(21, 0).getContents())
        )
        {}
        else
        {
            throw new Exception("模版不正确!请重新选择");
        }
    }

    public CountAssetForGraphResp countAssetForGraph() {
        //部门
        List<GraphDeptDataDto> deptDataDtoList = this.dao.getAllDataByDept();
        //类别
        List<GraphCategoryDataDto> categoryDataDtoList = this.dao.getAllDataByAssetCategory();
        //新增和报废
        List<GraphAddScrapDataDto> graphAddScrapDataDtoList = this.dao.getAllScrapAssetGroupByMonth();

        List<GraphTransferDataDto> transferDataDtoList = this.dao.getAllTransferDataByMonthly();


        CountAssetForGraphResp resp = CountAssetForGraphResp.builder()
                .deptDataDtoList(deptDataDtoList)
                .categoryDataDtoList(categoryDataDtoList)
                .graphAddScrapDataDtoList(graphAddScrapDataDtoList)
                .transferDataDtoList(transferDataDtoList)
                .build();
        return resp;
    }


}
