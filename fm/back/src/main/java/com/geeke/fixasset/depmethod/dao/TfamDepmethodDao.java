package com.geeke.fixasset.depmethod.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.fixasset.depmethod.entity.TfamDepmethod;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 资产类别表depmethodDAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamDepmethodDao extends CrudDao<TfamDepmethod> {}
