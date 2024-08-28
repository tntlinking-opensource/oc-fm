package com.geeke.assetting.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.assetting.entity.TfamAssetManagerDept;
import com.geeke.assetting.service.TfamAssetManagerDeptService;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import java.util.List;
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
 * 卡片所属部门Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/assetting/tfamAssetManagerDept")
public class TfamAssetManagerDeptController extends BaseController {

    @Autowired
    private TfamAssetManagerDeptService tfamAssetManagerDeptService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TfamAssetManagerDept entity = tfamAssetManagerDeptService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<TfamAssetManagerDept> result = tfamAssetManagerDeptService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<TfamAssetManagerDept> result = tfamAssetManagerDeptService.listAll(
            searchParams.getParams(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody TfamAssetManagerDept entity) {
        String id = tfamAssetManagerDeptService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TfamAssetManagerDept entity) {
        int rows = tfamAssetManagerDeptService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<TfamAssetManagerDept> entitys) {
        List<String> ids = tfamAssetManagerDeptService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<TfamAssetManagerDept> entitys) {
        List<String> ids = tfamAssetManagerDeptService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<TfamAssetManagerDept> entitys) {
        int rows = tfamAssetManagerDeptService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping("/adddept")
    public ResponseEntity<JSONObject> adddept(@RequestBody String jsonCondition) {
        String companyId = SessionUtils.getUser().getCompanyId().toString();
        JSONObject jsonObject = JSONObject.parseObject(jsonCondition);
        jsonObject.put("companyId", companyId);
        tfamAssetManagerDeptService.addAssetManagerDept(jsonObject);
        return ResponseEntity.ok(ResultUtil.successJson(""));

    }
}
