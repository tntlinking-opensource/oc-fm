package com.geeke.fixasset.asset.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.fixasset.asset.entity.TfamAsset;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 卡片管理Entity
 * @author
 * @version
 */

public class TfamAccessory extends DataEntity<TfamAccessory> {

    private static final long serialVersionUID = 1641034040897356478L;

    private TfamAsset card; // 关联卡片ID

    private String name; // 名称

    private String spec; // 规格

    private String unit; // 计量单位

    private Integer num; // 数量

    private BigDecimal money; // 金额

    // 构造方法
    public TfamAccessory() {
        super();
    }

    public TfamAccessory(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "关联卡片ID不能为空")
    public TfamAsset getCard() {
        return card;
    }

    public void setCard(TfamAsset card) {
        this.card = card;
    }

    @Length(min = 1, max = 100, message = "名称长度必须介于 1 和 100 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = 80, message = "规格长度必须介于 0 和 80 之间")
    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    @Length(min = 0, max = 80, message = "计量单位长度必须介于 0 和 80 之间")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tfam_accessory";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897356478";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "附属设备表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
