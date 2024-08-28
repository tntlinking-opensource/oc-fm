package com.geeke.vouchermanager.service;

import com.geeke.common.service.TreeService;
import com.geeke.vouchermanager.dao.TglAccountsTreeDao;
import com.geeke.vouchermanager.entity.TglAccountsTree;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("tglAccountsTreeService")
@Transactional(readOnly = true)
public class TglAccountsTreeService extends TreeService<TglAccountsTreeDao, TglAccountsTree> {
}
