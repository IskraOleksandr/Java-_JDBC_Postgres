package brainacad_org;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;

public class NoticeWriter {
    private static final Random random = new Random();

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(AppConfig.getDbUrl(), AppConfig.getDbUsername(), AppConfig.getDbPassword())) {
            while (true) {
                String message = generateMessage();
                String type = random.nextBoolean() ? "INFO" : "WARN";
                boolean processed = false;

                String sql = "INSERT INTO notice (message, type, processed) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, message);
                    pstmt.setString(2, type);
                    pstmt.setBoolean(3, processed);
                    pstmt.executeUpdate();
                }

                Thread.sleep(1000);
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String generateMessage() {
        LocalDateTime now = LocalDateTime.now();
        if (random.nextBoolean()) {
            return "Новое сообщение от " + now;
        } else {
            return "Произошла ошибка в " + now;
        }
    }
}
