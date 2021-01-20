package client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;
import socketClasses.ObjectType;
import socketClasses.Query;
import socketClasses.QueryType;
import socketClasses.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * This class defines the controller
 * used in the FXML implementation of the all wines page used by user.
 */
public class UserWinesViewController {
    private Client mainApp;

    @FXML
    private Button backButton;

    @FXML
    private Button requestButton;

    @FXML
    private Button buyButton;

    @FXML
    private TextField nameSearchField;

    @FXML
    private ChoiceBox<String> yearSearchChoice;

    @FXML
    private Button resetButton;

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
     * This method sets event for the buttons, table and shows all wines.
     */
    @FXML
    void initialize() {
        backButton.setOnMouseClicked(clickEvent -> this.mainApp.initUserLayout());

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        producerColumn.setCellValueFactory(cellData -> cellData.getValue().producerProperty());
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty());
        notesColumn.setCellValueFactory(cellData -> cellData.getValue().notesProperty());
        vinesColumn.setCellValueFactory(cellData -> cellData.getValue().vinesProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        ObservableList<String> years = FXCollections.observableArrayList();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
        int currentYear = Integer.parseInt(dtf.format(LocalDateTime.now()));
        for (int i = 1990; i <= currentYear; i++) {
            years.add(String.valueOf(i));
        }
        yearSearchChoice.setItems(years);
        yearSearchChoice.setValue("");

        resetButton.setOnMouseClicked(MouseEvent -> {
            nameSearchField.setText("");
            yearSearchChoice.setValue("");
            this.reset();
        });

        nameSearchField.textProperty().addListener((observable, oldValue, newValue) -> this.updateTable());
        yearSearchChoice.valueProperty().addListener(ChangeListener -> this.updateTable());

        wineTable.setOnMouseClicked( event -> {
            if(wineTable.getSelectionModel().getSelectedItem() != null) {
                if (wineTable.getSelectionModel().getSelectedItem().getQuantity() > 0){
                    buyButton.setDisable(false);
                    requestButton.setDisable(true);
                }else {
                    buyButton.setDisable(true);
                    requestButton.setDisable(false);
                }
            }
        });

        buyButton.setOnMouseClicked(MouseEvent -> {
            this.buy();
            this.reset();
        });

        requestButton.setOnMouseClicked(MouseEvent -> {
            this.request();
            this.reset();
        });
    }

