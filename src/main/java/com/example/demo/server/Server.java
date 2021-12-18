package com.example.demo.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private int port = 9000;
    private Set<String> userNames = new HashSet<>();
    public String topics[][] = init_topics() ;
    public int flags[][] = init_flags() ;
    private Set<SThread> SThreads = new HashSet<>();
    public void setTopics(String[][] topics) {
        this.topics = topics;
    }

    public void setFlags(int[][] flags) {
        this.flags = flags;
    }

    public int[][] init_flags(){
        int flags[][] = new int[20][20];
        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 20; j++){
                flags[i][j] = 0 ;
            }
        }
        return flags;
    }
    public String[][] init_topics(){
        String topics[][] = new String[20][20] ;
        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 20; j++){
                topics[i][j] = "No data in location " + String.valueOf(i) + " sensor " + String.valueOf(j) ;
            }
        }
        return topics;
    }
    public void execute() {
        
        try (ServerSocket serverSocket = new ServerSocket(port)) {
 
            System.out.println("Server is listening on port " + port);
 
            while (true) {
                Socket socket = serverSocket.accept();
                SThread newUser = new SThread(socket, this);
                SThreads.add(newUser);
                newUser.start();
            }
 
        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void start() {
        Server server = new Server();
        server.setFlags(server.init_flags());
        server.setTopics(server.init_topics());
        server.execute();
    }

    void addUserName(String userName) {
        userNames.add(userName);
    }
 
    void removeUser(String userName, SThread aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            SThreads.remove(aUser);
            System.out.println("The user " + userName + " quitted");
        }
    }
}