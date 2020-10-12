package com.hsbc.auto;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.PropertyConfigurator;

@WebListener("application logger")
public class ContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		String log4jFile = servletContext.getInitParameter("log4j-config");
		String fullPath = servletContext.getRealPath("") + File.separator + log4jFile;

		PropertyConfigurator.configure(fullPath);

	}

}
