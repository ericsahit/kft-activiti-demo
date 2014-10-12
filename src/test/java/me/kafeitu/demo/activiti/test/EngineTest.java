package me.kafeitu.demo.activiti.test;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import me.kafeitu.demo.activiti.entity.oa.Leave;
import me.kafeitu.demo.activiti.service.activiti.WorkflowProcessDefinitionService;
import me.kafeitu.demo.activiti.service.oa.leave.LeaveManager;
import me.kafeitu.demo.activiti.service.oa.leave.LeaveWorkflowService;
import me.kafeitu.modules.test.spring.SpringTransactionalTestCase;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class EngineTest extends SpringTransactionalTestCase {
	
	@Autowired
	private LeaveWorkflowService leaveWorkflowService;

	@Autowired
	protected WorkflowProcessDefinitionService workflowProcessDefinitionService;
	
	@PersistenceContext
	EntityManager em;

	@Autowired
	private LeaveManager leaveManager;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEngine() {
		
	}
	
	public static void main(String[] args) {
//		ProcessEngine engine = ProcessEngineConfiguration
//				.createStandaloneProcessEngineConfiguration()
//				.buildProcessEngine();
//		
//		RepositoryService repoService = engine.getRepositoryService();
//		RuntimeService runtService = engine.getRuntimeService();
//		TaskService tkService = engine.getTaskService();
//		
//		repoService.createDeployment().addClasspathResource("FinancialReportProcess.bpmn20.xml").deploy();
//		
//		runtService.startProcessInstanceByKey("financialReport");
//		
//		List<Task> tasks = tkService.createTaskQuery().taskCandidateUser("kermit").list();
//		tasks = tkService.createTaskQuery().taskCandidateGroup("accountancy").list();
//		
//		//领取任务
//		//现在一个会计要认领这个任务。 认领以后，这个用户就会成为任务的执行人 ， 任务会从会计组的其他成员的任务列表中消失。 认领任务的代码如下所示
//		tkService.claim(tasks.get(0).getId(), "fozzie");
//		
//		//任务会进入认领任务人的个人任务列表中。
//		tasks = tkService.createTaskQuery().taskAssignee("fozzie").list();
//		
//		//现在会计可以开始进行财报的工作了。报告完成后， 他可以完成任务， 意味着任务所需的所有工作都完成了。
//		tkService.complete(tasks.get(0).getId());
		
	    // Create Activiti process engine
	    ProcessEngine processEngine = ProcessEngineConfiguration
	      .createStandaloneProcessEngineConfiguration()
	      .buildProcessEngine();

	    // Get Activiti services
	    RepositoryService repositoryService = processEngine.getRepositoryService();
	    RuntimeService runtimeService = processEngine.getRuntimeService();

	    // Deploy the process definition
	    repositoryService.createDeployment()
	      .addClasspathResource("FinancialReportProcess.bpmn20.xml")
	      .deploy();

	    // Start a process instance
	    String procId = runtimeService.startProcessInstanceByKey("financialReport").getId();

	    // Get the first task
	    TaskService taskService = processEngine.getTaskService();
	    List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("accountancy").list();
	    for (Task task : tasks) {
	      System.out.println("Following task is available for accountancy group: " + task.getName());

	      // claim it
	      taskService.claim(task.getId(), "fozzie");
	    }

	    // Verify Fozzie can now retrieve the task
	    tasks = taskService.createTaskQuery().taskAssignee("fozzie").list();
	    for (Task task : tasks) {
	      System.out.println("Task for fozzie: " + task.getName());

	      // Complete the task
	      taskService.complete(task.getId());
	    }

	    System.out.println("Number of tasks for fozzie: "
	            + taskService.createTaskQuery().taskAssignee("fozzie").count());

	    // Retrieve and claim the second task
	    tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
	    for (Task task : tasks) {
	      System.out.println("Following task is available for accountancy group: " + task.getName());
	      taskService.claim(task.getId(), "kermit");
	    }

	    // Completing the second task ends the process
	    for (Task task : tasks) {
	      taskService.complete(task.getId());
	    }

	    // verify that the process is actually finished
	    HistoryService historyService = processEngine.getHistoryService();
	    HistoricProcessInstance historicProcessInstance =
	      historyService.createHistoricProcessInstanceQuery().processInstanceId(procId).singleResult();
	    System.out.println("Process instance end time: " + historicProcessInstance.getEndTime());

			  		
	}

}
