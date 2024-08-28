package com.geeke.assetting.service;

import com.alibaba.fastjson.JSONObject;
import com.geeke.assetting.dao.AssettingOrgDepartmentDao;
import com.geeke.assetting.dao.AssettingSysUserDao;
import com.geeke.assetting.dao.TfamAssetManagerDao;
import com.geeke.assetting.entity.AssettingOrgDepartment;
import com.geeke.assetting.entity.AssettingSysUser;
import com.geeke.assetting.entity.TfamAssetManager;
import com.geeke.assetting.entity.TfamAssetManagerPower;
import com.geeke.assetting.service.AssettingOrgDepartmentService;
import com.geeke.assetting.service.AssettingSysUserService;
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
import java.util.Set;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 卡片管理Service
 * @author
 * @version
 */

@Service("tfamAssetManagerService")
@Transactional(readOnly = false)
public class TfamAssetManagerService extends CrudService<TfamAssetManagerDao, TfamAssetManager> {

    @Autowired
    private AssettingSysUserDao assettingSysUserDao;

    @Autowired
    private AssettingSysUserService assettingSysUserService;

    @Autowired
    private AssettingOrgDepartmentDao assettingOrgDepartmentDao;

    @Autowired
    private AssettingOrgDepartmentService assettingOrgDepartmentService;
    @Autowired
    private TfamAssetManagerPowerService tfamAssetManagerPowerService;

