package com.geeke.vouchermanager.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.vouchermanager.entity.AccountLedgetype;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 分户DAO接口
 * @author
 * @version
 */
@Mapper
public interface AccountLedgetypeDao extends CrudDao<AccountLedgetype> {}
