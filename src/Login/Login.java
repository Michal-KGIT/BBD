package Login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;

public class Login extends Application {

    public void start(Stage stage)throws Exception {
        try {
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public static void main(String[] args){
        launch(args);
    }
}
