package model;

/**
 * Employee describes a employee who works at the Wine Shop.
 */
public class Employee extends Person {

    /**
     * Class constructor:
     * It generates an employee from its id, name, surname, email and password.
     *
     * @param id the id of the employee.
     * @param name the name of the employee.
     * @param surname the surname of the employee.
     * @param email the email of the employee.
     * @param password the password of the employee.
     */
    public Employee(int id, String name, String surname, String email, String password) {
        super(id, name, surname, email, password);
    }

    /**
     * Class constructor:
     * It generates an employee from its name, surname, email and password.
     *
     * @param name the name of the employee.
     * @param surname the surname of the employee.
     * @param email the email of the employee.
     * @param password the password of the employee.
     */
    public Employee(String name, String surname, String email, String password) {
        super(name, surname, email, password);
    }

    /**
     * Class constructor:
     * It generates an employee from its email and password.
     *
     * @param email the email of the employee.
     * @param password the password of the employee.
     */
    public Employee(String email, String password) {
        super(email, password);
    }
}