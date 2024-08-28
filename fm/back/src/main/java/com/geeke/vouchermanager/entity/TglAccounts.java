package com.geeke.vouchermanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 会计科目管理Entity
 * @author
 * @version
 */

@ApiModel(description = "生成get和set方法")
public class TglAccounts extends DataEntity<TglAccounts> {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1457254743943430348L;

    @ApiModelProperty("科目id")
    private String uqaccountid; // 科目id

    @ApiModelProperty("科目套id")
    private String uqaccountsetid; // 科目套id

    @ApiModelProperty("科目编码")
    private String varaccountcode; // 科目编码

    @ApiModelProperty("科目全编码")
    private String varaccountfullcode; // 科目全编码

    @ApiModelProperty("科目名称")
    private String varaccountname; // 科目名称

    @ApiModelProperty("科目属性")
    private String intproperty; // 科目属性
     @ApiModelProperty("科目属性名称")
    private String intpropertyno; // 科目属性名称

    @ApiModelProperty("科目类别")
    private String uqtypeid; // 科目类别
    @ApiModelProperty("科目类别名称")
    private String uqtypeidno; // 科目类别
    @ApiModelProperty("外币id")
    private String uqforeigncurrid; // 外币id
     @ApiModelProperty("外币id名称")
    private String uqforeigncurridno; // 外币id

    @ApiModelProperty("计量单位")
    private String varmeasure; // 计量单位
    @ApiModelProperty("计量单位名称")
    private String varmeasureno; // 计量单位名称
    @ApiModelProperty("是否分户核算")
    private Integer intisledge; // 是否分户核算
    // @ApiModelProperty("是否分户核算")
    private String uqledgetypeids; // 分户的id集合
    private String uqledgetypenames; // 分户的id集合

    @ApiModelProperty("是否分户交叉查询")
    private Integer intiscrossledge; // 是否分户交叉查询

    @ApiModelProperty("追朔科目ID")
    private String uqpreaccountid; // 追朔科目ID

    @ApiModelProperty("父级科目ID")
    private TglAccounts uqparentid; // 父级科目ID

    @ApiModelProperty("是否为末级（0不是，1是）")
    private String intislastlevel; // 是否为末级（0不是，1是）

    @ApiModelProperty("科目等级")
    private String intlevel; // 科目等级

    @ApiModelProperty("启停(0停用,1新建,2启用)")
    private String intflag; // 启停(0停用,1新建,2启用)

    @ApiModelProperty("科目全名称")
    private String varaccountfullname; // 科目全名称

    @ApiModelProperty("是否需要现金流量（0不需要，1可要可不要，2必须要）")
    private String intisflow = "0"; // 是否需要现金流量（0不需要，1可要可不要，2必须要）
    @ApiModelProperty("是否需要现金流量（0不需要，1可要可不要，2必须要）")
    private String intisflowno = "不需要"; // 是否需要现金流量（0不需要，1可要可不要，2必须要）
    @ApiModelProperty("tenant_id")
    private String tenantId; // tenant_id

    @ApiModelProperty("余额方向（0-借，1-贷）")
    private String isbalance; // 余额方向（0-借，1-贷）

    // 构造方法
    public TglAccounts() {
        super();
    }

    public TglAccounts(String id) {
        super(id);
    }

    // 生成get和set方法

    public String getUqaccountid() {
        return uqaccountid;
    }

    public void setUqaccountid(String uqaccountid) {
        this.uqaccountid = uqaccountid;
    }

    @Length(min = 1, max = 40, message = "科目套id长度必须介于 1 和 40 之间")
    public String getUqaccountsetid() {
        return uqaccountsetid;
    }

    public void setUqaccountsetid(String uqaccountsetid) {
        this.uqaccountsetid = uqaccountsetid;
    }

    @Length(min = 1, max = 100, message = "科目编码长度必须介于 1 和 100 之间")
    public String getVaraccountcode() {
        return varaccountcode;
    }

    public String getUqledgetypeids() {
        return uqledgetypeids;
    }

    public void setUqledgetypeids(String uqledgetypeids) {
        this.uqledgetypeids = uqledgetypeids;
    }

    public String getUqledgetypenames() {
        return uqledgetypenames;
    }

    public void setUqledgetypenames(String uqledgetypenames) {
        this.uqledgetypenames = uqledgetypenames;
    }

    public void setVaraccountcode(String varaccountcode) {
        this.varaccountcode = varaccountcode;
    }

    @Length(min = 1, max = 250, message = "科目全编码长度必须介于 1 和 250 之间")
    public String getVaraccountfullcode() {
        return varaccountfullcode;
    }

