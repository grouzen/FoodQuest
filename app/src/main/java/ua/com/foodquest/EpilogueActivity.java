package ua.com.foodquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.VideoView;

public class EpilogueActivity extends AppCompatActivity {

    Intent timerIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_epilogue);

        timerIntent = new Intent(this, TimerActivity.class);

        showEpilogue();
    }

    private void showEpilogue() {
        VideoView videoView = (VideoView) findViewById(R.id.videoView);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                long timerLeft = getIntent().getExtras().getLong(TimerActivity.EXTRA_TIMER_LEFT);

                timerIntent.putExtra(TimerActivity.EXTRA_TIMER_LEFT, timerLeft);
                startActivity(timerIntent);
            }
        });

        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.epilogue_full);
        videoView.start();
    }


    @Override
    public void onBackPressed() {}

}