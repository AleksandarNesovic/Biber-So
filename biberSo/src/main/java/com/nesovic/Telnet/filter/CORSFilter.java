package com.nesovic.Telnet.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ext.Provider;

//Enable it for Servlet 3.x implementations
//@ WebFilter(asyncSupported = true, urlPatterns = { "/*" }) 
@Provider
public class CORSFilter implements Filter {

	public CORSFilter() {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		HttpServletResponse response=(HttpServletResponse) servletResponse;
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		System.out.println("CORSFilter HTTP Request: " + request.getMethod());

		// Authorize (allow) all domains to consume the content
		response.addHeader("Access-Control-Allow-Headers", "Origin,Accept,Access-Control-Request-Method, Access-Control-Request-Headers,Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With,api_key,*");
		response.addHeader("Access-Control-Request-Headers", " Origin, X-Atmosphere-tracking-id, X-Atmosphere-Framework, X-Cache-Date, Content-Type, X-Atmosphere-Transport,  *");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST,DELETE");

		// HttpServletResponse resp = (HttpServletResponse) servletResponse;
		// For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
		if (request.getMethod().equals("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		} 
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}
	public void destroy() {
	}
	/*	@Override
		public void filter(ContainerRequestContext request,
				ContainerResponseContext response) throws IOException {
	        response.getHeaders().add("Access-Control-Allow-Origin", "*");
	        response.getHeaders().add("Access-Control-Allow-Headers",
	                "origin, content-type, accept, authorization");
	        response.getHeaders().add("Access-Control-Allow-Methods",
	                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
	    }*/

}


