package org.botcontrol;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static java.lang.Math.toIntExact;

public class MessageEditor {
    private final EditMessageText edm;
    public MessageEditor(){
        this.edm = new EditMessageText();
    }
    public void editMessage(long chatId, long messageId, String text, Bot bot, InlineKeyboardMarkup ikm){
        edm.setText(text);
        edm.setChatId(chatId);
        edm.setMessageId(toIntExact(messageId));
        edm.setReplyMarkup(ikm);
        try {
            bot.execute(edm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void editMessage(long chatId, long messageId, String text, Bot bot){
        edm.setText(text);
        edm.setChatId(chatId);
        edm.setMessageId(toIntExact(messageId));
        try {
            bot.execute(edm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
