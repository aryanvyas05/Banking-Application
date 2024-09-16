package connectionJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private final String url = "jdbc:postgresql://localhost/BankingApplication";
    private final String user = "postgres";
    private final String password = "Sujangarh@1";

    
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);

            if (conn != null) {
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    
    public static void main(String[] args) {
        DatabaseConnection app = new DatabaseConnection();
        app.connect();
    }
}

