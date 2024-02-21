package com.example.rythmgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.drm.DrmStore;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameCreator {

    // Переменные из активити создания игры
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    private final RelativeLayout gameContainer;
    private final View trackbar;
    private final TextView noteCountTextView;
    private final MediaPlayer songMusic;

    private int noteCount;  // Общее количество созданных нот
    private long millisToEnd;   // Количество оставшихся миллисекунд до окончания создания уровня
    private final HashMap<Long, float[]> noteTimingStorage;  // Хранилище времени, в которое должна появиться определенная нота

    public GameCreator(Context context) {
        GameCreator.context = context;
        this.gameContainer = GameCreatorActivity.gameContainer;
        this.trackbar = GameCreatorActivity.trackbar;
        this.noteCountTextView = GameCreatorActivity.noteCount;
        this.songMusic = GameCreatorActivity.songMusic;

        noteCount = 0;
        millisToEnd = this.songMusic.getDuration();
        noteTimingStorage = new HashMap<Long, float[]>();
    }

    public void setMillisToEnd(long value) { this.millisToEnd = value; }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    public void startCreatingGame() {
        // При клике на контейнер нот, сохранять время и положение нажатия
        gameContainer.setOnTouchListener((v, event) -> {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // Получение и запись позиции нажатия
                float[] positions = new float[2];
                positions[0] = event.getX();
                positions[1] = event.getY();

                // Запись позиций нажатия
                noteTimingStorage.put(millisToEnd, positions);

                // Инкрементирование количества созданных нот и их вывод
                noteCount++;
                noteCountTextView.setText("notes: " + noteCount);

                return true;
            }

            return false;
        });

        // Запуск трекбара
        GameHelper.startTrackbar(this.trackbar, songMusic.getDuration());

        // Запуск музыки
        songMusic.setOnCompletionListener(l -> closeCreatingGame());
        songMusic.start();

        // Запуск таймера
        GameTimer.start(this.songMusic.getDuration(), 1,
                this::setMillisToEnd, this::saveData);
    }

    public void closeCreatingGame() {
        // Завершение музыки
        songMusic.release();

        // Сохранение данных
        saveData();

        // Переход на следующую активити
        GameTransitionHelper.startChooseLevelActivity(context);
    }

    private void saveData() {

    }

    private void addSongInBackground(Song song) {
    }
}
