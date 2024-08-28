package com.geeke.fixasset.asset.service;

import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.fixasset.asset.dao.TfamAccessoryDao;
import com.geeke.fixasset.asset.entity.TfamAccessory;
import com.geeke.utils.Reflections;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 卡片管理Service
 * @author
 * @version
 */

@Service("tfamAccessoryService")
@Transactional(readOnly = true)
public class TfamAccessoryService extends CrudService<TfamAccessoryDao, TfamAccessory> {}
