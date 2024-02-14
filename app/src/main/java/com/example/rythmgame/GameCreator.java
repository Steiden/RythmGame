package com.example.rythmgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

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

            // Получение позиции нажатия
            float x = event.getX();
            float y = event.getY();
            float[] positions = new float[2];
            positions[0] = x;
            positions[1] = y;

            // Запись позиций нажатия
            noteTimingStorage.put(millisToEnd, positions);

            // Инкрементирование количества созданных нот и их вывод
            noteCount++;
            noteCountTextView.setText("notes: " + noteCount);

            return true;
        });

        // Запуск трекбара
        GameHelper.startTrackbar(this.trackbar, songMusic.getDuration());

        // Запуск музыки
        songMusic.setOnCompletionListener(mp -> songMusic.release());
        songMusic.start();

        // Запуск таймера
        new CountDownTimer(this.songMusic.getDuration(), 1) {
            public void onTick(long millisUntilFinished) { setMillisToEnd(millisUntilFinished); }
            public void onFinish() { saveData(); }
        }.start();
    }

    public void closeCreatingGame() {
        // Завершение музыки
        songMusic.release();

        // Переход на следующую активити
        GameTransitionHelper.startChooseLevelActivity(context);
    }

    private void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("noteTimingStorage.obj"))) {
            out.writeObject(noteTimingStorage);
            Log.println(Log.INFO, "Success", "Saving note timing storage");
        } catch (IOException ex) {
            Log.e("Saving note timing storage", "Error: " + ex.getMessage());
        }
    }
}
