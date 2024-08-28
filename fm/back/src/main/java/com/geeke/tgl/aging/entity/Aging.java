package com.geeke.tgl.aging.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 账龄区间Entity
 * @author
 * @version
 */

public class Aging extends DataEntity<Aging> {

    private static final long serialVersionUID = 1457254743943433574L;

    private String begday; // 起始天数

    private Integer aging; // 账龄

    private Integer beforeAging; // 前一个账龄（非数据库字段）

    private String tenantId; // 公司

    private String remark1; // 备用字段1

    // 构造方法
    public Aging() {
        super();
    }

    public Aging(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 0, max = 255, message = "起始天数长度必须介于 0 和 255 之间")
    public String getBegday() {
        return begday;
    }

    public void setBegday(String begday) {
        this.begday = begday;
    }

    @NotNull(message = "账龄不能为空")
    public Integer getAging() {
        return aging;
    }

    public void setAging(Integer aging) {
        this.aging = aging;
    }

    public Integer getBeforeAging() { return beforeAging; }

    public void setBeforeAging(Integer beforeAging) { this.beforeAging = beforeAging; }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Length(min = 0, max = 255, message = "备用字段1长度必须介于 0 和 255 之间")
    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tgl_aging";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943433574";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "账龄区间";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
