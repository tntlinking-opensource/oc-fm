package com.geeke.vouchermanager.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.vouchermanager.entity.TglLedger;
import com.geeke.vouchermanager.entity.TglLedgetype;
import com.geeke.vouchermanager.service.TglLedgetypeService;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 分户Controller
 * @author
 * @version
 */
@Api(value = "/vouchermanager/tglLedgetype", tags = {"分户Controller"})
@RestController
@RequestMapping(value = "/vouchermanager/tglLedgetype")

public class TglLedgetypeController extends BaseController {

    @Autowired
    private TglLedgetypeService tglLedgetypeService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "GET")
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TglLedgetype entity = tglLedgetypeService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<TglLedgetype> result = tglLedgetypeService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<TglLedgetype> result = tglLedgetypeService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "TglLedgetype", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody TglLedgetype entity) {
        String id = null;
        try {
            List<TglLedger> tglLedgerList = entity.getTglLedgerList();
            for (TglLedger tglLedger : tglLedgerList) {
                tglLedger.setIntislastlevel(1);
            }
            id = tglLedgetypeService.edit(entity).getId();
            return ResponseEntity.ok(ResultUtil.successJson(id));
        } catch (Exception e) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }

    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "TglLedgetype", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TglLedgetype entity) {
        int rows = 0;
        try {
            rows = tglLedgetypeService.deleteall(entity);
            return ResponseEntity.ok(ResultUtil.successJson(rows));
        } catch (Exception e) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }

    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<TglLedgetype>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<TglLedgetype> entitys) {
        List<String> ids = tglLedgetypeService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<TglLedgetype>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<TglLedgetype> entitys) {
        List<String> ids = tglLedgetypeService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<TglLedgetype>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<TglLedgetype> entitys) {
        int rows = tglLedgetypeService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "listAllAndChild")
    public ResponseEntity<JSONObject> listLedgetypeAndChild(@RequestBody SearchParams searchParams) {
        List<TglLedgetype> result = tglLedgetypeService.listLedgetypeAndChild(searchParams.getParams(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }
    /**
     * 分户数据导出
     * @param response
     * @return
     * @throws Exception
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "", required = true)
    })
    @ApiOperation(value = "分户导出", notes = "分户导出", httpMethod = "POST")
    @PostMapping(value = "/exportTglLedge")
    public ResponseEntity<JSONObject> exportTglLedge(HttpServletResponse response)  throws Exception {
        int rows = tglLedgetypeService.exportTglLedge(response);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "file", name = "file", value = "")
    })
    @ApiOperation(value = "导入分户", notes = "", httpMethod = "POST")
    @PostMapping(value = "importtTglLedge")
    public ResponseEntity<JSONObject> importtTglLedge(@RequestParam(value = "file", required = false) MultipartFile file){
        try {
            tglLedgetypeService.importtTglLedge(file);
            return ResponseEntity.ok(ResultUtil.successJson());
        } catch (Exception e) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }

    }
}
