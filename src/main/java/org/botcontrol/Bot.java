package org.botcontrol;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bot extends TelegramLongPollingBot{
    private final MessageSender messageSender;
    private final BotAnswers botAnswers;
    private final long[] idList = new long[]{1547968115, 586833144};
    private static final int CONNUM_LENGTH = 19;
//    private final long[] idList = new long[]{1547968115};
    public Bot(){
        KeyboardRow kr1 = new KeyboardRow();
        KeyboardRow kr2 = new KeyboardRow();
        kr1.add(new KeyboardButton("3 months"));
        kr1.add(new KeyboardButton("6 months"));
        kr2.add(new KeyboardButton("12 months"));
        List<KeyboardRow> list = new ArrayList<>();
        list.add(kr1);
        list.add(kr2);

        this.messageSender = new MessageSender(new ReplyKeyboardMarkup(list));
        this.botAnswers = new BotAnswers();
    }
    @Override
    public void onUpdateReceived(Update update){
        if (update.hasMessage()) {
            String text = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            if (update.getMessage().isCommand() && text.equals("/start")) {
                this.messageSender.sendMessage(chatId, botAnswers.getAnswer(BotAnswers.AnswerOption.GREET), this, false);
            } else if (isOneOfSubs(text)) {
                if (isUsernameVisible(update)) {
                    this.messageSender.sendMessage(chatId, this.botAnswers.getAnswer(Objects.requireNonNull(getResponse(text))), this, true);
                    this.messageSender.sendMessage(chatId, this.botAnswers.getAnswer(BotAnswers.AnswerOption.CONNUM_REQUEST), this);
                }
            } else if (text.startsWith("/connum")) {
                if (!isCorrectConnum(text)) {
                    this.messageSender.sendMessage(chatId, this.botAnswers.getAnswer((BotAnswers.BotError.CONNUM_ERROR)), this);
                } else {
                    this.messageSender.sendMessage(chatId, this.botAnswers.getAnswer((BotAnswers.AnswerOption.CONNUM_ACCEPTED)), this);
                    for (long num : idList){
                        this.messageSender.sendMessage(num, this.botAnswers.getConfirmationNumberAlert(update.getMessage().getChat().getUserName(), update.getMessage().getText().substring(8)), this);
                    }
                }
            }
        }
    }
    private boolean isCorrectConnum(String text) {
        if (!text.startsWith("/connum")) return false;
        if (text.length() != CONNUM_LENGTH) return false;
        return text.substring(8).matches("^\\d+$");
    }
    private boolean isUsernameVisible(Update update){
        if (update.getMessage().getChat().getUserName() == null){
            this.messageSender.sendMessage(update.getMessage().getChatId(), this.botAnswers.getAnswer(BotAnswers.BotError.USERNAME_NOT_VISIBLE), this, true);
            return false;
        } else {
            for (long num : idList){
                this.messageSender.sendMessage(num, this.botAnswers.getNewBuyerAlert(update.getMessage().getChat().getUserName(), update.getMessage().getText()), this);
            }
            return true;
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
    private BotAnswers.SubOption getResponse(String text) {
        switch (text) {
            case "3 months" -> {
                return BotAnswers.SubOption.MONTH_3;
            }
            case "6 months" -> {
                return BotAnswers.SubOption.MONTH_6;
            }
            case "12 months" -> {
                return BotAnswers.SubOption.MONTH_12;
            }
        }
        return null;
    }
//    @Override
//    public String getBotToken(){
//        return "6338938278:AAHElr41WuSQgFqBD5rskIcXB6fN6xhtedo"; // БОТ ДЛЯ ТЕСТА НОВЫХ ФУНКЦИЙ ТИПА)
//    }
    @Override
    public String getBotToken(){
        return "6486739146:AAG0Cd-MoQXq9DLtMVIpy3xD1DjZ9pUaw1I"; // ТГ ПРЕМИУМ БОТ
    }

//    @Override
//    public String getBotUsername() {
//        return "testerNoFunctionsBot"; // БОТ ДЛЯ ТЕСТА
//    }
    @Override
    public String getBotUsername() {
        return "cheaptgprem_bot"; // ТГ ПРЕМИУМ БОТ
    }
}
