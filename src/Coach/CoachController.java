package Coach;

import Client.ClientController;
import DataBase.DBConnection;
import Dish.DishController;
import Login.LoginController;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class CoachController extends ClientController implements Initializable {

    @FXML
    private TextField search;
    @FXML
    private TableView<ClientData> data;
    @FXML
    private TableColumn<ClientData, String> namec;
    @FXML
    private TableColumn<ClientData, String> surnamec;
    @FXML
    private TableColumn<ClientData, String> idc;
    @FXML
    private TableColumn<ClientData, String> datec;
    @FXML
    private TableColumn<ClientData, String> emailc;
    @FXML
    private Label clientGoal;
    @FXML
    private Label clientGoalKg;
    @FXML
    private TableView<DishData> dishTable;
    @FXML
    private TableColumn<DishData, String> groupc;
    @FXML
    private TableColumn<DishData, String> dishc;
    @FXML
    private TableColumn<DishData, String> kcalc;
    @FXML
    private TableColumn<DishData, String> proteinc;
    @FXML
    private TableColumn<DishData, String> carbc;
    @FXML
    private TableColumn<DishData, String> fatc;
    @FXML
    private TextField dishSearch;
    @FXML
    private Button buttonClient;
    @FXML
    private Button buttonNutrition;
    @FXML
    private GridPane gpClient;
    @FXML
    private GridPane gpNutrition;
    @FXML
    private Button reviewSize;
    @FXML
    private Button reviewPerformance;
    @FXML
    private GridPane gpSize;
    @FXML
    private GridPane gpPerformance;
    @FXML
    private Label idLabel;
    @FXML
    private Label dishName;
    @FXML
    private Label coachID;
    @FXML
    private Label nutritionID;
    @FXML
    private Label nutritionID1;
    @FXML
    private Label nutritionID2;
    @FXML
    private Label nutritionID3;
    @FXML
    private Label mealConfirmation;
    @FXML
    private Label checkMealPlan;
    @FXML
    private Button logOutButton;
    @FXML
    private Label kcal;
    @FXML
    private Label protein;
    @FXML
    private Label carb;
    @FXML
    private Label fat;
    @FXML
    private Label kcal1;
    @FXML
    private Label protein1;
    @FXML
    private Label carb1;
    @FXML
    private Label fat1;
    @FXML
    private Label kcal2;
    @FXML
    private Label protein2;
    @FXML
    private Label carb2;
    @FXML
    private Label fat2;
    @FXML
    private Label kcal3;
    @FXML
    private Label protein3;
    @FXML
    private Label carb3;
    @FXML
    private Label fat3;
    @FXML
    private Label allKcal;
    @FXML
    private Label allProtein;
    @FXML
    private Label allCarb;
    @FXML
    private Label allFat;

    @FXML
    private TableView<SizeData> sizeTable;
    @FXML
    private TableColumn<SizeData, String> cdate;
    @FXML
    private TableColumn<SizeData, String> kgc;
    @FXML
    private TableColumn<SizeData, String> shouldersc;
    @FXML
    private TableColumn<SizeData, String> chestc;
    @FXML
    private TableColumn<SizeData, String> rbicepsc;
    @FXML
    private TableColumn<SizeData, String> lbicepsc;
    @FXML
    private TableColumn<SizeData, String> waistsc;
    @FXML
    private TableColumn<SizeData, String> waistbc;
    @FXML
    private TableColumn<SizeData, String> glutesc;
    @FXML
    private TableColumn<SizeData, String> rthighc;
    @FXML
    private TableColumn<SizeData, String> lthighc;

    @FXML
    private TableView<PerformanceData> performanceTable;
    @FXML
    private TableColumn<PerformanceData, String> exerciseNamec;
    @FXML
    private TableColumn<PerformanceData, String> repc;
    @FXML
    private TableColumn<PerformanceData, String> setc;
    @FXML
    private TableColumn<PerformanceData, String> weightc;
    @FXML
    private TableColumn<PerformanceData, String> date;

    @FXML
    private Label kcalLabel;
    @FXML
    private Label kcalLabel1;
    @FXML
    private Label kcalLabel2;
    @FXML
    private Label kcalLabel3;
    @FXML
    private Label proteinLabel;
    @FXML
    private Label proteinLabel1;
    @FXML
    private Label proteinLabel2;
    @FXML
    private Label proteinLabel3;
    @FXML
    private Label carbLabel;
    @FXML
    private Label carbLabel1;
    @FXML
    private Label carbLabel2;
    @FXML
    private Label carbLabel3;
    @FXML
    private Label fatLabel;
    @FXML
    private Label fatLabel1;
    @FXML
    private Label fatLabel2;
    @FXML
    private Label fatLabel3;
    @FXML
    private TextField gramsTextField;
    @FXML
    private TextField gramsTextField1;
    @FXML
    private TextField gramsTextField2;
    @FXML
    private TextField gramsTextField3;
    @FXML
    private ComboBox<String> mealType;
    @FXML
    private ComboBox<String> mealGroup;
    @FXML
    private ComboBox<String> mealGroup1;
    @FXML
    private ComboBox<String> mealGroup2;
    @FXML
    private ComboBox<String> mealGroup3;
    @FXML
    private ComboBox dishComboBox;
    @FXML
    private ComboBox dishComboBox1;
    @FXML
    private ComboBox dishComboBox2;
    @FXML
    private ComboBox dishComboBox3;

    private String mealTypeValue;
    private String groupValue;
    private String groupValue1;
    private String groupValue2;
    private String groupValue3;
    ObservableList<String> groupList = FXCollections.observableArrayList("Mėsa", "Jūros gėrybės", "Daržovės", "Vaisiai/riešutai", "Pieno produktai", "Miltiniai produktai","Kruopos", "Padažai", "Prieskoniai", "Pusgaminiai", "Maisto papildai", "Greitas maistas", "Gėrimai", "Kiti produktai");
    ObservableList<String> mealTypeList = FXCollections.observableArrayList("Pirmas valgymas", "Antras valgymas", "Trečias valgymas", "Ketvirtas valgymas", "Penktas valgymas", "Užkandis");
    final ObservableList dishList = FXCollections.observableArrayList();
    final ObservableList dishList1 = FXCollections.observableArrayList();
    final ObservableList dishList2 = FXCollections.observableArrayList();
    final ObservableList dishList3 = FXCollections.observableArrayList();
    private ObservableList<ClientData> clientData;
    private ObservableList<DishData> dishData = FXCollections.observableArrayList();
    private ObservableList<SizeData> sizeData;
    private ObservableList<PerformanceData> performanceData;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime ldt = LocalDateTime.now();

    private String sqlLoadClient = "SELECT Client_ID, Name, Last_name, Date_of_birth, Email FROM mydb.client";
    private String sqlLoadDish = "SELECT Nutrition_group, Dish_name, Calories, Protein, Carbohydrate, Fat FROM mydb.nutrition ORDER BY Dish_name";
    private String sqlLoadSize = "SELECT Saving_date, Weight, Shoulders, Chest, Right_biceps, Left_biceps, Waist_big, Waist_small, Glutes, Right_thigh, Left_thigh FROM mydb.size WHERE Client_ID = ?";
    private String sqlLoadPerformance = "SELECT * FROM mydb.sports_performance WHERE Client_ID = ?";
    private String sqlLoadDishComboBox = "SELECT Dish_name FROM mydb.nutrition WHERE Nutrition_group = ?";
    private String sqlLoadNutritionID = "SELECT Nutrition_ID FROM mydb.nutrition WHERE Dish_name = ?";
    private String sqlLoadClientGoal = "SELECT * FROM mydb.goal WHERE Client_ID = ?";
    private String sqlDeleteDish = "DELETE FROM mydb.nutrition WHERE Dish_name = ?";
    private String sqlSelectDish = "SELECT * FROM mydb.nutrition WHERE Dish_name = ?";
    private String sqlCreateMealPlan = "INSERT INTO mydb.meal_plan(Meal_ID, Client_ID, Coach_ID, Nutrition_ID, Type_of_meal, Dish_name, Grams, Date) VALUES (?,?,?,?,?,?,?,?)";

    public void initialize(URL url, ResourceBundle rb) {
        coachID.setText(String.valueOf(ClientController.coachID));
        loadClient();
        displayTextField();
        refreshDish();
        mealType.setItems(mealTypeList);
        mealGroup.setItems(groupList);
        mealGroup1.setItems(groupList);
        mealGroup2.setItems(groupList);
        mealGroup3.setItems(groupList);
        show();
        displayDishName();
    }

    @FXML
    private void click(ActionEvent event) {
        if (event.getSource() == buttonClient) {
            gpClient.toFront();
        } else if (event.getSource() == buttonNutrition) {
            gpNutrition.toFront();
        }
    }

    @FXML
    private void clickClient(ActionEvent event) {
        if (event.getSource() == reviewSize) {
            gpSize.toFront();
        } else if (event.getSource() == reviewPerformance) {
            gpPerformance.toFront();
        }
    }

    private void loadClient() {
        try {
            Connection conn = DBConnection.getConnection();
            this.clientData = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sqlLoadClient);
            while (rs.next()) {
                this.clientData.add(new ClientData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        } catch (Exception e) {
        }
        this.idc.setCellValueFactory(new PropertyValueFactory<ClientData, String>("id"));
        this.namec.setCellValueFactory(new PropertyValueFactory<ClientData, String>("name"));
        this.surnamec.setCellValueFactory(new PropertyValueFactory<ClientData, String>("surname"));
        this.datec.setCellValueFactory(new PropertyValueFactory<ClientData, String>("date"));
        this.emailc.setCellValueFactory(new PropertyValueFactory<ClientData, String>("email"));
        this.data.setItems(null);
        this.data.setItems(this.clientData);
    }

    private void loadClientGoal() {
        if (clientGoalValidation()) {
            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sqlLoadClientGoal);
                ps.setString(1, this.idLabel.getText());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    clientGoal.setText("Kliento tikslas: " + rs.getString("Goal"));
                    clientGoalKg.setText("Kliento norimas svoris: " + rs.getString("GoalKg") + " kg");
                }
            } catch (Exception e) {
            }
        }
    }

    private void checkClientMeal(){
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM mydb.meal_plan WHERE Client_ID = ?");
            ps.setString(1, this.idLabel.getText());
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                checkMealPlan.setText("Klientas mitybos planą turi");
            }else checkMealPlan.setText("Klientas mitybos plano neturi");
        }catch (Exception e){
        }
    }

    @FXML
    private void searchClient(ActionEvent event) {
        FilteredList<ClientData> filteredList = new FilteredList<>(clientData, e -> true);
        search.setOnKeyPressed(e -> {
            search.textProperty().addListener(((observable, oldValue, newValue) -> {
                filteredList.setPredicate((Predicate<? super ClientData>) client -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (client.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    if (client.getSurname().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    if (client.getDate().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    if (client.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            }));
            SortedList<ClientData> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(data.comparatorProperty());
            data.setItems(sortedList);
        });
    }

    private void displayTextField() {
        data.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ClientData cd = data.getItems().get(data.getSelectionModel().getSelectedIndex());
                idLabel.setText(cd.getId());
                checkClientMeal();
                loadClientGoal();
            }
        });
    }

    @FXML
    private void addNewDish(ActionEvent event) {
        try {
            Stage KMIStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Dish/Dish.fxml").openStream());

            DishController dishController = (DishController) loader.getController();
            Scene scene = new Scene(root);
            KMIStage.setScene(scene);
            //KMIStage.setTitle("Mityba");
            KMIStage.setResizable(false);
            KMIStage.show();
        } catch (Exception e) {
        }
    }

    @FXML
    private void loadDish(ActionEvent event) {
        refreshDish();
    }

    @FXML
    private void deleteDish(ActionEvent event) {
        int dp = JOptionPane.showConfirmDialog(null, "Ar tikrai norite ištrinti?", "Delete", JOptionPane.YES_NO_OPTION);
        if (dp == 0) {
            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sqlDeleteDish);
                ps.setString(1, this.dishName.getText());
                ps.executeUpdate();
            } catch (Exception e) {
            }
            refreshDish();
        }
    }

    @FXML
    private void displayDishName(){
        dishTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DishData dishData = dishTable.getItems().get(dishTable.getSelectionModel().getSelectedIndex());
                dishName.setText(dishData.getDishName());
            }
        });
    }

    @FXML
    private void searchDish(KeyEvent event) {
        FilteredList<DishData> filteredList = new FilteredList<>(dishData, e -> true);
        dishSearch.setOnKeyPressed(e -> {
            dishSearch.textProperty().addListener(((observable, oldValue, newValue) -> {
                filteredList.setPredicate((Predicate<? super DishData>) dish -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (dish.getDishName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    if (dish.getProtein().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    if (dish.getCarbs().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    if (dish.getKcal().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    if (dish.getFats().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            }));
            SortedList<DishData> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(dishTable.comparatorProperty());
            dishTable.setItems(sortedList);
        });
    }

    @FXML
    private void loadClientPerformance(ActionEvent event) {
        try {
            Connection conn = DBConnection.getConnection();
            this.performanceData = FXCollections.observableArrayList();
            PreparedStatement ps = conn.prepareStatement(sqlLoadPerformance);
            ps.setString(1, this.idLabel.getText());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                this.performanceData.add(new PerformanceData(rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
            ps.execute();
            conn.close();
        } catch (Exception e) {
        }
        this.exerciseNamec.setCellValueFactory(new PropertyValueFactory<PerformanceData, String>("exercise"));
        this.setc.setCellValueFactory(new PropertyValueFactory<PerformanceData, String>("set"));
        this.repc.setCellValueFactory(new PropertyValueFactory<PerformanceData, String>("rep"));
        this.weightc.setCellValueFactory(new PropertyValueFactory<PerformanceData, String>("weight"));
        this.date.setCellValueFactory(new PropertyValueFactory<PerformanceData, String>("date"));
        this.performanceTable.setItems(null);
        this.performanceTable.setItems(performanceData);
    }

    @FXML
    private void loadClientSize(ActionEvent event) {
        try {
            Connection conn = DBConnection.getConnection();
            this.sizeData = FXCollections.observableArrayList();
            PreparedStatement ps = conn.prepareStatement(sqlLoadSize);
            ps.setString(1, this.idLabel.getText());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                this.sizeData.add(new SizeData(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(8), rs.getString(7), rs.getString(9), rs.getString(11), rs.getString(10), rs.getString(1)));
            }
            ps.execute();
            conn.close();
        } catch (Exception e) {
        }
        this.kgc.setCellValueFactory(new PropertyValueFactory<SizeData, String>("kg"));
        this.shouldersc.setCellValueFactory(new PropertyValueFactory<SizeData, String>("shoulders"));
        this.chestc.setCellValueFactory(new PropertyValueFactory<SizeData, String>("chest"));
        this.rbicepsc.setCellValueFactory(new PropertyValueFactory<SizeData, String>("rbiceps"));
        this.lbicepsc.setCellValueFactory(new PropertyValueFactory<SizeData, String>("lbiceps"));
        this.waistsc.setCellValueFactory(new PropertyValueFactory<SizeData, String>("waists"));
        this.waistbc.setCellValueFactory(new PropertyValueFactory<SizeData, String>("waistb"));
        this.glutesc.setCellValueFactory(new PropertyValueFactory<SizeData, String>("glutes"));
        this.rthighc.setCellValueFactory(new PropertyValueFactory<SizeData, String>("rthight"));
        this.lthighc.setCellValueFactory(new PropertyValueFactory<SizeData, String>("lthight"));
        this.cdate.setCellValueFactory(new PropertyValueFactory<SizeData, String>("date"));
        this.sizeTable.setItems(null);
        this.sizeTable.setItems(sizeData);
    }

    @FXML
    private void mealTypeComboBox(ActionEvent event) {
        if (mealType.getValue() == "Pirmas valgymas") {
            mealTypeValue = "Pirmas valgymas";
        } else if (mealType.getValue() == "Antras valgymas") {
            mealTypeValue = "Antras valgymas";
        } else if (mealType.getValue() == "Trečias valgymas") {
            mealTypeValue = "Trečias valgymas";
        } else if (mealType.getValue() == "Ketvirtas valgymas") {
            mealTypeValue = "Ketvirtas valgymas";
        } else if (mealType.getValue() == "Penktas valgymas") {
            mealTypeValue = "Penktas valgymas";
        } else if (mealType.getValue() == "Užkandis") {
            mealTypeValue = "Užkandis";
        }
    }

    @FXML
    private void groupComboBox(ActionEvent event) {
        if (mealGroup.getValue() == "Mėsa") {
            groupValue = "Mėsa";
        } else if (mealGroup.getValue() == "Jūros gėrybės") {
            groupValue = "Jūros gėrybės";
        } else if (mealGroup.getValue() == "Daržovės") {
            groupValue = "Daržovės";
        } else if (mealGroup.getValue() == "Vaisiai/riešutai") {
            groupValue = "Vaisiai/riešutai";
        } else if (mealGroup.getValue() == "Pieno produktai") {
            groupValue = "Pieno produktai";
        } else if (mealGroup.getValue() == "Miltiniai produktai") {
            groupValue = "Miltiniai produktai";
        } else if (mealGroup.getValue() == "Kruopos") {
                groupValue = "Kruopos";
        } else if (mealGroup.getValue() == "Padažai") {
            groupValue = "Padažai";
        } else if (mealGroup.getValue() == "Prieskoniai") {
            groupValue = "Prieskoniai";
        } else if (mealGroup.getValue() == "Pusgaminiai") {
            groupValue = "Pusgaminiai";
        } else if (mealGroup.getValue() == "Maisto papildai") {
            groupValue = "Maisto papildai";
        } else if (mealGroup.getValue() == "Greitas maistas") {
            groupValue = "Greitas maistas";
        } else if (mealGroup.getValue() == "Gėrimai") {
            groupValue = "Gėrimai";
        } else groupValue = "Kiti produktai";
    }

    @FXML
    private void groupComboBox1(ActionEvent event) {
        if (mealGroup1.getValue() == "Mėsa") {
            groupValue1 = "Mėsa";
        } else if (mealGroup1.getValue() == "Jūros gėrybės") {
            groupValue1 = "Jūros gėrybės";
        } else if (mealGroup1.getValue() == "Daržovės") {
            groupValue1 = "Daržovės";
        } else if (mealGroup1.getValue() == "Vaisiai/riešutai") {
            groupValue1 = "Vaisiai/riešutai";
        } else if (mealGroup1.getValue() == "Pieno produktai") {
            groupValue1 = "Pieno produktai";
        } else if (mealGroup1.getValue() == "Miltiniai produktai") {
            groupValue1 = "Miltiniai produktai";
        } else if (mealGroup1.getValue() == "Kruopos") {
            groupValue1 = "Kruopos";
        } else if (mealGroup1.getValue() == "Padažai") {
            groupValue1 = "Padažai";
        } else if (mealGroup1.getValue() == "Prieskoniai") {
            groupValue1 = "Prieskoniai";
        } else if (mealGroup1.getValue() == "Pusgaminiai") {
            groupValue1 = "Pusgaminiai";
        } else if (mealGroup1.getValue() == "Maisto papildai") {
            groupValue1 = "Maisto papildai";
        } else if (mealGroup1.getValue() == "Greitas maistas") {
            groupValue1 = "Greitas maistas";
        } else if (mealGroup1.getValue() == "Gėrimai") {
            groupValue1 = "Gėrimai";
        } else groupValue1 = "Kiti produktai";
    }

    @FXML
    private void groupComboBox2(ActionEvent event) {
        if (mealGroup2.getValue() == "Mėsa") {
            groupValue2 = "Mėsa";
        } else if (mealGroup2.getValue() == "Jūros gėrybės") {
            groupValue2 = "Jūros gėrybės";
        } else if (mealGroup2.getValue() == "Daržovės") {
            groupValue2 = "Daržovės";
        } else if (mealGroup2.getValue() == "Vaisiai/riešutai") {
            groupValue2 = "Vaisiai/riešutai";
        } else if (mealGroup2.getValue() == "Pieno produktai") {
            groupValue2 = "Pieno produktai";
        } else if (mealGroup2.getValue() == "Miltiniai produktai") {
            groupValue2 = "Miltiniai produktai";
        } else if (mealGroup2.getValue() == "Kruopos") {
            groupValue2 = "Kruopos";
        } else if (mealGroup2.getValue() == "Padažai") {
            groupValue2 = "Padažai";
        } else if (mealGroup2.getValue() == "Prieskoniai") {
            groupValue2 = "Prieskoniai";
        } else if (mealGroup2.getValue() == "Pusgaminiai") {
            groupValue2 = "Pusgaminiai";
        } else if (mealGroup2.getValue() == "Maisto papildai") {
            groupValue2 = "Maisto papildai";
        } else if (mealGroup2.getValue() == "Greitas maistas") {
            groupValue2 = "Greitas maistas";
        } else if (mealGroup2.getValue() == "Gėrimai") {
            groupValue2 = "Gėrimai";
        } else groupValue2 = "Kiti produktai";
    }

    @FXML
    private void groupComboBox3(ActionEvent event) {
        if (mealGroup3.getValue() == "Mėsa") {
            groupValue3 = "Mėsa";
        } else if (mealGroup3.getValue() == "Jūros gėrybės") {
            groupValue3 = "Jūros gėrybės";
        } else if (mealGroup3.getValue() == "Daržovės") {
            groupValue3 = "Daržovės";
        } else if (mealGroup3.getValue() == "Vaisiai/riešutai") {
            groupValue3 = "Vaisiai/riešutai";
        } else if (mealGroup3.getValue() == "Pieno produktai") {
            groupValue3 = "Pieno produktai";
        } else if (mealGroup3.getValue() == "Miltiniai produktai") {
            groupValue3 = "Miltiniai produktai";
        } else if (mealGroup3.getValue() == "Kruopos") {
            groupValue3 = "Kruopos";
        } else if (mealGroup3.getValue() == "Padažai") {
            groupValue3 = "Padažai";
        } else if (mealGroup3.getValue() == "Prieskoniai") {
            groupValue3 = "Prieskoniai";
        } else if (mealGroup3.getValue() == "Pusgaminiai") {
            groupValue3 = "Pusgaminiai";
        } else if (mealGroup3.getValue() == "Maisto papildai") {
            groupValue3 = "Maisto papildai";
        } else if (mealGroup3.getValue() == "Greitas maistas") {
            groupValue3 = "Greitas maistas";
        } else if (mealGroup3.getValue() == "Gėrimai") {
            groupValue3 = "Gėrimai";
        } else groupValue3 = "Kiti produktai";
    }

    @FXML
    private void nutritionComboBox(MouseEvent event) {
        dishList.clear();
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlLoadDishComboBox);
            ps.setString(1, this.groupValue);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dishList.add(rs.getString("Dish_name"));
            }
        } catch (Exception e) {
        }
        dishComboBox.setItems(dishList);

    }

    @FXML
    private void nutritionComboBox1(MouseEvent event) {
        dishList1.clear();
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlLoadDishComboBox);
            ps.setString(1, this.groupValue1);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dishList1.add(rs.getString("Dish_name"));
            }
        } catch (Exception e) {
        }
        dishComboBox1.setItems(dishList1);
    }

    @FXML
    private void nutritionComboBox2(MouseEvent event) {
        dishList2.clear();
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlLoadDishComboBox);
            ps.setString(1, this.groupValue2);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dishList2.add(rs.getString("Dish_name"));
            }
        } catch (Exception e) {
        }
        dishComboBox2.setItems(dishList2);
    }

    @FXML
    private void nutritionComboBox3(MouseEvent event) {
        dishList3.clear();
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlLoadDishComboBox);
            ps.setString(1, this.groupValue3);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dishList3.add(rs.getString("Dish_name"));
            }
        } catch (Exception e) {
        }
        dishComboBox3.setItems(dishList3);
    }

    @FXML
    private void loadDishInformation(KeyEvent event) {
        double grams = 1;
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlSelectDish);
            ps.setString(1, dishComboBox.getSelectionModel().getSelectedItem().toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                gramsTextField.setText(String.valueOf(grams));
                kcalLabel.setText(rs.getString("Calories"));
                double kcalValue = Double.parseDouble(kcalLabel.getText());
                double newKcal = kcalValue / 100;
                kcalLabel.setText(new DecimalFormat("##.##").format(newKcal));
                kcal.setText(new DecimalFormat("##.##").format(newKcal));
                proteinLabel.setText(rs.getString("Protein"));
                double proteinValue = Double.parseDouble(proteinLabel.getText());
                double newProtein = proteinValue / 100;
                proteinLabel.setText(new DecimalFormat("##.##").format(newProtein));
                protein.setText(new DecimalFormat("##.##").format(newProtein));
                carbLabel.setText(rs.getString("Carbohydrate"));
                double carbValue = Double.parseDouble(carbLabel.getText());
                double newCarb = carbValue / 100;
                carbLabel.setText(new DecimalFormat("##.##").format(newCarb));
                carb.setText(new DecimalFormat("##.##").format(newCarb));
                fatLabel.setText(rs.getString("Fat"));
                double fatValue = Double.parseDouble(fatLabel.getText());
                double newFat = fatValue / 100;
                fatLabel.setText(new DecimalFormat("##.##").format(newFat));
                fat.setText(new DecimalFormat("##.##").format(newFat));
            }
        } catch (Exception e) {
        }
        count();show();nutritionId();
    }

    @FXML
    private void loadDishInformation1(KeyEvent event) {
        double grams = 1;
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlSelectDish);
            ps.setString(1, dishComboBox1.getSelectionModel().getSelectedItem().toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                gramsTextField1.setText(String.valueOf(grams));
                kcalLabel1.setText(rs.getString("Calories"));
                double kcalValue = Double.parseDouble(kcalLabel1.getText());
                double newKcal = kcalValue / 100;
                kcalLabel1.setText(new DecimalFormat("##.##").format(newKcal));
                kcal1.setText(new DecimalFormat("##.##").format(newKcal));
                proteinLabel1.setText(rs.getString("Protein"));
                double proteinValue = Double.parseDouble(proteinLabel1.getText());
                double newProtein = proteinValue / 100;
                proteinLabel1.setText(new DecimalFormat("##.##").format(newProtein));
                protein1.setText(new DecimalFormat("##.##").format(newProtein));
                carbLabel1.setText(rs.getString("Carbohydrate"));
                double carbValue = Double.parseDouble(carbLabel1.getText());
                double newCarb = carbValue / 100;
                carbLabel1.setText(new DecimalFormat("##.##").format(newCarb));
                carb1.setText(new DecimalFormat("##.##").format(newCarb));
                fatLabel1.setText(rs.getString("Fat"));
                double fatValue = Double.parseDouble(fatLabel1.getText());
                double newFat = fatValue / 100;
                fatLabel1.setText(new DecimalFormat("##.##").format(newFat));
                fat1.setText(new DecimalFormat("##.##").format(newFat));
            }
        } catch (Exception e) {
        }
        count();show(); nutritionId();
    }

    @FXML
    private void loadDishInformation2(KeyEvent event) {
        double grams = 1;
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlSelectDish);
            ps.setString(1, dishComboBox2.getSelectionModel().getSelectedItem().toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                gramsTextField2.setText(String.valueOf(grams));
                kcalLabel2.setText(rs.getString("Calories"));
                double kcalValue = Double.parseDouble(kcalLabel2.getText());
                double newKcal = kcalValue / 100;
                kcalLabel2.setText(new DecimalFormat("##.##").format(newKcal));
                kcal2.setText(new DecimalFormat("##.##").format(newKcal));
                proteinLabel2.setText(rs.getString("Protein"));
                double proteinValue = Double.parseDouble(proteinLabel2.getText());
                double newProtein = proteinValue / 100;
                proteinLabel2.setText(new DecimalFormat("##.##").format(newProtein));
                protein2.setText(new DecimalFormat("##.##").format(newProtein));
                carbLabel2.setText(rs.getString("Carbohydrate"));
                double carbValue = Double.parseDouble(carbLabel2.getText());
                double newCarb = carbValue / 100;
                carbLabel2.setText(new DecimalFormat("##.##").format(newCarb));
                carb2.setText(new DecimalFormat("##.##").format(newCarb));
                fatLabel2.setText(rs.getString("Fat"));
                double fatValue = Double.parseDouble(fatLabel2.getText());
                double newFat = fatValue / 100;
                fatLabel2.setText(new DecimalFormat("##.##").format(newFat));
                fat2.setText(new DecimalFormat("##.##").format(newFat));
            }
        } catch (Exception e) {
        }
        count();show();nutritionId();
    }

    @FXML
    private void loadDishInformation3(KeyEvent event) {
        double grams = 1;
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlSelectDish);
            ps.setString(1, dishComboBox3.getSelectionModel().getSelectedItem().toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                gramsTextField3.setText(String.valueOf(grams));
                kcalLabel3.setText(rs.getString("Calories"));
                double kcalValue = Double.parseDouble(kcalLabel3.getText());
                double newKcal = kcalValue / 100;
                kcalLabel3.setText(new DecimalFormat("##.##").format(newKcal));
                kcal3.setText(new DecimalFormat("##.##").format(newKcal));
                proteinLabel3.setText(rs.getString("Protein"));
                double proteinValue = Double.parseDouble(proteinLabel3.getText());
                double newProtein = proteinValue / 100;
                proteinLabel3.setText(new DecimalFormat("##.##").format(newProtein));
                protein3.setText(new DecimalFormat("##.##").format(newProtein));
                carbLabel3.setText(rs.getString("Carbohydrate"));
                double carbValue = Double.parseDouble(carbLabel3.getText());
                double newCarb = carbValue / 100;
                carbLabel3.setText(new DecimalFormat("##.##").format(newCarb));
                carb3.setText(new DecimalFormat("##.##").format(newCarb));
                fatLabel3.setText(rs.getString("Fat"));
                double fatValue = Double.parseDouble(fatLabel3.getText());
                double newFat = fatValue / 100;
                fatLabel3.setText(new DecimalFormat("##.##").format(newFat));
                fat3.setText(new DecimalFormat("##.##").format(newFat));
            }
        } catch (Exception e) {
        }
        count();nutritionId();
    }

    @FXML
    private void grams(ActionEvent event) {
        if (gramsTextField.getText().isEmpty()) {
            kcalLabel.setText(String.valueOf(kcal.getText()));
            proteinLabel.setText(String.valueOf(protein.getText()));
            carbLabel.setText(String.valueOf(carb.getText()));
            fatLabel.setText(String.valueOf(fat.getText()));
        } else {
            double value = Double.parseDouble(gramsTextField.getText());
            double firstKcal = Double.parseDouble(kcalLabel.getText());
            double newKcal = value * firstKcal;
            kcalLabel.setText(new DecimalFormat("##.##").format(newKcal));
            double firstProtein = Double.parseDouble(proteinLabel.getText());
            double newProtein = value * firstProtein;
            proteinLabel.setText(new DecimalFormat("##.##").format(newProtein));
            double firstCarb = Double.parseDouble(carbLabel.getText());
            double newCarb = value * firstCarb;
            carbLabel.setText(new DecimalFormat("##.##").format(newCarb));
            double firstFat = Double.parseDouble(fatLabel.getText());
            double newFat = value * firstFat;
            fatLabel.setText(new DecimalFormat("##.##").format(newFat));
        }
        count();
    }

    @FXML
    private void grams1(ActionEvent event) {
        if (gramsTextField1.getText().isEmpty()) {
            kcalLabel1.setText(String.valueOf(kcal1.getText()));
            proteinLabel1.setText(String.valueOf(protein1.getText()));
            carbLabel1.setText(String.valueOf(carb1.getText()));
            fatLabel1.setText(String.valueOf(fat1.getText()));
        } else {
            double value1 = Double.parseDouble(gramsTextField1.getText());
            double firstKcal1 = Double.parseDouble(kcalLabel1.getText());
            double newKcal1 = value1 * firstKcal1;
            kcalLabel1.setText(new DecimalFormat("##.##").format(newKcal1));
            double firstProtein1 = Double.parseDouble(proteinLabel1.getText());
            double newProtein1 = value1 * firstProtein1;
            proteinLabel1.setText(new DecimalFormat("##.##").format(newProtein1));
            double firstCarb1 = Double.parseDouble(carbLabel1.getText());
            double newCarb1 = value1 * firstCarb1;
            carbLabel1.setText(new DecimalFormat("##.##").format(newCarb1));
            double firstFat1 = Double.parseDouble(fatLabel1.getText());
            double newFat1 = value1 * firstFat1;
            fatLabel1.setText(new DecimalFormat("##.##").format(newFat1));
        }
        count();
    }

    @FXML
    private void grams2(ActionEvent event) {
        if (gramsTextField2.getText().isEmpty()) {
            kcalLabel2.setText(String.valueOf(kcal2.getText()));
            proteinLabel2.setText(String.valueOf(protein2.getText()));
            carbLabel2.setText(String.valueOf(carb2.getText()));
            fatLabel2.setText(String.valueOf(fat2.getText()));
        } else {
            double value2 = Double.parseDouble(gramsTextField2.getText());
            double firstKcal2 = Double.parseDouble(kcalLabel2.getText());
            double newKcal2 = value2 * firstKcal2;
            kcalLabel2.setText(new DecimalFormat("##.##").format(newKcal2));
            double firstProtein2 = Double.parseDouble(proteinLabel2.getText());
            double newProtein2 = value2 * firstProtein2;
            proteinLabel2.setText(new DecimalFormat("##.##").format(newProtein2));
            double firstCarb2 = Double.parseDouble(carbLabel2.getText());
            double newCarb2 = value2 * firstCarb2;
            carbLabel2.setText(new DecimalFormat("##.##").format(newCarb2));
            double firstFat2 = Double.parseDouble(fatLabel2.getText());
            double newFat2 = value2 * firstFat2;
            fatLabel2.setText(new DecimalFormat("##.##").format(newFat2));
        }
        count();
    }

    @FXML
    private void grams3(ActionEvent event) {
        if (gramsTextField3.getText().isEmpty()) {
            kcalLabel3.setText(String.valueOf(kcal3.getText()));
            proteinLabel3.setText(String.valueOf(protein3.getText()));
            carbLabel3.setText(String.valueOf(carb3.getText()));
            fatLabel3.setText(String.valueOf(fat3.getText()));
        } else {
            double value3 = Double.parseDouble(gramsTextField3.getText());
            double firstKcal3 = Double.parseDouble(kcalLabel3.getText());
            double newKcal3 = value3 * firstKcal3;
            kcalLabel3.setText(new DecimalFormat("##.##").format(newKcal3));
            double firstProtein3 = Double.parseDouble(proteinLabel3.getText());
            double newProtein3 = value3 * firstProtein3;
            proteinLabel3.setText(new DecimalFormat("##.##").format(newProtein3));
            double firstCarb3 = Double.parseDouble(carbLabel3.getText());
            double newCarb3 = value3 * firstCarb3;
            carbLabel3.setText(new DecimalFormat("##.##").format(newCarb3));
            double firstFat3 = Double.parseDouble(fatLabel3.getText());
            double newFat3 = value3 * firstFat3;
            fatLabel3.setText(new DecimalFormat("##.##").format(newFat3));
        }
        count();
    }

    private void refreshDish() {
        dishData.clear();
        try {
            Connection connection = DBConnection.getConnection();
            this.dishData = FXCollections.observableArrayList();
            ResultSet rs = connection.createStatement().executeQuery(sqlLoadDish);
            while (rs.next()) {
                this.dishData.add(new DishData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));

            }
        } catch (Exception e) {
        }

        this.groupc.setCellValueFactory(new PropertyValueFactory<DishData, String>("group"));
        this.dishc.setCellValueFactory(new PropertyValueFactory<DishData, String>("dishName"));
        this.kcalc.setCellValueFactory(new PropertyValueFactory<DishData, String>("kcal"));
        this.proteinc.setCellValueFactory(new PropertyValueFactory<DishData, String>("protein"));
        this.carbc.setCellValueFactory(new PropertyValueFactory<DishData, String>("carbs"));
        this.fatc.setCellValueFactory(new PropertyValueFactory<DishData, String>("fats"));
        this.dishTable.setItems(null);
        this.dishTable.setItems(this.dishData);
    }

    @FXML
    private void mealPlan(ActionEvent event){
        createMealPlan();
        createMealPlan1();
        createMealPlan2();
        createMealPlan3();
        emptyFields();
        show();
    }

    @FXML
    private void createMealPlan() {
        if (emptyClientValidation() && emptyMealTypeValidation() && emptyMealGroupValidation() && emptyDishComboValidation()) {
            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT max(Meal_ID)+1 FROM mydb.meal_plan");
                ResultSet rs = ps.executeQuery();
                String mealID = "";
                while (rs.next()) {
                    mealID = rs.getString(1);
                }

                PreparedStatement ps1 = conn.prepareStatement(sqlCreateMealPlan);
                ps1.setString(1, mealID);
                ps1.setString(2, this.idLabel.getText());
                ps1.setString(3, this.coachID.getText());
                ps1.setString(4, this.nutritionID.getText());
                ps1.setString(5, this.mealTypeValue);
                ps1.setString(6, this.dishComboBox.getSelectionModel().getSelectedItem().toString());
                ps1.setString(7, this.gramsTextField.getText());
                ps1.setString(8, dtf.format(ldt));
                ps1.execute();
                mealConfirmation.setText("Mitybos planas sukurtas");
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    @FXML
    private void createMealPlan1() {
            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT max(Meal_ID)+1 FROM mydb.meal_plan");
                ResultSet rs = ps.executeQuery();
                String mealID = "";
                while (rs.next()) {
                    mealID = rs.getString(1);
                }

                PreparedStatement ps1 = conn.prepareStatement(sqlCreateMealPlan);
                ps1.setString(1, mealID);
                ps1.setString(2, this.idLabel.getText());
                ps1.setString(3, this.coachID.getText());
                ps1.setString(4, this.nutritionID1.getText());
                ps1.setString(5, this.mealTypeValue);
                ps1.setString(6, this.dishComboBox1.getSelectionModel().getSelectedItem().toString());
                ps1.setString(7, this.gramsTextField1.getText());
                ps1.setString(8, dtf.format(ldt));
                ps1.execute();
                conn.close();
            } catch (Exception e) {
            }
    }

    @FXML
    private void createMealPlan2() {
            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT max(Meal_ID)+1 FROM mydb.meal_plan");
                ResultSet rs = ps.executeQuery();
                String mealID = "";
                while (rs.next()) {
                    mealID = rs.getString(1);
                }

                PreparedStatement ps1 = conn.prepareStatement(sqlCreateMealPlan);
                ps1.setString(1, mealID);
                ps1.setString(2, this.idLabel.getText());
                ps1.setString(3, this.coachID.getText());
                ps1.setString(4, this.nutritionID2.getText());
                ps1.setString(5, this.mealTypeValue);
                ps1.setString(6, this.dishComboBox2.getSelectionModel().getSelectedItem().toString());
                ps1.setString(7, this.gramsTextField2.getText());
                ps1.setString(8, dtf.format(ldt));
                ps1.execute();
                conn.close();
            } catch (Exception e) {
        }
    }

    @FXML
    private void createMealPlan3() {
            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT max(Meal_ID)+1 FROM mydb.meal_plan");
                ResultSet rs = ps.executeQuery();
                String mealID = "";
                while (rs.next()) {
                    mealID = rs.getString(1);
                }

                PreparedStatement ps1 = conn.prepareStatement(sqlCreateMealPlan);
                ps1.setString(1, mealID);
                ps1.setString(2, this.idLabel.getText());
                ps1.setString(3, this.coachID.getText());
                ps1.setString(4, this.nutritionID3.getText());
                ps1.setString(5, this.mealTypeValue);
                ps1.setString(6, this.dishComboBox3.getSelectionModel().getSelectedItem().toString());
                ps1.setString(7, this.gramsTextField3.getText());
                ps1.setString(8, dtf.format(ldt));
                ps1.execute();
                conn.close();
            } catch (Exception e) {
            }
    }

    @FXML
    private void logOut(ActionEvent event) {
        try {
            Stage loginStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Login/Login.fxml").openStream());
            LoginController loginController = (LoginController) loader.getController();
            Scene scene = new Scene(root);
            loginStage.setScene(scene);
           // loginStage.setTitle("SSTS");
            loginStage.setResizable(false);
            loginStage.show();
            loginStage = (Stage) logOutButton.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
        }
    }

    @FXML
    private void count() {
        countKcal();
        countProtein();
        countCarb();
        countFat();
    }

    @FXML
    private void countKcal() {
        double sum = 0;
        DecimalFormat df = new DecimalFormat("####.##");
        if (kcalLabel3.getText().isEmpty() && kcalLabel2.getText().isEmpty() && kcalLabel1.getText().isEmpty()) {
            allKcal.setText(kcalLabel.getText());
        } else if (kcalLabel3.getText().isEmpty() && kcalLabel2.getText().isEmpty()) {
            double c1 = Double.parseDouble(kcalLabel.getText());
            double c2 = Double.parseDouble(kcalLabel1.getText());
            sum = c1 + c2;
            allKcal.setText(df.format(sum));
        } else if (kcalLabel3.getText().isEmpty()) {
            double c1 = Double.parseDouble(kcalLabel.getText());
            double c2 = Double.parseDouble(kcalLabel1.getText());
            double c3 = Double.parseDouble(kcalLabel2.getText());
            sum = c1 + c2 + c3;
            allKcal.setText(df.format(sum));
        } else if (kcalLabel3 != null && kcalLabel2 != null && kcalLabel1 != null && kcalLabel != null) {
            double c1 = Double.parseDouble(kcalLabel.getText());
            double c2 = Double.parseDouble(kcalLabel1.getText());
            double c3 = Double.parseDouble(kcalLabel2.getText());
            double c4 = Double.parseDouble(kcalLabel3.getText());
            sum = c1 + c2 + c3 + c4;
            allKcal.setText(df.format(sum));
        }
    }

    @FXML
    private void countProtein() {
        double sum = 0;
        DecimalFormat df = new DecimalFormat("####.##");
        if (proteinLabel3.getText().isEmpty() && proteinLabel2.getText().isEmpty() && proteinLabel1.getText().isEmpty()) {
            allProtein.setText(proteinLabel.getText());
        } else if (proteinLabel3.getText().isEmpty() && proteinLabel2.getText().isEmpty()) {
            double c1 = Double.parseDouble(proteinLabel.getText());
            double c2 = Double.parseDouble(proteinLabel1.getText());
            sum = c1 + c2;
            allProtein.setText(df.format(sum));
        } else if (proteinLabel3.getText().isEmpty()) {
            double c1 = Double.parseDouble(proteinLabel.getText());
            double c2 = Double.parseDouble(proteinLabel1.getText());
            double c3 = Double.parseDouble(proteinLabel2.getText());
            sum = c1 + c2 + c3;
            allProtein.setText(df.format(sum));
        } else if (proteinLabel3 != null && proteinLabel2 != null && proteinLabel1 != null && proteinLabel != null) {
            double c1 = Double.parseDouble(proteinLabel.getText());
            double c2 = Double.parseDouble(proteinLabel1.getText());
            double c3 = Double.parseDouble(proteinLabel2.getText());
            double c4 = Double.parseDouble(proteinLabel3.getText());
            sum = c1 + c2 + c3 + c4;
            allProtein.setText(df.format(sum));
        }
    }

    @FXML
    private void countCarb() {
        double sum = 0;
        DecimalFormat df = new DecimalFormat("####.##");
        if (carbLabel3.getText().isEmpty() && carbLabel2.getText().isEmpty() && carbLabel1.getText().isEmpty()) {
            allCarb.setText(carbLabel.getText());
        } else if (carbLabel3.getText().isEmpty() && carbLabel2.getText().isEmpty()) {
            double c1 = Double.parseDouble(carbLabel.getText());
            double c2 = Double.parseDouble(carbLabel1.getText());
            sum = c1 + c2;
            allCarb.setText(df.format(sum));
        } else if (carbLabel3.getText().isEmpty()) {
            double c1 = Double.parseDouble(carbLabel.getText());
            double c2 = Double.parseDouble(carbLabel1.getText());
            double c3 = Double.parseDouble(carbLabel2.getText());
            sum = c1 + c2 + c3;
            allCarb.setText(df.format(sum));
        } else if (carbLabel3 != null && carbLabel2 != null && carbLabel1 != null && carbLabel != null) {
            double c1 = Double.parseDouble(carbLabel.getText());
            double c2 = Double.parseDouble(carbLabel1.getText());
            double c3 = Double.parseDouble(carbLabel2.getText());
            double c4 = Double.parseDouble(carbLabel3.getText());
            sum = c1 + c2 + c3 + c4;
            allCarb.setText(df.format(sum));
        }
    }

    @FXML
    private void countFat() {
        double sum = 0;
        DecimalFormat df = new DecimalFormat("####.##");
        if (fatLabel3.getText().isEmpty() && fatLabel2.getText().isEmpty() && fatLabel1.getText().isEmpty()) {
            allFat.setText(fatLabel.getText());
        } else if (fatLabel3.getText().isEmpty() && fatLabel2.getText().isEmpty()) {
            double c1 = Double.parseDouble(fatLabel.getText());
            double c2 = Double.parseDouble(fatLabel1.getText());
            sum = c1 + c2;
            allFat.setText(df.format(sum));
        } else if (fatLabel3.getText().isEmpty()) {
            double c1 = Double.parseDouble(fatLabel.getText());
            double c2 = Double.parseDouble(fatLabel1.getText());
            double c3 = Double.parseDouble(fatLabel2.getText());
            sum = c1 + c2 + c3;
            allFat.setText(df.format(sum));
        } else if (fatLabel3 != null && fatLabel2 != null && fatLabel1 != null && fatLabel != null) {
            double c1 = Double.parseDouble(fatLabel.getText());
            double c2 = Double.parseDouble(fatLabel1.getText());
            double c3 = Double.parseDouble(fatLabel2.getText());
            double c4 = Double.parseDouble(fatLabel3.getText());
            sum = c1 + c2 + c3 + c4;
            allFat.setText(df.format(sum));
        }
    }

    private void nutritionId(){
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlLoadNutritionID);
            ps.setString(1, this.dishComboBox.getSelectionModel().getSelectedItem().toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                nutritionID.setText(rs.getString("Nutrition_ID"));
            }

            PreparedStatement ps1 = conn.prepareStatement(sqlLoadNutritionID);
            ps1.setString(1, this.dishComboBox1.getSelectionModel().getSelectedItem().toString());
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()){
                nutritionID1.setText(rs1.getString("Nutrition_ID"));
            }

            PreparedStatement ps2 = conn.prepareStatement(sqlLoadNutritionID);
            ps2.setString(1, this.dishComboBox2.getSelectionModel().getSelectedItem().toString());
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()){
                nutritionID2.setText(rs2.getString("Nutrition_ID"));
            }

            PreparedStatement ps3 = conn.prepareStatement(sqlLoadNutritionID);
            ps3.setString(1, this.dishComboBox3.getSelectionModel().getSelectedItem().toString());
            ResultSet rs3 = ps3.executeQuery();
            while (rs3.next()){
                nutritionID3.setText(rs3.getString("Nutrition_ID"));
            }
            conn.close();
        }catch (Exception e){
        }
    }

    private void emptyFields(){
        gramsTextField.setText("");
        gramsTextField1.setText("");
        gramsTextField2.setText("");
        kcalLabel.setText("");
        kcalLabel1.setText("");
        kcalLabel2.setText("");
        kcalLabel3.setText("");
        proteinLabel.setText("");
        proteinLabel1.setText("");
        proteinLabel2.setText("");
        proteinLabel3.setText("");
        carbLabel.setText("");
        carbLabel1.setText("");
        carbLabel2.setText("");
        carbLabel3.setText("");
        fatLabel.setText("");
        fatLabel1.setText("");
        fatLabel2.setText("");
        fatLabel3.setText("");
        allKcal.setText("");
        allFat.setText("");
        allCarb.setText("");
        allProtein.setText("");
    }

    @FXML
    private void show() {
        if (gramsTextField.getText().isEmpty()) {
            mealGroup1.setVisible(false);
            dishComboBox1.setVisible(false);
            gramsTextField1.setVisible(false);
            mealGroup2.setVisible(false);
            dishComboBox2.setVisible(false);
            gramsTextField2.setVisible(false);
            mealGroup3.setVisible(false);
            dishComboBox3.setVisible(false);
            gramsTextField3.setVisible(false);
        } else if (gramsTextField.getText() != null) {
            mealGroup1.setVisible(true);
            dishComboBox1.setVisible(true);
            gramsTextField1.setVisible(true);
        }
        if (gramsTextField1.getText().isEmpty()) {
            mealGroup2.setVisible(false);
            dishComboBox2.setVisible(false);
            gramsTextField2.setVisible(false);
            mealGroup3.setVisible(false);
            dishComboBox3.setVisible(false);
            gramsTextField3.setVisible(false);
        } else if (gramsTextField1.getText() != null) {
            mealGroup2.setVisible(true);
            dishComboBox2.setVisible(true);
            gramsTextField2.setVisible(true);
        }
        if (gramsTextField2.getText().isEmpty()) {
            mealGroup3.setVisible(false);
            dishComboBox3.setVisible(false);
            gramsTextField3.setVisible(false);
        } else if (gramsTextField2.getText() != null) {
            mealGroup3.setVisible(true);
            dishComboBox3.setVisible(true);
            gramsTextField3.setVisible(true);
        }
    }

    @FXML
    private void setToEmpty(){
        mealConfirmation.setText("");
    }

    @FXML
    private void empty(MouseEvent event){
        mealConfirmation.setText("");
        checkMealPlan.setText("");
    }

    @Override
    public void KMI(ActionEvent event) {
        super.KMI(event);
    }

    @Override
    public void caloriesCounter(ActionEvent event) {
        super.caloriesCounter(event);
    }


    public boolean emptyClientValidation(){
        if(idLabel.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Nepasirinktas klientas");
            alert.showAndWait();
            return false;
        }else return true;
    }

    public boolean emptyMealTypeValidation(){
        if(mealType.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Nepasirinkitas valgymas");
            alert.showAndWait();
            return false;
        }else return true;
    }

    public boolean clientGoalValidation(){
        try{
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM mydb.goal WHERE Client_ID = ?");
            ps.setString(1, this.idLabel.getText());
            ps.execute();
            clientGoalKg.setText("Kliento norimas svoris: -");
            clientGoal.setText("Kliento tikslas: -");
            conn.close();
        }catch (Exception e){
        }return true;
    }

    public boolean emptyMealGroupValidation(){
        if(mealGroup.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Nepasirinkta grupė");
            alert.showAndWait();
            return false;
        }else return true;
    }

    public boolean emptyDishComboValidation(){
        if(dishComboBox.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Klaida");
            alert.setHeaderText(null);
            alert.setContentText("Nepasirinktas produktas");
            alert.showAndWait();
            return false;
        }else return true;
    }
    }
