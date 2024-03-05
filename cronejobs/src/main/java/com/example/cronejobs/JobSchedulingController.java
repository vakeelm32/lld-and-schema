package com.example.cronejobs;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/schedule")
public class JobSchedulingController {

	@Autowired
	private TaskSchedulingService taskSchedulingService;

	

	@PostMapping(path = "/taskdef", consumes = "application/json", produces = "application/json")
	public void scheduleATask(@RequestBody TaskDefinition taskDefinition) {
		TaskDefinitionBean taskDefinitionBean = new TaskDefinitionBean();
		taskDefinitionBean.setTaskDefinition(taskDefinition);
		taskSchedulingService.scheduleATask(UUID.randomUUID().toString(), taskDefinitionBean,
				taskDefinition.getCronExpression());
	}

	@GetMapping(path = "/remove/{jobid}")
	public void removeJob(@PathVariable String jobid) {
		taskSchedulingService.removeScheduledTask(jobid);
	}
}