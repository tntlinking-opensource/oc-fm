package com.geeke.cardasset.credit.dao;

import com.geeke.cardasset.credit.entity.TfamChangeWayEntry;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 资产入账DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamChangeWayEntryDao extends CrudDao<TfamChangeWayEntry> {}
