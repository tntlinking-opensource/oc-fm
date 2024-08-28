package com.geeke.fixasset.asset.service;

import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.fixasset.asset.dao.TfamAsset2qrcodeDao;
import com.geeke.fixasset.asset.dao.TfamAssetDao;
import com.geeke.fixasset.asset.dto.ConditionDto;
import com.geeke.fixasset.asset.entity.TfamAsset;
import com.geeke.fixasset.asset.entity.TfamAsset2qrcode;
import com.geeke.utils.QRUtil;
import com.geeke.utils.Reflections;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 卡片管理Service
 * @author
 * @version
 */

@Service("tfamAsset2qrcodeService")
@Transactional(readOnly = true)
public class TfamAsset2qrcodeService extends CrudService<TfamAsset2qrcodeDao, TfamAsset2qrcode> {

    @Autowired
    private TfamAssetDao tfamAssetDao;


    @Transactional(readOnly = false)
    public List<TfamAsset2qrcode> getQRCode(List<String> cardIds, String qrcode_path) {
        List<TfamAsset2qrcode> qrcodeList = new ArrayList<>();
        if(cardIds !=null && cardIds.size()>0){
            cardIds.stream().forEach(cardId->{
                TfamAsset2qrcode qrcode =  this.dao.getQRCode(cardId);
                if (qrcode == null){
                    //新增一个
                    TfamAsset tfamAsset = tfamAssetDao.get(cardId);
                    ConditionDto conditionDto = new ConditionDto();
                    conditionDto.setId(tfamAsset.getId());
                    conditionDto.setAssetName(tfamAsset.getAssetName());
                    conditionDto.setAssetCode(tfamAsset.getAssetCode());
                    conditionDto.setCardNo(tfamAsset.getCardCode());
                    conditionDto.setCardModel(tfamAsset.getCardModel());
                    conditionDto.setCompanyId(tfamAsset.getCompanyId());
                    String qrCodePath = null;
                    try {
                        qrCodePath = QRUtil.createQRCode(qrcode_path, conditionDto);
                    } catch (Exception e) {
                        logger.error("二维码生成错误,assetId:{},assetName:{},e:{}",tfamAsset.getId(),tfamAsset.getAssetName(),e.getMessage());
                        e.printStackTrace();
                    }

                    this.dao.insert(buildObject(tfamAsset,qrCodePath));
                    qrcode =  this.dao.getQRCode(cardId);
                }

                qrcodeList.add(qrcode);
            });
        }
        return qrcodeList;
    }

    private TfamAsset2qrcode buildObject(TfamAsset tfamAsset,String qrCodePath){
        TfamAsset2qrcode qrcode = new TfamAsset2qrcode();
        qrcode.setCard(tfamAsset);
        qrcode.setQrcodePath(qrCodePath);

        return qrcode;
    }
}
