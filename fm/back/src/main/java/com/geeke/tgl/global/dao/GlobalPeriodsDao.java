package com.geeke.tgl.global.dao;

import com.geeke.common.data.PageRequest;
import com.geeke.common.persistence.CrudDao;
import com.geeke.tgl.global.entity.GlobalPeriods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会计期DAO接口
 * @author
 * @version
 */
@Mapper
public interface GlobalPeriodsDao extends CrudDao<GlobalPeriods> {

    /**
     * 根据条件查询会计期
     * @param pageRequest
     * @return
     */
    List<GlobalPeriods> selectPeriod(PageRequest pageRequest);
}
