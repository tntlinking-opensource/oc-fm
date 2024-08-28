package com.geeke.checkplan.attach.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.checkplan.checkplanmain.entity.TfamAssetCheckplanMain;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 盘点计划附件Entity
 * @author
 * @version
 */

public class TfamAssetCheckplanMainAttach extends DataEntity<TfamAssetCheckplanMainAttach> {

    private static final long serialVersionUID = 1641034040897357928L;

    private TfamAssetCheckplanMain main; // 盘点任务id

    private String attachTitle; // 附件类型标题

    private String attachRequired; // 附件是否必传

    private Integer quantity; // 数量限制

    // 构造方法
    public TfamAssetCheckplanMainAttach() {
        super();
    }

    public TfamAssetCheckplanMainAttach(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "盘点任务id不能为空")
    public TfamAssetCheckplanMain getMain() {
        return main;
    }

    public void setMain(TfamAssetCheckplanMain main) {
        this.main = main;
    }

    @Length(min = 0, max = 255, message = "附件类型标题长度必须介于 0 和 255 之间")
    public String getAttachTitle() {
        return attachTitle;
    }

    public void setAttachTitle(String attachTitle) {
        this.attachTitle = attachTitle;
    }

    @Length(min = 0, max = 32, message = "附件是否必传长度必须介于 0 和 32 之间")
    public String getAttachRequired() {
        return attachRequired;
    }

    public void setAttachRequired(String attachRequired) {
        this.attachRequired = attachRequired;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tfam_asset_checkplan_main_attach";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897357928";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "盘点计划附件动态定义表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
