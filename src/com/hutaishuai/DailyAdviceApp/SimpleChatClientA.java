package com.hutaishuai.DailyAdviceApp;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class SimpleChatClientA {
    JTextField outgoing;
    PrintWriter writer;
    Socket sock;

    public void go(){
        //注册按钮的监听者
        //调用setUpNetworking()
        JFrame frame = new JFrame("简单的聊天客户端");
        JPanel mainPanel = new JPanel();
        outgoing = new JTextField(20);
        JButton sendButton = new JButton("发送");
        sendButton.addActionListener(new SendButtonListener());
        mainPanel.add(outgoing);
        mainPanel.add(sendButton);
        frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
        setUpNetworking();
        frame.setSize(400,500);
        frame.setVisible(true);
    }

    private void setUpNetworking(){
        //建立socket、PrintWriter
        //赋值PrintWriter给实例变量
        try {
            sock = new Socket("127.0.0.1", 5000);
            writer = new PrintWriter(sock.getOutputStream());
            System.out.println("networking established");
        }catch (IOException ex){ex.printStackTrace();}
    }

    public class SendButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            //取得文字字段内容
            //传送到服务器上
            try {
                writer.println(outgoing.getText());
                writer.flush();
            }catch (Exception ex){ex.printStackTrace();}

            outgoing.setText("");
            outgoing.requestFocus();
        }
    }public static void main(String[] args){
        new SimpleChatClientA().go();
    }
}
