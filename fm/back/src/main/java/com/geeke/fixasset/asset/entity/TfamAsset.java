package com.geeke.fixasset.asset.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.admin.entity.User;
import com.geeke.common.persistence.DataEntity;
import com.geeke.fixasset.category.entity.TfamCategory;
import com.geeke.fixasset.changeway.entity.TfamChangeWay;
import com.geeke.fixasset.depmethod.entity.TfamDepmethod;
import com.geeke.fixasset.nature.entity.TfamNature;
import com.geeke.fixasset.producer.entity.TfamSupplier;
import com.geeke.fixasset.status.entity.TfamStatus;
import com.geeke.fixasset.usingstatus.entity.TfamUsingstatus;
import com.geeke.org.entity.Department;
import com.geeke.vouchermanager.entity.TglAccounts;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * 卡片管理Entity
 * @author
 * @version
 */

public class TfamAsset extends DataEntity<TfamAsset> {

    private static final long serialVersionUID = 1633870636743041902L;

    private String year; // 业务年份

    private String month; // 业务月份

    private String cardCode; // 卡片编号（自增长）

    private String assetCode; // 资产编号

    private String businessDate; // 建卡日期

    private TfamNature nature; // 卡片性质（1待新增制证，2已新增制证，3待减少制证，4已减少制证）

    private TfamCategory category; // 资产类别（资产类别表id）

    private String assetName; // 资产名称

    private String spec; // 规格

    private String cardModel; // 型号

    private String place; // 所在地点

    private TfamSupplier producer; // 生产厂家

    private String companyid; // 公司ID 允许为空

    private Department usedeptid; // 使用部门（部门id）

    private User userid; // 责任人（用户id）

    private Integer num; // 数量

    private String unit; // 计量单位（资产类别表unit）

    private TfamUsingstatus usingstatus; // 使用状况（1使用中，2未使用，3不需用，4经营性租出，5愈龄，7损坏需维修，8设备已外借，9设备待报废，10损坏丢失）

    private String memo; // 备注

    private TglAccounts assetAccountid; // 固定资产科目（科目id）

    private TglAccounts accudepAccountid; // 累计折旧科目（科目id）

    private TglAccounts predevaluateAccountid; // 固定资产减值准备科目（科目id）

    private TglAccounts depcostAccountid; // 折旧费用科目（科目id）

    private Integer isproject; // 是否在建工程中转。1是0否

    private TglAccounts projectAccountid; // 在建工程科目（科目id）

    private TglAccounts predevaluateCoraccountid; // 减值准备对方科目（科目id）

    private TglAccounts taxAccountid; // 税金科目（科目id）

    private BigDecimal value; // 原值

    private BigDecimal rate; // 税率(%)

    private BigDecimal tax; // 税金

    private BigDecimal accudep; // 累计折旧

    private BigDecimal predevaluate; // 减值准备

    private BigDecimal netvalue; // 净值

    private BigDecimal salvagerate; // 残值率(%)

    private BigDecimal salvage; // 残值

    private TfamDepmethod depmethod; // 折旧方法（资产类别表depmethod）

    private Integer naturemonth; // 预计使用月数

    private Integer servicemonth; // 剩余使用月数

    private BigDecimal deprate; // 月折旧率(%)

    private BigDecimal depamount; // 月折旧额

    private BigDecimal allworkloan; // 总工作量

    private String workloanunit; // 总工作量单位

    private TfamChangeWay addReason; // 新增原因（变动方式表id）

    private String createrid; // 创建人id

    private TfamStatus status; // 卡片状态（1新增待审核，2新增已审核，3报废待审核，4报废已审核）

    private String reduceReason; // 报废原因

    private BigDecimal lastyearaccudep; // 上年累计折旧（双倍余额法用）

    private Integer usedmonth = 0; // 已使用月数

    private Integer changedmonth = 0; // 折旧方法变动时记录已使用月数

    private String scrapDate; // 报废日期

    private String companyId; // 公司id

    private String businessdept; // 所属事业部

    private String employeeno; // 负责人工号

    private String userAscription; // 员工隶属

    private String assetAscription; // 设备隶属

    private String macAddress; // MAC地址

    private String startupDate; // 购置日期（原启用日期）

    private String assetbrand; // 品牌

    private String enableDate; // 启用日期（新）

