package com.geeke.vouchermanager.controller;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.sys.controller.BaseController;
import com.geeke.tgl.prop.entity.PropValue;
import com.geeke.tgl.prop.service.PropValueService;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.vouchermanager.entity.TglAccounts;
import com.geeke.vouchermanager.entity.TglVoucherDetails;
import com.geeke.vouchermanager.entity.TglVoucherMains;
import com.geeke.vouchermanager.service.TglAccountsService;
import com.geeke.vouchermanager.service.TglVoucherDetailsService;
import com.geeke.vouchermanager.service.TglVoucherMainsService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.omg.CORBA.StringHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 制证Controller
 * @author
 * @version
 */
@Api(value = "/vouchermanager/tglVoucherMains", tags = {"制证Controller"})
@RestController
@RequestMapping(value = "/vouchermanager/tglVoucherMains")

public class TglVoucherMainsController extends BaseController {

    @Autowired
    private TglVoucherMainsService tglVoucherMainsService;
    @Autowired
    private PropValueService propValueService;
    @Autowired
    private TglVoucherDetailsService tglVoucherDetailsService;
    @Autowired
    private TglAccountsService tglAccountsService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "通过id获取凭证信息", notes = "", httpMethod = "GET")
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TglVoucherMains entity = tglVoucherMainsService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        List<Parameter> params = searchParams.getParams();
        params.add(new Parameter("tenant_id","=",SessionUtils.getUser().getCompanyId()));
        int delect = -1;
        for (int i = 0; i < params.size(); i++) {
            Parameter parameter = params.get(i);
            if (parameter.getColumnName().equals("uqnumbering")&&parameter.getValue().equals("00000000-0000-0000-0000-000000000000")) {
                delect=i;
            }
        }
        if (delect!=-1){
            params.remove(delect);
        }
        Page<TglVoucherMains> result = tglVoucherMainsService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );
        List<TglVoucherMains> rows = result.getRows();
        if (rows==null){
            return ResponseEntity.ok(ResultUtil.successJson(result));
        }
        for (int i = 0; i < rows.size(); i++) {
            TglVoucherMains tglVoucherMains = rows.get(i);
            params = Lists.newArrayList();

            params.add(new Parameter("UQVOUCHERID", "=", tglVoucherMains.getUqvoucherid()));
            tglVoucherMains.setTglVoucherDetailsList(tglVoucherDetailsService.listAll(params, ""));
        }
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<TglVoucherMains> result = tglVoucherMainsService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "TglVoucherMains", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "保存，修改，暂存凭证", notes = "", httpMethod = "POST")
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody TglVoucherMains entity) {
        try {
            String id = tglVoucherMainsService.save(entity).getId();
            return ResponseEntity.ok(ResultUtil.successJson(id));
        } catch (Exception e) {
        return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage())
        );
    }

    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<String>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "凭证删除", notes = "", httpMethod = "POST")
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody List<String> entitys) {
        try {
        int rows = tglVoucherMainsService.delete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
        } catch (Exception e) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage())
            );
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<TglVoucherMains>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<TglVoucherMains> entitys) {
        List<String> ids = tglVoucherMainsService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<TglVoucherMains>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<TglVoucherMains> entitys) {
        List<String> ids = tglVoucherMainsService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<TglVoucherMains>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<TglVoucherMains> entitys) {
        int rows = tglVoucherMainsService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<String>", name = "entitys", value = "", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "vouchercheckdate", value = "", required = true)
    })
    @ApiOperation(value = "凭证审核", notes = "", httpMethod = "POST")
    @PostMapping(value = "/check/{vouchercheckdate}")
    public ResponseEntity<JSONObject> voucherCheck(@RequestBody List<String> entitys,@PathVariable("vouchercheckdate") String vouchercheckdate) {
        try {
            StringHolder errMsg = new StringHolder();

            errMsg.value = "";

            for (int i = 0; i < entitys.size(); i++) {
                String voucherid = entitys.get(i);
                int flag = this.tglVoucherMainsService.checks(voucherid);
                if (flag==1) {
                    return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400, "暂存凭证不能审核！！"));
                } else if (flag==2) {
                    return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400, "常用凭证不能审核！！"));
                } else {
                    this.tglVoucherMainsService.vouchercheck(voucherid, vouchercheckdate, errMsg);
                }

            }
            if (errMsg.value == "") {
                String companyId = SessionUtils.getUser().getCompanyId();
                PropValue byCompany = propValueService.getByCompany(companyId);
                String isCash=byCompany.getStatus();
                if(isCash.equals("1")){
                    voucherCash(entitys,vouchercheckdate);
                }
                return ResponseEntity.ok(ResultUtil.successJson());
            } else {
                return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400, errMsg.value));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400, e.getMessage()));
        }
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<String>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "凭证反审核", notes = "", httpMethod = "POST")
    @PostMapping(value = "/uncheck")
    public ResponseEntity<JSONObject> unVoucherCheck(@RequestBody List<String> entitys) {
        try {
            StringHolder errMsg = new StringHolder();

            errMsg.value = "";

            for (int i = 0; i < entitys.size(); i++) {
                String voucherid = entitys.get(i);
                this.tglVoucherMainsService.unvouchercheck(voucherid, errMsg);

            }
            if (errMsg.value == "") {
                return ResponseEntity.ok(ResultUtil.successJson());
            } else {
                return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400, errMsg.value));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400, e.getMessage()));
        }
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<String>", name = "entitys", value = "", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "vouchercashdate", value = "", required = true)
    })
    @ApiOperation(value = "凭证出纳", notes = "", httpMethod = "POST")
    @PostMapping(value = "/cash/{vouchercashdate}")
    public ResponseEntity<JSONObject> voucherCash(@RequestBody List<String> entitys,@PathVariable("vouchercashdate") String vouchercashdate) {
        try {
            StringHolder errMsg = new StringHolder();

            errMsg.value = "";

            for (int i = 0; i < entitys.size(); i++) {
                String voucherid = entitys.get(i);
                int flag = this.tglVoucherMainsService.checks(voucherid);
                    this.tglVoucherMainsService.vouchercash(voucherid, vouchercashdate, errMsg);

            }
            if (errMsg.value == "") {
                return ResponseEntity.ok(ResultUtil.successJson());
            } else {
                return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400, errMsg.value));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400, e.getMessage()));
        }
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<String>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "凭证反出纳", notes = "", httpMethod = "POST")
    @PostMapping(value = "/uncash")
    public ResponseEntity<JSONObject> unVoucherCash(@RequestBody List<String> entitys) {
        try {
            StringHolder errMsg = new StringHolder();

            errMsg.value = "";
            for (int i = 0; i < entitys.size(); i++) {
                String voucherid = entitys.get(i);
                this.tglVoucherMainsService.unvouchercash(voucherid, errMsg);
            }
            if (errMsg.value == "") {
                return ResponseEntity.ok(ResultUtil.successJson());
            } else {
                return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400, errMsg.value));
            }
        }  catch (Exception e) {
        e.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400, e.getMessage()));
    }

    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<String>", name = "entitys", value = "", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "vouchercashdate", value = "", required = true)
    })
    @ApiOperation(value = "凭证记账", notes = "", httpMethod = "POST")
    @PostMapping(value = "/end/{vouchercashdate}")
    public ResponseEntity<JSONObject> voucherEnd(@RequestBody List<String> entitys,@PathVariable("vouchercashdate") String vouchercashdate) {
        try {

            StringHolder errMsg = new StringHolder();

            errMsg.value = "";

            String result = "";
            for (int i = 0; i < entitys.size(); i++) {
                String voucherid = entitys.get(i);
                // TODO: 15/5/2023 这里缺少了往来核销的一部分代码，先留着
                //新增 2017-12-12
                //Created by wuzehua on 2017/10/12.
                //获取凭证记账前核销所存的临时表中的内容
//                List<EntityMap> list = this.voucherMainBP.getTempData(voucherid);
//                if (list != null && list.size() != 0) {
//                    for (int j = 0; j < list.size(); j++) {
//                        String mainData = list.get(j).getString("main_data");
//                        //将tgl_ac_offset_detail修改过的的mainData数据改回来,原封不动的传给前台
//                        //mainData = mainData.replaceAll("\"yetmoney\":0", "\"yetmoney\":null");
//
//                        String detailDatas = list.get(j).getString("detail_datas");
//                        //detailDatas = detailDatas.replaceAll("\"yetmoney\":0", "\"yetmoney\":null");
//                        this.currentoffsetBP.doManualRush(mainData, detailDatas);
//                        this.voucherMainBP.deleteTempData(voucherid);
//                    }
//                }




                this.tglVoucherMainsService.voucherend(voucherid,vouchercashdate, errMsg);
            }
            if (errMsg.value == "") {
                return ResponseEntity.ok(ResultUtil.successJson());
            } else {
                return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400, errMsg.value));
            }
        }  catch (Exception e) {
        e.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400, e.getMessage()));
        }
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<String>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "凭证反记账", notes = "", httpMethod = "POST")
    @PostMapping(value = "/unEnd")
    public ResponseEntity<JSONObject> unVouCherEnd(@RequestBody List<String> entitys) {
        try {
            StringHolder errMsg = new StringHolder();

            errMsg.value = "";
            for (int i = 0; i < entitys.size(); i++) {
                String voucherid = entitys.get(i);
                this.tglVoucherMainsService.unVoucherend(voucherid,errMsg);
            }
            if (errMsg.value == "") {
                return ResponseEntity.ok(ResultUtil.successJson());
            } else {
                return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400, errMsg.value));
            }
        }  catch (Exception e) {
        e.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400, e.getMessage()));
        }
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "file", name = "file", value = "")
    })
    @ApiOperation(value = "导入凭证", notes = "", httpMethod = "POST")
    @PostMapping(value = "import")
    public ResponseEntity<JSONObject> importVoucher(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        tglVoucherMainsService.importVoucher(file);
        return ResponseEntity.ok(ResultUtil.successJson());
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping(value = { "xjlllistPage", "" })
    public ResponseEntity<JSONObject> xjlllistPage(@RequestBody SearchParams searchParams) {
        List<Parameter> params = searchParams.getParams();
        params.add(new Parameter("x.TENANT_ID","=",SessionUtils.getUser().getCompanyId()));
        Page<Map<String,String>> result = tglVoucherMainsService.xjlllistPage(
                searchParams.getParams(),
                searchParams.getOffset(),
                searchParams.getLimit(),
                searchParams.getOrderby()
        );
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "string", name = "jsonCondition", value = "", required = true)
    })
    @ApiOperation(value = "期末余额结转", notes = "", httpMethod = "POST")
    @PostMapping("/autoconvert")
    public ResponseEntity<JSONObject> autoconvert(@RequestBody String jsonCondition){
        JSONObject map = JSON.parseObject(jsonCondition);
        String uqglobalperiodid = map.getString("uqglobalperiodid");
        SearchParams searchParams = new SearchParams();
        List<Parameter> params = Lists.newArrayList();
        params.add(new Parameter("UQGLOBALPERIODID", "=", uqglobalperiodid));
        params.add(new Parameter("tenant_id", "=", SessionUtils.getUser().getCompanyId()));
        params.add(new Parameter("UQNUMBERING", "=", "CF10100D-ED35-4B1D-AAC7-235F68DFB117"));
        params.add(new Parameter("INTDELETEFLAG", "<>", "1"));
        searchParams.setParams(params);
        List<TglVoucherMains> result = tglVoucherMainsService.listAll(searchParams.getParams(), searchParams.getOrderby());
        if (result.size() > 0) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400, "当期已经存在结转凭证"));
        }
        params = Lists.newArrayList();
        params.add(new Parameter("INTPROPERTY", "=", "5"));

        params.add(new Parameter("UQTYPEID", "in", ListUtil.of(9,10)));
        params.add(new Parameter("INTISLASTLEVEL", "=", 1));
        params.add(new Parameter("tenant_id", "=", SessionUtils.getUser().getCompanyId()));
        searchParams.setParams(params);
        List<TglAccounts> tglAccounts = tglAccountsService.listAll(searchParams.getParams(), searchParams.getOrderby());
        try {
        for (TglAccounts tglAccount : tglAccounts) {
            map.put("unprofitaccount",tglAccount.getUqaccountid());

                tglVoucherMainsService.autoconvert(map);
        }
        } catch (Exception e) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400, e.getMessage()));
        }
        return ResponseEntity.ok(ResultUtil.successJson());
    }

    public static void main(String[] args) {
        String strSql = " select * ,t.offsetmoney as remainmoney from (SELECT   ai.iniid," +
                "          '' as uqvoucherdetailid, " +
                "          '' as voucherid , " +
                "          ai.BUSDATE as accountdate ," +
                "          concat('[',ac.varaccountcode,']',ac.VARACCOUNTNAME) as accountcode ," +
                "          ai.UQACCOUNTID as uqaccountid," +
                "          ai.uqledgetypeid as uqledgetypeid," +
                "          ai.UQLEDGEID as uqledgeid," +
                "          ai.UQCOMPANYID as uqcompanyid ," +
                "          tlt.VARLEDGETYPENAME as accountledgertype, " +
                "          tl.VARLEDGENAME as accountledger,  " +
                "          ai.VARABSTRACT as varabstract  ," +
                "          CASE WHEN (ai.MNYDEBIT = 0) THEN ai.MNYCREDIT" +
                "               ELSE ai.MNYDEBIT END as offsetmoney ," +
                "          0 as yetmoney ," +
                "          '否' as isrelate ," +
                "          '' as intvouchernum," +
                "          ub.DISPLAYNAME as accountuser ," +
                "          ai.INTTYPE as inttype" +
                "          FROM TGL_AC_INI ai " +
                "          INNER JOIN TGL_AC_INI_REL ir on ai.UQACCOUNTID = ir.UQACCOUNTID and ir.tenant_id=? " +
                "          INNER JOIN tgl_accounts ac on ac.UQACCOUNTID=ai.UQACCOUNTID and ac.tenant_id=? " +
                "          left JOIN tgl_ledger tl on tl.UQLEDGEID = ai.UQLEDGEID and tl.tenant_id=?" +
                "          left JOIN tgl_ledgetype tlt on tlt.UQLEDGETYPEID = tl.UQLEDGETYPEID and tlt.tenant_id=?" +
                "          inner join tsys_userbase ub on ub.id = ir.UQUSERID and ub.tenant_id=? " +
                "          where ai.tenant_id=? ) t " +
                "          where t.inttype = ?  " ;
        strSql += " order by t.accountdate " ;
        System.out.println(strSql);
    }


}
