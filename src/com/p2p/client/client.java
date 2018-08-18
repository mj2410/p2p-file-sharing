package com.p2p.client;

import com.p2p.server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class client {
    private Peer peer;
    private String location;

    public void connectServer() throws IOException {
            Socket socket = new Socket(Server.serverID,Server.defaultPort);
            peer = new Peer(socket);
            new Thread(peer).start();
    }

//    public String getId() {
//        try {
//            return InetAddress.getLocalHost().getHostAddress();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

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
            peer.search(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
