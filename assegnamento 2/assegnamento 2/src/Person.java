public class Person {
    private String name;
    private String surname;
    private String email;
    private String password;
    private boolean status;

    /**
     * Class constructor
     *
     * @param name the name of the person
     * @param surname the surname of the person
     * @param email the email of the person
     * @param password the password of the person
     */
    public Person(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.status = false;
    }

    /**
     *
     * @return the name of the person
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the name of the person to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the surname of the person
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * @param surname the surname of the person to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     *
     * @return the email of the person
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email the email of the person to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return the password of the person
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password the password of the person to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return true if the person has logged in. false if the person has not logged in
     */
    public boolean isStatus() {
        return status;
    }

    /**
     *
     * @param status the login status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }
}
