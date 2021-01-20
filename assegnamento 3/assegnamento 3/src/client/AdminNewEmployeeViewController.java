package client;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.*;
import socketClasses.ObjectType;
import socketClasses.Query;
import socketClasses.QueryType;
import socketClasses.Response;

/**
 * This class defines the controller
 * used in the FXML implementation of the adding employee page used by admin.
 */
public class AdminNewEmployeeViewController {
    private Client mainApp;

    @FXML
    private Button backButton;

    @FXML
    private Button createButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField passwordTextField;

    /**
     * This method sets event for the buttons
     */
    @FXML
    void initialize() {
        backButton.setOnMouseClicked(clickEvent -> this.mainApp.initAdminEmployeesLayout());

        createButton.setOnMouseClicked(clickEvent -> this.signEmployee());
    }

    /**
     * This method is used to sign up a new employee: it sends you an alert message if the field have not all been completed or
     * if the email is already in use, else it calls the Client method to save the account.
     */
    private void signEmployee() {
        if (nameTextField.getText().equals("") || surnameTextField.getText().equals("") || emailTextField.getText().equals("") || passwordTextField.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty details!");
            alert.setHeaderText(null);
            alert.setContentText("Please, enter the requested infos.");
            alert.showAndWait();
        } else {
            Query query = new Query(QueryType.add, ObjectType.employee);
            query.employee = new Employee(nameTextField.getText(), surnameTextField.getText(), emailTextField.getText(), passwordTextField.getText());
            this.mainApp.sendRequest(query);
            try {
                Response response = this.mainApp.waitResponse();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if (response.statusBoolean) {
                    alert.setTitle("Success!");
                    alert.setHeaderText(null);
                    alert.setContentText("Employee created!");
                    alert.showAndWait();
                    this.mainApp.initAdminEmployeesLayout();
                } else {
                    alert.setTitle("Failed!");
                    alert.setHeaderText(null);
                    alert.setContentText("Email already used.");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is called by the Client to give a reference back to itself.
     *
     * @param mainApp the client.
     */
    public void setMainApp(Client mainApp) {
        this.mainApp = mainApp;
    }
}
