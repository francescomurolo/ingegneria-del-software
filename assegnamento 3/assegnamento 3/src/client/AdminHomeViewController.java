package client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * This class defines the controller
 * used in the FXML implementation of the admin home page.
 */
public class AdminHomeViewController {
    private Client mainApp;

    @FXML
    private Button logoutButton;

    @FXML
    private AnchorPane employeesPane;

    @FXML
    private AnchorPane usersPane;

    @FXML
    private AnchorPane winesPane;

    @FXML
    private AnchorPane ordersPane;

    /**
     * This method sets event for the buttons and anchorPanes
     */
    @FXML
    private void initialize() {
        logoutButton.setOnMouseClicked(clickEvent -> this.mainApp.logout());

        winesPane.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> this.mainApp.initAdminWinesLayout());

        employeesPane.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> this.mainApp.initAdminEmployeesLayout());

        ordersPane.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> this.mainApp.initAdminOrdersLayout());

        usersPane.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> this.mainApp.initAdminUsersLayout());
    }

    /**
     * This method is called by the Client to give a reference back to itself.
     *
     * @param mainApp the client.
     */
    public void setMainApp(Client mainApp) {
        this.mainApp = mainApp;
    }
}
