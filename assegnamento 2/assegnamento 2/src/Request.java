public class Request {
    private Wine wine;
    private User user;
    private int quantity;
    private boolean processed;

    /**
     * Class constructor
     *
     * @param wine the wine
     * @param user the user
     * @param quantity the quantity requested
     */
    public Request(Wine wine, User user, int quantity) {
        this.wine = wine;
        this.user = user;
        this.quantity = quantity;
        this.processed = false;
    }

    /**
     *
     * @return the wine requested
     */
    public Wine getWine() {
        return wine;
    }

    /**
     *
     * @param wine the wine requested to set
     */
    public void setWine(Wine wine) {
        this.wine = wine;
    }

    /**
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return the requested quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity the requested quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return True if the request has been processed. False if the request has not been processed
     */
    public boolean isProcessed() {
        return processed;
    }

    /**
     *
     * @param processed the processing status of the request to set
     */
    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    /**
     *
     * @return a description of the request
     */
    @Override
    public String toString(){
        return "Requested wine: " + wine.toString() + "\n\tUser: " + user.getEmail() + "\n\tQuantity requested: " + this.quantity + "\n";
    }
}
