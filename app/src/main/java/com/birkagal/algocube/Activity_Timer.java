package com.birkagal.algocube;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Activity_Timer extends AppCompatActivity {

    private Button timer_btn_reset;
    private ImageButton timer_btn_left, timer_btn_right;
    private TextView timer_txt_time;
    private ImageView red_circle, green_circle;
    private Handler timer_handler;
    private Runnable updateTimerThread;
    private boolean isTimerOn = false, isRight = false, isLeft = false, isFinished = false, isReset = true;
    private long startTime = 0L, timeInMilli = 0L;
    private ArrayList<Solve> solves;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        findViews();
        threadTimer();
        get_array_from_sp();

        timer_btn_left.setOnTouchListener(bottomTouchListener);
        timer_btn_right.setOnTouchListener(bottomTouchListener);
        timer_btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetClick();
            }
        });
    }

    private void resetClick() {
        isReset = true;
        toggleLights(true);
        if (isTimerOn) {
            stopTimer();
        } else {
            if (timeInMilli != 0) {
                solves.add(new Solve(timeInMilli));
                MySP.getInstance().putArray(MySP.KEYS.USER_SOLVE_LIST, solves);
            }
        }
        timeInMilli = 0L;
        timer_txt_time.setText(R.string.start_time);
    }

    private void threadTimer() {
        timer_handler = new Handler();
        updateTimerThread = new Runnable() {
            @Override
            public void run() {
                updateTimer();
                timer_handler.postDelayed(this, 0);
            }
        };
    }

    private void updateTimer() {
        timeInMilli = SystemClock.uptimeMillis() - startTime;
        String formattedTime = MySP.getInstance().getFormattedTime(timeInMilli);
        timer_txt_time.setText(formattedTime);
    }

    private void btnTouched(@NotNull View view, MotionEvent motionEvent) {
        int id = view.getId();

        if (id == timer_btn_left.getId()) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if (isReset) {
                    if (isRight) {
                        if (isTimerOn) {
                            toggleLights(true);
                            stopTimer();
                            isFinished = true;
                            isReset = false;
                        } else {
                            toggleLights(false);
                        }
                    }
                }
                isLeft = true;
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (isFinished) {
                    isFinished = false;
                } else {
                    if (isRight && isReset) {
                        toggleLights(false);
                        startTimer();
                    }
                }
                isLeft = false;
            }
        } else if (id == timer_btn_right.getId()) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if (isReset) {
                    if (isLeft) {
                        if (isTimerOn) {
                            toggleLights(true);
                            stopTimer();
                            isFinished = true;
                            isReset = false;
                        } else {
                            toggleLights(false);
                        }
                    }
                }
                isRight = true;
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (isFinished) {
                    isFinished = false;
                } else {
                    if (isLeft && isReset) {
                        toggleLights(false);
                        startTimer();
                    }
                }
                isRight = false;
            }
        }
    }

    private void toggleLights(boolean isRed) {
        if (isRed) {
            green_circle.setVisibility(View.INVISIBLE);
            red_circle.setVisibility(View.VISIBLE);
        } else {
            green_circle.setVisibility(View.VISIBLE);
            red_circle.setVisibility(View.INVISIBLE);
        }
    }

    private void startTimer() {
        isTimerOn = true;
        startTime = SystemClock.uptimeMillis();
        timer_handler.postDelayed(updateTimerThread, 0);
    }

    private void stopTimer() {
        isTimerOn = false;
        timer_handler.removeCallbacks(updateTimerThread);
    }

    private void get_array_from_sp() {
        solves = MySP.getInstance().getArray(MySP.KEYS.USER_SOLVE_LIST, new TypeToken<ArrayList<Solve>>() {
        });
        if (solves == null)
            solves = new ArrayList<>();
    }

    private void findViews() {
        timer_btn_left = findViewById(R.id.timer_btn_left);
        timer_btn_right = findViewById(R.id.timer_btn_right);
        timer_btn_reset = findViewById(R.id.timer_btn_reset);
        timer_txt_time = findViewById(R.id.timer_txt_time);
        red_circle = findViewById(R.id.red_circle);
        green_circle = findViewById(R.id.green_circle);
    }


    private final View.OnTouchListener bottomTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, @NotNull MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.performClick();
            }
            btnTouched(view, motionEvent);
            return false;
        }
    };
}
