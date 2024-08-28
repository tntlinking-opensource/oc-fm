package com.geeke.vouchermanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.vouchermanager.entity.TglLedger;
import com.google.common.collect.Lists;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 分户Entity
 * @author
 * @version
 */

@ApiModel(description = "生成get和set方法")
public class TglLedgetype extends DataEntity<TglLedgetype> {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1457254743943430949L;

    @ApiModelProperty("分户类别ID")
    private String uqledgetypeid; // 分户类别ID

    @ApiModelProperty("分户类别编码")
    private String varledgetypecode; // 分户类别编码

    @ApiModelProperty("分户类别名称")
    private String varledgetypename; // 分户类别名称

    @ApiModelProperty("上级ID")
    private String uqparentid; // 上级ID

    @ApiModelProperty("是否末级")
    private Integer intislastlevel; // 是否末级

    @ApiModelProperty("状态")
    private Integer intstatus; // 状态

    @ApiModelProperty("tenant_id")
    private String tenantId; // tenant_id

    private String code; // 自定义使用字段

    @ApiModelProperty("子表列表")
    private List<TglLedger> tglLedgerList = Lists.newArrayList(); // 子表列表

    // 构造方法
    public TglLedgetype() {
        super();
    }

    public TglLedgetype(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 40, message = "分户类别ID长度必须介于 1 和 40 之间")
    public String getUqledgetypeid() {
        return uqledgetypeid;
    }

    public void setUqledgetypeid(String uqledgetypeid) {
        this.uqledgetypeid = uqledgetypeid;
    }

    @Length(min = 0, max = 100, message = "分户类别编码长度必须介于 0 和 100 之间")
    public String getVarledgetypecode() {
        return varledgetypecode;
    }

    public void setVarledgetypecode(String varledgetypecode) {
        this.varledgetypecode = varledgetypecode;
    }

    @Length(min = 0, max = 100, message = "分户类别名称长度必须介于 0 和 100 之间")
    public String getVarledgetypename() {
        return varledgetypename;
    }

    public void setVarledgetypename(String varledgetypename) {
        this.varledgetypename = varledgetypename;
    }

    @Length(min = 0, max = 40, message = "上级ID长度必须介于 0 和 40 之间")
    public String getUqparentid() {
        return uqparentid;
    }

    public void setUqparentid(String uqparentid) {
        this.uqparentid = uqparentid;
    }

    public Integer getIntislastlevel() {
        return intislastlevel;
    }

    public void setIntislastlevel(Integer intislastlevel) {
        this.intislastlevel = intislastlevel;
    }

    public Integer getIntstatus() {
        return intstatus;
    }

    public void setIntstatus(Integer intstatus) {
        this.intstatus = intstatus;
    }

    @Length(min = 1, max = 100, message = "tenant_id长度必须介于 1 和 100 之间")
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public List<TglLedger> getTglLedgerList() {
        return tglLedgerList;
    }

    public void setTglLedgerList(List<TglLedger> tglLedgerList) {
        this.tglLedgerList = tglLedgerList;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tgl_ledgetype";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943430949";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "分户类别表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return false;
    }
}
