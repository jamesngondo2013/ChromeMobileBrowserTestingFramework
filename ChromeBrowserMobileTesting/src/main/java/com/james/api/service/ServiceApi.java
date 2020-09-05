package com.james.api.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class ServiceApi {

	private String environment;
	private String category;
	private String tenant;
	private String oauth;
	private OutputStream outputStream;
	private Properties prop;
	//private Properties properties;
	private final String propertyFilePath = "config//project.properties";
	
	
	public ServiceApi() throws IOException {
		setEnvironment();
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment() throws IOException {
		String env = System.getenv("ENV");
		if(environment == null) {
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(propertyFilePath));
				prop = new Properties();
				try {
					prop.load(reader);
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
			}
			//initialize from properties file
			category = prop.getProperty("CATEGORY_NAME").trim();
			tenant = prop.getProperty("TENANT_NAME").trim();
			
			if (env == null) {
				if (prop.getProperty("ENV") == null) {
					environment = "INT".toLowerCase();
				}
				else {
					environment = prop.getProperty("ENV");
				}
			}
			else {
				environment = env.trim();
			}
		}
	}
	
	public String getCurrentEnv() {
		String currentEnv;
		try {
			currentEnv = System.getenv("ENV");
		} catch (Exception e) {
			currentEnv = null;
		}
		return currentEnv;
	}
	
	public String getCategory() {
		return category;
	}
	
	public boolean isOauthUserEnabled() {
		oauth = prop.getProperty("OAUTH_USER_PASSWORD");
		return Boolean.valueOf(oauth.toLowerCase());
	}
	
	public String getTenant() {
		return tenant;
	}
	public boolean isDatabaseIconfigEnabled() {
		oauth = prop.getProperty("db.iconfig.credentials");
		return Boolean.valueOf(oauth.toLowerCase());
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}
	
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	public Properties getProp() {
		return prop;
	}
	
	public String getProperty(String key) {
		return prop.getProperty(key);
	}
	
	public String maskData(String data) {
	
		data = data.replaceAll("Authorization=Bearer (\\w*)", "Authorization=**masked");
		data = data.replaceAll("client_secret=(\\w*)-(\\w*)-(\\w*)-(\\w*)", "client_secret=**masked");
		data = data.replaceAll("([0-9]{3})-([0-9]{2})-([0-9]{4})", "XXX-XX-XXXX");
		return data;
	}
	
	
}
