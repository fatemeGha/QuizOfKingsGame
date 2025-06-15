package com.quizgame.Controller;

import javafx.event.ActionEvent;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.quizgame.Match;
import com.quizgame.Player;
import com.quizgame.Question;
import com.quizgame.Round;
import com.quizgame.DAO.*;

public class MatchPageController {

    @FXML
    private Label player1Name;
    @FXML
    private Label player2Name;
    @FXML
    private Label player1Score;
    @FXML
    private Label player2Score;
    @FXML
    private ImageView player1Avatar;
    @FXML
    private ImageView player2Avatar;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button backBTN;

    private Match match;
    private Player player1; /// me
    private Player player2;
    private int playerNumber;
    private int otherPlayerNumber;
    private ArrayList<Round> rounds;
    private int player1ScoreNumber;
    private int player2ScoreNumber;
    private Round currentRound ;

    void setData(Match match, Player player1, Player player2, int playerNumber) {
        this.match = match;
        if (playerNumber == 1) {
            otherPlayerNumber = 2;
            this.player1ScoreNumber = match.getPlayer1Score();
            this.player2ScoreNumber = match.getPlayer2Score();
            this.player1 = player1;
            this.player2 = player2;
        }
        if (playerNumber == 2) {
            otherPlayerNumber = 1;
            this.player1ScoreNumber = match.getPlayer2Score();
            this.player2ScoreNumber = match.getPlayer1Score();
            this.player2 = player1; // fafa
            this.player1 = player2; // far
        }
        this.playerNumber = playerNumber;
        System.out.println(playerNumber);
        show();
    }

    public void show() {

        player1Name.setText(player1.getUserName());
        player2Name.setText(player2.getUserName());
        player1Score.setText(String.valueOf(this.player1ScoreNumber));
        player2Score.setText(String.valueOf(this.player2ScoreNumber));

        VBox roundsVBox = new VBox();
        roundsVBox.setSpacing(10.0);
        scrollPane.setContent(roundsVBox);
        roundsVBox.setMaxWidth(Double.MAX_VALUE);
        scrollPane.setFitToWidth(true);

        player1Avatar.setImage(new Image(getClass().getResource("/images/" + player1.getAvatarURL()).toExternalForm()));
        player2Avatar.setImage(new Image(getClass().getResource("/images/" + player2.getAvatarURL()).toExternalForm()));

        ArrayList<Round> rounds = RoundsDAO.getAllByMatchID(match.getMatchID());
        int nRounds = rounds.size();
        this.rounds = rounds;
        HBox[] HBoxs = new HBox[10];
        for (int i = 0; i < 10; i++) {
            HBoxs[i] = new HBox();
            HBoxs[i].setSpacing(5.0);
            roundsVBox.getChildren().add(HBoxs[i]);
        }

        for (int i = 0; i < nRounds; i++) {
            HBoxs[i].setAlignment(Pos.CENTER);
            VBox vBoxCategory = new VBox();
            vBoxCategory.setAlignment(Pos.CENTER);
            ImageView imageView = new ImageView(
                    new Image(
                            getClass().getResource("/images/" + rounds.get(i).getCategory().getIconImage())
                                    .toExternalForm()));
            imageView.setFitHeight(50.0);
            imageView.setFitWidth(50.0);
            vBoxCategory.getChildren().add(imageView);
            vBoxCategory.getChildren().add(new Label(rounds.get(i).getCategory().getCategoryName()));

            Question q1 = rounds.get(i).getQ1();
            Question q2 = rounds.get(i).getQ2();
            Question q3 = rounds.get(i).getQ3();

            Circle circle1 = new Circle(20);
            Circle circle2 = new Circle(20);
            Circle circle3 = new Circle(20);
            Circle circle4 = new Circle(20);
            Circle circle5 = new Circle(20);
            Circle circle6 = new Circle(20);

            if ((!rounds.get(i).getPlayer1Done() && rounds.get(i).getPlayer2Done() && playerNumber == 1)
                    || (!rounds.get(i).getPlayer2Done() && rounds.get(i).getPlayer1Done() && playerNumber == 2)) {

                circle1.setFill(Color.WHITE);
                circle2.setFill(Color.WHITE);
                circle3.setFill(Color.WHITE);

                HBoxs[i].getChildren().addAll(circle1, circle2, circle3, vBoxCategory);
                showRectAngle(i, HBoxs[i]);

            } else {
                circle1.setFill(
                        q1.getCorrect_option().compareTo(rounds.get(i).getPlayerQ1(playerNumber)) == 0 ? Color.GREEN
                                : Color.RED);
                circle2.setFill(
                        q2.getCorrect_option().compareTo(rounds.get(i).getPlayerQ2(playerNumber)) == 0 ? Color.GREEN
                                : Color.RED);
                circle3.setFill(
                        q3.getCorrect_option().compareTo(rounds.get(i).getPlayerQ3(playerNumber)) == 0 ? Color.GREEN
                                : Color.RED);

                HBoxs[i].getChildren().addAll(circle1, circle2, circle3, vBoxCategory);

                if (playerNumber == 1 && !rounds.get(i).getPlayer2Done()
                        || playerNumber == 2 && !rounds.get(i).getPlayer1Done()) {
                    showRectAngle(0, HBoxs[i]);

                } else {
                    circle4.setFill(q1.getCorrect_option().compareTo(rounds.get(i).getPlayerQ1(otherPlayerNumber)) == 0
                            ? Color.GREEN
                            : Color.RED);
                    circle5.setFill(q2.getCorrect_option().compareTo(rounds.get(i).getPlayerQ2(otherPlayerNumber)) == 0
                            ? Color.GREEN
                            : Color.RED);
                    circle6.setFill(q3.getCorrect_option().compareTo(rounds.get(i).getPlayerQ3(otherPlayerNumber)) == 0
                            ? Color.GREEN
                            : Color.RED);

                    HBoxs[i].getChildren().addAll(circle4, circle5, circle6);

                    System.out.println("HBoxs[i].getChildren().add(circle4)");
                }
            }
        }

        System.out.println("URL rad shod" + match.getMatchID());

    }

