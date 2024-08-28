package com.geeke.vouchermanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 分户明细模板表Entity
 * @author
 * @version
 */

public class TglLedgerModel extends DataEntity<TglLedgerModel> {

    private static final long serialVersionUID = 1538459003414151174L;

    private String ledgertypename; // 分户类别名称

    private String ledgeritemcode; // 分户项目编码

    private String ledgeritemname; // 分户项目名称

    private String parentcode; // 上级项目编码

    private String tenantId; // tenant_id

    // 构造方法
    public TglLedgerModel() {
        super();
    }

    public TglLedgerModel(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 100, message = "分户类别名称长度必须介于 1 和 100 之间")
    public String getLedgertypename() {
        return ledgertypename;
    }

    public void setLedgertypename(String ledgertypename) {
        this.ledgertypename = ledgertypename;
    }

    @Length(min = 1, max = 100, message = "分户项目编码长度必须介于 1 和 100 之间")
    public String getLedgeritemcode() {
        return ledgeritemcode;
    }

    public void setLedgeritemcode(String ledgeritemcode) {
        this.ledgeritemcode = ledgeritemcode;
    }

    @Length(min = 1, max = 100, message = "分户项目名称长度必须介于 1 和 100 之间")
    public String getLedgeritemname() {
        return ledgeritemname;
    }

    public void setLedgeritemname(String ledgeritemname) {
        this.ledgeritemname = ledgeritemname;
    }

    @Length(min = 1, max = 100, message = "上级项目编码长度必须介于 1 和 100 之间")
    public String getParentcode() {
        return parentcode;
    }

    public void setParentcode(String parentcode) {
        this.parentcode = parentcode;
    }

    @Length(min = 1, max = 100, message = "tenant_id长度必须介于 1 和 100 之间")
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tgl_ledger_model";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1538459003414151174";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "分户明细模板表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return false;
    }
}
