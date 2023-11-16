import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/edit")
public class EditEmployeeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int employeeId = Integer.parseInt(request.getParameter("id"));

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS", "root", "")) {
            String sql = "SELECT * FROM employees WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, employeeId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String name = resultSet.getString("name");
                        String designation = resultSet.getString("designation");
                        BigDecimal salary = resultSet.getBigDecimal("salary");

                        out.println("<html><head><title>Edit Employee</title></head><body>");
                        out.println("<h2>Edit Employee</h2>");
                        out.println("<form action='editEmployee' method='post'>");
                        out.println("<input type='hidden' name='id' value='" + employeeId + "'>");
                        out.println("Name: <input type='text' name='name' value='" + name + "'><br>");
                        out.println("Designation: <input type='text' name='designation' value='" + designation + "'><br>");
                        out.println("Salary: <input type='text' name='salary' value='" + salary + "'><br>");
                        out.println("<input type='submit' value='Submit'>");
                        out.println("</form>");
                        out.println("</body></html>");
                    } else {
                        out.println("Employee not found");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int employeeId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String designation = request.getParameter("designation");
        BigDecimal salary = new BigDecimal(request.getParameter("salary"));

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS", "EMS", "")) {
            String sql = "UPDATE employees SET name=?, designation=?, salary=? WHERE id=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, designation);
                statement.setBigDecimal(3, salary);
                statement.setInt(4, employeeId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("viewEmployees");
    }
}