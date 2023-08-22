package org.botcontrol;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MessageSender {
    private final SendMessage sendMessage;
    public MessageSender(){
        this.sendMessage = new SendMessage();
    }
//    public void sendMessage(long chatId, String text, Bot bot) {
//        this.sendMessage.setChatId(chatId);
//        this.sendMessage.setText(text);
//        try {
//            bot.execute(sendMessage);
//        } catch (TelegramApiException e) {
//            throw new RuntimeException(e);
//        }
//    } //Общий метод для отправления сообщений пользователю
    public void sendMessage(long chatId, String text, Bot bot, boolean hideKeyboard) {
        this.sendMessage.setChatId(chatId);
        this.sendMessage.setText(text);
        this.sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true, false));
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        } //Метод если надо открыть или спрятать клавиатуру
    }
    public void sendMessageWithSubKeyboard(long chatId, String text, Bot bot, InlineKeyboardMarkup subKeyboard) {
        this.sendMessage.setChatId(chatId);
        this.sendMessage.setText(text);
        this.sendMessage.setReplyMarkup(subKeyboard);
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
