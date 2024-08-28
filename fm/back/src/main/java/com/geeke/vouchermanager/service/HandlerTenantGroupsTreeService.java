package com.geeke.vouchermanager.service;

import com.geeke.common.service.TreeService;
import com.geeke.vouchermanager.dao.HandlerGroupTenantTreeDao;
import com.geeke.vouchermanager.dao.TglAccountsTreeDao;
import com.geeke.vouchermanager.entity.GroupAccountsEntityTree;
import com.geeke.vouchermanager.entity.TglAccountsTree;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("handlerTenantGroupsTreeService")
@Transactional(readOnly = true)
public class HandlerTenantGroupsTreeService extends TreeService<HandlerGroupTenantTreeDao, GroupAccountsEntityTree> {
}
