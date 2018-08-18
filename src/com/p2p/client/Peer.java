package com.p2p.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Peer implements Runnable {
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ArrayList<File> files;


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
        while (true) {
            try {
                String massage = dataInputStream.readUTF();
                String answer = "no";
                switch (massage) {
                    case "haveYou?":
                        for (File file : files) {
                            if (file.getName().equals(dataInputStream.readUTF())) {
                                answer = "yes";
                                break;
                            }
                            answer = "no";
                        }
                        dataOutputStream.writeUTF(answer);
                        break;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void search(String fileName) throws IOException {
        dataOutputStream.writeUTF("search");
        dataOutputStream.flush();
        dataOutputStream.writeUTF(fileName);
        dataOutputStream.flush();
        if (dataInputStream.readUTF().equals("notFound"))
            return;

    }

    public void addFile(File file) {
        files.add(file);
    }
}
