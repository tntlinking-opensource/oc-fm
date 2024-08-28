package com.geeke.fixasset.depmethod.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 资产类别表depmethodEntity
 * @author
 * @version
 */

public class TfamDepmethod extends DataEntity<TfamDepmethod> {

    private static final long serialVersionUID = 1641034040897356422L;

    private Integer code; // 编码

    private String name; // 名称

    // 构造方法
    public TfamDepmethod() {
        super();
    }

    public TfamDepmethod(String id) {
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

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tfam_depmethod";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897356422";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "折旧方法表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
