package com.geeke.cardasset.credit.dao;

import com.geeke.cardasset.credit.entity.TfamDepfuture;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 资产折旧表DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamDepfutureDao extends CrudDao<TfamDepfuture> {}
