package com.geeke.vouchermanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 非结转科目发生数汇总表Entity
 * @author
 * @version
 */

public class TglPeriodAccountsNotjz extends DataEntity<TglPeriodAccountsNotjz> {

    private static final long serialVersionUID = 1457254743943433679L;

    private String uqcompanyid; // 单位ID

    private String uqglobalperiodid; // 全局会计期ID

    private String uqaccountid; // 会计科目ID

    private BigDecimal mnydebitperiod; // 非结转已入账本期借方金额

    private BigDecimal mnycreditperiod; // 非结转已入账本期贷方金额

    private BigDecimal mnyfdebitperiod; // 非结转已入账本期借方外币金额

    private BigDecimal mnyfcreditperiod; // 非结转已入账本期贷方外币金额

    private BigDecimal mnydebitperiodall; // 非结转全部本期借方金额

    private BigDecimal mnycreditperiodall; // 非结转全部本期贷方金额

    private BigDecimal mnyfdebitperiodall; // 非结转全部本期借方外币金额

    private BigDecimal mnyfcreditperiodall; // 非结转全部本期贷方外币金额

    private BigDecimal mnydebitcashed; // MNYDEBITCASHED

    private BigDecimal mnycreditcashed; // MNYCREDITCASHED

    private BigDecimal mnyfdebitcashed; // MNYFDEBITCASHED

    private BigDecimal mnyfcreditcashed; // MNYFCREDITCASHED

    // 构造方法
    public TglPeriodAccountsNotjz() {
        super();
    }

    public TglPeriodAccountsNotjz(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 40, message = "单位ID长度必须介于 1 和 40 之间")
    public String getUqcompanyid() {
        return uqcompanyid;
    }

    public void setUqcompanyid(String uqcompanyid) {
        this.uqcompanyid = uqcompanyid;
    }

    @Length(min = 1, max = 40, message = "全局会计期ID长度必须介于 1 和 40 之间")
    public String getUqglobalperiodid() {
        return uqglobalperiodid;
    }

    public void setUqglobalperiodid(String uqglobalperiodid) {
        this.uqglobalperiodid = uqglobalperiodid;
    }

    @Length(min = 1, max = 40, message = "会计科目ID长度必须介于 1 和 40 之间")
    public String getUqaccountid() {
        return uqaccountid;
    }

    public void setUqaccountid(String uqaccountid) {
        this.uqaccountid = uqaccountid;
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

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tgl_period_accounts_notjz";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943433679";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "非结转科目发生数汇总表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return false;
    }
}
