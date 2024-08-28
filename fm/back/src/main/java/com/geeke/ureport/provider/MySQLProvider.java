package com.geeke.ureport.provider;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.geeke.sys.utils.SessionUtils;
import com.geeke.ureport.entity.UreportFileEntity;
import com.geeke.ureport.entity.ViewUreportEntity;
import com.geeke.ureport.service.UreportFileService;
import com.geeke.ureport.service.ViewUreportService;
import com.geeke.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.bstek.ureport.provider.report.ReportFile;
import com.bstek.ureport.provider.report.ReportProvider;

import lombok.Setter;

/**
 * @PROJECT_NAME: fm
 * @NAME: MySQLProvider
 * @USER: yuy
 * @DATE: 2023/4/24 15:59
 * @DESCRIPTION: 自定义报表存储器
 */


@Setter
@Component
@ConfigurationProperties(prefix = "ureport.mysql.provider")
public class MySQLProvider implements ReportProvider{
	private final Logger log= LoggerFactory.getLogger(getClass());
	private static final String NAME = "mysql-provider-";
	private String prefix = "";
	private boolean disabled;
	
	@Autowired
	private ViewUreportService viewUreportService;
	@Autowired
	private UreportFileService ureportFileService;
	@Autowired
	private HttpServletRequest request;
	/**
	 * 根据报表名加载报表文件
	 * @param file 报表名称
	 * @return 返回的InputStream
	 */
	@Override
	public InputStream loadReport(String file) {
		UreportFileEntity ureportFileEntity = ureportFileService.queryUreportFileEntityByName(getCorrectName(file));
		byte[] content = ureportFileEntity.getContent();
		ByteArrayInputStream inputStream = new ByteArrayInputStream(content);
		return inputStream;
	}
	/**
	 * 根据报表名，删除指定的报表文件
	 * @param file 报表名称
	 */
	@Override
	public void deleteReport(String file) {
		ureportFileService.deleteReportFileByName(getCorrectName(file));
	}
	/**
	 * 获取所有的报表文件
	 * @return 返回报表文件列表
	 */
	@Override
	public List<ReportFile> getReportFiles() {
		List<UreportFileEntity> list = ureportFileService.queryReportFileList();
		List<ReportFile> reportList = new ArrayList<>();
		for (UreportFileEntity ureportFileEntity : list) {
			reportList.add(new ReportFile(ureportFileEntity.getName(), ureportFileEntity.getUpdateTime()));
		}
		return reportList;
	}
	/**
	 * 保存报表文件
	 * @param file 报表名称
	 * @param content 报表的XML内容
	 */
	@Override
	public void saveReport(String file, String content) {
		file = getCorrectName(file);
		UreportFileEntity ureportFileEntity = ureportFileService.queryUreportFileEntityByName(file);
		Date currentDate = new Date();
		if(ureportFileEntity == null){
			ureportFileEntity = new UreportFileEntity();
			ureportFileEntity.setName(file);
			ureportFileEntity.setContent(content.getBytes());
			ureportFileEntity.setCreateTime(currentDate);
			ureportFileEntity.setUpdateTime(currentDate);
			ureportFileService.insertReportFile(ureportFileEntity);
		}else{
			ureportFileEntity.setContent(content.getBytes());
   			ureportFileEntity.setUpdateTime(currentDate);
   			ureportFileService.updateReportFile(ureportFileEntity);
		}
		HttpSession session = request.getSession();
		if(session.getAttribute("viewid") == null){
			log.info("viewid为空");
		}else{
			String viewid = session.getAttribute("viewid").toString();
			log.info("viewid:"+viewid);
			ViewUreportEntity viewUreportEntity = new ViewUreportEntity();
			viewUreportEntity.setId(viewid);
			viewUreportEntity.setName(prefix+file);
			int i = viewUreportService.insertOne(viewUreportEntity);
			log.info(file+"ViewUreportEntity插入成功"+i);
		}
	}
	/**
	 * @return 返回存储器名称
	 */
	@Override
	public String getName() {
		return NAME;
	}
	/**
	 * @return 返回是否禁用
	 */
	@Override
	public boolean disabled() {
		return disabled;
	}
	/**
	 * @return 返回报表文件名前缀
	 */
	@Override
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @description 获取没有前缀的文件名
	 * @param name
	 * @return
	 */
	
	private String getCorrectName(String name){
		
		
		log.info("前缀:"+prefix);
		if(name.startsWith(prefix)){
			name = name.substring(prefix.length(), name.length());
		}
		return name; 
	}
	
	
}
