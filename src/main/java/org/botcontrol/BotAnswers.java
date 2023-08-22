package org.botcontrol;

import java.util.Objects;

public class BotAnswers {
    private final String MONTH_PRICE_3 = "899";
    private final String MONTH_PRICE_6 = "1299";
    private final String MONTH_PRICE_12 = "1699";
    private final String ADMIN_UNAME = "@Kanjomanjo";
    private final String TINK_PHONENUM = "+79786436201";
    private final String TINK_CARD_NUMBER = "2200 7008 9386 4917";
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
                    + "• 3 месяца - " + MONTH_PRICE_3 + "₽\n"
                    + "• 6 месяцев - " + MONTH_PRICE_6 + "₽\n"
                    + "• 12 месяцев - " + MONTH_PRICE_12 + "₽";
        }
        return null;
    }
    public void setAnswerSubscription(SubOption subOption){
        message = "Выбранная подписка - "+enumsToString(subOption)+"\n\n"
                +"Сумма к оплате - "+getPriceFromSubscription(subOption)+"₽\n\n";
    }
    public void setAnswerPayment(PaymentOption paymentOption){
        message += "Выбранный способ оплаты - "+enumsToString(paymentOption);
        switch (paymentOption){
            case CARD -> {
                message += "\n\nНОМЕР КАРТЫ:\n"+TINK_CARD_NUMBER;
            }
            case TINKOFF -> {
                message += "\n\nНОМЕР ТЕЛЕФОНА:\n"+TINK_PHONENUM;
            }
        }
        message += "\n\nПосле совершения оплаты, нажмите кнопку снизу чтобы подтвердить платёж";
    }
    public String getAnswer(BotError botError){
        if (Objects.requireNonNull(botError) == BotError.USERNAME_NOT_VISIBLE) {
            return "Для того, чтобы мы могли с вами связаться для доставки подписки, нам нужен ваше имя пользователя (@uname), которое написано в вашем профиле.\n\n"
                    + "К сожалению, мы не можем получить ваше имя пользователя (Либо оно отсутсвует, либо случилась ошибка).\n\n"
                    + "‼️Пожалуйста, установите имя пользователя и перезапустите диалог написав /start или нажав на соответствующую команду в Menu. Если ошибка не исправится, пожалуйста, напишите нашему менеджеру " + ADMIN_UNAME + "‼️";
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
                return MONTH_PRICE_3;
            }
            case MONTH_6 -> {
                return MONTH_PRICE_6;
            }
            case MONTH_12 -> {
                return MONTH_PRICE_12;
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
    public void setMessage(String msg){
        message = msg;
    }
}
