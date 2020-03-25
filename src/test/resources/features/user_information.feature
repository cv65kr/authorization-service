Feature: Getting user data

  Scenario: Successfully getting user data
    Given There is user registered with "test_user" username
    And There is access token for username "test_user" provided in the request header
    When I send a authorized "GET" request to "/uaa/users/current"
    Then Then the response status code should be 200
    And the JSON should be equal to:
      """
        {
          "authorities": [],
          "details": {
              "remoteAddress": "127.0.0.1",
              "sessionId": null,
              "tokenValue": "${json-unit.matches:uuid}",
              "tokenType": "Bearer",
              "decodedDetails": null
          },
          "authenticated": true,
          "userAuthentication": {
              "authorities": [],
              "details": {
                  "grant_type": "password",
                  "scope": "ui",
                  "username": "test_user"
              },
              "authenticated": true,
              "principal": {
                  "uuid": "${json-unit.matches:uuid}",
                  "username": "test_user",
                  "password": "${json-unit.ignore}",
                  "enabled": true,
                  "accountNonExpired": true,
                  "accountNonLocked": true,
                  "credentialsNonExpired": true,
                  "authorities": null
              },
              "credentials": null,
              "name": "test_user"
          },
          "credentials": "",
          "principal": {
              "uuid": "${json-unit.matches:uuid}",
              "username": "test_user",
              "password": "${json-unit.ignore}",
              "enabled": true,
              "accountNonExpired": true,
              "accountNonLocked": true,
              "credentialsNonExpired": true,
              "authorities": null
          },
          "oauth2Request": {
              "clientId": "browser",
              "scope": [
                  "ui"
              ],
              "requestParameters": {
                  "grant_type": "password",
                  "scope": "ui",
                  "username": "test_user"
              },
              "resourceIds": [],
              "authorities": [],
              "approved": true,
              "refresh": false,
              "redirectUri": null,
              "responseTypes": [],
              "extensions": {},
              "grantType": "password",
              "refreshTokenRequest": null
          },
          "clientOnly": false,
          "name": "test_user"
        }
      """

  Scenario: Fail to get user data because token is not provided
    Given There is user registered with "test_user" username
    When I send a "GET" request to "/uaa/users/current"
    Then Then the response status code should be 401
    And the JSON should be equal to:
      """
        {
            "error": "unauthorized",
            "error_description": "Full authentication is required to access this resource"
        }
      """