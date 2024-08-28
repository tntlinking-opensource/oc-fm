package com.geeke.vouchermanager.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.geeke.common.constants.ActionConstants;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.sys.entity.Action;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.utils.*;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.vouchermanager.dao.TglAcOffsetDetailDao;
import com.geeke.vouchermanager.dao.TglAcOffsetMainDao;
import com.geeke.vouchermanager.entity.*;
import com.geeke.vouchermanager.service.TglAcOffsetDetailService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.lang.ObjectUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.omg.CORBA.StringHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 往来冲销总表Service
 * @author
 * @version
 */

@Service("tglAcOffsetMainService")
@Transactional(readOnly = false)
public class TglAcOffsetMainService extends CrudService<TglAcOffsetMainDao, TglAcOffsetMain> {

    @Autowired
    private TglAcOffsetDetailDao tglAcOffsetDetailDao;

    @Autowired
    private TglAcOffsetDetailService tglAcOffsetDetailService;

    @Override
    public TglAcOffsetMain get(String id) {
        TglAcOffsetMain tglAcOffsetMain = super.get(id);

        List<Parameter> params = null;

        /*获取子表列表   往来冲销明细表*/
        params = Lists.newArrayList();

        params.add(new Parameter("UQMAINID", "=", tglAcOffsetMain.getId()));
        tglAcOffsetMain.setTglAcOffsetDetailList(tglAcOffsetDetailService.listAll(params, ""));

        return tglAcOffsetMain;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public TglAcOffsetMain save(TglAcOffsetMain tglAcOffsetMain) {
        TglAcOffsetMain tglAcOffsetMainTemp = super.save(tglAcOffsetMain);

        /* 保存子表数据     往来冲销明细表 */
        saveTglAcOffsetDetailList(tglAcOffsetMainTemp);

        return tglAcOffsetMainTemp;
    }

    /**
     * 删除
     * @param tglAcOffsetMain
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int delete(TglAcOffsetMain tglAcOffsetMain) {
        List<Parameter> params = null;
        PageRequest pageRequest;

        /* 处理子表     往来冲销明细表 */
        params = Lists.newArrayList();

        params.add(new Parameter("UQMAINID", "=", tglAcOffsetMain.getId()));
        pageRequest = new PageRequest(params);
        tglAcOffsetMain.setTglAcOffsetDetailList(tglAcOffsetDetailDao.listAll(pageRequest));

        if (tglAcOffsetMain.getTglAcOffsetDetailList() != null && tglAcOffsetMain.getTglAcOffsetDetailList().size() > 0) {
            tglAcOffsetDetailService.bulkDelete(tglAcOffsetMain.getTglAcOffsetDetailList());
        }

        int rows = super.delete(tglAcOffsetMain);
        return rows;
    }

    /**
     * 批量删除
     * @param tglAcOffsetMainList
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int bulkDelete(List<TglAcOffsetMain> tglAcOffsetMainList) {
        for (TglAcOffsetMain tglAcOffsetMain : tglAcOffsetMainList) {
            delete(tglAcOffsetMain);
        }

        return tglAcOffsetMainList.size();
    }

    /**
     * 生成操作日志
     * @param actionTypeId  操作类型Id
     * @param entity        操作的实体对象
     * @return
     */
    @Override
    protected Action createAction(String actionTypeId, TglAcOffsetMain entity) {
        Action action = super.createAction(actionTypeId, entity);
        if (action == null) {
            return null;
        }
        // 删除时记录把保存的数据保存到回收站
        if (ActionConstants.ACTION_DELETED.equals(actionTypeId)) {}
        return action;
    }

