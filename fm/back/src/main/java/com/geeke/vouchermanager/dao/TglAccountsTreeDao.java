package com.geeke.vouchermanager.dao;

import com.geeke.common.persistence.TreeDao;
import com.geeke.vouchermanager.entity.TglAccountsTree;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TglAccountsTreeDao extends TreeDao<TglAccountsTree> {
}
