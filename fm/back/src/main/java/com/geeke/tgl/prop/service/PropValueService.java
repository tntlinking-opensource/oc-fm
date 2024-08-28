package com.geeke.tgl.prop.service;

import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.tgl.prop.dao.PropValueDao;
import com.geeke.tgl.prop.entity.PropValue;
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
 * 自动出纳配置Service
 * @author
 * @version
 */

@Service("propValueService")
@Transactional(readOnly = true)
public class PropValueService extends CrudService<PropValueDao, PropValue> {

    /**
     * 根据公司ID查询 limit 1
     * @param companyId
     * @return
     */
    public PropValue getByCompany(String companyId) {
        return dao.getByCompany(companyId);
    }
}
