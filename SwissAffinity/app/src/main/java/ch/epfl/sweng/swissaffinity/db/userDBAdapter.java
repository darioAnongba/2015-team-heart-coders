package ch.epfl.sweng.swissaffinity.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by sahinfurkan on 06/11/15.
 */

public class userDBAdapter {
    private static final String DATABASE_NAME = "user";
    private static final String DATABASE_TABLE = "user_data";
    private static final int DATABASE_VERSION = 1;
    public static final String KEY_ID = "Id";
    public static final String KEY_USER_NAME = "UserName";
    public static final String KEY_EMAIL = "Email";
    public static final String KEY_ENABLED = "Enabled";
    public static final String KEY_LOCKED = "Locked";
    public static final String KEY_LAST_NAME = "LastName";
    public static final String KEY_FIRST_NAME = "FirstName";
    public static final String KEY_PHONE = "Phone";
    public static final String KEY_GENDER = "Gender";
    public static final String KEY_BIRTHDATE = "Birthdate";
    public static final String KEY_PROFESSION = "Profession";
    public static final String KEY_LOCATION_PREFERENCES = "LocationPreferences";
    public static final String KEY_EVENT_PREFERENCES = "EventPreferences";

    public static final String KEY_ROWID = "_id";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private static final String DATABASE_CREATE = "create table " +
                                                  DATABASE_TABLE +
                                                  " (" +
                                                  KEY_ROWID +
                                                  " integer primary key autoincrement, " +
                                                  KEY_ID +
                                                  " TEXT, " +
                                                  KEY_USER_NAME +
                                                  " TEXT, " +
                                                  KEY_EMAIL +
                                                  " TEXT, " +
                                                  KEY_ENABLED +
                                                  " TEXT, " +
                                                  KEY_LOCKED +
                                                  " TEXT, " +
                                                  KEY_LAST_NAME +
                                                  " TEXT, " +
                                                  KEY_FIRST_NAME +
                                                  " TEXT, " +
                                                  KEY_PHONE +
                                                  " TEXT, " +
                                                  KEY_GENDER +
                                                  " TEXT, " +
                                                  KEY_BIRTHDATE +
                                                  " TEXT, " +
                                                  KEY_PROFESSION +
                                                  " TEXT, " +
                                                  KEY_LOCATION_PREFERENCES+
                                                  " TEXT, " +
                                                  KEY_EVENT_PREFERENCES+
                                                  " TEXT);";
    private final Context mCtx;
    private String[] columns = new String[]{
            KEY_ROWID,
            KEY_ID,
            KEY_USER_NAME,
            KEY_EMAIL,
            KEY_ENABLED,
            KEY_LOCKED,
            KEY_LAST_NAME,
            KEY_FIRST_NAME,
            KEY_PHONE,
            KEY_GENDER,
            KEY_BIRTHDATE,
            KEY_PROFESSION,
            KEY_LOCATION_PREFERENCES,
            KEY_EVENT_PREFERENCES};

    public userDBAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public userDBAdapter open() throws android.database.SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public void createData(
            String id,
            String userName,
            String email,
            boolean enabled,
            boolean locked,
            String firstName,
            String lastName,
            String phone,
            String gender,
            String birthdate,
            String profession,
            ArrayList<String> locationPreferences,
            ArrayList<String> eventPreferences)
    {
        mDb = mDbHelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ID, id);
        initialValues.put(KEY_USER_NAME, userName);
        initialValues.put(KEY_EMAIL, email);
        initialValues.put(KEY_ENABLED, (enabled ? 1 : 0));
        initialValues.put(KEY_LOCKED, (locked ? 1 : 0));
        initialValues.put(KEY_LAST_NAME, lastName);
        initialValues.put(KEY_FIRST_NAME, firstName);
        initialValues.put(KEY_PHONE, phone);
        initialValues.put(KEY_GENDER, gender);
        initialValues.put(KEY_BIRTHDATE, birthdate);
        initialValues.put(KEY_PROFESSION, profession);
        initialValues.put(KEY_LOCATION_PREFERENCES, concatinateArrayToString(locationPreferences));
        initialValues.put(KEY_EVENT_PREFERENCES, concatinateArrayToString(eventPreferences));

        mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    private String concatinateArrayToString(ArrayList<String> arr){
        String res = "";
        String seperator = ",";

        if(arr == null || arr.size() == 0)
            return res;

        for (String str : arr){
            res += str + seperator;
        }
        return res.substring(0, res.length()-1);
    }

    public boolean deleteData(long rowId) {
        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor fetchAllData() {
        return mDb.query(DATABASE_TABLE, columns, null, null, null, null, null, null);
    }

    public Cursor fetchData(long rowId) throws SQLException {
        Cursor mCursor = mDb.query(
                true,
                DATABASE_TABLE,
                columns,
                KEY_ROWID + "=" + rowId,
                null,
                null,
                null,
                null,
                null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateData(
            long rowId,
            String id,
            String userName,
            String email,
            boolean enabled,
            boolean locked,
            String firstName,
            String lastName,
            String phone,
            String gender,
            String birthdate,
            String profession,
            ArrayList<String> locationPreferences,
            ArrayList<String> eventPreferences)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_ID, id);
        args.put(KEY_USER_NAME, userName);
        args.put(KEY_EMAIL, email);
        args.put(KEY_ENABLED, (enabled ? 1 : 0));
        args.put(KEY_LOCKED, (locked ? 1 : 0));
        args.put(KEY_LAST_NAME, lastName);
        args.put(KEY_FIRST_NAME, firstName);
        args.put(KEY_PHONE, phone);
        args.put(KEY_GENDER, gender);
        args.put(KEY_BIRTHDATE, birthdate);
        args.put(KEY_PROFESSION, profession);
        args.put(KEY_LOCATION_PREFERENCES, concatinateArrayToString(locationPreferences));
        args.put(KEY_EVENT_PREFERENCES, concatinateArrayToString(eventPreferences));
        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(
                SQLiteDatabase db, int oldVersion, int newVersion)
        { // Not used, but you could upgrade the database with ALTER scripts
        }
    }
}
