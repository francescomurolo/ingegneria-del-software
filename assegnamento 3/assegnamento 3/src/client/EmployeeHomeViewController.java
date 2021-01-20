package client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.*;
import socketClasses.ObjectType;
import socketClasses.Query;
import socketClasses.QueryType;
import socketClasses.Response;

/**
 * This class defines the controller
 * used in the FXML implementation of the employee home page.
 */
public class EmployeeHomeViewController {
    private Client mainApp;

    @FXML
    private Button logoutButton;

    @FXML
    private AnchorPane winesPane;

    @FXML
    private AnchorPane requestsPane;

    @FXML
    private AnchorPane ordersPane;

    @FXML
    private Text notificationCounter;

    /**
     * This method sets event for the buttons and anchorPanes.
     */
    @FXML
    private void initialize() {
        logoutButton.setOnMouseClicked(clickEvent -> this.mainApp.logout());

        winesPane.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> this.mainApp.initEmployeeWinesLayout());

        ordersPane.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> this.mainApp.initEmployeeOrdersLayout());

        requestsPane.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> this.mainApp.initEmployeeRequestsLayout());
    }

    /**
     * This method is called by the Client to give a reference back to itself.
     *
     * @param mainApp the client.
     */
    public void setMainApp(Client mainApp) {
        this.mainApp = mainApp;
        Query query = new Query(QueryType.count, ObjectType.employee);
        query.employee = (Employee) this.mainApp.getLoggedIn();
        this.mainApp.sendRequest(query);
        Response response = this.mainApp.waitResponse();
        notificationCounter.setText(String.valueOf(response.countInt));
    }
}
