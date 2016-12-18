package jp.rcc.bind;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        String service = getIntent().getStringExtra("SERVICE");
        if( service!=null ){
            DBHelper dbh = new DBHelper(this);
            SQLiteDatabase db = dbh.getWritableDatabase();
            String query = "";
            Cursor cursor = db.rawQuery(query, new String[] {});

            TableLayout table = new TableLayout(this);
            boolean next = cursor.moveToFirst();
            while(next) {
                TextView text = new TextView(this);
                text.setText(cursor.getString(0));
                table.addView(text);

                next = cursor.moveToNext();
            }
            ((RelativeLayout)this.findViewById(R.id.activity_main2)).addView(table);

            cursor.close();
            db.close();
            dbh.close();
        }
        //finish();
    }
}
