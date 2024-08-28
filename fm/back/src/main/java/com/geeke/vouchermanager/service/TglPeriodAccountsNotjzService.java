package com.geeke.vouchermanager.service;

import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.vouchermanager.dao.TglPeriodAccountsNotjzDao;
import com.geeke.vouchermanager.entity.TglPeriodAccountsNotjz;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 非结转科目发生数汇总表Service
 * @author
 * @version
 */

@Service("tglPeriodAccountsNotjzService")
@Transactional(readOnly = true)
public class TglPeriodAccountsNotjzService extends CrudService<TglPeriodAccountsNotjzDao, TglPeriodAccountsNotjz> {}