    public void setVaraccountfullcode(String varaccountfullcode) {
        this.varaccountfullcode = varaccountfullcode;
    }

    @Length(min = 1, max = 100, message = "科目名称长度必须介于 1 和 100 之间")
    public String getVaraccountname() {
        return varaccountname;
    }

    public void setVaraccountname(String varaccountname) {
        this.varaccountname = varaccountname;
    }

    @NotNull(message = "科目属性不能为空")
    public String getIntproperty() {
        return intproperty;
    }

    public void setIntproperty(String intproperty) {
        this.intproperty = intproperty;
    }

    @Length(min = 0, max = 40, message = "科目类别长度必须介于 0 和 40 之间")
    public String getUqtypeid() {
        return uqtypeid;
    }

    public void setUqtypeid(String uqtypeid) {
        this.uqtypeid = uqtypeid;
    }

    @Length(min = 0, max = 40, message = "外币id长度必须介于 0 和 40 之间")
    public String getUqforeigncurrid() {
        return uqforeigncurrid;
    }

    public void setUqforeigncurrid(String uqforeigncurrid) {
        this.uqforeigncurrid = uqforeigncurrid;
    }

    @Length(min = 0, max = 100, message = "计量单位长度必须介于 0 和 100 之间")
    public String getVarmeasure() {
        return varmeasure;
    }

    public void setVarmeasure(String varmeasure) {
        this.varmeasure = varmeasure;
    }

    public Integer getIntisledge() {
        return intisledge;
    }

    public void setIntisledge(Integer intisledge) {
        this.intisledge = intisledge;
    }

    public Integer getIntiscrossledge() {
        return intiscrossledge;
    }

    public void setIntiscrossledge(Integer intiscrossledge) {
        this.intiscrossledge = intiscrossledge;
    }

    @Length(min = 0, max = 40, message = "追朔科目ID长度必须介于 0 和 40 之间")
    public String getUqpreaccountid() {
        return uqpreaccountid;
    }

    public void setUqpreaccountid(String uqpreaccountid) {
        this.uqpreaccountid = uqpreaccountid;
    }

    @NotNull(message = "父级科目ID不能为空")
    public TglAccounts getUqparentid() {
        return uqparentid;
    }

    public void setUqparentid(TglAccounts uqparentid) {
        this.uqparentid = uqparentid;
    }

    @NotNull(message = "是否为末级（0不是，1是）不能为空")
    public String getIntislastlevel() {
        return intislastlevel;
    }

    public void setIntislastlevel(String intislastlevel) {
        this.intislastlevel = intislastlevel;
    }

    @NotNull(message = "科目等级不能为空")
    public String getIntlevel() {
        return intlevel;
    }

    public void setIntlevel(String intlevel) {
        this.intlevel = intlevel;
    }

    @NotNull(message = "启停(0停用,1新建,2启用)不能为空")
    public String getIntflag() {
        return intflag;
    }

    public void setIntflag(String intflag) {
        this.intflag = intflag;
    }

    @Length(min = 0, max = 1000, message = "科目全名称长度必须介于 0 和 1000 之间")
    public String getVaraccountfullname() {
        return varaccountfullname;
    }

    public void setVaraccountfullname(String varaccountfullname) {
        this.varaccountfullname = varaccountfullname;
    }

    public String getIntisflow() {
        return intisflow;
    }

    public void setIntisflow(String intisflow) {
        this.intisflow = intisflow;
    }

    @Length(min = 1, max = 100, message = "tenant_id长度必须介于 1 和 100 之间")
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getIsbalance() {
        return isbalance;
    }

    public void setIsbalance(String isbalance) {
        this.isbalance = isbalance;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tgl_accounts";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943430348";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "会计科目表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }

    public String getIntpropertyno() {
        return intpropertyno;
    }

    public void setIntpropertyno(String intpropertyno) {
        this.intpropertyno = intpropertyno;
    }

    public String getUqtypeidno() {
        return uqtypeidno;
    }

    public void setUqtypeidno(String uqtypeidno) {
        this.uqtypeidno = uqtypeidno;
    }

    public String getUqforeigncurridno() {
        return uqforeigncurridno;
    }

    public void setUqforeigncurridno(String uqforeigncurridno) {
        this.uqforeigncurridno = uqforeigncurridno;
    }

    public String getVarmeasureno() {
        return varmeasureno;
    }

    public void setVarmeasureno(String varmeasureno) {
        this.varmeasureno = varmeasureno;
    }

    public String getIntisflowno() {
        return intisflowno;
    }

    public void setIntisflowno(String intisflowno) {
        this.intisflowno = intisflowno;
    }
}
