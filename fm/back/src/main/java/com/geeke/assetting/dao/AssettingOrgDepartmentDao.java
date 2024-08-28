package com.geeke.assetting.dao;

import com.geeke.assetting.entity.AssettingOrgDepartment;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 卡片管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface AssettingOrgDepartmentDao extends CrudDao<AssettingOrgDepartment> {}
