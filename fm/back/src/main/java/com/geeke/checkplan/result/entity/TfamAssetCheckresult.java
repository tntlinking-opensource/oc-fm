package com.geeke.checkplan.result.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 盘点结果Entity
 * @author
 * @version
 */

public class TfamAssetCheckresult extends DataEntity<TfamAssetCheckresult> {

    private static final long serialVersionUID = 1641034040897357906L;

    private String code; // 结果编码

    private String name; // 盘点结果

    private String companyId; // 租户id

    // 构造方法
    public TfamAssetCheckresult() {
        super();
    }

    public TfamAssetCheckresult(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 0, max = 255, message = "结果编码长度必须介于 0 和 255 之间")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Length(min = 0, max = 255, message = "盘点结果长度必须介于 0 和 255 之间")
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
        return "tfam_asset_checkresult";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897357906";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "资产盘点结果";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
