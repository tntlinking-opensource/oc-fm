package com.geeke.vouchermanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.vouchermanager.entity.TglAccounts;
import com.geeke.vouchermanager.entity.TglLedgetype;
import javax.validation.constraints.NotNull;

/**
 * 分户Entity
 * @author
 * @version
 */

public class AccountLedgetype extends DataEntity<AccountLedgetype> {

    private static final long serialVersionUID = 1457254743943431146L;

    private TglAccounts uqaccountid; // 科目ID

    private TglLedgetype uqledgetypeid; // 分户类别ID

    // 构造方法
    public AccountLedgetype() {
        super();
    }

    public AccountLedgetype(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "科目ID不能为空")
    public TglAccounts getUqaccountid() {
        return uqaccountid;
    }

    public void setUqaccountid(TglAccounts uqaccountid) {
        this.uqaccountid = uqaccountid;
    }

    @NotNull(message = "分户类别ID不能为空")
    public TglLedgetype getUqledgetypeid() {
        return uqledgetypeid;
    }

    public void setUqledgetypeid(TglLedgetype uqledgetypeid) {
        this.uqledgetypeid = uqledgetypeid;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tgl_account_ledgetype";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943431146";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "科目分户核算关系表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return false;
    }
}
