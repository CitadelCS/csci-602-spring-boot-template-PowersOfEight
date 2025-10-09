Feature: Find Account By ID

  Scenario: Client retrieves a previously-created account via GET /account/{user_id} endpoint
    Given the application is running
    Given The created account exists
    When I send a GET request to the "/account/{user_id}" endpoint
    Then the response status code should be 200
    And the response should be in JSON format with the following fields:
      | user_id    |
      | username   |
      | email      |
      | created_on |
      | last_login |
    And the response should match the account details returned from the account creation
