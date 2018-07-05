package com.lj_2;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;

public class TestCurd {
	ProcessEngine ProcessEngine = ProcessEngines.getDefaultProcessEngine();
	
	//通过zip部署流程定义
	@Test
	public void testDeploy() {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/hello.zip");
		ZipInputStream zin = new ZipInputStream(in);
		Deployment deployment = ProcessEngine.getRepositoryService()
										.createDeployment()
										.addZipInputStream(zin)
										.name("hello")
										.deploy();

		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	
	//查询流程定义
	@Test
	public void testQueryPro() {
		List<ProcessDefinition> processDefinitionList = ProcessEngine.getRepositoryService()
										.createProcessDefinitionQuery()
//										查询条件
//										.processDefinitionId(arg0)
//										.processDefinitionKey()
										.processDefinitionKeyLike("%hello%")
//										排序条件
										.orderByDeploymentId().asc()
//										结果集
//										.count() //数量
//										.listPage(pageNo, pageSize) //分页查询
//										.singleResult() //查询单个结果
										.list();
		
		for(ProcessDefinition pro : processDefinitionList) {
			System.out.println(pro.getKey());
			System.out.println(pro.getDeploymentId());
			System.out.println(pro.getDiagramResourceName());
			System.out.println(pro.getId());
			System.out.println("-------------------------------------------");
		}
	}
}
