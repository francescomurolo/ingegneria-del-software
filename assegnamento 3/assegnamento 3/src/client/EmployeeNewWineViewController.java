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
 * used in the FXML implementation of the adding wine page used by employee.
 */
public class EmployeeNewWineViewController {
    private Client mainApp;

    @FXML
    private Button backButton;

    @FXML
    private Button createButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField producerTextField;

    @FXML
    private TextField yearTextField;

    @FXML
    private TextField notesTextField;

    @FXML
    private TextField vinesTextField;

    @FXML
    private TextField quantityTextField;

    /**
     * This method sets event for the buttons.
     */
    @FXML
    void initialize() {
        backButton.setOnMouseClicked(clickEvent -> this.mainApp.initEmployeeWinesLayout());

        createButton.setOnMouseClicked(clickEvent -> this.createWine());
    }

    /**
     * This method is used to create a new wine: it sends you an alert message if the field have not all been completed,
     * else it calls the Client method to save the wine.
     */
    private void createWine(){
        if (nameTextField.getText().equals("") || producerTextField.getText().equals("") || yearTextField.getText().equals("") || notesTextField.getText().equals("") || vinesTextField.getText().equals("") || quantityTextField.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty details!");
            alert.setHeaderText(null);
            alert.setContentText("Please, enter the requested infos.");
            alert.showAndWait();
        } else {
            Query query = new Query(QueryType.add, ObjectType.wine);
            query.wine = new Wine(nameTextField.getText(),producerTextField.getText(),yearTextField.getText(),notesTextField.getText(),vinesTextField.getText(),Integer.parseInt(quantityTextField.getText()));
            this.mainApp.sendRequest(query);
            try {
                Response response = this.mainApp.waitResponse();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if (response.statusBoolean){
                    alert.setTitle("Success!");
                    alert.setHeaderText(null);
                    alert.setContentText("Wine created!");
                    alert.showAndWait();
                    this.mainApp.initEmployeeWinesLayout();
                } else {
                    alert.setTitle("Failed!");
                    alert.setHeaderText(null);
                    alert.setContentText("Error. Try again.");
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

        quantityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                quantityTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        yearTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                yearTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
}
