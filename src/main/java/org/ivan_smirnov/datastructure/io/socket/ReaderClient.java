package org.ivan_smirnov.datastructure.io.socket;

import java.io.*;
import java.net.Socket;

public class ReaderClient {

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 3000);
             InputStream inputStream = new BufferedInputStream(socket.getInputStream());
             OutputStream outputStream = new BufferedOutputStream(socket.getOutputStream())
        ) {
            outputStream.write("Hello from client".getBytes());
            outputStream.flush();
            byte[] buffer = new byte[1024];
            int count = inputStream.read(buffer);
            System.out.println(new String(buffer, 0, count));
        }
    }
}