package helper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//database to store the attraction that has been mark as favourite by user, so that we can fetch the details in the view of favourite list
public class FavoritesDBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "TourIT.db";

    //Table Name
    private static final String TABLE_NAME = "Favorites";

    private static final String COLUMN_NAME_Attractions_Id = "id";
    private static final String COLUMN_NAME_Attractions_Name = "name";
    private static final String COLUMN_NAME_Email = "email";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INT";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COLUMN_NAME_Attractions_Id+ INT_TYPE + " primary key" + COMMA_SEP+
                    COLUMN_NAME_Attractions_Name + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_Email + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
    public FavoritesDBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }
    public void initDB(){
        Log.i("FavoritesDBHelper", "Creating a DB for favourite");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQL_CREATE_ENTRIES);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public Cursor getAllFavorites(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from Attractions Inner Join Favorites on Attractions.id = Favorites.id where Favorites.email='"+email+"'", null);
        System.out.println("Row count" + res.getCount());

        return res;
    }

    public long insertFavorites(String email, int id, String name)
    {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
       // db.execSQL(SQL_CREATE_ENTRIES);
// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_Attractions_Name, name);
        values.put(COLUMN_NAME_Email, email);
        values.put(COLUMN_NAME_Attractions_Id, id);


// Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.replace(TABLE_NAME, null,values);

        return newRowId;
    }
    public long deleteFavorites(String name[])

    {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        long newRowId;
        String selection = COLUMN_NAME_Attractions_Name+" = ?";
        newRowId = db.delete(TABLE_NAME, selection,name);
        return newRowId;
    }
}