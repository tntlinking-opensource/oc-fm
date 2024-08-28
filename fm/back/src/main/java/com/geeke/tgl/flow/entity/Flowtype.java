package com.geeke.tgl.flow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.TreeEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 现金流量类别Entity
 * @author
 * @version
 */

public class Flowtype extends TreeEntity<Flowtype> {

    private static final long serialVersionUID = 1457254743943430658L;

    private String uqflowtypeid; // 类别ID

    private String varcode; // 编码

    private String varname; // 名称

    private Flowtype uqparentid; // 上级ID

    private String varfullcode; // 全编码

    private String varfullname; // 全名称

    private Integer intlevel; // 级次

    private Integer intislastlevel; // 是否末级（0：否，1：是）

    private String tenantId; // 公司ID

    private Integer issystem; // 是否系统内置 1- 是 0 不是

    // 构造方法
    public Flowtype() {
        super();
    }

    public Flowtype(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 100, message = "类别ID长度必须介于 1 和 100 之间")
    public String getUqflowtypeid() {
        return uqflowtypeid;
    }

    public void setUqflowtypeid(String uqflowtypeid) {
        this.uqflowtypeid = uqflowtypeid;
    }

    @Length(min = 1, max = 50, message = "编码长度必须介于 1 和 50 之间")
    public String getVarcode() {
        return varcode;
    }

    public void setVarcode(String varcode) {
        this.varcode = varcode;
    }

    @Length(min = 1, max = 50, message = "名称长度必须介于 1 和 50 之间")
    public String getVarname() {
        return varname;
    }

    public void setVarname(String varname) {
        this.varname = varname;
    }

    @NotNull(message = "上级ID不能为空")
    public Flowtype getUqparentid() {
        return uqparentid;
    }

    public void setUqparentid(Flowtype uqparentid) {
        this.uqparentid = uqparentid;
    }

    @Length(min = 1, max = 500, message = "全编码长度必须介于 1 和 500 之间")
    public String getVarfullcode() {
        return varfullcode;
    }

    public void setVarfullcode(String varfullcode) {
        this.varfullcode = varfullcode;
    }

    @Length(min = 1, max = 500, message = "全名称长度必须介于 1 和 500 之间")
    public String getVarfullname() {
        return varfullname;
    }

    public void setVarfullname(String varfullname) {
        this.varfullname = varfullname;
    }

    @NotNull(message = "级次不能为空")
    public Integer getIntlevel() {
        return intlevel;
    }

    public void setIntlevel(Integer intlevel) {
        this.intlevel = intlevel;
    }

    @NotNull(message = "是否末级（0：否，1：是）不能为空")
    public Integer getIntislastlevel() {
        return intislastlevel;
    }

    public void setIntislastlevel(Integer intislastlevel) {
        this.intislastlevel = intislastlevel;
    }

    @NotNull(message = "公司ID不能为空")
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getIssystem() {
        return issystem;
    }

    public void setIssystem(Integer issystem) {
        this.issystem = issystem;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tgl_flowtype";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943430658";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "现金流量类别";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
