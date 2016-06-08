# Java-Northwind
A simple program which pulls information from the Northwind Database using SQL coded into the source, and displays the data in a table view using GUI.

Important Note: The Northwind database is included in the project folder. In order to run this program, you will either have to change the file path at line 187, or create a folder in your C: dive called "Data" and put the database file into that folder.

Upon startup, the program connects to the Northwind databse with a JDBC driver.

The raw results of an executed SQL statementare first stored in an observable list, which is then placed into the tableview displayed in the program. Whenever a new SQL statement is executed, the previously displayed table is cleared and replaced with the new one.

Users may view:
The order total of a searched order number.
The order details of a searched order number.
Customer details by state in which the order was placed.
Employee birthdays in a searched year.

It is suggested that a user have the Database open during execution and testing of the program, so they know what to search for. Some test examples to search for which are in the database:

Order numbers: 10248, 10249, 10250, 10251 ....... All the way to 11077<br>
State Abbreviations: BC, SP, DF, RJ, OR, WA, CA, AK, NM, ID, WY, MT, Lara, Nueva Esparta<br>
Years: 1952, 1955, 1960, 1963, 1968, 1969
