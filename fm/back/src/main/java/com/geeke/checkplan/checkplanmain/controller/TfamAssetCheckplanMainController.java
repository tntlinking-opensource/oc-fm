package com.geeke.checkplan.checkplanmain.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.geeke.checkplan.attach.entity.TfamAssetCheckplanMainAttach;
import com.geeke.checkplan.attach.service.TfamAssetCheckplanMainAttachService;
import com.geeke.checkplan.checkplanmain.entity.TfamAssetCheckplanMain;
import com.geeke.checkplan.checkplanmain.service.TfamAssetCheckplanMainService;
import com.geeke.checkplan.dept.dao.TfamAssetCheckplanMainDeptDao;
import com.geeke.checkplan.dept.entity.TfamAssetCheckplanMainDept;
import com.geeke.checkplan.dept.service.TfamAssetCheckplanMainDeptService;
import com.geeke.checkplan.detail.entity.TfamAssetCheckplanDetail;
import com.geeke.checkplan.detail.service.TfamAssetCheckplanDetailService;
import com.geeke.checkplan.req.CheckPlanMainStatusAndCategoryReq;
import com.geeke.checkplan.req.CheckplanMainSaveReq;
import com.geeke.checkplan.req.DownloadReq;
import com.geeke.checkplan.req.PersonCheckReq;
import com.geeke.checkplan.resp.StatusAndCategoryResp;
import com.geeke.checkplan.resp.TfamAssetCheckplanMainDto;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.fixasset.asset.dao.TfamAssetDao;
import com.geeke.fixasset.asset.dto.ConditionDto;
import com.geeke.fixasset.asset.entity.TfamAsset;
import com.geeke.org.dao.DepartmentDao;
import com.geeke.org.entity.Department;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 盘点主表Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/checkplan/checkplanmain/tfamAssetCheckplanMain")
public class TfamAssetCheckplanMainController extends BaseController {

    @Autowired
    private TfamAssetCheckplanMainService tfamAssetCheckplanMainService;


    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TfamAssetCheckplanMain entity = tfamAssetCheckplanMainService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping("/detail")
    public ResponseEntity<JSONObject> detail(@RequestBody TfamAssetCheckplanMain main) {
        TfamAssetCheckplanMainDto dto = tfamAssetCheckplanMainService.detail(main.getId());
        return ResponseEntity.ok(ResultUtil.successJson(dto));
    }

    @PostMapping("/get")
    public ResponseEntity<JSONObject> get(@RequestBody TfamAssetCheckplanMain main) {
        TfamAssetCheckplanMain entity = tfamAssetCheckplanMainService.get(main.getId());

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<TfamAssetCheckplanMain> result = tfamAssetCheckplanMainService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<TfamAssetCheckplanMain> result = tfamAssetCheckplanMainService.listAll(
            searchParams.getParams(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody CheckplanMainSaveReq req) {
        String id = tfamAssetCheckplanMainService.save(req).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }


    /**
     * 返回 根据 部门 状态 类别查出来的 数据
     * @param req
     * @return
     */
    @PostMapping(value = "getByStatusAndCategory")
    public ResponseEntity<JSONObject> getByStatusAndCategory(@RequestBody CheckplanMainSaveReq req) {
        List<StatusAndCategoryResp> statusAndCategoryResps = tfamAssetCheckplanMainService.getByStatusAndCategory(req);
        return ResponseEntity.ok(ResultUtil.successJson(statusAndCategoryResps));
    }

    @PostMapping(value = "getByStatusAndCategoryItem")
    public ResponseEntity<JSONObject> getByStatusAndCategoryItem(@RequestBody CheckplanMainSaveReq req) {
        List<TfamAsset> assetList = tfamAssetCheckplanMainService.getByStatusAndCategoryItem(req);
        return ResponseEntity.ok(ResultUtil.successJson(assetList));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TfamAssetCheckplanMain entity) {
        int rows = tfamAssetCheckplanMainService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<TfamAssetCheckplanMain> entitys) {
        List<String> ids = tfamAssetCheckplanMainService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<TfamAssetCheckplanMain> entitys) {
        List<String> ids = tfamAssetCheckplanMainService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<TfamAssetCheckplanMain> entitys) {
        int rows = tfamAssetCheckplanMainService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "update")
    public ResponseEntity<JSONObject> updateBase(@RequestBody CheckplanMainSaveReq req) {
        int rows = tfamAssetCheckplanMainService.update(req);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    /**
     * 判断盘点明细是否在(是否删除，是己关闭，是否不在盘点期)
     * @param req
     * @return
     */
    @PostMapping(value = "selectPlanByPerson")
    public ResponseEntity<JSONObject> selectPlanByPerson(@RequestBody CheckplanMainSaveReq req) {
        List<TfamAssetCheckplanMainDto> result = tfamAssetCheckplanMainService.selectPlanByPerson();
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "personCheck")
    public ResponseEntity<JSONObject> personCheck(@RequestBody PersonCheckReq req) {
        boolean result = tfamAssetCheckplanMainService.personCheck(req);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @RequestMapping("download")
    public void download(@RequestBody DownloadReq req, HttpServletRequest request, HttpServletResponse response) throws Exception{
        tfamAssetCheckplanMainService.download(req,request,response);
    }

    @PostMapping(value = "saveCheckAttach")
    public ResponseEntity<JSONObject> saveCheckAttach(@RequestParam("conditionDto") String conditionStr,
                                                      @RequestParam("file") MultipartFile[] file) throws Exception {
        ConditionDto conditionDto = JSONObject.parseObject(conditionStr, ConditionDto.class);
        boolean result = tfamAssetCheckplanMainService.saveCheckAttach(conditionDto,file);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }



}
