package com.james.api.util.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SqlDatabaseMybatis implements SqlDatabase{

	private List<Class> mappers;
	private SqlSession sqlSession;
	private PooledDataSource dataSource;
	
	
	@Override
	public SqlSession createSession(Properties properties) {
		
		if (this.sqlSession != null && this.sqlSession.getConnection() != null) {
			return this.sqlSession;
		}
		dataSource = new PooledDataSource();
		dataSource.setDriver(properties.getProperty("driver"));
		dataSource.setUrl(properties.getProperty("url"));
		dataSource.setUsername(properties.getProperty("user"));
		dataSource.setPassword(properties.getProperty("password"));
		
		Configuration configuration = new Configuration(new Environment("dbConnection", new JdbcTransactionFactory(), dataSource));
		
		if (CollectionUtils.isNotEmpty(mappers)) {
			mappers.forEach(configuration::addMapper);
		}
		
		this.sqlSession = new SqlSessionFactoryBuilder().build(configuration).openSession();
		
		return this.sqlSession;
	}

	@Override
	public void runSqlScript(String directory, String filename) throws SQLException {
		ScriptRunner scriptRunner = new ScriptRunner(dataSource.getConnection());
		try {
			scriptRunner.runScript(new BufferedReader(new FileReader(Objects.requireNonNull(getClass().getClassLoader().getResource(
					directory + "/" + filename)).getFile())));
		} catch (Exception e) {
			System.out.println("File not found: "+ e.getMessage());
		}
		
	}

	@Override
	public void runSqlScript(Reader reader) throws SQLException{
		ScriptRunner scriptRunner = new ScriptRunner(dataSource.getConnection());
		scriptRunner.runScript(reader);	
	}

	@Override
	public void setMappers(List<Class> mappers) {
		this.mappers = mappers;	
	}

	@Override
	public SqlSession getSqlSession() {
		return sqlSession;
	}

}
