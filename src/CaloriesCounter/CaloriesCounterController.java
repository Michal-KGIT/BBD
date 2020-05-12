package CaloriesCounter;

import KMI.KMIController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CaloriesCounterController extends KMIController implements Initializable {

    @FXML
    private Label daily;
    @FXML
    private TextField height;
    @FXML
    private TextField weight;
    @FXML
    private TextField years;
    @FXML
    private ComboBox goal;
    @FXML
    private RadioButton male;
    @FXML
    private RadioButton female;
    @FXML
    private ComboBox activity;

    ToggleGroup gender = new ToggleGroup();

    ObservableList<String> list = FXCollections.observableArrayList("Priaugti svorio", "Numesti svorio", "Išlaikyti svorį");
    ObservableList<String> listActivity = FXCollections.observableArrayList("Be sporto", "1-2 kart./sav.", "3-5 kart./sav.", "6-7 kart./sav.", "2 kart./diena");


    public void initialize(URL url, ResourceBundle rb) {
        goal.setItems(list);
        activity.setItems(listActivity);
    }

    @FXML
    private void maleRadioButton() {
        male.setToggleGroup(gender);
    }

    @FXML
    private void femaleRadioButton() {
        female.setToggleGroup(gender);
    }

    @FXML
    private void countCalories(ActionEvent event) {
        if (validateField()) {
            if (validateHeight() && validateWeight() && validateAge() && validateActivity() && validateGoal()) {
                double heightCM = Double.parseDouble(height.getText());
                double weightKG = Double.parseDouble(weight.getText());
                double age = Double.parseDouble(years.getText());
                double caloriesM = (10 * weightKG) + (6.25 * heightCM) - (5 * age) + 5;
                double caloriesF = (10 * weightKG) + (6.25 * heightCM) - (5 * age) - 161;

                if (goal.getSelectionModel().getSelectedItem().equals("Išlaikyti svorį")) {
                    if (activity.getSelectionModel().getSelectedItem().equals("Be sporto")) {
                        if (male.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesM * 1.2) + " kCal");
                        } else if (female.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesF * 1.2) + " kCal");
                        }
                    } else if (activity.getSelectionModel().getSelectedItem().equals("1-2 kart./sav.")) {
                        if (male.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesM * 1.375) + " kCal");
                        } else if (female.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesF * 1.375) + " kCal");
                        }
                    } else if (activity.getSelectionModel().getSelectedItem().equals("3-5 kart./sav.")) {
                        if (male.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesM * 1.55) + " kCal");
                        } else if (female.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesF * 1.55) + " kCal");
                        }
                    } else if (activity.getSelectionModel().getSelectedItem().equals("6-7 kart./sav.")) {
                        if (male.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesM * 1.725) + " kCal");
                        } else if (female.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesF * 1.725) + " kCal");
                        }
                    } else if (activity.getSelectionModel().getSelectedItem().equals("2 kart./diena")) {
                        if (male.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesM * 1.9) + " kCal");
                        } else if (female.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesF * 1.9) + " kCal");
                        }
                    }
                } else if (goal.getSelectionModel().getSelectedItem().equals("Numesti svorio")) {
                    if (activity.getSelectionModel().getSelectedItem().equals("Be sporto")) {
                        if (male.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesM / 1.07) + " kCal");
                        } else if (female.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesF / 1.15) + " kCal");
                        }
                    } else if (activity.getSelectionModel().getSelectedItem().equals("1-2 kart./sav.")) {
                        if (male.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesM / 0.90) + " kCal");
                        } else if (female.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesF / 0.957) + " kCal");
                        }
                    } else if (activity.getSelectionModel().getSelectedItem().equals("3-5 kart./sav.")) {
                        if (male.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesM / 0.78) + " kCal");
                        } else if (female.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesF / 0.82) + " kCal");
                        }
                    } else if (activity.getSelectionModel().getSelectedItem().equals("6-7 kart./sav.")) {
                        if (male.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesM / 0.68) + " kCal");
                        } else if (female.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesF / 0.718) + " kCal");
                        }
                    } else if (activity.getSelectionModel().getSelectedItem().equals("2 kart./diena")) {
                        if (male.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesM / 0.605) + " kCal");
                        } else if (female.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesF / 0.638) + " kCal");
                        }
                    }
                } else if (goal.getSelectionModel().getSelectedItem().equals("Priaugti svorio")) {
                    if (activity.getSelectionModel().getSelectedItem().equals("Be sporto")) {
                        if (male.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesM * 1.528) + " kCal");
                        } else if (female.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesF * 1.525) + " kCal");
                        }
                    } else if (activity.getSelectionModel().getSelectedItem().equals("1-2 kart./sav.")) {
                        if (male.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesM * 1.707) + " kCal");
                        } else if (female.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesF * 1.7) + " kCal");
                        }
                    } else if (activity.getSelectionModel().getSelectedItem().equals("3-5 kart./sav.")) {
                        if (male.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesM * 1.887) + " kCal");
                        } else if (female.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesF * 1.875) + " kCal");
                        }
                    } else if (activity.getSelectionModel().getSelectedItem().equals("6-7 kart./sav.")) {
                        if (male.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesM * 2.067) + " kCal");
                        } else if (female.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesF * 2.05) + " kCal");
                        }
                    } else if (activity.getSelectionModel().getSelectedItem().equals("2 kart./diena")) {
                        if (male.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesM * 2.247) + " kCal");
                        } else if (female.isSelected()) {
                            daily.setText(new DecimalFormat("#####").format(caloriesF * 2.224) + " kCal");
                        }
                    }
                }
            }
        }
    }

    private boolean validateAge(){
        Pattern age = Pattern.compile(("[0-9]+"));
        Matcher m = age.matcher(years.getText());
        if(m.find() && m.group().equals(years.getText())){
            return true;
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Amžius įvestas neteisingai");
            alert.showAndWait();
        }return false;
    }

    private boolean validateActivity(){
        if(activity.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Nepasirinktas aktyvumas");
            alert.showAndWait();
            return false;
        }else {
        }return true;
    }

    private boolean validateGoal(){
        if(goal.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Nepasirinktas tikslas");
            alert.showAndWait();
            return false;
        }else{
        }return true;
    }

    private boolean validateField(){
        if(height.getText().isEmpty() || weight.getText().isEmpty() || years.getText().isEmpty() || activity.getSelectionModel().getSelectedItem() == null ||
                goal.getSelectionModel().getSelectedItem() == null || (!(male.isSelected() || female.isSelected()))){
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