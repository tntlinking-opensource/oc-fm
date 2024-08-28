package com.geeke.vouchermanager.service;

import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.utils.EasyPoiUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.vouchermanager.dao.HandlerGroupTenantDao;
import com.geeke.vouchermanager.dao.HandlerGroupTenantTreeDao;
import com.geeke.vouchermanager.dao.TglAccountsDao;
import com.geeke.vouchermanager.entity.GroupAccountsEntity;
import com.geeke.vouchermanager.entity.GroupAccountsEntityTree;
import com.geeke.vouchermanager.entity.TglAccounts;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
@Transactional
public class HandlerTenantGroupsService extends CrudService<TglAccountsDao, TglAccounts> {
    @Autowired
    private HandlerGroupTenantDao handlerGroupTenantDao;
    @Autowired
    private HandlerGroupTenantTreeDao handlerGroupTenantTreeDao;
    @Autowired
    private TglAccountsDao tglAccountsDao;
    public void addAccounts(GroupAccountsEntity account, String uqLedgeTypeIds) throws Exception {
        String tenantid = SessionUtils.getUser().getCompanyId();
        String groupid = getGroupid();
        String uqaccountsetid1 = SessionUtils.getUser().getCompanyId();
        account.setGroup_id(groupid);
        account.setUqaccountsetid(uqaccountsetid1);
        String uqaccountsetid = account.getUqaccountsetid();
        //验证编码是否唯一
        Long list1 = handlerGroupTenantDao.getAccountCountByCode(uqaccountsetid, account.getVaraccountcode());
        if (list1>0) {
            throw new Exception("科目编号" + account.getVaraccountcode() + "已存在，不可使用！");
        }
        //验证同级下的科目名称是否唯一
        List<String> list2 = handlerGroupTenantDao.getAccountCountByName(account);
        if (list2.size() >= 1) {
            throw new Exception("科目名称" + account.getVaraccountname() + "在本级中已存在，不可使用！");
        }
        //获取父级科目信息（全编码、等级）以获取新增科目全编码和等级
        GroupAccountsEntity accountParent = null;
        String allname = "";
        if (account.getUqparentid().equals("00000000-0000-0000-0000-000000000000")) {
            accountParent = handlerGroupTenantDao.getAccountById(account.getUqparentid()).get(0);
        } else {
            accountParent = handlerGroupTenantDao.getAccountById(account.getUqparentid()).get(0);
            String code = accountParent.getVaraccountfullcode();
            String[] split = code.split("\\.");
            List list = Arrays.asList(split);
            for (int i = 0; i < list.size(); i++) {
                List<GroupAccountsEntity> str = handlerGroupTenantDao.getShowName((String) list.get(i), account.getGroup_id());
                if (i == 0) {
                    allname += str.get(0).getVaraccountname();
                } else {
                    allname += "\\" + str.get(0).getVaraccountname();
                }
            }
        }

        String fullCode = null;
        String fullName = null;
        String showname = null;
        int intlevel;
        if (accountParent.getUqaccountid().equals("00000000-0000-0000-0000-000000000000")) {
            fullCode = account.getVaraccountcode();
            fullName = account.getVaraccountname();
            showname = allname + account.getVaraccountname();
            intlevel = 1;
        } else {
            if (Integer.parseInt(accountParent.getIntlevel()) > 4) {
                throw new RuntimeException("科目最多只能创建5级！");
            }
            fullCode = accountParent.getVaraccountfullcode() + "." + account.getVaraccountcode();
            fullName = accountParent.getVaraccountfullname() + "-" + account.getVaraccountname();
            showname = allname + "\\" + account.getVaraccountname();
            intlevel = Integer.parseInt(accountParent.getIntlevel()) + 1;
        }
        account.setVaraccountfullcode(fullCode);
        account.setVaraccountfullname(fullName);
        account.setIntlevel(intlevel + "");
        String uqaccountid = UUID.randomUUID().toString().toUpperCase();
        account.setUqaccountid(uqaccountid);
        account.setUqpreaccountid(showname);
        //判断修改父级的末级状态
        if (accountParent.getIntislastlevel().equals("1")) {
            //修改父级的末级状态
            handlerGroupTenantDao.updateIntislastlevel(accountParent.getUqaccountid(), 0);
        }
        //新增科目
        handlerGroupTenantDao.addAccounts(account);
        //在组表中添加新增科目的信息
    }
    public void editGroupAccounts(GroupAccountsEntity account)throws Exception{
        String tenantid =SessionUtils.getUser().getCompanyId();
        String groupid = getGroupid();
        String uqaccountsetid =SessionUtils.getUser().getCompanyId();
        account.setGroup_id(groupid);
        account.setUqaccountsetid(uqaccountsetid);
        // 修改前的科目信息
        GroupAccountsEntity originalAccount = handlerGroupTenantDao.queryById(account.getUqaccountid());
        if (originalAccount.equals(account)) {
            return;
        }
        //验证修改后的编码是否唯一
        Long list1 = handlerGroupTenantDao.count(originalAccount.getUqaccountsetid(), account.getVaraccountcode(),account.getUqaccountid());
        if (list1 > 0&&!account.getVaraccountcode().equals(originalAccount.getVaraccountcode())) {
            throw new Exception("科目编号" + account.getVaraccountcode() + "已存在，不可使用！");
        }
        // 修改后的父级信息 - 用于设置varaccountfullcode
        if (!account.getUqparentid().equals("00000000-0000-0000-0000-000000000000")) {
            GroupAccountsEntity parentAccounts = handlerGroupTenantDao.queryById(account.getUqparentid());
            account.setVaraccountfullcode(parentAccounts.getVaraccountfullcode() + "." + account.getVaraccountcode());
        } else {
            account.setVaraccountfullcode(account.getVaraccountcode());
        }

        //验证同级下的科目名称是否唯一
        List<GroupAccountsEntity>  list = handlerGroupTenantDao.getGroupAccountByName(account.getVaraccountname(),account.getUqparentid(),account.getUqaccountsetid());
        if (list.size() > 1) {
            throw new Exception("科目名称“" + account.getVaraccountname() + " 在本级中已存在，不可使用！");
        }


        handlerGroupTenantDao.update(account);
        // 判断修改前父级是否有子级科目、有则不管、否则将其修改为是末级 flag = 1
        if (!originalAccount.getUqparentid().equals("00000000-0000-0000-0000-000000000000")) {
            List<GroupAccountsEntity> originalChild = handlerGroupTenantDao.queryGroupChildAccount(originalAccount.getUqparentid(), originalAccount.getGroup_id());
            if (originalChild.isEmpty()) {
                handlerGroupTenantDao.updateGroupIntislastlevel(originalAccount.getUqparentid(), 1);
            }
        }

        // 新父级修改为不是末级 flag = 0
        if (!account.getUqparentid().equals("00000000-0000-0000-0000-000000000000")) {
            handlerGroupTenantDao.updateGroupIntislastlevel(account.getUqparentid(), 0);
        }

        this.updateGroupAccountFullName(account);

        if (list.size() == 1 || list.size() == 0) {
            // 修改本级和下级全名称
            List<String> idList = new ArrayList<String>();

            // 如果没有以他为父级的科目、则不更新
            List<GroupAccountsEntity> entityMaps = handlerGroupTenantDao.queryGroupChildAccount(account.getUqaccountid(), account.getGroup_id());
            if (!entityMaps.isEmpty()) {
                for (GroupAccountsEntity entityMap : entityMaps) {
                    idList.add(entityMap.getUqaccountid());
                }
                this.updateGroupAccountFullName(idList);
            }
        }
        //修改报表展示字段
        GroupAccountsEntity accountParent = null;
        String allname = "";
        if (account.getUqparentid().equals("00000000-0000-0000-0000-000000000000")) {
            accountParent = handlerGroupTenantDao.getGroupAccountById(account.getUqparentid()).get(0);
        } else {
            accountParent = handlerGroupTenantDao.getGroupAccountById(account.getUqparentid()).get(0);
            String code = accountParent.getVaraccountfullcode();
            String[] split = code.split("\\.");
            List arrList = Arrays.asList(split);
            for (int i = 0; i < arrList.size(); i++) {
                List<GroupAccountsEntity> str = handlerGroupTenantDao.getShowName((String) arrList.get(i), account.getGroup_id());
                if (i == 0) {
                    allname += str.get(0).getVaraccountname();
                } else {
                    allname += "\\" + str.get(0).getVaraccountname();
                }
            }
        }
        String showname = null;
        if (accountParent.getUqaccountid().equals("00000000-0000-0000-0000-000000000000")) {
            showname = allname + account.getVaraccountname();
        } else {
            showname = allname + "\\" + account.getVaraccountname();
        }
        account.setUqpreaccountid(showname);
        //更新报表展示的全名称
        handlerGroupTenantDao.updateUqper(account);
    };
    public void updateGroupAccountFullName(GroupAccountsEntity account) throws Exception {
        //获取父级科目信息（全编码、等级）以获取新增科目全编码和等级
        GroupAccountsEntity accountParent = null;
        if (account.getUqparentid().equals("00000000-0000-0000-0000-000000000000")) {
            accountParent = handlerGroupTenantDao.getGroupAccountById(account.getUqparentid()).get(0);
        } else {
            accountParent = handlerGroupTenantDao.getGroupAccountById(account.getUqparentid()).get(0);
        }
        String fullName = null;
        if (accountParent.getUqaccountid().equals("00000000-0000-0000-0000-000000000000")) {
            fullName = account.getVaraccountname();
        } else {
            fullName = accountParent.getVaraccountfullname() + "-" + account.getVaraccountname();
        }
        account.setVaraccountfullname(fullName);
        handlerGroupTenantDao.update(account);
    }
    public void updateGroupAccountFullName(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            String fullName = null;
            String uqaccountid = list.get(i);
            GroupAccountsEntity account = handlerGroupTenantDao.getGroupAccountById(uqaccountid).get(0);
            //获取父级信息
            GroupAccountsEntity parent = null;
            if (account.getUqaccountid().equals("00000000-0000-0000-0000-000000000000")) {
                parent = handlerGroupTenantDao.getGroupAccountById(account.getUqparentid()).get(0);
            } else {
                parent = handlerGroupTenantDao.getGroupAccountById(account.getUqparentid()).get(0);
            }

            if (parent.getUqaccountid().equals("00000000-0000-0000-0000-000000000000")) {
                fullName = account.getVaraccountname();
            } else {
                fullName = parent.getVaraccountfullname() + "-" + account.getVaraccountname();
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
            handlerGroupTenantDao.update(account);
            //递归循环下级
            List<String> childList = handlerGroupTenantDao.getGroupAccountIdList(account.getUqaccountid());
            updateGroupAccountFullName(childList);
        }
    }
    public void deleteAccount(String accountid) throws Exception {
        String flag= handlerGroupTenantDao.queryById(accountid).getIntflag();
        if(flag.equals("0")){
            throw new Exception("删除失败，该科目不是末级科目！");
        }
        //校验通过，将其父科目修改为末级科目
        String parAccountid = handlerGroupTenantDao.queryById(accountid).getUqparentid();
        //先去查询其父类科目下面还有没有子
        int i = Integer.parseInt(handlerGroupTenantDao.queryById(parAccountid).getIntislastlevel());
        if(i==1){
            handlerGroupTenantDao.updateIsfinal(parAccountid);
        }
        handlerGroupTenantDao.delete(accountid);
    }
    public void synchronizeAccounts(String tenantidList,String jsonString) throws Exception {
        tenantidList=SessionUtils.getUser().getCompanyId();
        String groupid = getGroupid();
        List tenantlist = Arrays.asList(tenantidList.split(","));
        List codelist = Arrays.asList(jsonString.split(","));
        if (tenantlist.size()==0||codelist.size()==0){
            throw new Exception("请选择正确的参数");
        }
        for (int i = 0; i < tenantlist.size(); i++) {
            String tenantid = (String) tenantlist.get(i);

            for (int j = 0; j < codelist.size(); j++) {
                String code = (String) codelist.get(j);
                //因为科目拉取会变更科目层级关系，科目拉取前校验其父类科目是否存在凭证
                this.IsParentExistVoucher(tenantid,code,groupid);
                //判断该科目在当前租户是否存在
                int num = selectIsExist(tenantid, code);
                IsExistParents(code,groupid,tenantid);
                if (num > 0) {
                    // 更新操作对当前科目是否存在凭证进行校验
                    IsExistVoucher(tenantid,code);
                    //大于0表示当前存在此科目，更新
                    updateAccounts(groupid, tenantid, code);
                } else {
                    //表示当前不存在该科目，新增
                    insertAccounts(groupid, tenantid, code);
                }
            }
        }
    };
    public void IsParentExistVoucher(String tenantid, String code, String groupid) throws Exception {
        //此模版的租户id
        //反查此租户id的父id 为了获取code  从科目模版表里面取数据
        String parentId = handlerGroupTenantDao.getParentId(code, groupid).getUqparentid();
        //获取父类code
        String parentCode = handlerGroupTenantDao.getParentCode(parentId, groupid).getVaraccountcode();
        //从科目表里面拿下发公司的信息
        String parent_Id = tglAccountsDao.getParid(parentCode, tenantid).get(0).getUqaccountid();
        //获取要下发公司的凭证信息
        //获取公司名字，报错更加相信
        int num = tglAccountsDao.IsParentExistVoucher(tenantid, parent_Id);
        if (num > 0) {
            throw new Exception("选中的公司"+code + "的上级科目存在凭证！！");
        }
    }
    public int selectIsExist(String tenantid, String code) {
        return handlerGroupTenantDao.selectIsExist(tenantid, code);
    }
    public int IsExistParents(String code,String groupid,String tenantid) throws Exception {
        GroupAccountsEntity groupAccountsEntity = new GroupAccountsEntity();
        groupAccountsEntity.setVaraccountcode(code);
        groupAccountsEntity.setGroup_id(groupid);
        List<GroupAccountsEntity> groupAccountsEntities = handlerGroupTenantDao.queryAll(groupAccountsEntity);
        if (groupAccountsEntities.size()==0){
            throw new Exception("编号"+code+"不存在");
        }
        String uqparentid = groupAccountsEntities.get(0).getUqparentid();
        if (uqparentid.equals("00000000-0000-0000-0000-000000000000")){
            return 1;
        }else {
            int i = handlerGroupTenantDao.selectIsExistByCode(code.substring(0, code.length() - 2), tenantid);
            if (i==0){
                throw new Exception("编号"+code+"的上级科目不存在！！");
            }
        }
        return 1;
    }
    public void IsExistVoucher(String tenantid, String code) throws Exception {
        int num = handlerGroupTenantDao.IsExistVoucher(code, tenantid);
        if (num > 0) {
            throw new Exception(code + "科目存在凭证！！");
        }
    }

