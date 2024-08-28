package com.geeke.vouchermanager.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.vouchermanager.entity.CompanyLedgerPeriods;
import com.geeke.vouchermanager.service.CompanyLedgerPeriodsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 分户期初余额设置Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/vouchermanager/companyLedgerPeriods")
public class CompanyLedgerPeriodsController extends BaseController {

    @Autowired
    private CompanyLedgerPeriodsService companyLedgerPeriodsService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        CompanyLedgerPeriods entity = companyLedgerPeriodsService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<CompanyLedgerPeriods> result = companyLedgerPeriodsService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<CompanyLedgerPeriods> result = companyLedgerPeriodsService.listAll(
            searchParams.getParams(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "getByAccount")
    public ResponseEntity<JSONObject> getByAccount(@RequestBody SearchParams searchParams) {
        CompanyLedgerPeriods result = companyLedgerPeriodsService.getByAccount(searchParams.getParams());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody CompanyLedgerPeriods entity) {
        String id = companyLedgerPeriodsService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody CompanyLedgerPeriods entity) {
        int rows = companyLedgerPeriodsService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "import")
    public ResponseEntity<JSONObject> importCompanyLedgerPeriods(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        int rows = companyLedgerPeriodsService.importCompanyLedgerPeriods(file.getInputStream());
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "export")
    public ResponseEntity<JSONObject> exportCompanyLedgerPeriods(@RequestBody SearchParams searchParams, HttpServletResponse response) throws Exception {
        int rows = companyLedgerPeriodsService.exportCompanyLedgerPeriods(searchParams.getParams(), searchParams.getOrderby(), response);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<CompanyLedgerPeriods> entitys) {
        List<String> ids = companyLedgerPeriodsService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<CompanyLedgerPeriods> entitys) {
        List<String> ids = companyLedgerPeriodsService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<CompanyLedgerPeriods> entitys) {
        int rows = companyLedgerPeriodsService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
