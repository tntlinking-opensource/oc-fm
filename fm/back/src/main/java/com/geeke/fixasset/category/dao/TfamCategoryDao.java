package com.geeke.fixasset.category.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.fixasset.category.entity.TfamCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 资产类别DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamCategoryDao extends CrudDao<TfamCategory> {

    public List<Map<String,Object>> getTree(String parentId,String companyId);

    TfamCategory getCategoryByName(@Param("name") String name,@Param("companyId") String companyId);
}
