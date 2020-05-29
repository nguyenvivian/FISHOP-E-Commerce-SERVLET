import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/get-tax")
public class GetTax extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/html;charset=UTF-8"); 
			PrintWriter out = response.getWriter();
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sys?serverTimezone=US/Pacific", "root", "sql99server");
			Statement statement = connection.createStatement();
			ResultSet results = statement.executeQuery ("SELECT * FROM TAX_RATES where ZipCode="+request.getParameter("zip"));
			results.next();
			
			out.print("{\"State\":\""+results.getString("State")+"\", \"ZipCode\":\""+results.getString("ZipCode")+"\", \"TaxRegionName\":\""+results.getString("TaxRegionName")+"\", \"CombinedRate\":\""+results.getString("CombinedRate")+"\"}");
		}catch(Exception e) {e.printStackTrace();}
	}
}
