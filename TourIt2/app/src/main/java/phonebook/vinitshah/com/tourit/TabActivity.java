package phonebook.vinitshah.com.tourit;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import AdaptersPacakge.TabFragmentAdapter;
import Fragments.DescriptionFragment;
import Fragments.PhotosFragment;
import Fragments.VideoFragment;
import helper.Attraction;
//Tab view for Description and Photos
public class TabActivity extends AppCompatActivity implements DescriptionFragment.OnFragmentInteractionListener, PhotosFragment.OnFragmentInteractionListener, VideoFragment.OnFragmentInteractionListener
        {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] tabs = { "Favourites", "PhotosFragment", "Videos" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite2);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Attraction");
        setSupportActionBar(toolbar);
        Log.d("TabActivity","Loading the Tab view");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        Intent intent=getIntent();
        Attraction attraction = (Attraction)intent.getSerializableExtra("attraction");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = DescriptionFragment.newInstance(attraction);

        int tab=intent.getIntExtra("tab",0);
        if(tab==1){
            tabLayout.setScrollPosition(tab,0f,true);
            viewPager.setCurrentItem(tab);
        }

    }

            //loads tab view for photos and description
    private void setupViewPager(ViewPager viewPager) {
        TabFragmentAdapter adapter = new TabFragmentAdapter(getSupportFragmentManager());
        Intent intent=getIntent();
        Attraction attraction = (Attraction)intent.getSerializableExtra("attraction");
        Fragment fragment = DescriptionFragment.newInstance(attraction);
        adapter.addFragment(fragment, "Description");
        adapter.addFragment(new PhotosFragment(), "Photos");
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
