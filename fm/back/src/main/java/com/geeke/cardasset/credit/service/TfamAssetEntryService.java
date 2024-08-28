package com.geeke.cardasset.credit.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.geeke.cardasset.credit.dao.OrgDepartmentEntryDao;
import com.geeke.cardasset.credit.dao.SysUserEntryDao;
import com.geeke.cardasset.credit.dao.TfamAlterEntryDao;
import com.geeke.cardasset.credit.dao.TfamAssetEntryDao;
import com.geeke.cardasset.credit.dao.TfamCategoryEntryDao;
import com.geeke.cardasset.credit.dao.TfamChangeWayEntryDao;
import com.geeke.cardasset.credit.dao.TfamDepmethodEntryDao;
import com.geeke.cardasset.credit.dao.TfamNatureEntryDao;
import com.geeke.cardasset.credit.dao.TfamUsingstatusEntryDao;
import com.geeke.cardasset.credit.dao.TglVoucherMainsEntryDao;
import com.geeke.cardasset.credit.entity.OrgDepartmentEntry;
import com.geeke.cardasset.credit.entity.SysUserEntry;
import com.geeke.cardasset.credit.entity.TfamAlterEntry;
import com.geeke.cardasset.credit.entity.TfamAssetEntry;
import com.geeke.cardasset.credit.entity.TfamCategoryEntry;
import com.geeke.cardasset.credit.entity.TfamChangeWayEntry;
import com.geeke.cardasset.credit.entity.TfamDepmethodEntry;
import com.geeke.cardasset.credit.entity.TfamNatureEntry;
import com.geeke.cardasset.credit.entity.TfamUsingstatusEntry;
import com.geeke.cardasset.credit.entity.TglVoucherMainsEntry;
import com.geeke.cardasset.credit.service.OrgDepartmentEntryService;
import com.geeke.cardasset.credit.service.SysUserEntryService;
import com.geeke.cardasset.credit.service.TfamAlterEntryService;
import com.geeke.cardasset.credit.service.TfamCategoryEntryService;
import com.geeke.cardasset.credit.service.TfamChangeWayEntryService;
import com.geeke.cardasset.credit.service.TfamDepmethodEntryService;
import com.geeke.cardasset.credit.service.TfamNatureEntryService;
import com.geeke.cardasset.credit.service.TfamUsingstatusEntryService;
import com.geeke.cardasset.credit.service.TglVoucherMainsEntryService;
import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.sys.entity.Action;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.utils.Reflections;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 资产入账Service
 * @author
 * @version
 */

@Service("tfamAssetEntryService")
@Transactional(readOnly = false)
public class TfamAssetEntryService extends CrudService<TfamAssetEntryDao, TfamAssetEntry> {

    @Autowired
    private OrgDepartmentEntryDao orgDepartmentEntryDao;

    @Autowired
    private OrgDepartmentEntryService orgDepartmentEntryService;

    @Autowired
    private TfamNatureEntryDao tfamNatureEntryDao;

    @Autowired
    private TfamNatureEntryService tfamNatureEntryService;

    @Autowired
    private TfamCategoryEntryDao tfamCategoryEntryDao;

    @Autowired
    private TfamCategoryEntryService tfamCategoryEntryService;

    @Autowired
    private SysUserEntryDao sysUserEntryDao;

    @Autowired
    private SysUserEntryService sysUserEntryService;

    @Autowired
    private TfamUsingstatusEntryDao tfamUsingstatusEntryDao;

    @Autowired
    private TfamUsingstatusEntryService tfamUsingstatusEntryService;

    @Autowired
    private TfamDepmethodEntryDao tfamDepmethodEntryDao;

    @Autowired
    private TfamDepmethodEntryService tfamDepmethodEntryService;

    @Autowired
    private TfamChangeWayEntryDao tfamChangeWayEntryDao;

    @Autowired
    private TfamChangeWayEntryService tfamChangeWayEntryService;

    @Autowired
    private TfamAlterEntryDao tfamAlterEntryDao;

    @Autowired
    private TfamAlterEntryService tfamAlterEntryService;

    @Autowired
    private TglVoucherMainsEntryDao tglVoucherMainsEntryDao;

    @Autowired
    private TglVoucherMainsEntryService tglVoucherMainsEntryService;

