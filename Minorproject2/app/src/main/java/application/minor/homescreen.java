package application.minor;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class homescreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Dialog dialog;
    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialog = new Dialog(homescreen.this);
        dialog.setContentView(R.layout.dialogbox);
        serviceIntent = new Intent(this, spotalarm.class);

        startService(serviceIntent);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
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
            finishAffinity();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.exit) {
            this.finishAffinity();

        } else if (id == R.id.addreminder1) {
            Intent start=new Intent(homescreen.this,addreminder.class);
            startActivity(start);

        } else if (id == R.id.stored1) {
            Intent start=new Intent(homescreen.this,stored.class);
            startActivity(start);
        }
        else if (id == R.id.stored2) {
            Intent start=new Intent(homescreen.this,storedspot.class);
            startActivity(start);
        }
        else if (id == R.id.addspot2) {
            Intent start=new Intent(homescreen.this,addspot.class);
            startActivity(start);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void locrem(View view)
    {
        dialog.dismiss();
        Intent i=new Intent(homescreen.this,addreminder.class);
        startActivity(i);
    }
    public void spotfinder(View view)
    {
        dialog.dismiss();
        Intent i1=new Intent(homescreen.this,addspot.class);
        startActivity(i1);
    }
    @Override
    protected void onDestroy() {
        dialog.dismiss();
        stopService(serviceIntent);

        super.onDestroy();

    }

}
