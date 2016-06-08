/*
Author: Yulia Bachman
Program: Query the Northwind Database
Date: 2/27/2016

Note: The Northwind database is included in the project folder. 
In order to run this program, you will either have to change the file path at line 187,
or create a folder in your C: dive called Data and put the file into that folder.
*/

package ybachman_midterm_northwind;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Ybachman_Midterm_Northwind extends Application 
{    
    //Create global Observable List
    ObservableList<ObservableList> data;
    
    //Create global table
    TableView table = new TableView();
    
    //Statement for the database
    private Statement statement;
    
    //Labels
    private Label instructionLabel = new Label("Select An Option From the Menu");
    private Label tableResultsLabel = new Label("Table Results");
    
    //Text Field
    private TextField instructiontf = new TextField(); 
    
    //Create the Combo box
    ComboBox<String> queryOptions = new ComboBox<>();
    
    //Submit Button
    Button submit = new Button("Submit");
    
    @Override
    public void start(Stage stage) 
    {
        stage.setTitle("Northwind Database");
        stage.setResizable(false);
        
        //Initialize the database
        initializeDB();
        
        //Add Data table names to combo box
        queryOptions.getItems().addAll("Print Order Total", "Print Order Details", 
                "Print Customer Details", "Print Employee Birthdays");
        queryOptions.setStyle("-fx-color: green");
        //Set initial string for combo box
        queryOptions.setValue("Select Action");
        
        /**********************************************
        Create the UI for interaction with the Database 
        ***********************************************/
        
        //Main pane to hold everything
        BorderPane mainPane = new BorderPane();
        
        //Borderpane to place on the Left
        BorderPane leftPane = new BorderPane();
        leftPane.setStyle("-fx-border-color: green");
        //Add ComboBox
        leftPane.setTop(queryOptions);
        
        //GridPane to hold instructions
        GridPane instructionBox = new GridPane();
        Label space = new Label("");
        Label space1 = new Label("");
        Label space2 = new Label("");
        Label space3 = new Label("");
        Label space4 = new Label("");
        Label space5 = new Label("");
        Label space6 = new Label("");
        //Add Space for appearance
        instructionBox.add(space, 0, 0);
        instructionBox.add(space1, 0, 1);
        instructionBox.add(space2, 0, 2);
        instructionBox.add(space3, 0, 3);
        instructionBox.add(space4, 0, 4);
        instructionBox.add(space5, 0, 5);
        instructionBox.add(space6, 0, 6);
        //Add Instruction Label
        instructionBox.add(instructionLabel, 0, 7);
        //Add Text Field for user input
        instructionBox.add(instructiontf, 0, 8);
        instructiontf.setEditable(false); //Prevents users entering information until they select from the menu
        //Add Submit Button
        instructionBox.add(submit, 0, 9);
        
        //Add GridPane to the left BorderPane
        leftPane.setCenter(instructionBox);
        
        //Vbox for table results to be placed on the right
        VBox rightPane = new VBox();
        rightPane.setStyle("-fx-border-color: green");
        rightPane.setSpacing(5);
        rightPane.setPadding(new Insets(10, 0, 0, 10));
        rightPane.getChildren().add(tableResultsLabel);
        
        //Add the Table to the right VBox
        rightPane.getChildren().add(table);
        
        //Add the left and right panes to the mainPane
        mainPane.setLeft(leftPane);
        mainPane.setCenter(rightPane);
        
        //Add action handlers to display tables as they are selected from the combo box
        queryOptions.setOnAction(e -> 
            {
                //Clear text field for input
                instructiontf.clear();
                
                //Users can now enter information
                instructiontf.setEditable(true);
                
                //Get further information to complete the query base on selection action
                //Change the instruction label to ask for information
                switch (queryOptions.getValue())
                {
                    case "Print Order Total":
                        instructionLabel.setText("Please enter an Order Number");
                        break;
                    case "Print Order Details":
                        instructionLabel.setText("Please enter an Order Number");
                        break;
                    case "Print Customer Details":
                        instructionLabel.setText("Please enter a Region Abbreviation");
                        break;
                    case "Print Employee Birthdays":
                        instructionLabel.setText("Please enter a Year");
                        break;
                }
                
                //Submit button Action Handler
                submit.setOnAction(e2 -> 
                {
                    //Clear previously displayed Table
                    table.getColumns().clear();
                    //Call the showTable Method
                    showTable(queryOptions.getValue(), instructiontf.getText());
                });
            }
        );
        
        //Add mainPane to the scene
        Scene scene = new Scene(mainPane, 800, 400);
        stage.setScene(scene);
        stage.show();
    }
    
    //Intitialize database function
    private void initializeDB() 
    {
        try 
            {
              //Load the JDBC driver
              Class.forName("jstels.jdbc.mdb.MDBDriver2");
              System.out.println("Driver loaded");

              //Establish a connection
              Connection connection = DriverManager.getConnection
                ("jdbc:jstels:mdb:C:/Data/Northwind.mdb");
              System.out.println("Database connected");

              //Create a statement
              statement = connection.createStatement();
            }
        catch (Exception ex) 
            {
              ex.printStackTrace();
            }
    }
    
    private void showTable(String selectedAction, String actionInformation)
    {
        //String variable for the different sql command options
        String sqlCommand = "Temp";
        
        //Switch statement for differnt SQL command options based on action combo box
        switch (selectedAction)
        {
            case "Print Order Total": 
                sqlCommand = "SELECT SUM(CAST((unitprice * quantity) - (unitprice * discount) "
                        + "as numeric(10,2))) AS \"Order Total\" FROM \"Order Details\" "
                        + "WHERE orderid = " + actionInformation;
                break;
            case "Print Order Details":
                sqlCommand = "SELECT Orders.orderid, Orders.orderdate, CAST(Orders.freight as numeric(10,2)) "
                        + "AS freight, Products.productname, CAST(\"Order Details\".unitprice as numeric(10,2)) "
                        + "AS unitprice, \"Order Details\".quantity, CAST(\"Order Details\".discount as numeric(10,2)) "
                        + "AS discount FROM Orders INNER JOIN \"Order Details\" ON Orders.orderid = \"Order Details\".orderid\n"
                        +"INNER JOIN Products ON \"Order Details\".productid = Products.productid\n"
                        +"WHERE Orders.orderid = " + actionInformation;
                        
                break;
            case "Print Customer Details":
                sqlCommand = "SELECT companyname, region, country, city FROM Customers "
                        + "WHERE region = '" + actionInformation + "' ORDER BY city";
                break;
            case "Print Employee Birthdays":
                sqlCommand = "SELECT lastname, firstname, birthdate FROM Employees "
                        + "WHERE birthdate LIKE '%" + actionInformation + "%' ORDER BY lastname";
                break;
        }
        
        //Set ArrayList to collections
        data = FXCollections.observableArrayList();
        
        //Set Table width
        table.setPrefWidth(750);
        
        try {
            //Execute a SELECT SQL command
            ResultSet resultSet = statement.executeQuery(sqlCommand);

            //Dynamically add the columns from the query to the table
            //Columns are metadata
            for(int i = 0 ; i < resultSet.getMetaData().getColumnCount(); i++)
            {
                //Index counter
                final int j = i;  
                
                //For each column name from the database table, add that column name to the observable list
                TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>()
                {                    
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) 
                    {                  
                        //Get the value from the data table column to name the table view columns
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });

                //Add columns to the table
                table.getColumns().addAll(col); 
            }
            
            //For each query result, Add the data to the Observable List
            while(resultSet.next())
            {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                //For each table, the column number is metadata to add to the list
                for(int i = 1 ; i <= resultSet.getMetaData().getColumnCount(); i++)
                {
                    //Iterate Column, check for a null value
                    if (resultSet.getString(i) == null)
                    {
                        //If null, fill the cell with "Null"
                        row.add("Null");
                    }
                    else
                    {
                        //If not null, fill the cell with the string from the database table
                        row.add(resultSet.getString(i));
                    }
                }
                
                //Add the row to the list
                data.add(row);
            }
            
            //Take the Observable List and set it to the table
            table.setItems(data);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error with Displaying Data");   
        }
    }
}