    /* 保存子表数据     往来冲销明细表 */
    private void saveTglAcOffsetDetailList(TglAcOffsetMain tglAcOffsetMain) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("UQMAINID", "=", tglAcOffsetMain.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TglAcOffsetDetail> list_TglAcOffsetDetail = tglAcOffsetDetailDao.listAll(pageRequest);

        List<TglAcOffsetDetail> deletes = Lists.newArrayList(); // 删除列表
        List<TglAcOffsetDetail> inserts = Lists.newArrayList(); // 添加列表
        List<TglAcOffsetDetail> updates = Lists.newArrayList(); // 更新列表
        for (TglAcOffsetDetail tglAcOffsetDetailSaved : list_TglAcOffsetDetail) {
            boolean found = false;
            for (TglAcOffsetDetail tglAcOffsetDetail : tglAcOffsetMain.getTglAcOffsetDetailList()) {
                if (tglAcOffsetDetailSaved.getId().equals(tglAcOffsetDetail.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tglAcOffsetDetailSaved);
            }
        }
        if (deletes.size() > 0) {
            tglAcOffsetDetailService.bulkDelete(deletes);
        }
        for (TglAcOffsetDetail tglAcOffsetDetail : tglAcOffsetMain.getTglAcOffsetDetailList()) {
            if (StringUtils.isBlank(tglAcOffsetDetail.getId())) {
                tglAcOffsetDetail.setUqmainid(tglAcOffsetMain.getUqmainid());

                inserts.add(tglAcOffsetDetail);
            } else {
                updates.add(tglAcOffsetDetail);
            }
        }
        if (updates.size() > 0) {
            tglAcOffsetDetailService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tglAcOffsetDetailService.bulkInsert(inserts);
        }
    }
  public Page<Map<String, Object>>  getOnaccountDataPage(List<Parameter> parameters, int offset, int limit, String orderby){
      PageRequest pageRequest = new PageRequest(offset, limit, parameters, orderby);
      Map<String, Object> params = new HashMap<>();
      for (int i = 0; i < parameters.size(); i++) {
          Parameter parameter = parameters.get(i);
          params.put(parameter.getColumnName(),parameter.getValue());
//          if (parameter.getColumnName().equals("ledgertypeid")){
//              String[] split = parameter.getValue().toString().split(",");
//              String value="";
//              for (String s : split) {
//                  value+="\""+s+"\",";
//              }
//              params.put(parameter.getColumnName(),value.substring(0,value.length()-1));
//          } else if (parameter.getColumnName().equals("ledger")) {
//              String[] split = parameter.getValue().toString().split(",");
//              String value="";
//              for (String s : split) {
//                  value+="\""+s+"\",";
//              }
//              params.put(parameter.getColumnName(),value.substring(0,value.length()-1));
//          } else {
//              params.put(parameter.getColumnName(),parameter.getValue());
//          }
      }
      List<Map<String, Object>> datas = this.getOnaccountData(params);
      this.sortData(datas);
      int end = offset+limit ;
      if (end>datas.size()){
          end = datas.size();
      }
      Page<Map<String, Object>> pageData = new Page<>(datas.size(),datas.subList(offset,end));
      int totalPage = 0;
      if(datas.size()>0)
      {
          totalPage =(datas.size()/limit)+1;
      }
//      pageData(totalPage);
//      return new Page<Map<String, Object>>(total, list);
    return pageData;
  }
  public Page<Map<String, Object>>  getdata(List<Parameter> parameters, int offset, int limit, String orderby){
      PageRequest pageRequest = new PageRequest(offset, limit, parameters, orderby);
      Map<String, Object> params = new HashMap<>();
      for (int i = 0; i < parameters.size(); i++) {
          Parameter parameter = parameters.get(i);
          params.put(parameter.getColumnName(),parameter.getValue());
      }
      List<Map<String, Object>> datas = this.getOffsetData1(params);
      this.sortData(datas);
      int end = offset+limit ;
      if (end>datas.size()){
          end = datas.size();
      }
      Page<Map<String, Object>> pageData = new Page<>(datas.size(),datas.subList(offset,end));
      int totalPage = 0;
      if(datas.size()>0)
      {
          totalPage =(datas.size()/limit)+1;
      }
//      pageData(totalPage);
//      return new Page<Map<String, Object>>(total, list);
    return pageData;
  }
  public Page<Map<String, Object>>  accountlist(List<Parameter> parameters, int offset, int limit, String orderby){
      Map<String, Object> params = new HashMap<>();
      for (int i = 0; i < parameters.size(); i++) {
          Parameter parameter = parameters.get(i);
          params.put(parameter.getColumnName(),parameter.getValue());
      }
      StringBuilder sb = new StringBuilder();
      sb.append("  select dd.uqaccountid,dd.varaccountcode,dd.varaccountname,dd.uqparentid,dd.intproperty,dd.uqtypeid,dd.intislastlevel ,p.inidate from    ");
      sb.append("  (select t.INTFLAG,t.uqaccountid,t.varaccountcode,t.varaccountname,h.varaccountname as uqparentid,n.categoryname as intproperty,y.categorycode as uqtypeid,t.intislastlevel ");
      sb.append("   from tgl_accounts t,(select p.uqaccountid,p.varaccountname from tgl_accounts p ) h ,tob_category n, tob_category y  ");
      sb.append("   where   h.uqaccountid=t.uqparentid and n.categorycode=t.intproperty and n.CATEGORYTYPE='10000001' and y.categorycode=t.uqtypeid and y.CATEGORYTYPE='10000002')  dd ");
      sb.append("  LEFT JOIN (select g.inidate,g.uqaccountid  from  tgl_ac_ini_rel g ) p on dd.uqaccountid=p.uqaccountid  ");
      sb.append("   where  dd.intislastlevel=1 and dd.INTFLAG = 2 ");
      sb.append("   and    dd.uqtypeid in ('1','2','7','8')  ");
      Object varAccountCode = params.get("varAccountCode") != null ? params.get("varAccountCode"):"";
      Object varAccountName = params.get("varAccountCode") != null ? params.get("varAccountCode"):"";
      sb.append("   and   dd.varaccountcode like   '"+varAccountCode+"%'  ");
      sb.append("   and   dd.varaccountname  like   '"+varAccountName+"%' ");
      sb.append("   ORDER BY dd.varaccountcode asc ");
      sb.append("  LIMIT "+limit+" OFFSET "+offset+" ");
      List<Map<String, Object>> mapList = dao.getMapList(sb.toString());
      int datassize = dao.getMapListcount(sb.toString());
      Page<Map<String, Object>> pageData = new Page<>(datassize,mapList);
        return pageData;
  }
    public List<Map<String, Object>> getOnaccountData(Map<String, Object> params){
        List<Map<String, Object>> datas = new ArrayList<>();
        //获取凭证中的挂账数据
        filterParams(params);
        List<Map<String, Object>> datas_voucher = getVoucherData(params,"onaccount");
        datas.addAll(datas_voucher);
        // 获取往来初始化中的挂账数据
        List<Map<String, Object>> datas_init = dao.getOffsetInitDatas(1,params,SessionUtils.getUser().getCompanyId());
        datas.addAll(datas_init);
        //取往来表挂账记录。对比后排除掉余额为0的数据，并且计算已冲金额和余额
        return filterData(datas,2,params);
    }
    private void sortData(List<Map<String, Object>> datas){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> tmp = null ;
        int length = datas.size() ;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - 1 - i; j++) {//内层循环控制每一趟排序多少次
                try {
                    long la = sdf.parse(String.valueOf(datas.get(j).get("accountdate"))).getTime() ;
                    long lb = sdf.parse(String.valueOf(datas.get(j + 1).get("accountdate"))).getTime() ;
                    if (la > lb)
                    {
                        tmp = datas.get(j);
                        datas.set(j,datas.get(j+1));
                        datas.set(j+1,tmp);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }
  public Page<Map<String, Object>>  getOffsetDataPage(List<Parameter> parameters, int offset, int limit, String orderby){
      PageRequest pageRequest = new PageRequest(offset, limit, parameters, orderby);
      Map<String, Object> params = new HashMap<>();
      for (int i = 0; i < parameters.size(); i++) {
          Parameter parameter = parameters.get(i);
          params.put(parameter.getColumnName(),parameter.getValue());
//          if (parameter.getColumnName().equals("ledgertypeid")){
//              String[] split = parameter.getValue().toString().split(",");
//              String value="";
//              for (String s : split) {
//                  value+="\""+s+"\",";
//              }
//              params.put(parameter.getColumnName(),value.substring(0,value.length()-1));
//          } else if (parameter.getColumnName().equals("ledger")) {
//              String[] split = parameter.getValue().toString().split(",");
//              String value="";
//              for (String s : split) {
//                  value+="\""+s+"\",";
//              }
//              params.put(parameter.getColumnName(),value.substring(0,value.length()-1));
//          } else {
//              params.put(parameter.getColumnName(),parameter.getValue());
//          }

      }
      List<Map<String, Object>> datas = this.getOffsetData(params);
      this.sortData(datas);
      int end = offset+limit ;
      if (end>datas.size()){
          end = datas.size();
      }
      Page<Map<String, Object>> pageData = new Page<>(datas.size(),datas.subList(offset,end));
      int totalPage = 0;
      if(datas.size()>0)
      {
          totalPage =(datas.size()/limit)+1;
      }
//      return new Page<Map<String, Object>>(total, list);
    return pageData;
  }
    private int getOppType(int type){
        if (type == 1){
            return 2 ;
        }else if (type == 2){
            return 1 ;
        }else {
            throw new IllegalArgumentException("getOppType方法的参数值必须是1或者2");
        }
    }
    private double parseToNumber(double value){
        if (value >=0){
            return value;
        }else {
            return (0-value) ;
        }
    }
    private double formatMoney(double value, boolean type){
        if (type){
            return value ;
        }else {
            return 0-value;
        }
    }
    public void doOffset(String revertRecords)throws Exception{
        JSONObject jsonObject = JSONObject.parseObject(revertRecords);
        Object offsetDatas = jsonObject.get("offsetDatas");
        Object onaccountDatas = jsonObject.get("onaccountDatas");
        JSONArray offset_datas = JSONArray.parseArray(String.valueOf(offsetDatas));
        JSONArray onaccount_datas = JSONArray.parseArray(String.valueOf(onaccountDatas));
        this.dataCheck(offset_datas,onaccount_datas);
        //开始冲销
        for (int i=0 ; i<offset_datas.size() ; i++)
        {
            doRush(offset_datas.getJSONObject(i),onaccount_datas);
        }
    }
    private void doRush(JSONObject mainRecord,JSONArray detailRecords){
        //生成批次号，对一笔冲销记录进行冲销时，所有被冲销的挂账明细记录，以及这笔冲销记录对应的明细记录的批次号相同
        //一次操作主记录在往来信息记录主表中只会有一条记录，明细表也只有一条，
        String companyId = SessionUtils.getUser().getCompanyId();
        String userId = SessionUtils.getUser().getId();
        String uqbatchid = UUID.randomUUID().toString().toUpperCase();
        mainRecord.put("uqbatchid",uqbatchid);

        //生成主记录ID
        String uqmainid = UUID.randomUUID().toString().toUpperCase();
        mainRecord.put("uqmainid",uqmainid);

        //直接先插入一条初始化的主记录对应的核销金额为0明细记录
        mainRecord.put("money",0);
        //生成明细记录的主键
        String main_uqdetailid = UUID.randomUUID().toString();
        mainRecord.put("uqdetailid",main_uqdetailid);
//        dao.insertOffsetDetail(mainRecord,uqmainid);
        TglAcOffsetDetail tglAcOffsetDetail = new TglAcOffsetDetail();
        tglAcOffsetDetail.setUqdetailid(mainRecord.getString("uqdetailid"));
        tglAcOffsetDetail.setUqbatchid(mainRecord.getString("uqbatchid"));
        tglAcOffsetDetail.setUqmainid(uqmainid);
        tglAcOffsetDetail.setUqcompanyid(companyId);
        tglAcOffsetDetail.setUqvoucherid(mainRecord.getString("voucherid"));
        tglAcOffsetDetail.setUqvoudetailid(mainRecord.getString("uqvoucherdetailid"));
        tglAcOffsetDetail.setIniid(mainRecord.getString("iniid"));
        tglAcOffsetDetail.setUqaccountid(mainRecord.getString("uqaccountid"));
        tglAcOffsetDetail.setUqledgetypeid(mainRecord.getString("uqledgetypeid"));
        tglAcOffsetDetail.setUqledgeid(mainRecord.getString("uqledgeid"));
        tglAcOffsetDetail.setMoney(new BigDecimal(mainRecord.getString("money")));
        tglAcOffsetDetail.setUquserid(userId);
        tglAcOffsetDetail.setInserttime(DateUtils.getDateTime());
        tglAcOffsetDetail.setUpdatetime(DateUtils.getDateTime());
        tglAcOffsetDetail.setInttype(new BigDecimal(mainRecord.getString("inttype")));
        tglAcOffsetDetail.setTenantId(companyId);
        tglAcOffsetDetailDao.insert(tglAcOffsetDetail);
        boolean mainFlag = true ; //主记录付款金额正负的标识，默认为true表示正，false表示负
        boolean detailFlag = true ;//待冲销详细记录付款金额正负的标识，默认为true表示正，false表示负


        for(int n=0 ; n< detailRecords.size();n++) {

            double main_yetMoney = parseToNumber(mainRecord.getDouble("yetmoney"));//已冲金额
            double main_remainMoney =parseToNumber(mainRecord.getDouble("remainmoney")) ;//未冲金额

            //如果主记录被冲销完，则结束此过程,正常情况未冲金额等于0
            if (main_remainMoney == 0){
                return ;
            }
            double detail_yetmoney = parseToNumber(detailRecords.getJSONObject(n).getDouble("yetmoney"));//已冲金额
            double detail_remainmoney = parseToNumber(detailRecords.getJSONObject(n).getDouble("remainmoney"));//未冲金额
            if (detail_remainmoney == 0){
                //未冲金额需要大于0,否则冲下一条
                continue;
            }

            double main_offsetMoney = mainRecord.getDouble("offsetmoney");
            double detail_offsetMoney = detailRecords.getJSONObject(n).getDouble("offsetmoney");

            if (main_offsetMoney < 0){
                mainFlag = false ;
            }
            if (detail_offsetMoney < 0 ){
                detailFlag = false ;
            }

            if (main_remainMoney >= detail_remainmoney){
                //直接冲销,已冲金额为：ac_remainMoney ，未冲金额为remain=os_remainMoney-ac_remainMoney
                mainRecord.put("yetmoney",formatMoney(main_yetMoney + detail_remainmoney,mainFlag));
                mainRecord.put("remainmoney",formatMoney(main_remainMoney - detail_remainmoney,mainFlag));
                mainRecord.put("money",formatMoney(detail_remainmoney,mainFlag));

                detailRecords.getJSONObject(n).put("yetmoney",formatMoney(detail_yetmoney + detail_remainmoney,detailFlag));
                detailRecords.getJSONObject(n).put("remainmoney",0);//此时挂账记录被冲销完，余额为0
                detailRecords.getJSONObject(n).put("money",formatMoney(detail_remainmoney,detailFlag));//money为本次核销的金额
            }else{
                //冲销记录的未冲金额小于挂账记录的挂账余额
                //此时，冲销记录的未冲金额变为0
                mainRecord.put("yetmoney",formatMoney(main_yetMoney + main_remainMoney,mainFlag));//已冲金额为自己的未冲金额
                mainRecord.put("remainmoney",0);//此时冲销记录已经被冲销完成，未冲金额为0
                mainRecord.put("money",formatMoney(main_remainMoney,mainFlag));//此次核销的金额为冲销记录的未冲金额

                detailRecords.getJSONObject(n).put("yetmoney",formatMoney(detail_yetmoney + main_remainMoney,detailFlag));
                detailRecords.getJSONObject(n).put("remainmoney",formatMoney(detail_remainmoney-main_remainMoney,detailFlag));//此时挂账记录没有被冲销完，余额为ac_remainMoney-os_remainMoney
                detailRecords.getJSONObject(n).put("money",formatMoney(main_remainMoney,detailFlag));//money为本次核销的金额
            }
            //查询往来表中是否已经存在这条记录的冲销记录信息
            List<Map<String, Object>> main_existrecords = dao.getExistInMain(mainRecord,companyId) ;
            if (main_existrecords.size() > 0){
                //冲销操作时，正常情况对于这笔冲销记录，如果存在主表中，那么也只有一条类型为冲销的此记录,此时更新往来主表冲销记录
                uqmainid = String.valueOf(main_existrecords.get(0).get("uqmainid"));
                mainRecord.put("uqmainid",uqmainid);
                dao.updateMaindata(mainRecord,uqmainid,companyId);
            }else {
                TglAcOffsetMain tglAcOffsetMain = new TglAcOffsetMain();
                tglAcOffsetMain.setUqmainid(uqmainid);
                tglAcOffsetMain.setUqcompanyid(companyId);
                TglAcOffsetDetail tglAcOffsetDetail1 = new TglAcOffsetDetail();
                tglAcOffsetMain.setUqvoucherid(mainRecord.getString("voucherid"));
                tglAcOffsetMain.setUqvoudetailid(mainRecord.getString("uqvoucherdetailid"));
//                tglAcOffsetMain.setUqvoudetailid(tglAcOffsetDetail1);
//                tglAcOffsetMain.setUqmainid(mainRecord.getString("iniid"));
//                tglAcOffsetMain.setUqmainid(mainRecord.getString("uqaccountid"));
                tglAcOffsetMain.setIniid(mainRecord.getString("iniid"));
                tglAcOffsetMain.setUqaccountid(mainRecord.getString("uqaccountid"));
                tglAcOffsetMain.setUqledgetypeid(mainRecord.getString("uqledgetypeid"));
                tglAcOffsetMain.setUqledgeid(mainRecord.getString("uqledgeid"));
                tglAcOffsetMain.setTotalmoney(new BigDecimal(mainRecord.getString("offsetmoney")));

                tglAcOffsetMain.setRushedmoney(new BigDecimal(mainRecord.getString("yetmoney")));
                tglAcOffsetMain.setUquserid(userId);
                tglAcOffsetMain.setInserttime(DateUtils.getDateTime());
                tglAcOffsetMain.setUpdatetime(DateUtils.getDateTime());
                tglAcOffsetMain.setInttype(new BigDecimal(mainRecord.getString("inttype")));
                tglAcOffsetMain.setTenantId(companyId);

                dao.insert(tglAcOffsetMain);
                //添加主表新冲销记录
//                dao.insertOffsetData(mainRecord,uqmainid,companyId);
            }
            //更新主记录对应的明细记录
            dao.updateOffsetDetail(main_uqdetailid,mainRecord.getDouble("money"),mainRecord.getString("uqmainid"),companyId);

            List<Map<String, Object>>  onaccount_existrecords = dao.getExistInMain(detailRecords.getJSONObject(n),companyId) ;

            String detail_mainid = UUID.randomUUID().toString().toUpperCase();
            if(onaccount_existrecords.size()>0){
                //冲销操作时，对于这笔挂账记录，如果在主表中存在。那么也只有一条类型为挂账的此记录，此时更新这条主记录
                detail_mainid = String.valueOf(onaccount_existrecords.get(0).get("uqmainid"));
                detailRecords.getJSONObject(n).put("uqmainid",detail_mainid);
                dao.updateMaindata(detailRecords.getJSONObject(n),detail_mainid,companyId);
            }else{
                TglAcOffsetMain tglAcOffsetMain = new TglAcOffsetMain();
                tglAcOffsetMain.setUqmainid(detail_mainid);
                tglAcOffsetMain.setUqcompanyid(companyId);

//                TglAcOffsetDetail tglAcOffsetDetail1 = new TglAcOffsetDetail();
                tglAcOffsetMain.setUqvoucherid(detailRecords.getJSONObject(n).getString("voucherid"));
                tglAcOffsetMain.setUqvoudetailid(detailRecords.getJSONObject(n).getString("uqvoucherdetailid"));
//                tglAcOffsetMain.setUqvoudetailid(tglAcOffsetDetail1);
                tglAcOffsetMain.setIniid(detailRecords.getJSONObject(n).getString("iniid"));
                tglAcOffsetMain.setUqaccountid(detailRecords.getJSONObject(n).getString("uqaccountid"));
//                tglAcOffsetMain.setUqvoucherid(detailRecords.getJSONObject(n).getString("voucherid"));

                tglAcOffsetMain.setUqledgetypeid(detailRecords.getJSONObject(n).getString("uqledgetypeid"));
                tglAcOffsetMain.setUqledgeid(detailRecords.getJSONObject(n).getString("uqledgeid"));
                tglAcOffsetMain.setTotalmoney(new BigDecimal(detailRecords.getJSONObject(n).getString("offsetmoney")));

                tglAcOffsetMain.setRushedmoney(new BigDecimal(detailRecords.getJSONObject(n).getString("yetmoney")));
                tglAcOffsetMain.setUquserid(userId);
                tglAcOffsetMain.setInserttime(DateUtils.getDateTime());
                tglAcOffsetMain.setUpdatetime(DateUtils.getDateTime());
                tglAcOffsetMain.setInttype(new BigDecimal(detailRecords.getJSONObject(n).getString("inttype")));
                tglAcOffsetMain.setTenantId(companyId);

                dao.insert(tglAcOffsetMain);
                //添加主表挂账记录
//                dao.insertOffsetData(detailRecords.getJSONObject(n),detail_mainid);
            }
            detailRecords.getJSONObject(n).put("uqdetailid",UUID.randomUUID().toString());
            detailRecords.getJSONObject(n).put("uqbatchid",uqbatchid);
            TglAcOffsetDetail tglAcOffsetDetail1 = new TglAcOffsetDetail();
            tglAcOffsetDetail1.setUqdetailid(detailRecords.getJSONObject(n).getString("uqdetailid"));
            tglAcOffsetDetail1.setUqbatchid(detailRecords.getJSONObject(n).getString("uqbatchid"));
            tglAcOffsetDetail1.setUqmainid(detail_mainid);
            tglAcOffsetDetail1.setUqcompanyid(companyId);
            tglAcOffsetDetail1.setUqvoucherid(detailRecords.getJSONObject(n).getString("voucherid"));
            tglAcOffsetDetail1.setUqvoudetailid(detailRecords.getJSONObject(n).getString("uqvoucherdetailid"));
            tglAcOffsetDetail1.setIniid(detailRecords.getJSONObject(n).getString("iniid"));
            tglAcOffsetDetail1.setUqaccountid(detailRecords.getJSONObject(n).getString("uqaccountid"));
            tglAcOffsetDetail1.setUqledgetypeid(detailRecords.getJSONObject(n).getString("uqledgetypeid"));
            tglAcOffsetDetail1.setUqledgeid(detailRecords.getJSONObject(n).getString("uqledgeid"));
            tglAcOffsetDetail1.setMoney(new BigDecimal(detailRecords.getJSONObject(n).getString("money")));
            tglAcOffsetDetail1.setUquserid(userId);
            tglAcOffsetDetail1.setInserttime(DateUtils.getDateTime());
            tglAcOffsetDetail1.setUpdatetime(DateUtils.getDateTime());
            tglAcOffsetDetail1.setInttype(new BigDecimal(detailRecords.getJSONObject(n).getString("inttype")));
            tglAcOffsetDetail1.setTenantId(companyId);
            tglAcOffsetDetailDao.insert(tglAcOffsetDetail1);
//            dao.insertOffsetDetail(detailRecords.getJSONObject(n),detail_mainid);

        }
    }
    private  void dataCheck(JSONArray mainRecords,JSONArray detailRecords) throws Exception{
        String rush_flag = getSystemConfig("rush_flag");
        for (int i=0 ; i<mainRecords.size() ; i++)
        {
            String main_accountId = mainRecords.getJSONObject(i).getString("uqaccountid");
            String main_ledgeTypeId = mainRecords.getJSONObject(i).getString("uqledgetypeid");
            String main_ledgeId = mainRecords.getJSONObject(i).getString("uqledgeid");
            for (int j=0 ; j<detailRecords.size();j++)
            {
                String detail_accountId = detailRecords.getJSONObject(j).getString("uqaccountid");
                String detail_ledgeTypeId = detailRecords.getJSONObject(j).getString("uqledgetypeid");
                String detail_ledgeId = detailRecords.getJSONObject(j).getString("uqledgeid");

                if ("T".equals(rush_flag))
                {
                    if (main_accountId.equals(detail_accountId)&&main_ledgeTypeId.equals(detail_ledgeTypeId)&&main_ledgeId.equals(detail_ledgeId)){
                        continue;
                    }else {
                        throw new IllegalAccessException("科目及分户不同，不能冲销");
                    }
                }else {
                    if (main_accountId.equals(detail_accountId)){
                        continue;
                    }else {
                        throw new IllegalAccessException("科目不同，不能冲销");
                    }
                }
            }
        }
    }
    /**
     * 还原冲销
     * @param revertRecords 要还原的记录集合的字符串形式
     */
    public void revertRush(String revertRecords) {
        JSONObject jsonObject = JSONObject.parseObject(revertRecords);
        Object o = jsonObject.get("revertRecords");
        JSONArray records = JSONArray.parseArray(String.valueOf(o));
        String companyId = SessionUtils.getUser().getCompanyId();
        for (int i=0 ; i< records.size() ; i++){
            String uqmainid = records.getJSONObject(i).getString("uqmainid");
            int inttype = records.getJSONObject(i).getIntValue("inttype");
            //找出对应的详细记录
            List<Map<String, Object>> opDetails = dao.queryAllOpDetails(uqmainid,inttype,this.getOppType(inttype),companyId);
            //删除本方明细记录
            dao.deleteAllDetails(uqmainid,inttype,companyId);
            //删除本方主数据
            dao.deleteMainRecord(uqmainid,companyId);

            //更新对应的详细记录对应的主记录
            for (Map<String, Object> opDetail :opDetails){
                String opMainId = String.valueOf(opDetail.get("uqmainid"));
                String opdetailId = String.valueOf(opDetail.get("uqdetailid"));
                double opmoney = Double.valueOf(String.valueOf(opDetail.get("opmoney")));
                double money =Double.valueOf(String.valueOf(opDetail.get("money"))) ; //选中的这条要撤销记录明细记录中的冲销

                boolean opFlag = true ;

                if (opmoney<0){
                    opFlag = false ;
                }

                opmoney = this.parseToNumber(opmoney);
                money = this.parseToNumber(money);

                List<Map<String, Object>> opMain = dao.getMain(opMainId,companyId);//要撤销的明细记录对应的主记录

                double rushedmoney = this.parseToNumber( Double.valueOf(String.valueOf(opMain.get(0).get("rushedmoney"))));
                double notrushedmoney = this.parseToNumber(Double.valueOf(String.valueOf(opMain.get(0).get("notrushedmoney"))));

                double update_rushedmoney_main  ;
                double update_norushedmoney_main ;

                double update_money_detail ;
                if (opmoney <= money){
                    //删除此条明细
                    dao.deleteDetail(opdetailId,companyId);
                    //判断此条明细对应的主记录是否存在其他冲销明细
                    if(rushedmoney > opmoney){
                        update_rushedmoney_main = this.formatMoney(rushedmoney - opmoney ,opFlag);
                        update_norushedmoney_main = this.formatMoney(notrushedmoney + opmoney,opFlag) ;
                        dao.updateMain(opMainId,update_rushedmoney_main,update_norushedmoney_main,companyId);
                    }else {
                        dao.deleteMainRecord(opMainId,companyId);
                    }
                }else{
                    update_money_detail = this.formatMoney(opmoney - money,opFlag) ;
                    update_rushedmoney_main = this.formatMoney(rushedmoney - money,opFlag) ;
                    update_norushedmoney_main = this.formatMoney(notrushedmoney + money,opFlag) ;
                    dao.updateDetail(opdetailId,update_money_detail,companyId);
                    dao.updateMain(opMainId,update_rushedmoney_main,update_norushedmoney_main,companyId);

                }
            }
            //删除掉本次冲销金额为0的明细数据
            dao.deleteDetailForEmpty(companyId);

        }

    }
    public List<Map<String, Object>> getOffsetData(Map<String, Object> params){
        List<Map<String, Object>> datas = new ArrayList<>();
        //获取凭证中的冲销数据
        filterParams(params);
        List<Map<String, Object>> datas_voucher = getVoucherData(params,"offset");
        datas.addAll(datas_voucher);
        // 获取往来初始化中的冲销数据
        List<Map<String, Object>> datas_init = dao.getOffsetInitDatas(2,params,SessionUtils.getUser().getCompanyId());
        datas.addAll(datas_init);
        // 取往来表中冲销记录。对比后排除掉余额为0的数据,并且计算已冲金额和余额
        return filterData(datas,1,params);
    }
    public List<Map<String, Object>> getOffsetData1(Map<String, Object> params){
        List<Map<String, Object>> datas = new ArrayList<>();
        //获取凭证中的冲销数据
        filterParams(params);
        List<Map<String, Object>> datas_voucher = getVoucherData1(params);
        datas.addAll(datas_voucher);
        // 获取往来初始化中的冲销数据
        List<Map<String, Object>> datas_init = getOffsetInitDatas1(params);
        datas.addAll(datas_init);
        //对数据按照记账日期重新进行排序
        for (int i = 1; i < datas.size(); i++) {
            Map<String, Object> temp = datas.get(i);
            int j = i - 1;
            while (j >= 0 && Integer.valueOf(datas.get(j).get("accountdate").toString().replaceAll("-", "").substring(0,8)) > Integer.valueOf(temp.get("accountdate").toString().replaceAll("-", "").substring(0,8))) {
                datas.set(j + 1, datas.get(j));
                j--;
            }
            datas.set(j + 1, temp);
        }
        // 取往来表中冲销记录。对比后排除掉余额为0的数据,并且计算已冲金额和余额
        return filterData(datas,1,params);
    }
    public List<Map<String, Object>> getOffsetInitDatas1(Map<String, Object> cdtMap) {
        String companyId = SessionUtils.getUser().getCompanyId();

        String strSql = " select * ,t.inttype as inttype,t.offsetmoney as remainmoney from (SELECT   ai.iniid," +
                "          '' as uqvoucherdetailid, " +
                "          '' as voucherid , " +
                "          ai.BUSDATE as accountdate ," +
                "          concat('[',ac.varaccountcode,']',ac.VARACCOUNTNAME) as accountcode ," +
                "          ai.UQACCOUNTID as uqaccountid," +
                "          ai.uqledgetypeid as uqledgetypeid," +
                "          ai.UQLEDGEID as uqledgeid," +
                "          ai.UQCOMPANYID as uqcompanyid ," +
                "          tlt.VARLEDGETYPENAME as accountledgertype, " +
                "          tl.VARLEDGENAME as accountledger,  " +
                "          ai.VARABSTRACT as varabstract  ," +
                "          CASE WHEN (ai.MNYDEBIT = 0) THEN ai.MNYCREDIT" +
                "               ELSE ai.MNYDEBIT END as offsetmoney ," +
                "          0 as yetmoney ," +
                "          '否' as isrelate ," +
                "          '' as intvouchernum," +
                "          ub.name as accountuser ," +
                "          ai.INTTYPE as inttype" +
                "          FROM TGL_AC_INI ai " +
                "          INNER JOIN TGL_AC_INI_REL ir on ai.UQACCOUNTID = ir.UQACCOUNTID and ir.tenant_id='"+companyId+"' " +
                "          INNER JOIN tgl_accounts ac on ac.UQACCOUNTID=ai.UQACCOUNTID and ac.tenant_id='"+companyId+"'" +
                "          left JOIN tgl_ledger tl on tl.UQLEDGEID = ai.UQLEDGEID and tl.tenant_id='"+companyId+"'" +
                "          left JOIN tgl_ledgetype tlt on tlt.UQLEDGETYPEID = tl.UQLEDGETYPEID and tlt.tenant_id='"+companyId+"'" +
                "          inner join sys_user ub on ub.id = ir.UQUSERID and ub.company_id='"+companyId+"'" +
                "          where ai.tenant_id='"+companyId+"' ) t " +
                "          where t.inttype  ";
        Object type = cdtMap.get("offsettype");
        if ("全部".equals(type)) {
            strSql += " in(1,2)";
        } else {
            if (String.valueOf(cdtMap.get("offsettype")).equals("0")) {
                strSql += " in(1,2)";
            }
            if (String.valueOf(cdtMap.get("offsettype")).equals("1")) {
                strSql += " = 2";
            }
            if (String.valueOf(cdtMap.get("offsettype")).equals("2")) {
                strSql += " = 1";
            }
        }
        //根据查询条件拼接sql和参数
        String account = String.valueOf(cdtMap.get("account"));
        String dtfilldatefrom = String.valueOf(cdtMap.get("dtfilldatefrom"));
        String dtfilldateto = String.valueOf(cdtMap.get("dtfilldateto"));
        String account_user = String.valueOf(cdtMap.get("account_user"));
        String varabstract = String.valueOf(cdtMap.get("varabstract"));
        Object obj_money_form = cdtMap.get("money_form");
        Object obj_money_to = cdtMap.get("money_to");
        String accountingperiodfrom = String.valueOf(cdtMap.get("accountingperiodfrom"));
        String accountingperiodto = String.valueOf(cdtMap.get("accountingperiodto"));
        double money_form = 0;
        double money_to = 0;
        if (!String.valueOf(obj_money_form).equals("")) {
            money_form =Double.valueOf(String.valueOf(cdtMap.get("money_form")==null?0:cdtMap.get("money_form")));
        }
        if (!String.valueOf(obj_money_to).equals("")) {
            money_to = Double.valueOf(String.valueOf(cdtMap.get("money_to")==null?0:cdtMap.get("money_to")));
        }
        List<Object> sqlParams = new ArrayList<>();


        if (!account.equals("null")) {
            strSql += " and t.uqaccountid = '"+account+"' ";
        }
        if (!dtfilldatefrom.equals("null") && !dtfilldateto.equals("null")) {
            strSql += " AND t.accountdate >= binary '"+dtfilldatefrom+"' AND t.accountdate <= binary '"+dtfilldateto+"' ";
        }
        if (!account_user.equals("null")) {
            strSql += " AND t.accountuser  like binary '" + account_user + "%'";//容易发生sql注入风险
        }
        if (!varabstract.equals("null")) {
            strSql += " AND t.VARABSTRACT like binary '" + varabstract + "%' ";
        }
        if (!String.valueOf(obj_money_form).equals("null") && !String.valueOf(obj_money_to).equals("null")) {
            strSql += " AND t.offsetmoney >= '"+money_form+"' AND t.offsetmoney <= '"+money_to+"' ";

        }
        if (!String.valueOf(obj_money_form).equals("") && String.valueOf(obj_money_to).equals("")) {
            strSql += " AND t.offsetmoney >=  '"+money_form+"' ";
//            sqlParams.add(money_form);
        }
        if (String.valueOf(obj_money_form).equals("") && !String.valueOf(obj_money_to).equals("")) {
            strSql += " AND t.offsetmoney <= '"+money_to+"' ";
//            sqlParams.add(money_to);
        }
        String ledgerIds = String.valueOf(cdtMap.get("ledger"));
        String ledgertypes = String.valueOf(cdtMap.get("ledgertypeid"));
        String ids = "";
        if (!ledgerIds.equals("null")) {
            ids = createLedgeParams(ledgerIds, 1);
        }
        if (!ledgertypes.equals("null")) {
            if (StringUtils.isBlank(ledgerIds)) {
                ids += createLedgeParams(ledgertypes, 2);
            } else {
                ids += "," + createLedgeParams(ledgertypes, 2);
            }
        }
        if (!ids.equals("")) {
            strSql += " and t.uqledgeid in (" + ids + ") ";
        }
        if (!accountingperiodfrom.equals("null") && !accountingperiodto.equals("null")) {
            String replace = accountingperiodfrom.replace("年", "-").replace("月", "-01");
            String replace1 = accountingperiodto.replace("年", "-").replace("月", "-31");
            strSql += " AND accountdate >= binary '"+replace+"' AND accountdate <= binary '"+replace1+"' ";

        }
        strSql += " order by t.accountdate ";
        return dao.getMapList(strSql);
    }
    private List<Map<String, Object>> getVoucherData1(Map<String, Object> cdtMap) {
        List<Map<String, Object>> datas = new ArrayList<>();
        List<Map<String, Object>> datas_voucher = dao.getDataByVoucher(cdtMap,SessionUtils.getUser().getCompanyId());
        Object obj_money_form = cdtMap.get("money_form")==null? "":cdtMap.get("money_form");
        Object obj_money_to = cdtMap.get("money_to")==null?"":cdtMap.get("money_to");
        double money_form = 0;
        double money_to = 0;
        if (!obj_money_form.equals("")) {
            money_form = Double.valueOf(String.valueOf(cdtMap.get("money_form")))
            ;
        }
        if (!obj_money_to.equals("")) {
            money_to =Double.valueOf(String.valueOf(cdtMap.get("money_to")));
        }
        Object dataType = cdtMap.get("offsettype");
        String ledgerIds = String.valueOf(cdtMap.get("ledger"));
        String ledgertypes = String.valueOf(cdtMap.get("ledgertypeid"));
        for (Map<String, Object> em : datas_voucher) {
            String isledge = String.valueOf(em.get("intisledge"));
            String voucherType = String.valueOf(em.get("vouchertype"));
            double debitMoney = StringUtils.isNullOrEmpty(em.get("mnydebit"))?0.00:Double.valueOf(em.get("mnydebit").toString()) ;
            double creditMoney =StringUtils.isNullOrEmpty( em.get("mnycredit"))?0.00:Double.valueOf( em.get("mnycredit").toString());
            String accountingperiod = String.valueOf(em.get("accountingperiod"));
            boolean isoffset = isOffset(voucherType, debitMoney, creditMoney);
            if ("1".equals(dataType) && !isoffset) {
                continue;
            }
            if ("2".equals(dataType) && isoffset) {
                continue;
            }
            if ("1".equals(isledge)) {
                //表示存在分户，需要查询出分户分摊的所有记录
                String voucherDetailId = String.valueOf(em.get("uqvoucherdetailid"));
                String newledgerIds = createLedgeParams(ledgerIds, 1);
                String newledgertypes = createLedgeParams(ledgertypes, 2);
                String ledgerParams = newledgerIds;
                if (!newledgerIds.equals("")) {
                    ledgerParams = newledgerIds;
                }
                if (!ledgerParams.equals("") && !newledgertypes.equals("")) {
                    ledgerParams += "," + newledgertypes;
                } else {
                    ledgerParams += newledgertypes;
                }
                List<Map<String, Object>> ledgeVouchers = dao.getLedgerVouDetail(voucherDetailId, ledgerParams,SessionUtils.getUser().getCompanyId());
                for (Map<String, Object> ledgeVoucher : ledgeVouchers) {
                    Map<String, Object> ledge_voucher = new HashMap<>() ;
                    double ledgeMoney = Double.valueOf(ledgeVoucher.get("mnyamount").toString()) ;
                    if (!obj_money_form.equals("") && !obj_money_to.equals("")) {
                        if (money_form > ledgeMoney || ledgeMoney > money_to) {
                            continue;
                        }
                    }
                    if (!obj_money_form.equals("") && obj_money_to.equals("")) {
                        if (money_form > ledgeMoney) {
                            continue;
                        }
                    }
                    if (obj_money_form.equals("") && !obj_money_to.equals("")) {
                        if (ledgeMoney > money_to) {
                            continue;
                        }
                    }
                    ledge_voucher.putAll(em);
                    ledge_voucher.put("accountledgertype", ledgeVoucher.get("varledgetypename"));
                    ledge_voucher.put("accountledger", ledgeVoucher.get("varledgename"));
                    ledge_voucher.put("offsetmoney", ledgeMoney);
                    ledge_voucher.put("remainmoney", ledgeMoney);
                    ledge_voucher.put("accountingperiod", accountingperiod);
                    ledge_voucher.put("uqledgeid", ledgeVoucher.get("uqledgeid"));
                    ledge_voucher.put("uqledgetypeid", ledgeVoucher.get("uqledgertypeid"));
                    datas.add(ledge_voucher);
                }
            } else {
                //增加金额过滤，由于在sql中添加金额过滤会导致有分户时的金额过滤无效，所以再java代码中实现过滤
                double offsetmoney = Double.valueOf(String.valueOf(em.get("offsetmoney")));
                if (!obj_money_form.equals("") && !obj_money_to.equals("")) {
                    if (money_form > offsetmoney || offsetmoney > money_to) {
                        continue;
                    }
                }
                if (!obj_money_form.equals("") && obj_money_to.equals("")) {
                    if (money_form > offsetmoney) {
                        continue;
                    }
                }
                if (obj_money_form.equals("") && !obj_money_to.equals("")) {
                    if (offsetmoney > money_to) {
                        continue;
                    }
                }
                if (!ledgerIds.equals("null") || !ledgertypes.equals("null")) {
                    continue;
                }
                datas.add(em);
            }
        }
        return datas;
    }
    private List<Map<String, Object>> filterData(List<Map<String, Object>> data , int type, Map<String, Object> params){
        if (data.size() == 0){
            return data ;
        }
        List<Map<String, Object>> results = new ArrayList<>();
        //根据状态过滤，0全部，1未冲销，2已经冲销（包含全部冲销和部分冲销）3.部分冲销
        String status = String.valueOf(params.get("intstatus"));
        //判断是主界面请求数据还是窗口
        int isWindow = (int) params.get("iswindow");
        List<Map<String, Object>> newrushData = dao.newgetRushData(type,SessionUtils.getUser().getCompanyId());
        for (Map<String, Object> entityMap : data){
            entityMap.put("inttype",type);//添加数据类型字段，区分改条记录是冲销数据还是挂账数据
            String voucherDetailId = String.valueOf(entityMap.get("uqvoucherdetailid"));
            String initId = String.valueOf(entityMap.get("iniid"));
            String ledgeId = String.valueOf(entityMap.get("uqledgeid"));

//            List<Map<String, Object>> rushData = dao.getRushData(voucherDetailId,ledgeId,initId,type,SessionUtils.getUser().getCompanyId());
           //真tm屎山代码啊，有多少你tm去查多少次数据库
            List<Map<String, Object>> rushData= new ArrayList<>();
            for (Map<String, Object> newrushDatum : newrushData) {
                        String UQLEDGEID = newrushDatum.get("UQLEDGEID")==null?"":newrushDatum.get("UQLEDGEID").toString();
                        String UQVOUDETAILID = newrushDatum.get("UQVOUDETAILID")==null?"":newrushDatum.get("UQVOUDETAILID").toString();
                        String INIID = newrushDatum.get("INIID").toString();
                        if (initId.equals("")&&!ledgeId.equals("")){
                            if (!UQLEDGEID.equals(ledgeId)){
                                continue;
                            }
                            if (!UQVOUDETAILID.equals(voucherDetailId)){
                                continue;
                            }
                        }
                        if (initId.equals("")&&ledgeId.equals("")){
                            if (!UQVOUDETAILID.equals(voucherDetailId)){
                                continue;
                            }
                        }
                        if (!initId.equals("")){
                            if (INIID.equals(initId)){
                                continue;
                            }
                        }
                rushData.add(newrushDatum);
                }
            if (rushData.size() == 0){
                double yetmoney =  Double.valueOf( entityMap.get("yetmoney").toString()); // 冲销金额
                double remainmoney = Double.valueOf( entityMap.get("remainmoney").toString()); // 挂账金额
                if (status.equals("1")){
                    if (yetmoney != 0 ){//已冲金额不等于0，表示发生过冲销，过滤掉
                        continue;
                    }
                }
                if (status.equals("2")){
                    if (yetmoney == 0){ // 表示未冲销，过滤掉
                        continue;
                    }
                }
                if (status.equals("3")) {   // 表示部分冲销
                    if (remainmoney==0||yetmoney == 0){  // 表示冲销，过滤掉
                        continue;
                    }
                }
                if (isWindow == 1 && entityMap.get("remainmoney").toString().equals("0")){
                    continue;
                }
                entityMap.put("uqmainid","");
                results.add(entityMap);
            }else {
                //正常情况如果存在数据，有且只有一条
                String uqmainid = String.valueOf(rushData.get(0).get("uqmainid"));
                double rushedmoney = Double.valueOf(rushData.get(0).get("rushedmoney").toString()) ;
                double notRushedMoney = Double.valueOf( rushData.get(0).get("notrushedmoney").toString());
                if (status.equals("1")){
                    if (rushedmoney != 0){
                        continue;//过滤掉
                    }
                }
                if (status.equals("2")){
                    if (rushedmoney == 0){
                        continue;
                    }
                }
                if (status.equals("3")) {   // 表示部分冲销
                    if (notRushedMoney==0||rushedmoney == 0){  // 表示冲销，过滤掉
                        continue;
                    }
                }
                if (isWindow == 1 && notRushedMoney == 0){
                    continue;
                }
                entityMap.put("yetmoney",rushedmoney);
                entityMap.put("remainmoney",notRushedMoney);
                entityMap.put("uqmainid",uqmainid);
                results.add(entityMap);
            }
        }
        return  results ;
    }
    /**
     * 取凭证中的冲销挂账数据
     * @param jsonParams 查询数据时要用的参数的json封装
     * @param dataType 去数据类型，dataType=offset表示取冲销数据，dataType=onaccount表示取挂账数据
     * @return
     */
    public List<Map<String, Object>> getVoucherData(Map<String, Object> jsonParams,String dataType){
        List<Map<String, Object>> datas = new ArrayList<>();
        List<Map<String, Object>> datas_voucher = dao.getDataByVoucher(jsonParams,SessionUtils.getUser().getCompanyId());

        Object obj_money_form = jsonParams.get("money_form")==null? "":jsonParams.get("money_form");
        Object obj_money_to = jsonParams.get("money_to")==null?"":jsonParams.get("money_to");
        double money_form = 0;
        double money_to = 0;
        if(!obj_money_form.equals(""))
        {
            money_form = Double.valueOf(String.valueOf(jsonParams.get("money_form")))
            ;
        }
        if(!obj_money_to.equals(""))
        {
            money_to =Double.valueOf(String.valueOf(jsonParams.get("money_to")));
        }
        String ledgerIds = String.valueOf(jsonParams.get("ledger"));
        String ledgertypes = String.valueOf(jsonParams.get("ledgertypeid"));
        ledgerIds = createLedgeParams(ledgerIds,1);
        ledgertypes = createLedgeParams(ledgertypes,2);
        String ledgerParams = ledgerIds;
        if(!ledgerIds.equals("")){
            ledgerParams = ledgerIds ;
        }
        if (!ledgerParams.equals("")&& !ledgertypes.equals("")){
            ledgerParams += ","+ledgertypes ;
        }else {
            ledgerParams += ledgertypes ;
        }
        jsonParams.put("ledger",ledgerParams);
        for(Map<String, Object> em : datas_voucher){
            String isledge = String.valueOf(em.get("intisledge"));
            String voucherType = String.valueOf(em.get("vouchertype"));
            double debitMoney =StringUtils.isNullOrEmpty(em.get("mnydebit"))?0.00:Double.valueOf(em.get("mnydebit").toString()) ;
            double creditMoney = StringUtils.isNullOrEmpty( em.get("mnycredit"))?0.00:Double.valueOf( em.get("mnycredit").toString());
            boolean isoffset = isOffset(voucherType,debitMoney,creditMoney);



            if ("offset".equals(dataType) && !isoffset){
                continue;
            }
            if ("onaccount".equals(dataType) && isoffset){
                continue;
            }
            if ("1".equals(isledge)){
                //表示存在分户，需要查询出分户分摊的所有记录
                String voucherDetailId = String.valueOf(em.get("uqvoucherdetailid"));

                List<Map<String, Object>> ledgeVouchers = dao.getLedgerVouDetail(voucherDetailId,ledgerParams,SessionUtils.getUser().getCompanyId());
                for (Map<String, Object> ledgeVoucher : ledgeVouchers){
                    Map<String, Object> ledge_voucher = new HashMap<>() ;
                    double ledgeMoney = Double.valueOf(ledgeVoucher.get("mnyamount").toString()) ;
                    if(!obj_money_form.equals("") && !obj_money_to.equals(""))
                    {
                        if (money_form > ledgeMoney || ledgeMoney > money_to)
                        {
                            continue;
                        }
                    }
                    if(!obj_money_form.equals("") && obj_money_to.equals(""))
                    {
                        if (money_form > ledgeMoney)
                        {
                            continue;
                        }
                    }
                    if(obj_money_form.equals("") && !obj_money_to.equals(""))
                    {
                        if (ledgeMoney > money_to)
                        {
                            continue;
                        }
                    }
                    ledge_voucher.putAll(em);
                    ledge_voucher.put("accountledgertype",ledgeVoucher.get("varledgetypename"));
                    ledge_voucher.put("accountledger",ledgeVoucher.get("varledgename"));
                    ledge_voucher.put("offsetmoney",ledgeMoney);
                    ledge_voucher.put("remainmoney",ledgeMoney);
                    ledge_voucher.put("uqledgeid",ledgeVoucher.get("uqledgeid"));
                    ledge_voucher.put("uqledgetypeid",ledgeVoucher.get("uqledgertypeid"));
                    datas.add(ledge_voucher);
                }
            }else{
                //增加金额过滤，由于在sql中添加金额过滤会导致有分户时的金额过滤无效，所以再java代码中实现过滤
                double offsetmoney = Double.valueOf(String.valueOf(jsonParams.get("offsetmoney")==null?0:jsonParams.get("offsetmoney")));
                if(!obj_money_form.equals("") && !obj_money_to.equals(""))
                {
                    if (money_form > offsetmoney || offsetmoney > money_to)
                    {
                        continue;
                    }
                }
                if(!obj_money_form.equals("") && obj_money_to.equals(""))
                {
                    if (money_form > offsetmoney)
                    {
                        continue;
                    }
                }
                if(obj_money_form.equals("") && !obj_money_to.equals(""))
                {
                    if (offsetmoney > money_to)
                    {
                        continue;
                    }
                }
                if (!StringUtils.isBlank(ledgerIds) || !StringUtils.isBlank(ledgertypes) ){
                    continue;
                }
                datas.add(em);
            }


        }
        return datas;
    }

    public String createLedgeParams(String idstrs, int type){
        String result = "";
        if (StringUtils.isNullOrEmpty(idstrs)){
            return result ;
        }
        String[] strs = idstrs.split(",");
        List<String> params = new ArrayList<>();
        for (String str : strs){
            if(type == 1){
                if (str.contains("+")){//表示存在下级节点
                    str = str.substring(0,str.lastIndexOf("+"));
                    params.add(str);
                    List<Map<String, Object>> ids = dao.getLedgeByParentId(str,SessionUtils.getUser().getCompanyId());
                    for(Map<String, Object> em : ids){
                        params.add(String.valueOf(em.get("uqledgeid")));
                    }
                }else {
                    params.add(str);
                }
            }else if (type == 2){
                List<Map<String, Object>> ids = dao.getLedgeByledgeTypeId(str,SessionUtils.getUser().getCompanyId());
                for(Map<String, Object> em : ids){
                    params.add(String.valueOf(em.get("uqledgeid")));
                }
            }else {
                throw new IllegalArgumentException("type 必须为1或者2,当前为："+type);
            }


        }
        if (params.size()>0){
            for (String id : params){
                if (StringUtils.isNotEmpty(id)){
                    result += "'"+id +"'," ;
                }

            }
            result = result.substring(0,result.length() - 1);
        }
        return result ;
    }
    private boolean isOffset(String voucherType,double debitMoney,double creditMoney){
        if (("应付".equals(voucherType) || "预收".equals(voucherType)) && debitMoney < 0){
            //挂账
            return false ;
        }
        if (("应付".equals(voucherType) || "预收".equals(voucherType)) && creditMoney > 0){
            //挂账
            return false ;
        }
        if (("应付".equals(voucherType) || "预收".equals(voucherType)) && debitMoney > 0){
            //冲销
            return true ;
        }
        if (("应付".equals(voucherType) || "预收".equals(voucherType)) && creditMoney < 0){
            //冲销
            return true ;
        }

        if (("应收".equals(voucherType) || "预付".equals(voucherType)) && debitMoney > 0){
            //挂账
            return false ;
        }
        if (("应收".equals(voucherType) || "预付".equals(voucherType)) && creditMoney < 0){
            //挂账
            return false ;
        }
        if (("应收".equals(voucherType) || "预付".equals(voucherType)) && debitMoney < 0){
            //冲销
            return true ;
        }
        if (("应收".equals(voucherType) || "预付".equals(voucherType)) && creditMoney > 0){
            //冲销
            return true ;
        }

        return true ;

    }
    private void filterParams(Map<String, Object> params){
        int isWindow = (int) params.get("iswindow");
        if(isWindow == 1){
            String rush_flag = getSystemConfig("rush_flag");
            if (rush_flag.equals("F")){
                params.put("ledger","");
                params.put("ledgertypeid","");
            }
        }
    }
    private String getSystemConfig(String paramCode){
        String rush_flag ="T";//默认为T
        try {
            rush_flag = dao.getSystemConfig(SessionUtils.getUser().getCompanyId(),paramCode);
        } catch (Exception e) {
        }

        return rush_flag ;
    }
    /**
     * 人工匹配
     * @param mainData 封装主记录信息的JSONObject的字符串形式
     * @param detaildatas 封装详细记录信息集合的JSONArray的字符串形式
     */
    public void doManualRush(String mainData, String detaildatas) {
        String companyId = SessionUtils.getUser().getCompanyId();
        String userId = SessionUtils.getUser().getId();
        JSONObject main_data = JSONObject.parseObject(mainData);
        JSONArray detail_datas = JSONArray.parseArray(detaildatas);
        //生成批次号
        String uqbatchid = UUID.randomUUID().toString().toUpperCase();//生成批次号
        main_data.put("uqbatchid",uqbatchid);
        //保存主记录
        List<Map<String, Object>> existRecords = dao.getExistInMain(main_data,companyId);
        main_data.put("yetmoney",main_data.getString("money"));
        main_data.put("remainmoney",new BigDecimal(main_data.getString("remainmoney")).subtract(new BigDecimal(main_data.getString("money"))));
        String mainId = UUID.randomUUID().toString() ;
        if (existRecords.size() > 0){
            mainId = String.valueOf(existRecords.get(0).get("uqmainid"));
            dao.updateMaindata(main_data,mainId,companyId);
        }else{
            TglAcOffsetMain tglAcOffsetMain = new TglAcOffsetMain();
            tglAcOffsetMain.setUqmainid(mainId);
            tglAcOffsetMain.setUqcompanyid(companyId);
            tglAcOffsetMain.setUqvoucherid(main_data.getString("voucherid"));
            tglAcOffsetMain.setUqvoudetailid(main_data.getString("uqvoucherdetailid"));
//                tglAcOffsetMain.setUqvoudetailid(tglAcOffsetDetail1);
//                tglAcOffsetMain.setUqmainid(mainRecord.getString("iniid"));
//                tglAcOffsetMain.setUqmainid(mainRecord.getString("uqaccountid"));
            tglAcOffsetMain.setIniid(main_data.getString("iniid"));
            tglAcOffsetMain.setUqaccountid(main_data.getString("uqaccountid"));
            tglAcOffsetMain.setUqledgetypeid(main_data.getString("uqledgetypeid"));
            tglAcOffsetMain.setUqledgeid(main_data.getString("uqledgeid"));
            tglAcOffsetMain.setTotalmoney(new BigDecimal(main_data.getString("offsetmoney")));

            tglAcOffsetMain.setRushedmoney(new BigDecimal(main_data.getString("yetmoney")));
            tglAcOffsetMain.setNotrushedmoney(new BigDecimal(main_data.getString("remainmoney")));
            tglAcOffsetMain.setUquserid(userId);
            tglAcOffsetMain.setInserttime(DateUtils.getDateTime());
            tglAcOffsetMain.setUpdatetime(DateUtils.getDateTime());
            tglAcOffsetMain.setInttype(new BigDecimal(main_data.getString("inttype")));
            tglAcOffsetMain.setTenantId(companyId);

            dao.insert(tglAcOffsetMain);
//            dao.insertOffsetData(main_data,mainId);
        }
        main_data.put("uqdetailid",UUID.randomUUID().toString());
        TglAcOffsetDetail tglAcOffsetDetail = new TglAcOffsetDetail();
        tglAcOffsetDetail.setUqdetailid(main_data.getString("uqdetailid"));
        tglAcOffsetDetail.setUqbatchid(main_data.getString("uqbatchid"));
        tglAcOffsetDetail.setUqmainid(mainId);
        tglAcOffsetDetail.setUqcompanyid(companyId);
        tglAcOffsetDetail.setUqvoucherid(main_data.getString("voucherid"));
        tglAcOffsetDetail.setUqvoudetailid(main_data.getString("uqvoucherdetailid"));
        tglAcOffsetDetail.setIniid(main_data.getString("iniid"));
        tglAcOffsetDetail.setUqaccountid(main_data.getString("uqaccountid"));
        tglAcOffsetDetail.setUqledgetypeid(main_data.getString("uqledgetypeid"));
        tglAcOffsetDetail.setUqledgeid(main_data.getString("uqledgeid"));
        tglAcOffsetDetail.setMoney(new BigDecimal(main_data.getString("money")));
        tglAcOffsetDetail.setUquserid(userId);
        tglAcOffsetDetail.setInserttime(DateUtils.getDateTime());
        tglAcOffsetDetail.setUpdatetime(DateUtils.getDateTime());
        tglAcOffsetDetail.setInttype(new BigDecimal(main_data.getString("inttype")));
        tglAcOffsetDetail.setTenantId(companyId);
        tglAcOffsetDetailDao.insert(tglAcOffsetDetail);
//        currentoffsetDAO.insertOffsetDetail(main_data,mainId);

        for(int n=0 ; n< detail_datas.size();n++)
        {
            JSONObject detail_data = detail_datas.getJSONObject(n);
            detail_data.put("uqbatchid",uqbatchid);
            List<Map<String, Object>> existDetailRecords = dao.getExistInMain(detail_data,companyId);
            String detail_mainid = UUID.randomUUID().toString();
            if (existDetailRecords.size() > 0){
                detail_mainid = String.valueOf(existDetailRecords.get(0).get("uqmainid"));
                dao.updateMaindata(detail_data,detail_mainid,companyId);
            }else {
                TglAcOffsetMain tglAcOffsetMain = new TglAcOffsetMain();
                tglAcOffsetMain.setUqmainid(detail_mainid);
                tglAcOffsetMain.setUqcompanyid(companyId);
                tglAcOffsetMain.setUqvoucherid(detail_data.getString("voucherid"));
                tglAcOffsetMain.setUqvoudetailid(detail_data.getString("uqvoucherdetailid"));
//                tglAcOffsetMain.setUqvoudetailid(tglAcOffsetDetail1);
//                tglAcOffsetMain.setUqmainid(mainRecord.getString("iniid"));
//                tglAcOffsetMain.setUqmainid(mainRecord.getString("uqaccountid"));
                tglAcOffsetMain.setIniid(detail_data.getString("iniid"));
                tglAcOffsetMain.setUqaccountid(detail_data.getString("uqaccountid"));
                tglAcOffsetMain.setUqledgetypeid(detail_data.getString("uqledgetypeid"));
                tglAcOffsetMain.setUqledgeid(detail_data.getString("uqledgeid"));
                tglAcOffsetMain.setTotalmoney(new BigDecimal(detail_data.getString("offsetmoney")));

                tglAcOffsetMain.setRushedmoney(new BigDecimal(detail_data.getString("currentrushmoney")));
                tglAcOffsetMain.setNotrushedmoney(new BigDecimal(detail_data.getString("offsetmoney")).subtract(new BigDecimal(detail_data.getString("currentrushmoney"))));
                tglAcOffsetMain.setUquserid(userId);
                tglAcOffsetMain.setInserttime(DateUtils.getDateTime());
                tglAcOffsetMain.setUpdatetime(DateUtils.getDateTime());
                tglAcOffsetMain.setInttype(new BigDecimal(detail_data.getString("inttype")));
                tglAcOffsetMain.setTenantId(companyId);

                dao.insert(tglAcOffsetMain);
//                da.insertOffsetData(detail_data,detail_mainid);
            }
            detail_data.put("uqdetailid",UUID.randomUUID().toString());
            TglAcOffsetDetail tglAcOffsetDetail1 = new TglAcOffsetDetail();
            tglAcOffsetDetail1.setUqdetailid(detail_data.getString("uqdetailid"));
            tglAcOffsetDetail1.setUqbatchid(detail_data.getString("uqbatchid"));
            tglAcOffsetDetail1.setUqmainid(detail_mainid);
            tglAcOffsetDetail1.setUqcompanyid(companyId);
            tglAcOffsetDetail1.setUqvoucherid(detail_data.getString("voucherid"));
            tglAcOffsetDetail1.setUqvoudetailid(detail_data.getString("uqvoucherdetailid"));
            tglAcOffsetDetail1.setIniid(detail_data.getString("iniid"));
            tglAcOffsetDetail1.setUqaccountid(detail_data.getString("uqaccountid"));
            tglAcOffsetDetail1.setUqledgetypeid(detail_data.getString("uqledgetypeid"));
            tglAcOffsetDetail1.setUqledgeid(detail_data.getString("uqledgeid"));
            tglAcOffsetDetail1.setMoney(new BigDecimal(detail_data.getString("currentrushmoney")));
            tglAcOffsetDetail1.setUquserid(userId);
            tglAcOffsetDetail1.setInserttime(DateUtils.getDateTime());
            tglAcOffsetDetail1.setUpdatetime(DateUtils.getDateTime());
            tglAcOffsetDetail1.setInttype(new BigDecimal(detail_data.getString("inttype")));
            tglAcOffsetDetail1.setTenantId(companyId);
            tglAcOffsetDetailDao.insert(tglAcOffsetDetail1);
//            currentoffsetDAO.insertOffsetDetail(detail_data,detail_mainid);
        }
    }

    public String clearDetailData(String uqaccountid) throws Exception {
        JSONArray strSplit = JSONArray.parseArray(uqaccountid);
        String message = "";
        for (int l = 0; l < strSplit.size(); l++) {
            Object o = strSplit.get(l);
            JSONObject jsonObject = JSONObject.parseObject(String.valueOf(o));
            String uqaccountid1 = jsonObject.getString("uqaccountid");
            if (strSplit.get(l).equals("")) continue;
            String check = clearCheckIsHX(uqaccountid1);
            if (!"0".equals(check)) {
                message = message +l+ ","  ;
                continue;
            }
            clearDetailDatas(uqaccountid1);
        }
        return message;
    }
    public String clearCheckIsHX(String uqaccountid) throws Exception{
        StringBuilder sb = new StringBuilder();
        sb.append("  select count(r.iniid) as iniid from tgl_ac_offset_main r  where  r.uqaccountid = '"+uqaccountid+"'  ");
        List<Map<String, Object>> mapList = dao.getMapList(sb.toString());
        return (mapList.size()!=0&&mapList!=null)?mapList.get(0).get("iniid").toString():"0";
    }
    public void clearDetailDatas(String uqaccountid)throws Exception{

        String sql = " DELETE FROM tgl_ac_ini  WHERE uqaccountid = '"+uqaccountid+"' ";
        dao.execute(sql);

        String sql3 = " DELETE FROM tgl_ac_ini_rel  WHERE uqaccountid = '"+uqaccountid+"' " ;

        dao.execute(sql3);

    }

    public int exportExcels(SearchParams searchParams, HttpServletResponse response) {
        List<Parameter> parameters = new ArrayList<>();
        PageRequest pageRequest = new PageRequest(parameters);
        List<Map<String, Object>> tglLedgers = getdeletedataSql(searchParams);
        List<Map<String, Object>>  mapList = new ArrayList<>();
        Map<String, String> title = new LinkedHashMap<>();
//        分户类别	上级项目名称	分户项目编码	分户项目名称

        title.put("初始化类型","inttype");
        title.put("业务日期","busdate");
        title.put("初始化日期","inidate");
        title.put("分户项摘要目名称","varabstract");
        title.put("科目编号","varaccountcode");
        title.put("科目名称","varaccountname");
        title.put("分户项目编号","varledgecode");
        title.put("分户项目名称","varledgename");
        title.put("借方金额","mnydebit");
        title.put("贷方金额","mnycredit");


        for (int i = 0; i < tglLedgers.size(); i++) {
            Map<String, Object> stringObjectMap = tglLedgers.get(i);
            Map<String,Object> infoMap = new HashMap<>();
            infoMap.put("inttype",stringObjectMap.get("inttype"));
            infoMap.put("busdate",stringObjectMap.get("busdate").toString().substring(0,10));
            infoMap.put("inidate",stringObjectMap.get("inidate").toString().substring(0,10));
            infoMap.put("varabstract",stringObjectMap.get("varabstract"));
            infoMap.put("varaccountcode",stringObjectMap.get("varaccountcode"));
            infoMap.put("varaccountname",stringObjectMap.get("varaccountname"));
            infoMap.put("varledgecode",stringObjectMap.get("varledgecode"));
            infoMap.put("varledgename",stringObjectMap.get("varledgename"));
            infoMap.put("mnydebit",stringObjectMap.get("mnydebit"));
            infoMap.put("mnycredit",stringObjectMap.get("mnycredit"));


            mapList.add(infoMap);
        }
        EasyPoiUtil.DownloadExcel(response,"往来初始化导出",mapList,title);
        return tglLedgers.size();
    }
    public List<Map<String, Object>> getdeletedataSql(SearchParams searchParams){
        Map<String, Object> params = new HashMap<>();
        List<Parameter> parameters = searchParams.getParams();
        for (int i = 0; i < parameters.size(); i++) {
            Parameter parameter = parameters.get(i);
            params.put(parameter.getColumnName(),parameter.getValue());
        }
        Object varAccountCode = params.get("varAccountCode") != null ? params.get("varAccountCode"):"";
        Object varAccountName = params.get("varAccountCode") != null ? params.get("varAccountCode"):"";
        StringBuilder sb = new StringBuilder();
        sb.append(" select (case t.inttype when 1 then '挂账'  else '冲销' end) as inttype,   ");
        sb.append("   t.busdate,g.inidate,t.varabstract,h.varaccountcode,h.varaccountname,pb.varledgecode,concat('[',pb.varledgecode,']',pb.varledgename)  as varledgename,t.mnydebit,t.mnycredit ");
        sb.append("   from tgl_ac_ini t LEFT JOIN tgl_ledger pb on  t.uqledgeid=pb.uqledgeid  ,tgl_ac_ini_rel g,(select s.uqaccountid,s.varaccountname,s.varaccountcode,s.intislastlevel,s.uqtypeid from tgl_accounts s ) h,tob_category y  ");
        sb.append("   where t.uqaccountid=g.uqaccountid ");
        sb.append("   and t.uqaccountid=h.uqaccountid");
        sb.append("   and  h.intislastlevel = 1  ");
        sb.append("   and   h.varaccountcode like   '"+varAccountCode+"%'  ");
        sb.append("   and   h.varaccountname  like   '"+varAccountName+"%' ");
        sb.append("   and y.categorycode = h.uqtypeid  ");
        sb.append("   and y.CATEGORYTYPE='10000002' and y.categorycode in ('1','2','7','8') ");
        sb.append("   ORDER BY h.varaccountcode asc");
        return dao.getMapList(sb.toString());
    }

    public int importExcel(MultipartFile file) throws Exception {
        String uqcompanyid= SessionUtils.getUser().getCompanyId();
        StringHolder errormsg = new StringHolder();
        InputStream is = file.getInputStream();
        Workbook wk = Workbook.getWorkbook(is);
        Sheet st = wk.getSheet(0);  //读取第一个表格
        int rowlength= st.getRows();  //获取表格数据的行数
        if (rowlength <= 1) {
            throw new Exception("表格中没有数据!");
        } else {
            if (rowlength > 1) {
                this.validateUploadFile(st);
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                for (int row = 1; row < rowlength; row++) {

                    String busdate = ObjectUtils.toString(st.getCell(0, row).getContents().trim());
                    if (StringUtils.isEmpty(busdate)) {
                        break;
                    }
                    String varabstract = ObjectUtils.toString(st.getCell(1, row).getContents().trim());
                    String varaccountcode = ObjectUtils.toString(st.getCell(2, row).getContents().trim());
                    String varledgecode = ObjectUtils.toString(st.getCell(3, row).getContents().trim());
                    String mnydebit = ObjectUtils.toString(st.getCell(4, row).getContents().trim());
                    String mnycredit = ObjectUtils.toString(st.getCell(5, row).getContents().trim());

                    Map<String, Object> entity = new HashMap<>();
                    entity.put("busdate", busdate);
                    entity.put("varabstract", varabstract);
                    entity.put("varaccountcode", varaccountcode);
                    entity.put("varledgecode", varledgecode);
                    entity.put("mnydebit", mnydebit);
                    entity.put("mnycredit", mnycredit);
                    entity.put("inttype", this.changeType(varaccountcode,mnydebit,mnycredit));
                    list.add(entity);
                }
                //检查数据是否符合规定
                this.checkInitInfo(list, errormsg);
                //如果文件的数据都正确，则保存数据
                if ("".equals(errormsg.value)) {
                    for (int i = 0; i < list.size(); i++) {
                        //将数据插入表
                        importXLSInitData(list.get(i));
                    }
                }
            }
        }
        return 0;
    }
    public void validateUploadFile(Sheet st) throws Exception {
        if ("业务日期".equals(st.getCell(0, 0).getContents())
                && "摘要".equals(st.getCell(1, 0).getContents())
                && "科目编号".equals(st.getCell(2, 0).getContents())
                && "分户项目编号".equals(st.getCell(3, 0).getContents())
                && "借方金额".equals(st.getCell(4, 0).getContents())
                && "贷方金额".equals(st.getCell(5, 0).getContents())) {
        } else {
            throw new Exception("模版表不正确,请重新选择!");
        }
    }
    /**
     * 转换
     *
     * @author Administrator
     */
    public String changeType(String code, String mne1, String mne2) throws Exception {
        String rr=checkDateType(code);
        BigDecimal bdMne1 = null;

        if(!"".equals(mne1)){
            bdMne1 = new BigDecimal(mne1);
        }
        if(!(mne1.equals("")||mne1.equals("0")||mne1.equals("0.00"))){
            if(rr.equals("应付")||rr.equals("预收")){
                if(bdMne1.compareTo(new BigDecimal("0")) == 1){
                    return "2";
                }
                if(bdMne1.compareTo(new BigDecimal("0")) == -1){
                    return "1";
                }
            }
            if(rr.equals("应收")||rr.equals("预付")) {
                if (bdMne1.compareTo(new BigDecimal("0")) == 1) {
                    return "1";
                }
                if (bdMne1.compareTo(new BigDecimal("0")) == -1) {
                    return "2";
                }
            }
        }else{
            BigDecimal bdMne2 = new BigDecimal(mne2);
            if(rr.equals("应付")||rr.equals("预收")){
                if(bdMne2.compareTo(new BigDecimal("0")) == 1){
                    return "1";
                }
                if(bdMne2.compareTo(new BigDecimal("0")) == -1){
                    return "2";
                }
            }
            if(rr.equals("应收")||rr.equals("预付")){
                if(bdMne2.compareTo(new BigDecimal("0")) == 1){
                    return "2";
                }
                if(bdMne2.compareTo(new BigDecimal("0")) == -1){
                    return "1";
                }

            }
        }
        return "1";
    }
    public String checkDateType(String uqaccountcode) throws Exception {

        String sql=" select dd.uqtypeid from    " +
                "        (select t.INTFLAG,t.uqaccountid,t.varaccountcode,t.varaccountname,h.varaccountname as uqparentid,n.categoryname as intproperty,y.categorycode,y.categoryname as uqtypeid,t.intislastlevel " +
                "           from tgl_accounts t,(select p.uqaccountid,p.varaccountname from tgl_accounts p ) h ,tob_category n, tob_category y  " +
                "          where   h.uqaccountid=t.uqparentid and n.categorycode=t.intproperty and n.CATEGORYTYPE='10000001' and y.categorycode=t.uqtypeid and y.CATEGORYTYPE='10000002')  dd " +
                "          LEFT JOIN (select g.inidate,g.uqaccountid  from  tgl_ac_ini_rel g ) p on dd.uqaccountid=p.uqaccountid  " +
                "           where  dd.intislastlevel=1 and dd.INTFLAG = 2" +
                "           and     dd.categorycode in ('1','2','7','8')   " +
                "           and   dd.varaccountcode =  '"+uqaccountcode+"' " +
                "          ORDER BY dd.varaccountcode asc";


        List<Map<String, Object>> mapList = dao.getMapList(sql);

        return  mapList.size()!=0?mapList.get(0).get("uqtypeid").toString():"0";
    }
    /**
     * 验证导入的初始化主数据的正确性
     *
     * @author Administrator
     */
    public void checkInitInfo(List<Map<String, Object>> list, StringHolder errormsg) throws Exception {
        //1.检验各项非空字段；
        Map<String, Integer> map = new HashMap<String, Integer>();
        boolean hasnull = true;
        boolean dateIsRight = true;
        for (int i = 0; i < list.size(); i++) {
            int j = i + 2;
            Map<String, Object> entity = list.get(i);
            String busdate = String.valueOf(entity.get("busdate"));
            String varabstract = String.valueOf(entity.get("varabstract"));
            String varaccountcode = String.valueOf(entity.get("varaccountcode"));
            String varledgecode = String.valueOf(entity.get("varledgecode"));
            String mnydebit = String.valueOf(entity.get("mnydebit"));
            String mnycredit = String.valueOf(entity.get("mnycredit"));

            //1.检验各项非空字段；
            if (hasnull) {
                if (busdate .equals("null")  || "".equals(busdate)) {
                    errormsg.value += "表中第 " + j + " 行[业务日期]为空|";
                    hasnull = false;
                }
            }
            if (hasnull) {
                if (!(busdate.equals("null")  || "".equals(busdate))) {
//                        String pat = "\\d{4}-\\d{2}-\\d{2}" ;    // 指定好正则表达式
//                        Pattern p = Pattern.compile(pat) ;
//                        String pat1 = "\\d{4}/\\d{2}/\\d{2}" ;    // 指定好正则表达式
//                        Pattern p1 = Pattern.compile(pat1) ;// 实例化Pattern类
//                        Matcher m = p.matcher(busdate) ;
//                        Matcher m1 = p1.matcher(busdate) ; // 实例化Matcher类

                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
                    try {

                        format1.setLenient(false);
                        format1.parse(busdate);
                    } catch (ParseException e) {
                        try {
                            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                            format2.setLenient(false);
                            format2.parse(busdate);
                        } catch (ParseException e2) {
                            errormsg.value += "表中第 " + j + " 行[业务日期]格式应为(yyyy-MM-dd)或(yyyy/MM/dd)|";
                            dateIsRight = false;
                        }
                    }

                }
            }
            if (hasnull) {
                if (varaccountcode == null || "".equals(varaccountcode)) {
                    errormsg.value += "表中第" + j + " 行[科目编号]为空|";
                    hasnull = false;
                }
            }
            if (hasnull) {
                String checkNameList = checkIsInitDate(varaccountcode);
                if (!("0".equals(checkNameList))) {
                    errormsg.value += "表中第" + j + "条数据科目编号为" + varaccountcode + "已经发生过初始化不能导入|";
                    hasnull = false;
                }
            }
            if (hasnull) {
                String checkNameList = checkImportAccountcodeIsExist(varaccountcode);
                if ("0".equals(checkNameList)) {
                    errormsg.value += "表中第" + j + "条数据的[科目编号]在科目表中不存在|";
                    hasnull = false;
                }
            }
            if (hasnull) {
                String checkNameList = checkImportIsNeedType(varaccountcode);
                if ("".equals(checkNameList)) {
                    errormsg.value += "表中第" + j + "条数据的[科目编号]不是“应收，应付，预付，预收”类型的末级科目|";
                    hasnull = false;
                }
            }
            String ppt = importcheckDateIsExistFh(varaccountcode);
            if (ppt.equals("1")) {
                if (hasnull) {
                    String checkNameList = checkImportFHIsExist(varaccountcode, varledgecode);
                    if ("0".equals(checkNameList)) {
                        errormsg.value += "表中第" + j + "条数据的[科目编号]下没有该[分户项目编号]|";
                        hasnull = false;
                    }
                }
                if (hasnull && dateIsRight) {
                    String checkNameList = checkImportInitDateIsExist(varaccountcode, busdate, varledgecode);
                    if (!"0".equals(checkNameList)) {
                        errormsg.value += "表中第" + j + "条数据的[业务日期][分户项目编号]在初始化表中已存在|";
                        hasnull = false;
                    }
                }
            } else {
                if (hasnull) {

                    if (!"".equals(varledgecode)) {
                        errormsg.value += "表中第" + j + "条数据的[科目编号]下没有设置分户|";
                        hasnull = false;
                    }
                }
                if (hasnull && dateIsRight) {
                    String checkNameList = checkImportInitDateIsExist(varaccountcode, busdate, "全部");
                    if (!"0".equals(checkNameList)) {
                        errormsg.value += "表中第" + j + "条数据的[业务日期]在初始化表中已存在|";
                        hasnull = false;
                    }
                }
            }
            if (hasnull) {
                if ((mnydebit == null || "".equals(mnydebit) || "0".equals(mnydebit) || "0.00".equals(mnydebit)) && (mnycredit == null || "0".equals(mnycredit) || "".equals(mnycredit) || "0.00".equals(mnycredit))) {
                    errormsg.value += "表中第" + j + " 行[借方金额]和[贷方金额]都未填写|";
                    hasnull = false;
                }
                if (!(mnydebit == null || "".equals(mnydebit) || "0".equals(mnydebit) || "0.00".equals(mnydebit)) && !(mnycredit == null || "0".equals(mnycredit) || "".equals(mnycredit) || "0.00".equals(mnycredit))) {
                    errormsg.value += "表中第" + j + " 行[借方金额]和[贷方金额]都有填写|";
                    hasnull = false;
                }
            }

            if (hasnull) {
                for (int t = i + 1; t < list.size(); t++) {
                    Map<String, Object> entity3 = list.get(t);
                    String busdate13 = String.valueOf(entity3.get("busdate"));
                    String varaccountcode3 = String.valueOf(entity3.get("varaccountcode"));
                    String varledgecode3 = String.valueOf(entity3.get("varledgecode"));
                    if (hasnull) {
                        if ((busdate.equals(busdate13)) && (varledgecode.equals(varledgecode3)) && (varaccountcode.equals(varaccountcode3))) {
                            errormsg.value += "表中第" + (i + 2) + " 行[业务日期],[科目编号],[分户项目编号]和" + (t + 2) + "行相同|";
                            hasnull = false;
                        }
                    }
                }

            }
        }
    }
    public String checkIsInitDate(String uqaccountcode) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("  select t.inidate from tgl_ac_ini_rel t where  t.uqaccountid = (select k.uqaccountid from tgl_accounts k where k.varaccountcode = '"+uqaccountcode+"' )   ");


        List<Map<String, Object>> mapList = dao.getMapList(sb.toString());

        return  mapList.size()!=0?mapList.get(0).get("inidate").toString():"0";
    }
    /**
     * 导入时查询该科目编号是否存在
     * @author Administrator
     *
     */
    public String checkImportAccountcodeIsExist(String varaccountcode) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("  select COUNT(0) as aa from tgl_accounts t where  t.varaccountcode = '"+varaccountcode+"'   ");


        List<Map<String, Object>> mapList = dao.getMapList(sb.toString());

        return mapList.size()!=0?mapList.get(0).get("aa").toString():"0";
    }
    /**
     * .导入时判断是否是“应收，应付，预付，预收”类型的末级科目
     * @author Administrator
     *
     */
    public String checkImportIsNeedType(String varaccountcode) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("  select t.VARACCOUNTCODE as varaccountcode  from tob_category k , tgl_accounts t WHERE  k.categorycode in ('1','2','7','8')   ");
        sb.append("  and k.categorycode=t.uqtypeid and t.VARACCOUNTCODE = '"+varaccountcode+"' and t.INTISLASTLEVEL=1 ");

        List<Map<String, Object>> mapList = dao.getMapList(sb.toString());

        return  (mapList!=null&&mapList.size()!=0)?mapList.get(0).get("varaccountcode").toString():"";
    }
    /**
     * 导入查询该科目是否设有分户
     * @author Administrator
     *
     */
    public String importcheckDateIsExistFh(String uqaccountcode) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("  select t.INTISLEDGE as INTISLEDGE from tgl_accounts t where t.VARACCOUNTCODE='"+uqaccountcode+"' ");


