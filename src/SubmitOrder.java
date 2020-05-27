import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/submit-order")
public class SubmitOrder extends HttpServlet{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			PrintWriter out = response.getWriter();
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sys?serverTimezone=US/Pacific", "root", "sql99server");
			HttpSession session = request.getSession();
			
			RequestDispatcher requestHeader=request.getRequestDispatcher("/header.html");
			requestHeader.include(request, response);
			
			out.println("<h1>Your order was successfully placed!</h1>\n" + 
					"<p>Thank you [].&nbsp;</p>\n" + 
					"<p>Your order with the following items:</p>\n" + 
					"<p>[]</p>\n" + 
					"<p>for a total of [] has been successfully placed.</p>\n" + 
					"<p>We will be delivering it to [] [] [] [] with your contact information</p>\n" + 
					"<p>[] [] with shipping option [].</p>\n" + 
					"<p>Please shop with us again!</p>");
			
			RequestDispatcher requestFooter=request.getRequestDispatcher("/footer.html");
			requestFooter.include(request, response);
			
			out.flush();
			out.close();
		}catch (Exception e ) {System.out.println(e);}
	}
	
}
