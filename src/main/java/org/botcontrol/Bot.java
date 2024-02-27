package org.botcontrol;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot{
    //new artifact version
    private final MessageSender messageSender;
    private final BotAnswers botAnswers;
    private final InlineKeyboardController inlineKeyboardController;
    private final MessageEditor messageEditor;
    private final long[] idList = new long[]{222, 111}; //ids to send information about new clients to (taken out for security reasons)
//    private final long[] idList = new long[]{111};
    public Bot(){
        this.messageSender = new MessageSender();
        this.botAnswers = new BotAnswers();
        this.inlineKeyboardController = new InlineKeyboardController();
        this.messageEditor = new MessageEditor();
    }
    @Override
    public void onUpdateReceived(Update update){
        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            this.messageSender.sendMessageWithSubKeyboard(update.getMessage().getChatId(), this.botAnswers.getGreeting(), this, inlineKeyboardController.getSubscriptionOptions());
            sendToBosses(update);
        } else if (update.hasCallbackQuery() && isUsernameVisible(update)) {
            String query = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            switch (query) {
                case "main_menu" -> {
                    this.botAnswers.setMessage(null);
                    this.messageEditor.editMessage(chatId, messageId, this.botAnswers.getGreeting(), this, this.inlineKeyboardController.getSubscriptionOptions());
                }
                case "month_3" -> {
                    this.botAnswers.setAnswerSubscription(SubOption.MONTH_3);
                    this.messageEditor.editMessage(chatId, messageId, this.botAnswers.getMessage() + "Выберите способ оплаты - ", this, this.inlineKeyboardController.getPaymentOptions());
                }
                case "month_6" -> {
                    this.botAnswers.setAnswerSubscription(SubOption.MONTH_6);
                    this.messageEditor.editMessage(chatId, messageId, this.botAnswers.getMessage() + "Выберите способ оплаты - ", this, this.inlineKeyboardController.getPaymentOptions());
                }
                case "month_12" -> {
                    this.botAnswers.setAnswerSubscription(SubOption.MONTH_12);
                    this.messageEditor.editMessage(chatId, messageId, this.botAnswers.getMessage() + "Выберите способ оплаты - ", this, this.inlineKeyboardController.getPaymentOptions());
                }
                case "tink_card" -> {
                    this.botAnswers.setAnswerPayment(PaymentOption.CARD);
                    this.messageEditor.editMessage(chatId, messageId, this.botAnswers.getMessage(), this, this.inlineKeyboardController.getPaymentInitiation());
                }
                case "tink_number" -> {
                    this.botAnswers.setAnswerPayment(PaymentOption.TINKOFF);
                    this.messageEditor.editMessage(chatId, messageId, this.botAnswers.getMessage(), this, this.inlineKeyboardController.getPaymentInitiation());
                }
                case "payment_confirmed" -> {
                    for (long num : idList) {
                        this.messageSender.sendMessage(num, this.botAnswers.getConfirmationNumberAlert(update.getCallbackQuery().getMessage().getChat().getUserName()), this);
                    }
                    this.messageEditor.editMessage(chatId, messageId, this.botAnswers.getPaymentConfirmed(), this, this.inlineKeyboardController.getMainMenuMarkup());
                }
            }
        }
    }
    private void sendToBosses(Update update){
        if (update.getMessage().getChat().getUserName()!=null){
            for (long num : idList){
                this.messageSender.sendMessage(num, this.botAnswers.getNewBuyerAlert(update.getMessage().getChat().getUserName()), this);
            }
        }
    }
    private boolean isUsernameVisible(Update update){
        if (update.getCallbackQuery().getMessage().getChat().getUserName() == null){
            this.messageEditor.editMessage(update.getCallbackQuery().getMessage().getChatId(), update.getCallbackQuery().getMessage().getMessageId(), this.botAnswers.getUsernameError(), this);
            return false;
        } else {
            return true;
        }
    }
//    @Override
//    public String getBotToken(){
//        return ""; // Secondary bot for testing new features.
//    }
    @Override
    public String getBotToken(){
        return ""; // the original (the tokens are taken out for security reasons)
    }

//    @Override
//    public String getBotUsername() {
//        return "testerNoFunctionsBot";
//    }
    @Override
    public String getBotUsername() {
        return "cheaptgprem_bot";
    }
}
