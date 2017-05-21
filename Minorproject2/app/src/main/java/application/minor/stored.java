package application.minor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class stored extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AdapterView.OnItemLongClickListener,AdapterView.OnItemClickListener{
    Dbhandler dbhandler;
    LinearLayout lin;
    public adapterclass adap;
    ArrayList<StoredReminders> rem;
    ListView list;
    TextView norem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stored);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    norem=(TextView)findViewById(R.id.norem);
        lin=(LinearLayout)findViewById(R.id.linlay);
        setSupportActionBar(toolbar);
        list = (ListView) findViewById(R.id.list);
        list.setLongClickable(true);
        list.setOnItemLongClickListener(this);
        list.setOnItemClickListener(this);
        list.setClickable(true);


        dbhandler = new Dbhandler(this, null, null, 1);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        rem = dbhandler.givereminders();

        if (rem.size()!=0)
        {     lin.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
            adap = new adapterclass(stored.this, rem);
            list.setAdapter(adap);
            adap.setReminders(rem);
        }
        else
        {
            list.setVisibility(View.GONE);
            lin.setVisibility(View.VISIBLE);

        }


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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.exit) {
            this.finishAffinity();

        } else if (id == R.id.addreminder1) {
            Intent start=new Intent(stored.this,addreminder.class);
            startActivity(start);

        } else if (id == R.id.stored1) {


        }
        else if (id == R.id.stored2) {
            Intent start=new Intent(stored.this,storedspot.class);
            startActivity(start);
        }
        else if (id == R.id.addspot2) {
            Intent start=new Intent(stored.this,addspot.class);
            startActivity(start);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





    @Override
    public boolean onItemLongClick(AdapterView<?> l, View v,
                                   final int position, long id) {



        dbhandler.delete(rem.get(position).getId());
        rem.remove(position);
        if(rem.size()==0)
        {
            list.setVisibility(View.GONE);
            lin.setVisibility(View.VISIBLE);
        }
        adap.notifyDataSetChanged();


        return true;
    }
    @Override
    public void onItemClick(AdapterView<?> l, View v,
                                   final int position, long id) {


        Toast.makeText(stored.this,"Hold to Delete Reminder",Toast.LENGTH_SHORT).show();

    }
}






