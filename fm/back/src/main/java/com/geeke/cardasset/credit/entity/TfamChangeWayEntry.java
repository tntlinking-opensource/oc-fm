package com.geeke.cardasset.credit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 资产入账Entity
 * @author
 * @version
 */

public class TfamChangeWayEntry extends DataEntity<TfamChangeWayEntry> {

    private static final long serialVersionUID = 1641034040897358094L;

    private Integer code; // 编码

    private String name; // 名称

    private Integer type; // 类型。1增加2减少

    private String creator; // 创建人

    private String createdate; // 创建时间

    private String updator; // 更新人

    private String updatedate; // 更新时间

    private String companyId; // company_id

    // 构造方法
    public TfamChangeWayEntry() {
        super();
    }

    public TfamChangeWayEntry(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "编码不能为空")
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Length(min = 1, max = 200, message = "名称长度必须介于 1 和 200 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "类型。1增加2减少不能为空")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Length(min = 0, max = 100, message = "创建人长度必须介于 0 和 100 之间")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Length(min = 0, max = -1, message = "创建时间长度必须介于 0 和 -1 之间")
    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    @Length(min = 0, max = 100, message = "更新人长度必须介于 0 和 100 之间")
    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    @Length(min = 0, max = -1, message = "更新时间长度必须介于 0 和 -1 之间")
    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
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
        return "tfam_change_way";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897358094";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "变动方式表，卡片新增-新增方式 注：与change_type 并无关联入账使用";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
