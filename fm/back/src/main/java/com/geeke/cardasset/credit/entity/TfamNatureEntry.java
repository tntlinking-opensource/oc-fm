package com.geeke.cardasset.credit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 资产入账Entity
 * @author
 * @version
 */

public class TfamNatureEntry extends DataEntity<TfamNatureEntry> {

    private static final long serialVersionUID = 1641034040897358089L;

    private String code; // 编码

    private String name; // 名称

    private String companyId; // company_id

    // 构造方法
    public TfamNatureEntry() {
        super();
    }

    public TfamNatureEntry(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 11, message = "编码长度必须介于 1 和 11 之间")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Length(min = 1, max = 20, message = "名称长度必须介于 1 和 20 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 1, max = 100, message = "company_id长度必须介于 1 和 100 之间")
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
        return "tfam_nature";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897358089";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "卡片性质表入账使用";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
