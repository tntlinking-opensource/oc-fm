package com.geeke.cardasset.supplementreduce.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.geeke.assetting.entity.TfamDepmonth;
import com.geeke.assetting.service.TfamDepmonthService;
import com.geeke.cardasset.credit.entity.TfamDepfuture;
import com.geeke.cardasset.credit.service.TfamDepfutureService;
import com.geeke.cardasset.supplementreduce.dao.SysUserOldDao;
import com.geeke.cardasset.supplementreduce.dao.TfamAssetOldDao;
import com.geeke.cardasset.supplementreduce.dao.TfamCategoryOldDao;
import com.geeke.cardasset.supplementreduce.dao.TfamDepmethodOldDao;
import com.geeke.cardasset.supplementreduce.dao.TfamUsingstatusOldDao;
import com.geeke.cardasset.supplementreduce.entity.SysUserOld;
import com.geeke.cardasset.supplementreduce.entity.TfamAssetOld;
import com.geeke.cardasset.supplementreduce.entity.TfamCategoryOld;
import com.geeke.cardasset.supplementreduce.entity.TfamDepmethodOld;
import com.geeke.cardasset.supplementreduce.entity.TfamUsingstatusOld;
import com.geeke.cardasset.supplementreduce.service.SysUserOldService;
import com.geeke.cardasset.supplementreduce.service.TfamCategoryOldService;
import com.geeke.cardasset.supplementreduce.service.TfamDepmethodOldService;
import com.geeke.cardasset.supplementreduce.service.TfamUsingstatusOldService;
import com.geeke.cardasset.supplementreduce.util.BigDecimalUtils;
import com.geeke.cardasset.supplementreduce.util.DepUtils;
import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.fixasset.asset.dao.TfamDepsubDao;
import com.geeke.fixasset.asset.entity.TfamAsset;
import com.geeke.fixasset.asset.entity.TfamDepsub;
import com.geeke.fixasset.asset.service.TfamDepsubService;
import com.geeke.sys.entity.Action;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.utils.Reflections;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.vouchermanager.entity.*;
import com.geeke.vouchermanager.service.TglAccountsService;
import com.geeke.vouchermanager.service.TglVoucherMainsService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

/**
 * 补提冲减Service
 * @author
 * @version
 */

@Service("tfamAssetOldService")
@Transactional(readOnly = false)
public class TfamAssetOldService extends CrudService<TfamAssetOldDao, TfamAssetOld> {

    @Autowired
    private TfamCategoryOldDao tfamCategoryOldDao;

    @Autowired
    private TfamCategoryOldService tfamCategoryOldService;

    @Autowired
    private TfamUsingstatusOldDao tfamUsingstatusOldDao;

    @Autowired
    private TfamUsingstatusOldService tfamUsingstatusOldService;

    @Autowired
    private SysUserOldDao sysUserOldDao;

    @Autowired
    private SysUserOldService sysUserOldService;

    @Autowired
    private TfamDepmethodOldDao tfamDepmethodOldDao;

