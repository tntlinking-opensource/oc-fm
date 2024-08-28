package com.geeke.fixasset.alter.service;

import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.fixasset.alter.dao.TfamAlterDao;
import com.geeke.fixasset.alter.dto.SaveAlterDto;
import com.geeke.fixasset.alter.entity.TfamAlter;
import com.geeke.fixasset.asset.dao.TfamAssetDao;
import com.geeke.fixasset.asset.entity.TfamAsset;
import com.geeke.fixasset.category.dao.TfamCategoryDao;
import com.geeke.fixasset.category.entity.TfamCategory;
import com.geeke.fixasset.enums.OperatetAlterTypeEnum;
import com.geeke.fixasset.producer.dao.TfamSupplierDao;
import com.geeke.fixasset.producer.entity.TfamSupplier;
import com.geeke.utils.DateUtils;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.vouchermanager.entity.TglAccounts;
import com.geeke.vouchermanager.service.TglAccountsService;
import com.google.common.collect.Maps;

import java.util.*;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.Oneway;

/**
 * 资产变动明细Service
 *
 * @author
 */

@Service("tfamAlterService")
@Transactional(readOnly = true)
public class TfamAlterService extends CrudService<TfamAlterDao, TfamAlter> {

    @Autowired
    private TfamAssetDao tfamAssetDao;

    @Autowired
    private TglAccountsService tglAccountsService;

    @Autowired
    private TfamCategoryDao categoryDao;
    @Autowired
    private TfamSupplierDao producerDao;

    @Override
    public Page<TfamAlter> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
        String filter =
                "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        Page<TfamAlter> result = super.listPage(super.addFilter(parameters, filter), offset, limit, orderby);

