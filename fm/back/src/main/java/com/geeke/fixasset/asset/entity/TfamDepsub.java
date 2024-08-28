package com.geeke.fixasset.asset.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.fixasset.asset.entity.TfamAsset;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 卡片管理Entity
 * @author
 * @version
 */

public class TfamDepsub extends DataEntity<TfamDepsub> {

    private static final long serialVersionUID = 1641034040897356657L;

    private String year; // 折旧年份

    private String month; // 折旧月份

    private String date; // 折旧日期

    private TfamAsset asset; // 资产id（资产表id）

    private BigDecimal depamount; // 折旧金额

    private Integer depType; // 折旧类型（1月折旧，2补提，3冲减）

    private String resaon; // 补提冲减原因

    private BigDecimal value; // 原值

    private BigDecimal accudep; // 累计折旧

    private BigDecimal netvalue; // 净值

    private String vouformat; // 凭证字

    private String voucherid; // 凭证id

    private String abstracts; // 凭证摘要

    private String creatorid; // 操作人（用户表id）

    private String createTime; // 操作日期

    private String companyid; // 公司id（后期多公司备用）

    private BigDecimal workloan; // 工作量（工作量法每月折旧的工作量）

    private Integer depmethod; // 折旧方法

    private Integer dbflag; // 双倍余额法是否变动（1-已变动，0-没有变动）双倍余额法有效

    private String usingstatus; // usingstatus

    private String companyId; // company_id

    // 构造方法
    public TfamDepsub() {
        super();
    }

    public TfamDepsub(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 4, message = "折旧年份长度必须介于 1 和 4 之间")
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Length(min = 1, max = 2, message = "折旧月份长度必须介于 1 和 2 之间")
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Length(min = 1, max = -1, message = "折旧日期长度必须介于 1 和 -1 之间")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @NotNull(message = "资产id（资产表id）不能为空")
    public TfamAsset getAsset() {
        return asset;
    }

    public void setAsset(TfamAsset asset) {
        this.asset = asset;
    }

    @NotNull(message = "折旧金额不能为空")
    public BigDecimal getDepamount() {
        return depamount;
    }

    public void setDepamount(BigDecimal depamount) {
        this.depamount = depamount;
    }

    @NotNull(message = "折旧类型（1月折旧，2补提，3冲减）不能为空")
    public Integer getDepType() {
        return depType;
    }

    public void setDepType(Integer depType) {
        this.depType = depType;
    }

    @Length(min = 0, max = 80, message = "补提冲减原因长度必须介于 0 和 80 之间")
    public String getResaon() {
        return resaon;
    }

    public void setResaon(String resaon) {
        this.resaon = resaon;
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

    @Length(min = 0, max = 50, message = "凭证字长度必须介于 0 和 50 之间")
    public String getVouformat() {
        return vouformat;
    }

    public void setVouformat(String vouformat) {
        this.vouformat = vouformat;
    }

    @Length(min = 0, max = 100, message = "凭证id长度必须介于 0 和 100 之间")
    public String getVoucherid() {
        return voucherid;
    }

    public void setVoucherid(String voucherid) {
        this.voucherid = voucherid;
    }

    @Length(min = 0, max = 100, message = "凭证摘要长度必须介于 0 和 100 之间")
    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    @Length(min = 1, max = 100, message = "操作人（用户表id）长度必须介于 1 和 100 之间")
    public String getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(String creatorid) {
        this.creatorid = creatorid;
    }

    @Length(min = 1, max = -1, message = "操作日期长度必须介于 1 和 -1 之间")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Length(min = 0, max = 100, message = "公司id（后期多公司备用）长度必须介于 0 和 100 之间")
    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public BigDecimal getWorkloan() {
        return workloan;
    }

    public void setWorkloan(BigDecimal workloan) {
        this.workloan = workloan;
    }

    public Integer getDepmethod() {
        return depmethod;
    }

    public void setDepmethod(Integer depmethod) {
        this.depmethod = depmethod;
    }

    public Integer getDbflag() {
        return dbflag;
    }

    public void setDbflag(Integer dbflag) {
        this.dbflag = dbflag;
    }

    @Length(min = 0, max = 20, message = "usingstatus长度必须介于 0 和 20 之间")
    public String getUsingstatus() {
        return usingstatus;
    }

    public void setUsingstatus(String usingstatus) {
        this.usingstatus = usingstatus;
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
        return "tfam_depsub";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897356657";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "折旧明细表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
