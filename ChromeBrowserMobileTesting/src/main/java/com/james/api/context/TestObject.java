package com.james.api.context;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.james.api.service.ServiceApi;
import com.james.api.util.database.SqlDatabase;
import com.james.api.util.database.SqlDatabaseMybatis;
import com.james.api.util.database.SqlMappers;

import enums.Resource;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TestObject {

	private ServiceApi serviceApi;
	private ScenarioContext scenarioContext;
	private Long timeStart;
	private Long finishTime;
	private final Logger logger;
	//private VaultConfig vaultConfig;
	private Resource resource;
	private WebDriver driver;
	
	private String dbUsername;
	private String dbPassword;
	private SqlDatabase sqlDatabase;
	private SqlMappers sqlMappers;
	
	
	public TestObject() throws IOException {
		logger = Logger.getLogger(TestObject.class.getName());
		this.scenarioContext = new ScenarioContext();
		this.serviceApi = new ServiceApi();
		String env = serviceApi.getTenant();
		String tenant = serviceApi.getTenant();
		String cattegort = serviceApi.getCategory();
		//vaultConfig = new VaultConfig(tenant,category,env)
		
		setDbUsername();
		setDbPassword();
		
		if(serviceApi.getProperty("db.framework").toLowerCase().equals("mybatis")) {
			this.sqlDatabase = new SqlDatabaseMybatis();
		}
		assert this.sqlDatabase != null;
		this.sqlMappers = new SqlMappers(sqlDatabase);
		this.sqlDatabase.createSession(this.getDatabaseProperties());
		this.sqlMappers.countryMappers();
		
	}
	
	public Properties getDatabaseProperties() {
		
		Properties properties = new Properties();
		properties.setProperty("driver", serviceApi.getProperty(MessageFormat.format("db.{0}.driver", serviceApi.getEnvironment().toLowerCase())));
		properties.setProperty("url", serviceApi.getProperty(MessageFormat.format("db.{0}.url", serviceApi.getEnvironment().toLowerCase())));
		//properties.setProperty("user", serviceApi.getProperty(MessageFormat.format("db.{0}.user", serviceApi.getEnvironment().toLowerCase())));
		//properties.setProperty("password", serviceApi.getProperty(MessageFormat.format("db.{0}.password", serviceApi.getEnvironment().toLowerCase())));
		properties.setProperty("user", this.getDbUsername());
		properties.setProperty("password",this.getDbPassword());
		return properties;
	}

	public void setDbPassword() {
		
		try {
			dbPassword = serviceApi.getProperty(MessageFormat.format("db.{0}.password", serviceApi.getEnvironment().toLowerCase()));
			System.out.println("Database password: " + dbPassword);
		} catch (Exception e) {
			System.out.println("Credentials Error: Database password property not found in your vault/ prop file");
		}
		
	}

	public void setDbUsername() {
		
		try {
			dbUsername = serviceApi.getProperty(MessageFormat.format("db.{0}.user", serviceApi.getEnvironment().toLowerCase()));
			System.out.println("Database username: " + dbUsername);
		} catch (Exception e) {
			System.out.println("Credentials Error: Database username property not found in your vault/ prop file");
		}
	}
	
	public String getDbUsername() {
		return dbUsername;
	}

	public String getDbPassword() {
		return dbPassword;
	}
	// this to be removed use @Data lombok instead
	public SqlMappers getSqlMappers() {
		return sqlMappers;
	}
	// this to be removed use @Data lombok instead
	public SqlDatabase getSqlDatabase() {
		return sqlDatabase;
	}

	public ServiceApi getServiceApi() {
		return serviceApi;
	}
	
	public void setServiceApi(ServiceApi serviceApi) {
		this.serviceApi = serviceApi;
	}
	
	public ScenarioContext getScenarioContext() {
		return scenarioContext;
	}
	
	public void setScenarioContext(ScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
	
	public Long getTimeStart() {
		return timeStart;
	}
	
	public void setTimeStart(Long timeStart) {
		this.timeStart = timeStart;
	}
	
	public Long getFinishTime() {
		return finishTime;
	}
	
	public void setFinishTime(Long finishTime) {
		this.finishTime = finishTime;
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
}
