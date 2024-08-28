package com.geeke.fixasset.asset.dao;

import com.alibaba.fastjson.JSONObject;
import com.geeke.checkplan.resp.StatusAndCategoryResp;
import com.geeke.common.data.PageRequest;
import com.geeke.common.persistence.CrudDao;
import com.geeke.fixasset.asset.dto.*;
import com.geeke.fixasset.asset.entity.TfamAsset;
import com.geeke.fixasset.asset.resp.CountAssetResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 卡片管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamAssetDao extends CrudDao<TfamAsset> {
    String getMaxAssetCode(@Param("assetCode") String assetCode);

    String getMaxCardCode(@Param("companyId")String companyId);

    List<CountAssetDto> countAsset(@Param("groupCondition")String groupCondition,@Param("offset")int offset,@Param("limit")int limit);

    List<TfamAssetDto> listAlls(PageRequest pageRequest);


    List<GraphDeptDataDto> getAllDataByDept();


    List<GraphCategoryDataDto> getAllDataByAssetCategory();

    List<GraphAddScrapDataDto> getAllScrapAssetGroupByMonth();

    List<GraphTransferDataDto> getAllTransferDataByMonthly();

    CountAssetResp countAssetTotal(PageRequest pageRequest);

    List<StatusAndCategoryResp> selectStatusAndCategory(@Param("statusIds")List<String> statusIds, @Param("categoryIds") List<String> categoryIds, @Param("deptIds") List<String> deptIds);

    List<TfamAsset> selectStatusAndCategoryItem(@Param("statusIds")List<String> statusIds, @Param("categoryIds") List<String> categoryIds, @Param("deptIds") List<String> deptIds);

    List<JSONObject> getVoucherId(String id);
}
