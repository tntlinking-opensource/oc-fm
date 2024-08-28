package com.geeke.checkplan.resp;

import com.geeke.checkplan.image.entity.TfamAssetCheckimage;
import com.google.common.eventbus.AllowConcurrentEvents;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName DownloadResp
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/31 10:09
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DownloadResp extends TfamAssetCheckimage {
    private String attachTitle;
    private String attachRequired;
    private String taskName;
    private String assetCode;
}