    /**
     * This method is used to reset the table, buy button and request button.
     */
    private void reset(){
        wineTable.getSelectionModel().clearSelection();
        buyButton.setDisable(true);
        requestButton.setDisable(true);
    }
    /**
     * This method is used to request a wine.
     */
    private void request() {
        TextInputDialog requestDialog = new TextInputDialog();
        requestDialog.setTitle("New request");
        requestDialog.setHeaderText("You are requesting " + wineTable.getSelectionModel().getSelectedItem().getName());
        requestDialog.setContentText("Please enter the required quantity");
        TextField quantityEditor = requestDialog.getEditor();
        quantityEditor.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                quantityEditor.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        Optional<String> result = requestDialog.showAndWait();
        result.ifPresent(quantity -> {
            try {
                int requestQuantity = Integer.parseInt(quantity);
                if (requestQuantity == 0) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Request error!");
                    alert.setHeaderText("Invalid quantity!");
                    alert.setContentText("Please, check the required quantity.");
                    alert.showAndWait();
                } else {
                    Query query = new Query(QueryType.add, ObjectType.request);
                    query.wine = wineTable.getSelectionModel().getSelectedItem();
                    query.user = (User) this.mainApp.getLoggedIn();
                    query.wine.setYear(yearSearchChoice.getValue());
                    query.quantity = requestQuantity;
                    this.mainApp.sendRequest(query);
                    Response response = this.mainApp.waitResponse();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    if (response.statusBoolean) {
                        alert.setTitle("Success!");
                        alert.setHeaderText(null);
                        alert.setContentText("Your request is confirmed. You will be notified when the wine is available.");
                    } else {
                        alert.setTitle("Failed!");
                        alert.setHeaderText(null);
                        alert.setContentText("Request Error! Try again.");
                    }
                    alert.showAndWait();
                    this.updateTable();
                }
            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Request error!");
                alert.setHeaderText("Empty quantity field!");
                alert.setContentText("Try again.");
                alert.showAndWait();
            }
        });
    }

    /**
     * This method is used to order a wine.
     */
    private void buy() {
        TextInputDialog buyDialog = new TextInputDialog();
        buyDialog.setTitle("New order");
        buyDialog.setHeaderText("You are ordering " + wineTable.getSelectionModel().getSelectedItem().getName());
        buyDialog.setContentText("Please enter the quantity");
        TextField quantityEditor = buyDialog.getEditor();
        quantityEditor.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                quantityEditor.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        Optional<String> result = buyDialog.showAndWait();
        result.ifPresent(quantity -> {
            try {
                int orderQuantity = Integer.parseInt(quantity);
                if (orderQuantity == 0 || orderQuantity > wineTable.getSelectionModel().getSelectedItem().getQuantity()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Order error!");
                    alert.setHeaderText("Invalid quantity!");
                    alert.setContentText("Please, check the wine quantity you ordered.");
                    alert.showAndWait();
                } else {
                    Query query = new Query(QueryType.add, ObjectType.order);
                    query.wine = wineTable.getSelectionModel().getSelectedItem();
                    query.user = (User) this.mainApp.getLoggedIn();
                    query.wine.setYear(yearSearchChoice.getValue());
                    query.quantity = orderQuantity;
                    this.mainApp.sendRequest(query);
                    Response response = this.mainApp.waitResponse();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    if (response.statusBoolean) {
                        alert.setTitle("Success!");
                        alert.setHeaderText(null);
                        alert.setContentText("Your order is confirmed. Thank you!");
                    } else {
                        alert.setTitle("Failed!");
                        alert.setHeaderText(null);
                        alert.setContentText("Order Error! Try again.");
                    }
                    alert.showAndWait();
                    this.updateTable();
                }
            }catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Order error!");
                alert.setHeaderText("Empty quantity field!");
                alert.setContentText("Try again.");
                alert.showAndWait();
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
        if(nameSearchField.getText().equals("") && yearSearchChoice.getValue().equals("")) {
            Query query = new Query(QueryType.select, ObjectType.wine);
            this.mainApp.sendRequest(query);
            Response response = this.mainApp.waitResponse();
            wineTable.setItems(FXCollections.observableArrayList(response.wineList));
        }else{
            if (!nameSearchField.getText().equals("") && !yearSearchChoice.getValue().equals("")) {
                Query query = new Query(QueryType.selectWithFilter, ObjectType.user);
                Wine wine = new Wine();
                wine.setName(nameSearchField.getText());
                wine.setYear(yearSearchChoice.getValue());
                query.wine = wine;
                this.mainApp.sendRequest(query);
                Response response = this.mainApp.waitResponse();
                wineTable.setItems(FXCollections.observableArrayList(response.wineList));
            } else if (!nameSearchField.getText().equals("") && yearSearchChoice.getValue().equals("")) {
                Query query = new Query(QueryType.selectWithFilter, ObjectType.user);
                Wine wine = new Wine();
                wine.setName(nameSearchField.getText());
                wine.setYear("");
                query.wine = wine;
                this.mainApp.sendRequest(query);
                Response response = this.mainApp.waitResponse();
                wineTable.setItems(FXCollections.observableArrayList(response.wineList));
            } else if (nameSearchField.getText().equals("") && !yearSearchChoice.getValue().equals("")) {
                Query query = new Query(QueryType.selectWithFilter, ObjectType.user);
                Wine wine = new Wine();
                wine.setName("");
                wine.setYear(yearSearchChoice.getValue());
                query.wine = wine;
                this.mainApp.sendRequest(query);
                Response response = this.mainApp.waitResponse();
                wineTable.setItems(FXCollections.observableArrayList(response.wineList));
            }
        }
    }
}
