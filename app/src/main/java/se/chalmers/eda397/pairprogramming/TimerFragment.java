package se.chalmers.eda397.pairprogramming;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Timer;


public class TimerFragment extends Fragment implements View.OnClickListener{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private View mRootView = null;
    private CountDownTimer cdtimer;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TimerFragment  newInstance(int sectionNumber) {
        TimerFragment  fragment = new TimerFragment ();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public TimerFragment () {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_timer, container, false);

        final Button startButton = (Button) mRootView.findViewById(R.id.start_button);
        final Button stopButton = (Button) mRootView.findViewById(R.id.stop_button);
        final Button pauseButton = (Button) mRootView.findViewById(R.id.pause_button);
        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);

        return mRootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onClick(View v) {
        EditText input = (EditText)mRootView.findViewById(R.id.timer_input);
        int time;
        try {
            time = Integer.parseInt(input.getText().toString());
        } catch (Exception e) {
            Toast addRepoClickToast = Toast.makeText(this.getActivity(), "Try a smaller number", Toast.LENGTH_SHORT);
            addRepoClickToast.show();
            return;
        }
        TextView output = (TextView)mRootView.findViewById(R.id.timer_output);
        if(v.getId() == R.id.start_button) {
            startTimer(time);
        }
        else if(v.getId() == R.id.stop_button) {
            stopTimer();
        }
        else if(v.getId() == R.id.pause_button) {
            //pauseTimer();
        }
        //else if(v.getId() == R.id.resume_button) {
            //resumeTimer();
        //}

    }

    public void startTimer(int time)
    {
        mRootView.findViewById(R.id.timer_input).setVisibility(View.GONE);
        mRootView.findViewById(R.id.timer_output).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.start_button).setVisibility(View.GONE);
        final TextView output = (TextView)mRootView.findViewById(R.id.timer_output);
        cdtimer = new CountDownTimer((time*1000), 1000) {
            int h;
            int m;
            int s;
            public void onTick(long x) {
                h = (int)(x / (60*60*1000));
                x = (x - h*(60*60*1000));
                m = (int)(x / (60*1000));
                x = (x - m*(60*1000));
                s = (int)(x / 1000);
                x = (x - s*1000);
                String outputString = String.format("%02d:%02d:%02d", h,m,s);
                output.setText(outputString);
            }

            public void onFinish() {
                output.setText("Done!");
                mRootView.findViewById(R.id.timer_output).setVisibility(View.GONE);
                mRootView.findViewById(R.id.timer_input).setVisibility(View.VISIBLE);
                mRootView.findViewById(R.id.start_button).setVisibility(View.VISIBLE);

            }
        }.start();
    }

    public void stopTimer()
    {
        cdtimer.cancel();
        mRootView.findViewById(R.id.timer_output).setVisibility(View.GONE);
        mRootView.findViewById(R.id.timer_input).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.start_button).setVisibility(View.VISIBLE);
    }

    public void pauseTimer()
    {
        //cdtimer.
    }

}
