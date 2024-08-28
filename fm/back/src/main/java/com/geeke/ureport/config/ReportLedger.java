package com.geeke.ureport.config;

import com.geeke.tgl.ledgerReport.dao.LedgerReportDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ReportLedger {
    
    @Autowired
    private LedgerReportDao ledgerReportDao;

    /**
     * 方法必须包含三个参数：String，String，Map(monthYear：年月,status：状态)
     * 获取分户科目明细账报表数据
     * @return 集合类型 ，包含字段：money, code
     */
    public List<Map<String, Object>> getLedgerDetailData(String dsName, String datasetName, Map<String, Object> parameters) {
        String tableName = parameters.get("tableName").toString();
        if (StringUtils.isBlank(tableName)) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> list = ledgerReportDao.getLedgerDetailData(tableName);
        return list;
    }

    /**
     * 方法必须包含三个参数：String，String，Map(monthYear：年月,status：状态)
     * 获取分户科目余额表数据
     * @return 集合类型 ，包含字段：money, code
     */
    public List<Map<String, Object>> getLedgerBookData(String dsName, String datasetName, Map<String, Object> parameters) {
        String tableName = parameters.get("tableName").toString();
        String isseveral = parameters.get("isseveral").toString();
        String companyId = parameters.get("companyId").toString();
        if (StringUtils.isBlank(tableName)) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> list = ledgerReportDao.getLedgerBookData(tableName, isseveral,companyId);
        return list;
    }

    /**
     * 方法必须包含三个参数：String，String，Map(monthYear：年月,status：状态)
     * 获取科目分户总账报表数据
     * @return 集合类型 ，包含字段：money, code
     */
    public List<Map<String, Object>> getLedgerGeneralData(String dsName, String datasetName, Map<String, Object> parameters) {
        String tableName = parameters.get("tableName").toString();
        if (StringUtils.isBlank(tableName)) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> list = ledgerReportDao.getLedgerGeneralData(tableName);
        return list;
    }

    /**
     * 方法必须包含三个参数：String，String，Map(monthYear：年月,status：状态)
     * 获取科目分户明细账报表数据
     * @return 集合类型 ，包含字段：money, code
     */
    public List<Map<String, Object>> getAccountDetailData(String dsName, String datasetName, Map<String, Object> parameters) {
        String tableName = parameters.get("tableName").toString();
        if (StringUtils.isBlank(tableName)) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> list = ledgerReportDao.getAccountDetailData(tableName);
        return list;
    }

    /**
     * 方法必须包含三个参数：String，String，Map(monthYear：年月,status：状态)
     * 获取科目分户余额表数据
     * @return 集合类型 ，包含字段：money, code
     */
    public List<Map<String, Object>> getAccountBookData(String dsName, String datasetName, Map<String, Object> parameters) {
        String tableName = parameters.get("tableName").toString();
        String isseveral = parameters.get("isseveral").toString();
        if (StringUtils.isBlank(tableName)) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> list = ledgerReportDao.getAccountBookData(tableName, isseveral);
        return list;
    }
}
