package com.geeke.fixasset.asset.dto;

import com.geeke.checkplan.attach.entity.TfamAssetCheckplanMainAttach;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName ConditionDto
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/10 16:49
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConditionDto {
    private String id;
    /**
     * card_code
     */
    private String cardNo;
    private String assetName;
    private String assetCode;
    private String cardModel;
    private String producer;
    private String businessDate;
    private String place;
    private String companyId;
    private String mainId;
    private String cardId;
    private String cardCode;
    private String reason;
    private List<TfamAssetCheckplanMainAttach> attachList;
    private String attachCategory;
    private String imagePath;
    private String originalFilename;
}
