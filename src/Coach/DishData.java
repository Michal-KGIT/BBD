package Coach;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DishData {
    private final StringProperty group;
    private final StringProperty dishName;
    private final StringProperty kcal;
    private final StringProperty protein;
    private final StringProperty carbs;
    private final StringProperty fats;

    public DishData(String group, String dishName, String kcal, String protein, String carbs, String fats) {
        this.group = new SimpleStringProperty(group);
        this.dishName = new SimpleStringProperty(dishName);
        this.kcal = new SimpleStringProperty(kcal);
        this.protein = new SimpleStringProperty(protein);
        this.carbs = new SimpleStringProperty(carbs);
        this.fats = new SimpleStringProperty(fats);
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

    public String getDishName() {
        return dishName.get();
    }

    public StringProperty dishNameProperty() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName.set(dishName);
    }

    public String getKcal() {
        return kcal.get();
    }

    public StringProperty kcalProperty() {
        return kcal;
    }

    public void setKcal(String kcal) {
        this.kcal.set(kcal);
    }

    public String getProtein() {
        return protein.get();
    }

    public StringProperty proteinProperty() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein.set(protein);
    }

    public String getCarbs() {
        return carbs.get();
    }

    public StringProperty carbsProperty() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs.set(carbs);
    }

    public String getFats() {
        return fats.get();
    }

    public StringProperty fatsProperty() {
        return fats;
    }

    public void setFats(String fats) {
        this.fats.set(fats);
    }
}
