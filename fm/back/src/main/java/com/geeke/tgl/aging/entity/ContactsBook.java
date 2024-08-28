package com.geeke.tgl.aging.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;

import java.math.BigDecimal;

/**
 * 往来账龄报表Entity
 * @author
 * @version
 */
public class ContactsBook extends DataEntity<ContactsBook> {

    private String ledgername; // 客户档案名称

    private String direct; // 方向

    private BigDecimal perioddebit; // 本期借方

    private BigDecimal periodcredit; // 本期贷方

    private BigDecimal begphase; // 期初余额

    private BigDecimal endphase; // 期末余额

    // 构造方法
    public ContactsBook() {
        super();
    }

    public ContactsBook(String id) {
        super(id);
    }

    // 生成get和set方法

    public String getLedgername() {
        return ledgername;
    }
    public void setLedgername(String ledgername) {
        this.ledgername = ledgername;
    }

    public String getDirect() {
        return direct;
    }
    public void setDirect(String direct) {
        this.direct = direct;
    }

    public BigDecimal getPerioddebit() {
        return perioddebit;
    }
    public void setPerioddebit(BigDecimal periodcredit) {
        this.periodcredit = periodcredit;
    }

    public BigDecimal getPeriodcredit() {
        return periodcredit;
    }
    public void setPeriodcredit(BigDecimal periodcredit) {
        this.periodcredit = periodcredit;
    }

    public BigDecimal getBegphase(){
        return begphase;
    }
    public void setBegphase(BigDecimal begphase) {
        this.begphase = begphase;
    }

    public BigDecimal getEndphase() {
        return endphase;
    }
    public void setEndphase(BigDecimal endphase) {
        this.endphase = endphase;
    }
}
