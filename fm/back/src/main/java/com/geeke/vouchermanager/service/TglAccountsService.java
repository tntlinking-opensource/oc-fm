package com.geeke.vouchermanager.service;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.service.CrudService;
import com.geeke.utils.SessionUtils;
import com.geeke.vouchermanager.dao.TglAccountsDao;
import com.geeke.vouchermanager.entity.TglAccounts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 会计管理Service
 * @author
 * @version
 */

@Service("tglAccountsService")
@Transactional(readOnly = false)
public class TglAccountsService extends CrudService<TglAccountsDao, TglAccounts> {
    @Autowired
    private TglAccountsDao tglaccountsdao;
    public void editAccounts(TglAccounts account) throws Exception
    {
        account.setTenantId(SessionUtils.getUser().getCompanyId());
        // 修改前的科目信息
        TglAccounts originalAccount = this.tglaccountsdao.getAccountById(account.getUqaccountid()).get(0);
        if(originalAccount.equals(account)){
            return;
        }
        //验证修改后的编码是否唯一
        Long accountCountByCode = this.tglaccountsdao.getAccountCountByCode(originalAccount.getUqaccountsetid(), account.getVaraccountcode());
        if (accountCountByCode > 0 && !account.getVaraccountcode().equals(originalAccount.getVaraccountcode())) {
            throw new Exception("科目编号" + account.getVaraccountcode() + "已存在，不可使用！");
        }

        // 修改后的父级信息 - 用于设置varaccountfullcode
        if(!account.getUqparentid().getUqaccountid().equals("00000000-0000-0000-0000-000000000000")){
            TglAccounts parentAccounts = this.tglaccountsdao.getAccountById(account.getUqparentid().getUqaccountid()).get(0);
            account.setVaraccountfullcode(parentAccounts.getVaraccountfullcode()+"."+account.getVaraccountcode());
        }else{
            account.setVaraccountfullcode("0000."+account.getVaraccountcode());
        }

        //验证同级下的科目名称是否唯一
        List<TglAccounts> list = this.tglaccountsdao.getAccountCountByName(account);
        if (list.size() > 1)
        {
            throw new Exception("科目名称“"+account.getVaraccountname()+"”在本级中已存在，不可使用！");
        }


        this.tglaccountsdao.editAccount(account);
        // 判断修改前父级是否有子级科目、有则不管、否则将其修改为是末级 flag = 1
        if(!originalAccount.getUqparentid().getUqaccountid().equals("00000000-0000-0000-0000-000000000000")){
            List<TglAccounts> originalChild = this.tglaccountsdao.queryChildAccount(originalAccount.getUqparentid().getUqaccountid(), originalAccount.getTenantId());
            if(originalChild.isEmpty()){
                this.tglaccountsdao.updateInsertIntislastlevel(originalAccount.getUqparentid().getUqaccountid(),1);
            }
        }

        // 新父级修改为不是末级 flag = 0
        if(!account.getUqparentid().getUqaccountid().equals("00000000-0000-0000-0000-000000000000")){
            this.tglaccountsdao.updateInsertIntislastlevel(account.getUqparentid().getUqaccountid(),0);
        }

        this.updateAccountFullName(account);
        //跟新科目分户核算关系
        this.tglaccountsdao.deleteAccountAndLedgeType(account.getUqaccountid());
        //根据‘，’分割字符串uqLedgeTypeIds
        String uqLedgeTypeIds = account.getUqledgetypeids();
        account.setIntisledge(0);
        if (account.getUqledgetypeids() != null && !"".equals(account.getUqledgetypeids()))
        {
            account.setIntisledge(1);
            String [] uqLedgeTypeIdArray = uqLedgeTypeIds.split(",");
            for (String uqLedgeTypeId : uqLedgeTypeIdArray)
            {
                this.tglaccountsdao.addAccountAndLedgeType(account.getUqaccountid(), uqLedgeTypeId);
            }

        }
        if(list.size() == 1 || list.size() == 0)
        {
            //跟新组表信息(主要是中间级名称)
            this.tglaccountsdao.updateAccountGroupByGroupId(account.getUqaccountsetid(),
                    account.getVaraccountcode(), account.getVaraccountname());

            // 修改本级和下级全名称
            List<String> idList = new ArrayList<String>();

            // 如果没有以他为父级的科目、则不更新
            List<TglAccounts> entityMaps = this.tglaccountsdao.queryChildAccount(account.getUqaccountid(),account.getTenantId());
            if(!entityMaps.isEmpty()){
                for(TglAccounts entityMap:entityMaps){
                    idList.add(entityMap.getUqaccountid());
                }
                this.updateAccountFullName(idList);
            }
        }
        //修改报表展示字段
        TglAccounts accountParent = null;
        String allname="";
        if (account.getUqparentid().getUqaccountid().equals("00000000-0000-0000-0000-000000000000"))
        {
            accountParent = this.tglaccountsdao.getAccountById(account.getUqparentid().getUqaccountid()).get(0);
        }
        else
        {
            accountParent = this.tglaccountsdao.getAccountById(account.getUqparentid().getUqaccountid()).get(0);
            String code= accountParent.getVaraccountfullcode();
            String[] split = code.split("\\.");
            List arrList= Arrays.asList(split);
            for (int i=0;i<arrList.size();i++){
                if ( arrList.get(i).equals("0000")){
                    continue;
                }
                List<TglAccounts> str=this.tglaccountsdao.getShowName((String) arrList.get(i),SessionUtils.getUser().getCompanyId());
                if(i==0){
                    allname+=str.get(0).getVaraccountname();
                }else{
                    allname+="\\"+str.get(0).getVaraccountname();
                }
            }
        }
        String showname= null;
        if (accountParent.getUqaccountid().equals("00000000-0000-0000-0000-000000000000"))
        {
            showname= allname+account.getVaraccountname();
        }
        else
        {
            showname=allname+"\\"+account.getVaraccountname();
        }
        account.setUqpreaccountid(showname);
        //更新报表展示的全名称
        this.tglaccountsdao.updateUqper(account);

    }

