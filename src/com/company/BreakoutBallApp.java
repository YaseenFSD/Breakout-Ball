package com.company;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class BreakoutBallApp extends Application {
    StackPane rootPane = new StackPane();
    Scene rootScene = new Scene(rootPane, 800, 650);
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

        primaryStage.setResizable(false);
        primaryStage.setScene(rootScene);
        primaryStage.show();
    }

    private void initializeBar(){

    }
}