package com.geeke.fixasset.assetstatus.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.fixasset.assetstatus.entity.TfamAssetStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 卡片盘点状态DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamAssetStatusDao extends CrudDao<TfamAssetStatus> {}
