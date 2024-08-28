package com.geeke.fixasset.asset.dto;

import com.geeke.fixasset.asset.entity.TfamAsset;
import lombok.Data;

/**
 * @ClassName TfamAssetDto
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/16 15:46
 * @Version 1.0
 */
@Data
public class TfamAssetDto extends TfamAsset {
    private String userstatus;
}
