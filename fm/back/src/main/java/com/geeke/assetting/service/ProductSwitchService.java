package com.geeke.assetting.service;

import com.alibaba.fastjson.JSONObject;
import com.geeke.assetting.dao.ProductSwitchDao;
import com.geeke.assetting.dao.TfamProductSwitchDao;
import com.geeke.assetting.entity.TfamProductSwitch;
import com.geeke.common.data.Parameter;
import com.geeke.utils.SessionUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * li
 *
 * @Date 10:18
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 **/
@Service("productSwitchService")
@Transactional(readOnly = true)
public class ProductSwitchService {
    @Autowired
    private ProductSwitchDao dao;
    @Autowired
    private TfamProductSwitchDao tfamProductSwitchDao;
    @Autowired
    private TfamProductSwitchService tfamProductSwitchService;

    public TfamProductSwitch queryProductSwitch(){

        String companyId = SessionUtils.getUser().getCompanyId().toString();
        List<Parameter> params = null;
        params = Lists.newArrayList();
        params.add(new Parameter("company_id", "=",companyId));
        List<TfamProductSwitch> tfamProductSwitches = tfamProductSwitchService.listAll(params, "");

        if (tfamProductSwitches.size() == 0) {
            /**
             * 初始化默认记录
             */
            TfamProductSwitch tfamProductSwitch = new TfamProductSwitch();
            tfamProductSwitch.setRemindSwitch(1);
            tfamProductSwitch.setIndexWarn(1);
            tfamProductSwitch.setEmailWarn(1);
            tfamProductSwitch.setLastMonth(1);
            tfamProductSwitch.setCompanyId(companyId);
            addSwitchData(tfamProductSwitch);
            tfamProductSwitches.add(tfamProductSwitch);
        }
        return tfamProductSwitches.get(0);
    }
    public void addSwitchData(TfamProductSwitch cdtMap) {
        tfamProductSwitchService.save(cdtMap);
    }

    public void updateSwitch(JSONObject cdtMap) {
        //iesc/fam/assetsetting/productswitch/productswitch.jsp
        Integer remindSwitch = cdtMap.getInteger("remindSwitch");
        String companyId = SessionUtils.getUser().getCompanyId().toString();
        List<Parameter> params = null;
        params = Lists.newArrayList();
        params.add(new Parameter("company_id", "=",companyId));
        List<TfamProductSwitch> tfamProductSwitches = tfamProductSwitchService.listAll(params, "");
        TfamProductSwitch tfamProductSwitch = tfamProductSwitches.get(0);
        tfamProductSwitch.setRemindSwitch(remindSwitch);
        addSwitchData(tfamProductSwitch);
    }
    /**
     * 修改相关参数
     * @param cdtMap
     */

    public void updateParam(JSONObject cdtMap) {
        String companyId = SessionUtils.getUser().getCompanyId().toString();
        List<Parameter> params = null;
        params = Lists.newArrayList();
        params.add(new Parameter("company_id", "=",companyId));
        List<TfamProductSwitch> tfamProductSwitches = tfamProductSwitchService.listAll(params, "");
        TfamProductSwitch tfamProductSwitch = tfamProductSwitches.get(0);

        tfamProductSwitch.setRemindSwitch(cdtMap.getInteger("remindSwitch"));
        tfamProductSwitch.setLastMonth(cdtMap.getInteger("lastMonth"));
        tfamProductSwitch.setIndexWarn(cdtMap.getInteger("indexWarn"));
        tfamProductSwitch.setEmailWarn(cdtMap.getInteger("emailWarn"));
        addSwitchData(tfamProductSwitch);
    }
}
