package com.geeke.fixasset.usingstatus.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.cardasset.credit.entity.TfamUsingstatusEntry;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.fixasset.changeway.entity.TfamChangeWay;
import com.geeke.fixasset.usingstatus.entity.TfamUsingstatus;
import com.geeke.fixasset.usingstatus.service.TfamUsingstatusService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 卡片使用状态Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/fixasset/usingstatus/tfamUsingstatus")
public class TfamUsingstatusController extends BaseController {

    @Autowired
    private TfamUsingstatusService tfamUsingstatusService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TfamUsingstatus entity = tfamUsingstatusService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<TfamUsingstatus> result = tfamUsingstatusService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<TfamUsingstatus> result = tfamUsingstatusService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody TfamUsingstatus entity) {
        String companyId = SessionUtils.getUser().getCompanyId().toString();
        List<Parameter> params = null;
        params = Lists.newArrayList();
        params.add(new Parameter("company_id", "=",companyId));
        params.add(new Parameter("name", "=",entity.getName()));
        List<TfamUsingstatus> tfamUsingstatus1 = tfamUsingstatusService.listAll(params, "");
        if (tfamUsingstatus1.size() != 0){
            if (!Objects.equals(tfamUsingstatus1.get(0).getId(), entity.getId())) {
                return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,"当前名称已存在"));
            }
        }
        params = Lists.newArrayList();

        params.add(new Parameter("code", "=", entity.getCode()));
        List<TfamUsingstatus> tfamUsingstatusEntries = tfamUsingstatusService.listAll(params, "");
        if (tfamUsingstatusEntries.size()>0){
            if (StringUtils.isBlank(entity.getId())) {
                return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400, "编码" + entity.getCode() + "已经存在"));
            } else if (!entity.getId().equals(tfamUsingstatusEntries.get(0).getId())){
                return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400, "编码" + entity.getCode() + "已经存在"));
            }
        }
        String id = tfamUsingstatusService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TfamUsingstatus entity) {
        int rows = tfamUsingstatusService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<TfamUsingstatus> entitys) {
        List<String> ids = tfamUsingstatusService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<TfamUsingstatus> entitys) {
        List<String> ids = tfamUsingstatusService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<TfamUsingstatus> entitys) {
        int rows = tfamUsingstatusService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
