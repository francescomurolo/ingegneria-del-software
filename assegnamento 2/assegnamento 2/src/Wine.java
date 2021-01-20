public class Wine {
    private String name;
    private String producer;
    private String year;
    private String notes;
    private String vines;
    private int quantity;

    /**
     * Class constructor
     *
     * @param name the name of the wine
     * @param producer the producer of the wine
     * @param year the year of the wine
     * @param notes the notes of the wine
     * @param vines the vines of the wine
     * @param quantity the quantity available of the wine
     */
    public Wine(String name, String producer, String year, String notes, String vines, int quantity) {
        this.name = name;
        this.producer = producer;
        this.year = year;
        this.notes = notes;
        this.vines = vines;
        this.quantity = quantity;
    }

    /**
     *
     * @return the name of the wine
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the name of the wine to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the producer of the wine
     */
    public String getProducer() {
        return producer;
    }

    /**
     *
     * @param producer the producer of the wine to set
     */
    public void setProducer(String producer) {
        this.producer = producer;
    }

    /**
     *
     * @return the year of the wine
     */
    public String getYear() {
        return year;
    }

    /**
     *
     * @param year the year of the wine to set
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     *
     * @return the notes of the wine
     */
    public String getNotes() {
        return notes;
    }

    /**
     *
     * @param notes the notes of the wine to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     *
     * @return the vines of the wine
     */
    public String getVines() {
        return vines;
    }

    /**
     *
     * @param vines the vines of the wine to set
     */
    public void setVines(String vines) {
        this.vines = vines;
    }

    /**
     *
     * @return the quantity available of the wine
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity the quantity availabe of the wine to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return a description of the wine
     */
    @Override
    public String toString() {
        return this.name + " - " + this.year + " - " + this.producer + " - " + this.vines + ". Available quantity: " + this.quantity;
    }
}
