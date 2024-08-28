package com.geeke.tgl.aging.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.tgl.aging.entity.Aging;
import org.apache.ibatis.annotations.Mapper;

/**
 * 账龄区间DAO接口
 * @author
 * @version
 */
@Mapper
public interface AgingDao extends CrudDao<Aging> {}
