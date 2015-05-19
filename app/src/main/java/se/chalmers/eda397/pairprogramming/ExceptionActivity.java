package se.chalmers.eda397.pairprogramming;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import se.chalmers.eda397.pairprogramming.core.ExceptionHandler;

public class ExceptionActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        setContentView(R.layout.activity_error);

        TextView error = (TextView) findViewById(R.id.error_text);
        error.setText(getIntent().getStringExtra(ExceptionHandler.ARGS_ERROR_REPORT));

        Button btn = (Button)findViewById(R.id.error_button);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}