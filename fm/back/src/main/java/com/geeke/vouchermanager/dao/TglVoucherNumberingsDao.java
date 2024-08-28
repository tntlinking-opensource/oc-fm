package com.geeke.vouchermanager.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.vouchermanager.entity.TglVoucherNumberings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 凭证编号类别DAO接口
 * @author
 * @version
 */
@Mapper
public interface TglVoucherNumberingsDao extends CrudDao<TglVoucherNumberings> {}
