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

@WebServlet("/product-page")
public class ProductPage extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			ArrayList<String> prevVisited = (ArrayList<String>) session.getAttribute("prevVisited");
			if (prevVisited.size()==5) {
				prevVisited.remove(0);}
			prevVisited.add(request.getParameter("PRODUCT_ID"));			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sys?serverTimezone=US/Pacific", "root", "sql99server");
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery ("select * from product_CARD as pc INNER JOIN PRODUCT_DETAIL as pd WHERE pc.PRODUCT_ID = pd.PRODUCT_ID and pc.PRODUCT_ID="+request.getParameter("PRODUCT_ID"));
			result.next();
			response.setContentType("text/html;charset=UTF-8"); 
			PrintWriter out = response.getWriter();
			out.println(session.getId());
			out.println(prevVisited);
			out.println("<!DOCTYPE html>\n" + 
					"<html>\n" + 
					"\n" + 
					"<head>\n" + 
					"    <title>FISHOP</title>\n" + 
					"    <link rel=\"stylesheet\" href=\"product-page.css\">\n" + 
					"    <link rel=\"stylesheet\" href=\"main.css\">\n" + 
					"</head>\n" + 
					"\n" + 
					"<!-- BEGIN HEADER -->\n" + 
					"<div>\n" + 
					"    <a href=\"index.html\">\n" + 
					"        <img class=logo src=\"img/logo.png\" alt=\"logo\">\n" + 
					"    </a>\n" + 
					"    <div class=\"caption\">\n" + 
					"        <p>THE #1 FISH SHOP FOR YOUR ALL YOUR FISH NEEDS</p>\n" + 
					"    </div>\n" + 
					"</div>\n" + 
					"\n" + 
					"<div class=\"nav\">\n" + 
					"    <a href=\"index.html\">SHOP</a>\n" + 
					"    <a href=\"about.html\">ABOUT US</a>\n" + 
					"</div>\n" + 
					"<!-- END HEADER -->\n");
			
			out.println("<!-- BEGIN MIDDLE -->\n" + 
					"<div class=\"listing\" id=\""+ result.getString("PATH") +"\">\n" + 
					"    <header>"+result.getString("NAME")+"</header>\n" + 
					"    <div class=\" picture-carousel\" id=\"pictureCarousel\">\n" + 
					"        <a class=\"prev\" onclick=\"showPicture(-1)\">\n" + 
					"            < </a>\n" + 
					"                <a class=\"next\" onclick=\"showPicture(1)\">></a>\n" + 
					"    </div>\n" + 
					"    <p>PRICE (each):" +result.getFloat("PRICE")+ "</p>\n" + 
					"<p><form style='display:flex; justify-content:center;' action='shopping-cart' name='PRODUCT_ID' method='get'><button name='PRODUCT_ID' type=\"submit\" value='"+request.getParameter("PRODUCT_ID")+"'> Add to Cart </button></form></p>" +
					"</div>\n" + 
					"<div class=\"extended-description\">\n" + 
					"    <p>" + result.getString("DETAILED_DESCRIPTION") +
					"    </p>\n" + 
					"</div>\n" + 
					"<!-- END MIDDLE -->");
			
			out.println("<!-- BEGIN FOOTER -->\n" + 
					"<div class=\"nav\">\n" + 
					"    <a1>Name: Vivian Nguyen / UCINetID: nguyev12 / ID #: 84955920 / Machine Number: 27</a1>\n" + 
					"</div>\n" + 
					"<!-- END FOOTER -->\n" + 
					"<script src=\"main.js\"></script>\n" + 
					"\n" + 
					"</html>");
			
			
			
			result.close();
			out.flush();
			out.close();
			statement.close();
			connection.close();
		}catch(Exception e) {System.out.println(e);}
	}
}