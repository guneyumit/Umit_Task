package task.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import task.utility.CurrencyCodesExcelReader;
import task.utility.WebUtils;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;
import static task.utility.Driver.driver;
import static task.utility.WebUtils.*;

public class ConverterPage {

    public ConverterPage() {
        PageFactory.initElements(driver(), this);
    }

    public static String selectedCountry;
    public static String expectedCurrencyCode;

    public static String selectedCurrencyCodeSale;

    public static String selectedCurrencyCodeBuy;

    private final Random random = new Random();

    private int indexForSelectedCurrencyCodeForSell;
    private int sellAmount;

    @FindBy(xpath = "//label[text()='Sell']/parent::div//span[contains(@data-ng-bind,'select.selected')]")
    private WebElement selectedCurrencyTextForSell;

    @FindBy(xpath = "//label[text()='Buy']/parent::div//span[contains(@data-ng-bind,'select.selected')]")
    private WebElement selectedCurrencyTextForBuy;

    @FindBy(css = "div[data-ng-model='currencyExchangeVM.filter.from']")
    private WebElement selectCurrencyForSell;

    @FindBy(css = "div[data-ng-model='currencyExchangeVM.filter.to']")
    private WebElement selectCurrencyForBuy;

    @FindBy(css = "div.ui-select-choices-row")
    private List<WebElement> currencyCodeElementsInDropdown;

    @FindBy(css = "span.js-localization-popover")
    private WebElement countryButton;

    @FindBy(id = "countries-dropdown")
    private WebElement countriesDropdown;

    @FindBy(xpath = "//button[@id='countries-dropdown']/parent::div")
    private WebElement selectedCountryElement;

    @FindBy(css = "ul[aria-labelledby='countries-dropdown']>li>a")
    private List<WebElement> countryNameElements;

    @FindBy(css = "ul[aria-labelledby='countries-dropdown']")
    private WebElement countryNamesHolderElement;

    @FindBy(css = "input[data-ng-model*='from_amount']")
    private WebElement sellInput;

    @FindBy(css = "input[data-ng-model*='to_amount']")
    private WebElement buyInput;

    @FindBy(css = "button[data-ng-click='currencyExchangeVM.filterExchangeRates()']")
    private WebElement filterBtn;

    @FindBy(css = "td[data-title='Paysera rate'] span.ng-binding")
    private WebElement payseraAmountElement;

    @FindBy(css = "td[data-title='Swedbank amount'] span.ng-binding")
    private WebElement swedbankAmountElement;

    @FindBy(css = "td[data-title='Swedbank amount'] span.ng-binding.other-bank-loss")
    private WebElement differenceUnderSwedbankAmountElement;

    public void goToPage() {
        driver().get("https://www.paysera.lt/v2/en-LT/fees/currency-conversion-calculator#/");
    }

    public void enterValueInBuyOrSellInputBox(String boxName) {
        switch (boxName.toLowerCase()) {
            case "buy":
                scrollToElement(buyInput);
                buyInput.sendKeys("1");
                break;
            case "sell":
                scrollToElement(sellInput);
                sellInput.sendKeys("1");
                break;
        }
    }

    public void verifyRelatedInputBoxEmpty(String boxName) {
        WebUtils.waitForMilliSeconds(500);
        switch (boxName.toLowerCase()) {
            case "buy":
                scrollToElement(buyInput);
                assertEquals("", buyInput.getAttribute("value"));
                break;
            case "sell":
                scrollToElement(sellInput);
                assertEquals("", sellInput.getAttribute("value"));
                break;
        }
    }

    public void selectAnyCountry() {
        scrollToElement(countryButton);
        countryButton.click();
        countriesDropdown.click();
        waitForVisibility(countryNamesHolderElement, 3);
        int randomCountryIndex = new Random().nextInt(countryNameElements.size() - 1);
        selectedCountry = countryNameElements.get(randomCountryIndex).getText().trim();
        System.out.println("randomCountryIndex = " + randomCountryIndex);
        System.out.println("selectedCountry = " + selectedCountry);
        expectedCurrencyCode = CurrencyCodesExcelReader.getCurrencyCode(selectedCountry);
        countryNameElements.get(randomCountryIndex).click();
        waitFor(1);
        waitForPageToLoad(10);

    }

    public void verifyExpectedCurrencyCodeIsDisplayedAsSelected() {
        scrollToElement(selectedCurrencyTextForSell);
        waitFor(3);
        System.out.println("Expected currency code : " + expectedCurrencyCode);
        System.out.println("Actual currency code : " + selectedCurrencyTextForSell.getText().trim());
        assertEquals(expectedCurrencyCode, selectedCurrencyTextForSell.getText().trim());
        System.out.println("*-------*---------*----------*---------*");
    }

    public void waitUntilConversionTableLoaded() {
        WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.attributeToBe(sellInput, "value", "100"));
    }

    public void selectAnyCurrencyCodeFromSellDropdown() {
        selectCurrencyForSell.click();
        waitFor(1);

        indexForSelectedCurrencyCodeForSell = random.nextInt(currencyCodeElementsInDropdown.size() - 1) + 1;
        selectedCurrencyCodeSale = currencyCodeElementsInDropdown.get(indexForSelectedCurrencyCodeForSell).findElement(By.cssSelector("span>span")).getText().trim();
        currencyCodeElementsInDropdown.get(indexForSelectedCurrencyCodeForSell).click();
        waitForMilliSeconds(500);


    }

    public void selectAnyCurrencyCodeFromBuyDropdown() {
        selectCurrencyForBuy.click();
        waitFor(1);
        while (true) {
            int randomIndex = random.nextInt(currencyCodeElementsInDropdown.size() - 1) + 1;
            if (randomIndex != indexForSelectedCurrencyCodeForSell && !currencyCodeElementsInDropdown.get(randomIndex).getText().trim().equalsIgnoreCase(selectedCurrencyCodeSale)) {
                currencyCodeElementsInDropdown.get(randomIndex).click();
                break;
            }
        }
        waitForMilliSeconds(500);
    }

    public void enterValidValueInSellInputBox() {
        sellAmount = random.nextInt(101);
        sellInput.clear();
        sellInput.sendKeys(sellAmount + "");
        waitForMilliSeconds(500);


    }

    public void clickFilterBtn() {
        filterBtn.click();
        waitFor(5);
    }

    public void verifyDifferenceIsCorrect() {
        double swedbankAmount = Double.parseDouble(swedbankAmountElement.getText().trim());
        double payseraAmount = Double.parseDouble(payseraAmountElement.getText().trim());
        double expectedDifference = swedbankAmount - payseraAmount;
        double actualDifference = Double.parseDouble(differenceUnderSwedbankAmountElement.getText().trim().replace("(", "").replace(")", ""));
        assertEquals(expectedDifference, actualDifference, 2);
    }
}
