package com.geeke.vouchermanager.dao;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.data.PageRequest;
import com.geeke.common.persistence.CrudDao;
import com.geeke.vouchermanager.entity.TglAcIni;
import com.geeke.vouchermanager.entity.TglAcOffsetMain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 往来冲销总表DAO接口
 * @author
 * @version
 */
@Mapper
public interface TglAcOffsetMainDao extends CrudDao<TglAcOffsetMain> {


    public int getOnaccountDataPagecount(PageRequest pageRequest);
    public List<Map<String,Object>> getOnaccountDataPage(@Param("params") PageRequest pageRequest,@Param("tenantId") String tenantId,@Param("inttype") int inttype);
    public List<Map<String,Object>> getDataByVoucher(@Param("params") Map<String,Object> pageRequest,@Param("tenantId") String tenantId);
    public List<Map<String,Object>> getOffsetInitDatas(@Param("dataType") int dataType,@Param("params") Map<String,Object> pageRequest,@Param("tenantId") String tenantId);
    public List<Map<String,Object>> getLedgeByParentId(String ledgeParentId,String tenantId);
    public List<Map<String,Object>> getRushData(String voucherDetailId,String ledgeId,String initId,int type,String tenantId);
    public List<Map<String,Object>> newgetRushData(int type,String tenantId);
    public List<Map<String,Object>> getLedgeByledgeTypeId(String ledgeTypeId,String tenantId);
    public List<Map<String,Object>> getLedgerVouDetail(String uqvoucherdetailid,String ledgeIds,String tenantId);
    public List<Map<String,Object>> queryAllOpDetails(String mainId,int inttype ,int opinttype,String tenantId);
    String getSystemConfig(String tenantId,String paramCode);
    public int deleteAllDetails(String mainId,int inttype,String tenantId);
    public int deleteMainRecord(String mainId,String tenantId);
    public List<Map<String,Object>> getMain(String mainId,String tenantId);
    public int deleteDetail(String detailId,String tenantId);
    public int updateMain(String mainId,double rushedmoney,double notrushedmoney,String tenantId);
    public int updateDetail(String detailId,double money,String tenantId);
    public int deleteDetailForEmpty(String tenantId);
    public int insertOffsetDetail(JSONObject params, String mainId   );
    public List<Map<String,Object>> getExistInMain(@Param("params")JSONObject params,@Param("tenantId") String tenantId);
    public int updateMaindata(@Param("params")JSONObject record,@Param("mainId")String mainId,@Param("tenantId")String tenantId);
    public int updateOffsetDetail(String detailId,double money,String uqmainid,String tenantId);

    List<Map<String, Object>> getMapList(String strSql);

    int getMapListcount(String toString);

    void execute(String sql);
    int insertTglAcIni(TglAcIni entity);
}
