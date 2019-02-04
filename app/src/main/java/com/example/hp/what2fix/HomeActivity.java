package com.example.hp.what2fix;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;

public class HomeActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    ImageButton imageButton;
    String username, password;
    String[] Tabs = {"Completed", "Progress", "Pending"};
    String[] sortItems = {"A-Z", "Z-A", "Newest first", "Oldest first", "Order Low to High", "Order High to Low"};
    //public static String customers[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        imageButton = (ImageButton) findViewById(R.id.fab);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, Create.class);
                startActivity(i);
            }
        });

        Intent i = getIntent();
        username = i.getStringExtra("Username");
        password = i.getStringExtra("Password");

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Do you want to logout ?").setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                });
        AlertDialog close = alert.create();
        close.setTitle("Logout");
        close.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_refresh){

        } else if (id == R.id.action_sortby) {
            if(JsonParseCompleted.status[0].equals("Completed")){
                Toast.makeText(HomeActivity.this, "Completed", Toast.LENGTH_SHORT).show();
                //new CompletedFragment().abc();
                /*CompletedFragment.listView.setAdapter(new MyAdapter
                        (new CompletedFragment().getContext(), R.layout.item, JsonParse.orderId, customers, JsonParse.date)
                );*/
            } else if(JsonParseProgress.status[0].equals("In Progress")){
                Toast.makeText(HomeActivity.this, "In Progress", Toast.LENGTH_SHORT).show();
            } else if(JsonParsePending.status[0].equals("Pending")){
                Toast.makeText(HomeActivity.this, "Pending", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(HomeActivity.this, "shi nhi h", Toast.LENGTH_SHORT).show();
            }

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle("Sort By");
            alertBuilder.setSingleChoiceItems(sortItems, 0, new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(HomeActivity.this, sortItems[i], Toast.LENGTH_LONG).show();

                }
            });
            alertBuilder.setCancelable(false)
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(HomeActivity.this, "Ok", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(HomeActivity.this, "Cancel", Toast.LENGTH_LONG).show();
                        }
                    })
            ;
            AlertDialog alertDialog = alertBuilder.create();
            alertDialog.show();
            Button positive = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            Button negative = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            positive.setTextColor(Color.parseColor("#014c6f"));
            negative.setTextColor(Color.parseColor("#014c6f"));

        } else if (id == R.id.action_logout) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Do you want to logout ?").setCancelable(false)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
            ;
            AlertDialog close = alert.create();
            close.setTitle("Logout");
            close.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return new CompletedFragment();
                case 1: return new ProgressFragment();
                case 2: return new PendingFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Tabs[position];
        }
    }

}
