package ru.netology;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    @BeforeEach
    void setUp() {

        Configuration.browser = "chrome";
        open("http://localhost:9999");
    }

    @Test
    public void shouldCardDeliveryWithCssSelectors() {
        $("[data-test-id=city] .input__control").setValue("Москва");
        LocalDate today = LocalDate.now();
        LocalDate dayOfMeeting = today.plusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = dayOfMeeting.format(formatter);
        $("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(formattedDate);
        $("[data-test-id=name] .input__control").setValue("Иванов Иван");
        $("[data-test-id=phone] .input__control").setValue("+79890000002");
        //Чек-бокс
        $("div form fieldset label").click();
        //Забронировать
        $(Selectors.byText("Забронировать")).click();
        // Всплывающее окно успешно
        $(Selectors.withText("Успешно")).shouldBe(visible, Duration.ofSeconds(15));
        // Окно "Успешно" имеет текст "Встреча успешно забронирована на + дата"
        $("div.notification__content").shouldHave(exactText("Встреча успешно забронирована на " + formattedDate));

    }
}
