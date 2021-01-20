package client;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;
import socketClasses.ObjectType;
import socketClasses.Query;
import socketClasses.QueryType;
import socketClasses.Response;

import java.util.Optional;

/**
 * This class defines the controller
 * used in the FXML implementation of the all requests page used by employee.
 */
public class EmployeeRequestsViewController {
    private Client mainApp;

    @FXML
    private Button backButton;

    @FXML
    private Button processButton;

    @FXML
    private CheckBox requestCheckBox;

    @FXML
    private TableView<Request> requestTable;

    @FXML
    private TableColumn<Request, String> wineColumn;

    @FXML
    private TableColumn<Request, String> userColumn;

    @FXML
    private TableColumn<Request, Integer> quantityRequiredColumn;

    @FXML
    private TableColumn<Request, Integer> quantityPresentColumn;

    @FXML
    private TableColumn<Request, String> statusColumn;

    /**
     * This method sets event for the buttons, table and shows the requests.
     */
    @FXML
    void initialize() {
        backButton.setOnMouseClicked(clickEvent -> this.mainApp.initEmployeeLayout());

        requestCheckBox.setOnMouseClicked(clickEvent -> {
            this.updateTable();
            processButton.setDisable(true);
        });

        processButton.setOnMouseClicked(MouseEvent -> {
            this.process();
            this.reset();
        });

        requestTable.setOnMouseClicked( event -> {
            if(requestTable.getSelectionModel().getSelectedItem() != null) {
                processButton.setDisable(requestTable.getSelectionModel().getSelectedItem().processedProperty().asObject().get());
            }
        });

        wineColumn.setCellValueFactory(cellData -> cellData.getValue().getWine().nameProperty());
        quantityPresentColumn.setCellValueFactory(cellData -> cellData.getValue().getWine().quantityProperty().asObject());
        userColumn.setCellValueFactory(cellData -> cellData.getValue().getUser().emailProperty());
        quantityRequiredColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().getRequestStatus());
    }

    /**
     * This method is used to reset the table and process button.
     */
    private void reset(){
        requestTable.getSelectionModel().clearSelection();
        processButton.setDisable(true);
    }

    /**
     * This method is used to process a request.
     */
    private void process() {
        TextInputDialog requestDialog = new TextInputDialog();
        requestDialog.setTitle("Processing request");
        requestDialog.setHeaderText("You are processing the request");
        requestDialog.setContentText("Please enter the new quantity of wine available");
        TextField quantityEditor = requestDialog.getEditor();
        quantityEditor.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                quantityEditor.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        Optional<String> result = requestDialog.showAndWait();
        result.ifPresent(quantity -> {
            try {
                int newQuantity = Integer.parseInt(quantity);
                if (newQuantity == 0 || newQuantity < requestTable.getSelectionModel().getSelectedItem().getQuantity()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Process error!");
                    alert.setHeaderText("Invalid quantity!");
                    alert.setContentText("Please, check the added quantity.");
                    alert.showAndWait();
                } else {
                    Query query = new Query(QueryType.update, ObjectType.request);
                    query.request = requestTable.getSelectionModel().getSelectedItem();
                    query.quantity = requestTable.getSelectionModel().getSelectedItem().getWine().getQuantity() + newQuantity;
                    this.mainApp.sendRequest(query);
                    Response response = this.mainApp.waitResponse();
                    Alert alertResponse = new Alert(Alert.AlertType.INFORMATION);
                    if (response.statusBoolean) {
                        alertResponse.setTitle("Success!");
                        alertResponse.setHeaderText(null);
                        alertResponse.setContentText("The request has been processed.");
                    } else {
                        alertResponse.setTitle("Failed!");
                        alertResponse.setHeaderText(null);
                        alertResponse.setContentText("Error! Try again.");
                    }
                    alertResponse.showAndWait();
                    this.updateTable();
                }
            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Process error!");
                alert.setHeaderText("Empty quantity field!");
                alert.setContentText("Try again.");
                alert.showAndWait();
                this.reset();
            }
        });
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
     * This method updates the data in the request table.
     */
    private void updateTable() {
        this.reset();
        if(requestCheckBox.isSelected()) {
            Query query = new Query(QueryType.select, ObjectType.request);
            this.mainApp.sendRequest(query);
            Response response = this.mainApp.waitResponse();
            requestTable.setItems(FXCollections.observableArrayList(response.requestList));
        }else {
            Query query = new Query(QueryType.selectWithFilter, ObjectType.employee);
            this.mainApp.sendRequest(query);
            Response response = this.mainApp.waitResponse();
            requestTable.setItems(FXCollections.observableArrayList(response.requestList));
        }
    }
}
