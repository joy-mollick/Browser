package com.example.browser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MyDBFunctions extends SQLiteOpenHelper
{
    ///naming my databse store's name DATABSE_NAME
    ///naming my database's table name

    /// naming primary (identity) key by which we can map that value. TAB_ID
    /// naming another column say TAB_NAME
    /// naming second column say TAB_DAYS

    private static final String DATABSE_NAME = "mydb";
    private static final String TABLE_NAME = "mytab";

    private static final String TAB_ID = "id";
    private static final String TAB_STR = "name";
    private static final String TAB_TIME = "days";

    /// constructor of MyDBFunctions
    MyDBFunctions(Context c)
    {
        /// it is invoking
        super(c, DATABSE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        /// SQL -> structured Query Language
        String s = "CREATE TABLE "+TABLE_NAME+" ("+TAB_ID+" INTEGER PRIMARY KEY, "+TAB_STR+" TEXT, "+TAB_TIME+" TEXT)";
        /// making a structure of containing a table where TAB_ID is primary key , TAB_NAME and TAB_DAYS are columns
        db.execSQL(s);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    // ---- ---- adding data to database --- -----

    void addingDataToTable(DataTemp dt){

        /// it takes writable access
        SQLiteDatabase sqd  = this.getWritableDatabase();

        /// sqd instance  can take content values so make content of column (TAB_NAME and TAB_DAYS)
        ContentValues cv = new ContentValues();
        cv.put(TAB_STR, dt.getstr());
        cv.put(TAB_TIME, dt.getTime());
        /// insert content values in that table
        sqd.insert(TABLE_NAME, null, cv);
        sqd.close();
    }


    // --- ---- showing data ------ ----

    String[] my_data() {

        /// Readable access from database
        SQLiteDatabase sq = this.getReadableDatabase();

        /// select all from our table
        String q = "SELECT * FROM "+TABLE_NAME;

        /// cursor is one kind of iterator by this q sql.
        Cursor c = sq.rawQuery(q, null);

        /// we will fill up recvied_data array with data from
        String[] recvied_data = new String[c.getCount()];

        /// starting from first
        c.moveToFirst();

        if(c.moveToFirst()){
            int counter = 0 ;
            do {
                recvied_data[counter] = c.getString(c.getColumnIndex(TAB_STR+"")) +"\nTime : "+
                        c.getString(c.getColumnIndex(TAB_TIME+""));
                counter = counter+1;

            } while(c.moveToNext());

        }
        /// return array of data
        return recvied_data;
    }


    int delete_str(String str)
    {
        SQLiteDatabase s = this.getWritableDatabase();
        return s.delete(TABLE_NAME, TAB_STR+" = ?", new String[] {str});
    }


    String fetch_str(int id)
    {
        SQLiteDatabase sq = this.getReadableDatabase();
        /// SQL-(Structured Query Language)
        String q = "SELECT "+TAB_STR+" FROM "+TABLE_NAME+" WHERE "+TAB_ID+" = "+id;
        ///Taking only cursor of the row of the TAB_ID which reflex the position of on click item.
        Cursor c = sq.rawQuery(q, null);
        String s = "";
        c.moveToFirst();
        if(c.moveToFirst()) {
            s = c.getString(c.getColumnIndex(TAB_STR+""));
        }
        return s;
    }
}

