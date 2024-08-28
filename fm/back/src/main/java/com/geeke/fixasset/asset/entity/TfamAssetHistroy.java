package com.geeke.fixasset.asset.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.fixasset.asset.entity.TfamAsset;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 卡片管理Entity
 * @author
 * @version
 */

public class TfamAssetHistroy extends DataEntity<TfamAssetHistroy> {

    private static final long serialVersionUID = 1641034040897356480L;

    private String year; // 业务年份

    private String month; // 业务月份

    private TfamAsset cardCode; // 卡片编号（自增长）

    private String assetCode; // 资产编号

    private String businessDate; // 建卡日期

    private Integer nature; // 卡片性质（1待新增制证，2已新增制证，3待减少制证，4已减少制证）

    private String categoryId; // 资产类别（资产类别表id）

    private String assetName; // 资产名称

    private String spec; // 规格

    private String cardModel; // 型号

    private String place; // 所在地点

    private String producer; // 生产厂家

    private String companyid; // 公司ID 允许为空

    private String usedeptid; // 使用部门（部门id）

    private String userid; // 责任人（用户id）

    private Integer num; // 数量

    private String unit; // 计量单位（资产类别表unit）

    private String usingstatus; // 使用状况（1使用中，2未使用，3不需用，4经营性租出，5愈龄）

    private String memo; // 备注

    private String assetAccountid; // 固定资产科目（科目id）

    private String accudepAccountid; // 累计折旧科目（科目id）

    private String predevaluateAccountid; // 固定资产减值准备科目（科目id）

    private String depcostAccountid; // 折旧费用科目（科目id）

    private Integer isproject; // 是否在建工程中转。1是0否

    private String projectAccountid; // 在建工程科目（科目id）

    private String predevaluateCoraccountid; // 减值准备对方科目（科目id）

    private String taxAccountid; // 税金科目（科目id）

    private BigDecimal value; // 原值

    private Float rate; // 税率(%)

    private BigDecimal tax; // 税金

    private BigDecimal accudep; // 累计折旧

    private BigDecimal predevaluate; // 减值准备

    private BigDecimal netvalue; // 净值

    private Float salvagerate; // 残值率(%)

    private BigDecimal salvage; // 残值

    private Integer depmethod; // 折旧方法（资产类别表depmethod）

    private Integer naturemonth; // 预计使用月数

    private Integer servicemonth; // 剩余使用月数

    private Float deprate; // 月折旧率(%)

    private BigDecimal depamount; // 月折旧额

    private BigDecimal allworkloan; // 总工作量

    private String workloanunit; // 总工作量单位

    private String addReason; // 新增原因（变动方式表id）

    private String createrid; // 创建人id

    private Integer status; // 卡片状态（1新增待审核，2新增已审核，3报废待审核，4报废已审核）

    private String reduceReason; // 报废原因

    private BigDecimal lastyearaccudep; // 上年累计折旧（双倍余额法用）

    private Integer usedmonth = 0; // 已使用月数

    private Integer changedmonth = 0; // 折旧方法变动时记录已使用月数

    private String scrapDate; // 报废时间

    private String companyId; // company_id

    // 构造方法
    public TfamAssetHistroy() {
        super();
    }

    public TfamAssetHistroy(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 4, message = "业务年份长度必须介于 1 和 4 之间")
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Length(min = 1, max = 2, message = "业务月份长度必须介于 1 和 2 之间")
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @NotNull(message = "卡片编号（自增长）不能为空")
    public TfamAsset getCardCode() {
        return cardCode;
    }

    public void setCardCode(TfamAsset cardCode) {
        this.cardCode = cardCode;
    }

    @Length(min = 1, max = 40, message = "资产编号长度必须介于 1 和 40 之间")
    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    @Length(min = 1, max = -1, message = "建卡日期长度必须介于 1 和 -1 之间")
    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    @NotNull(message = "卡片性质（1待新增制证，2已新增制证，3待减少制证，4已减少制证）不能为空")
    public Integer getNature() {
        return nature;
    }

    public void setNature(Integer nature) {
        this.nature = nature;
    }

