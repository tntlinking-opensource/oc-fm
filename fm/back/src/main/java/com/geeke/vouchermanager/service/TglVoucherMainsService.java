package com.geeke.vouchermanager.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geeke.admin.entity.User;
import com.geeke.common.constants.ActionConstants;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.org.entity.Company;
import com.geeke.sys.entity.Action;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.tgl.flow.dao.FlowtypeDao;
import com.geeke.tgl.flow.entity.Flowtype;
import com.geeke.tgl.global.entity.GlobalPeriods;
import com.geeke.tgl.prop.dao.PropValueDao;
import com.geeke.tgl.prop.entity.PropValue;
import com.geeke.utils.*;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.vouchermanager.dao.*;
import com.geeke.vouchermanager.entity.*;
import com.geeke.vouchermanager.service.SysUserService;
import com.geeke.vouchermanager.service.TglGlobalPeriodsService;
import com.geeke.vouchermanager.service.TglGroupAccountsService;
import com.geeke.vouchermanager.service.TglVoucherDetailLedgerService;
import com.geeke.vouchermanager.service.TglVoucherDetailsService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang.ObjectUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.omg.CORBA.StringHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 制证Service
 *
 * @author
 */

@Service("tglVoucherMainsService")
@Transactional(readOnly = false)
public class TglVoucherMainsService extends CrudService<TglVoucherMainsDao, TglVoucherMains> {

    @Autowired
    private TglGlobalPeriodsDao tglGlobalPeriodsDao;

    @Autowired
    private TglGlobalPeriodsService tglGlobalPeriodsService;

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private AccountLedgetypeDao accountLedgetypeDao;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TglGroupAccountsDao tglGroupAccountsDao;

    @Autowired
    private TglGroupAccountsService tglGroupAccountsService;

    @Autowired
    private TglVoucherDetailsDao tglVoucherDetailsDao;

    @Autowired
    private TglVoucherDetailsService tglVoucherDetailsService;
    @Autowired
    private TglAccountsService tglAccountsService;

    @Autowired
    private TglVoucherDetailLedgerDao tglVoucherDetailLedgerDao;

    @Autowired
    private TglVoucherDetailLedgerService tglVoucherDetailLedgerService;
    @Autowired
    private TglVoucherMainsDao tglVoucherMainsDao;
    @Autowired
    private TglVoucherDetailLedgertypeDao tglVoucherDetailLedgertypeDao;
    @Autowired
    private TglAccountsXjllDao tglAccountsXjllDao;
    @Autowired
    private TglAccountsXjllService tglAccountsXjllService;
    @Autowired
    private TglPeriodAccountsDao tglPeriodAccountsDao;
    @Autowired
    private CompanyLedgerPeriodsDao companyLedgerPeriodsDao;
    @Autowired
    private TglPeriodAccountsNotjzDao tglPeriodAccountsNotjzDao;
    @Autowired
    private PropValueDao propValueDao;
    @Autowired
    private TglCompanyLedgerPeriodsDao tglCompanyLedgerPeriodsDao;
    @Autowired
    private TglAccountsDao tglAccountsDao;
    @Autowired
    private FlowtypeDao flowtypeDao;
    @Autowired
    private TglLedgerDao tglLedgerDao;

    @Override
    public TglVoucherMains get(String id) {
        TglVoucherMains tglVoucherMains = super.get(id);

        List<Parameter> params = null;

        /*获取子表列表   会计期*/
        params = Lists.newArrayList();

        params.add(new Parameter("UQGLOBALPERIODID", "=", tglVoucherMains.getUqglobalperiodid().getUqglobalperiodid()));
        tglVoucherMains.setTglGlobalPeriodsList(tglGlobalPeriodsService.listAll(params, "").get(0));
        tglVoucherMains.setUqglobalperiodid(tglGlobalPeriodsService.listAll(params, "").get(0));






        /*获取子表列表   凭证明细表*/
        params = Lists.newArrayList();

        params.add(new Parameter("UQVOUCHERID", "=", tglVoucherMains.getUqvoucherid()));
        List<TglVoucherDetails> byUqvoucherid = tglVoucherDetailsService.listAll(params, "");
        for (int i = 0; i < byUqvoucherid.size(); i++) {
            TglVoucherDetails tglVoucherDetails = byUqvoucherid.get(i);
            /*获取子表列表   会计科目表*/
            params = Lists.newArrayList();
            params.add(new Parameter("uqaccountid", "=", tglVoucherDetails.getUqaccountid()));
            List<TglAccounts> tglAccounts = tglAccountsService.listAll(params, "");
            tglVoucherDetails.setTglAccounts(tglAccounts.get(0));

            params = Lists.newArrayList();
            params.add(new Parameter("UQVOUCHERDETAIL_ID", "=", tglVoucherDetails.getUqvoucherdetailid()));

            List<TglAccountsXjll> tglAccountsXjlls = tglAccountsXjllService.listAll(params,"");
            if (tglAccountsXjlls.size()!=0){
                tglVoucherDetails.setTglAccountsXjll(tglAccountsXjlls.get(0));
            }
            List<TglLedgetype> tglLedgetypes = tglVoucherDetailsService.selectQuestionItemByQuestionId(tglVoucherDetails.getUqvoucherdetailid());
            if (tglLedgetypes.size()!=0){
                tglVoucherDetails.setTglledgetype(tglLedgetypes);
            }
        }
        tglVoucherMains.setTglVoucherDetailsList(byUqvoucherid);
        /*获取子表列表   凭证分录分户分摊表*/
        params = Lists.newArrayList();

//        params.add(new Parameter("UQVOUCHERDETAILID", "=", tglVoucherMains.getId()));
//        tglVoucherMains.setTglVoucherDetailLedgerList(tglVoucherDetailLedgerService.listAll(params, ""));

        return tglVoucherMains;
    }


    public TglVoucherMains save(TglVoucherMains tglVoucherMains) {
        //通过判断是否有凭证id进行判断是否是新增还是修改 然后修改  修改页面的 没有暂存的选项？

        if (StringUtils.isBlank(tglVoucherMains.getUqvoucherid())) {
            return add(tglVoucherMains);
        } else {
            return edit(tglVoucherMains);
        }
    }

    /**
     * 这里是处理凭证add添加的逻辑
     *
     * @param tglVoucherMains
     * @return
     */

    public TglVoucherMains add(TglVoucherMains tglVoucherMains) {
        String addUserid = SessionUtils.getUser().getId();
        SysUser sysUser = new SysUser();
        sysUser.setId(addUserid);
        tglVoucherMains.setUqfillerid(sysUser);
        if (tglVoucherMains.getMnydebitsum().compareTo(tglVoucherMains.getMnycreditsum()) != 0) {
            throw new RuntimeException("借贷金额不等！！！");
        }
        //这里如果是添加的话直接给一个uuid好吧
        tglVoucherMains.setUqvoucherid(UUID.randomUUID().toString().toUpperCase());
        tglVoucherMains.setTenantId(SessionUtils.getUser().getCompanyId());
        tglVoucherMains.setIntflag(0);
        //获取是否是暂存接口
        String submitodd = tglVoucherMains.getSubmitodd();
        //凭证字
        String uqnumberingName = tglVoucherMains.getUqnumberingname();
        //凭证类型
        String uqnumbering = tglVoucherMains.getUqnumbering();
        //获取凭证分录在进行处理
        List<TglVoucherDetails> tglVoucherDetailsList = tglVoucherMains.getTglVoucherDetailsList();

        //获取借贷余额数据
        String balance1001 = tglVoucherMainsDao.decideMoney(SessionUtils.getUser().getCompanyId(), "1001");
        String balance1002 = tglVoucherMainsDao.decideMoney(SessionUtils.getUser().getCompanyId(), "1002");
        BigDecimal newbalance1001 = new BigDecimal(0);
        BigDecimal newbalance1002 = new BigDecimal(0);
        //循环 分录进行数据处理 1获取借贷数据 2.赋值uuid
        int j = 1;
        for (TglVoucherDetails tglVoucherDetails : tglVoucherDetailsList) {
            tglVoucherDetails.setUqvoucherid(tglVoucherMains.getUqvoucherid());
            tglVoucherDetails.setUqaccountid(tglVoucherDetails.getTglAccounts().getUqaccountid());
            tglVoucherDetails.setIntseq(j);
            if (tglVoucherDetails.getMnydebit() == null) {
                tglVoucherDetails.setMnydebit(new BigDecimal(0));
            }
            if (tglVoucherDetails.getMnycredit() == null) {
                tglVoucherDetails.setMnycredit(new BigDecimal(0));
            }
            j++;
            if (tglVoucherDetails.getTglAccounts().getVaraccountcode().equals("1001")) {
                newbalance1001.add(tglVoucherDetails.getMnydebit().subtract(tglVoucherDetails.getMnycredit() == null ? new BigDecimal(0) : tglVoucherDetails.getMnycredit()));
            }
            if (tglVoucherDetails.getTglAccounts().getVaraccountcode().equals("1002")) {
                newbalance1002.add(tglVoucherDetails.getMnydebit().subtract(tglVoucherDetails.getMnycredit() == null ? new BigDecimal(0) : tglVoucherDetails.getMnycredit()));
            }
            tglVoucherDetails.setUqvoucherdetailid(UUID.randomUUID().toString().toUpperCase());
            List<TglLedgetype> tglledgetype = tglVoucherDetails.getTglledgetype();
            if (tglledgetype != null) {
                for (int ii = 0; ii < tglledgetype.size(); ii++) {
                    List<TglLedger> tglLedgerList = tglledgetype.get(ii).getTglLedgerList();
                    for (int i = 0; i < tglLedgerList.size(); i++) {
                        TglLedger tglLedger = tglLedgerList.get(i);
                        //这里存入分录分户表
                        TglVoucherDetailLedger tglVoucherDetailLedger = tglLedgerList.get(i).getTglVoucherDetailLedger();
                        tglVoucherDetailLedger.setUqvouledgerid(UUID.randomUUID().toString().toUpperCase());
                        tglVoucherDetailLedger.setUqvoucherdetailid(tglVoucherDetails.getUqvoucherdetailid());
                        tglVoucherDetailLedger.setIntseq(i + 1);
                        tglVoucherDetailLedger.setUqledgerid(tglLedger.getUqledgeid());
                        tglVoucherDetailLedger.setUqledgertypeid(tglledgetype.get(ii).getUqledgetypeid());
                        tglVoucherDetailLedgerDao.insert(tglVoucherDetailLedger);
                        //这里存入分录分户类别表 做关联关系表 - -有一说一这个表真可以淦掉
                        TglVoucherDetailLedgertype tglVoucherDetailLedgertype = new TglVoucherDetailLedgertype();
                        tglVoucherDetailLedgertype.setIntseq(i + 1);
                        tglVoucherDetailLedgertype.setUqvouledgertypeid(UUID.randomUUID().toString().toUpperCase());
                        tglVoucherDetailLedgertype.setUqledgertypeid(tglledgetype.get(ii).getUqledgetypeid());
                        tglVoucherDetailLedgertype.setUqvoucherdetailid(tglVoucherDetails.getUqvoucherdetailid());
                        tglVoucherDetailLedgertype.setUqvoucherid(tglVoucherMains.getUqvoucherid());
                        tglVoucherDetailLedgertypeDao.insert(tglVoucherDetailLedgertype);
                        //这里处理往来可以用 -- 这里先不做
                    }
                }


            }
        }
        //判断对比科目是否有余额
        // TODO: 8/5/2023 只做提醒不做原逻辑修改 这里现金和银行科目 1001和1002不一定是末级科目 不能确定到具体科目是否有余额 只能确定 一级有  
        if (new BigDecimal(balance1001).compareTo(newbalance1001) < 0) {
            throw new RuntimeException("科目1001余额不足");
        }
        if (new BigDecimal(balance1002).compareTo(newbalance1002) < 0) {
            throw new RuntimeException("科目1002余额不足");
        }

        int num = 0;
        if ("结转".equals(uqnumberingName))
        //这里赋值的是给凭证编码
        {
            tglVoucherMains.setUqnumberingname("结转");
            tglVoucherMains.setUqnumbering("CF10100D-ED35-4B1D-AAC7-235F68DFB117");
        } else {
            for (TglVoucherDetails tglVoucherDetails : tglVoucherDetailsList) {
                if (tglVoucherDetails.getTglAccounts().getVaraccountcode().substring(0, 4).equals("1001") && !tglVoucherDetails.getMnydebit().equals(0)) {
                    tglVoucherMains.setUqnumberingname("现收");
                    tglVoucherMains.setUqnumbering("00000000-0000-0000-0000-000000000001");

                } else if (tglVoucherDetails.getTglAccounts().getVaraccountcode().substring(0, 4).equals("1001") && !tglVoucherDetails.getMnycredit().equals(0)) {
                    tglVoucherMains.setUqnumberingname("现付");
                    tglVoucherMains.setUqnumbering("00000000-0000-0000-0000-000000000002");
                } else if (tglVoucherDetails.getTglAccounts().getVaraccountcode().substring(0, 4).equals("1002") && !tglVoucherDetails.getMnydebit().equals(0)) {
                    tglVoucherMains.setUqnumberingname("银收");
                    tglVoucherMains.setUqnumbering("00000000-0000-0000-0000-000000000003");
                } else if (tglVoucherDetails.getTglAccounts().getVaraccountcode().substring(0, 4).equals("1002") && !tglVoucherDetails.getMnycredit().equals(0)) {
                    tglVoucherMains.setUqnumberingname("银付");
                    tglVoucherMains.setUqnumbering("00000000-0000-0000-0000-000000000004");
                }
            }
        }
        if (StringUtils.isBlank(tglVoucherMains.getUqnumberingname())) {
            tglVoucherMains.setUqnumberingname("转账");
            tglVoucherMains.setUqnumbering("00000000-0000-0000-0000-000000000005");
        }

        //凭证字
        uqnumberingName = tglVoucherMains.getUqnumberingname();
        //凭证类型
        uqnumbering = tglVoucherMains.getUqnumbering();


        num = tglVoucherMainsDao.selectSeq(SessionUtils.getUser().getCompanyId(), tglVoucherMains.getUqglobalperiodid().getUqglobalperiodid(), uqnumbering);
        tglVoucherMains.setIntvouchernum(uqnumberingName + '-' + num);
        tglVoucherMains.setIntcompanyseq(num);
        //保存凭证分录数据
        tglVoucherDetailsDao.bulkInsert(tglVoucherMains.getTglVoucherDetailsList());
        //现金流量编制保存
        for (TglVoucherDetails tglVoucherDetails : tglVoucherMains.getTglVoucherDetailsList()) {
            TglAccountsXjll tglAccountsXjll = tglVoucherDetails.getTglAccountsXjll();
            if (tglAccountsXjll != null) {
                SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                String dtfillersrv = simpleformat.format(date);
                tglAccountsXjll.setUqvoucherdetailId(tglVoucherDetails.getUqvoucherdetailid());
                tglAccountsXjll.setTenantId(SessionUtils.getUser().getCompanyId());
                tglAccountsXjll.setCreateDate(dtfillersrv);
                tglAccountsXjll.setUpdateDate(dtfillersrv);
                tglAccountsXjll.setId(UUID.randomUUID().toString().toUpperCase());
                tglAccountsXjll.setXjllConfigId(tglAccountsXjll.getUqflowtypeid().getUqflowtypeid());
                tglAccountsXjllDao.insert(tglAccountsXjll);
            }

        }
        // 设置所有凭证是否需要出纳-默认为不出纳
        String intiscash = "0";
        if (isCash(tglVoucherMains.getUqnumbering())) {
            intiscash = "1";
        }
        tglVoucherMains.setIntcashflag(Integer.valueOf(intiscash));
        tglVoucherMains.setUqcompanyid(SessionUtils.getUser().getCompanyId());
        //保存凭证主数据
        tglVoucherMainsDao.insert(tglVoucherMains);
        //暂存的和保存的区别其实就是是否处理了余额表 - -其实是可干掉余额表的，但是有个问题就是如果没有余额表的话查报表效率低
        //如果是暂存 那么走这个 - -但是 好像没有东西要写
        if (submitodd.equals("1")) {

        }
        if (submitodd.equals("0")) {//如果是保存走这个
            handlerAccountPeriod(tglVoucherMains.getUqvoucherid(), 1, 1);
            handlerLedgerPeriod(tglVoucherMains.getUqvoucherid(), 1, 1);
            if (!tglVoucherMains.getUqnumbering().equals("CF10100D-ED35-4B1D-AAC7-235F68DFB117")) {
                handlerAccountPeriodNoJZ(tglVoucherMains.getUqvoucherid(), 1, 1);
            }
        }
        return tglVoucherMains;
    }

    public void handlerAccountPeriodNoJZ(String voucherid, int tag, int flag) {
        //1.获取凭证分录的分组信息，按照科目，单位，会计期
        List<Map<String, Object>> list = null;
        if (tag == 2) {
            list = this.tglVoucherMainsDao.getAccountPeriodCashInfo(voucherid, SessionUtils.getUser().getCompanyId());
        } else {
            list = this.tglVoucherMainsDao.getAccountPeriodInfo(voucherid,SessionUtils.getUser().getCompanyId());
        }
        for (int i = 0; i < list.size(); i++) {
            //1.获取余额表所需信息
            Map<String, Object> obj = list.get(i);
            String uqaccountid = obj.get("uqaccountid").toString();
            String uqglobalperiodid = obj.get("uqglobalperiodid").toString();
            String uqcompanyid = obj.get("uqcompanyid").toString();
            BigDecimal accdebitsum = new BigDecimal(obj.get("accdebitsum").toString());
            BigDecimal acccreditsum = new BigDecimal(obj.get("acccreditsum").toString());
            BigDecimal accfdebitsum = new BigDecimal(obj.get("accfdebitsum").toString());
            BigDecimal accfcreditsum = new BigDecimal(obj.get("accfcreditsum").toString());

            //2.锁掉余额表
            List<Map<String, Object>> lockList = this.tglVoucherMainsDao.lockAccountPeriodNoJZ(uqaccountid, uqglobalperiodid, uqcompanyid);
            int lockline = lockList.size();

            //3.更新余额表
            updateAccountPeriodNoJZ(lockline, uqaccountid, uqglobalperiodid, uqcompanyid,
                    accdebitsum, acccreditsum, accfdebitsum, accfcreditsum, tag, flag);
        }
    }

