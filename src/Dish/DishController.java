package Dish;

import DataBase.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DishController implements Initializable {

    @FXML
    private TextField dish;
    @FXML
    private ComboBox<String> group;
    @FXML
    private TextField kcal;
    @FXML
    private TextField protein;
    @FXML
    private TextField carb;
    @FXML
    private TextField fat;
    @FXML
    private WebView webView;

    private String comboValue;
    private WebEngine engine;
    ObservableList<String> mealTypeList = FXCollections.observableArrayList("Mėsa","Jūros gėrybės","Daržovės","Vaisiai/riešutai","Pieno produktai","Miltiniai produktai","Kruopos","Padažai","Prieskoniai","Pusgaminiai","Maisto papildai","Greitas maistas","Gėrimai","Kiti produktai");

    private String sqlAddNewDish = "INSERT INTO mydb.nutrition (Nutrition_ID, Nutrition_group, Dish_name, Calories, Protein, Carbohydrate, Fat) VALUES (?,?,?,?,?,?,?)";

    public void initialize(URL url, ResourceBundle rb){
        group.setItems(mealTypeList);
        engine = webView.getEngine();
        loadWebPage();
    }

    @FXML
    private void addDish (ActionEvent event){
        if(validateGroup() && validateKCAL() && validateProtein() && validateCarb() && validateFat() && validateDish()){
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps1 = conn.prepareStatement("SELECT max(Nutrition_ID)+1 FROM mydb.nutrition");
            ResultSet rs = ps1.executeQuery();
            String dish_id = "";
            while (rs.next()) {
                dish_id = rs.getString(1);
            }
            PreparedStatement ps = conn.prepareStatement(sqlAddNewDish);
            ps.setString(1, dish_id.toString());
            ps.setString(2, this.comboValue.toString());
            ps.setString(3, this.dish.getText());
            ps.setString(4, this.kcal.getText());
            ps.setString(5, this.protein.getText());
            ps.setString(6, this.carb.getText());
            ps.setString(7, this.fat.getText());
            ps.execute();
            conn.close();
        } catch (Exception e) {
        }clear();
    }
  }

    @FXML
    private void fillCombo(ActionEvent event){
        if (group.getValue() == "Mėsa") {
            comboValue = "Mėsa";
        }else if (group.getValue() == "Jūros gėrybės") {
            comboValue = "Jūros gėrybės";
        }else if (group.getValue() == "Daržovės") {
            comboValue = "Daržovės";
        }else if (group.getValue() == "Vaisiai/riešutai") {
            comboValue = "Vaisiai/riešutai";
        }else if (group.getValue() == "Pieno produktai") {
            comboValue = "Pieno produktai";
        }else if (group.getValue() == "Miltiniai produktai") {
            comboValue = "Miltiniai produktai";
        }else if(group.getValue() == "Kruopos"){
            comboValue = "Kruopos";
        }else if (group.getValue() == "Padažai") {
            comboValue = "Padažai";
        }else if (group.getValue() == "Prieskoniai") {
            comboValue = "Prieskoniai";
        }else if (group.getValue() == "Pusgaminiai") {
            comboValue = "Pusgaminiai";
        }else if (group.getValue() == "Maisto papildai") {
            comboValue = "Maisto papildai";
        }else if (group.getValue() == "Greitas maistas") {
            comboValue = "Greitas maistas";
        }else if (group.getValue() == "Gėrimai") {
            comboValue = "Gėrimai";
        }else if (group.getValue() == "Kiti produktai") {
            comboValue = "Kiti produktai";
        }
    }

    @FXML
    private void clear(){
        dish.setText("");
        protein.setText("");
        carb.setText("");
        kcal.setText("");
        fat.setText("");
    }

    @FXML
    private void loadWebPage(){
        engine.load("https://www.kilo.lt/produktai/");
    }

    private boolean validateKCAL(){
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(kcal.getText());
        if(m.find() && m.group().equals(kcal.getText())){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Blogai įvestas kalorjų kiekis");
            alert.showAndWait();
        }return false;
    }

    private boolean validateProtein(){
        Pattern p = Pattern.compile("[0-9]*[0-9._]*[0-9]");
        Matcher m = p.matcher(protein.getText());
        if(m.find() && m.group().equals(protein.getText())){
                       return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Blogai įvestas baltymų kiekis");
            alert.showAndWait();
        }return false;
    }

    private boolean validateCarb(){
        Pattern p = Pattern.compile("[0-9]*[0-9._]*[0-9]");
        Matcher m = p.matcher(carb.getText());
        if(m.find() && m.group().equals(carb.getText())){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Blogai įvestas angliavandenių kiekis");
            alert.showAndWait();
        }return false;
    }

    private boolean validateFat(){
        Pattern p = Pattern.compile("[0-9]*[0-9._]*[0-9]");
        Matcher m = p.matcher(fat.getText());
        if(m.find() && m.group().equals(fat.getText())){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Blogai įvestas riebalų kiekis");
            alert.showAndWait();
        }return false;
    }

    private boolean validateGroup(){
        if(group.getSelectionModel().getSelectedItem().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Nepasirinkta grupė");
            alert.showAndWait();
            return false;
        }else{
        }return true;
    }

    private boolean validateDish(){
        if(dish.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Įveskite patiekalo pavadinimą");
            alert.showAndWait();
            return false;
        }else {
        }return true;
    }
}
