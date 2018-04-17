package com.example.danceciliochua.lesson7;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        mTitle.setText(R.string.loading);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected() && queryString.length() != 0) {
            new FetchBook(mTitle, mAuthor).execute(queryString);
            mAuthor.setText("");
            mTitle.setText(R.string.loading);

        } else {
            if(queryString.length() == 0) {
                mAuthor.setText("");
                mTitle.setText("Please enter a search term");

            } else {
                mAuthor.setText("");
                mTitle.setText("Please check your network connection and try again.");

            }

        }


    }
}
