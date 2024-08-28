package com.geeke.vouchermanager.service;

import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.sys.entity.Action;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.utils.*;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.vouchermanager.dao.TglLedgerDao;
import com.geeke.vouchermanager.dao.TglLedgerModelDao;
import com.geeke.vouchermanager.dao.TglLedgetypeDao;
import com.geeke.vouchermanager.entity.GroupAccountsEntityTree;
import com.geeke.vouchermanager.entity.TglLedger;
import com.geeke.vouchermanager.entity.TglLedgerModel;
import com.geeke.vouchermanager.entity.TglLedgetype;
import com.geeke.vouchermanager.service.TglLedgerService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.InputStream;
import java.util.*;

import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang.ObjectUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.omg.CORBA.StringHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 分户Service
 * @author
 * @version
 */

@Service("tglLedgetypeService")
@Transactional(readOnly = false)
public class TglLedgetypeService extends CrudService<TglLedgetypeDao, TglLedgetype> {

    @Autowired
    private TglLedgerDao tglLedgerDao;
    @Autowired
    private TglLedgetypeDao tglLedgetypeDao;

    @Autowired
    private TglLedgerService tglLedgerService;
    @Autowired
    private TglLedgerModelDao tglLedgerModelDao;

    @Override
    public TglLedgetype get(String id) {
        TglLedgetype tglLedgetype = super.get(id);

        List<Parameter> params = null;

        /*获取子表列表   分户明细表*/
        params = Lists.newArrayList();

        params.add(new Parameter("UQLEDGETYPEID", "=", tglLedgetype.getUqledgetypeid()));
        tglLedgetype.setTglLedgerList(tglLedgerService.listAll(params, ""));

        return tglLedgetype;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public TglLedgetype save(TglLedgetype tglLedgetype) {
        TglLedgetype tglLedgetypeTemp = super.save(tglLedgetype);

        /* 保存子表数据     分户明细表 */
//        saveTglLedgerList(tglLedgetypeTemp);

        return tglLedgetypeTemp;
    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public TglLedgetype edit(TglLedgetype tglLedgetype) throws Exception {
        //验证同级下的类别名称是否唯一,如果名称不唯一，则抛出异常
        List<Parameter> params = null;
        PageRequest pageRequest;
        params = Lists.newArrayList();
        params.add(new Parameter("VARLEDGETYPENAME", "=", tglLedgetype.getVarledgetypename()));
        params.add(new Parameter("tenant_id", "=", SessionUtils.getUser().getCompanyId()));
        pageRequest = new PageRequest(params);
        /* 处理子表     分户明细表 */
        List<TglLedgetype> tglLedgers = tglLedgetypeDao.listAll(pageRequest);
        if (tglLedgers.size()!=0&&!tglLedgers.get(0).getUqledgetypeid().equals(tglLedgetype.getUqledgetypeid())){
            throw new Exception("分户类别" + tglLedgetype.getVarledgetypename() + "  有同名存在！！");
        }
        if (StringUtils.isBlank(tglLedgetype.getUqledgetypeid())){
            String uqLedgerTypeId= UUID.randomUUID().toString().toUpperCase();
            tglLedgetype.setUqledgetypeid(uqLedgerTypeId);
            tglLedgetype.setUqparentid(uqLedgerTypeId);
        }else {
            tglLedgetype.setId(tglLedgetype.getUqledgetypeid());
        }
        tglLedgetype.setIntstatus(2);
        tglLedgetype.setIntislastlevel(1);
        tglLedgetype.setTenantId(SessionUtils.getUser().getCompanyId());
        TglLedgetype tglLedgetypeTemp = super.save(tglLedgetype);

        /* 保存子表数据     分户明细表 */
        saveTglLedgerList(tglLedgetypeTemp);

        return tglLedgetypeTemp;
    }
    /**
     * 删除
     * @param tglLedgetype
     */

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int deleteall(TglLedgetype tglLedgetype) throws Exception {
        List<Parameter> params = null;
        PageRequest pageRequest;

        /* 处理子表     分户明细表 */
        params = Lists.newArrayList();

        params.add(new Parameter("UQLEDGETYPEID", "=", tglLedgetype.getUqledgetypeid()));
        pageRequest = new PageRequest(params);
        List<TglLedger> tglLedgers = tglLedgerDao.listAll(pageRequest);
        tglLedgetype.setTglLedgerList(tglLedgers);
        //直接判断这个分户是否有凭证了
        int DeleteByUqledgetypeid = tglLedgerDao.isDeleteByUqledgetypeid(tglLedgetype);
        if (DeleteByUqledgetypeid>0){
            throw new Exception("分户："+tglLedgetype.getVarledgetypename()+"已存在制证，不删除");
        }
        if (tglLedgetype.getTglLedgerList() != null && tglLedgetype.getTglLedgerList().size() > 0) {
            tglLedgerService.bulkDelete(tglLedgetype.getTglLedgerList());
        }

        int rows = super.delete(tglLedgetype);
        return rows;
    }

    /**
     * 批量删除
     * @param tglLedgetypeList
     */

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int bulkDeleteall(List<TglLedgetype> tglLedgetypeList) throws Exception {
        for (TglLedgetype tglLedgetype : tglLedgetypeList) {
            deleteall(tglLedgetype);
        }

        return tglLedgetypeList.size();
    }

    /**
     * 生成操作日志
     * @param actionTypeId  操作类型Id
     * @param entity        操作的实体对象
     * @return
     */
    @Override
    protected Action createAction(String actionTypeId, TglLedgetype entity) {
        Action action = super.createAction(actionTypeId, entity);
        if (action == null) {
            return null;
        }
        // 删除时记录把保存的数据保存到回收站
        if (ActionConstants.ACTION_DELETED.equals(actionTypeId)) {}
        return action;
    }

    /* 保存子表数据     分户明细表 */
    private void saveTglLedgerList(TglLedgetype tglLedgetype) throws Exception {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("UQLEDGETYPEID", "=", tglLedgetype.getUqledgetypeid()));
        PageRequest pageRequest = new PageRequest(params);
        List<TglLedger> list_TglLedger = tglLedgerDao.listAll(pageRequest);

        List<TglLedger> deletes = Lists.newArrayList(); // 删除列表
        List<TglLedger> inserts = Lists.newArrayList(); // 添加列表
        List<TglLedger> updates = Lists.newArrayList(); // 更新列表
        for (TglLedger tglLedgerSaved : list_TglLedger) {
            boolean found = false;
            for (TglLedger tglLedger : tglLedgetype.getTglLedgerList()) {
                if (tglLedgerSaved.getUqledgeid().equals(tglLedger.getUqledgeid())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tglLedgerSaved);
            }
        }
        if (deletes.size() > 0) {
            tglLedgerService.bulkDelete(deletes);
            for (TglLedger delete : deletes) {
                tglLedgerDao.deleteGroup(delete.getUqledgeid());
                tglLedgerDao.deleteLEDGERCOMPANY(delete.getUqledgeid());
                isDelete(delete);
            }
        }
        for (TglLedger tglLedger : tglLedgetype.getTglLedgerList()) {
            if (StringUtils.isBlank(tglLedger.getUqledgeid())) {
                tglLedger.setUqledgetypeid(tglLedgetype);
                String uuid= UUID.randomUUID().toString().toUpperCase();
                tglLedger.setUqparentid(uuid);
                tglLedger.setUqledgeid(uuid);
                tglLedger.setIntstatus(2);
                tglLedger.setIntlevel(1);
                tglLedger.setVarledgefullcode(tglLedger.getVarledgecode());
                tglLedger.setVarledgefullname(tglLedger.getVarledgename());
                tglLedger.setTenantId(SessionUtils.getUser().getCompanyId());
                inserts.add(tglLedger);
            } else {
                List<Parameter> params1 = Lists.newArrayList();

                params1.add(new Parameter("VARLEDGENAME", "=", tglLedger.getVarledgename()));
                params1.add(new Parameter("UQLEDGETYPEID", "=", tglLedger.getUqledgetypeid()));
                PageRequest pageRequest1 = new PageRequest(params1);
                List<TglLedger> tglLedgers = tglLedgerDao.listAll(pageRequest1);
                if (tglLedgers.size()!=0&&!tglLedger.getUqledgeid().equals(tglLedgers.get(0).getUqledgeid())){
                    throw new Exception( tglLedgetype.getVarledgetypename() + "分户下有同名存在！！");
                }

                List<Parameter> params2 = Lists.newArrayList();

                params2.add(new Parameter("VARLEDGECODE", "=", tglLedger.getVarledgecode()));
                params2.add(new Parameter("UQLEDGETYPEID", "=", tglLedger.getUqledgetypeid()));
                PageRequest pageRequest2 = new PageRequest(params1);
                List<TglLedger> tglLedgers1 = tglLedgerDao.listAll(pageRequest2);
                if (tglLedgers1.size()!=0&&!tglLedger.getUqledgeid().equals(tglLedgers.get(0).getUqledgeid())){
                    throw new Exception( tglLedgetype.getVarledgetypename() + "分户下有编码相同存在！！");
                }
                tglLedger.setId(tglLedger.getUqledgeid());
                updates.add(tglLedger);
            }
        }
        if (updates.size() > 0) {
            tglLedgerService.bulkUpdate(updates);
            for (TglLedger update : updates) {
                tglLedgerDao.deleteGroup(update.getUqledgeid());
            }
            tglLedgerDao.insertLastLevelToGroupTable(updates);

        }
        if (inserts.size() > 0) {
            List<String> strings = tglLedgerService.bulkInsert(inserts);
            tglLedgerDao.insertLastLevelToGroupTable(inserts);
            ArrayList<Map<String,String>> list = new ArrayList<>();
            for (String string : strings) {
                HashMap<String, String> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("uqledgeid",string);
                objectObjectHashMap.put("companyid",SessionUtils.getUser().getCompanyId());
                list.add(objectObjectHashMap);
            }
            tglLedgerDao.insertLEDGERCOMPANY(list);
        }
    }
    @Override
    public List<TglLedgetype> listAll(List<Parameter> parameters, String orderby) {

        List<Parameter> list = new ArrayList<>();
        if (parameters!=null){
            list.addAll(parameters);
        }
        list.add(new Parameter("tenant_id", "=",SessionUtils.getUser().getCompanyId() ));
        PageRequest pageRequest = new PageRequest(list, orderby);
        return dao.listAll(pageRequest);
    }
    public Boolean isDelete(TglLedger tglLedger) throws Exception {
        //判断转增分户是否已经有制证
        int deleteByUqledgerid = tglLedgerDao.isDeleteByUqledgerid(tglLedger);
        if (deleteByUqledgerid>0){
            throw new Exception("分户："+tglLedger.getVarledgename()+"已存在制证，不删除");
        }
        return true;
    }

    /**
     * 查询分户类型，包含子表分户明细
     * @param parameters
     * @param orderby
     * @return
     */
    public List<TglLedgetype> listLedgetypeAndChild(List<Parameter> parameters, String orderby) {
        PageRequest pageRequest = new PageRequest(parameters, orderby);
        return dao.listLedgetypeAndChild(pageRequest);
    }
    public int exportTglLedge(HttpServletResponse response) throws Exception {
        List<Parameter> parameters = new ArrayList<>();
        PageRequest pageRequest = new PageRequest(parameters);
        List<TglLedger> tglLedgers = tglLedgerDao.listAll(pageRequest);
        List<Map<String, Object>>  mapList = new ArrayList<>();
        Map<String, String> title = new LinkedHashMap<>();
//        分户类别	上级项目名称	分户项目编码	分户项目名称

        title.put("分户类别","varledgetypename");
        title.put("上级项目名称","parentvarledgename");
        title.put("分户项目编码","varledgecode");
        title.put("分户项目名称","varledgename");

        for (int i = 0; i < tglLedgers.size(); i++) {
            TglLedger tglLedger = tglLedgers.get(i);

            Map<String,Object> infoMap = new HashMap<>();
            infoMap.put("varledgetypename",tglLedger.getUqledgetypeid().getVarledgetypename());
            infoMap.put("parentvarledgename",tglLedger.getVarledgename());
            infoMap.put("varledgecode",tglLedger.getVarledgecode());
            infoMap.put("varledgename",tglLedger.getVarledgename());

            mapList.add(infoMap);
        }
        EasyPoiUtil.DownloadExcel(response,"分户",mapList,title);
        return tglLedgers.size();
    }
    public void importtTglLedge(MultipartFile file) throws Exception {
        String uqcompanyid= SessionUtils.getUser().getCompanyId();
        StringHolder errormsg = new StringHolder();
        InputStream is = file.getInputStream();
        Workbook wk = Workbook.getWorkbook(is);
        Sheet st = wk.getSheet(0);  //读取第一个表格
        int rowlength= st.getRows();  //获取表格数据的行数
        errormsg.value = "";
        this.checkInitPeriodTemplate(st); //检查导入数据的字段是否匹配
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for (int row = 1; row < rowlength; row++)
        {
            String ledgertypename = ObjectUtils.toString(st.getCell(0, row).getContents().trim());
            String ledgeritemcode = ObjectUtils.toString(st.getCell(1, row).getContents().trim());
            String ledgeritemname = ObjectUtils.toString(st.getCell(2, row).getContents().trim());
            String parentcode = ObjectUtils.toString(st.getCell(3, row).getContents().trim());

            Map<String,Object> entity = new HashMap<>();
            entity.put("ledgertypename", ledgertypename);
            entity.put("ledgeritemcode", ledgeritemcode);
            entity.put("ledgeritemname", ledgeritemname);
            entity.put("parentcode", parentcode);
            list.add(entity);
        }

        this.chackImportData(list, errormsg);

        if ("".equals(errormsg.value)){
            tglLedgerModelDao.deleteAll();
            for (int i = 0; i < list.size(); i++)
            {
                TglLedgerModel tglLedgerModel = new TglLedgerModel();
//                Map<String,Object> newEntity = new HashMap<>();
//                newEntity.put("ledgertypename", list.get(i).get("ledgertypename"));
//                newEntity.put("ledgeritemcode", list.get(i).get("ledgeritemcode"));
//                newEntity.put("ledgeritemname", list.get(i).get("ledgeritemname"));
//                newEntity.put("parentcode", list.get(i).get("parentcode"));
                tglLedgerModel.setLedgeritemcode(String.valueOf(list.get(i).get("ledgeritemcode")));
                tglLedgerModel.setLedgertypename(String.valueOf(list.get(i).get("ledgertypename")));
                tglLedgerModel.setLedgeritemname(String.valueOf(list.get(i).get("ledgeritemname")));
                tglLedgerModel.setParentcode(String.valueOf(list.get(i).get("parentcode")));
                tglLedgerModel.setTenantId(SessionUtils.getUser().getCompanyId());
                //将数据插入到模板表
                this.tglLedgerModelDao.insert(tglLedgerModel);
            }
            for (int i = 0; i < list.size(); i++)
            {
                int j = i+1;
                TglLedgerModel tglLedgerModel = new TglLedgerModel();
//                Map<String,Object> newEntity = new HashMap<>();
//                newEntity.put("ledgertypename", list.get(i).get("ledgertypename"));
//                newEntity.put("ledgeritemcode", list.get(i).get("ledgeritemcode"));
//                newEntity.put("ledgeritemname", list.get(i).get("ledgeritemname"));
//                newEntity.put("parentcode", list.get(i).get("parentcode"));
                tglLedgerModel.setLedgeritemcode(String.valueOf(list.get(i).get("ledgeritemcode")));
                tglLedgerModel.setLedgertypename(String.valueOf(list.get(i).get("ledgertypename")));
                tglLedgerModel.setLedgeritemname(String.valueOf(list.get(i).get("ledgeritemname")));
                tglLedgerModel.setParentcode(String.valueOf(list.get(i).get("parentcode")));
                tglLedgerModel.setTenantId(SessionUtils.getUser().getCompanyId());
                List<Parameter> parameter = new ArrayList<>();
                parameter.add(new Parameter("ledgertypename", "=", tglLedgerModel.getLedgertypename()));
                parameter.add(new Parameter("ledgeritemcode", "=", tglLedgerModel.getLedgeritemcode()));
                parameter.add(new Parameter("tenant_id", "=", SessionUtils.getUser().getCompanyId()));
                PageRequest pageRequest = new PageRequest(parameter);
                List<TglLedgerModel> tglLedgerModels = tglLedgerModelDao.listAll(pageRequest);
                if (tglLedgerModels.size()>1){
                    errormsg.value += "第 "+ j + " 行同类别下编码重复，请检查该条数据|";
                }
                parameter = Lists.newArrayList();
                parameter.add(new Parameter("ledgeritemname", "=", tglLedgerModel.getLedgeritemname()));
                parameter.add(new Parameter("ledgertypename", "=", tglLedgerModel.getLedgertypename()));
                if ( tglLedgerModel.getLedgeritemcode().equals(tglLedgerModel.getParentcode())){
                    parameter.add(new Parameter("parentcode", "=", tglLedgerModel.getParentcode()));

                }
                 tglLedgerModels = tglLedgerModelDao.listAll(new PageRequest(parameter));
                if (tglLedgerModels.size()>1){
                    errormsg.value += "第 "+ j + " 行同级下名称重复，请检查该条数据|";
                }
                parameter = Lists.newArrayList();
                parameter.add(new Parameter("ledgeritemcode", "=", tglLedgerModel.getParentcode()));
                tglLedgerModels = tglLedgerModelDao.listAll(new PageRequest(parameter));
                if (tglLedgerModels.size()!=1){
                    parameter = Lists.newArrayList();
                    parameter.add(new Parameter("varledgecode", "=", tglLedgerModel.getParentcode()));
                    parameter.add(new Parameter("uqledgetypeid.varledgetypename", "=", tglLedgerModel.getLedgertypename()));
                    List<TglLedger> tglLedgers = tglLedgerDao.listAll(new PageRequest(parameter));
                    if (tglLedgers.size()!=1){
                        errormsg.value += "第 "+ j + " 行上级项目编码不存在，请检查该条数据|";

                    }
                }
                //如果出现问题，抛出RuntimeException，使数据库事务回滚
                if (!"".equals(errormsg.value))
                {
                    throw new RuntimeException(errormsg.value);
                }
                Map<String, Object> procedure= new HashMap<>();
                procedure.put("tenant_id",SessionUtils.getUser().getCompanyId());
                procedure.put("uqcompanyid",uqcompanyid);
                tglLedgetypeDao.callProcedure(procedure);
                tglLedgetypeDao.callProcedureGroup(procedure);
                tglLedgerModelDao.deleteAll();

            }
        }
    }
    private void checkInitPeriodTemplate(Sheet st) throws Exception
    {
        if ("分户类别".equals(st.getCell(0, 0).getContents())
                && "分户项目编码".equals(st.getCell(1, 0).getContents())
                && "分户项目名称".equals(st.getCell(2, 0).getContents())
                && "上级项目编码".equals(st.getCell(3,0).getContents())){
        }else{
            throw new Exception("导入模版不正确!请重新选择");
        }
    }
    /**
     * 该方法用于校验数据
     */
    public void chackImportData(List<Map<String,Object>> list, StringHolder errormsg)
            throws Exception {
        for (int i = 0; i < list.size(); i++)
        {
            int j=i+1;
            Map<String,Object> entity = list.get(i);
            String ledgertypename = String.valueOf(entity.get("ledgertypename"));
            String ledgeritemcode = String.valueOf(entity.get("ledgeritemcode"));
            String ledgeritemname = String.valueOf(entity.get("ledgeritemname"));
            String parentcode = String.valueOf(entity.get("parentcode"));
            boolean isNull = true;
            //1、由于模板表中的字段都不允许为空，所以先校验excel中的数据是否为空
            if(ledgertypename==null || "".equals(ledgertypename))
            {
                errormsg.value += "第 "+ j + " 行 【分户类别名称】为空|";
                isNull = false;
            }
            if(ledgeritemcode==null || "".equals(ledgeritemcode))
            {
                errormsg.value += "第 "+ j + " 行 【分户项目编码】为空|";
                isNull = false;
            }
            if(ledgeritemname==null || "".equals(ledgeritemname))
            {
                errormsg.value += "第 "+ j + " 行 【分户项目名称】为空|";
                isNull = false;
            }
            if(parentcode==null || "".equals(parentcode))
            {
                errormsg.value += "第 "+ j + " 行 【上级项目编码】为空|";
                isNull = false;
            }
            //如果导入的数据没有未填写的，则继续校验下面的
            if(isNull)
            {
                //2、校验导入的分户类别是否存在(根据名称来判断)
                List<Parameter> parameter = new ArrayList<>();
                parameter.add(new Parameter("varledgetypename", "=", ledgertypename));
                parameter.add(new Parameter("tenant_id", "=", SessionUtils.getUser().getCompanyId()));
                PageRequest pageRequest = new PageRequest(parameter);
                List<TglLedgetype> tglLedgetypes = tglLedgetypeDao.listAll(pageRequest);

                if (tglLedgetypes.size()==0)
                {
                    errormsg.value += "第 "+ j + " 行分户类别不存在，请检查该条数据|";
                }
                else
                {
                    parameter = Lists.newArrayList();
                    parameter.add(new Parameter("varledgecode", "=", ledgeritemcode));
                    parameter.add(new Parameter("tenant_id", "=",SessionUtils.getUser().getCompanyId()));
                    parameter.add(new Parameter("uqledgetypeid.VARLEDGETYPENAME", "=", ledgertypename));

                    List<TglLedger> tglLedgers = tglLedgerDao.listAll(new PageRequest(parameter));
                    //3、检查同一类别下编码是否重复，这是考虑到之前类别中已经有手动在界面上添加分户项目的情况
//                    List<LedgerItemEntity> list3 = this.ledgehibernatedao.getLedgerItemEntityByCode(ledgertypename,ledgeritemcode);
                    if(tglLedgers.size()>0)
                    {
                        errormsg.value += "第 "+ j + " 行 分户项目编码已存在,请仔细核对|";
                    }
                }
            }
        }
    }
}
