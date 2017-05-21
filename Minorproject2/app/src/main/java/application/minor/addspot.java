package application.minor;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class addspot extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView date;
    EditText getmessage;
    String radii,radii1="";
    int radius;
    int length;
    String day,month,year,date1;
    Button ok,save;
    Dbhandler1 dbhandler1;
    Spinner spinner1,spinner2;
    ArrayAdapter<CharSequence> adapter1;
    ArrayAdapter<CharSequence> adapter2;
    CalendarView cal;
    Dialog dialog;
    String place,message="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addspot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ok=(Button)findViewById(R.id.ok);
        dialog = new Dialog(addspot.this);
        date=(TextView)findViewById(R.id.date1);
        dialog.setContentView(R.layout.calendar);
        getmessage=(EditText)findViewById(R.id.message1);
        cal = (CalendarView) dialog.findViewById(R.id.calendarview);
        dbhandler1=new Dbhandler1(this,null,null,1);

        save=(Button)findViewById(R.id.save);
        spinner2=(Spinner)findViewById(R.id.spinner2);
        adapter2=ArrayAdapter.createFromResource(this,R.array.places,R.layout.spinner);
        adapter2.setDropDownViewResource(R.layout.spinner);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                place=spinner2.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner1=(Spinner)findViewById(R.id.spinner1);
        adapter1=ArrayAdapter.createFromResource(this,R.array.radii,R.layout.spinner);
        adapter1.setDropDownViewResource(R.layout.spinner);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                radii=spinner1.getSelectedItem().toString();
                length=radii.length();
                radii1="";
                for(int j=0;j<length-1;j++)
                {
                    radii1=radii1+radii.charAt(j);

                }
                radius=Integer.parseInt(radii1);
                Log.d("radi",Double.toString(radius));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        date1= df.format(c.getTime());
        date.setText(date1);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {

                return true;
            }
        }

        return false;
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.exit) {
            this.finishAffinity();

        } else if (id == R.id.addreminder1) {
            Intent start=new Intent(addspot.this,addreminder.class);
            startActivity(start);

        } else if (id == R.id.stored1) {
            Intent start=new Intent(addspot.this,stored.class);
            startActivity(start);
        }
        else if (id == R.id.stored2) {
            Intent start=new Intent(addspot.this,storedspot.class);
            startActivity(start);
        }
        else if (id == R.id.addspot2) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void selectdate(View view)
    {
        dialog.show();
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {

                if(i2<10)
                {
                    day="0"+Integer.toString(i2);
                }
                else
                    day=Integer.toString(i2);
                i1=i1+1;
                if(i1<10)
                    month="0"+Integer.toString(i1);
                else
                    month=Integer.toString(i1);
                date1=day+"/"+month+"/"+ Integer.toString(i);
                date.setText(date1);

                date.setText(date1);

            }
        });}
    public void savespot(View view)
    {
        message=getmessage.getText().toString();
        Log.d("abc",message);
        if(message.isEmpty()==false)
        {
            spot Spot=new spot(message,place,date1,radius);
            dbhandler1.addnewspot(Spot);
            Toast.makeText(addspot.this,"Saved Successfully",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(addspot.this,homescreen.class);
            startActivity(i);
        }
        else
            Toast.makeText(addspot.this,"Please Enter all details",Toast.LENGTH_SHORT).show();
    }
    public void ok(View view)
    {
        dialog.dismiss();
    }
    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}
