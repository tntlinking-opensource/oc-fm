package com.geeke.assetting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 资产权限表Entity
 * @author
 * @version
 */

public class TfamAssetManagerPower extends DataEntity<TfamAssetManagerPower> {

    private static final long serialVersionUID = 1641034040897357050L;

    private String userid; // 资产管理员ID

    private String switchName; // 操作功能开关名称

    private Integer open; // 菜单权限状态 （0打开-有权限， 1关闭-无权限）

    private String companyId; // 租户ID

    // 构造方法
    public TfamAssetManagerPower() {
        super();
    }

    public TfamAssetManagerPower(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 64, message = "资产管理员ID长度必须介于 1 和 64 之间")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Length(min = 0, max = 24, message = "操作功能开关名称长度必须介于 0 和 24 之间")
    public String getSwitchName() {
        return switchName;
    }

    public void setSwitchName(String switchName) {
        this.switchName = switchName;
    }

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
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
        return "tfam_asset_manager_power";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897357050";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "资产权限表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
