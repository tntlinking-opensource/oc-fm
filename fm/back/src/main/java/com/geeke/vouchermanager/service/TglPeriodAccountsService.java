package com.geeke.vouchermanager.service;

import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.utils.*;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.vouchermanager.dao.TglAccountsDao;
import com.geeke.vouchermanager.dao.TglPeriodAccountsDao;
import com.geeke.vouchermanager.entity.TglAccounts;
import com.geeke.vouchermanager.entity.TglLedger;
import com.geeke.vouchermanager.entity.TglLedgerModel;
import com.geeke.vouchermanager.entity.TglPeriodAccounts;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang.ObjectUtils;
import org.omg.CORBA.StringHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 科目期初余额Service
 * @author
 * @version
 */

@Service("tglPeriodAccountsService")
@Transactional(readOnly = false)
public class TglPeriodAccountsService extends CrudService<TglPeriodAccountsDao, TglPeriodAccounts> {
    @Autowired
    private TglPeriodAccountsDao tglPeriodAccountsDao;
    @Autowired
    private TglAccountsDao tglaccountsdao;
    public Page<TglPeriodAccounts> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
        PageRequest pageRequest = new PageRequest(offset, limit, parameters, orderby);
//        Parameter parameter = new Parameter("tenant_id","=",getTenantId());
//        parameters.add(parameter);
        String accauntid=null;
        if (parameters.size()!=0){
            accauntid=parameters.get(0).getValue().toString();
        }
        int total = tglPeriodAccountsDao.count1(SessionUtils.getUser().getCompanyId(),accauntid,limit,offset);
        List<TglPeriodAccounts> list = null;
        if(total > 0) {
            list = tglPeriodAccountsDao.listPage1(SessionUtils.getUser().getCompanyId(),accauntid,limit,offset);
        }
        return new Page<TglPeriodAccounts>(total, list);
    }
    public TglPeriodAccounts add(TglPeriodAccounts entity) throws Exception {
        String companyId = SessionUtils.getUser().getCompanyId();
        TglAccounts uqaccountid = entity.getUqaccountid();
        if(StringUtils.isBlank(uqaccountid.getUqaccountid())){
            throw new Exception("选择的科目数据不正确:没有科目id");
        }
        List<TglAccounts> accountById = tglaccountsdao.getAccountById(uqaccountid.getUqaccountid());
        if (accountById.size()==1){
            String intflag = accountById.get(0).getIntislastlevel();
            if (intflag.equals("0")){
                throw new Exception("选择的科目为非末级科目！！"+uqaccountid.getVaraccountcode());
            }
        }else {
            throw new Exception("选择的科目数据不正确：查询不到科目数据"+uqaccountid.getVaraccountcode());
        }
//        int ishasseveral = tglPeriodAccountsDao.isHasSeveral(uqaccountid.getVaraccountcode(), getTenantId());
//        int ishassamedata = tglPeriodAccountsDao.isHasSameData(uqaccountid.getUqaccountid(), getTenantId());
//        if (ishasseveral>0){
//            throw new Exception("选择的科目存在发生数，不能新增科目期初余额");
//        }
//        if (ishassamedata>0){
//            throw new Exception("存在同一数据，请选择修改科目期初余额");
//        }

        //查询是否已经有数据了
        int i = tglPeriodAccountsDao.count2(companyId, entity.getUqaccountid().getUqaccountid());
        if (i>0){
            entity.setUqcompanyid(companyId);
            entity.setMnydebitperiod(entity.getMnydebitperiodall());
            entity.setMnycreditperiod(entity.getMnycreditperiodall());
            tglPeriodAccountsDao.update(entity);
        }else {
            entity.setUqcompanyid(companyId);
            entity.setId(IdGen.uuid());
            entity.setUqglobalperiodid("00000000-0000-0000-0000-000000000000");
            entity.setMnydebitperiod(entity.getMnydebitperiodall());
            entity.setMnycreditperiod(entity.getMnycreditperiodall());
            tglPeriodAccountsDao.insert(entity);
        }
        this.saveAction(this.createAction(ActionConstants.ACTION_CREATED, entity));
        return new TglPeriodAccounts();
    }
    public TglPeriodAccounts get1(String id) {
        return tglPeriodAccountsDao.get1(id,SessionUtils.getUser().getCompanyId());
    }
    public int exportTglPeriodAccounts(HttpServletResponse response) throws Exception {
        List<Parameter> parameters = new ArrayList<>();
        PageRequest pageRequest = new PageRequest(parameters);
        List<TglPeriodAccounts> tglPeriodAccounts = tglPeriodAccountsDao.listAll1(SessionUtils.getUser().getCompanyId());
        List<Map<String, Object>>  mapList = new ArrayList<>();
        Map<String, String> title = new LinkedHashMap<>();
//        科目编号	名称	期初借方余额	期初贷方余额

        title.put("科目编号","varaccountcode");
        title.put("名称","varaccountname");
        title.put("期初借方余额","mnydebitperiodall");
        title.put("期初贷方余额","mnycreditperiodall");

        for (int i = 0; i < tglPeriodAccounts.size(); i++) {
            TglPeriodAccounts tglPeriodAccount = tglPeriodAccounts.get(i);
            TglAccounts uqaccountid = tglPeriodAccount.getUqaccountid();
            Map<String,Object> infoMap = new HashMap<>();
            infoMap.put("varaccountcode",uqaccountid.getVaraccountcode());
            infoMap.put("varaccountname",uqaccountid.getVaraccountname());
            infoMap.put("mnydebitperiodall",tglPeriodAccount.getMnydebitperiod());
            infoMap.put("mnycreditperiodall",tglPeriodAccount.getMnycreditperiodall());

            mapList.add(infoMap);
        }
        EasyPoiUtil.DownloadExcel(response,"分户",mapList,title);
        return tglPeriodAccounts.size();
    }
    public void importTglPeriodAccounts(MultipartFile file) throws Exception {
        String uqcompanyid= SessionUtils.getUser().getCompanyId();
        StringHolder errormsg = new StringHolder();
        InputStream is = file.getInputStream();
        Workbook wk = Workbook.getWorkbook(is);
        Sheet st = wk.getSheet(0);  //读取第一个表格
        int rowlength= st.getRows();  //获取表格数据的行数
        errormsg.value = "";
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < rowlength; i++) {
            String v_accoundCode = ObjectUtils.toString(st.getCell(0, i).getContents().trim());
//                String v_accoundName = ObjectUtils.toString(sheet.getCell(1, row).getContents().trim());
            String v_debitMoney = ObjectUtils.toString(st.getCell(2, i).getContents().trim().replace(",", ""));
            double debitMoney = Double.parseDouble("".equals(v_debitMoney) ? "0" : v_debitMoney);
            String v_creditMoney = ObjectUtils.toString(st.getCell(3, i).getContents().trim().replace(",", ""));
            double creditMoney = Double.parseDouble("".equals(v_creditMoney) ? "0" : v_creditMoney);
            TglPeriodAccounts tglPeriodAccounts = new TglPeriodAccounts();
            tglPeriodAccounts.setUqcompanyid(uqcompanyid);
            tglPeriodAccounts.setUqglobalperiodid("00000000-0000-0000-0000-000000000000");
            tglPeriodAccounts.setMnydebitperiodall(BigDecimal.valueOf(debitMoney));
            tglPeriodAccounts.setMnycreditperiodall(BigDecimal.valueOf(creditMoney));
            List<Parameter> parameter = new ArrayList<>();
            parameter.add(new Parameter("VARACCOUNTCODE", "=", v_accoundCode));
            PageRequest pageRequest = new PageRequest(parameter);
            List<TglAccounts> tglAccounts = tglaccountsdao.listAll(pageRequest);
            if (tglAccounts.size()==0){
                throw new Exception("选择的科目数据不正确:科目不存在"+v_accoundCode);
            }
            tglPeriodAccounts.setUqaccountid(tglAccounts.get(0));
            add(tglPeriodAccounts);
        }

    }
}
