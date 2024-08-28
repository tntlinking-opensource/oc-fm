package com.geeke.tgl.tmp.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.sys.controller.BaseController;
import com.geeke.tgl.tmp.entity.TmpIdlist;
import com.geeke.tgl.tmp.service.TmpIdlistService;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import java.util.List;
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
 * 利润结转科目配置Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/tgl/tmp/tmpIdlist")
public class TmpIdlistController extends BaseController {

    @Autowired
    private TmpIdlistService tmpIdlistService;

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<TmpIdlist> result = tmpIdlistService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<Parameter> params = searchParams.getParams();
        params.add(new Parameter("tenant_id","=", SessionUtils.getUser().getCompanyId()));
        List<TmpIdlist> result = tmpIdlistService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody TmpIdlist entity) {
        String id = tmpIdlistService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }
}
