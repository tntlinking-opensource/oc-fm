package com.geeke.vouchermanager.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.vouchermanager.entity.TglLedgerModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 分户明细模板表DAO接口
 * @author
 * @version
 */
@Mapper
public interface TglLedgerModelDao extends CrudDao<TglLedgerModel> {
    public int deleteAll();
}
