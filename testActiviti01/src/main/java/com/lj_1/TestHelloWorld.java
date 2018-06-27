package com.lj_1;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * 工作流入门
 * @author lujian
 * @create 2018年6月26日
 * @version 1.0
 */
public class TestHelloWorld {
	//获取流程引擎
	ProcessEngine ProcessEngine = ProcessEngines.getDefaultProcessEngine();
	
	//步骤1.部署流程定义
	@Test
	public void testDeploy() {
		Deployment deployment = ProcessEngine.getRepositoryService()//repositoryService和流程定义管理相关
										.createDeployment()
										.addClasspathResource("diagrams/helloWorld.bpmn")
										.addClasspathResource("diagrams/helloWorld.png")
										.name("helloWorld")
										.deploy();

		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	
	//步骤2.启动流程实例
	@Test
	public void testInstance() {
		String key = "helloWorld";
		ProcessInstance processInstance = ProcessEngine.getRuntimeService()//runtimeService和执行过程相关
										.startProcessInstanceByKey(key);

		System.out.println(processInstance.getId());
		System.out.println(processInstance.getProcessInstanceId());
		System.out.println(processInstance.getProcessDefinitionId());
	}
	
	//步骤3.查询任务
	@Test
	public void testQueryTask() {
		String user = "张三";
		List<Task> taskList = ProcessEngine.getTaskService()//taskService和任务相关
										.createTaskQuery()
										.taskAssignee(user)
										.list();
		
		if(taskList != null && taskList.size() > 0) {
			for(Task task : taskList) {
				System.out.println(task.getId());
				System.out.println(task.getAssignee());
				System.out.println(task.getName());
				System.out.println(task.getProcessDefinitionId());
				System.out.println(task.getProcessInstanceId());
			}
		}
	}
	
	//步骤4.完成任务
	@Test
	public void testFinishTask() {
		String taskId = "102";
		ProcessEngine.getTaskService().complete(taskId);
	}

	
	//接下来就一直循环执行步骤3和步骤4，直到所有任务完成，会自动结束该流程
	//..
}
