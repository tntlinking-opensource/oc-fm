package com.geeke.fixasset.depmethod.service;

import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.fixasset.depmethod.dao.TfamDepmethodDao;
import com.geeke.fixasset.depmethod.entity.TfamDepmethod;
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
 * 资产类别表depmethodService
 * @author
 * @version
 */

@Service("tfamDepmethodService")
@Transactional(readOnly = true)
public class TfamDepmethodService extends CrudService<TfamDepmethodDao, TfamDepmethod> {}
