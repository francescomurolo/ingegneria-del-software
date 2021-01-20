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
 * used in the FXML implementation of the all wines page used by admin.
 */
public class AdminWinesViewController {
    private Client mainApp;

    @FXML
    private Button backButton;

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
     * This method sets event for the buttons and shows all wines.
     */
    @FXML
    void initialize() {
        backButton.setOnMouseClicked(clickEvent -> this.mainApp.initAdminLayout());

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        producerColumn.setCellValueFactory(cellData -> cellData.getValue().producerProperty());
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty());
        notesColumn.setCellValueFactory(cellData -> cellData.getValue().notesProperty());
        vinesColumn.setCellValueFactory(cellData -> cellData.getValue().vinesProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
    }

    /**
     * This method is called by the Client to give a reference back to itself.
     *
     * @param mainApp the client
     */
    public void setMainApp(Client mainApp) {
        this.mainApp = mainApp;
        this.updateTable();
    }

    /**
     * This method updates the data in the wine table
     */
    private void updateTable() {
        Query query = new Query(QueryType.select, ObjectType.wine);
        this.mainApp.sendRequest(query);
        Response response = this.mainApp.waitResponse();
        wineTable.setItems(FXCollections.observableArrayList(response.wineList));
    }
}
