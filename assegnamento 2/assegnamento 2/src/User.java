import java.util.ArrayList;

public class User extends Person{
    /**
     *
     * @param name the name of the user
     * @param surname the surname of the user
     * @param email the email of the user
     * @param password the password of the user
     */
    public User(String name, String surname, String email, String password) {
        super(name, surname, email, password);
    }

    /**
     * It is used to buy a certain amount of wine
     *
     * @param wine the wine
     * @param quantity the quantity
     */
    public void buyWine(Wine wine, int quantity){
        if(quantity <= wine.getQuantity()) {
            WineShop.orderList.add(new Order(wine, this, quantity));
            for(Wine w:WineShop.wineList){
                if(w.equals(wine)){
                    w.setQuantity(w.getQuantity() - quantity);
                }
            }
            System.out.println("Order created successfully");
        }else
            System.out.println("ERROR: The requested quantity is not available!");
    }

    /**
     * It is used to request a wine that is not available
     *
     * @param wine the wine
     * @param quantity the quantity
     */
    public void requestWine(Wine wine, int quantity){
        WineShop.requestList.add(new Request(wine,this,quantity));
        System.out.println("Request created successfully");
    }

    /**
     * It is used to print all notifications
     */
    public void readNotifications(){
        ArrayList<Request> processedRequests = new ArrayList<>();
        for(Request r:WineShop.requestList){
            if(r.getUser().equals(this) && r.isProcessed()){
                System.out.println("Request wine: " + r.getWine().toString());
                processedRequests.add(r);
            }
        }
        for(Request pRequest:processedRequests){
            WineShop.requestList.remove(pRequest);
        }
    }

    /**
     * it is used to print all orders placed
     */
    public void printOrders(){
        for(int i = 0;i<WineShop.orderList.size();i++){
            if(WineShop.orderList.get(i).getClient().equals(this)){
                System.out.println(i + 1 + ") " + WineShop.orderList.get(i).toString());
            }
        }
    }
}
