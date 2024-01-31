package com.example.rythmgame;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.util.Random;

public class GameHelper {

    // Получение случайной позиции
    public static float getRandomPos(float max) {
        Random rand = new Random();
        return rand.nextFloat() * max;
    }

    // Преобразование из px в dp
    public static int transformPxToDp(int value) {
        // Преобразование макс. значений из px в dp
        DisplayMetrics displayMetrics = GamePlay.context.getResources().getDisplayMetrics();

        // Преобразование значения из величины px в dp
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, displayMetrics);
    }
}