        List<Map<String, Object>> mapList = dao.getMapList(sb.toString());

        return  mapList.size()!=0?mapList.get(0).get("INTISLEDGE").toString():"0";
    }
    /**
     * 导入时查询分户是否存在
     * @author Administrator
     *
     */
    public String checkImportFHIsExist(String varaccountcode,String varledgecode) throws Exception {
        String sb=" select count(y.VARLEDGECODE) as  varledgecode from tgl_ledger y where y.UQLEDGETYPEID in (" +
                "   select f.UQLEDGETYPEID from tgl_ledgetype f where f.UQLEDGETYPEID in (" +
                "    select t.uqledgetypeid from tgl_account_ledgetype t where t.uqaccountid=" +
                "   (select s.uqaccountid from tgl_accounts s where s.VARACCOUNTCODE= '"+varaccountcode+"' and s.tenant_id = '"+ SessionUtils.getUser().getCompanyId()+"')))" +
                "   and y.INTISLASTLEVEL=1" +
                "    and y.VARLEDGECODE='"+varledgecode+"'";


        List<Map<String, Object>> mapList = dao.getMapList(sb);

        return  mapList.size()!=0?mapList.get(0).get("varledgecode").toString():"0";
    }
    /**
     * 导入时查询该科目编号下，该业务日期和分户是否存在
     * @author Administrator
     *
     */
    public String checkImportInitDateIsExist(String varaccountcode, String date,String varledgecode) throws Exception {
        StringBuilder sb = new StringBuilder();
        if(varledgecode.equals("全部")){
            sb.append("  select COUNT(0) as aa from tgl_ac_ini t where t.busdate = '"+date+"'   and t.uqaccountid=( select k.uqaccountid from tgl_accounts k where k.varaccountcode = ? )  ");
            List<Map<String, Object>> mapList = dao.getMapList(sb.toString());
            return mapList.size()!=0?mapList.get(0).get("aa").toString():"0";
        }else{
            sb.append("  select COUNT(0) as aa from tgl_ac_ini t where t.busdate = '"+date+"' and t.uqledgeid=(select l.uqledgeid from tgl_ledger l where l.varledgecode = '"+varledgecode+"'" +
                    " AND l.tenant_id = '"+SessionUtils.getUser().getCompanyId()+"')  and t.uqaccountid=( select k.uqaccountid from tgl_accounts k where k.varaccountcode = '"+varaccountcode+"'  and k.tenant_id = '"+SessionUtils.getUser().getCompanyId()+"')  ");
            List<Map<String, Object>> mapList = dao.getMapList(sb.toString());
            return mapList.size()!=0?mapList.get(0).get("aa").toString():"0";
        }
    }
    /**
     * 导入初始化数据
     * @author Administrator
     *
     */
    public void importXLSInitData( Map<String, Object> entity) throws Exception
    {
        String uqcompanyid = SessionUtils.getUser().getCompanyId();
        String TENANT_ID = SessionUtils.getUser().getCompanyId();
        String userid = SessionUtils.getUser().getId();
        StringBuilder sb = new StringBuilder();
        sb.append(" INSERT INTO tgl_ac_ini ");
        sb.append(" (iniid,uqaccountid,uqcompanyid,uqledgetypeid,uqledgeid,varabstract,mnydebit,mnycredit,uquserid,busdate,inttype)");
        sb.append(" VALUES ( uuid(), (select t.uqaccountid from tgl_accounts t where t.varaccountcode = '"+entity.get("varaccountcode").toString()+"'  and t.tenant_id = '"+TENANT_ID+"' ), '"+uqcompanyid+"', ");
        sb.append(" (select b.uqledgetypeid  from tgl_ledger b where   b.varledgecode = '"+entity.get("varledgecode")+"'  and b.tenant_id = '"+TENANT_ID+"'),");
        sb.append(" (select c.uqledgeid  from tgl_ledger c where   c.varledgecode = '"+entity.get("varledgecode")+"'  and c.tenant_id = '"+TENANT_ID+"'),");
        sb.append("   '"+entity.get("varabstract")+"', " +
                "'"+Double.parseDouble(entity.get("mnydebit").toString().equals("")?"0":entity.get("mnydebit").toString())+"', " +
                "'"+ Double.parseDouble(entity.get("mnycredit").toString().equals("")?"0":entity.get("mnycredit").toString())+"', '"+userid+"', '"+ entity.get("busdate")+"', '"+ entity.get("inttype")+"')");
        //公司id


        dao.execute(sb.toString());
        StringBuilder sp = new StringBuilder();
        sp.append(" INSERT INTO tgl_ac_ini_rel ");
        sp.append(" (uqaccountid,uqcompanyid,uquserid,inidate)");
        sp.append(" SELECT  (select t.uqaccountid from tgl_accounts t where t.varaccountcode = '"+entity.get("varaccountcode")+"'  and t.tenant_id = '"+TENANT_ID+"' ), '"+uqcompanyid+"', '"+userid+"',SYSDATE(),'"+TENANT_ID+"'  FROM DUAL WHERE NOT EXISTS ");
        sp.append( " (SELECT inidate FROM tgl_ac_ini_rel WHERE uqaccountid = (select k.uqaccountid from tgl_accounts k where k.varaccountcode = '"+entity.get("varaccountcode")+"'  and k.tenant_id = '"+TENANT_ID+"'))");

        dao.execute(sp.toString());

        StringBuilder sx = new StringBuilder();
        sx.append(" UPDATE tgl_ac_ini_rel ta SET ");
        sx.append(" ta.uqcompanyid = '"+uqcompanyid+"',ta.uquserid = '"+userid+"',ta.inidate = SYSDATE()");
        sx.append(" WHERE ta.uqaccountid = (select t.uqaccountid from tgl_accounts t where t.varaccountcode = '"+entity.get("varaccountcode")+"' and t.tenant_id = '"+TENANT_ID+"' )");
        dao.execute(sx.toString());
    }

    public Page<Map<String, Object>> geteditlistById(String uqaccountid) {


        List<Map<String, Object>> datas = this.getInitDetailData(uqaccountid);
        Page<Map<String, Object>> pageData = new Page<>(99999,datas);

        return pageData;
    }
    public List<Map<String, Object>> getInitDetailData(String uqaccountid){
        StringBuilder sb = new StringBuilder();
        sb.append("   select t.iniid,t.busdate,t.varabstract,concat('[',bb.varledgecode,']',bb.varledgename) as uqledgeid,t.mnydebit,t.mnycredit,t.inttype,t.uqledgetypeid, t.uqledgeid as ledgerdetailid ,concat('[',bb.varledgecode,']',bb.varledgename) as ledgertext   ");
        sb.append("   from tgl_ac_ini t LEFT JOIN tgl_ledger bb  on t.uqledgeid = bb.uqledgeid ");
        sb.append("   where    t.uqaccountid= '"+uqaccountid+"' ");
        sb.append("   ORDER BY t.busdate desc");
        return dao.getMapList(sb.toString());
    }
    public void deleteIni(String uqaccountid)
    {
        String sql = " DELETE FROM tgl_ac_ini  WHERE UQACCOUNTID = '"+uqaccountid+"' ";
        dao.execute(sql);

        String sql2 = " UPDATE tgl_ac_ini_rel ta SET ta.inidate = SYSDATE() WHERE ta.uqaccountid = '"+uqaccountid+"' ";
        dao.execute(sql2);


        String sql3 = " DELETE FROM tgl_ac_ini_rel  WHERE uqaccountid = '"+uqaccountid+"'  and not EXISTS " +
                "   (select t.INIID from  tgl_ac_ini t  WHERE t.uqaccountid = '"+uqaccountid+"') ";

        dao.execute(sql3);
    }
    public String insertTglAcIni(List<TglAcIni> entitys) {
        String uqcompanyid = SessionUtils.getUser().getCompanyId();
        String userid = SessionUtils.getUser().getId();
       if (entitys.size()>0){
           deleteIni(entitys.get(0).getUqaccountid());
       }
        for (int i = 0; i < entitys.size(); i++) {
            TglAcIni tglAcIni = entitys.get(i);
            tglAcIni.setIniid(IdGen.uuid());
            tglAcIni.setTenantId(uqcompanyid);
            tglAcIni.setUqcompanyid(uqcompanyid);
            tglAcIni.setUquserid(userid);
            if (tglAcIni.getMnydebit().compareTo(new BigDecimal(0))==-1) {
                tglAcIni.setInttype(new BigDecimal(2));
            } else{
                tglAcIni.setInttype(new BigDecimal(1));
            }
            dao.insertTglAcIni(tglAcIni);
            StringBuilder sp = new StringBuilder();
            sp.append(" INSERT INTO tgl_ac_ini_rel ");
            sp.append(" (uqaccountid,uqcompanyid,uquserid,inidate,tenant_id)");
            sp.append(" SELECT  '"+tglAcIni.getUqaccountid()+"', '"+uqcompanyid+"', '"+userid+"',SYSDATE(),'"+uqcompanyid+"'  FROM DUAL WHERE NOT EXISTS ");
            sp.append( " (SELECT inidate FROM tgl_ac_ini_rel WHERE uqaccountid = '"+tglAcIni.getUqaccountid()+"' and tenant_id = '"+uqcompanyid+"')");

            dao.execute(sp.toString());

            StringBuilder sx = new StringBuilder();
            sx.append(" UPDATE tgl_ac_ini_rel ta SET ");
            sx.append(" ta.uqcompanyid = '"+uqcompanyid+"',ta.uquserid = '"+userid+"',ta.inidate = SYSDATE(), tenant_id = '"+uqcompanyid+"'");
            sx.append(" WHERE ta.uqaccountid = '"+tglAcIni.getUqaccountid()+"' ");
            dao.execute(sx.toString());
        }

        return "";
    }
}
