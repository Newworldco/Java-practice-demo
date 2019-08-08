package com.hutaishuai.Colloections_chapter16;

import java.util.*;
import java.io.*;

public class Jukebox3 {

    ArrayList<Song> songList = new ArrayList<Song>();
    public static void main(String [] args){
        new Jukebox3().go();
    }

    public void go(){
        getSongs();
        System.out.println(songList);
        Collections.sort(songList);
        System.out.println(songList);

        ArtisCompare artisCompare = new ArtisCompare();
        Collections.sort(songList, artisCompare);
    }

    // 读取文件，获得歌曲，每个歌曲都要进行格式清洗
    void getSongs(){
        try {
            File file = new File("SongList.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null){
                addSong(line);
            }
        }catch (Exception ex){ex.printStackTrace();}
    }

    void addSong(String lineToParse){
        String[] tokens = lineToParse.split("/");

        Song nextSong = new Song(tokens[0], tokens[1], tokens[2], tokens[3]);
        songList.add(nextSong);
    }

    //创建并实现comparator
    class ArtisCompare implements Comparator<Song>{
        public int compare (Song one, Song two){
            return one.getArtist().compareTo(two.getArtist());
        }
    }
}
