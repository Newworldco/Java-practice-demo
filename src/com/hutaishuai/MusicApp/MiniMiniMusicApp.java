package com.hutaishuai.MusicApp;
import javax.sound.midi.*;

public class MiniMiniMusicApp {
    public static void main(String[] args){
        MiniMiniMusicApp mini = new MiniMiniMusicApp();
        mini.play();
    }

    public void play(){
        try{
            // 建立一个播放器，并打开
            Sequencer player = MidiSystem.getSequencer();
            player.open();

            // 把Sequence当作单曲CD
            Sequence seq = new Sequence(Sequence.PPQ, 4);

            // 把track 当作单曲
            Track track = seq.createTrack();

            ShortMessage a = new ShortMessage();
            a.setMessage(144, 1, 34, 100);
            MidiEvent noteOn = new MidiEvent(a, 1);
            track.add(noteOn);

            ShortMessage b = new ShortMessage();
            b.setMessage(128, 1, 44, 100);
            MidiEvent noteOff = new MidiEvent(b, 3);
            track.add(noteOff);

            player.setSequence(seq);//把CD放到播放器上
            player.start();//开始播放
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
