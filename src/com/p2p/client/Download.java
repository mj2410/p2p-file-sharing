package com.p2p.client;

import java.io.*;
import java.net.Socket;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class Download implements Runnable {
    private String pairIp;
    private Socket p2p;

    public Download(String pairIp) {
        this.pairIp = pairIp;
        try {
            p2p = new Socket(pairIp,3434);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        try {
            DataInputStream input = new DataInputStream(p2p.getInputStream());
            RandomAccessFile randomAccessFile = new RandomAccessFile(new File(Client.getLocation()),"rw");
            while (input.read(buffer) != -1) {
                randomAccessFile.write(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
