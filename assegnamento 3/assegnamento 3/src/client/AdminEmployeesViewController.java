package client;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.*;
import socketClasses.ObjectType;
import socketClasses.Query;
import socketClasses.QueryType;
import socketClasses.Response;

/**
 * This class defines the controller
 * used in the FXML implementation of the all employees page used by admin.
 */
public class AdminEmployeesViewController {
    private Client mainApp;

    @FXML
    private Button backButton;

    @FXML
    private Button createButton;

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TableColumn<Employee, String> surnameColumn;

    @FXML
    private TableColumn<Employee, String> emailColumn;

    @FXML
    private TableColumn<Employee, String> passwordColumn;

    /**
     * This method sets event for the buttons and shows all employees.
     */
    @FXML
    void initialize() {
        backButton.setOnMouseClicked(clickEvent -> this.mainApp.initAdminLayout());

        createButton.setOnMouseClicked(clickEvent -> this.mainApp.initAdminNewEmployeeLayout());

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        surnameColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
    }

    /**
     * This method is called by the Client to give a reference back to itself.
     *
     * @param mainApp the client.
     */
    public void setMainApp(Client mainApp) {
        this.mainApp = mainApp;
        this.updateTable();
    }

    /**
     * This method updates the data in the employee table.
     */
    private void updateTable() {
        Query query = new Query(QueryType.select, ObjectType.employee);
        this.mainApp.sendRequest(query);
        Response response = this.mainApp.waitResponse();
        employeeTable.setItems(FXCollections.observableArrayList(response.employeeList));
    }
}
