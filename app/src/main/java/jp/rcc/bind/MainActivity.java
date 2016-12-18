package jp.rcc.bind;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

import android.widget.TableLayout;
import android.widget.Button;
import android.widget.RelativeLayout;

import android.content.Intent;
import static java.lang.System.exit;

public class MainActivity extends AppCompatActivity {
    public static final String appKey = "12345678901";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DBHelper dbh = new DBHelper(this);
        SQLiteDatabase db = dbh.getWritableDatabase();

        Cursor cursor = db.rawQuery("select Service_url from bind group by Service_url;",new String[] {});

        TableLayout table = new TableLayout(this);
        boolean next = cursor.moveToFirst();
        while(next){
            final Button btn = new Button(this);
            btn.setText(cursor.getString(0));
            btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent i = new Intent(getApplicationContext(),Main2Activity.class);
                    i.putExtra("SERVICE",btn.getText());
                    startActivity(i);
                }
            });
            table.addView(btn);

            next = cursor.moveToNext();
        }
        ((RelativeLayout)this.findViewById(R.id.content_main)).addView(table);

        cursor.close();
        db.close();
        dbh.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
