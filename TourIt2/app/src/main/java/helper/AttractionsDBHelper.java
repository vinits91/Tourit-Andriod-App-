package helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
//Database helper to create and fetch data from database
public class AttractionsDBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    Context context;
    //Database Name
    private static final String DATABASE_NAME = "TourIT.db";

    //Table Name
    private static final String TABLE_NAME = "Attractions";

    private static final String COLUMN_NAME_Id = "id";
    private static final String COLUMN_NAME_Name="name";
    private static final String COLUMN_NAME_Description="description";
    private static final String COLUMN_NAME_Latitude="latitude";
    private static final String COLUMN_NAME_Longitude="longitude";
    private static final String COLUMN_NAME_Openhours="openhours";
    private static final String COLUMN_NAME_Closehours="closehours";
    private static final String COLUMN_NAME_Address="address";
    private static final String COLUMN_NAME_VideoLink="videoLink";
    private static final String COLUMN_NAME_Rating="rating";
    private static final String COLUMN_NAME_Picture="picture";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INT";
    private static final String DOUBLE_TYPE = " DOUBLE";
    private static final String FLOAT_TYPE = " FLOAT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COLUMN_NAME_Id+ " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_Name + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_Description + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_Latitude +DOUBLE_TYPE+ COMMA_SEP +
                    COLUMN_NAME_Longitude+DOUBLE_TYPE+ COMMA_SEP +
                    COLUMN_NAME_Openhours+ TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_Closehours+ TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_Address+ TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_VideoLink+ TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_Rating+ FLOAT_TYPE+ COMMA_SEP +
                    COLUMN_NAME_Picture+TEXT_TYPE +" )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public AttractionsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over

        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /*
    Retrieve all the records from the table Attractions
     */
    public Cursor getAllAttractions()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from Attractions", null);
        System.out.println("Row count" + res.getCount());

        return res;
    }
    /*
    Initialize the data at startup of the app.
     Data will be stored in local db */
    public void initializeDB(){
        long newRowId;
        Log.i("AttractionsDBHelper","Creating an Attraction DB");
// Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME_Id,1 );
        values.put(COLUMN_NAME_Name,"Lincoln Park Zoo" );
        values.put(COLUMN_NAME_Description,"Lincoln Park Zoo is a free 35-acre zoo located in Lincoln Park in Chicago, Illinois. " +
                "The zoo was founded in 1868, " +
                "making it one of the oldest zoos in the U.S. It is also one of a few free admission zoos in the United States.");
        values.put(COLUMN_NAME_Latitude,41.9208903);
        values.put(COLUMN_NAME_Longitude,-87.6329172);
        values.put(COLUMN_NAME_Openhours,10);
        values.put(COLUMN_NAME_Closehours,5);
        values.put(COLUMN_NAME_Address,"2001 N Clark St, Chicago, IL 60614");
        values.put(COLUMN_NAME_VideoLink,"https://www.youtube.com/embed/zx4ptBNWlgI");
        values.put(COLUMN_NAME_Rating,4.6);
        values.put(COLUMN_NAME_Picture,"lincolnpark");

// Insert the new row, returning the primary key value of the new row

        db.insert(TABLE_NAME, null, values);
        values.clear();

        values.put(COLUMN_NAME_Id, 2);
        values.put(COLUMN_NAME_Name, "Willis Tower");
        values.put(COLUMN_NAME_Description,"The Willis Tower, built and still commonly referred to as Sears Tower, is a 108-story, 1,451-foot (442 m) skyscraper in Chicago, Illinois, United States. " +
                "At completion in 1973, it surpassed the World Trade Center towers in New York to become the tallest building in the world, a title it held for nearly 25 years. " +
                "The Willis Tower is the second-tallest building in the United States and the 14th-tallest in the world. " +
                "More than one million people visit its observation deck each year, making it one of Chicago's most popular tourist destinations. ");
        values.put(COLUMN_NAME_Latitude,41.8788764);
        values.put(COLUMN_NAME_Longitude,-87.6359149);
        values.put(COLUMN_NAME_Openhours,10 );
        values.put(COLUMN_NAME_Closehours,8);
        values.put(COLUMN_NAME_Address,"233 S Wacker Dr, Chicago, IL 60606");
        values.put(COLUMN_NAME_VideoLink,"https://www.youtube.com/embed/ubS4Fy7zDUU");
        values.put(COLUMN_NAME_Rating,4.4);
        values.put(COLUMN_NAME_Picture,"willistower");
        db.insert(TABLE_NAME, null, values);
        values.clear();

        values.put(COLUMN_NAME_Id,3 );
        values.put(COLUMN_NAME_Name,"Chicago Bean" );
        values.put(COLUMN_NAME_Description,"Cloud Gate is a public sculpture by Indian-born British artist Anish Kapoor. " +
                "The sculpture is nicknamed The Bean because of its shape. Made up of 168 stainless steel plates welded together, its highly polished exterior has no visible seams. " +
                "Kapoor's design was inspired by liquid mercury and the sculpture's surface reflects and distorts the city's skyline. " +
                "The sculpture is popular with tourists as a photo-taking opportunity for its unique reflective properties.");
        values.put(COLUMN_NAME_Latitude,41.8826572);
        values.put(COLUMN_NAME_Longitude,-87.6233039);
        values.put(COLUMN_NAME_Openhours,6);
        values.put(COLUMN_NAME_Closehours,11);
        values.put(COLUMN_NAME_Address,"Chicago, IL");
        values.put(COLUMN_NAME_VideoLink,"https://www.youtube.com/embed/XEkcAWlnD78");
        values.put(COLUMN_NAME_Rating,4.8);
        values.put(COLUMN_NAME_Picture,"chicagobean");
        db.insert(TABLE_NAME, null, values);
        values.clear();

        values.put(COLUMN_NAME_Id, 4);
        values.put(COLUMN_NAME_Name,"Navy Pier" );
        values.put(COLUMN_NAME_Description,"Navy Pier is a 3,300-foot-long (1,010 m) pier on the Chicago shoreline of Lake Michigan. " +
                "It was a part of the Plan of Chicago developed by architect and city planner Daniel Burnham and his associates. " +
                "Its primary purpose was as a cargo facility for lake freighters, and warehouses were built up and down the Pier. " +
                "Today, the pier is one of the most visited attractions in the entire Midwestern United States and is Chicago's number one tourist attraction.");
        values.put(COLUMN_NAME_Latitude,41.891598);
        values.put(COLUMN_NAME_Longitude,-87.6045083);
        values.put(COLUMN_NAME_Openhours,10);
        values.put(COLUMN_NAME_Closehours,8);
        values.put(COLUMN_NAME_Address,"600 E Grand Ave, Chicago, IL 60611 ");
        values.put(COLUMN_NAME_VideoLink,"https://www.youtube.com/embed/-yic_5_Qz_w");
        values.put(COLUMN_NAME_Rating,4.1);
        values.put(COLUMN_NAME_Picture,"navypier");
        db.insert(TABLE_NAME, null, values);
        values.clear();

        values.put(COLUMN_NAME_Id, 5);
        values.put(COLUMN_NAME_Name,"Shedd Aquarium" );
        values.put(COLUMN_NAME_Description,"Shedd Aquarium is an indoor public aquarium in Chicago, Illinois in the United States . " +
                "It contains 1500 species including fish, marine mammals, birds, snakes, amphibians, and insects. " +
                "Shedd Aquarium was the first inland aquarium with a permanent saltwater fish collection." +
                " The aquarium has 2 million annual visitors; " +
                "it was the most visited aquarium in the U.S. in 2005, and in 2007, it surpassed the Field Museum as the most popular cultural attraction in Chicago ");
        values.put(COLUMN_NAME_Latitude,41.8651409);
        values.put(COLUMN_NAME_Longitude,-87.6169207);
        values.put(COLUMN_NAME_Openhours,9);
        values.put(COLUMN_NAME_Closehours,5);
        values.put(COLUMN_NAME_Address,"1200 S Lake Shore Dr, Chicago, IL 60605");
        values.put(COLUMN_NAME_VideoLink,"https://www.youtube.com/embed/fH-ECNbWJjo");
        values.put(COLUMN_NAME_Rating,4.1);
        values.put(COLUMN_NAME_Picture,"sheddaquarium");
        db.insert(TABLE_NAME, null, values);
        values.clear();

        values.put(COLUMN_NAME_Id,6 );
        values.put(COLUMN_NAME_Name,"Field Museum" );
        values.put(COLUMN_NAME_Description,"The Field Museum of Natural History is a natural history museum in Chicago, " +
                "and is one of the largest such museums in the world. " +
                "The museum maintains its status as a premier natural history museum through the size and quality of its educational and " +
                "scientific programs, as well as due to its extensive scientific specimen and artifact collections. " +
                "The diverse, high quality permanent exhibitions, which attract up to 2 million visitors annually, " +
                "range from the earliest fossils to past and current cultures from around the world " +
                "to interactive programming demonstrating today's urgent conservation needs. ");
        values.put(COLUMN_NAME_Latitude,41.866261);
        values.put(COLUMN_NAME_Longitude,-87.6169805);
        values.put(COLUMN_NAME_Openhours,9);
        values.put(COLUMN_NAME_Closehours,5);
        values.put(COLUMN_NAME_Address,"1400 S Lake Shore Dr, Chicago, IL 60605");
        values.put(COLUMN_NAME_VideoLink,"https://www.youtube.com/embed/b6t10od7Xio");
        values.put(COLUMN_NAME_Rating,4.5);
        values.put(COLUMN_NAME_Picture,"fieldmuseum");
        db.insert(TABLE_NAME, null, values);
        values.clear();

        values.put(COLUMN_NAME_Id,7 );
        values.put(COLUMN_NAME_Name,"Oak Street Beach" );
        values.put(COLUMN_NAME_Description,"Oak Street Beach is located on North Lake Shore Drive in Chicago, Illinois, " +
                "on the shore of Lake Michigan. One of a series of Chicago beaches, the Chicago Park District " +
                "defines Oak Street Beach as the area from approximately 1550 North Lake Shore Drive to 500 North Lake Shore Drive, " +
                "including Ohio Street Beach, the South Ledge, a concrete path running from Ohio Street beach to the Oak Street Curve, " +
                "Oak Street Beachstro Restaurant, " +
                "Oak Street Beach proper, the North Ledge, and a concrete path running from Oak Street Beach to North Avenue Beach.");
        values.put(COLUMN_NAME_Latitude,41.902972);
        values.put(COLUMN_NAME_Longitude,-87.6249527);
        values.put(COLUMN_NAME_Openhours,11);
        values.put(COLUMN_NAME_Closehours,7);
        values.put(COLUMN_NAME_Address,"1000 N Lake Shore Dr, Chicago,IL");
        values.put(COLUMN_NAME_VideoLink,"https://www.youtube.com/embed/YbHLQe_00u0");
        values.put(COLUMN_NAME_Rating,4);
        values.put(COLUMN_NAME_Picture,"beach");
        db.insert(TABLE_NAME, null, values);
        values.clear();

        values.put(COLUMN_NAME_Id,8 );
        values.put(COLUMN_NAME_Name,"Chicago Theatre" );
        values.put(COLUMN_NAME_Description,"The Chicago Theatre, originally known as the Balaban and Katz Chicago Theatre, " +
                "is a landmark theater located on North State Street in the Loop area of Chicago, Illinois, in the United States." +
                "The original 1921 interior decoration of the auditorium included fourteen large romantic French-themed murals " +
                "surrounding the proscenium by Chicago artist Louis Grell (1887-1960), " +
                "a common feature that Rapp and Rapp architects included in their movie palace designs.");
        values.put(COLUMN_NAME_Latitude,41.8853157);
        values.put(COLUMN_NAME_Longitude,-87.6277052);
        values.put(COLUMN_NAME_Openhours,12);
        values.put(COLUMN_NAME_Closehours,1);
        values.put(COLUMN_NAME_Address,"175 N State St, Chicago, IL 60601");
        values.put(COLUMN_NAME_VideoLink,"https://www.youtube.com/embed/5UNqtjsAX_s");
        values.put(COLUMN_NAME_Rating,4.5);
        values.put(COLUMN_NAME_Picture,"theater");
        db.insert(TABLE_NAME, null, values);
        values.clear();

        values.put(COLUMN_NAME_Id, 9);
        values.put(COLUMN_NAME_Name,"Chicago Water Tower" );
        values.put(COLUMN_NAME_Description,"The Chicago Water Tower is a contributing property in the Old Chicago Water " +
                "Tower District landmark district. The tower was constructed to house a large water pump, intended to draw water " +
                "from Lake Michigan. It is the second-oldest water tower in the United States, after the Louisville Water Tower in " +
                "Louisville, Kentucky.The Chicago Water Tower now serves as a Chicago Office of Tourism art gallery known as the " +
                "City Gallery in the Historic Water Tower. " +
                "It features the work of local photographers and artists.");
        values.put(COLUMN_NAME_Latitude,41.8971797 );
        values.put(COLUMN_NAME_Longitude,-87.6243939);
        values.put(COLUMN_NAME_Openhours,10);
        values.put(COLUMN_NAME_Closehours,6.30);
        values.put(COLUMN_NAME_Address,"806 N Michigan Ave, Chicago, IL 60611");
        values.put(COLUMN_NAME_VideoLink,"https://www.youtube.com/embed/Qol_nMQaE9k");
        values.put(COLUMN_NAME_Rating,4.2);
        values.put(COLUMN_NAME_Picture,"watertower");
        db.insert(TABLE_NAME, null, values);
        values.clear();

        values.put(COLUMN_NAME_Id, 10);
        values.put(COLUMN_NAME_Name,"Adler Planetarium" );
        values.put(COLUMN_NAME_Description," The Adler Planetarium is a public museum dedicated to the study of astronomy and astrophysics. " +
                "The Adler is America's first planetarium and part of Chicago's Museum Campus, which includes the John G. " +
                "Shedd Aquarium and The Field Museum. The Adler's mission is to inspire exploration and understanding of the Universe." +
                "The Adler is home to three full size theaters, extensive space science exhibitions, " +
                "and a significant collection of antique scientific instruments and print materials.");
        values.put(COLUMN_NAME_Latitude,41.866333);
        values.put(COLUMN_NAME_Longitude,-87.6067829);
        values.put(COLUMN_NAME_Openhours,9.30);
        values.put(COLUMN_NAME_Closehours,4);
        values.put(COLUMN_NAME_Address,"1300 S Lake Shore Dr, Chicago, IL 60605");
        values.put(COLUMN_NAME_VideoLink,"https://www.youtube.com/embed/VKAJMsEhfH4");
        values.put(COLUMN_NAME_Rating,4.3);
        values.put(COLUMN_NAME_Picture,"planetarium");
        db.insert(TABLE_NAME, null, values);
        values.clear();

        values.put(COLUMN_NAME_Id,11 );
        values.put(COLUMN_NAME_Name,"Buckingham Fountain" );
        values.put(COLUMN_NAME_Description,"Buckingham Fountain is a Chicago landmark in the center of Grant Park. " +
                "Dedicated in 1927, it is one of the largest fountains in the world. " +
                "Built in a rococo wedding cake style and inspired by the Latona Fountain at the Palace of Versailles, " +
                "it is meant to allegorically represent Lake Michigan. It operates from April to October, with regular water shows and " +
                "evening color-light shows. " +
                "During the winter, the fountain is decorated with festival lights.");
        values.put(COLUMN_NAME_Latitude,41.8757944);
        values.put(COLUMN_NAME_Longitude,-87.6189483);
        values.put(COLUMN_NAME_Openhours,8);
        values.put(COLUMN_NAME_Closehours,11);
        values.put(COLUMN_NAME_Address,"301 S Columbus Dr, Chicago, IL 60605");
        values.put(COLUMN_NAME_VideoLink,"https://www.youtube.com/embed/zD35RhCn_es");
        values.put(COLUMN_NAME_Rating,4.5);
        values.put(COLUMN_NAME_Picture,"fountain");
        db.insert(TABLE_NAME, null, values);
        values.clear();

        values.put(COLUMN_NAME_Id,12 );
        values.put(COLUMN_NAME_Name,"Chicago Cultural Center" );
        values.put(COLUMN_NAME_Description,"As the nation's first free municipal cultural center, " +
                "the Chicago Cultural Center is one of the city's most popular attractions and " +
                "is considered one of the most comprehensive arts showcases in the United States. " +
                "Each year, the Chicago Cultural Center features more than 1,000 programs and exhibitions covering a wide range of " +
                "the performing, visual and literary arts. " +
                "It also serves as headquarters for the Chicago Children's Choir.");
        values.put(COLUMN_NAME_Latitude,41.8837536);
        values.put(COLUMN_NAME_Longitude,-87.6249515);
        values.put(COLUMN_NAME_Openhours,9);
        values.put(COLUMN_NAME_Closehours,7);
        values.put(COLUMN_NAME_Address,"78 E Washington St, Chicago, IL 60602");
        values.put(COLUMN_NAME_VideoLink,"https://www.youtube.com/embed/GCVl2dyvMGI");
        values.put(COLUMN_NAME_Rating,4.6);
        values.put(COLUMN_NAME_Picture,"culturalcenter");
        db.insert(TABLE_NAME, null, values);
        values.clear();
    }
    /*
    deleting the contents of Attractions table
    -->only for testing purpose .
       */
    public long deleteOrder()

    {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();


        long newRowId;
        newRowId = db.delete(TABLE_NAME, null,null);
        return newRowId;
    }

    //check database exists or not
    public boolean doesDatabaseExist() {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        return dbFile.exists();
    }
}
