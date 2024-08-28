package com.geeke.ureport.service;


import com.geeke.ureport.entity.ViewUreportEntity;

public interface ViewUreportService {
	int insertOne(ViewUreportEntity viewUreportEntity);
	String queryNameByID(String id);
}
