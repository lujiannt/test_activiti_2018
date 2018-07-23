package com.lj_2;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
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
	
	//根据部署id删除流程定义
	@Test
	public void testDelPro() {
		//只能删除掉没有启动的流程定义
//		ProcessEngine.getRepositoryService()
//					.deleteDeployment("301");
		
		//级联删除，不管是否启动都删除（默认是false）
		ProcessEngine.getRepositoryService()
			.deleteDeployment("201", true);
	}
	
	//获取工作流图片并保存到本地
	@Test
	public void testSaveImg() throws Exception{
		String deployId = "101";
		//查询该部署id下所有资源文件
		List<String> list = ProcessEngine.getRepositoryService()
								.getDeploymentResourceNames(deployId);
		
		String fileName = "";
		for(String resName : list) {
			if(resName.indexOf("png") > 0) {
				fileName = resName;
			}
		}
			
		//直接获得资源文件转成的流
		InputStream is = ProcessEngine.getRepositoryService()
							.getResourceAsStream(deployId, fileName);
		
		FileUtils.copyInputStreamToFile(is, new File("F://test/"+fileName));
	}
	
	//查询出所有流程定义的最新版本
	@Test
	public void testQueryNewest() throws Exception{
		List<ProcessDefinition> list = ProcessEngine.getRepositoryService()
								.createProcessDefinitionQuery()
								.orderByProcessDefinitionVersion()
								.asc()
								.list();
		
		Map<String, Object> map = new LinkedHashMap<>();
		for(ProcessDefinition processDefinition : list) {
			map.put(processDefinition.getKey(), processDefinition);
		}
			
		Set<String> keys = map.keySet();
		for(String key : keys) {
			System.out.println("key = " + key + " version = " + ((ProcessDefinition)map.get(key)).getVersion());
		}
	}
	
	//删除该key下所有流程定义
	@Test
	public void testDelAll() throws Exception{
		List<ProcessDefinition> list = ProcessEngine.getRepositoryService()
								.createProcessDefinitionQuery()
								.list();
		
		for(ProcessDefinition processDefinition : list) {
			String deployId = processDefinition.getDeploymentId();
			
			ProcessEngine.getRepositoryService()
			.deleteDeployment(deployId, true);
		}
			
	}
}
