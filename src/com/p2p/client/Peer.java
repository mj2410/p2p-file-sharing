package com.p2p.client;

import com.p2p.server.Server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Peer implements Runnable {
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ArrayList<File> files;
    private String pairIp;
    private ExecutorService pool = Executors.newCachedThreadPool();


    public Peer(Socket socket) {
        this.socket = socket;
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        files = new ArrayList<File>();
    }

    @Override
    public void run() {
        Listener listener = null;
        while (true) {
            try {
                String massage = dataInputStream.readUTF();
                String answer = "no";
                switch (massage) {
                    case "haveYou?":
                        for (File file : files) {
                            if (file.getName().equals(dataInputStream.readUTF())) {
                                answer = "yes";
                                listener = new Listener(new File(Client.getLocation()));
                                break;
                            }
                            answer = "no";
                        }
                        dataOutputStream.writeUTF(answer);
                        if (answer.equals("yes")) {
                            dataOutputStream.writeUTF(InetAddress.getLocalHost().getHostAddress());
                            pool.execute(listener);
                        }
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String search(String fileName) throws IOException {
        dataOutputStream.writeUTF("search");
        dataOutputStream.flush();
        dataOutputStream.writeUTF(fileName);
        dataOutputStream.flush();
        if (dataInputStream.readUTF().equals("notFound"))
            return null;
        pairIp = dataInputStream.readUTF();
        return pairIp;
    }

    public void addFile(File file) {
        files.add(file);
    }
}
