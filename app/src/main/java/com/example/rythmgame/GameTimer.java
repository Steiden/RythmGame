package com.example.rythmgame;

import android.os.CountDownTimer;

public class GameTimer {
    private static CountDownTimer timer;

    public interface OnTickFunction {
        void onTick(long millisUntilFinished);
    }
    public interface OnFinishFunction {
        void onFinish();
    }

    public static CountDownTimer start(long duration, long interval, OnTickFunction actionOnTick, OnFinishFunction actionOnFinish) {
        timer = new CountDownTimer(duration, interval) {

            @Override
            public void onTick(long millisUntilFinished) {
                actionOnTick.onTick(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                actionOnFinish.onFinish();
            }
        };

        timer.start();

        return timer;
    }

    public void stop() {
        if(timer != null) {
            timer.cancel();
        }
    }
}
