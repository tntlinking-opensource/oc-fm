package com.geeke.ureport.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.BaseService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.org.entity.Company;
import com.geeke.sys.utils.SessionUtils;
import com.geeke.ureport.entity.UreportFileEntity;
import com.geeke.ureport.mapper.UreportFileMapper;
import com.geeke.ureport.service.UreportFileService;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class UreportFileServiceImpl extends BaseService implements UreportFileService {
	@Resource
	private UreportFileMapper ureportFileMapper;

	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public int checkExistByName(String name) {
		
		return ureportFileMapper.checkExistByName(name);
	}

	@Override
	public UreportFileEntity queryUreportFileEntityByName(String name) {
		// TODO Auto-generated method stub
		return ureportFileMapper.queryUreportFileEntityByName(name);
	}

	@Override
	public List<UreportFileEntity> queryReportFileList() {
		// TODO Auto-generated method stub
		return ureportFileMapper.queryReportFileList();
	}

	@Override
	public UreportFileEntity get(String id) {
		return ureportFileMapper.get(id);
	}

	@Override
	public Page<UreportFileEntity> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
		PageRequest pageRequest = new PageRequest(offset, limit, parameters, orderby);
		int total = ureportFileMapper.count(pageRequest);
		List<UreportFileEntity> list = null;
		if(total > 0) {
			list = ureportFileMapper.listPage(pageRequest);
		}
		return new Page<UreportFileEntity>(total, list);
	}

	@Override
	public List<UreportFileEntity> listAll(List<Parameter> parameters, String orderby) {
		// 租户、公司隔离条件
		PageRequest pageRequest = new PageRequest(parameters, orderby);
		return ureportFileMapper.listAll(pageRequest);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public int deleteReportFileByName(String name) {
		// TODO Auto-generated method stub
		return ureportFileMapper.deleteReportFileByName(name);
	}

	/**
	 * 报表设计器中的保存操作
	 * @param entity
	 * @return
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public int saveReportFile(UreportFileEntity entity) {
		// TODO Auto-generated method stub
		Date currentDate = new Date();
		UreportFileEntity ureportFileEntity = queryUreportFileEntityByName(entity.getName());
		if (null != ureportFileEntity && !"".equals(ureportFileEntity)) {
			throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "报表名称不可重复"));
		}
		JSONObject userObj = SessionUtils.getUserJson();
		if (userObj != null && StringUtils.isNotBlank(userObj.getString("id"))){
			entity.setUpdateBy(userObj.getString("name") + "(" + userObj.getString("loginName") + ")");
		}
		entity.setUpdateTime(currentDate);
		entity.setCreateTime(currentDate);
		return ureportFileMapper.insertReportFile(entity);
	}

	/**
	 * 在ureport报表组件中进行文件保存操作
	 * @param entity
	 * @return
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public int insertReportFile(UreportFileEntity entity) {
		return ureportFileMapper.insertReportFile(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public int updateReportFile(UreportFileEntity entity) {
		// TODO Auto-generated method stub
		entity.setUpdateTime(new Date());
		return ureportFileMapper.updateReportFile(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public int startReportFile(UreportFileEntity entity) {
		//先停用正在使用的报表
		List<Parameter> params = null;
		PageRequest pageRequest;
		params = Lists.newArrayList();
		params.add(new Parameter("company_id", "=", entity.getCompany().getId()));
		params.add(new Parameter("type", "=", entity.getType()));
		pageRequest = new PageRequest(params);
		JSONObject userObj = SessionUtils.getUserJson();
		List<UreportFileEntity> reportFileList = ureportFileMapper.listAll(pageRequest);
		if (reportFileList.size() > 0) {
			for (int i = 0; i < reportFileList.size(); i++) {
				reportFileList.get(i).setStatus(0);
			}
			ureportFileMapper.bulkUpdate(reportFileList); // 批量修改
		}
		entity.setStatus(1); // 启用当前记录
		if (userObj != null && StringUtils.isNotBlank(userObj.getString("id"))){
			entity.setUpdateBy(userObj.getString("name") + "(" + userObj.getString("loginName") + ")");
		}
		entity.setUpdateTime(new Date());
		return ureportFileMapper.updateReportFile(entity);
	}

}
