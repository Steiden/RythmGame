package com.example.rythmgame;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class GameNote {

    // Константы
    private final int MAX_SCALE = 175; // Максимальный размер ноты
    private final int MIN_SCALE = 50;  // Минимальный размер ноты
    private final int MAX_POSITION_X = 250; // Максимальное значение расположения по оси X
    private final int MAX_POSITION_Y = 500; // Максимальное значение расположения по оси Y

    // Элементы ноты
    private RelativeLayout NoteContainer;   // Ограничивающий контейнер
    private View NoteRing;  // Сужающееся кольцо ноты
    private Button NoteButton;  // Нота

    // Переменные для рамещения ноты
    private Context context;    // Контекст класса игры
    private final RelativeLayout NoteContainerParent; // Родительский контейнер игры

    // Конструктор
    public GameNote(Context context, RelativeLayout NoteContainerParent) {
        this.context = context;
        this.NoteContainerParent = NoteContainerParent;

        // Инициализация элементов нот
        NoteContainer = new RelativeLayout(context);
        NoteRing = new View(context);
        NoteButton = new Button(context);
    }

    // Геттеры
    public View getNoteRing() { return this.NoteRing; }
    public Button getNoteButton() { return this.NoteButton; }

    // Создание ноты
    public GameNote create() {
        // Преобразование макс. значений из px в dp
        int maxScaleValue = GameHelper.transformPxToDp(this.MAX_SCALE);
        int minScaleValue = GameHelper.transformPxToDp(this.MIN_SCALE);

        // Параметры контейнера
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(maxScaleValue, maxScaleValue);

        // Параметры кольца
        RelativeLayout.LayoutParams ringParams = new RelativeLayout.LayoutParams(maxScaleValue, maxScaleValue);
        ringParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        // Параметры ноты
        RelativeLayout.LayoutParams noteParams = new RelativeLayout.LayoutParams(minScaleValue, minScaleValue);
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
    public void place() {
        // Преобразование макс. позиций по X и Y в величину dp
        int maxPosXTransformed = GameHelper.transformPxToDp(this.MAX_POSITION_X);
        int maxPosYTransformed = GameHelper.transformPxToDp(this.MAX_POSITION_Y);

        // Установка смещения контейнера ноты в родителе
        NoteContainer.setTranslationX(GameHelper.getRandomPos(maxPosXTransformed));
        NoteContainer.setTranslationY(GameHelper.getRandomPos(maxPosYTransformed));

        // Добавление контейнера ноты в родительский Layout
        NoteContainerParent.addView(this.NoteContainer);
    }

    // Удаление ноты
    public void delete() {
        RelativeLayout NoteContainerParent = (RelativeLayout) this.NoteContainer.getParent();
        NoteContainerParent.removeView(this.NoteContainer);
    }
}
