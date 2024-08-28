package com.geeke.tgl.aging.service;

import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.BaseService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.tgl.aging.dao.ContactsBookDao;
import com.geeke.tgl.aging.entity.Aging;
import com.geeke.utils.EasyPoiUtil;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.constants.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 往来账龄报表Service
 * @author
 * @version
 */

@Service("contactsBookService")
@Transactional(readOnly = true)
public class ContactsBookService extends BaseService {

    @Autowired
    private ContactsBookDao dao;

    @Autowired
    private AgingService agingService;

    /**
     * 查询分页数据
     * @param map 查询参数
     * @return
     */
    public Page<Map<String, Object>> listPage(Map<String, Object> map) {
        map.put("UQACCOUNTSETID", SessionUtils.getUser().getCompanyId());
        // 查询账龄区间设置
        List<Parameter> agingParams = new ArrayList<>();
        List<Aging> agingList = agingService.listAll(agingParams, "");
        if (null == agingList || agingList.size() <= 0 || agingList.size() > 7) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "请完善账龄区间设置，勿超过7个！"));
        }
        int num =  dao.getContactsAccouts(map);
        if(num==0){
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "该科目不是往来科目，请重新选择科目!!!"));
        }
        for (int i = 0; i < agingList.size(); i++) {
            if (i == 0) {
                agingList.get(0).setBeforeAging(0);
            } else {
                agingList.get(i).setBeforeAging(agingList.get(i-1).getBeforeAging() + 1);
            }
        }
        int total = dao.count(map, agingList);
        List<Map<String, Object>> list = null;
        if(total > 0) {
            list = dao.listPage(map, agingList);
        }
        return new Page<Map<String, Object>>(total, list);
    }

    public int exportListAll(Map<String, Object> map, HttpServletResponse response) {
        map.put("UQACCOUNTSETID",SessionUtils.getUser().getCompanyId());
        // 查询账龄区间设置
        List<Parameter> agingParams = new ArrayList<>();
        List<Aging> agingList = agingService.listAll(agingParams, "");
        if (null == agingList || agingList.size() <= 0 || agingList.size() > 7) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "请完善账龄区间设置，勿超过7个！"));
        }
        List<Map<String, Object>>  mapList = new ArrayList<>();
        Map<String, String> title = new LinkedHashMap<>();
        title.put("客户档案名称","ledgername");
        title.put("方向","direct");
        title.put("期初余额","begphase");
        title.put("本期借方","perioddebit");
        title.put("本期贷方","periodcredit");
        title.put("期末余额","endphase");
        for (int i = 0; i < agingList.size(); i++) {
            if (i == 0) { // 设置查询条件
                agingList.get(0).setBeforeAging(0);
            } else {
                agingList.get(i).setBeforeAging(agingList.get(i-1).getBeforeAging() + 1);
            }
            title.put(agingList.get(i).getBegday() + i,"count" + i);
        }
        // 获取导出列表
        List<Map<String, Object>> list = dao.listAll(map, agingList);
        for(int i=0; i < list.size(); i++){
            Map<String,Object> infoMap = new HashMap<>();
            infoMap.put("ledgername", list.get(i).get("ledgername"));
            infoMap.put("direct", list.get(i).get("direct"));
            infoMap.put("begphase", list.get(i).get("begphase"));
            infoMap.put("perioddebit", list.get(i).get("perioddebit"));
            infoMap.put("periodcredit", list.get(i).get("periodcredit"));
            infoMap.put("endphase", list.get(i).get("endphase"));
            for (int j = 0; j < agingList.size(); j++) {
                infoMap.put("count" + j, list.get(i).get("count" + j));
            }
            mapList.add(infoMap);
        }
        EasyPoiUtil.DownloadExcel(response,"往来账龄表",mapList,title);
        return list.size();
    }
}
