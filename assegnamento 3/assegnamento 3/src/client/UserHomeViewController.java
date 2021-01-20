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
 * used in the FXML implementation of the user home page.
 */
public class UserHomeViewController {
    private Client mainApp;

    @FXML
    private Text userWelcomeText;

    @FXML
    private Button logoutButton;

    @FXML
    private AnchorPane winesPane;

    @FXML
    private AnchorPane notificationsPane;

    @FXML
    private AnchorPane ordersPane;

    @FXML
    private Text notificationText;

    /**
     * This method sets event for the buttons and anchorPanes.
     */
    @FXML
    private void initialize() {

        logoutButton.setOnMouseClicked(clickEvent -> this.mainApp.logout());

        winesPane.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> this.mainApp.initUserWinesLayout());

        notificationsPane.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> this.mainApp.initUserRequestsLayout());

        ordersPane.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> this.mainApp.initUserOrdersLayout());
    }

    /**
     * This method is called by the Client to give a reference back to itself.
     *
     * @param mainApp the client.
     */
    public void setMainApp(Client mainApp) {
        this.mainApp = mainApp;

        userWelcomeText.setText("Welcome " + this.mainApp.getLoggedIn().getName());
        Query query = new Query(QueryType.count, ObjectType.user);
        query.user = (User) this.mainApp.getLoggedIn();
        this.mainApp.sendRequest(query);
        Response response = this.mainApp.waitResponse();
        if(response.countInt > 0) {
            notificationText.setText(response.countInt + " new notifications!");
        }
    }
}
