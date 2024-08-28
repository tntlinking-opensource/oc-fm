package com.geeke.checkplan.result.dao;

import com.geeke.checkplan.result.entity.TfamAssetCheckresult;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 盘点结果DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamAssetCheckresultDao extends CrudDao<TfamAssetCheckresult> {}
