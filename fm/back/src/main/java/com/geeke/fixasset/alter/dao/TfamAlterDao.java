package com.geeke.fixasset.alter.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.fixasset.alter.entity.TfamAlter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 资产变动明细DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamAlterDao extends CrudDao<TfamAlter> {}
