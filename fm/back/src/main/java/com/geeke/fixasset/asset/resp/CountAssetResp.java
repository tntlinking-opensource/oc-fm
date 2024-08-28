package com.geeke.fixasset.asset.resp;

import com.geeke.fixasset.asset.dto.CountAssetDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName CountAssetResp
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/16 11:11
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountAssetResp {
    List<CountAssetDto> countAssetDtos;

    private Integer num;
    private String value;
    private String accudep;
    private String tax;
    private String netvalue;
    private int offset = 0;
    private int limit = 10;
    private int total;
}
