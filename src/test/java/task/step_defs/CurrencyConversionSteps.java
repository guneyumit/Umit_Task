package task.step_defs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import task.pages.ConverterPage;

public class CurrencyConversionSteps {

    ConverterPage converterPage = new ConverterPage();

    @Given("user is on the currency conversion page")
    public void user_is_on_the_currency_conversion_page() {
        converterPage.goToPage();
    }


    @When("user enters any value in {string}")
    public void user_enters_any_value_in(String boxName) {
        converterPage.enterValueInBuyOrSellInputBox(boxName);
    }
    @Then("the {string} should be emptied")
    public void the_should_be_emptied(String boxName) {
        converterPage.verifyRelatedInputBoxEmpty(boxName);
    }


    @When("user selects any country from countries dropdown")
    public void userSelectsAnyCountryFromCountriesDropdown() {
        converterPage.selectAnyCountry();
    }

    @Then("The currency of that country should come as selected after page is loaded")
    public void theCurrencyOfThatCountryShouldComeAsSelectedAfterPageIsLoaded() {
        converterPage.verifyExpectedCurrencyCodeIsDisplayedAsSelected();
    }

    @When("user selects any currency from sell dropdown")
    public void userSelectsAnyCurrencyFromSellDropdown() {
        converterPage.waitUntilConversionTableLoaded();
        converterPage.selectAnyCurrencyCodeFromSellDropdown();

    }

    @And("user enters a valid value in sell inputbox")
    public void userEntersAValidValueInSellInputbox() {
        converterPage.enterValidValueInSellInputBox();
    }

    @And("user selects any currency from buy dropdown")
    public void userSelectsAnyCurrencyFromBuyDropdown() {
        converterPage.selectAnyCurrencyCodeFromBuyDropdown();
    }

    @And("user clicks on filter button")
    public void userClicksOnFilterButton() {
        converterPage.clickFilterBtn();
    }

    @Then("difference in parentheses should be correct")
    public void differenceInParenthesesShouldBeCorrect() {
        converterPage.verifyDifferenceIsCorrect();
    }
}
