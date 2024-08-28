package com.geeke.fixasset.changeway.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.fixasset.category.entity.TfamCategory;
import com.geeke.fixasset.changeway.entity.TfamChangeWay;
import com.geeke.fixasset.changeway.service.TfamChangeWayService;
import com.geeke.fixasset.usingstatus.entity.TfamUsingstatus;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import java.util.List;
import java.util.Objects;

import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 变动方式Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/fixasset/changeway/tfamChangeWay")
public class TfamChangeWayController extends BaseController {

    @Autowired
    private TfamChangeWayService tfamChangeWayService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TfamChangeWay entity = tfamChangeWayService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<TfamChangeWay> result = tfamChangeWayService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<TfamChangeWay> result = tfamChangeWayService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody TfamChangeWay entity) {
        String companyId = SessionUtils.getUser().getCompanyId().toString();
        List<Parameter> params = null;
        params = Lists.newArrayList();
        params.add(new Parameter("company_id", "=",companyId));
        params.add(new Parameter("name", "=",entity.getName()));
        List<TfamChangeWay> tfamChangeWay1 = tfamChangeWayService.listAll(params, "");
        if (tfamChangeWay1.size() != 0){
            if (!Objects.equals(tfamChangeWay1.get(0).getId(), entity.getId())) {
                return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,"当前名称已存在"));
            }
        }
        params = Lists.newArrayList();

        params.add(new Parameter("code", "=", entity.getCode()));
        List<TfamChangeWay> tfamUsingstatusEntries = tfamChangeWayService.listAll(params, "");
        if (tfamUsingstatusEntries.size()>0){
            if (StringUtils.isBlank(entity.getId())) {
                return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400, "编码" + entity.getCode() + "已经存在"));
            } else if (!entity.getId().equals(tfamUsingstatusEntries.get(0).getId())){
                return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400, "编码" + entity.getCode() + "已经存在"));
            }
        }
        String id = tfamChangeWayService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TfamChangeWay entity) {
        int rows = tfamChangeWayService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<TfamChangeWay> entitys) {
        List<String> ids = tfamChangeWayService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<TfamChangeWay> entitys) {
        List<String> ids = tfamChangeWayService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<TfamChangeWay> entitys) {
        int rows = tfamChangeWayService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
    @PostMapping(value = "/exportTfamChangeWay")
    public ResponseEntity<JSONObject> exportTfamChangeWay(HttpServletResponse response)  throws Exception {
        int rows = tfamChangeWayService.exportTfamChangeWay(response);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
    @PostMapping(value = "importTfamChangeWay")
    public ResponseEntity<JSONObject> importTfamChangeWay(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        int rows = tfamChangeWayService.importTfamChangeWay(file.getInputStream());
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
