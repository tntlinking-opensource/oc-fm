package com.geeke.checkplan.image.service;

import com.geeke.admin.entity.User;
import com.geeke.checkplan.attach.entity.TfamAssetCheckplanMainAttach;
import com.geeke.checkplan.checkplanmain.entity.TfamAssetCheckplanMain;
import com.geeke.checkplan.image.dao.TfamAssetCheckimageDao;
import com.geeke.checkplan.image.entity.TfamAssetCheckimage;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.fixasset.asset.dto.ConditionDto;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 资产图片Service
 * @author
 * @version
 */

@Service("tfamAssetCheckimageService")
@Transactional(readOnly = true)
public class TfamAssetCheckimageService extends CrudService<TfamAssetCheckimageDao, TfamAssetCheckimage> {
    public Integer getNum(String attachCategory, String mainId) {

        return this.dao.getNum(attachCategory,mainId);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void addAttachInfo(ConditionDto conditionDto) {
        User user = SessionUtils.getUser();

        TfamAssetCheckimage image = new TfamAssetCheckimage();

        image.setMainId(conditionDto.getMainId());
        TfamAssetCheckplanMainAttach attach = new TfamAssetCheckplanMainAttach();
        attach.setId(conditionDto.getAttachCategory());
        image.setAttachCategory(attach);
        image.setCardId(conditionDto.getCardId());
        image.setCardNo(conditionDto.getCardNo());
        image.setCompanyId(user.getCompanyId());
        image.setImagePath(conditionDto.getImagePath());
        image.setCreateReason(conditionDto.getReason());
        image.setOriginalfilename(conditionDto.getOriginalFilename());

        this.dao.insert(image);
    }
}
