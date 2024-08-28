package com.geeke.vouchermanager.service;

import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.utils.Reflections;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.vouchermanager.dao.TglVoucherDetailsDao;
import com.geeke.vouchermanager.entity.TglLedger;
import com.geeke.vouchermanager.entity.TglLedgetype;
import com.geeke.vouchermanager.entity.TglVoucherDetails;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 制证Service
 * @author
 * @version
 */

@Service("tglVoucherDetailsService")
@Transactional(readOnly = true)
public class TglVoucherDetailsService extends CrudService<TglVoucherDetailsDao, TglVoucherDetails> {
    @Autowired
    private TglVoucherDetailsDao tglVoucherDetailsDao;
    public List<TglLedgetype> getByUqaccountId(String id){
        return tglVoucherDetailsDao.getByUqaccountId(id);
    }
    public List<TglLedgetype> selectQuestionItemByQuestionId(String id){
        return tglVoucherDetailsDao.selectQuestionItemByQuestionId(id);
    }
    public List<TglVoucherDetails> getByUqvoucherid(String id){
        return tglVoucherDetailsDao.getByUqvoucherid(id);
    }
}
