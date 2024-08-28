package com.geeke.fixasset.asset.resp;

import com.geeke.fixasset.alter.entity.TfamAlter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName LifeCricleResp
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/14 19:00
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LifeCricleResp {
    private List<TfamAlter> alters;
    private String endTime;
    private String startTime;
}
