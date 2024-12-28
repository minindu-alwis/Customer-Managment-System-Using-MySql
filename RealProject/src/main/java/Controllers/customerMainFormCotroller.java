package Controllers;

import Models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class customerMainFormCotroller implements Initializable {
    public TextField cusId;
    public TextField cusName;
    public TextField cusAddress;
    public TextField cusSalary;
    public TableColumn cusIdCol;
    public TableColumn cusNameCol;
    public TableColumn cusAddressCol;
    public TableColumn cusSalaryCol;
    public TableView tblCustomer;

    public void addCustomerOnAction(ActionEvent actionEvent) throws SQLException {

        ObservableList<Customer> itemObservableList= FXCollections.observableArrayList();

        Connection connection= DriverManager.getConnection("jdbc:mysql://localhost/thogakade","root","1234");
        System.out.println(connection);

        Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery("select * from customer");
        System.out.println(resultSet);

        while(resultSet.next()){
            String idd = resultSet.getString(1);
            String namee = resultSet.getString(2);
            String addresss = resultSet.getString(3);
            Double salaryy= resultSet.getDouble(4);

            Customer customer=new Customer(idd,namee,addresss,salaryy);
            itemObservableList.add(customer);
        }

        tblCustomer.setItems(itemObservableList);






    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cusIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        cusNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        cusAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        cusSalaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    public void addaCustomerOnAction(ActionEvent actionEvent) throws SQLException {
        String id=cusId.getText();
        String name=cusName.getText();
        String address=cusAddress.getText();
        Double salary=Double.parseDouble(cusSalary.getText());

        Customer customer=new Customer(id, name, address, salary);

        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost/thogakade", "root", "1234");

        String sql = "insert into customer (id, name, address, salary) values (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, address);
        preparedStatement.setDouble(4, salary);

        int addToTable = preparedStatement.executeUpdate();

        if (addToTable > 0) {
            givealert();
            clear();
        } else {
            clear();
            System.out.println("Failed to add customer.");
        }

        preparedStatement.close();
        connection.close();
    }

    public void clear(){

        cusId.clear();
        cusName.clear();
        cusAddress.clear();
        cusSalary.clear();

    }

    public void givealert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Adding");
        alert.setHeaderText(null);
        alert.setContentText("Added SuccsessFully !!!!!!");
        alert.show();
    }

}
