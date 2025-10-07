package edu.citadel.main.cucumber;

import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StepDefinitions {

    @LocalServerPort
    private int port;

    private ResponseEntity<String> response;

    private final RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Given("the application is running")
    public void the_application_is_running() {
        System.out.println("the application is running on port " + port);
    }

    @When("I send a request to the {string} endpoint")
    public void i_send_a_request_to_the_endpoint(String string) {
        // Write code here that turns the phrase above into concrete actions
        String baseUrl = "http://localhost:" + port;
        String url = baseUrl + string;
        System.out.println("Sending request to URL: " + url);
        response = restTemplate.getForEntity(url, String.class);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        int actualStatusCode = response.getStatusCode().value();
        System.out.println("Actual status code: " + actualStatusCode);
        org.junit.jupiter.api.Assertions.assertEquals(int1.intValue(), actualStatusCode);
    }

    @And("the response should be in JSON format with the following fields:")
    public void the_response_should_be_in_json_format(DataTable dataTable) throws JsonProcessingException {

       JsonNode node = objectMapper.readTree(response.getBody());

       dataTable.asList().forEach(field -> {
           assertTrue(node.has(field), "Response JSON missing field: " + field);
       });
    }

}
