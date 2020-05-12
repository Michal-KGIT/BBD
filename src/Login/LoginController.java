package Login;

import Client.ClientController;
import Client.Display;
import Coach.CoachController;
import DataBase.DBConnection;
import Registration.RegistrationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public TextField email;
    @FXML
    public PasswordField pass;
    @FXML
    private Label error;
    @FXML
    private Button loginButton;

    private String sqlClient = "SELECT * FROM mydb.client WHERE Email = ? AND Password = ?";
    private String sqlCoach = "SELECT * FROM mydb.coach WHERE Email = ? AND Password = ?";

    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void login(ActionEvent event) {
        if (emptyFieldValidation()) {
            loginClient();
            loginCoach();
        }
    }

    private void loginClient() {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlClient);
            ps.setString(1, email.getText());
            ps.setString(2, pass.getText());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String username = rs.getString("Name");
                Display.name = username;
                String last = rs.getString("Last_name");
                Display.lastName = last;
                String birthDate = rs.getString("Date_of_birth");
                Display.date = birthDate;
                String email = rs.getString("Email");
                Display.email = email;
                String height = rs.getString("Height");
                Display.height = height;
                int idClient = rs.getInt("Client_ID");
                Display.clientID = idClient;
                Stage clientStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = (Pane) loader.load(getClass().getResource("/Client/Client.fxml").openStream());
                ClientController clientController = (ClientController) loader.getController();
                Scene scene = new Scene(root);
                clientStage.setScene(scene);
//                clientStage.setTitle("Klientas");
                clientStage.setResizable(false);
                clientStage.show();
                clientStage = (Stage) loginButton.getScene().getWindow();
                clientStage.close();
            }
            conn.close();
            error.setText("Neteisingas el. pašto adresas ir/arba slaptažodis");
        } catch (Exception e) {

        }
    }

    private void loginCoach() {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps1 = conn.prepareStatement(sqlCoach);
            ps1.setString(1, email.getText());
            ps1.setString(2, pass.getText());
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                int idCoach = rs1.getInt("Coach_ID");
                ClientController.coachID = idCoach;
                Stage coachStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = (Pane) loader.load(getClass().getResource("/Coach/Coach.fxml").openStream());
                CoachController coachController = (CoachController) loader.getController();
                Scene scene = new Scene(root);
                coachStage.setScene(scene);
//                coachStage.setTitle("Treneris");
                coachStage.setResizable(false);
                coachStage.show();
                coachStage = (Stage) loginButton.getScene().getWindow();
                coachStage.close();
            }
            conn.close();
            error.setText("Neteisingas el. pašto adresas ir/arba slaptažodis");
        } catch (Exception e) {
        }
    }

    @FXML
    private void registration() {
        try {
            Stage registrationStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Registration/Registration.fxml").openStream());

            RegistrationController registrationController = (RegistrationController) loader.getController();
            Scene scene = new Scene(root);
            registrationStage.setScene(scene);
//            registrationStage.setTitle("Registracija");
            registrationStage.setResizable(false);
            registrationStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean emptyFieldValidation() {
        if (email.getText().isEmpty() || pass.getText().isEmpty()) {
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
