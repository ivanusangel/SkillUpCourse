package org.ivan_smirnov.datastructure.io.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) throws IOException {
        try (
                ServerSocket serverSocket = new ServerSocket(3000)
        ) {
            while (true) {
                try (
                        Socket socket = serverSocket.accept();
                        InputStream inputStream = new BufferedInputStream(socket.getInputStream());
                        OutputStream outputStream = new BufferedOutputStream(socket.getOutputStream())
                ) {
                    byte[] buffer = new byte[1024];
                    int count = inputStream.read(buffer);
                    System.out.println(new String(buffer, 0, count));
                    outputStream.write("echo ".getBytes(StandardCharsets.UTF_8));
                    outputStream.write(buffer, 0, count);
                    outputStream.flush();
                }
            }
        }
    }
}