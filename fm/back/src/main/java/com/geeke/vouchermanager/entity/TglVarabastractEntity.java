package com.geeke.vouchermanager.entity;

import com.geeke.common.persistence.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class TglVarabastractEntity extends DataEntity<TglVarabastractEntity> {

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("摘要")
    private String varabastract;
    @ApiModelProperty("多租户id")
    private String lesseeid;
    @ApiModelProperty("保存摘要时间")
    private String savedate;
    @ApiModelProperty("租户id")
    private String tenant_id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVarabastract() {
        return varabastract;
    }

    public void setVarabastract(String varabastract) {
        this.varabastract = varabastract;
    }

    public String getLesseeid() {
        return lesseeid;
    }

    public void setLesseeid(String lesseeid) {
        this.lesseeid = lesseeid;
    }

    public String getSavedate() {
        return savedate;
    }

    public void setSavedate(String savedate) {
        this.savedate = savedate;
    }

    public String getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }
}
