package com.geeke.checkplan.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName PersonCheckReq
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/21 10:46
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonCheckReq {
    /**
     * 计划id
     */
    private String planId;
    /**
     * 资产id
     */
    private String assetId;
    private String userId;
    private String employeeno;
    private String deptId;
    private String address;
    /**
     * 实际数量
     */
    private Integer actNum;
    /**
     * 盘点状态
     */
    private String checkResult;
    private String assetStatus;
    private String remark;
}