    /**
     * @param lockline
     * @param uqaccountid
     * @param uqglobalperiodid
     * @param uqcompanyid
     * @param accdebitsum
     * @param acccreditsum
     * @param tag              1-制证,2-出纳，3-记账
     * @param flag             传递正负1
     *                         制证传递tag1,flag1,查删改删除凭证传递tag1,flag-1,出纳tag2,flag1,反出纳tag2,flag-1,记账tag3,flag1
     */
    public void updateAccountPeriodNoJZ(int lockline, String uqaccountid, String uqglobalperiodid,
                                        String uqcompanyid, BigDecimal accdebitsum, BigDecimal acccreditsum,
                                        BigDecimal accfdebitsum, BigDecimal accfcreditsum, int tag, int flag) {
        String strSql = " ";

        accdebitsum = accdebitsum.multiply(new BigDecimal(flag));
        acccreditsum = acccreditsum.multiply(new BigDecimal(flag));
        accfdebitsum = accfdebitsum.multiply(new BigDecimal(flag));
        accfcreditsum = accfcreditsum.multiply(new BigDecimal(flag));
        if (lockline <= 0) {
            //不存在科目余额数据

            TglPeriodAccountsNotjz tglPeriodAccountsNotjz = new TglPeriodAccountsNotjz();
            tglPeriodAccountsNotjz.setUqcompanyid(uqcompanyid);
            tglPeriodAccountsNotjz.setUqglobalperiodid(uqglobalperiodid);
            tglPeriodAccountsNotjz.setUqaccountid(uqaccountid);


            tglPeriodAccountsNotjz.setMnydebitperiodall(accdebitsum);
            tglPeriodAccountsNotjz.setMnycreditperiodall(acccreditsum);
            tglPeriodAccountsNotjz.setMnyfdebitperiodall(accfdebitsum);
            tglPeriodAccountsNotjz.setMnyfcreditperiodall(accfcreditsum);


            tglPeriodAccountsNotjz.setMnydebitperiod(BigDecimal.valueOf(0));
            tglPeriodAccountsNotjz.setMnycreditperiod(BigDecimal.valueOf(0));
            tglPeriodAccountsNotjz.setMnyfdebitperiod(BigDecimal.valueOf(0));
            tglPeriodAccountsNotjz.setMnyfcreditperiod(BigDecimal.valueOf(0));

            tglPeriodAccountsNotjz.setMnydebitcashed(BigDecimal.valueOf(0));
            tglPeriodAccountsNotjz.setMnycreditcashed(BigDecimal.valueOf(0));
            tglPeriodAccountsNotjz.setMnyfdebitcashed(BigDecimal.valueOf(0));
            tglPeriodAccountsNotjz.setMnyfcreditcashed(BigDecimal.valueOf(0));


            tglPeriodAccountsNotjzDao.insert(tglPeriodAccountsNotjz);


        } else {

            if (tag == 1) {
                TglPeriodAccountsNotjz tglPeriodAccountsNotjz = new TglPeriodAccountsNotjz();
                tglPeriodAccountsNotjz.setUqcompanyid(uqcompanyid);
                tglPeriodAccountsNotjz.setUqglobalperiodid(uqglobalperiodid);
                tglPeriodAccountsNotjz.setUqaccountid(uqaccountid);


                tglPeriodAccountsNotjz.setMnydebitperiodall(accdebitsum);
                tglPeriodAccountsNotjz.setMnycreditperiodall(acccreditsum);
                tglPeriodAccountsNotjz.setMnyfdebitperiodall(accfdebitsum);
                tglPeriodAccountsNotjz.setMnyfcreditperiodall(accfcreditsum);


                tglPeriodAccountsNotjz.setMnydebitperiod(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnycreditperiod(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnyfdebitperiod(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnyfcreditperiod(BigDecimal.valueOf(0));

                tglPeriodAccountsNotjz.setMnydebitcashed(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnycreditcashed(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnyfdebitcashed(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnyfcreditcashed(BigDecimal.valueOf(0));


                tglPeriodAccountsNotjzDao.newupdate(tglPeriodAccountsNotjz);


            } else if (tag == 2) {
                TglPeriodAccountsNotjz tglPeriodAccountsNotjz = new TglPeriodAccountsNotjz();
                tglPeriodAccountsNotjz.setUqcompanyid(uqcompanyid);
                tglPeriodAccountsNotjz.setUqglobalperiodid(uqglobalperiodid);
                tglPeriodAccountsNotjz.setUqaccountid(uqaccountid);


                tglPeriodAccountsNotjz.setMnydebitperiodall(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnycreditperiodall(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnyfdebitperiodall(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnyfcreditperiodall(BigDecimal.valueOf(0));


                tglPeriodAccountsNotjz.setMnydebitperiod(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnycreditperiod(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnyfdebitperiod(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnyfcreditperiod(BigDecimal.valueOf(0));

                tglPeriodAccountsNotjz.setMnydebitcashed(accfcreditsum);
                tglPeriodAccountsNotjz.setMnycreditcashed(accfcreditsum);
                tglPeriodAccountsNotjz.setMnyfdebitcashed(accfcreditsum);
                tglPeriodAccountsNotjz.setMnyfcreditcashed(accfcreditsum);


                tglPeriodAccountsNotjzDao.newupdate(tglPeriodAccountsNotjz);

            } else if (tag == 3) {
                TglPeriodAccountsNotjz tglPeriodAccountsNotjz = new TglPeriodAccountsNotjz();
                tglPeriodAccountsNotjz.setUqcompanyid(uqcompanyid);
                tglPeriodAccountsNotjz.setUqglobalperiodid(uqglobalperiodid);
                tglPeriodAccountsNotjz.setUqaccountid(uqaccountid);


                tglPeriodAccountsNotjz.setMnydebitperiodall(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnycreditperiodall(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnyfdebitperiodall(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnyfcreditperiodall(BigDecimal.valueOf(0));


                tglPeriodAccountsNotjz.setMnydebitperiod(accdebitsum);
                tglPeriodAccountsNotjz.setMnycreditperiod(acccreditsum);
                tglPeriodAccountsNotjz.setMnyfdebitperiod(accfdebitsum);
                tglPeriodAccountsNotjz.setMnyfcreditperiod(accfcreditsum);

                tglPeriodAccountsNotjz.setMnydebitcashed(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnycreditcashed(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnyfdebitcashed(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnyfcreditcashed(BigDecimal.valueOf(0));


                tglPeriodAccountsNotjzDao.newupdate(tglPeriodAccountsNotjz);

            } else if (tag == 6) {
                TglPeriodAccountsNotjz tglPeriodAccountsNotjz = new TglPeriodAccountsNotjz();
                tglPeriodAccountsNotjz.setUqcompanyid(uqcompanyid);
                tglPeriodAccountsNotjz.setUqglobalperiodid(uqglobalperiodid);
                tglPeriodAccountsNotjz.setUqaccountid(uqaccountid);


                tglPeriodAccountsNotjz.setMnydebitperiodall(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnycreditperiodall(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnyfdebitperiodall(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnyfcreditperiodall(BigDecimal.valueOf(0));


                tglPeriodAccountsNotjz.setMnydebitperiod(accdebitsum.multiply(BigDecimal.valueOf(-1)));
                tglPeriodAccountsNotjz.setMnycreditperiod(acccreditsum.multiply(BigDecimal.valueOf(-1)));
                tglPeriodAccountsNotjz.setMnyfdebitperiod(acccreditsum.multiply(BigDecimal.valueOf(-1)));
                tglPeriodAccountsNotjz.setMnyfcreditperiod(accfcreditsum.multiply(BigDecimal.valueOf(-1)));

                tglPeriodAccountsNotjz.setMnydebitcashed(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnycreditcashed(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnyfdebitcashed(BigDecimal.valueOf(0));
                tglPeriodAccountsNotjz.setMnyfcreditcashed(BigDecimal.valueOf(0));


                tglPeriodAccountsNotjzDao.newupdate(tglPeriodAccountsNotjz);

            }

        }
    }
    public void handlerAccountPeriod(JSONObject map, int tag, int flag) {
        //1.获取凭证分录的分组信息，按照科目，单位，会计期
        List<Map<String, Object>> list = null;

        if (tag == 2) {
            list = this.tglVoucherMainsDao.getAccountPeriodCashInfo(map.getString("uqvoucherid"),SessionUtils.getUser().getId());
        } else {
            list = this.tglVoucherMainsDao.getAccountPeriodInfo(map.getString("uqvoucherid"),SessionUtils.getUser().getId());
        }
/**
 *   map.put("mnydebitsum1", money1);
 *             map.put("mnycreditsum", money.abs());
 *             map.put("mnycreditsum1", money1);
 */
        BigDecimal mnydebitsum1 = new BigDecimal(map.getString("mnydebitsum1"));
        BigDecimal mnycreditsum1 = new BigDecimal(map.getString("mnycreditsum1"));
        for (int i = 0; i < list.size(); i++) {
            BigDecimal accdebitsum = new BigDecimal(0);
            BigDecimal acccreditsum = new BigDecimal(0);
            //1.获取余额表所需信息
            Map<String, Object> obj =  list.get(i);
            String uqaccountid = String.valueOf(obj.get("uqaccountid"));
            String uqglobalperiodid = String.valueOf(obj.get("uqglobalperiodid"));
            String uqcompanyid = String.valueOf(obj.get("uqcompanyid"));
            if (mnydebitsum1.doubleValue()>=0) {
                acccreditsum = new BigDecimal(String.valueOf(obj.get("acccreditsum")));
                accdebitsum = new BigDecimal(String.valueOf(obj.get("accdebitsum")));
            }else if (mnydebitsum1.doubleValue()<0&&new BigDecimal(String.valueOf(obj.get("acccreditsum"))).doubleValue()!=0) { //
                accdebitsum = new BigDecimal(String.valueOf(obj.get("accdebitsum")));
//                accdebitsum = accdebitsum.multiply(BigDecimal.valueOf(-1));
                acccreditsum = new BigDecimal(String.valueOf(obj.get("acccreditsum")));
//                acccreditsum = acccreditsum.multiply(BigDecimal.valueOf(-1));
            }else if (mnydebitsum1.doubleValue()<0&&new BigDecimal(String.valueOf(obj.get("acccreditsum"))).doubleValue()==0) {
                accdebitsum =new BigDecimal(String.valueOf(obj.get("accdebitsum")));
                accdebitsum = accdebitsum.multiply(BigDecimal.valueOf(-1));
                acccreditsum = new BigDecimal(String.valueOf(obj.get("acccreditsum")));
                acccreditsum = acccreditsum.multiply(BigDecimal.valueOf(-1));
            }

            BigDecimal accfdebitsum = new BigDecimal(String.valueOf(obj.get("accfdebitsum")));
            BigDecimal accfcreditsum = new BigDecimal(String.valueOf(obj.get("accfcreditsum")));

            //2.锁掉余额表
            @SuppressWarnings("rawtypes")
            List lockList = this.tglVoucherMainsDao.lockAccountPeriod(uqaccountid, uqglobalperiodid, uqcompanyid);
            int lockline = lockList.size();

            //3.更新余额表
            updateAccountPeriod(lockline, uqaccountid, uqglobalperiodid, uqcompanyid,
                    accdebitsum, acccreditsum, accfdebitsum, accfcreditsum, tag, flag);
        }
    }
    public void handlerAccountPeriod(String voucherid, int tag, int flag) {
        //1.获取凭证分录的分组信息，按照科目，单位，会计期
        List<Map<String, Object>> list = null;

        if (tag == 2) {
            list = this.tglVoucherMainsDao.getAccountPeriodCashInfo(voucherid, SessionUtils.getUser().getCompanyId());
        } else {
            list = this.tglVoucherMainsDao.getAccountPeriodInfo(voucherid, SessionUtils.getUser().getCompanyId());
        }

        for (int i = 0; i < list.size(); i++) {
            //1.获取余额表所需信息
            Map<String, Object> obj = list.get(i);
            String uqaccountid = obj.get("uqaccountid").toString();
            String uqglobalperiodid = obj.get("uqglobalperiodid").toString();
            String uqcompanyid = obj.get("uqcompanyid").toString();
            BigDecimal accdebitsum = new BigDecimal(obj.get("accdebitsum").toString());
            BigDecimal acccreditsum = new BigDecimal(obj.get("acccreditsum").toString());
            BigDecimal accfdebitsum = new BigDecimal(obj.get("accfdebitsum").toString());
            BigDecimal accfcreditsum = new BigDecimal(obj.get("accfcreditsum").toString());

            //2.锁掉余额表
            List<Map<String, Object>> lockList = this.tglVoucherMainsDao.lockAccountPeriod(uqaccountid, uqglobalperiodid, uqcompanyid);
            int lockline = lockList.size();

            //3.更新余额表
            updateAccountPeriod(lockline, uqaccountid, uqglobalperiodid, uqcompanyid,
                    accdebitsum, acccreditsum, accfdebitsum, accfcreditsum, tag, flag);

        }
    }

    public void updateAccountPeriod(int lockline, String uqaccountid, String uqglobalperiodid,
                                    String uqcompanyid, BigDecimal accdebitsum, BigDecimal acccreditsum,
                                    BigDecimal accfdebitsum, BigDecimal accfcreditsum, int tag, int flag) {
        accdebitsum = accdebitsum.multiply(new BigDecimal(flag));
        acccreditsum = acccreditsum.multiply(new BigDecimal(flag));
        accfdebitsum = accfdebitsum.multiply(new BigDecimal(flag));
        accfcreditsum = accfcreditsum.multiply(new BigDecimal(flag));
        if (lockline <= 0) {

            //不存在科目余额数据
            TglPeriodAccounts tglPeriodAccounts = new TglPeriodAccounts();
            tglPeriodAccounts.setUqcompanyid(uqcompanyid);
            tglPeriodAccounts.setUqglobalperiodid(uqglobalperiodid);
            TglAccounts tglAccounts = new TglAccounts();
            tglAccounts.setUqaccountid(uqaccountid);
            tglPeriodAccounts.setUqaccountid(tglAccounts);

            tglPeriodAccounts.setMnydebitperiodall(accdebitsum);
            tglPeriodAccounts.setMnycreditperiodall(acccreditsum);
            tglPeriodAccounts.setMnyfdebitperiodall(accfdebitsum);
            tglPeriodAccounts.setMnyfcreditperiodall(accfcreditsum);

            tglPeriodAccounts.setMnydebitperiod(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnycreditperiod(BigDecimal.valueOf(0));

            tglPeriodAccounts.setMnyfdebitperiod(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnyfcreditperiod(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnydebitcashed(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnycreditcashed(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnyfdebitcashed(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnyfcreditcashed(BigDecimal.valueOf(0));
            tglPeriodAccountsDao.insert(tglPeriodAccounts);
        } else {
            if (tag == 1) {
                TglPeriodAccounts tglPeriodAccounts = new TglPeriodAccounts();
                tglPeriodAccounts.setUqcompanyid(uqcompanyid);
                tglPeriodAccounts.setUqglobalperiodid(uqglobalperiodid);
                TglAccounts tglAccounts = new TglAccounts();
                tglAccounts.setUqaccountid(uqaccountid);

                tglPeriodAccounts.setUqaccountid(tglAccounts);
                tglPeriodAccounts.setMnydebitperiodall(accdebitsum);
                tglPeriodAccounts.setMnycreditperiodall(acccreditsum);
                tglPeriodAccounts.setMnyfdebitperiodall(accfdebitsum);
                tglPeriodAccounts.setMnyfcreditperiodall(accfcreditsum);
                tglPeriodAccounts.setMnydebitperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnydebitcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditcashed(BigDecimal.valueOf(0));
                tglPeriodAccountsDao.newupdate(tglPeriodAccounts);
            } else if (tag == 2) {
                TglPeriodAccounts tglPeriodAccounts = new TglPeriodAccounts();
                tglPeriodAccounts.setUqcompanyid(uqcompanyid);
                tglPeriodAccounts.setUqglobalperiodid(uqglobalperiodid);
                TglAccounts tglAccounts = new TglAccounts();
                tglAccounts.setUqaccountid(uqaccountid);
                tglPeriodAccounts.setUqaccountid(tglAccounts);
                tglPeriodAccounts.setMnydebitperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnydebitperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnydebitcashed(accdebitsum);
                tglPeriodAccounts.setMnycreditcashed(acccreditsum);
                tglPeriodAccounts.setMnyfdebitcashed(accfdebitsum);
                tglPeriodAccounts.setMnyfcreditcashed(accfcreditsum);
                tglPeriodAccountsDao.newupdate(tglPeriodAccounts);
            } else if (tag == 3) {
                TglPeriodAccounts tglPeriodAccounts = new TglPeriodAccounts();
                tglPeriodAccounts.setUqcompanyid(uqcompanyid);
                tglPeriodAccounts.setUqglobalperiodid(uqglobalperiodid);
                TglAccounts tglAccounts = new TglAccounts();
                tglAccounts.setUqaccountid(uqaccountid);
                tglPeriodAccounts.setUqaccountid(tglAccounts);
                tglPeriodAccounts.setMnydebitperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnydebitperiod(accdebitsum);
                tglPeriodAccounts.setMnycreditperiod(acccreditsum);
                tglPeriodAccounts.setMnyfdebitperiod(accfdebitsum);
                tglPeriodAccounts.setMnyfcreditperiod(accfcreditsum);
                tglPeriodAccounts.setMnydebitcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditcashed(BigDecimal.valueOf(0));
                tglPeriodAccountsDao.newupdate(tglPeriodAccounts);
            }
        }

    }

    public void handlerLedgerPeriod(String voucherid, int tag, int flag) {
        //1.获取凭证分录的分组信息，按照科目，单位，会计期
        List<Map<String, Object>> list = null;

        if (tag == 2) {
            list = this.tglVoucherMainsDao.getLedgerPeriodCashInfo(voucherid, SessionUtils.getUser().getCompanyId());
        } else {
            list = this.tglVoucherMainsDao.getLedgerPeriodInfo(voucherid, SessionUtils.getUser().getCompanyId());
        }

        for (int i = 0; i < list.size(); i++) {
            //1.获取余额表所需信息
            Map<String, Object> obj = list.get(i);
            String uqaccountid = obj.get("uqaccountid").toString();
            String uqglobalperiodid = obj.get("uqglobalperiodid").toString();
            String uqcompanyid = obj.get("uqcompanyid").toString();
            String uqledgerid = obj.get("uqledgerid").toString();
            BigDecimal accdebitsum = new BigDecimal(obj.get("accdebitsum").toString());
            BigDecimal acccreditsum = new BigDecimal(obj.get("acccreditsum").toString());
            BigDecimal accfdebitsum = new BigDecimal(obj.get("accfdebitsum").toString());
            BigDecimal accfcreditsum = new BigDecimal(obj.get("accfcreditsum").toString());

            //2.锁掉余额表
            List<Map<String, Object>> lockList = tglVoucherMainsDao.lockLedgerPeriod(uqaccountid, uqglobalperiodid, uqcompanyid, uqledgerid);
            int lockline = lockList.size();

            //3.更新余额表
            updateLedgerPeriod(lockline, uqaccountid, uqglobalperiodid, uqcompanyid, uqledgerid,
                    accdebitsum, acccreditsum, accfdebitsum, accfcreditsum, tag, flag);
        }
    }

    public boolean isCash(String uqnumbering) {
        // 由科目性质判断改为根据凭证性质判断是否需要出纳
        if (uqnumbering.equals("00000000-0000-0000-0000-000000000001") || //现收
                uqnumbering.equals("00000000-0000-0000-0000-000000000002") || //现付
                uqnumbering.equals("00000000-0000-0000-0000-000000000003") || //银收
                uqnumbering.equals("00000000-0000-0000-0000-000000000004")) { // 银付
            return true;
        }
        return false;
    }

    /**
     * 这里是处理凭证edit修改的逻辑
     *
     * @param tglVoucherMains
     * @return
     */
    public TglVoucherMains edit(TglVoucherMains tglVoucherMains) {
        if (tglVoucherMains.getMnydebitsum().compareTo(tglVoucherMains.getMnycreditsum()) != 0) {
            throw new RuntimeException("借贷金额不等！！！");
        }
        StringHolder errMsg = new StringHolder();

        errMsg.value = "";
        String edituserid = SessionUtils.getUser().getId();
        SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dtfillersrv = simpleformat.format(date);
        tglVoucherMains.setDtfillersrv(dtfillersrv);

        boolean isedit = vouchercheckruleedit(tglVoucherMains.getDtfiller(), edituserid, tglVoucherMains.getUqvoucherid(), errMsg);
        if (isedit) {
            //删除老凭证的余额
            handlerAccountPeriod(tglVoucherMains.getUqvoucherid(), 1, -1);
            //删除老凭证的余额
            handlerLedgerPeriod(tglVoucherMains.getUqvoucherid(), 1, -1);
            /**
             * 处理非结转凭证余额计算
             */
            if (!tglVoucherMains.getUqnumbering().equals("CF10100D-ED35-4B1D-AAC7-235F68DFB117")) {
                handlerAccountPeriodNoJZ(tglVoucherMains.getUqvoucherid(), 1, -1);
            }
            //删除凭证分录
            deleteVoucherDetail(tglVoucherMains.getUqvoucherid());
            //删除该凭证往来信息
            tglVoucherMainsDao.deleteAc(tglVoucherMains.getUqvoucherid());

            //获取账期内凭证编号是否存在
            int tag = selectvouchernum(tglVoucherMains.getIntvouchernum(), tglVoucherMains.getUqglobalperiodid().getUqglobalperiodid(), tglVoucherMains.getUqvoucherid());
            if (tag > 0) {
                throw new RuntimeException("当前账期内凭证编号已存在相同编号，请核实后再操作！");
            }
            //update主表的制证日期，凭证字，会计期
            SysUser sysUser = new SysUser();
            sysUser.setId(edituserid);
            tglVoucherMains.setUqfillerid(sysUser);
            this.tglVoucherMainsDao.update(tglVoucherMains);

            List<TglVoucherDetails> tglVoucherDetailsList = tglVoucherMains.getTglVoucherDetailsList();

            //获取借贷余额数据
            String balance1001 = tglVoucherMainsDao.decideMoney(SessionUtils.getUser().getCompanyId(), "1001");
            String balance1002 = tglVoucherMainsDao.decideMoney(SessionUtils.getUser().getCompanyId(), "1002");
            BigDecimal newbalance1001 = new BigDecimal(0);
            BigDecimal newbalance1002 = new BigDecimal(0);
            //循环 分录进行数据处理 1获取借贷数据 2.赋值uuid
            for (TglVoucherDetails tglVoucherDetails : tglVoucherDetailsList) {
                if (tglVoucherDetails.getTglAccounts().getVaraccountcode().equals("1001")) {
                    newbalance1001.add(tglVoucherDetails.getMnydebit().subtract(tglVoucherDetails.getMnycredit()));
                }
                if (tglVoucherDetails.getTglAccounts().getVaraccountcode().equals("1002")) {
                    newbalance1002.add(tglVoucherDetails.getMnydebit().subtract(tglVoucherDetails.getMnycredit()));
                }
                tglVoucherDetails.setUqvoucherdetailid(UUID.randomUUID().toString().toUpperCase());
                List<TglLedgetype> tglledgetype = tglVoucherDetails.getTglledgetype();
                for (int ii = 0; ii < tglledgetype.size(); ii++) {
                    List<TglLedger> tglLedgerList = tglledgetype.get(ii).getTglLedgerList();
                    for (int i = 0; i < tglLedgerList.size(); i++) {
                        TglLedger tglLedger = tglLedgerList.get(i);
                        //这里存入分录分户表
                        TglVoucherDetailLedger tglVoucherDetailLedger = tglLedgerList.get(i).getTglVoucherDetailLedger();
                        tglVoucherDetailLedger.setUqvouledgerid(UUID.randomUUID().toString().toUpperCase());
                        tglVoucherDetailLedger.setUqvoucherdetailid(tglVoucherDetails.getUqvoucherdetailid());
                        tglVoucherDetailLedger.setIntseq(i + 1);
                        tglVoucherDetailLedger.setUqledgerid(tglLedger.getUqledgeid());
                        tglVoucherDetailLedger.setUqledgertypeid(tglledgetype.get(ii).getUqledgetypeid());
                        tglVoucherDetailLedgerDao.insert(tglVoucherDetailLedger);
                        //这里存入分录分户类别表 做关联关系表 - -有一说一这个表真可以淦掉
                        TglVoucherDetailLedgertype tglVoucherDetailLedgertype = new TglVoucherDetailLedgertype();
                        tglVoucherDetailLedgertype.setIntseq(i + 1);
                        tglVoucherDetailLedgertype.setUqvouledgertypeid(UUID.randomUUID().toString().toUpperCase());
                        tglVoucherDetailLedgertype.setUqledgertypeid(tglledgetype.get(ii).getUqledgetypeid());
                        tglVoucherDetailLedgertype.setUqvoucherdetailid(tglVoucherDetails.getUqvoucherdetailid());
                        tglVoucherDetailLedgertype.setUqvoucherid(tglVoucherMains.getUqvoucherid());
                        tglVoucherDetailLedgertypeDao.insert(tglVoucherDetailLedgertype);
                        //这里处理往来可以用 -- 这里先不做
                    }
                }
            }
            //判断对比科目是否有余额
            // TODO: 8/5/2023 只做提醒不做原逻辑修改 这里现金和银行科目 1001和1002不一定是末级科目 不能确定到具体科目是否有余额 只能确定 一级有
            if (new BigDecimal(balance1001).compareTo(newbalance1001) < 0) {
                throw new RuntimeException("科目1001余额不足");
            }
            if (new BigDecimal(balance1002).compareTo(newbalance1002) < 0) {
                throw new RuntimeException("科目1002余额不足");
            }
            //保存凭证分录数据
            tglVoucherDetailsDao.bulkInsert(tglVoucherMains.getTglVoucherDetailsList());

            //再次插入余额
            handlerAccountPeriod(tglVoucherMains.getUqvoucherid(), 1, 1);

            //删除老凭证的余额 这尼玛不是添加新凭证余额嘛 这是删除？？？？
            handlerLedgerPeriod(tglVoucherMains.getUqvoucherid(), 1, 1);
            // 现金流量编制表更新凭证
            tglVoucherMainsDao.deleteFlow(tglVoucherMains.getUqvoucherid());
            //现金流量编制保存
            for (TglVoucherDetails tglVoucherDetails : tglVoucherMains.getTglVoucherDetailsList()) {
                TglAccountsXjll tglAccountsXjll = tglVoucherDetails.getTglAccountsXjll();
                if (tglAccountsXjll != null) {
                    tglAccountsXjll.setUqvoucherdetailId(tglVoucherDetails.getUqvoucherdetailid());
                    tglAccountsXjll.setTenantId(SessionUtils.getUser().getCompanyId());
                    tglAccountsXjllDao.insert(tglAccountsXjll);
                }
            }
            if (!tglVoucherMains.getUqnumbering().equals("CF10100D-ED35-4B1D-AAC7-235F68DFB117")) {
                handlerAccountPeriodNoJZ(tglVoucherMains.getUqvoucherid(), 1, 1);
            }

        }


        return tglVoucherMains;
    }

    public int selectvouchernum(String intvouchernum, String uqglobalperiodid, String UQVOUCHERID) {
        TglVoucherMains tglVoucherMains = get(UQVOUCHERID);
        if (!StringUtils.isBlank(tglVoucherMains.getUqvoucherid())) {
            return 0;
        }
        int selectvouchernum = tglVoucherMainsDao.selectvouchernum(intvouchernum, uqglobalperiodid, UQVOUCHERID);
        return selectvouchernum;
    }

    public void deleteVoucherDetail(String voucherid) {
        try {
            this.tglVoucherMainsDao.deleteVoucherDetail1(voucherid);
            this.tglVoucherMainsDao.deleteVoucherDetail2(voucherid);
            this.tglVoucherMainsDao.deleteVoucherDetail3(voucherid);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean lockVoucher(String voucherid, String tag) {
        List<Map<String, Object>> maps = this.tglVoucherMainsDao.lockVoucher(voucherid, SessionUtils.getUser().getCompanyId());
        if (maps.size() > 0) {
            Map<String, Object> stringObjectMap = maps.get(0);
            String intflag = stringObjectMap.get("intflag").toString();
            String intcashflag = stringObjectMap.get("intcashflag").toString();
            String intdeleteflag = stringObjectMap.get("intdeleteflag").toString();

            if ("1".equals(tag) && "0".equals(intflag) && "0".equals(intdeleteflag)) {
                //审核
                return true;
            } else if ("2".equals(tag) && "1".equals(intcashflag)) {
                //出纳
                return true;
            } else if ("3".equals(tag) && "1".equals(intflag)) {
                //记账
                return true;
            } else if ("4".equals(tag) && "1".equals(intflag) && !"2".equals(intcashflag)) {
                //反审核
                return true;
            } else if ("5".equals(tag) && "1".equals(intflag) && "2".equals(intcashflag)) {
                //反出纳
                return true;
            } else if ("6".equals(tag) && "2".equals(intflag) && ("2".equals(intcashflag)||"0".equals(intcashflag))) {
            //反计账
            return true;
            }  else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean vouchercheckruleedit(String dtfiller, String userid, String voucherid, StringHolder errMsg) {
        //1.锁表，查询状态
        boolean isedit = lockVoucher(voucherid, "1");

        TglVoucherMains mainlist = get(voucherid);

        if (StringUtils.isBlank(mainlist.getUqvoucherid())) {
            throw new RuntimeException("没有找到凭证，请联系管理员！");
        }

        TglVoucherMains main = mainlist;

        main.setDtfiller(dtfiller);

        if (!isedit) {
            errMsg.value += "流水号为" + main.getIntcompanyseq() + "的凭证不能修改!";
            return false;
        }

//		//按照当前年月获取启用的会计期
//		Object[] period = voumaindao.getPeriodInfoByID(main.getUqglobalperiodid());
//
//		if(period != null)
//		{
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			Date date = sdf.parse(main.getDtfiller());
//
//			if(!(date.compareTo(sdf.parse(period[4].toString())) <= 0 && date.compareTo(sdf.parse(period[3].toString())) >= 0))
//			{
//				errMsg.value += "该凭证的制证日期必须在会计期起止时间内!";
//				return false;
//			}
//		}

//        //2.校验制证人是不是自己
//        if (main.getUqfillerid().compareTo(userid) != 0) {
//            errMsg.value += "流水号为" + main.getIntcompanyseq() + "的凭证只能由制证人修改!";
//            return false;
//        }

        return true;
    }

    public static void main(String[] args) {

        String strSql = "";
        strSql += " select d.uqaccountid,m.uqglobalperiodid,m.uqcompanyid,";
        strSql += " sum(d.mnydebit) as accdebitsum,sum(d.mnycredit) as acccreditsum,";
        strSql += " sum(ifnull(d.mnyfdebit,0)) as accfdebitsum,sum(ifnull(d.mnyfcredit,0)) as accfcreditsum";
        strSql += "  from tgl_voucher_details d";
        strSql += " inner join tgl_voucher_mains m on m.uqvoucherid=d.uqvoucherid and m.tenant_id=?";
        strSql += " inner join tgl_accounts gl on gl.uqaccountid = d.uqaccountid and gl.tenant_id=?";
        strSql += " where m.uqvoucherid= ? ";
        strSql += " group by d.uqaccountid,m.uqglobalperiodid,m.uqcompanyid ";
        strSql += " order by gl.varaccountcode";
        System.out.println(strSql);
    }

    public void updateLedgerPeriod(int lockline, String uqaccountid, String uqglobalperiodid,
                                   String uqcompanyid, String uqledgeid, BigDecimal accdebitsum, BigDecimal acccreditsum,
                                   BigDecimal accfdebitsum, BigDecimal accfcreditsum, int tag, int flag) {
        String strSql = " ";

        accdebitsum = accdebitsum.multiply(new BigDecimal(flag));
        acccreditsum = acccreditsum.multiply(new BigDecimal(flag));
        accfdebitsum = accfdebitsum.multiply(new BigDecimal(flag));
        accfcreditsum = accfcreditsum.multiply(new BigDecimal(flag));

        if (lockline <= 0) {
            //不存在科目余额数据

            CompanyLedgerPeriods companyLedgerPeriods = new CompanyLedgerPeriods();
            Company company = new Company();
            company.setId(uqcompanyid);
            companyLedgerPeriods.setUqcompanyid(company);

            GlobalPeriods globalPeriods = new GlobalPeriods();
            globalPeriods.setUqglobalperiodid(uqglobalperiodid);

            companyLedgerPeriods.setUqglobalperiodid(globalPeriods);

            TglAccounts tglAccounts = new TglAccounts();
            tglAccounts.setUqaccountid(uqaccountid);
            companyLedgerPeriods.setUqaccountid(tglAccounts);

            TglLedger tglLedger = new TglLedger();
            tglLedger.setUqledgeid(uqledgeid);
            companyLedgerPeriods.setUqledgeid(tglLedger);
            companyLedgerPeriods.setMnydebitperiodall(accdebitsum);
            companyLedgerPeriods.setMnycreditperiodall(acccreditsum);
            companyLedgerPeriods.setMnyfdebitperiodall(accfdebitsum);
            companyLedgerPeriods.setMnyfcreditperiodall(accfcreditsum);


            companyLedgerPeriods.setMnydebitperiod(BigDecimal.valueOf(0));
            companyLedgerPeriods.setMnycreditperiod(BigDecimal.valueOf(0));
            companyLedgerPeriods.setMnyfdebitperiod(BigDecimal.valueOf(0));
            companyLedgerPeriods.setMnyfcreditperiod(BigDecimal.valueOf(0));

            companyLedgerPeriods.setMnydebitcashed(BigDecimal.valueOf(0));
            companyLedgerPeriods.setMnycreditcashed(BigDecimal.valueOf(0));
            companyLedgerPeriods.setMnyfdebitcashed(BigDecimal.valueOf(0));
            companyLedgerPeriods.setMnyfcreditcashed(BigDecimal.valueOf(0));

            companyLedgerPeriods.setId(IdGen.uuid());
            companyLedgerPeriodsDao.insert(companyLedgerPeriods);


        } else {

            if (tag == 1) {
                CompanyLedgerPeriods companyLedgerPeriods = new CompanyLedgerPeriods();
                Company company = new Company();
                company.setId(uqcompanyid);
                companyLedgerPeriods.setUqcompanyid(company);

                GlobalPeriods globalPeriods = new GlobalPeriods();
                globalPeriods.setUqglobalperiodid(uqglobalperiodid);

                companyLedgerPeriods.setUqglobalperiodid(globalPeriods);

                TglAccounts tglAccounts = new TglAccounts();
                tglAccounts.setUqaccountid(uqaccountid);
                companyLedgerPeriods.setUqaccountid(tglAccounts);

                TglLedger tglLedger = new TglLedger();
                tglLedger.setUqledgeid(uqledgeid);
                companyLedgerPeriods.setUqledgeid(tglLedger);
                companyLedgerPeriods.setMnydebitperiodall(accdebitsum);
                companyLedgerPeriods.setMnycreditperiodall(acccreditsum);
                companyLedgerPeriods.setMnyfdebitperiodall(accfdebitsum);
                companyLedgerPeriods.setMnyfcreditperiodall(accfcreditsum);


                companyLedgerPeriods.setMnydebitperiod(BigDecimal.valueOf(0));
                companyLedgerPeriods.setMnycreditperiod(BigDecimal.valueOf(0));
                companyLedgerPeriods.setMnyfdebitperiod(BigDecimal.valueOf(0));
                companyLedgerPeriods.setMnyfcreditperiod(BigDecimal.valueOf(0));

                companyLedgerPeriods.setMnydebitcashed(BigDecimal.valueOf(0));
                companyLedgerPeriods.setMnycreditcashed(BigDecimal.valueOf(0));
                companyLedgerPeriods.setMnyfdebitcashed(BigDecimal.valueOf(0));
                companyLedgerPeriods.setMnyfcreditcashed(BigDecimal.valueOf(0));


                companyLedgerPeriodsDao.newupdate(companyLedgerPeriods);

            } else if (tag == 2) {


                CompanyLedgerPeriods companyLedgerPeriods = new CompanyLedgerPeriods();
                Company company = new Company();
                company.setId(uqcompanyid);
                companyLedgerPeriods.setUqcompanyid(company);

                GlobalPeriods globalPeriods = new GlobalPeriods();
                globalPeriods.setUqglobalperiodid(uqglobalperiodid);

                companyLedgerPeriods.setUqglobalperiodid(globalPeriods);

                TglAccounts tglAccounts = new TglAccounts();
                tglAccounts.setUqaccountid(uqaccountid);
                companyLedgerPeriods.setUqaccountid(tglAccounts);

                TglLedger tglLedger = new TglLedger();
                tglLedger.setUqledgeid(uqledgeid);
                companyLedgerPeriods.setUqledgeid(tglLedger);
                companyLedgerPeriods.setMnydebitperiodall(BigDecimal.valueOf(0));
                companyLedgerPeriods.setMnycreditperiodall(BigDecimal.valueOf(0));
                companyLedgerPeriods.setMnyfdebitperiodall(BigDecimal.valueOf(0));
                companyLedgerPeriods.setMnyfcreditperiodall(BigDecimal.valueOf(0));


                companyLedgerPeriods.setMnydebitperiod(BigDecimal.valueOf(0));
                companyLedgerPeriods.setMnycreditperiod(BigDecimal.valueOf(0));
                companyLedgerPeriods.setMnyfdebitperiod(BigDecimal.valueOf(0));
                companyLedgerPeriods.setMnyfcreditperiod(BigDecimal.valueOf(0));

                companyLedgerPeriods.setMnydebitcashed(accdebitsum);
                companyLedgerPeriods.setMnycreditcashed(acccreditsum);
                companyLedgerPeriods.setMnyfdebitcashed(accfdebitsum);
                companyLedgerPeriods.setMnyfcreditcashed(accfcreditsum);


                companyLedgerPeriodsDao.newupdate(companyLedgerPeriods);


            } else if (tag == 3) {


                CompanyLedgerPeriods companyLedgerPeriods = new CompanyLedgerPeriods();
                Company company = new Company();
                company.setId(uqcompanyid);
                companyLedgerPeriods.setUqcompanyid(company);

                GlobalPeriods globalPeriods = new GlobalPeriods();
                globalPeriods.setUqglobalperiodid(uqglobalperiodid);

                companyLedgerPeriods.setUqglobalperiodid(globalPeriods);

                TglAccounts tglAccounts = new TglAccounts();
                tglAccounts.setUqaccountid(uqaccountid);
                companyLedgerPeriods.setUqaccountid(tglAccounts);

                TglLedger tglLedger = new TglLedger();
                tglLedger.setUqledgeid(uqledgeid);
                companyLedgerPeriods.setUqledgeid(tglLedger);
                companyLedgerPeriods.setMnydebitperiodall(BigDecimal.valueOf(0));
                companyLedgerPeriods.setMnycreditperiodall(BigDecimal.valueOf(0));
                companyLedgerPeriods.setMnyfdebitperiodall(BigDecimal.valueOf(0));
                companyLedgerPeriods.setMnyfcreditperiodall(BigDecimal.valueOf(0));


                companyLedgerPeriods.setMnydebitperiod(accdebitsum);
                companyLedgerPeriods.setMnycreditperiod(acccreditsum);
                companyLedgerPeriods.setMnyfdebitperiod(accfdebitsum);
                companyLedgerPeriods.setMnyfcreditperiod(accfcreditsum);

                companyLedgerPeriods.setMnydebitcashed(BigDecimal.valueOf(0));
                companyLedgerPeriods.setMnycreditcashed(BigDecimal.valueOf(0));
                companyLedgerPeriods.setMnyfdebitcashed(BigDecimal.valueOf(0));
                companyLedgerPeriods.setMnyfcreditcashed(BigDecimal.valueOf(0));


                companyLedgerPeriodsDao.newupdate(companyLedgerPeriods);


            }
        }
    }



    public int delete(List<String> entitys) {
        for (int i = 0; i < entitys.size(); i++) {
            String voucherid = entitys.get(i);
            unvouchersave(voucherid);
        }
        return 1;
    }

    private boolean vouchercheckruleunsave(String userid, String voucherid) {
        //1.锁表，查询状态
        boolean isedit = lockVoucher(voucherid, "1");

        TglVoucherMains mainlist = get(voucherid);


        if (StringUtils.isBlank(mainlist.getUqvoucherid())) {
            throw new RuntimeException("没有找到凭证，请联系管理员！");
        }

        if (!isedit) {
            throw new RuntimeException(mainlist.getIntvouchernum()+"  已审核不能删除!");
        }
//   放开权限
//        //2.校验制证人是不是自己
//        if (main.getUqfillerid().compareTo(userid) != 0) {
//            errMsg.value += "流水号为" + main.getIntcompanyseq() + "的凭证只能由制证人删除!";
//            return false;
//        }

        return true;
    }

    public void handlerPeriodAccount(String voucherid, int tag, int flag, String uqtypeid) {
        //1.获取凭证分录的分组信息，按照科目，单位，会计期
        List<Map<String, Object>> list = null;

        if (tag == 2) {
            list = this.tglVoucherMainsDao.getAccountPeriodCashInfo(voucherid, SessionUtils.getUser().getCompanyId());
        } else {
            list = this.tglVoucherMainsDao.findAccountPeriodInfo(voucherid, uqtypeid, SessionUtils.getUser().getCompanyId());
        }


        for (int i = 0; i < list.size(); i++) {
            //1.获取余额表所需信息
            Map<String, Object> obj = list.get(i);
            String uqaccountid = obj.get("uqaccountid").toString();
            String uqglobalperiodid = obj.get("uqglobalperiodid").toString();
            String uqcompanyid = obj.get("uqcompanyid").toString();
            BigDecimal accdebitsum = new BigDecimal(obj.get("accdebitsum").toString());
            BigDecimal acccreditsum = new BigDecimal(obj.get("acccreditsum").toString());
            BigDecimal accfdebitsum = new BigDecimal(obj.get("accfdebitsum").toString());
            BigDecimal accfcreditsum = new BigDecimal(obj.get("accfcreditsum").toString());
            //2.锁掉余额表
            List<Map<String, Object>> lockList = this.tglVoucherMainsDao.lockAccountPeriod(uqaccountid, uqglobalperiodid, uqcompanyid);
            int lockline = lockList.size();

            //3.更新余额表
            supdateAccountPeriod(lockline, uqaccountid, uqglobalperiodid, uqcompanyid,
                    accdebitsum, acccreditsum, accfdebitsum, accfcreditsum, tag, flag);

        }
    }

    /**
     * @param lockline
     * @param uqaccountid
     * @param uqglobalperiodid
     * @param uqcompanyid
     * @param accdebitsum
     * @param acccreditsum
     * @param tag              1-制证,2-出纳，3-记账
     * @param flag             传递正负1
     *                         制证传递tag1,flag1,查删改删除凭证传递tag1,flag-1,出纳tag2,flag1,反出纳tag2,flag-1,记账tag3,flag1
     */
    public void supdateAccountPeriod(int lockline, String uqaccountid, String uqglobalperiodid,
                                     String uqcompanyid, BigDecimal accdebitsum, BigDecimal acccreditsum,
                                     BigDecimal accfdebitsum, BigDecimal accfcreditsum, int tag, int flag) {

        String sqlStr = "";

        accdebitsum = accdebitsum.multiply(new BigDecimal(flag));
        acccreditsum = acccreditsum.multiply(new BigDecimal(flag));
        accfdebitsum = accfdebitsum.multiply(new BigDecimal(flag));
        accfcreditsum = accfcreditsum.multiply(new BigDecimal(flag));

        if (lockline <= 0) {
            //不存在科目余额数据

            TglPeriodAccounts tglPeriodAccounts = new TglPeriodAccounts();

            tglPeriodAccounts.setUqcompanyid(uqcompanyid);
            tglPeriodAccounts.setUqglobalperiodid(uqglobalperiodid);
            TglAccounts tglAccounts = new TglAccounts();
            tglAccounts.setUqaccountid(uqaccountid);
            tglPeriodAccounts.setUqaccountid(tglAccounts);

            tglPeriodAccounts.setMnydebitperiodall(accdebitsum);
            tglPeriodAccounts.setMnycreditperiodall(acccreditsum);
            tglPeriodAccounts.setMnyfdebitperiodall(accfdebitsum);
            tglPeriodAccounts.setMnyfcreditperiodall(accfcreditsum);

            tglPeriodAccounts.setMnydebitperiod(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnycreditperiod(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnyfdebitperiod(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnyfcreditperiod(BigDecimal.valueOf(0));

            tglPeriodAccounts.setMnydebitcashed(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnycreditcashed(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnyfdebitcashed(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnyfcreditcashed(BigDecimal.valueOf(0));

            tglPeriodAccountsDao.insert(tglPeriodAccounts);


        } else {


            if (tag == 1) {


                TglPeriodAccounts tglPeriodAccounts = new TglPeriodAccounts();

                tglPeriodAccounts.setUqcompanyid(uqcompanyid);
                tglPeriodAccounts.setUqglobalperiodid(uqglobalperiodid);
                TglAccounts tglAccounts = new TglAccounts();
                tglAccounts.setUqaccountid(uqaccountid);
                tglPeriodAccounts.setUqaccountid(tglAccounts);

                tglPeriodAccounts.setMnydebitperiodall(accdebitsum);
                tglPeriodAccounts.setMnycreditperiodall(acccreditsum);
                tglPeriodAccounts.setMnyfdebitperiodall(accfdebitsum);
                tglPeriodAccounts.setMnyfcreditperiodall(accfcreditsum);

                tglPeriodAccounts.setMnydebitperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditperiod(BigDecimal.valueOf(0));

                tglPeriodAccounts.setMnydebitcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditcashed(BigDecimal.valueOf(0));

                tglPeriodAccountsDao.newupdate(tglPeriodAccounts);

            } else if (tag == 2) {
                TglPeriodAccounts tglPeriodAccounts = new TglPeriodAccounts();

                tglPeriodAccounts.setUqcompanyid(uqcompanyid);
                tglPeriodAccounts.setUqglobalperiodid(uqglobalperiodid);
                TglAccounts tglAccounts = new TglAccounts();
                tglAccounts.setUqaccountid(uqaccountid);
                tglPeriodAccounts.setUqaccountid(tglAccounts);

                tglPeriodAccounts.setMnydebitperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditperiodall(BigDecimal.valueOf(0));

                tglPeriodAccounts.setMnydebitperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditperiod(BigDecimal.valueOf(0));

                tglPeriodAccounts.setMnydebitcashed(accdebitsum);
                tglPeriodAccounts.setMnycreditcashed(acccreditsum);
                tglPeriodAccounts.setMnyfdebitcashed(accfdebitsum);
                tglPeriodAccounts.setMnyfcreditcashed(accfcreditsum);

                tglPeriodAccountsDao.newupdate(tglPeriodAccounts);


            } else if (tag == 3) {
                TglPeriodAccounts tglPeriodAccounts = new TglPeriodAccounts();

                tglPeriodAccounts.setUqcompanyid(uqcompanyid);
                tglPeriodAccounts.setUqglobalperiodid(uqglobalperiodid);
                TglAccounts tglAccounts = new TglAccounts();
                tglAccounts.setUqaccountid(uqaccountid);
                tglPeriodAccounts.setUqaccountid(tglAccounts);

                tglPeriodAccounts.setMnydebitperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditperiodall(BigDecimal.valueOf(0));

                tglPeriodAccounts.setMnydebitperiod(accdebitsum);
                tglPeriodAccounts.setMnycreditperiod(acccreditsum);
                tglPeriodAccounts.setMnyfdebitperiod(accfdebitsum);
                tglPeriodAccounts.setMnyfcreditperiod(accfcreditsum);

                tglPeriodAccounts.setMnydebitcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditcashed(BigDecimal.valueOf(0));

                tglPeriodAccountsDao.newupdate(tglPeriodAccounts);
            }

        }
    }

    public void unvouchersave(String voucherid) {
        //查询凭证分录，删除余额

        String unvouchersaveuserid = SessionUtils.getUser().getId();
        //获取删除的凭证是否为结转凭证
        TglVoucherMains tglVoucherMains = get(voucherid);
        String uqnumbering = tglVoucherMains.getUqnumbering();
        boolean isunsave = vouchercheckruleunsave(unvouchersaveuserid, voucherid);
        if (isunsave) {
            //结转凭证删除逻辑
            if (uqnumbering.equals("CF10100D-ED35-4B1D-AAC7-235F68DFB117")) {
                //处理费用类
                handlerPeriodAccount(voucherid, 1, -1, "9");//操作tgl_period_accounts
                //处理收入类
                handlerPeriodAccounts(voucherid, 1, -1, "10");
            } else {
                deletePeriodAccount(voucherid, 1, -1);
            }
            handlerLedgerPeriod(voucherid, 1, -1);// 分户操作

//            this.voucherDetailBP.delTglperiodAccount(voucherid);
            //新增-出纳增加写入非结转凭证


            TglVoucherMains mainlist = get(voucherid);

            if (StringUtils.isBlank(mainlist.getUqvoucherid())) {
                throw new RuntimeException("没有找到凭证，请联系管理员！");
            }


            tglVoucherMainsDao.deleteFlow(voucherid);

            /**
             * 处理非结转凭证余额计算
             */

            if (!mainlist.getUqnumbering().equals("CF10100D-ED35-4B1D-AAC7-235F68DFB117")) {
                handlerAccountPeriodNoJZ(voucherid, 1, -1);
            }

            int mdom = (int) ((Math.random() * 9 + 1) * 10000);
            //update主表的删除标志
            this.tglVoucherMainsDao.delVoucher("_" + mdom, voucherid,SessionUtils.getUser().getCompanyId());

            //删除该凭证往来信息
            this.tglVoucherMainsDao.deleteAc(voucherid);
        }
    }

    public void deletePeriodAccount(String voucherid, int tag, int flag) {
        //1.获取凭证分录的分组信息，按照科目，单位，会计期
        List<Map<String, Object>> list = null;

        if (tag == 2) {
            list = this.tglVoucherMainsDao.getAccountPeriodCashInfo(voucherid, SessionUtils.getUser().getCompanyId());
        } else {
            list = this.tglVoucherMainsDao.selectAccountPeriodInfo(voucherid, SessionUtils.getUser().getCompanyId());
        }


        for (int i = 0; i < list.size(); i++) {
            //1.获取余额表所需信息

            Map<String, Object> obj = list.get(i);
            String uqaccountid = obj.get("uqaccountid").toString();
            String uqglobalperiodid = obj.get("uqglobalperiodid").toString();
            String uqcompanyid = obj.get("uqcompanyid").toString();
            BigDecimal accdebitsum = new BigDecimal(obj.get("accdebitsum").toString());
            BigDecimal acccreditsum = new BigDecimal(obj.get("acccreditsum").toString());
            BigDecimal accfdebitsum = new BigDecimal(obj.get("accfdebitsum").toString());
            BigDecimal accfcreditsum = new BigDecimal(obj.get("accfcreditsum").toString());

            //2.锁掉余额表
            List<Map<String, Object>> lockList = this.tglVoucherMainsDao.lockAccountPeriod(uqaccountid, uqglobalperiodid, uqcompanyid);
            int lockline = lockList.size();

            //3.更新余额表
            delectAccountPeriod(lockline, uqaccountid, uqglobalperiodid, uqcompanyid,
                    accdebitsum, acccreditsum, accfdebitsum, accfcreditsum, tag, flag);

        }
    }

    public void delectAccountPeriod(int lockline, String uqaccountid, String uqglobalperiodid, String uqcompanyid, BigDecimal accdebitsum, BigDecimal acccreditsum, BigDecimal accfdebitsum, BigDecimal accfcreditsum, int tag, int flag) {

        String strSql = " ";
        accdebitsum = accdebitsum.multiply(new BigDecimal(flag));
        acccreditsum = acccreditsum.multiply(new BigDecimal(flag));
        accfdebitsum = accfdebitsum.multiply(new BigDecimal(flag));
        accfcreditsum = accfcreditsum.multiply(new BigDecimal(flag));
        //获取凭证发生数表数据是否为负，制证时借贷方是否有负金额存在
        if (lockline <= 0) {
            //不存在科目余额数据

            TglPeriodAccounts tglPeriodAccounts = new TglPeriodAccounts();

            tglPeriodAccounts.setUqcompanyid(uqcompanyid);
            tglPeriodAccounts.setUqglobalperiodid(uqglobalperiodid);
            TglAccounts tglAccounts = new TglAccounts();
            tglAccounts.setUqaccountid(uqaccountid);
            tglPeriodAccounts.setUqaccountid(tglAccounts);

            tglPeriodAccounts.setMnydebitperiodall(accdebitsum);
            tglPeriodAccounts.setMnycreditperiodall(acccreditsum);
            tglPeriodAccounts.setMnyfdebitperiodall(accfdebitsum);
            tglPeriodAccounts.setMnyfcreditperiodall(accfcreditsum);

            tglPeriodAccounts.setMnydebitperiod(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnycreditperiod(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnyfdebitperiod(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnyfcreditperiod(BigDecimal.valueOf(0));

            tglPeriodAccounts.setMnydebitcashed(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnycreditcashed(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnyfdebitcashed(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnyfcreditcashed(BigDecimal.valueOf(0));

            tglPeriodAccountsDao.insert(tglPeriodAccounts);


        } else {


            if (tag == 1) {

                TglPeriodAccounts tglPeriodAccounts = new TglPeriodAccounts();

                tglPeriodAccounts.setUqcompanyid(uqcompanyid);
                tglPeriodAccounts.setUqglobalperiodid(uqglobalperiodid);
                TglAccounts tglAccounts = new TglAccounts();
                tglAccounts.setUqaccountid(uqaccountid);
                tglPeriodAccounts.setUqaccountid(tglAccounts);

                tglPeriodAccounts.setMnydebitperiodall(accdebitsum);
                tglPeriodAccounts.setMnycreditperiodall(acccreditsum);
                tglPeriodAccounts.setMnyfdebitperiodall(accfdebitsum);
                tglPeriodAccounts.setMnyfcreditperiodall(accfcreditsum);

                tglPeriodAccounts.setMnydebitperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditperiod(BigDecimal.valueOf(0));

                tglPeriodAccounts.setMnydebitcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditcashed(BigDecimal.valueOf(0));

                tglPeriodAccountsDao.newupdate(tglPeriodAccounts);


            } else if (tag == 2) {

                TglPeriodAccounts tglPeriodAccounts = new TglPeriodAccounts();

                tglPeriodAccounts.setUqcompanyid(uqcompanyid);
                tglPeriodAccounts.setUqglobalperiodid(uqglobalperiodid);
                TglAccounts tglAccounts = new TglAccounts();
                tglAccounts.setUqaccountid(uqaccountid);
                tglPeriodAccounts.setUqaccountid(tglAccounts);

                tglPeriodAccounts.setMnydebitperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditperiodall(BigDecimal.valueOf(0));

                tglPeriodAccounts.setMnydebitperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditperiod(BigDecimal.valueOf(0));

                tglPeriodAccounts.setMnydebitcashed(accdebitsum);
                tglPeriodAccounts.setMnycreditcashed(acccreditsum);
                tglPeriodAccounts.setMnyfdebitcashed(accfdebitsum);
                tglPeriodAccounts.setMnyfcreditcashed(accfcreditsum);

                tglPeriodAccountsDao.newupdate(tglPeriodAccounts);


            } else if (tag == 3) {


                TglPeriodAccounts tglPeriodAccounts = new TglPeriodAccounts();

                tglPeriodAccounts.setUqcompanyid(uqcompanyid);
                tglPeriodAccounts.setUqglobalperiodid(uqglobalperiodid);
                TglAccounts tglAccounts = new TglAccounts();
                tglAccounts.setUqaccountid(uqaccountid);
                tglPeriodAccounts.setUqaccountid(tglAccounts);

                tglPeriodAccounts.setMnydebitperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditperiodall(BigDecimal.valueOf(0));

                tglPeriodAccounts.setMnydebitperiod(accdebitsum);
                tglPeriodAccounts.setMnycreditperiod(acccreditsum);
                tglPeriodAccounts.setMnyfdebitperiod(accfdebitsum);
                tglPeriodAccounts.setMnyfcreditperiod(accfcreditsum);

                tglPeriodAccounts.setMnydebitcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditcashed(BigDecimal.valueOf(0));
                tglPeriodAccountsDao.newupdate(tglPeriodAccounts);
            }
        }

    }

    //收入类
    public void handlerPeriodAccounts(String voucherid, int tag, int flag, String uqtypeid) {
        //1.获取凭证分录的分组信息，按照科目，单位，会计期
        List<Map<String, Object>> list = null;

        if (tag == 2) {
            list = this.tglVoucherMainsDao.getAccountPeriodCashInfo(voucherid, SessionUtils.getUser().getCompanyId());
        } else {
            list = this.tglVoucherMainsDao.findAccountPeriodInfos(voucherid, uqtypeid, SessionUtils.getUser().getCompanyId());
        }

        //  收入类结转   贷：本年利润   如果在借  则为特殊凭证  特殊处理
        for (int i = 0; i < list.size(); i++) {
            //1.获取余额表所需信息
            BigDecimal accdebitsum = new BigDecimal(0);
            BigDecimal acccreditsum = new BigDecimal(0);
            Map<String, Object> obj = list.get(i);
            String uqaccountid = obj.get("uqaccountid").toString();
            String uqglobalperiodid = obj.get("uqglobalperiodid").toString();
            String uqcompanyid = obj.get("uqcompanyid").toString();
            accdebitsum = new BigDecimal(obj.get("accdebitsum").toString());
            acccreditsum = new BigDecimal(obj.get("acccreditsum").toString());
            BigDecimal accfdebitsum = new BigDecimal(obj.get("accfdebitsum").toString());
            BigDecimal accfcreditsum = new BigDecimal(obj.get("accfcreditsum").toString());


            //2.锁掉余额表
            List<Map<String, Object>> lockList = this.tglVoucherMainsDao.lockAccountPeriod(uqaccountid, uqglobalperiodid, uqcompanyid);
            int lockline = lockList.size();

            //3.更新余额表
            supdateAccountPeriod(lockline, uqaccountid, uqglobalperiodid, uqcompanyid,
                    accdebitsum, acccreditsum, accfdebitsum, accfcreditsum, tag, flag);

        }
    }

    /**
     * 批量删除
     *
     * @param tglVoucherMainsList
     */
    @Override
    public int bulkDelete(List<TglVoucherMains> tglVoucherMainsList) {
        for (TglVoucherMains tglVoucherMains : tglVoucherMainsList) {
            delete(tglVoucherMains);
        }

        return tglVoucherMainsList.size();
    }

    /**
     * 生成操作日志
     *
     * @param actionTypeId 操作类型Id
     * @param entity       操作的实体对象
     * @return
     */
    @Override
    protected Action createAction(String actionTypeId, TglVoucherMains entity) {
        Action action = super.createAction(actionTypeId, entity);
        if (action == null) {
            return null;
        }
        // 删除时记录把保存的数据保存到回收站
        if (ActionConstants.ACTION_DELETED.equals(actionTypeId)) {
//            for (TglGlobalPeriods child : entity.getTglGlobalPeriodsList()) {
//                ActionRecycle recycle = new ActionRecycle();
//                recycle.setTableName(child.getBusTableName());
//                recycle.setObjectId(child.getId());
//                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
//                action.getActionRecycleList().add(recycle);
//            }
        }
        return action;
    }

    /* 保存子表数据     会计期 */
//    private void saveTglGlobalPeriodsList(TglVoucherMains tglVoucherMains) {
//        List<Parameter> params = Lists.newArrayList();
//
//        params.add(new Parameter("UQGLOBALPERIODID", "=", tglVoucherMains.getId()));
//        PageRequest pageRequest = new PageRequest(params);
//        List<TglGlobalPeriods> list_TglGlobalPeriods = tglGlobalPeriodsDao.listAll(pageRequest);
//
//        List<TglGlobalPeriods> deletes = Lists.newArrayList(); // 删除列表
//        List<TglGlobalPeriods> inserts = Lists.newArrayList(); // 添加列表
//        List<TglGlobalPeriods> updates = Lists.newArrayList(); // 更新列表
//        for (TglGlobalPeriods tglGlobalPeriodsSaved : list_TglGlobalPeriods) {
//            boolean found = false;
//            for (TglGlobalPeriods tglGlobalPeriods : tglVoucherMains.getTglGlobalPeriodsList()) {
//                if (tglGlobalPeriodsSaved.getId().equals(tglGlobalPeriods.getId())) {
//                    found = true;
//                    break;
//                }
//            }
//            if (!found) {
//                deletes.add(tglGlobalPeriodsSaved);
//            }
//        }
//        if (deletes.size() > 0) {
//            tglGlobalPeriodsService.bulkDelete(deletes);
//        }
//        for (TglGlobalPeriods tglGlobalPeriods : tglVoucherMains.getTglGlobalPeriodsList()) {
//            if (StringUtils.isBlank(tglGlobalPeriods.getId())) {
//                tglGlobalPeriods.setUqglobalperiodid(tglGlobalPeriods.getUqglobalperiodid());
//
//                inserts.add(tglGlobalPeriods);
//            } else {
//                updates.add(tglGlobalPeriods);
//            }
//        }
//        if (updates.size() > 0) {
//            tglGlobalPeriodsService.bulkUpdate(updates);
//        }
//        if (inserts.size() > 0) {
//            tglGlobalPeriodsService.bulkInsert(inserts);
//        }
//    }


    /* 保存子表数据     凭证明细表 */
    private void saveTglVoucherDetailsList(TglVoucherMains tglVoucherMains) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("UQVOUCHERID", "=", tglVoucherMains.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TglVoucherDetails> list_TglVoucherDetails = tglVoucherDetailsDao.listAll(pageRequest);

        List<TglVoucherDetails> deletes = Lists.newArrayList(); // 删除列表
        List<TglVoucherDetails> inserts = Lists.newArrayList(); // 添加列表
        List<TglVoucherDetails> updates = Lists.newArrayList(); // 更新列表
        for (TglVoucherDetails tglVoucherDetailsSaved : list_TglVoucherDetails) {
            boolean found = false;
            for (TglVoucherDetails tglVoucherDetails : tglVoucherMains.getTglVoucherDetailsList()) {
                if (tglVoucherDetailsSaved.getId().equals(tglVoucherDetails.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tglVoucherDetailsSaved);
            }
        }
        if (deletes.size() > 0) {
            tglVoucherDetailsService.bulkDelete(deletes);
        }
        for (TglVoucherDetails tglVoucherDetails : tglVoucherMains.getTglVoucherDetailsList()) {
            if (StringUtils.isBlank(tglVoucherDetails.getId())) {
                tglVoucherDetails.setUqvoucherid(tglVoucherDetails.getUqvoucherid());

                inserts.add(tglVoucherDetails);
            } else {
                updates.add(tglVoucherDetails);
            }
        }
        if (updates.size() > 0) {
            tglVoucherDetailsService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tglVoucherDetailsService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     凭证分录分户分摊表 */
    public String getTenantId() {
        return SessionUtils.getUser().getCompanyId();
    }

    public int checks(String voucherid) {
        List<Map<String, Object>> checks = tglVoucherMainsDao.checks(voucherid);
        if (checks.size()!=0){
            return Integer.valueOf(checks.get(0).get("submitodd").toString());
        }
        return 0;
    }

    public void vouchercheck(String jsonVoucherid, String vouchercheckdate, StringHolder errMsg) throws Exception {
        String checkeruserid = SessionUtils.getUser().getId();
        String voucherid = jsonVoucherid;
        boolean ischeck = this.vouchercheckrulecheck(checkeruserid, voucherid, vouchercheckdate, errMsg);

        if (ischeck) {
            SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String dtcheckersrv = simpleformat.format(date);
            TglVoucherMains mainlist = get(voucherid);
            SysUser sysUser = new SysUser();
            sysUser.setId(checkeruserid);
            mainlist.setUqcheckerid(sysUser);
            mainlist.setDtchecker(vouchercheckdate);
            mainlist.setDtcheckersrv(dtcheckersrv);
            mainlist.setIntflag(1);
            tglVoucherMainsDao.update(mainlist);
        }
    }

    private boolean vouchercheckrulecheck(String userid, String voucherid, String vouchercheckdate, StringHolder errMsg) throws ParseException {

        boolean iscash = lockVoucher(voucherid, "1");

        TglVoucherMains mainlist = get(voucherid);


        if (StringUtils.isBlank(mainlist.getUqvoucherid())) {
            errMsg.value += "没有找到凭证，请联系管理员！";
            return false;
        }


        if (!iscash) {
            errMsg.value += "流水号为" + mainlist.getIntcompanyseq() + "的凭证已经审核过了!";
            return false;
        }

        //1.检查制证人和审核人是不是同一个人
        if (mainlist.getUqfillerid().getId().equals(userid)) {
            errMsg.value += "流水号为" + mainlist.getIntcompanyseq() + "的凭证的制证人和审核人是同一个人!";
            return false;
        }

        //2。检查审核时间是不是在会计期起止时间内
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(vouchercheckdate);
        TglGlobalPeriods uqglobalperiodid = mainlist.getTglGlobalPeriodsList();
//        TglGlobalPeriods uqglobalperiodid = tglGlobalPeriodsList.get(0);
        if (!(date.compareTo(sdf.parse(uqglobalperiodid.getDtend())) <= 0 && date.compareTo(sdf.parse(uqglobalperiodid.getDtbegin())) >= 0)) {
            errMsg.value += "流水号为" + mainlist.getIntcompanyseq() + "的凭证审核时间不在会计期起止时间内!";
            return false;
        }

        //3.检查审核时间要晚于制证时间
        Date filldate = sdf.parse(mainlist.getChargeagainstsubmitodd());
        if (!(date.compareTo(filldate) >= 0)) {
            errMsg.value += "流水号为" + mainlist.getIntcompanyseq() + "的凭证审核日期不能早于交易日期!";
            return false;
        }

        return true;
    }

    public void unvouchercheck(String voucherid, StringHolder errMsg) {
        String uncheckeruserid = SessionUtils.getUser().getId();
        this.vouchercheckruleuncheck(uncheckeruserid, voucherid, errMsg);
    }

    private boolean vouchercheckruleuncheck(String userid, String voucherid, StringHolder errMsg) {
        boolean isuncheck = lockVoucher(voucherid, "4");

        TglVoucherMains tglVoucherMains = get(voucherid);
        if (StringUtils.isBlank(tglVoucherMains.getUqvoucherid())) {
            errMsg.value += "没有找到凭证，请联系管理员！";
            return false;
        }


        if (!isuncheck) {

            String intFlag = String.valueOf(tglVoucherMains.getIntflag());
            String cashFlag = String.valueOf(tglVoucherMains.getIntcashflag());
            String appendStr = "";
            if (cashFlag.equals("2")) {
                appendStr += "该凭证已出纳，请先进行反出纳操作！";
            } else if (intFlag.equals("2")) {
                appendStr += "该凭证已记账，请先进行反记账操作！";
            }
            errMsg.value += "流水号为" + tglVoucherMains.getIntcompanyseq() + "的凭证目前不能反审核!" + appendStr;
            return false;
        }

        //审核人和反审核人必须是同一个人
        if (tglVoucherMains.getUqcheckerid().getId().equals(userid)) {
            errMsg.value += "流水号为" + tglVoucherMains.getIntcompanyseq() + "的凭证的反审核人和审核人不是同一个人!";
            return false;
        }
        SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dtcheckersrv = simpleformat.format(date);
        SysUser sysUser = new SysUser();
        tglVoucherMains.setUqcheckerid(sysUser);
        tglVoucherMains.setDtchecker(voucherid);
        tglVoucherMains.setDtchecker(null);
        tglVoucherMains.setDtcheckersrv(null);
        tglVoucherMains.setIntflag(0);
        tglVoucherMainsDao.update(tglVoucherMains);
        return true;
    }

    public void vouchercash(String jsonVoucherid, String vouchercashdate, StringHolder errMsg) throws Exception {
        String cashuserid = SessionUtils.getUser().getId();

        String voucherid = jsonVoucherid;

        boolean iscash = this.vouchercheckrulecash(cashuserid, voucherid, vouchercashdate, errMsg);

        if (iscash) {
            SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String dtcashersrv = simpleformat.format(date);
            TglVoucherMains tglVoucherMains = get(voucherid);
            SysUser sysUser = new SysUser();
            sysUser.setId(cashuserid);
            tglVoucherMains.setUqcasherid(sysUser);
            tglVoucherMains.setDtcasher(vouchercashdate);
            tglVoucherMains.setDtcashersrv(dtcashersrv);
            tglVoucherMains.setIntflag(1);
            tglVoucherMains.setIntcashflag(2);
            tglVoucherMainsDao.update(tglVoucherMains);
//            this.voumaindao.updateVoucherState("2", "1", voucherid, cashuserid, vouchercashdate, dtcashersrv);

            handlerAccountPeriod(voucherid, 2, 1);

            handlerLedgerPeriod(voucherid, 2, 1);

            //新增-出纳增加写入非结转凭证


            if (StringUtils.isBlank(tglVoucherMains.getUqvoucherid())) {
                throw new RuntimeException("没有找到凭证，请联系管理员！");
            }
            /**
             * 处理非结转凭证余额计算
             */
            if (!tglVoucherMains.getUqnumbering().equals("CF10100D-ED35-4B1D-AAC7-235F68DFB117")) {
                handlerAccountPeriodNoJZ(voucherid, 2, 1);
            }
        }
    }

    private boolean vouchercheckrulecash(String userid, String voucherid, String vouchercashdate, StringHolder errMsg) throws Exception {
        boolean iscash = lockVoucher(voucherid, "2");

        TglVoucherMains tglVoucherMains = get(voucherid);


        if (StringUtils.isBlank(tglVoucherMains.getUqvoucherid())) {
            errMsg.value += "没有找到凭证，请联系管理员！";
            return false;
        }

        if (!iscash) {
            errMsg.value += "流水号为" + tglVoucherMains.getIntcompanyseq() + "的凭证已经出纳过了!";
            return false;
        }

        //1.检查制证人和审核人和出纳人是不是同一个人
        /*if (main.getUqfillerid().compareTo(userid) == 0) {
            errMsg.value += "流水号为" + main.getIntcompanyseq() + "的凭证的制证人和出纳人是同一个人!";
            return false;
        }*/
        String companyId = SessionUtils.getUser().getCompanyId();
        PropValue byCompany = propValueDao.getByCompany(companyId);
        String cash ="";
        if (byCompany!=null){
             cash = byCompany.getStatus();
        }

        if (!cash.equals("1")) {
            if (tglVoucherMains.getUqcheckerid().getId().equals(userid)) {
                errMsg.value += "流水号为" + tglVoucherMains.getIntcompanyseq() + "的凭证的审核人和出纳人是同一个人!";
                return false;
            }
        }
        //2。检查审核时间是不是在会计期起止时间内
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(vouchercashdate);

        if (!(date.compareTo(sdf.parse(tglVoucherMains.getUqglobalperiodid().getDtend())) <= 0 && date.compareTo(sdf.parse(tglVoucherMains.getUqglobalperiodid().getDtbegin())) >= 0)) {
            errMsg.value += "流水号为" + tglVoucherMains.getIntcompanyseq() + "的凭证出纳时间不在会计期起止时间内!";
            return false;
        }

        //3.检查出纳时间要晚于审核时间
        Date filldate = sdf.parse(tglVoucherMains.getDtchecker());
        if (!(date.compareTo(filldate)>= 0)) {
            errMsg.value += "流水号为" + tglVoucherMains.getIntcompanyseq() + "的凭证出纳日期不能早于审核日期!";
            return false;
        }

        return true;
    }

    public void unvouchercash(String voucherid, StringHolder errMsg) throws Exception {

        String cashuserid = SessionUtils.getUser().getId();

        boolean iscash = this.vouchercheckruleuncash(cashuserid, voucherid, errMsg);

        if (iscash) {
            handlerAccountPeriod(voucherid, 2, -1);

            handlerLedgerPeriod(voucherid, 2, -1);


            //新增-出纳增加写入非结转凭证

            TglVoucherMains tglVoucherMains = get(voucherid);
            if (StringUtils.isBlank(tglVoucherMains.getUqvoucherid())) {
                throw new RuntimeException("没有找到凭证，请联系管理员！");
            }
            SysUser sysUser = new SysUser();
            tglVoucherMains.setUqcasherid(sysUser);
            tglVoucherMains.setDtcasher(null);
            tglVoucherMains.setDtcashersrv(tglVoucherMains.getDtfillersrv());
            tglVoucherMains.setIntflag(1);
            tglVoucherMains.setIntcashflag(1);
            tglVoucherMainsDao.update(tglVoucherMains);
            /**
             * 处理非结转凭证余额计算
             */
            if (!tglVoucherMains.getUqnumbering().equals("CF10100D-ED35-4B1D-AAC7-235F68DFB117")) {
                handlerAccountPeriodNoJZ(voucherid, 2, -1);
            }
        }
    }

    private boolean vouchercheckruleuncash(String userid, String voucherid, StringHolder errMsg) throws Exception {
        boolean isuncash = lockVoucher(voucherid, "5");

        TglVoucherMains tglVoucherMains = get(voucherid);
        if (StringUtils.isBlank(tglVoucherMains.getUqvoucherid())) {
            errMsg.value += "没有找到凭证，请联系管理员！";
            return false;
        }


        if (!isuncash) {

            String intFlag = String.valueOf(tglVoucherMains.getIntcashflag());
            String cashFlag = String.valueOf(tglVoucherMains.getIntcashflag());
            String appendStr = "";
            if (intFlag.equals("2")) {
                appendStr += "该凭证已记账，请先进行反记账操作！";
            }
            errMsg.value += "流水号为" + tglVoucherMains.getIntcompanyseq() + "的凭证目前不能反出纳！" + appendStr;
            return false;
        }

        //1.检查制证人和审核人和出纳人是不是同一个人
        if (!tglVoucherMains.getUqcasherid().getId().equals(userid)) {
            errMsg.value += "流水号为" + tglVoucherMains.getIntcompanyseq() + "的凭证的反出纳人和出纳人不是同一个人!";
            return false;
        }
        return true;
    }

    public void voucherend(String jsonVoucherid, String voucherenddate, StringHolder errMsg) throws Exception {
        String cashuserid = SessionUtils.getUser().getId();

        String voucherid = jsonVoucherid;


        TglVoucherMains tglVoucherMains = get(voucherid);
        if (StringUtils.isBlank(tglVoucherMains.getUqvoucherid())) {
            throw new RuntimeException("没有找到凭证，请联系管理员！");
        }


        boolean isend = this.vouchercheckruleend(tglVoucherMains, cashuserid, voucherid, voucherenddate, errMsg);

        if (isend) {
            SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String dtcashersrv = simpleformat.format(date);
            SysUser sysUser = new SysUser();
            sysUser.setId(cashuserid);
            tglVoucherMains.setUqaccountantid(sysUser);
            tglVoucherMains.setDtaccountant(voucherenddate);
            tglVoucherMains.setDtaccountantsrv(dtcashersrv);
            tglVoucherMains.setIntflag(2);
            tglVoucherMainsDao.update(tglVoucherMains);
//            this.voumaindao.updateVoucherState("3", "", voucherid, cashuserid, voucherenddate, dtcashersrv);

//            this.voumaindao.updateVouchernum(main.getUqvoucherid(), main.getUqcompanyid(), main.getUqglobalperiodid(), main.getUqnumbering());

            handlerAccountPeriod(voucherid, 3, 1);

            handlerLedgerPeriod(voucherid, 3, 1);


            //新增-出纳增加写入非结转凭证
            if (StringUtils.isBlank(tglVoucherMains.getUqvoucherid())) {
                throw new RuntimeException("没有找到凭证，请联系管理员！");
            }

            /**
             * 处理非结转凭证余额计算
             */

            if (!tglVoucherMains.getUqnumbering().equals("CF10100D-ED35-4B1D-AAC7-235F68DFB117")) {
                handlerAccountPeriodNoJZ(voucherid, 3, 1);
            }
        }

    }

    private boolean vouchercheckruleend(TglVoucherMains main, String userid, String voucherid, String voucherenddate, StringHolder errMsg) throws Exception {
        boolean iscash = lockVoucher(voucherid, "3");

        if (!iscash) {
            errMsg.value += "流水号为" + main.getIntcompanyseq() + "的凭证已经记账过了!";
            return false;
        }

        //1.检查制证人和审核人和出纳人是不是同一个人
        /*if (main.getUqfillerid().compareTo(userid) == 0) {
            errMsg.value += "流水号为" + main.getIntcompanyseq() + "的凭证的制证人和记账人是同一个人!";
            return false;
        }*/

        if (main.getUqcheckerid().getId().equals(userid)) {
            errMsg.value += "流水号为" + main.getIntcompanyseq() + "的凭证的审核人和记账人是同一个人!";
            return false;
        }

       /* if (main.getUqcasherid().compareTo(userid) == 0) {
            errMsg.value += "流水号为" + main.getIntcompanyseq() + "的凭证的出纳人和记账人是同一个人!";
            return false;
        }*/

        //2。检查记账时间是不是在会计期起止时间内
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(voucherenddate);

        if (!(date.compareTo(sdf.parse(main.getUqglobalperiodid().getDtend())) <= 0 && date.compareTo(sdf.parse(main.getUqglobalperiodid().getDtbegin())) >= 0)) {
            errMsg.value += "流水号为" + main.getIntcompanyseq() + "的凭证记账时间不在会计期起止时间内!";
            return false;
        }

        //3.检查记账时间要晚于审核时间
        Date filldate = sdf.parse(main.getDtchecker());
        if (!(date.compareTo(filldate) >= 0)) {
            errMsg.value += "流水号为" + main.getIntcompanyseq() + "的凭证记账日期不能早于审核日期!";
            return false;
        }

        if ("2".equals(main.getIntcashflag())) {
            //已出纳
            filldate = sdf.parse(main.getDtcasher());
            if (!(date.compareTo(filldate) >= 0)) {
                errMsg.value += "流水号为" + main.getIntcompanyseq() + "的凭证记账日期不能早于出纳日期!";
                return false;
            }
        }

        return true;
    }

    /**
     * 凭证反记账
     *
     * @param jsonVoucherid
     * @param errMsg
     */
    public void unVoucherend(String jsonVoucherid, StringHolder errMsg) throws Exception {
        String cashuserid = SessionUtils.getUser().getId();

        String voucherid = jsonVoucherid;

        TglVoucherMains tglVoucherMains = get(voucherid);

        if (StringUtils.isBlank(tglVoucherMains.getUqvoucherid())) {
            throw new RuntimeException("没有找到凭证，请联系管理员！");
        }
        boolean isend = this.unVouchercheckruleend("6", tglVoucherMains, cashuserid, voucherid, errMsg);

        if (isend) {
            SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date date = new java.util.Date();
            String dtcashersrv = simpleformat.format(date);

            int result = this.tglVoucherMainsDao.selectIntCashFlag(voucherid);
            if (result > 0) {
                SysUser sysUser = new SysUser();
                tglVoucherMains.setUqaccountantid(sysUser);
                tglVoucherMains.setIntflag(1);
                tglVoucherMains.setIntcashflag(2);
                tglVoucherMainsDao.update(tglVoucherMains);
//                this.voucherMainDAO.updateVoucherState("7", "", voucherid, cashuserid, voucherenddate, dtcashersrv);
            } else {
                SysUser sysUser = new SysUser();
                tglVoucherMains.setUqaccountantid(sysUser);
                tglVoucherMains.setIntflag(1);
                tglVoucherMains.setIntcashflag(1);
                tglVoucherMainsDao.update(tglVoucherMains);
//                this.voucherMainDAO.updateVoucherState("6", "", voucherid, cashuserid, voucherenddate, dtcashersrv);
            }


            // this.voucherMainDAO.unUpdateVouchernum("6", main.getUqvoucherid(), main.getUqcompanyid(), main.getUqglobalperiodid(), main.getUqnumbering());

            unHandlerAccountPeriod(voucherid, 3, 1);

            unHandlerLedgerPeriod(voucherid, 3, 1);

            // this.voucherMainDAO.deleteVoucheMainSerial(voucherid);


            /**
             * 处理非结转凭证余额计算
             */

            if (!tglVoucherMains.getUqnumbering().equals("CF10100D-ED35-4B1D-AAC7-235F68DFB117")) {
                handlerAccountPeriodNoJZ(voucherid, 6, 1);
            }

        }
    }

    private boolean unVouchercheckruleend(String tag, TglVoucherMains main, String userid, String voucherid, StringHolder errMsg) throws Exception {
        boolean iscash = lockVoucher(voucherid, tag);
        if (!iscash) {
            errMsg.value += "流水号为" + main.getIntcompanyseq() + "的凭证目前还未记账，不能对未记账凭证进行反记账操作!";
            return false;
        }
        //1.检查制证人和审核人和出纳人是不是同一个人
        if (!main.getUqaccountantid().getId().equals(userid)) {
            errMsg.value += "流水号为" + main.getIntcompanyseq() + "的凭证的反记账人和记账人不是同一个人!";
            return false;
        }

        int result = this.tglVoucherMainsDao.offsetIsTrue(main.getUqvoucherid());

        if (result > 0) {
            errMsg.value += "流水号为" + main.getIntcompanyseq() + "的凭证已经存在往来冲销记录，不允许反记账";
        }
        //都反记账了要这个现在抓？？？？？？搞不懂
        //2。检查记账时间是不是在会计期起止时间内
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = sdf.parse(voucherenddate);
//        if (!(date.compareTo(sdf.parse(main.getUqglobalperiodid().getDtend())) <= 0 && date.compareTo(sdf.parse(main.getUqglobalperiodid().getDtbegin())) >= 0)) {
//            errMsg.value += "流水号为" + main.getIntcompanyseq() + "的凭证记账时间不在会计期起止时间内!";
//            return false;
//        }


        //3.检查记账时间要晚于审核时间
//        Date filldate = sdf.parse(main.getDtchecker());
//        if (!(date.compareTo(filldate) >= 0)) {
//            errMsg.value += "流水号为" + main.getIntcompanyseq() + "的凭证反记账日期不能早于审核日期!";
//            return false;
//        }

//        if ("2".equals(main.getIntcashflag())) {
//            //已出纳
//            filldate = sdf.parse(main.getDtcasher());
//            if (!(date.compareTo(filldate) >= 0)) {
//                errMsg.value += "流水号为" + main.getIntcompanyseq() + "的凭证反记账日期不能早于出纳日期!";
//                return false;
//            }
//        }

        return true;
    }

    public void unHandlerAccountPeriod(String voucherid, int tag, int flag) {
        //1.获取凭证分录的分组信息，按照科目，单位，会计期
        List<Map<String, Object>> list = null;

        if (tag == 2) {
            list = this.tglVoucherMainsDao.getAccountPeriodCashInfo(voucherid, SessionUtils.getUser().getCompanyId());
        } else {
            list = this.tglVoucherMainsDao.getAccountPeriodInfo(voucherid, SessionUtils.getUser().getCompanyId());
        }

        for (int i = 0; i < list.size(); i++) {
            //1.获取余额表所需信息
            Map<String, Object> obj = list.get(i);
//            String uqaccountid = obj[0].toString();
//            String uqglobalperiodid = obj[1].toString();
//            String uqcompanyid = obj[2].toString();
//            BigDecimal accdebitsum = new BigDecimal(obj[3].toString());
//            BigDecimal acccreditsum = new BigDecimal(obj[4].toString());
//            BigDecimal accfdebitsum = new BigDecimal(obj[5].toString());
//            BigDecimal accfcreditsum = new BigDecimal(obj[6].toString());
            String uqaccountid = obj.get("uqaccountid").toString();
            String uqglobalperiodid = obj.get("uqglobalperiodid").toString();
            String uqcompanyid = obj.get("uqcompanyid").toString();
            BigDecimal accdebitsum = new BigDecimal(obj.get("accdebitsum").toString());
            BigDecimal acccreditsum = new BigDecimal(obj.get("acccreditsum").toString());
            BigDecimal accfdebitsum = new BigDecimal(obj.get("accfdebitsum").toString());
            BigDecimal accfcreditsum = new BigDecimal(obj.get("accfcreditsum").toString());
            //2.锁掉余额表
            List<Map<String, Object>> lockList = this.tglVoucherMainsDao.lockAccountPeriod(uqaccountid, uqglobalperiodid, uqcompanyid);
            int lockline = lockList.size();

            //3.更新余额表
            unUpdateAccountPeriod(lockline, uqaccountid, uqglobalperiodid, uqcompanyid,
                    accdebitsum, acccreditsum, accfdebitsum, accfcreditsum, tag, flag);
        }
    }

    /**
     * @param lockline
     * @param uqaccountid
     * @param uqglobalperiodid
     * @param uqcompanyid
     * @param accdebitsum
     * @param acccreditsum
     * @param tag              1-制证,2-出纳，3-记账
     * @param flag             传递正负1
     *                         制证传递tag1,flag1,查删改删除凭证传递tag1,flag-1,出纳tag2,flag1,反出纳tag2,flag-1,记账tag3,flag1
     */
    public void unUpdateAccountPeriod(int lockline, String uqaccountid, String uqglobalperiodid,
                                      String uqcompanyid, BigDecimal accdebitsum, BigDecimal acccreditsum,
                                      BigDecimal accfdebitsum, BigDecimal accfcreditsum, int tag, int flag) {
        String strSql = " ";

        accdebitsum = accdebitsum.multiply(new BigDecimal(flag));
        acccreditsum = acccreditsum.multiply(new BigDecimal(flag));
        accfdebitsum = accfdebitsum.multiply(new BigDecimal(flag));
        accfcreditsum = accfcreditsum.multiply(new BigDecimal(flag));

        if (lockline <= 0) {
            //不存在科目余额数据
            TglPeriodAccounts tglPeriodAccounts = new TglPeriodAccounts();


            tglPeriodAccounts.setUqcompanyid(uqcompanyid);
            tglPeriodAccounts.setUqglobalperiodid(uqglobalperiodid);
            TglAccounts tglAccounts = new TglAccounts();
            tglAccounts.setUqaccountid(uqaccountid);
            tglPeriodAccounts.setUqaccountid(tglAccounts);

            tglPeriodAccounts.setMnydebitperiodall(accdebitsum);
            tglPeriodAccounts.setMnycreditperiodall(acccreditsum);
            tglPeriodAccounts.setMnyfdebitperiodall(accfdebitsum);
            tglPeriodAccounts.setMnyfcreditperiodall(accfcreditsum);

            tglPeriodAccounts.setMnydebitperiod(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnycreditperiod(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnyfdebitperiod(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnyfcreditperiod(BigDecimal.valueOf(0));

            tglPeriodAccounts.setMnydebitcashed(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnycreditcashed(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnyfdebitcashed(BigDecimal.valueOf(0));
            tglPeriodAccounts.setMnyfcreditcashed(BigDecimal.valueOf(0));

            tglPeriodAccountsDao.insert(tglPeriodAccounts);

        } else {

            TglPeriodAccounts tglPeriodAccounts = new TglPeriodAccounts();

            if (tag == 1) {

                tglPeriodAccounts.setUqcompanyid(uqcompanyid);
                tglPeriodAccounts.setUqglobalperiodid(uqglobalperiodid);
                TglAccounts tglAccounts = new TglAccounts();
                tglAccounts.setUqaccountid(uqaccountid);
                tglPeriodAccounts.setUqaccountid(tglAccounts);

                tglPeriodAccounts.setMnydebitperiodall(accdebitsum);
                tglPeriodAccounts.setMnycreditperiodall(acccreditsum);
                tglPeriodAccounts.setMnyfdebitperiodall(accfdebitsum);
                tglPeriodAccounts.setMnyfcreditperiodall(accfcreditsum);

                tglPeriodAccounts.setMnydebitperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditperiod(BigDecimal.valueOf(0));

                tglPeriodAccounts.setMnydebitcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditcashed(BigDecimal.valueOf(0));

            } else if (tag == 2) {


                tglPeriodAccounts.setUqcompanyid(uqcompanyid);
                tglPeriodAccounts.setUqglobalperiodid(uqglobalperiodid);
                TglAccounts tglAccounts = new TglAccounts();
                tglAccounts.setUqaccountid(uqaccountid);
                tglPeriodAccounts.setUqaccountid(tglAccounts);

                tglPeriodAccounts.setMnydebitperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditperiodall(BigDecimal.valueOf(0));

                tglPeriodAccounts.setMnydebitperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitperiod(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditperiod(BigDecimal.valueOf(0));

                tglPeriodAccounts.setMnydebitcashed(accdebitsum);
                tglPeriodAccounts.setMnycreditcashed(acccreditsum);
                tglPeriodAccounts.setMnyfdebitcashed(accfdebitsum);
                tglPeriodAccounts.setMnyfcreditcashed(accfcreditsum);
            } else if (tag == 3) {


                tglPeriodAccounts.setUqcompanyid(uqcompanyid);
                tglPeriodAccounts.setUqglobalperiodid(uqglobalperiodid);
                TglAccounts tglAccounts = new TglAccounts();
                tglAccounts.setUqaccountid(uqaccountid);
                tglPeriodAccounts.setUqaccountid(tglAccounts);

                tglPeriodAccounts.setMnydebitperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitperiodall(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditperiodall(BigDecimal.valueOf(0));

                tglPeriodAccounts.setMnydebitperiod(accdebitsum);
                tglPeriodAccounts.setMnycreditperiod(acccreditsum);
                tglPeriodAccounts.setMnyfdebitperiod(accfdebitsum);
                tglPeriodAccounts.setMnyfcreditperiod(accfcreditsum);

                tglPeriodAccounts.setMnydebitcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnycreditcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfdebitcashed(BigDecimal.valueOf(0));
                tglPeriodAccounts.setMnyfcreditcashed(BigDecimal.valueOf(0));
            }
            tglPeriodAccountsDao.newSubtractUpdate(tglPeriodAccounts);
        }
    }

    public void unHandlerLedgerPeriod(String voucherid, int tag, int flag) {
        //1.获取凭证分录的分组信息，按照科目，单位，会计期
        List<Map<String, Object>> list = null;

        if (tag == 2) {
            list = this.tglVoucherMainsDao.getLedgerPeriodCashInfo(voucherid, SessionUtils.getUser().getCompanyId());
        } else {
            list = this.tglVoucherMainsDao.getLedgerPeriodInfo(voucherid, SessionUtils.getUser().getCompanyId());
        }

        for (int i = 0; i < list.size(); i++) {
            //1.获取余额表所需信息
            Map<String, Object> obj = list.get(i);
            String uqaccountid = obj.get("uqaccountid").toString();
            String uqglobalperiodid = obj.get("uqglobalperiodid").toString();
            String uqcompanyid = obj.get("uqcompanyid").toString();
            String uqledgerid = obj.get("uqledgerid").toString();
            BigDecimal accdebitsum = new BigDecimal(obj.get("accdebitsum").toString());
            BigDecimal acccreditsum = new BigDecimal(obj.get("acccreditsum").toString());
            BigDecimal accfdebitsum = new BigDecimal(obj.get("accfdebitsum").toString());
            BigDecimal accfcreditsum = new BigDecimal(obj.get("accfcreditsum").toString());

            //2.锁掉余额表
            List<Map<String, Object>> lockList = this.tglVoucherMainsDao.lockLedgerPeriod(uqaccountid, uqglobalperiodid, uqcompanyid, uqledgerid);
            int lockline = lockList.size();

            //3.更新余额表
            unUpdateLedgerPeriod(lockline, uqaccountid, uqglobalperiodid, uqcompanyid, uqledgerid,
                    accdebitsum, acccreditsum, accfdebitsum, accfcreditsum, tag, flag);
        }
    }

    /**
     * @param lockline
     * @param uqaccountid
     * @param uqglobalperiodid
     * @param uqcompanyid
     * @param accdebitsum
     * @param acccreditsum
     * @param tag              1-制证,2-出纳，3-记账
     * @param flag             传递正负1
     *                         制证传递tag1,flag1,查删改删除凭证传递tag1,flag-1,出纳tag2,flag1,反出纳tag2,flag-1,记账tag3,flag1
     */
    public void unUpdateLedgerPeriod(int lockline, String uqaccountid, String uqglobalperiodid,
                                     String uqcompanyid, String uqledgeid, BigDecimal accdebitsum, BigDecimal acccreditsum,
                                     BigDecimal accfdebitsum, BigDecimal accfcreditsum, int tag, int flag) {
        String strSql = " ";

        accdebitsum = accdebitsum.multiply(new BigDecimal(flag));
        acccreditsum = acccreditsum.multiply(new BigDecimal(flag));
        accfdebitsum = accfdebitsum.multiply(new BigDecimal(flag));
        accfcreditsum = accfcreditsum.multiply(new BigDecimal(flag));
        TglCompanyLedgerPeriods tglCompanyLedgerPeriods = new TglCompanyLedgerPeriods();
        if (lockline <= 0) {
            //不存在科目余额数据


            tglCompanyLedgerPeriods.setUqcompanyid(uqcompanyid);
            tglCompanyLedgerPeriods.setUqglobalperiodid(uqglobalperiodid);
            tglCompanyLedgerPeriods.setUqaccountid(uqaccountid);
            tglCompanyLedgerPeriods.setUqledgeid(uqledgeid);

            tglCompanyLedgerPeriods.setMnydebitperiodall(accdebitsum);
            tglCompanyLedgerPeriods.setMnycreditperiodall(acccreditsum);
            tglCompanyLedgerPeriods.setMnyfdebitperiodall(accfdebitsum);
            tglCompanyLedgerPeriods.setMnyfcreditperiodall(accfcreditsum);

            tglCompanyLedgerPeriods.setMnydebitperiod(BigDecimal.valueOf(0));
            tglCompanyLedgerPeriods.setMnycreditperiod(BigDecimal.valueOf(0));
            tglCompanyLedgerPeriods.setMnyfdebitperiod(BigDecimal.valueOf(0));
            tglCompanyLedgerPeriods.setMnyfcreditperiod(BigDecimal.valueOf(0));

            tglCompanyLedgerPeriods.setMnydebitcashed(BigDecimal.valueOf(0));
            tglCompanyLedgerPeriods.setMnycreditcashed(BigDecimal.valueOf(0));
            tglCompanyLedgerPeriods.setMnyfdebitcashed(BigDecimal.valueOf(0));
            tglCompanyLedgerPeriods.setMnyfcreditcashed(BigDecimal.valueOf(0));

            tglCompanyLedgerPeriods.setId(IdGen.uuid());
            tglCompanyLedgerPeriodsDao.insert(tglCompanyLedgerPeriods);


        } else {

            List<Object> paras = new ArrayList<Object>();
            if (tag == 1) {


                tglCompanyLedgerPeriods.setUqcompanyid(uqcompanyid);
                tglCompanyLedgerPeriods.setUqglobalperiodid(uqglobalperiodid);
                tglCompanyLedgerPeriods.setUqaccountid(uqaccountid);
                tglCompanyLedgerPeriods.setUqledgeid(uqledgeid);

                tglCompanyLedgerPeriods.setMnydebitperiodall(accdebitsum);
                tglCompanyLedgerPeriods.setMnycreditperiodall(acccreditsum);
                tglCompanyLedgerPeriods.setMnyfdebitperiodall(accfdebitsum);
                tglCompanyLedgerPeriods.setMnyfcreditperiodall(accfcreditsum);

                tglCompanyLedgerPeriods.setMnydebitperiod(BigDecimal.valueOf(0));
                tglCompanyLedgerPeriods.setMnycreditperiod(BigDecimal.valueOf(0));
                tglCompanyLedgerPeriods.setMnyfdebitperiod(BigDecimal.valueOf(0));
                tglCompanyLedgerPeriods.setMnyfcreditperiod(BigDecimal.valueOf(0));

                tglCompanyLedgerPeriods.setMnydebitcashed(BigDecimal.valueOf(0));
                tglCompanyLedgerPeriods.setMnycreditcashed(BigDecimal.valueOf(0));
                tglCompanyLedgerPeriods.setMnyfdebitcashed(BigDecimal.valueOf(0));
                tglCompanyLedgerPeriods.setMnyfcreditcashed(BigDecimal.valueOf(0));


            } else if (tag == 2) {


                tglCompanyLedgerPeriods.setUqcompanyid(uqcompanyid);
                tglCompanyLedgerPeriods.setUqglobalperiodid(uqglobalperiodid);
                tglCompanyLedgerPeriods.setUqaccountid(uqaccountid);
                tglCompanyLedgerPeriods.setUqledgeid(uqledgeid);

                tglCompanyLedgerPeriods.setMnydebitperiodall(BigDecimal.valueOf(0));
                tglCompanyLedgerPeriods.setMnycreditperiodall(BigDecimal.valueOf(0));
                tglCompanyLedgerPeriods.setMnyfdebitperiodall(BigDecimal.valueOf(0));
                tglCompanyLedgerPeriods.setMnyfcreditperiodall(BigDecimal.valueOf(0));

                tglCompanyLedgerPeriods.setMnydebitperiod(BigDecimal.valueOf(0));
                tglCompanyLedgerPeriods.setMnycreditperiod(BigDecimal.valueOf(0));
                tglCompanyLedgerPeriods.setMnyfdebitperiod(BigDecimal.valueOf(0));
                tglCompanyLedgerPeriods.setMnyfcreditperiod(BigDecimal.valueOf(0));

                tglCompanyLedgerPeriods.setMnydebitcashed(accdebitsum);
                tglCompanyLedgerPeriods.setMnycreditcashed(acccreditsum);
                tglCompanyLedgerPeriods.setMnyfdebitcashed(accfdebitsum);
                tglCompanyLedgerPeriods.setMnyfcreditcashed(accfcreditsum);


            } else if (tag == 3) {


                tglCompanyLedgerPeriods.setUqcompanyid(uqcompanyid);
                tglCompanyLedgerPeriods.setUqglobalperiodid(uqglobalperiodid);
                tglCompanyLedgerPeriods.setUqaccountid(uqaccountid);
                tglCompanyLedgerPeriods.setUqledgeid(uqledgeid);

                tglCompanyLedgerPeriods.setMnydebitperiodall(BigDecimal.valueOf(0));
                tglCompanyLedgerPeriods.setMnycreditperiodall(BigDecimal.valueOf(0));
                tglCompanyLedgerPeriods.setMnyfdebitperiodall(BigDecimal.valueOf(0));
                tglCompanyLedgerPeriods.setMnyfcreditperiodall(BigDecimal.valueOf(0));

                tglCompanyLedgerPeriods.setMnydebitperiod(accdebitsum);
                tglCompanyLedgerPeriods.setMnycreditperiod(acccreditsum);
                tglCompanyLedgerPeriods.setMnyfdebitperiod(accfdebitsum);
                tglCompanyLedgerPeriods.setMnyfcreditperiod(accfcreditsum);

                tglCompanyLedgerPeriods.setMnydebitcashed(BigDecimal.valueOf(0));
                tglCompanyLedgerPeriods.setMnycreditcashed(BigDecimal.valueOf(0));
                tglCompanyLedgerPeriods.setMnyfdebitcashed(BigDecimal.valueOf(0));
                tglCompanyLedgerPeriods.setMnyfcreditcashed(BigDecimal.valueOf(0));


            }
            tglCompanyLedgerPeriodsDao.newupdate(tglCompanyLedgerPeriods);
//            this.execute(strSql, paras);
        }
    }

    public int importVoucher(MultipartFile file) throws Exception {
        InputStream is = file.getInputStream();
        Workbook wk = Workbook.getWorkbook(is);
        Sheet st = wk.getSheet(0);  //读取第一个表格
        Company company = JSON.parseObject(com.geeke.sys.utils.SessionUtils.getUserJson().getJSONObject("company").toJSONString(), Company.class);
        int rowlength= st.getRows();  //获取表格数据的行数

        this.checkFile(st); //检查导入数据的字段是否匹配
        ArrayList<TglVoucherDetails> tglVoucherDetailss = new ArrayList<>();
        ArrayList<String> sole = new ArrayList<>();
        for (int row = 0; row < rowlength; row++) {
            String year = ObjectUtils.toString(st.getCell(0, row).getContents().trim());
            String month = ObjectUtils.toString(st.getCell(1, row).getContents().trim());
            String day = ObjectUtils.toString(st.getCell(2, row).getContents().trim());
            String ppz = ObjectUtils.toString(st.getCell(3, row).getContents().trim());
            String ppzcode = ObjectUtils.toString(st.getCell(4, row).getContents().trim());
            String INTAFFIX = ObjectUtils.toString(st.getCell(5, row).getContents().trim());
            String VARABSTRACT = ObjectUtils.toString(st.getCell(6, row).getContents().trim());
            String VARACCOUNTCODE = ObjectUtils.toString(st.getCell(7, row).getContents().trim());
            String MNYDEBIT = ObjectUtils.toString(st.getCell(8, row).getContents().trim());
            String MNYCREDIT = ObjectUtils.toString(st.getCell(9, row).getContents().trim());
            String xjll = ObjectUtils.toString(st.getCell(10, row).getContents().trim());
            String department = ObjectUtils.toString(st.getCell(11, row).getContents().trim());
            String vendor = ObjectUtils.toString(st.getCell(11, row).getContents().trim());
            String client = ObjectUtils.toString(st.getCell(12, row).getContents().trim());
            String employee = ObjectUtils.toString(st.getCell(13, row).getContents().trim());
            TglVoucherDetails tglVoucherDetails = new TglVoucherDetails();
            tglVoucherDetails.setIntseq(row);
            tglVoucherDetails.setVarabstract(VARABSTRACT);
            List<Parameter> params = null;
            params = Lists.newArrayList();
            params.add(new Parameter("VARACCOUNTCODE", "=", VARACCOUNTCODE));
            PageRequest pageRequest = new PageRequest(params);
            List<TglAccounts> tglAccounts = tglAccountsDao.listAll(pageRequest);
            tglVoucherDetails.setUqaccountid(tglAccounts.get(0).getUqaccountid());
            tglVoucherDetails.setTglAccounts(tglAccounts.get(0));
            tglVoucherDetails.setMnydebit(MNYDEBIT==null?new BigDecimal(0):new BigDecimal(MNYDEBIT));
            tglVoucherDetails.setMnycredit(MNYCREDIT==null?new BigDecimal(0):new BigDecimal(MNYCREDIT));
            if (StringUtils.isBlank(xjll)){
                params = Lists.newArrayList();
                params.add(new Parameter("varcode", "=", xjll));
                pageRequest = new PageRequest(params);
                List<Flowtype> flowtypes = flowtypeDao.listAll(pageRequest);
                TglAccountsXjll tglAccountsXjll = new TglAccountsXjll();
                tglAccountsXjll.setUqflowtypeid(flowtypes.get(0));
                tglVoucherDetails.setTglAccountsXjll(tglAccountsXjll);
            }

            TglLedgetype tglLedgetype = new TglLedgetype();
            ArrayList<TglLedger> tglLedgers = new ArrayList<>();
            if (StringUtils.isBlank(department)){
                TglLedger tglLedger = new TglLedger();
                params = Lists.newArrayList();
                params.add(new Parameter("VARLEDGECODE", "=", department));
                pageRequest = new PageRequest(params);
                List<TglLedger> tglLedgers1 = tglLedgerDao.listAll(pageRequest);
                tglLedgers.add(tglLedgers1.get(0));
            }
            if (StringUtils.isBlank(vendor)){
                TglLedger tglLedger = new TglLedger();
                params = Lists.newArrayList();
                params.add(new Parameter("VARLEDGECODE", "=", vendor));
                pageRequest = new PageRequest(params);
                List<TglLedger> tglLedgers1 = tglLedgerDao.listAll(pageRequest);
                tglLedgers.add(tglLedgers1.get(0));
            }
            if (StringUtils.isBlank(client)){
                TglLedger tglLedger = new TglLedger();
                params = Lists.newArrayList();
                params.add(new Parameter("VARLEDGECODE", "=", client));
                pageRequest = new PageRequest(params);
                List<TglLedger> tglLedgers1 = tglLedgerDao.listAll(pageRequest);
                tglLedgers.add(tglLedgers1.get(0));
            }
            if (StringUtils.isBlank(employee)){
                TglLedger tglLedger = new TglLedger();
                params = Lists.newArrayList();
                params.add(new Parameter("VARLEDGECODE", "=", employee));
                pageRequest = new PageRequest(params);
                List<TglLedger> tglLedgers1 = tglLedgerDao.listAll(pageRequest);
                tglLedgers.add(tglLedgers1.get(0));
            }
            tglVoucherDetailss.add(tglVoucherDetails);
            sole.add(year+month+day+ppz+ppzcode);
        }
        HashMap<String , ArrayList> objectObjectHashMap = new HashMap<>();
        for (int i = 0; i < sole.size(); i++) {
            if (objectObjectHashMap.containsKey(sole.get(i))){
                ArrayList arrayList = objectObjectHashMap.get(sole.get(i));
                arrayList.add(i);
            }else {
                ArrayList<Object> objects = new ArrayList<>();
                objects.add(i);
                objectObjectHashMap.put(sole.get(i),objects);
            }
        }
        Set<String> strings = objectObjectHashMap.keySet();
        ArrayList<TglVoucherMains> objects = new ArrayList<>();
        for (String string : strings) {
            ArrayList arrayList = objectObjectHashMap.get(string);
            TglVoucherMains tglVoucherMains = new TglVoucherMains();
            tglVoucherMains.setIntaffix(Integer.valueOf(ObjectUtils.toString(st.getCell(5, (Integer) arrayList.get(0)).getContents().trim())));
            List<Parameter> params = null;
            params = Lists.newArrayList();
           int year = Integer.parseInt(ObjectUtils.toString(st.getCell(0, (Integer) arrayList.get(0)).getContents().trim()));
           int month = Integer.parseInt(ObjectUtils.toString(st.getCell(1, (Integer) arrayList.get(0)).getContents().trim()));
           int day = Integer.parseInt(ObjectUtils.toString(st.getCell(2, (Integer) arrayList.get(0)).getContents().trim()));
            params.add(new Parameter("varname", "=", year+month+"期"));
            params.add(new Parameter("INTSTATUS", "=", 2));
            PageRequest pageRequest = new PageRequest(params);
            List<TglGlobalPeriods> tglGlobalPeriods = tglGlobalPeriodsDao.listAll(pageRequest);
            if (tglGlobalPeriods.size()==0){
                throw new Exception("会计期"+year+month+"期"+"不存在或者未开启");
            }
            tglVoucherMains.setUqglobalperiodid(tglGlobalPeriods.get(0));
            tglVoucherMains.setSubmitodd(String.valueOf(0));
            BigDecimal mnydebitsum = new BigDecimal(0);
            BigDecimal mnycreditsum = new BigDecimal(0);
            String dtfiller = year+"-"+month+"-"+day;
            String chargeagainstsubmitodd =year+"-"+month+"-"+day;
            for (Object o : arrayList) {
                mnydebitsum.add(tglVoucherDetailss.get((Integer) o).getMnydebit());
                mnycreditsum.add(tglVoucherDetailss.get((Integer) o).getMnycredit());
            }
            objects.add(tglVoucherMains);
        }
        for (TglVoucherMains object : objects) {
            save(object);
        }
        return 1;
    }
    public String getUqvoucherById(String pzz) {
        String pzid = "";
        switch (pzz) {
            case "现收":
                pzid = "00000000-0000-0000-0000-000000000001";
                break;
            case "现付":
                pzid = "00000000-0000-0000-0000-000000000002";
                break;
            case "银收":
                pzid = "00000000-0000-0000-0000-000000000003";
                break;
            case "银付":
                pzid = "00000000-0000-0000-0000-000000000004";
                break;
            case "转账":
                pzid = "00000000-0000-0000-0000-000000000005";
                break;
            default:
                System.out.println("Invalid day");
                break;
        }
        return pzid;
    }
    private void checkFile(Sheet sheet) throws Exception {
        if ("年份".equals(sheet.getCell(0, 0).getContents())
                && "月".equals(sheet.getCell(1, 0).getContents())
                && "日".equals(sheet.getCell(2, 0).getContents())
                && "凭证类型".equals(sheet.getCell(3, 0).getContents())
                && "凭证号".equals(sheet.getCell(4, 0).getContents())
                && "附件数".equals(sheet.getCell(5, 0).getContents())
                && "摘 要".equals(sheet.getCell(6, 0).getContents())
                && "科目编码".equals(sheet.getCell(7, 0).getContents())
                && "借 方".equals(sheet.getCell(8, 0).getContents())
                && "贷 方".equals(sheet.getCell(9, 0).getContents())
                && "现金流量项目".equals(sheet.getCell(10, 0).getContents())
                && "部门".equals(sheet.getCell(11, 0).getContents())
                && "供应商编码".equals(sheet.getCell(12, 0).getContents())
                && "客户编码".equals(sheet.getCell(13, 0).getContents())
                && "员工编码".equals(sheet.getCell(14, 0).getContents())
        ) {
        } else {
            throw new Exception("模版不正确!请重新选择");
        }
    }
    public Page<Map<String,String>> xjlllistPage(List<Parameter> parameters, int offset, int limit, String orderby) {
        PageRequest pageRequest = new PageRequest(offset, limit, parameters, orderby);
        int total = dao.xjlllistcount(pageRequest);
        List<Map<String,String>> list = null;
        if(total > 0) {
            list = dao.xjlllistPage(pageRequest);
        }
        return new Page<Map<String,String>>(total, list);
    }
    public static double sub(Double value1, Double value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.subtract(b2).doubleValue();
    }
    public void autoconvert(JSONObject map) throws Exception {
        //获得收入的余额科目发生数及信息
        JSONObject incomedetail = new JSONObject();
        List<Map<String,Object>> incomelist = this.getAccountList(map, "10");
        if (incomelist.size() != 0) {
            incomedetail = addVoucherDetail(incomelist, map,0);
        }
        //获得费用的余额科目发生数及信息
        JSONObject costdetail = new JSONObject();
        List<Map<String,Object>> costlist = this.getAccountList(map, "9");
        if (costlist.size() != 0) {
            String accountid = costlist.get(0).get("uqaccountid").toString();
            String uqglobalperiodid = map.getString("uqglobalperiodid");
            String pmcash= tglVoucherMainsDao.getPmCash(accountid,uqglobalperiodid);
            BigDecimal bd=new BigDecimal(pmcash);
            int i = bd.compareTo(BigDecimal.ZERO);
            if(i==-1){
                costdetail = addVoucherDetail(costlist, map,1);
            }else{
                costdetail = addVoucherDetails(costlist, map);
            }
        }
        //处理 本年利润 与未分配利润
        if (incomedetail.size() != 0 && costdetail.size() != 0) {
            Double incomeMoney = 0.00;
            Double costMoney = 0.00;

            JSONObject decimalfh = new JSONObject();//分户金额


            for (String key : incomedetail.keySet()) {
                //获取收入总金额
                incomeMoney = incomeMoney + Double.valueOf(incomedetail.get(key).toString());
            }

            for (String key : costdetail.keySet()) {
                //获取费用总金额
                costMoney = costMoney + Double.valueOf(costdetail.get(key).toString());
                //获取分户数据
                decimalfh.put(key, sub(Math.abs(Double.valueOf(costdetail.get(key).toString())), Math.abs(Double.valueOf(incomedetail.get(key).toString()))));
            }
            BigDecimal decimal = new BigDecimal(String.valueOf(Math.abs(sub(costMoney, incomeMoney))));
            BigDecimal zero = new BigDecimal(0);

            if (map.getString("unprofitaccount") != null && map.getString("unprofitaccount").length() > 0) {
                this.profitAccount(decimal, decimalfh, zero, map);
            }
        }
        //设置记忆选择的科目信息
//        this.addRemberAccount(map);
    }
    public void profitAccount(BigDecimal decimal, JSONObject decimalfh,BigDecimal zero, JSONObject map) throws Exception {
        List<VoucherDetail> details = new ArrayList<VoucherDetail>();
        String voucherMainID = UUID.randomUUID().toString().toUpperCase();

        VoucherDetail detail1 = new VoucherDetail();
        detail1.setUqvoucherdetailid(UUID.randomUUID().toString().toUpperCase());
        detail1.setUqvoucherid(voucherMainID);
        detail1.setIntseq(1);
        detail1.setUqaccountid(map.getString("profitaccount"));
        detail1.setVarabstract("年末结转");

        VoucherDetail detail2 = new VoucherDetail();
        detail2.setUqvoucherdetailid(UUID.randomUUID().toString().toUpperCase());
        detail2.setUqvoucherid(voucherMainID);
        detail2.setIntseq(2);
        detail2.setUqaccountid(map.getString("unprofitaccount"));
        detail2.setVarabstract("年末结转");

        //本年利润中已做  借-贷 为负数  贷方数大 在本年利润记一笔借方 在未分配利润记一笔贷方
        if (decimal.compareTo(zero) == -1) {
            //本年利润 记借方
            detail1.setMnydebit(decimal.abs());
            detail1.setMnycredit(new BigDecimal(0));
            //未分配 记贷方
            detail2.setMnydebit(new BigDecimal(0));
            detail2.setMnycredit(decimal.abs());
        } else if (decimal.compareTo(zero) == 1)//借-贷 为正数  借方数大 在本年利润记一笔贷方 在未分配利润记一笔借方
        {
            //本年利润 记贷方
            detail1.setMnydebit(new BigDecimal(0));
            detail1.setMnycredit(decimal.abs());
            //未分配 记借方
            detail2.setMnydebit(decimal.abs());
            detail2.setMnycredit(new BigDecimal(0));
        }

        details.add(detail1);
        details.add(detail2);
        //新增凭证明细数据
        this.tglVoucherDetailsDao.saveVoucherDetail(details);
        //新增分户数据
        insertintoLegerss(details, decimalfh, map);

        map.put("mnydebitsum", decimal.abs());
        map.put("mnycreditsum", decimal.abs());
        map.put("uqvoucherid", voucherMainID);

        this.addVoucherMain(map);

    }
    /**
     * 新增未分配利润分户数据
     * @param list
     * @param decimalfh
     * @param map
     */
    public void insertintoLegerss(List<VoucherDetail> list, JSONObject decimalfh,JSONObject map) {
        try {
            for (VoucherDetail voucherDetail : list) {
                //获取成本中心id和所对应的分户id
//                String resultSql = "SELECT tl.UQLEDGETYPEID,tl.UQLEDGEID FROM tgl_account_ledgetype tal  LEFT JOIN tgl_ledger tl ON tl.UQLEDGETYPEID = tal.UQLEDGETYPEID where UQACCOUNTID ='" + voucherDetail.getUqaccountid() + "'";
                List<Map<String, Object>> resultparam = tglVoucherDetailsDao.resultparam(SessionUtils.getUser().getCompanyId(), voucherDetail.getUqaccountid());
                //本年利润期末结转
                if (this.isLrjzAccount(voucherDetail.getUqaccountid())) {
                    //查询会计分户表中是否有对应的关系
//                    String sql = "select count(*) from tgl_account_ledgetype where UQACCOUNTID ='" + voucherDetail.getUqaccountid() + "'";
//                    int result = this.querySingleInteger(sql);
                    List<Parameter> params = Lists.newArrayList();
                    params.add(new Parameter("UQACCOUNTID", "=", voucherDetail.getUqaccountid()));
                    PageRequest pageRequest = new PageRequest(params);
                    int result = accountLedgetypeDao.count(pageRequest);
                    if (result > 0) {
                        //处理分户信息
//                        String strSql = "insert into tgl_voucher_detail_ledgertype(uqvouledgertypeid,uqledgertypeid,";
//                        strSql += " uqvoucherdetailid,uqvoucherid,intseq)";
//                        strSql += " values(?, ?, ?, ?, ?) ";
//                        List<Object> paras = new ArrayList<Object>();
//                        paras.add(UUID.randomUUID().toString());
//                        paras.add(resultparam.get(0).getString("uqledgetypeid"));
                        String detailid = voucherDetail.getUqvoucherdetailid();//生成detailid
//                        paras.add(detailid);
//                        paras.add(voucherDetail.getUqvoucherid());
//                        paras.add(voucherDetail.getIntseq());
//                        this.execute(strSql, paras);
                        TglVoucherDetailLedgertype tglVoucherDetailLedgertype = new TglVoucherDetailLedgertype();
                        tglVoucherDetailLedgertype.setUqvouledgertypeid(UUID.randomUUID().toString());
                        tglVoucherDetailLedgertype.setUqledgertypeid(String.valueOf(resultparam.get(0).get("uqledgetypeid")));
                        tglVoucherDetailLedgertype.setUqvoucherdetailid(voucherDetail.getUqvoucherdetailid());
                        tglVoucherDetailLedgertype.setUqvoucherid(voucherDetail.getUqvoucherid());
                        tglVoucherDetailLedgertype.setIntseq(voucherDetail.getIntseq());
                        tglVoucherDetailLedgertypeDao.insert(tglVoucherDetailLedgertype);
                        //添加分户明细
//                        String Sql = "insert into tgl_voucher_detail_ledger(uqvouledgerid,uqledgerid,";
//                        Sql += " uqvoucherdetailid,uqledgertypeid,mnyamount,intseq,mnydebit,mnycredit)";
//                        Sql += " values(?, ?, ?, ?, ?, ?, ?, ? ) ";
//                        List<Object> parasInfo = new ArrayList<Object>();
                        if (Double.valueOf(voucherDetail.getMnydebit().toString()) != 0.00) {
                            //插入借方分户信息
                            int i = 0;
                            for (String key : decimalfh.keySet()) {
                                i++;
//                                parasInfo.add(UUID.randomUUID().toString());
//                                parasInfo.add(key);
//                                parasInfo.add(detailid);
//                                parasInfo.add(resultparam.get(0).getString("uqledgetypeid"));
//                                parasInfo.add(decimalfh.get(key));
//                                parasInfo.add(i);
//                                parasInfo.add(decimalfh.get(key));
//                                parasInfo.add("0.00");
                                TglVoucherDetailLedger tglVoucherDetailLedger = new TglVoucherDetailLedger();
                                tglVoucherDetailLedger.setUqvouledgerid(UUID.randomUUID().toString());
                                tglVoucherDetailLedger.setUqledgerid(key);
                                tglVoucherDetailLedger.setUqvoucherdetailid(detailid);
                                tglVoucherDetailLedger.setUqledgertypeid(String.valueOf(resultparam.get(0).get("uqledgetypeid")));
                                tglVoucherDetailLedger.setMnyamount((BigDecimal) decimalfh.get(key));
                                tglVoucherDetailLedger.setIntseq(i);
                                tglVoucherDetailLedger.setMnydebit((BigDecimal) decimalfh.get(key));
                                tglVoucherDetailLedger.setMnycredit(new BigDecimal("0.00"));
                                tglVoucherDetailLedgerDao.insert(tglVoucherDetailLedger);
//                                this.execute(Sql, parasInfo);
//                                parasInfo.clear();
                            }
                        } else {
                            //插入贷方分户信息
                            int i = 0;
                            for (String key : decimalfh.keySet()) {
                                i++;
//                                parasInfo.add(UUID.randomUUID().toString());
//                                parasInfo.add(key);
//                                parasInfo.add(detailid);
//                                parasInfo.add(resultparam.get(0).getString("uqledgetypeid"));
//                                parasInfo.add(decimalfh.get(key));
//                                parasInfo.add(i);
//                                parasInfo.add("0.00");
//                                parasInfo.add(decimalfh.get(key));
//                                this.execute(Sql, parasInfo);
//                                parasInfo.clear();
                                TglVoucherDetailLedger tglVoucherDetailLedger = new TglVoucherDetailLedger();
                                tglVoucherDetailLedger.setUqvouledgerid(UUID.randomUUID().toString());
                                tglVoucherDetailLedger.setUqledgerid(key);
                                tglVoucherDetailLedger.setUqvoucherdetailid(detailid);
                                tglVoucherDetailLedger.setUqledgertypeid(String.valueOf(resultparam.get(0).get("uqledgetypeid")));
                                tglVoucherDetailLedger.setMnyamount((BigDecimal) decimalfh.get(key));
                                tglVoucherDetailLedger.setIntseq(i);
                                tglVoucherDetailLedger.setMnydebit(new BigDecimal("0.00"));
                                tglVoucherDetailLedger.setMnycredit((BigDecimal) decimalfh.get(key));
                                tglVoucherDetailLedgerDao.insert(tglVoucherDetailLedger);
                            }
                        }
                    }
                } else {
                    //查询会计分户表中是否有对应的关系
//                    String sql = "select count(*) from tgl_account_ledgetype where UQACCOUNTID ='" + voucherDetail.getUqaccountid() + "'";
//                    int result = this.querySingleInteger(sql);
                    List<Parameter> params = Lists.newArrayList();
                    params.add(new Parameter("UQACCOUNTID", "=", voucherDetail.getUqaccountid()));
                    PageRequest pageRequest = new PageRequest(params);
                    int result = accountLedgetypeDao.count(pageRequest);
                    if (result > 0) {
                        //处理分户信息
//                        String strSql = "insert into tgl_voucher_detail_ledgertype(uqvouledgertypeid,uqledgertypeid,";
//                        strSql += " uqvoucherdetailid,uqvoucherid,intseq)";
//                        strSql += " values(?, ?, ?, ?, ?) ";
//                        List<Object> paras = new ArrayList<Object>();
//                        paras.add(UUID.randomUUID().toString());
//                        paras.add(resultparam.get(0).getString("uqledgetypeid"));
                        String detailid = voucherDetail.getUqvoucherdetailid();//生成detailid
//                        paras.add(detailid);
//                        paras.add(voucherDetail.getUqvoucherid());
//                        paras.add(voucherDetail.getIntseq());
//                        this.execute(strSql, paras);
                        TglVoucherDetailLedgertype tglVoucherDetailLedgertype = new TglVoucherDetailLedgertype();
                        tglVoucherDetailLedgertype.setUqvouledgertypeid(UUID.randomUUID().toString());
                        tglVoucherDetailLedgertype.setUqledgertypeid(String.valueOf(resultparam.get(0).get("uqledgetypeid")));
                        tglVoucherDetailLedgertype.setUqvoucherdetailid(voucherDetail.getUqvoucherdetailid());
                        tglVoucherDetailLedgertype.setUqvoucherid(voucherDetail.getUqvoucherid());
                        tglVoucherDetailLedgertype.setIntseq(voucherDetail.getIntseq());
                        tglVoucherDetailLedgertypeDao.insert(tglVoucherDetailLedgertype);
                        //添加分户明细
                        String Sql = "insert into tgl_voucher_detail_ledger(uqvouledgerid,uqledgerid,";
                        Sql += " uqvoucherdetailid,uqledgertypeid,mnyamount,intseq,mnydebit,mnycredit)";
                        Sql += " values(?, ?, ?, ?, ?, ?, ?, ? ) ";
                        List<Object> parasInfo = new ArrayList<Object>();
                        if (Double.valueOf(voucherDetail.getMnydebit().toString()) != 0.00) {
                            //插入借方分户信息
                            int i = 0;
                            for (String key : decimalfh.keySet()) {
                                i++;
//                                parasInfo.add(UUID.randomUUID().toString());
//                                parasInfo.add(key);
//                                parasInfo.add(detailid);
//                                parasInfo.add(resultparam.get(0).get("uqledgetypeid"));
//                                parasInfo.add(decimalfh.get(key));
//                                parasInfo.add(i);
//                                parasInfo.add("0.00");
//                                parasInfo.add(decimalfh.get(key));
//                                this.execute(Sql, parasInfo);
//                                parasInfo.clear();
                                TglVoucherDetailLedger tglVoucherDetailLedger = new TglVoucherDetailLedger();
                                tglVoucherDetailLedger.setUqvouledgerid(UUID.randomUUID().toString());
                                tglVoucherDetailLedger.setUqledgerid(key);
                                tglVoucherDetailLedger.setUqvoucherdetailid(detailid);
                                tglVoucherDetailLedger.setUqledgertypeid(String.valueOf(resultparam.get(0).get("uqledgetypeid")));
                                tglVoucherDetailLedger.setMnyamount((BigDecimal) decimalfh.get(key));
                                tglVoucherDetailLedger.setIntseq(i);
                                tglVoucherDetailLedger.setMnydebit(new BigDecimal("0.00"));
                                tglVoucherDetailLedger.setMnycredit((BigDecimal) decimalfh.get(key));
                                tglVoucherDetailLedgerDao.insert(tglVoucherDetailLedger);
                            }
                        } else {
                            //插入借方分户信息
                            int i = 0;
                            for (String key : decimalfh.keySet()) {
                                i++;
//                                parasInfo.add(UUID.randomUUID().toString());
//                                parasInfo.add(key);
//                                parasInfo.add(detailid);
//                                parasInfo.add(resultparam.get(0).getString("uqledgetypeid"));
//                                parasInfo.add(decimalfh.get(key));
//                                parasInfo.add(i);
//                                parasInfo.add(decimalfh.get(key));
//                                parasInfo.add("0.00");
//                                this.execute(Sql, parasInfo);
//                                parasInfo.clear();
                                TglVoucherDetailLedger tglVoucherDetailLedger = new TglVoucherDetailLedger();
                                tglVoucherDetailLedger.setUqvouledgerid(UUID.randomUUID().toString());
                                tglVoucherDetailLedger.setUqledgerid(key);
                                tglVoucherDetailLedger.setUqvoucherdetailid(detailid);
                                tglVoucherDetailLedger.setUqledgertypeid(String.valueOf(resultparam.get(0).get("uqledgetypeid")));
                                tglVoucherDetailLedger.setMnyamount((BigDecimal) decimalfh.get(key));
                                tglVoucherDetailLedger.setIntseq(i);
                                tglVoucherDetailLedger.setMnydebit((BigDecimal) decimalfh.get(key));
                                tglVoucherDetailLedger.setMnycredit(new BigDecimal("0.00"));
                                tglVoucherDetailLedgerDao.insert(tglVoucherDetailLedger);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 获得发生数的科目数据
     *
     * @param map
     * @param uqtypeid
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> getAccountList(JSONObject map, String uqtypeid) throws Exception {
        map.put("uqtypeid", uqtypeid);
        Set<String> strings = map.keySet();
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();

        for (String string : strings) {
            objectObjectHashMap.put(string,map.get(string));
        }
        List<Map<String,Object>> list = this.tglVoucherMainsDao.getAccountPeriod(objectObjectHashMap);
        return list;
    }
    public JSONObject addVoucherDetails(List<Map<String,Object>> list, JSONObject map) throws Exception {
        List<VoucherDetail> details = new ArrayList<VoucherDetail>();
        String voucherMainID = UUID.randomUUID().toString().toUpperCase();
        BigDecimal zero = new BigDecimal(0);
        BigDecimal money = new BigDecimal(0);
        BigDecimal money1 = new BigDecimal(0);
        JSONObject mnyamountMap = new JSONObject();
        int tol = 0;
        for (int i = 0; i < list.size(); i++) {
            tol = i + 2;
            String balance = list.get(i).get("balance").toString();
            if (balance != null && !balance.equals("0.00") && !balance.equals("0")) {
                VoucherDetail detail = new VoucherDetail();
                BigDecimal mny = new BigDecimal(balance);//借-贷
                money = money.add(mny);// 累加数据
                money1 = money1.add( mny);// 累加数据
                String uqaccountid = list.get(i).get("uqaccountid").toString();
                detail.setUqvoucherdetailid(UUID.randomUUID().toString().toUpperCase());
                detail.setUqvoucherid(voucherMainID);
                detail.setUqaccountid(uqaccountid);
                detail.setVarabstract("期末结转");
                if (mny.compareTo(zero) < 0)//小于0	借-贷
                {
                    //贷方数据大  要在该科目 做一笔借方的 数据
                    detail.setMnydebit(mny.abs());
                    detail.setMnycredit(new BigDecimal(0));
                    detail.setIntseq(tol);
//                     //针对数据凭证分录科目几金额为负数，特殊制结转凭证
//                    detail.setMnydebit(new BigDecimal(0));
//                    detail.setMnycredit(mny);
//                    detail.setIntseq(tol);
                } else if (mny.compareTo(zero) > 0)//大于0	借-贷
                {
                    //借方数据大		要在该科目 做一笔贷方的 数据
                    detail.setMnydebit(new BigDecimal(0));
                    detail.setMnycredit(mny.abs());
                    detail.setIntseq(tol);
                }
                details.add(detail);
            }
        }
        //处理本年利润科目的明细
        if (money.compareTo(zero) != 0) {
            VoucherDetail detail = new VoucherDetail();
            if (money.compareTo(zero) < 0)//小于0  总计	借-贷
            {
                //贷方数据大  要在该科目 做一笔借方的 数据 再累计科目中记录一笔 贷方的
                detail.setMnydebit(new BigDecimal(0));
                detail.setMnycredit(money.abs());
                detail.setIntseq(1);
            } else if (money.compareTo(zero) > 0)//大于0  总计	借-贷
            {
                //借方数据大		要在该科目 做一笔贷方的 数据  再累计科目中记录一笔 借方的
                detail.setMnydebit(money.abs());
                detail.setMnycredit(new BigDecimal(0));
                detail.setIntseq(1);
            }
            detail.setUqvoucherdetailid(UUID.randomUUID().toString().toUpperCase());
            detail.setUqvoucherid(voucherMainID);
            detail.setUqaccountid(map.getString("profitaccount"));
            detail.setVarabstract("期末结转");
            details.add(detail);

            //新增凭证明细数据
            this.tglVoucherDetailsDao.saveVoucherDetail(details);

            //新增分户数据
            mnyamountMap = insertintoLegers(details, map);

            map.put("mnydebitsum", money.abs());
            map.put("mnydebitsum1", money1);
            map.put("mnycreditsum", money.abs());
            map.put("mnycreditsum1", money1);
            map.put("uqvoucherid", voucherMainID);

            //新增凭证主表数据
            this.addVoucherMain(map);
        }

        return mnyamountMap;
    }
    public void addVoucherMain(JSONObject map) throws Exception {
        TglVoucherMains main = new TglVoucherMains();
        //查删改是传递主键过来的
        main.setUqvoucherid(map.getString("uqvoucherid"));
        main.setUqnumbering("CF10100D-ED35-4B1D-AAC7-235F68DFB117");

        String uqcompanyid = ObjectUtils.toString(SessionUtils.getUser().getCompanyId());
        int num= tglVoucherMainsDao.selectSeq( uqcompanyid, map.getString("uqglobalperiodid"),main.getUqnumbering());

        main.setIntcompanyseq(num);
        main.setIntvouchernum("费用结转"+"-"+num);
        main.setUqcompanyid(uqcompanyid);
        main.setIntaffix(2);
        TglGlobalPeriods tglGlobalPeriods = new TglGlobalPeriods();
        String uqglobalperiodid = map.getString("uqglobalperiodid");
        tglGlobalPeriods.setUqglobalperiodid(uqglobalperiodid);
        main.setUqglobalperiodid(tglGlobalPeriods);
        main.setMnydebitsum(new BigDecimal(map.getString("mnydebitsum")));
        main.setMnycreditsum(new BigDecimal(map.getString("mnycreditsum")));
        SysUser sysUser = new SysUser();
        main.setUqaccountantid(sysUser);
        main.setUqcasherid(sysUser);
        main.setUqcheckerid(sysUser);
        String userid = ObjectUtils.toString(SessionUtils.getUser().getId());
        SysUser sysUser1 = new SysUser();
        sysUser1.setId(userid);
        main.setUqfillerid(sysUser1);
        //获取账期内最后一天日期
        String lastday=this.tglVoucherMainsDao.getLastDayOfMonth(SessionUtils.getUser().getCompanyId(),uqglobalperiodid);

        //显示用制证时间(即月末)
        main.setDtfiller(lastday);
        //显示用交易时间
        main.setChargeagainstsubmitodd(lastday);
        //修改凭证状态
        SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dtfillersrv = simpleformat.format(date);
        main.setDtfillersrv(dtfillersrv);
        //0为保存凭证标志
//        voumaindao.saveVoucherMain(main,"0");
        main.setSubmitodd(String.valueOf(0));
        // boolean iscash = this.voumaindao.isCash(main.getUqvoucherid());
        boolean iscash = isCash(main.getUqnumbering());
        String intiscash = "0";
        if (iscash) {
            intiscash = "1";
            main.setIntcashflag(1);
        }else {
            main.setIntcashflag(0);
        }
        SysUser sysUser2 = new SysUser();
        sysUser2.setId(SessionUtils.getUser().getId());
        main.setUqfillerid(sysUser2);
        main.setIntflag(0);

        //更新主表中标志字段(主要维护是否出纳字段)   main.getDtfiller()真实制证时间，0保存凭证
//        this.voumaindao.updateVoucherState("0", intiscash, main.getUqvoucherid(), m8session.getUserID(), main.getDtfiller(), "");

//        //流水号更新，初始值为-999999 每条主信息都递增一下流水号
//        this.voumaindao.updateVoucherCompanySeq(main.getUqvoucherid(), main.getUqcompanyid(), main.getUqglobalperiodid());

        //处理科目余额表的数据 主要用于结转后 平掉余额表金额
        this.handlerAccountPeriod(map, 1, 1);
    }
    public JSONObject addVoucherDetail(List<Map<String,Object>> list, JSONObject map,int flag) throws Exception {
        List<VoucherDetail> details = new ArrayList<VoucherDetail>();
        String voucherMainID = UUID.randomUUID().toString().toUpperCase();
        BigDecimal zero = new BigDecimal(0);
        BigDecimal money = new BigDecimal(0);
        JSONObject mnyamountMap = new JSONObject();
        int tol = 0;
        for (int i = 0; i < list.size(); i++) {
            tol = tol + 1;
            BigDecimal balance = new BigDecimal(list.get(i).get("balance").toString()) ;
            if (balance != null && !balance.toString().equals("0.00") && !balance.toString().equals("0")) {
                VoucherDetail detail = new VoucherDetail();
                BigDecimal mny = balance;//借-贷
                money =money.add(mny);// 累加数据
                String uqaccountid = list.get(i).get("uqaccountid").toString();
                detail.setUqvoucherdetailid(UUID.randomUUID().toString().toUpperCase());
                detail.setUqvoucherid(voucherMainID);
//                detail.setIntseq(tol);
                detail.setUqaccountid(uqaccountid);
                detail.setVarabstract("期末结转");

                if (mny.compareTo(zero) == -1)//小于0	借-贷
                {
                    //贷方数据大  要在该科目 做一笔借方的 数据

                    if(flag==1){
                        detail.setMnycredit(mny);
                        detail.setMnydebit(new BigDecimal(0));
                    }else{
                        detail.setMnydebit(new BigDecimal(0));
                        detail.setMnycredit(mny.abs());
                    }
                    detail.setIntseq(tol);
                } else if (mny.compareTo(zero) == 1)//大于0	借-贷
                {
                    //借方数据大		要在该科目 做一笔贷方的 数据
                    if(flag==1){
                        detail.setMnycredit(new BigDecimal(0));
                        detail.setMnydebit(mny);
                    }else{
                        detail.setMnydebit(mny.abs());
                        detail.setMnycredit(new BigDecimal(0));
                    }
                    detail.setIntseq(tol);
                }
                details.add(detail);
            }
        }
        //处理本年利润科目的明细
        if (money.compareTo(zero) != 0) {
            VoucherDetail detail = new VoucherDetail();
            if (money.compareTo(zero) == -1)//小于0  总计	借-贷
            {
                //贷方数据大  要在该科目 做一笔借方的 数据 再累计科目中记录一笔 贷方的
                if(flag==1){
                    detail.setMnycredit(new BigDecimal(0));
                    detail.setMnydebit(money);
                }else{
                    detail.setMnydebit(money.abs());
                    detail.setMnycredit(new BigDecimal(0));
                }
                detail.setIntseq(tol + 2);
            } else if (money.compareTo(zero) == 1)//大于0  总计	借-贷
            {
                //借方数据大		要在该科目 做一笔贷方的 数据  再累计科目中记录一笔 借方的
                if(flag==1){

                    detail.setMnycredit(money);
                    detail.setMnydebit(new BigDecimal(0));
                }else{
                    detail.setMnydebit(new BigDecimal(0));
                    detail.setMnycredit(money.abs());
                }
                detail.setIntseq(tol + 2);
            }
            detail.setUqvoucherdetailid(UUID.randomUUID().toString().toUpperCase());
            detail.setUqvoucherid(voucherMainID);
//            detail.setIntseq(tol + 1);
            detail.setUqaccountid(String.valueOf(map.get("profitaccount")));
            detail.setVarabstract("期末结转");
            details.add(detail);

            //新增凭证明细数据
            this.tglVoucherDetailsDao.saveVoucherDetail(details);

            //新增分户数据
            mnyamountMap = insertintoLegers(details, map);
            if (flag==1) {
                map.put("mnydebitsum", money);
                map.put("mnycreditsum", money);
            }else {
                map.put("mnydebitsum", money.abs());
                map.put("mnycreditsum", money.abs());
            }
            if(flag==1){
                map.put("mnydebitsum1", money);
                map.put("mnycreditsum1", money);
            }else{
                map.put("mnydebitsum1", money);
                map.put("mnycreditsum1", money);
            }
            map.put("uqvoucherid", voucherMainID);
            //新增凭证主表数据
            this.saddVoucherMain(map,flag);
        }

        return mnyamountMap;
    }
    public void saddVoucherMain(JSONObject map,int flag) throws Exception {
        TglVoucherMains main = new TglVoucherMains();
//        VoucherMain main = new VoucherMain();
        //查删改是传递主键过来的
        main.setUqvoucherid(map.getString("uqvoucherid"));

        main.setUqnumbering("CF10100D-ED35-4B1D-AAC7-235F68DFB117");
//        M8Session m8session = new M8Session();
        String uqcompanyid = ObjectUtils.toString(SessionUtils.getUser().getCompanyId());
        int num= tglVoucherMainsDao.selectSeq( uqcompanyid, map.getString("uqglobalperiodid"),main.getUqnumbering());
        if(flag==1){
            main.setIntvouchernum("特殊费用结转"+"-"+num);
        }else{
            main.setIntvouchernum("收入结转"+"-"+num);
        }
        main.setIntcompanyseq(num);
        main.setUqcompanyid(uqcompanyid);
        main.setIntaffix(2);
        String uqglobalperiodid = map.getString("uqglobalperiodid");
        TglGlobalPeriods tglGlobalPeriods = new TglGlobalPeriods();
        tglGlobalPeriods.setUqglobalperiodid(uqglobalperiodid);
        main.setUqglobalperiodid(tglGlobalPeriods);
        if(flag==1){
//            main.setMnydebitsum(new BigDecimal(map.getString("mnycreditsum")));
//            main.setMnycreditsum(new BigDecimal(map.getString("mnydebitsum")));
            main.setMnydebitsum(new BigDecimal(map.getString("mnydebitsum")));
            main.setMnycreditsum(new BigDecimal(map.getString("mnycreditsum")));
        }else{
            main.setMnydebitsum(new BigDecimal(map.getString("mnydebitsum")));
            main.setMnycreditsum(new BigDecimal(map.getString("mnycreditsum")));
        }
        SysUser sysUser = new SysUser();
        main.setUqaccountantid(sysUser);
        main.setUqcasherid(sysUser);
        main.setUqcheckerid(sysUser);
        sysUser=new SysUser();
        String userid = ObjectUtils.toString(SessionUtils.getUser().getId());
        sysUser.setId(userid);
        main.setUqfillerid(sysUser);
        //获取账期内最后一天日期
        String lastday=this.tglVoucherMainsDao.getLastDayOfMonth(SessionUtils.getUser().getCompanyId(),uqglobalperiodid);
        //显示用制证时间(即月末)
        main.setDtfiller(lastday);
        //存交易时间
        main.setChargeagainstsubmitodd(lastday);
        //修改凭证状态
        SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dtfillersrv = simpleformat.format(date);
        main.setDtfillersrv(dtfillersrv);
        //0为保存凭证标志
//        voumaindao.saveVoucherMain(main,"0");
        main.setSubmitodd(String.valueOf(0));

        // boolean iscash = this.voumaindao.isCash(main.getUqvoucherid());
        boolean iscash = isCash(main.getUqnumbering());

        String intiscash = "0";
        if (iscash) {
            intiscash = "1";
            main.setIntcashflag(1);
        }else {
            main.setIntcashflag(0);
        }
        SysUser sysUser1 = new SysUser();
        sysUser1.setId(SessionUtils.getUser().getId());
        main.setUqfillerid(sysUser1);
        main.setIntflag(0);
        //更新主表中标志字段(主要维护是否出纳字段)   main.getDtfiller()真实制证时间，0保存凭证
//       updateVoucherState("0", intiscash, main.getUqvoucherid(), m8session.getUserID(), main.getDtfiller(), "");
        tglVoucherMainsDao.insert(main);
//        //流水号更新，初始值为-999999 每条主信息都递增一下流水号
//        this.voumaindao.updateVoucherCompanySeq(main.getUqvoucherid(), main.getUqcompanyid(), main.getUqglobalperiodid());

        //处理科目余额表的数据 主要用于结转后 平掉余额表金额
        this.shandlerAccountPeriod(map, 1, 1);
    }
    public void shandlerAccountPeriod(JSONObject map, int tag, int flag) {
        //1.获取凭证分录的分组信息，按照科目，单位，会计期
        List<Map<String, Object>> list = null;

        if (tag == 2) {
            list = this.tglVoucherMainsDao.getAccountPeriodCashInfo(map.getString("uqvoucherid"),SessionUtils.getUser().getCompanyId());
        } else {
            list = this.tglVoucherMainsDao.getAccountPeriodInfo(map.getString("uqvoucherid"),SessionUtils.getUser().getCompanyId());
        }

//        for (int i = 0; i < list.size(); i++) {
        //1.获取余额表所需信息
//            Object[] obj = (Object[]) list.get(i);
//            String uqaccountid = obj[0].toString();
//            String uqglobalperiodid = obj[1].toString();
//            String uqcompanyid = obj[2].toString();
//            BigDecimal accdebitsum = new BigDecimal(obj[3].toString());
//            BigDecimal acccreditsum = new BigDecimal(obj[4].toString());

        BigDecimal mnydebitsum1 = new BigDecimal(map.getString("mnydebitsum1"));
        BigDecimal mnycreditsum1 = new BigDecimal(map.getString("mnycreditsum1"));
        for (int i = 0; i < list.size(); i++) {
            BigDecimal accdebitsum = new BigDecimal(0);
            BigDecimal acccreditsum = new BigDecimal(0);
            //1.获取余额表所需信息
            Map<String, Object> obj =  list.get(i);
            String uqaccountid = obj.get("uqaccountid").toString();
            String uqglobalperiodid = obj.get("uqglobalperiodid").toString();
            String uqcompanyid = obj.get("uqcompanyid").toString();
            /**
             * 2022年4月28日 17:13:57
             * 我一直在想为什么这里面判断正负的业务这么复杂
             * 我在先这里的查出来的数据是正就放正 是负就放负
             * 后面删除逻辑也就正常呀
             * 特殊的也不用处理
             */
            accdebitsum = new BigDecimal(obj.get("accdebitsum").toString());
            acccreditsum = new BigDecimal(obj.get("acccreditsum").toString());
//            if (flags==1){
//                accdebitsum = new BigDecimal(obj[3].toString());
//                acccreditsum = new BigDecimal(obj[4].toString());
//            }else {
//            if (mnycreditsum1.doubleValue()>=0) {
//                acccreditsum = new BigDecimal(obj[4].toString());
//                accdebitsum = new BigDecimal(obj[3].toString());
//            }else if (mnycreditsum1.doubleValue()<0&&new BigDecimal(obj[4].toString()).doubleValue()==0) { //
//                accdebitsum = new BigDecimal(obj[3].toString());
////                accdebitsum = accdebitsum.multiply(BigDecimal.valueOf(-1));
//                acccreditsum = new BigDecimal(obj[4].toString());
////                acccreditsum = acccreditsum.multiply(BigDecimal.valueOf(-1));
//            }else if (mnycreditsum1.doubleValue()<0&&new BigDecimal(obj[4].toString()).doubleValue()!=0) {
//                accdebitsum = new BigDecimal(obj[3].toString());
//                accdebitsum = accdebitsum.multiply(BigDecimal.valueOf(-1));
//                acccreditsum = new BigDecimal(obj[4].toString());
//                acccreditsum = acccreditsum.multiply(BigDecimal.valueOf(-1));
//            }
//            }
            BigDecimal accfdebitsum = new BigDecimal(obj.get("accfdebitsum").toString());
            BigDecimal accfcreditsum = new BigDecimal(obj.get("accfcreditsum").toString());
            //2.锁掉余额表
            @SuppressWarnings("rawtypes")
            List lockList = this.tglVoucherMainsDao.lockAccountPeriod(uqaccountid, uqglobalperiodid, uqcompanyid);
            int lockline = lockList.size();

            //3.更新余额表
           supdateAccountPeriod(lockline, uqaccountid, uqglobalperiodid, uqcompanyid,
                    accdebitsum, acccreditsum, accfdebitsum, accfcreditsum, tag, flag);
        }
    }

    public JSONObject insertintoLegers(List<VoucherDetail> list, JSONObject map) {
        try {
            JSONObject mnyamountMap = new JSONObject();//金额和分户id
            for (VoucherDetail voucherDetail : list) {
                //获取成本中心id和所对应的分户id
//                String resultSql = "SELECT tl.UQLEDGETYPEID,tl.UQLEDGEID FROM tgl_account_ledgetype tal  LEFT JOIN tgl_ledger tl ON tl.UQLEDGETYPEID = tal.UQLEDGETYPEID and tl.tenant_id=? where UQACCOUNTID ='" + voucherDetail.getUqaccountid() + "'";
//                List<Map<String,String>> resultparam = this.getMapList(resultSql, new Object[]{SessionUtils.getUser().getCompanyId()});
                List<Map<String, Object>> resultparam = tglVoucherDetailsDao.resultparam(SessionUtils.getUser().getCompanyId(), voucherDetail.getUqaccountid());
                //判断是否为本年利润期末结转
                if (this.isLrjzAccount(voucherDetail.getUqaccountid())) {//科目id
                    //查询会计分户表中是否有对应的关系
//                    String sql = "select count(*) from tgl_account_ledgetype where UQACCOUNTID ='" + voucherDetail.getUqaccountid() + "'";
//                    int result = this.querySingleInteger(sql);

                    List<Parameter> params = Lists.newArrayList();
                    params.add(new Parameter("UQACCOUNTID", "=", voucherDetail.getUqaccountid()));
                    PageRequest pageRequest = new PageRequest(params);
                    int result = accountLedgetypeDao.count(pageRequest);

                    if (result > 0) {
                        //处理分户信息
//                        String strSql = "insert into tgl_voucher_detail_ledgertype(uqvouledgertypeid,uqledgertypeid,";
//                        strSql += " uqvoucherdetailid,uqvoucherid,intseq)";
//                        strSql += " values(?, ?, ?, ?, ?) ";
//                        List<Object> paras = new ArrayList<Object>();
//                        paras.add(UUID.randomUUID().toString());
//                        paras.add(resultparam.get(0).get("uqledgetypeid"));
                      //生成detailid
//                        paras.add(detailid);
//                        paras.add(voucherDetail.getUqvoucherid());
//                        paras.add(voucherDetail.getIntseq());
//                        this.execute(strSql, paras);
                        //添加分户明细
                        TglVoucherDetailLedgertype tglVoucherDetailLedgertype = new TglVoucherDetailLedgertype();
                        tglVoucherDetailLedgertype.setUqvouledgertypeid(UUID.randomUUID().toString());
                        tglVoucherDetailLedgertype.setUqledgertypeid(String.valueOf(resultparam.get(0).get("uqledgetypeid")));
                        tglVoucherDetailLedgertype.setUqvoucherdetailid(voucherDetail.getUqvoucherdetailid());
                        tglVoucherDetailLedgertype.setUqvoucherid(voucherDetail.getUqvoucherid());
                        tglVoucherDetailLedgertype.setIntseq(voucherDetail.getIntseq());
                        tglVoucherDetailLedgertypeDao.insert(tglVoucherDetailLedgertype);
//                        String Sql = "insert into tgl_voucher_detail_ledger(uqvouledgerid,uqledgerid,";
//                        Sql += " uqvoucherdetailid,uqledgertypeid,mnyamount,intseq,mnydebit,mnycredit)";
//                        Sql += " values(?, ?, ?, ?, ?, ?, ?, ? ) ";

//                        List<Object> parasInfo = new ArrayList<Object>();
                        int i = 0;//为了获取循环次数作为序号
                        String detailid = voucherDetail.getUqvoucherdetailid();
                        for (String key : mnyamountMap.keySet()) {


                            TglVoucherDetailLedger tglVoucherDetailLedger = new TglVoucherDetailLedger();
                            tglVoucherDetailLedger.setUqvouledgerid(UUID.randomUUID().toString());
                            tglVoucherDetailLedger.setUqledgerid(key);
                            tglVoucherDetailLedger.setUqvoucherdetailid(detailid);
                            tglVoucherDetailLedger.setUqledgertypeid(String.valueOf(resultparam.get(0).get("uqledgetypeid")));
                            i++;
//                            parasInfo.add(UUID.randomUUID().toString());
//                            parasInfo.add(key);//分户id
//                            parasInfo.add(detailid);//明细id
//                            parasInfo.add(resultparam.get(0).get("uqledgetypeid"));
                            //判断金额是贷方金额还是借方金额，借方金额大于0，贷方金额小于0，结转后贷方和借方方向相反
                            if (Double.valueOf(mnyamountMap.get(key).toString()) > 0) {
                                tglVoucherDetailLedger.setMnyamount(BigDecimal.valueOf(-1 * Math.abs(Double.valueOf(String.valueOf(mnyamountMap.get(key))))));
                                tglVoucherDetailLedger.setIntseq(i - 1);
                                tglVoucherDetailLedger.setMnydebit(BigDecimal.valueOf(-1 * Math.abs(Double.valueOf(String.valueOf(mnyamountMap.get(key))))));
                                tglVoucherDetailLedger.setMnycredit(new BigDecimal(0.00));
//                                parasInfo.add(-1 * Math.abs(Double.valueOf(String.valueOf(mnyamountMap.get(key)))));//金额
//                                parasInfo.add(i - 1);//序号
//                                parasInfo.add(-1 * Math.abs(Double.valueOf(String.valueOf(mnyamountMap.get(key)))));//借方金额
//                                parasInfo.add("0.00");//贷方金额
                                mnyamountMap.put(key, -1 * Math.abs(Double.valueOf(String.valueOf(mnyamountMap.get(key)))));
                            } else {
                                tglVoucherDetailLedger.setMnyamount(BigDecimal.valueOf(Math.abs(Double.valueOf(String.valueOf(mnyamountMap.get(key))))));
                                tglVoucherDetailLedger.setIntseq(i - 1);
                                tglVoucherDetailLedger.setMnydebit(new BigDecimal(0.00));
                                tglVoucherDetailLedger.setMnycredit(BigDecimal.valueOf(Math.abs(Double.valueOf(String.valueOf(mnyamountMap.get(key))))));
//                                parasInfo.add(Math.abs(Double.valueOf(String.valueOf(mnyamountMap.get(key)))));
//                                parasInfo.add(i - 1);
//                                parasInfo.add("0.00");//借方金额
//                                parasInfo.add(Math.abs(Double.valueOf(String.valueOf(mnyamountMap.get(key)))));//贷方金额
                                mnyamountMap.put(key, Math.abs(Double.valueOf(String.valueOf(mnyamountMap.get(key)))));
                            }
                            tglVoucherDetailLedgerDao.insert(tglVoucherDetailLedger);
//                            this.execute(Sql, parasInfo);
//                            parasInfo.clear();
                        }
                        /*String resultSql = "select uqledgeid from tgl_ledger where uqledgetypeid = 'E4C4035C-04B5-42B5-AB02-BC6709CBFE31'";
                        List<EntityMap> resultparam = this.getMapList(resultSql);*/
                        //插入所有分户id和，值为0
                        for (int j = 0; j < resultparam.size(); j++) {
                            if (mnyamountMap.containsKey(resultparam.get(j).get("uqledgeid")) == false) {
                                mnyamountMap.put(String.valueOf(resultparam.get(j).get("uqledgeid")), 0.00);
                            }
                        }
                    }
                } else {
                    //查询会计分户表中是否有对应的关系
//                    String sql = "select count(*) from tgl_account_ledgetype where UQACCOUNTID ='" + voucherDetail.getUqaccountid() + "'";
//                    int result = this.querySingleInteger(sql);
                    List<Parameter> params = Lists.newArrayList();
                    params.add(new Parameter("UQACCOUNTID", "=", voucherDetail.getUqaccountid()));
                    PageRequest pageRequest = new PageRequest(params);
                    int result = accountLedgetypeDao.count(pageRequest);
                    if (result > 0) {
//                        //处理分户信息
//                        String strSql = "insert into tgl_voucher_detail_ledgertype(uqvouledgertypeid,uqledgertypeid,";//插入凭证分录分户类别表
//                        strSql += " uqvoucherdetailid,uqvoucherid,intseq)";
//                        strSql += " values(?, ?, ?, ?, ?) ";
//                        List<Object> paras = new ArrayList<Object>();
//                        paras.add(UUID.randomUUID().toString());
//                        paras.add(resultparam.get(0).get("uqledgetypeid"));//成本中心id
                        String detailid = voucherDetail.getUqvoucherdetailid();//生成detailid
//                        paras.add(detailid);
//                        paras.add(voucherDetail.getUqvoucherid());//凭证id
//                        paras.add(voucherDetail.getIntseq());//序号
//                        this.execute(strSql, paras);
                        TglVoucherDetailLedgertype tglVoucherDetailLedgertype = new TglVoucherDetailLedgertype();
                        tglVoucherDetailLedgertype.setUqvouledgertypeid(UUID.randomUUID().toString());
                        tglVoucherDetailLedgertype.setUqledgertypeid(String.valueOf(resultparam.get(0).get("uqledgetypeid")));
                        tglVoucherDetailLedgertype.setUqvoucherdetailid(detailid);
                        tglVoucherDetailLedgertype.setUqvoucherid(voucherDetail.getUqvoucherid());
                        tglVoucherDetailLedgertype.setIntseq(voucherDetail.getIntseq());
                        tglVoucherDetailLedgertypeDao.insert(tglVoucherDetailLedgertype);
                        //添加分户明细
//                        String Sql = "insert into tgl_voucher_detail_ledger(uqvouledgerid,uqledgerid,";
//                        Sql += " uqvoucherdetailid,uqledgertypeid,mnyamount,intseq,mnydebit,mnycredit)";
//                        Sql += " values(?, ?, ?, ?, ?, ?, ?, ? ) ";
//                        List<Object> parasInfo = new ArrayList<Object>();
//                        String string = "SELECT td.* FROM tgl_voucher_detail_ledger td INNER JOIN tgl_voucher_details d ON td.UQVOUCHERDETAILID = d.UQVOUCHERDETAILID AND d.UQACCOUNTID = '" + voucherDetail.getUqaccountid() + "' INNER JOIN tgl_voucher_mains m ON m.UQVOUCHERID = d.UQVOUCHERID and m.tenant_id=? AND m.UQGLOBALPERIODID = '" + map.get("uqglobalperiodid") + "' AND m.UQNUMBERING != 'CF10100D-ED35-4B1D-AAC7-235F68DFB117' WHERE 1 = 1 and m.INTFLAG = '2' or m.INTCASHFLAG = '2'";
                        List<Map<String, Object>> entityMaps = tglVoucherDetailsDao.getentityMaps(SessionUtils.getUser().getCompanyId(), voucherDetail.getUqaccountid(), String.valueOf(map.get("uqglobalperiodid")));
//                        List<Map<String,Object>> entityMaps = this.getMapList(string, new Object[]{new M8Session().getAttribute("TENANT_ID").toString()});//获取所有分户明细凭证

//                        String paramResult = "SELECT td.UQLEDGERID as UQLEDGERID,sum(td.MNYAMOUNT) as MNYAMOUNT,sum(td.MNYCREDIT)as MNYCREDIT,sum(td.MNYDEBIT) as MNYDEBIT  FROM tgl_voucher_detail_ledger td INNER JOIN tgl_voucher_details d ON td.UQVOUCHERDETAILID = d.UQVOUCHERDETAILID AND d.UQACCOUNTID = '" + voucherDetail.getUqaccountid() + "' INNER JOIN tgl_voucher_mains m ON m.UQVOUCHERID = d.UQVOUCHERID and m.tenant_id=? AND m.UQGLOBALPERIODID = '" + map.get("uqglobalperiodid") + "' AND m.UQNUMBERING != 'CF10100D-ED35-4B1D-AAC7-235F68DFB117' WHERE 1 = 1 and m.INTFLAG = '2' or m.INTCASHFLAG = '2' GROUP BY td.UQLEDGERID";
//                        List<Map<String,Object>> paramMaps = this.getMapList(paramResult, new Object[]{new M8Session().getAttribute("TENANT_ID").toString()});//获取分户凭证汇总

                        List<Map<String, Object>> paramMaps = tglVoucherDetailsDao.getparamResult(SessionUtils.getUser().getCompanyId(), voucherDetail.getUqaccountid(), String.valueOf(map.get("uqglobalperiodid")));
                        //获取分户借贷方金额总数
                        for (int i = 0; i < paramMaps.size(); i++) {

                            String key = paramMaps.get(i).get("uqledgerid").toString();
                            Double mnycreditValue = Double.valueOf(paramMaps.get(i).get("mnycredit").toString());
                            Double mnydebitValue = Double.valueOf(paramMaps.get(i).get("mnydebit").toString());
                            //判断map里是否包含分户id，
                            if (mnyamountMap.containsKey(key)) {
                                mnyamountMap.put(key, Double.valueOf(paramMaps.get(i).get("mnydebit").toString()) + Double.valueOf(mnyamountMap.get(key).toString()));
                                mnyamountMap.put(key, -1 * Double.valueOf(paramMaps.get(i).get("mnycredit").toString()) + Double.valueOf(mnyamountMap.get(key).toString()));
                            } else {
                                mnyamountMap.put(key, mnydebitValue);
                                mnyamountMap.put(key, -1 * mnycreditValue + mnydebitValue);
                            }
                        }
                        for (int i = 0; i < entityMaps.size(); i++) {
                            TglVoucherDetailLedger tglVoucherDetailLedger = new TglVoucherDetailLedger();
                            tglVoucherDetailLedger.setUqvouledgerid(UUID.randomUUID().toString());
                            tglVoucherDetailLedger.setUqledgerid(String.valueOf(entityMaps.get(i).get("uqledgerid")));
                            tglVoucherDetailLedger.setUqvoucherdetailid(detailid);
                            tglVoucherDetailLedger.setUqledgertypeid(String.valueOf(entityMaps.get(i).get("uqledgertypeid")));
//                            parasInfo.add(UUID.randomUUID().toString());
//                            parasInfo.add(entityMaps.get(i).get("uqledgerid"));
//                            parasInfo.add(detailid);
//                            parasInfo.add(entityMaps.get(i).get("uqledgertypeid"));
                            if (entityMaps.size() == 0) {
                                return null;
                            } else {
                                //如果借方为0则插入数据
                                if (entityMaps.get(i).get("mnydebit").equals("0.00") && !entityMaps.get(i).get("mnycredit").equals("0.00")) {
                                    tglVoucherDetailLedger.setMnyamount((BigDecimal) entityMaps.get(i).get("mnyamount"));
                                    tglVoucherDetailLedger.setIntseq(i);
                                    tglVoucherDetailLedger.setMnydebit((BigDecimal) entityMaps.get(i).get("mnyamount"));
                                    tglVoucherDetailLedger.setMnycredit(new BigDecimal("0.00"));
//                                    parasInfo.add(entityMaps.get(i).get("mnyamount"));
//                                    parasInfo.add(i);
//                                    parasInfo.add(entityMaps.get(i).get("mnyamount"));
//                                    parasInfo.add("0.00");
                                } else if (entityMaps.get(i).get("mnycredit").equals("0.00") && !entityMaps.get(i).get("mnydebit").equals("0.00")) {
                                    tglVoucherDetailLedger.setMnyamount((BigDecimal) entityMaps.get(i).get("mnyamount"));
                                    tglVoucherDetailLedger.setIntseq(i);
                                    tglVoucherDetailLedger.setMnydebit(new BigDecimal("0.00"));
                                    tglVoucherDetailLedger.setMnycredit((BigDecimal) entityMaps.get(i).get("mnyamount"));
//                                    parasInfo.add(entityMaps.get(i).get("mnyamount"));
//                                    parasInfo.add(i);
//                                    parasInfo.add("0.00");
//                                    parasInfo.add(entityMaps.get(i).get("mnyamount"));
                                } else {
                                    tglVoucherDetailLedger.setMnyamount((BigDecimal) entityMaps.get(i).get("mnyamount"));
                                    tglVoucherDetailLedger.setIntseq(i);
                                    tglVoucherDetailLedger.setMnydebit((BigDecimal) entityMaps.get(i).get("mnycredit"));
                                    tglVoucherDetailLedger.setMnycredit((BigDecimal) entityMaps.get(i).get("mnydebit"));
//                                    parasInfo.add(entityMaps.get(i).get("mnyamount"));
//                                    parasInfo.add(i);
//                                    parasInfo.add(entityMaps.get(i).get("mnycredit"));
//                                    parasInfo.add(entityMaps.get(i).get("mnydebit"));
                                }
                            }
//                            this.execute(Sql, parasInfo);
                            tglVoucherDetailLedgerDao.insert(tglVoucherDetailLedger);
//                            parasInfo.clear();
                        }
                    }
                }
            }
            return mnyamountMap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    private boolean isLrjzAccount(String accountid) {
        boolean result = true;
        String strSql = "select bulkid from tgl_tmp_idlist where id='PROFITACCOUNT' and tenant_id=?";
//        List<Map<String,String>> lst = this.getMapList(strSql, new Object[]{SessionUtils.getUser().getCompanyId()});
        List<Map<String, Object>> lst = tglVoucherDetailsDao.lst(SessionUtils.getUser().getCompanyId());
        if (lst.size() > 0) {
            Map<String,Object> map = lst.get(0);
            if (map.get("bulkid").equals(accountid)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
