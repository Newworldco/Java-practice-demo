package com.hutaishuai.BeatboxApp;
import java.awt.*;
import javax.swing.*;
import javax.sound.midi.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;

public class BeatBox {
    JPanel mainPanel;
    ArrayList<JCheckBox> checkboxList; //把checkbox储存在ArrayList中
    Sequencer sequencer;
    Sequence sequence;
    Track track;
    JFrame theFrame;

    //乐器的名称，用array维护
    String[] instrumentNames = {"Bass Drum", "closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal",
            "Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap",
            "Low-mid Tom", "High Agogo", "Open Hi Conga"};
    //实际乐器的关键字，例如35是bass
    int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};

    public static void main(String[] args) {
        new BeatBox().buildGUI();
    }

    public void buildGUI() {
        theFrame = new JFrame("Cyber BeatBox");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));//设置面板上摆设组件时的空白边缘

        checkboxList = new ArrayList<JCheckBox>();
        Box buttonBox = new Box(BoxLayout.Y_AXIS);

        JButton start = new JButton("开始");
        start.addActionListener(new MyStartListener());
        buttonBox.add(start);

        JButton stop = new JButton("结束");
        stop.addActionListener(new MyStopListener());
        buttonBox.add(stop);

        JButton upTempo = new JButton("加快");
        upTempo.addActionListener(new MyUpTempoListener());
        buttonBox.add(upTempo);

        JButton downTempo = new JButton("减慢");
        downTempo.addActionListener(new MyDownTempoListener());
        buttonBox.add(downTempo);

        JButton serializelt = new JButton("存储");
        serializelt.addActionListener(new MySendListener());
        buttonBox.add(serializelt);

        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (int i = 0; i < 16; i++) {
            nameBox.add(new Label(instrumentNames[i]));
        }

        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);

        theFrame.getContentPane().add(background);

        GridLayout grid = new GridLayout(16, 16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER, mainPanel);

        //创建checkbox组，设定成未勾选的为false并加到ArrayList和面板上
        for (int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkboxList.add(c);
            mainPanel.add(c);
        }

        setUpMidi();

        theFrame.setBounds(50, 50, 300, 300);
        theFrame.pack();
        theFrame.setVisible(true);

    }//结束此方法

    public void setUpMidi() {
        //一般的MIDI设置程序代码
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//此方法结束


    public void buildTrackAndStart() {
        /*
        重点在这里！此处会将复选框状态转换为MIDI事件并加到track上
         */

        //创建出16个元素的数组来存储一项乐器的值。如果该节应该要演奏
        //其值会是关键字值，否则值为零
        int[] trackList = null;

        //清除旧的track做一个新的
        sequence.deleteTrack(track);
        track = sequence.createTrack();

        //对每个乐器都执行一次
        for (int i = 0; i < 16; i++) {
            trackList = new int[16];

            int key = instruments[i];//设定代表乐器的关键字

            //对每一拍执行一次
            for (int j = 0; j < 16; j++) {
                JCheckBox jc = (JCheckBox) checkboxList.get(j + (16 * i));

                //如果有勾选，将关键字值放到数组的该位置上，不然的话就补零
                if (jc.isSelected()) {
                    trackList[j] = key;
                } else {
                    trackList[j] = 0;
                }
            }//内部循环

            //创建此乐器的事件并加到track上
            makeTracks(trackList);
            track.add(makeEvent(176, 1, 127, 0, 16));
        }//外部循环关闭

        //确保第16拍有事件，否则beatbox不会重复播放
        track.add(makeEvent(192, 9, 1, 0, 15));
        try {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);//指定无穷的重复次数
            sequencer.start();
            sequencer.setTempoInBPM(120);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//关闭buildTrackAndStart方法

    //第一个内部类，按钮的监听者
    public class MyStartListener implements ActionListener{
        public void actionPerformed(ActionEvent a){
            buildTrackAndStart();
        }
    }//关闭内部类

    public class MyStopListener implements ActionListener{
        public void actionPerformed(ActionEvent a){
            sequencer.stop();
        }
    }//关闭内部类

    public class MyUpTempoListener implements ActionListener{
        public void actionPerformed(ActionEvent a){
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float)(tempoFactor * 1.03));
        }
    }// 关闭内部类

    public class MyDownTempoListener implements ActionListener{
        public void actionPerformed(ActionEvent a){
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float)(tempoFactor * .97));//节奏因子，预设为1.0,每次调整3%
        }
    }//关闭内部类



    //创建某项乐器的所有事件
    public void makeTracks(int[] list){

        for (int i =0; i<16; i++){
            int key = list[i];

            //创建NOTE ON和 NOTE OFF事件并加入到track上
            if(key !=0){
                track.add(makeEvent(144,9,key,100,i));
                track.add(makeEvent(128,9,key, 100, i+1));
            }
        }
    }

    public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick){
        MidiEvent event = null;
        try{
            ShortMessage a = new ShortMessage();
            a.setMessage(comd,chan,one,two);
            event = new MidiEvent(a, tick);
        }catch(Exception e){e.printStackTrace();}
        return event;
    }

    //存储节奏
    public class MySendListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            //此数组用来保存复选框的状态
            boolean[] checkboxState = new boolean[256];
            for (int i = 0; i < 256; i++){
                JCheckBox check = (JCheckBox) checkboxList.get(i);
                if (check.isSelected()){
                    checkboxState[i] = true;
                }
            }
            try {
                FileOutputStream fileStream = new FileOutputStream(new File("Checkbox.ser"));
                ObjectOutputStream os = new ObjectOutputStream(fileStream);
                os.writeObject(checkboxState);
            }catch (Exception e){e.printStackTrace();}
        }
    }

    //读取还原节奏
    public class MyReadInListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            boolean[] checkboxState = null;
            try {
                FileInputStream fileIn = new FileInputStream(new File("Checkbox.ser"));
                ObjectInputStream is = new ObjectInputStream(fileIn);
                checkboxState = (boolean[]) is.readObject();
            }catch (Exception ex){ex.printStackTrace();}

            for (int i = 0; i < 256; i++){
                JCheckBox check = (JCheckBox) checkboxList.get(i);
                if (checkboxState[i]){
                    check.setSelected(true);
                }else {
                    check.setSelected(false);
                }

            }
            sequencer.stop();
            buildTrackAndStart();
        }
    }
}//关闭类



