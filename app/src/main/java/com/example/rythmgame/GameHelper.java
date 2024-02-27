package com.example.rythmgame;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

//import net.beadsproject.beads.analysis.BeatDetect;
//import net.beadsproject.beads.analysis.FeatureExtractor;
//import net.beadsproject.beads.analysis.WaveformAnalyzer;
//import net.beadsproject.beads.core.AudioContext;
//import net.beadsproject.beads.core.AudioIO;
//import net.beadsproject.beads.data.audiofile.AudioFileReader;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Random;

public class GameHelper {

    private Context context;
    private DisplayMetrics displayMetrics;

    public GameHelper(Context context) {
        this.context = context;
        this.displayMetrics = this.context.getResources().getDisplayMetrics();
    }

    // Получение случайной позиции
    public static int getRandomPos(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1);
    }

    // Преобразование из px в dp
    public int transformPxToDp(int px) {
        return px / (displayMetrics.densityDpi / 160);
    }

    // Преобразование из dp в px
    public int transformDpToPx(int dp) {
        return dp * displayMetrics.densityDpi / 160;
    }

    // Получение ширины экрана устройства
    public int getScreenWidth() {
        return transformPxToDp(this.context.getResources().getDisplayMetrics().widthPixels);
    }

    // Получение высоты экрана устройства
    public int getScreenHeight() {
        return transformPxToDp(this.context.getResources().getDisplayMetrics().heightPixels);
    }

    public void startTrackbar(View trackbar, int songDuration) {
        // Настройка и запуск анимации трекбара
        ValueAnimator trackbarAnimator = ValueAnimator.ofInt(0,
                        transformDpToPx((int) (getScreenWidth() - (getScreenWidth() * 0.2))))
                .setDuration(songDuration);
        trackbarAnimator.setInterpolator(new LinearInterpolator());

        trackbarAnimator.addUpdateListener(animation -> {
            // Получение текущего значения ширины трекбара
            int animatedValue = (int) animation.getAnimatedValue();

            // Установка текущих размеров трекбару
            ViewGroup.LayoutParams params = trackbar.getLayoutParams();
            params.width = animatedValue;
            trackbar.setLayoutParams(params);
        });

        // Запуск анимации трекбара
        trackbarAnimator.start();
    }

    public static boolean inRange(NoteTiming noteTiming, long millisUntilFinished) {
        long noteTime = noteTiming.getTime();
        return millisUntilFinished > (noteTime - 20) && millisUntilFinished < (noteTime + 20);
    }
}