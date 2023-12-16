import Db.Dbconnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;

public class AppIntialize extends Application {
    double x;
    double y;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("view/loginForm.fxml"));
        Scene scene = new Scene(parent);

        parent.setOnMousePressed((MouseEvent event)->{
            x =event.getSceneX();
            y =event.getSceneY();
        });
        parent.setOnMouseDragged((MouseEvent e)->{
            primaryStage.setX(e.getScreenX()-x);
            primaryStage.setY(e.getScreenY()-y);
            primaryStage.setOpacity(.4);
        });
        parent.setOnMouseReleased((MouseEvent e)->{
            primaryStage.setOpacity(1);
        });
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
//https://youtu.be/GexuzbRgqVY?si=dmVE0i86qkKRuE-B