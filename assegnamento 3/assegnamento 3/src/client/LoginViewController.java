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
 * used in the FXML implementation of the login page.
 **/
public class LoginViewController {
    private Client mainApp;

    @FXML
    private TextField loginEmail;

    @FXML
    private TextField loginPassword;

    @FXML
    private Button loginButton;

    @FXML
    private TextField signupName;

    @FXML
    private TextField signupSurname;

    @FXML
    private TextField signupEmail;

    @FXML
    private TextField signupPassword;

    @FXML
    private Button signupButton;

    /**
     * This method sets event for the login and signup buttons
     */
    @FXML
    private void initialize() {
        loginButton.setOnMouseClicked(clickEvent -> this.login());

        signupButton.setOnMouseClicked(clickEvent -> {
            this.signup();
            signupName.setText("");
            signupSurname.setText("");
            signupEmail.setText("");
            signupPassword.setText("");
        });
    }

    /**
     * This method is used to verify login: if username or password are not correct, it
     * shows an warning alert, else it calls Client's method to choose the right layout
     * for admin, employee and user.
     */
    private void login() {
        Query query = new Query(QueryType.selectWithFilter, ObjectType.person);
        query.person = new Person(loginEmail.getText(), loginPassword.getText());
        this.mainApp.sendRequest(query);
        Response response = this.mainApp.waitResponse();
        Person loggedIn = response.person;
        if (loggedIn.getId() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Wrong login!");
            alert.setHeaderText(null);
            alert.setContentText("Please, check your login details.");
            alert.showAndWait();
        } else {
            this.mainApp.setLoggedIn(loggedIn);

            if (loggedIn instanceof User)
                this.mainApp.initUserLayout();
            else if (loggedIn instanceof Employee)
                this.mainApp.initEmployeeLayout();
            else if (loggedIn instanceof Admin)
                this.mainApp.initAdminLayout();
        }
    }

    /**
     * This method is used to sign up a new user: it sends you an alert message if the field have not all been completed or
     * if the email is already in use, else it calls the Client method to save the account.
     * Finally it shows a welcome message.
     */
    private void signup() {
        if (signupName.getText().equals("") || signupSurname.getText().equals("") || signupEmail.getText().equals("") || signupPassword.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty details!");
            alert.setHeaderText(null);
            alert.setContentText("Please, enter the requested infos.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Query query = new Query(QueryType.add, ObjectType.user);
            query.person = new Person(signupName.getText(), signupSurname.getText(), signupEmail.getText(), signupPassword.getText());
            this.mainApp.sendRequest(query);
            Response response = this.mainApp.waitResponse();
            if (response.statusBoolean) {
                alert.setTitle("Success!");
                alert.setHeaderText(null);
                alert.setContentText("Welcome!");
            } else {
                alert.setTitle("Failed!");
                alert.setHeaderText(null);
                alert.setContentText("This user already exists.");
            }
            alert.showAndWait();
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
