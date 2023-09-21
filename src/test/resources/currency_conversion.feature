@smoke
Feature: Currency Conversion scenarios

  @TC-1
  Scenario Outline: Verify that one of buy and sell inputboxes are emptied when user starts to type in other one.
    Given user is on the currency conversion page
    When user enters any value in "<input_box>"
    Then the "<other_input>" should be emptied
    Examples:
      | input_box | other_input |
      | buy       | sell        |
      | sell      | buy         |

  @TC-2
  Scenario: Verify that when user selects a country from countries dropdown in the footer,
  its currency should be selected after page is loaded
    Given user is on the currency conversion page
    #When user selects any country from countries dropdown
    #Then The currency of that country should come as selected after page is loaded

  @TC-3
  Scenario: Verify that difference between Paysera Amount and Swedbank Amount is displayed correctly
    Given user is on the currency conversion page
    And user selects any currency from sell dropdown
    And user selects any currency from buy dropdown
    When user enters a valid value in sell inputbox
    And user clicks on filter button
    Then difference in parentheses should be correct