    @Autowired
    private TfamDepmethodOldService tfamDepmethodOldService;
    @Autowired
    private TfamDepsubService tfamDepsubDao;
    @Autowired
    private TfamDepfutureService tfamDepfutureService;
    @Autowired
    private TfamDepmonthService tfamDepmonthService;
    @Autowired
    private TglVoucherMainsService tglVoucherMainsService;
    @Autowired
    private TglAccountsService tglAccountsService;
    public static int CURRDEPFLAG = 1;//计提额标志位 1为正常需要折旧 0为卡片已经逾龄 计提额为0
    @Override
    public TfamAssetOld get(String id) {
        TfamAssetOld tfamAssetOld = super.get(id);

        List<Parameter> params = null;

        /*获取子表列表   资产类别表*/
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetOld.getId()));
        tfamAssetOld.setTfamCategoryOldList(tfamCategoryOldService.listAll(params, ""));

        /*获取子表列表   使用状况表。对应卡片中usingstatus*/
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetOld.getId()));
        tfamAssetOld.setTfamUsingstatusOldList(tfamUsingstatusOldService.listAll(params, ""));

        /*获取子表列表   用户*/
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetOld.getId()));
        tfamAssetOld.setSysUserOldList(sysUserOldService.listAll(params, ""));

        /*获取子表列表   折旧方法表*/
        params = Lists.newArrayList();

        params.add(new Parameter("code", "=", tfamAssetOld.getId()));
        tfamAssetOld.setTfamDepmethodOldList(tfamDepmethodOldService.listAll(params, ""));

        return tfamAssetOld;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public TfamAssetOld save(TfamAssetOld tfamAssetOld) {
        TfamAssetOld tfamAssetOldTemp = super.save(tfamAssetOld);

        /* 保存子表数据     资产类别表 */
        saveTfamCategoryOldList(tfamAssetOldTemp);

        /* 保存子表数据     使用状况表。对应卡片中usingstatus */
        saveTfamUsingstatusOldList(tfamAssetOldTemp);

        /* 保存子表数据     用户 */
        saveSysUserOldList(tfamAssetOldTemp);

        /* 保存子表数据     折旧方法表 */
        saveTfamDepmethodOldList(tfamAssetOldTemp);

        return tfamAssetOldTemp;
    }

    /**
     * 删除
     * @param tfamAssetOld
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int delete(TfamAssetOld tfamAssetOld) {
        List<Parameter> params = null;
        PageRequest pageRequest;

        /* 处理子表     资产类别表 */
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetOld.getId()));
        pageRequest = new PageRequest(params);
        tfamAssetOld.setTfamCategoryOldList(tfamCategoryOldDao.listAll(pageRequest));

        if (tfamAssetOld.getTfamCategoryOldList() != null && tfamAssetOld.getTfamCategoryOldList().size() > 0) {
            tfamCategoryOldService.bulkDelete(tfamAssetOld.getTfamCategoryOldList());
        }

        /* 处理子表     使用状况表。对应卡片中usingstatus */
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetOld.getId()));
        pageRequest = new PageRequest(params);
        tfamAssetOld.setTfamUsingstatusOldList(tfamUsingstatusOldDao.listAll(pageRequest));

        if (tfamAssetOld.getTfamUsingstatusOldList() != null && tfamAssetOld.getTfamUsingstatusOldList().size() > 0) {
            tfamUsingstatusOldService.bulkDelete(tfamAssetOld.getTfamUsingstatusOldList());
        }

        /* 处理子表     用户 */
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetOld.getId()));
        pageRequest = new PageRequest(params);
        tfamAssetOld.setSysUserOldList(sysUserOldDao.listAll(pageRequest));

        if (tfamAssetOld.getSysUserOldList() != null && tfamAssetOld.getSysUserOldList().size() > 0) {
            sysUserOldService.bulkDelete(tfamAssetOld.getSysUserOldList());
        }

        /* 处理子表     折旧方法表 */
        params = Lists.newArrayList();

        params.add(new Parameter("code", "=", tfamAssetOld.getId()));
        pageRequest = new PageRequest(params);
        tfamAssetOld.setTfamDepmethodOldList(tfamDepmethodOldDao.listAll(pageRequest));

        if (tfamAssetOld.getTfamDepmethodOldList() != null && tfamAssetOld.getTfamDepmethodOldList().size() > 0) {
            tfamDepmethodOldService.bulkDelete(tfamAssetOld.getTfamDepmethodOldList());
        }

        int rows = super.delete(tfamAssetOld);
        return rows;
    }

    /**
     * 批量删除
     * @param tfamAssetOldList
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int bulkDelete(List<TfamAssetOld> tfamAssetOldList) {
        for (TfamAssetOld tfamAssetOld : tfamAssetOldList) {
            delete(tfamAssetOld);
        }

        return tfamAssetOldList.size();
    }

    /**
     * 生成操作日志
     * @param actionTypeId  操作类型Id
     * @param entity        操作的实体对象
     * @return
     */
    @Override
    protected Action createAction(String actionTypeId, TfamAssetOld entity) {
        Action action = super.createAction(actionTypeId, entity);
        if (action == null) {
            return null;
        }
        // 删除时记录把保存的数据保存到回收站
        if (ActionConstants.ACTION_DELETED.equals(actionTypeId)) {
            for (TfamCategoryOld child : entity.getTfamCategoryOldList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TfamUsingstatusOld child : entity.getTfamUsingstatusOldList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (SysUserOld child : entity.getSysUserOldList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TfamDepmethodOld child : entity.getTfamDepmethodOldList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }
        }
        return action;
    }

    /* 保存子表数据     资产类别表 */
    private void saveTfamCategoryOldList(TfamAssetOld tfamAssetOld) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetOld.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TfamCategoryOld> list_TfamCategoryOld = tfamCategoryOldDao.listAll(pageRequest);

        List<TfamCategoryOld> deletes = Lists.newArrayList(); // 删除列表
        List<TfamCategoryOld> inserts = Lists.newArrayList(); // 添加列表
        List<TfamCategoryOld> updates = Lists.newArrayList(); // 更新列表
        for (TfamCategoryOld tfamCategoryOldSaved : list_TfamCategoryOld) {
            boolean found = false;
            for (TfamCategoryOld tfamCategoryOld : tfamAssetOld.getTfamCategoryOldList()) {
                if (tfamCategoryOldSaved.getId().equals(tfamCategoryOld.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tfamCategoryOldSaved);
            }
        }
        if (deletes.size() > 0) {
            tfamCategoryOldService.bulkDelete(deletes);
        }
        for (TfamCategoryOld tfamCategoryOld : tfamAssetOld.getTfamCategoryOldList()) {
            if (StringUtils.isBlank(tfamCategoryOld.getId())) {
                tfamCategoryOld.setId(tfamAssetOld.getId()); // 子表id与父表id一致

                inserts.add(tfamCategoryOld);
            } else {
                updates.add(tfamCategoryOld);
            }
        }
        if (updates.size() > 0) {
            tfamCategoryOldService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tfamCategoryOldService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     使用状况表。对应卡片中usingstatus */
    private void saveTfamUsingstatusOldList(TfamAssetOld tfamAssetOld) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetOld.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TfamUsingstatusOld> list_TfamUsingstatusOld = tfamUsingstatusOldDao.listAll(pageRequest);

        List<TfamUsingstatusOld> deletes = Lists.newArrayList(); // 删除列表
        List<TfamUsingstatusOld> inserts = Lists.newArrayList(); // 添加列表
        List<TfamUsingstatusOld> updates = Lists.newArrayList(); // 更新列表
        for (TfamUsingstatusOld tfamUsingstatusOldSaved : list_TfamUsingstatusOld) {
            boolean found = false;
            for (TfamUsingstatusOld tfamUsingstatusOld : tfamAssetOld.getTfamUsingstatusOldList()) {
                if (tfamUsingstatusOldSaved.getId().equals(tfamUsingstatusOld.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tfamUsingstatusOldSaved);
            }
        }
        if (deletes.size() > 0) {
            tfamUsingstatusOldService.bulkDelete(deletes);
        }
        for (TfamUsingstatusOld tfamUsingstatusOld : tfamAssetOld.getTfamUsingstatusOldList()) {
            if (StringUtils.isBlank(tfamUsingstatusOld.getId())) {
                tfamUsingstatusOld.setId(tfamAssetOld.getId()); // 子表id与父表id一致

                inserts.add(tfamUsingstatusOld);
            } else {
                updates.add(tfamUsingstatusOld);
            }
        }
        if (updates.size() > 0) {
            tfamUsingstatusOldService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tfamUsingstatusOldService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     用户 */
    private void saveSysUserOldList(TfamAssetOld tfamAssetOld) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("id", "=", tfamAssetOld.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<SysUserOld> list_SysUserOld = sysUserOldDao.listAll(pageRequest);

        List<SysUserOld> deletes = Lists.newArrayList(); // 删除列表
        List<SysUserOld> inserts = Lists.newArrayList(); // 添加列表
        List<SysUserOld> updates = Lists.newArrayList(); // 更新列表
        for (SysUserOld sysUserOldSaved : list_SysUserOld) {
            boolean found = false;
            for (SysUserOld sysUserOld : tfamAssetOld.getSysUserOldList()) {
                if (sysUserOldSaved.getId().equals(sysUserOld.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(sysUserOldSaved);
            }
        }
        if (deletes.size() > 0) {
            sysUserOldService.bulkDelete(deletes);
        }
        for (SysUserOld sysUserOld : tfamAssetOld.getSysUserOldList()) {
            if (StringUtils.isBlank(sysUserOld.getId())) {
                sysUserOld.setId(tfamAssetOld.getId()); // 子表id与父表id一致

                inserts.add(sysUserOld);
            } else {
                updates.add(sysUserOld);
            }
        }
        if (updates.size() > 0) {
            sysUserOldService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            sysUserOldService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     折旧方法表 */
    private void saveTfamDepmethodOldList(TfamAssetOld tfamAssetOld) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("code", "=", tfamAssetOld.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TfamDepmethodOld> list_TfamDepmethodOld = tfamDepmethodOldDao.listAll(pageRequest);

        List<TfamDepmethodOld> deletes = Lists.newArrayList(); // 删除列表
        List<TfamDepmethodOld> inserts = Lists.newArrayList(); // 添加列表
        List<TfamDepmethodOld> updates = Lists.newArrayList(); // 更新列表
        for (TfamDepmethodOld tfamDepmethodOldSaved : list_TfamDepmethodOld) {
            boolean found = false;
            for (TfamDepmethodOld tfamDepmethodOld : tfamAssetOld.getTfamDepmethodOldList()) {
                if (tfamDepmethodOldSaved.getId().equals(tfamDepmethodOld.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tfamDepmethodOldSaved);
            }
        }
        if (deletes.size() > 0) {
            tfamDepmethodOldService.bulkDelete(deletes);
        }
        for (TfamDepmethodOld tfamDepmethodOld : tfamAssetOld.getTfamDepmethodOldList()) {
            if (StringUtils.isBlank(tfamDepmethodOld.getId())) {
                tfamDepmethodOld.setCode(tfamAssetOld.getDepmethod().getCode());

                inserts.add(tfamDepmethodOld);
            } else {
                updates.add(tfamDepmethodOld);
            }
        }
        if (updates.size() > 0) {
            tfamDepmethodOldService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tfamDepmethodOldService.bulkInsert(inserts);
        }
    }
    public JSONObject getBusDate() throws Exception
    {
        String companyid = SessionUtils.getUser().getCompanyId();
        Map<String,Object> depMap = this.dao.getBusDate(companyid);
        Map<String,Object> startMap = this.dao.getSysStartdate(companyid);
        //这里直接查询数据库
        Map<String,Object> depreciationTimeMap = this.dao.getDepreciationTime(companyid);
        Object o = depreciationTimeMap.get("value");
        int depreciation_time = Integer.parseInt(String.valueOf(o));//0月初，1月末
//        int depreciation_time = 1;//0月初，1月末
        JSONObject busDate = new JSONObject();
        busDate.put("depreciation_time",depreciation_time);
        String depDate = depMap.get("value").toString();
        if (depreciation_time == 0)
        {
            busDate.put("year",depDate.substring(0,4));
            busDate.put("month",depDate.substring(4));
        }
        else
        {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
            Date date = df.parse(depDate);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, 1);
            Date newdate = calendar.getTime();
            String dateString =  df.format(newdate);
            busDate.put("year",dateString.substring(0,4));
            busDate.put("month",dateString.substring(4));
        }
        String startDate = startMap.get("value").toString();
        busDate.put("startyear",startDate.substring(0,startDate.indexOf('-')));
        busDate.put("startmonth",startDate.substring(startDate.indexOf('-')+1));
        JSONObject nextMonth = this.getNextMonth(busDate.getString("year"), busDate.getString("month"));
        busDate.put("nextyear",nextMonth.getString("year"));
        busDate.put("nextmonth",nextMonth.getString("month"));
        return busDate;
    }
    public JSONObject getNextMonth(String year, String month) throws Exception
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
        Date date = df.parse(year+month);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        Date newdate = calendar.getTime();
        String dateString =  df.format(newdate);

        JSONObject nextMonth = new JSONObject();
        nextMonth.put("year", dateString.substring(0, 4));
        nextMonth.put("month", dateString.substring(4));
        return nextMonth;
    }
    public void supplementDep(JSONObject cdtMap, String[] ids) throws Exception
    {
        cdtMap.putAll(cdtMap.getJSONObject("jsonCondition"));
        int supplementNumber = cdtMap.getString("supplementNumber").equals("")?0: cdtMap.getIntValue("supplementNumber");
        double supplementMoney = cdtMap.getString("supplementMoney").equals("")?0:cdtMap.getDouble("supplementMoney");
        String supplementReason = cdtMap.getString("supplementReason");
        String creatorid = cdtMap.getString("creatorid");
        String companyid = cdtMap.getString("companyid");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date nowdate = new Date();
        String create_time = sdf.format(nowdate);

        JSONObject busDate = this.getBusDate();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, busDate.getInteger("year"));
        cal.set(Calendar.MONTH, busDate.getInteger("month"));
        cal.set(Calendar.DAY_OF_MONTH, 0);
        String date = sdf1.format(cal.getTime());

        for (int i = 0; i < ids.length ; i++)
        {
            String cardid = ids[i];
            //获取卡片信息
            List<JSONObject> assetList = this.dao.getAssetInfoByID(cardid);
            if(assetList.size() > 0)
            {
                JSONObject assetMap = assetList.get(0);
                String year = assetMap.getString("year");
                String month = assetMap.getString("month");
                String business_date = assetMap.getString("business_date");
                String usingstatus = assetMap.getString("usingstatus");
                int depmethod = assetMap.getInteger("depmethod"); //折旧方法
                double value = Double.valueOf(assetMap.getString("value")); //原值
                double netvalue =Double.valueOf( assetMap.getString("netvalue")); //净值
                double salvage = Double.valueOf(assetMap.getString("salvage")); //残值
                double salvagerate = Double.valueOf(assetMap.getString("salvagerate")); //残值率
                double accudep = Double.valueOf(assetMap.getString("accudep")); //累计折旧
                double lastyearaccudep = Double.valueOf(assetMap.getString("lastyearaccudep")!=null?assetMap.getString("lastyearaccudep"):"0"); //截止上年折旧累计
                int naturemonth = assetMap.getInteger("naturemonth"); //预计使用月数
                int servicemonth = assetMap.getInteger("servicemonth"); //剩余使用月数
                int usedmonth = assetMap.getInteger("usedmonth"); //已使用月数/折旧月数
                int changedmonth = assetMap.getInteger("changedmonth"); //变动已使用月数
                double allworkloan = Double.valueOf(assetMap.getString("allworkloan")); //总工作量
                double workloan = BigDecimalUtils.formatBigDecimalToDouble(allworkloan / (naturemonth - changedmonth)); //单期工作量

                SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(df.parse(year+month));
                calendar.add(Calendar.MONTH, -1);
                Date dates = calendar.getTime();
                String lastmonth = df.format(dates);
                List<JSONObject> lastMonthDep = this.dao.getLastMonthDep(cardid, lastmonth.substring(0, 4), lastmonth.substring(4), companyid);
                JSONObject lastDepMap=null;
                if (lastMonthDep.size() <= 0)
                {
                }
                else
                {
                    lastDepMap = lastMonthDep.get(0);
                }
                int d_dbflag = 0;
                if (lastDepMap != null)
                {
                    d_dbflag = lastDepMap.getInteger("dbflag");
                }

                //重新计算卡片的“累计折旧”，“净值”。
                if(supplementNumber == 0  && supplementMoney != 0d)
                {
                    //按金额补提
                    JSONObject depMap = new JSONObject();

                    int depmonth = servicemonth - 1;
                    int depflag = 0;
                    Double depamount = new Double(0);
                    if (netvalue - salvage <= supplementMoney || depmonth == 0)
                    {
                        depamount = BigDecimalUtils.formatBigDecimalToDouble(netvalue - salvage);
                        accudep = BigDecimalUtils.formatBigDecimalToDouble(accudep + (netvalue - salvage));
                        netvalue = BigDecimalUtils.formatBigDecimalToDouble(netvalue - (netvalue - salvage));
                        depflag = 1;
                    }
                    else
                    {
                        depamount = supplementMoney;
                        accudep = BigDecimalUtils.formatBigDecimalToDouble(accudep + supplementMoney);
                        netvalue = BigDecimalUtils.formatBigDecimalToDouble(netvalue - supplementMoney);
                    }
                    depMap.put("accudep",accudep);
                    depMap.put("netvalue",netvalue);
                    depMap.put("depflag",depflag);
                    depMap.put("cardid",cardid);

                    this.dao.depUpdate(depMap);
                    TfamDepsub tfamDepsub = new TfamDepsub();
                    tfamDepsub.setYear(year);
                    tfamDepsub.setMonth(month);
                    tfamDepsub.setDate(date);
                    TfamAsset tfamAsset = new TfamAsset();
                    tfamAsset.setId(cardid);
                    tfamDepsub.setAsset(tfamAsset);
                    tfamDepsub.setDepamount(BigDecimal.valueOf(depamount));
                    tfamDepsub.setDepType(2);
                    tfamDepsub.setResaon(supplementReason);
                    tfamDepsub.setUsingstatus(usingstatus);
                    tfamDepsub.setWorkloan(BigDecimal.valueOf(0));
                    tfamDepsub.setDepmethod(depmonth);
                    tfamDepsub.setValue(BigDecimal.valueOf(value));
                    tfamDepsub.setAccudep(BigDecimal.valueOf(accudep));
                    tfamDepsub.setNetvalue(BigDecimal.valueOf(netvalue));
                    tfamDepsub.setCreatorid(creatorid);
                    tfamDepsub.setCreateTime(create_time);
                    tfamDepsub.setCompanyid(companyid);
                    tfamDepsub.setCompanyId(companyid);
//                    JSONObject entityMap = new JSONObject();
//                    entityMap.put("id", UUID.randomUUID().toString());
//                    entityMap.put("year", year);
//                    entityMap.put("month", month);
//                    entityMap.put("date", date);
//                    entityMap.put("asset_id", cardid);
//                    entityMap.put("depamount", depamount);
//                    entityMap.put("dep_type", 2);
//                    entityMap.put("resaon", supplementReason);
//                    entityMap.put("usingstatus", usingstatus);
//                    entityMap.put("workloan", 0);
//                    entityMap.put("depmethod", depmethod);
//                    entityMap.put("value", value);
//                    entityMap.put("accudep", accudep);
//                    entityMap.put("netvalue", netvalue);
//                    entityMap.put("creatorid", creatorid);
//                    entityMap.put("create_time", create_time);
//                    entityMap.put("companyid", companyid);
                    this.tfamDepsubDao.save(tfamDepsub);
                }
                else if(supplementMoney == 0d && supplementNumber != 0)
                {
                    //按月补提
                    JSONObject resMap = null;
                    JSONObject depMap = new JSONObject();
                    double depamount = 0.0d;
                    for (int j = 0; j < supplementNumber; j++)
                    {
                        if (depmethod == 1)//平均年限
                        {
                            resMap = DepUtils.dep_ag(value, netvalue, salvage, salvagerate,
                                    accudep, naturemonth - changedmonth, servicemonth);
                        }
                        if (depmethod == 2)//双倍余额递减
                        {
                            resMap = DepUtils.dep_db(value, netvalue, salvage, accudep, lastyearaccudep,
                                    naturemonth - changedmonth, servicemonth, usedmonth - changedmonth, d_dbflag);
                        }
                        if (depmethod == 3)//年数总和
                        {
                            resMap = DepUtils.dep_sy(value, netvalue, salvage, accudep,
                                    naturemonth - changedmonth, servicemonth );
                        }
                        if (depmethod == 4)//工作量
                        {
                            resMap = DepUtils.dep_wl(value, netvalue, salvage, accudep,
                                    allworkloan, workloan, servicemonth);
                        }
                        accudep = resMap.getDouble(DepUtils.DEPACCOUNT);
                        netvalue = resMap.getDouble(DepUtils.DEPVALUE);
                        depMap.put("accudep",resMap.getDouble(DepUtils.DEPACCOUNT));
                        depMap.put("netvalue",resMap.getDouble(DepUtils.DEPVALUE));
                        depMap.put("depflag",resMap.getInteger(DepUtils.DEPFLAG));
                        depMap.put("cardid",cardid);

                        //更新资产/卡片信息
                        this.dao.depUpdate(depMap);

                        //按月计提仅记录一条数据
                        depamount = BigDecimalUtils.formatBigDecimalToDouble(depamount + resMap.getDouble(DepUtils.DEPAMOUNT));
                    }
                    TfamDepsub tfamDepsub = new TfamDepsub();
                    tfamDepsub.setYear(year);
                    tfamDepsub.setMonth(month);
                    tfamDepsub.setDate(date);
                    TfamAsset tfamAsset = new TfamAsset();
                    tfamAsset.setId(cardid);
                    tfamDepsub.setAsset(tfamAsset);
                    tfamDepsub.setDepamount(BigDecimal.valueOf(depamount));
                    tfamDepsub.setDepType(2);
                    tfamDepsub.setResaon(supplementReason);
                    tfamDepsub.setUsingstatus(usingstatus);
                    tfamDepsub.setWorkloan(BigDecimal.valueOf(0));
                    tfamDepsub.setDepmethod(depmethod);
                    tfamDepsub.setValue(BigDecimal.valueOf(value));
                    tfamDepsub.setAccudep(BigDecimal.valueOf(accudep));
                    tfamDepsub.setNetvalue(BigDecimal.valueOf(netvalue));
                    tfamDepsub.setCreatorid(creatorid);
                    tfamDepsub.setCreateTime(create_time);
                    tfamDepsub.setCompanyid(companyid);
                    tfamDepsub.setCompanyId(companyid);
//                    EntityMap entityMap = new EntityMap();
//                    entityMap.put("id", UUID.randomUUID().toString());
//                    entityMap.put("year", year);
//                    entityMap.put("month", month);
//                    entityMap.put("date", date);
//                    entityMap.put("asset_id", cardid);
//                    entityMap.put("depamount", depamount);
//                    entityMap.put("dep_type", 2);
//                    entityMap.put("resaon", supplementReason);
//                    entityMap.put("usingstatus", usingstatus);
//                    entityMap.put("workloan", 0);
//                    entityMap.put("depmethod", depmethod);
//                    entityMap.put("value", value);
//                    entityMap.put("accudep", accudep);
//                    entityMap.put("netvalue", netvalue);
//                    entityMap.put("creatorid", creatorid);
//                    entityMap.put("create_time", create_time);
//                    entityMap.put("companyid", companyid);
//                    this.dao.addDepmonth(entityMap);
                    this.tfamDepsubDao.save(tfamDepsub);
                }
            }

        }
    }
    public void reduceDep(JSONObject cdtMap,String[] ids) throws Exception
    {
        cdtMap.putAll(cdtMap.getJSONObject("jsonCondition"));
        double reduceMoney = cdtMap.getDouble("reduceMoney");
        String reduceReason = cdtMap.getString("reduceReason");
        String creatorid = cdtMap.getString("creatorid");
        String companyid = cdtMap.getString("companyid");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date nowdate = new Date();
        String create_time = sdf.format(nowdate);

        JSONObject busDate = this.getBusDate();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, busDate.getInteger("year"));
        cal.set(Calendar.MONTH, busDate.getInteger("month"));
        cal.set(Calendar.DAY_OF_MONTH, 0);
        String date = sdf1.format(cal.getTime());

        for (int i = 0; i < ids.length ; i++)
        {
            String cardid = ids[i];
            //获取卡片信息
            List<JSONObject> assetList = this.dao.getAssetInfoByID(cardid);
            if(assetList.size() > 0)
            {
                JSONObject assetMap = assetList.get(0);
                String year = assetMap.getString("year");
                String month = assetMap.getString("month");
                String business_date = assetMap.getString("business_date");
                String usingstatus = assetMap.getString("usingstatus");
                int depmethod = assetMap.getInteger("depmethod"); //折旧方法
                double value = assetMap.getDouble("value"); //原值
                double netvalue = assetMap.getDouble("netvalue"); //净值
                double salvage = assetMap.getDouble("salvage"); //残值
                double salvagerate = assetMap.getDouble("salvagerate"); //残值率
                double accudep = assetMap.getDouble("accudep"); //累计折旧
                double lastyearaccudep = assetMap.getDouble("lastyearaccudep"); //截止上年折旧累计
                int naturemonth = assetMap.getInteger("naturemonth"); //预计使用月数
                int servicemonth = assetMap.getInteger("servicemonth"); //剩余使用月数
                int usedmonth = assetMap.getInteger("usedmonth"); //已使用月数/折旧月数
                double allworkloan = assetMap.getDouble("allworkloan"); //总工作量
//                double workloan = BigDecimalUtils.formatBigDecimalToDouble(allworkloan / naturemonth); //单期工作量

                //冲减卡片 还原卡片最后使用装填
                JSONObject oldUsingStatus = this.dao.getOldUsingStatus(cardid);
                String oldusingstatus = oldUsingStatus.getString("usingstatus");

                //重新计算卡片的“累计折旧”，“净值”。
                //按金额冲减
                JSONObject depMap = new JSONObject();

                accudep = BigDecimalUtils.formatBigDecimalToDouble(accudep - reduceMoney);
                netvalue = BigDecimalUtils.formatBigDecimalToDouble(netvalue + reduceMoney);

                depMap.put("accudep",accudep);
                depMap.put("netvalue",netvalue);
                depMap.put("depflag",0);
                depMap.put("oldusingstatus",oldusingstatus);
                depMap.put("cardid",cardid);

                this.dao.depUpdate(depMap);
//
//                JSONObject entityMap = new JSONObject();
//                entityMap.put("id", UUID.randomUUID().toString());
//                entityMap.put("year", year);
//                entityMap.put("month", month);
//                entityMap.put("date", date);
//                entityMap.put("asset_id", cardid);
//                entityMap.put("depamount", reduceMoney);
//                entityMap.put("dep_type", 3);
//                entityMap.put("resaon", reduceReason);
//                entityMap.put("usingstatus", usingstatus);//卡片使用状态
//                entityMap.put("workloan", 0);
//                entityMap.put("depmethod", depmethod);
//                entityMap.put("value", value);
//                entityMap.put("accudep", accudep);
//                entityMap.put("netvalue", netvalue);
//                entityMap.put("creatorid", creatorid);
//                entityMap.put("create_time", create_time);
//                entityMap.put("companyid", companyid);
//                this.dao.addDepmonth(entityMap);
                TfamDepsub tfamDepsub = new TfamDepsub();
                tfamDepsub.setYear(year);
                tfamDepsub.setMonth(month);
                tfamDepsub.setDate(date);
                TfamAsset tfamAsset = new TfamAsset();
                tfamAsset.setId(cardid);
                tfamDepsub.setAsset(tfamAsset);
                tfamDepsub.setDepamount(BigDecimal.valueOf(reduceMoney));
                tfamDepsub.setDepType(2);
                tfamDepsub.setResaon(reduceReason);
                tfamDepsub.setUsingstatus(usingstatus);
                tfamDepsub.setWorkloan(BigDecimal.valueOf(0));
                tfamDepsub.setDepmethod(depmethod);
                tfamDepsub.setValue(BigDecimal.valueOf(value));
                tfamDepsub.setAccudep(BigDecimal.valueOf(accudep));
                tfamDepsub.setNetvalue(BigDecimal.valueOf(netvalue));
                tfamDepsub.setCreatorid(creatorid);
                tfamDepsub.setCreateTime(create_time);
                tfamDepsub.setCompanyid(companyid);
                this.tfamDepsubDao.save(tfamDepsub);

            }

        }
    }
    public void projection(int type, int num) throws Exception
    {
        String companyid = SessionUtils.getUser().getCompanyId();
        String creatorid = SessionUtils.getUser().getId();
        //清除折旧预测表中原有数据
        this.dao.truncateData();
        //折旧方式 月初/月末
        Map<String,Object> depreciationTimeMap = this.dao.getDepreciationTime(companyid);
        Object o = depreciationTimeMap.get("value");
        int depreciation_time = Integer.parseInt(String.valueOf(o));//0月初，1月末
        //获取待预测卡片信息
        List<JSONObject> list = this.dao.getAssets(companyid);
        if (list.size() <= 0)
        {
            throw new RuntimeException("没有可预测数据");
        }
        for(int i = 0; i < list.size(); i++)
        {
            JSONObject assetMap = list.get(i);
            //每张卡片递归预测折旧之前 初始化计提额标志位
            CURRDEPFLAG = 1;
            //递归折旧
            this.recursiveProj(assetMap, type, num, depreciation_time);
        }
    }
    public Page<JSONObject> projlist(List<Parameter> parameters, int offset, int limit, String orderby) throws Exception
    {
        Parameter parameter = parameters.get(0);
        String type = parameter.getValue().toString();
        PageRequest pageRequest = new PageRequest(offset, limit, parameters, orderby);
        int total =0;
        List<JSONObject> list = null;
        if(type.equals("0"))  //按年
        {
            total = dao.getYearDatePagecount(type);
            if(total > 0) {
                list =  this.dao.getYearDatePage(type, offset, limit);
            }
        }
        else if(type.equals("1") ) //按月
        {
            total = dao.getMonthDatePagecount(type);
            if(total > 0) {
                list =  this.dao.getMonthDatePage(type, offset, limit);
            }
        }
        return new Page<JSONObject>(total, list);
    }
    /**
     * 折旧预测递归方法
     * @param assetMap
     * @param type 预测类型 0-按年预测,1-按月预测
     * @param num 预测年数/月数
     * @param depType 系统折旧类型 0-月初折旧 1-月末折旧
     * @throws Exception
     */
    public void recursiveProj(JSONObject assetMap, int type, int num, int depType) throws Exception
    {
        String companyid = SessionUtils.getUser().getCompanyId();
        String creatorid = SessionUtils.getUser().getId();
        if (num > 0)
        {
            String year = assetMap.getString("year");
            String month = assetMap.getString("month");
            int usingstatus = assetMap.getInteger("usingstatus");

            if (depType == 0 )//月初折旧 先折旧 -> 月份+1 -> 再记录
            {
                if (usingstatus != 5)//如果卡片没有愈龄
                {
                    //折旧
                    this.dodep(assetMap);
                }
                //月份后推
                JSONObject dateMap = this.getNextMonth(year, month);
                assetMap.put("year", dateMap.getString("year"));
                assetMap.put("month", dateMap.getString("month"));

                if (type == 0)//按年预测 每年年末记录
                {
                    //本（年/月）折旧额（按预测类型区分） 按年预测时 等于本年月折旧额累加
                    double currdep = 0.00d;
                    if(CURRDEPFLAG == 1)
                    {
                        currdep = BigDecimalUtils.formatBigDecimalToDouble(assetMap.getDouble("currdep"))
                                + assetMap.getDouble("depamount");
                        if("0".equals(assetMap.getString("servicemonth")))
                        {
                            //卡片已经逾龄 不存在计提额
                            CURRDEPFLAG = 0;
                        }
                    }
                    else
                    {
                        currdep = BigDecimalUtils.formatBigDecimalToDouble(assetMap.getDouble("currdep"));
                    }
                    assetMap.put("currdep", currdep);
                    /*double currdep = BigDecimalUtils.formatBigDecimalToDouble(assetMap.getDouble("currdep"))
                            + assetMap.getDouble("depamount");*/
//                    assetMap.put("currdep",currdep);

                    if (assetMap.getString("month").equals("12"))
                    {
                        TfamDepfuture tfamDepfuture = new TfamDepfuture();
                        tfamDepfuture.setAssetId(assetMap.getString("id"));
                        tfamDepfuture.setYear(assetMap.getString("year"));
                        tfamDepfuture.setMonth(assetMap.getString("month"));
                        tfamDepfuture.setUsingstatus(assetMap.getString("usingstatus"));
                        tfamDepfuture.setValue(assetMap.getBigDecimal("value"));
                        tfamDepfuture.setAccudep(assetMap.getBigDecimal("accudep"));
                        tfamDepfuture.setNetvalue(assetMap.getBigDecimal("netvalue"));
                        tfamDepfuture.setCurrdep(assetMap.getBigDecimal("currdep"));
                        tfamDepfuture.setNaturemonth(assetMap.getInteger("naturemonth"));
                        tfamDepfuture.setServicemonth(assetMap.getInteger("servicemonth"));
                        tfamDepfuture.setCompanyId(companyid);
                        tfamDepfutureService.save(tfamDepfuture);
                        //添加记录到预测表
//                        this.dao.insertDepFuture(assetMap);
                        //年末 本（年/月）折旧额 清零

                        assetMap.put("currdep", 0.00d);
                        //循环数 -1
                        num--;
                    }
                }
                if(type == 1)//按月预测
                {
                    //本（年/月）折旧额（按预测类型区分） 按月预测时 就等于当月折旧额
                    if(CURRDEPFLAG == 1)
                    {
                        assetMap.put("currdep", assetMap.getDouble("depamount"));
                        if("0".equals(assetMap.getString("servicemonth")))
                        {
                            //卡片已经逾龄 不存在计提额
                            CURRDEPFLAG = 0;
                        }
                    }
                    else
                    {
                        assetMap.put("currdep", 0);
                    }
                    TfamDepfuture tfamDepfuture = new TfamDepfuture();
                    tfamDepfuture.setAssetId(assetMap.getString("id"));
                    tfamDepfuture.setYear(assetMap.getString("year"));
                    tfamDepfuture.setMonth(assetMap.getString("month"));
                    tfamDepfuture.setUsingstatus(assetMap.getString("usingstatus"));
                    tfamDepfuture.setValue(assetMap.getBigDecimal("value"));
                    tfamDepfuture.setAccudep(assetMap.getBigDecimal("accudep"));
                    tfamDepfuture.setNetvalue(assetMap.getBigDecimal("netvalue"));
                    tfamDepfuture.setCurrdep(assetMap.getBigDecimal("currdep"));
                    tfamDepfuture.setNaturemonth(assetMap.getInteger("naturemonth"));
                    tfamDepfuture.setServicemonth(assetMap.getInteger("servicemonth"));
                    tfamDepfuture.setCompanyId(companyid);
                    tfamDepfutureService.save(tfamDepfuture);
                    //添加记录到预测表
//                    this.dao.insertDepFuture(assetMap);
                    //循环 -1
                    num--;
                }
            }
            else  //月末折旧 先折旧 -> 记录 -> 月份+1 当月新建卡片不折旧
            {
                //建卡时间
                String a_year = assetMap.getString("a_year");
                String a_month = assetMap.getString("a_month");
                //愈龄或当月新建卡片不折旧
                if (usingstatus != 5 && !(a_year.equals(year) && a_month.equals(month)))
                {
                    this.dodep(assetMap);
                }
                if (type == 0)//按年预测 每年年末记录
                {
                    //本（年/月）折旧额（按预测类型区分） 按年预测时 等于本年月折旧额累加
                    double currdep = 0.00d;
                    if(CURRDEPFLAG == 1)
                    {
                        currdep = BigDecimalUtils.formatBigDecimalToDouble(assetMap.getDouble("currdep"))
                                + assetMap.getDouble("depamount");
                        if("0".equals(assetMap.getString("servicemonth")))
                        {
                            //卡片已经逾龄 不存在计提额
                            CURRDEPFLAG = 0;
                        }
                    }
                    else
                    {
                        currdep = BigDecimalUtils.formatBigDecimalToDouble(assetMap.getDouble("currdep"));
                    }
                    assetMap.put("currdep",currdep);
                    if (assetMap.getString("month").equals("12"))
                    {
                        TfamDepfuture tfamDepfuture = new TfamDepfuture();
                        tfamDepfuture.setAssetId(assetMap.getString("id"));
                        tfamDepfuture.setYear(assetMap.getString("year"));
                        tfamDepfuture.setMonth(assetMap.getString("month"));
                        tfamDepfuture.setUsingstatus(assetMap.getString("usingstatus"));
                        tfamDepfuture.setValue(assetMap.getBigDecimal("value"));
                        tfamDepfuture.setAccudep(assetMap.getBigDecimal("accudep"));
                        tfamDepfuture.setNetvalue(assetMap.getBigDecimal("netvalue"));
                        tfamDepfuture.setCurrdep(assetMap.getBigDecimal("currdep"));
                        tfamDepfuture.setNaturemonth(assetMap.getInteger("naturemonth"));
                        tfamDepfuture.setServicemonth(assetMap.getInteger("servicemonth"));
                        tfamDepfuture.setCompanyId(companyid);
                        tfamDepfutureService.save(tfamDepfuture);
                        //添加记录到预测表
//                        this.dao.insertDepFuture(assetMap);
                        //年末 本（年/月）折旧额 清零
                        assetMap.put("currdep", 0.00d);
                        //循环 -1
                        num--;
                    }
                }
                if(type == 1)//按月预测
                {
                    //本（年/月）折旧额（按预测类型区分） 按月预测时 就等于当月折旧额
                    if(CURRDEPFLAG == 1)
                    {
                        assetMap.put("currdep", assetMap.getDouble("depamount"));
                        if("0".equals(assetMap.getString("servicemonth")))
                        {
                            //卡片已经逾龄 不存在计提额
                            CURRDEPFLAG = 0;
                        }
                    }
                    else
                    {
                        assetMap.put("currdep", 0);
                    }
                    //添加记录到预测表
                    TfamDepfuture tfamDepfuture = new TfamDepfuture();
                    tfamDepfuture.setAssetId(assetMap.getString("id"));
                    tfamDepfuture.setYear(assetMap.getString("year"));
                    tfamDepfuture.setMonth(assetMap.getString("month"));
                    tfamDepfuture.setUsingstatus(assetMap.getString("usingstatus"));
                    tfamDepfuture.setValue(assetMap.getBigDecimal("value"));
                    tfamDepfuture.setAccudep(assetMap.getBigDecimal("accudep"));
                    tfamDepfuture.setNetvalue(assetMap.getBigDecimal("netvalue"));
                    tfamDepfuture.setCurrdep(assetMap.getBigDecimal("currdep"));
                    tfamDepfuture.setNaturemonth(assetMap.getInteger("naturemonth"));
                    tfamDepfuture.setServicemonth(assetMap.getInteger("servicemonth"));
                    tfamDepfuture.setCompanyId(companyid);
                    tfamDepfutureService.save(tfamDepfuture);
//                    this.dao.insertDepFuture(assetMap);
                    //循环 -1
                    num--;
                }
                //月份后推
                JSONObject dateMap = this.getNextMonth(year, month);
                assetMap.put("year", dateMap.getString("year"));
                assetMap.put("month", dateMap.getString("month"));
            }
            //递归 预测
            this.recursiveProj(assetMap, type, num, depType);
        }
    }
    /**
     * 预测折旧方法
     * @param assetMap
     * @throws Exception
     */
    public void dodep(JSONObject assetMap) throws Exception
    {
        String companyid = SessionUtils.getUser().getCompanyId();
        String creatorid = SessionUtils.getUser().getId();
        String id = assetMap.getString("id");
        double value = assetMap.getDouble("value");
        double netvalue = assetMap.getDouble("netvalue");
        double salvage = assetMap.getDouble("salvage");
        double salvagerate = assetMap.getDouble("salvagerate");
        double accudep = assetMap.getDouble("accudep");
        double allworkloan = assetMap.getDouble("allworkloan");
        double lastyearaccudep = assetMap.getDouble("lastyearaccudep");
        int depmethod = assetMap.getInteger("depmethod");
        int naturemonth = assetMap.getInteger("naturemonth");
        int servicemonth = assetMap.getInteger("servicemonth");
        int usedmonth = assetMap.getInteger("usedmonth");
        int changedmonth = assetMap.getInteger("changedmonth");
        String year = assetMap.getString("year");
        String month = assetMap.getString("month");
        //查询上月折旧信息 （预测开始第一个月时需要校验 卡片折旧方法变动 和 双倍余额法是否已变换计算方式）
        SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(df.parse(year + month));
        calendar.add(Calendar.MONTH, -1);
        Date date = calendar.getTime();
        String lastmonth = df.format(date);
        List<JSONObject> lastMonthDep =  this.dao.getLastMonthDep(id, lastmonth.substring(0, 4), lastmonth.substring(4),companyid);
        JSONObject lastDepMap=null;
        if (lastMonthDep.size() <= 0)
        {
        }
        else
        {
            lastDepMap = lastMonthDep.get(0);
        }
        int dbflag = 0;
        if (lastDepMap != null)
        {

            dbflag =lastDepMap.containsKey("dbflag")?lastDepMap.getInteger("dbflag"):0;
            assetMap.put("dbflag", dbflag);

            int d_depmethod  =lastDepMap.containsKey("depmethod")? lastDepMap.getInteger("depmethod"):null;
            if (d_depmethod != depmethod) //折旧方法有变动 变动已折旧月数=已折旧月数
            {
                changedmonth = usedmonth;
                assetMap.put("changedmonth", usedmonth);
            }
        }
        if ((usedmonth - changedmonth) % 12 == 0)//折旧满一年 上年累计折旧 = 累计折旧
        {
            lastyearaccudep = accudep;
            assetMap.put("lastyearaccudep", lastyearaccudep);
        }

        JSONObject depMap = null;
        if (depmethod == 1)//平均年限
        {
            depMap = DepUtils.dep_ag(value, netvalue, salvage, salvagerate,
                    accudep, naturemonth - changedmonth, servicemonth);
        }
        if (depmethod == 2)//双倍余额递减
        {
            depMap = DepUtils.dep_db(value, netvalue, salvage, accudep, lastyearaccudep,
                    naturemonth - changedmonth, servicemonth, usedmonth - changedmonth, dbflag);
            //双倍余额法 更新算法转换标志
            assetMap.put("dbflag", depMap.getInteger("dbflag"));
        }
        if (depmethod == 3)//年数总和
        {
            depMap = DepUtils.dep_sy(value, netvalue, salvage, accudep,
                    naturemonth - changedmonth, servicemonth );
        }
        if (depmethod == 4)//工作量
        {
            depMap = DepUtils.dep_wl2(value, netvalue, salvage, accudep, naturemonth, servicemonth);
        }
        //折旧结果更新到assetMap
        assetMap.put("depamount", depMap.getDouble(DepUtils.DEPAMOUNT));
        assetMap.put("deprate", depMap.getDouble(DepUtils.DEPREAT));
        assetMap.put("netvalue", depMap.getDouble(DepUtils.DEPVALUE));
        assetMap.put("accudep", depMap.getDouble(DepUtils.DEPACCOUNT));
        assetMap.put("servicemonth", depMap.getInteger(DepUtils.DEPMONTH));
        if(depMap.getInteger(DepUtils.DEPFLAG) == 1) //愈龄
        {
            assetMap.put("usingstatus", 5);
        }
        assetMap.put("usedmonth", usedmonth+1);//已折旧月数+1
    }
    /**
     * 每月折旧
     * @param cdtMap
     * @param workoadList
     * @throws Exception
     */
    public void depMonthly( JSONObject cdtMap, JSONArray workoadList)throws Exception
    {
        String companyid = SessionUtils.getUser().getCompanyId();
        String creatorid = SessionUtils.getUser().getId();
        //本月新增的卡片 当月不折旧。但是 所有卡片 依然要备份，还有卡片表中的业务年和月也要向后推移。
        //折旧折的是上月新增已审核，且使用状态不为愈龄的卡片，
        Map<String,Object> depreciationTimeMap = this.dao.getDepreciationTime(companyid);
        Object o = depreciationTimeMap.get("value");
        int depreciation_time = Integer.parseInt(String.valueOf(o));//0月初，1月末
        cdtMap.put("depreciation_time",depreciation_time);
        if (depreciation_time == 0)
        {
            this.doDepOnFirst( cdtMap, workoadList);
        }
        else
        {
            this.doDepOnLast( cdtMap, workoadList);
        }
    }

    /**
     * 月末折旧 过程
     * @param cdtMap
     * @param workoadList
     * @throws Exception
     */
    private void doDepOnLast( JSONObject cdtMap, JSONArray workoadList) throws Exception
    {
        String companyid = SessionUtils.getUser().getCompanyId();
        String creatorid = SessionUtils.getUser().getId();
        //先折旧操作
        this.doDep( cdtMap, workoadList);
        //作镜像
        this.dao.assetCopy(companyid);
        //业务年月后推
        String year = cdtMap.getString("year");
        String month = cdtMap.getString("month");
        JSONObject nextMonth = this.getNextMonth(year, month);
        this.dao.dateUpdate(nextMonth.getString("year"), nextMonth.getString("month"),companyid);
        //每月折旧记录表添加记录
        TfamDepmonth tfamDepmonth = new TfamDepmonth();
        tfamDepmonth.setYear(year);
        tfamDepmonth.setMonth(month);
        tfamDepmonth.setVoucherid("");
        tfamDepmonth.setCompanyId(companyid);
        tfamDepmonth.setCompanyid(companyid);
        tfamDepmonthService.save(tfamDepmonth);
//        this.dao.addDepmonth(entityMap);
    }

    /**
     * 月初折旧 过程
     * @param cdtMap
     * @param workoadList
     * @throws Exception
     */
    private void doDepOnFirst(JSONObject cdtMap, JSONArray workoadList) throws Exception
    {
        String companyid = SessionUtils.getUser().getCompanyId();
        String creatorid = SessionUtils.getUser().getId();
        //先作镜像
        this.dao.assetCopy(companyid);
        this.doDep( cdtMap, workoadList);
        //业务年月后推
        String year = cdtMap.getString("year");
        String month = cdtMap.getString("month");
//        EntityMap nextMonth = this.depBP.getNextMonth(year, month);
        this.dao.dateUpdate(year, month,companyid);
        //每月折旧记录表添加记录
        JSONObject entityMap = new JSONObject();
        TfamDepmonth tfamDepmonth = new TfamDepmonth();
        tfamDepmonth.setYear(year);
        tfamDepmonth.setMonth(month);
        tfamDepmonth.setVoucherid("");
        tfamDepmonth.setCompanyId(companyid);
        tfamDepmonth.setCompanyid(companyid);
        tfamDepmonthService.save(tfamDepmonth);
//        this.dao.addDepmonth(entityMap);
    }
    /**
     * 折旧相关操作
     * @param cdtMap 折旧月份信息
     * @param workoadList 工作量列表
     */
    private void doDep(  JSONObject cdtMap, JSONArray workoadList) throws Exception
    {
        String companyid = SessionUtils.getUser().getCompanyId();
        String creatorid = SessionUtils.getUser().getId();
        //获取需要折旧的资产/卡片数据
        List<JSONObject> assetList = this.dao.getAssets1(cdtMap);
        //计算上个月
        String year = cdtMap.getString("year");
        String month = cdtMap.getString("month");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(df.parse(year + month));
        calendar.add(Calendar.MONTH, -1);
        Date date = calendar.getTime();
        String lastmonth = df.format(date);
        //折旧
        for (int i = 0; i < assetList.size(); i++)
        {
            JSONObject assetMap = assetList.get(i);
            String id = assetMap.getString("id");
            double value = assetMap.getDouble("value");
            double netvalue = assetMap.getDouble("netvalue");
            double salvage = assetMap.getDouble("salvage");
            double salvagerate = assetMap.getDouble("salvagerate");
            double accudep = assetMap.getDouble("accudep");
            double allworkloan = assetMap.getDouble("allworkloan");
            double lastyearaccudep = assetMap.getDouble("lastyearaccudep");
            int depmethod = assetMap.getInteger("depmethod");
            int naturemonth = assetMap.getInteger("naturemonth");
            int servicemonth = assetMap.getInteger("servicemonth");
            int usedmonth = assetMap.getInteger("usedmonth");
            int changedmonth = assetMap.getInteger("changedmonth");
            //查询上月折旧信息
            List<JSONObject> lastMonthDep =  this.dao.getLastMonthDep(id, lastmonth.substring(0, 4), lastmonth.substring(4),companyid);
            JSONObject lastDepMap=null;
            if (lastMonthDep.size() <= 0)
            {
            }
            else
            {
                lastDepMap = lastMonthDep.get(0);
            }
            int d_dbflag = 0;
            if (lastDepMap != null)
            {
                int d_depmethod  = lastDepMap.getInteger("depmethod");
                d_dbflag = lastDepMap.getInteger("dbflag");
                if (d_depmethod != depmethod) //折旧方法有变动 更新已折旧月数
                {
                    //卡片变动中 如果变动折旧类型 相当于从该事件点起 此卡片为一个新卡片 所以需要将已使用月数更新到变动已使用月数字段中，
                    //页面上预计使用月数不变，在使用预计使用月数的时候，用naturemonth-changedmonth，即为实际的变动之后的预计使用月数
                    this.dao.upChangedmonth(id, usedmonth);
                    changedmonth = usedmonth;
                }
            }
            if ((usedmonth - changedmonth) % 12 == 0)
            {
                lastyearaccudep = accudep;
                //跟新上年累计折旧
                this.dao.upLastYearAccudep(id);
            }

            JSONObject depMap = null;
            if (depmethod == 1)//平均年限
            {
                depMap = DepUtils.dep_ag(value, netvalue, salvage, salvagerate,
                        accudep, naturemonth - changedmonth, servicemonth);
            }
            if (depmethod == 2)//双倍余额递减
            {
                depMap = DepUtils.dep_db(value, netvalue, salvage, accudep, lastyearaccudep,
                        naturemonth - changedmonth, servicemonth, usedmonth - changedmonth, d_dbflag);
            }
            if (depmethod == 3)//年数总和
            {
                depMap = DepUtils.dep_sy(value, netvalue, salvage, accudep,
                        naturemonth - changedmonth, servicemonth );
            }
            if (depmethod == 4)//工作量
            {
                for (int j = 0; j < workoadList.size(); j++)
                {
                    JSONObject obj = workoadList.getJSONObject(j);
                    if (obj.getString("id").equals(id))
                    {
                        double workloan = obj.getDouble("workloan");
                        depMap = DepUtils.dep_wl(value, netvalue, salvage, accudep,
                                allworkloan, workloan, servicemonth);
                        depMap.put("workloan", workloan);
                        break;
                    }
                }
            }
            depMap.put("assetid", assetMap.getString("id"));
            //更新资产/卡片信息
            this.depUpdate(depMap);
            //已折旧月数+1
            this.dao.upUsedMonth(depMap.getString("assetid"));

            depMap.put("id", UUID.randomUUID().toString());
            depMap.put("year", year);
            depMap.put("month", month);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Map<String,Object> depreciationTimeMap = this.dao.getDepreciationTime(companyid);
            Object o = depreciationTimeMap.get("value");
            int depreciation_time = Integer.parseInt(String.valueOf(o));//0月初，1月末
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, Integer.parseInt(year));
            if(depreciation_time == 0)
            {
                cal.set(Calendar.MONTH, Integer.parseInt(month)-1);
                cal.set(Calendar.DAY_OF_MONTH, 1);
            }
            else
            {
                cal.set(Calendar.MONTH, Integer.parseInt(month));
                cal.set(Calendar.DAY_OF_MONTH, 0);
            }
            depMap.put("date", sdf.format(cal.getTime()));
            depMap.put("vouformat", cdtMap.getString("vouformat"));
            depMap.put("abstract", cdtMap.getString("abstract"));
            depMap.put("creatorid", creatorid);
            depMap.put("create_time", sdf.format(new Date()));
            depMap.put("companyid", "");
            //添加明细
            this.addDepsub(assetMap, depMap);
        }
    }
    public void depUpdate(JSONObject entityMap)
    {
        if (entityMap != null)
        {
            this.dao.depUpdate(entityMap);
        }
    }
    /**
     * 添加折旧明细
     * @param assetMap 资产信息
     * @param depMap 折旧信息
     */
    public void addDepsub(JSONObject assetMap, JSONObject depMap)
    {
        String companyid = SessionUtils.getUser().getCompanyId();
        String creatorid = SessionUtils.getUser().getId();
        TfamDepsub tfamDepsub = new TfamDepsub();


        tfamDepsub.setYear(depMap.getString("year"));
        tfamDepsub.setMonth(depMap.getString("month"));
        tfamDepsub.setDate(depMap.getString("date"));
        TfamAsset tfamAsset = new TfamAsset();
        tfamAsset.setId(assetMap.getString("id"));
        tfamDepsub.setAsset(tfamAsset);
        tfamDepsub.setDepamount(depMap.getBigDecimal(DepUtils.DEPAMOUNT));
        tfamDepsub.setDepType(1);
        tfamDepsub.setWorkloan(depMap.getBigDecimal("workloan"));
        tfamDepsub.setDepmethod(assetMap.getInteger("depmethod"));
        tfamDepsub.setDbflag(depMap.getInteger("dbflag"));
        tfamDepsub.setValue(assetMap.getBigDecimal("value"));
        tfamDepsub.setAccudep(depMap.getBigDecimal(DepUtils.DEPACCOUNT));
        tfamDepsub.setNetvalue(depMap.getBigDecimal(DepUtils.DEPVALUE));
        tfamDepsub.setVoucherid("");
        tfamDepsub.setVouformat(depMap.getString("vouformat"));
        tfamDepsub.setAbstracts(depMap.getString("abstract"));
        tfamDepsub.setCreatorid(depMap.getString("creatorid"));
        tfamDepsub.setCreateTime(depMap.getString("create_time"));
        tfamDepsub.setCompanyid(depMap.getString("companyid"));
        tfamDepsub.setCompanyId(depMap.getString("companyid"));
        this.tfamDepsubDao.save(tfamDepsub);
    }
    /**
     * 检查是否有待审核的资产
     * @return true/false
     */
    public boolean getNoCheckAssets()
    {
        String companyid = SessionUtils.getUser().getCompanyId();
        String creatorid = SessionUtils.getUser().getId();
        List<JSONObject> list = this.dao.getNoCheckAssets(companyid);
        if (list.size() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    /**
     * 获取工作量法的资产
     * @return
     */
    public List<JSONObject> getWorkloadList()
    {
        String companyid = SessionUtils.getUser().getCompanyId();
        String creatorid = SessionUtils.getUser().getId();
        return this.dao.getWorkloadList(companyid);
    }
    /**
     * 获取根据当前日期查询会计期及折旧状态和关联凭证
     * @param year 业务年
     * @param month 业务月
     * @return 当前所在会计期，没有则往前查询
     */
    public JSONObject loadInfo(String year, String month) throws Exception
    {
        String companyid = SessionUtils.getUser().getCompanyId();
        String creatorid = SessionUtils.getUser().getId();
        JSONObject map = this.getNextMonth(year,month);
        List<JSONObject> list = this.dao.loadInfo(map.getString("year"), map.getString("month"),companyid);
        if ( list.size() > 0 )
        {
            return list.get(0);
        }
        else
        {
            JSONObject entityMap = new JSONObject();
            entityMap.put("year", year);
            entityMap.put("month", month);
            entityMap.put("isvoucher", 0);
            entityMap.put("voucher1", "");
            entityMap.put("voucher2", "");
            entityMap.put("isdep", 0);
            return entityMap;
        }
    }
    /**
     * 凭证生成
     */
    public void produceVoucher(String jsonObject) throws Exception{
        String companyid = SessionUtils.getUser().getCompanyId();
        String creatorid = SessionUtils.getUser().getId();
        JSONObject jsonObject1 = JSONObject.parseObject(jsonObject);
        String[] split = jsonObject1.getString("depdate").split("-");
        String year=split[0];
        String month=split[1];
        String abstracts = jsonObject1.getString("abstracts");
        String numbering = jsonObject1.getString("numbering");
        String loginName = jsonObject1.getString("loginName");
        List<JSONObject> assetRecordList = this.dao.selectAssetRecord(year,month,companyid);
        List<JSONObject> assetRecordListCopy = this.dao.selectAssetRecordCopy(year,month,companyid);
        assetRecordListCopy.addAll(assetRecordList);
        String yearAndMonthId = this.dao.selectIntYearUqglobalperiodId(year,month,companyid);
//        String userid = this.dao.selectUserId(loginName);
        String userid = loginName;
        String depamount = this.dao.selectDepcostAndAccudep(year,month,companyid);
        //循环从折旧明细表中取出资产id（资产表id）
        TglVoucherMains voucherMain = new TglVoucherMains();
        voucherMain.setUqnumbering(numbering);//凭证类型
        voucherMain.setUqcompanyid(companyid);//公司
        voucherMain.setIntaffix(1);//附件数  为空
        SysUser sysUser = new SysUser();
        sysUser.setId(userid);
        voucherMain.setUqfillerid(sysUser);//制证人
        if (StringUtils.isNullOrEmpty(depamount)){
            throw new Exception(year+""+month+"未折旧!");
        }
        voucherMain.setMnydebitsum(depamount.equalsIgnoreCase("null") ? new BigDecimal(0.00):new BigDecimal(depamount));//借方合计
        voucherMain.setMnycreditsum(depamount.equalsIgnoreCase("null") ? new BigDecimal(0.00):new BigDecimal(depamount));//贷方合计
        String yearAndMonthStatus = this.dao.selectGlobalStatus(year,month,companyid);
        TglGlobalPeriods tglGlobalPeriods = new TglGlobalPeriods();
        tglGlobalPeriods.setUqglobalperiodid(yearAndMonthId);
        voucherMain.setUqglobalperiodid(tglGlobalPeriods);//会计期
        if("2".equals(yearAndMonthStatus)){
            voucherMain.setDtfiller(year+"-"+month+"-01");//制证日期
            voucherMain.setDtfillersrv(year+"-"+month+"-01");//制证日期
        }else{
            throw new Exception(year+""+month+"会计期没有启用!");
        }
        List<TglVoucherDetails> tglVoucherDetails = new ArrayList<>();
        for(JSONObject entityMap : assetRecordListCopy){
            TglVoucherDetails tglVoucherDetails1 = new TglVoucherDetails();
            tglVoucherDetails1.setUqvoucherdetailid("");
            tglVoucherDetails1.setUqvoucherid("");
            for(Map.Entry<String,Object>map : entityMap.entrySet()){
                if("depcost".equals(map.getKey())){//折旧费用科目
                    tglVoucherDetails1.setUqaccountid( map.getValue().toString());
                    List<Parameter> params = null;
                    /*获取子表列表   资产类别表*/
                    params = Lists.newArrayList();
                    params.add(new Parameter("uqaccountid", "=", map.getValue().toString()));
                    tglVoucherDetails1.setTglAccounts(tglAccountsService.listAll(params, "").get(0));
                    tglVoucherDetails1.setVarabstract(abstracts);
                    tglVoucherDetails1.setMnydebit( entityMap.getBigDecimal("depamount"));
                    tglVoucherDetails1.setMnycredit(BigDecimal.valueOf(0.00));
                    tglVoucherDetails.add(tglVoucherDetails1);
                }else if("accudep".equals(map.getKey())) {//累计折旧费科目
                    tglVoucherDetails1.setUqaccountid(map.getValue().toString());
                    List<Parameter> params = null;
                    /*获取子表列表   资产类别表*/
                    params = Lists.newArrayList();
                    params.add(new Parameter("uqaccountid", "=", map.getValue().toString()));
                    tglVoucherDetails1.setTglAccounts(tglAccountsService.listAll(params, "").get(0));
                    tglVoucherDetails1.setVarabstract(abstracts);
                    tglVoucherDetails1.setMnycredit( entityMap.getBigDecimal("depamount"));
                    tglVoucherDetails1.setMnydebit(BigDecimal.valueOf(0.00));
                    tglVoucherDetails.add(tglVoucherDetails1);
                }
            }
        }
        voucherMain.setTglVoucherDetailsList(tglVoucherDetails);
        voucherMain.setSubmitodd("0");
        voucherMain.setChargeagainstsubmitodd(year+"-"+month+"-01");
        this.tglVoucherMainsService.save(voucherMain);
    }

}
