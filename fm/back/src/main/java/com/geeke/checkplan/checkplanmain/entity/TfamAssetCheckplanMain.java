package com.geeke.checkplan.checkplanmain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.admin.entity.User;
import com.geeke.common.persistence.DataEntity;
import com.geeke.org.entity.Department;
import com.google.common.collect.Lists;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 盘点主表Entity
 * @author
 * @version
 */

public class TfamAssetCheckplanMain extends DataEntity<TfamAssetCheckplanMain> {

    private static final long serialVersionUID = 1641034040897357834L;

    private String applyNo; // 申请单号

    private String registryDate; // 登记日期

    private User registryPerson; // 登记人

    private Department registryDept; // 登记部门编号

    private String taskName; // 计划名称

    private String remark; // 备注信息

    private String startDate; // 开始时间

    private String endDate; // 线束日期

    private String executorId; // 盘点执行人id

    private Integer totalAsset; // 盘点资产数量合计

    private String companyId; // 公司id

    private Integer alreadyNum; //已盘点数量

    private Integer status = 0; // 盘点任务开关状态（0开启1关闭）

    // 构造方法
    public TfamAssetCheckplanMain() {
        super();
    }

    public TfamAssetCheckplanMain(String id) {
        super(id);
    }

    // 生成get和set方法


    public Integer getAlreadyNum() {
        return alreadyNum;
    }

    public void setAlreadyNum(Integer alreadyNum) {
        this.alreadyNum = alreadyNum;
    }

    @Length(min = 0, max = 255, message = "申请单号长度必须介于 0 和 255 之间")
    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    @Length(min = 1, max = -1, message = "登记日期长度必须介于 1 和 -1 之间")
    public String getRegistryDate() {
        return registryDate;
    }

    public void setRegistryDate(String registryDate) {
        this.registryDate = registryDate;
    }

    public User getRegistryPerson() {
        return registryPerson;
    }

    public void setRegistryPerson(User registryPerson) {
        this.registryPerson = registryPerson;
    }

    public Department getRegistryDept() {
        return registryDept;
    }

    public void setRegistryDept(Department registryDept) {
        this.registryDept = registryDept;
    }

    @Length(min = 0, max = 255, message = "计划名称长度必须介于 0 和 255 之间")
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Length(min = 0, max = 255, message = "备注信息长度必须介于 0 和 255 之间")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Length(min = 0, max = -1, message = "开始时间长度必须介于 0 和 -1 之间")
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Length(min = 0, max = -1, message = "线束日期长度必须介于 0 和 -1 之间")
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Length(min = 0, max = 255, message = "盘点执行人id长度必须介于 0 和 255 之间")
    public String getExecutorId() {
        return executorId;
    }

    public void setExecutorId(String executorId) {
        this.executorId = executorId;
    }

    public Integer getTotalAsset() {
        return totalAsset;
    }

    public void setTotalAsset(Integer totalAsset) {
        this.totalAsset = totalAsset;
    }

    @Length(min = 0, max = 200, message = "公司id长度必须介于 0 和 200 之间")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tfam_asset_checkplan_main";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897357834";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "资产盘点计划主表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
