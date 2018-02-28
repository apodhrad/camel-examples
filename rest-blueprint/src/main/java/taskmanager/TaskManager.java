package taskmanager;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {

	private List<Task> tasks;
	private int indexSequence;

	public TaskManager() {
		tasks = new ArrayList<>();

		Task t1 = new Task();
		t1.setDescription("Initial task");
		Task t2 = new Task();
		t2.setDescription("Another task");

		addTask(t1);
		addTask(t2);
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public Task getTask(int id) {
		return tasks.stream().filter(task -> task.getId() == id).findFirst().orElse(new Task());
	}

	public Task addTask(Task task) {
		task.setId(indexSequence++);
		tasks.add(task);
		return task;
	}

	public Task updateTask(Task task) {
		Task foundTask = getTask(task.getId());
		foundTask.setDescription(task.getDescription());
		return foundTask;
	}

	public void deleteTask(int id) {
		tasks.remove(getTask(id));
	}
}
