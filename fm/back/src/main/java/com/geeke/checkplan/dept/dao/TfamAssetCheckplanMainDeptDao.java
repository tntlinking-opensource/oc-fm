package com.geeke.checkplan.dept.dao;

import com.geeke.checkplan.dept.entity.TfamAssetCheckplanMainDept;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资产盘点部门DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamAssetCheckplanMainDeptDao extends CrudDao<TfamAssetCheckplanMainDept> {
    List<TfamAssetCheckplanMainDept> getByMainId(@Param("mainId") String mainId);

    void deleteByMainId(@Param("mainId") String mainId);

    TfamAssetCheckplanMainDept selectVisable(@Param("mainId")String mainId,@Param("deptId") String deptId);
}
