package com.geeke.ureport.service;

import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.tgl.flow.entity.Flowitems;
import com.geeke.ureport.entity.UreportFileEntity;

import java.util.List;


public interface UreportFileService {
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
	 * 查询全部报表
	 * @return 
	 */
	public List<UreportFileEntity> queryReportFileList();

	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public UreportFileEntity get(String id);

	/**
	 * 查询报表(分页)
	 * @param offset
	 * @param limit
	 * @return
	 */
	public Page<UreportFileEntity> listPage(List<Parameter> parameters, int offset, int limit, String orderby);

	/**
	 * 查询列表数据
	 * @param parameters
	 * @param orderby
	 * @return
	 */
	public List<UreportFileEntity> listAll(List<Parameter> parameters, String orderby);
	
	/**
	 * 根据报表名称删除报表
	 * @param name
	 * @return
	 */
	public int deleteReportFileByName(String name);

	/**
	 *  保存报表
	 */
	public int saveReportFile(UreportFileEntity entity);
	
	
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

	/**
	 * 启用报表
	 * @param entity
	 * @return
	 */
	public int startReportFile(UreportFileEntity entity);
}
