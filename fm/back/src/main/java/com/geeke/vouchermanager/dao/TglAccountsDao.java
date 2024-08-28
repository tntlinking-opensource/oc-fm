package com.geeke.vouchermanager.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.vouchermanager.entity.GroupAccountsEntity;
import com.geeke.vouchermanager.entity.TglAccounts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 会计科目管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface TglAccountsDao extends CrudDao<TglAccounts> {
    public Long editAccounts(GroupAccountsEntity account,String tenantid,String code);
    public Long editAccount(TglAccounts account);
    public Long updateUqaccountid(String s,String tenantid,String uqaccountid);
    public Long updateAccountGroupByGroupId(String uqaccountsetid,
                                            String varaccountcode,
                                            String varaccountname);
    public Long updateInsertIntislastlevel(String parentid,int flag);
    public Long updateAccountFullName(TglAccounts account);
    public Long updateUqper(TglAccounts account);
    public String selectUqaccountsetid(String tenantid);
    public List<TglAccounts> getParid(String code,String tenantid);
    public List<TglAccounts> getShowName(String code,String tenantid);
    public List<TglAccounts> getBytenantid(String tenantid);
    public List<TglAccounts> getAccountById(String uqaccountid);
    public List<TglAccounts> queryChildAccount(String uqaccountid,String tenantid);
    public List<TglAccounts> getAccountCountByName(TglAccounts account);
    public List<TglAccounts> getByUqaccountid(String parentId);
    public int addTenantAccounts(GroupAccountsEntity account,String tenantid);
    public int add(TglAccounts tglaccounts);
    public TglAccounts findAccountById(String parentid,String tenantid);
    public int IsParentExistVoucher(String tenantid,String parentId);
    public int deleteGroupAccounts(String tenantid);
    public int deleteAccountAndLedgeType(String uqaccountid);
    public Long getAccountCountByCode(String uqaccountsetid,String varaccountcode);
    public void addAccountAndLedgeType(String uqaccountid,String uqledgetypeid);
    public List<String> getAccountIdList(String uqaccountid);
    public void updateIntflag(String intflag,String varaccountfullcode ,String uqaccountsetid,String uqaccountid);

    /**
     * 根据租户、会计期年月查询金额和科目编码（利润表）
     * @param map
     * @return
     */
    List<Map<String, Object>> getReportDesignLr(@Param("map")Map<String, Object> map);

    /**
     * 根据租户、会计期年月查询金额和科目编码（资产负债表）
     * @param map
     * @return
     */
    List<Map<String, Object>> getReportDesignZcfz(@Param("map")Map<String, Object> map);

    /**
     * 根据租户、会计期年月查询金额和科目编码（现金流量表）
     * @param map
     * @return
     */
    List<Map<String, Object>> getReportDesignXjll(@Param("map")Map<String, Object> map);

    /**
     * 科目级别拉框
     * @return
     */
    List<Map<String, Object>> getSubjectlevelList();
}
