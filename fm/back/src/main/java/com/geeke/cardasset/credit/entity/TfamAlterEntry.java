package com.geeke.cardasset.credit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.cardasset.credit.entity.TglVoucherMainsEntry;
import com.geeke.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 资产入账Entity
 * @author
 * @version
 */

public class TfamAlterEntry extends DataEntity<TfamAlterEntry> {

    private static final long serialVersionUID = 1641034040897358095L;

    private String year; // 审核时点

    private String month; // 审核时点

    private String date; // 审核时点

    private String assetId; // 卡片id

    private String alterType; // 变动内容

    private String oldValue; // 变动前内容

    private String newValue; // 变动前内容

    private String reason; // 变动原因

    private TglVoucherMainsEntry voucherid; // 凭证主表id

    private String vouformat; // 凭证字

    private String abstracts; // 凭证摘要

    private String companyId; // 公司id

    private Integer sourceType; // 来源 1 新增 2减少 3 调整 4 调拨

    // 构造方法
    public TfamAlterEntry() {
        super();
    }

    public TfamAlterEntry(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 0, max = 4, message = "审核时点长度必须介于 0 和 4 之间")
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Length(min = 0, max = 2, message = "审核时点长度必须介于 0 和 2 之间")
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Length(min = 0, max = -1, message = "审核时点长度必须介于 0 和 -1 之间")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Length(min = 0, max = 100, message = "卡片id长度必须介于 0 和 100 之间")
    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    @Length(min = 0, max = 100, message = "变动内容长度必须介于 0 和 100 之间")
    public String getAlterType() {
        return alterType;
    }

    public void setAlterType(String alterType) {
        this.alterType = alterType;
    }

    @Length(min = 0, max = 80, message = "变动前内容长度必须介于 0 和 80 之间")
    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    @Length(min = 0, max = 80, message = "变动前内容长度必须介于 0 和 80 之间")
    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    @Length(min = 0, max = 80, message = "变动原因长度必须介于 0 和 80 之间")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public TglVoucherMainsEntry getVoucherid() {
        return voucherid;
    }

    public void setVoucherid(TglVoucherMainsEntry voucherid) {
        this.voucherid = voucherid;
    }

    @Length(min = 0, max = 20, message = "凭证字长度必须介于 0 和 20 之间")
    public String getVouformat() {
        return vouformat;
    }

    public void setVouformat(String vouformat) {
        this.vouformat = vouformat;
    }

    @Length(min = 0, max = 100, message = "凭证摘要长度必须介于 0 和 100 之间")
    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    @Length(min = 0, max = 100, message = "公司id长度必须介于 0 和 100 之间")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tfam_alter";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897358095";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "变动明细表入账使用";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
