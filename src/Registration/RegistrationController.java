package Registration;

import DataBase.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationController extends javax.swing.JFrame implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private DatePicker date;
    @FXML
    private TextField email;
    @FXML
    private TextField weight;
    @FXML
    private TextField height;
    @FXML
    private PasswordField pass;
    @FXML
    private PasswordField pass1;
    @FXML
    private Button registrationButton;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String sqlAdd = "INSERT INTO mydb.client(Client_ID, Name, Last_name, Date_of_birth, Weight, Height, Email, Password) VALUES (?,?,?,?,?,?,?,?)";

    public void initialize (URL url, ResourceBundle rb) {
        date.setConverter(new StringConverter <LocalDate>(){
            public String toString(LocalDate t){
                if (t != null){
                    return formatter.format(t);
                }return null;
            }
            public LocalDate fromString(String string){
                if (string != null && !string.trim().isEmpty()){
                    return LocalDate.parse(string, formatter);
                }return null;
            }
        });
    }

    @FXML
    private void registration(ActionEvent event){
        if (validateField() && validateEmail() && validateName() && validateSurname() &&validatePass() && validateHeight() && validateWeight()) {
            if (validateValue())
                if (confirmationDialog()){
                    try {
                        Connection conn = DBConnection.getConnection();
                        PreparedStatement ps = conn.prepareStatement("SELECT max(Client_ID)+1 FROM mydb.client");
                        ResultSet rs = ps.executeQuery();
                        String user_id = "";
                        while (rs.next()) {
                            user_id = rs.getString(1);
                        }

                        PreparedStatement ps1 = conn.prepareStatement(sqlAdd);
                        ps1.setString(1, user_id);
                        ps1.setString(2, this.name.getText());
                        ps1.setString(3, this.surname.getText());
                        ps1.setString(4, this.date.getEditor().getText());
                        ps1.setString(5, this.weight.getText());
                        ps1.setString(6, this.height.getText());
                        ps1.setString(7, this.email.getText());
                        ps1.setString(8, this.pass.getText());
                        ps1.execute();
                        conn.close();
                        Stage registrationStage = new Stage();
                        registrationStage = (Stage)registrationButton.getScene().getWindow();
                        registrationStage.close();
                    } catch (Exception e) {
                    }
                }
        }
    }

    private boolean validateName(){
        Pattern p = Pattern.compile("[A-Z][a-z]+");
        Matcher m = p.matcher(name.getText());
        if(m.find() && m.group().equals(name.getText())){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Blogai įvesti duomenys");
            alert.showAndWait();
        }return false;
    }

    private boolean validateSurname(){
        Pattern p = Pattern.compile("[A-Z][a-z]+");
        Matcher m = p.matcher(surname.getText());
        if(m.find() && m.group().equals(surname.getText())){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Blogai įvesti duomenys");
            alert.showAndWait();
        }return false;
    }

    private boolean validateEmail(){
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
    Matcher m = p.matcher(email.getText());
        if(m.find() && m.group().equals(email.getText())){
        return true;
    }else{
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Klaida");
        alert.setHeaderText(null);
        alert.setContentText("Negaliojantis el. pašto adresas");
        alert.showAndWait();
    }return false;
}

    private boolean validateField(){
        if(name.getText().isEmpty() || surname.getText().isEmpty() || date.getEditor().getText().isEmpty() || email.getText().isEmpty() || pass.getText().isEmpty() || pass1.getText().isEmpty() || weight.getText().isEmpty() || height.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Neįvesti visi privalomi duomenys");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validatePass(){
        if(pass.getText() == null ? pass1.getText() != null : !pass.getText().equals(pass1.getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Slaptažodžiai nesutampa");
            alert.showAndWait();
            return false;
        }return true;
    }

    private boolean validateValue(){
        try{
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT Email FROM mydb.client WHERE Email = '"+email.getText()+"'");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Klaida");
                alert.setHeaderText(null);
                alert.setContentText("Toks el. pašto adresas jau yra išsaugotas. Įveskite naują el. paštą");
                alert.showAndWait();
                return false;
            }
        }catch (Exception e){
        }return true;
    }

    private boolean validateHeight(){
        Pattern p = Pattern.compile("[0-9]+[0-9._]*[0-9]");
        Matcher m = p.matcher(height.getText());
        if(m.find() && m.group().equals(height.getText())){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Blogai įvesti duomenys");
            alert.showAndWait();
        }return false;
    }

    private boolean validateWeight(){
        Pattern p = Pattern.compile("[0-9]+[0-9._]*[0-9]+");
        Matcher m = p.matcher(weight.getText());
        if(m.find() && m.group().equals(weight.getText())){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Blogai įvesti duomenys");
            alert.showAndWait();
        }return false;
    }

    private boolean confirmationDialog(){
        JOptionPane.showMessageDialog(null, "Regsitracija sėkminga", "Registracija", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

}
