package com.quizgame.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static Scene scene;
    
    ////// emtiaz dar halat hidden malum nabashe
    ///  barresi soal dokme
    /// barresi soal anjam
    /// tanzim avatar
    /// jostojo karbar
    /// dour haye jadid online ezafe beshe
    /// namayesh jadval
    /// emtiaz har nafar
    /// index gozari
    /// sakhti soal entekhab
    /// sakhti soal tanzimat
    /// hazf karbar
    /// 
    /// 
    /////  ta raghib bazi nakarde man natunam dor bad ro shro konam
    //////// edit tarikh
    ////// didan soal ha pazirofte ya na 
    ////// /// sakht soal 
    /// ////// zamane soala


    @Override
    public void start(Stage stage) throws Exception {
        

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/quizgame/Controller/LoginPage" + ".fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        


    }

    public static void main(String[] args) throws IOException {
        launch();
    }
}







/*
java --module-path "C:\Program Files (x86)\javafx-sdk-21.0.7\lib" --add-modules javafx.controls,javafx.fxml -cp target/classes com.quizgame.Controller.App

*/

