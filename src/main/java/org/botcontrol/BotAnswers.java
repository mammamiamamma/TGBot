package org.botcontrol;

import java.util.Objects;

public class BotAnswers {
//    private final String MONTH_PRICE_3 = "899";
//    private final String MONTH_PRICE_6 = "1299";
//    private final String MONTH_PRICE_12 = "1699";
//    private final String ADMIN_UNAME = "@Kanjomanjo";
//    private final String TINK_PHONENUM = "+79786436201";
//    private final String TINK_CARD_NUMBER = "2200 7008 9386 4917";
    private final String SUBSCRIPTION_TEMPLATE = "Выбранная подписка - %s\n\nСумма к оплате - %s₽\n\n";
    private final String PAYMENT_CONFIRMATION_TEMPLATE = "Выбранный способ оплаты - %s\n\n%s\n\nПосле совершения оплаты, нажмите кнопку снизу чтобы подтвердить платёж";
    private String message;
    enum AnswerOption {
        GREET
    }
    enum BotError {
        USERNAME_NOT_VISIBLE
    }
    enum SubOption {
        MONTH_3, MONTH_6, MONTH_12
    }
    enum PaymentOption{
        CARD, TINKOFF
    }
    public String getAnswer(AnswerOption answerOptions){
        if (Objects.requireNonNull(answerOptions) == AnswerOption.GREET) {
            return "⭐Добро пожаловать в TelegramPremiumBot, бот по покупке самого дешевого премиум на просторах телеграма!\n\n"
                    + "‼️Чтобы начать, выберите один из вариантов подписки внизу и нажмите на соответствующую кнопку.\n\n"
                    + "\uD83D\uDCB2Цены:\n"
                    + "• 3 месяца - " + getPrice_3months() + "₽\n"
                    + "• 6 месяцев - " + getPrice_6months() + "₽\n"
                    + "• 12 месяцев - " + getPrice_12months() + "₽";
        }
        return null;
    }
    public void setAnswerSubscription(SubOption subOption) {
        message = String.format(SUBSCRIPTION_TEMPLATE, enumsToString(subOption), getPriceFromSubscription(subOption));
    }
    public void setAnswerPayment(PaymentOption paymentOption) {
        String paymentDetails = paymentOption == PaymentOption.CARD ? "НОМЕР КАРТЫ:\n" + getTinkCardNumber() : "НОМЕР ТЕЛЕФОНА:\n" + getTinkPhoneNumber();
        message += String.format(PAYMENT_CONFIRMATION_TEMPLATE, enumsToString(paymentOption), paymentDetails);
    }
    public String getAnswer(BotError botError){
        if (Objects.requireNonNull(botError) == BotError.USERNAME_NOT_VISIBLE) {
            return "Для того, чтобы мы могли с вами связаться для доставки подписки, нам нужен ваше имя пользователя (@uname), которое написано в вашем профиле.\n\n"
                    + "К сожалению, мы не можем получить ваше имя пользователя (Либо оно отсутсвует, либо случилась ошибка).\n\n"
                    + "‼️Пожалуйста, установите имя пользователя и перезапустите диалог написав /start или нажав на соответствующую команду в Menu. Если ошибка не исправится, пожалуйста, напишите нашему менеджеру " + getAdminName() + "‼️";
        }
        return null;
    }
    public String enumsToString(PaymentOption option){
        switch (option){
            case CARD -> {
                return "TINKOFF/Карта";
            }
            case TINKOFF -> {
                return "TINKOFF/Телефон";
            }
        }
        return null;
    }
    public String enumsToString(SubOption option){
        switch (option){
            case MONTH_3 -> {
                return "3 месяца";
            }
            case MONTH_6 -> {
                return "6 месяцев";
            }
            case MONTH_12 -> {
                return "12 месяцев";
            }
        }
        return null;
    }
    public String getPriceFromSubscription(SubOption subOption){
        switch(subOption) {
            case MONTH_3 -> {
                return getPrice_3months();
            }
            case MONTH_6 -> {
                return getPrice_6months();
            }
            case MONTH_12 -> {
                return getPrice_12months();
            }
        }
        return null;
    }
    public String getNewBuyerAlert(String uname){
        return "Пользователь @"+uname+" нажал на кнопку 'БАБЛО'";
    }
    public String getConfirmationNumberAlert(String uname){
        return "Пользователь @"+uname+" подтвердил оплату (сам, мож наебал)";
    }
    public String getMessage(){
        return message;
    }
    private String getTinkCardNumber() {
        return "2200 7008 9386 4917";
    }
    private String getTinkPhoneNumber() {
        return "+79786436201";
    }
    private String getPrice_3months(){
        return "899";
    }
    private String getPrice_6months(){
        return "1299";
    }
    private String getPrice_12months(){
        return "1699";
    }
    private String getAdminName(){
        return "@Kanjomanjo";
    }
    public void setMessage(String msg){
        message = msg;
    }
}
