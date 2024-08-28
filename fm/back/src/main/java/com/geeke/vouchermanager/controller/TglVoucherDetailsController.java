package com.geeke.vouchermanager.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.vouchermanager.entity.TglLedger;
import com.geeke.vouchermanager.entity.TglLedgetype;
import com.geeke.vouchermanager.entity.TglVoucherDetailLedger;
import com.geeke.vouchermanager.entity.TglVoucherDetails;
import com.geeke.vouchermanager.service.TglVoucherDetailsService;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 制证Controller
 * @author
 * @version
 */
@Api(value = "/vouchermanager/tglVoucherDetails", tags = {"制证Controller"})
@RestController
@RequestMapping(value = "/vouchermanager/tglVoucherDetails")

public class TglVoucherDetailsController extends BaseController {

    @Autowired
    private TglVoucherDetailsService tglVoucherDetailsService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "通过id获取分录数据", notes = "", httpMethod = "GET")
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TglVoucherDetails entity = tglVoucherDetailsService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "通过分录id获取分录数据", notes = "", httpMethod = "GET")
    @GetMapping("/getDetailLedgerById/{id}")
    public ResponseEntity<JSONObject> getDetailLedgerById(@PathVariable("id") String id) {
        List<TglLedgetype> entity = tglVoucherDetailsService.selectQuestionItemByQuestionId(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "通过科目id查询分录分户数据", notes = "", httpMethod = "GET",response = TglLedgetype.class)
    @GetMapping("/getByUqaccountId/{id}")
    public ResponseEntity<JSONObject> getByUqaccountId(@PathVariable("id") String id) {
        List<TglLedgetype> entity = tglVoucherDetailsService.getByUqaccountId(id);
        for (TglLedgetype tglLedgetype : entity) {
            List<TglLedger> tglLedgerList = tglLedgetype.getTglLedgerList();
            for (TglLedger tglLedger : tglLedgerList) {
                TglVoucherDetailLedger tglVoucherDetailLedger = new TglVoucherDetailLedger();
                tglVoucherDetailLedger.setMnyamount(new BigDecimal(0));
                tglVoucherDetailLedger.setMnydebit(new BigDecimal(0));
                tglVoucherDetailLedger.setMnycredit(new BigDecimal(0));
                tglLedger.setTglVoucherDetailLedger(tglVoucherDetailLedger);
            }
        }
        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<TglVoucherDetails> result = tglVoucherDetailsService.listPage(
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
        List<TglVoucherDetails> result = tglVoucherDetailsService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "TglVoucherDetails", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody TglVoucherDetails entity) {
        String id = tglVoucherDetailsService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "TglVoucherDetails", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TglVoucherDetails entity) {
        int rows = tglVoucherDetailsService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<TglVoucherDetails>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<TglVoucherDetails> entitys) {
        List<String> ids = tglVoucherDetailsService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<TglVoucherDetails>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<TglVoucherDetails> entitys) {
        List<String> ids = tglVoucherDetailsService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<TglVoucherDetails>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<TglVoucherDetails> entitys) {
        int rows = tglVoucherDetailsService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
