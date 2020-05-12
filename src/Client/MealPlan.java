package Client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MealPlan {
    private final StringProperty group;
    private final StringProperty dish;
    private final StringProperty grams;
    private final StringProperty dateCreated;

    public MealPlan(String group, String dish, String grams, String dateCreated) {
        this.group = new SimpleStringProperty(group);
        this.dish = new SimpleStringProperty(dish);
        this.grams = new SimpleStringProperty(grams);
        this.dateCreated = new SimpleStringProperty(dateCreated);
    }

    public String getDateCreated() {
        return dateCreated.get();
    }

    public StringProperty dateCreatedProperty() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated.set(dateCreated);
    }

    public String getGroup() {
        return group.get();
    }

    public StringProperty groupProperty() {
        return group;
    }

    public void setGroup(String group) {
        this.group.set(group);
    }

    public String getDish() {
        return dish.get();
    }

    public StringProperty dishProperty() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish.set(dish);
    }

    public String getGrams() {
        return grams.get();
    }

    public StringProperty gramsProperty() {
        return grams;
    }

    public void setGrams(String grams) {
        this.grams.set(grams);
    }
}
