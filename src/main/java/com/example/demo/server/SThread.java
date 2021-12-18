package com.example.demo.server;

import java.io.*;
import java.net.Socket;
 
public class SThread extends Thread {
    private Socket socket;
    private Server server;
    private PrintWriter writer;

    public SThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }
    public void run() {
        try {

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
 
            printUsers();
            String message_client = reader.readLine();
            String[] part = message_client.split("-");
            String userName = part[0];
            server.addUserName(userName);
            String message = "";
            String type_client = part[1];
            System.out.println("Type of user is " + type_client);
            String location = part[2];
            System.out.println("Location " + location);
            String sensor = part[3];
            System.out.println("Sensor " + sensor);
            String clientMessage = null;
            try {
                if(Integer.parseInt(type_client) == 2) {
                        if(server.flags[Integer.parseInt(location)][Integer.parseInt(sensor)] == 1){
                            message = "Topic is using by anothor publisher.";
                            writer.println(message);
                        }
                        else {

                            server.flags[Integer.parseInt(location)][Integer.parseInt(sensor)] = 1;
                            do {
                                clientMessage = reader.readLine();
                                message = clientMessage;
                                System.out.println(message);
                                if (message != null) {
                                    server.topics[Integer.parseInt(location)][Integer.parseInt(sensor)] = message;
                                    message = server.topics[Integer.parseInt(location)][Integer.parseInt(sensor)];
                                    writer.println(message);
                                }
                            } while (!clientMessage.equals("quit"));
                        }


                }
                else {
                    do {
                        message = server.topics[Integer.parseInt(location)][Integer.parseInt(sensor)];
                        writer.println(message);
                        Thread.sleep(5000);
                    }while (true);
                }
            }
            catch (Exception ignored){
                System.out.printf(String.valueOf(ignored));
            }
            server.removeUser(userName, this);
            socket.close();
            server.flags[Integer.parseInt(location)][Integer.parseInt(sensor)] = 0;
            message = userName + " has quitted.";

 
        } catch (IOException ex) {
            System.out.println("Error in SThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    void printUsers() {

    }

}