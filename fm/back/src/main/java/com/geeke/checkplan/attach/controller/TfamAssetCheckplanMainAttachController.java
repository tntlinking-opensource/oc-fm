package com.geeke.checkplan.attach.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.checkplan.attach.entity.TfamAssetCheckplanMainAttach;
import com.geeke.checkplan.attach.service.TfamAssetCheckplanMainAttachService;
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
 * 盘点计划附件Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/checkplan/attach/tfamAssetCheckplanMainAttach")
public class TfamAssetCheckplanMainAttachController extends BaseController {

    @Autowired
    private TfamAssetCheckplanMainAttachService tfamAssetCheckplanMainAttachService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TfamAssetCheckplanMainAttach entity = tfamAssetCheckplanMainAttachService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<TfamAssetCheckplanMainAttach> result = tfamAssetCheckplanMainAttachService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<TfamAssetCheckplanMainAttach> result = tfamAssetCheckplanMainAttachService.listAll(
            searchParams.getParams(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody TfamAssetCheckplanMainAttach entity) {
        String id = tfamAssetCheckplanMainAttachService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TfamAssetCheckplanMainAttach entity) {
        int rows = tfamAssetCheckplanMainAttachService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<TfamAssetCheckplanMainAttach> entitys) {
        List<String> ids = tfamAssetCheckplanMainAttachService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<TfamAssetCheckplanMainAttach> entitys) {
        List<String> ids = tfamAssetCheckplanMainAttachService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<TfamAssetCheckplanMainAttach> entitys) {
        int rows = tfamAssetCheckplanMainAttachService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
