package com.p2p.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ServerSocket serverSocket;
    private static ArrayList<ClientHandler> handlers;
    public static String serverID = "127.0.0.1";
    public static final int defaultPort = 3434;
    private ExecutorService pool = Executors.newCachedThreadPool();

    public Server() {
        handlers = new ArrayList<ClientHandler>();
        while (true){
            try(Socket socket = serverSocket.accept()){
                ClientHandler clientHandler = new ClientHandler(socket);
                handlers.add(clientHandler);
                pool.execute(clientHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //start peer runnable

    //search : call search method of each peer and return random id or false and call method listen of that id to accept a socket
    // and when the socket accepted the break the loop.

    //


    public static ArrayList<ClientHandler> getHandlers() {
        return handlers;
    }
}
