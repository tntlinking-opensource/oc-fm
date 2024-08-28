package com.geeke.tgl.ledgerReport.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.sys.controller.BaseController;
import com.geeke.tgl.ledgerReport.service.LedgerReportService;
import com.geeke.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 分户科目明细账Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/tgl/ledger/report")
public class LedgerReportController extends BaseController {

    @Autowired
    private LedgerReportService ledgerReportService;

    @PostMapping(value = "ledgerDetail")
    public ResponseEntity<JSONObject> getLedgerDetailTableName(@RequestBody Map<String, Object> mapParams) {
        String result = ledgerReportService.createLedgerDetail(mapParams);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "ledgerBook")
    public ResponseEntity<JSONObject> getLedgerBookTableName(@RequestBody Map<String, Object> mapParams) {
        String result = ledgerReportService.createLedgerBook(mapParams);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "ledgerGeneral")
    public ResponseEntity<JSONObject> getLedgerGeneralTableName(@RequestBody Map<String, Object> mapParams) {
        String result = ledgerReportService.createLedgerGeneral(mapParams);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "accountDetail")
    public ResponseEntity<JSONObject> getAccountDetailTableName(@RequestBody Map<String, Object> mapParams) {
        String result = ledgerReportService.createAccountDetail(mapParams);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "accountBook")
    public ResponseEntity<JSONObject> getAccountBookTableName(@RequestBody Map<String, Object> mapParams) {
        String result = ledgerReportService.createAccountBook(mapParams);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

}
