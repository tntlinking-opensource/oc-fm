package com.geeke.fixasset.asset.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName GraphDeptDataDto
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/17 11:05
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraphDeptDataDto {

    private Integer num;
    private String value;
    private String accudep;
    private String netvalue;
    private String deptId;
    private String name;
}
