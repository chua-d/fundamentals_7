package com.example.danceciliochua.lesson7;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mBookInput;
    private TextView mTitle;
    private TextView mAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBookInput = (EditText) findViewById(R.id.bookInput);
        mTitle = (TextView) findViewById(R.id.titleText);
        mAuthor = (TextView) findViewById(R.id.authorText);

    }

    public void searchBooks(View view) {
        String queryString = mBookInput.getText().toString();

    }
}
