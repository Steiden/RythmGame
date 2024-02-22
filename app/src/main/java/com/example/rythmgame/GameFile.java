package com.example.rythmgame;

import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public class GameFile {
    private File file;

    public GameFile(File file) {
        this.file = file;
    }

    private boolean createFileIfNotExists() {
        try {
            if (!this.file.exists()) {
                if (!this.file.createNewFile()) {
                    Log.d("Creating new file", "File doesn't create");
                    return true;
                } else {
                    Log.d("Creating new file", "File was create");
                }
            }
            return false;
        } catch (Exception ex) {
            Log.e("Create file if don't exists", ex.getMessage());
            return false;
        }
    }

    public boolean saveData(Object data) {
        try {
            // Преобразование данных в json формат
            Gson gson = new Gson();
            String dataJson = gson.toJson(data);

            // Создание файла, если не существует
            if(!createFileIfNotExists()) return false;

            // Запись данных в файл
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(dataJson);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            return true;
        } catch (Exception ex) {
            Log.e("Saving data into file", ex.getMessage());
            return false;
        }
    }

    public Object loadData(Type type) {
        try {
            // Если файл не найден, вернуть null
            if (createFileIfNotExists()) return null;

            // Чтение данных
            try (FileReader reader = new FileReader(this.file)) {
                Gson gson = new Gson();
                return gson.fromJson(reader, type);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception ex) {
            Log.e("Loading data from file", ex.getMessage());
            return false;
        }
    }
}
