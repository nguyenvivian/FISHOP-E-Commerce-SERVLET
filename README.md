# FISHOP-ECommerce-Site
## Author:
Name: Vivian Nguyen, UCINetId: nguyev12, ID#: 84955920
## Base URL:
http://circinus-27.ics.uci.edu:8080/project1/index.html
## General Navigation/Layout
The landing page lists all the products available under their respective categories. Clicking on any product will bring the user to a product page with more information as well as the purchase form. Clicking "About Us" in the navigation bar will give the user information about the company and general information.
## Points
1. An overview of your business, the products you sell, the management team, and any other information that you think makes sense for the customers to know about your company.  
   - **"About Us" in the navigation bar will list all of this information.**
2. Display a list of products (at least 10) available for sale in a table with multiple rows and column, where each product is shown within a separate cell.
   - **The landing page shows 4 fish food items, and 6 live fish products.**
3. Display an image for each product available for sale in each cell.
   - **Each product has an image. Each product's dedicated product page has three images.**
4. Display the price and other key information (e.g., color, material, etc.) associated with each product in the corresponding table cell.
   - **Every product either has Ingredients (fish food) or Color (live fish), a corresponding weight, and a price.**
5. The user should be able to choose a product from this table by clicking on the corresponding image, which should lead to a new page that provides additional details about the product, e.g., more images, detailed description, etc. 
   - **The user can click on the image, button, or title of the product to be taken to that product's dedicated product page. Each product has three supplementary images as well as a detailed description.**
6. On the detailed description page, the user should be able to order a product by filling a form. The form should allow the user to enter the product identifier, quantity, first name, last name, phone number, shipping address, shipping method (e.g., overnight, 2-days expedited, 6-days ground), credit card information, and anything else that you think makes sense for your business.
   - **Every product has a purchase form that must be filled out completely in order to send an e-mail.**
7. Upon submitting the form, the website should send an email with the purchase order information included in the body of the email. Note that to really send an email, one needs to connect to the SMTP server, which is not possible with the client-side software. Thus, this requirement simply requires bringing up the email client with the purchase order information included in the body of the email and allowing the user to send the email. 
   - **If the user fills out the form correctly, then the user's e-mail client will pop up with all of the purchase form filled out in the body of the e-mail.**
8. Before submitting the form, it should be checked for proper formatting, including whether all fields are filled properly, whether the phone number, address, and credit card are properly formatted, etc. An alarm should be raised if a field is empty or not properly formatted to prevent submission of bad data. 
   - **The purchase form cannot have any empty fields, and inputs with specific requirements have example formats that must be followed. If the user encounters an error, the page will reflect all the errors that must be fixed before proceeding.**
9. Your website should use CSS to specify at least 10 stylistic properties for your website, such as the background for your table, the color and size of your font, the size of your images, and other stylistic preferences you may have.
   - **There are more than 10 stylistic properties for each the landing page and the product pages. These properties are located in main.css and product-page.css.**
10. Provide the ability to track the mouse movement, such that when the mouse moves over a product image, the size of the image is increased, and when the mouse moves out, the size of the image is returned back to normal. This feature can be implemented on either the page that displays the various products, or on the pages that show the details of each product, or both.
    - **Every image on the landing page will be scaled 2x when the cursor hovers over it. Every image on the detailed product page will be magnified 1.1x when hovered over.**
