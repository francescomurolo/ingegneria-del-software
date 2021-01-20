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
 * used in the FXML implementation of the all orders page used by employee.
 */
public class EmployeeOrdersViewController {
    private Client mainApp;

    @FXML
    private Button backButton;

    @FXML
    private Button shipButton;

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, String> wineColumn;

    @FXML
    private TableColumn<Order, String> userColumn;

    @FXML
    private TableColumn<Order, Integer> quantityColumn;

    @FXML
    private TableColumn<Order, String> statusColumn;

    /**
     * This method sets event for the buttons, table and shows all orders.
     */
    @FXML
    void initialize() {
        backButton.setOnMouseClicked(clickEvent -> this.mainApp.initEmployeeLayout());

        shipButton.setOnMouseClicked(MouseEvent -> {
            this.ship();
            this.reset();
        });

        orderTable.setOnMouseClicked( event -> {
            if(orderTable.getSelectionModel().getSelectedItem() != null) {
                shipButton.setDisable(orderTable.getSelectionModel().getSelectedItem().shippedProperty().asObject().get());
            }
        });

        wineColumn.setCellValueFactory(cellData -> cellData.getValue().getWine().nameProperty());
        userColumn.setCellValueFactory(cellData -> cellData.getValue().getUser().emailProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().getOrderStatus());
    }

    /**
     * This method is used to reset the table and ship button.
     */
    private void reset(){
        orderTable.getSelectionModel().clearSelection();
        shipButton.setDisable(true);
    }

    /**
     * This method is used to ship an order.
     */
    private void ship() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Shipping confirmation", ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Query query = new Query(QueryType.update, ObjectType.order);
            query.order = orderTable.getSelectionModel().getSelectedItem();
            this.mainApp.sendRequest(query);
            Response response = this.mainApp.waitResponse();
            Alert alertResponse = new Alert(Alert.AlertType.INFORMATION);
            if (response.statusBoolean){
                alertResponse.setTitle("Success!");
                alertResponse.setHeaderText(null);
                alertResponse.setContentText("The order has been shipped.");
            } else {
                alertResponse.setTitle("Failed!");
                alertResponse.setHeaderText(null);
                alertResponse.setContentText("Error! Try again.");
            }
            alertResponse.showAndWait();
            this.updateTable();
        }
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
     * This method updates the data in the order table.
     */
    private void updateTable() {
        Query query = new Query(QueryType.select, ObjectType.order);
        this.mainApp.sendRequest(query);
        Response response = this.mainApp.waitResponse();
        orderTable.setItems(FXCollections.observableArrayList(response.orderList));
    }
}
