package application.minor;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Dbhandler extends SQLiteOpenHelper {
    public static final int version = 1;
    public static final String databasename = "reminders.db";
    public static final String tablename = "Reminders";
    public static final String columnid = "id";
    public static final String columnradius="radius";
    public static final String columnaddress = "address";
    public static final String columnmessage = "message";
    public static final String columndate = "date";
    public static final String columntask = "task";
    public static final String columnlatitude = "latitude";
    public static final String columnlongitude = "longitude";


    public Dbhandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, databasename, factory, version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = " CREATE TABLE " + tablename + "(" +
                columnid + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                columnaddress + " TEXT," +
                columnmessage + " TEXT," +
                columnradius + " REAL," +
                columntask + " TEXT," +
                columndate + " TEXT," +
                columnlatitude + " REAL," +
                columnlongitude + " REAl" +
                ");";
        db.execSQL(query);
    }

    public void addnewreminder(Reminders reminder) {
        ContentValues values = new ContentValues();
        values.put(columnaddress, reminder.getAddress());
        values.put(columndate, reminder.getDate());
        values.put(columnlatitude, reminder.getLatitude());
        values.put(columnlongitude, reminder.getLongitude());
        values.put(columnmessage, reminder.getMessage());
        values.put(columntask, reminder.getTask());
        values.put(columnradius, reminder.getRadius());
        SQLiteDatabase db = getWritableDatabase();

        db.insert(tablename, null, values);
        db.close();

    }

    public void delete(int id1) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE From " + tablename + " WHERE " + columnid + "=\"" + id1 + "\";");

    }


    public  ArrayList<StoredReminders> givereminders() {
        ArrayList<StoredReminders> rem1=new ArrayList<StoredReminders>();
        StoredReminders storem;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + tablename + " WHERE 1";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(columnaddress)) != null) {
                storem=new StoredReminders();
               storem.setAddress( c.getString(c.getColumnIndex(columnaddress)));
                storem.setId( c.getInt(c.getColumnIndex(columnid)));
                storem.setLatitude( c.getDouble(c.getColumnIndex(columnlatitude)));
                storem.setLongitude( c.getDouble(c.getColumnIndex(columnlongitude)));
                storem.setDate( c.getString(c.getColumnIndex(columndate)));
                storem.setTask( c.getString(c.getColumnIndex(columntask)));
                storem.setMessage( c.getString(c.getColumnIndex(columnmessage)));
                storem.setRadius( c.getDouble(c.getColumnIndex(columnradius)));

rem1.add(storem);
            }
            c.moveToNext();
        }
        db.close();
        return rem1;
    }
}
