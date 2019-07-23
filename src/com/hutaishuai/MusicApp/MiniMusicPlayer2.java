package com.hutaishuai.MusicApp;
import javax.sound.midi.*;


public class MiniMusicPlayer2 implements ControllerEventListener {
    public static void main(String[] args){
        MiniMusicPlayer2 mini = new MiniMusicPlayer2();
        mini.go();}

    public void go(){
        try{
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            //像sequence注册事件
            int[] eventsIWant = {127};
            sequencer.addControllerEventListener(this, eventsIWant);

            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            //创建连续的音符事件
            for (int i=5; i<61; i+=4){
                track.add(makeEvent(144,1,i,100,i));

                //插入事件编号为127的自定义ControllerEvent(176)
                track.add(makeEvent(176,1,127,0,i));

                track.add(makeEvent(128,1,i,100,i+2));
            }

            //开始播放
            sequencer.setSequence(seq);
            sequencer.setTempoInBPM(200);
            sequencer.start();
        }catch(Exception ex){ex.printStackTrace();}
    }

    //获知事件时在命令答应出字符串的事件处理程序
    public void controlChange(ShortMessage event){
        System.out.println("la");
    }


    public  MidiEvent makeEvent(int comd, int chan, int one, int two, int tick){
        MidiEvent event = null;
        try{
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);

        }catch (Exception e){}
        return event;
    }
}

