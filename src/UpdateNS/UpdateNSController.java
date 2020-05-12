package UpdateNS;

import Client.Display;
import DataBase.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateNSController extends Display implements Initializable {

    @FXML
    private TextField nameChange;
    @FXML
    private TextField lastNameChange;
    @FXML
    private TextField dateChange;
    @FXML
    private TextField emailChange;
    @FXML
    private TextField height;
    @FXML
    private PasswordField oldPass;
    @FXML
    private PasswordField passChange;
    @FXML
    private PasswordField passChange1;
    @FXML
    private Label confirmation;
    @FXML
    private Label oldpassL;
    @FXML
    private Label ID;

    private String sqlUpdate = "UPDATE mydb.client SET Name = ?, Last_name = ?, Height = ?, Email = ?,  Password = ? WHERE Client_ID= ?";

    public void initialize(URL url, ResourceBundle rb) {
        nameChange.setText(String.valueOf(Display.name));
        lastNameChange.setText(String.valueOf(Display.lastName));
        dateChange.setText(String.valueOf(Display.date));
        emailChange.setText(String.valueOf(Display.email));
        height.setText(String.valueOf(Display.height));
        ID.setText(String.valueOf(Display.clientID));
        dateChange.setEditable(false);
        oldPass();
    }

    @FXML
    private void update(ActionEvent event) {
        if (validatePassword() && validateEmptyField() && validateName() && validateLastName() && validateEmail() && validateHeight() && validateOldPass()) {
            if (confirmationDialog()) {
                try {
                    Connection conn = DBConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sqlUpdate);
                    ps.setString(1, this.nameChange.getText());
                    ps.setString(2, this.lastNameChange.getText());
                    ps.setString(3, this.height.getText());
                    ps.setString(4, this.emailChange.getText());
                    ps.setString(5, this.passChange.getText());
                    ps.setString(6, this.ID.getText());
                    ps.executeUpdate();
                    conn.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private static String hashPass(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean validatePassword() {
        if (passChange.getText() == null ? passChange1.getText() != null : !passChange.getText().equals(passChange1.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Slaptažodžiai nesutampa");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validateEmptyField() {
        if (nameChange.getText().isEmpty() || lastNameChange.getText().isEmpty() || emailChange.getText().isEmpty() || passChange.getText().isEmpty() || passChange1.getText().isEmpty() || height.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Neivesti visi duomenys");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validateName() {
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(nameChange.getText());
        if (m.find() && m.group().equals(nameChange.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Blogai įvestas vardas");
            alert.showAndWait();
        }
        return false;
    }

    private boolean validateLastName() {
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(lastNameChange.getText());
        if (m.find() && m.group().equals(lastNameChange.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Blogai įvesta pavardė");
            alert.showAndWait();
        }
        return false;
    }

    private boolean validateEmail(){
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(emailChange.getText());
        if(m.find() && m.group().equals(emailChange.getText())){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Negaliojantis el. pašto adresas");
            alert.showAndWait();
        }return false;
    }

    private boolean validateHeight(){
        Pattern patternHeight = Pattern.compile(("[0-9]+[0-9._]*[0-9]"));
        Matcher h = patternHeight.matcher(height.getText());
        if(h.find() && h.group().equals(height.getText())){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Blogai įvestas ūgis");
            alert.showAndWait();
        }return false;
    }

    private void oldPass(){
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM mydb.client WHERE Client_ID = ?");
            ps.setString(1, this.ID.getText());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                oldpassL.setText(rs.getString("Password"));
            }
        }catch (Exception e){
        }
    }
    //if(BCrypt.checkpw(String.valueOf(pass), rs.getString("Password")));

    private boolean validateOldPass(){
        if(oldpassL.getText().equals(oldPass.getText())){
            return true;
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Blogai įvestas senas slaptažodis");
            alert.showAndWait();
            return false;
        }
    }

    private boolean confirmationDialog(){
        if (validateHeight() && validateEmail() && validateLastName() && validateName() && validateEmptyField() && validatePassword()){
            confirmation.setText("Duomenys atnaujinti sėkmingai");
            return true;
        }return false;
    }
}



