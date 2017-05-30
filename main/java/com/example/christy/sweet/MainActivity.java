package com.example.christy.sweet;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //shared preferrence
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pref = getApplicationContext().getSharedPreferences("pref",0);
        editor = pref.edit();

        id = pref.getString("id",null);
        if(id==null)
        {
            Intent seetha = new Intent(getApplicationContext(),Login.class);
            startActivity(seetha);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.register_places) {
            //parking places in current city
            Parking_places fg = new Parking_places();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.content_main,
                    fg,fg.getTag()).commit();
            // Handle the camera action
        }else if (id == R.id.map_places) {
            Parking_places_map fg = new Parking_places_map();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.content_main,
                    fg,fg.getTag()).commit();
        } else if (id == R.id.nav_gallery) {
            //parking places in a different city
            Search_places fg = new Search_places();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.content_main,
                    fg,fg.getTag()).commit();

        }
        else if (id == R.id.nav_manage) {
            Search_places_map fg = new Search_places_map();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.content_main,
                    fg,fg.getTag()).commit();
            /*Maps fg = new Maps();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.content_main,
                    fg,fg.getTag()).commit();*/
        } else if (id == R.id.logout) {
            Logout fg = new Logout();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.content_main,
                    fg,fg.getTag()).commit();
            //logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logout(){
        //Toast.makeText(getContext(), "Your logout", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder
                = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("Are you sure you want to register?")
                .setTitle("Are you sure?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Perform some action such as saving the item
                                Toast.makeText(getApplicationContext(),"Your ok the ",Toast.LENGTH_LONG).show();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }
}
