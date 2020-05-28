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

@WebServlet("/fetch-product")
public class FetchProduct extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/html;charset=UTF-8"); 
			PrintWriter out = response.getWriter();
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sys?serverTimezone=US/Pacific", "root", "sql99server");
			Statement statement = connection.createStatement();
			ResultSet results = statement.executeQuery ("SELECT * FROM PRODUCT_CARD");
			
			out.println(
					"<!-- BEGIN MIDDLE SECTION -->\n" + 
					"<div class='grid-container'>\n" + 
					"    <!-- BEGIN LEFT NAVIGATION PANEL -->\n" + 
					"    <div class='left' id='left-nav'>\n" + 
					"        <header>SHOP</header>\n" + 
					"        <a class='left-nav' href='#fish-food'>FISH PRODUCTS</a>\n" + 
					"    </div>\n" + 
					"    <!-- END LEFT NAVIFATION PANEL -->\n" + 
					"\n" + 
					"    <div class='middle'>\n" + 
					"        <div class='product-header' id='fish-food'>FISH PRODUCTS</div>\n" + 
					"            <div class='product'>\n");
			
			while (results.next()) {
				out.println(
						
						"                <div class='product-card'>\n" + 
						"                    <a name='product_id' value='"+results.getInt("PRODUCT_ID")+"' href='product-page?PRODUCT_ID="+results.getInt("PRODUCT_ID")+"'>\n" + 
						"                        <img src='"+results.getString("PATH")+"' alt='api tropical fish food' style='"+results.getString("DIMENSION")+"'>\n" + 
						"                        <product-name>"+results.getString("NAME")+"</product-name>\n" + 
						"                        <product-price>"+results.getFloat("PRICE")+"</product-price>\n" + 
						"                        <product-description>"+results.getString("DESCRIPTION")+"</product-description>\n" + 
						"                        <product-description>"+results.getString("WEIGHT")+"</product-description>\n" + 
						"                        <p><button>More Details</button></p>\n" + 
						"                    </a>\n" + 
						"                </div>\n");
			}
			
			out.println(
					"            </div>\n" + 
					"        </div>\n" + 
					"    </div>\n" + 
					"</div>\n" + 
					"<!-- END MIDDLE SECTION -->");
			
			out.flush();
			out.close();
			results.close();
			statement.close();
			connection.close();
		}catch(Exception e) {System.out.println(e);}
	}
}
