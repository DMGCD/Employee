package controller;


import TM.addEmployeeTM;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class dashFormController {
    @FXML
    private Button LogOutbtn;

    @FXML
    private AnchorPane addEmployeeForm;

    @FXML
    private Button addEmployeeNav_btn;

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
    private Button addEmployee_UpdateBtn;

    @FXML
    private Button addEmployee_addBtn;

    @FXML
    private Button addEmployee_clearBtn;


    public void btaadminnCloseOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }
}
