package com.geeke.vouchermanager.dao;

import com.geeke.vouchermanager.entity.GroupAccountsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Mapper
public interface HandlerGroupTenantDao {
    public Long getAccountCountByCode(String uqaccountsetid, String accountCode);
    public List<String> getAccountCountByName(GroupAccountsEntity account);
    public List<String> getGroupAccountIdList(String uqaccountid);
    public List<GroupAccountsEntity> getAccountById(String uqaccountid);
    public List<GroupAccountsEntity> getAccountCount(String groupid,String code);
    public List<GroupAccountsEntity> queryAll(GroupAccountsEntity account);
    public List<GroupAccountsEntity> getShowName(String code, String groupid);
    public List<GroupAccountsEntity> queryGroupChildAccount(String uqparentid, String groupid);
    public void updateIntislastlevel(String uqaccountid, int flag);
    public Long updateGroupIntislastlevel(String uqparentid, int flag);
    public Long updateUqper(GroupAccountsEntity account);
    public Long updateAccountGroupByGroupId(String uqaccountsetid,String code,String varaccountname);
    public void addAccounts(GroupAccountsEntity account);
    public GroupAccountsEntity queryById(String uqaccountid);
    public GroupAccountsEntity queryAllByLimit(GroupAccountsEntity account);
    public GroupAccountsEntity getParentId(String code,String groupid);
    public GroupAccountsEntity getParentCode(String parentId,String groupid);
    public Long count(GroupAccountsEntity account);
    public String insert(GroupAccountsEntity account);
    public Long update(GroupAccountsEntity account);
    public Long deleteAccountGroupByAccountId(String uqaccountid);
    public Long updateIsfinal(String parAccountid);
    public Long insertBatch(GroupAccountsEntity account);
    public Long insertOrUpdateBatch(GroupAccountsEntity account);
    public Long insertIntoAccountGroup(@Param("map") Map<String, Object> entity );
    public Long deleteById(GroupAccountsEntity account);
    public Long delete(String accountid);
    public Long deleteAccounts(String tenantid);
    public int selectIsExist(String tenantid,String accountid);
    public int selectIsExistByCode(String code,String tenantid);
    public int IsExistVoucher(String code,String tenantid);
    public int findAccountCountByCode(String uqaccountsetid,String code);
    public int findAccountCountByName(GroupAccountsEntity account);
    public int getVoucher(String tenantid);
//    public List<GroupAccountsEntity> getGroupAccountById(GroupAccountsEntity account);
    public Long count(String uqaccountsetid,String varaccountcode,String uqaccountid);
    public List<GroupAccountsEntity> getGroupAccountById(String account);
    public List<GroupAccountsEntity> getGroupAccountByName(String varaccountname,String uqparentid,String uqaccountsetid);
}
