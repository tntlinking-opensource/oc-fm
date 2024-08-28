package com.geeke.vouchermanager.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.org.entity.Company;
import com.geeke.sys.entity.DictItem;
import com.geeke.sys.utils.SessionUtils;
import com.geeke.tgl.flow.entity.Flowitems;
import com.geeke.tgl.global.entity.GlobalPeriods;
import com.geeke.utils.EasyPoiUtil;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.vouchermanager.dao.CompanyLedgerPeriodsDao;
import com.geeke.vouchermanager.dao.TglAccountsDao;
import com.geeke.vouchermanager.dao.TglLedgerDao;
import com.geeke.vouchermanager.dao.TglLedgetypeDao;
import com.geeke.vouchermanager.entity.CompanyLedgerPeriods;
import com.geeke.vouchermanager.entity.TglAccounts;
import com.geeke.vouchermanager.entity.TglLedger;
import com.geeke.vouchermanager.entity.TglLedgetype;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

/**
 * 分户期初余额设置Service
 * @author
 * @version
 */

@Service("companyLedgerPeriodsService")
@Transactional(readOnly = true)
public class CompanyLedgerPeriodsService extends CrudService<CompanyLedgerPeriodsDao, CompanyLedgerPeriods> {

    @Autowired
    private TglLedgerDao tglLedgerDao;
    @Autowired
    private TglAccountsDao accountsDao;
    @Autowired
    private TglLedgetypeDao ledgetypeDao;

