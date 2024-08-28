package com.geeke.cardasset.supplementreduce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 补提冲减Entity
 * @author
 * @version
 */

public class TfamUsingstatusOld extends DataEntity<TfamUsingstatusOld> {

    private static final long serialVersionUID = 1641034040897358520L;

    private Integer code; // 编码

    private String name; // 名称

    private String companyId; // 租户ID

    // 构造方法
    public TfamUsingstatusOld() {
        super();
    }

    public TfamUsingstatusOld(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "编码不能为空")
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Length(min = 1, max = 20, message = "名称长度必须介于 1 和 20 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = 64, message = "租户ID长度必须介于 0 和 64 之间")
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
        return "tfam_usingstatus";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897358520";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "使用状况表。对应卡片中usingstatus";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
