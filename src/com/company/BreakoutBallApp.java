package com.company;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;



public class BreakoutBallApp extends Application {
    final int WIDTH = 1000;
    final int HEIGHT = 650;
    double horizontalMover = 1.5;
    double verticalMover = -1.5;
    AnchorPane rootPane = new AnchorPane();
    Scene rootScene = new Scene(rootPane, WIDTH, HEIGHT);
    Stage primaryStage;

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

        primaryStage.setResizable(false);
        primaryStage.setScene(rootScene);
        primaryStage.show();
    }

    private void initializeBar() {

    }

    private void initializeBall() {
        Circle ball = new Circle(5, Paint.valueOf("1500d1"));
        ball.setLayoutX(WIDTH / 2);
        ball.setLayoutY(HEIGHT - 200);
        rootPane.getChildren().add(ball);
        Bounds bounds = rootPane.getBoundsInParent();
        System.out.println(bounds.getMaxX());

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), actionEvent -> {
            // move ball
            ball.setLayoutX(ball.getLayoutX() + horizontalMover);
            ball.setLayoutY(ball.getLayoutY() + verticalMover);
            setMovers(bounds, ball);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    void setMovers(Bounds bounds, Circle ball){
        if (ball.getLayoutX() >= bounds.getMaxX()) {
//            hits right wall
            horizontalMover *= -1;
        }

        if (ball.getLayoutX() <= bounds.getMinX()){
//            hits left wall
            horizontalMover *= -1;
        }

        if (ball.getLayoutY() >= bounds.getMaxY()){
//            hits top wall
            verticalMover *= -1;
        }

        if (ball.getLayoutY() <= bounds.getMinY()){
//            hits bottom wall
            verticalMover *= -1;
        }
    }
}