package com.geeke.ureport.mapper;

import java.util.List;

import com.geeke.common.data.PageRequest;
import com.geeke.common.persistence.CrudDao;
import com.geeke.ureport.entity.UreportFileEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UreportFileMapper {
	/**
	 *  根据报表名称检查报表是否存在
	 * @param name 报表名称
	 * @return
	 */
	public int checkExistByName(String name);
	
	/**
	 *  根据报表名称查询报表
	 * @param name 报表名称
	 * @return
	 */
	public UreportFileEntity queryUreportFileEntityByName(String name);

	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public UreportFileEntity get(String id);

	/**
	 * 查询数据条数(用在分页查询)
	 * @param pageRequest
	 * @return
	 */
	public int count(PageRequest pageRequest);

	/**
	 * 分页查询数据列表
	 * @param pageRequest
	 * @return
	 */
	public List<UreportFileEntity> listPage(PageRequest pageRequest);

	/**
	 * 查询所有数据列表
	 * @param pageRequest
	 * @return
	 */
	public List<UreportFileEntity> listAll(PageRequest pageRequest);

	/**
	 * 批量修改
	 * @param entitys
	 * @return
	 */
	public int bulkUpdate(@Param("entitys")List<UreportFileEntity> entitys);

	/**
	 * 查询全部报表
	 * @return 
	 */
	public List<UreportFileEntity> queryReportFileList();
	
	/**
	 * 根据报表名称删除报表
	 * @param name
	 * @return
	 */
	public int deleteReportFileByName(String name);
	
	
	/**
	 *  保存报表
	 */
	public int insertReportFile(UreportFileEntity entity);
	
	/**
	 *  更新报表
	 * @param entity
	 * @return
	 */
	public int updateReportFile(UreportFileEntity entity);
}