    HBox showRectAngle(int i, HBox hbox) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(130);
        rectangle.setHeight(40);
        rectangle.setArcWidth(10);
        rectangle.setArcHeight(10);
        rectangle.setFill(Color.LIGHTBLUE);
        Text text;
        if (i == 0) {
            text = new Text("wating for next player");
        } else {
            text = new Text("Hidden");
        }
        text.setFill(Color.DARKBLUE);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(rectangle, text);
        hbox.getChildren().add(stack);
        return hbox;
    }

    @FXML
    private void handlePlayAgain(ActionEvent event) {
        for(Round round : rounds){
            if((!round.getPlayer1Done() && round.getPlayer2Done() && playerNumber == 1)
            || (!round.getPlayer2Done() && round.getPlayer1Done() && playerNumber == 2)){
                    currentRound = round;
                    showQuestionPage(event);
                    return;
            }
        }
        showCategoryPage(event);
    }

    void showCategoryPage(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(chooseCategoryPageController.class
                .getResource("/com/quizgame/Controller/chooseCategoryPage" + ".fxml"));
        Parent root;
        try {
            root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            chooseCategoryPageController controller = fxmlLoader.getController();
            controller.setData(playerNumber, player1, match, rounds.size() + 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void showQuestionPage(ActionEvent event) {
  

        FXMLLoader fxmlLoader = new FXMLLoader(QuestionPageController.class
                .getResource("/com/quizgame/Controller/QuestionPage" + ".fxml"));
        Parent root;
        try {
            root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            QuestionPageController controller = fxmlLoader.getController();
            ArrayList<Question> questions = new ArrayList<>(Arrays.asList(null, currentRound.getQ1(), currentRound.getQ2(), currentRound.getQ3()));
            controller.setData(currentRound.getQ1() , currentRound , match , 1 , questions ,player1 , playerNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void back(ActionEvent event) {
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
            controller.setData(player1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}