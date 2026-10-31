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

  Scenario: The sitemap lists the pages
    Given the application is alive
    When a crawler requests "/sitemap.xml"
    Then the response status is 200
    And the page contains "<loc>https://www.trevorism.com/docs</loc>"
