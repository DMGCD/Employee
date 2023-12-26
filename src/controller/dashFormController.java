package controller;


import Db.Dbconnection;
import Db.SalarrryEmployeTM;
import TM.addEmployeeTM;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ParallelCamera;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

import static java.lang.Integer.parseInt;

public class dashFormController {
    @FXML
    private TextField salary_EmpId;

    @FXML
    private Label salary_LastName;

    @FXML
    private Label salary_Position;

    @FXML
    private TextField salary_Salary;
    @FXML
    private Label salary_firstName;
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
private Image image;
    @FXML
    private TextField addEmployee_EmpId;

    @FXML
    private TextField addEmployee_FirstName;

    @FXML
    private ComboBox<String> addEmployee_Gender;

    @FXML
    private AnchorPane addEmployee_Image;

    @FXML
    private ImageView addEmployeeImageview;

    @FXML
    private Button addEmployee_ImportBtn;

    @FXML
    private TextField addEmployee_LastName;

    @FXML
    private TextField addEmployee_Phone;
    @FXML
    private TableView<SalarrryEmployeTM> salaryTableView;
    @FXML
    private ComboBox<String> addEmployee_Position;

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
    String empID;
double x;
double y;
    String getData;
    Comparable<String> empPostion;
    Comparable<String> empGender;
    double salary;

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
        addEmployee_Search.clear();
homeForm.setVisible(true);
addEmployeeForm.setVisible(false);
salaryForm.setVisible(false);
homeBtn.setStyle("-fx-background-color: #48A538");
addEmployeeNav_btn.setStyle("-fx-background-color: transparent");
salary_btn.setStyle("-fx-background-color: transparent");

    }

    public void addEmployeeNav_btnOnAction(ActionEvent actionEvent) {
addEmployee_Search.clear();
        clearFeild();
        loadAddEmployeeTable();
        empID=empIdCreator();
        addEmployee_EmpId.setText(empID);

        homeForm.setVisible(false);
        addEmployeeForm.setVisible(true);
        salaryForm.setVisible(false);

        homeBtn.setStyle("-fx-background-color: transparent");
        addEmployeeNav_btn.setStyle("-fx-background-color: #48A538");
        salary_btn.setStyle("-fx-background-color: transparent");


    }

    public void salary_btnOnAction(ActionEvent actionEvent) {
        homeForm.setVisible(false);
        addEmployeeForm.setVisible(false);
        salaryForm.setVisible(true);
        salaryTbleInsertData();
        salaryTableLoad();
        homeBtn.setStyle("-fx-background-color: transparent");
        addEmployeeNav_btn.setStyle("-fx-background-color:transparent ");
        salary_btn.setStyle("-fx-background-color: #48A538");


    }

