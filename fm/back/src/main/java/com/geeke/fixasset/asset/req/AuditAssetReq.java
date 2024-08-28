package com.geeke.fixasset.asset.req;

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
public class AuditAssetReq {
    /**
     * id集
     */
    private List<String> ids;
    private Integer auditType;
    /**
     * 1 审核 2 反审核
     */
    private Integer moveType;

    private String scrapeReason;
}
