package com.geeke.ureport.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AccountingBookMapper {
     List<Map<String,String>> getAccountingBookStatus();

     List<Map<String, String>> getHkontLevel();

     List<Map<String, String>> queryHkontList();
}
