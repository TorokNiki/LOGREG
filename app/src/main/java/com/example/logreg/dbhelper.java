package com.example.logreg;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class dbhelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "regisztracio";
    public static final String TABLE_NAME = "felhasznalo";

    public static final String COL_1 = "id"; //COL_id
    public static final String COL_2 = "email"; //COL_email
    public static final String COL_3 = "felhnev"; //COL_felhnev
    public static final String COL_4 = "jelszo";
    public static final String COL_5 = "teljesnev";

    public dbhelper(Context context)
    {
        super(context,DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTables ="CREATE TABLE IF NOT EXISTS "+TABLE_NAME + "(" +
                        COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE," +
                        COL_2+" TEXT NOT NULL UNIQUE, " +
                        COL_3+ " TEXT NOT NULL UNIQUE, " +
                        COL_4+ " TEXT NOT NULl, " +
                        COL_5+ " TEXT NOT NULL)";
        //az UNIQE-t lehet a végén is és kkor zárójelben felsorolni amit azzá akarunk tenni. pl UNIQUE(COL_2,COL_3)
        sqLiteDatabase.execSQL(createTables);
//        vagy    sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME +
//                " (id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT NOT NULL UNIQUE, felhnev TEXT NOT NULL UNIQUE, jelszo TEXT NOT NULL, teljesnev TEXT NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }

    public boolean adatRogzites(String email, String felhnev, String jelszo, String teljesnev)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, email);
        contentValues.put(COL_3, felhnev);
        contentValues.put(COL_4, md5(jelszo));
        contentValues.put(COL_5, teljesnev);

        long eredmeny = database.insert(TABLE_NAME, null, contentValues);

        if (eredmeny == -1)
            return false;
        else
            return true;
    }
    public Cursor adatLekerdezes()
    {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor eredmeny = database.rawQuery("SELECT * from " + TABLE_NAME, null);
        return eredmeny;
    }
    public Cursor felhasznaloEllenorzes(String email, String jelszo)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor eredmeny = database.rawQuery("SELECT teljesnev from " + TABLE_NAME + " where (email = '" + email + "' OR felhnev= '"+email+"') AND jelszo = '" + md5(jelszo)+"'", null);
        return eredmeny;
    }
    public boolean ellenorzesEmail(String email)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor eredmeny = database.rawQuery("SELECT id from " + TABLE_NAME + " where email = '" + email + "'", null);
        return eredmeny.getCount()==1;
    }
    public boolean ellenorzesFelhnev(String fNev)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor eredmeny = database.rawQuery("SELECT id from " + TABLE_NAME + " where felhnev = '" + fNev + "'", null);
        return eredmeny.getCount()==1;
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


  //  public long adatTorles(int id){
//        SQLiteDatabase database = this.getWritableDatabase();
//        return database.delete(TABLE_NAME,COL_1+" = ?",new String[] {String.valueOf(id)});
//    }

//    public long adatModosit(String id, String email, String felhnev, String jelszo, String teljesnev){
//        SQLiteDatabase database = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(COL_2, email);
//        values.put(COL_3, felhnev);
//        values.put(COL_4, jelszo);
//        values.put(COL_5, teljesnev);
//
//        return database.update(TABLE_NAME, values, COL_1 + " = ?", new String[]{id});
//    }
}
