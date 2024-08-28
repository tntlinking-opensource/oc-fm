package com.geeke.tgl.flow.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.tgl.flow.entity.Flowitems;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 现金流量项目DAO接口
 * @author
 * @version
 */
@Mapper
public interface FlowitemsDao extends CrudDao<Flowitems> {}
