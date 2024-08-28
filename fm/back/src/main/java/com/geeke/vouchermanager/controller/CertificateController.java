package com.geeke.vouchermanager.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.common.data.Page;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.vouchermanager.entity.TglVarabastractEntity;
import com.geeke.vouchermanager.service.CertificateService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/certificate")

public class CertificateController extends BaseController {

    @Autowired
    private CertificateService certificateService;

    @GetMapping("/getAbstract")
    public ResponseEntity getAbstract(@RequestParam String data,@RequestParam int limit,@RequestParam int offset){
        List<TglVarabastractEntity> anAbstract = certificateService.getAbstract(data,limit,offset);
        String total = certificateService.countAbstract(data);
        Page<TglVarabastractEntity> page = new Page<>(Integer.parseInt(total),anAbstract);
        ResponseEntity ok = ResponseEntity.ok(ResultUtil.successJson(page));
        return ok;
    }

    @GetMapping("/addAbstract")
    public ResponseEntity addAbstract(@RequestParam String data){
        try {
            certificateService.addAbstract(data);
            return ResponseEntity.ok((ResultUtil.successJson("添加成功")));
        }catch (Exception e){
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }


    }

    @GetMapping("/delAbstract")
    public ResponseEntity delAbstract(@RequestParam String id){
        try {
            certificateService.delAbstract(id);
            return ResponseEntity.ok((ResultUtil.successJson("删除成功")));
        }catch (Exception e){
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }

    }

    @GetMapping("/getPeriodList")
    public ResponseEntity getPeriodList(){
        try {
            List<Map<String,String>> periodList = certificateService.getPeriodList();
            ResponseEntity<List<Map<String, String>>> ok = ResponseEntity.ok(periodList);
            return ResponseEntity.ok(periodList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }

    }


}
