package com.geeke.vouchermanager.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.vouchermanager.entity.TglCompanyLedgerPeriods;
import com.geeke.vouchermanager.entity.TglPeriodAccounts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 科目分户发生数汇总表DAO接口
 * @author
 * @version
 */
@Mapper
public interface TglCompanyLedgerPeriodsDao extends CrudDao<TglCompanyLedgerPeriods> {
    public int newupdate(TglCompanyLedgerPeriods entity);
}
