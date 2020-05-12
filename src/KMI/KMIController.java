package KMI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KMIController implements Initializable {

    @FXML
    private TextField height;
    @FXML
    private TextField weight;
    @FXML
    private Label index;
    @FXML
    private Label classification;

    public void initialize (URL url, ResourceBundle rb){
    }

    @FXML
    public void count(ActionEvent event) {
        if (validateHeight() && validateWeight()) {
            double one = Double.parseDouble(weight.getText());
            double two = Double.parseDouble(height.getText());
            double answer = Double.valueOf(one / ((two * two) / 10000));
            index.setText(new DecimalFormat("##.##").format(answer));
            if (answer < 18.5) {
                classification.setText("Svoris per mažas");
            } else if (answer >= 18.5 && answer <= 24.9) {
                classification.setText("Svoris yra normalus");
            } else if (answer >= 25 && answer <= 29.9) {
                classification.setText("Antsvoris");
            } else if (answer >= 30 && answer <= 34.9) {
                classification.setText("Pirmo laipsnio nutukimas");
            } else if (answer >= 35 && answer <= 39.9) {
                classification.setText("Antro laipsnio nutukimas");
            } else if (answer > 40)
                classification.setText("Trečio laipsnio nutukimas");
        }
    }

    public boolean validateHeight(){
        Pattern patternHeight = Pattern.compile(("[0-9]+[0-9._]*[0-9]"));
        Matcher h = patternHeight.matcher(height.getText());
        if(h.find() && h.group().equals(height.getText())){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Ūgis įvestas neteisingai");
            alert.showAndWait();
        }return false;
    }

    public boolean validateWeight(){
        Pattern patternWeight = Pattern.compile(("[0-9]+[0-9._]*[0-9]+"));
        Matcher w = patternWeight.matcher(weight.getText());
        if(w.find() && w.group().equals(weight.getText())){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Svoris įvestas neteisingai");
            alert.showAndWait();
        }return false;
    }
}
