package com.geeke.tgl.ledgerReport.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 分户科目明细账DAO接口
 * @author
 * @version
 */
@Mapper
public interface LedgerReportDao {

    /**
     * 调用存储过程，创建临时表（分户科目明细账）
     * @param map
     */
    void createLedgerDetail(@Param("map") Map<String, Object> map);

    /**
     * 调用存储过程，创建临时表（分户科目余额表）
     * @param map
     */
    void createLedgerBook(@Param("map") Map<String, Object> map);

    /**
     * 调用存储过程，创建临时表（科目分户总账）
     * @param map
     */
    void createLedgerGeneral(@Param("map") Map<String, Object> map);

    /**
     * 调用存储过程，创建临时表（科目分户明细账）
     * @param map
     */
    void createAccountDetail(@Param("map") Map<String, Object> map);

    /**
     * 调用存储过程，创建临时表（科目分户余额表）
     * @param map
     */
    void createAccountBook(@Param("map") Map<String, Object> map);

    /**
     * 获取分户科目明细账报表数据
     * @param tableName
     * @return
     */
    List<Map<String, Object>> getLedgerDetailData(@Param("tableName")String tableName);

    /**
     * 获取分户科目余额表数据
     * @param tableName
     * @return
     */
    List<Map<String, Object>> getLedgerBookData(@Param("tableName")String tableName, @Param("isseveral")String isseveral, @Param("companyId")String companyId);

    /**
     * 获取科目分户总账报表数据
     * @param tableName
     * @return
     */
    List<Map<String, Object>> getLedgerGeneralData(@Param("tableName")String tableName);

    /**
     * 获取科目分户明细账报表数据
     * @param tableName
     * @return
     */
    List<Map<String, Object>> getAccountDetailData(@Param("tableName")String tableName);

    /**
     * 获取科目分户余额表数据
     * @param tableName
     * @return
     */
    List<Map<String, Object>> getAccountBookData(@Param("tableName")String tableName, @Param("isseveral")String isseveral);

}
