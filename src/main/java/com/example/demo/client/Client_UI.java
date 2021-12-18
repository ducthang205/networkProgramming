package com.example.demo.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;




public class Client_UI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Client");
        primaryStage.setHeight(700);
        primaryStage.setWidth(700);

        primaryStage.show();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(60, 60, 60, 60));

        Scene scene = new Scene(grid, 300, 400);
        primaryStage.setScene(scene);
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw_1 = new Label("Type of client:");
        grid.add(pw_1, 0, 2);

        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(
                "Subcriber", "Publisher")
        );
        grid.add(cb, 1, 2);

        Label pw_2 = new Label("Location:");
        grid.add(pw_2, 0, 3);

        TextField LocationField = new TextField();
        grid.add(LocationField, 1, 3);

        Label pw_3 = new Label("Type of sensor:");
        grid.add(pw_3, 0, 4);

        TextField SensorField = new TextField();
        grid.add(SensorField, 1, 4);

        Button btn = new Button("Request");
        HBox hbBtn = new HBox(10);
        Button btn_stop = new Button("Disconnect");
        HBox hbBtn_stop = new HBox(10);

        Group layout2 = new Group();
        Scene sceen2 = new Scene(layout2, 300, 300);
        Label label2 = new Label("Data Page (Auto generate)");
        label2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        label2.setLayoutX(220);
        Button button2 = new Button("Back");
        button2.setOnAction(e -> primaryStage.setScene(scene));

        ListView<String> listView = Client.listView;


        listView.setPrefSize(300,300);
        // Focus
        layout2.getChildren().addAll(label2, button2);
        layout2.getChildren().add(listView);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
        btn.setOnAction(actionEvent -> {

            System.out.println(userTextField.getText());
            System.out.println(cb.getValue());
            System.out.println(SensorField.getText());
            System.out.println(LocationField.getText());

                    String username = userTextField.getText();
                    int type;
                    if (cb.getValue().equals("Publisher")) {
                        Text text = new Text("Data is generating!");
                        grid.add(text, 1,7);
                        type = 2;
                    } else {
                        Text text = new Text("Data is reading from server. Data will be updated after 5s!");
                        grid.add(text, 1,7);
                        type = 1;
                    }
                    int location = Integer.parseInt(LocationField.getText());
                    int sensor = Integer.parseInt(SensorField.getText());
            Task task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Client client = null;
                    try {
                        client = new Client();
                        client.run_client(username, type, location, sensor, listView);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }


            };
            ProgressBar bar = new ProgressBar();
            bar.progressProperty().bind(task.progressProperty());
            new Thread(task).start();
        });
        final Label lbl = new Label();
        lbl.setLayoutX(240);
        lbl.setLayoutY(300);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 5);
        hbBtn_stop.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn_stop.getChildren().add(btn_stop);
        grid.add(listView,1,8);

    }
}