    @Length(min = 1, max = 100, message = "资产类别（资产类别表id）长度必须介于 1 和 100 之间")
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Length(min = 1, max = 40, message = "资产名称长度必须介于 1 和 40 之间")
    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    @Length(min = 0, max = 80, message = "规格长度必须介于 0 和 80 之间")
    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    @Length(min = 0, max = 80, message = "型号长度必须介于 0 和 80 之间")
    public String getCardModel() {
        return cardModel;
    }

    public void setCardModel(String cardModel) {
        this.cardModel = cardModel;
    }

    @Length(min = 0, max = 40, message = "所在地点长度必须介于 0 和 40 之间")
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Length(min = 0, max = 80, message = "生产厂家长度必须介于 0 和 80 之间")
    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    @Length(min = 0, max = 100, message = "公司ID 允许为空长度必须介于 0 和 100 之间")
    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    @Length(min = 1, max = 100, message = "使用部门（部门id）长度必须介于 1 和 100 之间")
    public String getUsedeptid() {
        return usedeptid;
    }

    public void setUsedeptid(String usedeptid) {
        this.usedeptid = usedeptid;
    }

    @Length(min = 1, max = 100, message = "责任人（用户id）长度必须介于 1 和 100 之间")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @NotNull(message = "数量不能为空")
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Length(min = 1, max = 20, message = "计量单位（资产类别表unit）长度必须介于 1 和 20 之间")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Length(min = 1, max = 20, message = "使用状况（1使用中，2未使用，3不需用，4经营性租出，5愈龄）长度必须介于 1 和 20 之间")
    public String getUsingstatus() {
        return usingstatus;
    }

    public void setUsingstatus(String usingstatus) {
        this.usingstatus = usingstatus;
    }

    @Length(min = 0, max = 200, message = "备注长度必须介于 0 和 200 之间")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Length(min = 1, max = 100, message = "固定资产科目（科目id）长度必须介于 1 和 100 之间")
    public String getAssetAccountid() {
        return assetAccountid;
    }

    public void setAssetAccountid(String assetAccountid) {
        this.assetAccountid = assetAccountid;
    }

    @Length(min = 1, max = 100, message = "累计折旧科目（科目id）长度必须介于 1 和 100 之间")
    public String getAccudepAccountid() {
        return accudepAccountid;
    }

    public void setAccudepAccountid(String accudepAccountid) {
        this.accudepAccountid = accudepAccountid;
    }

    @Length(min = 1, max = 100, message = "固定资产减值准备科目（科目id）长度必须介于 1 和 100 之间")
    public String getPredevaluateAccountid() {
        return predevaluateAccountid;
    }

    public void setPredevaluateAccountid(String predevaluateAccountid) {
        this.predevaluateAccountid = predevaluateAccountid;
    }

    @Length(min = 1, max = 100, message = "折旧费用科目（科目id）长度必须介于 1 和 100 之间")
    public String getDepcostAccountid() {
        return depcostAccountid;
    }

    public void setDepcostAccountid(String depcostAccountid) {
        this.depcostAccountid = depcostAccountid;
    }

    public Integer getIsproject() {
        return isproject;
    }

    public void setIsproject(Integer isproject) {
        this.isproject = isproject;
    }

    @Length(min = 0, max = 100, message = "在建工程科目（科目id）长度必须介于 0 和 100 之间")
    public String getProjectAccountid() {
        return projectAccountid;
    }

    public void setProjectAccountid(String projectAccountid) {
        this.projectAccountid = projectAccountid;
    }

    @Length(min = 1, max = 100, message = "减值准备对方科目（科目id）长度必须介于 1 和 100 之间")
    public String getPredevaluateCoraccountid() {
        return predevaluateCoraccountid;
    }

    public void setPredevaluateCoraccountid(String predevaluateCoraccountid) {
        this.predevaluateCoraccountid = predevaluateCoraccountid;
    }

    @Length(min = 0, max = 100, message = "税金科目（科目id）长度必须介于 0 和 100 之间")
    public String getTaxAccountid() {
        return taxAccountid;
    }

    public void setTaxAccountid(String taxAccountid) {
        this.taxAccountid = taxAccountid;
    }

