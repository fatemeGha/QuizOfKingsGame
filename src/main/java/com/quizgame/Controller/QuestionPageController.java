package com.quizgame.Controller;

import java.io.IOException;
import java.util.ArrayList;

import com.quizgame.Match;
import com.quizgame.Player;
import com.quizgame.Question;
import com.quizgame.Round;
import com.quizgame.DAO.RoundsDAO;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class QuestionPageController {

    @FXML
    private Button OptionA;

    @FXML
    private Button OptionB;

    @FXML
    private Button OptionC;

    @FXML
    private Button OptionD;

    @FXML
    private Label QuestionText;

    @FXML
    private VBox VboxContent;

    @FXML
    private HBox hboxT;

    private final double TOTAL_TIME = 10;
    private Question question;
    private Round round;
    private Match match;
    int qNumber;
    Player player;
    ArrayList<Question> questions;
    int playerNumber;

    void setData(Question question, Round round, Match match, int qNumber, ArrayList<Question> questions,
            Player player, int playerNumber) {
        this.question = question;
        this.match = match;
        this.round = round;
        this.qNumber = qNumber;
        this.questions = questions;
        this.player = player;
        this.playerNumber = playerNumber;
        System.out.println(round.getPlayer1Done() + "round.getPlayer1Done()");
        System.out.println(round.getPlayer2Done() + "round.getPlayer2Done()");
        QuestionText.setText(question.getQuestion_text());
        OptionA.setText(question.getOption_a());
        OptionB.setText(question.getOption_b());
        OptionC.setText(question.getOption_c());
        OptionD.setText(question.getOption_d());
        showTime();
    }

    void showTime() {

        Rectangle backgroundBar = new Rectangle(300, 30);
        backgroundBar.setArcWidth(20);
        backgroundBar.setArcHeight(20);
        backgroundBar.setFill(Color.web("#FFFFFF"));

        Rectangle timerBar = new Rectangle(300, 30);
        timerBar.setArcWidth(20);
        timerBar.setArcHeight(20);
        timerBar.setFill(new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#66ff99")),
                new Stop(1, Color.web("#00cc66"))));

        StackPane timerPane = new StackPane(backgroundBar, timerBar);
        timerPane.setPrefSize(300, 30);
        StackPane.setAlignment(timerBar, Pos.CENTER_LEFT);

        Label answerLabel = new Label("");
        answerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18;");

        hboxT.getChildren().addAll(timerPane, answerLabel);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(timerBar.widthProperty(), 300)),
                new KeyFrame(Duration.seconds(TOTAL_TIME), new KeyValue(timerBar.widthProperty(), 0)));

        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(0.1), e -> {
                    AnimationTimer colorUpdater = new AnimationTimer() {
                        @Override
                        public void handle(long now) {
                            double progress = timerBar.getWidth() / 300.0;
                            updateColor(timerBar, progress);
                        }
                    };
                    colorUpdater.start();
                }));

        timeline.setOnFinished(e -> {
            submitOption(question.getCorrect_option().compareTo("A") == 0 ? "B" : "A" , e, 
            question.getCorrect_option().compareTo("A") == 0 ? OptionA : 
            question.getCorrect_option().compareTo("B") == 0 ? OptionB : 
            question.getCorrect_option().compareTo("A") == 0 ? OptionC :
            OptionD, false);
        });

        timeline.play();

    }

    private void updateColor(Rectangle rect, double progress) {
        if (progress > 0.5) {
            rect.setFill(new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.web("#66ff99")),
                    new Stop(1, Color.web("#00cc66"))));
        } else {
            rect.setFill(new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.web("#ff6666")),
                    new Stop(1, Color.web("#cc0000"))));
        }
    }

    @FXML
    void OptionAClicked(ActionEvent event) {
        submitOption("A", event, OptionA, true);
    }

    @FXML
    void OptionBClicked(ActionEvent event) {
        submitOption("B", event, OptionB, true);
    }

    @FXML
    void OptionCClicked(ActionEvent event) {
        submitOption("C", event, OptionC, true);
    }

    @FXML
    void OptionDClicked(ActionEvent event) {
        submitOption("D", event, OptionD, true);
    }

    void submitOption(String option, Event eventOption, Button optionBTN, boolean isInTime) {
        if (isInTime) {
            showOptionIsCorrect(option, eventOption, optionBTN);
        }else{
            showOptionIsCorrect(question.getCorrect_option(), eventOption, optionBTN); 
        }
        switch (qNumber) {
            case 1:
                round.setPlayerQ1(playerNumber, option);
                if (playerNumber == 1) {
                    if (round.getQ1().getCorrect_option().compareTo(option) == 0) {
                        match.addPlayer1Score();
                    }
                } else {
                    if (round.getQ1().getCorrect_option().compareTo(option) == 0) {
                        match.addPlayer2Score();
                    }
                }
                break;
            case 2:
                round.setPlayerQ2(playerNumber, option);

                if (playerNumber == 1) {
                    if (round.getQ2().getCorrect_option().compareTo(option) == 0) {
                        match.addPlayer1Score();
                    }
                } else {
                    if (round.getQ2().getCorrect_option().compareTo(option) == 0) {
                        match.addPlayer2Score();
                    }
                }
                break;
            case 3:
                round.setPlayerQ3(playerNumber, option);
                if (playerNumber == 1) {
                    if (round.getQ3().getCorrect_option().compareTo(option) == 0) {
                        match.addPlayer1Score();
                        round.setPlayer1Done(true);
                    }
                } else {
                    if (round.getQ3().getCorrect_option().compareTo(option) == 0) {
                        match.addPlayer2Score();
                        round.setPlayer2Done(true);
                    }
                }
                break;

            default:
                break;
        }

    }

    void showOptionIsCorrect(String option, Event eventOption, Button optionBTN) {
        if (question.getCorrect_option().compareTo(option) == 0) {
            optionBTN.setStyle("-fx-background-color: green;");
        } else {
            optionBTN.setStyle("-fx-background-color:  red;");
        }
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            showNextQuestion(optionBTN);
        });
        pause.play();
    }

    void showNextQuestion(Button button) {
        if (qNumber < 3) {

            FXMLLoader fxmlLoader = new FXMLLoader(QuestionPageController.class
                    .getResource("/com/quizgame/Controller/questionPage" + ".fxml"));
            Parent root;
            try {

                root = fxmlLoader.load();
                Stage stage = (Stage) button.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                QuestionPageController controller = fxmlLoader.getController();
                controller.setData(questions.get(qNumber + 1), round, match, qNumber + 1, questions, player,
                        playerNumber);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            RoundsDAO.updatePlayerAnswer(playerNumber, round.getID(), round.getPlayerQ1(playerNumber),
                    round.getPlayerQ2(playerNumber), round.getPlayerQ3(playerNumber));
            match.updatePlayer1Score(match.getPlayer1Score());
            match.updatePlayer2Score(match.getPlayer2Score());
            System.out.println("playerID = " + player.getID());
            FXMLLoader fxmlLoader = new FXMLLoader(QuestionPageController.class
                    .getResource("/com/quizgame/Controller/MatchPage" + ".fxml"));
            Parent root;
            try {
                root = fxmlLoader.load();
                Stage stage = (Stage) button.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                MatchPageController controller = fxmlLoader.getController();
                System.out.println("player number = " + playerNumber + "  round Number = " + round.getRound_number());
                if (playerNumber == 1 && round.getRound_number() == 1) {
                    controller.setData(match, player, new Player(), playerNumber);
                } else if (playerNumber == 1 && round.getRound_number() != 1) {
                    controller.setData(match, match.getPlayer2(), match.getPlayer2(), playerNumber);
                } else if (playerNumber == 2) {
                    controller.setData(match, match.getPlayer1(), player, playerNumber);
                }
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
