package com.geeke.cardasset.credit.service;

import com.geeke.cardasset.credit.dao.TfamDepfutureDao;
import com.geeke.cardasset.credit.entity.TfamDepfuture;
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
 * 资产折旧表Service
 * @author
 * @version
 */

@Service("tfamDepfutureService")
@Transactional(readOnly = true)
public class TfamDepfutureService extends CrudService<TfamDepfutureDao, TfamDepfuture> {}
