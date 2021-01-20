import java.io.IOException;

public class Administrator extends Person{
    /**
     * Class constructor
     *
     * @param name the name of the administrator
     * @param surname the surname of the administrator
     * @param email the email of the administrator
     * @param password the password of the administrator
     */
    public Administrator(String name, String surname, String email, String password) {
        super(name, surname, email, password);
    }

    /**
     * It is used to add a person to a club
     *
     * @param club the club
     * @param person the person
     * @return a string that contains the name of the administrator and the name of the person who was added
     */
    public String addUser(Club club, Person person){
        Person[] tmpList = new Person[club.getPersonList().length + 1];

        System.arraycopy(club.getPersonList(), 0, tmpList,0, club.getPersonList().length);
        tmpList[tmpList.length - 1] = person;

        club.setPersonList(tmpList);

        return this.getName() + " has added " + person.getName() + "\n";
    }

    /**
     * It is used to remove a person from a club
     *
     * @param club the club
     * @param person the person
     * @return a string that contains the name of the administrator and the name of the person who was removed
     */
    public String removeUser(Club club, Person person){
        try {
            for (int i = 0; i < club.getPersonList().length; i++) {
                if (club.getPersonList()[i] == person) {
                    Person tmpPerson = club.getPersonList()[i];
                    club.getPersonList()[i] = club.getPersonList()[club.getPersonList().length - 1];
                    club.getPersonList()[club.getPersonList().length - 1] = tmpPerson;

                    Person[] tmpList = new Person[club.getPersonList().length - 1];

                    System.arraycopy(club.getPersonList(), 0, tmpList, 0, club.getPersonList().length - 1);

                    club.setPersonList(tmpList);

                    return this.getName() + " has removed " + tmpPerson.getName() + "\n";
                }
            }

            return null;

        }catch(Exception e) {
            return e.getMessage();
        }
    }

    /**
     * It is used to update a information of an user
     *
     * @param club the club
     * @param person the person
     * @param attribute the name of the information
     * @param value the value of the information to set
     * @return a string that contains the confirmation of the update
     */
    public String updateUser(Club club, Person person, String attribute, String value){
        try {
            for (int i = 0; i < club.getPersonList().length; i++) {
                if (club.getPersonList()[i] == person) {
                    switch (attribute) {
                        case "name" -> club.getPersonList()[i].setName(value);
                        case "surname" -> club.getPersonList()[i].setSurname(value);
                        case "email" -> club.getPersonList()[i].setEmail(value);
                        case "password" -> club.getPersonList()[i].setPassword(value);
                    }
                }
            }

            return this.getName() + " has updated the " + attribute + " of " + person.getName() + " " + person.getSurname() + "\n";

        }catch(Exception e) {
            return e.getMessage();
        }
    }

    /**
     * It is used to add an activity to a club
     *
     * @param club the club
     * @param activity the activity
     * @return a string that contains the name of the administrator and the name of the course or competition that was added
     */
    public String addActivity(Club club, Activity activity){
        Activity[] tmpList = new Activity[club.getActivityList().length + 1];

        System.arraycopy(club.getActivityList(), 0, tmpList, 0, club.getActivityList().length);
        tmpList[tmpList.length - 1] = activity;

        club.setActivityList(tmpList);

        if (activity instanceof Course)
            return this.getName() + " has added the course " + activity.getName() + "\n";
        else if (activity instanceof Competition)
            return this.getName() + " has added the competition " + activity.getName() + "\n";

        return null;
    }

    /**
     * It is used to remove an activity from a club
     *
     * @param club the club
     * @param activity the activity
     * @return a string that contains the name of the administrator and the name of the course or competition that was removed
     */
    public String removeActivity(Club club, Activity activity){
        try{
            for (int i = 0; i < club.getActivityList().length; i++) {
                if (club.getActivityList()[i].getName().equals(activity.getName())){
                    Activity tmpActivity = club.getActivityList()[i];
                    club.getActivityList()[i] = club.getActivityList()[club.getActivityList().length - 1];
                    club.getActivityList()[club.getActivityList().length - 1] = tmpActivity;

                    Activity[] tmpList = new Activity[club.getActivityList().length - 1];

                    System.arraycopy(club.getActivityList(), 0, tmpList, 0, club.getActivityList().length - 1);

                    club.setActivityList(tmpList);

                    if(club.getActivityList()[i] instanceof Course)
                        return this.getName() + " has removed the course " + club.getActivityList()[i].getName() + "\n";
                    else if(club.getActivityList()[i] instanceof Competition)
                        return this.getName() + " has removed the competition " + club.getActivityList()[i].getName() + "\n";
                }
            }

            return null;

        }catch(Exception e) {
            return e.getMessage();
        }
    }

    /**
     * It is used to update the name of an activity
     *
     * @param club the club
     * @param activity the activity
     * @param value the name of activity to set
     * @return a string that contains the confirmation of the update
     */
    public String updateActivity(Club club, Activity activity, String value){
        for (int i = 0; i < club.getPersonList().length; i++) {
            if (club.getActivityList()[i] == activity){
                club.getActivityList()[i].setName(value);
            }
        }

        return this.getName() + " has updated the name of " + activity.getName() + "\n";
    }
}