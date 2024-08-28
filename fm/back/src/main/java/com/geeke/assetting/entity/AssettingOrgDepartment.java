package com.geeke.assetting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 卡片管理Entity
 * @author
 * @version
 */

public class AssettingOrgDepartment extends DataEntity<AssettingOrgDepartment> {

    private static final long serialVersionUID = 1641034040897356569L;

    private String companyId; // 公司

    private String code; // 编号

    private String name; // 名称

    private String director; // 总监

    private String manager; // 经理

    // 构造方法
    public AssettingOrgDepartment() {
        super();
    }

    public AssettingOrgDepartment(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 21, message = "公司长度必须介于 1 和 21 之间")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Length(min = 1, max = 64, message = "编号长度必须介于 1 和 64 之间")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Length(min = 1, max = 128, message = "名称长度必须介于 1 和 128 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = 21, message = "总监长度必须介于 0 和 21 之间")
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Length(min = 0, max = 21, message = "经理长度必须介于 0 和 21 之间")
    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "org_department";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897356569";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "部门";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
