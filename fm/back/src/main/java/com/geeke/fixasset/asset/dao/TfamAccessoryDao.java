package com.geeke.fixasset.asset.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.fixasset.asset.entity.TfamAccessory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 卡片管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamAccessoryDao extends CrudDao<TfamAccessory> {}
