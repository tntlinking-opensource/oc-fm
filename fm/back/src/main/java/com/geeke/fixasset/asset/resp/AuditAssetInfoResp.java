package com.geeke.fixasset.asset.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.util.List;

/**
 * @ClassName AuditAssetInforesp
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/10 11:36
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditAssetInfoResp {
    private List<AuditAssetResp> noNeedAuditAssets;
    private String cardSets;

    public AuditAssetInfoResp buildSets(List<AuditAssetResp> noNeedAuditAssets){
        this.noNeedAuditAssets = noNeedAuditAssets;
        int count = 0;
        StringBuilder builder = new StringBuilder();
        for (AuditAssetResp noNeedAuditAsset : this.noNeedAuditAssets) {
            String cardCode = noNeedAuditAsset.getCardCode();
            if (count == 0){
                builder.append("卡片编号:").append(cardCode);
            }else {
                builder.append(",").append(cardCode);
            }
            count++;
        }
        builder.append("的状态不能进行该操作");
        this.cardSets = builder.toString();


        return this;
    }
}
