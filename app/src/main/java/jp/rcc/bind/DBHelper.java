package jp.rcc.bind;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by unkno_000 on 2016/12/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    /* データベース名 */
    private final static String DB_NAME = "bind.db";
    /* データベースのバージョン */
    private final static int DB_VER = 1;

    /*
     * コンストラクタ
      */
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);

    }

    /*
     * onCreateメソッド
     * データベースが作成された時に呼ばれます。
     * テーブルの作成などを行います。
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "";
        sql += "create table bind (";
        sql += " Id integer primary key autoincrement";
        sql += ",Service_url text";
        sql += ",User_name text";
        sql += ")";
        db.execSQL(sql);
        db.execSQL("insert into bind (Service_url, User_name) values ('service.tatsu.site', '小林')");
        db.execSQL("insert into bind (Service_url, User_name) values ('service.tatsu.site', 'tatsuaki86')");

        db.execSQL("insert into bind (Service_url, User_name) values ('nora.world', 'のら')");
        db.execSQL("insert into bind (Service_url, User_name) values ('nora.world', 'horklie')");

    }

    /*
     * onUpgradeメソッド
     * onUpgrade()メソッドはデータベースをバージョンアップした時に呼ばれます。
     * 現在のレコードを退避し、テーブルを再作成した後、退避したレコードを戻すなどの処理を行います。
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}