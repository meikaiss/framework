package com.android.framework.media.audiodemo;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.framework.media.audio.AudioRecordButton;
import com.android.framework.media.audio.MediaPlayManager;
import com.android.framework.media.R;
import com.android.framework.media.audio.Recorder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meikai on 16/3/22.
 */
public class AudioDemoActivity extends Activity {

    private AudioRecordButton btnRecord;
    private ListView voiceList;
    private ArrayAdapter<Recorder> mAdapter;
    private List<Recorder> recorderList = new ArrayList<>();

    private AnimationDrawable animation;
    private View voiceAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_demo);


        voiceList = (ListView) findViewById(R.id.voiceList);
        btnRecord = (AudioRecordButton) findViewById(R.id.btnRecord);
        btnRecord.setAudioRecordFinishListener(new MyAudioRecordFinishListener());

        mAdapter = new VoiceListAdapter(this, recorderList);
        voiceList.setAdapter(mAdapter);
        voiceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (animation != null) {
                    voiceAnim
                            .setBackgroundResource(R.drawable.icon_voice_ripple);
                    voiceAnim = null;
                }
                voiceAnim = view.findViewById(R.id.voiceAnim);
                voiceAnim.setBackgroundResource(R.drawable.anim_play_audio);
                animation = (AnimationDrawable) voiceAnim.getBackground();
                animation.start();

                MediaPlayManager.playSound(recorderList.get(position).getFilePath(),
                        new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                voiceAnim
                                        .setBackgroundResource(R.drawable.icon_voice_ripple);
                            }
                        });

            }
        });
    }

    class MyAudioRecordFinishListener implements AudioRecordButton.AudioRecordFinishListener {

        @Override
        public void onFinish(float second, String filePath) {

            Recorder recorder = new Recorder(second, filePath);

            recorderList.add(recorder);
            mAdapter.notifyDataSetChanged();
            voiceList.setSelection(recorderList.size() - 1);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaPlayManager.release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MediaPlayManager.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaPlayManager.resume();
    }

}