package com.geeke.fixasset.asset.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName CountAssetReq
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/16 11:08
 * @Version 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountAssetReq {
    /**
     * 分组条件
     */
    private String groupCondition;

    /**
     * 卡片状态
     */
    private String assetStatus;

    private int offset = 0;

    private int limit = 10;
}
