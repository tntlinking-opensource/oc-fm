package com.geeke.fixasset.asset.req;

import lombok.Data;

import java.util.List;

/**
 * @ClassName AssetTransferReq
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/14 17:09
 * @Version 1.0
 */
@Data
public class AssetTransferReq {
    private List<String> ids;

    private String deptId;

    private String userId;

    private String reason;
}
