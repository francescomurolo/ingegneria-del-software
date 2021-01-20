package model;

/**
 * Admin describes the administrator of Wine Shop.
 */
public class Admin extends Person {

    /**
     * Class constructor:
     * It generates an admin from its id, name, surname, email, password.
     *
     * @param id the id of the admin.
     * @param name the name of the admin.
     * @param surname the surname of the admin.
     * @param email the email of the admin.
     * @param password the password of the admin.
     */
    public Admin(int id, String name, String surname, String email, String password) {
        super(id, name, surname, email, password);
    }

    /**
     * Class constructor:
     * It generates an admin from its name, surname, email, password.
     *
     * @param name the name of the admin.
     * @param surname the surname of the admin.
     * @param email the email of the admin.
     * @param password the password of the admin.
     */
    public Admin(String name, String surname, String email, String password) {
        super(name, surname, email, password);
    }


    /**
     * Class constructor:
     * It generates an admin from its email and password.
     *
     * @param email the email of the admin.
     * @param password the password of the admin.
     */
    public Admin(String email, String password) {
        super(email, password);
    }
}
