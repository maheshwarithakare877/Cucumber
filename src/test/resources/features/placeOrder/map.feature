@Sprint1
Feature:  Login with Invalid User
  Scenario: Login with Invalid User
    Given User is on the login page
    When User enters below creds as Map:
      |  UserName | Password |
      | Admin     | admin1236|
      | testAdmin | admin789 |
      | testAdmin | admin8463|
    Then user should verifies error massage   "Invalid credentials"
