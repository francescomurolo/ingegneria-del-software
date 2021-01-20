package model;

import javafx.beans.property.*;

import java.io.Serializable;

/**
 * Request describes a wine request.
 */
public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * The request id.
     */
    private int id;

    /**
     * The requested wine.
     */
    private Wine wine;

    /**
     * The user that requested the wine.
     */
    private User user;

    /**
     * The requested quantity of wine.
     */
    private int quantity;

    /**
     * The processing status.
     */
    private boolean processed;

    /**
     * The display status of the processed request by the user.
     */
    private boolean seen;

    /**
     * Class constructor:
     * It generates a request from its id, wine, user, quantity, processing status and display status.
     *
     * @param id the id.
     * @param wine the wine.
     * @param user the user.
     * @param quantity the quantity requested.
     * @param processed the processing status.
     * @param seen the display status.
     */
    public Request(int id, Wine wine, User user, int quantity, boolean processed, boolean seen) {
        this.id = id;
        this.wine = wine;
        this.user = user;
        this.quantity = quantity;
        this.processed = processed;
        this.seen = seen;
    }

    /**
     * Class constructor:
     * It generates a request from its id, wine, user, quantity, processing status.
     *
     * @param id the id.
     * @param wine the wine.
     * @param user the user.
     * @param quantity the quantity requested.
     * @param processed the processing status.
     */
    public Request(int id, Wine wine, User user, int quantity, boolean processed) {
        this.id = id;
        this.wine = wine;
        this.user = user;
        this.quantity = quantity;
        this.processed = processed;
    }

    /**
     * Gets the request id.
     *
     * @return the id of the request.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the request id.
     *
     * @param id the id of the request to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the requested wine.
     *
     * @return the wine requested.
     */
    public Wine getWine() {
        return wine;
    }

    /**
     * Sets the requested wine.
     *
     * @param wine the wine requested to set.
     */
    public void setWine(Wine wine) {
        this.wine = wine;
    }

    /**
     * Gets the user that requested the wine.
     *
     * @return the user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user that requested the wine.
     *
     * @param user the user to set.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the requested quantity.
     *
     * @return the requested quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the requested quantity.
     *
     * @param quantity the requested quantity to set.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the processing status.
     *
     * @return True if the request has been processed. False if the request has not been processed.
     */
    public boolean isProcessed() {
        return processed;
    }

    /**
     * Set the processing status.
     *
     * @param processed the processing status of the request to set.
     */
    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    /**
     * Gets the display status of the processed request by the user.
     *
     * @return True if the request status has been seen by the user. False if the request status has not been seen by the user.
     */
    public boolean isSeen() {
        return seen;
    }

    /**
     * Sets the display status.
     *
     * @param seen the display status to set.
     */
    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    /**
     * Gets the IntegerProperty value of requested quantity.
     *
     * @return the quantity.
     */
    public IntegerProperty quantityProperty(){
        return new SimpleIntegerProperty(quantity);
    }

    /**
     * Gets the BooleanProperty value of processing status.
     *
     * @return the processing status.
     */
    public BooleanProperty processedProperty(){
        return new SimpleBooleanProperty(processed);
    }

    /**
     * Gets the BooleanProperty value of display status.
     *
     * @return the display status.
     */
    public BooleanProperty seenProperty() {
        return new SimpleBooleanProperty(seen);
    }

    /**
     * Gets the StringProperty value of processing status.
     *
     * @return "Processed" if processing status is true. "Unprocessed" if processing status is false.
     */
    public StringProperty getRequestStatus (){
        String status = this.processed ? "Processed" : "Unprocessed";
        return new SimpleStringProperty(status);
    }
}
