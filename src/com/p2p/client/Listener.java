package com.p2p.client;

import java.beans.PropertyChangeListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener implements Runnable {
    private File file;
    private Socket pair;

    public Listener(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        try {
            ServerSocket miniServer = new ServerSocket(3434);
            pair = miniServer.accept();

            DataOutputStream dataOutputStream = new DataOutputStream(pair.getOutputStream());
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            byte[] buffer = new byte[1024];
            while (randomAccessFile.read(buffer) != -1) {
                dataOutputStream.write(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
