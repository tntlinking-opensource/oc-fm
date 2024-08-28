package com.geeke.checkplan.req;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName CheckPlanMainStatusAndCategoryReq
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/20 12:18
 * @Version 1.0
 */
@Data
@Builder
public class CheckPlanMainStatusAndCategoryReq {
    private String statusId;
    private String categoryId;
}
