package com.geeke.ureport.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.ten.entity.Tenant;
import com.geeke.ureport.entity.UreportFileEntity;
import com.geeke.ureport.service.UreportFileService;
import com.geeke.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 报表设计器Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/report/reportFile")

public class UreportFileController {
    @Autowired
    private UreportFileService ureportFileService;

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<UreportFileEntity> result = ureportFileService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<UreportFileEntity> result = ureportFileService.listAll(searchParams.getParams(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        UreportFileEntity entity = ureportFileService.get(id);
        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody UreportFileEntity entity) {
        int row = ureportFileService.saveReportFile(entity);
        return ResponseEntity.ok(ResultUtil.successJson(row));
    }

    @PostMapping(value = "update")
    public ResponseEntity<JSONObject> update(@RequestBody UreportFileEntity entity) {
        int row = ureportFileService.updateReportFile(entity);
        return ResponseEntity.ok(ResultUtil.successJson(row));
    }

    @PostMapping(value = "start")
    public ResponseEntity<JSONObject> start(@RequestBody UreportFileEntity entity) {
        int row = ureportFileService.startReportFile(entity);
        return ResponseEntity.ok(ResultUtil.successJson(row));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody UreportFileEntity entity) {
        int rows = ureportFileService.deleteReportFileByName(entity.getName());
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
