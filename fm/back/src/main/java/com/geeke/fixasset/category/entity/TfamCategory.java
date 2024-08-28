package com.geeke.fixasset.category.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 资产类别Entity
 * @author
 * @version
 */

public class TfamCategory extends DataEntity<TfamCategory> {

    private static final long serialVersionUID = 1633870636743042273L;

    private String fullcode; // 全编码

    private String name; // 名称

    private String fullname; // 全名称

    private Integer depmethod; // 折旧方法

    private Integer servicemonth; // 使用月限、折旧月份

    private BigDecimal salvagerate; // 残值率（%）

    private String unit; // 计量单位

    private Integer isleaf; // 是否末级。1是0否

    private String creator; // 创建人



    private String updator; // 更新人



    private String parentid = "0"; // 上级ID

    private String parentcode; // 上级类型编码

    private String imagename; // 资产图例图片名称

    private String imagepath; // 资产图例图片路径
    private String imagebase64; // 资产图例图片路径

    private String companyId; // company_id

    // 构造方法
    public TfamCategory() {
        super();
    }

    public TfamCategory(String id) {
        super(id);
    }

    // 生成get和set方法

    public String getImagebase64() {
        return imagebase64;
    }

    public void setImagebase64(String imagebase64) {
        this.imagebase64 = imagebase64;
    }

    @Length(min = 1, max = 100, message = "全编码长度必须介于 1 和 100 之间")
    public String getFullcode() {
        return fullcode;
    }

    public void setFullcode(String fullcode) {
        this.fullcode = fullcode;
    }

    @Length(min = 1, max = 40, message = "名称长度必须介于 1 和 40 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 1, max = 200, message = "全名称长度必须介于 1 和 200 之间")
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @NotNull(message = "折旧方法不能为空")
    public Integer getDepmethod() {
        return depmethod;
    }

    public void setDepmethod(Integer depmethod) {
        this.depmethod = depmethod;
    }

    @NotNull(message = "使用月限、折旧月份不能为空")
    public Integer getServicemonth() {
        return servicemonth;
    }

    public void setServicemonth(Integer servicemonth) {
        this.servicemonth = servicemonth;
    }

    @NotNull(message = "残值率（%）不能为空")
    public BigDecimal getSalvagerate() {
        return salvagerate;
    }

    public void setSalvagerate(BigDecimal salvagerate) {
        this.salvagerate = salvagerate;
    }

    @Length(min = 0, max = 20, message = "计量单位长度必须介于 0 和 20 之间")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @NotNull(message = "是否末级。1是0否不能为空")
    public Integer getIsleaf() {
        return isleaf;
    }

    public void setIsleaf(Integer isleaf) {
        this.isleaf = isleaf;
    }

    @Length(min = 0, max = 100, message = "创建人长度必须介于 0 和 100 之间")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }


    @Length(min = 0, max = 100, message = "更新人长度必须介于 0 和 100 之间")
    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }


    @Length(min = 0, max = 100, message = "上级ID长度必须介于 0 和 100 之间")
    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    @Length(min = 0, max = 100, message = "上级类型编码长度必须介于 0 和 100 之间")
    public String getParentcode() {
        return parentcode;
    }

    public void setParentcode(String parentcode) {
        this.parentcode = parentcode;
    }

    @Length(min = 0, max = 255, message = "资产图例图片名称长度必须介于 0 和 255 之间")
    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    @Length(min = 0, max = 255, message = "资产图例图片路径长度必须介于 0 和 255 之间")
    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    @Length(min = 1, max = 100, message = "company_id长度必须介于 1 和 100 之间")
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
        return "tfam_category";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1633870636743042273";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "资产类别表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
