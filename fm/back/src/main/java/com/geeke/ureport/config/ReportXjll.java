package com.geeke.ureport.config;

import com.geeke.common.service.BaseService;
import com.geeke.vouchermanager.dao.TglAccountsDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReportXjll extends BaseService {

    @Autowired
    private TglAccountsDao tglAccountsDao;

    /**
     * 方法必须包含三个参数：String，String，Map(monthYear：年月,status：状态)
     * 获取本期金额
     * @return 集合类型 ，包含字段：money, code
     */
    public List<Map<String, Object>> getReportDataXjll(String dsName, String datasetName, Map<String, Object> parameters) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (parameters.containsKey("monthYear") && parameters.containsKey("status") && parameters.containsKey("companyId")) {
            String monthYear = parameters.get("monthYear").toString();
            String status = parameters.get("status").toString();
            if (StringUtils.isBlank(monthYear) || StringUtils.isBlank(status)) {
                return null;
            }
            String companyId = parameters.get("companyId").toString();
            String year = monthYear.substring(0, 4); // 年份
            // 查询Map
            Map<String, Object> searchMap = new HashMap<>();
            searchMap.put("tenantId", companyId);
            searchMap.put("yearMonth", monthYear);
            searchMap.put("year", year);
            if ("1".equals(status)) { // 获取已记账信息
                searchMap.put("intFlag", "true");
            }
            list = tglAccountsDao.getReportDesignXjll(searchMap);
        }
        return list;
    }
}
