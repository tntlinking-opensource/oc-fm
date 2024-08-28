package com.geeke.vouchermanager.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.vouchermanager.entity.TglPeriodAccountsNotjz;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 非结转科目发生数汇总表DAO接口
 * @author
 * @version
 */
@Mapper
public interface TglPeriodAccountsNotjzDao extends CrudDao<TglPeriodAccountsNotjz> {
    public int newupdate(TglPeriodAccountsNotjz entity);
}
