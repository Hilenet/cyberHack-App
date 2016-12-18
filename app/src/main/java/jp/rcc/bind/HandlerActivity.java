package jp.rcc.bind;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;


import static jp.rcc.bind.MainActivity.appKey;

public class HandlerActivity extends AppCompatActivity {
    public static String service;
    public static String username;
    public static LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        service = new String();
        //parse
        Uri uri = this.getIntent().getData();
        service = uri.getLastPathSegment();
        Log.d("debug", service);
        TextView logo =  (TextView)this.findViewById(R.id.activity_handler).findViewById(R.id.logo);
        logo.setText(service);

        ArrayList<String> users = getUsers(service);

        // gen button
        layout = (LinearLayout)findViewById(R.id.linear);
        ArrayList<Button> buttons = new ArrayList<Button>();
        for(final String user : users) {
            username = user;
            Button tmp = new Button(this);
            tmp.setText(user);

            tmp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    //post
                    taskExe();

                    // kill layout
                    HandlerActivity.layout.removeAllViews();

                    // this should replace to: listen handler callback
                    ImageView complete = new ImageView(getApplicationContext());
                    complete.setImageResource(R.drawable.lgtm);

                    HandlerActivity.layout.addView(complete);

                }

            });

            layout.addView(tmp);
        }
    }

    private void taskExe () {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                //proc
                String urlStr = "http://ec2-52-69-154-35.ap-northeast-1.compute.amazonaws.com/";
                String json = "{" +
                        "\"key\":\"" + appKey +
                        "\", \"service\":\"" + HandlerActivity.service +
                        "\", \"user\":\"" + HandlerActivity.username +
                        "\"}";

                String token="";

                try {
                    String buffer = "";
                    HttpURLConnection con = null;
                    URL url = new URL(urlStr);
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                    OutputStream os = con.getOutputStream();
                    PrintStream ps = new PrintStream(os);
                    ps.print(json);
                    ps.close();

                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    buffer = reader.readLine();

                    token = buffer;

                    con.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject jenkins = new JSONObject(token);
                    token = jenkins.getString("token");

                } catch (JSONException e) {}

                Log.d("debug", token);
                //token = "token1";

                // post token to service
                urlStr = "http://"+HandlerActivity.service+"/auth/client";
                json = "{" +
                        "\"token\":\"" + token + "\"}";

                Log.d("debug", json);

                String res = "";

                try {
                    String buffer = "";
                    HttpURLConnection con = null;
                    URL url = new URL(urlStr);
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                    OutputStream os = con.getOutputStream();
                    PrintStream ps = new PrintStream(os);
                    ps.print(json);
                    ps.close();

                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

                    buffer = reader.readLine();

                    res= buffer;

                    con.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.d("debug", res);



                if(res.contains("true")) {
                    Uri uri = Uri.parse("http://" + service + "/token/"+token);
                    Log.d("debug", uri.getPath());
                    Intent i = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(i);
                } else {
                    Log.d("debug", "failed");
                }


                return null;
            }

        };

        task.execute();

    }

    private ArrayList<String> getUsers(String service) {
        ArrayList<String> users = new ArrayList<String>();

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor cursor =db.rawQuery("select * from bind where Service_url='"+service+"'", new String[] {});

        boolean next =  cursor.moveToFirst();
        while(next) {
            String tmp = cursor.getString(2);
            users.add(tmp);
            Log.d("debug", tmp);

            next = cursor.moveToNext();
        }

        cursor.close();
        db.close();
        helper.close();

        return users;
    }
}
