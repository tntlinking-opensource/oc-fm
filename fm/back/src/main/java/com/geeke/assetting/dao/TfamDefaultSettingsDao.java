package com.geeke.assetting.dao;

import com.geeke.assetting.entity.TfamDefaultSettings;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 资产科目方案配置DAO接口
 * @author
 * @version
 */
@Mapper
public interface TfamDefaultSettingsDao extends CrudDao<TfamDefaultSettings> {
    public List<Map<String,Object>> getData(String companyId);

}
