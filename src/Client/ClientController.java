package Client;

import CaloriesCounter.CaloriesCounterController;
import DataBase.DBConnection;
import Goal.GoalController;
import KMI.KMIController;
import Login.LoginController;
import Size.SizeController;
import UpdateNS.UpdateNSController;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientController extends Display implements Initializable {

    @FXML
    private Button sizeButton;
    @FXML
    private Button performanceButton;
    @FXML
    private Button nutritionButton;
    @FXML
    private Button homeButton;
    @FXML
    private Button logOutButton;
    @FXML
    private GridPane gpSize;
    @FXML
    private GridPane gpPerformance;
    @FXML
    private GridPane gpNutrition;
    @FXML
    private GridPane gpEmpty;
    @FXML
    private Label names;
    @FXML
    private Label id;
    @FXML
    private Label idPerformance;
    @FXML
    private Label weightClientLabel;
    @FXML
    private Label heightClientLabel;
    @FXML
    private Label weightSize;
    @FXML
    private Label kgGoal;
    @FXML
    private Label goal;
    @FXML
    private Label reachGoal;
    @FXML
    private LineChart<String, Double> lineChart;

    @FXML
    private Label kgLabel;
    @FXML
    private Label kmiLabel;
    @FXML
    private Label shoulderLabel;
    @FXML
    private Label chestLabel;
    @FXML
    private Label rBicepsLabel;
    @FXML
    private Label lBicepsLabel;
    @FXML
    private Label waistBLabel;
    @FXML
    private Label waistSLabel;
    @FXML
    private Label glutesLabel;
    @FXML
    private Label rThighLabel;
    @FXML
    private Label lThighLabel;
    @FXML
    private ComboBox comboboxData;
    @FXML
    private Label idLabel;

    @FXML
    private TextField exerciseName;
    @FXML
    private TextField repCounts;
    @FXML
    private TextField setCounts;
    @FXML
    private TextField weightKg;
    @FXML
    private DatePicker dateAdd;
    @FXML
    private TextField searchPerformance;

    @FXML
    private TableView<PerformanceData> performanceTableView;
    @FXML
    private TableColumn<PerformanceData, String> exerciseNamec;
    @FXML
    private TableColumn<PerformanceData, String> weightc;
    @FXML
    private TableColumn<PerformanceData, String> repc;
    @FXML
    private TableColumn<PerformanceData, String> idcperformance;
    @FXML
    private TableColumn<PerformanceData, String> setsc;
    @FXML
    private TableColumn<PerformanceData, String> datec;

    @FXML
    private ComboBox type;
    @FXML
    private ComboBox mealPlanDate;
    @FXML
    private TableView<MealPlan> mealPlan;
    @FXML
    private TableColumn<MealPlan, String> groupc;
    @FXML
    private TableColumn<MealPlan, String> dishc;
    @FXML
    private TableColumn<MealPlan, String> gramsc;
    @FXML
    private TableColumn<MealPlan, String> createDatec;

    @FXML
    private ImageView bodyImage;

    final ObservableList comboFill = FXCollections.observableArrayList();
    final ObservableList mealDateComboFill = FXCollections.observableArrayList();
    private ObservableList<PerformanceData> pd = FXCollections.observableArrayList();
    private ObservableList<MealPlan> mp = FXCollections.observableArrayList();
    public static int coachID;
    //private ObservableList<ClientData> cd = FXCollections.observableArrayList(); // cia buvo static
    ObservableList<String> typeOfMeal = FXCollections.observableArrayList("Pirmas valgymas", "Antras valgymas", "Trečias valgymas", "Ketvirtas valgymas", "Penktas valgymas", "Užkandis");

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String sqlAddPerformance = "INSERT INTO mydb.sports_performance(Performance_ID, Client_ID, Exercise, Repetitions, Sets, Weight, Date) VALUES (?,?,?,?,?,?,?)";
    private String sqlDeletePerformance = "DELETE FROM mydb.sports_performance WHERE Performance_ID = ?";
    private String sqlLoadSize = "SELECT * FROM mydb.size WHERE Saving_date = ? ORDER BY Saving_date";
    private String sqlLoadPerformance = "SELECT * FROM mydb.sports_performance WHERE Client_ID = ? ORDER BY Date";
    private String sqlDisplayClientSizeKG = "SELECT Weight FROM mydb.size WHERE Client_ID = ?";
    private String sqlDisplayClientKG = "SELECT Weight FROM mydb.client WHERE Client_ID = ?";
    private String sqlDisplayClientCM = "SELECT Height FROM mydb.client WHERE Client_ID = ?";
    private String sqlDisplayGoal = "SELECT GoalKg FROM mydb.goal WHERE Client_ID = ?";
    private String sqlDisplayReachMaintain = "SELECT Goal FROM mydb.goal WHERE Client_ID = ? AND Goal = 'išlaikyti'";
    private String sqlDisplayReachLose = "SELECT Goal FROM mydb.goal WHERE Client_ID = ? AND Goal = 'numesti'";
    private String sqlDisplayReachGain = "SELECT Goal FROM mydb.goal WHERE Client_ID = ? AND Goal = 'priaugti'";
    private String sqlLoadLineChart = "SELECT * FROM mydb.size WHERE Client_ID = ? ORDER BY Saving_date";
    private String sqlLoadMealGroup = "SELECT * FROM mydb.meal_plan WHERE Type_of_meal = ? AND Client_ID = ? ORDER BY Date ASC";
    private String sqlLoadFullMealPlan = "SELECT * FROM mydb.meal_plan WHERE Client_ID = ? ORDER BY Date ASC, FIELD(Type_of_meal, 'Pirmas valgymas', 'Antras valgymas', 'Trečias valgymas', 'Ketvirtas valgymas', 'Penktas valgymas', 'Užkandis')";
    private String sqlSelectMealDate = "SELECT DISTINCT Date FROM mydb.meal_plan WHERE Client_ID = ?";
    private String sqlLoadMealPlanDate = "SELECT * FROM mydb.meal_plan WHERE Date = ? AND Client_ID = ? ORDER BY FIELD(Type_of_meal, 'Pirmas valgymas', 'Antras valgymas', 'Trečias valgymas', 'Ketvirtas valgymas', 'Penktas valgymas', 'Užkandis')";

    public void initialize(URL url, ResourceBundle rb) {
        dateAdd.setConverter(new StringConverter<LocalDate>() {
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
        names.setText((Display.name) + "  " + (Display.lastName));
        id.setText(String.valueOf(Display.clientID));
        selectOneRow();
        refreshPerformance();
        displayWeight();
        displayClientWeight();
        displayClientCM();
        displayGoalMaintain();
        displayGoalLose();
        displayGoalGain();
        displayClientGoal();
        displayClientKGGoal();
        lineChartKg();
        type.setItems(typeOfMeal);
        displayPerformance();
        displayBodyImage();
    }

    @FXML
    private void choose(ActionEvent event) {
        if (event.getSource() == homeButton) {
            homeButton.getStyleClass().removeAll();
            gpEmpty.toFront();
        } else if (event.getSource() == performanceButton) {
            gpPerformance.toFront();
        } else if (event.getSource() == sizeButton) {
            gpSize.toFront();
        } else if (event.getSource() == nutritionButton) {
            gpNutrition.toFront();
        }
    }

    @FXML
    public void KMI(ActionEvent event)  {
        try {
            Stage KMIStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/KMI/KMI.fxml").openStream());

            KMIController kmiController = (KMIController) loader.getController();
            Scene scene = new Scene(root);
            KMIStage.setScene(scene);
            KMIStage.setResizable(false);
            KMIStage.show();
        } catch (Exception e) {
        }
    }

    @FXML
    public void caloriesCounter(ActionEvent event) {
        try {
            Stage calories = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/CaloriesCounter/CaloriesCounter.fxml").openStream());

            CaloriesCounterController caloriesCounterController = (CaloriesCounterController) loader.getController();
            Scene scene = new Scene(root);
            calories.setScene(scene);
            calories.setResizable(false);
            calories.show();
        } catch (Exception e) {
        }
    }

    @FXML
    private void addSize(ActionEvent event) {
        try {
            Stage SizeStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Size/Size.fxml").openStream());

            SizeController sizeController = (SizeController) loader.getController();
            Scene scene = new Scene(root);
            SizeStage.setScene(scene);
            SizeStage.setResizable(false);
            SizeStage.show();
        } catch (Exception e) {
        }
    }

    @FXML
    private void fillComboBox(MouseEvent event) {
        comboFill.clear();
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT Client_ID FROM mydb.client");
            ResultSet rs = ps.executeQuery();
            String clientsID = "";
            while (rs.next()) {
                clientsID.equals(id);
            }

            PreparedStatement ps1 = conn.prepareStatement("SELECT Saving_date FROM mydb.size WHERE Client_ID = ?");
            ps1.setInt(1, clientID);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                comboFill.add(rs1.getString("Saving_date"));
            }
            conn.close();
            rs1.close();
        } catch (Exception e) {
        }
        comboboxData.setItems(comboFill);
    }

    @FXML
    private void loadSize(ActionEvent event) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlLoadSize);
            ps.setString(1, this.id.getText());
            ps.setString(1, (comboboxData.getSelectionModel().getSelectedItem().toString()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                kgLabel.setText((rs.getString("Weight") + " kg"));
                kmiLabel.setText(rs.getString("BMI"));
                shoulderLabel.setText(rs.getString("Shoulders") + " cm");
                chestLabel.setText(rs.getString("Chest") + " cm");
                rBicepsLabel.setText(rs.getString("Right_biceps") + " cm");
                lBicepsLabel.setText(rs.getString("Left_biceps") + " cm");
                waistSLabel.setText(rs.getString("Waist_small") + " cm");
                waistBLabel.setText(rs.getString("Waist_big") + " cm");
                glutesLabel.setText(rs.getString("Glutes") + " cm");
                rThighLabel.setText(rs.getString("Left_thigh") + " cm");
                lThighLabel.setText(rs.getString("Right_thigh") + " cm");
                idLabel.setText(rs.getString("Size_ID"));
            }
            conn.close();
        } catch (Exception e) {
        }
    }

    @FXML
    public void update(MouseEvent mouseEvent) {
        try {
            Stage KMIStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/UpdateNS/UpdateNS.fxml").openStream());

            UpdateNSController updateNSController = (UpdateNSController) loader.getController();
            Scene scene = new Scene(root);
            KMIStage.setScene(scene);
            KMIStage.setTitle("Atnaujinti");
            KMIStage.setResizable(false);
            KMIStage.show();
        } catch (Exception e) {
        }
    }

    @FXML
    private void savePerformance() {
        if (validatePerformanceEmptyFields()  && validateSet() ) {
            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT max(Performance_ID)+ 1 FROM mydb.sports_performance");
                ResultSet rs = ps.executeQuery();
                String performance = "";
                while (rs.next()) {
                    performance = rs.getString(1);
                }

                PreparedStatement ps1 = conn.prepareStatement("SELECT Client_ID FROM mydb.client");
                PreparedStatement ps2 = conn.prepareStatement(sqlAddPerformance);
                ResultSet rs1 = ps1.executeQuery();
                String performanceID = "";
                while (rs1.next()) {
                    performanceID.equals(id);
                }
                ps2.setString(1, performance);
                ps2.setInt(2, Integer.valueOf(id.getText()));
                ps2.setString(3, this.exerciseName.getText());
                ps2.setString(4, this.repCounts.getText());
                ps2.setString(5, this.setCounts.getText());
                ps2.setString(6, this.weightKg.getText());
                ps2.setString(7, this.dateAdd.getEditor().getText());
                ps2.execute();
                conn.close();
            } catch (Exception e) {
            }
            refreshPerformance();
            refreshPerformanceField();
        }
    }

    @FXML
    private void displayPerformance() {
        performanceTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                PerformanceData performanceData = performanceTableView.getItems().get(performanceTableView.getSelectionModel().getSelectedIndex());
                exerciseName.setText(performanceData.getExercise());
                setCounts.setText(performanceData.getSet());
                repCounts.setText(performanceData.getRep());
                weightKg.setText(performanceData.getWeight());
                dateAdd.getEditor().setText(performanceData.getDate());
                idPerformance.setText(performanceData.getId());
            }
        });
    }

    @FXML
    private void deletePerformance(ActionEvent event) {
        int dp = JOptionPane.showConfirmDialog(null, "Ar tikrai norite ištrinti?", "Delete", JOptionPane.YES_NO_OPTION);
        if (dp == 0) {
            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sqlDeletePerformance);
                ps.setString(1, this.idPerformance.getText());
                ps.executeUpdate();
            } catch (Exception e) {
            }
            refreshPerformance();
        }
    }

    @FXML
    private void updatePerformance(ActionEvent event) {
        if (validatePerformanceEmptyFields()  && validateSet()) {
            String value1 = exerciseName.getText();
            String value2 = repCounts.getText();
            String value3 = setCounts.getText();
            String value4 = weightKg.getText();
            String value5 = dateAdd.getEditor().getText();
            String sqlUpdatePerformance = "UPDATE mydb.sports_performance SET Exercise= '" + value1 + "', Repetitions= '" + value2 + "', Sets= '" + value3 + "', Weight= '" + value4 + "', Date= '" + value5 + "' WHERE Performance_ID = '" + idPerformance.getText() + "'";
            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sqlUpdatePerformance);
                ps.executeUpdate();
                conn.close();
            } catch (Exception e) {
            }
            refreshPerformance();
            refreshPerformanceField();
        }
    }

    @FXML
    private void searchPerformance(KeyEvent event) {
        FilteredList<PerformanceData> filteredList = new FilteredList<>(pd, e -> true);
        searchPerformance.setOnKeyPressed(e -> {
            searchPerformance.textProperty().addListener(((observable, oldValue, newValue) -> {
                filteredList.setPredicate((Predicate<? super PerformanceData>) performance -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCase = newValue.toLowerCase();
                    if (performance.getExercise().toLowerCase().contains(lowerCase)) {
                        return true;
                    }
                    if (performance.getRep().toLowerCase().contains(lowerCase)) {
                        return true;
                    }
                    if (performance.getSet().toLowerCase().contains(lowerCase)) {
                        return true;
                    }
                    if (performance.getWeight().toLowerCase().contains(lowerCase)) {
                        return true;
                    }
                    if (performance.getDate().toLowerCase().contains(lowerCase)) {
                        return true;
                    }
                    return false;
                });
            }));
            SortedList<PerformanceData> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(performanceTableView.comparatorProperty());
            performanceTableView.setItems(sortedList);
        });
    }

    @FXML
    private void newGoal(MouseEvent event) {
        try {
            Stage Goal = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Goal/Goal.fxml").openStream());

            GoalController goalController = (GoalController) loader.getController();
            Scene scene = new Scene(root);
            Goal.setScene(scene);
            Goal.setResizable(false);
            Goal.show();
        } catch (Exception e) {
        }
    }

    private void selectOneRow() {
        performanceTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                PerformanceData performanceData = performanceTableView.getItems().get(performanceTableView.getSelectionModel().getSelectedIndex());
                idPerformance.setText(performanceData.getId());
            }
        });
    }

    private void refreshPerformanceField(){
        exerciseName.setText("");
        setCounts.setText("");
        repCounts.setText("");
        weightKg.setText("");
        dateAdd.getEditor().setText("");
        idPerformance.setText("");
    }

    private void refreshPerformance() {
        pd.clear();
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT Client_ID FROM mydb.client");
            ResultSet rs = ps.executeQuery();
            String clientsID = "";
            while (rs.next()) {
                clientsID.equals(id);
            }

            this.pd = FXCollections.observableArrayList();

            PreparedStatement ps1 = conn.prepareStatement(sqlLoadPerformance);
            ps1.setInt(1, clientID);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                this.pd.add(new PerformanceData(rs1.getString(3), rs1.getString(4), rs1.getString(5), rs1.getString(6), rs1.getString(7), rs1.getString(1)));
            }
        } catch (Exception e) {
        }
        this.idcperformance.setCellValueFactory(new PropertyValueFactory<PerformanceData, String>("id"));
        this.exerciseNamec.setCellValueFactory(new PropertyValueFactory<PerformanceData, String>("exercise"));
        this.repc.setCellValueFactory(new PropertyValueFactory<PerformanceData, String>("rep"));
        this.setsc.setCellValueFactory(new PropertyValueFactory<PerformanceData, String>("set"));
        this.weightc.setCellValueFactory(new PropertyValueFactory<PerformanceData, String>("weight"));
        this.datec.setCellValueFactory(new PropertyValueFactory<PerformanceData, String>("date"));
        this.performanceTableView.setItems(null);
        this.performanceTableView.setItems(this.pd);
        selectOneRow();
    }

    private void displayWeight() {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlDisplayClientSizeKG);
            ps.setString(1, this.id.getText());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                weightSize.setText(rs.getString("Weight"));
            }
        } catch (Exception e) {
        }
    }

    private void displayClientWeight() {
        try {
            Connection conn = DBConnection.getConnection();
            if (weightSize.getText().isEmpty()) {
                PreparedStatement ps = conn.prepareStatement(sqlDisplayClientKG);
                ps.setString(1, this.id.getText());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    weightClientLabel.setText(rs.getString("Weight"));
                }
            } else if (weightSize.getText() != null) {
                PreparedStatement ps = conn.prepareStatement(sqlDisplayClientSizeKG);
                ps.setString(1, this.id.getText());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    weightClientLabel.setText(rs.getString("Weight"));
                }
            }
        } catch (Exception e) {
        }
    }

    private void displayClientCM() {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlDisplayClientCM);
            ps.setString(1, this.id.getText());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                heightClientLabel.setText(rs.getString("Height"));
            }
        } catch (Exception e) {
        }
    }

    private void displayClientGoal() {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlDisplayGoal);
            ps.setInt(1, clientID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                goal.setText(rs.getString("GoalKg"));
            }
        } catch (Exception e) {
        }
    }

    private void displayGoalMaintain() {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlDisplayReachMaintain);
            ps.setInt(1, clientID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reachGoal.setText(rs.getString("Goal"));
            }
        } catch (Exception e) {
        }
    }

    private void displayGoalLose() {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlDisplayReachLose);
            ps.setInt(1, clientID);
            ResultSet rs1 = ps.executeQuery();
            while (rs1.next()) {
                reachGoal.setText(rs1.getString("Goal"));
            }
        } catch (Exception e) {
        }
    }

    private void displayGoalGain() {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlDisplayReachGain);
            ps.setInt(1, clientID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reachGoal.setText(rs.getString("Goal"));
            }
        } catch (Exception e) {
        }
    }

    private void displayClientKGGoal() {
        double value1 = Double.parseDouble(weightClientLabel.getText());
        double value2 = Double.parseDouble(goal.getText());
        double answer = value1 - value2;
        if (answer < 0) {
            answer = -answer;
        }
        kgGoal.setText(answer + " kg");
    }

    private void lineChartKg() {
        XYChart.Series<String, Double> series = new XYChart.Series();
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlLoadLineChart);
            ps.setInt(1, clientID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                series.getData().add(new XYChart.Data<>(rs.getString(3), rs.getDouble(5)));
            }
            lineChart.getData().add(series);
        } catch (Exception e) {
        }
    }

    @FXML
    private void displayBodyImage() {
        Image image = new Image("/Client/Body.jpg");
        bodyImage.setImage(image);
    }

    @FXML
    private void loadGroupMeal(ActionEvent event) {
        try {
            this.mp = FXCollections.observableArrayList();
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlLoadMealGroup);
            ps.setString(1, type.getSelectionModel().getSelectedItem().toString());
            ps.setString(2, this.id.getText());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                this.mp.add(new MealPlan(rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
            ps.execute();
            conn.close();
        } catch (Exception e) {
        }
        this.groupc.setCellValueFactory(new PropertyValueFactory<MealPlan, String>("group"));
        this.dishc.setCellValueFactory(new PropertyValueFactory<MealPlan, String>("dish"));
        this.gramsc.setCellValueFactory(new PropertyValueFactory<MealPlan, String>("grams"));
        this.createDatec.setCellValueFactory(new PropertyValueFactory<MealPlan, String>("dateCreated"));
        this.mealPlan.setItems(null);
        this.mealPlan.setItems(this.mp);
    }

    @FXML
    private void loadFullMealPlan(ActionEvent event) {
        try {
            this.mp = FXCollections.observableArrayList();
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlLoadFullMealPlan);
            ps.setString(1, this.id.getText());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                this.mp.add(new MealPlan(rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
            ps.execute();
            conn.close();
        } catch (Exception e) {
        }
        this.groupc.setCellValueFactory(new PropertyValueFactory<MealPlan, String>("group"));
        this.dishc.setCellValueFactory(new PropertyValueFactory<MealPlan, String>("dish"));
        this.gramsc.setCellValueFactory(new PropertyValueFactory<MealPlan, String>("grams"));
        this.createDatec.setCellValueFactory(new PropertyValueFactory<MealPlan, String>("dateCreated"));
        this.mealPlan.setItems(null);
        this.mealPlan.setItems(this.mp);
    }

    @FXML
    private void fillMealDateCombo(MouseEvent event) {
        mealDateComboFill.clear();
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlSelectMealDate);
            ps.setString(1, this.id.getText());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                mealDateComboFill.add(rs.getString("Date"));
            }
            ps.close();
            conn.close();
        } catch (Exception e) {
        }
        mealPlanDate.setItems(mealDateComboFill);
    }

    @FXML
    private void displayMealPlanDate(ActionEvent event) {
        try {
            this.mp = FXCollections.observableArrayList();
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlLoadMealPlanDate);
            ps.setString(1, mealPlanDate.getSelectionModel().getSelectedItem().toString());
            ps.setString(2, this.id.getText());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                this.mp.add(new MealPlan(rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
            ps.execute();
            conn.close();
        } catch (Exception e) {
        }
        this.groupc.setCellValueFactory(new PropertyValueFactory<MealPlan, String>("group"));
        this.dishc.setCellValueFactory(new PropertyValueFactory<MealPlan, String>("dish"));
        this.gramsc.setCellValueFactory(new PropertyValueFactory<MealPlan, String>("grams"));
        this.createDatec.setCellValueFactory(new PropertyValueFactory<MealPlan, String>("dateCreated"));
        this.mealPlan.setItems(null);
        this.mealPlan.setItems(this.mp);
    }

    @FXML
    private void logOut(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Login/Login.fxml").openStream());
            LoginController loginController = (LoginController) loader.getController();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            stage = (Stage) logOutButton.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
        }
    }

    @FXML
    private void tableViewToPDFMealPlan(ActionEvent event) {
        if (validateEmptyMeal()) {
            String path = "";
            JFileChooser jFileChooser = new JFileChooser();
            Document document = new Document();
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int x = jFileChooser.showSaveDialog(null);
            if (x == JFileChooser.APPROVE_OPTION) {
                path = jFileChooser.getSelectedFile().getPath();
            }

            try {
                PdfWriter.getInstance(document, new FileOutputStream(path + ".pdf"));
                document.open();
                BaseFont baseFont = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font font = new Font(baseFont);
                Paragraph paragraph = new Paragraph("Vardas: " + Display.name + "          " + "Pavardė: " +Display.lastName, new Font(baseFont));
                PdfPTable pdfPTable = new PdfPTable(4);
                pdfPTable.setSpacingBefore(20);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell("Valgymas");
                pdfPTable.addCell("Patiekalo pavadinimas");
                pdfPTable.addCell("Gramai");
                pdfPTable.addCell(new PdfPCell(new Phrase("Sukūrimo data",font)));

                for (int i = 0; i < mealPlan.getItems().size(); i++) {
                    String group = mealPlan.getItems().get(i).getGroup();
                    String dish = mealPlan.getItems().get(i).getDish();
                    String grams = mealPlan.getItems().get(i).getGrams();
                    String date = mealPlan.getItems().get(i).getDateCreated();

                    pdfPTable.addCell(new PdfPCell(new Phrase(group,font)));
                    pdfPTable.addCell(new PdfPCell(new Phrase(dish,font)));
                    pdfPTable.addCell(grams);
                    pdfPTable.addCell(date);
                }
                document.add(paragraph);
                document.add(pdfPTable);
            } catch (DocumentException e) {
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
                e.printStackTrace();
            }
            document.close();
        }
    }

    private boolean validatePerformanceEmptyFields() {
        if (exerciseName.getText().isEmpty() || repCounts.getText().isEmpty() || setCounts.getText().isEmpty() || weightKg.getText().isEmpty() || dateAdd.getEditor().getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Neįvesti visi privalomi duomenys");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validateSet() {
        Pattern p = Pattern.compile("[0-9]");
        Matcher s = p.matcher(setCounts.getText());
        if (s.find() && s.group().equals(setCounts.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Blogai įvesti duomenys");
            alert.showAndWait();
        }return false;
    }

    private boolean validateEmptyMeal(){
        if (mealPlan.getItems().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Nepasirinkitas mitybos planas");
            alert.showAndWait();
            return false;
        }else return true;
    }
}



