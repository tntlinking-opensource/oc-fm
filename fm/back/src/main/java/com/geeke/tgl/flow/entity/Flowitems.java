package com.geeke.tgl.flow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.sys.entity.DictItem;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 现金流量项目Entity
 * @author
 * @version
 */

public class Flowitems extends DataEntity<Flowitems> {

    private static final long serialVersionUID = 1457254743943430686L;

    private String uqflowitemid; // 类别ID

    private String varcode; // 编码

    private String varname; // 名称

    private Flowtype uqflowtypeid; // 现金流量类别ID

    private String uqflowtypecode; // 现金流量类别编号（导入使用，非数据库字段）

    private Integer intstatus; // 状态(0停用,1新建,2启用)

    private String tenantId; // 公司id

    private DictItem direct; // 流向 0-流入  1-流出

    private Integer issystem; // 是否系统内置 1 - 是 0 不是

    // 构造方法
    public Flowitems() {
        super();
    }

    public Flowitems(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 100, message = "类别ID长度必须介于 1 和 100 之间")
    public String getUqflowitemid() {
        return uqflowitemid;
    }

    public void setUqflowitemid(String uqflowitemid) {
        this.uqflowitemid = uqflowitemid;
    }

    @Length(min = 1, max = 50, message = "编码长度必须介于 1 和 50 之间")
    public String getVarcode() {
        return varcode;
    }

    public void setVarcode(String varcode) {
        this.varcode = varcode;
    }

    @Length(min = 1, max = 100, message = "名称长度必须介于 1 和 100 之间")
    public String getVarname() {
        return varname;
    }

    public void setVarname(String varname) {
        this.varname = varname;
    }

    @NotNull(message = "现金流量类别ID不能为空")
    public Flowtype getUqflowtypeid() {
        return uqflowtypeid;
    }

    public void setUqflowtypeid(Flowtype uqflowtypeid) {
        this.uqflowtypeid = uqflowtypeid;
    }

    public String getUqflowtypecode() { return uqflowtypecode; }
    public void setUqflowtypecode(String uqflowtypecode) { this.uqflowtypecode = uqflowtypecode; }

    @NotNull(message = "状态(0停用,1新建,2启用)不能为空")
    public Integer getIntstatus() {
        return intstatus;
    }

    public void setIntstatus(Integer intstatus) {
        this.intstatus = intstatus;
    }

    @NotNull(message = "租户id不能为空")
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Length(min = 0, max = 255, message = "流向 0-流入  1-流出长度必须介于 0 和 255 之间")
    public DictItem getDirect() {
        return direct;
    }

    public void setDirect(DictItem direct) {
        this.direct = direct;
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
        return "tgl_flowitems";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943430686";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "现金流量项目";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
