package com.example.rythmgame;

import java.util.ArrayList;
import java.util.List;

public class Song {

    private int id;
    private String name;
    private String author;
    private int image;
    private int song;
    private List<NoteTiming> noteTimings;

    public Song(int id, String name, String author, int image, int song) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.image = image;
        this.song = song;
        this.noteTimings = new ArrayList<>();
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

    public List<NoteTiming> getNoteTimings() {
        return noteTimings;
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

    public void setNoteTimings(List<NoteTiming> noteTimings) {
        this.noteTimings = noteTimings;
    }
}
