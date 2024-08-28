package com.geeke.vouchermanager.dao;

import com.geeke.common.data.PageRequest;
import com.geeke.common.persistence.CrudDao;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.vouchermanager.entity.TglVarabastractEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CertificateDao {
    List<TglVarabastractEntity> getAbstract(String data,String tenant_id,int limit,int offset);

    void addAbstract(String id,String data,String lesseeId,String date,String tenantId );

    String countAbstract(String data, String tenant_id);

    void delAbstract(String id);

    List<Map<String,String>> getPeriodList();
}
