public class Order {
    private Wine wine;
    private User client;
    private Employee employee;
    private int quantity;
    private boolean shipped;

    /**
     * Class constructor
     *
     * @param wine the wine
     * @param client the client
     * @param quantity the wine quantity
     */
    public Order(Wine wine, User client, int quantity) {
        this.wine = wine;
        this.client = client;
        this.quantity = quantity;
        this.shipped = false;
    }

    /**
     *
     * @return the wine
     */
    public Wine getWine() {
        return wine;
    }

    /**
     *
     * @return the client
     */
    public User getClient() {
        return client;
    }

    /**
     *
     * @return the employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     *
     * @param employee the employee to set
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     *
     * @return the wine quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity the wine quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return true if the order has been shipped. false if if the order has not been shipped
     */
    public boolean isShipped() {
        return shipped;
    }

    /**
     *
     * @param shipped the shipment status to set
     */
    public void setShipped(boolean shipped) {
        this.shipped = shipped;
    }

    /**
     *
     * @return a description of the order
     */
    @Override
    public String toString(){
        if(shipped)
            return "Wine: " + wine.toString() + " - Shipped";
        else
            return "Wine: " + wine.toString() + " - Not shipped";
    }
}
