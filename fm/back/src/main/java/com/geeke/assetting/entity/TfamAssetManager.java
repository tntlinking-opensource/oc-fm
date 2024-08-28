package com.geeke.assetting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.assetting.entity.AssettingOrgDepartment;
import com.geeke.assetting.entity.AssettingSysUser;
import com.geeke.assetting.entity.AssettingSysUser;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 卡片管理Entity
 * @author
 * @version
 */

public class TfamAssetManager extends DataEntity<TfamAssetManager> {

    private static final long serialVersionUID = 1641034040897356567L;

    private AssettingSysUser userid; // 管理员用户ID

    private String createTime; // 创建时间

    private String companyId; // 租户ID

    private List<AssettingSysUser> assettingSysUserList = Lists.newArrayList(); // 子表列表

    private List<AssettingOrgDepartment> assettingOrgDepartmentList = Lists.newArrayList(); // 子表列表

    // 构造方法
    public TfamAssetManager() {
        super();
    }

    public TfamAssetManager(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "管理员用户ID不能为空")
    public AssettingSysUser getUserid() {
        return userid;
    }

    public void setUserid(AssettingSysUser userid) {
        this.userid = userid;
    }

    @Length(min = 0, max = -1, message = "创建时间长度必须介于 0 和 -1 之间")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Length(min = 0, max = 64, message = "租户ID长度必须介于 0 和 64 之间")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public List<AssettingSysUser> getAssettingSysUserList() {
        return assettingSysUserList;
    }

    public void setAssettingSysUserList(List<AssettingSysUser> assettingSysUserList) {
        this.assettingSysUserList = assettingSysUserList;
    }

    public List<AssettingOrgDepartment> getAssettingOrgDepartmentList() {
        return assettingOrgDepartmentList;
    }

    public void setAssettingOrgDepartmentList(List<AssettingOrgDepartment> assettingOrgDepartmentList) {
        this.assettingOrgDepartmentList = assettingOrgDepartmentList;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tfam_asset_manager";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897356567";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "卡片管理";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
