package com.geeke.cardasset.supplementreduce.dao;

import com.geeke.cardasset.supplementreduce.entity.TfamCategoryOld;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 补提冲减DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamCategoryOldDao extends CrudDao<TfamCategoryOld> {}
