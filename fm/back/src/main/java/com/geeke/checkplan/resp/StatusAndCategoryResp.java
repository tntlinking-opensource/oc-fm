package com.geeke.checkplan.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName StatusAndCategoryResp
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/20 17:07
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusAndCategoryResp {
    private String categoryName;
    private String categoryId;
    private String statusName;
    private String statusId;
    private String num;
    private String unit;
}
