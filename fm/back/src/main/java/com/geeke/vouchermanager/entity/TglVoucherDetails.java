package com.geeke.vouchermanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 制证Entity
 * @author
 * @version
 */

@ApiModel(description = "生成get和set方法")
public class TglVoucherDetails extends DataEntity<TglVoucherDetails> {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1457254743943432958L;

    @ApiModelProperty("后台生成不用传 凭证明细ID")
    private String uqvoucherdetailid; // 凭证明细ID

    @ApiModelProperty("后台生成不用传 凭证ID")
    private String uqvoucherid; // 凭证ID

    @ApiModelProperty("序号")
    private Integer intseq; // 序号

    @ApiModelProperty("摘要")
    private String varabstract; // 摘要

    @ApiModelProperty("往来ID")
    private String uqcgsid; // 往来ID

    @ApiModelProperty("会计科目ID")
    private String uqaccountid; // 会计科目ID

    @ApiModelProperty("借方金额")
    private BigDecimal mnydebit; // 借方金额

    @ApiModelProperty("贷方金额")
    private BigDecimal mnycredit; // 贷方金额

    @ApiModelProperty(hidden = true)
    private BigDecimal mnyfdebit; // MNYFDEBIT

    @ApiModelProperty(hidden = true)
    private BigDecimal mnyfcredit; // MNYFCREDIT

    @ApiModelProperty(hidden = true)
    private String uqpaytypeid; // 结算方式

    @ApiModelProperty(hidden = true)
    private String vartypeno; // 结算号

    @ApiModelProperty(hidden = true)
    private BigDecimal decnumber; // 数量

    @ApiModelProperty(hidden = true)
    private BigDecimal mnyprice; // 单价

    @ApiModelProperty(hidden = true)
    private BigDecimal mnyforeigncurr; // 外币金额

    @ApiModelProperty(hidden = true)
    private BigDecimal decexchagerate; // 外币汇率

    @ApiModelProperty("分户类型 ")
    private List<TglLedgetype> tglledgetype = Lists.newArrayList();
    @ApiModelProperty("科目信息")
    private TglAccounts tglAccounts;
    @ApiModelProperty("现金流量")
    private TglAccountsXjll tglAccountsXjll;
    // 构造方法
    public TglVoucherDetails() {
        super();
    }

    public TglVoucherDetails(String id) {
        super(id);
    }

    public TglAccounts getTglAccounts() {
        return tglAccounts;
    }

    public void setTglAccounts(TglAccounts tglAccounts) {
        this.tglAccounts = tglAccounts;
    }
// 生成get和set方法

    public TglAccountsXjll getTglAccountsXjll() {
        return tglAccountsXjll;
    }

    public void setTglAccountsXjll(TglAccountsXjll tglAccountsXjll) {
        this.tglAccountsXjll = tglAccountsXjll;
    }

    public  List<TglLedgetype> getTglledgetype() {
        return tglledgetype;
    }

    public void setTglledgetype( List<TglLedgetype> tglledgetype) {
        this.tglledgetype = tglledgetype;
    }

    @Length(min = 1, max = 40, message = "凭证明细ID长度必须介于 1 和 40 之间")
    public String getUqvoucherdetailid() {
        return uqvoucherdetailid;
    }

    public void setUqvoucherdetailid(String uqvoucherdetailid) {
        this.uqvoucherdetailid = uqvoucherdetailid;
    }

    @Length(min = 1, max = 40, message = "凭证ID长度必须介于 1 和 40 之间")
    public String getUqvoucherid() {
        return uqvoucherid;
    }

    public void setUqvoucherid(String uqvoucherid) {
        this.uqvoucherid = uqvoucherid;
    }

    @NotNull(message = "序号不能为空")
    public Integer getIntseq() {
        return intseq;
    }

    public void setIntseq(Integer intseq) {
        this.intseq = intseq;
    }

    @Length(min = 1, max = 500, message = "摘要长度必须介于 1 和 500 之间")
    public String getVarabstract() {
        return varabstract;
    }

    public void setVarabstract(String varabstract) {
        this.varabstract = varabstract;
    }

    @Length(min = 0, max = 40, message = "往来ID长度必须介于 0 和 40 之间")
    public String getUqcgsid() {
        return uqcgsid;
    }

    public void setUqcgsid(String uqcgsid) {
        this.uqcgsid = uqcgsid;
    }

    @Length(min = 1, max = 40, message = "会计科目ID长度必须介于 1 和 40 之间")
    public String getUqaccountid() {
        return uqaccountid;
    }

    public void setUqaccountid(String uqaccountid) {
        this.uqaccountid = uqaccountid;
    }

    @NotNull(message = "借方金额不能为空")
    public BigDecimal getMnydebit() {
        return mnydebit;
    }

    public void setMnydebit(BigDecimal mnydebit) {
        this.mnydebit = mnydebit;
    }

    @NotNull(message = "贷方金额不能为空")
    public BigDecimal getMnycredit() {
        return mnycredit;
    }

    public void setMnycredit(BigDecimal mnycredit) {
        this.mnycredit = mnycredit;
    }

    public BigDecimal getMnyfdebit() {
        return mnyfdebit;
    }

    public void setMnyfdebit(BigDecimal mnyfdebit) {
        this.mnyfdebit = mnyfdebit;
    }

    public BigDecimal getMnyfcredit() {
        return mnyfcredit;
    }

    public void setMnyfcredit(BigDecimal mnyfcredit) {
        this.mnyfcredit = mnyfcredit;
    }

    @Length(min = 0, max = 40, message = "结算方式长度必须介于 0 和 40 之间")
    public String getUqpaytypeid() {
        return uqpaytypeid;
    }

    public void setUqpaytypeid(String uqpaytypeid) {
        this.uqpaytypeid = uqpaytypeid;
    }

    @Length(min = 0, max = 40, message = "结算号长度必须介于 0 和 40 之间")
    public String getVartypeno() {
        return vartypeno;
    }

    public void setVartypeno(String vartypeno) {
        this.vartypeno = vartypeno;
    }

    public BigDecimal getDecnumber() {
        return decnumber;
    }

    public void setDecnumber(BigDecimal decnumber) {
        this.decnumber = decnumber;
    }

    public BigDecimal getMnyprice() {
        return mnyprice;
    }

    public void setMnyprice(BigDecimal mnyprice) {
        this.mnyprice = mnyprice;
    }

    public BigDecimal getMnyforeigncurr() {
        return mnyforeigncurr;
    }

    public void setMnyforeigncurr(BigDecimal mnyforeigncurr) {
        this.mnyforeigncurr = mnyforeigncurr;
    }

    public BigDecimal getDecexchagerate() {
        return decexchagerate;
    }

    public void setDecexchagerate(BigDecimal decexchagerate) {
        this.decexchagerate = decexchagerate;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tgl_voucher_details";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943432958";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "凭证明细表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return false;
    }
}
