package com.company;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;


public class BreakoutBallApp extends Application {
    final int WIDTH = 1000;
    final int HEIGHT = 650;
    double horizontalMover = 2;
    double verticalMover = -2;
    final double INIT_H_MOVER = horizontalMover;
    final double INIT_V_MOVER = verticalMover;
    static int scoreAdd = 0;
    AnchorPane rootPane = new AnchorPane();
    StackPane endGameContainer = new StackPane();
    Text text = new Text();
    Scene rootScene = new Scene(rootPane, WIDTH, HEIGHT);
    Stage primaryStage;

    //Setting the properties of the rectangle
    Rectangle paddleBar = new Rectangle();
    Circle ball = new Circle(5, Paint.valueOf("1500d1"));
    ArrayList<Rectangle> blocks = new ArrayList<>();
    Timeline mainTimeLine;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Breakout Ball");


        rootScene.setOnKeyPressed(e -> {
            System.out.println(e.getCode());
        });
        initializeScore();
        initializeBar();
        initializeBall();
        initializeBlocks();


        primaryStage.setResizable(false);
        primaryStage.setScene(rootScene);
        rootPane.getChildren().add(endGameContainer);
        primaryStage.show();
    }

    // score
    private void initializeScore(){
        Text score = new Text();
        score.setX(850);
        score.setY(15);
        score.setText("Score :");
        score.setFill(Color.RED);
        score.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        rootPane.getChildren().add(score);


        text.setX(940);
        text.setY(15);
        text.setText(String.valueOf(scoreAdd));
        text.setFill(Color.RED);
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        rootPane.getChildren().add(text);
    }
    private void initializeBar() {

        paddleBar.setX(150.0f);
        paddleBar.setY(620.0f);
        paddleBar.setWidth(250.0f);
        paddleBar.setHeight(30.0f);

        paddleBar.setArcHeight(15);
        paddleBar.setArcWidth(15);
        //Setting other properties
        paddleBar.setFill(Color.DARKCYAN);
        paddleBar.setStrokeWidth(2.0);
        paddleBar.setStroke(Color.DARKSLATEGREY);
        rootPane.getChildren().addAll(paddleBar);


        rootScene.setOnKeyPressed(e -> {
            switch (e.getCode()){
                case RIGHT:
                    while(paddleCollisionRightWall()) {
                        paddleBar.setX(paddleBar.getX() + 15);
                        break;
                    }

                    break;
                case LEFT:

                    while(paddleCollisionLeftWall()) {
                        paddleBar.setX(paddleBar.getX() - 15);
                        break;
                    }


            }
        });
    }
// when paddle hits left side of wall it stops
    private boolean paddleCollisionLeftWall() {
        if (paddleBar.getX() >  5){
            return true;
        }else {
            return false;
        }
    }
    // when paddle hits right side of wall it stops
    private boolean paddleCollisionRightWall() {
        if (paddleBar.getX() <= 740){
            return true;
        }else {
            return false;
        }
    }

    private void initializeBall() {
        if (rootPane.getChildren().contains(ball)) {
            rootPane.getChildren().remove(ball);
        }
        ball.setLayoutX(WIDTH / 2);
        ball.setLayoutY(HEIGHT - 200);
        rootPane.getChildren().add(ball);
        Bounds bounds = rootPane.getBoundsInParent();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), actionEvent -> {
            // move ball
            ball.setLayoutX(ball.getLayoutX() + horizontalMover);
            ball.setLayoutY(ball.getLayoutY() + verticalMover);
            blocks.removeIf(block -> checkBallHitBlock(block));
            setMovers(bounds);
            checkPaddleCollision();

            if(blocks.isEmpty()){
//                win
                endGame();
                showEndMessage("Congrats! You win \n Your score is " + scoreAdd);
            };
        }));
        mainTimeLine = timeline;
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
// check ball collide with paddle bar
    private void checkPaddleCollision() {
        if (ball.getBoundsInParent().intersects(paddleBar.getBoundsInParent())) {

            setMovers(paddleBar.getBoundsInParent());
          
        }

    }


    void setMovers(Bounds bounds) {
        if (ball.getLayoutX() > bounds.getMaxX()) {
//            hits right wall
            horizontalMover *= -1;
            return;

        }

        if (ball.getLayoutX() <= bounds.getMinX()) {
//            hits left wall
            horizontalMover *= -1;
            return;

        }

        if (ball.getLayoutY() >= bounds.getMaxY()) {
//            hits bottom wall
            if (ball.getLayoutY() >= HEIGHT){
                endGame();
                showEndMessage("Oh no you lost");
                return;
            }
            verticalMover *= -1;
            return;
        }

        if (ball.getLayoutY() <= bounds.getMinY()) {
//            hits top wall
            verticalMover *= -1;
            return;
        }


    }


    void endGame(){
        mainTimeLine.stop();
        verticalMover = INIT_V_MOVER;
        horizontalMover = INIT_H_MOVER;
        showRetryButton();
    }


    void showEndMessage(String str){
        Text message = new Text(str);

        endGameContainer.setMinWidth(WIDTH);
        endGameContainer.setLayoutY(HEIGHT / 2);
//        endGameContainer.setMinHeight(200);
//        endGameContainer.setBackground(new Background(new BackgroundFill(Color.TURQUOISE, CornerRadii.EMPTY, Insets.EMPTY)));
        endGameContainer.getChildren().add(message);
        message.setFont(new Font(25));

    }


    void showRetryButton(){
        Button btn = new Button();
        btn.setText("Replay");
        btn.setTranslateY(70 + btn.getTranslateY());
        endGameContainer.getChildren().add(btn);
        btn.setOnAction(e -> {
            blocks.clear();
            rootPane.getChildren().clear();
            rootPane.getChildren().add(endGameContainer);
            endGameContainer.getChildren().clear();
            initializeScore();
            initializeBlocks();
            initializeBall();
            initializeBar();
            scoreAdd = 0;
            text.setText(String.valueOf(scoreAdd));
           
        });
    }

    void initializeBlocks() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 16; j++) {
                Rectangle block = new Rectangle(50, 50, Color.DARKBLUE);
                block.setLayoutX(block.getLayoutX() + (j * 60) + 25);
                block.setLayoutY(block.getLayoutY() + (i * 60) + 25);
                blocks.add(block);
                rootPane.getChildren().add(block);
            }
        }
    }

    boolean checkBallHitBlock(Rectangle block){

        if (ball.getBoundsInParent().intersects(block.getBoundsInParent())) {
            rootPane.getChildren().remove(block);
            setMovers(block.getBoundsInParent());
            scoreAdd += 10;
            text.setText(String.valueOf(scoreAdd));
            return true;
        }
        return false;
    }


}