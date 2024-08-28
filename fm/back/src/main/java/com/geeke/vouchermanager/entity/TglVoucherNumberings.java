package com.geeke.vouchermanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 凭证编号类别Entity
 * @author
 * @version
 */

public class TglVoucherNumberings extends DataEntity<TglVoucherNumberings> {

    private static final long serialVersionUID = 1538459003414151282L;

    private String uqnumberingid; // 凭证编号ID

    private String varname; // 凭证编号名称

    private String varvoucherhave; // 凭证必有科目

    private String varvoucherno; // 凭证必无科目

    private String vardebithave; // 借方必有科目

    private String vardebitno; // 借方必无科目

    private String varcredithave; // 贷方必有科目

    private String varcreditno; // 贷方必无科目

    private Integer intstatus; // INTSTATUS

    private String tenantId; // TENANT_ID

    private String sort; // 序号

    // 构造方法
    public TglVoucherNumberings() {
        super();
    }

    public TglVoucherNumberings(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 40, message = "凭证编号ID长度必须介于 1 和 40 之间")
    public String getUqnumberingid() {
        return uqnumberingid;
    }

    public void setUqnumberingid(String uqnumberingid) {
        this.uqnumberingid = uqnumberingid;
    }

    @Length(min = 1, max = 50, message = "凭证编号名称长度必须介于 1 和 50 之间")
    public String getVarname() {
        return varname;
    }

    public void setVarname(String varname) {
        this.varname = varname;
    }

    @Length(min = 0, max = 100, message = "凭证必有科目长度必须介于 0 和 100 之间")
    public String getVarvoucherhave() {
        return varvoucherhave;
    }

    public void setVarvoucherhave(String varvoucherhave) {
        this.varvoucherhave = varvoucherhave;
    }

    @Length(min = 0, max = 100, message = "凭证必无科目长度必须介于 0 和 100 之间")
    public String getVarvoucherno() {
        return varvoucherno;
    }

    public void setVarvoucherno(String varvoucherno) {
        this.varvoucherno = varvoucherno;
    }

    @Length(min = 0, max = 100, message = "借方必有科目长度必须介于 0 和 100 之间")
    public String getVardebithave() {
        return vardebithave;
    }

    public void setVardebithave(String vardebithave) {
        this.vardebithave = vardebithave;
    }

    @Length(min = 0, max = 100, message = "借方必无科目长度必须介于 0 和 100 之间")
    public String getVardebitno() {
        return vardebitno;
    }

    public void setVardebitno(String vardebitno) {
        this.vardebitno = vardebitno;
    }

    @Length(min = 0, max = 100, message = "贷方必有科目长度必须介于 0 和 100 之间")
    public String getVarcredithave() {
        return varcredithave;
    }

    public void setVarcredithave(String varcredithave) {
        this.varcredithave = varcredithave;
    }

    @Length(min = 0, max = 100, message = "贷方必无科目长度必须介于 0 和 100 之间")
    public String getVarcreditno() {
        return varcreditno;
    }

    public void setVarcreditno(String varcreditno) {
        this.varcreditno = varcreditno;
    }

    public Integer getIntstatus() {
        return intstatus;
    }

    public void setIntstatus(Integer intstatus) {
        this.intstatus = intstatus;
    }

    @Length(min = 0, max = 100, message = "TENANT_ID长度必须介于 0 和 100 之间")
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Length(min = 0, max = 255, message = "序号长度必须介于 0 和 255 之间")
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tgl_voucher_numberings";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1538459003414151282";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "凭证编号类别";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return false;
    }
}
