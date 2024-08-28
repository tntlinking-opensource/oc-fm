package com.geeke.vouchermanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 往来初始化表Entity
 * @author
 * @version
 */

public class TglAcIni extends DataEntity<TglAcIni> {

    private static final long serialVersionUID = 1740772218121937295L;

    private String iniid; // 往来初始化明细ID

    private String uqaccountid; // 科目ID

    private String uqcompanyid; // 公司ID

    private String uqledgetypeid; // 分户类别ID

    private String uqledgeid; // 分户ID

    private String varabstract; // 摘要

    private BigDecimal mnydebit; // 借方金额

    private BigDecimal mnycredit; // 贷方金额

    private String uquserid; // 用户ID

    private String busdate; // 业务日期

    private BigDecimal inttype; // 类型：1：挂账。2：冲销

    private String tenantId; // tenant_id

    // 构造方法
    public TglAcIni() {
        super();
    }

    public TglAcIni(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 40, message = "往来初始化明细ID长度必须介于 1 和 40 之间")
    public String getIniid() {
        return iniid;
    }

    public void setIniid(String iniid) {
        this.iniid = iniid;
    }

    @Length(min = 1, max = 40, message = "科目ID长度必须介于 1 和 40 之间")
    public String getUqaccountid() {
        return uqaccountid;
    }

    public void setUqaccountid(String uqaccountid) {
        this.uqaccountid = uqaccountid;
    }

    @Length(min = 1, max = 40, message = "公司ID长度必须介于 1 和 40 之间")
    public String getUqcompanyid() {
        return uqcompanyid;
    }

    public void setUqcompanyid(String uqcompanyid) {
        this.uqcompanyid = uqcompanyid;
    }

    @Length(min = 0, max = 40, message = "分户类别ID长度必须介于 0 和 40 之间")
    public String getUqledgetypeid() {
        return uqledgetypeid;
    }

    public void setUqledgetypeid(String uqledgetypeid) {
        this.uqledgetypeid = uqledgetypeid;
    }

    @Length(min = 0, max = 40, message = "分户ID长度必须介于 0 和 40 之间")
    public String getUqledgeid() {
        return uqledgeid;
    }

    public void setUqledgeid(String uqledgeid) {
        this.uqledgeid = uqledgeid;
    }

    @Length(min = 0, max = 500, message = "摘要长度必须介于 0 和 500 之间")
    public String getVarabstract() {
        return varabstract;
    }

    public void setVarabstract(String varabstract) {
        this.varabstract = varabstract;
    }

    public BigDecimal getMnydebit() {
        return mnydebit;
    }

    public void setMnydebit(BigDecimal mnydebit) {
        this.mnydebit = mnydebit;
    }

    public BigDecimal getMnycredit() {
        return mnycredit;
    }

    public void setMnycredit(BigDecimal mnycredit) {
        this.mnycredit = mnycredit;
    }

    @Length(min = 1, max = 40, message = "用户ID长度必须介于 1 和 40 之间")
    public String getUquserid() {
        return uquserid;
    }

    public void setUquserid(String uquserid) {
        this.uquserid = uquserid;
    }

    @Length(min = 1, max = -1, message = "业务日期长度必须介于 1 和 -1 之间")
    public String getBusdate() {
        return busdate;
    }

    public void setBusdate(String busdate) {
        this.busdate = busdate;
    }

    @NotNull(message = "类型：1：挂账。2：冲销不能为空")
    public BigDecimal getInttype() {
        return inttype;
    }

    public void setInttype(BigDecimal inttype) {
        this.inttype = inttype;
    }

    @Length(min = 1, max = 100, message = "tenant_id长度必须介于 1 和 100 之间")
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tgl_ac_ini";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1740772218121937295";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "往来初始化表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return false;
    }
}
