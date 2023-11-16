import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.*;

@WebServlet("/view")
public class ViewEmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Employee List</title></head><body>");
        out.println("<h2>Employee List</h2>");

        out.println("<table border='1'>");
        out.println("<tr><th>ID</th><th>Name</th><th>Designation</th><th>Salary</th></tr>");

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS", "root", "")) {
            String sql = "SELECT * FROM employees";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String designation = resultSet.getString("designation");
                    BigDecimal salary = resultSet.getBigDecimal("salary");

                    out.println("<tr>");
                    out.println("<td>" + id + "</td>");
                    out.println("<td>" + name + "</td>");
                    out.println("<td>" + designation + "</td>");
                    out.println("<td>" + salary + "</td>");
                    out.println("</tr>");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.println("</table>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}