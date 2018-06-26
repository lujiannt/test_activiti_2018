package testActiviti01;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

public class Test01 {
	@Test
	public void test01(){
//		常规方法
		ProcessEngineConfiguration pec = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
		
		ProcessEngine processEngine = pec.buildProcessEngine();
		System.out.println(processEngine);
		
//		简单方法
//		ProcessEngine processEngine1 = ProcessEngines.getDefaultProcessEngine();
//		System.out.println(processEngine1);
	}

}
