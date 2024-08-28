package com.geeke.vouchermanager.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.vouchermanager.dao.HandlerGroupTenantTreeDao;
import com.geeke.vouchermanager.entity.GroupAccountsEntity;
import com.geeke.vouchermanager.entity.GroupAccountsEntityTree;
import com.geeke.vouchermanager.entity.TglAccounts;
import com.geeke.vouchermanager.entity.TglAccountsTree;
import com.geeke.vouchermanager.service.HandlerTenantGroupsService;
import com.geeke.vouchermanager.service.HandlerTenantGroupsTreeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
/**
 * 会计科目模板管理Controller
 * @author
 * @version
 */
@Api(value = "/handler/grouptenant", tags = {"会计科目模板管理Controller"})
@RestController
@RequestMapping("/handler/grouptenant")

public class HandlerTenantGroupsController extends BaseController {

    @Autowired
    private HandlerTenantGroupsService handlerTenantGroupsService;
    @Autowired
    private HandlerTenantGroupsTreeService handlerTenantGroupsTreeService;
    /**
     * 新增科目
     *
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpSession", name = "httpSession", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "jsonString", value = "")
    })
    @ApiOperation(value = "新增科目", notes = "新增科目", httpMethod = "POST")
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<JSONObject> addAccounts(HttpSession httpSession,
                                                  @RequestParam(required = false) String jsonString) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();



            GroupAccountsEntity account =
                    new GroupAccountsEntity(null, "",
                            jsonObject.getString("varaccountcode"), null,
                            jsonObject.getString("varaccountname"), null,
                            jsonObject.getString("intproperty"), jsonObject.getString("uqtypeid"),
                            jsonObject.getString("uqforeigncurrid"), jsonObject.getString("varmeasure"),
                            jsonObject.getString("intisledge"), null,
                            null, jsonObject.getString("uqparentid"),
                            "1", null, "2", jsonObject.getString("intisflow"), jsonObject.getString("isbalance"),
                            "");
            handlerTenantGroupsService.addAccounts(account, jsonObject.getString("uqledgetypeids"));

            return ResponseEntity.ok(ResultUtil.successJson());
        } catch (Exception e) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }
    /**
     * 修改科目
     *
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpSession", name = "httpSession", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "jsonString", value = "")
    })
    @ApiOperation(value = "修改科目", notes = "修改科目", httpMethod = "POST")
    @PostMapping("/groupedit")
    @ResponseBody
    public ResponseEntity<JSONObject> editAccounts(HttpSession httpSession,
                                     @RequestParam(required = false) String jsonString) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
//			String direction = cdtMap.getString("direction");

            GroupAccountsEntity account = new GroupAccountsEntity(
                    jsonObject.getString("uqaccountid"), "",
                    jsonObject.getString("varaccountcode"), null,
                    jsonObject.getString("varaccountname"), null,
                    jsonObject.getString("intproperty"), jsonObject.getString("uqtypeid"),
                    jsonObject.getString("uqforeigncurrid"), jsonObject.getString("varmeasure"),
                    jsonObject.getString("intisledge"), null,
                    null, jsonObject.getString("uqparentid"),
                    null, null, null, jsonObject.getString("intisflow"), jsonObject.getString("isbalance"), "");
            handlerTenantGroupsService.editGroupAccounts(account);

            return ResponseEntity.ok(ResultUtil.successJson());
        } catch (Exception e) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }
    /**
     * 删除科目
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "accountid", value = "", required = true)
    })
    @ApiOperation(value = "删除科目", notes = "删除科目", httpMethod = "DELETE")
    @ResponseBody
    @DeleteMapping("/delete")
    public ResponseEntity<JSONObject> deleteAccount(@RequestParam String accountid) {
        try {
            handlerTenantGroupsService.deleteAccount(accountid);
            return ResponseEntity.ok(ResultUtil.successJson());
        } catch (Exception e) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }
    /**
     * 租户组科目主数据同步租户
     * 1,租户有作更新操作 2,租户无作新增操作
     *
     * @param tenantidList
     * @param jsonString
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "tenantidList", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpSession", name = "httpSession", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "jsonString", value = "")
    })
    @ApiOperation(value = "租户组科目主数据同步租户 1,租户有作更新操作 2,租户无作新增操作", notes = "租户组科目主数据同步租户 1,租户有作更新操作 2,租户无作新增操作", httpMethod = "POST")
    @ResponseBody
    @PostMapping("/synchronizeAccounts")
    public ResponseEntity<JSONObject> synchronizeAccounts(@RequestParam String tenantidList, HttpSession httpSession,
                                            @RequestParam(required = false) String jsonString) {
        try {
            handlerTenantGroupsService.synchronizeAccounts(tenantidList,jsonString);
            return ResponseEntity.ok(ResultUtil.successJson());
        } catch (Exception e) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "tree")
    public ResponseEntity<JSONObject> tree(@RequestBody SearchParams searchParams) {
        List<GroupAccountsEntityTree> result = handlerTenantGroupsTreeService.tree(searchParams.getParams(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        if (searchParams.getParams().size()>0&&searchParams.getParams().get(0).getValue().equals("0000")){
            searchParams.setParams(new ArrayList<>());
        }
        Page<GroupAccountsEntityTree> result = handlerTenantGroupsTreeService.listPage(
                searchParams.getParams(),
                searchParams.getOffset(),
                searchParams.getLimit(),
                searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    /**
     * 初始化科目
     * @param tenantidList
     * @param httpSession
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "tenantidList", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpSession", name = "httpSession", value = "", required = true)
    })
    @ApiOperation(value = "初始化科目", notes = "初始化科目")
    @ResponseBody
    @RequestMapping("/initializationAccounts")
    public ResponseEntity<JSONObject> initializationAccounts(@RequestParam String tenantidList,HttpSession httpSession
    ) {
        try {

            handlerTenantGroupsService.initializationAccounts(tenantidList);
            return ResponseEntity.ok(ResultUtil.successJson());
        }catch (Exception e){
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }

    /**
     * 科目模板数据导出
     * @param response
     * @return
     * @throws Exception
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "", required = true)
    })
    @ApiOperation(value = "科目模板数据导出", notes = "科目模板数据导出", httpMethod = "POST")
    @PostMapping(value = "/exportAccountInfo")
    public ResponseEntity<JSONObject> exportAccountInfo(HttpServletResponse response)  throws Exception {
        int rows = handlerTenantGroupsService.exportAccountInfo(response);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
