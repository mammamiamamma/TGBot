package org.botcontrol;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot{
    //new artifact version
    private final MessageSender messageSender;
    private final BotAnswers botAnswers;
    private final InlineKeyboardController inlineKeyboardController;
    private final MessageEditor messageEditor;
    private final long[] idList = new long[]{1547968115, 586833144};
//    private final long[] idList = new long[]{1547968115};
    public Bot(){
        this.messageSender = new MessageSender();
        this.botAnswers = new BotAnswers();
        this.inlineKeyboardController = new InlineKeyboardController();
        this.messageEditor = new MessageEditor();
    }
    @Override
    public void onUpdateReceived(Update update){
        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            this.messageSender.sendMessageWithSubKeyboard(update.getMessage().getChatId(), this.botAnswers.getAnswer(BotAnswers.AnswerOption.GREET), this, inlineKeyboardController.getSubscriptionOptions());
            sendToBosses(update);
        } else if (update.hasCallbackQuery()) {
            String query = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (isUsernameVisible(update)) {
                switch (query) {
                    case "main_menu" -> {
                        this.botAnswers.setMessage(null);
                        this.messageEditor.editMessage(chatId, messageId, this.botAnswers.getAnswer(BotAnswers.AnswerOption.GREET), this, this.inlineKeyboardController.getSubscriptionOptions());
                    }
                    case "month_3" -> {
                        this.botAnswers.setAnswerSubscription(BotAnswers.SubOption.MONTH_3);
                        this.messageEditor.editMessage(chatId, messageId, this.botAnswers.getMessage() + "Выберите способ оплаты - ", this, this.inlineKeyboardController.getPaymentOptions());
                    }
                    case "month_6" -> {
                        this.botAnswers.setAnswerSubscription(BotAnswers.SubOption.MONTH_6);
                        this.messageEditor.editMessage(chatId, messageId, this.botAnswers.getMessage() + "Выберите способ оплаты - ", this, this.inlineKeyboardController.getPaymentOptions());
                    }
                    case "month_12" -> {
                        this.botAnswers.setAnswerSubscription(BotAnswers.SubOption.MONTH_12);
                        this.messageEditor.editMessage(chatId, messageId, this.botAnswers.getMessage() + "Выберите способ оплаты - ", this, this.inlineKeyboardController.getPaymentOptions());
                    }
                    case "tink_card" -> {
                        this.botAnswers.setAnswerPayment(BotAnswers.PaymentOption.CARD);
                        this.messageEditor.editMessage(chatId, messageId, this.botAnswers.getMessage(), this, this.inlineKeyboardController.getPaymentInitiation());
                    }
                    case "tink_number" -> {
                        this.botAnswers.setAnswerPayment(BotAnswers.PaymentOption.TINKOFF);
                        this.messageEditor.editMessage(chatId, messageId, this.botAnswers.getMessage(), this, this.inlineKeyboardController.getPaymentInitiation());
                    }
                    case "payment_confirmed" -> {
                        for (long num : idList) {
                            this.messageSender.sendMessage(num, this.botAnswers.getConfirmationNumberAlert(update.getCallbackQuery().getMessage().getChat().getUserName()), this);
                        }
                        this.messageEditor.editMessage(chatId, messageId, "Спасибо за подтверждение оплаты, админ вскоре с вами свяжется для отправления подписки", this, this.inlineKeyboardController.getMainMenuMarkup());
                    }
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
            this.messageEditor.editMessage(update.getCallbackQuery().getMessage().getChatId(), update.getCallbackQuery().getMessage().getMessageId(), this.botAnswers.getAnswer(BotAnswers.BotError.USERNAME_NOT_VISIBLE), this);
            return false;
        } else {
            return true;
        }
    }
    @Override
    public String getBotToken(){
        return "6338938278:AAHElr41WuSQgFqBD5rskIcXB6fN6xhtedo"; // БОТ ДЛЯ ТЕСТА НОВЫХ ФУНКЦИЙ ТИПА)
    }
//    @Override
//    public String getBotToken(){
//        return "6486739146:AAG0Cd-MoQXq9DLtMVIpy3xD1DjZ9pUaw1I"; // ТГ ПРЕМИУМ БОТ
//    }

    @Override
    public String getBotUsername() {
        return "testerNoFunctionsBot"; // БОТ ДЛЯ ТЕСТА
    }
//    @Override
//    public String getBotUsername() {
//        return "cheaptgprem_bot"; // ТГ ПРЕМИУМ БОТ
//    }
}
