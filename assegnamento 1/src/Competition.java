public class Competition extends  Activity{
    /**
     * Class constructor
     *
     * @param name the name of the competition
     * @param personList the list of the people
     */
    public Competition(String name, Person[] personList) {
        super(name, personList);
    }

    /**
     * Class constructor
     *
     * @param name the name of the competition
     */
    public Competition(String name) {
        super(name);
    }
}
