package com.geeke.checkplan.resp;

import com.geeke.checkplan.attach.entity.TfamAssetCheckplanMainAttach;
import com.geeke.checkplan.checkplanmain.entity.TfamAssetCheckplanMain;
import com.geeke.checkplan.dept.entity.TfamAssetCheckplanMainDept;
import com.geeke.checkplan.detail.entity.TfamAssetCheckplanDetail;
import com.geeke.fixasset.category.entity.TfamCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName TfamAssetCheckplanMainDto
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/20 14:06
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TfamAssetCheckplanMainDto extends TfamAssetCheckplanMain {
    private String percentage;
    private String attachNum;
    private String excuteStatus;
    private List<CheckByDeptResp> depts;
    private List<CheckByCategoryResp> categorys;
    private List<AttachResp> attachResps;
    private List<TfamAssetCheckplanMainDept> deptList;
    private List<StatusAndCategoryResp> statusAndCategorys;
    private List<TfamAssetCheckplanMainAttach> attaches;
    private List<TfamCategory> categoryImages;
}
