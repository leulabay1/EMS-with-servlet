import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;

@WebServlet("/add")
public class AddEmployeeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Sresponse.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        // HTML form directly in the servlet
        response.getWriter().println("<!DOCTYPE html>");
        response.getWriter().println("<html lang=\"en\">");
        response.getWriter().println("<head>");
        response.getWriter().println("<meta charset=\"UTF-8\">");
        response.getWriter().println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        response.getWriter().println("<title>Add Employee</title>");
        response.getWriter().println("</head>");
        response.getWriter().println("<body>");
        response.getWriter().println("<h2>Add Employee</h2>");
        response.getWriter().println("<form action=\"addEmployee\" method=\"post\">");
        response.getWriter().println("<label for=\"name\">Name:</label>");
        response.getWriter().println("<input type=\"text\" id=\"name\" name=\"name\" required><br>");
        response.getWriter().println("<label for=\"designation\">Designation:</label>");
        response.getWriter().println("<input type=\"text\" id=\"designation\" name=\"designation\" required><br>");
        response.getWriter().println("<label for=\"salary\">Salary:</label>");
        response.getWriter().println("<input type=\"number\" id=\"salary\" name=\"salary\" required><br>");
        response.getWriter().println("<button type=\"submit\">Add Employee</button>");
        response.getWriter().println("</form>");
        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String designation = request.getParameter("designation");
        BigDecimal salary = new BigDecimal(request.getParameter("salary"));

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS", "root", "")) {
            String sql = "INSERT INTO employees (name, designation, salary) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, designation);
                statement.setBigDecimal(3, salary);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("/view");
    }
}