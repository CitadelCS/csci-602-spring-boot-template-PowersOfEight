Feature: Find Account By an ID that does not exist

  Scenario: Client attempts to retrieve account via GET /account/{user_id} endpoint
    Given the application is running
    Given The created account exists
    When I send a GET request to the "/account/-1" endpoint
    Then the response status code should be 404
#    And the response should be in JSON format with the following fields:
#      | user_id    |
#      | username   |
#      | email      |
#      | created_on |
#      | last_login |
#    And the response should match the account details returned from the account creation
