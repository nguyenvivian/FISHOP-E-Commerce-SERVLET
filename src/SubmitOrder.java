import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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
					"<p>Thank you, "+request.getParameter("fname")+" "+request.getParameter("lname")+"</p>\n" + 
					"<p>Your order with the following items:</p>\n");
			
			
			ArrayList<String> shoppingCart = (ArrayList<String>) session.getAttribute("shoppingCart");
			for (int i = 0; i < shoppingCart.size(); ++i) {
				Statement statement = connection.createStatement();
				ResultSet results = statement.executeQuery ("SELECT * FROM PRODUCT_CARD where product_id="+shoppingCart.get(i));
				results.next();
				out.println(
						"<p style='color: red;'> - "+results.getString("NAME")+"</p>\n"
				);
				results.close();
				statement.close();
			}
			
			
			out.println(
					"<p>for a total of $"+request.getParameter("total")+" has been successfully placed.</p>\n" + 
					"<p>We will be delivering it to "+request.getParameter("street")+" "+request.getParameter("city")+" "+request.getParameter("state")+" "+request.getParameter("postalCode")+" with your contact information</p>\n" + 
					"<p>"+request.getParameter("phoneNumber")+" with shipping option "+" "+request.getParameter("shippingMethod")+".</p>\n" + 
					"<p>Please shop with us again!</p>");
			
			RequestDispatcher requestFooter=request.getRequestDispatcher("/footer.html");
			requestFooter.include(request, response);
			
			out.flush();
			out.close();
		}catch (Exception e ) {System.out.println(e);}
	}
	
}
