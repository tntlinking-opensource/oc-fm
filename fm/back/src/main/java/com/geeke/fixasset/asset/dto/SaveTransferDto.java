package com.geeke.fixasset.asset.dto;

import com.geeke.fixasset.asset.entity.TfamAsset;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SaveTransfer
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/12 20:37
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveTransferDto {
    private TfamAsset asset;
    private String transferReason;
}
