package com.geeke.checkplan.dept.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.checkplan.dept.entity.TfamAssetCheckplanMainDept;
import com.geeke.checkplan.dept.service.TfamAssetCheckplanMainDeptService;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
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
 * 资产盘点部门Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/checkplan/dept/tfamAssetCheckplanMainDept")
public class TfamAssetCheckplanMainDeptController extends BaseController {

    @Autowired
    private TfamAssetCheckplanMainDeptService tfamAssetCheckplanMainDeptService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TfamAssetCheckplanMainDept entity = tfamAssetCheckplanMainDeptService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<TfamAssetCheckplanMainDept> result = tfamAssetCheckplanMainDeptService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<TfamAssetCheckplanMainDept> result = tfamAssetCheckplanMainDeptService.listAll(
            searchParams.getParams(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody TfamAssetCheckplanMainDept entity) {
        String id = tfamAssetCheckplanMainDeptService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TfamAssetCheckplanMainDept entity) {
        int rows = tfamAssetCheckplanMainDeptService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<TfamAssetCheckplanMainDept> entitys) {
        List<String> ids = tfamAssetCheckplanMainDeptService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<TfamAssetCheckplanMainDept> entitys) {
        List<String> ids = tfamAssetCheckplanMainDeptService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<TfamAssetCheckplanMainDept> entitys) {
        int rows = tfamAssetCheckplanMainDeptService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
