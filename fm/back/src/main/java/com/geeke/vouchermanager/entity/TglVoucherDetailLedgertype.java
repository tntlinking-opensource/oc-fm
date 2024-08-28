package com.geeke.vouchermanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 凭证分录分户类别表Entity
 * @author
 * @version
 */

public class TglVoucherDetailLedgertype extends DataEntity<TglVoucherDetailLedgertype> {

    private static final long serialVersionUID = 1457254743943433464L;

    private String uqvouledgertypeid; // 凭证分录分户主键ID

    private String uqledgertypeid; // 凭证分录分户类别ID

    private String uqvoucherdetailid; // 凭证明细ID

    private String uqvoucherid; // 凭证ID

    private Integer intseq; // 序号

    // 构造方法
    public TglVoucherDetailLedgertype() {
        super();
    }

    public TglVoucherDetailLedgertype(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 40, message = "凭证分录分户主键ID长度必须介于 1 和 40 之间")
    public String getUqvouledgertypeid() {
        return uqvouledgertypeid;
    }

    public void setUqvouledgertypeid(String uqvouledgertypeid) {
        this.uqvouledgertypeid = uqvouledgertypeid;
    }

    @Length(min = 1, max = 40, message = "凭证分录分户类别ID长度必须介于 1 和 40 之间")
    public String getUqledgertypeid() {
        return uqledgertypeid;
    }

    public void setUqledgertypeid(String uqledgertypeid) {
        this.uqledgertypeid = uqledgertypeid;
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
        return "tgl_voucher_detail_ledgertype";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943433464";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "凭证分录分户类别表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return false;
    }
}
