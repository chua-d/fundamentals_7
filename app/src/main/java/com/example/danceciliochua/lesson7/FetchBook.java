package com.example.danceciliochua.lesson7;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class FetchBook extends AsyncTask<String, Void, String> {

    private TextView mTitleText;
    private TextView mAuthorText;

    public FetchBook(TextView mTitleText, TextView mAuthorText) {
        this.mTitleText = mTitleText;
        this.mAuthorText = mAuthorText;

    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
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
                    mTitleText.setText(title);
                    mAuthorText.setText(authors);
                    return;
                }
            }

            mTitleText.setText("No Results Found");
            mAuthorText.setText("");

        } catch (Exception e) {
            mTitleText.setText("No Results Found");
            mAuthorText.setText("");
            e.printStackTrace();

        }

    }

}
