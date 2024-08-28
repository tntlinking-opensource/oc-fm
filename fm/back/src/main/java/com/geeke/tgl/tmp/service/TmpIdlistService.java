package com.geeke.tgl.tmp.service;

import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.ten.entity.Tenant;
import com.geeke.tgl.global.entity.GlobalPeriods;
import com.geeke.tgl.tmp.dao.TmpIdlistDao;
import com.geeke.tgl.tmp.entity.TmpIdlist;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 利润结转科目配置Service
 * @author
 * @version
 */

@Service("tmpIdlistService")
@Transactional(readOnly = true)
public class TmpIdlistService extends CrudService<TmpIdlistDao, TmpIdlist> {

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    @Override
    public TmpIdlist save(TmpIdlist tmpIdlist) {
        // 根据id、tenant_id（公司）判断科目配置是否已存在
        PageRequest pageRequest;
        List<Parameter> params = Lists.newArrayList();
        params.add(new Parameter("tenant_id", "=", tmpIdlist.getTenantId()));
        params.add(new Parameter("id", "=", tmpIdlist.getId()));
        pageRequest = new PageRequest(params);
        int count = dao.count(pageRequest);
        if (count > 0) { // //存在 使用update
            dao.update(tmpIdlist);
        } else { //不存在 使用insert 一般是第一次
            dao.insert(tmpIdlist);
        }
        return tmpIdlist;
    }
}
