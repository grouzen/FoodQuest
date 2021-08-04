package ua.com.foodquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    Intent timerIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        timerIntent = new Intent(this, TimerActivity.class);

        showPrologue();
    }


    private void showPrologue() {
        VideoView videoView = (VideoView) findViewById(R.id.videoView);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                startActivity(timerIntent);
            }
        });


        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.prologue);
        videoView.start();
    }

    @Override
    public void onBackPressed() {}

}