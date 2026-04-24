@Sprint1
Feature: Login Functionality with Invalid data
  Scenario: Invalid user login with data table
    Given User is on the login page
    When user tries login with following credentials:
    |  UserName | Password |
    | Admin     | admin1236|
    | testAdmin | admin789 |
    | testAdmin | admin8463|
    Then user should verifies error massage   "Invalid credentials"