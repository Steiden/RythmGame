package com.example.rythmgame;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class GamePlay {
    // Игровые переменные
    private GameNote actualGameNote;  // Текущая нота
    private int score;  // Счет
    private float accuracy; // Точность
    private int scoreToIncrease;    // Счет прибавляемый
    private float accuracyToIncrease;    // Точность приблавляемая

    // Переменные для анимаций
    ObjectAnimator noteRingAnimationScaleX; // Анимация сужения кольца ноты по оси X
    ObjectAnimator noteRingAnimationScaleY; // Анимация сужения кольца ноты по оси Y

    // Переменные для работы в активити
    @SuppressLint("StaticFieldLeak")
    public static Context context;  // Контекст активити игры
    private final RelativeLayout NoteContainerParent;   // Контейнер для размещения нот
    private final TextView scoreTextView;   // TextView для счета
    private final TextView accuracyTextView;    // TextView для точности
    private final View trackbar;    // View для продолжительности песни

    // Конструктор класса
    public GamePlay(Context context, RelativeLayout NoteContainerParent, TextView scoreTextView, TextView accuracyTextView, View trackbar) {
        GamePlay.context = context;
        this.NoteContainerParent = NoteContainerParent;
        this.scoreTextView = scoreTextView;
        this.accuracyTextView = accuracyTextView;
        this.trackbar = trackbar;
        this.score = 0;
        this.accuracy = 100;
    }

    // Сеттеры
    private void setScore(int score) {
        this.score += score;
        scoreToIncrease = 0;
    }

    private void setAccuracy(float value) {
        this.accuracy = (float) Math.round((this.accuracy + value) / 2 * 100) / 100;
        accuracyToIncrease = 0;
    }

    // Начало игры
    public void startGame() {
        // Запуск трекбара
        startTrackbar(60000);

        // Создание и размещение ноты
        createAndPlaceNote();
    }

    // Закрытие игры
    public void closeGame() {
        // Удаление анимаций
        noteRingAnimationScaleX.removeAllListeners();
        noteRingAnimationScaleY.removeAllListeners();

        // Удаление текущей ноты
        this.actualGameNote.delete();
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
            public void onAnimationStart(@NonNull Animator animation) {
            }

            public void onAnimationEnd(@NonNull Animator animation) {
                // Вызов событий при клике
                startVibration();

                // Установка точности и счета
                setScore(scoreToIncrease);
                setAccuracy(accuracyToIncrease);

                // Отображение статистики
                scoreTextView.setText("" + score);
                accuracyTextView.setText(accuracy + "%");

                // Удаление текущей ноты и размещение новой
                deleteNoteAndCreateAndPlaceNew();
            }

            public void onAnimationCancel(@NonNull Animator animation) {
            }

            public void onAnimationRepeat(@NonNull Animator animation) {
            }
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
            if (clickTime >= 750 && clickTime <= 1250) {
                scoreToIncrease = 100;
                accuracyToIncrease = 10;
            } else if (clickTime >= 450 && clickTime < 750) {
                scoreToIncrease = 200;
                accuracyToIncrease = 33.3f;
            } else if (clickTime >= 250 && clickTime < 450) {
                scoreToIncrease = 300;
                accuracyToIncrease = 50;
            } else if (clickTime > 0 && clickTime < 250) {
                scoreToIncrease = 500;
                accuracyToIncrease = 100;
            }

            // Анимация сужения кольца заканчивается
            this.noteRingAnimationScaleY.end();
            this.noteRingAnimationScaleX.end();
        });
    }

    // Запуск трекбара
    private void startTrackbar(int songDuration) {
        // Настройка и запуск анимации трекбара
        ObjectAnimator.ofFloat(trackbar,
                "scaleX", 1f, 1000.0f)
                .setDuration(songDuration)
                .start();
    }

    // Запуск вибрации
    private void startVibration() {
        if(GameActivity.vibrator.hasVibrator()) {
            GameActivity.vibrator.vibrate(100);
        }
    }
}
