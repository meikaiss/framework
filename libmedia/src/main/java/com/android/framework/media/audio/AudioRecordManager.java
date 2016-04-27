package com.android.framework.media.audio;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by meikai on 16/3/22.
 */
public class AudioRecordManager {

    private MediaRecorder mediaRecorder;
    private String dir;
    private String currentFilePath;

    private static AudioRecordManager audioInstance;

    public boolean isPrepared = false;

    private AudioRecordManager(String dir) {
        this.dir = dir;
    }

    public interface AudioStateChangeListener {
        void wellPrepared();
    }

    public AudioStateChangeListener audioStateChangeListener;

    public void setOnAudioStateChangeListener(AudioStateChangeListener listener) {
        audioStateChangeListener = listener;
    }

    public static AudioRecordManager getInstance(String dir) {
        if (audioInstance == null) {
            synchronized (AudioRecordManager.class) {
                if (audioInstance == null) {
                    audioInstance = new AudioRecordManager(dir);
                }
            }
        }
        return audioInstance;
    }

    public void prepareAudio() {

        File fileDir = new File(dir);
        if (!fileDir.exists())
            fileDir.mkdirs();
        String fileName = generateFileName();
        File file = new File(fileDir, fileName);

        currentFilePath = file.getAbsolutePath();

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        mediaRecorder.setOutputFile(file.getAbsolutePath());
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            isPrepared = true;
        } catch (IOException e) {
            Log.e("prepareAudio", "Error when preparing or starting recorder", e);
            return;
        }

        if (audioStateChangeListener != null) {
            audioStateChangeListener.wellPrepared();
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
        if( mediaRecorder != null){
            try{
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
            }catch (IllegalStateException e){
                Log.e("AudioRecordManager", "release");
            }
        }
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

