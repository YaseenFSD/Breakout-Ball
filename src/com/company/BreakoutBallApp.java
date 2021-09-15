package com.company;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;


public class BreakoutBallApp extends Application {
    final int WIDTH = 1000;
    final int HEIGHT = 650;
    double horizontalMover = 15.5;
    double verticalMover = -15.5;

    AnchorPane rootPane = new AnchorPane();
    Scene rootScene = new Scene(rootPane, WIDTH, HEIGHT);
    Stage primaryStage;
    Circle ball = new Circle(5, Paint.valueOf("1500d1"));
    ArrayList<Rectangle> blocks = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Breakout Ball");


        rootScene.setOnKeyPressed(e -> {
            System.out.println(e.getCode());
//            System.out.println("");
        });

        initializeBall();
        initializeBlocks();

        primaryStage.setResizable(false);
        primaryStage.setScene(rootScene);
        primaryStage.show();
    }

    private void initializeBar() {

    }

    private void initializeBall() {
//        Circle ball = new Circle(5, Paint.valueOf("1500d1"));
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
            if(blocks.isEmpty()){
//                win
                endGame();
                showWinningMessage();
            };
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
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
//                TODO end game

//                System.out.println("bottom of scene");
//                return;
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
        horizontalMover = 0;
        verticalMover = 0;
    }

    void showWinningMessage(){
//        Rectangle container = new Pane;
        Text message = new Text("Congrats! You win");
        StackPane container = new StackPane();
        container.setMinWidth(WIDTH);
        container.setLayoutY(HEIGHT / 2);
//        container.setBackground(new Background(new BackgroundFill(Color.TURQUOISE, CornerRadii.EMPTY, Insets.EMPTY)));
        container.getChildren().add(message);
        message.setFont(new Font(25));

        rootPane.getChildren().add(container);
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
            return true;
        }
        return false;
    }


}