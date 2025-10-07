package edu.citadel.main.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions extends CucumberSpringConfiguration {
    @Given("the application is running")
    public void the_application_is_running() {
        System.out.println("the application is running");
    }

    @When("I send a GET request to the {string} endpoint")
    public void i_send_a_get_request_to_the_endpoint(String endpoint) {
        System.out.println("I send a GET request to the " + endpoint + " endpoint");
    }

    @Then("the status code should be {int}")
    public void the_status_code_should_be(Integer int1) {
        System.out.println("the status code should be " + int1);
    }
}
