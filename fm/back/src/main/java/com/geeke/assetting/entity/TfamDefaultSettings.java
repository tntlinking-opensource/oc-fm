package com.geeke.assetting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 资产科目方案配置Entity
 * @author
 * @version
 */

public class TfamDefaultSettings extends DataEntity<TfamDefaultSettings> {

    private static final long serialVersionUID = 1641034040897356228L;

    private String item; // 默认项

    private String value; // 默认值 对于7个科目 存放科目id

    private String companyId; // company_id

    // 构造方法
    public TfamDefaultSettings() {
        super();
    }

    public TfamDefaultSettings(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 40, message = "默认项长度必须介于 1 和 40 之间")
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Length(min = 1, max = 40, message = "默认值 对于7个科目 存放科目id长度必须介于 1 和 40 之间")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
        return "tfam_default_settings";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897356228";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "默认设置表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
