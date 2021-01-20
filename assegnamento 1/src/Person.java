public class Person {
    private String name;
    private String surname;
    private String email;
    private String password;

    /**
     * Class constructor
     *
     * @param name the name of the person
     * @param surname the surname of the person
     * @param email the email of the person
     * @param password the password of the person
     */
    public Person(String name, String surname, String email, String password){
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
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
     * It is used to subscribe to an activity
     *
     * @param activity the activity
     * @return a string that contains the name of the person who subscribed and the name of the activity
     */
    public String subscribe(Activity activity){
        Person[] tmpList = new Person[activity.getPersonList().length + 1];

        System.arraycopy(activity.getPersonList(), 0, tmpList,0, activity.getPersonList().length);
        tmpList[tmpList.length - 1] = this;

        activity.setPersonList(tmpList);

        return this.name + " has subscribed to " + activity.getName() + "\n";
    }

    /**
     * It is used to unsubscribe from an activity
     *
     * @param activity the activity
     * @return a string that contains the name of the person who unsubscribed and the name of the activity
     */
    public String unsubscribe(Activity activity) {
        try {
            for (int i = 0; i < activity.getPersonList().length; i++) {
                if (activity.getPersonList()[i] == this) {
                    Person tmpPerson = activity.getPersonList()[i];
                    activity.getPersonList()[i] = activity.getPersonList()[activity.getPersonList().length - 1];
                    activity.getPersonList()[activity.getPersonList().length - 1] = tmpPerson;

                    Person[] tmpList = new Person[activity.getPersonList().length - 1];

                    System.arraycopy(activity.getPersonList(), 0, tmpList, 0, activity.getPersonList().length - 1);

                    activity.setPersonList(tmpList);

                    return tmpPerson.getName() + " has unsubscribed from " + activity.getName() + "\n";
                }
            }

            return null;

        }catch(Exception e) {
            return e.getMessage();
        }
    }
}
