package com.geeke.tgl.flow.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSONObject;

import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import com.geeke.sys.controller.BaseController;
import com.geeke.tgl.flow.entity.Flowitems;
import com.geeke.tgl.flow.service.FlowitemsService;
import com.geeke.utils.ResultUtil;

import java.util.*;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 现金流量项目Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/tgl/flow/flowitems")
public class FlowitemsController extends BaseController {

    @Autowired
    private FlowitemsService flowitemsService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        Flowitems entity = flowitemsService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<Flowitems> result = flowitemsService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<Flowitems> result = flowitemsService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody Flowitems entity) {
        String id = flowitemsService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "import")
    public ResponseEntity<JSONObject> importFlowitems(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        int rows = flowitemsService.importCashFlowFile(file.getInputStream());
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "export")
    public ResponseEntity<JSONObject> exportFlowitems(HttpServletResponse response) throws Exception {
        int rows = flowitemsService.exportCashFlowInfo(response);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody Flowitems entity) {
        int rows = flowitemsService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<Flowitems> entitys) {
        List<String> ids = flowitemsService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<Flowitems> entitys) {
        List<String> ids = flowitemsService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<Flowitems> entitys) {
        int rows = flowitemsService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
