package com.hutaishuai.SinkaDotgame;
import java.io.*;
import java.util.*;

public class GameHelper {
    private static final String alphabet = "abcdefg";
    //设置网格的参数
    private int gridLength = 7;
    private int gridSize = 49;
    private int [] grid = new int[gridSize];
    private int comCount = 0;

    public String getUserInput(String prompt){
       String inputLine = null;
       System.out.print(prompt + "");
       try{
          BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
          inputLine = is.readLine();
          if(inputLine.length() == 0) return null;
       }catch (IOException e){
           System.out.println("IOException:" + e);

       }
       return inputLine.toLowerCase();
    }

    public ArrayList<String> placeDotCom(int comSize){
        ArrayList<String> alphaCells = new ArrayList<>();
        String [] alphacoord = new String[comSize];
        String temp = null;
        int [] coords = new int[comSize];
        int attempts = 0;
        boolean success =false;
        int location = 0;

        comCount++;
        int incr =1;
        if ((comCount % 2)==1){
            incr = gridLength;
        }

        while (!success & attempts++ < 200){
            location = (int) (Math.random()*gridSize);
            // System.out.print("try" + location);
            int x = 0;
            success = true;
            while(success && x < comSize){
                if(grid[location]==0){
                    coords[x++] = location;
                    location += incr;
                    if (location >= gridSize){
                        success = false;
                    }
                    // 超出右边缘，失败
                    if (x>0 && (location % gridLength == 0)){
                        success = false;
                    }
                }else {
                    //System.out.print("used" + location);
                    success = false;
                }
            }
        }//while循环结束;

        int x = 0;
        int row = 0;
        int column = 0;
        // System.out.println("\n");
        while (x < comSize){
            grid[coords[x]] = 1;
            //得到行的值
            row = (int) (coords[x]/gridLength);
            //得到列的值
            column = coords[x] % gridLength;
            //得到列的值
            temp = String.valueOf(alphabet.charAt(column));

            alphaCells.add(temp.concat(Integer.toString(row)));
            x++;
            System.out.print("  cord " + alphaCells.get(x-1));
        }

        return alphaCells;
    }
}
