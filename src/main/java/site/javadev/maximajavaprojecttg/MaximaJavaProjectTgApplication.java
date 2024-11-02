package site.javadev.maximajavaprojecttg;

import com.sun.tools.javac.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import site.javadev.maximajavaprojecttg.bot.TelegramBot;

@SpringBootApplication
public class MaximaJavaProjectTgApplication implements CommandLineRunner {


    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private final TelegramBot telegramBot;

    public MaximaJavaProjectTgApplication(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public static void main(String[] args) {
        SpringApplication.run(MaximaJavaProjectTgApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Используем DefaultBotSession как параметр
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class); // Правильная инициализация

        try {
            // Регистрация бота
            telegramBotsApi.registerBot(telegramBot);
            logger.info("Bot registered successfully!");
        } catch (TelegramApiException e) {
            logger.error("Error registering bot: {}", e.getMessage());
        }
    }


}

