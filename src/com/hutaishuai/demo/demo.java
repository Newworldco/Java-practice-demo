package com.hutaishuai.demo;
import java.io.*;
public class demo {
    public static void main(String[] args) {
        Console cons = System.console();
        String username = cons.readLine("Username:");
        char[] passwd = cons.readPassword("Password:");
    }
}
