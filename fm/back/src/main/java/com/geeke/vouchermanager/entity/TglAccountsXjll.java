package com.geeke.vouchermanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.tgl.flow.entity.Flowtype;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 现金流量表Entity
 * @author
 * @version
 */

@ApiModel(description = "生成get和set方法")
public class TglAccountsXjll extends DataEntity<TglAccountsXjll> {


    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1457254743943433335L;

    @ApiModelProperty("ID")
    private String id; // ID

    @ApiModelProperty("配置id")
    private String xjllConfigId; // 配置id

    @ApiModelProperty("凭证ID")
    private String certifucateId; // 凭证ID

    @ApiModelProperty("科目id")
    private String accountsId; // 科目id

    @ApiModelProperty("账期ID")
    private String accountperiodId; // 账期ID

    @ApiModelProperty("借方金额")
    private BigDecimal jiemoney; // 借方金额

    @ApiModelProperty("贷方金额")
    private BigDecimal daimoney; // 贷方金额



    @ApiModelProperty("创建人")
    private String creator; // 创建人
    @ApiModelProperty("现金流量类别")
    private Flowtype uqflowtypeid;



    @ApiModelProperty("修改人")
    private String updateor; // 修改人

    @ApiModelProperty("凭证分录ID")
    private String uqvoucherdetailId; // 凭证分录ID

    @ApiModelProperty("租户id")
    private String tenantId; // 租户id

    // 构造方法
    public TglAccountsXjll() {
        super();
    }

    public TglAccountsXjll(String id) {
        super(id);
    }

    // 生成get和set方法
    public Flowtype getUqflowtypeid() {
        return uqflowtypeid;
    }

    public void setUqflowtypeid(Flowtype uqflowtypeid) {
        this.uqflowtypeid = uqflowtypeid;
    }
    @Length(min = 1, max = 100, message = "ID长度必须介于 1 和 100 之间")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Length(min = 0, max = 100, message = "配置id长度必须介于 0 和 100 之间")
    public String getXjllConfigId() {
        return xjllConfigId;
    }

    public void setXjllConfigId(String xjllConfigId) {
        this.xjllConfigId = xjllConfigId;
    }

    @Length(min = 0, max = 100, message = "凭证ID长度必须介于 0 和 100 之间")
    public String getCertifucateId() {
        return certifucateId;
    }

    public void setCertifucateId(String certifucateId) {
        this.certifucateId = certifucateId;
    }

    @Length(min = 0, max = 100, message = "科目id长度必须介于 0 和 100 之间")
    public String getAccountsId() {
        return accountsId;
    }

    public void setAccountsId(String accountsId) {
        this.accountsId = accountsId;
    }

    @Length(min = 0, max = 100, message = "账期ID长度必须介于 0 和 100 之间")
    public String getAccountperiodId() {
        return accountperiodId;
    }

    public void setAccountperiodId(String accountperiodId) {
        this.accountperiodId = accountperiodId;
    }

    public BigDecimal getJiemoney() {
        return jiemoney;
    }

    public void setJiemoney(BigDecimal jiemoney) {
        this.jiemoney = jiemoney;
    }

    public BigDecimal getDaimoney() {
        return daimoney;
    }

    public void setDaimoney(BigDecimal daimoney) {
        this.daimoney = daimoney;
    }



    @Length(min = 0, max = 100, message = "创建人长度必须介于 0 和 100 之间")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }


    @Length(min = 0, max = 100, message = "修改人长度必须介于 0 和 100 之间")
    public String getUpdateor() {
        return updateor;
    }

    public void setUpdateor(String updateor) {
        this.updateor = updateor;
    }

    @Length(min = 0, max = 40, message = "凭证分录ID长度必须介于 0 和 40 之间")
    public String getUqvoucherdetailId() {
        return uqvoucherdetailId;
    }

    public void setUqvoucherdetailId(String uqvoucherdetailId) {
        this.uqvoucherdetailId = uqvoucherdetailId;
    }

    @Length(min = 0, max = 100, message = "租户id长度必须介于 0 和 100 之间")
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tgl_accounts_xjll";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943433335";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "制证现金流量编制表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return false;
    }
}
