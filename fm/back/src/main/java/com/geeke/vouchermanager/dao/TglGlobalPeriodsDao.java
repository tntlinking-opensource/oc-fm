package com.geeke.vouchermanager.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.vouchermanager.entity.TglGlobalPeriods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 制证DAO接口
 * @author
 * @version
 */
@Mapper
public interface TglGlobalPeriodsDao extends CrudDao<TglGlobalPeriods> {}
