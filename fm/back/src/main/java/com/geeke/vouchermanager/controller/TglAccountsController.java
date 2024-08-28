package com.geeke.vouchermanager.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.vouchermanager.entity.TglAccounts;
import com.geeke.vouchermanager.entity.TglAccountsTree;
import com.geeke.vouchermanager.service.TglAccountsService;
import com.geeke.vouchermanager.service.TglAccountsTreeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 会计科目管理Controller
 * @author
 * @version
 */
@Api(value = "/vouchermanager/InitLedgePeriod/tglAccounts", tags = {"会计科目管理Controller"})
@RestController
@RequestMapping(value = "/vouchermanager/InitLedgePeriod/tglAccounts")

public class TglAccountsController extends BaseController {

    @Autowired
    private TglAccountsService tglAccountsService;
    @Autowired
    private TglAccountsTreeService tglAccountsTreeService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "GET")
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TglAccounts entity = tglAccountsService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST")
    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        if (searchParams.getParams().size()>0&&searchParams.getParams().get(0).getValue().equals("0000")){
            searchParams.setParams(new ArrayList<>());
        }
        List<Parameter> params = searchParams.getParams();
        params.add(new Parameter("tenant_id","=", SessionUtils.getUser().getCompanyId()));
        params.add(new Parameter("uqparentid.tenant_id","=", SessionUtils.getUser().getCompanyId()));
        Page<TglAccounts> result = tglAccountsService.listPage(
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
        List<Parameter> params = searchParams.getParams();
        params.add(new Parameter("tenant_id","=", SessionUtils.getUser().getCompanyId()));
        params.add(new Parameter("uqparentid.tenant_id","=", SessionUtils.getUser().getCompanyId()));
        List<TglAccounts> result = tglAccountsService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "TglAccounts", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody TglAccounts entity) {
        try {
            tglAccountsService.editAccounts(entity);
            return ResponseEntity.ok(ResultUtil.successJson());
        } catch (Exception e) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
//        String id = tglAccountsService.save(entity).getId();

    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "TglAccounts", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TglAccounts entity) {
        int rows = tglAccountsService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<TglAccounts>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<TglAccounts> entitys) {
        List<String> ids = tglAccountsService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<TglAccounts>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<TglAccounts> entitys) {
        List<String> ids = tglAccountsService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<TglAccounts>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<TglAccounts> entitys) {
        int rows = tglAccountsService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取科目详情", notes = "通过搜索条件获取科目详情", httpMethod = "POST")
    @PostMapping(value = "tree")
    public ResponseEntity<JSONObject> tree(@RequestBody SearchParams searchParams) {
        List<Parameter> params = searchParams.getParams();
        params.add(new Parameter("tenant_id","=", SessionUtils.getUser().getCompanyId()));
        params.add(new Parameter("parent.tenant_id","=", SessionUtils.getUser().getCompanyId()));
        List<TglAccountsTree> result = tglAccountsTreeService.tree(searchParams.getParams(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpSession", name = "httpSession", value = "", required = true),
            @ApiImplicitParam(paramType = "body", dataType = "string", name = "jsonString", value = "")
    })
    @ApiOperation(value = "启用科目", notes = "启用科目", httpMethod = "POST")
    @PostMapping("/updateflagstart")
    @ResponseBody
    public ResponseEntity<JSONObject> updateflagstart(HttpSession httpSession,
                                      @RequestBody(required=false) String jsonString)
    {
        try
        {
            tglAccountsService.updateIntflag(jsonString,"start");
            return ResponseEntity.ok(ResultUtil.successJson());
        }
        catch (Exception e)
        {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpSession", name = "httpSession", value = "", required = true),
            @ApiImplicitParam(paramType = "body", dataType = "string", name = "jsonString", value = "")
    })
    @ApiOperation(value = "停用科目", notes = "停用科目", httpMethod = "POST")
    @PostMapping("/updateflagclose")
    @ResponseBody
    public ResponseEntity<JSONObject> updateflagclose(HttpSession httpSession,
                                      @RequestBody(required=false) String jsonString)
    {
        try
        {
            tglAccountsService.updateIntflag(jsonString,"close");
            return ResponseEntity.ok(ResultUtil.successJson());
        }
        catch (Exception e)
        {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }

    @PostMapping(value = "listSubjectlevel")
    public ResponseEntity<JSONObject> getSubjectlevelList() {
        List<Map<String, Object>> result = tglAccountsService.getSubjectlevelList();
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

}