    public void updateAccounts(String groupid, String tenantid, String code) {
        //先从租户组表获取数据
        GroupAccountsEntity groupAccountsEntity = new GroupAccountsEntity();
        groupAccountsEntity.setGroup_id(groupid);
        groupAccountsEntity.setVaraccountcode(code);
        GroupAccountsEntity accounts = handlerGroupTenantDao.queryAll(groupAccountsEntity).get(0);
        tglAccountsDao.editAccounts(accounts,tenantid,code);
        //维护中间表
        String uqaccountsetid = this.tglAccountsDao.selectUqaccountsetid(tenantid);
        //跟新组表信息(主要是中间级名称)
        handlerGroupTenantDao.updateAccountGroupByGroupId(uqaccountsetid,
                accounts.getVaraccountcode(), accounts.getVaraccountname());

    }
    public void insertAccounts(String groupid, String tenantid, String code) throws Exception {
        //先从租户组表获取数据
        GroupAccountsEntity accounts = handlerGroupTenantDao.getAccountCount(groupid, code).get(0);

        String uqaccountsetid = tglAccountsDao.selectUqaccountsetid(tenantid);
        //验证编码是否唯一
        int list1 = handlerGroupTenantDao.findAccountCountByCode(uqaccountsetid, accounts.getVaraccountcode());
        if (list1!=0) {
            throw new Exception("科目编号" + accounts.getVaraccountcode() + "已存在，不可使用！");
        }
        //验证同级下的科目名称是否唯一
        int list2 = handlerGroupTenantDao.findAccountCountByName(accounts);
        if (list2 >= 1) {
            throw new Exception("科目名称" + accounts.getVaraccountname() + "在本级中已存在，不可使用！");
        }
        //获取父级科目信息（全编码、等级）以获取新增科目全编码和等级
        GroupAccountsEntity accountParent = null;
        String allname = "";
        String parentid = "";
        String fullName = "";
        String showname = "";
        int intlevel;
        if (accounts.getUqparentid().equals("00000000-0000-0000-0000-000000000000")) {
            accountParent = handlerGroupTenantDao.getParentId(code, groupid);
            parentid = "00000000-0000-0000-0000-000000000000";
            fullName = accounts.getVaraccountname();
            showname = allname + accounts.getVaraccountname();
            intlevel = 1;
        } else {
            List splitcode = Arrays.asList(accounts.getVaraccountfullcode().split("\\."));
            Object o = splitcode.get(splitcode.size() - 2);
            //科目表主id
            parentid = tglAccountsDao.getParid(o.toString(), tenantid).get(0).getUqaccountid();
            accountParent = handlerGroupTenantDao.getParentId(code, groupid);
            String code1 = accountParent.getVaraccountfullcode();
            String[] split = code1.split("\\.");
            List list = Arrays.asList(split);
            for (int i = 0; i < list.size() - 1; i++) {
                List<TglAccounts> str = tglAccountsDao.getParid((String) list.get(i), tenantid);
                if (i == 0) {
                    allname += str.get(0).getVaraccountname();
                    fullName += str.get(0).getVaraccountname();
                } else {
                    allname += "\\" + str.get(0).getVaraccountname();
                    fullName += "-" + str.get(0).getVaraccountname();
                }
            }
            if (Integer.parseInt(accountParent.getIntlevel()) > 5) {
                throw new Exception("科目最多只能创建5级！");
            }

            fullName = fullName + "-" + accounts.getVaraccountname();
            showname = allname + "\\" + accounts.getVaraccountname();
            intlevel = Integer.parseInt(accounts.getIntlevel());
        }
        //
        accounts.setUqparentid(parentid);
        accounts.setVaraccountfullname(fullName);
        accounts.setIntlevel(intlevel + "");
        String uqaccountid = UUID.randomUUID().toString().toUpperCase();
        accounts.setUqaccountid(uqaccountid);
        accounts.setUqpreaccountid(showname);
        accounts.setUqaccountsetid(uqaccountsetid);
        //判断修改父级的末级状态
        if (accountParent.getIntislastlevel().equals("1")) {
            //修改父级的末级状态
//            this.HandlerGroupTenantDao.updateInsertIntislastlevel(accountParent.getUqaccountid(), 0);
            tglAccountsDao.updateInsertIntislastlevel(parentid, 0);

            //清除以父级为末级在组表中的数据
            handlerGroupTenantDao.deleteAccountGroupByAccountId(accountParent.getUqaccountid());
        }
        //新增科目
        accounts.setUqaccountsetid(SessionUtils.getUser().getCompanyId());
        tglAccountsDao.addTenantAccounts(accounts, tenantid);
        //在组表中添加新增科目的信息
        addIntoAccountGroup(accounts, accounts.getUqaccountid(), tenantid);
        //添加科目分户核算关系

    }
    public void addIntoAccountGroup(GroupAccountsEntity account, String parentid, String tenantid) throws Exception {
        //判断是否到顶级
        if (!"00000000-0000-0000-0000-000000000000".equals(parentid)) {
            //获取父级信息
            TglAccounts parentEntity = tglAccountsDao.findAccountById(parentid, tenantid);
            //封装组表实体
            HashMap entity = new HashMap();
            entity.put("uqaccountsetid", account.getUqaccountsetid());
            entity.put("uqgroupid", UUID.randomUUID().toString().toUpperCase());
            entity.put("vargroupname", parentEntity.getVaraccountname());
            entity.put("vargroupcode", parentEntity.getVaraccountcode());
            entity.put("vargroupfullcode", parentEntity.getVaraccountfullcode());
            entity.put("intgrouplevel", parentEntity.getIntlevel());
            entity.put("uqaccountid", account.getUqaccountid());
            entity.put("varaccountcode", account.getVaraccountcode());
            entity.put("intaccountlevel", account.getIntlevel());
            entity.put("tenantid", tenantid);
            if (parentEntity.getUqaccountid().equals(account.getUqparentid())) {
                entity.put("intislastlevel", 0);
            } else {
                entity.put("intislastlevel", parentEntity.getIntislastlevel());
            }

            //执行插入
            handlerGroupTenantDao.insertIntoAccountGroup(entity);
            //递归自身
            addIntoAccountGroup(account, parentEntity.getUqparentid().getUqaccountid(), tenantid);
        }
    }
    public int getVoucher(String tenantid) {
        return handlerGroupTenantDao.getVoucher(tenantid);
    }
    public void initializationAccounts(String tenantidList) throws Exception {
        tenantidList=SessionUtils.getUser().getCompanyId();
        String groupid = getGroupid();
        List tenantlist = Arrays.asList(tenantidList.split(","));
        if (tenantlist.size()==0){
            throw new Exception("请选择正确的参数");
        }
        for (int i = 0; i < tenantlist.size(); i++) {
            String tenantid = (String) tenantlist.get(i);
            //验证当前租户是否有凭证存在，如果存在则抛错
            int tag=handlerGroupTenantDao.getVoucher(tenantid);
            if(tag>0){
                throw new Exception("科目初始化失败，当前租户存在凭证！");
            }
            initializationAccount(tenantid,groupid);
        }
    }
    public void initializationAccount(String tenantid, String groupid) {
        String accountSetId = SessionUtils.getUser().getCompanyId();
        // 清除科目主表数据
        handlerGroupTenantDao.deleteAccounts(tenantid);
        accountinitialization(tenantid, accountSetId, groupid);//插入1级科目
        initializeAccount(tenantid);
//        this.initializeAccount(tenantid);
//        this.initializeAccount1(tenantid);
        // 清除科目关系表数据
        tglAccountsDao.deleteGroupAccounts(tenantid);
        addAccountGroup(tenantid, accountSetId);
    }
    public void accountinitialization(String tenant_id, String accountSetId, String groupid) {
        //从租户组拿一套数据
        GroupAccountsEntity groupAccountsEntity = new GroupAccountsEntity();
        groupAccountsEntity.setGroup_id(groupid);
        List<GroupAccountsEntity> list = this.handlerGroupTenantDao.queryAll(groupAccountsEntity);
        for (int i = 0; i < list.size(); i++) {
            String sql = "";
            String uuid = UUID.randomUUID().toString();
            TglAccounts groupAccountsEntity1 = new TglAccounts();
            groupAccountsEntity1.setUqaccountid(uuid);
            if (list.get(i).getUqaccountid().equals("00000000-0000-0000-0000-000000000000")){
                groupAccountsEntity1.setUqaccountid("00000000-0000-0000-0000-000000000000");
                groupAccountsEntity1.setVaraccountfullcode("0000");
            }else {
                groupAccountsEntity1.setVaraccountfullcode("0000."+list.get(i).getVaraccountfullcode());
                groupAccountsEntity1.setUqpreaccountid(list.get(i).getVaraccountfullname().replace('.', '/'));
            }
            groupAccountsEntity1.setUqaccountsetid(accountSetId);
            groupAccountsEntity1.setVaraccountcode(list.get(i).getVaraccountcode());

            groupAccountsEntity1.setVaraccountname(list.get(i).getVaraccountname());
            groupAccountsEntity1.setIntproperty(list.get(i).getIntproperty());
            groupAccountsEntity1.setUqtypeid(list.get(i).getUqtypeid());
            groupAccountsEntity1.setIntisledge(0);
            TglAccounts tglAccounts = new TglAccounts();
            tglAccounts.setUqaccountid(list.get(i).getUqparentid());
            groupAccountsEntity1.setUqparentid(tglAccounts);
            groupAccountsEntity1.setIntislastlevel(list.get(i).getIntislastlevel());
            groupAccountsEntity1.setIntlevel(list.get(i).getIntlevel());
            groupAccountsEntity1.setIntflag("2");
            groupAccountsEntity1.setVaraccountfullname(list.get(i).getVaraccountfullname());
            groupAccountsEntity1.setIntisflow(list.get(i).getIntisflow());
            groupAccountsEntity1.setTenantId(tenant_id);
            if (!list.get(i).getUqaccountid().equals("00000000-0000-0000-0000-000000000000")){
                groupAccountsEntity1.setIsbalance(list.get(i).getIsbalance());

            }
            this.tglAccountsDao.add(groupAccountsEntity1);
        }
    }
    public void initializeAccount(String tenantid){
        List<TglAccounts> allAccount = tglAccountsDao.getBytenantid(tenantid);
        for (TglAccounts map : allAccount) {
            String varaccountfullcode = map.getVaraccountfullcode();
            List<String> strings = Arrays.asList(varaccountfullcode.split("\\."));
            if(!map.getUqparentid().getUqaccountid().equals("00000000-0000-0000-0000-000000000000")){
                try {
                    String s = strings.get(strings.size() - 2);
                    tglAccountsDao.updateUqaccountid(s,tenantid,map.getUqaccountid());
                }catch (Exception e){
                    System.out.println(map);
                }


            }

        }
    }
    public void addAccountGroup(String tenantid, String accountSetId) {
        List<TglAccounts> allAccounts = tglAccountsDao.getBytenantid(tenantid);
        for (int i = 0; i < allAccounts.size(); i++) {
            TglAccounts tglAccounts = allAccounts.get(i);

            String accountId = tglAccounts.getUqaccountid();
            if (tglAccounts.getIntislastlevel().equals("1")) {
                //添加科目组 - 第一次的parentid传递自己
                    addAccountsGroup(accountId, accountId, accountSetId, tenantid);
            }
        }
    }
    private void addAccountsGroup(String parentId, String accountId, String accountSetId, String tenantId) {
        //获取父级信息
        if (!"00000000-0000-0000-0000-000000000000".equals(parentId)) {
            List<TglAccounts> allAccounts = tglAccountsDao.getByUqaccountid(parentId);
            TglAccounts tglAccounts = allAccounts.get(0);
            String intislastlevel = "0";
            if(parentId.equals(accountId) && !tglAccounts.getUqparentid().getUqaccountid().equals("00000000-0000-0000-0000-000000000000")){
                intislastlevel = "1";
            }
            //执行插入
//            StringBuilder insertAccountGroup = new StringBuilder();
//            insertAccountGroup.append(" INSERT INTO tgl_account_group ");
//            insertAccountGroup.append(" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?) ");
//            String sql="";
//            sql="INSERT INTO tgl_account_group VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

            HashMap hashMap = new HashMap();
            hashMap.put("uqaccountsetid",accountSetId);
            hashMap.put("uqgroupid", UUID.randomUUID().toString().toUpperCase());
            hashMap.put("vargroupname",tglAccounts.getVaraccountname());
            hashMap.put("vargroupcode",tglAccounts.getVaraccountcode());
            hashMap.put("vargroupfullcode",tglAccounts.getVaraccountfullcode());
            hashMap.put("intgrouplevel",tglAccounts.getIntlevel());
            hashMap.put("uqaccountid",accountId);
            hashMap.put("varaccountcode",tglAccounts.getVaraccountcode());
            hashMap.put("intaccountlevel",tglAccounts.getIntlevel());
            hashMap.put("intislastlevel",intislastlevel);
            hashMap.put("tenantId",tenantId);
            handlerGroupTenantDao.insertIntoAccountGroup(hashMap);
            //递归自身
            this.addAccountsGroup(
                    tglAccounts.getUqparentid().getUqaccountid(),
                    accountId,
                    accountSetId,
                    tenantId);
        }
    }
    public int exportAccountInfo(HttpServletResponse response) throws Exception {
        List<Parameter> parameters = new ArrayList<>();
        PageRequest pageRequest = new PageRequest(parameters);
        List<GroupAccountsEntityTree> groupAccountsEntityTrees = handlerGroupTenantTreeDao.listAll(pageRequest);
        List<Map<String, Object>>  mapList = new ArrayList<>();
        Map<String, String> title = new LinkedHashMap<>();
//        科目编号	名称	上级科目编号	上级科目名称	科目性质名称	科目类别名称	现金流量标志	外币名称	计量单位名称	分户类别名称	状态

        title.put("科目编号","varaccountcode");
        title.put("科目名称","varaccountname");
        title.put("上级科目编号","parentvaraccountcode");
        title.put("上级科目名称","parentvaraccountname");
        title.put("科目性质名称","intproperty");
        title.put("科目类别名称","uqtypeid");
        title.put("现金流量标志","intisflow");
        title.put("外币名称","uqforeigncurrid");
        title.put("计量单位名称","varmeasure");
        title.put("分户类别名称","varmeasure1");
        title.put("状态","intflag");
        for (int i = 0; i < groupAccountsEntityTrees.size(); i++) {
            GroupAccountsEntityTree groupAccountsEntityTree = groupAccountsEntityTrees.get(i);
            if (groupAccountsEntityTree.getVaraccountcode().equals("0000")){
                continue;
            }
            Map<String,Object> infoMap = new HashMap<>();
            infoMap.put("varaccountcode", groupAccountsEntityTree.getVaraccountcode());
            infoMap.put("varaccountname", groupAccountsEntityTree.getVaraccountname());
            infoMap.put("parentvaraccountcode", groupAccountsEntityTree.getParent().getVaraccountcode());
            infoMap.put("parentvaraccountname", groupAccountsEntityTree.getParent().getVaraccountname());
            infoMap.put("intproperty", groupAccountsEntityTree.getIntproperty());
            infoMap.put("uqtypeid", groupAccountsEntityTree.getUqtypeid());
            infoMap.put("intisflow", groupAccountsEntityTree.getIntisflow());
            infoMap.put("uqforeigncurrid", groupAccountsEntityTree.getUqforeigncurrid());
            infoMap.put("varmeasure", groupAccountsEntityTree.getVarmeasure());
            infoMap.put("varmeasure1", "");
            infoMap.put("intflag", groupAccountsEntityTree.getIntflag());
            mapList.add(infoMap);
        }
        EasyPoiUtil.DownloadExcel(response,"科目模板管理",mapList,title);
        return groupAccountsEntityTrees.size();
    }
}
