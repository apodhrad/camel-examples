package taskmanager;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;

import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycompany.Application;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, classes = Application.class)
@TestPropertySource(locations = "classpath:application.properties")
public class GreetingsTest {

	@Test
	public void testHelloGreeting() throws Exception {
		get("/greetings/hello/World").then().body(equalTo("Hello World"));
	}

}
