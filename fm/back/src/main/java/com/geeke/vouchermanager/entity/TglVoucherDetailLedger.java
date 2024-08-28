package com.geeke.vouchermanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 制证Entity
 * @author
 * @version
 */

@ApiModel(description = "生成get和set方法")
public class TglVoucherDetailLedger extends DataEntity<TglVoucherDetailLedger> {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1457254743943432959L;

    @ApiModelProperty("凭证分录分户主键ID")
    private String uqvouledgerid; // 凭证分录分户主键ID

    @ApiModelProperty("凭证分录分户分摊ID")
    private String uqledgerid; // 凭证分录分户分摊ID

    @ApiModelProperty("凭证分录分户类别ID")
    private String uqledgertypeid; // 凭证分录分户类别ID

    @ApiModelProperty("凭证分录ID")
    private String uqvoucherdetailid; // 凭证分录ID

    @ApiModelProperty("摊销金额")
    private BigDecimal mnyamount; // 摊销金额

    @ApiModelProperty("摊销借方金额")
    private BigDecimal mnydebit; // 摊销借方金额

    @ApiModelProperty("摊销贷方金额")
    private BigDecimal mnycredit; // 摊销贷方金额

    @ApiModelProperty("序号")
    private Integer intseq; // 序号

    // 构造方法
    public TglVoucherDetailLedger() {
        super();
    }

    public TglVoucherDetailLedger(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 40, message = "凭证分录分户主键ID长度必须介于 1 和 40 之间")
    public String getUqvouledgerid() {
        return uqvouledgerid;
    }

    public void setUqvouledgerid(String uqvouledgerid) {
        this.uqvouledgerid = uqvouledgerid;
    }

    @Length(min = 1, max = 40, message = "凭证分录分户分摊ID长度必须介于 1 和 40 之间")
    public String getUqledgerid() {
        return uqledgerid;
    }

    public void setUqledgerid(String uqledgerid) {
        this.uqledgerid = uqledgerid;
    }

    @Length(min = 1, max = 40, message = "凭证分录分户类别ID长度必须介于 1 和 40 之间")
    public String getUqledgertypeid() {
        return uqledgertypeid;
    }

    public void setUqledgertypeid(String uqledgertypeid) {
        this.uqledgertypeid = uqledgertypeid;
    }

    @Length(min = 1, max = 40, message = "凭证分录ID长度必须介于 1 和 40 之间")
    public String getUqvoucherdetailid() {
        return uqvoucherdetailid;
    }

    public void setUqvoucherdetailid(String uqvoucherdetailid) {
        this.uqvoucherdetailid = uqvoucherdetailid;
    }

    public BigDecimal getMnyamount() {
        return mnyamount;
    }

    public void setMnyamount(BigDecimal mnyamount) {
        this.mnyamount = mnyamount;
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

    public Integer getIntseq() {
        return intseq;
    }

    public void setIntseq(Integer intseq) {
        this.intseq = intseq;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tgl_voucher_detail_ledger";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943432959";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "凭证分录分户分摊表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return false;
    }
}
