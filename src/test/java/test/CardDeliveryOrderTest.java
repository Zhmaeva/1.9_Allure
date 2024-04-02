package test;

import data.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryOrderTest {

    //selectors
    String validDateSelector = "[data-test-id='date'] input";
    String validCitySelector = "[data-test-id='city'] input";
    String validNameSelector = "[data-test-id='name'] input";
    String validPhoneSelector = "[data-test-id='phone'] input";
    String validAgreementSelector = "[data-test-id='agreement'] span.checkbox__box";
    String sendFormButtonSelector = "button .button__text";
    String notificationTitleSelector = "[data-test-id='success-notification'] .notification__title";
    String notificationContentSelector = "[data-test-id='success-notification'] .notification__content";
    String notificationRePlanTitleSelector = "[data-test-id='replan-notification'] .notification__title";
    String notificationRePlanContentSelector = "[data-test-id='replan-notification'] .notification__content";
    String notificationRePlanButton = "[data-test-id='replan-notification'] button";

    //messages
    String sendButtonText = "Запланировать";
    String notificationTitleText = "Успешно!";
    String notificationContentText = "Встреча успешно запланирована на ";
    String notificationRePlanTitleText = "Необходимо подтверждение";
    String notificationButtonText = "Перепланировать";
    String notificationRePlanContentText = "У вас уже запланирована встреча на другую дату. Перепланировать?";


    //methods
    void clearField(String selector) {
        $(selector).sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $(selector).sendKeys(Keys.DELETE);
    }

    public void fillingOutCardOrderForm(String city, String date, String name, String phone) {
        $(validCitySelector).setValue(city);
        $(validDateSelector).setValue(date);
        $(validNameSelector).setValue(name);
        $(validPhoneSelector).setValue(phone);
        $(validAgreementSelector).click();
        $$(sendFormButtonSelector).find(exactText(sendButtonText)).click();
    }

    public void checkNotification(String date) {
        $(notificationTitleSelector).shouldBe(visible, Duration.ofSeconds(15)).shouldHave(innerText(notificationTitleText));
        $(notificationContentSelector).shouldHave(visible, Duration.ofSeconds(15)).shouldHave(innerText(notificationContentText + date));
    }

    public void checkRePlanNotification(String date) {
        $(notificationRePlanTitleSelector).shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text(notificationRePlanTitleText));
        $(notificationRePlanContentSelector).shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text(notificationRePlanContentText));
        $(notificationRePlanButton).should(visible).shouldHave(text(notificationButtonText)).click();
        $(notificationTitleSelector).shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text(notificationTitleText));
        $(notificationContentSelector).shouldHave(visible, Duration.ofSeconds(15)).shouldHave(text(notificationContentText + date));
    }

    @BeforeEach
    void beforeTests() {
        open("http://localhost:9999");
        clearField(validDateSelector);
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndRePlanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        fillingOutCardOrderForm(validUser.getCity(), firstMeetingDate, validUser.getName(), validUser.getPhone());
        $$(sendFormButtonSelector).find(exactText(sendButtonText)).click();
        checkNotification(firstMeetingDate);
        clearField(validDateSelector);
        $(validDateSelector).setValue(secondMeetingDate);
        $$(sendFormButtonSelector).find(exactText(sendButtonText)).click();
        checkRePlanNotification(secondMeetingDate);
        checkNotification(secondMeetingDate);
    }
}
