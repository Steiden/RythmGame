package com.example.rythmgame;

import android.content.Context;
import android.content.Intent;

public class GameTransitionHelper {
    // Переход к активити Main
    public static void startMainActivity(Context context) {
        context.startActivity(new Intent(context.getApplicationContext(), MainActivity.class));
    }

    // Переход к активити Settings
    public static void startSettingsActivity(Context context) {
        context.startActivity(new Intent(context.getApplicationContext(), SettingsActivity.class));
    }

    // Переход к активити ChooseSong
    public static void startChooseSongActivity(Context context) {
        context.startActivity(new Intent(context.getApplicationContext(), ChooseSongActivity.class));
    }

    // Переход к активити ChooseLevel
    public static void startLevelActivity(Context context) {
        context.startActivity(new Intent(context.getApplicationContext(), ChooseLevelActivity.class));
    }

    // Переход к активити Game
    public static void startGameActivity(Context context) {
        context.startActivity(new Intent(context.getApplicationContext(), Game.class));
    }
}
