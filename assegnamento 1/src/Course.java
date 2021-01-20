public class Course extends Activity{
    /**
     * Class constructor
     *
     * @param name the name of the course
     * @param personList the list of the people
     */
    public Course(String name, Person[] personList) {
        super(name, personList);
    }

    /**
     * Class constructor
     *
     * @param name the name of the course
     */
    public Course(String name) {
        super(name);
    }
}
