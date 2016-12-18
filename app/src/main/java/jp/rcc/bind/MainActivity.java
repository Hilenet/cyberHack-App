package jp.rcc.bind;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TableLayout table = new TableLayout(this);
        TableLayout table = (TableLayout)findViewById(R.id.t1);

        String[] servise = {"google", "Yahoo!", "twitter"};
        for (String s : servise) {
            TableRow tr = new TableRow(this);
            final Button btn = new Button(this);
            btn.setText(s);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("debug", ((Button)v).getText()+"");

                    Intent i = new Intent (getApplicationContext(), Main2Activity.class);
                    i.putExtra("SERVICE", btn.getText());
                    startActivity(i);

                } // a = {"hoge": "text", "foo": "hogwhogw"}

            });

            table.addView(tr);
            tr.addView(btn);


        }

        /*
        TableLayout.LayoutParams layparams = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layparams.setMargins(500, 100, 100, 0);

        table = (TableLayout) findViewById(R.id.t1);
        table.setLayoutParams(layparams);
        */
    }
}