package com.disfinder.hobot;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Configuration {
	public static final Configuration ConfigurationInstance = new Configuration();
	private Configuration() {}
	public static Configuration getInstance() {return ConfigurationInstance;}
	
	private static Config configuration;
	
	public static String name;
	public static String version;
	
	public static void loadConfig()
	{
		configuration = ConfigFactory.load();
		name	= configuration.getString("main.name");
		version	= configuration.getString("main.version");
	}
	public static Config getConfiguration()
	{
		return configuration;
		
	}
}
