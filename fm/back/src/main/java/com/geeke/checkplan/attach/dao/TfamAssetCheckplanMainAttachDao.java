package com.geeke.checkplan.attach.dao;

import com.geeke.checkplan.attach.entity.TfamAssetCheckplanMainAttach;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 盘点计划附件DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamAssetCheckplanMainAttachDao extends CrudDao<TfamAssetCheckplanMainAttach> {
    List<TfamAssetCheckplanMainAttach> selectByMainId(@Param("mainId") String mainId);

    void deleteByMainId(@Param("mainId")String mainId);
}
