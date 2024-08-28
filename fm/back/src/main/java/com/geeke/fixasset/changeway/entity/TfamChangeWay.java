package com.geeke.fixasset.changeway.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 变动方式Entity
 * @author
 * @version
 */

public class TfamChangeWay extends DataEntity<TfamChangeWay> {

    private static final long serialVersionUID = 1633870636743042398L;

    private Integer code; // 编码

    private String name; // 名称

    private Integer type; // 类型。1增加2减少

    private String companyId; // 公司id

    // 构造方法
    public TfamChangeWay() {
        super();
    }

    public TfamChangeWay(String id) {
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

    @Length(min = 1, max = 200, message = "名称长度必须介于 1 和 200 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "类型。1增加2减少不能为空")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Length(min = 1, max = 100, message = "公司id长度必须介于 1 和 100 之间")
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
        return "tfam_change_way";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1633870636743042398";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "变动方式表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
