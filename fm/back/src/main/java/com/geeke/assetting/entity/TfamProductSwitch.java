package com.geeke.assetting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 产品开关Entity
 * @author
 * @version
 */

public class TfamProductSwitch extends DataEntity<TfamProductSwitch> {

    private static final long serialVersionUID = 1577773656573117285L;

    private Integer remindSwitch = 1; // 提醒开关（0打开1关闭）

    private Integer indexWarn = 1; // 首页提醒（0打开1关闭）

    private Integer emailWarn = 1; // 邮件提醒（0打开1关闭）

    private Integer lastMonth = 1; // 剩余月份提醒阈值（月数）

    private String companyId; // 租户ID

    // 构造方法
    public TfamProductSwitch() {
        super();
    }

    public TfamProductSwitch(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "提醒开关（0打开1关闭）不能为空")
    public Integer getRemindSwitch() {
        return remindSwitch;
    }

    public void setRemindSwitch(Integer remindSwitch) {
        this.remindSwitch = remindSwitch;
    }

    @NotNull(message = "首页提醒（0打开1关闭）不能为空")
    public Integer getIndexWarn() {
        return indexWarn;
    }

    public void setIndexWarn(Integer indexWarn) {
        this.indexWarn = indexWarn;
    }

    @NotNull(message = "邮件提醒（0打开1关闭）不能为空")
    public Integer getEmailWarn() {
        return emailWarn;
    }

    public void setEmailWarn(Integer emailWarn) {
        this.emailWarn = emailWarn;
    }

    @NotNull(message = "剩余月份提醒阈值（月数）不能为空")
    public Integer getLastMonth() {
        return lastMonth;
    }

    public void setLastMonth(Integer lastMonth) {
        this.lastMonth = lastMonth;
    }

    @Length(min = 0, max = 64, message = "租户ID长度必须介于 0 和 64 之间")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tfam_product_switch";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1577773656573117285";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "产品开关表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
