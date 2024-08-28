package com.geeke.ureport;

import org.springframework.stereotype.Component;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MyTest {

    /**
     * 方法必须包含三个参数：String，String，Map
     *
     * @return 集合类型 ，包含字段：id,name,salary
     */
    public List<Map<String, Object>> loadReportData(String dsName, String datasetName, Map<String, Object> parameters) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 1000; i++) {
            // 模拟数据
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("id", i);
            m.put("name", RandomStringUtils.random(10, true, false));
            m.put("salary", RandomUtils.nextInt(1,999999999) + i);
            list.add(m);
        }
        return list;

    }
}
