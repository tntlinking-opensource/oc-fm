package com.geeke.checkplan.detail.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.checkplan.detail.entity.TfamAssetCheckplanDetail;
import com.geeke.checkplan.detail.service.TfamAssetCheckplanDetailService;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
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
 * 盘点详情Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/checkpaln/detail/tfamAssetCheckplanDetail")
public class TfamAssetCheckplanDetailController extends BaseController {

    @Autowired
    private TfamAssetCheckplanDetailService tfamAssetCheckplanDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TfamAssetCheckplanDetail entity = tfamAssetCheckplanDetailService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<TfamAssetCheckplanDetail> result = tfamAssetCheckplanDetailService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<TfamAssetCheckplanDetail> result = tfamAssetCheckplanDetailService.listAll(
            searchParams.getParams(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody TfamAssetCheckplanDetail entity) {
        String id = tfamAssetCheckplanDetailService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TfamAssetCheckplanDetail entity) {
        int rows = tfamAssetCheckplanDetailService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<TfamAssetCheckplanDetail> entitys) {
        List<String> ids = tfamAssetCheckplanDetailService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<TfamAssetCheckplanDetail> entitys) {
        List<String> ids = tfamAssetCheckplanDetailService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<TfamAssetCheckplanDetail> entitys) {
        int rows = tfamAssetCheckplanDetailService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
