package com.geeke.checkplan.image.dao;

import com.geeke.checkplan.image.entity.TfamAssetCheckimage;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 资产图片DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamAssetCheckimageDao extends CrudDao<TfamAssetCheckimage> {
    Integer getNum(@Param("attachCategory") String attachCategory,@Param("mainId") String mainId);

}
