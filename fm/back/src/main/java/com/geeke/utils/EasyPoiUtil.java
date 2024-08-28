package com.geeke.utils;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Author: shenzy
 * @DateTime: 2021/2/9 10:24
 * @Description: TODO
 */
public class EasyPoiUtil {
    /**
     * 导入Excel
     *
     * @param TitleRows
     * @param HeadRows
     * @param file
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> importExcel(Integer TitleRows, Integer HeadRows, InputStream file) throws Exception {
        ImportParams  params = new ImportParams();

        params.setTitleRows(TitleRows);
        params.setHeadRows(HeadRows);
        List<Map<String, Object>> list = ExcelImportUtil.importExcel(file, Map.class, params);
        return list;
    }

    //文件方式
    public static List<Map<String, Object>> importExcel(Integer TitleRows, Integer HeadRows, File file) throws Exception {
        ImportParams params = new ImportParams();

        params.setTitleRows(TitleRows);
        params.setHeadRows(HeadRows);
        List<Map<String, Object>> list = ExcelImportUtil.importExcel(file, Map.class, params);
        return list;
    }
    /**
     * 数据转excel并下载
     * 有实体类的情况
     * @param response
     * @param list
     * @param excelname
     * @param pojoClass
     */

    public static void DownloadExcel(HttpServletResponse response, List<?> list, String excelname, Class<?> pojoClass) {

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), pojoClass, list);
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            String fn = new String(excelname.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fn + ".xls");
            workbook.write(outputStream);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 数据类型为Map的导出
     * @param response
     * @param excelname
     * @param map
     * @param title
     */

    public static void DownloadExcel(HttpServletResponse response,String excelname, List<Map<String, Object>> map,
                                     Map<String, String> title) {

        try {
            List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();

            Set<String> keySet = title.keySet();
            int i=0;
            for (String key : keySet) {
                String value = title.get(key);

                ExcelExportEntity excelExportEntity = new ExcelExportEntity(key, value);
                excelExportEntity.setOrderNum(i);
                entity.add(excelExportEntity);
                i++;
            }

            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), entity, map);

            OutputStream outputStream = null;
            outputStream = response.getOutputStream();
            String fn = new String(excelname.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fn + ".xls");
            workbook.write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据类型为Map的导出
     *
     * @param response
     * @param excelname
     * @param map【对应key,导出列名】
     * @param title
     */

    public static void DownloadCustomizeExcel(HttpServletResponse response, String excelname, List<?> map,
                                              Map<String, String> title) throws Exception {
        List<Map<String, Object>> maps = new ArrayList<>();

        if (maps!=null&&maps.size()>0) {
            maps = convertListBean2ListMap(map);
        }

        List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
        Set<String> keySet = title.keySet();
        for (String key : keySet) {
            String value = title.get(key);
            ExcelExportEntity excelExportEntity = new ExcelExportEntity(value, key);
            entity.add(excelExportEntity);
        }

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), entity, maps);
        OutputStream outputStream = null;
        outputStream = response.getOutputStream();
        String fn = new String(excelname.getBytes(), "ISO8859_1");
        response.setHeader("Content-disposition", "attachment; filename=" + fn + ".xls");
        workbook.write(outputStream);

    }

    /**
     * 将 List<JavaBean>对象转化为List<Map>
     *
     * @return Object对象
     * @author DJZ
     * @version 2016年3月20日 11:03:01
     */
    public static List<Map<String, Object>> convertListBean2ListMap(List<?> beanList) throws Exception {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (int i = 0, n = beanList.size(); i < n; i++) {
            Object bean = beanList.get(i);
            if (bean instanceof Map) {
                return (List<Map<String, Object>>) beanList;
            }
            Map map = convertBean2Map(bean);
            mapList.add(map);
        }
        return mapList;


    }
    /**
     * 将 JavaBean对象转化为 Map
     */
    public static Map convertBean2Map(Object bean) throws Exception {

        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = beanInfo
                .getPropertyDescriptors();

        for (int i = 0, n = propertyDescriptors.length; i < n; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }



}
