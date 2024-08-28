package com.geeke.tgl.aging.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.sys.controller.BaseController;
import com.geeke.tgl.aging.entity.Aging;
import com.geeke.tgl.aging.entity.ContactsBook;
import com.geeke.tgl.aging.service.ContactsBookService;
import com.geeke.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 往来账龄报表Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/tgl/contactsbook")
public class ContactsBookController extends BaseController {

    @Autowired
    private ContactsBookService contactsBookService;

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody Map<String, Object> map) {
        Page<Map<String, Object>> result = contactsBookService.listPage(map);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "export")
    public ResponseEntity<JSONObject> export(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        int rows = contactsBookService.exportListAll(map, response);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

}
