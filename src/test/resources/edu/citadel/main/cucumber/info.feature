Feature: Application Info Retrieval

  Scenario: Client makes call to GET /info endpoint
    Given the application is running
    When I send a GET request to the "/info" endpoint
    Then the response status code should be 200
    And the response should be in JSON format with the following fields:
      | name          |
      | description   |
      | version       |