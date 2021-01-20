package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

/**
 * Wine describes a single wine.
 */
public class Wine implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * The wine id.
     */
    private int id;

    /**
     * The wine name.
     */
    private String name;

    /**
     * The wine producer.
     */
    private String producer;

    /**
     * The wine year.
     */
    private String year;

    /**
     * The wine notes.
     */
    private String notes;

    /**
     * The wine vines.
     */
    private String vines;

    /**
     * The wine quantity.
     */
    private int quantity;

    /**
     * Class constructor:
     * It generates a empty wine object.
     */
    public Wine() {
        this.id = 0;
        this.name = "";
        this.producer = "";
        this.year = "";
        this.notes = "";
        this.vines = "";
        this.quantity = 0;
    }
    /**
     * Class constructor:
     * It generates a wine from its id, name, producer, year, notes, vines and quantity.
     *
     * @param id the id of the wine.
     * @param name the name of the wine.
     * @param producer the producer of the wine.
     * @param year the year of the wine.
     * @param notes the notes of the wine.
     * @param vines the vines of the wine.
     * @param quantity the quantity available of the wine.
     */
    public Wine(int id, String name, String producer, String year, String notes, String vines, int quantity) {
        this.id = id;
        this.name = name;
        this.producer = producer;
        this.year = year;
        this.notes = notes;
        this.vines = vines;
        this.quantity = quantity;
    }

    /**
     * Class constructor:
     * It generates a wine from its name, producer, year, notes, vines and quantity.
     *
     * @param name the name of the wine.
     * @param producer the producer of the wine.
     * @param year the year of the wine.
     * @param notes the notes of the wine.
     * @param vines the vines of the wine.
     * @param quantity the quantity available of the wine.
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
     * Gets the the wine id.
     *
     * @return the id of the wine.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the wine id.
     *
     * @param id the id of the wine to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the wine name.
     *
     * @return the name of the wine.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the wine name.
     *
     * @param name the name of the wine to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the wine producer.
     *
     * @return the producer of the wine.
     */
    public String getProducer() {
        return producer;
    }

    /**
     * Sets the wine producer.
     *
     * @param producer the producer of the wine to set.
     */
    public void setProducer(String producer) {
        this.producer = producer;
    }

    /**
     * Gets the wine year.
     *
     * @return the year of the wine.
     */
    public String getYear() {
        return year;
    }

    /**
     * Sets the wine year.
     *
     * @param year the year of the wine to set.
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * Gets the wine notes.
     *
     * @return the notes of the wine.
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the wine notes.
     *
     * @param notes the notes of the wine to set.
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Gets the wine vines.
     *
     * @return the vines of the wine.
     */
    public String getVines() {
        return vines;
    }

    /**
     * Sets the wine wines.
     *
     * @param vines the vines of the wine to set.
     */
    public void setVines(String vines) {
        this.vines = vines;
    }

    /**
     * Gets the wine quantity.
     *
     * @return the available quantity of the wine.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the wine quantity.
     *
     * @param quantity the available quantity of the wine to set.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the StringProperty value of wine name.
     *
     * @return the name.
     */
    public StringProperty nameProperty(){
        return new SimpleStringProperty(name);
    }

    /**
     * Gets the StringProperty value of wine producer.
     *
     * @return the producer.
     */
    public StringProperty producerProperty(){
        return new SimpleStringProperty(producer);
    }

    /**
     * Gets the StringProperty value of wine vines.
     *
     * @return the vines.
     */
    public StringProperty vinesProperty(){
        return new SimpleStringProperty(vines);
    }

    /**
     * Gets the StringProperty value of wine notes.
     *
     * @return the notes.
     */
    public StringProperty notesProperty(){
        return new SimpleStringProperty(notes);
    }

    /**
     * Gets the IntegerProperty value of wine quantity.
     *
     * @return the quantity.
     */
    public IntegerProperty quantityProperty(){
        return new SimpleIntegerProperty(quantity);
    }

    /**
     * Gets the StringProperty value of wine year.
     *
     * @return the year.
     */
    public StringProperty yearProperty(){
        return new SimpleStringProperty(year);
    }
}
