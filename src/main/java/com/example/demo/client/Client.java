package com.example.demo.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    enum Type_Client{Publisher, Subcriber};
    public static ObservableList<String> lists = FXCollections.observableArrayList();
    static ListView<String> listView = new ListView<String>(lists);
    private InetAddress hostname;
    private int port;
    private String userName;
    private Type_Client type_client;
    private int Location;
    private int Sensor;

    public Client() throws IOException {
        this.hostname = InetAddress.getLocalHost();
        this.port = 9000;
    }

    public void execute(String userName, int type, int location, int sensor, ListView<String> listView) {
        try {
            Socket socket = new Socket(hostname, port);
            new ReadThread(socket, this, userName, type, location, sensor).start();
            new WriteThread(socket, this, userName, type, location, sensor, listView).start();
 
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
 
    }

    void setUserName(String userName) {
        this.userName = userName;
    }

    public void setLocation(int location) {
        Location = location;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setType_client(Type_Client type_client) {
        this.type_client = type_client;
    }

    public void setSensor(int sensor) {
        Sensor = sensor;
    }

    public int getSensor() {
        return Sensor;
    }

    public int getLocation() {
        return Location;
    }

    public Type_Client getType_client() {
        return type_client;
    }

    String getUserName() {
        return this.userName;
    }
 
 
    public void run_client(String userName, int type, int location, int sensor, ListView<String> listView) throws IOException {
        Client client = new Client();
        client.execute(userName, type, location, sensor, listView);
    }
}