# Java-Northwind
A simple program which pulls information from the Northwind Database using SQL coded into the source, and displays the data in table view using GUI.

Important Note: The Northwind database is included in the project folder. In order to run this program, you will either have to change the file path at line 187, or create a folder in your C: dive called Data and put the file into that folder.

Upon start up, the program connects to the Northwind databse with a JDBC driver.

The raw results of an executed SQL statementare first stored in an observable list, which is then placed into the tableview displayed in the program. Whenever a new SQL statement is executed, the previously displayed table is cleared and replaced with the new one.

Users may view:
The order total of a searched order number.
The order details of a searched order number.
Customer details by state in which the order was placed.
Employee birthdays in a searched year.
