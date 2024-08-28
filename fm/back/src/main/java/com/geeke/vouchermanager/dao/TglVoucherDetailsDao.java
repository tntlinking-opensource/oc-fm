package com.geeke.vouchermanager.dao;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.persistence.CrudDao;
import com.geeke.vouchermanager.entity.TglLedger;
import com.geeke.vouchermanager.entity.TglLedgetype;
import com.geeke.vouchermanager.entity.TglVoucherDetails;
import com.geeke.vouchermanager.entity.VoucherDetail;
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
public interface TglVoucherDetailsDao extends CrudDao<TglVoucherDetails> {
    public List<TglLedgetype> getByUqaccountId(String id);
    public List<TglLedgetype> selectQuestionItemByQuestionId(String id);
    public List<TglVoucherDetails> getByUqvoucherid(String uqvoucherid);
    public int saveVoucherDetail(@Param("entitys")List<VoucherDetail> entitys);
    List<Map<String, Object>> resultparam(String companyid,String uqaccountid);
    List<Map<String, Object>> lst(String companyid);
    List<Map<String, Object>> getentityMaps(String companyid,String uqaccountid,String uqglobalperiodid);
    List<Map<String, Object>> getparamResult(String companyid,String uqaccountid,String uqglobalperiodid);
}
