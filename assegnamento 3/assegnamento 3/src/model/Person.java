package model;

import javafx.beans.property.*;

import java.io.Serializable;

/**
 * Person describes a generic person. It is the parent class of Admin, Employee and User.
 */
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * The person id.
     */
    private int id;

    /**
     * The person name.
     */
    private String name;

    /**
     * The person surname.
     */
    private String surname;

    /**
     * The person email.
     */
    private String email;

    /**
     * The person password.
     */
    private String password;

    /**
     * Class constructor:
     * It generates a empty person object.
     */
    public Person() {
        this.id = 0;
        this.name = "";
        this.surname = "";
        this.email = "";
        this.password = "";
    }

    /**
     * Class constructor:
     * It generates a person from its id, name, surname, email and password.
     *
     * @param id the id of the person.
     * @param name the name of the person.
     * @param surname the surname of the person.
     * @param email the email of the person.
     * @param password the password of the person.
     */
    public Person(int id, String name, String surname, String email, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    /**
     * Class constructor:
     * It generates a person from its name, surname, email and password.
     *
     * @param name the name of the person.
     * @param surname the surname of the person.
     * @param email the email of the person.
     * @param password the password of the person.
     */
    public Person(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    /**
     * Class constructor:
     * It generates a person from its email and password.
     *
     * @param email the email of the person.
     * @param password the password of the person.
     */
    public Person(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Gets the person id.
     *
     * @return the id of the person.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the person id.
     *
     * @param id the id of the person to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the person name.
     *
     * @return the name of the person.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the person name.
     *
     * @param name the name of the person to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the person surname.
     *
     * @return the surname of the person.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the person surname.
     *
     * @param surname the surname of the person to set.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the person email.
     *
     * @return the email of the person.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the person email.
     *
     * @param email the email of the person to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the person password.
     *
     * @return the password of the person.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the person password.
     *
     * @param password the password of the person to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the StringProperty value of the name.
     *
     * @return the name.
     */
    public StringProperty nameProperty(){
        return new SimpleStringProperty(name);
    }

    /**
     * Gets the StringProperty value of the surname.
     *
     * @return the surname.
     */
    public StringProperty surnameProperty(){
        return new SimpleStringProperty(surname);
    }

    /**
     * Gets the StringProperty value of the email.
     *
     * @return the email.
     */
    public StringProperty emailProperty(){
        return new SimpleStringProperty(email);
    }

    /**
     * Gets the StringProperty value of the password.
     *
     * @return the password.
     */
    public StringProperty passwordProperty(){
        return new SimpleStringProperty(password);
    }
}

