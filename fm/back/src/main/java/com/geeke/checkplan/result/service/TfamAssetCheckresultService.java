package com.geeke.checkplan.result.service;

import com.geeke.checkplan.result.dao.TfamAssetCheckresultDao;
import com.geeke.checkplan.result.entity.TfamAssetCheckresult;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
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
 * 盘点结果Service
 * @author
 * @version
 */

@Service("tfamAssetCheckresultService")
@Transactional(readOnly = true)
public class TfamAssetCheckresultService extends CrudService<TfamAssetCheckresultDao, TfamAssetCheckresult> {}
