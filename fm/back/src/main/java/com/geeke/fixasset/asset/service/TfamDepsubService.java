package com.geeke.fixasset.asset.service;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.fixasset.asset.dao.TfamDepsubDao;
import com.geeke.fixasset.asset.entity.TfamDepsub;
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

@Service("tfamDepsubService")
@Transactional(readOnly = true)
public class TfamDepsubService extends CrudService<TfamDepsubDao, TfamDepsub> {
    public Page<JSONObject> projinfo(List<Parameter> parameters, int offset, int limit, String orderby)
    {
        PageRequest pageRequest = new PageRequest(offset, limit, parameters, orderby);
        JSONObject jsonObject = new JSONObject();
        for (Parameter parameter : parameters) {
            jsonObject.put(parameter.getColumnName(),parameter.getValue());
        }
        int total = dao.getInfoDatePageCount(jsonObject);
        List<JSONObject> list = null;
        if(total > 0) {
            list = dao.getInfoDatePagePage(jsonObject);
            for (int i = 0; i < list.size(); i++)
            {
                JSONObject entityMap = list.get(i);
                List<JSONObject> assetId = this.dao.getMaxDateVoucherid(entityMap.getString("asset_id"));
                JSONObject maxDateVoucherid = new JSONObject();
                if(assetId.size( )>0 )
                {
                    maxDateVoucherid = assetId.get(0);
                }else
                {
                    maxDateVoucherid.put("voucherid","");
                    maxDateVoucherid.put("intvouchernum","");
                }
                entityMap.putAll(maxDateVoucherid);
            }
        }
        return new Page<JSONObject>(total, list);
    }
}
