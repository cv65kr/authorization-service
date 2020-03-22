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
    And the JSON should be equal to:
      """
        {
           "error": "Conflict",
           "message": "user already exists: test_user",
           "path": "/uaa/users",
           "status": 409,
           "timestamp": "${json-unit.matches:timestamp}yyyy-MM-dd'T'HH:mm:ss.SSSZ"
        }
      """

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
    And the JSON should be equal to:
      """
        {
            "timestamp": "${json-unit.matches:timestamp}yyyy-MM-dd'T'HH:mm:ss.SSSZ",
            "status": 400,
            "error": "Bad Request",
            "errors": [
                {
                    "codes": [
                        "NotEmpty.signUpDto.username",
                        "NotEmpty.username",
                        "NotEmpty.java.lang.String",
                        "NotEmpty"
                    ],
                    "arguments": [
                        {
                            "codes": [
                                "signUpDto.username",
                                "username"
                            ],
                            "arguments": null,
                            "defaultMessage": "username",
                            "code": "username"
                        }
                    ],
                    "defaultMessage": "must not be empty",
                    "objectName": "signUpDto",
                    "field": "username",
                    "rejectedValue": "",
                    "bindingFailure": false,
                    "code": "NotEmpty"
                }
            ],
            "message": "Validation failed for object='signUpDto'. Error count: 1",
            "path": "/uaa/users"
        }
      """

  Scenario: Fail to register user because username not provided
    Given There is access token for service provided in the request header
    When I send a authorized "POST" request to "/uaa/users" with body:
      """
        {
          "password": "test_password"
        }
      """
    Then Then the response status code should be 400
    And the JSON should be equal to:
      """
        {
            "timestamp": "${json-unit.matches:timestamp}yyyy-MM-dd'T'HH:mm:ss.SSSZ",
            "status": 400,
            "error": "Bad Request",
            "errors": [
                {
                    "codes": [
                        "NotEmpty.signUpDto.username",
                        "NotEmpty.username",
                        "NotEmpty.java.lang.String",
                        "NotEmpty"
                    ],
                    "arguments": [
                        {
                            "codes": [
                                "signUpDto.username",
                                "username"
                            ],
                            "arguments": null,
                            "defaultMessage": "username",
                            "code": "username"
                        }
                    ],
                    "defaultMessage": "must not be empty",
                    "objectName": "signUpDto",
                    "field": "username",
                    "rejectedValue": null,
                    "bindingFailure": false,
                    "code": "NotEmpty"
                },
                {
                    "codes": [
                        "NotNull.signUpDto.username",
                        "NotNull.username",
                        "NotNull.java.lang.String",
                        "NotNull"
                    ],
                    "arguments": [
                        {
                            "codes": [
                                "signUpDto.username",
                                "username"
                            ],
                            "arguments": null,
                            "defaultMessage": "username",
                            "code": "username"
                        }
                    ],
                    "defaultMessage": "must not be null",
                    "objectName": "signUpDto",
                    "field": "username",
                    "rejectedValue": null,
                    "bindingFailure": false,
                    "code": "NotNull"
                }
            ],
            "message": "Validation failed for object='signUpDto'. Error count: 2",
            "path": "/uaa/users"
        }
      """

  Scenario: Fail to register user because token is not provided
    When I send a "POST" request to "/uaa/users" with body:
      """
        {
          "username": "test_user",
          "password": "test_password"
        }
      """
    Then Then the response status code should be 401
    And the JSON should be equal to:
      """
        {
            "error": "unauthorized",
            "error_description": "Full authentication is required to access this resource"
        }
      """