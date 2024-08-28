package com.geeke.cardasset.credit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.cardasset.credit.entity.OrgDepartmentEntry;
import com.geeke.cardasset.credit.entity.OrgDepartmentEntry;
import com.geeke.cardasset.credit.entity.SysUserEntry;
import com.geeke.cardasset.credit.entity.SysUserEntry;
import com.geeke.cardasset.credit.entity.TfamAlterEntry;
import com.geeke.cardasset.credit.entity.TfamAlterEntry;
import com.geeke.cardasset.credit.entity.TfamCategoryEntry;
import com.geeke.cardasset.credit.entity.TfamCategoryEntry;
import com.geeke.cardasset.credit.entity.TfamChangeWayEntry;
import com.geeke.cardasset.credit.entity.TfamChangeWayEntry;
import com.geeke.cardasset.credit.entity.TfamDepmethodEntry;
import com.geeke.cardasset.credit.entity.TfamDepmethodEntry;
import com.geeke.cardasset.credit.entity.TfamNatureEntry;
import com.geeke.cardasset.credit.entity.TfamNatureEntry;
import com.geeke.cardasset.credit.entity.TfamUsingstatusEntry;
import com.geeke.cardasset.credit.entity.TfamUsingstatusEntry;
import com.geeke.cardasset.credit.entity.TglVoucherMainsEntry;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 资产入账Entity
 * @author
 * @version
 */

public class TfamAssetEntry extends DataEntity<TfamAssetEntry> {

    private static final long serialVersionUID = 1641034040897358087L;

    private TfamAlterEntry tfamAlterEntry;
    private String year; // 业务年份

    private String month; // 业务月份

    private String cardCode; // 卡片编号（自增长）

    private String assetCode; // 资产编号

    private String businessDate; // 建卡日期

    private TfamNatureEntry nature; // tfam_nature 卡片性质（1待新增制证，2已新增制证，3待减少制证，4已减少制证）

    private TfamCategoryEntry category; // tfam_category 资产类别（资产类别表id）

    private String assetName; // 资产名称

    private String spec; // 规格

    private String cardModel; // 型号

    private String place; // 所在地点

    private String producer; // tfam_supplier 生产厂家

    private String companyid; // org_company 公司ID 允许为空

    private OrgDepartmentEntry usedeptid; // org_department 使用部门（部门id）

    private SysUserEntry userid; // sys_user 责任人（用户id）

    private Integer num; // 数量

    private String unit; // 计量单位（手动输入）

    private TfamUsingstatusEntry usingstatus; // tfam_usingstatus 使用状况（1使用中，2未使用，3不需用，4经营性租出，5愈龄，7损坏需维修，8设备已外。借，9设备待报废，10损坏丢失）

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

    private BigDecimal rate; // 税率(%)

    private BigDecimal tax; // 税金

    private BigDecimal accudep; // 累计折旧

    private BigDecimal predevaluate; // 减值准备

    private BigDecimal netvalue; // 净值

    private BigDecimal salvagerate; // 残值率(%)

    private BigDecimal salvage; // 残值

    private TfamDepmethodEntry depmethod; // tfam_depmethod  折旧方法（资产类别表depmethod）

    private Integer naturemonth; // 预计使用月数

    private Integer servicemonth; // 剩余使用月数

    private BigDecimal deprate; // 月折旧率(%)

    private BigDecimal depamount; // 月折旧额

    private BigDecimal allworkloan; // 总工作量

    private String workloanunit; // 总工作量单位

    private TfamChangeWayEntry addReason; // tfam_change_way  新增原因（变动方式表id）

    private SysUserEntry createrid; // 创建人id

    private Integer status = 1; // tfam_status  卡片状态（1新增待审核，2新增已审核，3报废待审核，4报废已审核）

    private TfamChangeWayEntry reduceReason; // 报废原因

    private BigDecimal lastyearaccudep; // 上年累计折旧（双倍余额法用）

    private Integer usedmonth = 0; // 已使用月数

    private Integer changedmonth = 0; // 折旧方法变动时记录已使用月数

    private String scrapDate; // 报废日期

    private String companyId; // 公司id

    private String businessdept; // 所属事业部

    private SysUserEntry employeeno; // 负责人工号

    private String userAscription; // 员工隶属

    private String assetAscription; // 设备隶属

    private String macAddress; // MAC地址

    private String startupDate; // 购置日期（原启用日期）

    private String assetbrand; // 品牌

    private String enableDate; // 启用日期（新）

    private List<OrgDepartmentEntry> orgDepartmentEntryList = Lists.newArrayList(); // 子表列表

    private List<TfamNatureEntry> tfamNatureEntryList = Lists.newArrayList(); // 子表列表

    private List<TfamCategoryEntry> tfamCategoryEntryList = Lists.newArrayList(); // 子表列表

    private List<SysUserEntry> sysUserEntryList = Lists.newArrayList(); // 子表列表