    /**
     * 根据科目、分户类型、公司查询初期余额
     * @param parameters
     * @return
     */
    public CompanyLedgerPeriods getByAccount(List<Parameter> parameters) {
        PageRequest pageRequest = new PageRequest(parameters, "");
        return dao.getByAccount(pageRequest);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int importCompanyLedgerPeriods(InputStream is) throws Exception {
        Workbook wk = Workbook.getWorkbook(is);
        Sheet st = wk.getSheet(0);  //读取第一个表格
        Company company = JSON.parseObject(SessionUtils.getUserJson().getJSONObject("company").toJSONString(), Company.class);
        int rowlength= st.getRows();  //获取表格数据的行数

        this.checkFile(st); //检查导入数据的字段是否匹配
        List<CompanyLedgerPeriods> list = new ArrayList<>();
        //因为第一行是字段名，所以循环从1开始
        for (int row = 1; row < rowlength; row++) {
            String varaccountcode = ObjectUtils.toString(st.getCell(0, row).getContents().trim());
            String varledgetypename = ObjectUtils.toString(st.getCell(1, row).getContents().trim());
            String varledgecode = ObjectUtils.toString(st.getCell(2, row).getContents().trim());
            String mnydebitperiod = ObjectUtils.toString(st.getCell(3, row).getContents().trim());
            String mnycreditperiod = ObjectUtils.toString(st.getCell(4, row).getContents().trim());

            CompanyLedgerPeriods entity = new CompanyLedgerPeriods();
            entity.setUqcompanyid(company); // 公司
            TglAccounts accounts = new TglAccounts(); // 科目
            accounts.setVaraccountcode(varaccountcode);
            entity.setUqaccountid(accounts);
            entity.setVarledgetypename(varledgetypename);
            GlobalPeriods globalPeriods = new GlobalPeriods(); // 会计期
            globalPeriods.setUqglobalperiodid("00000000-0000-0000-0000-000000000000");
            entity.setUqglobalperiodid(globalPeriods);
            TglLedger ledger = new TglLedger(); // 分户
            ledger.setVarledgecode(varledgecode);
            entity.setUqledgeid(ledger);
            entity.setMnydebitperiod(StringUtils.isNotBlank(mnydebitperiod) ? new BigDecimal(mnydebitperiod) : new BigDecimal(0));
            entity.setMnydebitperiodall(StringUtils.isNotBlank(mnydebitperiod) ? new BigDecimal(mnydebitperiod) : new BigDecimal(0));
            entity.setMnycreditperiod(StringUtils.isNotBlank(mnycreditperiod) ? new BigDecimal(mnycreditperiod) : new BigDecimal(0));
            entity.setMnycreditperiodall(StringUtils.isNotBlank(mnycreditperiod) ? new BigDecimal(mnycreditperiod) : new BigDecimal(0));
            list.add(entity);
        }
        //检查数据是否符合规定
        List<Map<String, Object>> idMaps = this.checkImportData(list);
        //如果文件的数据都正确，则保存数据
        List<CompanyLedgerPeriods> inserts = Lists.newArrayList(); // 添加列表
        List<CompanyLedgerPeriods> updates = Lists.newArrayList(); // 更新列表
        for (int i = 0; i < list.size(); i++) {
            // 根据现有实体信息查询，判断数据库是否已存在记录
            Map<String, Object> search = new HashMap<>();
            search.put("uqaccountid", idMaps.get(i).get("uqaccountid").toString());
            search.put("uqcompanyid", company.getId());
            search.put("uqglobalperiodid", "00000000-0000-0000-0000-000000000000");
            search.put("uqledgeid", idMaps.get(i).get("uqledgeid").toString());
            CompanyLedgerPeriods ledgePeriod = dao.getLedgePeriodByEntity(search);
            if (null != ledgePeriod && StringUtils.isNotBlank(ledgePeriod.getId())) {
                //存在记录 则执行更新
                ledgePeriod.setMnydebitperiodall(list.get(i).getMnydebitperiodall());
                ledgePeriod.setMnydebitperiod(list.get(i).getMnydebitperiodall());
                ledgePeriod.setMnycreditperiodall(list.get(i).getMnycreditperiodall());
                ledgePeriod.setMnycreditperiod(list.get(i).getMnycreditperiodall());
                updates.add(ledgePeriod);
            } else {
                //不存在 执行插入
                CompanyLedgerPeriods newEntity = list.get(i);
                newEntity.getUqaccountid().setUqaccountid(idMaps.get(i).get("uqaccountid").toString());
                newEntity.getUqledgeid().setUqledgeid(idMaps.get(i).get("uqledgeid").toString());
                newEntity.preInsert();
                inserts.add(newEntity);
            }
            // 金额比较暂不处理
//            // 获取当前分户 -- 科目的期初余额
//            List<Object> accountPeriod = this.dao.getAccountPeriod(newEntity);
//            // 获取当前科目下的所有分户余额
//            List<EntityMap> money = this.dao.getLedgePeriodSum(newEntity);
//            // 进行比较
//            if (money.size() != 0) {
//                String jieFang = money.get(0).getString("MNYDEBITPERIOD");// 借方
//                String daiFang = money.get(0).getString("MNYCREDITPERIOD");// 贷方
//                BigDecimal jieFang1 = new BigDecimal(jieFang);
//                BigDecimal daiFang1 = new BigDecimal(daiFang);
//                Object[] objs;
//                try {
//                    objs = (Object[])accountPeriod.get(0);
//                }catch (Exception e){
//                    throw new RuntimeException(list.get(i).getString("varaccountcode")+"科目的期初余额不存在！");
//                }
//
//                String kemuJie = String.valueOf(objs[0]);
//                String kemuDai = String.valueOf(objs[1]);
//                BigDecimal kemuJie1 = new BigDecimal(kemuJie);
//                BigDecimal kemuDai2 = new BigDecimal(kemuDai);
//                if (jieFang1.compareTo(kemuJie1) > 0) {
//                    throw new RuntimeException("分户借方期初余额超过科目借方期初余额！");
//                }
//                if (daiFang1.compareTo(kemuDai2) > 0) {
//                    throw new RuntimeException("分户贷方期初余额超过科目贷方期初余额！");
//                }
//            }
        }
        if (updates.size() > 0) {
            dao.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            dao.bulkInsert(inserts);
        }
        return inserts.size() + updates.size();
    }

    public int exportCompanyLedgerPeriods(List<Parameter> parameters, String orderby, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("orderby", orderby);
        for (int i = 0; i < parameters.size(); i++) {
            if (("UQCOMPANYID").equals(parameters.get(i).getColumnName())) {
                map.put("UQCOMPANYID", parameters.get(i).getValue()); // 公司
            }
            if (("UQACCOUNTID").equals(parameters.get(i).getColumnName())) {
                map.put("UQACCOUNTID", parameters.get(i).getValue()); // 科目
            }
            if (("UQLEDGETYPEID").equals(parameters.get(i).getColumnName())) {
                map.put("UQLEDGETYPEID", parameters.get(i).getValue()); // 分户类别
            }
        }
        List<Map<String, Object>> list = dao.exportLedgePeriod(map);
        List<Map<String, Object>>  mapList = new ArrayList<>();
        Map<String, String> title = new LinkedHashMap<>();
        title.put("科目编号","varaccountcode");
        title.put("科目名称","varaccountname");
        title.put("分户类别名称","varledgetypename");
        title.put("分户项目编码","varledgecode");
        title.put("分户项目名称","varledgename");
        title.put("期初借方余额","mnydebitperiodall");
        title.put("期初贷方余额","mnycreditperiodall");
        for(int i=0; i < list.size(); i++){
            Map<String,Object> infoMap = new HashMap<>();
            infoMap.put("varaccountcode", list.get(i).get("varaccountcode"));
            infoMap.put("varaccountname", list.get(i).get("varaccountname"));
            infoMap.put("varledgetypename", list.get(i).get("varledgetypename"));
            infoMap.put("varledgecode", list.get(i).get("varledgecode"));
            infoMap.put("varledgename", list.get(i).get("varledgename"));
            infoMap.put("mnydebitperiodall", list.get(i).get("mnydebitperiod"));
            infoMap.put("mnycreditperiodall", list.get(i).get("mnycreditperiod"));
            mapList.add(infoMap);
        }
        EasyPoiUtil.DownloadExcel(response,"分户期初余额设置",mapList,title);
        return list.size();
    }

    private void checkFile(Sheet sheet) throws Exception {
        if ("科目编码".equals(sheet.getCell(0, 0).getContents())
                && "分户类别名称".equals(sheet.getCell(1, 0).getContents())
                && "分户项目编码".equals(sheet.getCell(2, 0).getContents())
                && "年初借方余额".equals(sheet.getCell(3, 0).getContents())
                && "年初贷方余额".equals(sheet.getCell(4, 0).getContents()))
        {}
        else
        {
            throw new Exception("模版不正确!请重新选择");
        }
    }
    /**
     * 检查导入数据是否符合规定
     * @param list 待检验数据
     * @throws Exception
     */
    public List<Map<String, Object>> checkImportData(List<CompanyLedgerPeriods> list) throws Exception {
        List<Map<String, Object>> idMaps = new ArrayList<>();
        StringBuffer errormsg = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            int j = i+2;
            CompanyLedgerPeriods entity = list.get(i);
            String varaccountcode = entity.getUqaccountid().getVaraccountcode();
            String varledgetypename = entity.getVarledgetypename();
            String varledgecode = entity.getUqledgeid().getVarledgecode();
            BigDecimal mnydebitperiod = entity.getMnydebitperiod();
            BigDecimal mnycreditperiod = entity.getMnycreditperiod();

            boolean hasnull = true;
            //1.检验各项非空字段；
            if (StringUtils.isBlank(varaccountcode)) {
                errormsg.append("第 "+ j + " 行 【科目编号】为空|");
                hasnull = false;
            }
            if (StringUtils.isBlank(varledgetypename)) {
                errormsg.append("第 "+ j + " 条 【分户类别名称】为空|");
                hasnull = false;
            }
            if (StringUtils.isBlank(varledgecode)) {
                errormsg.append("第"+ j + "条 分户项目编码为空|");
                hasnull = false;
            }
            if (mnydebitperiod.compareTo(BigDecimal.ZERO) > 0 &&  mnycreditperiod.compareTo(BigDecimal.ZERO) > 0) {
                errormsg.append("第"+ j + "条 年初借方余额和年初贷方余额不可同时存在|");
                hasnull = false;
            }
            if (hasnull) {
                //2.检查导入的科目是否有允许分户设置；
                String uqaccountsetid = com.geeke.utils.SessionUtils.getUser().getCompanyId();
                PageRequest accountPage;
                List<Parameter> accountParams = Lists.newArrayList();
                accountParams.add(new Parameter("UQACCOUNTSETID", "=", uqaccountsetid));
                accountParams.add(new Parameter("VARACCOUNTCODE", "=", varaccountcode));
                accountPage = new PageRequest(accountParams);
                List<TglAccounts> tglAccounts = accountsDao.listAll(accountPage);
                if (tglAccounts.size() <=0 ) {
                    errormsg.append("第 "+ j + " 行 科目不存在或不为末级科目|");
                    hasnull = false;
                }
                else if ("0".equals(tglAccounts.get(0).getIntisledge())) {
                    errormsg.append("第 "+ j + " 行 科目不允许设置分户|");
                    hasnull = false;
                }
                else {
                    //3.检查导入的分户类别是否存在（根据名称来判断）；
                    List<TglLedgetype> ledgerTypes = ledgetypeDao.getLedgerTypeEntityByName(tglAccounts.get(0).getUqaccountid(), varledgetypename);
                    if (ledgerTypes.size() <= 0) {
                        errormsg.append("第 "+ j + " 行 分户类别不在对应科目的分户关系中|");
                        hasnull = false;
                    } else {
                        //4.导入的分户项目是否存在（根据分户项目代码判断）；
                        PageRequest ledgerPage;
                        List<Parameter> ledgerParams = Lists.newArrayList();
                        ledgerParams.add(new Parameter("VARLEDGECODE", "=", varledgecode));
                        ledgerParams.add(new Parameter("UQLEDGETYPEID", "=", ledgerTypes.get(0).getUqledgetypeid()));
                        ledgerPage = new PageRequest(ledgerParams);
                        List<TglLedger> ledgers = tglLedgerDao.listAll(ledgerPage);
                        if (ledgers.size() <= 0) {
                            errormsg.append("第 "+ j + " 行 分户项目不在对应分户类别中|");
                            hasnull = false;
                        } else if (1 != ledgers.get(0).getIntislastlevel()) {
                            errormsg.append("第 "+ j + " 行 分户项目不是末级项目|");
                            hasnull = false;
                        } else if (!mnydebitperiod.toString().matches("^\\d+(\\.\\d{1,2})?$")) {
                            errormsg.append("第 "+ j + " 行 年初借方余额数据格式不正确|");
                            hasnull = false;
                        }
                        else if (!mnycreditperiod.toString().matches("^\\d+(\\.\\d{1,2})?$")) {
                            errormsg.append("第 "+ j + " 行 年初贷方余额数据格式不正确|");
                            hasnull = false;
                        }
                        else {
                            Map<String, Object> idMap = new HashMap<>();
                            idMap.put("uqaccountid", tglAccounts.get(0).getUqaccountid());
                            idMap.put("uqledgeid", ledgers.get(0).getUqledgeid());
                            idMaps.add(idMap);
                        }
                    }
                }
            }
            if (!hasnull) {
                throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, errormsg.toString()));
            }
        }
        return idMaps;
    }
}
