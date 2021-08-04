package ua.com.foodquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;

public class LoseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_lose);

        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.laugh);
        mediaPlayer.start();
    }

    @Override
    public void onBackPressed() {}
}