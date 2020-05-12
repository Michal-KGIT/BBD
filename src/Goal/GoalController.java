package Goal;

import Client.Display;
import DataBase.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sun.security.pkcs11.Secmod;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoalController implements Initializable {

    @FXML
    private TextField kgGoal;
    @FXML
    private RadioButton gain;
    @FXML
    private RadioButton lose;
    @FXML
    private RadioButton maintain;
    @FXML
    private Label clientID;
    @FXML
    private Label confirmation;

    private String radiobuttonLabel;
    ToggleGroup clientGoal = new ToggleGroup();

    private String sqlSaveGoal = "INSERT INTO mydb.goal(Goal_ID, Client_ID, Goal, GoalKg) VALUES (?,?,?,?)";
    private String sqlUpdateGoal = "UPDATE mydb.goal SET Goal = ?, GoalKg = ? WHERE Client_ID = ?";

    public void initialize(URL url, ResourceBundle rb){
        clientID.setText(String.valueOf(Display.clientID));
    }

    @FXML
    private void loseRadioButton(ActionEvent event){
        lose.setToggleGroup(clientGoal);
        radiobuttonLabel = "numesti";
        confirmation.setText("");
    }

    @FXML
    private void gainRadioButton(ActionEvent event){
        gain.setToggleGroup(clientGoal);
        radiobuttonLabel = "priaugti";
        confirmation.setText("");
    }

    @FXML
    private void maintainRadioButton(ActionEvent event){
        maintain.setToggleGroup(clientGoal);
        radiobuttonLabel = "išlaikyti";
        confirmation.setText("");
    }

    @FXML
    private void saveGoal(ActionEvent event) {
        if (validateEmptyFields()) {
            if (validateRadioButton() && validateKG()) {
                if (validateExistingGoal()) {
                    try {
                        Connection conn = DBConnection.getConnection();
                        PreparedStatement ps = conn.prepareStatement("SELECT max(Goal_ID)+1 FROM mydb.goal");
                        ResultSet rs = ps.executeQuery();
                        String sizeID = "";
                        while (rs.next()) {
                            sizeID = rs.getString(1);
                        }

                        PreparedStatement ps2 = conn.prepareStatement(sqlSaveGoal);
                        PreparedStatement ps1 = conn.prepareStatement("SELECT Client_ID FROM mydb.client");
                        ResultSet rs1 = ps1.executeQuery();
                        String clientsID = "";
                        while (rs1.next()) {
                            clientsID.equals(clientID);
                        }
                        ps2.setInt(1, Integer.valueOf(sizeID));
                        ps2.setInt(2, Integer.valueOf(clientID.getText()));
                        ps2.setString(3, radiobuttonLabel);
                        ps2.setString(4, this.kgGoal.getText());
                        confirmation.setText("Tikslas sėkmingai išsaugotas");
                        ps2.execute();
                        conn.close();
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Klaida");
                        alert.setHeaderText(null);
                        alert.setContentText("Nepasirinktas tiksla");
                        alert.showAndWait();
                    }
                }
            }
        }
    }

    @FXML
    private void updateGoal(ActionEvent event){
        if(validateKG()){
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlUpdateGoal);
            ps.setString(1, radiobuttonLabel);
            ps.setString(2, this.kgGoal.getText());
            ps.setString(3, this.clientID.getText());
            ps.executeUpdate();
            confirmation.setText("Tikslas sėkmingai atnaujintas");
            conn.close();
        }catch (Exception e){
        }
        }
    }

    private boolean validateExistingGoal(){
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM mydb.goal WHERE Client_ID = ?");
            ps.setString(1, this.clientID.getText());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Klaida");
                alert.setHeaderText(null);
                alert.setContentText("Naujo tikslo sukurti negalima. Atnaujinkite senąjį tikslą");
                alert.showAndWait();
                return false;
            }
            conn.close();
        }catch (Exception e){
        }return true;
    }

    private boolean validateKG(){
        Pattern p = Pattern.compile("[0-9]+[0-9._]*[0-9]+");
        Matcher m = p.matcher(kgGoal.getText());
        if(m.find() && m.group().equals(kgGoal.getText())){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Blogai įvestas svoris");
            alert.showAndWait();
        }return false;
    }

    private boolean validateRadioButton(){
        if(!(maintain.isSelected() || lose.isSelected() || gain.isSelected())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Pasirinkite tikslą");
            alert.showAndWait();
            return false;
        }return true;
    }

    private boolean validateEmptyFields() {
        if (kgGoal.getText().isEmpty() && (!(maintain.isSelected() || lose.isSelected() || gain.isSelected()))) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Neįvesti visi privalomi duomenys");
            alert.showAndWait();
            return false;
        }
        return true;
    }

}
