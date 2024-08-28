package com.geeke.ureport.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.geeke.ureport.service.ViewUreportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @PROJECT_NAME: fm
 * @NAME: ViewByIDController
 * @USER: yuy
 * @DATE: 2023/4/24 16:04
 * @DESCRIPTION:
 */

@RestController
public class ViewByIDController {
	private final Logger log= LoggerFactory.getLogger(getClass());
	@Autowired
	private ViewUreportService viewUreportService;
	@RequestMapping("/viewbyid")
	public ModelAndView index(
			@RequestParam(value = "viewid", defaultValue = "000000") String viewid,
			HttpSession session){
		ModelAndView mv = new ModelAndView();
		String name = viewUreportService.queryNameByID(viewid);
		if(name == null || name.isEmpty()){
			session.setAttribute("viewid", new Date().getTime()+"");
			mv.setViewName("redirect:/ureport/designer"); // 使用重定向
		}else{
			session.setAttribute("viewid", viewid);
			mv.setViewName("redirect:/ureport/designer?_u="+name); // 使用重定向
		}
		// mv.setViewName("forward:/ureport/designer?_u=report-sssss.ureport.xml"); // 隐式跳转
		log.info(viewid+" : "+name);
		return mv;
	}
}