    @Override
    public TfamAssetEntry get(String id) {
        TfamAssetEntry tfamAssetEntry = super.get(id);

        List<Parameter> params = null;

        /*获取子表列表   部门入账使用*/
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetEntry.getId()));
        tfamAssetEntry.setOrgDepartmentEntryList(orgDepartmentEntryService.listAll(params, ""));

        /*获取子表列表   卡片性质表入账使用*/
        params = Lists.newArrayList();

        params.add(new Parameter("code", "=", tfamAssetEntry.getId()));
        tfamAssetEntry.setTfamNatureEntryList(tfamNatureEntryService.listAll(params, ""));

        /*获取子表列表   资产类别表入账使用*/
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetEntry.getId()));
        tfamAssetEntry.setTfamCategoryEntryList(tfamCategoryEntryService.listAll(params, ""));

        /*获取子表列表   用户入账使用*/
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetEntry.getId()));
        tfamAssetEntry.setSysUserEntryList(sysUserEntryService.listAll(params, ""));

        /*获取子表列表   使用状况表。对应卡片中usingstatus入账使用*/
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetEntry.getId()));
        tfamAssetEntry.setTfamUsingstatusEntryList(tfamUsingstatusEntryService.listAll(params, ""));

        /*获取子表列表   折旧方法表入账使用*/
        params = Lists.newArrayList();

        params.add(new Parameter("code", "=", tfamAssetEntry.getId()));
        tfamAssetEntry.setTfamDepmethodEntryList(tfamDepmethodEntryService.listAll(params, ""));

        /*获取子表列表   变动方式表，卡片新增-新增方式 注：与change_type 并无关联入账使用*/
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetEntry.getId()));
        tfamAssetEntry.setTfamChangeWayEntryList(tfamChangeWayEntryService.listAll(params, ""));

        /*获取子表列表   变动明细表入账使用*/
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetEntry.getId()));
        tfamAssetEntry.setTfamAlterEntryList(tfamAlterEntryService.listAll(params, ""));

        /*获取子表列表   凭证主表入账使用*/
        params = Lists.newArrayList();

        params.add(new Parameter("UQVOUCHERID", "=", tfamAssetEntry.getId()));
        tfamAssetEntry.setTglVoucherMainsEntryList(tglVoucherMainsEntryService.listAll(params, ""));

        return tfamAssetEntry;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public TfamAssetEntry save(TfamAssetEntry tfamAssetEntry) {
        TfamAssetEntry tfamAssetEntryTemp = super.save(tfamAssetEntry);

        /* 保存子表数据     部门入账使用 */
        saveOrgDepartmentEntryList(tfamAssetEntryTemp);

        /* 保存子表数据     卡片性质表入账使用 */
        saveTfamNatureEntryList(tfamAssetEntryTemp);

        /* 保存子表数据     资产类别表入账使用 */
        saveTfamCategoryEntryList(tfamAssetEntryTemp);

        /* 保存子表数据     用户入账使用 */
        saveSysUserEntryList(tfamAssetEntryTemp);

        /* 保存子表数据     使用状况表。对应卡片中usingstatus入账使用 */
        saveTfamUsingstatusEntryList(tfamAssetEntryTemp);

        /* 保存子表数据     折旧方法表入账使用 */
        saveTfamDepmethodEntryList(tfamAssetEntryTemp);

        /* 保存子表数据     变动方式表，卡片新增-新增方式 注：与change_type 并无关联入账使用 */
        saveTfamChangeWayEntryList(tfamAssetEntryTemp);

        /* 保存子表数据     变动明细表入账使用 */
        saveTfamAlterEntryList(tfamAssetEntryTemp);

        /* 保存子表数据     凭证主表入账使用 */
        saveTglVoucherMainsEntryList(tfamAssetEntryTemp);

        return tfamAssetEntryTemp;
    }

    /**
     * 删除
     * @param tfamAssetEntry
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int delete(TfamAssetEntry tfamAssetEntry) {
        List<Parameter> params = null;
        PageRequest pageRequest;

        /* 处理子表     部门入账使用 */
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetEntry.getId()));
        pageRequest = new PageRequest(params);
        tfamAssetEntry.setOrgDepartmentEntryList(orgDepartmentEntryDao.listAll(pageRequest));

        if (tfamAssetEntry.getOrgDepartmentEntryList() != null && tfamAssetEntry.getOrgDepartmentEntryList().size() > 0) {
            orgDepartmentEntryService.bulkDelete(tfamAssetEntry.getOrgDepartmentEntryList());
        }

        /* 处理子表     卡片性质表入账使用 */
        params = Lists.newArrayList();

        params.add(new Parameter("code", "=", tfamAssetEntry.getId()));
        pageRequest = new PageRequest(params);
        tfamAssetEntry.setTfamNatureEntryList(tfamNatureEntryDao.listAll(pageRequest));

        if (tfamAssetEntry.getTfamNatureEntryList() != null && tfamAssetEntry.getTfamNatureEntryList().size() > 0) {
            tfamNatureEntryService.bulkDelete(tfamAssetEntry.getTfamNatureEntryList());
        }

        /* 处理子表     资产类别表入账使用 */
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetEntry.getId()));
        pageRequest = new PageRequest(params);
        tfamAssetEntry.setTfamCategoryEntryList(tfamCategoryEntryDao.listAll(pageRequest));

        if (tfamAssetEntry.getTfamCategoryEntryList() != null && tfamAssetEntry.getTfamCategoryEntryList().size() > 0) {
            tfamCategoryEntryService.bulkDelete(tfamAssetEntry.getTfamCategoryEntryList());
        }

        /* 处理子表     用户入账使用 */
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetEntry.getId()));
        pageRequest = new PageRequest(params);
        tfamAssetEntry.setSysUserEntryList(sysUserEntryDao.listAll(pageRequest));

        if (tfamAssetEntry.getSysUserEntryList() != null && tfamAssetEntry.getSysUserEntryList().size() > 0) {
            sysUserEntryService.bulkDelete(tfamAssetEntry.getSysUserEntryList());
        }

        /* 处理子表     使用状况表。对应卡片中usingstatus入账使用 */
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetEntry.getId()));
        pageRequest = new PageRequest(params);
        tfamAssetEntry.setTfamUsingstatusEntryList(tfamUsingstatusEntryDao.listAll(pageRequest));

        if (tfamAssetEntry.getTfamUsingstatusEntryList() != null && tfamAssetEntry.getTfamUsingstatusEntryList().size() > 0) {
            tfamUsingstatusEntryService.bulkDelete(tfamAssetEntry.getTfamUsingstatusEntryList());
        }

        /* 处理子表     折旧方法表入账使用 */
        params = Lists.newArrayList();

        params.add(new Parameter("code", "=", tfamAssetEntry.getId()));
        pageRequest = new PageRequest(params);
        tfamAssetEntry.setTfamDepmethodEntryList(tfamDepmethodEntryDao.listAll(pageRequest));

        if (tfamAssetEntry.getTfamDepmethodEntryList() != null && tfamAssetEntry.getTfamDepmethodEntryList().size() > 0) {
            tfamDepmethodEntryService.bulkDelete(tfamAssetEntry.getTfamDepmethodEntryList());
        }

        /* 处理子表     变动方式表，卡片新增-新增方式 注：与change_type 并无关联入账使用 */
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetEntry.getId()));
        pageRequest = new PageRequest(params);
        tfamAssetEntry.setTfamChangeWayEntryList(tfamChangeWayEntryDao.listAll(pageRequest));

        if (tfamAssetEntry.getTfamChangeWayEntryList() != null && tfamAssetEntry.getTfamChangeWayEntryList().size() > 0) {
            tfamChangeWayEntryService.bulkDelete(tfamAssetEntry.getTfamChangeWayEntryList());
        }

        /* 处理子表     变动明细表入账使用 */
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetEntry.getId()));
        pageRequest = new PageRequest(params);
        tfamAssetEntry.setTfamAlterEntryList(tfamAlterEntryDao.listAll(pageRequest));

        if (tfamAssetEntry.getTfamAlterEntryList() != null && tfamAssetEntry.getTfamAlterEntryList().size() > 0) {
            tfamAlterEntryService.bulkDelete(tfamAssetEntry.getTfamAlterEntryList());
        }

        /* 处理子表     凭证主表入账使用 */
        params = Lists.newArrayList();

        params.add(new Parameter("UQVOUCHERID", "=", tfamAssetEntry.getId()));
        pageRequest = new PageRequest(params);
        tfamAssetEntry.setTglVoucherMainsEntryList(tglVoucherMainsEntryDao.listAll(pageRequest));

        if (tfamAssetEntry.getTglVoucherMainsEntryList() != null && tfamAssetEntry.getTglVoucherMainsEntryList().size() > 0) {
            tglVoucherMainsEntryService.bulkDelete(tfamAssetEntry.getTglVoucherMainsEntryList());
        }

        int rows = super.delete(tfamAssetEntry);
        return rows;
    }

    /**
     * 批量删除
     * @param tfamAssetEntryList
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int bulkDelete(List<TfamAssetEntry> tfamAssetEntryList) {
        for (TfamAssetEntry tfamAssetEntry : tfamAssetEntryList) {
            delete(tfamAssetEntry);
        }

        return tfamAssetEntryList.size();
    }

    /**
     * 生成操作日志
     * @param actionTypeId  操作类型Id
     * @param entity        操作的实体对象
     * @return
     */
    @Override
    protected Action createAction(String actionTypeId, TfamAssetEntry entity) {
        Action action = super.createAction(actionTypeId, entity);
        if (action == null) {
            return null;
        }
        // 删除时记录把保存的数据保存到回收站
        if (ActionConstants.ACTION_DELETED.equals(actionTypeId)) {
            for (OrgDepartmentEntry child : entity.getOrgDepartmentEntryList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TfamNatureEntry child : entity.getTfamNatureEntryList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TfamCategoryEntry child : entity.getTfamCategoryEntryList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (SysUserEntry child : entity.getSysUserEntryList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TfamUsingstatusEntry child : entity.getTfamUsingstatusEntryList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TfamDepmethodEntry child : entity.getTfamDepmethodEntryList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TfamChangeWayEntry child : entity.getTfamChangeWayEntryList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TfamAlterEntry child : entity.getTfamAlterEntryList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }
        }
        return action;
    }

    /* 保存子表数据     部门入账使用 */
    private void saveOrgDepartmentEntryList(TfamAssetEntry tfamAssetEntry) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetEntry.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<OrgDepartmentEntry> list_OrgDepartmentEntry = orgDepartmentEntryDao.listAll(pageRequest);

        List<OrgDepartmentEntry> deletes = Lists.newArrayList(); // 删除列表
        List<OrgDepartmentEntry> inserts = Lists.newArrayList(); // 添加列表
        List<OrgDepartmentEntry> updates = Lists.newArrayList(); // 更新列表
        for (OrgDepartmentEntry orgDepartmentEntrySaved : list_OrgDepartmentEntry) {
            boolean found = false;
            for (OrgDepartmentEntry orgDepartmentEntry : tfamAssetEntry.getOrgDepartmentEntryList()) {
                if (orgDepartmentEntrySaved.getId().equals(orgDepartmentEntry.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(orgDepartmentEntrySaved);
            }
        }
        if (deletes.size() > 0) {
            orgDepartmentEntryService.bulkDelete(deletes);
        }
        for (OrgDepartmentEntry orgDepartmentEntry : tfamAssetEntry.getOrgDepartmentEntryList()) {
            if (StringUtils.isBlank(orgDepartmentEntry.getId())) {
                orgDepartmentEntry.setId(tfamAssetEntry.getId()); // 子表id与父表id一致

                inserts.add(orgDepartmentEntry);
            } else {
                updates.add(orgDepartmentEntry);
            }
        }
        if (updates.size() > 0) {
            orgDepartmentEntryService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            orgDepartmentEntryService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     卡片性质表入账使用 */
    private void saveTfamNatureEntryList(TfamAssetEntry tfamAssetEntry) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("code", "=", tfamAssetEntry.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TfamNatureEntry> list_TfamNatureEntry = tfamNatureEntryDao.listAll(pageRequest);

        List<TfamNatureEntry> deletes = Lists.newArrayList(); // 删除列表
        List<TfamNatureEntry> inserts = Lists.newArrayList(); // 添加列表
        List<TfamNatureEntry> updates = Lists.newArrayList(); // 更新列表
        for (TfamNatureEntry tfamNatureEntrySaved : list_TfamNatureEntry) {
            boolean found = false;
            for (TfamNatureEntry tfamNatureEntry : tfamAssetEntry.getTfamNatureEntryList()) {
                if (tfamNatureEntrySaved.getId().equals(tfamNatureEntry.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tfamNatureEntrySaved);
            }
        }
        if (deletes.size() > 0) {
            tfamNatureEntryService.bulkDelete(deletes);
        }
        for (TfamNatureEntry tfamNatureEntry : tfamAssetEntry.getTfamNatureEntryList()) {
            if (StringUtils.isBlank(tfamNatureEntry.getId())) {
                tfamNatureEntry.setCode(tfamAssetEntry.getCardCode());

                inserts.add(tfamNatureEntry);
            } else {
                updates.add(tfamNatureEntry);
            }
        }
        if (updates.size() > 0) {
            tfamNatureEntryService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tfamNatureEntryService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     资产类别表入账使用 */
    private void saveTfamCategoryEntryList(TfamAssetEntry tfamAssetEntry) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetEntry.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TfamCategoryEntry> list_TfamCategoryEntry = tfamCategoryEntryDao.listAll(pageRequest);

        List<TfamCategoryEntry> deletes = Lists.newArrayList(); // 删除列表
        List<TfamCategoryEntry> inserts = Lists.newArrayList(); // 添加列表
        List<TfamCategoryEntry> updates = Lists.newArrayList(); // 更新列表
        for (TfamCategoryEntry tfamCategoryEntrySaved : list_TfamCategoryEntry) {
            boolean found = false;
            for (TfamCategoryEntry tfamCategoryEntry : tfamAssetEntry.getTfamCategoryEntryList()) {
                if (tfamCategoryEntrySaved.getId().equals(tfamCategoryEntry.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tfamCategoryEntrySaved);
            }
        }
        if (deletes.size() > 0) {
            tfamCategoryEntryService.bulkDelete(deletes);
        }
        for (TfamCategoryEntry tfamCategoryEntry : tfamAssetEntry.getTfamCategoryEntryList()) {
            if (StringUtils.isBlank(tfamCategoryEntry.getId())) {
                tfamCategoryEntry.setId(tfamAssetEntry.getId()); // 子表id与父表id一致

                inserts.add(tfamCategoryEntry);
            } else {
                updates.add(tfamCategoryEntry);
            }
        }
        if (updates.size() > 0) {
            tfamCategoryEntryService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tfamCategoryEntryService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     用户入账使用 */
    private void saveSysUserEntryList(TfamAssetEntry tfamAssetEntry) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetEntry.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<SysUserEntry> list_SysUserEntry = sysUserEntryDao.listAll(pageRequest);

        List<SysUserEntry> deletes = Lists.newArrayList(); // 删除列表
        List<SysUserEntry> inserts = Lists.newArrayList(); // 添加列表
        List<SysUserEntry> updates = Lists.newArrayList(); // 更新列表
        for (SysUserEntry sysUserEntrySaved : list_SysUserEntry) {
            boolean found = false;
            for (SysUserEntry sysUserEntry : tfamAssetEntry.getSysUserEntryList()) {
                if (sysUserEntrySaved.getId().equals(sysUserEntry.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(sysUserEntrySaved);
            }
        }
        if (deletes.size() > 0) {
            sysUserEntryService.bulkDelete(deletes);
        }
        for (SysUserEntry sysUserEntry : tfamAssetEntry.getSysUserEntryList()) {
            if (StringUtils.isBlank(sysUserEntry.getId())) {
                sysUserEntry.setId(tfamAssetEntry.getId()); // 子表id与父表id一致

                inserts.add(sysUserEntry);
            } else {
                updates.add(sysUserEntry);
            }
        }
        if (updates.size() > 0) {
            sysUserEntryService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            sysUserEntryService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     使用状况表。对应卡片中usingstatus入账使用 */
    private void saveTfamUsingstatusEntryList(TfamAssetEntry tfamAssetEntry) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetEntry.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TfamUsingstatusEntry> list_TfamUsingstatusEntry = tfamUsingstatusEntryDao.listAll(pageRequest);

        List<TfamUsingstatusEntry> deletes = Lists.newArrayList(); // 删除列表
        List<TfamUsingstatusEntry> inserts = Lists.newArrayList(); // 添加列表
        List<TfamUsingstatusEntry> updates = Lists.newArrayList(); // 更新列表
        for (TfamUsingstatusEntry tfamUsingstatusEntrySaved : list_TfamUsingstatusEntry) {
            boolean found = false;
            for (TfamUsingstatusEntry tfamUsingstatusEntry : tfamAssetEntry.getTfamUsingstatusEntryList()) {
                if (tfamUsingstatusEntrySaved.getId().equals(tfamUsingstatusEntry.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tfamUsingstatusEntrySaved);
            }
        }
        if (deletes.size() > 0) {
            tfamUsingstatusEntryService.bulkDelete(deletes);
        }
        for (TfamUsingstatusEntry tfamUsingstatusEntry : tfamAssetEntry.getTfamUsingstatusEntryList()) {
            if (StringUtils.isBlank(tfamUsingstatusEntry.getId())) {
                tfamUsingstatusEntry.setId(tfamAssetEntry.getId()); // 子表id与父表id一致

                inserts.add(tfamUsingstatusEntry);
            } else {
                updates.add(tfamUsingstatusEntry);
            }
        }
        if (updates.size() > 0) {
            tfamUsingstatusEntryService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tfamUsingstatusEntryService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     折旧方法表入账使用 */
    private void saveTfamDepmethodEntryList(TfamAssetEntry tfamAssetEntry) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("code", "=", tfamAssetEntry.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TfamDepmethodEntry> list_TfamDepmethodEntry = tfamDepmethodEntryDao.listAll(pageRequest);

        List<TfamDepmethodEntry> deletes = Lists.newArrayList(); // 删除列表
        List<TfamDepmethodEntry> inserts = Lists.newArrayList(); // 添加列表
        List<TfamDepmethodEntry> updates = Lists.newArrayList(); // 更新列表
        for (TfamDepmethodEntry tfamDepmethodEntrySaved : list_TfamDepmethodEntry) {
            boolean found = false;
            for (TfamDepmethodEntry tfamDepmethodEntry : tfamAssetEntry.getTfamDepmethodEntryList()) {
                if (tfamDepmethodEntrySaved.getId().equals(tfamDepmethodEntry.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tfamDepmethodEntrySaved);
            }
        }
        if (deletes.size() > 0) {
            tfamDepmethodEntryService.bulkDelete(deletes);
        }
        for (TfamDepmethodEntry tfamDepmethodEntry : tfamAssetEntry.getTfamDepmethodEntryList()) {
            if (StringUtils.isBlank(tfamDepmethodEntry.getId())) {
                tfamDepmethodEntry.setCode(tfamAssetEntry.getCardCode());

                inserts.add(tfamDepmethodEntry);
            } else {
                updates.add(tfamDepmethodEntry);
            }
        }
        if (updates.size() > 0) {
            tfamDepmethodEntryService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tfamDepmethodEntryService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     变动方式表，卡片新增-新增方式 注：与change_type 并无关联入账使用 */
    private void saveTfamChangeWayEntryList(TfamAssetEntry tfamAssetEntry) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetEntry.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TfamChangeWayEntry> list_TfamChangeWayEntry = tfamChangeWayEntryDao.listAll(pageRequest);

        List<TfamChangeWayEntry> deletes = Lists.newArrayList(); // 删除列表
        List<TfamChangeWayEntry> inserts = Lists.newArrayList(); // 添加列表
        List<TfamChangeWayEntry> updates = Lists.newArrayList(); // 更新列表
        for (TfamChangeWayEntry tfamChangeWayEntrySaved : list_TfamChangeWayEntry) {
            boolean found = false;
            for (TfamChangeWayEntry tfamChangeWayEntry : tfamAssetEntry.getTfamChangeWayEntryList()) {
                if (tfamChangeWayEntrySaved.getId().equals(tfamChangeWayEntry.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tfamChangeWayEntrySaved);
            }
        }
        if (deletes.size() > 0) {
            tfamChangeWayEntryService.bulkDelete(deletes);
        }
        for (TfamChangeWayEntry tfamChangeWayEntry : tfamAssetEntry.getTfamChangeWayEntryList()) {
            if (StringUtils.isBlank(tfamChangeWayEntry.getId())) {
                tfamChangeWayEntry.setId(tfamAssetEntry.getId()); // 子表id与父表id一致

                inserts.add(tfamChangeWayEntry);
            } else {
                updates.add(tfamChangeWayEntry);
            }
        }
        if (updates.size() > 0) {
            tfamChangeWayEntryService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tfamChangeWayEntryService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     变动明细表入账使用 */
    private void saveTfamAlterEntryList(TfamAssetEntry tfamAssetEntry) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetEntry.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TfamAlterEntry> list_TfamAlterEntry = tfamAlterEntryDao.listAll(pageRequest);

        List<TfamAlterEntry> deletes = Lists.newArrayList(); // 删除列表
        List<TfamAlterEntry> inserts = Lists.newArrayList(); // 添加列表
        List<TfamAlterEntry> updates = Lists.newArrayList(); // 更新列表
        for (TfamAlterEntry tfamAlterEntrySaved : list_TfamAlterEntry) {
            boolean found = false;
            for (TfamAlterEntry tfamAlterEntry : tfamAssetEntry.getTfamAlterEntryList()) {
                if (tfamAlterEntrySaved.getId().equals(tfamAlterEntry.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tfamAlterEntrySaved);
            }
        }
        if (deletes.size() > 0) {
            tfamAlterEntryService.bulkDelete(deletes);
        }
        for (TfamAlterEntry tfamAlterEntry : tfamAssetEntry.getTfamAlterEntryList()) {
            if (StringUtils.isBlank(tfamAlterEntry.getId())) {
                tfamAlterEntry.setId(tfamAssetEntry.getId()); // 子表id与父表id一致

                inserts.add(tfamAlterEntry);
            } else {
                updates.add(tfamAlterEntry);
            }
        }
        if (updates.size() > 0) {
            tfamAlterEntryService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tfamAlterEntryService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     凭证主表入账使用 */
    private void saveTglVoucherMainsEntryList(TfamAssetEntry tfamAssetEntry) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("UQVOUCHERID", "=", tfamAssetEntry.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TglVoucherMainsEntry> list_TglVoucherMainsEntry = tglVoucherMainsEntryDao.listAll(pageRequest);

        List<TglVoucherMainsEntry> deletes = Lists.newArrayList(); // 删除列表
        List<TglVoucherMainsEntry> inserts = Lists.newArrayList(); // 添加列表
        List<TglVoucherMainsEntry> updates = Lists.newArrayList(); // 更新列表
        for (TglVoucherMainsEntry tglVoucherMainsEntrySaved : list_TglVoucherMainsEntry) {
            boolean found = false;
            for (TglVoucherMainsEntry tglVoucherMainsEntry : tfamAssetEntry.getTglVoucherMainsEntryList()) {
                if (tglVoucherMainsEntrySaved.getId().equals(tglVoucherMainsEntry.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tglVoucherMainsEntrySaved);
            }
        }
        if (deletes.size() > 0) {
            tglVoucherMainsEntryService.bulkDelete(deletes);
        }
        for (TglVoucherMainsEntry tglVoucherMainsEntry : tfamAssetEntry.getTglVoucherMainsEntryList()) {
            if (StringUtils.isBlank(tglVoucherMainsEntry.getId())) {
                tglVoucherMainsEntry.setUqvoucherid(tfamAssetEntry.getId());

                inserts.add(tglVoucherMainsEntry);
            } else {
                updates.add(tglVoucherMainsEntry);
            }
        }
        if (updates.size() > 0) {
            tglVoucherMainsEntryService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tglVoucherMainsEntryService.bulkInsert(inserts);
        }
    }
    public void linkVoucher(JSONArray alterid, String voucherid)
    {
        for (Object s : alterid) {
            this.dao.linkVoucher(s.toString(),voucherid);
        }

    }

    public void cancelVoucher(String ids)
    {
        JSONObject jsonObject = JSONObject.parseObject(ids);

            this.dao.cancelVoucher(jsonObject.getString("id"));

    }
    public void linkVoucher1(JSONArray alterid, String voucherid)
    {
        for (Object s : alterid) {
            this.dao.linkVoucher1(s.toString(),voucherid);
        }

    }

    public void cancelVoucher1(String ids)
    {
        JSONObject jsonObject = JSONObject.parseObject(ids);

            this.dao.cancelVoucher1(jsonObject.getString("id"));

    }
}
