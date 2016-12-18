package jp.rcc.bind;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        String service = getIntent().getStringExtra("SERVICE");
        if( service!=null ){
            DBHelper dbh = new DBHelper(this);
            SQLiteDatabase db = dbh.getWritableDatabase();

            Cursor cursor = db.rawQuery("select User_name from bind where Service_url = \"" +service+"\";", new String[] {});

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
