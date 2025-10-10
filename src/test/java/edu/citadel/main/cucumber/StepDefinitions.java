package edu.citadel.main.cucumber;

import edu.citadel.dal.AccountRepository;
import edu.citadel.dal.model.Account;
import io.cucumber.core.exception.CucumberException;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StepDefinitions {

    // Initialize logger
    private static final Logger LOGGER = LoggerFactory.getLogger(StepDefinitions.class);

    @LocalServerPort
    private int port;

    @Autowired
    private CucumberTestContext cucumberTestContext;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private AccountRepository accountRepository;

    private ResponseEntity<String> response;

    private final RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String baseUrl;



    @Given("the application is running")
    public void the_application_is_running() {
        assertTrue(context.getBeanDefinitionCount() > 0, "Application context should have beans");
        LOGGER.debug("Application context loaded with {} beans", context.getBeanDefinitionCount());
        LOGGER.debug("The application is running on port {}", port);
        baseUrl = "http://localhost:" + port;
    }

    @Given("The created account exists")
    public void theCreatedAccountExists() {
        assertTrue(cucumberTestContext.containsKey("createdAccount"), "No account found in test context");
    }

    @When("I send a GET request to the {string} endpoint")
    public void i_send_a_get_request_to_the_endpoint(String path) {
        // Replace placeholders in path
        if (cucumberTestContext.containsKey("createdAccount")) {
            Account createdAccount = cucumberTestContext.get("createdAccount", Account.class);
            path = path.replace("{user_id}", String.valueOf(createdAccount.getUser_id()));
            path = path.replace("{username}", createdAccount.getUsername());
        }
        String url = baseUrl + path;
        LOGGER.debug("Sending request to URL: " + url);
        try{
            response = restTemplate.getForEntity(url, String.class);
        } catch (RestClientException e){
            if (e instanceof HttpClientErrorException){
                response = ResponseEntity
                            .status(((HttpClientErrorException) e).getStatusCode())
                            .body(((HttpClientErrorException) e).getResponseBodyAsString());
            } else {
                throw e;
            }
        }
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer int1) {
        int actualStatusCode = response.getStatusCode().value();
        LOGGER.debug("Actual status code: {}", actualStatusCode);
        org.junit.jupiter.api.Assertions.assertEquals(int1.intValue(), actualStatusCode);
    }

    @And("the response should be in JSON format with the following fields:")
    public void the_response_should_be_in_json_format(DataTable dataTable) throws JsonProcessingException {
       JsonNode node = objectMapper.readTree(response.getBody());
       dataTable.asList().forEach(field -> {
           assertTrue(node.has(field), "Response JSON missing field: " + field);
       });
    }

    @When("I send a POST request to the {string} endpoint with the following JSON body:")
    public void iSendAPOSTRequestToTheEndpointWithTheFollowingJSONBody(String path, String jsonBody) {
        // Replace placeholders in JSON body
        String postFix = String.valueOf(new Random().nextInt(10000));
        jsonBody = jsonBody.replace("{postFix}", postFix);
        String url = baseUrl + path;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        response = restTemplate.postForEntity(url, request, String.class);
    }

    @And("the account should exist in the database")
    public void theAccountShouldExistInTheDatabase() {
        if (cucumberTestContext.containsKey("createdAccount")) {
            Account created = cucumberTestContext.get("createdAccount", Account.class);
            Optional<Account> dbAccount = accountRepository.findById(created.getUser_id());
            assertTrue(dbAccount.isPresent(), "Account should exist in the database");
            assertEquals(created.getUser_id(), dbAccount.get().getUser_id(), "Database account ID should match created account");
            assertEquals(created.getEmail(), dbAccount.get().getEmail(), "Database account username should match created account");
            assertEquals(created.getPassword(), dbAccount.get().getPassword(), "Database account email should match created account");
        } else {
            throw new CucumberException("No account found in test context to verify in the database");
        }
    }

    @And("I can store the account details for other scenarios")
    public void iCanStoreTheAccountDetailsForOtherScenarios() throws JsonProcessingException {
        cucumberTestContext.put("createdAccount", objectMapper.readValue(response.getBody(), Account.class));
        assertTrue(cucumberTestContext.containsKey("createdAccount"), "Account should exist in the context");
    }

    @And("I can delete the account from the database")
    public void iCanDeleteTheAccountFromTheDatabase() {
        if (cucumberTestContext.containsKey("createdAccount")) {
            Account created = cucumberTestContext.get("createdAccount", Account.class);
            accountRepository.deleteById(created.getUser_id());
            LOGGER.debug("Deleted account with ID: {}", created.getUser_id());
        } else {
            throw new CucumberException("No account found in test context to delete from the database");
        }
        LOGGER.debug("Cleaned up test accounts from the database");
    }


    @And("the response should match the account details returned from the account creation")
    public void theResponseShouldMatchTheAccountDetailsReturnedFromTheAccountCreation() throws JsonProcessingException {
        if (cucumberTestContext.containsKey("createdAccount")) {
            Account created = cucumberTestContext.get("createdAccount", Account.class);
            Account resultAccount = objectMapper.readValue(response.getBody(), Account.class);
            assertEquals(created.getUser_id(), resultAccount.getUser_id(), "Returned account ID should match created account");
            assertEquals(created.getEmail(), resultAccount.getEmail(), "Returned account username should match created account");
            assertEquals(created.getPassword(), resultAccount.getPassword(), "Returned account email should match created account");
        } else {
            throw new CucumberException("No account found in test context to verify in the response");
        }
    }
}
