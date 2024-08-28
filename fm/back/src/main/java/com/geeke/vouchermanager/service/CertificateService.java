package com.geeke.vouchermanager.service;


import com.geeke.admin.dao.UserDao;
import com.geeke.admin.entity.User;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.BaseService;
import com.geeke.common.service.CrudService;
import com.geeke.utils.SessionUtils;
import com.geeke.vouchermanager.dao.CertificateDao;
import com.geeke.vouchermanager.entity.TglVarabastractEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

@Service("certificateService")
@Transactional
public class CertificateService  extends BaseService {
    @Autowired
    private CertificateDao certificateDao;

    public List<TglVarabastractEntity> getAbstract(String data,int limit,int offset) {
        String tenant_id = SessionUtils.getUser().getCompanyId();
        List param = new ArrayList();
        List<TglVarabastractEntity> anAbstract = certificateDao.getAbstract(data,tenant_id,limit,offset);

        return anAbstract;
    }
    public String countAbstract(String data){
        String tenant_id =SessionUtils.getUser().getCompanyId();
        return certificateDao.countAbstract(data,tenant_id);
    }
    public void addAbstract(String data) {
        String tenant_id = SessionUtils.getUser().getCompanyId();
        String id = UUID.randomUUID().toString();
        String lesseeId = UUID.randomUUID().toString();
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sf.format(now);
        certificateDao.addAbstract(id,data,lesseeId,date,tenant_id);
    }

    public void delAbstract(String id) {
        certificateDao.delAbstract(id);
    }

    public List<Map<String,String>> getPeriodList() {
       return certificateDao.getPeriodList();
    }
}
