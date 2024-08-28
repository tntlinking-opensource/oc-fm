package com.geeke.fixasset.asset.dao;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.data.PageRequest;
import com.geeke.common.persistence.CrudDao;
import com.geeke.fixasset.asset.entity.TfamDepsub;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 卡片管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamDepsubDao extends CrudDao<TfamDepsub> {

    int getInfoDatePageCount(JSONObject jsonObject);

    List<JSONObject> getInfoDatePagePage(JSONObject jsonObject);

    List<JSONObject> getMaxDateVoucherid(String assetId);
}
