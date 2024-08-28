package com.geeke.checkplan.detail.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.checkplan.result.entity.TfamAssetCheckresult;
import com.geeke.common.persistence.DataEntity;
import com.geeke.fixasset.assetstatus.entity.TfamAssetStatus;
import com.google.common.collect.Lists;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 盘点详情Entity
 * @author
 * @version
 */

public class TfamAssetCheckplanDetail extends DataEntity<TfamAssetCheckplanDetail> {

    private static final long serialVersionUID = 1641034040897357876L;

    private String main; // 资产盘点计划主表id

    private String cardId; // 卡片id

    private String cardNo; // 卡片编号

    private String assetName; // 资产名称

    private String userid; // 责任人

    private String cardModel; // 规格型号

    private String netvalue; // 净值

    private String evalvalue; // 评估净值

    private Integer num; // 资产数量

    private String unit; // 资产单位

    private Integer actNum; // 盘点实际数量

    private TfamAssetStatus beforeCheckStatus; // 盘点前资产状态

    private TfamAssetCheckresult checkResult; // 盘点结果

    private TfamAssetStatus assetStatus; // 盘点后资产状态

    private String remark; // 备注

    private Integer checkStatus; // 盘点状态 0- 未盘点 1 -已盘点

    private String checkDate; // 盘点时间

    // 构造方法
    public TfamAssetCheckplanDetail() {
        super();
    }

    public TfamAssetCheckplanDetail(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 0, max = 255, message = "资产盘点计划主表id长度必须介于 0 和 255 之间")
    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    @Length(min = 0, max = 225, message = "卡片id长度必须介于 0 和 225 之间")
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    @Length(min = 0, max = 255, message = "卡片编号长度必须介于 0 和 255 之间")
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    @Length(min = 0, max = 255, message = "资产名称长度必须介于 0 和 255 之间")
    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    @Length(min = 0, max = 50, message = "责任人长度必须介于 0 和 50 之间")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Length(min = 0, max = 255, message = "规格型号长度必须介于 0 和 255 之间")
    public String getCardModel() {
        return cardModel;
    }

    public void setCardModel(String cardModel) {
        this.cardModel = cardModel;
    }

    @Length(min = 0, max = 255, message = "净值长度必须介于 0 和 255 之间")
    public String getNetvalue() {
        return netvalue;
    }

    public void setNetvalue(String netvalue) {
        this.netvalue = netvalue;
    }

    @Length(min = 0, max = 255, message = "评估净值长度必须介于 0 和 255 之间")
    public String getEvalvalue() {
        return evalvalue;
    }

    public void setEvalvalue(String evalvalue) {
        this.evalvalue = evalvalue;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Length(min = 0, max = 10, message = "资产单位长度必须介于 0 和 10 之间")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getActNum() {
        return actNum;
    }

    public void setActNum(Integer actNum) {
        this.actNum = actNum;
    }

    public TfamAssetStatus getBeforeCheckStatus() {
        return beforeCheckStatus;
    }

    public void setBeforeCheckStatus(TfamAssetStatus beforeCheckStatus) {
        this.beforeCheckStatus = beforeCheckStatus;
    }

    public TfamAssetCheckresult getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(TfamAssetCheckresult checkResult) {
        this.checkResult = checkResult;
    }

    public TfamAssetStatus getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(TfamAssetStatus assetStatus) {
        this.assetStatus = assetStatus;
    }

    @Length(min = 0, max = 255, message = "备注长度必须介于 0 和 255 之间")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Length(min = 0, max = 1, message = "盘点状态 0- 未盘点 1 -已盘点长度必须介于 0 和 1 之间")
    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    @Length(min = 0, max = -1, message = "盘点时间长度必须介于 0 和 -1 之间")
    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tfam_asset_checkplan_detail";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897357876";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "资产盘点计划明细表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
