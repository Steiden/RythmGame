package com.example.rythmgame;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class GameNote {

    // Константы
    private final int MAX_SCALE; // Максимальный размер ноты
    private final int MIN_SCALE;  // Минимальный размер ноты
    private final int NOTE_DURATION = 1250;    // Длительность ноты на поле
    // ____________________________________________________


    // Элементы ноты
    private final RelativeLayout NoteContainer;   // Ограничивающий контейнер
    private final View NoteRing;  // Сужающееся кольцо ноты
    private final Button NoteButton;  // Нота
    private final RelativeLayout NoteContainerParent; // Родительский контейнер игры
    // ____________________________________________________


    private long clickTime;  // Время, за которое нажата нота


    // Переменные для анимаций
    ObjectAnimator noteRingAnimationScaleX; // Анимация сужения кольца ноты по оси X
    ObjectAnimator noteRingAnimationScaleY; // Анимация сужения кольца ноты по оси Y
    // ____________________________________________________


    // Счет и точности, прибавляемые к общим
    private int scoreToIncrease;    // Счет прибавляемый
    private float accuracyToIncrease;    // Точность приблавляемая
    // ____________________________________________________


    // Конструктор
    public GameNote(Context context, RelativeLayout NoteContainerParent) {
        // Переменные для рамещения ноты
        this.NoteContainerParent = NoteContainerParent;

        // Инициализация элементов нот
        NoteContainer = new RelativeLayout(context);
        NoteRing = new View(context);
        NoteButton = new Button(context);

        // Получение ширины и высоты родительского контейнера ноты
        int noteContainerParentWidth = this.NoteContainerParent.getWidth();
        int noteContainerParentHeight = this.NoteContainerParent.getHeight();

        GameHelper gameHelper = new GameHelper(context);
        this.MAX_SCALE = gameHelper.transformDpToPx(gameHelper.getScreenWidth() / 3);
        this.MIN_SCALE = gameHelper.transformDpToPx(gameHelper.getScreenWidth() / 9);

        // Установка макс и мин значения для расположения ноты по осям X и Y
        // Минимальное значение расположения по оси X
        int MIN_POSITION_X = (int) (0 + (noteContainerParentWidth * 0.2));
        // Минимальное значение расположения по оси Y
        int MIN_POSITION_Y = (int) (0 + (noteContainerParentHeight * 0.2));
        // Максимальное значение расположения по оси X
        int MAX_POSITION_X = (int) (noteContainerParentWidth - (noteContainerParentWidth * 0.2));
        // Максимальное значение расположения по оси Y
        int MAX_POSITION_Y = (int) (noteContainerParentHeight - (noteContainerParentHeight * 0.2));
    }

    // Геттеры
    public View getNoteRing() { return this.NoteRing; }
    public Button getNoteButton() { return this.NoteButton; }
    public long getClickTime() { return this.clickTime; }

    // Сеттеры
    public void setClickTime(long value) { this.clickTime = value; }

    // Создание ноты
    public GameNote create() {
        // Параметры контейнера
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(MAX_SCALE, MAX_SCALE);

        // Параметры кольца
        RelativeLayout.LayoutParams ringParams = new RelativeLayout.LayoutParams(MAX_SCALE, MAX_SCALE);
        ringParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        // Параметры ноты
        RelativeLayout.LayoutParams noteParams = new RelativeLayout.LayoutParams(MIN_SCALE, MIN_SCALE);
        noteParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        // Установка размеров контейнеру
        NoteContainer.setLayoutParams(layoutParams);

        // Установка параметров кольцу
        NoteRing.setLayoutParams(ringParams);
        NoteRing.setBackgroundResource(R.drawable.shape_ring);

        // Установка параметров ноте
        NoteButton.setLayoutParams(noteParams);
        NoteButton.setBackgroundResource(R.drawable.shape_oval);

        // Добавление кольца и ноты в контейнер
        NoteContainer.addView(NoteRing);
        NoteContainer.addView(NoteButton);

        return this;
    }

    // Размещение ноты
    public void place(float x, float y) {
        // Установка смещения контейнера ноты в родителе
        this.NoteContainer.setTranslationX((float) (x - x * 0.3));
        this.NoteContainer.setTranslationY((float) (y - y * 0.3));

        // Добавление контейнера ноты в родительский Layout
        NoteContainerParent.addView(this.NoteContainer);

        createRingAnimation();
        startRingAnimation();

        addActionsOnClickNote();

        startClickTimer();
    }

    // Удаление ноты
    public void delete() {
        RelativeLayout NoteContainerParent = (RelativeLayout) this.NoteContainer.getParent();
        NoteContainerParent.removeView(NoteContainer);
    }

    // Запуск таймера для подсчета очков
    private void startClickTimer() {
        GameTimer.start(this.NOTE_DURATION, 1,
                this::setClickTime, () -> setClickTime(0));
    }

    // Создание анимации сужения кольца ноты
    private void createRingAnimation() {
        // Настройка анимации сужения кольца ноты по X
        this.noteRingAnimationScaleX = ObjectAnimator.ofFloat(this.NoteRing, "scaleX",
                1f, 0.40f).setDuration(this.NOTE_DURATION);
        this.noteRingAnimationScaleX.setInterpolator(new LinearInterpolator());

        // Настройка анимации сужения кольца ноты по Y
        this.noteRingAnimationScaleY = ObjectAnimator.ofFloat(this.NoteRing, "scaleY",
                1f, 0.4f).setDuration(this.NOTE_DURATION);
        this.noteRingAnimationScaleY.setInterpolator(new LinearInterpolator());

        // Удаление ноты по окончанию анимации
        this.noteRingAnimationScaleX.addListener(new Animator.AnimatorListener() {
            public void onAnimationStart(@NonNull Animator animation) {
            }

            public void onAnimationEnd(@NonNull Animator animation) {
                // Установка точности и счета
                GamePlay.setScore(scoreToIncrease);
                GamePlay.setAccuracy(accuracyToIncrease);

                // Обнуление счета и точности к прибавке
                scoreToIncrease = 0;
                accuracyToIncrease = 0;

                // Удаление ноты
                delete();
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
        this.NoteButton.setOnClickListener(v -> {

            // Расчет количества очков и точности, исходя из времени нажатия на ноту
            if (clickTime >= 750 && clickTime <= this.NOTE_DURATION) {
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
            } else {
                scoreToIncrease = 0;
                accuracyToIncrease = 0;
            }

            // Запуск вибрации
            startVibration();

            // Проигрывание звука нажатия
            MediaPlayer osuSound = MediaPlayer.create(GamePlay.context, R.raw.osu_hit_sound);
            osuSound.setOnCompletionListener(mp -> osuSound.release());
            osuSound.start();

            // Анимация сужения кольца заканчивается
            this.noteRingAnimationScaleY.end();
            this.noteRingAnimationScaleX.end();
        });
    }

    // Запуск вибрации
    private void startVibration() {
        if (GameActivity.vibrator.hasVibrator()) {
            GameActivity.vibrator.vibrate(100);
        }
    }
}
