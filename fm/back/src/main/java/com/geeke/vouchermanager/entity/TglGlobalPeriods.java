package com.geeke.vouchermanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 制证Entity
 * @author
 * @version
 */

@ApiModel(description = "生成get和set方法")
public class TglGlobalPeriods extends DataEntity<TglGlobalPeriods> {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1457254743943432960L;

    @ApiModelProperty("会计期ID")
    private String uqglobalperiodid; // 会计期ID

    @ApiModelProperty("租户")
    private String tenantId; // 租户

    @ApiModelProperty("年份")
    private Integer intyear; // 年份

    @ApiModelProperty("月份")
    private Integer intmonth; // 月份

    @ApiModelProperty("会计期名称")
    private String varname; // 会计期名称

    @ApiModelProperty("状态(0 停用，1 新增，2 启用)")
    private Integer intstatus; // 状态(0 停用，1 新增，2 启用)

    @ApiModelProperty("属性")
    private Integer intproperty; // 属性

    @ApiModelProperty("有效开始时间")
    private String dtbegin; // 有效开始时间

    @ApiModelProperty("有效结束时间")
    private String dtend; // 有效结束时间

    @ApiModelProperty("年月")
    private Integer intyearmonth; // 年月

    // 构造方法
    public TglGlobalPeriods() {
        super();
    }

    public TglGlobalPeriods(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 40, message = "会计期ID长度必须介于 1 和 40 之间")
    public String getUqglobalperiodid() {
        return uqglobalperiodid;
    }

    public void setUqglobalperiodid(String uqglobalperiodid) {
        this.uqglobalperiodid = uqglobalperiodid;
    }

    @Length(min = 1, max = 21, message = "租户长度必须介于 1 和 21 之间")
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @NotNull(message = "年份不能为空")
    public Integer getIntyear() {
        return intyear;
    }

    public void setIntyear(Integer intyear) {
        this.intyear = intyear;
    }

    @NotNull(message = "月份不能为空")
    public Integer getIntmonth() {
        return intmonth;
    }

    public void setIntmonth(Integer intmonth) {
        this.intmonth = intmonth;
    }

    @Length(min = 1, max = 40, message = "会计期名称长度必须介于 1 和 40 之间")
    public String getVarname() {
        return varname;
    }

    public void setVarname(String varname) {
        this.varname = varname;
    }

    public Integer getIntstatus() {
        return intstatus;
    }

    public void setIntstatus(Integer intstatus) {
        this.intstatus = intstatus;
    }

    public Integer getIntproperty() {
        return intproperty;
    }

    public void setIntproperty(Integer intproperty) {
        this.intproperty = intproperty;
    }

    @Length(min = 1, max = 24, message = "有效开始时间长度必须介于 1 和 24 之间")
    public String getDtbegin() {
        return dtbegin;
    }

    public void setDtbegin(String dtbegin) {
        this.dtbegin = dtbegin;
    }

    @Length(min = 1, max = 24, message = "有效结束时间长度必须介于 1 和 24 之间")
    public String getDtend() {
        return dtend;
    }

    public void setDtend(String dtend) {
        this.dtend = dtend;
    }

    @NotNull(message = "年月不能为空")
    public Integer getIntyearmonth() {
        return intyearmonth;
    }

    public void setIntyearmonth(Integer intyearmonth) {
        this.intyearmonth = intyearmonth;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tgl_global_periods";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943432960";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "会计期";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
