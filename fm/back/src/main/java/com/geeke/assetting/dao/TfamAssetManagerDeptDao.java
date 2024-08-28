package com.geeke.assetting.dao;

import com.geeke.assetting.entity.TfamAssetManagerDept;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 卡片所属部门DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamAssetManagerDeptDao extends CrudDao<TfamAssetManagerDept> {
    int deleteAssetManagerDept(String userId);
}
