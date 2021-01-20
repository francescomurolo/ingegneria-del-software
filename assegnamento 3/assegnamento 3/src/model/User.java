package model;

/**
 * User describes a customer of the wine shop.
 */
public class User extends Person {

    /**
     * Class constructor:
     * It generates a user from its id, name, surname, email, password
     *
     * @param id the id of the user
     * @param name the name of the user
     * @param surname the surname of the user
     * @param email the email of the user
     * @param password the password of the user
     */
    public User(int id, String name, String surname, String email, String password) {
        super(id, name, surname, email, password);
    }
    /**
     * Class constructor:
     * It generates a user from its name, surname, email, password
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
     * Class constructor:
     * It generates a user from its email and password
     *
     * @param email the email of the user
     * @param password the password of the user
     */
    public User(String email, String password){
        super(email, password);
    }
}
