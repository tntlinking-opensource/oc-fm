package com.geeke.vouchermanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.org.entity.Company;
import com.geeke.tgl.global.entity.GlobalPeriods;
import com.geeke.vouchermanager.entity.TglAccounts;
import com.geeke.vouchermanager.entity.TglLedger;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 * 分户期初余额设置Entity
 * @author
 * @version
 */

@ApiModel(description = "生成get和set方法")
public class CompanyLedgerPeriods extends DataEntity<CompanyLedgerPeriods> {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1457254743943431111L;

    @ApiModelProperty("公司")
    private Company uqcompanyid; // 公司

    @ApiModelProperty("会计期")
    private GlobalPeriods uqglobalperiodid; // 会计期

    @ApiModelProperty("会计科目")
    private TglAccounts uqaccountid; // 会计科目

    @ApiModelProperty("分户")
    private TglLedger uqledgeid; // 分户

    @ApiModelProperty("全部本期借方金额")
    private BigDecimal mnydebitperiodall; // 全部本期借方金额

    @ApiModelProperty("全部本期贷方金额")
    private BigDecimal mnycreditperiodall; // 全部本期贷方金额

    @ApiModelProperty("全部本期借方外币金额")
    private BigDecimal mnyfdebitperiodall; // 全部本期借方外币金额

    @ApiModelProperty("全部本期贷方外币金额")
    private BigDecimal mnyfcreditperiodall; // 全部本期贷方外币金额

    @ApiModelProperty("已入账本期借方金额")
    private BigDecimal mnydebitperiod; // 已入账本期借方金额

    @ApiModelProperty("已入账本期贷方金额")
    private BigDecimal mnycreditperiod; // 已入账本期贷方金额

    @ApiModelProperty("已入账本期借方外币金额")
    private BigDecimal mnyfdebitperiod; // 已入账本期借方外币金额

    @ApiModelProperty("已入账本期贷方外币金额")
    private BigDecimal mnyfcreditperiod; // 已入账本期贷方外币金额

    @ApiModelProperty("已出纳本期借方金额")
    private BigDecimal mnydebitcashed; // 已出纳本期借方金额

    @ApiModelProperty("已出纳本期贷方金额")
    private BigDecimal mnycreditcashed; // 已出纳本期贷方金额

    @ApiModelProperty("已出纳本期借方外币金额")
    private BigDecimal mnyfdebitcashed; // 已出纳本期借方外币金额

    @ApiModelProperty("已出纳本期贷方外币金额")
    private BigDecimal mnyfcreditcashed; // 已出纳本期贷方外币金额

    @ApiModelProperty("分户类别名称（非数据库字段，导入使用）")
    private String varledgetypename; // 分户类别名称（非数据库字段，导入使用）

    // 构造方法
    public CompanyLedgerPeriods() {
        super();
    }

    public CompanyLedgerPeriods(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "公司不能为空")
    public Company getUqcompanyid() {
        return uqcompanyid;
    }

    public void setUqcompanyid(Company uqcompanyid) {
        this.uqcompanyid = uqcompanyid;
    }

    @NotNull(message = "会计期不能为空")
    public GlobalPeriods getUqglobalperiodid() {
        return uqglobalperiodid;
    }

    public void setUqglobalperiodid(GlobalPeriods uqglobalperiodid) {
        this.uqglobalperiodid = uqglobalperiodid;
    }

    @NotNull(message = "会计科目不能为空")
    public TglAccounts getUqaccountid() {
        return uqaccountid;
    }

    public void setUqaccountid(TglAccounts uqaccountid) {
        this.uqaccountid = uqaccountid;
    }

    @NotNull(message = "分户不能为空")
    public TglLedger getUqledgeid() {
        return uqledgeid;
    }

    public void setUqledgeid(TglLedger uqledgeid) {
        this.uqledgeid = uqledgeid;
    }

    public BigDecimal getMnydebitperiodall() {
        return mnydebitperiodall;
    }

    public void setMnydebitperiodall(BigDecimal mnydebitperiodall) {
        this.mnydebitperiodall = mnydebitperiodall;
    }

    public BigDecimal getMnycreditperiodall() {
        return mnycreditperiodall;
    }

    public void setMnycreditperiodall(BigDecimal mnycreditperiodall) {
        this.mnycreditperiodall = mnycreditperiodall;
    }

    public BigDecimal getMnyfdebitperiodall() {
        return mnyfdebitperiodall;
    }

    public void setMnyfdebitperiodall(BigDecimal mnyfdebitperiodall) {
        this.mnyfdebitperiodall = mnyfdebitperiodall;
    }

    public BigDecimal getMnyfcreditperiodall() {
        return mnyfcreditperiodall;
    }

    public void setMnyfcreditperiodall(BigDecimal mnyfcreditperiodall) {
        this.mnyfcreditperiodall = mnyfcreditperiodall;
    }

    public BigDecimal getMnydebitperiod() {
        return mnydebitperiod;
    }

    public void setMnydebitperiod(BigDecimal mnydebitperiod) {
        this.mnydebitperiod = mnydebitperiod;
    }

    public BigDecimal getMnycreditperiod() {
        return mnycreditperiod;
    }

    public void setMnycreditperiod(BigDecimal mnycreditperiod) {
        this.mnycreditperiod = mnycreditperiod;
    }

    public BigDecimal getMnyfdebitperiod() {
        return mnyfdebitperiod;
    }

    public void setMnyfdebitperiod(BigDecimal mnyfdebitperiod) {
        this.mnyfdebitperiod = mnyfdebitperiod;
    }

    public BigDecimal getMnyfcreditperiod() {
        return mnyfcreditperiod;
    }

    public void setMnyfcreditperiod(BigDecimal mnyfcreditperiod) {
        this.mnyfcreditperiod = mnyfcreditperiod;
    }

    public BigDecimal getMnydebitcashed() {
        return mnydebitcashed;
    }

    public void setMnydebitcashed(BigDecimal mnydebitcashed) {
        this.mnydebitcashed = mnydebitcashed;
    }

    public BigDecimal getMnycreditcashed() {
        return mnycreditcashed;
    }

    public void setMnycreditcashed(BigDecimal mnycreditcashed) {
        this.mnycreditcashed = mnycreditcashed;
    }

    public BigDecimal getMnyfdebitcashed() {
        return mnyfdebitcashed;
    }

    public void setMnyfdebitcashed(BigDecimal mnyfdebitcashed) {
        this.mnyfdebitcashed = mnyfdebitcashed;
    }

    public BigDecimal getMnyfcreditcashed() {
        return mnyfcreditcashed;
    }

    public void setMnyfcreditcashed(BigDecimal mnyfcreditcashed) {
        this.mnyfcreditcashed = mnyfcreditcashed;
    }

    public String getVarledgetypename() { return varledgetypename; }

    public void setVarledgetypename(String varledgetypename) { this.varledgetypename = varledgetypename; }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tgl_company_ledger_periods";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943431111";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "分户期初余额设置";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return false;
    }
}
