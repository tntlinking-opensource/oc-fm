package com.geeke.fixasset.changetype.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.fixasset.changetype.entity.TfamChangeType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 资产变动方式字典DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamChangeTypeDao extends CrudDao<TfamChangeType> {}
