package com.example.cronejobs;

import java.util.Date;


public class TaskDefinitionBean implements Runnable {

	private TaskDefinition taskDefinition;

	@Override
	public void run() {
		System.out.println(
				Thread.currentThread().getName() + "| action: " + taskDefinition.getActionType() + "| " + new Date());
	}

	public TaskDefinition getTaskDefinition() {
		return taskDefinition;
	}

	public void setTaskDefinition(TaskDefinition taskDefinition) {
		this.taskDefinition = taskDefinition;
	}
}