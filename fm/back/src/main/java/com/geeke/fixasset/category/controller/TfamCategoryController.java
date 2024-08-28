package com.geeke.fixasset.category.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.fixasset.category.entity.TfamCategory;
import com.geeke.fixasset.category.service.TfamCategoryService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 资产类别Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/fixasset/category/tfamCategory")
public class TfamCategoryController extends BaseController {

    @Autowired
    private TfamCategoryService tfamCategoryService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TfamCategory entity = tfamCategoryService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<TfamCategory> result = tfamCategoryService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<TfamCategory> result = tfamCategoryService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody TfamCategory entity) {
        String companyId = SessionUtils.getUser().getCompanyId().toString();
        List<Parameter> params = null;
        params = Lists.newArrayList();
        params.add(new Parameter("company_id", "=",companyId));
        params.add(new Parameter("fullcode", "=",entity.getFullcode()));
        List<TfamCategory> tfamCategories1 = tfamCategoryService.listAll(params, "");
        if (tfamCategories1.size() != 0){
            if (!Objects.equals(tfamCategories1.get(0).getId(), entity.getId())) {
                return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,"当前编码已存在"));
            }
        }

        params = Lists.newArrayList();
        params.add(new Parameter("company_id", "=",companyId));
        params.add(new Parameter("fullname", "=",entity.getFullname()));
        List<TfamCategory> tfamCategories2 = tfamCategoryService.listAll(params, "");
        if (tfamCategories2.size() != 0){
            if (!Objects.equals(tfamCategories2.get(0).getId(), entity.getId())) {
                return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,"当前名称已经存在"));
            }
        }
        if (!entity.getParentid().equals("0")){


            /*获取子表列表   用户*/
            params = Lists.newArrayList();
            params.add(new Parameter("company_id", "=",companyId));
            params.add(new Parameter("id", "=",entity.getParentid()));
            List<TfamCategory> tfamCategories = tfamCategoryService.listAll(params, "");
            if (tfamCategories.size()!=0){
                TfamCategory tfamCategory = tfamCategories.get(0);
                tfamCategory.setIsleaf(0);
                tfamCategoryService.save(tfamCategory);
            }
        }
        String id = tfamCategoryService.save(entity).getId();

        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TfamCategory entity) {
        List<Parameter> params = null;
        String companyId = SessionUtils.getUser().getCompanyId().toString();
        /*获取子表列表   用户*/
        params = Lists.newArrayList();
        params.add(new Parameter("company_id", "=",companyId));
        params.add(new Parameter("id", "=",entity.getId()));
        int rows=0;
        List<TfamCategory> tfamCategories = tfamCategoryService.listAll(params, "");
        if (tfamCategories.size()!=0){
            TfamCategory tfamCategory = tfamCategories.get(0);
            tfamCategory.setIsleaf(0);
            rows = tfamCategoryService.delete(tfamCategory);
            if (!tfamCategory.getParentid().equals("0")){
                params = Lists.newArrayList();
                params.add(new Parameter("company_id", "=",companyId));
                params.add(new Parameter("parentid", "=",tfamCategory.getParentid()));
                List<TfamCategory> tfamCategories1 = tfamCategoryService.listAll(params, "");
                if (tfamCategories1.size()==0){
                    params = Lists.newArrayList();
                    params.add(new Parameter("company_id", "=",companyId));
                    params.add(new Parameter("id", "=",tfamCategory.getParentid()));
                    List<TfamCategory> tfamCategories2 = tfamCategoryService.listAll(params, "");
                    if (tfamCategories2.size()!=0){
                        TfamCategory tfamCategory1 = tfamCategories2.get(0);
                        tfamCategory1.setIsleaf(1);
                        tfamCategoryService.save(tfamCategory1);
                    }
                }

            }

        }



        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<TfamCategory> entitys) {
        List<String> ids = tfamCategoryService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<TfamCategory> entitys) {
        List<String> ids = tfamCategoryService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<TfamCategory> entitys) {
        int rows = tfamCategoryService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
    @RequestMapping({"getData"})
    @ResponseBody
    public ResponseEntity getData(@RequestParam(required = false) String jsonCondition) {
        try {
            //页面上加载需要的内容不需要参数
            List<Map<String, Object>> list = this.tfamCategoryService.getTree("0", 0);
            return ResponseEntity.ok(ResultUtil.successJson(list));
        } catch (Exception var6) {
            var6.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,var6.getMessage()));
        }
    }
    @RequestMapping({"getDataByID/{id}"})
    @ResponseBody
    public ResponseEntity getDataByID(@PathVariable("id") String id) {
        try {
            //页面上加载需要的内容不需要参数
            TfamCategory entity = tfamCategoryService.get(id);
            String jsonString = JSONObject.toJSONString(entity);
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            String parentid = entity.getParentid();
            if (!parentid.equals("0")){
                TfamCategory tfamCategory = tfamCategoryService.get(parentid);
                jsonObject.put("parentid",tfamCategory.getId());
            }
            return ResponseEntity.ok(ResultUtil.successJson(jsonObject));
        } catch (Exception var6) {
            var6.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,var6.getMessage()));
        }
    }
    @PostMapping(value = "/exportTfamCategory")
    public ResponseEntity<JSONObject> exportTfamCategory(HttpServletResponse response)  throws Exception {
        int rows = tfamCategoryService.exportTfamCategory(response);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
    @PostMapping(value = "importTfamCategory")
    public ResponseEntity<JSONObject> importTfamCategory(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        int rows = tfamCategoryService.importTfamCategory(file.getInputStream());
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
