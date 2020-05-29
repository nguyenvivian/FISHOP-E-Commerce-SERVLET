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

@WebServlet("/shopping-cart")
public class ShoppingCart extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/html;charset=UTF-8"); 
			PrintWriter out = response.getWriter();
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sys?serverTimezone=US/Pacific", "root", "sql99server");
			
			RequestDispatcher requestHeader=request.getRequestDispatcher("/header.html");
			requestHeader.include(request, response);
			HttpSession session = request.getSession();
			
			System.out.println(request.getParameter("PRODUCT_ID"));
			
			ArrayList<String> shoppingCart = (ArrayList<String>) session.getAttribute("shoppingCart");
			shoppingCart.add(request.getParameter("PRODUCT_ID"));			
			
			out.println(
					"<h1><strong>Shopping Cart</strong></h1>\n" + 
					"<table style='border: 1px solid black;'>\n" + 
					"<thead>\n" + 
					"<tr>\n" + 
					"<td style='border: 1px solid black;'>PRODUCT ID</td>\n" + 
					"<td style='border: 1px solid black;'>NAME</td>\n" + 
					"<td style='border: 1px solid black;'>DESCRIPTION</td>\n" + 
					"<td style='border: 1px solid black;'>PRICE</td>\n" + 
					"</tr>\n" + 
					"</thead>\n" + 
					"<tbody >\n" );
			
			for (int i = 0; i < ((ArrayList<String>) session.getAttribute("shoppingCart")).size(); ++i) {
				Statement statement = connection.createStatement();
				ResultSet results = statement.executeQuery ("SELECT * FROM PRODUCT_CARD where product_id="+shoppingCart.get(i));
				results.next();
				out.println(
						"<tr>\n" +
						"<td style='border-left: 1px solid black;'>"+results.getInt("PRODUCT_ID")+"</td>\n" + 
						"<td style='border-left: 1px solid black;'>"+results.getString("NAME")+"</td>\n" + 
						"<td style='border-left: 1px solid black;'>"+results.getString("DESCRIPTION")+"</td>\n" + 
						"<td style='border-left: 1px solid black;'>"+results.getFloat("PRICE")+"</td>\n" +
						"</tr>\n"
				);
				results.close();
				statement.close();
			}
					
			out.println(		
					"</tbody>\n" + 
					"</table>");
			
			out.println(
					"<p><form action='checkout' name='checkout' method='get'><button name='shoppingCart' type=\"submit\" value='"+session.getAttribute("shoppingCart")+"'> Check Out </button></form></p>"
					);
			
			RequestDispatcher requestFooter=request.getRequestDispatcher("/footer.html");
			requestFooter.include(request, response);
			
			out.flush();
			out.close();
	
		}catch(Exception e) {System.out.println(e);}
		
	}
}