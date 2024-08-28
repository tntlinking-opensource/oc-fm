package com.geeke.fixasset.changeway.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.fixasset.changeway.entity.TfamChangeWay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 变动方式DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamChangeWayDao extends CrudDao<TfamChangeWay> {}
