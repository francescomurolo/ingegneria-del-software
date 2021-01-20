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
 * used in the FXML implementation of the all wines page used by employee.
 */
public class EmployeeWinesViewController {
    private Client mainApp;

    @FXML
    private Button backButton;

    @FXML
    private Button newWineButton;

    @FXML
    private Button updateButton;

    @FXML
    private CheckBox filterCheckBox;

    @FXML
    private TableView<Wine> wineTable;

    @FXML
    private TableColumn<Wine, String> nameColumn;

    @FXML
    private TableColumn<Wine, String> producerColumn;

    @FXML
    private TableColumn<Wine, String> yearColumn;

    @FXML
    private TableColumn<Wine, String> notesColumn;

    @FXML
    private TableColumn<Wine, String> vinesColumn;

    @FXML
    private TableColumn<Wine, Integer> quantityColumn;

    /**
     * This method sets event for the buttons, table and shows all wines
     */
    @FXML
    void initialize() {
        backButton.setOnMouseClicked(clickEvent -> this.mainApp.initEmployeeLayout());

        newWineButton.setOnMouseClicked(clickEvent -> this.mainApp.initEmployeeNewWineLayout());

        filterCheckBox.setOnMouseClicked(clickEvent -> this.updateTable());

        updateButton.setOnMouseClicked(MouseEvent -> {
            this.updateQuantity();
            this.reset();
        });

        wineTable.setOnMouseClicked( event -> {
            if(wineTable.getSelectionModel().getSelectedItem() != null)
                updateButton.setDisable(false);
        });

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        producerColumn.setCellValueFactory(cellData -> cellData.getValue().producerProperty());
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty());
        notesColumn.setCellValueFactory(cellData -> cellData.getValue().notesProperty());
        vinesColumn.setCellValueFactory(cellData -> cellData.getValue().vinesProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
    }

    /**
     * This method is used to reset the table and update button.
     */
    private void reset(){
        wineTable.getSelectionModel().clearSelection();
        updateButton.setDisable(true);
    }

    /**
     * This method is used to update the wine quantity.
     */
    private void updateQuantity() {
        TextInputDialog buyDialog = new TextInputDialog();
        buyDialog.setTitle("Update quantity");
        buyDialog.setHeaderText("You are updating the quantity of " + wineTable.getSelectionModel().getSelectedItem().getName());
        buyDialog.setContentText("Please enter the quantity to add");
        TextField quantityEditor = buyDialog.getEditor();
        quantityEditor.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                quantityEditor.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        Optional<String> result = buyDialog.showAndWait();
        result.ifPresent(quantity -> {
            try{
                int newQuantity = Integer.parseInt(quantity);
                if (newQuantity == 0){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Update error!");
                    alert.setHeaderText("Invalid quantity!");
                    alert.setContentText("Please, check the wine quantity to add.");
                    alert.showAndWait();
                } else {
                    Query query = new Query(QueryType.update, ObjectType.wine);
                    query.wine = wineTable.getSelectionModel().getSelectedItem();
                    query.quantity = wineTable.getSelectionModel().getSelectedItem().getQuantity() + newQuantity;
                    this.mainApp.sendRequest(query);
                    Response response = this.mainApp.waitResponse();
                    Alert alertResponse = new Alert(Alert.AlertType.INFORMATION);
                    if (response.statusBoolean) {
                        alertResponse.setTitle("Success!");
                        alertResponse.setHeaderText(null);
                        alertResponse.setContentText("Quantity updated.");
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
                alert.setTitle("Update error!");
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
     * This method updates the data in the wine table.
     */
    private void updateTable() {
        this.reset();
        if(!filterCheckBox.isSelected()) {
            Query query = new Query(QueryType.select, ObjectType.wine);
            this.mainApp.sendRequest(query);
            Response response = this.mainApp.waitResponse();
            wineTable.setItems(FXCollections.observableArrayList(response.wineList));
        }else {
            Query query = new Query(QueryType.selectWithFilter, ObjectType.wine);
            this.mainApp.sendRequest(query);
            Response response = this.mainApp.waitResponse();
            wineTable.setItems(FXCollections.observableArrayList(response.wineList));
        }
    }
}
