package com.geeke.fixasset.asset.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.fixasset.asset.dto.TfamAssetDto;
import com.geeke.fixasset.asset.entity.TfamAsset;
import com.geeke.fixasset.asset.req.*;
import com.geeke.fixasset.asset.resp.*;
import com.geeke.fixasset.asset.service.TfamAssetService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 卡片管理Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/fixasset/asset/tfamAsset")
public class TfamAssetController extends BaseController {

    @Autowired
    private TfamAssetService tfamAssetService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TfamAsset entity = tfamAssetService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<TfamAsset> result = tfamAssetService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<TfamAssetDto> result = tfamAssetService.listAlls(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "count")
    public ResponseEntity<JSONObject> count(@RequestBody SearchParams searchParams) {
        CountAssetResp countAssetResp = tfamAssetService.count(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(countAssetResp));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody TfamAsset entity) {
        String id = tfamAssetService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "saveAndUpdate")
    public ResponseEntity<JSONObject> saveAndUpdate(@RequestBody SaveAndUpdateReq saveAndUpdateReq) {
        String id = tfamAssetService.saveAndUpdate(saveAndUpdateReq).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "assetTransfer")
    public ResponseEntity<JSONObject> assetTransfer(@RequestBody AssetTransferReq transferReq) {
        tfamAssetService.assetTransfer(transferReq);
        return ResponseEntity.ok(ResultUtil.successJson());
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TfamAsset entity) {
        int rows = tfamAssetService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<TfamAsset> entitys) {
        List<String> ids = tfamAssetService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<TfamAsset> entitys) {
        List<String> ids = tfamAssetService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<TfamAsset> entitys) {
        int rows = tfamAssetService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "export")
    public ResponseEntity<JSONObject> exportAsset(@RequestBody SearchParams searchParams, HttpServletResponse response) throws Exception {
        int rows = tfamAssetService.exportAsset(searchParams.getParams(), searchParams.getOrderby(), response);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "import")
    public ResponseEntity<JSONObject> importTfamAsset(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        List res = tfamAssetService.importTfamAsset(file.getInputStream());
        return ResponseEntity.ok(ResultUtil.successJson(res));
    }

    @PostMapping(value = "copy")
    public ResponseEntity<JSONObject> copy(@RequestBody CopyAssetReq copyAssetReq) {
        Boolean result = tfamAssetService.copy(copyAssetReq);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "audit")
    public ResponseEntity<JSONObject> audit(@RequestBody AuditAssetReq auditAssetReq) {
        AuditAssetInfoResp result = tfamAssetService.audit(auditAssetReq);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "scrapAuditApply")
    public ResponseEntity<JSONObject> scrapAuditApply(@RequestBody AuditAssetReq auditAssetReq) {
        List<AuditAssetResp> result = tfamAssetService.scrapAuditApply(auditAssetReq);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "printQRCode")
    public ResponseEntity<JSONObject> printQRCode(@RequestBody AuditAssetReq auditAssetReq, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String result = tfamAssetService.printQRCode(auditAssetReq.getIds(),request,response);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }


    @PostMapping(value = "lifeCircle")
    public ResponseEntity<JSONObject> lifeCircle(@RequestBody CopyAssetReq copyAssetReq) {
        LifeCricleResp lifeCricleResp = tfamAssetService.lifeCircle(copyAssetReq);
        return ResponseEntity.ok(ResultUtil.successJson(lifeCricleResp));
    }

    @PostMapping(value = "countAsset")
    public ResponseEntity<JSONObject> countAsset(@RequestBody CountAssetReq copyAssetReq) {
        CountAssetResp countAssetResp = tfamAssetService.countAsset(copyAssetReq);
        return ResponseEntity.ok(ResultUtil.successJson(countAssetResp));
    }

    @PostMapping(value = "countAssetForGraph")
    public ResponseEntity<JSONObject> countAssetForGraph() {
        CountAssetForGraphResp countAssetForGraphresp = tfamAssetService.countAssetForGraph();
        return ResponseEntity.ok(ResultUtil.successJson(countAssetForGraphresp));
    }

    @PostMapping(value = "countAssetExport")
    public ResponseEntity<JSONObject> countAssetExport(@RequestBody CountAssetReq copyAssetReq, HttpServletResponse response) {
        int rows = tfamAssetService.countAssetExport(copyAssetReq, response);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

}
