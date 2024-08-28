package com.geeke.fixasset.nature.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.fixasset.nature.entity.TfamNature;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 卡片性质DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamNatureDao extends CrudDao<TfamNature> {}
