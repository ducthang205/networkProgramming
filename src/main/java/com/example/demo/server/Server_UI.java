package com.example.demo.server;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Server_UI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        primaryStage.setTitle("Server");
        final Label lbl = new Label();
        lbl.setLayoutX(240);
        lbl.setLayoutY(300);

        btn.setLayoutX(240);
        btn.setLayoutY(250);
        btn.setText("Start Server");

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                lbl.setText("Server is running. Port 9000.");

                Thread t = new Thread(){
                    @Override
                    public void run() {
                        Server server = new Server();
                        server.start();
                    }
                };
                t.start();
            }
        });

        Group root = new Group();
        root.getChildren().add(btn);
        root.getChildren().add(lbl);
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }
}