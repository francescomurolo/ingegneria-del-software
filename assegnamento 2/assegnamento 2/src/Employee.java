public class Employee extends Person{
    /**
     * Class constructor
     *
     * @param name the name of the employee
     * @param surname the surname of the employee
     * @param email the email of the employee
     * @param password the password of the employee
     */
    public Employee(String name, String surname, String email, String password) {
        super(name, surname, email, password);
    }

    /**
     * It is used to add a new wine to the wine list of WineShop
     *
     * @param wine the wine
     */
    public void addWine(Wine wine){
        WineShop.wineList.add(wine);
    }

    /**
     * It is used to ship an order
     *
     * @param order the order
     */
    public void shipOrder(Order order){
        for (int i=0;i<WineShop.orderList.size();i++) {
            if(WineShop.orderList.get(i).equals(order) && !WineShop.orderList.get(i).isShipped()) {
                WineShop.orderList.get(i).setEmployee(this);
                WineShop.orderList.get(i).setShipped(true);
            }else
                System.out.println("The order " + i +" has already been shipped");
        }
    }

    /**
     * It is used to update the quantity of a wine
     *
     * @param wine the wine
     * @param quantity the quantity to set
     */
    public void updateQuantity(Wine wine, int quantity){
        for(Wine w:WineShop.wineList){
            if(w.equals(wine)) {
                w.setQuantity(w.getQuantity() + quantity);
                System.out.println("The quantity of wine: " + wine.getName() + " " + wine.getProducer() + " " + wine.getYear() + " has been updated");
            }
        }
    }

    /**
     * It is used to print all the requests
     */
    public void printRequests(){
        System.out.println("Requests: ");
        for(int i = 0;i < WineShop.requestList.size();i++){
            System.out.println(i +") " + WineShop.requestList.get(i).toString());
        }
    }

    /**
     * it is used to process a request
     *
     * @param request the request
     */
    public void processRequest(Request request){
        for(Wine w:WineShop.wineList){
            if(w.equals(request.getWine())) {
                updateQuantity(w, request.getQuantity());
                for(Request r:WineShop.requestList){
                    if(r.equals(request)){
                        r.setProcessed(true);
                        System.out.println("The request has been processed successfully");
                    }
                }
            }
        }
    }

    /**
     * It is used to print all unavailable wines
     */
    public void readNotifications(){
        for(Wine w:WineShop.wineList){
            if(w.getQuantity() == 0){
                System.out.println("The wine: " + w.toString() + " is not available (Quantity = 0)");
            }
        }
    }

}
