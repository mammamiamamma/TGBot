package org.botcontrol;
public class BotAnswers {
    private final String MONTH_PRICE_3 = "899";
    private final String MONTH_PRICE_6 = "1299";
    private final String MONTH_PRICE_12 = "1699";
    private final String ADMIN_UNAME = "@Kanjomanjo";
    private final String QIWI_USERNAME = "2BAD4EVER";
    private final String QIWI_CARD_NUMBER = "4890 4974 9720 8204";
    enum AnswerOption {
        GREET, CONNUM_REQUEST, CONNUM_ACCEPTED
    }
    enum BotError {
        CONNUM_ERROR, USERNAME_NOT_VISIBLE
    }
    enum SubOption {
        MONTH_3, MONTH_6, MONTH_12
    }
    public String getAnswer(AnswerOption answerOptions){
        switch (answerOptions) {
            case GREET -> {
                return "⭐Добро пожаловать в TelegramPremiumBot, бот по покупке самого дешевого премиум на просторах телеграма!\n\n"
                        + "‼️Чтобы начать, выберите один из вариантов подписки внизу и нажмите на соответствующую кнопку.\n\n"
                        + "\uD83D\uDCB2Цены:\n"
                        + "• 3 месяца - "+MONTH_PRICE_3+"₽\n"
                        + "• 6 месяцев - "+MONTH_PRICE_6+"₽\n"
                        + "• 12 месяцев - "+MONTH_PRICE_12+"₽";
            }
            case CONNUM_REQUEST -> {
                return "⭐Так же, после оплаты вашего заказа, вы можете дополнительно отправить боту номер вашей транзакции (размером в 11 цифр) после этого сообщения (используя комманду /connum +11 цифр подтверждения) чтобы упростить администратору подтверждение заказа.⭐\n\n"
                        + "\uD83D\uDCCCПример:\n"
                        + "/connum 11122233344\n\n"
                        + "В случае возникновения вопросов по поводу заказа или оплаты, обращайтесь к нашему администратору "+ADMIN_UNAME;
            }
            case CONNUM_ACCEPTED -> {
                return "Номер заказа отправлен на рассмотрение. В ближайшее время администратор свяжется с вами для подтверждения заказа и отправки подписки. Поздравляем!";
            }
        }
        return null;
    }
    public String getAnswer(SubOption subOption){
        switch (subOption){
            case MONTH_3 -> {
                return "⭐️Отлично! Вы выбрали 3 месяца подписки Telegram Premium. Для завершения покупки осталось провести оплату на сумму "+MONTH_PRICE_3+"₽.\n"
                        + "•\uD83D\uDFE7QIWI - по никнейму или по номеру карты:\n\n"
                        + "Никнейм - "+QIWI_USERNAME+"\n"
                        + "Номер карты - "+QIWI_CARD_NUMBER+"\n\n"
                        + "‼️ОЧЕНЬ ВАЖНО‼️\n"
                        + "‼️Во время оформления перевода на эти данные, укажите в комменатарии к заказу имя пользователя (@uname), на аккаунт которого должна прийти подписка.‼️\n\n"
                        + "В течении от 1 до 3 часов после оплаты заказа, подписка должна поступить на указанный в комментарии аккаунт. В случае каких-то вопросов и/или ошибки, с вами свяжется наш администратор.";
            }
            case MONTH_6 -> {
                return "⭐️Отлично! Вы выбрали 6 месяцев подписки Telegram Premium. Для завершения покупки осталось провести оплату на сумму "+MONTH_PRICE_6+"₽.\n"
                        + "•\uD83D\uDFE7QIWI - по никнейму или по номеру карты:\n\n"
                        + "Никнейм - "+QIWI_USERNAME+"\n"
                        + "Номер карты - "+QIWI_CARD_NUMBER+"\n\n"
                        + "‼️ОЧЕНЬ ВАЖНО‼️\n"
                        + "‼️Во время оформления перевода на эти данные, укажите в комменатарии к заказу имя пользователя (@uname), на аккаунт которого должна прийти подписка.‼️\n\n"
                        + "В течении от 1 до 3 часов после оплаты заказа, подписка должна поступить на указанный в комментарии аккаунт. В случае каких-то вопросов и/или ошибки, с вами свяжется наш администратор.";
            }
            case MONTH_12 -> {
                return "⭐️Отлично! Вы выбрали 12 месяцев подписки Telegram Premium. Для завершения покупки осталось провести оплату на сумму "+MONTH_PRICE_12+"₽.\n"
                        + "•\uD83D\uDFE7QIWI - по никнейму или по номеру карты:\n\n"
                        + "Никнейм - "+QIWI_USERNAME+"\n"
                        + "Номер карты - "+QIWI_CARD_NUMBER+"\n\n"
                        + "‼️ОЧЕНЬ ВАЖНО‼️\n"
                        + "‼️Во время оформления перевода на эти данные, укажите в комменатарии к заказу имя пользователя (@uname), на аккаунт которого должна прийти подписка.‼️\n\n"
                        + "В течении от 1 до 3 часов после оплаты заказа, подписка должна поступить на указанный в комментарии аккаунт. В случае каких-то вопросов и/или ошибки, с вами свяжется наш администратор("+ADMIN_UNAME+".";
            }
        }
        return null;
    }
    public String getAnswer(BotError botError){
        switch (botError){
            case USERNAME_NOT_VISIBLE -> {
                return "Для того, чтобы мы могли с вами связаться для доставки подписки, нам нужен ваше имя пользователя (@uname), которое написано в вашем профиле.\n"
                        + "К сожалению, мы не можем получить ваше имя пользователя (Либо оно отсутсвует, либо случилась ошибка).\n"
                        + "‼️Пожалуйста, установите имя пользователя и перезапустите диалог написав /start или нажав на соответствующую команду в Menu. Если ошибка не исправится, пожалуйста, напишите нашему менеджеру "+ADMIN_UNAME+"‼️";
            }
            case CONNUM_ERROR -> {
                return "Неверный номер подтверждения. Проверьте правильность его написания.";
            }
        }
        return null;
    }
    public String getNewBuyerAlert(String uname, String subtype){
        return "NEW BUYER ALERT - @"+uname+", "+subtype;
    }
    public String getConfirmationNumberAlert(String uname, String confirmationNumber){
        return "BUYER @"+uname+" PROVIDED THE CONFIRMATION NUMBER: "+confirmationNumber;
    }
}
