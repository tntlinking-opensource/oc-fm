package com.geeke.assetting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 卡片所属部门Entity
 * @author
 * @version
 */

public class TfamAssetManagerDept extends DataEntity<TfamAssetManagerDept> {

    private static final long serialVersionUID = 1641034040897357070L;

    private String userid; // 资产管理员id

    private String deptid; // 职权范围部门id

    private String companyId; // 租户ID

    // 构造方法
    public TfamAssetManagerDept() {
        super();
    }

    public TfamAssetManagerDept(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 64, message = "资产管理员id长度必须介于 1 和 64 之间")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Length(min = 1, max = 64, message = "职权范围部门id长度必须介于 1 和 64 之间")
    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
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
        return "tfam_asset_manager_dept";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897357070";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "卡片所属部门";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
