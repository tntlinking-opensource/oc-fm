package com.geeke.cardasset.supplementreduce.service;

import com.geeke.cardasset.supplementreduce.dao.TfamDepmethodOldDao;
import com.geeke.cardasset.supplementreduce.entity.TfamDepmethodOld;
import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
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
 * 补提冲减Service
 * @author
 * @version
 */

@Service("tfamDepmethodOldService")
@Transactional(readOnly = true)
public class TfamDepmethodOldService extends CrudService<TfamDepmethodOldDao, TfamDepmethodOld> {

}
