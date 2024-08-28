package com.geeke.fixasset.usingstatus.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.fixasset.usingstatus.entity.TfamUsingstatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 卡片使用状态DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamUsingstatusDao extends CrudDao<TfamUsingstatus> {
    TfamUsingstatus getStatusByName(@Param("usingstatusName") String usingstatusName,@Param("companyId") String companyId);
}
