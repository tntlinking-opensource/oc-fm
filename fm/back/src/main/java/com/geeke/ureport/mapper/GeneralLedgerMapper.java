package com.geeke.ureport.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface GeneralLedgerMapper {
     void getGeneralLedgerTableName(@Param("parameters")Map<String, Object> parameters);
     void getJournalTableName(@Param("parameters")Map<String, Object> parameters);
     void getDetailedLedgerName(@Param("parameters")Map<String, Object> parameters);
     void getTrialBalance(@Param("parameters")Map<String, Object> parameters);
     void getAccountBalance(@Param("parameters")Map<String, Object> parameters);
     void getLedgerGlBookName(@Param("parameters")Map<String, Object> parameters);


     List<Map<String, Object>> loadGeneralLedGer(String tableName);
     List<Map<String, Object>> loadJournal(String tableName);
     List<Map<String, Object>> loadDetailedLedger(@Param("parameters")Map<String,Object> parameters);
     List<Map<String, Object>> loadTrialBalance(@Param("parameters")Map<String, Object> parameters);
     List<Map<String, Object>> loadAccountBalance(@Param("parameters")Map<String, Object> parameters);
     List<Map<String, Object>> loadLedgerGlBook(@Param("parameters")Map<String, Object> parameters);

     void addNewTemporary(String tableName, String companyId);
     void addtemporary(String tableName, String temporary, String companyId);
     void addAccountBalance(String tableName, String temporary, String companyId);



}
