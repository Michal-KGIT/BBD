package Size;

import Client.Display;
import DataBase.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class SizeController implements Initializable {

    @FXML
    private TextField kg;
    @FXML
    private TextField KMI;
    @FXML
    private TextField shoulders;
    @FXML
    private TextField chest;
    @FXML
    private TextField rBiceps;
    @FXML
    private TextField lBiceps;
    @FXML
    private TextField waistS;
    @FXML
    private TextField waistB;
    @FXML
    private TextField glutes;
    @FXML
    private TextField rThigh;
    @FXML
    private TextField lThigh;
    @FXML
    private DatePicker addDate;
    @FXML
    private Label clientID;
    @FXML
    private Label idLabel;
    @FXML
    private Label confirmation;
    @FXML
    private ComboBox combo;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    final ObservableList sizeFill = FXCollections.observableArrayList();

    private String sqlAddSize = "INSERT INTO mydb.size (Size_ID, Client_ID, Saving_date, BMI, Weight, Shoulders, Chest, Right_biceps, Left_biceps, Waist_big, Waist_small, Glutes, Right_thigh, Left_thigh) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private String sqlLoadSize = "SELECT * FROM mydb.size WHERE Saving_date = ?";
    private String sqlUpdateSize = "UPDATE mydb.size SET BMI=?, Weight=?, Glutes=?, Right_thigh=?, Left_thigh=?, Chest=?, Right_biceps=?, Left_biceps=?, Shoulders=?, Waist_small=?, Waist_big=? WHERE Saving_date = ?";
    private String sqlDeleteSize = "DELETE FROM mydb.size WHERE Size_ID = ?";
    private String sqlValidateSizeDate = "SELECT * FROM mydb.size WHERE Saving_date = ? AND Client_ID = ?" ;


    public void initialize(URL url, ResourceBundle rb){
        clientID.setText(String.valueOf(Display.clientID));
        addDate.setConverter(new StringConverter<LocalDate>() {
            public String toString(LocalDate t) {
                if (t != null) {
                    return formatter.format(t);
                }
                return null;
            }

            public LocalDate fromString(String string) {
                if (string != null && !string.trim().isEmpty()) {
                    return LocalDate.parse(string, formatter);
                }
                return null;
            }
        });
    }

    @FXML
    private void comboFill(MouseEvent event) {
        sizeFill.clear();
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT Saving_date FROM mydb.size WHERE Client_ID = ?");
            ps.setString(1, this.clientID.getText());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sizeFill.add(rs.getString("Saving_date"));
            }
            conn.close();
            rs.close();
        } catch (Exception e) {
        }
        combo.setItems(sizeFill);
    }

    @FXML
    private void AddNewSize(ActionEvent event) {
        if(validateEmptyFields()){
        if(validateSizeDate()) {
            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT max(Size_ID)+1 FROM mydb.size");
                ResultSet rs = ps.executeQuery();
                String sizeID = "";
                while (rs.next()) {
                    sizeID = rs.getString(1);
                }

                PreparedStatement ps1 = conn.prepareStatement(sqlAddSize);
                ps1.setString(1, sizeID.toString());
                ps1.setString(2, this.clientID.getText());
                ps1.setString(3, this.addDate.getEditor().getText());
                ps1.setString(4, this.KMI.getText());
                ps1.setString(5, this.kg.getText());
                ps1.setString(6, this.shoulders.getText());
                ps1.setString(7, this.chest.getText());
                ps1.setString(8, this.rBiceps.getText());
                ps1.setString(9, this.lBiceps.getText());
                ps1.setString(10, this.waistB.getText());
                ps1.setString(11, this.waistS.getText());
                ps1.setString(12, this.glutes.getText());
                ps1.setString(13, this.rThigh.getText());
                ps1.setString(14, this.lThigh.getText());
                ps1.execute();
                confirmation.setText("Kūno matmenys sėkmingai išsaugoti");
                conn.close();
            } catch (Exception e) {
            }
        }
    }
    }

    @FXML
    private void updateSize (ActionEvent event) {
        if (validateEmptyFields()) {
            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sqlUpdateSize);
                ps.setString(1, this.KMI.getText());
                ps.setString(2, this.kg.getText());
                ps.setString(3, this.glutes.getText());
                ps.setString(4, this.rThigh.getText());
                ps.setString(5, this.lThigh.getText());
                ps.setString(6, this.chest.getText());
                ps.setString(7, this.rBiceps.getText());
                ps.setString(8, this.lBiceps.getText());
                ps.setString(9, this.shoulders.getText());
                ps.setString(10, this.waistS.getText());
                ps.setString(11, this.waistB.getText());
                ps.setString(12, this.combo.getSelectionModel().getSelectedItem().toString());
                ps.executeUpdate();
                confirmation.setText("Atnaujinimas sėkmingas");
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    @FXML
    private void fillTextFieldSizeUpdate(ActionEvent event){
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlLoadSize);
            ps.setString(1, this.clientID.getText());
            ps.setString(1, (combo.getSelectionModel().getSelectedItem().toString()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                kg.setText((rs.getString("Weight")));
                KMI.setText(rs.getString("BMI"));
                shoulders.setText(rs.getString("Shoulders"));
                chest.setText(rs.getString("Chest"));
                rBiceps.setText(rs.getString("Right_biceps"));
                lBiceps.setText(rs.getString("Left_biceps"));
                waistS.setText(rs.getString("Waist_big"));
                waistB.setText(rs.getString("Waist_big"));
                glutes.setText(rs.getString("Glutes"));
                rThigh.setText(rs.getString("Left_thigh"));
                lThigh.setText(rs.getString("Right_thigh"));
                addDate.getEditor().setText(rs.getString("Saving_date"));
                idLabel.setText(rs.getString("Size_ID"));
            }
            conn.close();
        }catch (Exception e){
        }
    }

    @FXML
    private void deleteSize(ActionEvent event) {
        int dp = JOptionPane.showConfirmDialog(null, "Ar tikrai norite ištrinti?", "Delete", JOptionPane.YES_NO_OPTION);
        if (dp == 0) {
            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sqlDeleteSize);
                ps.setString(1, this.idLabel.getText());
                ps.executeUpdate();
                kg.setText("");
                KMI.setText("");
                shoulders.setText("");
                chest.setText("");
                rBiceps.setText("");
                lBiceps.setText("");
                waistS.setText("");
                waistB.setText("");
                glutes.setText("");
                rThigh.setText("");
                lThigh.setText("");
                addDate.getEditor().setText("");
            } catch (Exception e) {
            }
        }
    }

    private boolean validateSizeDate(){
        try{
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlValidateSizeDate);
            ps.setString(1, this.addDate.getEditor().getText());
            ps.setString(2, this.clientID.getText());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Klaida");
                alert.setHeaderText(null);
                alert.setContentText("Tokia data jau yra išsaugota");
                alert.showAndWait();
                return false;
            }
        }catch (Exception e){
        }return true;
    }

    private boolean validateEmptyFields(){
        if (KMI.getText().isEmpty() || kg.getText().isEmpty() || shoulders.getText().isEmpty() || chest.getText().isEmpty() || rBiceps.getText().isEmpty() || lBiceps.getText().isEmpty() ||
                waistB.getText().isEmpty() || waistS.getText().isEmpty() || glutes.getText().isEmpty() || rThigh.getText().isEmpty() || lThigh.getText().isEmpty() ||
                addDate.getEditor().getText().isEmpty()) {
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

