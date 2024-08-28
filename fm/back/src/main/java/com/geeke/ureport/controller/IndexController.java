package com.geeke.ureport.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PROJECT_NAME: fm
 * @NAME: IndexController
 * @USER: yuy
 * @DATE: 2023/4/24 16:03
 * @DESCRIPTION:
 */

@RestController
public class IndexController {
	private final Logger log= LoggerFactory.getLogger(getClass());
	@RequestMapping("/")
	public String index(HttpSession session){
		log.info("session:"+ session.getAttribute("viewid"));
        return "viewid:"+ session.getAttribute("viewid")+",  <a href=/viewbyid>/viewbyid</a>";
    }
}
