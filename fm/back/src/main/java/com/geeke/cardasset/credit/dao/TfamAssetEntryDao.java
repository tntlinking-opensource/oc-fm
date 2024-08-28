package com.geeke.cardasset.credit.dao;

import com.geeke.cardasset.credit.entity.TfamAssetEntry;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 资产入账DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamAssetEntryDao extends CrudDao<TfamAssetEntry> {
    void linkVoucher(String s, String voucherid);
    void linkVoucher1(String s, String voucherid);

    void cancelVoucher(String id);
    void cancelVoucher1(String id);
}
