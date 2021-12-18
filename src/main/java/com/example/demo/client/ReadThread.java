package com.example.demo.client;

import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
 
public class ReadThread extends Thread {
    private BufferedReader reader;
    private Socket socket;
    private Client client;
 
    public ReadThread(Socket _socket, Client _client, String userName, int type, int location, int sensor) {
        this.socket = _socket;
        this.client = _client;
 
        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
 
    public void run() {
        while (true) {
            try {
                String response = reader.readLine();
                if(response != null) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Client.listView.getItems().add(response);
                        }
                    });


                }
            } catch (IOException ex) {

                break;
            }
        }
    }
}