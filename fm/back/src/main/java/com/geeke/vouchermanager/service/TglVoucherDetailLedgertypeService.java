package com.geeke.vouchermanager.service;

import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.vouchermanager.dao.TglVoucherDetailLedgertypeDao;
import com.geeke.vouchermanager.entity.TglVoucherDetailLedgertype;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 凭证分录分户类别表Service
 * @author
 * @version
 */

@Service("tglVoucherDetailLedgertypeService")
@Transactional(readOnly = true)
public class TglVoucherDetailLedgertypeService extends CrudService<TglVoucherDetailLedgertypeDao, TglVoucherDetailLedgertype> {}
