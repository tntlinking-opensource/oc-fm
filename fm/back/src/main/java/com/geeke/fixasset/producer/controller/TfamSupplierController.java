package com.geeke.fixasset.producer.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.fixasset.producer.entity.TfamSupplier;
import com.geeke.fixasset.producer.service.TfamSupplierService;
import com.geeke.fixasset.usingstatus.entity.TfamUsingstatus;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import java.util.List;
import java.util.Objects;

import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 供应商管理Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/fixasset/producer/tfamSupplier")
public class TfamSupplierController extends BaseController {

    @Autowired
    private TfamSupplierService tfamSupplierService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TfamSupplier entity = tfamSupplierService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<TfamSupplier> result = tfamSupplierService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<TfamSupplier> result = tfamSupplierService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody TfamSupplier entity) {
        String companyId = SessionUtils.getUser().getCompanyId().toString();
        List<Parameter> params = null;
        params = Lists.newArrayList();
        params.add(new Parameter("company_id", "=",companyId));
        params.add(new Parameter("name", "=",entity.getName()));
        List<TfamSupplier> tfamSupplier1 = tfamSupplierService.listAll(params, "");
        if (tfamSupplier1.size() != 0){
            if (!Objects.equals(tfamSupplier1.get(0).getId(), entity.getId())) {
                return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,"当前名称已存在"));
            }
        }
        String id = tfamSupplierService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TfamSupplier entity) {
        int rows = tfamSupplierService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<TfamSupplier> entitys) {
        List<String> ids = tfamSupplierService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<TfamSupplier> entitys) {
        List<String> ids = tfamSupplierService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<TfamSupplier> entitys) {
        int rows = tfamSupplierService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "", required = true)
    })
    @ApiOperation(value = "供应商导出", notes = "供应商导出", httpMethod = "POST")
    @PostMapping(value = "/exportTfamSupplier")
    public ResponseEntity<JSONObject> exportTfamSupplier(HttpServletResponse response)  throws Exception {
        int rows = tfamSupplierService.exportTfamSupplier(response);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
    @PostMapping(value = "importTfamSupplier")
    public ResponseEntity<JSONObject> importTfamSupplier(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        int rows = tfamSupplierService.importTfamSupplier(file.getInputStream());
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
