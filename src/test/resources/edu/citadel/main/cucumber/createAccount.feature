Feature: Account Creation

  Scenario: Client creates an account via POST /account endpoint
    Given the application is running
    When I send a POST request to the "/account" endpoint with the following JSON body:
    """
    {
        "username": "testuser{postFix}",
        "password": "Test@1234",
        "email": "test{postFix}@testExample.com"
    }
    """
    Then the response status code should be 201
    And the response should be in JSON format with the following fields:
    | user_id   |
    | username  |
    | email     |
    | created_on|
    | last_login|
    And I can store the account details for other scenarios
    And the account should exist in the database
