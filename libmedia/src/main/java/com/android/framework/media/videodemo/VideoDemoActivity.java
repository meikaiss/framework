package com.android.framework.media.videodemo;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.framework.media.R;

import java.io.IOException;

/**
 * Created by meikai on 16/4/27.
 */
public class VideoDemoActivity extends AppCompatActivity {

//    String videoUrl = "http://192.168.31.221/~meikai/Video/fd9b0b2cc80f44b58eef44ae807 799fd.low.mp4";
    String videoUrl = "http://192.168.31.221/~meikai/Video/output.mp4";

    Button btnStart;
    Button btnTest;

    TextureView textureView;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_demo);

        textureView = (TextureView) findViewById(R.id.video_texture_view);

        btnStart = (Button) findViewById(R.id.btn_play);
        btnTest = (Button) findViewById(R.id.btn_test);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer = new MediaPlayer();

                try {

                    mediaPlayer.setDataSource(videoUrl);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                    mediaPlayer.setOnBufferingUpdateListener(bufferingUpdateListener);
                    mediaPlayer.setOnCompletionListener(completionListener);
                    mediaPlayer.setOnPreparedListener(preparedListener);
                    mediaPlayer.setOnErrorListener(errorListener);

                    mediaPlayer.setSurface(new Surface(textureView.getSurfaceTexture()));

                    mediaPlayer.prepare();

                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VideoDemoActivity.this, "提示", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    MediaPlayer.OnBufferingUpdateListener bufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            Log.e("VideoDemoActivity", "onBufferingUpdate, 进度:"+ percent + "%");
        }
    } ;

    MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            Log.e("VideoDemoActivity", "onCompletion, 播放完成");
        }
    };

    MediaPlayer.OnPreparedListener preparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            Log.e("VideoDemoActivity", "onPrepared, 已经准备完毕");
        }
    };

    MediaPlayer.OnErrorListener errorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            Log.e("VideoDemoActivity", "onError, 播放出错了");
            return false;
        }
    };


}
