package se.chalmers.eda397.pairprogramming;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;

import org.w3c.dom.Text;


public class TimerFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private EditText mTimerInput = null;
    private TextView mTimerOutput = null;
    private Button mStartButton = null;
    private Button mResumeButton = null;
    private Button mPauseButton = null;
    private TextView mEndText = null;

    private CountDownTimer cdtimer;
    private long PauseTime;

    public static TimerFragment newInstance(int sectionNumber) {
        TimerFragment fragment = new TimerFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    public TimerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timer, container, false);

        mEndText = (TextView)rootView.findViewById(R.id.timer_end_text);

        mTimerInput = (EditText)rootView.findViewById(R.id.timer_input);
        mTimerOutput = (TextView)rootView.findViewById(R.id.timer_output);

        mStartButton = (Button)rootView.findViewById(R.id.start_button);
        mStartButton.setOnClickListener(this);

        mPauseButton = (Button)rootView.findViewById(R.id.pause_button);
        mPauseButton.setOnClickListener(this);

        mResumeButton = (Button)rootView.findViewById(R.id.resume_button);
        mResumeButton.setOnClickListener(this);

        final Button stopButton = (Button)rootView.findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onClick(View v) {
        String inputStr = mTimerInput.getText().toString();

        int hr = 0, min = 0, sec = 0;

        if (inputStr.lastIndexOf(":") >= 0) {
            sec = Integer.parseInt(inputStr.substring(inputStr.lastIndexOf(":") + 1, inputStr.length()));
            inputStr = inputStr.substring(0, inputStr.lastIndexOf(":"));
            if (inputStr.lastIndexOf(":") >= 0) {
                min = Integer.parseInt(inputStr.substring(inputStr.lastIndexOf(":") + 1, inputStr.length()));
                inputStr = inputStr.substring(0, inputStr.lastIndexOf(":"));
                if (inputStr.lastIndexOf(":") >= 0) {
                    hr = Integer.parseInt(inputStr.substring(inputStr.lastIndexOf(":") + 1, inputStr.length()));
                }
                else {
                    hr = Integer.parseInt(inputStr);
                }
            }
            else {
                min = Integer.parseInt(inputStr);
            }
        }
        else {
            sec = Integer.parseInt(inputStr);
        }

        if (hr > 24 || min > 60 || sec > 60) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Invalid input")
                    .setMessage("Enter correct time and correct format HH:MM:SS.")
                    .setNegativeButton(getString(R.string.ok), null);

            AlertDialog alert = builder.create();
            alert.show();
            return;
        }

        int time = (hr * 60 * 60) + (min * 60) + sec;

        switch (v.getId()){
            case R.id.start_button:
                startTimer(time);
                break;
            case R.id.stop_button:
                stopTimer();
                break;
            case R.id.pause_button:
                pauseTimer();
                break;
            case R.id.resume_button:
                resumeTimer();
                break;
        }
    }

    public void startTimer(int time)  {
        mTimerInput.setVisibility(View.GONE);
        mTimerOutput.setVisibility(View.VISIBLE);
        mStartButton.setVisibility(View.GONE);
        mEndText.setText("");

        cdtimer = new CountDownTimer((time * 1000), 1000) {
            int h, m, s;

            public void onTick(long x) {
                PauseTime = x;
                h = (int) (x / (60 * 60 * 1000));
                x = (x - h * (60 * 60 * 1000));
                m = (int) (x / (60 * 1000));
                x = (x - m * (60 * 1000));
                s = (int) (x / 1000);

                String outputString = String.format("%02d:%02d:%02d", h, m, s);
                mTimerOutput.setText(outputString);
            }

            public void onFinish() {
                mEndText.setText(getString(R.string.timesup));
                updateGUIWhenDone();
            }
        }.start();
    }

    public void stopTimer() {
        cdtimer.cancel();
        updateGUIWhenDone();
    }

    public void pauseTimer() {
        cdtimer.cancel();
        mPauseButton.setVisibility(View.GONE);
        mResumeButton.setVisibility(View.VISIBLE);
    }

    public void resumeTimer() {
        mPauseButton.setVisibility(View.VISIBLE);
        mResumeButton.setVisibility(View.GONE);

        cdtimer = new CountDownTimer(PauseTime, 1000) {
            int h, m, s;

            public void onTick(long x) {
                PauseTime = x;
                h = (int) (x / (60 * 60 * 1000));
                x = (x - h * (60 * 60 * 1000));
                m = (int) (x / (60 * 1000));
                x = (x - m * (60 * 1000));
                s = (int) (x / 1000);
                String outputString = String.format("%02d:%02d:%02d", h, m, s);
                mTimerOutput.setText(outputString);
            }

            public void onFinish() {
                mEndText.setText(getString(R.string.timesup));
                updateGUIWhenDone();
            }
        }.start();
    }

    private void updateGUIWhenDone() {
        mTimerInput.setVisibility(View.VISIBLE);
        mTimerOutput.setVisibility(View.GONE);
        mStartButton.setVisibility(View.VISIBLE);
    }

}