package com.quizgame.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.quizgame.*;
import com.quizgame.DAO.CategoriesDAO;
import com.quizgame.DAO.QuestionsDAO;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AddQuestionPageController {

    private Player player;

    @FXML
    private Button MainPageBTN;

    @FXML
    private Button QuestionBTN;

    @FXML
    private HBox hboxbuttons;

    @FXML
    private Button profileBTN;

    @FXML
    private ScrollPane scrollPaneContent;

    private int categoryID;

    public void setData(Player player) {
        this.player = player;
        show();
    }

    void show() {
        Button allBTN = new Button("show all questions created");
        allBTN.setMinHeight(hboxbuttons.getPrefHeight());
        Button acceptedBTN = new Button("show accepted questions");
        acceptedBTN.setMinHeight(hboxbuttons.getPrefHeight());
        Button unacceptedBTN = new Button("show unaccepted questions");
        unacceptedBTN.setMinHeight(hboxbuttons.getPrefHeight());
        Button creatBTN = new Button("creat new question");
        creatBTN.setMinHeight(hboxbuttons.getPrefHeight());

        hboxbuttons.getChildren().addAll(allBTN, acceptedBTN, unacceptedBTN, creatBTN);
        HBox.setHgrow(allBTN, Priority.ALWAYS);
        HBox.setHgrow(acceptedBTN, Priority.ALWAYS);
        HBox.setHgrow(unacceptedBTN, Priority.ALWAYS);
        HBox.setHgrow(creatBTN, Priority.ALWAYS);

        allBTN.setMaxWidth(Double.MAX_VALUE);
        acceptedBTN.setMaxWidth(Double.MAX_VALUE);
        unacceptedBTN.setMaxWidth(Double.MAX_VALUE);
        creatBTN.setMaxWidth(Double.MAX_VALUE);

        creatBTN.setStyle("-fx-background-color: #5EB0D0;");
        acceptedBTN.setStyle("-fx-background-color: lightblue;");
        unacceptedBTN.setStyle("-fx-background-color: lightblue;");
        allBTN.setStyle("-fx-background-color: lightblue;");

        allBTN.setOnAction(event -> {
            showAllQuestions(event);
            setAllLightBlue(allBTN, acceptedBTN, unacceptedBTN, creatBTN);
        });
        acceptedBTN.setOnAction(event -> {
            showAcceptedQuestions(event);
            setAllLightBlue(acceptedBTN, unacceptedBTN, creatBTN, allBTN);

        });
        unacceptedBTN.setOnAction(event -> {
            showUnAcceptedQuestions(event);
            setAllLightBlue(unacceptedBTN, allBTN, acceptedBTN, creatBTN);

        });
        creatBTN.setOnAction(event -> {
            creatNewQuestion(event);
            setAllLightBlue(creatBTN, allBTN, acceptedBTN, unacceptedBTN);

        });

    }

    void showAllQuestions(Event event) {
        ArrayList<Question> questions = QuestionsDAO.getAllApprovedByAuthorId(player.getID());
        questions.addAll(QuestionsDAO.getAllUnAcceptedByAuthorId(player.getID()));
        questions.addAll(QuestionsDAO.getAllPendingByAuthorId(player.getID()));
        showQuestion(questions);
    }

    void showAcceptedQuestions(Event event) {
        ArrayList<Question> questions = QuestionsDAO.getAllApprovedByAuthorId(player.getID());
        showQuestion(questions);

    }

    void showUnAcceptedQuestions(Event event) {
        ArrayList<Question> questions = QuestionsDAO.getAllUnAcceptedByAuthorId(player.getID());
        showQuestion(questions);
    }

    void creatNewQuestion(Event event1) {
        VBox vBoxContent = new VBox();
        TextArea question = new TextArea();
        TextArea optionA = new TextArea();
        TextArea optionB = new TextArea();
        TextArea optionC = new TextArea();
        TextArea optionD = new TextArea();
        HBox hBoxOption1And2 = new HBox();
        HBox hBoxOption3And4 = new HBox();

        ComboBox<String> comboBox = new ComboBox<>();
        ArrayList<Category> categories= CategoriesDAO.getAll();
        for(Category category : categories){
            comboBox.getItems().add(category.getCategoryName());
        }
        comboBox.setOnAction(e -> {
            String selected = comboBox.getValue();
            categoryID = CategoriesDAO.getByName(selected).getCategoryID();
        });
        Button submitBTN = new Button("Submit");
        Label eroreLabel = new Label("Complete all sections.");
        eroreLabel.setVisible(false);
        eroreLabel.setTextFill(Color.RED);
        submitBTN.setOnAction(event ->{
            ArrayList<String> options = new ArrayList<>(Arrays.asList(optionA.getText() , optionB.getText() , optionC.getText() , optionD.getText()));
            Collections.shuffle(options);
            String currectOPtion = (options.get(0).compareTo(optionA.getText()) == 0 ? "A" : options.get(1).compareTo(optionA.getText()) == 0 ? "B" : options.get(2).compareTo(optionA.getText()) == 0 ? "C" : "D" );
            if(optionA.getText().compareTo(optionA.getPromptText()) != 0 && optionB.getText().compareTo(optionB.getPromptText()) != 0 && optionC.getText().compareTo(optionC.getPromptText()) != 0&& optionD.getText().compareTo(optionD.getPromptText()) != 0 && question.getText()!=null && categoryID!= 0){
                QuestionsDAO.insert(question.getText(), options.get(0), options.get(1), options.get(2), options.get(3), currectOPtion, 1, categoryID, player.getID(), "pending");
                System.out.println("Ok");
                eroreLabel.setVisible(false);
            }
            else{
                eroreLabel.setVisible(true);
            }
        });

        setStyleCreatQuestion(eroreLabel , comboBox ,submitBTN ,vBoxContent, hBoxOption1And2, hBoxOption3And4, question, optionA, optionB, optionC, optionD);


    }

    void setStyleCreatQuestion(Label eroreLabel, ComboBox<String> comboBox, Button submitBTN, VBox vBoxContent, HBox hBoxOption1And2,
            HBox hBoxOption3And4, TextArea question, TextArea optionA, TextArea optionB, TextArea optionC,
            TextArea optionD) {
        scrollPaneContent.setContent(vBoxContent);
        vBoxContent.setSpacing(20.0);
        vBoxContent.setAlignment(Pos.TOP_CENTER);
        vBoxContent.setPadding(new Insets(15, 15, 15, 15));
        vBoxContent.setMaxSize(570, 350);

        question.setMaxHeight(100.0);
        question.setMinHeight(100.0);
        question.setPrefWidth(450.0);
        HBox.setHgrow(hBoxOption1And2, Priority.ALWAYS);
        HBox.setHgrow(hBoxOption3And4, Priority.ALWAYS);
        hBoxOption1And2.setSpacing(20.0);
        hBoxOption3And4.setSpacing(20.0);
        hBoxOption1And2.setPadding(new Insets(15, 15, 15, 15));
        hBoxOption3And4.setPadding(new Insets(15, 15, 15, 15));
        hBoxOption1And2.getChildren().addAll(optionA, optionB);
        hBoxOption3And4.getChildren().addAll(optionC, optionD);

        optionA.setPromptText("Write currect option here");
        optionB.setPromptText("Write option B");
        optionC.setPromptText("Write option C");
        optionD.setPromptText("Write option D");

        optionA.setStyle("-fx-control-inner-background: lightgreen;");
        optionB.setStyle("-fx-control-inner-background: #FFE5E5;");
        optionC.setStyle("-fx-control-inner-background: #FFE5E5;");
        optionD.setStyle("-fx-control-inner-background: #FFE5E5;");

        comboBox.setPromptText("Select an category");

        vBoxContent.getChildren().addAll(question, hBoxOption1And2, hBoxOption3And4, comboBox, submitBTN , eroreLabel);

    }

    void setAllLightBlue(Button BTN1, Button BTN2, Button BTN3, Button BTN4) {
        BTN1.setStyle("-fx-background-color: #5EB0D0;");
        BTN2.setStyle("-fx-background-color: lightblue;");
        BTN3.setStyle("-fx-background-color: lightblue;");
        BTN4.setStyle("-fx-background-color: lightblue;");
    }

    void showQuestion(ArrayList<Question> questions){
        VBox vBoxContent = new VBox();
        vBoxContent.setSpacing(20);
        scrollPaneContent.setContent(vBoxContent);
        for(Question question : questions){
            ImageView qImageView = new ImageView(new Image(getClass().getResource("/images/" + "questionIcon.png").toExternalForm()));
            qImageView.setFitWidth(50.0);
            qImageView.setFitHeight(50.0);
            Label questionLBL = new Label(question.getQuestion_text());
            HBox qHBox = new HBox(qImageView , questionLBL );
            qHBox.setStyle("-fx-background-color: #e6f2ff;");
            qHBox.setAlignment(Pos.CENTER);
            qHBox.setSpacing(20);

            ImageView optionImageView = new ImageView(new Image(getClass().getResource("/images/" + "optionIcon.png").toExternalForm()));
            optionImageView.setFitWidth(50.0);
            optionImageView.setFitHeight(50.0);
            Label optionLBL = new Label("A : " + question.getOption_a() + "  B : " + question.getOption_b() + "  C : " + question.getOption_c() + "  D : " + question.getOption_d() );
            HBox optionHBox = new HBox(optionImageView , optionLBL );
            optionHBox.setStyle("-fx-background-color: #e6f2ff;");
            optionHBox.setAlignment(Pos.CENTER);
            optionHBox.setSpacing(20);

            VBox vBoxQuestion = new VBox(qHBox , optionHBox);
            vBoxQuestion.setMinHeight(50);
            vBoxContent.getChildren().add(vBoxQuestion);
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
