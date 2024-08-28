package com.geeke.fixasset.assetstatus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 卡片盘点状态Entity
 * @author
 * @version
 */

public class TfamAssetStatus extends DataEntity<TfamAssetStatus> {

    private static final long serialVersionUID = 1641034040897356344L;

    private String code; // 资产状态编码

    private String name; // 资产状态名称

    private String companyId; // 租户id

    // 构造方法
    public TfamAssetStatus() {
        super();
    }

    public TfamAssetStatus(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 0, max = 255, message = "资产状态编码长度必须介于 0 和 255 之间")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Length(min = 0, max = 255, message = "资产状态名称长度必须介于 0 和 255 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = 50, message = "租户id长度必须介于 0 和 50 之间")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tfam_asset_status";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897356344";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "卡片状态";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
