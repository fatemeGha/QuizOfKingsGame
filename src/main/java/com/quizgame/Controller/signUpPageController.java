package com.quizgame.Controller;
import com.quizgame.DAO.PlayersDAO;
import com.quizgame.Functions.*;
import java.io.IOException;

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


public class signUpPageController {
    @FXML
    private Label EmailError;

    @FXML
    private TextField EmailTextField;

    @FXML
    private Button LoginButton;

    @FXML
    private Label PassError;

    @FXML
    private TextField PassTextField;

    @FXML
    private Label RepeatPassEror;

    @FXML
    private Label RepeatPassError;

    @FXML
    private TextField RepeatPassTextField;

    @FXML
    private Button SignUpButton;

    @FXML
    private Label UserNameError;

    @FXML
    private TextField UserNameTextField;

    @FXML
    void Login(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(signUpPageController.class.getResource("/com/quizgame/Controller/LoginPage" + ".fxml"));
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

    @FXML
    void SignUp(ActionEvent event) {
        String email = EmailTextField.getText() ;
        String userName = UserNameTextField.getText() ;
        String pass = PassTextField.getText();
        String repeatPass = RepeatPassTextField.getText();
        if(SignUp.checkEmail(email) == false){
            EmailError.setVisible(true);
        } else if(SignUp.checkPass(pass) == false){
            PassError.setVisible(true);
        }else if(SignUp.checkUserName(userName) == false){
            UserNameError.setVisible(true);
     
        }else if(SignUp.checkRepeatPass(pass , repeatPass) == false){
        RepeatPassError.setVisible(true);
        } else{
                PlayersDAO.insert(userName, email, pass);
            
        }
    }
    }


