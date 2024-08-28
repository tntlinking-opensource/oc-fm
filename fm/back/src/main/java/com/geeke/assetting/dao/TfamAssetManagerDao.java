package com.geeke.assetting.dao;

import com.alibaba.fastjson.JSONObject;
import com.geeke.assetting.entity.TfamAssetManager;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 卡片管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamAssetManagerDao extends CrudDao<TfamAssetManager> {
    List<Map<String, Object>> listManagerMenu(String companyId,String userId);
    int deleteAssetManagerMenu(String userId);
}
