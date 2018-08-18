package com.p2p.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                String massage = dataInputStream.readUTF();
                switch (massage){
                    case "search":
                        search(dataInputStream.readUTF());
                        break;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void search(String file) throws IOException {
        for (ClientHandler clientHandler : Server.getHandlers()){
            if(clientHandler.find(file)){
                dataOutputStream.writeUTF();
                return;
            }
        }
        dataOutputStream.writeUTF("notFound");
    }

    public boolean find(String str) throws IOException {
            dataOutputStream.writeUTF("haveYou?");
            dataOutputStream.flush();
            dataOutputStream.writeUTF(str);
            dataOutputStream.flush();
            if (dataInputStream.readUTF().equals("yes"))
                return true;
        return false;
    }
}
