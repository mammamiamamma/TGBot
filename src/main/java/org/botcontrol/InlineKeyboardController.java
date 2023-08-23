package org.botcontrol;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardController {
    private InlineKeyboardMarkup subscriptionOptions;
    private InlineKeyboardMarkup paymentOptions;
    private final InlineKeyboardMarkup mainMenuMarkup;
    private InlineKeyboardMarkup paymentInitiation;

    public InlineKeyboardController(){
        InlineKeyboardButton mainMenuButton = new InlineKeyboardButton();
        mainMenuButton.setText("\uD83C\uDFE0Главное меню");
        mainMenuButton.setCallbackData("main_menu");
        List<InlineKeyboardButton> mainMenuRow = new ArrayList<>();
        mainMenuRow.add(mainMenuButton);

        mainMenuMarkup = new InlineKeyboardMarkup(List.of(mainMenuRow));
        setSubscriptionOptions();
        setPaymentOptions(mainMenuRow);
        setPaymentInitiation(mainMenuRow);
    }
    private void setSubscriptionOptions(){
        InlineKeyboardButton MONTH_3_BUTTON = createInlineButton("3 месяца\uD83D\uDD25", "month_3");
        InlineKeyboardButton MONTH_6_BUTTON = createInlineButton("6 месяцев\uD83D\uDD25\uD83D\uDD25", "month_6");
        InlineKeyboardButton MONTH_12_BUTTON = createInlineButton("12 месяцев\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25", "month_12");
        List<InlineKeyboardButton> subRow1 = new ArrayList<>();
        List<InlineKeyboardButton> subRow2 = new ArrayList<>();
        List<InlineKeyboardButton> subRow3 = new ArrayList<>();
        subRow1.add(MONTH_3_BUTTON);
        subRow2.add(MONTH_6_BUTTON);
        subRow3.add(MONTH_12_BUTTON);
        List<List<InlineKeyboardButton>> subRows = new ArrayList<>();
        subRows.add(subRow1);
        subRows.add(subRow2);
        subRows.add(subRow3);

        subscriptionOptions = new InlineKeyboardMarkup(subRows);
    }
    private void setPaymentOptions(List<InlineKeyboardButton> menuRow){
        InlineKeyboardButton CARD_BUTTON = createInlineButton("\uD83D\uDCB3Тинькофф / Карта", "tink_card");
        InlineKeyboardButton TINKOFF_BUTTON = createInlineButton("\uD83D\uDCDFТинькофф / Номер телефона", "tink_number");
        List<InlineKeyboardButton> subRow1 = new ArrayList<>();
        List<InlineKeyboardButton> subRow2 = new ArrayList<>();
        subRow1.add(CARD_BUTTON);
        subRow2.add(TINKOFF_BUTTON);
        List<List<InlineKeyboardButton>> subRows = new ArrayList<>();
        subRows.add(subRow1);
        subRows.add(subRow2);

        subRows.add(menuRow);

        paymentOptions = new InlineKeyboardMarkup(subRows);
    }
    private void setPaymentInitiation(List<InlineKeyboardButton> menuRow){
        InlineKeyboardButton CONFIRM_BUTTON = createInlineButton("✔️Подтвердить оплату✔️", "payment_confirmed");
        List<InlineKeyboardButton> subRow1 = new ArrayList<>();
        subRow1.add(CONFIRM_BUTTON);
        List<List<InlineKeyboardButton>> subRows = new ArrayList<>();
        subRows.add(subRow1);

        subRows.add(menuRow);

        paymentInitiation = new InlineKeyboardMarkup(subRows);
    }
    private InlineKeyboardButton createInlineButton(String text, String callbackData){
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callbackData);
        return button;
    }
    public InlineKeyboardMarkup getPaymentOptions() {
        return paymentOptions;
    }
    public InlineKeyboardMarkup getSubscriptionOptions() {
        return subscriptionOptions;
    }
    public InlineKeyboardMarkup getPaymentInitiation(){
        return paymentInitiation;
    }
    public InlineKeyboardMarkup getMainMenuMarkup() {
        return mainMenuMarkup;
    }
}

