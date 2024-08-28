package com.geeke.ureport;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import com.bstek.ureport.console.UReportServlet;

@ImportResource("classpath:ureport-console-context.xml")
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = "com.geeke")
public class RegUreport {

	 /**注册 UReportServlet
     * @param  servlet
     * @param  urlMappings 值为“/ureport/*”的 urlMappings 是一定不能变的，否则系统将无法运行。
     */
	 @Bean
	 public ServletRegistrationBean buildUreportServlet(){
		 return new ServletRegistrationBean(new UReportServlet(), "/ureport/*");
	 }
}
