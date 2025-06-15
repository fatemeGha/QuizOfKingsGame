package com.quizgame.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import com.quizgame.Functions.*;

public class LoginPageController {
   
    public LoginPageController(){

    }
    
    @FXML
    private Button LoginBTN;

    @FXML
    private Label PassLable;

    @FXML
    private TextField PassTextField;

    @FXML
    private Label PasswordError;

    @FXML
    private Button SignUpBTN;

    @FXML
    private Label UserNameError;

    @FXML
    private Label UserNameLable;

    @FXML
    private TextField UserNameTextField;

   

    @FXML
    void Login(ActionEvent event) {
        String username = UserNameTextField.getText();
        String password = PassTextField.getText();
        Login login = new Login(username , password);
        if(!login.checkLogin){
        UserNameError.setVisible(true);
        PasswordError.setVisible(true);
        }
        else{
       
        FXMLLoader fxmlLoader = new FXMLLoader(MainPageController.class.getResource("/com/quizgame/Controller/MainPage" + ".fxml"));
        Parent root;
        try {
            
            root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            MainPageController controller = fxmlLoader.getController();
            controller.setData(login.player);
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

    }

    @FXML
    void SignUp(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(signUpPageController.class.getResource("/com/quizgame/Controller/signUpPage" + ".fxml"));
        Parent root;
        try {
            root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }



}
}
