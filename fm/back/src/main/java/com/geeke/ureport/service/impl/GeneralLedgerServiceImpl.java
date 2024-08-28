package com.geeke.ureport.service.impl;

import com.bstek.ureport.provider.image.ImageProvider;
import com.geeke.admin.entity.User;
import com.geeke.common.service.BaseService;
import com.geeke.ureport.mapper.GeneralLedgerMapper;
import com.geeke.ureport.service.GeneralLedgerService;
import com.geeke.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GeneralLedgerServiceImpl extends BaseService implements GeneralLedgerService {

    @Resource
    GeneralLedgerMapper mapper;


    @Override
    public String getGeneralLedgerTableName(String dsName, String dataSetName, Map<String, Object> parameters) {
//        String uqaccountsetid =SessionUtils.getUser().getCompanyId();
        parameters.put("isLastLevel",1);
        parameters.put("tableName","");
        parameters.put("errmsg","");
        String startPeriod = (String) parameters.get("startPeriod");
        String endPeriod = (String) parameters.get("endPeriod");
        String hkontLevel = (String) parameters.get("hkontLevel");
        String intstatus = (String) parameters.get("intstatus");
        String uqcompanyid = (String) parameters.get("uqcompanyid");
        parameters.put("startPeriod",Integer.parseInt(startPeriod));
        parameters.put("endPeriod",Integer.parseInt(endPeriod));
        parameters.put("hkontLevel",Integer.parseInt(hkontLevel));
        parameters.put("intstatus",Integer.parseInt(intstatus));
        //parameters.put("uqcompanyid",companyId);
        parameters.put("uqaccountsetid",uqcompanyid);
        mapper.getGeneralLedgerTableName(parameters);
        return parameters.get("tableName").toString();
    }

    @Override
    public String getJournalTableName(String dsName, String dataSetName, Map<String, Object> parameters) {
//        String uqaccountsetid = SessionUtils.getUser().getCompanyId();
        parameters.put("isLastLevel",1);
        parameters.put("tableName","");
        parameters.put("errmsg","");
        String startPeriod = (String) parameters.get("startPeriod");
        String endPeriod = (String) parameters.get("endPeriod");
        String hkontLevel = (String) parameters.get("hkontLevel");
        String intstatus = (String) parameters.get("intstatus");
        String isseveral = (String) parameters.get("isseveral");
        String uqcompanyid = (String) parameters.get("uqcompanyid");
        String isalldisplayed = (String) parameters.get("isalldisplayed");
        parameters.put("startPeriod",Integer.parseInt(startPeriod));
        parameters.put("endPeriod",Integer.parseInt(endPeriod));
        parameters.put("hkontLevel",Integer.parseInt(hkontLevel));
        parameters.put("intstatus",Integer.parseInt(intstatus));
        parameters.put("uqaccountsetid",uqcompanyid);
        parameters.put("isseveral","true".equals(isseveral)==true?1:0);
        parameters.put("isalldisplayed","true".equals(isalldisplayed)==true?1:0);
        mapper.getJournalTableName(parameters);
        return parameters.get("tableName").toString();
    }

    @Override
    public String getDetailedLedgerName(String dsName, String dataSetName, Map<String, Object> parameters) {
//        String uqaccountsetid = SessionUtils.getUser().getCompanyId();
        parameters.put("isLastLevel",1);
        parameters.put("tableName",""); //出参
        parameters.put("errmsg","");//出参
        String startPeriod = (String) parameters.get("startPeriod");
        String endPeriod = (String) parameters.get("endPeriod");
        String hkontLevel = (String) parameters.get("hkontLevel");
        String intstatus = (String) parameters.get("intstatus");
        String isseveral = (String) parameters.get("isseveral");
        String uqcompanyid = (String) parameters.get("uqcompanyid");
        String isalldisplayed = (String) parameters.get("isalldisplayed");
        parameters.put("startPeriod",Integer.parseInt(startPeriod));
        parameters.put("endPeriod",Integer.parseInt(endPeriod));
        parameters.put("hkontLevel",Integer.parseInt(hkontLevel));
        parameters.put("intstatus",Integer.parseInt(intstatus));
        parameters.put("uqaccountsetid",uqcompanyid);
        parameters.put("isseveral","true".equals(isseveral)==true?1:0);
        parameters.put("isalldisplayed","true".equals(isalldisplayed)==true?1:0);
        String companyStr = (String) parameters.get("uqcompanyid");
        String[] companyArr = companyStr.split(",");
        String tableName = "";
        for (int i = 0; i < companyArr.length; i++) {
            parameters.put("companyId",companyArr[i]);
            if (i==0){
                tableName= "tgl_mxz_tmp";
                mapper.addNewTemporary(tableName,"");
                mapper.getDetailedLedgerName(parameters);
                String tableName1 = (String) parameters.get("tableName");
                mapper.addtemporary(tableName,tableName1,companyArr[i]);
            }else {
                parameters.put("tableName","");
                mapper.getDetailedLedgerName(parameters);
                String tableName1 = (String) parameters.get("tableName");
                mapper.addtemporary(tableName,tableName1,companyArr[i]);
            }

        }

        return tableName;
    }

    @Override
    public String getTrialBalance(String dsName, String dataSetName, Map<String, Object> parameters) {
        String uqaccountsetid =(String) parameters.get("companyId");
        String period = (String) parameters.get("period");
        String intstatus = (String) parameters.get("intstatus");
        String companyId = (String) parameters.get("companyId");
        parameters.put("tableName",""); //出参
        parameters.put("errmsg","");//出参
        //入参
        parameters.put("period",Integer.parseInt(period));
        parameters.put("intstatus",Integer.parseInt(intstatus));
        parameters.put("uqaccountsetid",companyId);
        parameters.put("companyId",companyId);
        mapper.getTrialBalance(parameters);
        String tableName = (String) parameters.get("tableName");
        return tableName;
    }

    @Override
    public String getAccountBalanceName(String dsName, String dataSetName, Map<String, Object> parameters) {

        parameters.put("isLastLevel",1);
        parameters.put("tableName",""); //出参
        parameters.put("errmsg","");//出参
        String startPeriod = (String) parameters.get("startPeriod");
        String endPeriod = (String) parameters.get("endPeriod");
        String hkontLevel = (String) parameters.get("hkontLevel");
        String intstatus = (String) parameters.get("intstatus");
        String isseveral = (String) parameters.get("isseveral");
        parameters.put("startPeriod",Integer.parseInt(startPeriod));
        parameters.put("endPeriod",Integer.parseInt(endPeriod));
        parameters.put("hkontLevel",Integer.parseInt(hkontLevel));
        parameters.put("intstatus",Integer.parseInt(intstatus));

        parameters.put("isseveral","true".equals(isseveral)==true?1:0);
        String companyStr = (String) parameters.get("uqcompanyid");
        String[] companyArr = companyStr.split(",");
        String tableName = "";
        for (int i = 0; i < companyArr.length; i++) {
            parameters.put("companyId",companyArr[i]);
            parameters.put("uqaccountsetid",companyArr[i]);
            if (i==0){
                mapper.getAccountBalance(parameters);
                tableName = (String) parameters.get("tableName");
            }else {
                parameters.put("tableName","");
                mapper.getAccountBalance(parameters);
                String temporary = (String) parameters.get("tableName");
                mapper.addAccountBalance(tableName,temporary,companyArr[i]);
            }

        }

        return tableName;
    }

    @Override
    public String getLedgerGlBookName(String dsName, String dataSetName, Map<String, Object> parameters) {
//        String uqaccountsetid = SessionUtils.getUser().getCompanyId();
        parameters.put("isLastLevel",1);
        parameters.put("tableName","");
        parameters.put("errmsg","");
        parameters.put("ledgetypename1","["+parameters.get("ledgetypename")+"]"+parameters.get("ledgetypecode"));
        String startPeriod = (String) parameters.get("startPeriod");
        String endPeriod = (String) parameters.get("endPeriod");
        String hkontLevel = (String) parameters.get("hkontLevel");
        String intstatus = (String) parameters.get("intstatus");
        String uqcompanyid = (String) parameters.get("uqcompanyid");
        parameters.put("startPeriod",Integer.parseInt(startPeriod));
        parameters.put("endPeriod",Integer.parseInt(endPeriod));
        parameters.put("hkontLevel",Integer.parseInt(hkontLevel));
        parameters.put("intstatus",Integer.parseInt(intstatus));
        parameters.put("uqaccountsetid",uqcompanyid);
        parameters.put("uqcompanyid",uqcompanyid);
        mapper.getLedgerGlBookName(parameters);
        return parameters.get("tableName").toString();
    }

    @Override
    public List<Map<String, Object>> loadGeneralLedGer(String tableName) {
        return mapper.loadGeneralLedGer(tableName);
    }

    @Override
    public List<Map<String, Object>> loadJournal(String tableName) {
        return mapper.loadJournal(tableName);
    }

    @Override
    public List<Map<String, Object>> loadDetailedLedger(Map<String,Object> parameters) {

        return mapper.loadDetailedLedger(parameters);
    }

    @Override
    public List<Map<String, Object>> loadTrialBalance(Map<String, Object> parameters) {
        return mapper.loadTrialBalance(parameters);
    }

    @Override
    public List<Map<String, Object>> loadAccountBalance(Map<String, Object> parameters) {
        return mapper.loadAccountBalance(parameters);
    }

    @Override
    public List<Map<String, Object>> loadLedgerGlBook(Map<String, Object> parameters) {
        return mapper.loadLedgerGlBook(parameters);
    }

    public String getUqaccountsetid(){
        return SessionUtils.getUser().getCompanyId();
    }

}
