package com.hutaishuai.DailyAdviceApp;
import java.io.*;
import java.net.*;

public class DailyAdviceServer {
    String[] adviceList = {"你妈嗨","这波不愧，666","哎，队友呢，队友呢","这个版本没我"};
    public void go(){
        try {
            ServerSocket serverSocket = new ServerSocket(4242);

            while (true){
                //这个方法会停下来等待要求达到之后才会继续
                Socket sock = serverSocket.accept();

                PrintWriter writer = new PrintWriter(sock.getOutputStream());
                String advice = getAdvice();
                writer.println(advice);
                writer.close();
                System.out.println(advice);
            }
        }catch(IOException ex){ex.printStackTrace();}

    }// 关闭go函数

    private String getAdvice(){
        int random = (int) (Math.random()* adviceList.length);
        return adviceList[random];
    }

    public static void main(String[] args){
        DailyAdviceServer server = new DailyAdviceServer();
        server.go();
    }
}
