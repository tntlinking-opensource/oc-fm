package com.geeke.tgl.tmp.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.tgl.tmp.entity.TmpIdlist;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 利润结转科目配置DAO接口
 * @author
 * @version
 */
@Mapper
public interface TmpIdlistDao extends CrudDao<TmpIdlist> {}
