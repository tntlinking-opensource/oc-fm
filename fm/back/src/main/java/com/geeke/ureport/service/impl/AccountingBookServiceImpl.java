package com.geeke.ureport.service.impl;

import com.geeke.common.service.BaseService;
import com.geeke.ureport.mapper.AccountingBookMapper;
import com.geeke.ureport.service.AccountingBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Service
@Transactional(readOnly = true)
public class AccountingBookServiceImpl extends BaseService implements AccountingBookService {
    @Resource
    AccountingBookMapper mapper;

    @Override
    public List<Map<String, String>> getAccountingBookStatus() {
        return mapper.getAccountingBookStatus();
    }

    @Override
    public List<Map<String, String>> getHkontLevel() {
        return mapper.getHkontLevel();
    }

    @Override
    public List<Map<String, String>> queryHkontList() {
        return mapper.queryHkontList();
    }
}
