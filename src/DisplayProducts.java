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

@WebServlet("/DisplayProducts")
public class DisplayProducts extends HttpServlet{
	ArrayList<String> prevVisited = new ArrayList<>();
	String session_id = new String("sessionId");
	String prevVisitedKey = new String("prevVisited");
    Integer numVisits = 0;
    String numVisitsKey = new String("numVisits");
	ArrayList<String> shoppingCart = new ArrayList<>();
	String shoppingCartKey = new String("shoppingCart");

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/html;charset=UTF-8"); 
			PrintWriter out = response.getWriter();
		    HttpSession session = request.getSession(true);
		    
			if (session.isNew()) {
			   session.setAttribute(session_id, session.getId());
			} else {
				numVisits = (Integer)session.getAttribute(numVisitsKey); 
			   ++numVisits;
			}
			session.setAttribute(shoppingCartKey, shoppingCart);
			session.setAttribute(prevVisitedKey, prevVisited);
			session.setAttribute(numVisitsKey,  numVisits);
			out.println("SessionID: "+ session.getId());
			out.println("Visit #: "+ numVisits);
				
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sys?serverTimezone=US/Pacific", "root", "sql99server");
			
			RequestDispatcher requestHeader=request.getRequestDispatcher("/header.html");
			requestHeader.include(request, response);

			out.println(
					"<html>"
					+ "<div class='middle'>\n" 
							+ "<div class='product-header' id='fish-products'>Previously Viewed Products:</div>\n");
			
			if (!prevVisited.isEmpty()) {
				for (int i = 0; i < prevVisited.size(); ++i) {
					Statement statement = connection.createStatement();
					ResultSet results = statement.executeQuery ("SELECT * FROM PRODUCT_CARD where product_id="+prevVisited.get(i));
					results.next();
					out.println(
							"<a style='display:block;' href='product-page?PRODUCT_ID="+results.getInt("PRODUCT_ID")+"'>"+(i+1)+". "+results.getString("NAME")+"</a>\n"
					);
					results.close();
					statement.close();
				}
			}
			
			out.println(" </div></div></html>");
			
			connection.close();

			RequestDispatcher requestMiddle=request.getRequestDispatcher("/fetch-product");
			requestMiddle.include(request, response);
						
			RequestDispatcher requestFooter=request.getRequestDispatcher("/footer.html");
			requestFooter.include(request, response);
			
			out.flush();
			out.close();
		
		}catch(Exception e) {System.out.println(e);}
	}
}