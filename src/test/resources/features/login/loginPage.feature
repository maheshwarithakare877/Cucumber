@Sprint1
Feature: Login Functionality with valid user
  Background:
    Given User is on the login page

  Scenario: Login with Valid User Test

    When User enter username as "Admin" and password as "admin123"
    Then User verified it redirected to the dashboard

   When user should see following Dashbord menu items:
    |Admin         |
    |PIM           |
    |Leave         |
    |Time          |
    |Recruitment   |
    |My Info       |
    |Performance   |
    |Dashboard     |
    |Directory     |
    |Maintenance   |
    |Claim         |
    |Buzz          |