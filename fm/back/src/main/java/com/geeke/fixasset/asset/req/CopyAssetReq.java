package com.geeke.fixasset.asset.req;

import lombok.Data;

/**
 * @ClassName CopyAssetReq
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/8 10:28
 * @Version 1.0
 */
@Data
public class CopyAssetReq {
    /**
     *资产id
     */
    private String assetId;
    /**
     * 复制次数
     */
    private Integer copyTimes;
}
