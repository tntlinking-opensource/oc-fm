package com.geeke.fixasset.producer.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.fixasset.producer.entity.TfamSupplier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 供应商管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamSupplierDao extends CrudDao<TfamSupplier> {

    TfamSupplier getSupplierByName(@Param("producerName") String producerName,@Param("companyId") String companyId);

}
