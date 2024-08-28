package com.geeke.checkplan.detail.service;

import com.geeke.checkplan.detail.dao.TfamAssetCheckplanDetailDao;
import com.geeke.checkplan.detail.entity.TfamAssetCheckplanDetail;
import com.geeke.checkplan.resp.CheckByCategoryResp;
import com.geeke.checkplan.resp.CheckByDeptResp;
import com.geeke.checkplan.resp.StatusAndCategoryResp;
import com.geeke.common.service.CrudService;

import com.geeke.fixasset.category.entity.TfamCategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 盘点详情Service
 * @author
 * @version
 */

@Service("tfamAssetCheckplanDetailService")
@Transactional(readOnly = true)
public class TfamAssetCheckplanDetailService extends CrudService<TfamAssetCheckplanDetailDao, TfamAssetCheckplanDetail> {
    public List<CheckByDeptResp> getByDept(String mainId) {
        return this.dao.getByDept(mainId);
    }

    public List<CheckByCategoryResp> getByCatagory(String mainId) {
        return this.dao.getByCatagory(mainId);
    }

    public List<TfamAssetCheckplanDetail> getDeptByMainId(String id) {
        return this.dao.getDeptByMainId(id);

    }

    public List<StatusAndCategoryResp> selectStatusAndCategory(String id) {
        return this.dao.selectStatusAndCategory(id);
    }

    public void deleteByMainId(String mainId) {
        this.dao.deleteByMainId(mainId);
    }

    public TfamAssetCheckplanDetail selectPlan(String assetId, String mainId) {
        return this.dao.selectPlan(assetId,mainId);
    }

    public Integer selectAlreadyCheckNum(String planId) {
        return this.dao.selectAlreadyCheckNum(planId);
    }

    public List<TfamCategory> selectCatagory(String mainId) {
        return this.dao.selectCatagory(mainId);
    }
}
