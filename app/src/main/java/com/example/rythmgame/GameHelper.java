package com.example.rythmgame;

import android.animation.ValueAnimator;
import android.util.DisplayMetrics;
import android.util.Log;
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

    // Статичные переменные
    public static final DisplayMetrics displayMetrics = GamePlay.context.getResources().getDisplayMetrics();
    public static final int screenWidth = transformPxToDp(getScreenWidth());
    public static final int screenHeight = transformPxToDp(getScreenHeight());

    // Получение случайной позиции
    public static int getRandomPos(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1);
    }

    // Преобразование из px в dp
    public static int transformPxToDp(int px) {
        return px / (displayMetrics.densityDpi / 160);
    }

    // Преобразование из dp в px
    public static int transformDpToPx(int dp) {
        return dp * displayMetrics.densityDpi / 160;
    }

    // Получение ширины экрана устройства
    private static int getScreenWidth() {
        return GamePlay.context.getResources().getDisplayMetrics().widthPixels;
    }

    // Получение высоты экрана устройства
    private static int getScreenHeight() {
        return GamePlay.context.getResources().getDisplayMetrics().heightPixels;
    }

    public static void startTrackbar(View trackbar, int songDuration) {
        // Настройка и запуск анимации трекбара
        ValueAnimator trackbarAnimator = ValueAnimator.ofInt(0,
                        GameHelper.transformDpToPx((int) (GameHelper.screenWidth - (GameHelper.screenWidth * 0.2))))
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
}