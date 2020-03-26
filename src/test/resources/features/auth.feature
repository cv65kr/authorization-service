Feature: Getting token

  Scenario: Successfully getting user token
    Given There is user registered with "test_user" username
    And There is not entries in Redis
    And There is provided header with "Authorization" key and value "Basic YnJvd3Nlcjo="
    And There is provided header with "Content-Type" key and value "application/x-www-form-urlencoded"
    When I send a "POST" request to "/uaa/oauth/token?grant_type=password&scope=ui&username=test_user&password=password"
    Then Then the response status code should be 200
    And the JSON should be equal to:
      """
        {
          "access_token": "${json-unit.matches:uuid}",
          "token_type": "bearer",
          "refresh_token": "${json-unit.matches:uuid}",
          "expires_in": "${json-unit.any-number}",
          "scope": "ui"
        }
      """

  Scenario: Failed getting user token when authorization token is not provided
    Given There is user registered with "test_user" username
    And There is provided header with "Content-Type" key and value "application/x-www-form-urlencoded"
    When I send a "POST" request to "/uaa/oauth/token?grant_type=password&scope=ui&username=test_user&password=password"
    Then Then the response status code should be 401
    And the JSON should be equal to:
      """
        {
          "error": "Unauthorized",
          "message": "Unauthorized",
          "path": "/uaa/oauth/token",
          "status": 401,
          "timestamp": "${json-unit.matches:timestamp}yyyy-MM-dd'T'HH:mm:ss.SSSZ"
        }
      """

  Scenario: Failed getting user token when user is not exist
    Given There is provided header with "Authorization" key and value "Basic YnJvd3Nlcjo="
    And There is provided header with "Content-Type" key and value "application/x-www-form-urlencoded"
    When I send a "POST" request to "/uaa/oauth/token?grant_type=password&scope=ui&username=test_user&password=password"
    Then Then the response status code should be 400
    And the JSON should be equal to:
      """
        {
          "error": "invalid_grant",
          "error_description": "Bad credentials"
        }
      """

  Scenario: Successfully getting service token
    Given There is not entries in Redis
    And There is provided header with "Authorization" key and value "Basic c2VydmljZTpzZXJ2aWNlLXBhc3N3b3Jk"
    And There is provided header with "Content-Type" key and value "application/x-www-form-urlencoded"
    When I send a "POST" request to "/uaa/oauth/token?grant_type=client_credentials"
    Then Then the response status code should be 200
    And the JSON should be equal to:
      """
        {
          "access_token": "${json-unit.matches:uuid}",
          "token_type": "bearer",
          "expires_in": "${json-unit.any-number}",
          "scope": "server"
        }
      """