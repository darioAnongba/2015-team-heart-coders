package ch.epfl.sweng.swissaffinity.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by sahinfurkan on 06/11/15.
 */
public class UserDBAdapter {
    private static final String DATABASE_NAME = "user";
    private static final String DATABASE_TABLE = "user_data";
    private static final int DATABASE_VERSION = 1;
    public static final String KEY_TITLE = "title";
    public static final String KEY_BODY = "body";
    public static final String KEY_ROWID = "_id";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE + " ("
                    + KEY_ROWID + " integer primary key autoincrement, " + KEY_TITLE + " text not null, "
                    + KEY_BODY + " text not null);";
    private final Context mCtx;
    public UserDBAdapter(Context ctx){
        this.mCtx = ctx;
    }

    public UserDBAdapter open() throws android.database.SQLException{
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        mDbHelper.close();
    }

    public long createData(String title, String body){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE,title);
        initialValues.put(KEY_BODY, body);
        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteData(long rowId){
        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) >0;
    }

    public Cursor fetchAllData(){
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE,
                KEY_BODY}, null, null, null, null, null);
    }

    public Cursor fetchData(long rowId) throws SQLException {
        Cursor mCursor = mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                KEY_TITLE, KEY_BODY},KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if(mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateData(long rowId, String title, String body){
        ContentValues args = new ContentValues();
        args.put(KEY_TITLE, title); args.put(KEY_BODY, body);
        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db){ db.execSQL(DATABASE_CREATE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){ // Not used, but you could upgrade the database with ALTER scripts
        }
    }
}
