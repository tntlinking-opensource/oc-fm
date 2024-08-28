package com.geeke.cardasset.credit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 资产折旧表Entity
 * @author
 * @version
 */

public class TfamDepfuture extends DataEntity<TfamDepfuture> {

    private static final long serialVersionUID = 1641034040897358726L;

    private String assetId; // 资产id

    private String year; // 年份

    private String month; // 月份

    private String usingstatus; // 使用状态

    private BigDecimal value; // 原值

    private BigDecimal accudep; // 累计折旧

    private BigDecimal netvalue; // 净值

    private BigDecimal currdep; // 本（年/月）折旧

    private Integer naturemonth; // 预计使用月数

    private Integer servicemonth; // 剩余使用月数

    private String companyId; // company_id

    // 构造方法
    public TfamDepfuture() {
        super();
    }

    public TfamDepfuture(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 100, message = "资产id长度必须介于 1 和 100 之间")
    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    @Length(min = 1, max = 4, message = "年份长度必须介于 1 和 4 之间")
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Length(min = 1, max = 2, message = "月份长度必须介于 1 和 2 之间")
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Length(min = 1, max = 20, message = "使用状态长度必须介于 1 和 20 之间")
    public String getUsingstatus() {
        return usingstatus;
    }

    public void setUsingstatus(String usingstatus) {
        this.usingstatus = usingstatus;
    }

    @NotNull(message = "原值不能为空")
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @NotNull(message = "累计折旧不能为空")
    public BigDecimal getAccudep() {
        return accudep;
    }

    public void setAccudep(BigDecimal accudep) {
        this.accudep = accudep;
    }

    @NotNull(message = "净值不能为空")
    public BigDecimal getNetvalue() {
        return netvalue;
    }

    public void setNetvalue(BigDecimal netvalue) {
        this.netvalue = netvalue;
    }

    @NotNull(message = "本（年/月）折旧不能为空")
    public BigDecimal getCurrdep() {
        return currdep;
    }

    public void setCurrdep(BigDecimal currdep) {
        this.currdep = currdep;
    }

    @NotNull(message = "预计使用月数不能为空")
    public Integer getNaturemonth() {
        return naturemonth;
    }

    public void setNaturemonth(Integer naturemonth) {
        this.naturemonth = naturemonth;
    }

    @NotNull(message = "剩余使用月数不能为空")
    public Integer getServicemonth() {
        return servicemonth;
    }

    public void setServicemonth(Integer servicemonth) {
        this.servicemonth = servicemonth;
    }

    @Length(min = 1, max = 100, message = "company_id长度必须介于 1 和 100 之间")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tfam_depfuture";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897358726";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "资产折旧表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
