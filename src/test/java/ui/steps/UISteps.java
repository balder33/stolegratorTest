package ui.steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;

public class UISteps {
    @Given("^user open authorization page$")
    public void openUrl(){
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        open("http://test-app.d6.dev.devcaz.com/admin/login");
    }

    @Given("^user authorized in the admin panel$")
    public void authInAdminPanel(){
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        open("http://test-app.d6.dev.devcaz.com/admin/login");
        $(byName("UserLogin[username]")).shouldBe(Condition.visible).sendKeys("admin1");
        $(byName("UserLogin[password]")).shouldBe(Condition.visible).sendKeys("[9k<k8^z!+$$GkuP");
        $(byName("yt0")).shouldBe(Condition.visible).click();
    }

    @Given("^user went to the Players page$")
    public void wentToPlayersPage(){
        $(byXpath("//*[@id=\"nav\"]/li[8]/a")).shouldBe(Condition.visible).click();
        $(byCssSelector("#s-menu-users > li:nth-child(1) > a")).shouldBe(Condition.visible).click();

    }

    @When("^in field \"([^\"]*)\" user enter the text \"([^\"]*)\"$")
    public void input(String fieldName, String fieldValue){
        String name = null;
        if (fieldName.equals("Login")) name = "UserLogin[username]";
        else if (fieldName.equals("Password")) name = "UserLogin[password]";
        $(byName(name)).shouldBe(Condition.visible).sendKeys(fieldValue);
    }

    @When("^click 'Sign in' button$")
    public void clickButton(){
        $(byName("yt0")).should(Condition.exist).click();
    }

    @When("^on the main side menu choose \"([^\"]*)\" item")
    public void clickButtonOnSideMenu(String btnName){
        $(byXpath("//span[text()='"+btnName+"']")).shouldBe(Condition.visible).click();
    }

    @When("^on the expanded Users menu choose \"([^\"]*)\" item")
    public void clickButtonOnUsersMenu(String btnName){
        $(byXpath("//*[@id='s-menu-users']//*[text()='"+btnName+"']")).shouldBe(Condition.visible).click();
    }

    @When("^on Players table click on the \"([^\"]*)\" column name")
    public void clickSortColumnButton(String columnName){
        $(byXpath("//th[@class='hide-mobile']/a[text()='"+columnName+"']")).shouldBe(Condition.visible).click();
    }

    @Then("^url is \"([^\"]*)\"$")
    public void checkUrl(String url){
        String currentUrl = url();
        Assert.assertEquals(currentUrl, url);
    }

    @Then("^admin panel loaded$")
    public void checkAdminPanelLoad(){
        $(byClassName("page")).shouldBe(Condition.visible);
    }

    @Then("^players page loaded$")
    public void checkPlayersPageLoad(){
        $(byClassName("panel-heading")).shouldBe(Condition.visible);
    }

    @Then("^sorting by column is correct$")
    public void checkSortingByColumn(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int count = 2;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String firstDateText = $(byXpath("//*[@class='centered hide-mobile'][text()='1']/following-sibling::td[9]")).shouldBe(Condition.visible).getText();
        LocalDateTime firstDate = LocalDateTime.parse(firstDateText, formatter);
        while (count < 21) {
            String currentDateText = $(byXpath("//*[@class='centered hide-mobile'][text()='"+count+"']/following-sibling::td[9]")).getText();
            LocalDateTime currentDate = LocalDateTime.parse(currentDateText, formatter);
            Assert.assertTrue("Сортировка говно", currentDate.isAfter(firstDate));
            firstDate = currentDate;
            count++;
        }
    }
}


