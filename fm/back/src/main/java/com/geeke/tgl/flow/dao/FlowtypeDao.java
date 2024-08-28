package com.geeke.tgl.flow.dao;

import com.geeke.common.data.PageRequest;
import com.geeke.common.persistence.TreeDao;
import com.geeke.tgl.flow.entity.Flowtype;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 现金流量类别DAO接口
 * @author
 * @version
 */
@Mapper
public interface FlowtypeDao extends TreeDao<Flowtype> {
    /**
     * 根据编号获取信息
     * @param code
     * @return
     */
    Flowtype getFlowTypeByCode(@Param("code") String code);
}
