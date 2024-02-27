package com.example.rythmgame;

import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class GameFile<T> {
    private final File file;

    public GameFile(File file) {
        this.file = file;
    }

    public boolean createFileIfNotExists() {
        try {
            if (!this.file.exists()) {
                if (!this.file.createNewFile()) {
                    Log.d("Creating new file", "File doesn't create");
                    return false;
                } else {
                    Log.d("Creating new file", "File was create");
                    return true;
                }
            }
            Log.d("Creating new file", "File was found");
            return true;
        } catch (Exception ex) {
            Log.e("Create file if don't exists", ex.getMessage());
            return false;
        }
    }

    public void saveData(T data) {
        try {
            // Создание файла, если не существует
            if(!createFileIfNotExists()) return;

            // Запись данных в файл
            try (FileWriter writer = new FileWriter(file)) {
                Gson gson = new Gson();
                writer.write(gson.toJson(data));

                Log.d("Saving data into file", "File saved successfully");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            Log.e("Saving data into file", ex.getMessage());
        }
    }

    public T loadData(Type  type) {
        try {
            // Если файл не найден, вернуть null
            if (!createFileIfNotExists()) return null;

            // Чтение данных
            try (FileReader reader = new FileReader(this.file)) {
                Gson gson = new Gson();
                Log.d("Loading data from file", "Data from file was found");

                return gson.fromJson(reader, type);
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        } catch (Exception ex) {
            Log.e("Loading data from file", ex.getMessage());
            return null;
        }
    }

    public T loadDataList() {
        try {
            // Если файл не найден, вернуть null
            if (!createFileIfNotExists()) return null;

            // Чтение данных
            try (FileReader reader = new FileReader(this.file)) {
                Gson gson = new Gson();
                Log.d("Loading data from file", "Data from file was found");

                return (T) Arrays.asList(gson.fromJson(reader, Song[].class));
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        } catch (Exception ex) {
            Log.e("Loading data from file", ex.getMessage());
            return null;
        }
    }
}
