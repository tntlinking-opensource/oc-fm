package com.geeke.vouchermanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.vouchermanager.entity.TglAcOffsetDetail;
import com.geeke.vouchermanager.entity.TglAcOffsetDetail;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 往来冲销总表Entity
 * @author
 * @version
 */

public class TglAcOffsetMain extends DataEntity<TglAcOffsetMain> {

    private static final long serialVersionUID = 1538459003414151355L;

    private String uqmainid; // 主键ID

    private String uqcompanyid; // 公司ID

    private String uqvoucherid; // 凭证ID（关联凭证时不为空）

    private String uqvoudetailid; // 凭证明细ID（关联凭证时不为空）

    private String iniid; // 往来初始化明细ID（非关联凭证时不为空）

    private String uqaccountid; // 科目ID

    private String uqledgetypeid; // 分户类别ID

    private String uqledgeid; // 分户ID

    private BigDecimal totalmoney = new BigDecimal("0.00"); // 总金额

    private BigDecimal rushedmoney = new BigDecimal("0.00"); // 已冲金额

    private BigDecimal notrushedmoney = new BigDecimal("0.00"); // 未冲金额

    private String uquserid; // 用户ID

    private String inserttime; // 新增时间

    private String updatetime; // 更新时间

    private BigDecimal inttype = new BigDecimal("0"); // 类型：1:冲销记录 2：挂账记录

    private String tenantId; // tenant_id

    private List<TglAcOffsetDetail> tglAcOffsetDetailList = Lists.newArrayList(); // 子表列表

    // 构造方法
    public TglAcOffsetMain() {
        super();
    }

    public TglAcOffsetMain(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 40, message = "主键ID长度必须介于 1 和 40 之间")
    public String getUqmainid() {
        return uqmainid;
    }

    public void setUqmainid(String uqmainid) {
        this.uqmainid = uqmainid;
    }

    @Length(min = 0, max = 40, message = "公司ID长度必须介于 0 和 40 之间")
    public String getUqcompanyid() {
        return uqcompanyid;
    }

    public void setUqcompanyid(String uqcompanyid) {
        this.uqcompanyid = uqcompanyid;
    }

    @Length(min = 0, max = 40, message = "凭证ID（关联凭证时不为空）长度必须介于 0 和 40 之间")
    public String getUqvoucherid() {
        return uqvoucherid;
    }

    public void setUqvoucherid(String uqvoucherid) {
        this.uqvoucherid = uqvoucherid;
    }

    public String getUqvoudetailid() {
        return uqvoudetailid;
    }

    public void setUqvoudetailid(String uqvoudetailid) {
        this.uqvoudetailid = uqvoudetailid;
    }

    @Length(min = 0, max = 40, message = "往来初始化明细ID（非关联凭证时不为空）长度必须介于 0 和 40 之间")
    public String getIniid() {
        return iniid;
    }

    public void setIniid(String iniid) {
        this.iniid = iniid;
    }

    @Length(min = 0, max = 40, message = "科目ID长度必须介于 0 和 40 之间")
    public String getUqaccountid() {
        return uqaccountid;
    }

    public void setUqaccountid(String uqaccountid) {
        this.uqaccountid = uqaccountid;
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

    public BigDecimal getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(BigDecimal totalmoney) {
        this.totalmoney = totalmoney;
    }

    public BigDecimal getRushedmoney() {
        return rushedmoney;
    }

    public void setRushedmoney(BigDecimal rushedmoney) {
        this.rushedmoney = rushedmoney;
    }

    public BigDecimal getNotrushedmoney() {
        return notrushedmoney;
    }

    public void setNotrushedmoney(BigDecimal notrushedmoney) {
        this.notrushedmoney = notrushedmoney;
    }

    @Length(min = 0, max = 40, message = "用户ID长度必须介于 0 和 40 之间")
    public String getUquserid() {
        return uquserid;
    }

    public void setUquserid(String uquserid) {
        this.uquserid = uquserid;
    }

    @Length(min = 0, max = -1, message = "新增时间长度必须介于 0 和 -1 之间")
    public String getInserttime() {
        return inserttime;
    }

    public void setInserttime(String inserttime) {
        this.inserttime = inserttime;
    }

    @Length(min = 0, max = -1, message = "更新时间长度必须介于 0 和 -1 之间")
    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

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

    public List<TglAcOffsetDetail> getTglAcOffsetDetailList() {
        return tglAcOffsetDetailList;
    }

    public void setTglAcOffsetDetailList(List<TglAcOffsetDetail> tglAcOffsetDetailList) {
        this.tglAcOffsetDetailList = tglAcOffsetDetailList;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tgl_ac_offset_main";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1538459003414151355";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "往来冲销总表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return false;
    }
}
