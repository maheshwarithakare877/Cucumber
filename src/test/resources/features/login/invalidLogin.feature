@Sprint1
Feature: Login with Invalid User
Background:
  Given User is on the login page

  Scenario Outline:Login with InValid User Test

    When User enter username as "<username>" and password as "<password>"
    Then User verified it error message should be "Invalid credentials"



    
    Examples:
      | username | password |
      | Admin    | ghtyujnn |
      | sadsa    | admin123 |
      | asdas    | asdsa    |
                                 