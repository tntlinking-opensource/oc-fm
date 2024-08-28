package com.geeke.fixasset.asset.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.fixasset.asset.entity.TfamAsset;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 卡片管理Entity
 * @author
 * @version
 */

public class TfamAssetTransfer extends DataEntity<TfamAssetTransfer> {

    private static final long serialVersionUID = 1641034040897356717L;

    private TfamAsset card; // 资产卡片id

    private String beforeTransferOwner; // 调拨前责任人

    private String afterTransferOwner; // 调拨后责任人

    private String beforeTransferDepartment; // 调拨前使用部门

    private String afterTransferDepartment; // 调拨后使用部门

    private String transferReason; // 调拨原因

    private String companyId; // 租户id

    private String transferDate; // 调拨日期

    private String transferCreatorId; // 操作人id

    private String transferCreatorName; // 操作人姓名

    // 构造方法
    public TfamAssetTransfer() {
        super();
    }

    public TfamAssetTransfer(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "资产卡片id不能为空")
    public TfamAsset getCard() {
        return card;
    }

    public void setCard(TfamAsset card) {
        this.card = card;
    }

    @Length(min = 0, max = 255, message = "调拨前责任人长度必须介于 0 和 255 之间")
    public String getBeforeTransferOwner() {
        return beforeTransferOwner;
    }

    public void setBeforeTransferOwner(String beforeTransferOwner) {
        this.beforeTransferOwner = beforeTransferOwner;
    }

    @Length(min = 0, max = 255, message = "调拨后责任人长度必须介于 0 和 255 之间")
    public String getAfterTransferOwner() {
        return afterTransferOwner;
    }

    public void setAfterTransferOwner(String afterTransferOwner) {
        this.afterTransferOwner = afterTransferOwner;
    }

    @Length(min = 0, max = 255, message = "调拨前使用部门长度必须介于 0 和 255 之间")
    public String getBeforeTransferDepartment() {
        return beforeTransferDepartment;
    }

    public void setBeforeTransferDepartment(String beforeTransferDepartment) {
        this.beforeTransferDepartment = beforeTransferDepartment;
    }

    @Length(min = 0, max = 255, message = "调拨后使用部门长度必须介于 0 和 255 之间")
    public String getAfterTransferDepartment() {
        return afterTransferDepartment;
    }

    public void setAfterTransferDepartment(String afterTransferDepartment) {
        this.afterTransferDepartment = afterTransferDepartment;
    }

    @Length(min = 0, max = 400, message = "调拨原因长度必须介于 0 和 400 之间")
    public String getTransferReason() {
        return transferReason;
    }

    public void setTransferReason(String transferReason) {
        this.transferReason = transferReason;
    }

    @Length(min = 0, max = 100, message = "租户id长度必须介于 0 和 100 之间")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Length(min = 1, max = -1, message = "调拨日期长度必须介于 1 和 -1 之间")
    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    @Length(min = 0, max = 255, message = "操作人id长度必须介于 0 和 255 之间")
    public String getTransferCreatorId() {
        return transferCreatorId;
    }

    public void setTransferCreatorId(String transferCreatorId) {
        this.transferCreatorId = transferCreatorId;
    }

    @Length(min = 0, max = 255, message = "操作人姓名长度必须介于 0 和 255 之间")
    public String getTransferCreatorName() {
        return transferCreatorName;
    }

    public void setTransferCreatorName(String transferCreatorName) {
        this.transferCreatorName = transferCreatorName;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tfam_asset_transfer";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897356717";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "资产调拨表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
