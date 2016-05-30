package com.gensoft.saasapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(locations = "classpath:config/base.properties") 
public class BaseProperties {

	public BaseProperties() {
		// TODO Auto-generated constructor stub
	}
	
	private String logoimgpath;

	public String getLogoimgpath() {
		return logoimgpath;
	}

	public void setLogoimgpath(String logoimgpath) {
		this.logoimgpath = logoimgpath;
	}
	
	

}
