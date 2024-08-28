package com.geeke.ureport.service.impl;

import javax.annotation.Resource;

import com.geeke.ureport.entity.ViewUreportEntity;
import com.geeke.ureport.mapper.ViewUreportMapper;
import com.geeke.ureport.service.ViewUreportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ViewUreportServiceImpl implements ViewUreportService {
	@Resource
	private ViewUreportMapper viewUreportMapper;
	@Transactional
	public int insertOne(ViewUreportEntity viewUreportEntity) {
		int i = 0;
		String name = viewUreportMapper.queryNameByID(viewUreportEntity.getId());
		if(name==null || name.isEmpty()){
			i = viewUreportMapper.insertOne(viewUreportEntity);
		}
		return i;
	}
	@Override
	public String queryNameByID(String id) {
		String name = viewUreportMapper.queryNameByID(id);
		return name;
	}

}
