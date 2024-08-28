package com.geeke.ureport.mapper;

import com.geeke.ureport.entity.ViewUreportEntity;
import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface ViewUreportMapper {
	
	public String queryNameByID(String id);
	public int insertOne(ViewUreportEntity viewUreportEntity);
}
