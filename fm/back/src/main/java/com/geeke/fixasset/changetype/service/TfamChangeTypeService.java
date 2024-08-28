package com.geeke.fixasset.changetype.service;

import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.fixasset.changetype.dao.TfamChangeTypeDao;
import com.geeke.fixasset.changetype.entity.TfamChangeType;
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
 * 资产变动方式字典Service
 * @author
 * @version
 */

@Service("tfamChangeTypeService")
@Transactional(readOnly = true)
public class TfamChangeTypeService extends CrudService<TfamChangeTypeDao, TfamChangeType> {}
