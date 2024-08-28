package com.geeke.tgl.global.service;

import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.sys.entity.Action;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.sys.entity.DictItem;
import com.geeke.sys.entity.DictType;
import com.geeke.ten.entity.Tenant;
import com.geeke.tgl.global.dao.GlobalPeriodsDao;
import com.geeke.tgl.global.entity.GlobalPeriods;
import com.geeke.utils.Reflections;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * 会计期Service
 * @author
 * @version
 */

@Service("globalPeriodsService")
@Transactional(readOnly = true)
public class GlobalPeriodsService extends CrudService<GlobalPeriodsDao, GlobalPeriods> {

    @Override
    public Page<GlobalPeriods> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
        // 租户过滤
        String filter = "{columnName: 'UQGLOBALPERIODID', queryType: '!=', value: '00000000-0000-0000-0000-000000000000'},{'columnName':'tenant_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        Page<GlobalPeriods> result = super.listPage(super.addFilter(parameters, filter), offset, limit, orderby);
        return result;
    }

    @Override
    public List<GlobalPeriods> listAll(List<Parameter> parameters, String orderby) {
        // 租户过滤
        String filter = "{columnName: 'UQGLOBALPERIODID', queryType: '!=', value: '00000000-0000-0000-0000-000000000000'},{'columnName':'tenant_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        PageRequest pageRequest = new PageRequest(super.addFilter(parameters, filter), orderby);
        List<GlobalPeriods> result = dao.listAll(pageRequest);
        return result;
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    @Override
    public GlobalPeriods save(GlobalPeriods globalPeriods) {
        if (StringUtils.isBlank(globalPeriods.getId())) { // 新增
            globalPeriods.setUqglobalperiodid(UUID.randomUUID().toString());
        }
        // 约束条件处理
        Map<String, String> colMaps = Maps.newHashMap();
        // 会计期年月不可重复
        colMaps.clear();
        colMaps.put("tenant_id", "tenantId");
        colMaps.put("intyearmonth", "intyearmonth");
        colMaps.put("del_flag", "delFlag");
        if (exists(dao, globalPeriods, colMaps)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "保存失败！错误原因: 会计期年月已经存在"));
        }
        // 会计名称不可重复
        colMaps.clear();
        colMaps.put("tenant_id", "tenantId");
        colMaps.put("varname", "varname");
        colMaps.put("del_flag", "delFlag");
        if (exists(dao, globalPeriods, colMaps)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "保存失败！错误原因: 会计期名称已经存在"));
        }
        // 判断该租户是否是首次添加数据（首次添加需要添加两条数据）
        PageRequest pageRequest;
        List<Parameter> params = Lists.newArrayList();
        params.add(new Parameter("tenant_id", "=", globalPeriods.getTenantId()));
        pageRequest = new PageRequest(params);
        int count = dao.count(pageRequest);
        if (count <= 0) { // 该租户首次添加
            GlobalPeriods firstEntity = new GlobalPeriods();
            BeanUtils.copyProperties(globalPeriods, firstEntity);
            firstEntity.setUqglobalperiodid("00000000-0000-0000-0000-000000000000");
            super.save(firstEntity);
        }
        return super.save(globalPeriods);
    }

    /**
     * 根据条件查询会计期
     * @param parameters
     * @return
     */
    public List<GlobalPeriods> selectPeriod(List<Parameter> parameters) {
        return dao.selectPeriod(new PageRequest(parameters, ""));
    }
}
