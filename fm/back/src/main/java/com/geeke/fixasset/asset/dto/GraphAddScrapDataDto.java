package com.geeke.fixasset.asset.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName GraphAddScarapDataDto
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/17 11:06
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraphAddScrapDataDto {

    /**
     * 新增数量 原值 日期
     */
    private Integer adNum;
    private String adValue;
    private String adItem;

    /**
     * 报废 数量 原值 日期
     */
    private Integer sNum;
    private String sValue;
    private String sItem;
}
