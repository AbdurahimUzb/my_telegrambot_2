package example;

import example.classvaluta.Main;
import example.classvaluta.ValutaHttp;
import example.namoz.NamozInfo;
import example.obhavo.Obhavo;
import lombok.SneakyThrows;
import lombok.Synchronized;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.List;

public class MainBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "https://t.me/taqvim_info_bot";
    }

    @Override
    public String getBotToken() {
        return "7120809630:AAG8MX4SAISuhJrA-QRHmLFqyM_-gAix7Hk";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            if (text.equals("/start")) {
                sendMessageWithInlineKeyboard(chatId, "Salom ! Quyidagi variantlardan birini tanlang:", getMainInlineKeyboard());
            } else if (text.equals("/clear")) {
                clearChatMessage(update.getMessage());
            } else {
                semdMessage(chatId, "Boshlash uchun /start ✅ ni bosing.\n\nBarcha ma'lumotlarni tozalash uchun\n /clear ✅ ni bosing.");
            }
        } else if (update.hasCallbackQuery()) {
            handleCallback(update.getCallbackQuery());
        }
    }

    @SneakyThrows
    @Synchronized
    private void semdMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        execute(message);
    }

    @SneakyThrows
    @Synchronized
    public void sendMessageWithInlineKeyboard(long chatId, String text, InlineKeyboardMarkup markup) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setReplyMarkup(markup);
        execute(message);
    }

    @Synchronized
    private InlineKeyboardMarkup getMainInlineKeyboard() {

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("💸 Valutalar");
        button1.setCallbackData("valutalar");
        row1.add(button1);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("🌦 Ob-havo ma'lumotlari");
        button2.setCallbackData("ob_havo");
        row2.add(button2);

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText("🕌 Namoz vaqtlari");
        button3.setCallbackData("namoz_vaqtlari");
        row3.add(button3);

        List<InlineKeyboardButton> row4 = new ArrayList<>();
        InlineKeyboardButton button4 = new InlineKeyboardButton();
        button4.setText("Clear ✅");
        button4.setCallbackData("/clear");
        row4.add(button4);

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);

        markup.setKeyboard(keyboard);
        return markup;
    }

    @SneakyThrows
    @Synchronized
    private void clearChatMessage(Message message) {

        Long chatId = message.getChatId();
        Integer messageId = message.getMessageId();

        DeleteMessage deleteMessage = new DeleteMessage();
        for (int i = messageId; i > 0; i--) {
            deleteMessage.setChatId(chatId.toString());
            deleteMessage.setMessageId(i);
            execute(deleteMessage);
        }
    }

    @SneakyThrows
    @Synchronized
    private void handleCallback(CallbackQuery callbackQuery) {

        Message message = (Message) callbackQuery.getMessage();

        String data = callbackQuery.getData();
        Long chatId = callbackQuery.getMessage().getChatId();
        int messageId = message.getMessageId();

        if (data.equals("valutalar")) {
            Main main = new Main();
            SendMessage sendMessage = main.createCurrencyMessage(chatId);
            execute(sendMessage);

        } else if (data.equals("ob_havo")) {
            String messageObhavo = new Obhavo().sendHttpObhavo();
            SendMessage obhavo = new SendMessage();
            obhavo.setChatId(chatId);
            obhavo.setText(messageObhavo);
            execute(obhavo);
        } else if (data.equals("namoz_vaqtlari")) {
            String namozHttp = new NamozInfo().getNamozHttpInfo();
            SendMessage namoz = new SendMessage();
            namoz.setChatId(chatId);
            namoz.setText(namozHttp);
            execute(namoz);
        } else if (data.equals("/clear")) {
            clearChatMessage(message);
        } else {
            SendMessage sendMessage = new SendMessage();
            ValutaHttp valutaHttp = new ValutaHttp();
            String valuta = valutaHttp.getValutalar(data);
            sendMessage.setChatId(chatId);
            sendMessage.setText(valuta);
            execute(sendMessage);
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @SneakyThrows
    public static void main(String[] args) {

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        MainBot mainBot = new MainBot();
        botsApi.registerBot(mainBot);
    }

}