package com.geeke.cardasset.credit.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.cardasset.credit.entity.OrgDepartmentEntry;
import com.geeke.cardasset.credit.service.OrgDepartmentEntryService;
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

import javax.servlet.http.HttpSession;

/**
 * 资产入账Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/cardasset/credit/orgDepartmentEntry")
public class OrgDepartmentEntryController extends BaseController {

    @Autowired
    private OrgDepartmentEntryService orgDepartmentEntryService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        OrgDepartmentEntry entity = orgDepartmentEntryService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<OrgDepartmentEntry> result = orgDepartmentEntryService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<OrgDepartmentEntry> result = orgDepartmentEntryService.listAll(
            searchParams.getParams(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody OrgDepartmentEntry entity) {
        String id = orgDepartmentEntryService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody OrgDepartmentEntry entity) {
        int rows = orgDepartmentEntryService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<OrgDepartmentEntry> entitys) {
        List<String> ids = orgDepartmentEntryService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<OrgDepartmentEntry> entitys) {
        List<String> ids = orgDepartmentEntryService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<OrgDepartmentEntry> entitys) {
        int rows = orgDepartmentEntryService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }


}
