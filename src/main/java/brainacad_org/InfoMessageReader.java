package brainacad_org;

import java.sql.*;

public class InfoMessageReader {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(AppConfig.getDbUrl(), AppConfig.getDbUsername(), AppConfig.getDbPassword())) {
            while (true) {
                String sql = "SELECT id, message, type, processed FROM notice WHERE type = 'INFO' AND processed = false";
                try (PreparedStatement pstmt = connection.prepareStatement(sql);
                     ResultSet rs = pstmt.executeQuery()) {

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String message = rs.getString("message");
                        System.out.println(message);

                        String deleteSql = "DELETE FROM notice WHERE id = ?";
                        try (PreparedStatement deletePstmt = connection.prepareStatement(deleteSql)) {
                            deletePstmt.setInt(1, id);
                            deletePstmt.executeUpdate();
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
