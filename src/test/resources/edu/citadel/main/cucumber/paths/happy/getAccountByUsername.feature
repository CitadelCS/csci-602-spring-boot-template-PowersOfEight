Feature: Get Account By Username

  Scenario: Client retrieves a previously-created account via GET /account/username/{username} endpoint
    Given the application is running
    Given The created account exists
    When I send a GET request to the "/account/username/{username}" endpoint
    Then the response status code should be 200
    And the response should be in JSON format with the following fields:
      | user_id    |
      | username   |
      | email      |
      | created_on |
      | last_login |
    And the response should match the account details returned from the account creation
    And I can delete the account from the database
