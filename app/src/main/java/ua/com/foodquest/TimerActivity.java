package ua.com.foodquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    static final long TIMER_DEADLINE = 3600;
    static final long INPUT_ANSWER = 6;
    static final long INPUT_HIDE_PERIOD = 20 * 60;

    static final String EXTRA_TIMER_LEFT = "timer_left";

    TextView looserView;
    EditText minutesLeftInput;
    TextView timerView;
    Intent loseIntent;
    Intent epilogueIntent;
    long millisLeft;
    CountDownTimer timer;
    MediaPlayer laughPlayer;
    MediaPlayer musicPlayer;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_timer);

        looserView = (TextView) findViewById(R.id.looser);
        minutesLeftInput = (EditText) findViewById(R.id.minutesLeftInput);
        timerView = (TextView) findViewById(R.id.timer);
        loseIntent = new Intent(this, LoseActivity.class);
        epilogueIntent = new Intent(this, EpilogueActivity.class);

        minutesLeftInput.setVisibility(View.INVISIBLE);
        looserView.setVisibility(View.INVISIBLE);

        bundle = getIntent().getExtras();

        laughPlayer = MediaPlayer.create(getApplicationContext(), R.raw.laugh);
        musicPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music);

        long leftMillis = getLeftMillis();

        showTimer(leftMillis);
        startMusic(leftMillis);
    }

    private long getLeftMillis() {
        if (bundle != null) {
            return bundle.getLong(EXTRA_TIMER_LEFT, TIMER_DEADLINE * 1000);
        } else {
            return TIMER_DEADLINE * 1000;
        }
    }

    private void startMusic(long leftMillis) {
        int base = (int) TIMER_DEADLINE * 1000;
        int msec = base - (int) leftMillis;

        musicPlayer.seekTo(msec);
        musicPlayer.start();
    }

    private void showTimer(long timerLeft) {
        if (bundle != null) {
            minutesLeftInput.setVisibility(View.VISIBLE);
        }

        handleInput();

        if (timerLeft != 0) {
            timer = new CountDownTimer(timerLeft, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    millisLeft = millisUntilFinished;

                    if (millisUntilFinished < (TIMER_DEADLINE - INPUT_HIDE_PERIOD) * 1000 && bundle == null) {
                        minutesLeftInput.setVisibility(View.VISIBLE);
                    }

                    String formatted = formatTime(millisUntilFinished / 1000);

                    timerView.setText(formatted);
                }

                @Override
                public void onFinish() {
                    laughPlayer.start();

                    String formatted = formatTime(0);
                    timerView.setText(formatted);
                    timerView.setTextColor(Color.RED);

                    looserView.setVisibility(View.VISIBLE);

                    // Force to show input
                    minutesLeftInput.setVisibility(View.VISIBLE);

                    timer.cancel();
                    millisLeft = 0;
                }
            };

            timer.start();
        } else {
            String formatted = formatTime(0);
            timerView.setText(formatted);
            timerView.setTextColor(Color.RED);

            looserView.setVisibility(View.VISIBLE);

            // Force to show input
            minutesLeftInput.setVisibility(View.VISIBLE);
        }
    }

    private String formatTime(long secondsLeft) {
        long minutes = secondsLeft / 60;
        long seconds = secondsLeft - (minutes * 60);

        return String.format(Locale.ENGLISH, "%02d:%02d", minutes, seconds);
    }

    private void handleInput() {

        minutesLeftInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                CharSequence value = v.getText();

                if (value.toString().equals("q")) {
                    startActivity(loseIntent);
                    timer.cancel();
                } else if (value.length() > 1) {
                    Character symbol = value.charAt(0);

                    try {
                        int number = Integer.parseInt(value.subSequence(1, value.length()).toString());

                        if (number <= INPUT_ANSWER && symbol == '#') {
                            epilogueIntent.putExtra(EXTRA_TIMER_LEFT, millisLeft);

                            laughPlayer.stop();
                            musicPlayer.pause();
                            startActivity(epilogueIntent);
                            timer.cancel();
                        }
                    } catch (Exception e) {
                    }
                }

                return true;
            }
        });

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);

        return minutesLeftInput.requestFocus();
    }


    @Override
    public void onBackPressed() {
        // Disable to go back
    }

}