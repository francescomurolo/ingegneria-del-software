public class Club {
    private String name;
    private Person[] personList;
    private Activity[] activityList;

    /**
     * Class constructor
     *
     * @param name the name of the club
     * @param personList the list of the people
     * @param activityList the list of the activities
     */
    public Club(String name, Person[] personList, Activity[] activityList) {
        this.name = name;
        this.personList = personList;
        this.activityList = activityList;
    }

    /**
     *
     * @return the name of the club
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the name of the club to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the list of the people
     */
    public Person[] getPersonList() {
        return personList;
    }

    /**
     *
     * @param personList the list of the people to set
     */
    public void setPersonList(Person[] personList) {
        this.personList = personList;
    }

    /**
     *
     * @return the list of the activities
     */
    public Activity[] getActivityList() {
        return activityList;
    }

    /**
     *
     * @param activityList the list of activities to set
     */
    public void setActivityList(Activity[] activityList) {
        this.activityList = activityList;
    }

    /**
     * It is used to print the list of club people
     *
     * @return a string that is the concatenation of the names and surnames of partners and administrators
     */
    public String printPeople(){
        String result = "";

        for (Person p:personList) {
            if(p instanceof Partner)
                result += "Partner: " + p.getName() + " " + p.getSurname() + "\n";
            else if(p instanceof  Administrator)
                result += "Administrator: " + p.getName() + " " + p.getSurname() + "\n";
        }

        return result;
    }

    /**
     * It is used to print the list of club activities
     *
     * @return a string that is the concatenation of competition and course names
     */
    public String printActivities(){
        String result = "";

        for(Activity a:activityList){
            if(a instanceof Competition)
                result += "Competition: " + a.getName() + "\n";
            else if(a instanceof  Course)
                result += "Course: " + a.getName() + "\n";
        }

        return result;
    }
}
