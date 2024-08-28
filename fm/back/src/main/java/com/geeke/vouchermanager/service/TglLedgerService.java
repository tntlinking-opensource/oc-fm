package com.geeke.vouchermanager.service;

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
import com.geeke.vouchermanager.dao.TglLedgerDao;
import com.geeke.vouchermanager.entity.TglLedger;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 分户Service
 * @author
 * @version
 */

@Service("tglLedgerService")
@Transactional(readOnly = true)
public class TglLedgerService extends CrudService<TglLedgerDao, TglLedger> {

    /**
     * 查询分户、分户初期余额集合（分页）
     * @param parameters
     * @param offset
     * @param limit
     * @param orderby
     * @return
     */
    public Page<TglLedger> listCompanyLedgerByLedgety(List<Parameter> parameters, int offset, int limit, String orderby) {
        Map<String, Object> map = new HashMap<>();
        map.put("offset", offset);
        map.put("limit", limit);
        map.put("orderby", orderby);
        for (int i = 0; i < parameters.size(); i++) {
            if (("UQCOMPANYID").equals(parameters.get(i).getColumnName())) {
                map.put("UQCOMPANYID", parameters.get(i).getValue()); // 公司
            }
            if (("UQACCOUNTID").equals(parameters.get(i).getColumnName())) {
                map.put("UQACCOUNTID", parameters.get(i).getValue()); // 科目
            }
            if (("UQLEDGETYPEID").equals(parameters.get(i).getColumnName())) {
                map.put("UQLEDGETYPEID", parameters.get(i).getValue()); // 分户类别
            }
        }
        int total = dao.countAndCompanyLedger(map);
        List<TglLedger> list = null;
        if(total > 0) {
            list = dao.listCompanyLedgerByLedgety(map);
        }
        return new Page<TglLedger>(total, list);
    }
}
