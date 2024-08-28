package com.geeke.ureport.service;

import java.util.List;
import java.util.Map;

public interface GeneralLedgerService {

    String getGeneralLedgerTableName(String dsName, String dataSetName, Map<String, Object> parameters);
    String getJournalTableName(String dsName, String dataSetName, Map<String, Object> parameters);
    String getDetailedLedgerName(String dsName, String dataSetName, Map<String, Object> parameters);
    String getTrialBalance(String dsName, String dataSetName, Map<String, Object> parameters);
    String getAccountBalanceName(String dsName, String dataSetName, Map<String, Object> parameters);
    String getLedgerGlBookName(String dsName, String dataSetName, Map<String, Object> parameters);

    List<Map<String,Object>> loadGeneralLedGer(String tableName);
    List<Map<String,Object>> loadJournal(String tableName);
    List<Map<String,Object>> loadDetailedLedger(Map<String,Object> parameters);
    List<Map<String, Object>> loadTrialBalance(Map<String, Object> parameters);
    List<Map<String, Object>> loadAccountBalance(Map<String, Object> parameters);
    List<Map<String, Object>> loadLedgerGlBook(Map<String, Object> parameters);


}
