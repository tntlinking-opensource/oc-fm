package com.geeke.vouchermanager.dao;

import com.geeke.common.persistence.TreeDao;
import com.geeke.vouchermanager.entity.GroupAccountsEntity;
import com.geeke.vouchermanager.entity.GroupAccountsEntityTree;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface HandlerGroupTenantTreeDao extends TreeDao<GroupAccountsEntityTree> {
}
