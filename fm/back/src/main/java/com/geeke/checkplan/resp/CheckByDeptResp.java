package com.geeke.checkplan.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName CheckBydeptResp
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/20 14:58
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckByDeptResp {
    private String deptName;
    private String total;
    private String unit;
    private String alreadlyCkeck;
    private String waitCkeck;
    private String percent;
}
