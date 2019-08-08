package com.hutaishuai.Colloections_chapter16;

import java.io.ObjectInputStream;
import java.util.Comparator;

public class Song implements Comparable<Song>{
    String title;
    String artist;
    String rating;
    String bpm;

    public boolean equals(Object aSong){
        Song s = (Song) aSong;
        return getTitle().equals(s.getTitle());
    }

    public int hashCode(){
        return title.hashCode();
    }

    Song (String t, String a, String r, String b){
        title = t;
        artist = a;
        rating = r;
        bpm = b;
    }

    public String getTitle(){
        return title;
    }

    public String getArtist(){
        return artist;
    }

    public String getRating(){
        return rating;
    }

    public String getBpm(){
        return bpm;
    }

    public String toString(){
        return  title;
    }


    public int compareTo(Song other){

        return title.compareTo(other.getTitle());
    }



}
