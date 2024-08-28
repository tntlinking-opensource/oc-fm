package com.geeke.tgl.tmp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 利润结转科目配置Entity
 * @author
 * @version
 */

public class TmpIdlist extends DataEntity<TmpIdlist> {

    private static final long serialVersionUID = 1457254743943430499L;

    private String bulkid; // bulkid

    private String typename; // typename

    private String tenantId; // 公司

    // 构造方法
    public TmpIdlist() {
        super();
    }

    public TmpIdlist(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "bulkid不能为空")
    public String getBulkid() {
        return bulkid;
    }

    public void setBulkid(String bulkid) {
        this.bulkid = bulkid;
    }

    @Length(min = 0, max = 40, message = "typename长度必须介于 0 和 40 之间")
    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    @NotNull(message = "tenant_id不能为空")
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
        return "tgl_tmp_idlist";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943430499";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "利润结转科目配置";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
