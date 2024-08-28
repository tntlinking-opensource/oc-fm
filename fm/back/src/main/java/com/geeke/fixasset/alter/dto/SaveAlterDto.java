package com.geeke.fixasset.alter.dto;

import com.geeke.fixasset.asset.entity.TfamAsset;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SaveAlterDto
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/9 15:44
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveAlterDto {
    private TfamAsset asset;
    private Integer operatetType;
    private String reason;
}
