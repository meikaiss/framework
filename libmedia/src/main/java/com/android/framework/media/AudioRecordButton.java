package com.android.framework.media;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

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
    private AudioManager audioManager;

    private float mTime;
    private boolean isReady = false;

    public AudioRecordButton(Context context) {
        this(context, null);
    }

    public AudioRecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        dialogManager = new AudioRecordDialog(getContext());

        String dir = AudioManager.getSourceFolderPath();
        audioManager = AudioManager.getInstance(dir);
        audioManager
                .setOnAudioStateChangeListener(new MyOnAudioStateChangeListener());

        setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                isReady = true;
                audioManager.prepareAudio();
                return false;
            }
        });
    }

    class MyOnAudioStateChangeListener implements AudioManager.AudioStateChangeListener {

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
                    mHandler.sendEmptyMessage(MSG_VOLUME_CHAMGED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    };

    private static final int MSG_AUDIO_PREPARED = 0x110;
    private static final int MSG_VOLUME_CHAMGED = 0x111;
    private static final int MSG_DIALOG_DISMISS = 0x112;

    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_AUDIO_PREPARED:
                    dialogManager.showDialog();
                    isRecording = true;

                    new Thread(getVolumeRunnable).start();

                    break;
                case MSG_VOLUME_CHAMGED:
                    dialogManager.updateVolumeLevel(audioManager.getVoiceLevel(7));
                    break;
                case MSG_DIALOG_DISMISS:
                    dialogManager.dismissDialog();

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
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
                if (!isReady) {
                    resetState();
                    return super.onTouchEvent(event);
                }
                if (!isRecording || mTime < 0.6f) {
                    dialogManager.stateLengthShort();
                    audioManager.cancel();
                    mHandler.sendEmptyMessageDelayed(MSG_DIALOG_DISMISS, 1300);
                } else if (currentState == STATE_RECORDING) {
                    dialogManager.dismissDialog();
                    audioManager.release();

                    // callbackToActivity
                    if (audioRecordFinishListener != null) {
                        audioRecordFinishListener.onFinish(mTime,
                                audioManager.getCurrentPath());
                    }

                } else if (currentState == STATE_WANT_CANCEL) {
                    dialogManager.dismissDialog();
                    audioManager.cancel();

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
