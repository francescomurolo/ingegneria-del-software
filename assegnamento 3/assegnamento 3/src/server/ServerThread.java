package server;

import model.*;
import socketClasses.ObjectType;
import socketClasses.Query;
import socketClasses.Response;
import socketClasses.ResponseType;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the interaction
 * with a client of the server.
 **/
public class ServerThread implements Runnable {
    private static final long SLEEPTIME = 200;

    private static final String DBURL = "jdbc:mysql://localhost:3306/winedb?";
    private static final String ARGS = "serverTimezone=UTC";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "";

    Connection databaseConnection = null;
    Statement stmt = null;

    private Server server;
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    /**
     * Class constructor.
     *
     * @param s the server.
     * @param c the client socket.
     **/
    public ServerThread(final Server s, final Socket c) {
        this.server = s;
        this.socket = c;
    }

    /**
     * It runs the code.
     */
    @Override
    public void run() {
        connectToDatabase();
        try {
            setupStreams();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                waitForQuery();
            } catch (Exception e) {
                e.printStackTrace();
                closeAll();
                System.exit(0);
            } finally {
                closeAll();
            }
        }
    }

    /**
     * It establishes the connection with the Database.
     */
    private void connectToDatabase() {
        System.out.println("Connecting to database...\n");
        try {
            databaseConnection = DriverManager.getConnection(DBURL + ARGS, LOGIN, PASSWORD);
            stmt = databaseConnection.createStatement();
            System.out.println("Connected to Database\n");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Not connected to Database\n");
        }
    }

    /**
     * It disconnects the server from Database.
     *
     * @throws SQLException if the disconnection from Database fails.
     */
    private void disconnectFromDatabase() throws SQLException {
        databaseConnection.close();
        stmt.close();
    }

    /**
     * It sets the streams to send and receive data.
     *
     * @throws IOException if the setup of streams fails.
     */
    private void setupStreams() throws IOException {
        input = new ObjectInputStream(new BufferedInputStream(this.socket.getInputStream()));
        output = null;
    }

    /**
     * It waits for incoming query and execute it.
     */
    private void waitForQuery() {
        while(true) {
            try {
                Query incomingQuery = (Query) input.readObject();
                Thread.sleep(SLEEPTIME);
                if (incomingQuery != null) {
                    if(output == null)
                        output = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    executeQuery(incomingQuery);
                }
            } catch (ClassNotFoundException | IOException | SQLException | InterruptedException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    /**
     * It executes the query.
     *
     * @param query the query to execute.
     * @throws SQLException if the connection with Database fails.
     * @throws IOException if the response creation fails.
     */
    private void executeQuery(Query query) throws IOException, SQLException {
        switch(query.type) {
            case add: {
                switch (query.objectType) {
                    case employee -> newEmployee(query.employee);
                    case order -> buyWine(query.user, query.wine, query.quantity);
                    case request -> requestWine(query.user, query.wine, query.quantity);
                    case wine -> addWine(query.wine);
                    case user -> signup(query.user);
                }
                break;
            }

            case select: {
                switch (query.objectType) {
                    case wine -> getWines();
                    case user -> getAllUsers();
                    case order -> getAllOrders();
                    case request -> getAllRequests();
                    case employee -> getAllEmployees();
                }
                break;
            }

            case selectWithFilter: {
                switch (query.objectType) {
                    case person -> login(query.person);
                    case user -> getWineByNameAndYear(query.wine);
                    case request -> getUserRequests(query.user);
                    case unSeenRequest -> getUnseenRequests(query.user);
                    case order -> getUserOrders(query.user);
                    case employee -> getUnprocessedRequests();
                    case wine -> getFinishedWines();
                }
                break;
            }

            case count: {
                switch (query.objectType) {
                    case user -> getUnseenRequestsNumber(query.user);
                    case employee -> getNotificationsNumber();
                }
                break;
            }

            case update: {
                switch (query.objectType) {
                    case request -> processRequest(query.request, query.quantity);
                    case order -> shipOrder(query.order);
                    case wine -> updateQuantity(query.wine, query.quantity);
                }
                break;
            }

            default:
                break;
        }
    }

    /**
     * It closes all streams and socket.
     */
    private void closeAll() {
        try {
            output.close();
            input.close();
            this.server.close();
            this.socket.close();
            disconnectFromDatabase();
        } catch(IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * It is used to get all wines.
     *
     * @throws SQLException if the connection with Database fails.
     * @throws IOException if the response creation fails.
     */
    private void getWines() throws SQLException, IOException {
        List<Wine> wineList = new ArrayList<>();
        stmt = databaseConnection.createStatement();
        String sql = "SELECT * FROM wine";
        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next()){
            wineList.add(new Wine(resultSet.getInt("ID"),
                                resultSet.getString("Name"),
                                resultSet.getString("Producer"),
                                resultSet.getString("Year"),
                                resultSet.getString("Notes"),
                                resultSet.getString("Vines"),
                                resultSet.getInt("Quantity")));
        }
        resultSet.close();
        Response response = new Response(ResponseType.sendList, ObjectType.wine);
        response.wineList = wineList;
        output.writeObject(response);
        output.flush();
    }

    /**
     * It is used to research the wines by their name and/or their year.
     *
     * @param wine the wine to search.
     * @throws SQLException if the connection with Database fails.
     * @throws IOException if the response creation fails.
     */
    private void getWineByNameAndYear(Wine wine) throws SQLException, IOException {
        ArrayList<Wine> wineList = new ArrayList<>();
        stmt = databaseConnection.createStatement();
        String sql;
        if (wine.getName().equals(""))
            sql = "SELECT * FROM wine WHERE Year LIKE '" + wine.getYear() + "'";
        else if (wine.getYear().equals(""))
            sql = "SELECT * FROM wine WHERE Name LIKE '%" + wine.getName() + "%'";
        else
            sql = "SELECT * FROM wine WHERE Name LIKE '%" + wine.getName() + "%' AND Year LIKE '" + wine.getYear() + "'";
        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next()){
            wineList.add(new Wine(resultSet.getInt("ID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Producer"),
                    resultSet.getString("Year"),
                    resultSet.getString("Notes"),
                    resultSet.getString("Vines"),
                    resultSet.getInt("Quantity")));
        }
        resultSet.close();
        Response response = new Response(ResponseType.sendList, ObjectType.wine);
        response.wineList = wineList;
        output.writeObject(response);
        output.flush();
    }

    /**
     * It is used to login in wine shop.
     *
     * @param person the person logging.
     * @throws SQLException if the connection with Database fails.
     * @throws IOException if the response creation fails.
     */
    private void login(Person person) throws SQLException, IOException{
        Response response = new Response(ResponseType.sendObject, ObjectType.person);
        stmt = databaseConnection.createStatement();
        String sql = "SELECT * FROM person WHERE Email LIKE '" + person.getEmail() + "' AND Password LIKE '" + person.getPassword() + "'";
        ResultSet resultSet = stmt.executeQuery(sql);
        if(resultSet.next()) {
            if (resultSet.getInt("Role") == 0) {
                response.person = new Admin(resultSet.getInt("ID"), resultSet.getString("Name"), resultSet.getString("Surname"), resultSet.getString("Email"), resultSet.getString("Password"));
            } else if (resultSet.getInt("Role") == 1) {
                response.person = new Employee(resultSet.getInt("ID"), resultSet.getString("Name"), resultSet.getString("Surname"), resultSet.getString("Email"), resultSet.getString("Password"));
            } else {
                response.person = new User(resultSet.getInt("ID"), resultSet.getString("Name"), resultSet.getString("Surname"), resultSet.getString("Email"), resultSet.getString("Password"));
            }
        } else {
            response.person = new Person();
        }
        resultSet.close();
        output.writeObject(response);
        output.flush();
    }

    /**
     * It is used to signing in wine shop.
     *
     * @param user the user to sign in wine shop.
     * @throws SQLException if the connection with Database fails.
     * @throws IOException if the response creation fails.
     */
    private void signup(User user) throws SQLException, IOException {
        Response response = new Response(ResponseType.status, ObjectType.person);
        String sql = "SELECT * FROM person WHERE Email LIKE '" + user.getEmail() + "'";
        stmt = databaseConnection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        if (resultSet.next())
            response.statusBoolean = false;
        String insertQuery = "INSERT INTO person(Name, Surname, Email, Password, Role) VALUES ('" + user.getName() + "','" + user.getSurname() + "','" + user.getEmail() + "','" + user.getPassword() + "',2)";
        PreparedStatement updateStmt = databaseConnection.prepareStatement(insertQuery);
        updateStmt.executeUpdate();
        response.statusBoolean = true;

        output.writeObject(response);
        output.flush();
    }

    /**
     * It is used to add a new employee.
     *
     * @param employee new employee to add.
     * @throws SQLException if the connection with Database fails.
     * @throws IOException if the response creation fails.
     */
    private void newEmployee(Employee employee) throws SQLException, IOException {
        Response response = new Response(ResponseType.status, ObjectType.employee);
        String sql = "SELECT * FROM person WHERE Email LIKE '" + employee.getEmail() + "'";
        stmt = databaseConnection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        if (resultSet.next())
            response.statusBoolean = false;
        String insertQuery = "INSERT INTO person(Name, Surname, Email, Password, Role) VALUES ('" + employee.getName() + "','" + employee.getSurname() + "','" + employee.getEmail() + "','" + employee.getPassword() + "',1)";
        PreparedStatement updateStmt = databaseConnection.prepareStatement(insertQuery);
        updateStmt.executeUpdate();
        response.statusBoolean = true;
        output.writeObject(response);
        output.flush();
    }

    /**
     * It is used to get all employees.
     *
     * @throws SQLException if the connection with Database fails.
     * @throws IOException if the response creation fails.
     */
    private void getAllEmployees() throws SQLException, IOException{
        List<Employee> employeeList = new ArrayList<>();
        stmt = databaseConnection.createStatement();
        String sql = "SELECT * FROM person WHERE role=1";
        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next()){
            employeeList.add(new Employee(resultSet.getInt("person.ID"),
                                          resultSet.getString("person.Name"),
                                          resultSet.getString("person.Surname"),
                                          resultSet.getString("person.Email"),
                                          resultSet.getString("person.Password")));
        }
        resultSet.close();
        Response response = new Response(ResponseType.sendList, ObjectType.employee);
        response.employeeList = employeeList;
        output.writeObject(response);
        output.flush();
    }

    /**
     * It is used to get all users.
     *
     * @throws SQLException if the connection with Database fails.
     * @throws IOException if the response creation fails.
     */
    private void getAllUsers() throws SQLException, IOException{
        List<User> userList = new ArrayList<>();
        stmt = databaseConnection.createStatement();
        String sql = "SELECT * FROM person WHERE role=2";
        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next()){
            userList.add(new User(resultSet.getInt("person.ID"),
                                  resultSet.getString("person.Name"),
                                  resultSet.getString("person.Surname"),
                                  resultSet.getString("person.Email"),
                                  resultSet.getString("person.Password")));
        }
        resultSet.close();
        Response response = new Response(ResponseType.sendList, ObjectType.user);
        response.userList = userList;
        output.writeObject(response);
        output.flush();
    }

    /**
     * It is used to get all requests.
     *
     * @throws SQLException if the connection with Database fails.
     * @throws IOException if the response creation fails.
     */
    private void getAllRequests() throws SQLException, IOException{
        List<Request> requestList = new ArrayList<>();
        stmt = databaseConnection.createStatement();
        String sql = "SELECT * FROM request, wine, person " +
                     "WHERE request.WINE_ID=wine.ID AND request.USER_ID=person.ID";
        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next()){
            requestList.add(new Request(resultSet.getInt("request.ID"),
                                        new Wine(resultSet.getInt("wine.ID"),
                                                 resultSet.getString("wine.Name"),
                                                 resultSet.getString("Producer"),
                                                 resultSet.getString("Year"),
                                                 resultSet.getString("Notes"),
                                                 resultSet.getString("Vines"),
                                                 resultSet.getInt("wine.Quantity")),
                                        new User(resultSet.getString("person.Name"),
                                                 resultSet.getString("person.Surname"),
                                                 resultSet.getString("person.Email"),
                                                 resultSet.getString("person.Password")),
                            resultSet.getInt("request.Quantity"),
                            resultSet.getBoolean("request.Processed")));
        }
        resultSet.close();
        Response response = new Response(ResponseType.sendList, ObjectType.request);
        response.requestList = requestList;
        output.writeObject(response);
        output.flush();
    }

    /**
     * It is used to get all orders.
     *
     * @throws SQLException if the connection with Database fails.
     * @throws IOException if the response creation fails.
     */
    private void getAllOrders() throws SQLException, IOException{
        List<Order> orderList = new ArrayList<>();
        stmt = databaseConnection.createStatement();
        String sql = "SELECT * FROM winedb.`order`,wine,person WHERE WINE_ID=wine.ID AND USER_ID=person.ID";
        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next()){
            orderList.add(new Order(resultSet.getInt("order.ID"),
                                    new Wine(resultSet.getInt("wine.ID"),
                                             resultSet.getString("wine.Name"),
                                             resultSet.getString("wine.Producer"),
                                             resultSet.getString("wine.Year"),
                                             resultSet.getString("wine.Notes"),
                                             resultSet.getString("wine.Vines"),
                                             resultSet.getInt("wine.Quantity")),
                                    new User(resultSet.getInt("person.ID"),
                                             resultSet.getString("person.Name"),
                                             resultSet.getString("person.Surname"),
                                             resultSet.getString("person.Email"),
                                             resultSet.getString("person.Password")),
                                             resultSet.getInt("order.Quantity"),
                                    resultSet.getBoolean("order.Shipped")));
        }
        resultSet.close();
        Response response = new Response(ResponseType.sendList, ObjectType.order);
        response.orderList = orderList;
        output.writeObject(response);
        output.flush();
    }

    /**
     * It is used to buy a wine.
     *
     * @param user the user buying.
     * @param wine the wine ordered.
     * @param quantity the quantity to buy.
     * @throws SQLException if the connection with Database fails.
     * @throws IOException if the response creation fails.
     */
    private void buyWine(User user, Wine wine, int quantity) throws SQLException, IOException {
        Response response = new Response(ResponseType.status, ObjectType.wine);
        String insertQuery = "INSERT INTO winedb.`order`(USER_ID, WINE_ID, Quantity) " +
                             "VALUES (" + user.getId() + "," + wine.getId() + "," + quantity + ")";
        PreparedStatement updateStmt = databaseConnection.prepareStatement(insertQuery);
        updateStmt.executeUpdate();
        response.statusBoolean = true;
        setQuantity(wine, wine.getQuantity() - quantity);
        output.writeObject(response);
        output.flush();
    }

    /**
     * it is used to set the quantity of wine present after a successful order.
     *
     * @param wine the wine to update.
     * @param quantity the new quantity to set.
     * @throws SQLException if the connection with Database fails.
     */
    private void setQuantity(Wine wine, int quantity) throws SQLException {
        String updateQuery = "UPDATE wine SET Quantity =" + quantity + " WHERE wine.ID=" + wine.getId();
        PreparedStatement updateStmt = databaseConnection.prepareStatement(updateQuery);
        updateStmt.executeUpdate();
    }

    /**
     * It is used to request a wine.
     *
     * @param user the user requesting.
     * @param wine the requested wine.
     * @param quantity the requested quantity.
     * @throws SQLException if the connection with Database fails.
     * @throws IOException if the response creation fails.
     */
    private void requestWine(User user, Wine wine, int quantity) throws SQLException, IOException {
        Response response = new Response(ResponseType.status, ObjectType.request);
        String insertQuery = "INSERT INTO request(USER_ID,WINE_ID,Quantity) " +
                             "VALUES (" + user.getId() + "," + wine.getId() + "," + quantity + ")";
        PreparedStatement updateStmt = databaseConnection.prepareStatement(insertQuery);
        updateStmt.executeUpdate();
        response.statusBoolean = true;
        output.writeObject(response);
        output.flush();
    }

    /**
     * It is used to get all requests of an user.
     *
     * @param user the user.
     * @throws SQLException if the connection with Database fails.
     * @throws IOException if the response creation fails.
     */
    private void getUserRequests(User user) throws SQLException, IOException{
        List<Request> requestList = new ArrayList<>();
        stmt = databaseConnection.createStatement();
        String sql = "SELECT * FROM request, wine, person " +
                     "WHERE request.WINE_ID=wine.ID AND USER_ID=person.ID AND person.ID=" + user.getId() + " " +
                     "ORDER BY request.Processed, request.ID DESC";
        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next()){
            requestList.add(new Request(resultSet.getInt("request.ID"),
                    new Wine(resultSet.getInt("wine.ID"),
                            resultSet.getString("wine.Name"),
                            resultSet.getString("Producer"),
                            resultSet.getString("Year"),
                            resultSet.getString("Notes"),
                            resultSet.getString("Vines"),
                            resultSet.getInt("wine.Quantity")),
                    new User(resultSet.getString("person.Name"),
                            resultSet.getString("person.Surname"),
                            resultSet.getString("person.Email"),
                            resultSet.getString("person.Password")),
                    resultSet.getInt("request.Quantity"),
                    resultSet.getBoolean("request.Processed")));
        }
        resultSet.close();
        updateSeenRequests(user);
        Response response = new Response(ResponseType.sendList, ObjectType.request);
        response.requestList = requestList;
        output.writeObject(response);
        output.flush();
    }

    /**
     * It is used to update the display status of all unseen requests of an user.
     *
     * @param user the user.
     * @throws SQLException if the connection with Database fails.
     */
    private void updateSeenRequests(User user) throws SQLException {
        String updateQuery = "UPDATE request SET Seen=1 WHERE USER_ID=" + user.getId() + " AND Processed=1 AND Seen=0";
        PreparedStatement updateStmt = databaseConnection.prepareStatement(updateQuery);
        updateStmt.executeUpdate();
    }

    /**
     * It is used to get all unseen requests of an user.
     *
     * @param user the user.
     * @throws SQLException if the connection with Database fails.
     * @throws IOException if the response creation fails.
     */
    private void getUnseenRequests(User user) throws SQLException, IOException{
        List<Request> requestList = new ArrayList<>();
        stmt = databaseConnection.createStatement();
        String sql ="SELECT * FROM request, wine, person " +
                    "WHERE request.WINE_ID=wine.ID AND USER_ID=person.ID AND person.ID=" + user.getId() + " AND request.Seen=0 " +
                    "ORDER BY request.ID DESC";
        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next()){
            requestList.add(new Request(resultSet.getInt("request.ID"),
                    new Wine(resultSet.getInt("wine.ID"),
                            resultSet.getString("wine.Name"),
                            resultSet.getString("Producer"),
                            resultSet.getString("Year"),
                            resultSet.getString("Notes"),
                            resultSet.getString("Vines"),
                            resultSet.getInt("wine.Quantity")),
                    new User(resultSet.getString("person.Name"),
                            resultSet.getString("person.Surname"),
                            resultSet.getString("person.Email"),
                            resultSet.getString("person.Password")),
                    resultSet.getInt("request.Quantity"),
                    resultSet.getBoolean("request.Processed")));
        }
        resultSet.close();
        updateSeenRequests(user);
        Response response = new Response(ResponseType.sendList, ObjectType.request);
        response.requestList = requestList;
        output.writeObject(response);
        output.flush();
    }

    /**
     * It is used to get the number of unseen requests of an user.
     *
     * @param user the user.
     * @throws SQLException if the connection with Database fails.
     * @throws IOException if the response creation fails.
     */
    private void getUnseenRequestsNumber(User user) throws SQLException, IOException{
        Response response = new Response(ResponseType.count, ObjectType.request);
        stmt = databaseConnection.createStatement();
        String sql = "SELECT COUNT(*) FROM request " +
                     "WHERE USER_ID=" + user.getId() + " AND Processed=1 AND Seen=0";
        ResultSet resultSet = stmt.executeQuery(sql);
        resultSet.next();
        response.countInt=resultSet.getInt(1);
        resultSet.close();
        output.writeObject(response);
        output.flush();
    }

    /**
     * It is used to get all user orders.
     *
     * @param user the user.
     * @throws SQLException if the connection with Database fails.
     * @throws IOException if the response creation fails.
     */
    private void getUserOrders(User user) throws SQLException, IOException{
        List<Order> orderList = new ArrayList<>();
        stmt = databaseConnection.createStatement();
        String sql = "SELECT * FROM winedb.`order`,wine WHERE WINE_ID=wine.ID AND USER_ID=" + user.getId();
        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next()){
            orderList.add(new Order(resultSet.getInt("order.ID"),
                                    new Wine(resultSet.getInt("wine.ID"),
                                            resultSet.getString("wine.Name"),
                                            resultSet.getString("wine.Producer"),
                                            resultSet.getString("wine.Year"),
                                            resultSet.getString("wine.Notes"),
                                            resultSet.getString("wine.Vines"),
                                            resultSet.getInt("wine.Quantity")),
                                    user,
                                    resultSet.getInt("order.Quantity"),
                                    resultSet.getBoolean("order.Shipped")));
        }
        resultSet.close();
        Response response = new Response(ResponseType.sendList, ObjectType.order);
        response.orderList = orderList;
        output.writeObject(response);
        output.flush();
    }

    /**
     * It is used to add a new wine in the system.
     *
     * @param wine the new wine to add.
     * @throws SQLException if the connection with Database fails.
     * @throws IOException if the response creation fails.
     */
    private void addWine(Wine wine) throws SQLException, IOException {
        Response response = new Response(ResponseType.status, ObjectType.wine);
        String insertQuery = "INSERT INTO wine(Name, Producer, Year, Notes, Vines, Quantity) " +
                             "VALUES ('" + wine.getName() + "','" + wine.getProducer() + "','" + wine.getYear() + "','" + wine.getNotes() + "','" + wine.getVines() + "'," + wine.getQuantity() + ")";
        PreparedStatement updateStmt = databaseConnection.prepareStatement(insertQuery);
        updateStmt.executeUpdate();
        response.statusBoolean = true;
        output.writeObject(response);
        output.flush();
    }

    /**
     * It is used to ship a order.
     *
     * @param order the order to ship.
     * @throws SQLException if the connection with Database fails.
     * @throws IOException if the response creation fails.
     */
    private void shipOrder(Order order) throws SQLException, IOException {
        String updateQuery = "UPDATE winedb.`order` SET Shipped=1 WHERE winedb.`order`.ID=" + order.getId();
        PreparedStatement updateStmt = databaseConnection.prepareStatement(updateQuery);
        updateStmt.executeUpdate();
        Response response = new Response(ResponseType.status, ObjectType.order);
        response.statusBoolean = true;
        output.writeObject(response);
        output.flush();
    }

    /**
     * It is used to get all unprocessed requests.
     *
     * @throws SQLException if the connection with Database fails.
     * @throws IOException if the response creation fails.
     */
    private void getUnprocessedRequests() throws SQLException, IOException{
        List<Request> requestList = new ArrayList<>();
        stmt = databaseConnection.createStatement();
        String sql =   "SELECT * FROM request, wine, person " +
                "WHERE request.WINE_ID=wine.ID AND request.USER_ID=person.ID AND Processed=0";
        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next()){
            requestList.add(new Request(resultSet.getInt("request.ID"),
                    new Wine(resultSet.getInt("wine.ID"),
                            resultSet.getString("wine.Name"),
                            resultSet.getString("Producer"),
                            resultSet.getString("Year"),
                            resultSet.getString("Notes"),
                            resultSet.getString("Vines"),
                            resultSet.getInt("wine.Quantity")),
                    new User(resultSet.getString("person.Name"),
                            resultSet.getString("person.Surname"),
                            resultSet.getString("person.Email"),
                            resultSet.getString("person.Password")),
                    resultSet.getInt("request.Quantity"),
                    resultSet.getBoolean("request.Processed")));
        }
        resultSet.close();
        Response response = new Response(ResponseType.sendList, ObjectType.request);
        response.requestList = requestList;
        output.writeObject(response);
        output.flush();
    }

    /**
     * It is used to process a request.
     *
     * @param request the request.
     * @param quantity the new quantity of the wine.
     * @throws SQLException if the connection with Database fails.
     * @throws IOException if the response creation fails.
     */
    private void processRequest(Request request, int quantity) throws SQLException, IOException {
        String updateQuery = "UPDATE request SET Processed=1 WHERE request.ID=" + request.getId();
        PreparedStatement updateStmt = databaseConnection.prepareStatement(updateQuery);
        updateStmt.executeUpdate();
        setQuantity(request.getWine(),quantity);
        Response response = new Response(ResponseType.status, ObjectType.request);
        response.statusBoolean = true;
        output.writeObject(response);
        output.flush();
    }

    /**
     * It is used to update the quantity of a wine.
     *
     * @param wine the wine to be updated.
     * @param quantity the new quantity.
     * @throws SQLException if the connection with Database fails.
     * @throws IOException if the response creation fails.
     */
    private void updateQuantity(Wine wine, int quantity) throws SQLException, IOException {
        String updateQuery = "UPDATE wine SET Quantity =" + quantity + " WHERE wine.ID=" + wine.getId();
        PreparedStatement updateStmt = databaseConnection.prepareStatement(updateQuery);
        updateStmt.executeUpdate();
        processAllRequests(wine, quantity);
        Response response = new Response(ResponseType.status, ObjectType.wine);
        response.statusBoolean = true;
        output.writeObject(response);
        output.flush();
    }

    /**
     * it is used to process all requests for a wine if the required quantity
     * of wine is less than or equal to the available quantity.
     *
     * @param wine the wine.
     * @param quantity the new quantity available.
     * @throws SQLException if the connection with Database fails.
     */
    private void processAllRequests(Wine wine, int quantity) throws SQLException {
        String sql = "SELECT * FROM request,person WHERE WINE_ID=" + wine.getId() + " AND Processed=0";
        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next()){
            if(resultSet.getInt("request.Quantity") <= quantity)
                updateRequest(resultSet.getInt("request.ID"));
        }
    }

    /**
     * It is used to update a request by id.
     *
     * @param id the request id.
     * @throws SQLException if the connection with Database fails.
     */
    private void updateRequest(int id) throws SQLException {
        String updateQuery = "UPDATE request SET Processed=1 WHERE request.ID=" + id;
        PreparedStatement updateStmt = databaseConnection.prepareStatement(updateQuery);
        updateStmt.executeUpdate();
    }

    /**
     * It is used to get the number of wines not available.
     *
     * @throws SQLException if the connection with Database fails.
     * @throws IOException if the response creation fails.
     */
    private void getNotificationsNumber() throws SQLException, IOException{
        Response response = new Response(ResponseType.count, ObjectType.wine);
        stmt = databaseConnection.createStatement();
        String sql = "SELECT COUNT(*) FROM wine WHERE wine.Quantity=0";
        ResultSet resultSet = stmt.executeQuery(sql);
        resultSet.next();
        response.countInt=resultSet.getInt(1);
        resultSet.close();
        output.writeObject(response);
        output.flush();
    }

    /**
     * It is used to get all wines not available.
     *
     * @throws SQLException if the connection with Database fails.
     * @throws IOException if the response creation fails.
     */
    private void getFinishedWines() throws SQLException, IOException{
        List<Wine> wineList = new ArrayList<>();
        stmt = databaseConnection.createStatement();
        String sql = "SELECT * FROM wine WHERE Quantity=0";
        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next()){
            wineList.add(new Wine(resultSet.getInt("ID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Producer"),
                    resultSet.getString("Year"),
                    resultSet.getString("Notes"),
                    resultSet.getString("Vines"),
                    resultSet.getInt("Quantity")));
        }
        resultSet.close();
        Response response = new Response(ResponseType.sendList, ObjectType.wine);
        response.wineList = wineList;
        output.writeObject(response);
        output.flush();
    }
}

