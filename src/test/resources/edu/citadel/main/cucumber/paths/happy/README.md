# Happy Path Cucumber Integration Tests

* This directory contains Cucumber `feature` files that define a happy path for the Accounts features.
* Each `feature` file is designed to be run in sequence, building on the state established by the previous tests.
* The tests cover the following scenarios:
    - User registration (Creating an account)
    - Finding an account by `user_id`
    - Finding an account by `username`
* At the end of the last test, the account created in the first test will be removed from the database to ensure a clean state.
* These tests are intended to verify that the core functionality of the Accounts features works as expected in the environment.
