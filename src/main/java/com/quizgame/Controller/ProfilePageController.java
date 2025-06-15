package com.quizgame.Controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import com.quizgame.Player;
import com.quizgame.DAO.PlayersDAO;
import com.quizgame.Functions.SignUp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProfilePageController {

    @FXML
    private Button EditBirthdayBTN;

    @FXML
    private Button EditUserNameBTN;

    @FXML
    private DatePicker birthdayDatePicker;

    @FXML
    private Button editPhonBTN;

    @FXML
    private Button emailEditBTN;

    @FXML
    private Label emailError;

    @FXML
    private TextField emailTextField;

    @FXML
    private Button passEditBTN;

    @FXML
    private Label passError;

    @FXML
    private TextField passTextField;

    @FXML
    private Label phonError;

    @FXML
    private TextField phonTextField;

    @FXML
    private DatePicker registrationDatePicker;

    @FXML
    private Label repeatPassError;

    @FXML
    private Label repeatPassLBL;

    @FXML
    private TextField repeatPassTextField;

    @FXML
    private Label userNameError;

    @FXML
    private TextField userNameTextField;

    @FXML
    private Button MainPageBTN;

    @FXML
    private Button addQuestionBTN;
    @FXML
    private Button profileBTN;
    


    private Player player;

    void setData(Player player) {
        this.player = player;
        userNameTextField.setText(player.getUserName());
        passTextField.setText(player.getPassword());
        emailTextField.setText(player.getEmail());
        LocalDate localDate = player.getRegistrationDate().toLocalDate();
        registrationDatePicker.setValue(localDate);
        registrationDatePicker.setDisable(true);
        birthdayDatePicker.setDisable(true);
        if(player.getBirthday()!=null){
            LocalDate birthday = player.getRegistrationDate().toLocalDate();
        registrationDatePicker.setValue(birthday);
        }
    }

    @FXML
    void EditBirthday(ActionEvent event) {
        if (EditBirthdayBTN.getText().compareTo("Edit") == 0) {
            birthdayDatePicker.setDisable(false);
            birthdayDatePicker.setEditable(true);
            EditBirthdayBTN.setText("Done");
        } else {
            LocalDate selectedDate =  birthdayDatePicker.getValue();
            java.sql.Date sqlDate = java.sql.Date.valueOf(selectedDate);
            PlayersDAO.updateBirthday(player.getID(), sqlDate);
            EditBirthdayBTN.setText("Edit");
            birthdayDatePicker.setEditable(false);
        }
    }

    @FXML
    void EditUserName(ActionEvent event) {
        if (EditUserNameBTN.getText().compareTo("Edit") == 0) {
            userNameTextField.setEditable(true);
            EditUserNameBTN.setText("Done");
        } else {
            if (SignUp.checkUserName(userNameTextField.getText())) {
                PlayersDAO.updateUserName(player.getID(), userNameTextField.getText());
                userNameError.setVisible(false);
                EditUserNameBTN.setText("Edit");
                userNameTextField.setEditable(false);
            } else {
                userNameError.setVisible(true);
            }
        }

    }

    @FXML
    void editPhon(ActionEvent event) {
        if (editPhonBTN.getText().compareTo("Edit") == 0) {
            phonTextField.setEditable(true);
            editPhonBTN.setText("Done");
        }else {
            if (SignUp.checkPhonrNumber(phonTextField.getText())) {
                PlayersDAO.updatephoneNumber(player.getID(), phonTextField.getText());
                phonError.setVisible(false);
                editPhonBTN.setText("Edit");
                phonTextField.setEditable(false);
            } else {
                phonError.setVisible(true);
            }
        }

    }

    @FXML
    void emailEdit(ActionEvent event) {
        if (emailEditBTN.getText().compareTo("Edit") == 0) {
            emailTextField.setEditable(true);
            emailEditBTN.setText("Done");
        }else {
            if (SignUp.checkEmail(emailTextField.getText())) {
                PlayersDAO.updateEmail(player.getID(), emailTextField.getText());
                emailError.setVisible(false);
                emailEditBTN.setText("Edit");
                emailTextField.setEditable(false);
            } else {
                emailError.setVisible(true);
            }
        }

    }

    @FXML
    void passEdit(ActionEvent event) {
        if (passEditBTN.getText().compareTo("Edit") == 0) {
            passTextField.setEditable(true);
            passEditBTN.setText("Done");
            repeatPassLBL.setVisible(true);
            repeatPassTextField.setVisible(true);
        }else {
            if(!SignUp.checkRepeatPass(passTextField.getText(), repeatPassTextField.getText())){
                repeatPassError.setVisible(true);
            }
            else if (SignUp.checkPass(passTextField.getText()) ) {
                PlayersDAO.updatePassword(player.getID(), passTextField.getText());
                passError.setVisible(false);
                repeatPassError.setVisible(false);
                editPhonBTN.setText("Edit");
                passTextField.setEditable(false);
                repeatPassTextField.setVisible(false);
                repeatPassLBL.setVisible(false);
            } else {
                phonError.setVisible(true);
            }
        }
    }

    
    @FXML
    void goToMainPage(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(
                MainPageController.class.getResource("/com/quizgame/Controller/MainPage" + ".fxml"));
        Parent root;
        try {

            root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            MainPageController controller = fxmlLoader.getController();
            controller.setData(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToProfilePage(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(
                ProfilePageController.class.getResource("/com/quizgame/Controller/profilePage" + ".fxml"));
        Parent root;
        try {

            root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            ProfilePageController controller = fxmlLoader.getController();
            controller.setData(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToQuestionPage(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(
                ProfilePageController.class.getResource("/com/quizgame/Controller/AddQuestionPage" + ".fxml"));
        Parent root;
        try {

            root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            AddQuestionPageController controller = fxmlLoader.getController();
            controller.setData(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
