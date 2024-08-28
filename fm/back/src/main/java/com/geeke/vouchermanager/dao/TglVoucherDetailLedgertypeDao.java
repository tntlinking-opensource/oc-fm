package com.geeke.vouchermanager.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.vouchermanager.entity.TglVoucherDetailLedgertype;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 凭证分录分户类别表DAO接口
 * @author
 * @version
 */
@Mapper
public interface TglVoucherDetailLedgertypeDao extends CrudDao<TglVoucherDetailLedgertype> {}