    public void updateAccountFullName(TglAccounts account) throws Exception {
        //获取父级科目信息（全编码、等级）以获取新增科目全编码和等级
        TglAccounts accountParent = null;
        if (account.getUqparentid().getUqaccountid().equals("00000000-0000-0000-0000-000000000000"))
        {
            accountParent = this.tglaccountsdao.getAccountById(account.getUqparentid().getUqaccountid()).get(0);
        }
        else
        {
            accountParent = this.tglaccountsdao.getAccountById(account.getUqparentid().getUqaccountid()).get(0);
        }
        String fullName = null;
        if (accountParent.getUqaccountid().equals("00000000-0000-0000-0000-000000000000"))
        {
            fullName = account.getVaraccountname();
        }
        else
        {
            fullName = accountParent.getVaraccountfullname()+"-"+account.getVaraccountname();
        }
        account.setVaraccountfullname(fullName);
        this.tglaccountsdao.updateAccountFullName(account);
    }
    public void updateAccountFullName(List<String> list) throws Exception
    {
        for (int i = 0; i < list.size(); i++) {
            String fullName = null;
            String uqaccountid = list.get(i);
            TglAccounts account = this.tglaccountsdao.getAccountById(uqaccountid).get(0);
            //获取父级信息
            TglAccounts parent = null;
            if (account.getUqaccountid().equals("00000000-0000-0000-0000-000000000000"))
            {
                parent = this.tglaccountsdao.getAccountById(account.getUqparentid().getUqaccountid()).get(0);
            }
            else
            {
                parent = this.tglaccountsdao.getAccountById(account.getUqparentid().getUqaccountid()).get(0);
            }

            if (parent.getUqaccountid().equals("00000000-0000-0000-0000-000000000000"))
            {
                fullName = account.getVaraccountname();
            }
            else
            {
                fullName = parent.getVaraccountfullname()+"-"+account.getVaraccountname();
            }
            //修改全名称
            account.setVaraccountfullname(fullName);//intproperty uqtypeid uqforeigncurrid varmeasure intisledge intisflow
            if (!parent.getUqaccountid().equals("00000000-0000-0000-0000-000000000000")) {
                account.setIntisledge(parent.getIntisledge());
                account.setUqforeigncurrid(parent.getUqforeigncurrid());
                account.setIntproperty(parent.getIntproperty());
                account.setVarmeasure(parent.getVarmeasure());
                account.setIntisflow(parent.getIntisflow());
                account.setUqtypeid(parent.getUqtypeid());
                account.setIsbalance(parent.getIsbalance());
            }
            this.tglaccountsdao.updateAccountFullName(account);
            //递归循环下级
            List<String> childList = this.tglaccountsdao.getAccountIdList(account.getUqaccountid());
            this.updateAccountFullName(childList);
        }
    }
    public void updateIntflag(String jsonString,String startorclose) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        String[] idArrays = jsonObject.getString("ids").split(",");
        String uqaccountsetid= SessionUtils.getUser().getCompanyId();
        for(int i = 0; i < idArrays.length; i++)
        {
            //获取当前科目信息
            List<TglAccounts> list = this.tglaccountsdao.getAccountById(idArrays[i]);
            TglAccounts accountEntity = list.get(0);
            String accountId = accountEntity.getUqaccountid();
            if ("00000000-0000-0000-0000-000000000000".equals(accountId))
            {
                //循环下级所有科目
                List<String> idList = this.tglaccountsdao.getAccountIdList(accountId);
                for (String id : idList)
                {
                    List<TglAccounts> list2 = this.tglaccountsdao.getAccountById(id);
                    String fullcode = list2.get(0).getVaraccountfullcode();
                    updateIntflag(uqaccountsetid, null, fullcode, startorclose, 0);
                }
            }
            else
            {
                if ("start".equals(startorclose))
                {
                    int intflag = Integer.parseInt(accountEntity.getIntflag());
                    if (intflag != 2)
                    {
                        //先向上递归开启父级科目
                        this.startIntflag(idArrays[i], startorclose);
                    }
                    //在向下开启所有子集科目
                    String fullcode = accountEntity.getVaraccountfullcode();
                    updateIntflag(uqaccountsetid, null, fullcode, startorclose, 0);
                }
                if ("close".equals(startorclose))
                {
                    int intflag = Integer.parseInt(accountEntity.getIntflag());
                    String fullcode = accountEntity.getVaraccountfullcode();
                    //如果未关闭 则这向下关闭所有子节点
                    if (intflag != 0 )
                    {
                        updateIntflag(uqaccountsetid, null, fullcode, startorclose, 0);
                    }
                }
            }
        }

    }
    public void updateIntflag(String uqaccountsetid, String uqaccountid, String fullcode, String startorclose, int type){
        if (type == 1) {
            if (startorclose.equals("start")) {
                tglaccountsdao.updateIntflag("2",null,null,uqaccountid);
            }
        }
        if (type == 0) {
            if (startorclose.equals("start")) {
                tglaccountsdao.updateIntflag("2",fullcode,uqaccountsetid,null);
            }
            if (startorclose.equals("close")) {
                tglaccountsdao.updateIntflag("0",fullcode,uqaccountsetid,null);

            }

        }
    }
    public void startIntflag(String uqaccountid, String startorclose) throws Exception
    {
        List<TglAccounts> list = this.tglaccountsdao.getAccountById(uqaccountid);
        TglAccounts accountEntity = list.get(0);
        int intflag = Integer.parseInt(accountEntity.getIntflag());
        String uqparentid = accountEntity.getUqparentid().getUqaccountid();
        if ((intflag != 2) && (!"00000000-0000-0000-0000-000000000000".equals(uqparentid)))
        {
            this.startIntflag(uqparentid, startorclose);
        }
        updateIntflag(null,uqaccountid, null, startorclose, 1);
    }

    /**
     * 科目级别拉框
     * @return
     */
    public List<Map<String, Object>> getSubjectlevelList() {
        return dao.getSubjectlevelList();
    }

    /**
     *查询科目
     */
    public TglAccounts getAccountById(String uqaccountid){
        if (StringUtils.isBlank(uqaccountid)){
            return null;
        }
        List<TglAccounts> tglAccounts = dao.getAccountById(uqaccountid);
        if (tglAccounts == null || tglAccounts.size()<1){
            return null;
        }
        return tglAccounts.get(0);
    }
}
