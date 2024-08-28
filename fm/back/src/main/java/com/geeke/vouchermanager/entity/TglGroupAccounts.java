package com.geeke.vouchermanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 制证Entity
 * @author
 * @version
 */

@ApiModel(description = "生成get和set方法")
public class TglGroupAccounts extends DataEntity<TglGroupAccounts> {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1457254743943432962L;

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
    private Integer intproperty; // 科目属性

    @ApiModelProperty("科目类别")
    private String uqtypeid; // 科目类别

    @ApiModelProperty("外币id")
    private String uqforeigncurrid; // 外币id

    @ApiModelProperty("计量单位")
    private String varmeasure; // 计量单位

    @ApiModelProperty("是否分户核算")
    private Integer intisledge; // 是否分户核算

    @ApiModelProperty("是否分户交叉查询")
    private Integer intiscrossledge; // 是否分户交叉查询

    @ApiModelProperty("追朔科目ID")
    private String uqpreaccountid; // 追朔科目ID

    @ApiModelProperty("父级科目ID")
    private String uqparentid; // 父级科目ID

    @ApiModelProperty("是否为末级（0不是，1是）")
    private Integer intislastlevel; // 是否为末级（0不是，1是）

    @ApiModelProperty("科目等级")
    private Integer intlevel; // 科目等级

    @ApiModelProperty("启停(0停用,1新建,2启用)")
    private Integer intflag; // 启停(0停用,1新建,2启用)

    @ApiModelProperty("科目全名称")
    private String varaccountfullname; // 科目全名称

    @ApiModelProperty("是否需要现金流量（0不需要，1可要可不要，2必须要）")
    private Integer intisflow = 0; // 是否需要现金流量（0不需要，1可要可不要，2必须要）

    @ApiModelProperty("group_id")
    private String groupId; // group_id

    @ApiModelProperty("余额方向（0-借，1-贷）")
    private Integer isbalance; // 余额方向（0-借，1-贷）

    // 构造方法
    public TglGroupAccounts() {
        super();
    }

    public TglGroupAccounts(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 40, message = "科目id长度必须介于 1 和 40 之间")
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
    public Integer getIntproperty() {
        return intproperty;
    }

    public void setIntproperty(Integer intproperty) {
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

    @Length(min = 1, max = 40, message = "父级科目ID长度必须介于 1 和 40 之间")
    public String getUqparentid() {
        return uqparentid;
    }

    public void setUqparentid(String uqparentid) {
        this.uqparentid = uqparentid;
    }

    @NotNull(message = "是否为末级（0不是，1是）不能为空")
    public Integer getIntislastlevel() {
        return intislastlevel;
    }

    public void setIntislastlevel(Integer intislastlevel) {
        this.intislastlevel = intislastlevel;
    }

    @NotNull(message = "科目等级不能为空")
    public Integer getIntlevel() {
        return intlevel;
    }

    public void setIntlevel(Integer intlevel) {
        this.intlevel = intlevel;
    }

    @NotNull(message = "启停(0停用,1新建,2启用)不能为空")
    public Integer getIntflag() {
        return intflag;
    }

    public void setIntflag(Integer intflag) {
        this.intflag = intflag;
    }

    @Length(min = 0, max = 1000, message = "科目全名称长度必须介于 0 和 1000 之间")
    public String getVaraccountfullname() {
        return varaccountfullname;
    }

    public void setVaraccountfullname(String varaccountfullname) {
        this.varaccountfullname = varaccountfullname;
    }

    public Integer getIntisflow() {
        return intisflow;
    }

    public void setIntisflow(Integer intisflow) {
        this.intisflow = intisflow;
    }

    @Length(min = 1, max = 100, message = "group_id长度必须介于 1 和 100 之间")
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getIsbalance() {
        return isbalance;
    }

    public void setIsbalance(Integer isbalance) {
        this.isbalance = isbalance;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tgl_group_accounts";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943432962";
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
        return false;
    }
}