    private List<TfamUsingstatusEntry> tfamUsingstatusEntryList = Lists.newArrayList(); // 子表列表

    private List<TfamDepmethodEntry> tfamDepmethodEntryList = Lists.newArrayList(); // 子表列表

    private List<TfamChangeWayEntry> tfamChangeWayEntryList = Lists.newArrayList(); // 子表列表

    private List<TfamAlterEntry> tfamAlterEntryList = Lists.newArrayList(); // 子表列表

    private List<TglVoucherMainsEntry> tglVoucherMainsEntryList = Lists.newArrayList(); // 子表列表

    // 构造方法
    public TfamAssetEntry() {
        super();
    }

    public TfamAssetEntry(String id) {
        super(id);
    }

    // 生成get和set方法


    public TfamAlterEntry getTfamAlterEntry() {
        return tfamAlterEntry;
    }

    public void setTfamAlterEntry(TfamAlterEntry tfamAlterEntry) {
        this.tfamAlterEntry = tfamAlterEntry;
    }

    @Length(min = 0, max = 4, message = "业务年份长度必须介于 0 和 4 之间")
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Length(min = 0, max = 2, message = "业务月份长度必须介于 0 和 2 之间")
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Length(min = 1, max = 20, message = "卡片编号（自增长）长度必须介于 1 和 20 之间")
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

    @Length(min = 0, max = -1, message = "建卡日期长度必须介于 0 和 -1 之间")
    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    @NotNull(message = "tfam_nature 卡片性质（1待新增制证，2已新增制证，3待减少制证，4已减少制证）不能为空")
    public TfamNatureEntry getNature() {
        return nature;
    }

    public void setNature(TfamNatureEntry nature) {
        this.nature = nature;
    }

    @NotNull(message = "tfam_category 资产类别（资产类别表id）不能为空")
    public TfamCategoryEntry getCategory() {
        return category;
    }

