Feature: Health Check Endpoint

  Scenario: Client makes call to GET /health endpoint
    Given the application is running
    When I send a request to the "/health" endpoint
    Then the response status code should be 200
    And the response should be in JSON format with the following fields:
      | status        |