//Salary tble load ,insert data

    public void intiDataSalaryTbale(){
        salaryTableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("empId"));
        salaryTableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("firstName"));
        salaryTableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("lastName"));
        salaryTableView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("positionEmp"));
        salaryTableView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    public void salaryTableLoad(){
        salaryTableView.getItems().clear();
        Connection connection = Dbconnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select *from salary");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String empId = resultSet.getString(2);
                String fName = resultSet.getString(3);
                String lName = resultSet.getString(4);
                String positionEmp = resultSet.getString(5);
                double salary = resultSet.getDouble(6);
                ObservableList<SalarrryEmployeTM> items = salaryTableView.getItems();
                items.add(new SalarrryEmployeTM(empId,fName,lName,positionEmp,salary));
                salaryTableView.refresh();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public void salaryTbleInsertData(){

        Connection connection = Dbconnection.getInstance().getConnection();
        Connection connection1 = Dbconnection.getInstance().getConnection();
        salary=0.00;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select *from employee");
            ResultSet resultSet = preparedStatement.executeQuery();
            PreparedStatement preparedStatement2 = connection.prepareStatement("delete from salary");
            preparedStatement2.executeUpdate();


            while(resultSet.next()){
                PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO salary(empid,firstName,lastName,position,salary)  VALUES  (?,?,?,?,?)");
                String empId = resultSet.getString(2);
                String fName = resultSet.getString(3);
                String lName = resultSet.getString(4);
                String posiEmp = resultSet.getString(7);

                preparedStatement1.setObject(1,empId);
                preparedStatement1.setObject(2,fName);
                preparedStatement1.setObject(3,lName);
                preparedStatement1.setObject(4,posiEmp);
                preparedStatement1.setObject(5,salary);
                preparedStatement1.executeUpdate();
                System.out.println(empId+fName+lName+posiEmp);
                salaryTableView.refresh();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public void salary_UpdatebtnOnAction(ActionEvent actionEvent) {
        Db
    }

    public void salary_ClearbtnOnAction(ActionEvent actionEvent) {
        salaryTableView.refresh();
        salary_Salary.clear();
        salary_firstName.setText("");
        salary_EmpId.clear();
        salary_LastName.setText("");
        salary_Position.setText("");
        salaryTableView.getSelectionModel().clearSelection();
    }
   //initialize method
    public void initialize(){
        empIdCreator();

        addEmployee_EmpId.setText(empID);
        addEmployee_EmpId.setDisable(true);
        //initialize ComboBox
        comboBoxEmpPosition();
        comboBoxEmpGEnder();
        homeForm.setVisible(true);
        addEmployeeForm.setVisible(false);
        salaryForm.setVisible(false);
       //initalize colomn for table
        initAddEmployeeTableColumns();
        intiDataSalaryTbale();
        loadAddEmployeeTable();
        //salary tble selection items get to text field
salaryTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SalarrryEmployeTM>() {
    @Override
    public void changed(ObservableValue<? extends SalarrryEmployeTM> observable, SalarrryEmployeTM oldValue, SalarrryEmployeTM newValue) {
        ObservableList<SalarrryEmployeTM> items = salaryTableView.getItems();
        SalarrryEmployeTM selectedItem = salaryTableView.getSelectionModel().getSelectedItem();
        if(items!=null &&selectedItem!=null){
            salary_EmpId.setText(selectedItem.getEmpId());
            salary_firstName.setText(selectedItem.getFirstName());
salary_LastName.setText(selectedItem.getLastName());
salary_Position.setText(selectedItem.getPositionEmp());
salary_Salary.setText(String.valueOf(selectedItem.getSalary()));
        }
        else{
            return;
        }
    }
});
// employee tble selected item get to text field
        addEmployee_TableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<addEmployeeTM>() {
            @Override
            public void changed(ObservableValue<? extends addEmployeeTM> observable, addEmployeeTM oldValue, addEmployeeTM newValue) {
                addEmployeeTM selectedItem = addEmployee_TableView.getSelectionModel().getSelectedItem();
                ObservableList<addEmployeeTM> items = addEmployee_TableView.getSelectionModel().getSelectedItems();
                if(selectedItem != null ){

empID=selectedItem.getEmpID();
                     addEmployee_EmpId.setText(empID);
                    addEmployee_FirstName.setText(selectedItem.getFirstName());
                    addEmployee_LastName.setText(selectedItem.getLastName());
                    addEmployee_Phone.setText(selectedItem.getPhoneNum());
                    String url ="file:"+ selectedItem.getImage();
                    image =new Image(url,101,97,false,true);
                    addEmployeeImageview.setImage(image);
                    getData=selectedItem.getImage();



                    addEmployee_TableView.refresh();

                }
                else{

                    addEmployee_TableView.refresh();
                    return;
                }


            }
        });
    }
    public void comboBoxEmpPosition(){

        addEmployee_Position.setItems(FXCollections.observableArrayList(position));
    }
    public void comboBoxEmpGEnder(){

        addEmployee_Gender.setItems(FXCollections.observableArrayList(gender));
    }
    private String [] position={"Web Dveloper(Front)","Web Developer(Back)","Web Developer(Full Stack)","Mobile App Developer","Web Designer","UI/Ux Desingner"};

    private String [] gender={"Male","Female","Other"};
    private  final  ObservableList<addEmployeeTM> dataListSearch =FXCollections.observableArrayList();

    public void initAddEmployeeTableColumns(){
        addEmployee_TableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("empID"));
        addEmployee_TableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("firstName"));
        addEmployee_TableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addEmployee_TableView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("gender"));
        addEmployee_TableView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        addEmployee_TableView.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("position"));
        addEmployee_TableView.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("date"));