    public void setCategory(TfamCategoryEntry category) {
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

    @Length(min = 0, max = 80, message = "tfam_supplier 生产厂家长度必须介于 0 和 80 之间")
    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    @Length(min = 0, max = 100, message = "org_company 公司ID 允许为空长度必须介于 0 和 100 之间")
    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public OrgDepartmentEntry getUsedeptid() {
        return usedeptid;
    }

    public void setUsedeptid(OrgDepartmentEntry usedeptid) {
        this.usedeptid = usedeptid;
    }

    public SysUserEntry getUserid() {
        return userid;
    }

    public void setUserid(SysUserEntry userid) {
        this.userid = userid;
    }

    @NotNull(message = "数量不能为空")
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Length(min = 1, max = 20, message = "计量单位（手动输入）长度必须介于 1 和 20 之间")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @NotNull(
        message = "tfam_usingstatus 使用状况（1使用中，2未使用，3不需用，4经营性租出，5愈龄，7损坏需维修，8设备已外。借，9设备待报废，10损坏丢失）不能为空"
    )
    public TfamUsingstatusEntry getUsingstatus() {
        return usingstatus;
    }

    public void setUsingstatus(TfamUsingstatusEntry usingstatus) {
        this.usingstatus = usingstatus;
    }

    @Length(min = 0, max = 200, message = "备注长度必须介于 0 和 200 之间")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Length(min = 0, max = 100, message = "固定资产科目（科目id）长度必须介于 0 和 100 之间")
    public String getAssetAccountid() {
        return assetAccountid;
    }

    public void setAssetAccountid(String assetAccountid) {
        this.assetAccountid = assetAccountid;
    }

    @Length(min = 0, max = 100, message = "累计折旧科目（科目id）长度必须介于 0 和 100 之间")
    public String getAccudepAccountid() {
        return accudepAccountid;
    }

    public void setAccudepAccountid(String accudepAccountid) {
        this.accudepAccountid = accudepAccountid;
    }

    @Length(min = 0, max = 100, message = "固定资产减值准备科目（科目id）长度必须介于 0 和 100 之间")
    public String getPredevaluateAccountid() {
        return predevaluateAccountid;
    }

    public void setPredevaluateAccountid(String predevaluateAccountid) {
        this.predevaluateAccountid = predevaluateAccountid;
    }

    @Length(min = 0, max = 100, message = "折旧费用科目（科目id）长度必须介于 0 和 100 之间")
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

    @Length(min = 0, max = 100, message = "减值准备对方科目（科目id）长度必须介于 0 和 100 之间")
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

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getAccudep() {
        return accudep;
    }

    public void setAccudep(BigDecimal accudep) {
        this.accudep = accudep;
    }

    public BigDecimal getPredevaluate() {
        return predevaluate;
    }

    public void setPredevaluate(BigDecimal predevaluate) {
        this.predevaluate = predevaluate;
    }

    public BigDecimal getNetvalue() {
        return netvalue;
    }

    public void setNetvalue(BigDecimal netvalue) {
        this.netvalue = netvalue;
    }

    public BigDecimal getSalvagerate() {
        return salvagerate;
    }

    public void setSalvagerate(BigDecimal salvagerate) {
        this.salvagerate = salvagerate;
    }

    public BigDecimal getSalvage() {
        return salvage;
    }

    public void setSalvage(BigDecimal salvage) {
        this.salvage = salvage;
    }

    public TfamDepmethodEntry getDepmethod() {
        return depmethod;
    }

    public void setDepmethod(TfamDepmethodEntry depmethod) {
        this.depmethod = depmethod;
    }

    public Integer getNaturemonth() {
        return naturemonth;
    }

    public void setNaturemonth(Integer naturemonth) {
        this.naturemonth = naturemonth;
    }

    public Integer getServicemonth() {
        return servicemonth;
    }

    public void setServicemonth(Integer servicemonth) {
        this.servicemonth = servicemonth;
    }

    public BigDecimal getDeprate() {
        return deprate;
    }

    public void setDeprate(BigDecimal deprate) {
        this.deprate = deprate;
    }

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

    public TfamChangeWayEntry getAddReason() {
        return addReason;
    }

    public void setAddReason(TfamChangeWayEntry addReason) {
        this.addReason = addReason;
    }

    public SysUserEntry getCreaterid() {
        return createrid;
    }

    public void setCreaterid(SysUserEntry createrid) {
        this.createrid = createrid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public TfamChangeWayEntry getReduceReason() {
        return reduceReason;
    }

    public void setReduceReason(TfamChangeWayEntry reduceReason) {
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

    @Length(min = 0, max = 100, message = "公司id长度必须介于 0 和 100 之间")
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

    public SysUserEntry getEmployeeno() {
        return employeeno;
    }

    public void setEmployeeno(SysUserEntry employeeno) {
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

    @Length(min = 0, max = -1, message = "购置日期（原启用日期）长度必须介于 0 和 -1 之间")
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

    public List<OrgDepartmentEntry> getOrgDepartmentEntryList() {
        return orgDepartmentEntryList;
    }

    public void setOrgDepartmentEntryList(List<OrgDepartmentEntry> orgDepartmentEntryList) {
        this.orgDepartmentEntryList = orgDepartmentEntryList;
    }

    public List<TfamNatureEntry> getTfamNatureEntryList() {
        return tfamNatureEntryList;
    }

    public void setTfamNatureEntryList(List<TfamNatureEntry> tfamNatureEntryList) {
        this.tfamNatureEntryList = tfamNatureEntryList;
    }

    public List<TfamCategoryEntry> getTfamCategoryEntryList() {
        return tfamCategoryEntryList;
    }

    public void setTfamCategoryEntryList(List<TfamCategoryEntry> tfamCategoryEntryList) {
        this.tfamCategoryEntryList = tfamCategoryEntryList;
    }

    public List<SysUserEntry> getSysUserEntryList() {
        return sysUserEntryList;
    }

    public void setSysUserEntryList(List<SysUserEntry> sysUserEntryList) {
        this.sysUserEntryList = sysUserEntryList;
    }

    public List<TfamUsingstatusEntry> getTfamUsingstatusEntryList() {
        return tfamUsingstatusEntryList;
    }

    public void setTfamUsingstatusEntryList(List<TfamUsingstatusEntry> tfamUsingstatusEntryList) {
        this.tfamUsingstatusEntryList = tfamUsingstatusEntryList;
    }

    public List<TfamDepmethodEntry> getTfamDepmethodEntryList() {
        return tfamDepmethodEntryList;
    }

    public void setTfamDepmethodEntryList(List<TfamDepmethodEntry> tfamDepmethodEntryList) {
        this.tfamDepmethodEntryList = tfamDepmethodEntryList;
    }

    public List<TfamChangeWayEntry> getTfamChangeWayEntryList() {
        return tfamChangeWayEntryList;
    }

    public void setTfamChangeWayEntryList(List<TfamChangeWayEntry> tfamChangeWayEntryList) {
        this.tfamChangeWayEntryList = tfamChangeWayEntryList;
    }

    public List<TfamAlterEntry> getTfamAlterEntryList() {
        return tfamAlterEntryList;
    }

    public void setTfamAlterEntryList(List<TfamAlterEntry> tfamAlterEntryList) {
        this.tfamAlterEntryList = tfamAlterEntryList;
    }

    public List<TglVoucherMainsEntry> getTglVoucherMainsEntryList() {
        return tglVoucherMainsEntryList;
    }

    public void setTglVoucherMainsEntryList(List<TglVoucherMainsEntry> tglVoucherMainsEntryList) {
        this.tglVoucherMainsEntryList = tglVoucherMainsEntryList;
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
        return "1641034040897358087";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "卡片表入账使用";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
