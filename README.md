# FISHOP-ECommerce-Site SERVLET
## Author:
Name: Vivian Nguyen, UCINetId: nguyev12, ID#: 84955920
## Base URL:
http://localhost:8080/FISHOP-Servlet-2/index.html
## General Navigation/Layout
The landing page lists all the products available under their respective categories. Clicking on any product will bring the user to a product page with more information as well as the add to cart button. Users can continue adding products to their cart or they can check out. Checking out will forward the user to a purchase form. Clicking "About Us" in the navigation bar will give the user information about the company and general information as well as citations for the images used.
## Points
1. Include the output of two servlets to create the homepage for your e-commerce site: the first servlet should handle the displaying of the list of products obtained from a backend database, and the second servlet should use session tracking to display the last 5 products that the user has visited (viewed the product details page). In case this number is less than 5, show whatever amount of information you have stored in the session. You are required to use servlet "include" feature to implement this requirement. 
    - **FetchProducts is the servlet class for the website's homepage. FetchProducts outputs the product card listings for of all of the available products obtained from the SQL database. The second servlet, DisplayProducts uses session tracking in order to display the last 5 products that the user has visited during their session.**

2. Using servlets create a "product details" page. This page should take a product identifier as a parameter and show the product details after getting the relevant information from the database. This page should NOT have an order form, only a button to "Add to Cart". Use servlet "session" to store the products in a shopping cart. 
	- **ProductPage is the servlet class that takes a PRODUCT_ID as a parameter, and uses that PRODUCT_ID to draw the corresponding row in the databse. This row is then used to provide the rest of the item's details such as the name, price, extended description, and additional images. Each product page has an Add to Cart button that, when clicked, will add that product to the session's shopping cart and forward the user to an overview of the items they already have in their cart.**

3. Using servlets create a "check out" page, which allows the user to place an order. The page should show all the products in the shopping cart and the total price. This page should have a form which will allow the user to do the following:
	-Enter shipping information: name, shipping address, phone number, credit card information, etc.
	-Submit the order for storage in the backend database
	-On successful submission, forward to the order details page. You are required to use servlet "forward" feature to implement this requirement. 
		-**CheckOut is the servlet class that displays the session's shopping cart along with a purchase form. The inital sum of the product's prices are shown at the bottom. The user can begin to enter in their information. AJAX is used to dynamically display states based on the zip code and calculate tax rates and shipping costs into the total price. Users can enter their information and submit their order. If their inputs are valid and accepted by the databse, then it is stored in the table, PRODUCT_ORDERS. A successful submission will forward the user to a confirmation page in which all of their shopping cart and personal information will be detailed.**