addEmployee_TableView.setItems(dataListSearch);
searchFilterMethod();
loadAddEmployeeTable();
    }

    private void searchFilterMethod() {
FilteredList filterData=new FilteredList<>(dataListSearch,e->true);
addEmployee_Search.setOnKeyReleased(e->{
    addEmployee_Search.textProperty().addListener((observable, oldValue, newValue) ->{

        filterData.setPredicate((Predicate <? super addEmployeeTM >) cust-> {
            String lowerCaseFilter =  newValue.toLowerCase();
            if(cust.getEmpID().contains(newValue)){
                return true;
            }
            else if(cust.getFirstName().toLowerCase().contains(lowerCaseFilter)){
                return true;
            }
            else if(cust.getLastName().toLowerCase().contains(lowerCaseFilter)){
                return true;
            }
            else if(cust.getPosition().toLowerCase().contains(lowerCaseFilter)){
                return true;
            }
            else if(cust.getPhoneNum().toLowerCase().contains(lowerCaseFilter)){
                return true;
            }
            else if(cust.getGender().toLowerCase().contains(lowerCaseFilter)){
                return true;
            }

           return false;
        });

    } );
    final SortedList<addEmployeeTM> sortedData = new SortedList<>(filterData);
    sortedData.comparatorProperty().bind(addEmployee_TableView.comparatorProperty());
    addEmployee_TableView.setItems(sortedData);




});


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
                String image = resultSet.getString(8);
                Date date = resultSet.getDate(9);
                ObservableList<addEmployeeTM> items = addEmployee_TableView.getItems();
                items.add(new addEmployeeTM(empID,firstName,lastName,gender,phoneNum,position,date,image));
                addEmployee_TableView.refresh();

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void importImageOnMouseClicked(MouseEvent mouseEvent) {

        FileChooser open = new FileChooser();
        File getPath = open.showOpenDialog(mainFormRoot.getScene().getWindow());
        if(getPath!=null){
         getData = getPath.getAbsolutePath();
            image =new Image(getPath.toURI().toString(),101,97,false,true);
            addEmployeeImageview.setImage(image);
        }
    }
    public void addEmployee_GenderOnAction(ActionEvent actionEvent) {

       empGender = addEmployee_Gender.getSelectionModel().getSelectedItem();

    }


    public void addEmployee_PositionOnAction(ActionEvent actionEvent) {
         empPostion = addEmployee_Position.getSelectionModel().getSelectedItem();

    }
//add button on Emmployee Add
    public void addEmployee_addBtnOnAction(ActionEvent actionEvent) {
if(addEmployee_Position.getSelectionModel().getSelectedItem()!=null && addEmployee_Gender.getSelectionModel().getSelectedItem()!=null && addEmployee_FirstName.getText()!=null && addEmployee_LastName.getText()!=null && addEmployee_Phone.getText()!=null && addEmployee_EmpId.getText()!=null &&getData !=null && getData!=""){
    Alert alert = new Alert(Alert.AlertType.INFORMATION,"",ButtonType.OK);
    alert.setHeaderText("SuccessFul Add !");

    insertData();


    Optional<ButtonType> buttonType = alert.showAndWait();
    if(buttonType.get().equals(ButtonType.OK)){
        Alert alt = new Alert(Alert.AlertType.CONFIRMATION, "If You Want Another Employee Details! ", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType1 = alt.showAndWait();
        if(buttonType1.get().equals(ButtonType.YES)){
            clearFeild();
            empID=empIdCreator();
            addEmployee_EmpId.setText(empID);

        }
        else {
            clearFeild();
            empID=empIdCreator();
            addEmployee_EmpId.setText(empID);

        }

    }

}
else{
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText("Fill   Correct And Dont give Blanck !");
    alert.showAndWait();
}


    }
public void clearFeild(){
        addEmployee_Gender.getSelectionModel().clearSelection();
        addEmployee_Position.getSelectionModel().clearSelection();
        addEmployee_LastName.clear();
        addEmployee_Phone.clear();
        addEmployee_FirstName.clear();
        addEmployee_EmpId.clear();
        getData="";
        addEmployeeImageview.setImage(null);
}

    public void insertData(){

        Connection connection = Dbconnection.getInstance().getConnection();
        try {
            java.util.Date date = new java.util.Date();
            java.sql.Date date1=  new java.sql.Date(date.getTime());
            PreparedStatement preparedStatement = connection.prepareStatement("insert into employee( empID, firstName, lastName, gender, phoneNum, position, image, date) values (?,?,?,?,?,?,?,?)");
            preparedStatement.setObject(1,addEmployee_EmpId.getText());
            preparedStatement.setObject(2,addEmployee_FirstName.getText());
            preparedStatement.setObject(3,addEmployee_LastName.getText());
            preparedStatement.setObject(4,addEmployee_Gender.getSelectionModel().getSelectedItem());
            preparedStatement.setObject(5,addEmployee_Phone.getText());
            preparedStatement.setObject(6,addEmployee_Position.getSelectionModel().getSelectedItem());
            preparedStatement.setObject(7,getData);
            preparedStatement.setObject(8,date1);
            preparedStatement.executeUpdate();

            loadAddEmployeeTable();
            addEmployee_TableView.refresh();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void addEmployee_UpdateBtnOnAction(ActionEvent actionEvent) {

        empUpdateDetail();
        clearFeild();
    }
    // update button Details
    public void empUpdateDetail(){
        Connection connection = Dbconnection.getInstance().getConnection();
        try {
            Alert alt = new Alert(Alert.AlertType.CONFIRMATION, "If You Want Update", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alt.showAndWait();
            if(buttonType.get().equals(ButtonType.YES)){

                String url =getData;

                url =url.replace("\\","\\\\");
                java.util.Date date = new java.util.Date();
                java.sql.Date date1=  new java.sql.Date(date.getTime());
                PreparedStatement preparedStatement = connection.prepareStatement("update employee set firstName=?, lastName=?,gender=?,phoneNum=?,position=?,image=?,date=? where empID=?");
                preparedStatement.setObject(1,addEmployee_FirstName.getText());
                preparedStatement.setObject(2,addEmployee_LastName.getText());
                preparedStatement.setObject(3,addEmployee_Gender.getSelectionModel().getSelectedItem());
                preparedStatement.setObject(4,addEmployee_Phone.getText());
                preparedStatement.setObject(5,addEmployee_Position.getSelectionModel().getSelectedItem());
                preparedStatement.setObject(6,url);
                preparedStatement.setObject(7,date1);
                preparedStatement.setObject(8,addEmployee_EmpId.getText());
                preparedStatement.executeUpdate();
                loadAddEmployeeTable();
                addEmployee_TableView.refresh();
                clearFeild();
               String s = empIdCreator();
               empID=s;
               addEmployee_EmpId.setText(s);
            }
            else{
                clearFeild();


                addEmployee_EmpId.setText(empID);


            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void addEmployee_DeletebtnOnAction(ActionEvent actionEvent) {
        Connection connection = Dbconnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from employee where empID =?");
            preparedStatement.setObject(1,empID);
            int i = preparedStatement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Delete Successfull",ButtonType.OK);

            Optional<ButtonType> buttonType = alert.showAndWait();
            if(buttonType.get().equals(ButtonType.OK)){
                clearFeild();
                loadAddEmployeeTable();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public String empIdCreator(){

        Connection connection = Dbconnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select empID from employee order by empID desc limit 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String empIDS =resultSet.getString(1);
                empIDS=empIDS.substring(1,empIDS.length());
               int empIDI = Integer.parseInt(empIDS);
                empIDI++;
                if(empIDI<10){
                    empIDS="E00"+empIDI;
                }
                else if(empIDI<100){
                    empIDS="E0"+empIDI;
                }
                else {
                    empIDS="E"+empIDI;
                }
                empID=empIDS;
                return empID;

            }
            else{
                empID="E001";
                return empID;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void addEmployee_clearBtnOnAction(ActionEvent actionEvent) {

        clearFeild();
        Comparable<String> s = empIdCreator();
        addEmployee_EmpId.setText((String) s);

    }


}
