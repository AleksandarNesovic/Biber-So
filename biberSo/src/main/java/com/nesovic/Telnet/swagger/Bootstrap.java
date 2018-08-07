package com.nesovic.Telnet.swagger;

import javax.servlet.ServletConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import io.swagger.jaxrs.config.BeanConfig;

public class Bootstrap extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		BeanConfig beanConfig=new BeanConfig();
		//beanConfig.setBasePath("/webapi/");
		beanConfig.setBasePath("biberSo/webapi/");
		//beanConfig.setHost("biber-so.herokuapp.com/");
		beanConfig.setHost("localhost:8080");
		beanConfig.setTitle("Narudzbina app Swagger Docs");
		beanConfig.setResourcePackage("com.nesovic.Telnet");
		beanConfig.setPrettyPrint(true);
		beanConfig.setScan(true);
		//beanConfig.setSchemes(new String[] {"https"});
		beanConfig.setSchemes(new String[] {"http"});
		beanConfig.setVersion("1.0");
	}
}

