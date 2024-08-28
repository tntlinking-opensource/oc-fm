package com.geeke.checkplan.checkplanmain.dao;

import com.geeke.checkplan.checkplanmain.entity.TfamAssetCheckplanMain;
import com.geeke.checkplan.resp.DownloadResp;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 盘点主表DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamAssetCheckplanMainDao extends CrudDao<TfamAssetCheckplanMain> {
    List<TfamAssetCheckplanMain> selectVisablePlan(@Param("companyId") String companyId);

    Integer selectAlreadyCheckNum(@Param("mainId") String mainId);

    int checkMainById(@Param("mainId") String mainId);
    List<TfamAssetCheckplanMain> MainById(@Param("mainId") String mainId);

    List<DownloadResp> queryAttachZip(@Param("mainId")String mainId,@Param("attachCategory") String attachCategory);
}
