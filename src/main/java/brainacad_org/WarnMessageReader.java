package brainacad_org;


import java.sql.*;

public class WarnMessageReader {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(AppConfig.getDbUrl(), AppConfig.getDbUsername(), AppConfig.getDbPassword())) {
            while (true) {
                String sql = "SELECT id, message, type, processed FROM notice WHERE type = 'WARN' AND processed = false";
                try (PreparedStatement pstmt = connection.prepareStatement(sql);
                     ResultSet rs = pstmt.executeQuery()) {

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String message = rs.getString("message");
                        System.out.println(message);

                        String updateSql = "UPDATE notice SET processed = true WHERE id = ?";
                        try (PreparedStatement updatePstmt = connection.prepareStatement(updateSql)) {
                            updatePstmt.setInt(1, id);
                            updatePstmt.executeUpdate();
                        }
                    }
                }
                Thread.sleep(1000);
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
