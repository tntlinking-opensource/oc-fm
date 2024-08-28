package com.geeke.assetting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 月折旧表Entity
 * @author
 * @version
 */

public class TfamDepmonth extends DataEntity<TfamDepmonth> {

    private static final long serialVersionUID = 1641034040897356443L;

    private String year; // 折旧年份

    private String month; // 折旧月份

    private String voucherid; // 凭证id

    private String companyid; // 公司id（多公司备用）

    private String companyId; // company_id

    // 构造方法
    public TfamDepmonth() {
        super();
    }

    public TfamDepmonth(String id) {
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

    @Length(min = 1, max = 4, message = "折旧月份长度必须介于 1 和 4 之间")
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Length(min = 0, max = 40, message = "凭证id长度必须介于 0 和 40 之间")
    public String getVoucherid() {
        return voucherid;
    }

    public void setVoucherid(String voucherid) {
        this.voucherid = voucherid;
    }

    @Length(min = 0, max = 100, message = "公司id（多公司备用）长度必须介于 0 和 100 之间")
    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
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
        return "tfam_depmonth";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897356443";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "月折旧表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
