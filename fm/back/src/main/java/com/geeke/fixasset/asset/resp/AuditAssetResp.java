package com.geeke.fixasset.asset.resp;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @ClassName AuditAssetReq
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/8 11:42
 * @Version 1.0
 */
@Data
@Builder
public class AuditAssetResp {
    private String cardCode;
    private String msg;
}
