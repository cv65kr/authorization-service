Feature: Check current application state

  Scenario: Successfully get actuator healthcheck
    When I send a "GET" request to "/uaa/actuator/health"
    Then Then the response status code should be 200
    And the JSON should be equal to:
      """
        {
           "status": "UP"
        }
      """

  Scenario: Failed get actuator info when user is not authorized
    When I send a "GET" request to "/uaa/actuator/info"
    Then Then the response status code should be 401