package com.example.rythmgame;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class GameNote {

    // Константы
    private int MAX_SCALE; // Максимальный размер ноты
    private int MIN_SCALE;  // Минимальный размер ноты

    private final int MIN_POSITION_X;   // Минимальное значение расположения по оси X
    private final int MIN_POSITION_Y;   // Минимальное значение расположения по оси Y
    private final int MAX_POSITION_X; // Максимальное значение расположения по оси X
    private final int MAX_POSITION_Y; // Максимальное значение расположения по оси Y

    // Элементы ноты
    private final RelativeLayout NoteContainer;   // Ограничивающий контейнер
    private final View NoteRing;  // Сужающееся кольцо ноты
    private final Button NoteButton;  // Нота

    private final RelativeLayout NoteContainerParent; // Родительский контейнер игры

    private long clickTime;  // Время, за которое нажата нота

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
        this.MIN_POSITION_X = (int) (0 + (noteContainerParentWidth * 0.2));
        this.MIN_POSITION_Y = (int) (0 + (noteContainerParentHeight * 0.2));
        this.MAX_POSITION_X = (int) (noteContainerParentWidth - (noteContainerParentWidth * 0.2));
        this.MAX_POSITION_Y = (int) (noteContainerParentHeight - (noteContainerParentHeight * 0.2));
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
        this.NoteContainer.setTranslationX(x);
        this.NoteContainer.setTranslationY(y);

        // Добавление контейнера ноты в родительский Layout
        NoteContainerParent.addView(this.NoteContainer);

        startClickTimer();
    }

    // Удаление ноты
    public void delete() {
        RelativeLayout NoteContainerParent = (RelativeLayout) this.NoteContainer.getParent();
        NoteContainerParent.removeView(NoteContainer);
    }

    // Запуск таймера для подсчета очков
    private void startClickTimer() {
        GameTimer.start(1250, 1,
                this::setClickTime, () -> setClickTime(0));
    }
}
