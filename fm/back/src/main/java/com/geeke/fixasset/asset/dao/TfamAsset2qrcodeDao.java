package com.geeke.fixasset.asset.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.fixasset.asset.entity.TfamAsset2qrcode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 卡片管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamAsset2qrcodeDao extends CrudDao<TfamAsset2qrcode> {
    TfamAsset2qrcode getQRCode(@Param("cardId")String cardId);
}
