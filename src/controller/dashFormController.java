package controller;


import Db.Dbconnection;
import TM.addEmployeeTM;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ParallelCamera;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class dashFormController {
  //logout
    @FXML
    private Button LogOutbtn;
//anchor pane set
    @FXML
    private AnchorPane addEmployeeForm;
    @FXML
    private AnchorPane salaryForm;
    @FXML
    private AnchorPane homeForm;

    //btn set of nav bar
    @FXML
    private Button addEmployeeNav_btn;

    @FXML
    private Button homeBtn;

    @FXML
    private Button salary_btn;

    //************************
    @FXML
    private Button addEmployee_Deletebtn;

    @FXML
    private TextField addEmployee_EmpId;

    @FXML
    private TextField addEmployee_FirstName;

    @FXML
    private ComboBox<?> addEmployee_Gender;

    @FXML
    private AnchorPane addEmployee_Image;

    @FXML
    private Button addEmployee_ImportBtn;

    @FXML
    private TextField addEmployee_LastName;

    @FXML
    private TextField addEmployee_Phone;

    @FXML
    private ComboBox<?> addEmployee_Position;

    @FXML
    private TextField addEmployee_Search;

    @FXML
    private TableView<addEmployeeTM> addEmployee_TableView;
    @FXML
    private AnchorPane mainFormRoot;

    @FXML
    private Button addEmployee_UpdateBtn;

    @FXML
    private Button addEmployee_addBtn;

    @FXML
    private Button addEmployee_clearBtn;
double x;
double y;

    public void btaadminnCloseOnAction(ActionEvent actionEvent) {

        System.exit(0);
    }

    public void minimizeBtnOnAction(ActionEvent actionEvent) {
        Stage stage=(Stage)mainFormRoot.getScene().getWindow();
        stage.setIconified(true);

    }
    @FXML
    void LogOutbtnOnAcction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"",ButtonType.NO,ButtonType.YES);

        alert.setHeaderText("You Are LOgOUt From The System!");

        Optional<ButtonType> btn = alert.showAndWait();
if(btn.get().equals(ButtonType.YES)){
    Parent parent = FXMLLoader.load(this.getClass().getResource("../view/loginForm.fxml"));
    Scene scene = new Scene(parent);
    Stage stage = new Stage();
    parent.setOnMousePressed((MouseEvent e)->{
        x=e.getSceneX();
        y=e.getSceneY();

    });
    parent.setOnMouseDragged((MouseEvent e)->{
        stage.setX(e.getScreenX()-x);
        stage.setY(e.getScreenY()-y);
        stage.setOpacity(.6);
    });
    parent.setOnMouseReleased((MouseEvent e)->{
        stage.setOpacity(1);
    });
    stage.initStyle(StageStyle.TRANSPARENT);
    stage.setScene(scene);
    stage.centerOnScreen();
    stage.show();
    mainFormRoot.getScene().getWindow().hide();


}
else{

}
    }

    public void homeBtnOnAction(ActionEvent actionEvent) {
homeForm.setVisible(true);
addEmployeeForm.setVisible(false);
salaryForm.setVisible(false);
homeBtn.setStyle("-fx-background-color: #48A538");
addEmployeeNav_btn.setStyle("-fx-background-color: transparent");
salary_btn.setStyle("-fx-background-color: transparent");
//        try {
//            Thread.sleep(300);
//            homeBtn.setStyle("-fx-background-color: transparent");
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void addEmployeeNav_btnOnAction(ActionEvent actionEvent) {
        loadAddEmployeeTable();
        homeForm.setVisible(false);
        addEmployeeForm.setVisible(true);
        salaryForm.setVisible(false);
        homeBtn.setStyle("-fx-background-color: transparent");
        addEmployeeNav_btn.setStyle("-fx-background-color: #48A538");
        salary_btn.setStyle("-fx-background-color: transparent");

//        try {
//            Thread.sleep(300);
//            addEmployeeNav_btn.setStyle("-fx-background-color: transparent");
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void salary_btnOnAction(ActionEvent actionEvent) {
        homeForm.setVisible(false);
        addEmployeeForm.setVisible(false);
        salaryForm.setVisible(true);

        homeBtn.setStyle("-fx-background-color: transparent");
        addEmployeeNav_btn.setStyle("-fx-background-color:transparent ");
        salary_btn.setStyle("-fx-background-color: #48A538");

//        try {
//            Thread.sleep(300);
//            salary_btn.setStyle("-fx-background-color: transparent");
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }
   //initialize method
    public void initialize(){
        homeForm.setVisible(true);
        addEmployeeForm.setVisible(false);
        salaryForm.setVisible(false);
        initAddEmployeeTableColumns();
        loadAddEmployeeTable();
        addEmployee_TableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<addEmployeeTM>() {
            @Override
            public void changed(ObservableValue<? extends addEmployeeTM> observable, addEmployeeTM oldValue, addEmployeeTM newValue) {
                ObservableList<addEmployeeTM> itemSelect = addEmployee_TableView.getSelectionModel().getSelectedItems();
                if(itemSelect != null){
                    addEmployee_TableView.refresh();

                }
                else{
                    addEmployee_TableView.getSelectionModel().getSelectedItems().clear();
                    addEmployee_TableView.refresh();
                    return;
                }


            }
        });
    }

    public void initAddEmployeeTableColumns(){
        addEmployee_TableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("empID"));
        addEmployee_TableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("firstName"));
        addEmployee_TableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addEmployee_TableView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("gender"));
        addEmployee_TableView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        addEmployee_TableView.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("position"));
        addEmployee_TableView.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("date"));

    }
    public void loadAddEmployeeTable(){
        addEmployee_TableView.getItems().clear();
        Connection connection = Dbconnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select *from employee");
            while (resultSet.next()){
                String empID = resultSet.getString(2);
                String firstName = resultSet.getString(3);
                String lastName = resultSet.getString(4);
                String gender = resultSet.getString(5);
                String phoneNum = resultSet.getString(6);
                String position = resultSet.getString(7);
                Date date = resultSet.getDate(9);
                ObservableList<addEmployeeTM> items = addEmployee_TableView.getItems();
                items.add(new addEmployeeTM(empID,firstName,lastName,gender,phoneNum,position,date));
                addEmployee_TableView.refresh();

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
