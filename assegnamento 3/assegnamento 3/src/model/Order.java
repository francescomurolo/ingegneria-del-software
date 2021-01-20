package model;

import javafx.beans.property.*;

import java.io.Serializable;

/**
 * Order describes a wine order.
 */
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * The order id.
     */
    private int id;

    /**
     * The ordered wine.
     */
    private Wine wine;

    /**
     * The user that ordered the wine.
     */
    private User user;

    /**
     * The ordered quantity of wine.
     */
    private int quantity;

    /**
     * The shipping status.
     */
    private boolean shipped;

    /**
     * Class constructor:
     * It generates an order from its id, wine, user, quantity and shipping status.
     *
     * @param id the order id.
     * @param wine the wine.
     * @param user the user.
     * @param quantity the wine quantity to order.
     * @param shipped the shipping status.
     */
    public Order(int id, Wine wine, User user, int quantity, boolean shipped) {
        this.id = id;
        this.wine = wine;
        this.user = user;
        this.quantity = quantity;
        this.shipped = shipped;
    }

    /**
     * Gets the order id.
     *
     * @return the id of the order.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the order id.
     *
     * @param id the id of the order to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the ordered wine.
     *
     * @return the wine of the order.
     */
    public Wine getWine() {
        return wine;
    }

    /**
     * Sets the ordered wine.
     *
     * @param wine the ordered wine to set.
     */
    public void setWine(Wine wine) {
        this.wine = wine;
    }

    /**
     * Gets the user that ordered the wine.
     *
     * @return the user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user that ordered the wine.
     *
     * @param user the user.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the ordered quantity.
     *
     * @return the ordered quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the ordered quantity.
     *
     * @param quantity the ordered quantity to set.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the shipping status.
     *
     * @return true if the order has been shipped. false if if the order has not been shipped.
     */
    public boolean isShipped() {
        return shipped;
    }

    /**
     * Sets the shipping status.
     *
     * @param shipped the shipping status to set.
     */
    public void setShipped(boolean shipped) {
        this.shipped = shipped;
    }

    /**
     * Gets the IntegerProperty value of order quantity.
     *
     * @return the quantity.
     */
    public IntegerProperty quantityProperty(){
        return new SimpleIntegerProperty(quantity);
    }

    /**
     * Gets the BooleanProperty value of shipping status.
     *
     * @return the shipping status.
     */
    public BooleanProperty shippedProperty(){
        return new SimpleBooleanProperty(shipped);
    }

    /**
     * Gets the StringProperty value of shipping status.
     *
     * @return "Shipped" if shipping status is true. "Not Shipped" if shipping status is false.
     */
    public StringProperty getOrderStatus (){
        String status = this.shipped ? "Shipped" : "Not Shipped";
        return new SimpleStringProperty(status);
    }
}
