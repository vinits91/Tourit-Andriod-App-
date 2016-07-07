package phonebook.vinitshah.com.tourit;


import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.sql.Time;
import java.util.ArrayList;

import helper.Attraction;

public class PreActivity extends AppCompatActivity {

    Button next;
    Button b2;

    ArrayList<Attraction> AttrList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        AttrList = new ArrayList<Attraction>();
        Attraction ChiB = new Attraction(R.drawable.chicagobean, "Chicago Bean",
                "Cloud Gate is a public sculpture by Indian-born British artist Anish Kapoor. " +
                        "The sculpture is nicknamed The Bean because of its shape.\n Made up of 168 stainless steel plates welded together, its highly polished exterior has no visible seams. " +
                        "Kapoor's design was inspired by liquid mercury and the sculpture's surface reflects and distorts the city's skyline. " +
                        "The sculpture is popular with tourists as a photo-taking opportunity for its unique reflective properties.",
                41.8825601, -87.6231439,
                "","",
                "Cloud Gate, Chicago, IL", "https://www.youtube.com/embed/IVqWosmM8VA",
                4.8f,"chicagobean"
        );
        Attraction NvP = new Attraction(R.drawable.navypier, "Navy Pier",
                "Navy Pier is a 3,300-foot-long (1,010 m) pier on the Chicago shoreline of Lake Michigan. \n\n\n" +
                        "It was a part of the Plan of Chicago developed by architect and city planner Daniel Burnham and his associates. " +
                        "Its primary purpose was as a cargo facility for lake freighters, and warehouses were built up and down the Pier. " +
                        "Today, the pier is one of the most visited attractions in the entire Midwestern United States and is Chicago's number one tourist attraction.",
                41.8915103,-87.6047051,
                "", "",
                "Navy Pier, 600 E Grand Ave, Chicago, IL 60611", "https://www.youtube.com/embed/-yic_5_Qz_w",
                4.2f,"navypier"
        );
        Attraction WlsT = new Attraction(R.drawable.willistower, "Willis Tower",
                "The Willis Tower, built and still commonly referred to as Sears Tower, is a 108-story, 1,451-foot (442 m) skyscraper in Chicago, Illinois, United States. \n" +
                        "At completion in 1973, it surpassed the World Trade Center towers in New York to become the tallest building in the world, a title it held for nearly 25 years. " +
                        "The Willis Tower is the second-tallest building in the United States and the 14th-tallest in the world. " +
                        "More than one million people visit its observation deck each year, making it one of Chicago's most popular tourist destinations. ",
                41.8788339,-87.6359563,
                "", "",
                "Willis Tower, 233 S Wacker Dr, Chicago, IL 60606", "https://www.youtube.com/watch?v=cixpZBorNTI",
                4.4f,"willistower"
        );
        Attraction FldM = new Attraction(R.drawable.fieldmuseum, "Field Museum",
                "The Field Museum of Natural History is a natural history museum in Chicago, " +
                        "and is one of the largest such museums in the world.\n " +
                        "The museum maintains its status as a premier natural history museum through the size and quality of its educational and " +
                        "scientific programs, as well as due to its extensive scientific specimen and artifact collections. " +
                        "The diverse, high quality permanent exhibitions, which attract up to 2 million visitors annually, " +
                        "range from the earliest fossils to past and current cultures from around the world " +
                        "to interactive programming demonstrating today's urgent conservation needs. ",
                41.8662592,-87.6168732,
                "", "",
                "1400 S Lake Shore Dr，Chicago, IL 60605", "https://www.youtube.com/embed/b6t10od7Xio",
                4.8f,"fieldmuseum"
        );
        Attraction Shedd = new Attraction(R.drawable.sheddaquarium, "Shedd Aquarium",
                "Shedd Aquarium is an indoor public aquarium in Chicago, Illinois in the United States, " +
                        "it contains 1500 species including fish, marine mammals, birds, snakes, amphibians, and insects.\n " +
                        "Shedd Aquarium was the first inland aquarium with a permanent saltwater fish collection. ",
                41.8651409,-87.6169207,
                "", "",
                "1200 S Lake Shore Dr, Chicago, IL 60605", " https://www.youtube.com/watch?v=fH-ECNbWJjo",
                4.8f,"sheddaquarium"
        );

        AttrList.add(ChiB);
        AttrList.add(NvP);
        AttrList.add(WlsT);
        AttrList.add(FldM);
        AttrList.add(Shedd);

        next=(Button) findViewById(R.id.next);
        /*
        * This button pass a bundle of attractions list to AttrationList activity
        * */

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //


                Intent intent = new Intent(PreActivity.this, FavList.class);
                Bundle bdl = new Bundle();
                //bdl.putSerializable("AttrList", AttrList);
                //intent.putExtras(bdl);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_pre, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*//noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

}
