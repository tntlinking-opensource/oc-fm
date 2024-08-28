package com.geeke.checkplan.dept.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.checkplan.checkplanmain.entity.TfamAssetCheckplanMain;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 资产盘点部门Entity
 * @author
 * @version
 */

public class TfamAssetCheckplanMainDept extends DataEntity<TfamAssetCheckplanMainDept> {

    private static final long serialVersionUID = 1641034040897357861L;

    private TfamAssetCheckplanMain main; // 任务表单ID

    private String deptId; // 部门ID

    // 构造方法
    public TfamAssetCheckplanMainDept() {
        super();
    }

    public TfamAssetCheckplanMainDept(String id) {
        super(id);
    }

    // 生成get和set方法

    public TfamAssetCheckplanMain getMain() {
        return main;
    }

    public void setMain(TfamAssetCheckplanMain main) {
        this.main = main;
    }

    @Length(min = 0, max = 40, message = "部门ID长度必须介于 0 和 40 之间")
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tfam_asset_checkplan_main_dept";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897357861";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "盘点计划部门范围表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
