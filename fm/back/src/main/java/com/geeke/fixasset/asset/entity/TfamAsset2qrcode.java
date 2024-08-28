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

public class TfamAsset2qrcode extends DataEntity<TfamAsset2qrcode> {

    private static final long serialVersionUID = 1641034040897356479L;

    private TfamAsset card; // 卡片id

    private String qrcodePath; // 二维码路径

    // 构造方法
    public TfamAsset2qrcode() {
        super();
    }

    public TfamAsset2qrcode(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "卡片id不能为空")
    public TfamAsset getCard() {
        return card;
    }

    public void setCard(TfamAsset card) {
        this.card = card;
    }

    @Length(min = 1, max = 255, message = "二维码路径长度必须介于 1 和 255 之间")
    public String getQrcodePath() {
        return qrcodePath;
    }

    public void setQrcodePath(String qrcodePath) {
        this.qrcodePath = qrcodePath;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tfam_asset2qrcode";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897356479";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "存放资产二维码";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
