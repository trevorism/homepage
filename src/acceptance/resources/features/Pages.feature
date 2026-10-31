Feature: Pages crawlers can read
  In order to be found through search and shared links, every page must be readable without JavaScript

  Scenario: A crawler reads a page's content
    Given the application is alive
    When a crawler requests "/docs"
    Then the response status is 200
    And the page contains "Trevorism Auth service"
    And the page is canonical at "https://www.trevorism.com/docs"

  Scenario: Unknown pages are not found
    Given the application is alive
    When a crawler requests "/no-such-page"
    Then the response status is 404

  Scenario: App pages load but stay out of the index
    Given the application is alive
    When a crawler requests "/change/some-tenant-guid"
    Then the response status is 200
    And the page is not indexed

  Scenario: The header bar is laid out before JavaScript runs
    Given the application is alive
    When a crawler requests "/"
    Then the page's stylesheet carries the header bar's styles

  Scenario: Visitors can request a free assessment
    Given the application is alive
    When a crawler requests "/assessment"
    Then the response status is 200
    And the page contains "Get a free assessment"
    And the page contains "Only your email is required"

  Scenario: The retired contact page points to the assessment
    Given the application is alive
    When a crawler requests "/contact" without following redirects
    Then the response status is 301
    And it redirects to "/assessment"

  Scenario: An assessment request without a valid email is refused
    Given the application is alive
    When an assessment request without a valid email is submitted
    Then the response status is 400

  Scenario: The sitemap lists the pages
    Given the application is alive
    When a crawler requests "/sitemap.xml"
    Then the response status is 200
    And the page contains "<loc>https://www.trevorism.com/docs</loc>"
    And the page contains "<loc>https://www.trevorism.com/assessment</loc>"
    And the page contains "<loc>https://www.trevorism.com/ai</loc>"
