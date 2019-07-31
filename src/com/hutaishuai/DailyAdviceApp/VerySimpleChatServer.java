package com.hutaishuai.DailyAdviceApp;
import java.io.*;
import java.net.*;
import java.util.*;

public class VerySimpleChatServer {
    /*
    本程序是书中的现成码
    这是一个非常简单的聊天服务器程序
    有很多不足，你可以学完本书后加强这个程序
    另外一种现在就可以锻炼的方法是自己给程序加注释
     */

    ArrayList clientOutputStreams;

    public class ClientHandler implements Runnable{
        BufferedReader reader;
        Socket sock;

        //构造函数,初始化获取传来的数据
        public ClientHandler(Socket clientSocket){
            try {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            }catch(Exception ex){ex.printStackTrace();}
        }

        public void run(){
            String message;
            try {
                while ((message = reader.readLine()) != null){
                    System.out.println("read" + message);
                    tellEveryone(message);
                }
            }catch (Exception ex){ex.printStackTrace();}
        }//关闭run()
    }//关闭内部类

    public static void main(String[] args){
        new VerySimpleChatServer().go();
    }

    public void go(){
        clientOutputStreams = new ArrayList();
        try {
            ServerSocket serverSock = new ServerSocket(6000);

            while (true) {
                Socket clientSocket = serverSock.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);

                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
                System.out.println("建立连接");
            }
        }catch (Exception ex){ex.printStackTrace();}
    }//关闭go

    public void tellEveryone(String message){
        // 打印出客户端的信息
        Iterator it = clientOutputStreams.iterator();
        while (it.hasNext()){
            try {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                writer.flush();
            }catch (Exception ex){ex.printStackTrace();}
        }
    }
}
