package com.disfinder.hobot;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.typesafe.config.Config;
//import com.typesafe.config.ConfigFactory;



public class StartupListener implements  ServletContextListener {
	private static final Log LOG = LogFactory.getLog(StartupListener.class);


	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		LOG.info("Going to shtdown");
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		LOG.debug("Load configuration");
//		Configuration CI=Configuration.getInstance();
		Configuration.loadConfig();
		Config config=Configuration.getConfiguration();
		LOG.debug("Loaded config: "+config);
		LOG.debug("You are using hobot "+Configuration.name+":"+Configuration.version);
		System.out.println("Started "+LOG.isInfoEnabled());  
		LOG.info("info Startig up");
		LOG.debug(" debugStartig up!!");
		LOG.warn("warn Startig up");
		LOG.trace(" trace warn Startig up");
		LOG.fatal("fatal lalala");
		LOG.error("Startig up!!");
		
	}

}
