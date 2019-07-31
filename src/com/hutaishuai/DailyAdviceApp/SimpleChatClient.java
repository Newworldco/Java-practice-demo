package com.hutaishuai.DailyAdviceApp;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleChatClient {
    /*
    有线程的版本，可以在送出信息给服务器的同时
    读取来自服务器的信息。
     */
    JTextArea incoming;
    JTextField outgoing;
    BufferedReader reader;
    PrintWriter writer;
    Socket sock;

    public static void main(String[] args){
        SimpleChatClient client = new SimpleChatClient();
        client.go();
    }

    public void go(){
        JFrame frame = new JFrame("乞丐版聊天客户端");
        JPanel mainPanel = new JPanel();
        incoming = new JTextArea(15,50);
        incoming.setLineWrap(true);
        incoming.setWrapStyleWord(true);
        incoming.setEditable(false);
        JScrollPane qScroller = new JScrollPane(incoming);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outgoing = new JTextField(20);
        JButton sendButton = new JButton("发送");
        sendButton.addActionListener(new SendButtonListener());
        mainPanel.add(qScroller);
        mainPanel.add(outgoing);
        mainPanel.add(sendButton);
        setUpNetworking();

        //启动新的线程，以内部类作为任务
        //此任务是读取服务器的socket串流显示在文本区域；
        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();

        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(400,500);
        frame.setVisible(true);

    }//关闭go

    private void setUpNetworking(){
        try{
            sock = new Socket("127.0.0.1",6000);
            InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(streamReader);
            writer = new PrintWriter(sock.getOutputStream());
            System.out.println("通信网络建立好了");

        }catch(IOException ex){ex.printStackTrace();}
    }//关闭setUpNetworking

    public class SendButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent ev){
            //用户按下send 按钮时送出文本字段的内容到服务器上
            try {
                writer.println(outgoing.getText());
                writer.flush();
            }catch (Exception ex){ex.printStackTrace();}

            outgoing.setText("");
            outgoing.requestFocus();
        }

    }

    //持续读取服务器信息并把它加到可滚动的文本区域上；
    public class IncomingReader implements Runnable{
        public void run(){
            String message;
            try {
                while ((message = reader.readLine()) != null){
                    System.out.println("read" + message);
                    incoming.append(message + "\n");
                }//while 结束
            }catch (Exception ex){ex.printStackTrace();}
        }
    }//关闭内部类
}
