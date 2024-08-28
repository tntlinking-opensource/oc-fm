package com.geeke.cardasset.credit.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.geeke.cardasset.credit.entity.TfamAssetEntry;
import com.geeke.cardasset.credit.service.TfamAssetEntryService;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import java.util.List;

import com.geeke.utils.constants.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 资产入账Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/cardasset/credit/tfamAssetEntry")
public class TfamAssetEntryController extends BaseController {

    @Autowired
    private TfamAssetEntryService tfamAssetEntryService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TfamAssetEntry entity = tfamAssetEntryService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<TfamAssetEntry> result = tfamAssetEntryService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<TfamAssetEntry> result = tfamAssetEntryService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody TfamAssetEntry entity) {
        String id = tfamAssetEntryService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TfamAssetEntry entity) {
        int rows = tfamAssetEntryService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<TfamAssetEntry> entitys) {
        List<String> ids = tfamAssetEntryService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<TfamAssetEntry> entitys) {
        List<String> ids = tfamAssetEntryService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<TfamAssetEntry> entitys) {
        int rows = tfamAssetEntryService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
    @RequestMapping("/linkvoucher")
    @ResponseBody
    public ResponseEntity<JSONObject> linkVoucher(@RequestBody String json)
    {
        try
        {
            JSONObject jsonObject = JSONObject.parseObject(json);
            JSONArray alterid = jsonObject.getJSONArray("alterid");
            String voucherid = jsonObject.getString("voucherid");
            this.tfamAssetEntryService.linkVoucher(alterid,voucherid);

            return ResponseEntity.ok(ResultUtil.successJson());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }

    @RequestMapping("/cancelvoucher")
    @ResponseBody
    public ResponseEntity<JSONObject> cancelVoucher(@RequestBody String arraySelecteID)
    {
        try
        {



            this.tfamAssetEntryService.cancelVoucher(arraySelecteID);

            return ResponseEntity.ok(ResultUtil.successJson());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }
    @RequestMapping("/linkvoucher1")
    @ResponseBody
    public ResponseEntity<JSONObject> linkVoucher1(@RequestBody String json)
    {
        try
        {
            JSONObject jsonObject = JSONObject.parseObject(json);
            JSONArray alterid = jsonObject.getJSONArray("alterid");
            String voucherid = jsonObject.getString("voucherid");
            this.tfamAssetEntryService.linkVoucher1(alterid,voucherid);

            return ResponseEntity.ok(ResultUtil.successJson());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }

    @RequestMapping("/cancelvoucher1")
    @ResponseBody
    public ResponseEntity<JSONObject> cancelVoucher1(@RequestBody String arraySelecteID)
    {
        try
        {



            this.tfamAssetEntryService.cancelVoucher1(arraySelecteID);

            return ResponseEntity.ok(ResultUtil.successJson());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }
}
