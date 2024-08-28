package com.geeke.fixasset.asset.resp;

import com.geeke.fixasset.asset.dto.GraphAddScrapDataDto;
import com.geeke.fixasset.asset.dto.GraphCategoryDataDto;
import com.geeke.fixasset.asset.dto.GraphDeptDataDto;
import com.geeke.fixasset.asset.dto.GraphTransferDataDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName countAssetForGraphresp
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/17 10:59
 * @Version 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountAssetForGraphResp {
    private List<GraphDeptDataDto> deptDataDtoList;
    private List<GraphCategoryDataDto> categoryDataDtoList;
    private List<GraphAddScrapDataDto> graphAddScrapDataDtoList;
    private List<GraphTransferDataDto> transferDataDtoList;
}
