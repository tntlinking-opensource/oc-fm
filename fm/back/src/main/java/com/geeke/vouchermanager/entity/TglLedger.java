package com.geeke.vouchermanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.vouchermanager.entity.TglLedgetype;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 分户Entity
 * @author
 * @version
 */

@ApiModel(description = "生成get和set方法")
public class TglLedger extends DataEntity<TglLedger> {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1457254743943430950L;

    @ApiModelProperty("分户ID")
    private String uqledgeid; // 分户ID

    @ApiModelProperty(hidden = true)
    private TglLedgetype uqledgetypeid; // 分户类别ID

    @ApiModelProperty("分户编码")
    private String varledgecode; // 分户编码

    @ApiModelProperty("分户名称")
    private String varledgename; // 分户名称

    @ApiModelProperty("分户全编码")
    private String varledgefullcode; // 分户全编码

    @ApiModelProperty("分户全名称")
    private String varledgefullname; // 分户全名称

    @ApiModelProperty("上级ID")
    private String uqparentid; // 上级ID

    @ApiModelProperty("分户等级")
    private Integer intlevel; // 分户等级

    @ApiModelProperty("是否末级")
    private Integer intislastlevel; // 是否末级

    @ApiModelProperty("状态")
    private Integer intstatus; // 状态

    @ApiModelProperty("tenant_id")
    private String tenantId; // tenant_id

    private String code; // 自定义使用字段

    @ApiModelProperty(hidden = true)
    private CompanyLedgerPeriods companyLedgerPeriods; // 分户初期余额
    @ApiModelProperty("分户初期余额")
    private TglVoucherDetailLedger tglVoucherDetailLedger; // 分户初期余额

    // 构造方法
    public TglLedger() {
        super();
    }

    public TglLedger(String id) {
        super(id);
    }

    public TglVoucherDetailLedger getTglVoucherDetailLedger() {
        return tglVoucherDetailLedger;
    }

    public void setTglVoucherDetailLedger(TglVoucherDetailLedger tglVoucherDetailLedger) {
        this.tglVoucherDetailLedger = tglVoucherDetailLedger;
    }
// 生成get和set方法


    public String getUqledgeid() {
        return uqledgeid;
    }

    public void setUqledgeid(String uqledgeid) {
        this.uqledgeid = uqledgeid;
    }

    public TglLedgetype getUqledgetypeid() {
        return uqledgetypeid;
    }

    public void setUqledgetypeid(TglLedgetype uqledgetypeid) {
        this.uqledgetypeid = uqledgetypeid;
    }

    @Length(min = 0, max = 100, message = "分户编码长度必须介于 0 和 100 之间")
    public String getVarledgecode() {
        return varledgecode;
    }

    public void setVarledgecode(String varledgecode) {
        this.varledgecode = varledgecode;
    }

    @Length(min = 0, max = 100, message = "分户名称长度必须介于 0 和 100 之间")
    public String getVarledgename() {
        return varledgename;
    }

    public void setVarledgename(String varledgename) {
        this.varledgename = varledgename;
    }

    @Length(min = 0, max = 250, message = "分户全编码长度必须介于 0 和 250 之间")
    public String getVarledgefullcode() {
        return varledgefullcode;
    }

    public void setVarledgefullcode(String varledgefullcode) {
        this.varledgefullcode = varledgefullcode;
    }

    @Length(min = 0, max = 1000, message = "分户全名称长度必须介于 0 和 1000 之间")
    public String getVarledgefullname() {
        return varledgefullname;
    }

    public void setVarledgefullname(String varledgefullname) {
        this.varledgefullname = varledgefullname;
    }

    @Length(min = 0, max = 40, message = "上级ID长度必须介于 0 和 40 之间")
    public String getUqparentid() {
        return uqparentid;
    }

    public void setUqparentid(String uqparentid) {
        this.uqparentid = uqparentid;
    }

    public Integer getIntlevel() {
        return intlevel;
    }

    public void setIntlevel(Integer intlevel) {
        this.intlevel = intlevel;
    }

    public Integer getIntislastlevel() {
        return intislastlevel;
    }

    public void setIntislastlevel(Integer intislastlevel) {
        this.intislastlevel = intislastlevel;
    }

    public Integer getIntstatus() {
        return intstatus;
    }

    public void setIntstatus(Integer intstatus) {
        this.intstatus = intstatus;
    }

    @Length(min = 1, max = 100, message = "tenant_id长度必须介于 1 和 100 之间")
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public CompanyLedgerPeriods getCompanyLedgerPeriods() { return companyLedgerPeriods; }

    public void setCompanyLedgerPeriods(CompanyLedgerPeriods companyLedgerPeriods) { this.companyLedgerPeriods = companyLedgerPeriods; }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tgl_ledger";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943430950";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "分户明细表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return false;
    }
}
