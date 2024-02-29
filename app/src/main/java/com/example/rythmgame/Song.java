package com.example.rythmgame;

import java.util.HashMap;
import java.util.List;

public class Song {

    private int id;
    private String name;
    private String author;
    private int image;
    private int song;
    private HashMap<Integer, List<NoteTiming>> difficults;
//    private List<NoteTiming> noteTimings;

    // TODO: Здесь теперь вместо единичной сложности уровня будет HashMap для хранения списка NoteTiming на каждую сложность

    public Song(int id, String name, String author, int image, int song) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.image = image;
        this.song = song;
//        this.noteTimings = new ArrayList<>();
        this.difficults = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getImage() {
        return image;
    }

    public int getSong() {
        return song;
    }

//    public List<NoteTiming> getNoteTimings() {
//        return noteTimings;
//    }


    public HashMap<Integer, List<NoteTiming>> getDifficults() {
        return difficults;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setSong(int song) {
        this.song = song;
    }

//    public void setNoteTimings(List<NoteTiming> noteTimings) {
//        this.noteTimings = noteTimings;
//    }

    public void setDifficults(HashMap<Integer, List<NoteTiming>> difficults) {
        this.difficults = difficults;
    }
}
