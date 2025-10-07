Feature: Application Info Can Be Retrieved
  Scenario: Client makes call to GET /info endpoint
    Given the application is running
    When I send a request to the "/info" endpoint
    Then the response status code should be 200