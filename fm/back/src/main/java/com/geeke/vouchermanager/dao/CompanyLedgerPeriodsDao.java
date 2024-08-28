package com.geeke.vouchermanager.dao;

import com.geeke.common.data.PageRequest;
import com.geeke.common.persistence.CrudDao;
import com.geeke.vouchermanager.entity.CompanyLedgerPeriods;
import com.geeke.vouchermanager.entity.TglPeriodAccounts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 分户期初余额设置DAO接口
 * @author
 * @version
 */
@Mapper
public interface CompanyLedgerPeriodsDao extends CrudDao<CompanyLedgerPeriods> {

    /**
     * 根据科目、分户类型、公司查询初期余额
     * @param pageRequest
     * @return
     */
    CompanyLedgerPeriods getByAccount(PageRequest pageRequest);

    /**
     * 根据科目、分户类型、公司导出初期余额
     * @param map
     * @return
     */
    List<Map<String, Object>> exportLedgePeriod(@Param("map")Map<String, Object> map);

    /**
     * 根据实体中科目、会计期、分户就行查询
     * @param map
     * @return
     */
    CompanyLedgerPeriods getLedgePeriodByEntity(@Param("map")Map<String, Object> map);
    public int newupdate(CompanyLedgerPeriods entity);
}
