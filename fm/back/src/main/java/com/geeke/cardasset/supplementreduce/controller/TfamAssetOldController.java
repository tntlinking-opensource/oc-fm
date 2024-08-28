package com.geeke.cardasset.supplementreduce.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.geeke.cardasset.supplementreduce.entity.TfamAssetOld;
import com.geeke.cardasset.supplementreduce.service.TfamAssetOldService;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import java.util.List;

import com.geeke.utils.constants.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 补提冲减Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/cardasset/supplementreduce/tfamAssetOld")
public class TfamAssetOldController extends BaseController {

    @Autowired
    private TfamAssetOldService tfamAssetOldService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TfamAssetOld entity = tfamAssetOldService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<TfamAssetOld> result = tfamAssetOldService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<TfamAssetOld> result = tfamAssetOldService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody TfamAssetOld entity) {
        String id = tfamAssetOldService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TfamAssetOld entity) {
        int rows = tfamAssetOldService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<TfamAssetOld> entitys) {
        List<String> ids = tfamAssetOldService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<TfamAssetOld> entitys) {
        List<String> ids = tfamAssetOldService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<TfamAssetOld> entitys) {
        int rows = tfamAssetOldService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
    @PostMapping("/supplementdep")
    @ResponseBody
    public ResponseEntity<JSONObject> supplementDep(@RequestBody String jsonCondition)
    {
        try
        {
            JSONObject cdtMap = JSONObject.parseObject(jsonCondition);
            String string = cdtMap.getString("arraySelecteID");
            JSONArray jsonArray =new  JSONArray() ;
            jsonArray.add(string);
            String[] ids = new String[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++)
            {
                ids[i] = jsonArray.getString(i);
            }


            String companyid = SessionUtils.getUser().getCompanyId();
            String creatorid = SessionUtils.getUser().getId();
            cdtMap.put("companyid",companyid);
            cdtMap.put("creatorid",creatorid);

            this.tfamAssetOldService.supplementDep(cdtMap, ids);

            return ResponseEntity.ok(ResultUtil.successJson());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }
    @PostMapping("/reducedep")
    @ResponseBody
    public ResponseEntity<JSONObject> reducedep(@RequestBody String jsonCondition)
    {
        try
        {
            JSONObject cdtMap = JSONObject.parseObject(jsonCondition);
            String string = cdtMap.getString("arraySelecteID");
            JSONArray jsonArray =new  JSONArray() ;
            jsonArray.add(string);
            String[] ids = new String[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++)
            {
                ids[i] = jsonArray.getString(i);
            }


            String companyid = SessionUtils.getUser().getCompanyId();
            String creatorid = SessionUtils.getUser().getId();
            cdtMap.put("companyid",companyid);
            cdtMap.put("creatorid",creatorid);

            this.tfamAssetOldService.reduceDep(cdtMap, ids);

            return ResponseEntity.ok(ResultUtil.successJson());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }
    @PostMapping("/proj")
    public ResponseEntity<JSONObject> doProj(@RequestBody JSONObject jsonObject)
    {
        try
        {
            int type = jsonObject.getIntValue("type");
            int num = jsonObject.getIntValue("num");
            this.tfamAssetOldService.projection(type, num);
            return ResponseEntity.ok(ResultUtil.successJson());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }
    @PostMapping("/projlist")
    public ResponseEntity<JSONObject> projlist(@RequestBody SearchParams searchParams)
    {
        try
        {
            Page<JSONObject> projlist= this.tfamAssetOldService.projlist(
                    searchParams.getParams(),
                searchParams.getOffset(),
                searchParams.getLimit(),
                searchParams.getOrderby());
            return ResponseEntity.ok(ResultUtil.successJson(projlist));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }
    @PostMapping("/dodep")
    public ResponseEntity<JSONObject> doDep(@RequestBody JSONObject jsonObject)
    {
        try
        {
            String workloadData = jsonObject.getString("workloadData");
            JSONObject cdtMap = jsonObject.getJSONObject("formData");
            if (workloadData != null && !"".equals(workloadData))
            {
                JSONArray workoadList = jsonObject.getJSONArray(workloadData);
                this.tfamAssetOldService.depMonthly( cdtMap, workoadList);
            }
            else
            {
                this.tfamAssetOldService.depMonthly( cdtMap, null);
            }
            return ResponseEntity.ok(ResultUtil.successJson());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }
    @PostMapping("/checkasset")
    @ResponseBody
    public ResponseEntity<JSONObject> checkAsset()
    {
        try
        {
            boolean bol = this.tfamAssetOldService.getNoCheckAssets();
            if (bol)
            {
                return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,""));
            }
            else
            {
                return ResponseEntity.ok(ResultUtil.successJson());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }
    @PostMapping("/workloadlist")
    @ResponseBody
    public ResponseEntity<JSONObject> workloadList(@RequestParam(required = false) String type)
    {
        try
        {
            List<JSONObject> list = this.tfamAssetOldService.getWorkloadList();
            if ("check".equals(type))
            {
                if (list.size() > 0)
                {
                    return ResponseEntity.ok(ResultUtil.successJson());
                }
                else
                {
                    return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,""));
                }
            }
            else
            {
                return ResponseEntity.ok(ResultUtil.successJson(list));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }
    @PostMapping("/busdate")
    @ResponseBody
    public ResponseEntity<JSONObject> busDate()
    {
        try
        {
            JSONObject entityMap = this.tfamAssetOldService.getBusDate();
            return ResponseEntity.ok(ResultUtil.successJson(entityMap));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }
    @PostMapping("/loadinfo")
    @ResponseBody
    public ResponseEntity<JSONObject> loadInfo( @RequestBody String  jsonObject)
    {
        try
        {
            JSONObject jsonObject1 = JSONObject.parseObject(jsonObject);
            String[] split = jsonObject1.getString("depdate").split("-");
            String year = split[0];
            String month = split[1];
            JSONObject entityMap = this.tfamAssetOldService.loadInfo(year, month);
            return ResponseEntity.ok(ResultUtil.successJson(entityMap));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }
    /**
     * 生成凭证
     * @return
     */
    @RequestMapping("/produceVoucher")
    @ResponseBody
    public ResponseEntity<JSONObject> produceVoucher(@RequestBody String jsonObject)
    {
        try{
            this.tfamAssetOldService.produceVoucher(jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
        return ResponseEntity.ok(ResultUtil.successJson("凭证生成成功！"));
    }
}
