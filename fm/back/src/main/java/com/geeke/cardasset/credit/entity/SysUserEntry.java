package com.geeke.cardasset.credit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 资产入账Entity
 * @author
 * @version
 */

public class SysUserEntry extends DataEntity<SysUserEntry> {

    private static final long serialVersionUID = 1641034040897358091L;

    private String companyId; // 公司

    private String departmentId; // 部门

    private String empolyno; // 工号

    private String name; // 名称

    private String loginName; // 登录账号

    private String loginPassword; // 密码

    private String isLocked = "0"; // 禁用

    private String phone; // 手机号

    private String email; // 邮箱地址

    // 构造方法
    public SysUserEntry() {
        super();
    }

    public SysUserEntry(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 0, max = 21, message = "公司长度必须介于 0 和 21 之间")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Length(min = 0, max = 21, message = "部门长度必须介于 0 和 21 之间")
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Length(min = 0, max = 100, message = "工号长度必须介于 0 和 100 之间")
    public String getEmpolyno() {
        return empolyno;
    }

    public void setEmpolyno(String empolyno) {
        this.empolyno = empolyno;
    }

    @Length(min = 0, max = 128, message = "名称长度必须介于 0 和 128 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 1, max = 10, message = "登录账号长度必须介于 1 和 10 之间")
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Length(min = 0, max = 64, message = "密码长度必须介于 0 和 64 之间")
    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    @Length(min = 1, max = 1, message = "禁用长度必须介于 1 和 1 之间")
    public String getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
    }

    @Length(min = 1, max = 20, message = "手机号长度必须介于 1 和 20 之间")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Length(min = 1, max = 36, message = "邮箱地址长度必须介于 1 和 36 之间")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "sys_user";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897358091";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "用户入账使用";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
