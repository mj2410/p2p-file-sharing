package com.p2p.client;

import com.p2p.server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private Peer peer;
    private static String location = System.getProperty("user.home") + File.separator + "SharedFiles";
    private String pairId;
    private static String serverIp = "127.0.0.1";

    public void connectServer() throws IOException {
            Socket socket = new Socket(serverIp, 3434);
            peer = new Peer(socket);
            new Thread(peer).start();
    }

    public void disconnect() {
    }

    public Peer getPeer() {
        return peer;
    }

    public void addFile(File file) {
        peer.addFile(file);
    }

    public void search(String string) {
        try {
            pairId = peer.search(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getLocation() {
        return location;
    }

    public static void setLocation(String location) {
        Client.location = location;
    }

    public void download() {
        Thread thread = new Thread(new Download(pairId));
        thread.start();
    }

}
