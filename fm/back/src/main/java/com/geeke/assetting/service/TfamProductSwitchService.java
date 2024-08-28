package com.geeke.assetting.service;

import com.geeke.assetting.dao.TfamProductSwitchDao;
import com.geeke.assetting.entity.TfamProductSwitch;
import com.geeke.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 产品开关Service
 * @author
 * @version
 */

@Service("tfamProductSwitchService")
@Transactional(readOnly = true)
public class TfamProductSwitchService extends CrudService<TfamProductSwitchDao, TfamProductSwitch> {}
