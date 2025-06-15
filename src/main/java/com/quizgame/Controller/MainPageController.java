package com.quizgame.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.quizgame.Match;
import com.quizgame.Player;
import com.quizgame.Question;
import com.quizgame.Round;
import com.quizgame.DAO.*;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainPageController {

    Player player;
    @FXML
    private ScrollPane ScrollPaneMatches;
    @FXML
    private Button startBTN;

    public void setData(Player player) {
        this.player = player;
        show();
    }

    void show() {
        ScrollPaneMatches.setFitToWidth(true);
        ScrollPaneMatches.setFitToHeight(true);
        ScrollPaneMatches.setPannable(true);
        VBox vboxMatch = new VBox();
        vboxMatch.setSpacing(10.0);
        vboxMatch.setAlignment(Pos.CENTER);
        ScrollPaneMatches.setContent(vboxMatch);
        ArrayList<Match> matchs = MatchesDAO.getAllByPlayerID(player.getID());
        int nMatch = matchs.size();
        HBox[] hBoxs = new HBox[nMatch];

        for (int i = 0; i < nMatch; i++) {
            Player anotherPlayer = matchs.get(i).getAnotherPlayer(player.getID());
            ImageView imageView = new ImageView(
                    new Image(getClass().getResource("/images/" + anotherPlayer.getAvatarURL()).toExternalForm()));
            imageView.setFitHeight(50.0);
            imageView.setFitWidth(50.0);
            Label playerName = new Label(anotherPlayer.getUserName());
            hBoxs[i] = new HBox(imageView, playerName);
            hBoxs[i].setSpacing(5.0);
            hBoxs[i].setAlignment(Pos.CENTER);
            hBoxs[i].setStyle("-fx-background-color: lightblue;");
            int iFinal = i;
            hBoxs[i].setOnMouseClicked(event -> {
                showMatchPage(matchs.get(iFinal) , event);
            });
        }
        vboxMatch.getChildren().addAll(hBoxs);

    }

    void showMatchPage(Match match , Event event) {
        FXMLLoader fxmlLoader = new FXMLLoader(QuestionPageController.class
                .getResource("/com/quizgame/Controller/MatchPage" + ".fxml"));
        Parent root;
        try {
            root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            MatchPageController controller = fxmlLoader.getController();
            int playerNumber = match.getPlayerNumber(player.getID());

            

            if (playerNumber == 1 && match.getPlayer2()== null) {
                controller.setData(match, player, new Player(), playerNumber);
            }if (playerNumber == 1 && match.getPlayer2() != null) {
                controller.setData(match, player, match.getPlayer2(), playerNumber);
            }  else if (playerNumber == 2) {
                controller.setData( match, match.getPlayer1(), player, playerNumber);

            }
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    @FXML
    void Start(ActionEvent event) {
        ArrayList<Match> matchs = MatchesDAO.getAll();
        for (Match match : matchs) {
            if (match.getStatus().compareTo("openForJoin") == 0 && player.getID() != match.getPlayer1().getID()) {
                MatchesDAO.updateToActive(match.getMatchID(), player.getID());
                ArrayList<Round> rounds = RoundsDAO.getAllByMatchID(match.getMatchID()); /// almost 1 round
                ArrayList<Question> questions = new ArrayList<>(Collections.nCopies(5, null));
                questions.add(1, rounds.get(0).getQ1());
                questions.add(2, rounds.get(0).getQ2());
                questions.add(3, rounds.get(0).getQ3());
                FXMLLoader fxmlLoader = new FXMLLoader(QuestionPageController.class
                        .getResource("/com/quizgame/Controller/questionPage" + ".fxml"));
                Parent root;
                try {

                    root = fxmlLoader.load();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    QuestionPageController controller = fxmlLoader.getController();
                    controller.setData(questions.get(1), rounds.get(0), match, 1, questions, player, 2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }

        Match match = MatchesDAO.insert(player.getID(), "openForJoin");
        FXMLLoader fxmlLoader = new FXMLLoader(chooseCategoryPageController.class
                .getResource("/com/quizgame/Controller/chooseCategoryPage" + ".fxml"));
                System.out.println("match ID = " + match.getMatchID() + " player1ID =  " + match.getPlayer1().getID());
        Parent root;
        try {

            root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            chooseCategoryPageController controller = fxmlLoader.getController();
            controller.setData(1 , player, match, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void function1(ActionEvent event) {

    }

    @FXML
    void function2(ActionEvent event) {

    }

    @FXML
    void function3(ActionEvent event) {
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

    @FXML
    void function4(ActionEvent event) {
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

}
