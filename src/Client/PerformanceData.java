package Client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PerformanceData {
    private final StringProperty exercise;
    private final StringProperty rep;
    private final StringProperty set;
    private final StringProperty weight;
    private final StringProperty date;
    private final StringProperty id;

    public PerformanceData(String exercise, String rep, String set, String weight, String date, String id) {
        this.exercise = new SimpleStringProperty(exercise);
        this.rep = new SimpleStringProperty(rep);
        this.set = new SimpleStringProperty(set);
        this.weight = new SimpleStringProperty(weight);
        this.date = new SimpleStringProperty(date);
        this.id = new SimpleStringProperty(id);
    }

    public String getExercise() {
        return exercise.get();
    }

    public StringProperty exerciseProperty() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise.set(exercise);
    }

    public String getRep() {
        return rep.get();
    }

    public StringProperty repProperty() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep.set(rep);
    }

    public String getSet() {
        return set.get();
    }

    public StringProperty setProperty() {
        return set;
    }

    public void setSet(String set) {
        this.set.set(set);
    }

    public String getWeight() {
        return weight.get();
    }

    public StringProperty weightProperty() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight.set(weight);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }
}
