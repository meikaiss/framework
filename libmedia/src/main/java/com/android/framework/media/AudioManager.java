package com.android.framework.media;

import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by meikai on 16/3/22.
 */
public class AudioManager {

    private MediaRecorder mediaRecorder;
    private String dir;
    private String currentFilePath;

    private static AudioManager audioInstance;

    public boolean isPrepared = false;

    private AudioManager(String dir) {
        this.dir = dir;
    }

    public interface AudioStateChangeListener {
        void wellPrepared();
    }

    public AudioStateChangeListener audioStateChangeListener;

    public void setOnAudioStateChangeListener(AudioStateChangeListener listener) {
        audioStateChangeListener = listener;
    }

    public static AudioManager getInstance(String dir) {
        if (audioInstance == null) {
            synchronized (AudioManager.class) {
                if (audioInstance == null) {
                    audioInstance = new AudioManager(dir);
                }
            }
        }
        return audioInstance;
    }

    public void prepareAudio() {
        try {
            isPrepared = false;
            File fileDir = new File(dir);
            if (!fileDir.exists())
                fileDir.mkdirs();
            String fileName = generateFileName();
            File file = new File(fileDir, fileName);

            currentFilePath = file.getAbsolutePath();

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setOutputFile(file.getAbsolutePath());
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

            mediaRecorder.prepare();
            mediaRecorder.start();

            isPrepared = true;

            if (audioStateChangeListener != null) {
                audioStateChangeListener.wellPrepared();
            }
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }

    public String getAudioFileExtension() {
        return ".aac";
    }

    /**
     * @return
     */
    private String generateFileName() {
        return UUID.randomUUID().toString() + getAudioFileExtension();
    }

    public int getVoiceLevel(int maxLevel) {
        if (isPrepared) {
            try {
                return maxLevel * mediaRecorder.getMaxAmplitude() / 32768 + 1;
            } catch (Exception e) {
            }
        }
        return 1;
    }

    public void release() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;

    }

    public void cancel() {
        release();
        if (currentFilePath != null) {
            File file = new File(currentFilePath);
            file.delete();
            currentFilePath = null;
        }
    }

    public String getCurrentPath() {
        return currentFilePath;
    }

    public static String getSourceFolderPath() {
        return Environment.getExternalStorageDirectory() + "/cache_audio";
    }
}

