package com.geeke.vouchermanager.dao;

import com.geeke.common.data.PageRequest;
import com.geeke.common.persistence.CrudDao;
import com.geeke.vouchermanager.entity.TglVoucherMains;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 制证DAO接口
 * @author
 * @version
 */
@Mapper
public interface TglVoucherMainsDao extends CrudDao<TglVoucherMains> {
 int selectSeq(String tenantId,String uqglobalperiodid,String uqnumbering);
 String decideMoney(String tenantId,String accountCode);
 String getPmCash(String accountid,String uqglobalperiodid);
 String getLastDayOfMonth(String tenantId,String uqglobalperiodid);
 List<Map<String, Object>> getAccountPeriodCashInfo(String voucherid, String tenantId);
 List<Map<String, Object>> findAccountPeriodInfo(String voucherid,String uqtypeid, String tenantId);
 List<Map<String, Object>> findAccountPeriodInfos(String voucherid,String uqtypeid, String tenantId);
 List<Map<String, Object>> getAccountPeriodInfo(String voucherid, String tenantId);
 List<Map<String, Object>> selectAccountPeriodInfo(String voucherid, String tenantId);
 List<Map<String, Object>> getLedgerPeriodCashInfo(String voucherid, String tenantId);
 List<Map<String, Object>> getLedgerPeriodInfo(String voucherid, String tenantId);
 List<Map<String, Object>> lockAccountPeriod(String uqaccountid,String uqglobalperiodid,String uqcompanyid);
 List<Map<String, Object>> lockLedgerPeriod(String uqaccountid,String uqglobalperiodid,String uqcompanyid,String uqledgerid);
 List<Map<String, Object>> lockAccountPeriodNoJZ(String uqaccountid,String uqglobalperiodid,String uqcompanyid);

 List<Map<String, Object>> lockVoucher(String voucherid,String tenantId);
 int deleteVoucherDetail1(String voucherid);
 int deleteVoucherDetail2(String voucherid);
 int deleteVoucherDetail3(String voucherid);
 int deleteAc(String voucherid);
 int delVoucher(String mdom,String voucherid,String tenantId);
 int deleteFlow(String voucherid);
 int selectIntCashFlag(String voucherid);
 int offsetIsTrue(String voucherid);
 int selectvouchernum(String intvouchernum, String uqglobalperiodid, String UQVOUCHERID);
 List<Map<String, Object>> checks(String voucherid);
 public List<Map<String, String>> xjlllistPage(PageRequest pageRequest);
 public int xjlllistcount(PageRequest pageRequest);
 public List<Map<String, Object>> getAccountPeriod(@Param("parameters") Map<String, Object> parameters);
}