        return result;
    }

    @Override
    public List<TfamAlter> listAll(List<Parameter> parameters, String orderby) {
        String filter =
                "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        PageRequest pageRequest = new PageRequest(super.addFilter(parameters, filter), orderby);

        List<TfamAlter> result = dao.listAll(pageRequest);

        return result;
    }

    public List<TfamAlter> listAllByAsset(String assetId) {
        List<Parameter> parameters = new ArrayList<>();
        Parameter parameter = new Parameter();
        parameter.setColumnName("asset_id");
        parameter.setValue(assetId);
        parameter.setQueryType("=");
        parameters.add(parameter);
        String filter =
                "{'columnName':'company_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        PageRequest pageRequest = new PageRequest(super.addFilter(parameters, filter), null);

        List<TfamAlter> result = dao.listAll(pageRequest);

        return result;
    }


    public TglAccounts getAccountById(TglAccounts tglAccounts) {
        if (tglAccounts == null) {
            return null;
        }
        return tglAccountsService.getAccountById(tglAccounts.getUqaccountid());
    }

    /**
     * 新增审核
     * 报废审核
     * 新增反审核
     * 报废反审核
     * <p>
     * saveAlterDto asset 修改之后的数据
     *
     * @param saveAlterDto
     */
    public void save(SaveAlterDto saveAlterDto) {
        List<TfamAlter> tfamAlters = new ArrayList<>();

        TfamAsset newAsset = saveAlterDto.getAsset();
        Integer operatetType = saveAlterDto.getOperatetType();
        if (OperatetAlterTypeEnum.ADD.getCode().equals(operatetType)) {
            TfamAlter tfamAlter = new TfamAlter();
            tfamAlter.setAlterType("0001");
            tfamAlter.setReason("新增审核");
            tfamAlter.setDate(DateUtils.getDateTime());
            tfamAlter.setYear(DateUtils.getYear());
            tfamAlter.setMonth(DateUtils.getMonth());
            tfamAlter.setAssetId(newAsset.getId());
            tfamAlter.setOldValue("0.0");
            tfamAlter.setNewValue(newAsset.getValue() == null ? "0.0" : newAsset.getValue().toString());
            tfamAlter.setCompanyId(newAsset.getCompanyId());
            tfamAlter.setSourceType(1);
            tfamAlters.add(tfamAlter);
        } else if (OperatetAlterTypeEnum.UPDATE.getCode().equals(operatetType) ||
                OperatetAlterTypeEnum.TRANSFER.getCode().equals(operatetType)) {
            Integer sourceType = 3;
            if (OperatetAlterTypeEnum.TRANSFER.getCode().equals(operatetType)) {
                sourceType = 4;
            }
            TfamAsset oldAsset = tfamAssetDao.get(newAsset.getId());
            if (oldAsset != null) {
                oldAsset.setAccudepAccountid(getAccountById(oldAsset.getAccudepAccountid()));
                oldAsset.setAssetAccountid(getAccountById(oldAsset.getAssetAccountid()));
                oldAsset.setDepcostAccountid(getAccountById(oldAsset.getDepcostAccountid()));
                oldAsset.setPredevaluateAccountid(getAccountById(oldAsset.getPredevaluateAccountid()));
                oldAsset.setPredevaluateCoraccountid(getAccountById(oldAsset.getPredevaluateCoraccountid()));
                oldAsset.setTaxAccountid(getAccountById(oldAsset.getTaxAccountid()));
                oldAsset.setProjectAccountid(getAccountById(oldAsset.getProjectAccountid()));
            }
            //资产类别
            if (oldAsset.getCategory() == null || !oldAsset.getCategory().getId().equals(newAsset.getCategory().getId())) {
                TfamCategory category = categoryDao.get(newAsset.getCategory().getId());
                TfamAlter cateGoryAlter = TfamAlter.builder()
                        .alterType("0003")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(oldAsset.getCategory().getFullname())
                        .newValue(category != null ? category.getFullname() : "")
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(cateGoryAlter);
            }
            //  0004	资产名称
            if (!oldAsset.getAssetName().equals(newAsset.getAssetName())) {
                TfamAlter cateGoryAlter = TfamAlter.builder()
                        .alterType("0004")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(oldAsset.getAssetName())
                        .newValue(newAsset.getAssetName())
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(cateGoryAlter);
            }
            // 0005	型号 cardModel
            if (!oldAsset.getCardModel().equals(newAsset.getCardModel())) {
                TfamAlter cateGoryAlter = TfamAlter.builder()
                        .alterType("0005")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(oldAsset.getCardModel())
                        .newValue(newAsset.getCardModel())
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(cateGoryAlter);
            }

            // 0006	所在地点 place
            if (!oldAsset.getPlace().equals(newAsset.getPlace())) {
                TfamAlter cateGoryAlter = TfamAlter.builder()
                        .alterType("0006")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(oldAsset.getPlace())
                        .newValue(newAsset.getPlace())
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(cateGoryAlter);
            }
            //0007	生产厂家 producer
            if (oldAsset.getProducer() == null || !oldAsset.getProducer().getId().equals(newAsset.getProducer().getId())) {
                TfamSupplier supplier = producerDao.get(newAsset.getProducer().getId());
                TfamAlter cateGoryAlter = TfamAlter.builder()
                        .alterType("0007")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(oldAsset.getProducer().getName())
                        .newValue(supplier != null ? supplier.getName() : "")
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(cateGoryAlter);
            }
            // 0008	使用部门 getUsedeptid
            if (oldAsset.getUsedeptid() == null || !oldAsset.getUsedeptid().getId().equals(newAsset.getUsedeptid().getId())) {
                TfamAlter alter = TfamAlter.builder()
                        .alterType("0008")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(oldAsset.getUsedeptid().getName())
                        .newValue(newAsset.getUsedeptid().getName())
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(alter);
            }
            //0009	责任人
            if (!oldAsset.getUserid().getId().equals(newAsset.getUserid().getId())) {
                TfamAlter alter = TfamAlter.builder()
                        .alterType("0009")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(oldAsset.getUserid().getName())
                        .newValue(newAsset.getUserid().getName())
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(alter);
            }
            //0010	数量
            if (!oldAsset.getNum().equals(newAsset.getNum())) {
                TfamAlter alter = TfamAlter.builder()
                        .alterType("0010")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(oldAsset.getNum().toString())
                        .newValue(newAsset.getNum().toString())
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(alter);
            }
            //0011	计量单位
            if (!oldAsset.getUnit().equals(newAsset.getUnit())) {
                TfamAlter alter = TfamAlter.builder()
                        .alterType("0011")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(oldAsset.getUnit().toString())
                        .newValue(newAsset.getUnit().toString())
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(alter);
            }
            //0012	使用状况
            if (!oldAsset.getUsingstatus().getId().equals(newAsset.getUsingstatus().getId())) {
                TfamAlter alter = TfamAlter.builder()
                        .alterType("0012")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(oldAsset.getUsingstatus().getName())
                        .newValue(newAsset.getUsingstatus().getName())
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(alter);
            }
            //0013	备注
            if (!oldAsset.getRemarks().equals(newAsset.getRemarks())) {
                TfamAlter alter = TfamAlter.builder()
                        .alterType("0012")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(oldAsset.getRemarks())
                        .newValue(newAsset.getRemarks())
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(alter);
            }
            // 0014	固定资产科目 UQACCOUNTID
            if (oldAsset.getAssetAccountid() == null || oldAsset.getAssetAccountid().getUqaccountid() == null || !newAsset.getAssetAccountid().getUqaccountid().equals(oldAsset.getAssetAccountid().getUqaccountid())) {
                if (null != newAsset.getAssetAccountid()) {
                    TfamAlter alter = TfamAlter.builder()
                            .alterType("0014")
                            .assetId(newAsset.getId())
                            .reason(saveAlterDto.getReason())
                            .companyId(newAsset.getCompanyId())
                            .year(DateUtils.getYear())
                            .month(DateUtils.getMonth())
                            .date(DateUtils.getDateTime())
                            .oldValue(oldAsset.getAssetAccountid() != null ? oldAsset.getAssetAccountid().getVaraccountfullname() : "")
                            .newValue(newAsset.getAssetAccountid().getVaraccountfullname())
                            .sourceType(sourceType)
                            .build();
                    tfamAlters.add(alter);
                }

            }
            //0015	累计折旧科目
            if (oldAsset.getAccudepAccountid() == null || oldAsset.getAccudepAccountid().getUqaccountid() == null || !newAsset.getAccudepAccountid().getUqaccountid().equals(oldAsset.getAccudepAccountid().getUqaccountid())) {
                if (null != newAsset.getAccudepAccountid()) {
                    TfamAlter alter = TfamAlter.builder()
                            .alterType("0015")
                            .assetId(newAsset.getId())
                            .reason(saveAlterDto.getReason())
                            .companyId(newAsset.getCompanyId())
                            .year(DateUtils.getYear())
                            .month(DateUtils.getMonth())
                            .date(DateUtils.getDateTime())
                            .oldValue(oldAsset.getAccudepAccountid() != null ? oldAsset.getAccudepAccountid().getVaraccountfullname() : "")
                            .newValue(newAsset.getAccudepAccountid().getVaraccountfullname())
                            .sourceType(sourceType)
                            .build();
                    tfamAlters.add(alter);
                }
            }
            //0016	固定资产减值准备科目
            if (oldAsset.getPredevaluateAccountid() == null || oldAsset.getPredevaluateAccountid().getUqaccountid() == null || !newAsset.getPredevaluateAccountid().getUqaccountid().equals(oldAsset.getPredevaluateAccountid().getUqaccountid())) {
                if (null != newAsset.getPredevaluateAccountid()) {
                    TfamAlter alter = TfamAlter.builder()
                            .alterType("0016")
                            .assetId(newAsset.getId())
                            .reason(saveAlterDto.getReason())
                            .companyId(newAsset.getCompanyId())
                            .year(DateUtils.getYear())
                            .month(DateUtils.getMonth())
                            .date(DateUtils.getDateTime())
                            .oldValue(oldAsset.getPredevaluateAccountid() != null ? oldAsset.getPredevaluateAccountid().getVaraccountfullname() : "")
                            .newValue(newAsset.getPredevaluateAccountid().getVaraccountfullname())
                            .sourceType(sourceType)
                            .build();
                    tfamAlters.add(alter);
                }
            }
            //0017	折旧费用科目
            if (oldAsset.getDepcostAccountid() == null || oldAsset.getDepcostAccountid().getUqaccountid() == null || !newAsset.getDepcostAccountid().getUqaccountid().equals(oldAsset.getDepcostAccountid().getUqaccountid())) {
                if (null != newAsset.getDepcostAccountid()) {
                    TfamAlter alter = TfamAlter.builder()
                            .alterType("0017")
                            .assetId(newAsset.getId())
                            .reason(saveAlterDto.getReason())
                            .companyId(newAsset.getCompanyId())
                            .year(DateUtils.getYear())
                            .month(DateUtils.getMonth())
                            .date(DateUtils.getDateTime())
                            .oldValue(oldAsset.getDepcostAccountid() != null ? oldAsset.getDepcostAccountid().getVaraccountfullname() : "")
                            .newValue(newAsset.getDepcostAccountid().getVaraccountfullname())
                            .sourceType(sourceType)
                            .build();
                    tfamAlters.add(alter);
                }
            }
            //0018	在建工程科目中转
            if (!oldAsset.getIsproject().equals(newAsset.getIsproject())) {
                TfamAlter alter = TfamAlter.builder()
                        .alterType("0018")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(oldAsset.getIsproject().toString())
                        .newValue(newAsset.getIsproject().toString())
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(alter);
            }
            //0019	在建工程科目
            if (oldAsset.getProjectAccountid() == null || oldAsset.getProjectAccountid().getUqaccountid() == null || !newAsset.getProjectAccountid().getUqaccountid().equals(oldAsset.getProjectAccountid().getUqaccountid())) {
                if (null != newAsset.getProjectAccountid()) {
                    TfamAlter alter = TfamAlter.builder()
                            .alterType("0019")
                            .assetId(newAsset.getId())
                            .reason(saveAlterDto.getReason())
                            .companyId(newAsset.getCompanyId())
                            .year(DateUtils.getYear())
                            .month(DateUtils.getMonth())
                            .date(DateUtils.getDateTime())
                            .oldValue(oldAsset.getProjectAccountid() != null ? oldAsset.getProjectAccountid().getVaraccountfullname() : "")
                            .newValue(newAsset.getProjectAccountid().getVaraccountfullname())
                            .sourceType(sourceType)
                            .build();
                    tfamAlters.add(alter);
                }
            }

            //0020	减值准备对方科目
            if (oldAsset.getPredevaluateCoraccountid() == null || oldAsset.getPredevaluateCoraccountid().getUqaccountid() == null || !newAsset.getPredevaluateCoraccountid().getUqaccountid().equals(oldAsset.getPredevaluateCoraccountid().getUqaccountid())) {
                if (null != newAsset.getPredevaluateCoraccountid()) {
                    TfamAlter alter = TfamAlter.builder()
                            .alterType("0020")
                            .assetId(newAsset.getId())
                            .reason(saveAlterDto.getReason())
                            .companyId(newAsset.getCompanyId())
                            .year(DateUtils.getYear())
                            .month(DateUtils.getMonth())
                            .date(DateUtils.getDateTime())
                            .oldValue(oldAsset.getPredevaluateCoraccountid() != null ? oldAsset.getPredevaluateCoraccountid().getVaraccountfullname() : "")
                            .newValue(newAsset.getPredevaluateCoraccountid().getVaraccountfullname())
                            .sourceType(sourceType)
                            .build();
                    tfamAlters.add(alter);
                }
            }
            //0021	税金科目
            if (oldAsset.getTaxAccountid() == null || oldAsset.getTaxAccountid().getUqaccountid() == null || !newAsset.getTaxAccountid().getUqaccountid().equals(oldAsset.getTaxAccountid().getUqaccountid())) {
                if (null != newAsset.getTaxAccountid()) {
                    TfamAlter alter = TfamAlter.builder()
                            .alterType("0021")
                            .assetId(newAsset.getId())
                            .reason(saveAlterDto.getReason())
                            .companyId(newAsset.getCompanyId())
                            .year(DateUtils.getYear())
                            .month(DateUtils.getMonth())
                            .date(DateUtils.getDateTime())
                            .oldValue(oldAsset.getTaxAccountid() != null ? oldAsset.getTaxAccountid().getVaraccountfullname() : "")
                            .newValue(newAsset.getTaxAccountid().getVaraccountfullname())
                            .sourceType(sourceType)
                            .build();
                    tfamAlters.add(alter);
                }
            }
            //0022	原值
            if (oldAsset.getValue().doubleValue() !=
                    newAsset.getValue().doubleValue()) {
                TfamAlter alter = TfamAlter.builder()
                        .alterType("0022")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(String.format("%.2f", oldAsset.getValue().doubleValue()))
                        .newValue(String.format("%.2f", newAsset.getValue().doubleValue()))
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(alter);
            }
            //0023	税率(%)
            if (oldAsset.getRate().doubleValue() != newAsset.getRate().doubleValue()) {
                TfamAlter alter = TfamAlter.builder()
                        .alterType("0023")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(String.format("%.2f", oldAsset.getRate().doubleValue()))
                        .newValue(String.format("%.2f", newAsset.getRate().doubleValue()))
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(alter);
            }
            //0024	税金
            if (oldAsset.getTax().doubleValue() != newAsset.getTax().doubleValue()) {
                TfamAlter alter = TfamAlter.builder()
                        .alterType("0024")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(String.format("%.2f", oldAsset.getTax().doubleValue()))
                        .newValue(String.format("%.2f", newAsset.getTax().doubleValue()))
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(alter);
            }
            //0025	减值准备
            if (oldAsset.getPredevaluate().doubleValue() != newAsset.getPredevaluate().doubleValue()) {
                TfamAlter alter = TfamAlter.builder()
                        .alterType("0025")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(String.format("%.2f", oldAsset.getPredevaluate().doubleValue()))
                        .newValue(String.format("%.2f", newAsset.getPredevaluate().doubleValue()))
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(alter);
            }
            //0026	净值
            if (oldAsset.getNetvalue().doubleValue() != newAsset.getNetvalue().doubleValue()) {
                TfamAlter alter = TfamAlter.builder()
                        .alterType("0026")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(String.format("%.2f", oldAsset.getNetvalue().doubleValue()))
                        .newValue(String.format("%.2f", newAsset.getNetvalue().doubleValue()))
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(alter);
            }
            //0027	残值率(%)
            if (oldAsset.getSalvagerate().doubleValue() != newAsset.getSalvagerate().doubleValue()) {
                TfamAlter alter = TfamAlter.builder()
                        .alterType("0027")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(String.format("%.2f", oldAsset.getSalvagerate().doubleValue()))
                        .newValue(String.format("%.2f", newAsset.getSalvagerate().doubleValue()))
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(alter);
            }
            //0028	残值
            if (oldAsset.getSalvage().doubleValue() != newAsset.getSalvage().doubleValue()) {
                TfamAlter alter = TfamAlter.builder()
                        .alterType("0028")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(String.format("%.2f", oldAsset.getSalvagerate().doubleValue()))
                        .newValue(String.format("%.2f", newAsset.getSalvagerate().doubleValue()))
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(alter);
            }
            //0029	折旧方法
            if (!oldAsset.getDepmethod().getId().equals(newAsset.getDepmethod().getId())) {
                TfamAlter alter = TfamAlter.builder()
                        .alterType("0029")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(oldAsset.getDepmethod().getName())
                        .newValue(newAsset.getDepmethod().getName())
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(alter);
            }
            //0030	预计使用月数
            if (!oldAsset.getNaturemonth().equals(newAsset.getNaturemonth())) {
                TfamAlter alter = TfamAlter.builder()
                        .alterType("0030")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(oldAsset.getNaturemonth().toString())
                        .newValue(newAsset.getNaturemonth().toString())
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(alter);
            }
            //0031	剩余使用月数
            if (!oldAsset.getServicemonth().equals(newAsset.getServicemonth())) {
                TfamAlter alter = TfamAlter.builder()
                        .alterType("0031")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(oldAsset.getServicemonth().toString())
                        .newValue(newAsset.getServicemonth().toString())
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(alter);
            }
            //0032	月折旧率(%)
            if (oldAsset.getDeprate().doubleValue() != newAsset.getDeprate().doubleValue()) {
                TfamAlter alter = TfamAlter.builder()
                        .alterType("0032")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(String.format("%.2f", oldAsset.getDeprate().doubleValue()))
                        .newValue(String.format("%.2f", newAsset.getDeprate().doubleValue()))
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(alter);
            }
            //0033	月折旧额
            if (oldAsset.getDepamount().doubleValue() != newAsset.getDepamount().doubleValue()) {
                TfamAlter alter = TfamAlter.builder()
                        .alterType("0033")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(String.format("%.2f", oldAsset.getDepamount().doubleValue()))
                        .newValue(String.format("%.2f", newAsset.getDepamount().doubleValue()))
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(alter);
            }
            //0034	工作总量
            if (oldAsset.getAllworkloan().doubleValue() != newAsset.getAllworkloan().doubleValue()) {
                TfamAlter alter = TfamAlter.builder()
                        .alterType("0034")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(String.format("%.2f", oldAsset.getAllworkloan().doubleValue()))
                        .newValue(String.format("%.2f", newAsset.getAllworkloan().doubleValue()))
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(alter);
            }
            //0035	工作量单位
            if (!oldAsset.getWorkloanunit().equals(newAsset.getWorkloanunit())) {
                TfamAlter alter = TfamAlter.builder()
                        .alterType("0035")
                        .assetId(newAsset.getId())
                        .reason(saveAlterDto.getReason())
                        .companyId(newAsset.getCompanyId())
                        .year(DateUtils.getYear())
                        .month(DateUtils.getMonth())
                        .date(DateUtils.getDateTime())
                        .oldValue(oldAsset.getWorkloanunit().toString())
                        .newValue(newAsset.getWorkloanunit().toString())
                        .sourceType(sourceType)
                        .build();
                tfamAlters.add(alter);
            }
            //0036	方法
//            if (oldAsset.getWorkloanunit().equals (newAsset.getWorkloanunit()) ){
//                TfamAlter alter = TfamAlter.builder()
//                        .alterType("0036")
//                        .assetId(newAsset.getId())
//                        .reason(saveAlterDto.getReason())
//                        .companyId(newAsset.getCompanyId())
//                        .year(DateUtils.getYear())
//                        .month(DateUtils.getMonth())
//                        .date(DateUtils.getDateTime())
//                        .oldValue(oldAsset.getWorkloanunit().toString())
//                        .newValue(newAsset.getWorkloanunit().toString())
//                        .build();
//                tfamAlters.add(alter);
//            }


        } else if (OperatetAlterTypeEnum.DELETE.getCode().equals(operatetType)) {
            TfamAlter tfamAlter = new TfamAlter();
            tfamAlter.setAlterType("0002");
            tfamAlter.setReason(newAsset.getReduceReason());
            tfamAlter.setDate(DateUtils.getDateTime());
            tfamAlter.setYear(DateUtils.getYear());
            tfamAlter.setMonth(DateUtils.getMonth());
            tfamAlter.setAssetId(newAsset.getId());
            tfamAlter.setOldValue("0.0");
            tfamAlter.setNewValue(newAsset.getValue() == null ? "0.0" : newAsset.getValue().toString());
            tfamAlter.setCompanyId(newAsset.getCompanyId());
            tfamAlter.setSourceType(2);
            tfamAlters.add(tfamAlter);
        }

        if (tfamAlters.size() > 0) {
            for (int i = 0; i < tfamAlters.size(); i++) {
                TfamAlter tfamAlter = tfamAlters.get(i);
                save(tfamAlter);
            }
        }

    }


}
