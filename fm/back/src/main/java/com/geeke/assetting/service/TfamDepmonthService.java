package com.geeke.assetting.service;

import com.geeke.assetting.dao.TfamDepmonthDao;
import com.geeke.assetting.entity.TfamDepmonth;
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
 * 月折旧表Service
 * @author
 * @version
 */

@Service("tfamDepmonthService")
@Transactional(readOnly = true)
public class TfamDepmonthService extends CrudService<TfamDepmonthDao, TfamDepmonth> {}
