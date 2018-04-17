package com.example.danceciliochua.lesson7;

import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.content.Context;
import android.support.v4.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

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

        if(getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0,null,this);
        }

    }

    public void searchBooks(View view) {
        String queryString = mBookInput.getText().toString();

        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        mTitle.setText(R.string.loading);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected() && queryString.length() != 0) {
            mAuthor.setText("");
            mTitle.setText(R.string.loading);
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);

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

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        Log.d("A", "Start");
        return new BookLoader(this, args.getString("queryString"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            for(int i = 0; i<itemsArray.length();i++) {
                JSONObject book = itemsArray.getJSONObject(i);
                String title=null;
                String authors=null;
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                JSONArray authorList = volumeInfo.getJSONArray("authors");


                try {
                    title = volumeInfo.getString("title");
                    authors = "";
                    for(int j=0; j<authorList.length(); j++) {
                        authors = authors + "\n" + authorList.getString(j);
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }

                if(title != null && authors != null) {
                    mTitle.setText(title);
                    mAuthor.setText(authors);
                    return;
                }
            }

            mTitle.setText("No Results Found");
            mAuthor.setText("");

        } catch (Exception e) {
            mTitle.setText("No Results Found");
            mAuthor.setText("");
            e.printStackTrace();

        }

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
