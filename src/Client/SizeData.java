package Client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SizeData {
    private final StringProperty kg;
    private final StringProperty kmi;
    private final StringProperty shoulders;
    private final StringProperty chest;
    private final StringProperty rbiceps;
    private final StringProperty lbiceps;
    private final StringProperty waists;
    private final StringProperty waistb;
    private final StringProperty glutes;
    private final StringProperty lthight;
    private final StringProperty rthight;
    private final StringProperty date;

    public SizeData(String kg, String kmi, String shoulders, String chest, String rbiceps, String lbiceps, String waists, String waistb, String glutes, String lthight, String rthight, String date) {
        this.kg = new SimpleStringProperty(kg);
        this.kmi = new SimpleStringProperty(kmi);
        this.shoulders = new SimpleStringProperty(shoulders);
        this.chest = new SimpleStringProperty(chest);
        this.rbiceps = new SimpleStringProperty(rbiceps);
        this.lbiceps = new SimpleStringProperty(lbiceps);
        this.waists = new SimpleStringProperty(waists);
        this.waistb = new SimpleStringProperty(waistb);
        this.glutes = new SimpleStringProperty(glutes);
        this.lthight = new SimpleStringProperty(lthight);
        this.rthight = new SimpleStringProperty(rthight);
        this.date = new SimpleStringProperty(date);
    }


    public String getKg() {
        return kg.get();
    }

    public StringProperty kgProperty() {
        return kg;
    }

    public void setKg(String kg) {
        this.kg.set(kg);
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

    public String getKmi() {
        return kmi.get();
    }

    public StringProperty kmiProperty() {
        return kmi;
    }

    public void setKmi(String kmi) {
        this.kmi.set(kmi);
    }

    public String getShoulders() {
        return shoulders.get();
    }

    public StringProperty shouldersProperty() {
        return shoulders;
    }

    public void setShoulders(String shoulders) {
        this.shoulders.set(shoulders);
    }

    public String getChest() {
        return chest.get();
    }

    public StringProperty chestProperty() {
        return chest;
    }

    public void setChest(String chest) {
        this.chest.set(chest);
    }

    public String getRbiceps() {
        return rbiceps.get();
    }

    public StringProperty rbicepsProperty() {
        return rbiceps;
    }

    public void setRbiceps(String rbiceps) {
        this.rbiceps.set(rbiceps);
    }

    public String getLbiceps() {
        return lbiceps.get();
    }

    public StringProperty lbicepsProperty() {
        return lbiceps;
    }

    public void setLbiceps(String lbiceps) {
        this.lbiceps.set(lbiceps);
    }

    public String getWaists() {
        return waists.get();
    }

    public StringProperty waistsProperty() {
        return waists;
    }

    public void setWaists(String waists) {
        this.waists.set(waists);
    }

    public String getWaistb() {
        return waistb.get();
    }

    public StringProperty waistbProperty() {
        return waistb;
    }

    public void setWaistb(String waistb) {
        this.waistb.set(waistb);
    }

    public String getGlutes() {
        return glutes.get();
    }

    public StringProperty glutesProperty() {
        return glutes;
    }

    public void setGlutes(String glutes) {
        this.glutes.set(glutes);
    }

    public String getLthight() {
        return lthight.get();
    }

    public StringProperty lthightProperty() {
        return lthight;
    }

    public void setLthight(String lthight) {
        this.lthight.set(lthight);
    }

    public String getRthight() {
        return rthight.get();
    }

    public StringProperty rthightProperty() {
        return rthight;
    }

    public void setRthight(String rthight) {
        this.rthight.set(rthight);
    }
}
