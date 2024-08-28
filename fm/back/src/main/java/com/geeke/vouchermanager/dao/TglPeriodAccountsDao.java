package com.geeke.vouchermanager.dao;

import com.geeke.common.data.PageRequest;
import com.geeke.common.persistence.CrudDao;
import com.geeke.vouchermanager.entity.TglLedger;
import com.geeke.vouchermanager.entity.TglPeriodAccounts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 科目期初余额DAO接口
 * @author
 * @version
 */
@Mapper
public interface TglPeriodAccountsDao extends CrudDao<TglPeriodAccounts> {
    int isHasSeveral(String code,String uqcompanyid);
    int isHasSameData(String uqaccountid,String uqcompanyid);
    public int count1(String tenantId,String accauntid,int limit,int offset);
    public int count2(String uqcompanyid,String accauntid);
    public List<TglPeriodAccounts> listPage1(String  tenantId,String accauntid,int limit,int offset);
    public List<TglPeriodAccounts> listAll1(String  tenantId);
    public TglPeriodAccounts get1(String id  ,String tenantId);
    public int newupdate(TglPeriodAccounts entity);
    public int newSubtractUpdate(TglPeriodAccounts entity);
}
