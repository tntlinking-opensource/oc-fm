package com.geeke.assetting.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.assetting.entity.TfamAssetManager;
import com.geeke.assetting.service.TfamAssetManagerService;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.fixasset.changeway.entity.TfamChangeWay;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 卡片管理Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/assetting/tfamAssetManager")
public class TfamAssetManagerController extends BaseController {

    @Autowired
    private TfamAssetManagerService tfamAssetManagerService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TfamAssetManager entity = tfamAssetManagerService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<TfamAssetManager> result = tfamAssetManagerService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<TfamAssetManager> result = tfamAssetManagerService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }
    @PostMapping(value = "listmenu/{id}")
    public ResponseEntity<JSONObject> listAssetManagerMenu(@PathVariable("id") String id) {
        JSONObject jsonObject = new JSONObject();
        String companyId = SessionUtils.getUser().getCompanyId().toString();
        jsonObject.put("companyId",companyId);
        jsonObject.put("userid",id);
        List<Map<String, Object>> maps = tfamAssetManagerService.listManagerMenu(jsonObject);
        JSONObject jsonObject1 = new JSONObject();
        for (int i = 0; i < maps.size(); i++) {
            jsonObject1.put(String.valueOf(maps.get(i).get("switch_name")),maps.get(i).get("open"));
        }
        return ResponseEntity.ok(ResultUtil.successJson(jsonObject1));
    }
    @PostMapping("/addmenu")
    public ResponseEntity<JSONObject> addAssetManagerMenu(@RequestBody String jsonCondition) {
        String companyId = SessionUtils.getUser().getCompanyId().toString();
        JSONObject jsonObject = JSONObject.parseObject(jsonCondition);
        jsonObject.put("companyId", companyId);
        tfamAssetManagerService.addAssetManagerMenu(jsonObject);
        return ResponseEntity.ok(ResultUtil.successJson(""));

    }
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody TfamAssetManager entity) {
        String companyId = com.geeke.utils.SessionUtils.getUser().getCompanyId().toString();
        List<Parameter> params = null;
        params = Lists.newArrayList();
        params.add(new Parameter("company_id", "=",companyId));
        params.add(new Parameter("userid", "=",entity.getUserid()));
        List<TfamAssetManager> tfamProductSwitches = tfamAssetManagerService.listAll(params, "");
        if (tfamProductSwitches.size()==0){
            String id = tfamAssetManagerService.save(entity).getId();
            return ResponseEntity.ok(ResultUtil.successJson(id));

        }else {
            return ResponseEntity.ok(ResultUtil.successJson("该人员已经存在"));
        }
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TfamAssetManager entity) {
        int rows = tfamAssetManagerService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<TfamAssetManager> entitys) {
        List<String> ids = tfamAssetManagerService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<TfamAssetManager> entitys) {
        List<String> ids = tfamAssetManagerService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<TfamAssetManager> entitys) {
        int rows = tfamAssetManagerService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

}
