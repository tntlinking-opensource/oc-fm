package com.geeke.assetting.dao;

import com.geeke.assetting.entity.TfamDepmonth;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 月折旧表DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamDepmonthDao extends CrudDao<TfamDepmonth> {}
