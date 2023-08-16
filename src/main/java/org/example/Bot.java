package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bot extends TelegramLongPollingBot {
    private ReplyKeyboardMarkup rkm;
    private final long[] idList = new long[]{1547968115, 586833144};
//    private final long[] idList = new long[]{1547968115};
    @Override
    public void onUpdateReceived(Update update){
//        System.out.println(update.getMessage().getChatId());
        SendMessage greetMsg = new SendMessage();
        if (update.hasMessage()) {
            if (update.getMessage().isCommand() && update.getMessage().getText().equals("/start")) {
                Message message = update.getMessage();
                long chatId = message.getChatId();
                greetMsg.setChatId(chatId);
                starterMessage(greetMsg);
                greetMsg.setReplyMarkup(rkm);
                try {
                    execute(greetMsg);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (isOneOfSubs(update.getMessage().getText())) {
                SendMessage sm = new SendMessage();
                sm.setChatId(update.getMessage().getChatId());
                if (isUsernameVisible(update)) {
                    sm.setText(Objects.requireNonNull(getResponse(update)));
                    sm.setReplyMarkup(new ReplyKeyboardRemove(true,false));
                    try {
                        execute(sm);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    sm.setText("""
                            ⭐Так же, после оплаты вашего заказа, вы можете дополнительно отправить боту номер вашей транзакции (размером в 11 цифр) после этого сообщения (используя комманду /connum +11 цифр подтверждения) чтобы упростить администратору подтверждение заказа.⭐

                            \uD83D\uDCCCПример:\s
                            /connum 11122233344

                            В случае возникновения вопросов по поводу заказа или оплаты, обращайтесь к нашему администратору @Kanjomanjo""");
                    sm.setChatId(update.getMessage().getChatId());
                    try {
                        execute(sm);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if (update.getMessage().getText().startsWith("/connum")) {
                SendMessage sm = new SendMessage();
                sm.setChatId(update.getMessage().getChatId());
                if (!isCorrectConnum(update.getMessage().getText())) {
                    sm.setText("Неверный номер подтверждения. Проверьте правильность его написания.");
                } else {
                    sm.setText("Номер заказа отправлен на рассмотрение. В ближайшее время администратор свяжется с вами для подтверждения заказа и отправки подписки. Поздравляем!");
                    sendMessageToBosses(update, update.getMessage().getText().substring(8));
                }
                try {
                    execute(sm);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    private boolean isCorrectConnum(String text){
        if (!text.startsWith("/connum")) return false;
        if (text.length()!=19) return false;
        String subtext = text.substring(8);
        if(subtext.length()!=11) return false;
        String pattern = "^\\d+$";
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(subtext);
        return matcher.matches();
    }
    private boolean isUsernameVisible(Update update){
//    private boolean mammothAlert(Update update){
        SendMessage sm = new SendMessage();
        if (update.getMessage().getChat().getUserName() == null){
            sm.setText("""
                    Для того, чтобы мы могли с вами связаться для доставки подписки, нам нужен ваше имя пользователя (@uname), которое написано в вашем профиле.\s
                    К сожалению, мы не можем получить ваше имя пользователя (Либо оно отсутсвует, либо случилась ошибка).\s
                    ‼️Пожалуйста, установите имя пользователя и перезапустите диалог написав /start или нажав на соответствующую команду в Menu. Если ошибка не исправится, пожалуйста, напишите нашему менеджеру @Kanjomanjo‼️""");
            sm.setChatId(update.getMessage().getChatId());
            sm.setReplyMarkup(new ReplyKeyboardRemove(true,false));
            try {
                execute(sm);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            return false;
        } else {
            sendMessageToBosses(update);
            return true;
        }
    }
    /*
    НАДО ИЗМЕНИТЬ КАК-ТО ЧТОБЫ ПОСЛЕ ОПЛАТЫ БЫЛО ЧТО ЛИ
     */
    private void sendMessageToBosses(Update update, String confirmationNumber){
        SendMessage sm = new SendMessage();
        sm.setText("BUYER @"+update.getMessage().getChat().getUserName()+" PROVIDED THE CONFIRMATION NUMBER: "+confirmationNumber);
        for (long num : idList){
            sm.setChatId(num);
            try {
                execute(sm);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void sendMessageToBosses(Update update){
        SendMessage sm = new SendMessage();
        sm.setText("NEW BUYER ALERT - @"+update.getMessage().getChat().getUserName()+", "+update.getMessage().getText());
        for (long num : idList){
            sm.setChatId(num);
            try {
                execute(sm);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private boolean isOneOfSubs(String msg){
        switch(msg){
            case "3 months", "12 months", "6 months" -> {
                return true;
            }
        }
        return false;
    }
    private void starterMessage(SendMessage sm){
        sm.setText("""
                ⭐Добро пожаловать в TelegramPremiumBot, бот по покупке самого дешевого премиум на просторах телеграма!\s

                ‼️Чтобы начать, выберите один из вариантов подписки внизу и нажмите на соответствующую кнопку.\s

                \uD83D\uDCB2Цены:
                • 3 месяца - 899₽
                • 6 месяцев - 1299₽
                • 12 месяцев - 1699₽""");
    }
    enum SUBSCRIPTION_OPTION{
        MONTH3, MONTH6, MONTH12
    }
    private String getResponse(Update update) {
        switch (update.getMessage().getText()) {
            case "3 months" -> {
                return subPaymentInit(SUBSCRIPTION_OPTION.MONTH3);
            }
            case "6 months" -> {
                return subPaymentInit(SUBSCRIPTION_OPTION.MONTH6);
            }
            case "12 months" -> {
                return subPaymentInit(SUBSCRIPTION_OPTION.MONTH12);
            }
        }
        return null;
    }
    private String subPaymentInit(SUBSCRIPTION_OPTION subopt){
        switch (subopt){
            case MONTH3 -> {
                return """
                        ⭐️Отлично! Вы выбрали 3 месяца подписки Telegram Premium. Для завершения покупки осталось провести оплату на сумму 899₽.

                        •\uD83D\uDFE7QIWI - по никнейму или по номеру карты:\s
                        Никнейм - 2BAD4EVER
                        Номер карты - 4890 4974 9720 8204

                        ‼️ОЧЕНЬ ВАЖНО‼️
                        ‼️Во время оформления перевода на эти данные, укажите в комменатарии к заказу имя пользователя (@uname), на аккаунт которого должна прийти подписка.‼️

                        В течении от 1 до 3 часов после оплаты заказа, подписка должна поступить на указанный в комментарии аккаунт. В случае каких-то вопросов и/или ошибки, с вами свяжется наш администратор.""";
            }
            case MONTH6 -> {
                return """
                        ⭐️Отлично! Вы выбрали 6 месяцев подписки Telegram Premium. Для завершения покупки осталось провести оплату на сумму 1299₽.

                        •\uD83D\uDFE7QIWI - по никнейму или по номеру карты:\s
                        Никнейм - 2BAD4EVER
                        Номер карты - 4890 4974 9720 8204

                        ‼️ОЧЕНЬ ВАЖНО‼️
                        ‼️Во время оформления перевода на эти данные, укажите в комменатарии к заказу имя пользователя (@uname), на аккаунт которого должна прийти подписка.‼️

                        В течении от 1 до 3 часов после оплаты заказа, подписка должна поступить на указанный в комментарии аккаунт. В случае каких-то вопросов и/или ошибки, с вами свяжется наш администратор.""";
            }
            case MONTH12 -> {
                return """
                        ⭐️Отлично! Вы выбрали 12 месяцев подписки Telegram Premium. Для завершения покупки осталось провести оплату на сумму 1699₽.

                        •\uD83D\uDFE7QIWI - по никнейму или по номеру карты:\s
                        Никнейм - 2BAD4EVER
                        Номер карты - 4890 4974 9720 8204

                        ‼️ОЧЕНЬ ВАЖНО‼️
                        ‼️Во время оформления перевода на эти данные, укажите в комменатарии к заказу имя пользователя (@uname), на аккаунт которого должна прийти подписка.‼️

                        В течении от 1 до 3 часов после оплаты заказа, подписка должна поступить на указанный в комментарии аккаунт. В случае каких-то вопросов и/или ошибки, с вами свяжется наш администратор.""";
            }
        }
        return null;
    }
    public void setMonths(){
        KeyboardRow kr1 = new KeyboardRow();
        KeyboardRow kr2 = new KeyboardRow();
        KeyboardButton month1but = new KeyboardButton("3 months");
        KeyboardButton month3but = new KeyboardButton("6 months");
        KeyboardButton month12but = new KeyboardButton("12 months");
        kr1.add(month1but);
        kr1.add(month3but);
        kr2.add(month12but);
        List<KeyboardRow> list = new ArrayList<>();
        list.add(kr1);
        list.add(kr2);
        rkm = new ReplyKeyboardMarkup();
        rkm.setKeyboard(list);
    }
    @Override
    public String getBotToken(){
        return "6486739146:AAG0Cd-MoQXq9DLtMVIpy3xD1DjZ9pUaw1I";
    }

    @Override
    public String getBotUsername() {
        return "cheaptgprem_bot";
    }
}
