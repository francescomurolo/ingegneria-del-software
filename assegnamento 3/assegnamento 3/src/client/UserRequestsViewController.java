package client;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;
import socketClasses.ObjectType;
import socketClasses.Query;
import socketClasses.QueryType;
import socketClasses.Response;

/**
 * This class defines the controller
 * used in the FXML implementation of the user requests page.
 */
public class UserRequestsViewController {
    private Client mainApp;

    @FXML
    private Button backButton;

    @FXML
    private CheckBox requestCheckBox;

    @FXML
    private TableView<Request> requestTable;

    @FXML
    private TableColumn<Request, String> wineColumn;

    @FXML
    private TableColumn<Request, Integer> quantityColumn;

    @FXML
    private TableColumn<Request, String> statusColumn;

    /**
     * This method sets event for the buttons and shows all user requests.
     */
    @FXML
    void initialize() {
        backButton.setOnMouseClicked(clickEvent -> this.mainApp.initUserLayout());

        requestCheckBox.setOnMouseClicked(clickEvent -> this.updateTable());

        wineColumn.setCellValueFactory(cellData -> cellData.getValue().getWine().nameProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().getRequestStatus());
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
     *
     */
    private void updateTable() {
        Query query;
        if (requestCheckBox.isSelected()) {
            query = new Query(QueryType.selectWithFilter, ObjectType.request);
            query.user = (User) this.mainApp.getLoggedIn();
            this.mainApp.sendRequest(query);
            Response response = this.mainApp.waitResponse();
            requestTable.setItems(FXCollections.observableArrayList(response.requestList));
        } else {
            query = new Query(QueryType.selectWithFilter, ObjectType.unSeenRequest);
            query.user = (User) this.mainApp.getLoggedIn();
            this.mainApp.sendRequest(query);
            Response response = this.mainApp.waitResponse();
            requestTable.setItems(FXCollections.observableArrayList(response.requestList));
        }
    }
}
