package com.geeke.cardasset.supplementreduce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 补提冲减Entity
 * @author
 * @version
 */

public class TfamDepmethodOld extends DataEntity<TfamDepmethodOld> {

    private static final long serialVersionUID = 1641034040897358522L;

    private String code; // 编码

    private String name; // 名称

    // 构造方法
    public TfamDepmethodOld() {
        super();
    }

    public TfamDepmethodOld(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 0, max = 11, message = "编码长度必须介于 0 和 11 之间")
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
        return "1641034040897358522";
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
