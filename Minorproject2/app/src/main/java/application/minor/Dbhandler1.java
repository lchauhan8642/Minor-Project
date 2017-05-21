package application.minor;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Dbhandler1 extends SQLiteOpenHelper {
    public static final int version = 1;
    public static final String databasename = "addspot.db";
    public static final String tablename = "Spot";
    public static final String columnid = "id";
    public static final String columnplace="place";
    public static final String columnradius="radius";
    public static final String columnmessage = "message";
    public static final String columndate = "date";



    public Dbhandler1(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, databasename, factory, version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = " CREATE TABLE " + tablename + "(" +
                columnid + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                columnplace + " TEXT," +
                columnmessage + " TEXT," +
                columnradius + " INTEGER," +

                columndate + " TEXT " +


                ");";
        db.execSQL(query);


    }

    public void addnewspot(spot Spot) {
        ContentValues values = new ContentValues();

        values.put(columndate, Spot.getDate());
        values.put(columnplace,Spot.getPlace());

        values.put(columnmessage, Spot.getMessage());

        values.put(columnradius, Spot.getRadius());
        SQLiteDatabase db = getWritableDatabase();

        db.insert(tablename, null, values);
        db.close();

    }

    public void delete(int id1) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE From " + tablename + " WHERE " + columnid + "=\"" + id1 + "\";");

    }


    public  ArrayList<spot1> givespot() {
        ArrayList<spot1> Spot1=new ArrayList<spot1>();
        spot1 spot2;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + tablename + " WHERE 1";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(columndate)) != null) {
                spot2=new spot1();

                spot2.setId( c.getInt(c.getColumnIndex(columnid)));

                spot2.setPlace(c.getString(c.getColumnIndex(columnplace)));
                spot2.setDate( c.getString(c.getColumnIndex(columndate)));

                spot2.setMessage( c.getString(c.getColumnIndex(columnmessage)));
                spot2.setRadius( c.getInt(c.getColumnIndex(columnradius)));

                Spot1.add(spot2);
            }
            c.moveToNext();
        }
        db.close();
        return Spot1;
    }
}
