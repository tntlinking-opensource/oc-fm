package com.geeke.assetting.service;

import com.alibaba.fastjson.JSONObject;
import com.geeke.assetting.dao.TfamDefaultSettingsDao;
import com.geeke.assetting.dao.TfamDepmonthDao;
import com.geeke.assetting.entity.TfamDefaultSettings;
import com.geeke.assetting.entity.TfamDepmonth;
import com.geeke.assetting.entity.TfamProductSwitch;
import com.geeke.assetting.resdto.TfamDefaultSettingsDto;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.vouchermanager.dao.TglAccountsDao;
import com.geeke.vouchermanager.entity.TglAccounts;
import com.geeke.vouchermanager.service.TglAccountsService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 资产科目方案配置Service
 * @author
 * @version
 */

@Service("tfamDefaultSettingsService")
@Transactional(readOnly = false)
public class TfamDefaultSettingsService extends CrudService<TfamDefaultSettingsDao, TfamDefaultSettings> {

    @Autowired
    TfamDepmonthDao tfamDepmonthDao;

    @Autowired
    private TglAccountsDao accountsDao;

    public void defaultsettingsave(JSONObject cdMap) throws Exception
    {
        String companyId = SessionUtils.getUser().getCompanyId().toString();
        if(cdMap.containsKey("asset_accountID"))
        {


            List<Parameter> params = null;
            params = Lists.newArrayList();
            params.add(new Parameter("company_id", "=",companyId));
            params.add(new Parameter("item", "=","asset_account"));
            List<TfamDefaultSettings> tfamDefaultSettings = this.listAll(params, "");

            if(tfamDefaultSettings.size()==0)
            {
                TfamDefaultSettings tfamDefaultSettings1 = new TfamDefaultSettings();
                tfamDefaultSettings1.setItem("asset_account");
                tfamDefaultSettings1.setValue(cdMap.getString("asset_accountID"));
                tfamDefaultSettings1.setCompanyId(companyId);
                this.save(tfamDefaultSettings1);
            }
            else
            {
                TfamDefaultSettings tfamDefaultSettings1 = tfamDefaultSettings.get(0);
                tfamDefaultSettings1.setItem("asset_account");
                tfamDefaultSettings1.setValue(cdMap.getString("asset_accountID"));
                this.save(tfamDefaultSettings1);

            }
        }

        if(cdMap.containsKey("accudep_accountID"))
        {

            List<Parameter> params = null;
            params = Lists.newArrayList();
            params.add(new Parameter("company_id", "=",companyId));
            params.add(new Parameter("item", "=","accudep_account"));
            List<TfamDefaultSettings> tfamDefaultSettings = this.listAll(params, "");



            if(tfamDefaultSettings.size()==0)
            {
                TfamDefaultSettings tfamDefaultSettings1 = new TfamDefaultSettings();
                tfamDefaultSettings1.setItem("accudep_account");
                tfamDefaultSettings1.setValue(cdMap.getString("accudep_accountID"));
                tfamDefaultSettings1.setCompanyId(companyId);
                this.save(tfamDefaultSettings1);
            }
            else
            {
                TfamDefaultSettings tfamDefaultSettings1 = tfamDefaultSettings.get(0);
                tfamDefaultSettings1.setItem("accudep_account");
                tfamDefaultSettings1.setValue(cdMap.getString("accudep_accountID"));
                this.save(tfamDefaultSettings1);

            }
        }

        if(cdMap.containsKey("predevaluate_accountID"))
        {

            List<Parameter> params = null;
            params = Lists.newArrayList();
            params.add(new Parameter("company_id", "=",companyId));
            params.add(new Parameter("item", "=","predevaluate_account"));
            List<TfamDefaultSettings> tfamDefaultSettings = this.listAll(params, "");



            if(tfamDefaultSettings.size()==0)
            {

                TfamDefaultSettings tfamDefaultSettings1 = new TfamDefaultSettings();
                tfamDefaultSettings1.setItem("predevaluate_account");
                tfamDefaultSettings1.setValue(cdMap.getString("predevaluate_accountID"));
                tfamDefaultSettings1.setCompanyId(companyId);
                this.save(tfamDefaultSettings1);
            }
            else
            {
                TfamDefaultSettings tfamDefaultSettings1 = tfamDefaultSettings.get(0);
                tfamDefaultSettings1.setItem("predevaluate_account");
                tfamDefaultSettings1.setValue(cdMap.getString("predevaluate_accountID"));
                this.save(tfamDefaultSettings1);
            }
        }

        if(cdMap.containsKey("project_accountID"))
        {
            List<Parameter> params = null;
            params = Lists.newArrayList();
            params.add(new Parameter("company_id", "=",companyId));
            params.add(new Parameter("item", "=","project_account"));
            List<TfamDefaultSettings> tfamDefaultSettings = this.listAll(params, "");


            if(tfamDefaultSettings.size()==0)
            {
                TfamDefaultSettings tfamDefaultSettings1 = new TfamDefaultSettings();
                tfamDefaultSettings1.setItem("project_account");
                tfamDefaultSettings1.setValue(cdMap.getString("project_accountID"));
                tfamDefaultSettings1.setCompanyId(companyId);
                this.save(tfamDefaultSettings1);

            }
            else
            {
                TfamDefaultSettings tfamDefaultSettings1 = tfamDefaultSettings.get(0);
                tfamDefaultSettings1.setItem("project_account");
                tfamDefaultSettings1.setValue(cdMap.getString("project_accountID"));
                this.save(tfamDefaultSettings1);

            }
        }

        if(cdMap.containsKey("predevaluate_coraccountID"))
        {
            List<Parameter> params = null;
            params = Lists.newArrayList();
            params.add(new Parameter("company_id", "=",companyId));
            params.add(new Parameter("item", "=","predevaluate_coraccount"));
            List<TfamDefaultSettings> tfamDefaultSettings = this.listAll(params, "");

            if(tfamDefaultSettings.size()==0)
            {
                TfamDefaultSettings tfamDefaultSettings1 = new TfamDefaultSettings();
                tfamDefaultSettings1.setItem("predevaluate_coraccount");
                tfamDefaultSettings1.setValue(cdMap.getString("predevaluate_coraccountID"));
                tfamDefaultSettings1.setCompanyId(companyId);
                this.save(tfamDefaultSettings1);

            }
            else
            {
                TfamDefaultSettings tfamDefaultSettings1 = tfamDefaultSettings.get(0);
                tfamDefaultSettings1.setItem("predevaluate_coraccount");
                tfamDefaultSettings1.setValue(cdMap.getString("predevaluate_coraccountID"));
                this.save(tfamDefaultSettings1);
            }
        }

        if(cdMap.containsKey("depcost_accountID"))
        {
            List<Parameter> params = null;
            params = Lists.newArrayList();
            params.add(new Parameter("company_id", "=",companyId));
            params.add(new Parameter("item", "=","depcost_account"));
            List<TfamDefaultSettings> tfamDefaultSettings = this.listAll(params, "");


            if(tfamDefaultSettings.size()==0)
            {
                TfamDefaultSettings tfamDefaultSettings1 = new TfamDefaultSettings();
                tfamDefaultSettings1.setItem("depcost_account");
                tfamDefaultSettings1.setValue(cdMap.getString("depcost_accountID"));
                tfamDefaultSettings1.setCompanyId(companyId);
                this.save(tfamDefaultSettings1);
            }
            else
            {
                TfamDefaultSettings tfamDefaultSettings1 = tfamDefaultSettings.get(0);
                tfamDefaultSettings1.setItem("depcost_account");
                tfamDefaultSettings1.setValue(cdMap.getString("depcost_accountID"));
                this.save(tfamDefaultSettings1);
            }
        }

        if(cdMap.containsKey("tax_accountID"))
        {
            List<Parameter> params = null;
            params = Lists.newArrayList();
            params.add(new Parameter("company_id", "=",companyId));
            params.add(new Parameter("item", "=","tax_account"));
            List<TfamDefaultSettings> tfamDefaultSettings = this.listAll(params, "");


            if(tfamDefaultSettings.size()==0)
            {
                TfamDefaultSettings tfamDefaultSettings1 = new TfamDefaultSettings();
                tfamDefaultSettings1.setItem("tax_account");
                tfamDefaultSettings1.setValue(cdMap.getString("tax_accountID"));
                tfamDefaultSettings1.setCompanyId(companyId);
                this.save(tfamDefaultSettings1);
            }
            else
            {
                TfamDefaultSettings tfamDefaultSettings1 = tfamDefaultSettings.get(0);
                tfamDefaultSettings1.setItem("tax_account");
                tfamDefaultSettings1.setValue(cdMap.getString("tax_accountID"));
                this.save(tfamDefaultSettings1);
            }
        }

        if(cdMap.containsKey("rate"))
        {
            List<Parameter> params = null;
            params = Lists.newArrayList();
            params.add(new Parameter("company_id", "=",companyId));
            params.add(new Parameter("item", "=","rate"));
            List<TfamDefaultSettings> tfamDefaultSettings = this.listAll(params, "");



            if(tfamDefaultSettings.size()==0)
            {
                TfamDefaultSettings tfamDefaultSettings1 = new TfamDefaultSettings();
                tfamDefaultSettings1.setItem("rate");
                tfamDefaultSettings1.setValue(cdMap.getString("rate"));
                tfamDefaultSettings1.setCompanyId(companyId);
                this.save(tfamDefaultSettings1);
            }
            else
            {
                TfamDefaultSettings tfamDefaultSettings1 = tfamDefaultSettings.get(0);
                tfamDefaultSettings1.setItem("rate");
                tfamDefaultSettings1.setValue(cdMap.getString("rate"));
                this.save(tfamDefaultSettings1);
            }
        }

        if(cdMap.containsKey("depreciation_time"))
        {
            List<Parameter> params = null;
            params = Lists.newArrayList();
            params.add(new Parameter("company_id", "=",companyId));
            params.add(new Parameter("item", "=","depreciation_time"));
            List<TfamDefaultSettings> tfamDefaultSettings = this.listAll(params, "");


            if(tfamDefaultSettings.size()==0)
            {
                TfamDefaultSettings tfamDefaultSettings1 = new TfamDefaultSettings();
                tfamDefaultSettings1.setItem("depreciation_time");
                tfamDefaultSettings1.setValue(cdMap.getString("depreciation_time"));
                tfamDefaultSettings1.setCompanyId(companyId);
                this.save(tfamDefaultSettings1);
            }
            else
            {
                TfamDefaultSettings tfamDefaultSettings1 = tfamDefaultSettings.get(0);
                tfamDefaultSettings1.setItem("depreciation_time");
                tfamDefaultSettings1.setValue(cdMap.getString("depreciation_time"));
                this.save(tfamDefaultSettings1);
            }
        }

        if(cdMap.containsKey("sys_startdate"))
        {
            List<Parameter> params = null;
            params = Lists.newArrayList();
            params.add(new Parameter("company_id", "=",companyId));
            params.add(new Parameter("item", "=","sys_startdate"));
            List<TfamDefaultSettings> tfamDefaultSettings = this.listAll(params, "");


            if(tfamDefaultSettings.size()==0)
            {
                TfamDefaultSettings tfamDefaultSettings1 = new TfamDefaultSettings();
                tfamDefaultSettings1.setItem("sys_startdate");
                tfamDefaultSettings1.setValue(cdMap.getString("sys_startdate"));
                tfamDefaultSettings1.setCompanyId(companyId);
                this.save(tfamDefaultSettings1);


                //向tfam_depmonth表中插入一条id为全零的数据，只插入年月，如果折旧方法是月初折旧，就插入当年用户输入的年月，如果是月末折旧，就插入当前用户输入的上一个年月
                //查询折旧时点
                params = Lists.newArrayList();
                params.add(new Parameter("company_id", "=",companyId));
                params.add(new Parameter("item", "=","depreciation_time"));
                List<TfamDefaultSettings> tfamDefaultSettings2 = this.listAll(params, "");
                String depreciation_time = tfamDefaultSettings2.get(0).getValue();
                String yearAndMonth = cdMap.getString("sys_startdate");
                String[] strings = yearAndMonth.split("-");
                String yearString = null;
                String monthString = null;
                if(!depreciation_time.equals(""))
                {
                    if(depreciation_time.equals("0"))
                    {
                        yearString = strings[0];
                        monthString = strings[1];
                    }
                    else
                    {
                        int year = Integer.parseInt(strings[0]);
                        int month = Integer.parseInt(strings[1]);

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
                        Calendar c = Calendar.getInstance();
                        GregorianCalendar gc = new GregorianCalendar();

                        gc.set(Calendar.YEAR,year);
                        gc.set(Calendar.MONTH,month-1);
                        Date date = new Date();
                        date = gc.getTime();

                        c.setTime(date);
                        c.add(Calendar.MONTH, -1);
                        Date m = c.getTime();
                        String newYearAndMonth = format.format(m);

                        String[] newStrings = newYearAndMonth.split("-");

                        yearString = newStrings[0];
                        monthString = newStrings[1];
                    }
                    if(yearString!=null && monthString!=null)
                    {
                        TfamDepmonth tfamDepmonth = new TfamDepmonth();
                        tfamDepmonth.setYear(yearString);
                        tfamDepmonth.setMonth(yearString);
                        tfamDepmonth.setCompanyId(companyId);
                        tfamDepmonth.preInsert();
                        tfamDepmonth.setId("00000000-0000-0000-0000-000000000000");
                        tfamDepmonthDao.insert(tfamDepmonth);

                    }
                }
                else
                {
                    throw new Exception("折旧时点未设置");
                }
            }
            else
            {
                TfamDefaultSettings tfamDefaultSettings1 = tfamDefaultSettings.get(0);
                tfamDefaultSettings1.setItem("sys_startdate");
                tfamDefaultSettings1.setValue(cdMap.getString("sys_startdate"));
                this.save(tfamDefaultSettings1);
            }
        }
    }
    public List<Map<String, Object>> getData(){
        String companyId = SessionUtils.getUser().getCompanyId().toString();
       return this.dao.getData(companyId);
    }

    public List<TfamDefaultSettingsDto> listDefault(List<Parameter> params, String orderby) {
        List<TfamDefaultSettings> tfamDefaultSettings = this.listAll(params, orderby);
        List<TfamDefaultSettingsDto> dtos = new ArrayList<>();
        tfamDefaultSettings.stream().forEach(item ->{
            TfamDefaultSettingsDto tfamDefaultSettingsDto = new TfamDefaultSettingsDto();
            BeanUtils.copyProperties(item,tfamDefaultSettingsDto);
            String value = item.getValue();
            List<TglAccounts> accounts = accountsDao.getAccountById(value);
            if (accounts != null && accounts.size()>0){
                TglAccounts tglAccounts = accounts.get(0);
                tfamDefaultSettingsDto.setTglAccounts(tglAccounts);
            }

            dtos.add(tfamDefaultSettingsDto);
        });

        return dtos;
    }
}
