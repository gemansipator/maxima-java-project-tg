package site.javadev.maximajavaprojecttg.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;

    private final CommandHandler commandHandler = new CommandHandler();

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            SendMessage sendMessage = commandHandler.handleCommand(update, messageText);

            if (sendMessage != null) {
                try {
                    execute(sendMessage);
                    logger.info("Message sent successfully to chat ID: {}", update.getMessage().getChatId());
                } catch (TelegramApiException e) {
                    logger.error("Error while sending message to chat ID {}: {}", update.getMessage().getChatId(), e.getMessage());
                }
            }
        } else {
            logger.warn("Received an update that is not a message or does not contain text.");
        }
    }
}
