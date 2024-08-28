package com.geeke.tgl.prop.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.tgl.prop.entity.PropValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 自动出纳配置DAO接口
 * @author
 * @version
 */
@Mapper
public interface PropValueDao extends CrudDao<PropValue> {

    /**
     * 根据公司ID查询 limit 1
     * @param companyId
     * @return
     */
    PropValue getByCompany(@Param("companyId")String companyId);
}
