package com.lenovo.itac.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		//需要过滤掉静态资源
		String servletPath = req.getServletPath();
		boolean isStaticResource = (servletPath.indexOf("/css/") != -1)
					|| (servletPath.indexOf("/js/") != -1)
					|| (servletPath.indexOf("/easyui/") != -1)
					|| (servletPath.indexOf("/image/") != -1);
		
		if (servletPath.indexOf("/login") == -1 && !isStaticResource) {
			HttpSession session = req.getSession(true);
			if (session.getAttribute("user") == null) {
				req.getRequestDispatcher("/login.jsp").forward(req, res);
			}
		}
		
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
