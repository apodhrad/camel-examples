package taskmanager;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;

import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import io.restassured.http.ContentType;

public class TaskManagerTest extends CamelBlueprintTestSupport {

	@Before
	public void initTaskManager() {
		delete("/taskmanager/tasks").andReturn();
	}

	@Test
	public void testGettingInitialTasks() throws Exception {
		get("/taskmanager/tasks").then().body("data.tasks.description",
				equalTo(Arrays.asList("Initial task", "Another task")));
	}

	@Test
	public void testAddingNewTask() throws Exception {
		given().contentType(ContentType.JSON).body("{\"description\": \"New task\"}").post("/taskmanager/tasks").then()
				.body("data.task.id", equalTo(3)).body("data.task.description", equalTo("New task"));
		get("/taskmanager/tasks").then().body("data.tasks.description",
				equalTo(Arrays.asList("Initial task", "Another task", "New task")));
	}

	@Test
	public void testDeletingNewTask() throws Exception {
		delete("/taskmanager/tasks/1").then().body("data", Matchers.isEmptyOrNullString());
		get("/taskmanager/tasks").then().body("data.tasks.description", equalTo(Arrays.asList("Another task")));
	}

	@Test
	public void testUpdatingNewTask() throws Exception {
		given().contentType(ContentType.JSON).body("{\"id\":\"2\", \"description\": \"Second task\"}")
				.put("/taskmanager/tasks").then().body("data.task.description", equalTo("Second task"));
		get("/taskmanager/tasks").then().body("data.tasks.description",
				equalTo(Arrays.asList("Initial task", "Second task")));
	}

	@Override
	protected String getBlueprintDescriptor() {
		return "OSGI-INF/blueprint/blueprint.xml";
	}

}
