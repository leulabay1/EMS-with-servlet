import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/init")
public class DBsetup extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        String userName = req.getParameter("unit");
        pw.printf("%s is %d years old", userName, "what");
    }
}
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String jdbcUrl = "jdbc:mysql://localhost:3306";
            String username = "root";
            String password = "";

            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

                try (Statement statement = connection.createStatement()) {
                    String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS EMS";
                    statement.executeUpdate(createDatabaseSQL);

                    System.out.println("created database");

                }

                String useDatabaseSQL = "Use EMS";
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(useDatabaseSQL);
                }

                String createTableSQL = "CREATE TABLE IF NOT EXISTS employees (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "name VARCHAR(255) NOT NULL," +
                        "designation VARCHAR(255) NOT NULL," +
                        "salary DECIMAL(10, 2) NOT NULL" +
                        ")";
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(createTableSQL);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
