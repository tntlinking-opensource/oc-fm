package com.geeke.fixasset.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName OperatetAlterTypeEnum
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/9 15:50
 * @Version 1.0
 */
@AllArgsConstructor
@Getter
public enum OperatetAlterTypeEnum {

    /**
     * 新增审核
     *      * 报废审核
     *      * 新增反审核
     *      * 报废反审核
     */
    ADD("新增审核",1),
    UPDATE("调整",2),
    DELETE("报废审核",3),
    TRANSFER("调拨",4),
    ADVERSE_ADD("新增反审核",5),
    ADVERSE_DEL("报废反审核",6),
    ;

    /**
     * 描述
     */
    private String name;
    private Integer code;
}
