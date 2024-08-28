package com.geeke.fixasset.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @ClassName AuditStatusEnum
 * @Description 1新增待审核，2新增已审核，3报废待审核，4报废已审核）
 * @Author 任杰
 * @Date 2023/8/8 10:48
 * @Version 1.0
 */
@AllArgsConstructor
@Getter
public enum AuditStatusEnum {


    MIN("下限",0),
    /**
     * 新增待审核
     */
    ADDPREAUDIT("新增待审核",1),
    /**
     * 新增已审核
     */
    ADDADUITED("新增已审核",2),
    /**
     * 报废待审核
     */
    SCRAPPREAUDIT("报废待审核",3),
    /**
     * 报废已审核
     */
    SCRAPAUDITED("报废已审核",4),

    MAX("上限限",5),
    ;

    /**
     * 描述
     */
    private String name;
    private Integer code;

    public static String getAuditName(Integer code){
        if (Objects.isNull(code)){
            return null;
        }
        for (AuditStatusEnum item: values()) {
            if (item.getCode().equals(code)) {
                return item.getName();
            }
        }
        return null;
    }
}
