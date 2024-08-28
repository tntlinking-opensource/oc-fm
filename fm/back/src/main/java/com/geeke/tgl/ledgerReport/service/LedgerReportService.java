package com.geeke.tgl.ledgerReport.service;

import com.geeke.common.service.BaseService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.tgl.ledgerReport.dao.LedgerReportDao;
import com.geeke.tgl.tmp.dao.TmpIdlistDao;
import com.geeke.tgl.tmp.entity.TmpIdlist;
import com.geeke.tgl.tmp.service.TmpIdlistService;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 分户科目明细账Service
 * @author
 * @version
 */

@Service("ledgerDetailService")
@Transactional(readOnly = true)
public class LedgerReportService extends BaseService {

    @Autowired
    private LedgerReportDao dao;
    @Autowired
    private TmpIdlistDao tmpIdlistDao;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public String createLedgerDetail(Map<String, Object> mapParams) {
        mapParams.put("uqaccountsetid", SessionUtils.getUser().getCompanyId());
        // 输出参数
        mapParams.put("tableName", "");
        mapParams.put("params", "");
        // 调用存储过程，生成临时表（分户科目明细账）
        dao.createLedgerDetail(mapParams);
        String tableName = mapParams.get("tableName").toString();
        if (StringUtils.isBlank(tableName)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "查询分户明细账错误！"));
        }
        return tableName;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public String createLedgerBook(Map<String, Object> mapParams) {
        mapParams.put("uqaccountsetid",SessionUtils.getUser().getCompanyId());
        // 输出参数
        mapParams.put("tableName", "");
        mapParams.put("params", "");
        // 调用存储过程，生成临时表（分户科目余额表）
        dao.createLedgerBook(mapParams);
        String tableName = mapParams.get("tableName").toString();
        if (StringUtils.isBlank(tableName)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "查询分户科目余额表错误！"));
        }
        return tableName;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public String createLedgerGeneral(Map<String, Object> mapParams) {
        mapParams.put("uqaccountsetid", SessionUtils.getUser().getCompanyId());
        mapParams.put("accountlevel", 99); // 级次
        // 输出参数
        String bulkid = UUID.randomUUID().toString().toUpperCase();
        List<String> detailIdList = (List<String>)mapParams.get("ledgerdetailid");
        if (detailIdList.size() <= 0) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "请选择正确的分户信息！"));
        }
        List<TmpIdlist> tmpIdlistList = new ArrayList<>();
        for (int i = 0; i < detailIdList.size(); i++) {
            TmpIdlist entity = new TmpIdlist();
            entity.setBulkid(bulkid);
            entity.setId(detailIdList.get(i));
            entity.setTypename("ledgerdetail");
            tmpIdlistList.add(entity);
        }
        tmpIdlistDao.bulkInsert(tmpIdlistList); // 临时保存信息
        mapParams.put("bulkid", bulkid);
        mapParams.put("tableName", "");
        mapParams.put("params", "");
        // 调用存储过程，生成临时表（科目分户总账）
        dao.createLedgerGeneral(mapParams);
        String tableName = mapParams.get("tableName").toString();
        if (StringUtils.isBlank(tableName)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "查询科目分户总账表错误！"));
        }
        return tableName;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public String createAccountDetail(Map<String, Object> mapParams) {
        mapParams.put("uqaccountsetid", SessionUtils.getUser().getCompanyId());
        // 输出参数
        String bulkid = UUID.randomUUID().toString().toUpperCase();
        List<String> detailIdList = (List<String>)mapParams.get("ledgerdetailid");
        if (detailIdList.size() <= 0) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "请选择正确的分户信息！"));
        }
        List<TmpIdlist> tmpIdlistList = new ArrayList<>();
        for (int i = 0; i < detailIdList.size(); i++) {
            TmpIdlist entity = new TmpIdlist();
            entity.setBulkid(bulkid);
            entity.setId(detailIdList.get(i));
            entity.setTypename("ledgerdetail");
            tmpIdlistList.add(entity);
        }
        tmpIdlistDao.bulkInsert(tmpIdlistList); // 临时保存信息
        mapParams.put("bulkid", bulkid);
        mapParams.put("tableName", "");
        mapParams.put("params", "");
        // 调用存储过程，生成临时表（科目分户总账）
        dao.createAccountDetail(mapParams);
        String tableName = mapParams.get("tableName").toString();
        if (StringUtils.isBlank(tableName)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "查询科目分户明细账错误！"));
        }
        return tableName;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public String createAccountBook(Map<String, Object> mapParams) {
        mapParams.put("uqaccountsetid",SessionUtils.getUser().getCompanyId());
        // 输出参数
        String bulkid = UUID.randomUUID().toString().toUpperCase();
        List<String> detailIdList = (List<String>)mapParams.get("ledgerdetailid");
        if (detailIdList.size() <= 0) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "请选择正确的分户信息！"));
        }
        List<TmpIdlist> tmpIdlistList = new ArrayList<>();
        for (int i = 0; i < detailIdList.size(); i++) {
            TmpIdlist entity = new TmpIdlist();
            entity.setBulkid(bulkid);
            entity.setId(detailIdList.get(i));
            entity.setTypename("ledgerdetail");
            tmpIdlistList.add(entity);
        }
        tmpIdlistDao.bulkInsert(tmpIdlistList); // 临时保存信息
        mapParams.put("bulkid", bulkid);
        mapParams.put("tableName", "");
        mapParams.put("params", "");
        // 调用存储过程，生成临时表（科目分户余额表）
        dao.createAccountBook(mapParams);
        String tableName = mapParams.get("tableName").toString();
        if (StringUtils.isBlank(tableName)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "查询科目分户余额表错误！"));
        }
        return tableName;
    }

}
