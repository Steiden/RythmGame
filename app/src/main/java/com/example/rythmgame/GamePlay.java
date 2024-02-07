package com.example.rythmgame;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class GamePlay {
    // Игровые переменные
    private GameNote actualGameNote;  // Текущая нота
    private int score;  // Счет
    private float accuracy; // Точность

    // Переменные для анимаций
    ObjectAnimator noteRingAnimationScaleX; // Анимация сужения кольца ноты по оси X
    ObjectAnimator noteRingAnimationScaleY; // Анимация сужения кольца ноты по оси Y

    // Переменные для работы в активити
    @SuppressLint("StaticFieldLeak")
    public static Context context;  // Контекст активити игры
    private final RelativeLayout NoteContainerParent;   // Контейнер для размещения нот
    private final TextView scoreTextView;   // TextView для счета
    private final TextView accuracyTextView;    // TextView для точности

    // Конструктор класса
    public GamePlay(Context context, RelativeLayout NoteContainerParent, TextView scoreTextView, TextView accuracyTextView) {
        GamePlay.context = context;
        this.NoteContainerParent = NoteContainerParent;
        this.scoreTextView = scoreTextView;
        this.accuracyTextView = accuracyTextView;
        this.score = 0;
        this.accuracy = 100;
    }

    // Сеттеры
    private void setScore(int score) {
        this.score += score;
    }
    private void setAccuracy(float value) {
        this.accuracy = (float) Math.round((this.accuracy + value) / 2);
    }

    // Создание и размещение ноты
    public void createAndPlaceNote() {
        // Установка текущей ноты
        this.actualGameNote = new GameNote(context, this.NoteContainerParent);

        // Создание анимации сужения кольца ноты
        createRingAnimation();

        // Добавление действий при клике на ноту
        addActionsOnClickNote();

        // Создание и размещение текущей ноты
        this.actualGameNote.create().place();

        // Запуск анимации сужения кольца ноты
        startRingAnimation();
    }

    // Удаление предыдущей ноты, создание и размещение новой ноты
    public void deleteNoteAndCreateAndPlaceNew() {
        // Удаление текущей ноты
        this.actualGameNote.delete();

        // Создание и размещение ноты
        createAndPlaceNote();
    }

    // Создание анимации сужения кольца ноты
    private void createRingAnimation() {
        // Настройка анимации сужения кольца ноты по X
        this.noteRingAnimationScaleX = ObjectAnimator.ofFloat(this.actualGameNote.getNoteRing(), "scaleX", 1f, 0.40f).setDuration(1250);
        this.noteRingAnimationScaleX.setInterpolator(new LinearInterpolator());

        // Настройка анимации сужения кольца ноты по Y
        this.noteRingAnimationScaleY = ObjectAnimator.ofFloat(this.actualGameNote.getNoteRing(), "scaleY", 1f, 0.4f).setDuration(1250);
        this.noteRingAnimationScaleY.setInterpolator(new LinearInterpolator());

        // Удаление ноты по окончанию анимации
        this.noteRingAnimationScaleX.addListener(new Animator.AnimatorListener() {
            public void onAnimationStart(@NonNull Animator animation) {}
            public void onAnimationEnd(@NonNull Animator animation) {
                deleteNoteAndCreateAndPlaceNew();
            }
            public void onAnimationCancel(@NonNull Animator animation) {}
            public void onAnimationRepeat(@NonNull Animator animation) {}
        });
    }

    // Запуск анимации сужения кольца ноты
    private void startRingAnimation() {
        this.noteRingAnimationScaleY.start();
        this.noteRingAnimationScaleX.start();
    }

    // Добавление действий при клике на ноту
    @SuppressLint("SetTextI18n")
    private void addActionsOnClickNote() {
        this.actualGameNote.getNoteButton().setOnClickListener(v -> {
            // Получение времени нажатия на ноту
            long clickTime = this.actualGameNote.getClickTime();

            // Расчет количества очков и точности, исходя из времени нажатия на ноту
            if(clickTime >= 750 && clickTime <= 1250) {
                setScore(100);
                setAccuracy(10);
            }
            else if(clickTime >= 450 && clickTime < 750) {
                setScore(200);
                setAccuracy(33.3f);
            }
            else if(clickTime >= 250 && clickTime < 450) {
                setScore(300);
                setAccuracy(50);
            }
            else if(clickTime > 0 && clickTime < 250) {
                setScore(500);
                setAccuracy(100);
            }
            else {
                setScore(0);
                setAccuracy(0);
            }

            // Отображение статистики
            scoreTextView.setText("" + this.score);
            accuracyTextView.setText(this.accuracy + "%");

            // Анимация сужения кольца заканчивается
            this.noteRingAnimationScaleY.end();
            this.noteRingAnimationScaleX.end();
        });
    }
}
