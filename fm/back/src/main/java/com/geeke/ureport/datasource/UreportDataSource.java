package com.geeke.ureport.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bstek.ureport.definition.datasource.BuildinDatasource;

/**
 * @PROJECT_NAME: fm
 * @NAME: UreportDataSource
 * @USER: yuy
 * @DATE: 2023/4/24 15:54
 * @DESCRIPTION: 内置数据源，实现BuildinDatasource接口
 */

@Component
public class UreportDataSource implements BuildinDatasource {
	private static final String NAME = "MyDataSource";
	private Logger log = LoggerFactory.getLogger(UreportDataSource.class);
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public String name() {
		return NAME;
	}

	@Override
	public Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			log.error("Ureport 数据源 获取连接失败！");
			e.printStackTrace();
		}
		return null;
	}

}
