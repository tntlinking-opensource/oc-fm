package com.geeke.checkplan.resp;

import com.geeke.checkplan.attach.entity.TfamAssetCheckplanMainAttach;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName AttachResp
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/20 15:46
 * @Version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachResp extends TfamAssetCheckplanMainAttach {
    private Integer num;
}
