package com.geeke.vouchermanager.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.vouchermanager.entity.TglPeriodAccounts;
import com.geeke.vouchermanager.service.TglPeriodAccountsService;
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
 * 科目期初余额Controller
 * @author
 * @version
 */
@Api(value = "/vouchermanager/tglPeriodAccounts", tags = {"科目期初余额Controller"})
@RestController
@RequestMapping(value = "/vouchermanager/tglPeriodAccounts")

public class TglPeriodAccountsController extends BaseController {

    @Autowired
    private TglPeriodAccountsService tglPeriodAccountsService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "GET")
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TglPeriodAccounts entity = tglPeriodAccountsService.get1(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST",response=TglPeriodAccounts.class)
    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<TglPeriodAccounts> result = tglPeriodAccountsService.listPage(
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
        List<TglPeriodAccounts> result = tglPeriodAccountsService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "TglPeriodAccounts", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody TglPeriodAccounts entity) {
        String id = null;
        try {
            id = tglPeriodAccountsService.add(entity).getId();
            return ResponseEntity.ok(ResultUtil.successJson(id));
        } catch (Exception e) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "TglPeriodAccounts", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TglPeriodAccounts entity) {
        int rows = tglPeriodAccountsService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<TglPeriodAccounts>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<TglPeriodAccounts> entitys) {
        List<String> ids = tglPeriodAccountsService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<TglPeriodAccounts>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<TglPeriodAccounts> entitys) {
        List<String> ids = tglPeriodAccountsService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<TglPeriodAccounts>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<TglPeriodAccounts> entitys) {
        int rows = tglPeriodAccountsService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "", required = true)
    })
    @ApiOperation(value = "科目期初余额导出", notes = "科目期初余额导出", httpMethod = "POST")
    @PostMapping(value = "/exportTglPeriodAccounts")
    public ResponseEntity<JSONObject> exportTglPeriodAccounts(HttpServletResponse response)  throws Exception {
        int rows = tglPeriodAccountsService.exportTglPeriodAccounts(response);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "file", name = "file", value = "")
    })
    @ApiOperation(value = "科目期初余额导入", notes = "", httpMethod = "POST")
    @PostMapping(value = "importTglPeriodAccounts")
    public ResponseEntity<JSONObject> importTglPeriodAccounts(@RequestParam(value = "file", required = false) MultipartFile file){
        try {
            tglPeriodAccountsService.importTglPeriodAccounts(file);
            return ResponseEntity.ok(ResultUtil.successJson());
        } catch (Exception e) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }

    }
}
