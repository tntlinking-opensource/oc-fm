package com.geeke.fixasset.asset.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName GraphCategoryDataDto
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/17 11:06
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraphCategoryDataDto {
    private Integer num;
    private String value;
    private String accudep;
    private String netvalue;
    private String categoryId;
    private String name;
}
