package com.example.rythmgame;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

//import net.beadsproject.beads.analysis.BeatDetect;
//import net.beadsproject.beads.analysis.FeatureExtractor;
//import net.beadsproject.beads.analysis.WaveformAnalyzer;
//import net.beadsproject.beads.core.AudioContext;
//import net.beadsproject.beads.core.AudioIO;
//import net.beadsproject.beads.data.audiofile.AudioFileReader;

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

//    public static int getBpm(MediaPlayer music) {
//        AudioContext ac = new AudioContext(new AudioIO.Default());
//        AudioFileReader afr = new AudioFileReader(ac, 4096);
//        String audioFilePath = AUDIO_FILE_NAME;
//
//        try {
//            AssetFileDescriptor afd = getAssets().openFd(audioFilePath);
//            float durationInSeconds = afd.getLength() / (float) afd.getSampleRate() / afd.getFormat().getChannelCount() / 2;
//            BeatDetect beatDetect = new BeatDetect(durationInSeconds);
//            WaveformAnalyzer waveformAnalyzer = new WaveformAnalyzer(ac, 70);
//
//            ac.out.addDependent(waveformAnalyzer);
//            ac.start();
//
//            MediaPlayer mediaPlayer = new MediaPlayer();
//            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//            mediaPlayer.setOnCompletionListener(mp -> {
//                float bpm = beatDetect.getBPM();
//                System.out.println("BPM аудиофайла: " + bpm);
//                mediaPlayer.release();
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}