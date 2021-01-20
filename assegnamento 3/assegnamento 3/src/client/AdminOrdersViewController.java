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
 * used in the FXML implementation of the all orders page used by admin.
 */
public class AdminOrdersViewController {
    private Client mainApp;

    @FXML
    private Button backButton;

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
     * This method sets event for the buttons and shows all orders.
     */
    @FXML
    void initialize() {
        backButton.setOnMouseClicked(clickEvent -> this.mainApp.initAdminLayout());

        wineColumn.setCellValueFactory(cellData -> cellData.getValue().getWine().nameProperty());
        userColumn.setCellValueFactory(cellData -> cellData.getValue().getUser().emailProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().getOrderStatus());
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
