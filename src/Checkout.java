import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@WebServlet("/checkout")
public class Checkout extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/html;charset=UTF-8"); 
			PrintWriter out = response.getWriter();
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sys?serverTimezone=US/Pacific", "root", "sql99server");
			HttpSession session = request.getSession();
			ArrayList<String> shoppingCart = (ArrayList<String>) session.getAttribute("shoppingCart");

			RequestDispatcher requestHeader=request.getRequestDispatcher("/header.html");
			requestHeader.include(request, response);

			out.println("<div>");
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
			float prePrice = (float) 0.0;
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
				prePrice += results.getFloat("PRICE");
				results.close();
				statement.close();
			}
			out.println(		
					"</tbody>\n" + 
					"</table>");
			out.println("<div>");
			
			BigDecimal totalPrice = (new BigDecimal(prePrice)).setScale(2, BigDecimal.ROUND_HALF_UP);
			out.println(
					"        <form style='display:block;' id='myForm' onsubmit='return checkForm();' action='checkout' method='post'>\n" + 
					"            <p style='color:darkred;' class='error' id='error'></p>\n" + 
					"            <label for='fname'>First name:</label>\n" + 
					"            <input style='display:block;' type='text' name='fname' id='fname'>\n" + 
					"            <label for='lname'>Last name:</label>\n" + 
					"            <input style='display:block;' type='text' name='lname' id='lname'>\n" + 
					"            <label for='phoneNumber'>Phone Number (XXXXXXXXXX): </label>\n" + 
					"            <input style='display:block;' type='text' name='phoneNumber' id='phoneNumber'>\n" + 
					"            <label for='street'>Street:</label>\n" + 
					"            <input style='display:block;' type='text' name='street' id='street'>\n" + 
					"            <label for='city'>City:</label>\n" + 
					"            <input style='display:block;' type='text' name='city' id='city'>\n" + 
					"            <label for='state'>State:</label>\n" + 
					"            <input style='display:block;' type='text' name='state' id='state'>\n" + 
					"            <label for='postalCode'>Postal Code (XXXXX):</label>\n" + 
					"            <input style='display:block;' type='text' name='postalCode' id='postalCode'  onblur='return calcTax(this.value,"+totalPrice+");'>\n" + 
					"            <label for='shippingMethod'>Shipping Method:</label>\n" + 
					"            <select style='display:block;' id='shippingMethod' name='shippingMethod' onChange='return addShipping(this.value);'>\n" + 
					"                <option value='default' selected='selected'>Select From List</option>\n" + 
					"                <option>Overnight $20 </option>\n" + 
					"                <option>2 Days Expedited $15</option>\n" + 
					"                <option>6 Days Ground $10</option>\n" + 
					"            </select>"+
					"            <label for='ccnumber'>Credit Card Number (XXXXXXXXXXXXXXXX):</label>\n" + 
					"            <input style='display:block;' type='text' name='ccnumber' id='ccnumber'>\n" + 
					"            <label for='ccv'>CCV (XXX) or (XXXX):</label>\n" + 
					"            <input style='display:block;' type='text' name='ccv' id='ccv'>\n" + 
					"			 <label for=\"total\">Total Price + Shipping + Tax Rate (<a id='taxrate' name='taxrate'></a>): </label>\n" +
					"			 <input style='width: 70px;' id='total' name='total' readonly='readonly' value='"+totalPrice+"'>" +
					"            <input style='display:block;' type='submit' id='submit'>\n" + 

					"        </form>\n");
					

			session.setAttribute("TotalPrice", totalPrice);
			RequestDispatcher requestFooter=request.getRequestDispatcher("/footer.html");
			requestFooter.include(request, response);
			out.flush();
			out.close();
		}catch (Exception e) {System.out.println(e);}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sys?serverTimezone=US/Pacific", "root", "sql99server");

			String address = request.getParameter("street")+' '+request.getParameter("city")+", "+request.getParameter("state")+' '+request.getParameter("postalCode");
			String sql = "insert into product_order values(?, ?, ?, ?, ?, ?, ?, ?, ?); ";
			
			HttpSession session = request.getSession();
			ArrayList<String> shoppingCart = (ArrayList<String>) session.getAttribute("shoppingCart");

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, request.getParameter("fname"));
			preparedStatement.setString(2, request.getParameter("lname"));
			preparedStatement.setString(3, request.getParameter("phoneNumber"));
			preparedStatement.setString(4, address);
			preparedStatement.setString(5, request.getParameter("shippingMethod"));
			preparedStatement.setString(6, request.getParameter("ccnumber"));
			preparedStatement.setString(7, request.getParameter("ccv"));
			preparedStatement.setString(8, shoppingCart.toString());
			preparedStatement.setString(9, session.getAttribute("TotalPrice").toString());
			
			preparedStatement.addBatch();
			int[] affectedRecords = preparedStatement.executeBatch();
			
			RequestDispatcher requestConfirmation = request.getRequestDispatcher("/submit-order");
			requestConfirmation.forward(request,response);
		}
		catch (Exception e ) {
			e.printStackTrace();
		}
	}
}
