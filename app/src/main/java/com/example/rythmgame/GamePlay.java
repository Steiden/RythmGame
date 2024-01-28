package com.example.rythmgame;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

public class GamePlay {
    private GameNote actualGameNote;  // Текущая нота

    public static Context context;
    private RelativeLayout NoteContainerParent;

    // Конструктор
    public GamePlay(Context context, RelativeLayout NoteContainerParent) {
        GamePlay.context = context;
        this.NoteContainerParent = NoteContainerParent;
    }

    // Создание и размещение ноты
    public void createAndPlaceNote() {
        // Создание экземпляра новой ноты
        GameNote gameNote = new GameNote(context, this.NoteContainerParent);

        // Установка текущей ноты
        this.actualGameNote = gameNote;

        // Настройка анимации сужения кольца ноты по X
        ObjectAnimator noteRingAnimationScaleX = ObjectAnimator.ofFloat(gameNote.getNoteRing(), "scaleX", 1f, 0.4f).setDuration(2000);
        noteRingAnimationScaleX.setInterpolator(new LinearInterpolator());

        // Настройка анимации сужения кольца ноты по Y
        ObjectAnimator noteRingAnimationScaleY = ObjectAnimator.ofFloat(gameNote.getNoteRing(), "scaleY", 1f, 0.4f).setDuration(2000);
        noteRingAnimationScaleY.setInterpolator(new LinearInterpolator());

        // Удаление ноты по окончанию анимации
        noteRingAnimationScaleX.addListener(new Animator.AnimatorListener() {
            public void onAnimationStart(@NonNull Animator animation) {}
            public void onAnimationEnd(@NonNull Animator animation) {
                deleteNoteAndCreateAndPlaceNew();
            }
            public void onAnimationCancel(@NonNull Animator animation) {}
            public void onAnimationRepeat(@NonNull Animator animation) {}
        });

        // Создание и размещение текущей ноты
        this.actualGameNote.create().place();

        // Запуск анимации сужения кольца ноты
        noteRingAnimationScaleX.start();
        noteRingAnimationScaleY.start();

        // Установка слушателя события по клику
        gameNote.getNoteButton().setOnClickListener(v -> {
            noteRingAnimationScaleX.end();
            noteRingAnimationScaleY.end();
        });
    }

    // Удаление предыдущей ноты,
    // создание и размещение новой ноты
    public void deleteNoteAndCreateAndPlaceNew() {
        // Удаление текущей ноты
        this.actualGameNote.delete();

        // Создание и размещение ноты
        createAndPlaceNote();
    }
}
