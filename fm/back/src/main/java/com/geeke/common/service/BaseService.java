package com.geeke.common.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.geeke.common.data.Parameter;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.utils.FreeMarkers;
import com.geeke.sys.utils.SessionUtils;
import com.geeke.utils.JwtUtils;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.jsonwebtoken.Claims;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service基类
 * @author lys
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class BaseService {

	// 当前用户和当前公司的key值
	private static String CURRENT_USER_KEY = "currentUser";
	private static String CURRENT_COMPANY_KEY = "currentCompany";

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	//创建锁对象
	Lock lock = new ReentrantLock(); //不指定布尔类型，默认非公平锁

	private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");

	/**
	 * 筛选条件过滤
	 * @param parameters
	 * @param filter
	 * @return
	 */
	protected List<Parameter> addFilter(List<Parameter> parameters, String filter) {
		List<Parameter> newList = Lists.newArrayList();
		String myFilter = evalFilter(filter);
		List<Parameter> filters = JSONArray.parseArray(myFilter, Parameter.class);

		if(null != filters && filters.size() > 0) {
			// 刷选条件使用（）包含，避免引发逻辑错误
			newList.add(new Parameter("", "(", "" ));
			newList.addAll(filters);
			newList.add(new Parameter("", ")", "" ));
		}

		if(null != parameters && parameters.size() > 0) {
			// 客户端条件使用（）包含，避免引发逻辑错误
			newList.add(new Parameter("", "(", "" ));
			newList.addAll(parameters);
			newList.add(new Parameter("", ")", "" ));
		}
		return newList;
	}

	private String evalFilter(String filter) {
		StringBuffer buffer =  new StringBuffer();
		buffer.append("[");
		buffer.append(filter);
		buffer.append("]");

		if(!filter.contains(CURRENT_USER_KEY) && !filter.contains(CURRENT_COMPANY_KEY)) {
			return buffer.toString();
		}

		// 获取锁
		lock.lock();
		try {
			engine.put(CURRENT_USER_KEY, SessionUtils.getUserJson());
			engine.put(CURRENT_COMPANY_KEY,SessionUtils.getUserJson().getJSONObject("company"));
			StringBuffer bufferEval =  new StringBuffer();
			bufferEval.append("var obj = JSON.stringify(");
			bufferEval.append(buffer);
			bufferEval.append(");");

			engine.eval(bufferEval.toString());
			return engine.get("obj").toString();
		} catch(Exception e) {
			throw new CommonJsonException(ResultUtil.errorJson(ErrorEnum.E_50001, "业务表筛选条件配置格式不正确"));
		}finally {
			// 释放锁
			lock.unlock();
		}
	}

	protected String getTenantId(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String database = "1409315006189821953";
		//先查看请求头是否直接带了租户id 带了直接走 包括数据总线也是这么走
		String tenantId = request.getHeader("tenant_id");
		if(StringUtils.isNotBlank(tenantId) && !Objects.equals(tenantId,"0")){
			return tenantId;
		}
		String token = request.getHeader("X-Token");
		if( token == null ) {
			token = request.getParameter("token");
		}
		if(token == null ) {
			return database;
		}
		try {
			JwtUtils jwtUtils = new JwtUtils();
			Claims claims = jwtUtils.parseJWT(token);
			JSONObject json = JSONObject.parseObject(claims.getSubject());
			if(StringUtils.isNotBlank(json.getString("tenant_id")) && !Objects.equals(json.getString("tenant_id"),"0")) {
				return json.getString("tenant_id");
			}
		}catch(Exception e){
			return database;
		}
		return database;
	}
	protected String getGroupid(){
		return "1409315006189821953";
	}
	protected String getUqaccountsetid(){
		return "1409315006189821953";
	}
}
