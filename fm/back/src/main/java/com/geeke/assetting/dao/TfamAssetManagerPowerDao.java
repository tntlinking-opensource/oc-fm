package com.geeke.assetting.dao;

import com.geeke.assetting.entity.TfamAssetManagerPower;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 资产权限表DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamAssetManagerPowerDao extends CrudDao<TfamAssetManagerPower> {}
