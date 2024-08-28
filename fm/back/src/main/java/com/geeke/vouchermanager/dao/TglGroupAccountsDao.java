package com.geeke.vouchermanager.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.vouchermanager.entity.TglGroupAccounts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 制证DAO接口
 * @author
 * @version
 */
@Mapper
public interface TglGroupAccountsDao extends CrudDao<TglGroupAccounts> {}
