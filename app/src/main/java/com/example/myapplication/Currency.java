package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Currency extends AppCompatActivity   {
    TextView updateTime, usd,aud,cny,cad,eur,jpy;

    private  final static String URL = "https://open.er-api.com/v6/latest/HKD";

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        new FetchDataTask().execute(URL);


        //updateTime = findViewById(R.id.updateTime);
        usd = findViewById(R.id.usd);
        aud = findViewById(R.id.aud);
        cny = findViewById(R.id.cny);
        cad = findViewById(R.id.cad);
        eur = findViewById(R.id.eur);
        jpy = findViewById(R.id.jpy);


    }
    // This is the JSON body of the post
    JSONObject postData;
    // This is a constructor that allows you to pass in the JSON body

    private class FetchDataTask extends AsyncTask<String, Void, Void> {
        private Object postData;

        // This is a function that we are overriding from AsyncTask. It takes Strings as parameters because that is what we defined for the parameters of our async task
        @Override
        protected Void doInBackground(String... params) {

            try {
                // This is getting the url from the string we passed in
                URL url = new URL(params[0]);
                System.out.println(url);

                // Create the urlConnection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


                urlConnection.setDoInput(true);
                //urlConnection.setDoOutput(true);

                urlConnection.setRequestProperty("Content-Type", "application/json");

                urlConnection.setRequestMethod("GET");


                // OPTIONAL - Sets an authorization header
                //urlConnection.setRequestProperty("Authorization", "someAuthString");

                // Send the post body
                if (this.postData != null) {
                    OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                    writer.write(postData.toString());
                    writer.flush();
                }

                int statusCode = urlConnection.getResponseCode();

                if (statusCode == 200) {

                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                    String response = convertInputStreamToString(inputStream);

                    //System.out.println(response);
                    parseJSON(response);
                    // From here you can convert the string to JSON with whatever JSON parser you like to use
                    // After converting the string to JSON, I call my custom callback. You can follow this process too, or you can implement the onPostExecute(Result) method
                } else {
                    // Status code is not 200
                    // Do something to handle the error
                }

            } catch (Exception e) {
                Log.d("tag", e.getLocalizedMessage());
            }
            return null;
        }

        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;

        }

        private void parseJSON(String data){

            try{
                JSONObject jsonObject = new JSONObject(data);
                JSONObject rate = jsonObject.getJSONObject("rates");
                //String date = jsonObject.getString("time_last_update_utc");
                //System.out.println(date);

                //updateTime.setText(date);
                //updateTime.setText(jsonObject.getString("time_last_update_utc"));
                usd.setText(rate.getString("USD"));
                jpy.setText(rate.getString("JPY"));
                cny.setText(rate.getString("CNY"));
                aud.setText(rate.getString("AUD"));
                eur.setText(rate.getString("EUR"));
                cad.setText(rate.getString("CAD"));



                //int jsonArrLength = jsonMainNode.length();

                /*for(int i=0; i < jsonArrLength; i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    String postTitle = jsonChildNode.getString("post_title");
                    //tutorialList.add(postTitle);
                }*/

                // Get ListView object from xml
                //listView = (ListView) findViewById(R.id.list);

                // Define a new Adapter
                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, tutorialList);

                // Assign adapter to ListView
                //listView.setAdapter(adapter);

            }catch(Exception e){
                Log.i("App", "Error parsing data" +e.getMessage());

            }
        }


    }
}