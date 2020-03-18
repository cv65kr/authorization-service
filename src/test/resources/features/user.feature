Feature: Register user accounts

  Scenario: Successfully register user account
    Given There is access token for service provided in the request header
    When I send a authorized "POST" request to "/uaa/users" with body:
      """
        {
          "username": "test_user",
          "password": "test_password"
        }
      """
    Then Then the response status code should be 200

  Scenario: Fail to register user because username already used
    Given There is user registered with "test_user" username
    And There is access token for service provided in the request header
    When I send a authorized "POST" request to "/uaa/users" with body:
      """
        {
          "username": "test_user",
          "password": "test_password"
        }
      """
    Then Then the response status code should be 409

  Scenario: Fail to register user because username is empty
    Given There is access token for service provided in the request header
    When I send a authorized "POST" request to "/uaa/users" with body:
      """
        {
          "username": "",
          "password": "test_password"
        }
      """
    Then Then the response status code should be 400

  Scenario: Fail to register user because username not provided
    Given There is access token for service provided in the request header
    When I send a authorized "POST" request to "/uaa/users" with body:
      """
        {
          "password": "test_password"
        }
      """
    Then Then the response status code should be 400

  Scenario: Fail to register user because token is not provided
    When I send a "POST" request to "/uaa/users" with body:
      """
        {
          "username": "test_user",
          "password": "test_password"
        }
      """
    Then Then the response status code should be 401