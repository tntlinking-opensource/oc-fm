package com.geeke.vouchermanager.service;

import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.vouchermanager.dao.TglAccountsXjllDao;
import com.geeke.vouchermanager.entity.TglAccountsXjll;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 现金流量表Service
 * @author
 * @version
 */

@Service("tglAccountsXjllService")
@Transactional(readOnly = true)
public class TglAccountsXjllService extends CrudService<TglAccountsXjllDao, TglAccountsXjll> {}
