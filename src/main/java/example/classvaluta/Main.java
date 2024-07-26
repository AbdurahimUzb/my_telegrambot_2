package example.classvaluta;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public SendMessage createCurrencyMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Assalomu alaykum hush kelibsiz. 😊😊😊");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        FieldValutes[] fieldValutes = FieldValutes.values();

        int counter = 0;
        for (int i = 0; i < 25; i++) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                if (counter < fieldValutes.length) {
                    InlineKeyboardButton button = new InlineKeyboardButton();
                    FieldValutes fieldValute = fieldValutes[counter];
                    button.setText(fieldValute.getFlag() + " " + fieldValute.getCuntry());
                    button.setCallbackData(fieldValute.getCuntry());
                    rowInline.add(button);
                    counter++;
                }
            }
            rowsInline.add(rowInline);
        }

        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Clear ✅");
        button.setCallbackData("/clear");

        row.add(button);
        rowsInline.add(row);


        markup.setKeyboard(rowsInline);
        message.setReplyMarkup(markup);
        return message;
    }
}