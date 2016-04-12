package com.android.framework.media;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.lang.ref.WeakReference;

/**
 * Created by meikai on 16/3/22.
 */
public class AudioRecordButton extends Button {
    private static final int STATE_NORMAL = 1;
    private static final int STATE_RECORDING = 2;
    private static final int STATE_WANT_CANCEL = 3;

    private static final int DISTANCE_CANCEL_Y = 50;

    private int currentState = STATE_NORMAL;
    private boolean isRecording = false;
    private AudioRecordDialog dialogManager;
    private static AudioRecordManager audioRecordManager;

    private static float mTime;
    private boolean isReady = false;

    public AudioRecordButton(Context context) {
        this(context, null);
    }

    public AudioRecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        dialogManager = new AudioRecordDialog(getContext());

        String dir = AudioRecordManager.getSourceFolderPath();
        audioRecordManager = AudioRecordManager.getInstance(dir);
        audioRecordManager
                .setOnAudioStateChangeListener(new MyOnAudioStateChangeListener());

        setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                isReady = true;
                audioRecordManager.prepareAudio();
                return false;
            }
        });
    }

    class MyOnAudioStateChangeListener implements AudioRecordManager.AudioStateChangeListener {

        @Override
        public void wellPrepared() {
            mHandler.sendEmptyMessage(MSG_AUDIO_PREPARED);

        }
    }

    public interface AudioRecordFinishListener {
        void onFinish(float second, String filePath);
    }

    private AudioRecordFinishListener audioRecordFinishListener;

    public void setAudioRecordFinishListener(AudioRecordFinishListener listener) {
        audioRecordFinishListener = listener;
    }

    private Runnable getVolumeRunnable = new Runnable() {

        @Override
        public void run() {

            while (isRecording) {
                try {
                    Thread.sleep(100);
                    mTime += 0.1f;
                    mHandler.sendEmptyMessage(MSG_VOLUME_CHANGED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    };

    private static final int MSG_AUDIO_PREPARED = 0x110;
    private static final int MSG_VOLUME_CHANGED = 0x111;
    private static final int MSG_DIALOG_DISMISS = 0x112;

    public Handler mHandler = new StaticHandler(this);

    private static class StaticHandler extends Handler {

        WeakReference<AudioRecordButton> audioRecordButtonWeakReference;

        public StaticHandler(AudioRecordButton audioRecordButton) {
            this.audioRecordButtonWeakReference = new WeakReference<>(audioRecordButton);
        }

        @Override
        public void handleMessage(Message msg) {
            AudioRecordButton audioRecordButton = audioRecordButtonWeakReference.get();
            if (audioRecordButton == null) {
                return;
            }
            switch (msg.what) {
                case MSG_AUDIO_PREPARED:
                    audioRecordButton.dialogManager.showDialog();
                    audioRecordButton.isRecording = true;

                    new Thread(audioRecordButton.getVolumeRunnable).start();

                    break;
                case MSG_VOLUME_CHANGED:
                    audioRecordButton.dialogManager.updateVolumeLevel(audioRecordButton.audioRecordManager.getVoiceLevel(7));
                    break;
                case MSG_DIALOG_DISMISS:
                    mTime = 0;
                    audioRecordManager.cancel();
                    audioRecordButton.dialogManager.dismissDialog();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.e("__onTouchEvent" , "ACTION_DOWN, isReady"+isReady+ ", mTime="+mTime);
                mTime = 0;
                Log.e("__onTouchEvent" , "ACTION_DOWN, isReady"+isReady+ ", 之后  mTime="+mTime);
                changeState(STATE_RECORDING);
                break;
            case MotionEvent.ACTION_MOVE:

                if (isRecording) {
                    if (wantCancel(x, y)) {
                        changeState(STATE_WANT_CANCEL);
                        dialogManager.stateWantCancel();
                    } else {
                        changeState(STATE_RECORDING);
                        dialogManager.stateRecording();
                    }
                }

                break;

            case MotionEvent.ACTION_UP:
                Log.e("__onTouchEvent" , "ACTION_UP, isReady"+isReady+ ", mTime="+mTime);
                if (!isReady) {
                    resetState();
                    return super.onTouchEvent(event);
                }
                if (!isRecording || mTime < 0.8f) {
                    dialogManager.stateLengthShort();
//                    audioRecordManager.cancel();
                    mHandler.sendEmptyMessageDelayed(MSG_DIALOG_DISMISS, 1500);
                } else if (currentState == STATE_RECORDING) {
                    dialogManager.dismissDialog();
                    audioRecordManager.release();

                    // callbackToActivity
                    if (audioRecordFinishListener != null) {
                        audioRecordFinishListener.onFinish(mTime,
                                audioRecordManager.getCurrentPath());
                    }

                } else if (currentState == STATE_WANT_CANCEL) {
                    dialogManager.dismissDialog();
                    audioRecordManager.cancel();

                }
                resetState();
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void resetState() {

        isRecording = false;
        isReady = false;
        changeState(STATE_NORMAL);
        mTime = 0;
    }

    private boolean wantCancel(int x, int y) {
        if (x < 0 || x > getWidth()) {
            return true;
        }
        if (y < -DISTANCE_CANCEL_Y || y > getHeight() + DISTANCE_CANCEL_Y) {
            return true;
        }
        return false;
    }

    private void changeState(int state) {

        if (currentState != state) {
            currentState = state;
            switch (state) {
                case STATE_NORMAL:
                    setBackgroundResource(R.drawable.btn_recorder_normal);
                    setText(R.string.btn_recorder_normal);

                    break;
                case STATE_RECORDING:
                    setBackgroundResource(R.drawable.btn_recorder_normal);
                    setText(R.string.btn_recorder_recording);
                    if (isRecording) {
                        dialogManager.stateRecording();
                    }
                    break;
                case STATE_WANT_CANCEL:
                    setBackgroundResource(R.drawable.btn_recorder_normal);
                    setText(R.string.btn_recorder_want_cancel);
                    dialogManager.stateWantCancel();
                    break;

                default:
                    break;
            }
        }
    }

}