    @Override
    public TfamAssetManager get(String id) {
        TfamAssetManager tfamAssetManager = super.get(id);

        List<Parameter> params = null;

        /*获取子表列表   用户*/
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetManager.getId()));
        tfamAssetManager.setAssettingSysUserList(assettingSysUserService.listAll(params, ""));

        /*获取子表列表   部门*/
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetManager.getId()));
        tfamAssetManager.setAssettingOrgDepartmentList(assettingOrgDepartmentService.listAll(params, ""));

        return tfamAssetManager;
    }
    public List<Map<String,Object>> listManagerMenu(JSONObject entity) {

        return this.dao.listManagerMenu(entity.getString("companyId"),entity.getString("userid"));
    }
    public void addAssetManagerMenu(JSONObject cdtMap) {
        String userid = cdtMap.getString("userid");
        List<Parameter> params = null;

        /*获取子表列表   用户*/
        params = Lists.newArrayList();
        params.add(new Parameter("company_id", "=",cdtMap.getString("companyId")));
        params.add(new Parameter("userid", "=",cdtMap.getString("userid")));
        List<TfamAssetManagerPower> tfamAssetManagerPowers = tfamAssetManagerPowerService.listAll(params, "");
        if (tfamAssetManagerPowers.size()==0){
            Set<String> strings = cdtMap.keySet();
            for (String string : strings) {
                if (string.contains("switch_")) {
                    TfamAssetManagerPower tfamAssetManagerPower = new TfamAssetManagerPower();
                    tfamAssetManagerPower.setOpen(Integer.valueOf(cdtMap.getString(string)));
                    tfamAssetManagerPower.setSwitchName(string);
                    tfamAssetManagerPower.setCompanyId(cdtMap.getString("companyId"));
                    tfamAssetManagerPower.setUserid(cdtMap.getString("userid"));
                    tfamAssetManagerPowerService.save(tfamAssetManagerPower);
                }
            }
        }else {
            for (TfamAssetManagerPower tfamAssetManagerPower : tfamAssetManagerPowers) {
                String switchName = tfamAssetManagerPower.getSwitchName();
                String string1 = cdtMap.getString(switchName);
                tfamAssetManagerPower.setOpen(Integer.valueOf(string1));
                tfamAssetManagerPowerService.save(tfamAssetManagerPower);
            }
        }

    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public TfamAssetManager save(TfamAssetManager tfamAssetManager) {
        TfamAssetManager tfamAssetManagerTemp = super.save(tfamAssetManager);

        /* 保存子表数据     用户 */
        saveAssettingSysUserList(tfamAssetManagerTemp);

        /* 保存子表数据     部门 */
        saveAssettingOrgDepartmentList(tfamAssetManagerTemp);

        return tfamAssetManagerTemp;
    }

    /**
     * 删除
     * @param tfamAssetManager
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int delete(TfamAssetManager tfamAssetManager) {
        List<Parameter> params = null;
        PageRequest pageRequest;

        /* 处理子表     用户 */
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetManager.getId()));
        pageRequest = new PageRequest(params);
        tfamAssetManager.setAssettingSysUserList(assettingSysUserDao.listAll(pageRequest));

        if (tfamAssetManager.getAssettingSysUserList() != null && tfamAssetManager.getAssettingSysUserList().size() > 0) {
            assettingSysUserService.bulkDelete(tfamAssetManager.getAssettingSysUserList());
        }

        /* 处理子表     部门 */
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetManager.getId()));
        pageRequest = new PageRequest(params);
        tfamAssetManager.setAssettingOrgDepartmentList(assettingOrgDepartmentDao.listAll(pageRequest));

        if (
            tfamAssetManager.getAssettingOrgDepartmentList() != null &&
            tfamAssetManager.getAssettingOrgDepartmentList().size() > 0
        ) {
            assettingOrgDepartmentService.bulkDelete(tfamAssetManager.getAssettingOrgDepartmentList());
        }

        int rows = super.delete(tfamAssetManager);
        return rows;
    }

    /**
     * 批量删除
     * @param tfamAssetManagerList
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int bulkDelete(List<TfamAssetManager> tfamAssetManagerList) {
        for (TfamAssetManager tfamAssetManager : tfamAssetManagerList) {
            delete(tfamAssetManager);
        }

        return tfamAssetManagerList.size();
    }

    /**
     * 生成操作日志
     * @param actionTypeId  操作类型Id
     * @param entity        操作的实体对象
     * @return
     */
    @Override
    protected Action createAction(String actionTypeId, TfamAssetManager entity) {
        Action action = super.createAction(actionTypeId, entity);
        if (action == null) {
            return null;
        }
        // 删除时记录把保存的数据保存到回收站
        if (ActionConstants.ACTION_DELETED.equals(actionTypeId)) {
            for (AssettingSysUser child : entity.getAssettingSysUserList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (AssettingOrgDepartment child : entity.getAssettingOrgDepartmentList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }
        }
        return action;
    }

    /* 保存子表数据     用户 */
    private void saveAssettingSysUserList(TfamAssetManager tfamAssetManager) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetManager.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<AssettingSysUser> list_AssettingSysUser = assettingSysUserDao.listAll(pageRequest);

        List<AssettingSysUser> deletes = Lists.newArrayList(); // 删除列表
        List<AssettingSysUser> inserts = Lists.newArrayList(); // 添加列表
        List<AssettingSysUser> updates = Lists.newArrayList(); // 更新列表
        for (AssettingSysUser assettingSysUserSaved : list_AssettingSysUser) {
            boolean found = false;
            for (AssettingSysUser assettingSysUser : tfamAssetManager.getAssettingSysUserList()) {
                if (assettingSysUserSaved.getId().equals(assettingSysUser.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(assettingSysUserSaved);
            }
        }
        if (deletes.size() > 0) {
            assettingSysUserService.bulkDelete(deletes);
        }
        for (AssettingSysUser assettingSysUser : tfamAssetManager.getAssettingSysUserList()) {
            if (StringUtils.isBlank(assettingSysUser.getId())) {
                assettingSysUser.setId(tfamAssetManager.getId()); // 子表id与父表id一致

                inserts.add(assettingSysUser);
            } else {
                updates.add(assettingSysUser);
            }
        }
        if (updates.size() > 0) {
            assettingSysUserService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            assettingSysUserService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     部门 */
    private void saveAssettingOrgDepartmentList(TfamAssetManager tfamAssetManager) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetManager.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<AssettingOrgDepartment> list_AssettingOrgDepartment = assettingOrgDepartmentDao.listAll(pageRequest);

        List<AssettingOrgDepartment> deletes = Lists.newArrayList(); // 删除列表
        List<AssettingOrgDepartment> inserts = Lists.newArrayList(); // 添加列表
        List<AssettingOrgDepartment> updates = Lists.newArrayList(); // 更新列表
        for (AssettingOrgDepartment assettingOrgDepartmentSaved : list_AssettingOrgDepartment) {
            boolean found = false;
            for (AssettingOrgDepartment assettingOrgDepartment : tfamAssetManager.getAssettingOrgDepartmentList()) {
                if (assettingOrgDepartmentSaved.getId().equals(assettingOrgDepartment.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(assettingOrgDepartmentSaved);
            }
        }
        if (deletes.size() > 0) {
            assettingOrgDepartmentService.bulkDelete(deletes);
        }
        for (AssettingOrgDepartment assettingOrgDepartment : tfamAssetManager.getAssettingOrgDepartmentList()) {
            if (StringUtils.isBlank(assettingOrgDepartment.getId())) {
                assettingOrgDepartment.setId(tfamAssetManager.getId()); // 子表id与父表id一致

                inserts.add(assettingOrgDepartment);
            } else {
                updates.add(assettingOrgDepartment);
            }
        }
        if (updates.size() > 0) {
            assettingOrgDepartmentService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            assettingOrgDepartmentService.bulkInsert(inserts);
        }
    }
}
