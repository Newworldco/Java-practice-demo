package com.hutaishuai.SinkaDotgame;
import java.util.*;

public class DotComBust {
    //声明变量
    private GameHelper helper = new GameHelper();
    private ArrayList<DotCom> dotComList = new ArrayList<DotCom>();
    private int numOfGuesses = 0;

    //建立游戏
    private void setUpGame(){
        // 首先，创建dotcom，设定位置
        DotCom one  = new DotCom();
        one.setName("pets.com");
        DotCom two = new DotCom();
        two.setName("eToys.com");
        DotCom three = new DotCom();
        three.setName("Go2.com");
        dotComList.add(one);
        dotComList.add(two);
        dotComList.add(three);

        //提示信息
        System.out.println("你的目标是击沉三艘船");

        //为每个船设置位置
        for(DotCom dotComToSet : dotComList){
            ArrayList<String> newLocation = helper.placeDotCom(3);
            dotComToSet.setLocationCells(newLocation);
        }
    }

    private void startPlaying(){
        while(!dotComList.isEmpty())
        {
            String userGuess = helper.getUserInput("Enter a guess");
            checkUserGuess(userGuess);
        }
        finishGame();
    }

    //检测用户输入的方法
    private void checkUserGuess(String userGuess){
        numOfGuesses++;
        String result = "miss";

        for(DotCom dotComToTest : dotComList){
            result = dotComToTest.checkYourself(userGuess);
            if (result.equals("hit"))
            {
                break;
            }
            if (result.equals("kill"))
            {
                dotComList.remove(dotComToTest);
                break;
            }
        }
        System.out.println(result);
    }

    private void finishGame(){
        System.out.println("所有船只击沉");
        if(numOfGuesses <= 18){
            System.out.println("你猜了" + numOfGuesses +  "次");
        }else{
            System.out.println("猜了太多次，你被沉了");
        }
    }

    public static void main(String[] args){
        DotComBust game = new DotComBust();
        game.setUpGame();
        game.startPlaying();
    }
}
