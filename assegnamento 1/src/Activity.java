public class Activity {
    private String name;
    private Person[] personList;

    /**
     * Class constructor
     *
     * @param name the name of the activity
     * @param personList the list of the people
     */
    public Activity(String name, Person[] personList) {
        this.name = name;
        this.personList = personList;
    }

    /**
     * Class constructor
     *
     * @param name the name of the activity
     */
    public Activity(String name) {
        this.name = name;
        this.personList = new Person[0];
    }

    /**
     *
     * @return the name of the activity
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the name of the activity to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the list of people
     */
    public Person[] getPersonList() {
        return personList;
    }

    /**
     *
     * @param personList the list of people to set
     */
    public void setPersonList(Person[] personList) {
        this.personList = personList;
    }

    /**
     * It is used to print the number of people subscribed and the list of people
     *
     * @return a string that contains the number of people subscribed and the list of names and surnames of people
     */
    public String printSubscriptions(){
        String result = "Subscribed to " + name + ": " + personList.length + "\n";

        for (Person p: personList)
            result += p.getName() + " " + p.getSurname() + "\n";

        return result;
    }
}
