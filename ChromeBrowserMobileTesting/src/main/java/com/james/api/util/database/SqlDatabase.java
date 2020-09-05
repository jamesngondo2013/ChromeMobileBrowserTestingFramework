package com.james.api.util.database;

import java.io.Reader;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.session.SqlSession;

public interface SqlDatabase {

	SqlSession createSession(Properties properties);
	void runSqlScript(String directory, String filename) throws SQLException;
	void runSqlScript(Reader reader) throws SQLException;
	void setMappers(List<Class> mappers);
	SqlSession getSqlSession();
}
