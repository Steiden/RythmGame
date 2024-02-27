package com.example.rythmgame;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GameFileHelper {

    public static List<Song> getSongsList(Context context) {
        try {
            File file = new File(context.getExternalFilesDir(null), "songsList.json");
            GameFile<List<Song>> gameFile = new GameFile<>(file);

            return gameFile.loadDataList();
        } catch (Exception ex) {
            Log.e("Loading data from file", ex.getMessage());
            return null;
        }
    }

    public static void saveSongsList(Context context, List<Song> songsList) {
        try {
            File file = new File(context.getExternalFilesDir(null), "songsList.json");
            GameFile<List<Song>> gameFile = new GameFile<>(file);

            gameFile.saveData(songsList);
        } catch (Exception ex) {
            Log.e("Loading data from file", ex.getMessage());
        }
    }

    public static Song getSelectedSong(Context context) {
        try {
            File file = new File(context.getExternalFilesDir(null), "selectedSong.json");
            GameFile<Song> gameFile = new GameFile<>(file);

            return gameFile.loadData(Song.class);
        } catch (Exception ex) {
            Log.e("Loading data from file", ex.getMessage());
            return null;
        }
    }

    public static void saveSelectedSong(Context context, Song song) {
        try {
            File file = new File(context.getExternalFilesDir(null), "selectedSong.json");
            GameFile<Song> gameFile = new GameFile<>(file);

            gameFile.saveData(song);
        } catch (Exception ex) {
            Log.e("Loading data from file", ex.getMessage());
        }
    }
}
