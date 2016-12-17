package jp.rcc.bind;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;

public class HandlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        String service = new String();
        //parse
        Uri uri = this.getIntent().getData();
        service = uri.getLastPathSegment();
        Log.d("debug", service);
        TextView logo =  (TextView)this.findViewById(R.id.activity_handler).findViewById(R.id.logo);
        logo.setText(service);

        ArrayList<String> users = getUsers(service);

        // gen button
        LinearLayout layout = (LinearLayout)findViewById(R.id.linear);
        ArrayList<Button> buttons = new ArrayList<Button>();
        for(String user : users) {
            Button tmp = new Button(this);
            tmp.setText(user);

            tmp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    String user = v.getText();

                    //post
                }
            });
        }
        layout.addView(tmp);
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
