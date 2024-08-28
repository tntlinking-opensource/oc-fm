package com.geeke.fixasset.asset.req;

import com.geeke.fixasset.asset.entity.TfamAsset;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SaveAndUpdateReq
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/12 20:02
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveAndUpdateReq extends TfamAsset {
    /**
     * 1.新增
     * 2.调整
     * 3.调拨
     */
    private Integer operateType;
    private String reason;
}
