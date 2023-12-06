package controller;


import Db.Dbconnection;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class loginFormController {
    double x =0;
    double y=0;
    @FXML
    private Button btnClosed;
    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;


    public void btnCloseOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void txtUserNameOnAction(ActionEvent actionEvent) {
        if(!txtUserName.getText().isEmpty()){
            txtPassword.requestFocus();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Massege!");
            alert.setHeaderText(null);
            alert.setContentText("Fill the UserName");
            alert.showAndWait();
        }


    }

    public void txtPasswordOnAction(ActionEvent actionEvent) {
        if(!txtPassword.getText().isEmpty()){
            loginToEmployeeDashBoard();
            txtUserName.clear();
            txtPassword.clear();
        }

    }
    public void loginToEmployeeDashBoard(){



        String passworrdL = txtPassword.getText();
        String userNameL = txtUserName.getText();
        Connection connection = Dbconnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select *from admin where password=? and username=?");
            preparedStatement.setObject(1,passworrdL);
            preparedStatement.setObject(2,userNameL);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                btnLogin.getScene().getWindow().hide();


                Parent parent = FXMLLoader.load(this.getClass().getResource("../view/dashForm.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = new Stage();

                parent.setOnMousePressed((MouseEvent e)->{

                    x= e.getSceneX();
                    y=e.getSceneY();

                });
                parent.setOnMouseDragged((MouseEvent e)->{
                    stage.setX(e.getScreenX()-x);
                    stage.setY(e.getScreenY()-y);
                    stage.setOpacity(.4);
                });
                parent.setOnMouseReleased((MouseEvent e)->{
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            }
            else{
                txtUserName.clear();
                txtPassword.clear();
                txtUserName.requestFocus();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Wrong Password Or User Name");
                alert.showAndWait();

            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void btnLoginOnAction(ActionEvent actionEvent) {
        loginToEmployeeDashBoard();
        txtUserName.clear();
        txtPassword.clear();
    }
}
