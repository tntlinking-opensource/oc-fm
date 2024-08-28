package com.geeke.ureport.bean;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.bstek.ureport.provider.image.ImageProvider;
import com.geeke.ureport.service.GeneralLedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Component
public class GeneralLedger implements ImageProvider {
    @Autowired
    private  GeneralLedgerService service;

    /**
     * 分类总账
     * @param dsName
     * @param dataSetName
     * @param parameters
     * @return
     */
    public List<Map<String,Object>> loadGeneralLedGer(String dsName, String dataSetName, Map<String, Object> parameters) {
        //获取表名字
        String tableName = service.getGeneralLedgerTableName(dsName, dataSetName, parameters);
        List<Map<String, Object>> maps = service.loadGeneralLedGer(tableName);
        return maps;
    }


    /**
     * 日记账
     * @param dsName
     * @param dataSetName
     * @param parameters
     * @return
     */
    public List<Map<String,Object>> loadJournal(String dsName, String dataSetName, Map<String, Object> parameters) {
        //获取表名字
        String tableName = service.getJournalTableName(dsName, dataSetName, parameters);
        List<Map<String, Object>> maps = service.loadJournal(tableName);
        return maps;
    }


    /**
     * 明细账
     * @param dsName
     * @param dataSetName
     * @param parameters
     * @return
     */
    public List<Map<String,Object>> loadDetailedLedger(String dsName, String dataSetName, Map<String, Object> parameters) {
        //获取表名字
        String tableName = service.getDetailedLedgerName(dsName, dataSetName, parameters);
        parameters.put("tableName",tableName);
        List<Map<String, Object>> maps = service.loadDetailedLedger(parameters);
        return maps;
    }

    /**
     * 试算平衡表
     * @param dsName
     * @param dataSetName
     * @param parameters
     * @return
     */
    public List<Map<String,Object>> trialBalance(String dsName, String dataSetName, Map<String, Object> parameters) {
        //获取表名字
        String tableName = service.getTrialBalance(dsName, dataSetName, parameters);
        parameters.put("tableName",tableName);
        List<Map<String, Object>> data = service.loadTrialBalance(parameters);
        if (data.size() !=0) {
            Map<String, Object> rs = data.get(data.size() - 1);
            Object ncjfye=  rs.get("ncjfye"); //年初借方余额
            Object ncdfye=  rs.get("ncdfye"); //年初贷方余额
            Object sqjfye=  rs.get("sqjfye"); //上期借方余额
            Object sqdfye=  rs.get("sqdfye"); //上期贷方余额
            Object bqjf=  rs.get("bqjf"); //本期借方发生
            Object bqdf=  rs.get("bqdf"); //本期贷方发生
            Object bqjfye=  rs.get("bqjfye"); //本期借方余额
            Object bqdfye=  rs.get("bqdfye"); //本期贷方余额
            Object qmjfye=  rs.get("qmjfye"); //期末借方余额
            Object qmdfye=  rs.get("qmdfye"); //期末贷方余额
            Object bqljjfye=  rs.get("bqljjfye"); //本期止借方累计
            Object bqljdfye=  rs.get("bqljdfye"); //本期止贷方累计
            if (!ncjfye.equals(ncdfye)||!sqjfye.equals(sqdfye)||!bqjf.equals(bqdf)||!bqjfye.equals(bqdfye)
                    ||!qmjfye.equals(qmdfye)||!bqljjfye.equals(bqljdfye)){
                parameters.put("isBalance","false");
            }else{
                parameters.put("isBalance","true");
            }
        }
        return data;
    }

    /**
     *科目余额表
     * @param dsName
     * @param dataSetName
     * @param parameters
     * @return
     */
    public List<Map<String,Object>> accountBalance(String dsName, String dataSetName, Map<String, Object> parameters) {
        //获取表名字
        String tableName = service.getAccountBalanceName(dsName, dataSetName, parameters);
        parameters.put("tableName",tableName);
        List<Map<String, Object>> maps = service.loadAccountBalance(parameters);
        return maps;
    }


    /**
     * 分户科目总账
     * @param dsName
     * @param dataSetName
     * @param parameters
     * @return
     */
    public List<Map<String,Object>> ledgerGlBook(String dsName, String dataSetName, Map<String, Object> parameters) {
        //获取表名字
        String tableName = service.getLedgerGlBookName(dsName, dataSetName, parameters);
        parameters.put("tableName",tableName);
        List<Map<String, Object>> maps = service.loadLedgerGlBook(parameters);
        return maps;
    }
    @Override
    public InputStream getImage(String s) {
        File file = new File(s);
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);
            String name = file.getName();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return stream;
    }

    @Override
    public boolean support(String s) {
        return true;
    }

}
