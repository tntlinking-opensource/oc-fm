package com.geeke.ureport.service;

import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.ureport.entity.UreportFileEntity;

import java.util.List;
import java.util.Map;

public interface AccountingBookService {
      List<Map<String,String>> getAccountingBookStatus();

      List<Map<String, String>> getHkontLevel();

      List<Map<String, String>> queryHkontList();
}
