package com.geeke.assetting.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.assetting.entity.TfamProductSwitch;
import com.geeke.assetting.service.ProductSwitchService;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.constants.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * li
 *
 * @Date 10:11
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
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequestMapping("/fam/assetting/productswitch")
public class ProductSwitchController {
    @Autowired
    private ProductSwitchService bp;
    @RequestMapping("/queryAuth")
    @ResponseBody
    public ResponseEntity queryAuthData(HttpSession httpSession){
        try {
            TfamProductSwitch periodList = bp.queryProductSwitch();
            return ResponseEntity.ok(ResultUtil.successJson(periodList));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }
    /**
     * 修改产品开关状态
     * @return
     */
    @RequestMapping("/updateSwitch")
    @ResponseBody
    public ResponseEntity updateSwitch(@RequestBody String jsonCondition){
        JSONObject cdtMap = JSONObject.parseObject(jsonCondition);
        bp.updateSwitch(cdtMap);
        return ResponseEntity.ok(ResultUtil.successJson());
    }
    /**
     * 修改开关中的参数
     * @return
     */
    @RequestMapping("/updateParam")
    @ResponseBody
    public ResponseEntity updateParam(@RequestBody String jsonCondition){
        JSONObject cdtMap = JSONObject.parseObject(jsonCondition);
        bp.updateParam(cdtMap);
        return ResponseEntity.ok(ResultUtil.successJson());
    }
}
