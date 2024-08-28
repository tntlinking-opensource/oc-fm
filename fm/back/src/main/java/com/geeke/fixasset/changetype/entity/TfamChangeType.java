package com.geeke.fixasset.changetype.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 资产变动方式字典Entity
 * @author
 * @version
 */

public class TfamChangeType extends DataEntity<TfamChangeType> {

    private static final long serialVersionUID = 1641034040897357449L;

    private Integer code; // 编码（1增加，2减少，后续根据卡片信息做字典）

    private String name; // 名称

    private String companyid; // 公司id

    // 构造方法
    public TfamChangeType() {
        super();
    }

    public TfamChangeType(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "编码（1增加，2减少，后续根据卡片信息做字典）不能为空")
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Length(min = 1, max = 200, message = "名称长度必须介于 1 和 200 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = 100, message = "公司id长度必须介于 0 和 100 之间")
    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tfam_change_type";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897357449";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "变动内容表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