    @NotNull(message = "原值不能为空")
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @NotNull(message = "税率(%)不能为空")
    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    @NotNull(message = "税金不能为空")
    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    @NotNull(message = "累计折旧不能为空")
    public BigDecimal getAccudep() {
        return accudep;
    }

    public void setAccudep(BigDecimal accudep) {
        this.accudep = accudep;
    }

    @NotNull(message = "减值准备不能为空")
    public BigDecimal getPredevaluate() {
        return predevaluate;
    }

    public void setPredevaluate(BigDecimal predevaluate) {
        this.predevaluate = predevaluate;
    }

    @NotNull(message = "净值不能为空")
    public BigDecimal getNetvalue() {
        return netvalue;
    }

    public void setNetvalue(BigDecimal netvalue) {
        this.netvalue = netvalue;
    }

    @NotNull(message = "残值率(%)不能为空")
    public Float getSalvagerate() {
        return salvagerate;
    }

    public void setSalvagerate(Float salvagerate) {
        this.salvagerate = salvagerate;
    }

    @NotNull(message = "残值不能为空")
    public BigDecimal getSalvage() {
        return salvage;
    }

    public void setSalvage(BigDecimal salvage) {
        this.salvage = salvage;
    }

    @NotNull(message = "折旧方法（资产类别表depmethod）不能为空")
    public Integer getDepmethod() {
        return depmethod;
    }

    public void setDepmethod(Integer depmethod) {
        this.depmethod = depmethod;
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

    @NotNull(message = "月折旧率(%)不能为空")
    public Float getDeprate() {
        return deprate;
    }

    public void setDeprate(Float deprate) {
        this.deprate = deprate;
    }

    @NotNull(message = "月折旧额不能为空")
    public BigDecimal getDepamount() {
        return depamount;
    }

    public void setDepamount(BigDecimal depamount) {
        this.depamount = depamount;
    }

    public BigDecimal getAllworkloan() {
        return allworkloan;
    }

    public void setAllworkloan(BigDecimal allworkloan) {
        this.allworkloan = allworkloan;
    }

    @Length(min = 0, max = 80, message = "总工作量单位长度必须介于 0 和 80 之间")
    public String getWorkloanunit() {
        return workloanunit;
    }

    public void setWorkloanunit(String workloanunit) {
        this.workloanunit = workloanunit;
    }

    @Length(min = 0, max = 100, message = "新增原因（变动方式表id）长度必须介于 0 和 100 之间")
    public String getAddReason() {
        return addReason;
    }

    public void setAddReason(String addReason) {
        this.addReason = addReason;
    }

    @Length(min = 1, max = 100, message = "创建人id长度必须介于 1 和 100 之间")
    public String getCreaterid() {
        return createrid;
    }

    public void setCreaterid(String createrid) {
        this.createrid = createrid;
    }

    @NotNull(message = "卡片状态（1新增待审核，2新增已审核，3报废待审核，4报废已审核）不能为空")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Length(min = 0, max = 100, message = "报废原因长度必须介于 0 和 100 之间")
    public String getReduceReason() {
        return reduceReason;
    }

    public void setReduceReason(String reduceReason) {
        this.reduceReason = reduceReason;
    }

    public BigDecimal getLastyearaccudep() {
        return lastyearaccudep;
    }

    public void setLastyearaccudep(BigDecimal lastyearaccudep) {
        this.lastyearaccudep = lastyearaccudep;
    }

    public Integer getUsedmonth() {
        return usedmonth;
    }

    public void setUsedmonth(Integer usedmonth) {
        this.usedmonth = usedmonth;
    }

    public Integer getChangedmonth() {
        return changedmonth;
    }

    public void setChangedmonth(Integer changedmonth) {
        this.changedmonth = changedmonth;
    }

    @Length(min = 0, max = -1, message = "报废时间长度必须介于 0 和 -1 之间")
    public String getScrapDate() {
        return scrapDate;
    }

    public void setScrapDate(String scrapDate) {
        this.scrapDate = scrapDate;
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
        return "tfam_asset_histroy";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897356480";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "卡片历史表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
