package com.quizgame.Controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.quizgame.Category;
import com.quizgame.Match;
import com.quizgame.Player;
import com.quizgame.Question;
import com.quizgame.Round;
import com.quizgame.DAO.*;

import java.io.IOException;

public class chooseCategoryPageController {

    @FXML
    private AnchorPane circlePane;
    private Player player;
    private Match match;
    public Category category;
    private int roundNumber;
    private int playerNumber;

    private final double centerX = 200;
    private final double centerY = 200;
    private final double radius = 150;

    public void setData(int playerNumber ,Player player, Match match , int roundNumber ) {
        this.playerNumber = playerNumber;
        this.roundNumber = roundNumber;
        this.player = player;
        this.match = match;
    }

    public void initialize() {
        ArrayList<Category> categories = CategoriesDAO.getAll();
        Collections.shuffle(categories);
        drawCircleParts(List.of(categories.get(0).getCategoryName(), categories.get(1).getCategoryName(),
                categories.get(2).getCategoryName()));
    }

    public void drawCircleParts(List<String> labels) {
        List<Color> colors = new ArrayList<>(List.of(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE,
                Color.PURPLE, Color.CYAN, Color.PINK, Color.BROWN, Color.LIME,
                Color.DARKBLUE, Color.DARKRED, Color.DARKGREEN, Color.GOLD,
                Color.MAGENTA, Color.DARKORANGE, Color.TEAL, Color.DARKVIOLET));
        Collections.shuffle(colors);

        for (int i = 0; i < 3; i++) {
            double startAngle = 90 + i * 120;
            double length = 120;

            Arc arc = new Arc(centerX, centerY, radius, radius, startAngle, length);
            arc.setType(ArcType.ROUND);
            arc.setFill(colors.get(i));
            arc.setStroke(Color.BLACK);
            arc.setStrokeWidth(2);

            circlePane.getChildren().add(arc);

            double midAngle = Math.toRadians(startAngle + length / 2);
            double textRadius = radius * 0.6; 

            double textX = centerX + textRadius * Math.cos(midAngle) - 20;
            double textY = centerY - textRadius * Math.sin(midAngle);

            Text label = new Text(textX, textY, labels.get(i));
            label.setFill(Color.WHITE);
            label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
            circlePane.getChildren().add(label);

            final int index = i;
            label.setOnMouseClicked(event -> {
                showQuestionPage(event , label);
            });
            arc.setOnMouseClicked(event -> {
                showQuestionPage(event , label);
            });
        }
    }

    void showQuestionPage(Event event , Text label){
        category = CategoriesDAO.getByName( label.getText());
        ArrayList<Question> questions = QuestionsDAO.getAllByCategoryID(category.getCategoryID());
        Collections.shuffle(questions);
        Round round = RoundsDAO.insert(match.getMatchID(), category.getCategoryID(),
                roundNumber, questions.get(1).getQuestion_id(), questions.get(2).getQuestion_id(),
                questions.get(3).getQuestion_id());
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
            controller.setData(questions.get(1) , round,match , 1 , questions , player , playerNumber);
            System.out.println(round.getID());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
