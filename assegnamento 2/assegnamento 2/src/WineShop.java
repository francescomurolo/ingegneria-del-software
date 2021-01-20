import java.util.ArrayList;

public class WineShop {
    public static ArrayList<Wine> wineList = new ArrayList<>();
    public static ArrayList<Person> personList = new ArrayList<>();
    public static ArrayList<Order> orderList = new ArrayList<>();
    public static ArrayList<Request> requestList = new ArrayList<>();

    /**
     * It is used to print all the wines
     */
    public static void printWines(){
        for(int i=0;i<wineList.size();i++){
            System.out.println(i + 1 + ") " + wineList.get(i).toString());
        }
    }

    /**
     * It is used to research a wine
     *
     * @param name the name of the wine
     * @param year the year of the wine
     *
     * @return the wine if it was found. null if it was not found
     */
    public static Wine searchWine(String name, String year){
        boolean found = false;
        for(Wine w:WineShop.wineList){
            if(w.getName().equals(name) && w.getYear().equals(year)){
                found = true;
                System.out.println("Searched wine: " + w.toString());
                return w;
            }
        }
        System.out.println("ERROR: Wine not found!");
        return null;
    }

    /**
     * It is used to log into the system
     *
     * @param email the email of the person
     * @param password the password of the person
     * @return the person if he has logged in successfully. null if he has not logged in successfully
     */
    public static Person login(String email, String password){
        for(Person p:personList){
            if(p.getEmail().equals(email) && p.getPassword().equals(password)){
                System.out.println("Welcome " + email);
                p.setStatus(true);
                return p;
            }
        }
        System.out.println("ERROR: Login failed!");
        return null;
    }

    /**
     * It is used to log out of the system
     *
     * @param person the person who wants to log out
     */
    public static void logout(Person person){
        for(Person p:personList){
            if(p.equals(person)){
                p.setStatus(false);
                System.out.println(person.getEmail() + ": logout successful\n");
            }
        }
    }

    /**
     * It is used to signing in the system
     *
     * @param user the user who wants to signing in the system
     */
    public static void signup(User user){
        boolean found = false;
        for(Person p:personList) {
            if (p instanceof User && user.getEmail().equals(p.getEmail())){
                System.out.println("ERROR: Email already present in the system!");
                found = true;
            }
        }

        if(!found) {
            System.out.println(user.getEmail() + ": signup successful");
            user.setStatus(true);
            personList.add(user);
        }
    }
}
