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
public class DownloadReq {
    /**
     * 计划id
     */
    private String mainId;
    private String attachCategory;
}
