Feature: Context Root of Trevorism
  In order to use the trevorism API, it must be available

  Scenario: Root of the API HTTPS
    Given the application is alive
    When I navigate to "https://www.trevorism.com/api"
    Then then a link to the help page is displayed

  Scenario: Ping HTTPS
    Given the application is alive
    When I ping the application deployed to "https://www.trevorism.com/api"
    Then pong is returned, to indicate the service is alive
