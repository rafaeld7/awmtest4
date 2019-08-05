package com.AWM.awmtest4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpView();
        setUpViewPagerAdapter();


    }

    private void setUpView(){
        imageView = findViewById(R.id.imageView);
        appBarLayout = findViewById(R.id.appBarlayout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

    }

    private void setUpViewPagerAdapter() {
        viewPagerAdapter.addfragment(new Opcion1Fragment(),  getString(R.string.tab_1));
        viewPagerAdapter.addfragment(new Opcion2Fragment(), getString(R.string.tab_2));
        viewPagerAdapter.addfragment(new Opcion3Fragment(), getString(R.string.tab_3));
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        imageView.setImageResource(R.drawable.ic_home_black_24dp);
                        Log.d("TAG1","Posicion" + tabLayout.getSelectedTabPosition()+ " Titulo: " + viewPagerAdapter.getPageTitle(tabLayout.getSelectedTabPosition()));
                        break;
                    case 1:
                        imageView.setImageResource(R.drawable.ic_add_circle_outline_black_24dp);
                        Log.d("TAG1","Posicion" + tabLayout.getSelectedTabPosition()+ " Titulo: " + viewPagerAdapter.getPageTitle(tabLayout.getSelectedTabPosition()));
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.ic_account_circle_black_24dp);
                        Log.d("TAG1","Posicion" + tabLayout.getSelectedTabPosition()+ " Titulo: " + viewPagerAdapter.getPageTitle(tabLayout.getSelectedTabPosition()));
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /*public void editarActivity (){
        Intent i = new Intent(this, editarActivity.class) ;
        startActivity(i);
    }*/
}
