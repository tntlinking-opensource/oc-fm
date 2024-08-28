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
public class ReportZcfz extends BaseService {

    @Autowired
    private TglAccountsDao tglAccountsDao;

    /**
     * 方法必须包含三个参数：String，String，Map(monthYear：年月,status：状态)
     * 获取本年初余额
     * @return 集合类型 ，包含字段：money, code
     */
    public List<Map<String, Object>> getBeforeYear(String dsName, String datasetName, Map<String, Object> parameters) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (parameters.containsKey("monthYear") && parameters.containsKey("status") && parameters.containsKey("companyId")) {
            String monthYear = parameters.get("monthYear").toString();
            String status = parameters.get("status").toString();
            if (StringUtils.isBlank(monthYear) || StringUtils.isBlank(status)) {
                return null;
            }
            String year = monthYear.substring(0, 4); // 年份
            String companyId = parameters.get("companyId").toString();
            // 查询Map
            Map<String, Object> searchMap = new HashMap<>();
            searchMap.put("tenantId", companyId);
            searchMap.put("yearMonth", year + "00");
            list = getReportDesignLr(searchMap, status);
        }
        return list;
    }

    /**
     * 方法必须包含三个参数：String，String，Map(monthYear：年月,status：状态)
     * 获取期末余额
     * @return 集合类型 ，包含字段：money, code
     */
    public List<Map<String, Object>> getAfterYear(String dsName, String datasetName, Map<String, Object> parameters) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (parameters.containsKey("monthYear") && parameters.containsKey("status") && parameters.containsKey("companyId")) {
            String monthYear = parameters.get("monthYear").toString();
            String status = parameters.get("status").toString();
            if (StringUtils.isBlank(monthYear) || StringUtils.isBlank(status)) {
                return null;
            }
            String companyId = parameters.get("companyId").toString();
            // 查询Map
            Map<String, Object> searchMap = new HashMap<>();
            searchMap.put("tenantId", companyId);
            searchMap.put("yearMonth", monthYear);
            list = getReportDesignLr(searchMap, status);
        }
        return list;
    }

    public List<Map<String, Object>> getReportDesignLr(Map<String, Object> searchMap, String status) {
        List<Map<String, Object>> list = tglAccountsDao.getReportDesignZcfz(searchMap);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if ("1".equals(status)) { // 获取已记账的字段信息：money1, 将
                    list.get(i).put("money", list.get(i).get("accountMoney"));
                } else { // 全部
                    list.get(i).put("money", list.get(i).get("allMoney"));
                }
            }
        }
        return list;
    }
}
