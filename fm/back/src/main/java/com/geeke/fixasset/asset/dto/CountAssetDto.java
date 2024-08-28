package com.geeke.fixasset.asset.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName CountAssetDto
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/16 11:12
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountAssetDto {
    private String countItemId;
    private String countItemName;
    private Integer num;
    private String value;
    private String accudep;
    private String netvalue;
}
