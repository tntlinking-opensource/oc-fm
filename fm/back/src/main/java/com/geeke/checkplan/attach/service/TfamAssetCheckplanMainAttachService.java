package com.geeke.checkplan.attach.service;

import com.geeke.checkplan.attach.dao.TfamAssetCheckplanMainAttachDao;
import com.geeke.checkplan.attach.entity.TfamAssetCheckplanMainAttach;
import com.geeke.checkplan.checkplanmain.entity.TfamAssetCheckplanMain;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.fixasset.asset.dto.ConditionDto;
import com.geeke.utils.ResultUtil;
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
 * 盘点计划附件Service
 * @author
 * @version
 */

@Service("tfamAssetCheckplanMainAttachService")
@Transactional(readOnly = true)
public class TfamAssetCheckplanMainAttachService
    extends CrudService<TfamAssetCheckplanMainAttachDao, TfamAssetCheckplanMainAttach> {
    public List<TfamAssetCheckplanMainAttach> selectByMainId(String mainId) {
        return this.dao.selectByMainId(mainId);
    }

    public void deleteByMainId(String mainId) {
        this.dao.deleteByMainId(mainId);
    }
}
