package com.geeke.checkplan.image.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.checkplan.attach.entity.TfamAssetCheckplanMainAttach;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 资产图片Entity
 * @author
 * @version
 */

public class TfamAssetCheckimage extends DataEntity<TfamAssetCheckimage> {

    private static final long serialVersionUID = 1641034040897358064L;

    private String cardNo; // 资产卡片编号

    private String imagePath; // 图片路径

    private String creator; // 操作人

    private String createReason; // 上传原因

    private String createTime; // 上传时间

    private String companyId; // 租户id

    private TfamAssetCheckplanMainAttach attachCategory; // 附件类别ID

    private String mainId; // 盘点计划ID

    private String cardId; // 资产卡片ID

    private String originalfilename; // 附件原名称

    // 构造方法
    public TfamAssetCheckimage() {
        super();
    }

    public TfamAssetCheckimage(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 0, max = 30, message = "资产卡片编号长度必须介于 0 和 30 之间")
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    @Length(min = 0, max = 255, message = "图片路径长度必须介于 0 和 255 之间")
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Length(min = 0, max = 255, message = "操作人长度必须介于 0 和 255 之间")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Length(min = 0, max = 255, message = "上传原因长度必须介于 0 和 255 之间")
    public String getCreateReason() {
        return createReason;
    }

    public void setCreateReason(String createReason) {
        this.createReason = createReason;
    }

    @Length(min = 0, max = -1, message = "上传时间长度必须介于 0 和 -1 之间")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Length(min = 0, max = 100, message = "租户id长度必须介于 0 和 100 之间")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public TfamAssetCheckplanMainAttach getAttachCategory() {
        return attachCategory;
    }

    public void setAttachCategory(TfamAssetCheckplanMainAttach attachCategory) {
        this.attachCategory = attachCategory;
    }

    @Length(min = 0, max = 64, message = "盘点计划ID长度必须介于 0 和 64 之间")
    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    @Length(min = 0, max = 64, message = "资产卡片ID长度必须介于 0 和 64 之间")
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    @Length(min = 0, max = 300, message = "附件原名称长度必须介于 0 和 300 之间")
    public String getOriginalfilename() {
        return originalfilename;
    }

    public void setOriginalfilename(String originalfilename) {
        this.originalfilename = originalfilename;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tfam_asset_checkimage";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897358064";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "资产盘点图片保存表-由company_id+card_no确定唯一。image存储-新增卡片-实物图片处上传的图片";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