    private List<TfamDepsub> tfamDepsubList = Lists.newArrayList(); // 子表列表

    private List<TfamAssetTransfer> tfamAssetTransferList = Lists.newArrayList(); // 子表列表

    private List<TfamAccessory> tfamAccessoryList = Lists.newArrayList(); // 子表列表

    private List<TfamAsset2qrcode> tfamAsset2qrcodeList = Lists.newArrayList(); // 子表列表

    private List<TfamAssetHistroy> tfamAssetHistroyList = Lists.newArrayList(); // 子表列表

    // 构造方法
    public TfamAsset() {
        super();
    }

    public TfamAsset(String id) {
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
    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
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
    public TfamNature getNature() {
        return nature;
    }

    public void setNature(TfamNature nature) {
        this.nature = nature;
    }

    @NotNull(message = "资产类别（资产类别表id）不能为空")
    public TfamCategory getCategory() {
        return category;
    }

    public void setCategory(TfamCategory category) {
        this.category = category;
    }

    @Length(min = 1, max = 200, message = "资产名称长度必须介于 1 和 200 之间")
    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    @Length(min = 0, max = 800, message = "规格长度必须介于 0 和 800 之间")
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

    public TfamSupplier getProducer() {
        return producer;
    }

    public void setProducer(TfamSupplier producer) {
        this.producer = producer;
    }

    @Length(min = 0, max = 100, message = "公司ID 允许为空长度必须介于 0 和 100 之间")
    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public Department getUsedeptid() {
        return usedeptid;
    }

    public void setUsedeptid(Department usedeptid) {
        this.usedeptid = usedeptid;
    }

    public User getUserid() {
        return userid;
    }

    public void setUserid(User userid) {
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

    @NotNull(
        message = "使用状况（1使用中，2未使用，3不需用，4经营性租出，5愈龄，7损坏需维修，8设备已外借，9设备待报废，10损坏丢失）不能为空"
    )
    public TfamUsingstatus getUsingstatus() {
        return usingstatus;
    }

    public void setUsingstatus(TfamUsingstatus usingstatus) {
        this.usingstatus = usingstatus;
    }

    @Length(min = 0, max = 200, message = "备注长度必须介于 0 和 200 之间")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @NotNull(message = "固定资产科目（科目id）不能为空")
    public TglAccounts getAssetAccountid() {
        return assetAccountid;
    }

    public void setAssetAccountid(TglAccounts assetAccountid) {
        this.assetAccountid = assetAccountid;
    }

    @NotNull(message = "累计折旧科目（科目id）不能为空")
    public TglAccounts getAccudepAccountid() {
        return accudepAccountid;
    }

    public void setAccudepAccountid(TglAccounts accudepAccountid) {
        this.accudepAccountid = accudepAccountid;
    }

    @NotNull(message = "固定资产减值准备科目（科目id）不能为空")
    public TglAccounts getPredevaluateAccountid() {
        return predevaluateAccountid;
    }

    public void setPredevaluateAccountid(TglAccounts predevaluateAccountid) {
        this.predevaluateAccountid = predevaluateAccountid;
    }

    @NotNull(message = "折旧费用科目（科目id）不能为空")
    public TglAccounts getDepcostAccountid() {
        return depcostAccountid;
    }

    public void setDepcostAccountid(TglAccounts depcostAccountid) {
        this.depcostAccountid = depcostAccountid;
    }

    public Integer getIsproject() {
        return isproject;
    }

    public void setIsproject(Integer isproject) {
        this.isproject = isproject;
    }

    public TglAccounts getProjectAccountid() {
        return projectAccountid;
    }

    public void setProjectAccountid(TglAccounts projectAccountid) {
        this.projectAccountid = projectAccountid;
    }

    @NotNull(message = "减值准备对方科目（科目id）不能为空")
    public TglAccounts getPredevaluateCoraccountid() {
        return predevaluateCoraccountid;
    }

    public void setPredevaluateCoraccountid(TglAccounts predevaluateCoraccountid) {
        this.predevaluateCoraccountid = predevaluateCoraccountid;
    }

    public TglAccounts getTaxAccountid() {
        return taxAccountid;
    }

    public void setTaxAccountid(TglAccounts taxAccountid) {
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
    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
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
    public BigDecimal getSalvagerate() {
        return salvagerate;
    }

    public void setSalvagerate(BigDecimal salvagerate) {
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
    public TfamDepmethod getDepmethod() {
        return depmethod;
    }

    public void setDepmethod(TfamDepmethod depmethod) {
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
    public BigDecimal getDeprate() {
        return deprate;
    }

    public void setDeprate(BigDecimal deprate) {
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

    public TfamChangeWay getAddReason() {
        return addReason;
    }

    public void setAddReason(TfamChangeWay addReason) {
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
    public TfamStatus getStatus() {
        return status;
    }

    public void setStatus(TfamStatus status) {
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

    @Length(min = 0, max = -1, message = "报废日期长度必须介于 0 和 -1 之间")
    public String getScrapDate() {
        return scrapDate;
    }

    public void setScrapDate(String scrapDate) {
        this.scrapDate = scrapDate;
    }

    @Length(min = 1, max = 100, message = "公司id长度必须介于 1 和 100 之间")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Length(min = 0, max = 255, message = "所属事业部长度必须介于 0 和 255 之间")
    public String getBusinessdept() {
        return businessdept;
    }

    public void setBusinessdept(String businessdept) {
        this.businessdept = businessdept;
    }

    @Length(min = 0, max = 10, message = "负责人工号长度必须介于 0 和 10 之间")
    public String getEmployeeno() {
        return employeeno;
    }

    public void setEmployeeno(String employeeno) {
        this.employeeno = employeeno;
    }

    @Length(min = 0, max = 255, message = "员工隶属长度必须介于 0 和 255 之间")
    public String getUserAscription() {
        return userAscription;
    }

    public void setUserAscription(String userAscription) {
        this.userAscription = userAscription;
    }

    @Length(min = 0, max = 255, message = "设备隶属长度必须介于 0 和 255 之间")
    public String getAssetAscription() {
        return assetAscription;
    }

    public void setAssetAscription(String assetAscription) {
        this.assetAscription = assetAscription;
    }

    @Length(min = 0, max = 255, message = "MAC地址长度必须介于 0 和 255 之间")
    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @Length(min = 1, max = -1, message = "购置日期（原启用日期）长度必须介于 1 和 -1 之间")
    public String getStartupDate() {
        return startupDate;
    }

    public void setStartupDate(String startupDate) {
        this.startupDate = startupDate;
    }

    @Length(min = 0, max = 255, message = "品牌长度必须介于 0 和 255 之间")
    public String getAssetbrand() {
        return assetbrand;
    }

    public void setAssetbrand(String assetbrand) {
        this.assetbrand = assetbrand;
    }

    @Length(min = 0, max = -1, message = "启用日期（新）长度必须介于 0 和 -1 之间")
    public String getEnableDate() {
        return enableDate;
    }

    public void setEnableDate(String enableDate) {
        this.enableDate = enableDate;
    }

    public List<TfamDepsub> getTfamDepsubList() {
        return tfamDepsubList;
    }

    public void setTfamDepsubList(List<TfamDepsub> tfamDepsubList) {
        this.tfamDepsubList = tfamDepsubList;
    }

    public List<TfamAssetTransfer> getTfamAssetTransferList() {
        return tfamAssetTransferList;
    }

    public void setTfamAssetTransferList(List<TfamAssetTransfer> tfamAssetTransferList) {
        this.tfamAssetTransferList = tfamAssetTransferList;
    }

    public List<TfamAccessory> getTfamAccessoryList() {
        return tfamAccessoryList;
    }

    public void setTfamAccessoryList(List<TfamAccessory> tfamAccessoryList) {
        this.tfamAccessoryList = tfamAccessoryList;
    }

    public List<TfamAsset2qrcode> getTfamAsset2qrcodeList() {
        return tfamAsset2qrcodeList;
    }

    public void setTfamAsset2qrcodeList(List<TfamAsset2qrcode> tfamAsset2qrcodeList) {
        this.tfamAsset2qrcodeList = tfamAsset2qrcodeList;
    }

    public List<TfamAssetHistroy> getTfamAssetHistroyList() {
        return tfamAssetHistroyList;
    }

    public void setTfamAssetHistroyList(List<TfamAssetHistroy> tfamAssetHistroyList) {
        this.tfamAssetHistroyList = tfamAssetHistroyList;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tfam_asset";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1633870636743041902";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "卡片表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @Override
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
