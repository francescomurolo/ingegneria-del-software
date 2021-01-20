package socketClasses;

import model.*;

import java.io.Serializable;

/**
 * This class provides a model of a query request message.
 *
 * The client use this class to do requests to server.
 */
public class Query implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * socketClasses.Query type of request.
     */
    public QueryType type;

    /**
     * Object type requested.
     */
    public ObjectType objectType;

    /**
     * Person object.
     */
    public Person person;

    /**
     * Wine object.
     */
    public Wine wine;

    /**
     * Employee object.
     */
    public Employee employee;

    /**
     * User object.
     */
    public User user;

    /**
     * Order object.
     */
    public Order order;

    /**
     * Request object.
     */
    public Request request;

    /**
     * Quantity value.
     */
    public int quantity;

    /**
     * Class constructor:
     * It generates a socketClasses.Query object from its query type and object type.
     *
     * @param type the type of query.
     * @param objectType the type of object.
     */
    public Query(QueryType type, ObjectType objectType) {
        this.type = type;
        this.objectType = objectType;
    }
}
