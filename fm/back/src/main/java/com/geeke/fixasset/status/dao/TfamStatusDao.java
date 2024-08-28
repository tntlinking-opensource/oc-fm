package com.geeke.fixasset.status.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.fixasset.status.entity.TfamStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 卡片状态DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamStatusDao extends CrudDao<TfamStatus> {
    TfamStatus getStatusByName(String name);

}
