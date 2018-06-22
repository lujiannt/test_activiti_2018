package testAiviti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;

public class Test01 {
	public static void main(String[] args) {
		//配置数据库信息
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
		processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
		processEngineConfiguration.setJdbcUrl("jdbc:mysql://localhost:3306/testactiviti");
		processEngineConfiguration.setJdbcUsername("root");
		processEngineConfiguration.setJdbcPassword("root");
		
		/**
		public static final String DB_SCHEMA_UPDATE_FALSE = "false";（默认,需要数据库、表都存在）
		public static final String DB_SCHEMA_UPDATE_CREATE_DROP = "create-drop"（先删除再创建）;
		public static final String DB_SCHEMA_UPDATE_TRUE = "true"（自动创建）;
		**/
		processEngineConfiguration.setDatabaseSchemaUpdate(processEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		
		//工作流核心对象流程引擎ProcessEngine
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
		System.out.println(processEngine);
	}
	
}
