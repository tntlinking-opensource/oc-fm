package com.geeke.vouchermanager.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.vouchermanager.entity.TglAcIni;
import com.geeke.vouchermanager.entity.TglAcOffsetMain;
import com.geeke.vouchermanager.service.TglAcOffsetMainService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
 * 往来冲销总表Controller
 * @author
 * @version
 */
@Api(value = "/vouchermanager/tglAcOffsetMain", tags = {"往来冲销总表Controller"})
@RestController
@RequestMapping(value = "/vouchermanager/tglAcOffsetMain")
public class TglAcOffsetMainController extends BaseController {

    @Autowired
    private TglAcOffsetMainService tglAcOffsetMainService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "GET")
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TglAcOffsetMain entity = tglAcOffsetMainService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }
    @ApiOperation(value = "", notes = "", httpMethod = "GET")
    @GetMapping("editlist/{id}")
    public ResponseEntity<JSONObject> geteditlistById(@PathVariable("id") String uqaccountid) {
        Page<Map<String, Object>> result = tglAcOffsetMainService.geteditlistById(uqaccountid);

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }
    @PostMapping(value = "importExcel")
    public ResponseEntity<JSONObject> importExcel(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        try {
        int rows = tglAcOffsetMainService.importExcel(file);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
        }catch (Exception e){
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<TglAcOffsetMain> result = tglAcOffsetMainService.listPage(
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
        List<TglAcOffsetMain> result = tglAcOffsetMainService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "TglAcOffsetMain", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody TglAcOffsetMain entity) {
        String id = tglAcOffsetMainService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "TglAcOffsetMain", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TglAcOffsetMain entity) {
        int rows = tglAcOffsetMainService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<TglAcOffsetMain>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<TglAcOffsetMain> entitys) {
        List<String> ids = tglAcOffsetMainService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "savedata")
    public ResponseEntity<JSONObject> savedata(@RequestBody List<TglAcIni> entitys) {
        String ids = tglAcOffsetMainService.insertTglAcIni(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<TglAcOffsetMain>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<TglAcOffsetMain> entitys) {
        List<String> ids = tglAcOffsetMainService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<TglAcOffsetMain>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<TglAcOffsetMain> entitys) {
        int rows = tglAcOffsetMainService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
    /**
     *挂账记录
     * @param searchParams
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "挂账记录", notes = "挂账记录", httpMethod = "POST")
    @PostMapping(value = { "getOnAccountData" })
    public ResponseEntity<JSONObject> getOnAccountData(@RequestBody SearchParams searchParams) {
        try {


        Page<Map<String, Object>> result = tglAcOffsetMainService.getOnaccountDataPage(
                searchParams.getParams(),
                searchParams.getOffset(),
                searchParams.getLimit(),
                searchParams.getOrderby()
        );
        return ResponseEntity.ok(ResultUtil.successJson(result));
        }catch (Exception e){
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }
    /**
     *往来查询
     * @param searchParams
     * @return
     */
    @ApiOperation(value = "冲销记录", notes = "冲销记录", httpMethod = "POST")
    @PostMapping(value = { "getOffsetData" })
    public ResponseEntity<JSONObject> getOffsetData(@RequestBody SearchParams searchParams) {
        try {
            Page<Map<String, Object>> result = tglAcOffsetMainService.getOffsetDataPage(
                searchParams.getParams(),
                searchParams.getOffset(),
                searchParams.getLimit(),
                searchParams.getOrderby()
        );
        return ResponseEntity.ok(ResultUtil.successJson(result));
        }catch (Exception e){
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }
    /**
     *往来查询
     * @param searchParams
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "往来查询", notes = "往来查询", httpMethod = "POST")
    @PostMapping(value = { "getdata" })
    public ResponseEntity<JSONObject> getdata(@RequestBody SearchParams searchParams) {
        try {


        Page<Map<String, Object>> result = tglAcOffsetMainService.getdata(
                searchParams.getParams(),
                searchParams.getOffset(),
                searchParams.getLimit(),
                searchParams.getOrderby()
        );
        return ResponseEntity.ok(ResultUtil.successJson(result));
        }catch (Exception e){
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }
    /**
     *往来查询
     * @param searchParams
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "往来查询", notes = "往来查询", httpMethod = "POST")
    @PostMapping(value = { "accountlist" })
    public ResponseEntity<JSONObject> accountlist(@RequestBody SearchParams searchParams) {
        try {
        Page<Map<String, Object>> result = tglAcOffsetMainService.accountlist(
                searchParams.getParams(),
                searchParams.getOffset(),
                searchParams.getLimit(),
                searchParams.getOrderby()
        );
        return ResponseEntity.ok(ResultUtil.successJson(result));
        }catch (Exception e){
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }

    @PostMapping(value = "/exportExcels")
    public ResponseEntity<JSONObject> exportExcels(@RequestBody SearchParams searchParams,HttpServletResponse response)  throws Exception {
        int rows = tglAcOffsetMainService.exportExcels(searchParams,response);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
    @PostMapping(value = { "deletedata" })
    public ResponseEntity<JSONObject> deletedata(@RequestBody String uqaccountid) {
        try {
            String rel= this.tglAcOffsetMainService.clearDetailData( uqaccountid );
            if (rel.equals("")) {
                return ResponseEntity.ok(ResultUtil.successJson(""));
            } else {
                return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,"部分数据已被核销，不能全部清除初始化！"));
//                return "在您选择的数据中第" + message + "条已做核销，不能清除！";
            }
        }catch (Exception e){
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "string", name = "revertRecords", value = "", required = true)
    })
    @ApiOperation(value = "还原冲销", notes = "还原冲销", httpMethod = "POST")
    @PostMapping(value = { "revertRush" })
    public ResponseEntity<JSONObject> revertRush(@RequestBody String revertRecords) {
        tglAcOffsetMainService.revertRush(revertRecords);
        return ResponseEntity.ok(ResultUtil.successJson());
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "string", name = "revertRecords", value = "", required = true)
    })
    @ApiOperation(value = "冲销接口", notes = "", httpMethod = "POST")
    @PostMapping(value = { "doOffset" })
    public ResponseEntity<JSONObject> doOffset(@RequestBody String revertRecords) {
        try {
            tglAcOffsetMainService.doOffset(revertRecords);
            return ResponseEntity.ok(ResultUtil.successJson());
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }

    }
    @PostMapping(value = { "doManualRush" })
    public ResponseEntity<JSONObject> doManualRush(@RequestBody String revertRecords) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(revertRecords);
            tglAcOffsetMainService.doManualRush(jsonObject.getString("mainData"),jsonObject.getString("detaildatas"));
            return ResponseEntity.ok(ResultUtil.successJson());
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }

    }
}
