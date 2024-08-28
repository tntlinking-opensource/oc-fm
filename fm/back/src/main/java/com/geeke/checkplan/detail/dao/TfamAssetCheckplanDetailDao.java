package com.geeke.checkplan.detail.dao;

import com.geeke.checkplan.detail.entity.TfamAssetCheckplanDetail;
import com.geeke.checkplan.resp.CheckByCategoryResp;
import com.geeke.checkplan.resp.CheckByDeptResp;
import com.geeke.checkplan.resp.StatusAndCategoryResp;
import com.geeke.common.persistence.CrudDao;
import com.geeke.fixasset.category.entity.TfamCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 盘点详情DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamAssetCheckplanDetailDao extends CrudDao<TfamAssetCheckplanDetail> {
    List<CheckByDeptResp> getByDept(@Param("mainId")String mainId);

    List<CheckByCategoryResp> getByCatagory(@Param("mainId")String mainId);

    List<TfamAssetCheckplanDetail> getDeptByMainId(@Param("mainId")String mainId);

    List<StatusAndCategoryResp> selectStatusAndCategory(@Param("mainId")String mainId);

    void deleteByMainId(@Param("mainId")String mainId);

    TfamAssetCheckplanDetail selectPlan(@Param("assetId")String assetId,@Param("mainId") String mainId);

    Integer selectAlreadyCheckNum(@Param("mainId") String planId);

    List<TfamCategory> selectCatagory(@Param("mainId")String mainId);
}
