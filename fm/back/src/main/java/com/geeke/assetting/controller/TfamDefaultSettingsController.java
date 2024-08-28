package com.geeke.assetting.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.assetting.entity.TfamDefaultSettings;
import com.geeke.assetting.resdto.TfamDefaultSettingsDto;
import com.geeke.assetting.service.TfamDefaultSettingsService;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import java.util.List;
import java.util.Map;

import com.geeke.utils.constants.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 资产科目方案配置Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/assetting/tfamDefaultSettings")
public class TfamDefaultSettingsController extends BaseController {

    @Autowired
    private TfamDefaultSettingsService tfamDefaultSettingsService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TfamDefaultSettings entity = tfamDefaultSettingsService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<TfamDefaultSettings> result = tfamDefaultSettingsService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<TfamDefaultSettings> result = tfamDefaultSettingsService.listAll(
            searchParams.getParams(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listDefault")
    public ResponseEntity<JSONObject> listDefault(@RequestBody SearchParams searchParams) {
        List<TfamDefaultSettingsDto> result = tfamDefaultSettingsService.listDefault(
                searchParams.getParams(),
                searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody TfamDefaultSettings entity) {
        String id = tfamDefaultSettingsService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TfamDefaultSettings entity) {
        int rows = tfamDefaultSettingsService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<TfamDefaultSettings> entitys) {
        List<String> ids = tfamDefaultSettingsService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<TfamDefaultSettings> entitys) {
        List<String> ids = tfamDefaultSettingsService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<TfamDefaultSettings> entitys) {
        int rows = tfamDefaultSettingsService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
    @PostMapping(value="defaultsettingsave")
    public ResponseEntity<JSONObject> defaultsettingsave(@RequestBody String entity) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(entity);
        tfamDefaultSettingsService.defaultsettingsave(jsonObject);
        return ResponseEntity.ok(ResultUtil.successJson());
    }
    @RequestMapping({"/getData"})
    @ResponseBody
    public ResponseEntity<JSONObject> getData(@RequestParam(required = false) String jsonCondition) {
        try {
            //页面上加载需要的内容不需要参数


            List<Map<String, Object>> list = this.tfamDefaultSettingsService.getData();
            JSONObject jsonObject = new JSONObject();
            for (Map<String, Object> stringObjectMap : list) {
                jsonObject.put(stringObjectMap.get("item").toString(),stringObjectMap.get("name"));
                jsonObject.put(stringObjectMap.get("item").toString()+"ID",stringObjectMap.get("value"));
                if (stringObjectMap.get("item").toString().equals("rate")){
                    jsonObject.put(stringObjectMap.get("item").toString(),stringObjectMap.get("value"));
                }else if (stringObjectMap.get("item").toString().equals("sys_startdate")){
                    jsonObject.put(stringObjectMap.get("item").toString(),stringObjectMap.get("value"));
                }else if (stringObjectMap.get("item").toString().equals("depreciation_time")){
                    jsonObject.put(stringObjectMap.get("item").toString(),stringObjectMap.get("value"));
                }
            }
            return ResponseEntity.ok(ResultUtil.successJson(jsonObject));
        } catch (Exception var6) {
            var6.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,var6.getMessage()));
        }
    }
}
