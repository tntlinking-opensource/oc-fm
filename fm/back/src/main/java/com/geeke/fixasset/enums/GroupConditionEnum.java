package com.geeke.fixasset.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName GroupConditionEnum
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/16 11:37
 * @Version 1.0
 */
@AllArgsConstructor
@Getter
public enum GroupConditionEnum {
    CATEGORYID("category_id"),
    USEDEPTID("usedeptid"),
    ASSETACCOUNTID("asset_accountid"),
    DEPMETHOD("depmethod"),
    USINGSTATUS("usingstatus"),
    ;

    /**
     * 描述
     */
    private String name;
}
