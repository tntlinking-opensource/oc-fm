package com.geeke.vouchermanager.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.vouchermanager.entity.TglAcOffsetDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 往来冲销总表DAO接口
 * @author
 * @version
 */
@Mapper
public interface TglAcOffsetDetailDao extends CrudDao<TglAcOffsetDetail> {}
