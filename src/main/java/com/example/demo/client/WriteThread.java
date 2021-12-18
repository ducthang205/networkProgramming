package com.example.demo.client;

import javafx.scene.control.ListView;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class WriteThread extends Thread {
    private PrintWriter writer;
    private final Socket socket;
    private final Client client;
    public String userName;
    public int type;
    public int location;
    public int sensor;
    public ListView<String> listView;
 
    public WriteThread(Socket _socket, Client _client, String userName, int type, int location, int sensor, ListView<String> listView) {
        this.socket = _socket;
        this.client = _client;
        this.userName = userName;
        this.type = type;
        this.location = location;
        this.sensor = sensor;
        this.listView = listView;
        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
 
    public void run() {
        String message = this.userName + "-" + String.valueOf(this.type) + "-" + String.valueOf(this.location) + "-" + String.valueOf(this.sensor);
        writer.println(message);
        String text = null;
        do {
                if(type == 2) {
                    System.out.printf("Generate data auto: \n");
                    while (true) {

                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        String time = dtf.format(now);
                        Random generator = new Random();
                        String temperature = " Temperature: " + String.valueOf(generator.nextInt(100) + 1) + "°C";
                        System.out.println(time + temperature);
                        writer.println(time + temperature);
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

        } while (true);



    }
}
