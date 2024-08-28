package com.geeke.cardasset.supplementreduce.dao;

import com.alibaba.fastjson.JSONObject;
import com.geeke.cardasset.supplementreduce.entity.TfamAssetOld;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 补提冲减DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamAssetOldDao extends CrudDao<TfamAssetOld> {
    Map<String, Object> getBusDate(String companyId);

    Map<String, Object> getSysStartdate(String companyId);


    Map<String, Object> getDepreciationTime(String companyid);

    List<JSONObject> getAssetInfoByID(String cardid);

    List<JSONObject> getLastMonthDep(String cardid, String substring, String substring1,String companyId);

    void depUpdate(JSONObject depMap);

    void addDepmonth(JSONObject entityMap);

    JSONObject getOldUsingStatus(String cardid);

    void truncateData();

    List<JSONObject> getAssets(String companyId);
    List<JSONObject> getAssets1(JSONObject companyId);

    void assetCopy(String companyId);

    void upChangedmonth(String id, int usedmonth);

    void upLastYearAccudep(String id);

    void upUsedMonth(String assetid);

    void dateUpdate(String year, String month, String companyid);

    List<JSONObject> getNoCheckAssets(String companyId);

    List<JSONObject> getWorkloadList(String companyId);

    List<JSONObject> loadInfo(String year, String month, String companyid);

    List<JSONObject> selectAssetRecord(String year, String month, String companyid);

    List<JSONObject> selectAssetRecordCopy(String year, String month,String companyid);

    String selectIntYearUqglobalperiodId(String year, String month, String companyid);

    String selectDepcostAndAccudep(String year, String month, String companyid);

    String selectGlobalStatus(String year, String month, String companyid);

    int getYearDatePagecount(String type);

    List<JSONObject> getYearDatePage(String type, int offset, int limit);

    int getMonthDatePagecount(String type);

    List<JSONObject> getMonthDatePage(String type, int offset, int limit);
}
