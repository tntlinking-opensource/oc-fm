package com.geeke.checkplan.req;

import com.geeke.checkplan.attach.entity.TfamAssetCheckplanMainAttach;
import com.geeke.checkplan.checkplanmain.entity.TfamAssetCheckplanMain;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @ClassName CheckplanMainSaveReq
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/20 12:14
 * @Version 1.0
 */
@Data
@Builder
public class CheckplanMainSaveReq extends TfamAssetCheckplanMain {
    private List<String> deptIds;
    private List<String> statusIds;
    private List<String> categoryIds;
    private List<TfamAssetCheckplanMainAttach> attachs;

    private List<String> delDeptIds;
    private List<CheckPlanMainStatusAndCategoryReq> delStatusAndCategory;
    private List<TfamAssetCheckplanMainAttach> delAttachs;

}
