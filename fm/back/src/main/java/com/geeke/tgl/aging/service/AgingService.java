package com.geeke.tgl.aging.service;

import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.tgl.aging.dao.AgingDao;
import com.geeke.tgl.aging.entity.Aging;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 账龄区间Service
 * @author
 * @version
 */

@Service("agingService")
@Transactional(readOnly = true)
public class AgingService extends CrudService<AgingDao, Aging> {

    @Override
    public Page<Aging> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
        // 租户过滤
        String filter = "{'columnName':'tenant_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        Page<Aging> result = super.listPage(super.addFilter(parameters, filter), offset, limit, orderby);
        return result;
    }

    @Override
    public List<Aging> listAll(List<Parameter> parameters, String orderby) {
        // 租户过滤
        String filter = "{'columnName':'tenant_id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}";
        PageRequest pageRequest = new PageRequest(super.addFilter(parameters, filter), orderby);
        List<Aging> result = dao.listAll(pageRequest);
        return result;
    }

    /**
     * 批量添加或者修改
     * @param entitys
     * @return
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public List<String> bulkInsertAndUpdate(List<Aging> entitys) {
        List<String> ids = Lists.newArrayList();
        if (entitys.size() > 7) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "账龄区间设置添加请勿超过7个！"));
        }
        // 查询数据库中已存在的集合
        List<Parameter> params = Lists.newArrayList();
        List<Aging> list_aging = dao.listAll(new PageRequest(params));

        List<Aging> deletes = Lists.newArrayList(); // 删除列表
        List<Aging> inserts = Lists.newArrayList(); // 添加列表
        List<Aging> updates = Lists.newArrayList(); // 更新列表

        for (Aging agingSaved : list_aging) {
            boolean found = false;
            for (Aging aging : entitys) {
                if (agingSaved.getId().equals(aging.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                agingSaved.setOldUpdateDate(agingSaved.getUpdateDate());
                deletes.add(agingSaved);
            }
        }
        if (deletes.size() > 0) {
            dao.bulkDelete(deletes);
        }
        for (Aging aging : entitys) {
            if (StringUtils.isBlank(aging.getId())) {
                aging.preInsert();
                inserts.add(aging);
                ids.add(aging.getId());
            } else {
                aging.preUpdate();
                updates.add(aging);
                ids.add(aging.getId());
            }
        }
        if (updates.size() > 0) {
            dao.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            dao.bulkInsert(inserts);
        }
        return ids;
    }
}
