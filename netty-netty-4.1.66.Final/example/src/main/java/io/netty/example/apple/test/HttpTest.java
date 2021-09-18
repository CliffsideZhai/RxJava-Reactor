package io.netty.example.apple.test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author cliffside
 * @date 2021-09-18 21:01
 */
public class HttpTest {
    public static final String SEPARATOR = "\r\n";

    public static void main(String[] args) {
        ServerSocket serverSocket ;
        try {
            serverSocket = new ServerSocket(8080);
            while (true){
                Socket accept = serverSocket.accept();
                doProcess(accept);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private static void doProcess(Socket accept) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(accept.getOutputStream()));


            String line;
            while (!"".equals(line = bufferedReader.readLine())){
                System.out.println(line);
            }

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("HTTP/1.1 200 OK").append(SEPARATOR)
                    .append("Content-Type: application/json;charset=utf-8").append(SEPARATOR)
                    .append(SEPARATOR).append("{{\"zsk\":\"zsk\"}}");
            bufferedWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
