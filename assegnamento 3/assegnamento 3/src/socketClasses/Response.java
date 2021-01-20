package socketClasses;

import model.*;

import java.io.Serializable;
import java.util.List;

/**
 * This class provides a model of a response message.
 *
 * The server use this class to response to client.
 */
public class Response implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * List of wines.
     */
    public List<Wine> wineList;

    /**
     * List of orders.
     */
    public List<Order> orderList;

    /**
     * List of requests.
     */
    public List<Request> requestList;

    /**
     * List of employee.
     */
    public List<Employee> employeeList;

    /**
     * List of users.
     */
    public List<User> userList;

    /**
     * Person object.
     */
    public Person person;

    /**
     * boolean value.
     */
    public boolean statusBoolean;

    /**
     * int value.
     */
    public int countInt;

    /**
     * The type of response.
     */
    public ResponseType responseType;

    /**
     * The object type.
     */
    public ObjectType objectType;

    /**
     * Class constructor:
     * It generates a socketClasses.Response object from its response type and object type.
     *
     * @param responseType the type of response.
     * @param objectType the type of object.
     */
    public Response(ResponseType responseType, ObjectType objectType){
        this.responseType = responseType;
        this.objectType = objectType;
    }

    /**
     * CLass constructor:
     * It generates a empty socketClasses.Response object.
     */
    public Response() {}
}