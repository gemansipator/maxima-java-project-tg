package site.javadev.maximajavaprojecttg.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.List;

public class CommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommandHandler.class);
    private boolean isActive = true;

    public SendMessage handleCommand(Update update, String messageText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());

        // Обработка команды
        switch (messageText) {
            case "/stop":
                isActive = false;
                sendMessage.setText("Бот отключен. Для повторного запуска введите /start.");
                break;
            case "/start":
                isActive = true;
                sendMessage.setText("Бот снова активен! Напишите что-нибудь, и я повторю это.");
                sendMessage.setReplyMarkup(createKeyboard()); // Добавляем клавиатуру
                break;
            case "/help":
                sendMessage.setText("Доступные команды:\n" +
                        "/start - Запустить бота\n" +
                        "/stop - Отключить бота\n");
                sendMessage.setReplyMarkup(createKeyboard()); // Добавляем клавиатуру
                break;
            default:
                if (isActive) {
                    sendMessage.setText("Вы написали: " + messageText);
                    sendMessage.setReplyMarkup(createKeyboard()); // Добавляем клавиатуру
                } else {
                    // Если бот отключен, ничего не отправляем пользователю.
                    return null;
                }
                break;
        }

        return sendMessage;
    }

    public boolean isActive() {
        return isActive;
    }

    // Метод для создания главного меню
    private ReplyKeyboardMarkup createKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("/start"));
        row1.add(new KeyboardButton("/stop"));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("/help")); // Кнопка теперь будет отправлять /help

        keyboardMarkup.setKeyboard(List.of(row1, row2));

        return keyboardMarkup;
    }
}
