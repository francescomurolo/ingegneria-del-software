package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Person;
import socketClasses.Query;
import socketClasses.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class defines a client that sends an object
 * to a server and receives its answer.
 **/
public class Client extends Application {
    private static final int SPORT = 4444;
    private static final String SHOST = "localhost";
    private Stage primaryStage;

    private AnchorPane homeLayout;

    private Person loggedIn;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    private Socket connectionSocket;

    /**
     * It starts the client code.
     *
     * @param primaryStage the stage
     * @throws IOException if the connection with server or the streams setup fails.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Wine Shop");

        initLoginLayout();

        primaryStage.show();

        connectToServer();
        setupStreams();
    }

    /**
     * It creates the socket and establishes the connection with the server.
     *
     * @throws IOException if the connection with server fails.
     */
    private void connectToServer() throws IOException {
        connectionSocket = new Socket(SHOST, SPORT);
    }

    /**
     * It sets the streams to send and receive data.
     *
     * @throws IOException if the streams setup fails.
     */
    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(connectionSocket.getOutputStream());
        input = null;
    }

    /**
     * This method send the request to server.
     *
     * @param query the query for the request
     */
    public void sendRequest(Query query) {
        try {
            output.writeObject(query);
            output.flush();
            if(input == null)
                input = new ObjectInputStream(connectionSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method wait and receive the response by server.
     *
     * @return the server response.
     */
    public Response waitResponse() {
        while(true) {
            try {
                return (Response)input.readObject();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * It initializes the login layout.
     */
    public void initLoginLayout() {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("LoginView.fxml"));

            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.show();
            LoginViewController controller = loader.getController();
            controller.setMainApp(this);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * It initializes the Admin home layout.
     */
    public void initAdminLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AdminHomeView.fxml"));
            homeLayout = loader.load();

            Scene scene = new Scene(homeLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            AdminHomeViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * It initializes the User home layout.
     */
    public void initUserLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UserHomeView.fxml"));
            homeLayout = loader.load();

            Scene scene = new Scene(homeLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            UserHomeViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * It initializes the Employee home layout.
     */
    public void initEmployeeLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EmployeeHomeView.fxml"));
            homeLayout = loader.load();

            Scene scene = new Scene(homeLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            EmployeeHomeViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * It initializes the wine list layout of user.
     */
    public void initUserWinesLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UserWinesView.fxml"));
            homeLayout = loader.load();

            Scene scene = new Scene(homeLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            UserWinesViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * It initializes the request list layout of user.
     */
    public void initUserRequestsLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UserRequestsView.fxml"));
            homeLayout = loader.load();

            Scene scene = new Scene(homeLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            UserRequestsViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * It initializes the order list layout of user.
     */
    public void initUserOrdersLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UserOrdersView.fxml"));
            homeLayout = loader.load();

            Scene scene = new Scene(homeLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            UserOrdersViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * It initializes the user list layout of admin.
     */
    public void initAdminUsersLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AdminUsersView.fxml"));
            homeLayout = loader.load();

            Scene scene = new Scene(homeLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            AdminUsersViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * It initializes the order list layout of admin.
     */
    public void initAdminOrdersLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AdminOrdersView.fxml"));
            homeLayout = loader.load();

            Scene scene = new Scene(homeLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            AdminOrdersViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * It initializes the employee list layout of admin.
     */
    public void initAdminEmployeesLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AdminEmployeesView.fxml"));
            homeLayout = loader.load();

            Scene scene = new Scene(homeLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            AdminEmployeesViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * It initializes the wine list layout of admin.
     */
    public void initAdminWinesLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AdminWinesView.fxml"));
            homeLayout = loader.load();

            Scene scene = new Scene(homeLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            AdminWinesViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * It initializes the adding employee layout of admin.
     */
    public void initAdminNewEmployeeLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AdminNewEmployeeView.fxml"));
            homeLayout = loader.load();

            Scene scene = new Scene(homeLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            AdminNewEmployeeViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * It initializes the wine list layout of employee.
     */
    public void initEmployeeWinesLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EmployeeWinesView.fxml"));
            homeLayout = loader.load();

            Scene scene = new Scene(homeLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            EmployeeWinesViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * It initializes the order list layout of employee.
     */
    public void initEmployeeOrdersLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EmployeeOrdersView.fxml"));
            homeLayout = loader.load();

            Scene scene = new Scene(homeLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            EmployeeOrdersViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * It initializes the request list layout of employee.
     */
    public void initEmployeeRequestsLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EmployeeRequestsView.fxml"));
            homeLayout = loader.load();

            Scene scene = new Scene(homeLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            EmployeeRequestsViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * It initializes the adding wine layout of employee.
     */
    public void initEmployeeNewWineLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EmployeeNewWineView.fxml"));
            homeLayout = loader.load();

            Scene scene = new Scene(homeLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            EmployeeNewWineViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * It logs you out.
     */
    public void logout() {
        this.loggedIn = null;
        initLoginLayout();
    }

    /**
     * It gets the logged in person.
     *
     * @return the logged in person.
     */
    public Person getLoggedIn() {
        return loggedIn;
    }

    /**
     * It sets the logged in person.
     *
     * @param loggedIn the logged in person.
     */
    public void setLoggedIn(Person loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * Starts the client.
     *
     * @param args the method does not requires arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
