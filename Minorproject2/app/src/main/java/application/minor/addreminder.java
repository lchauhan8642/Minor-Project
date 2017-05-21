package application.minor;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class addreminder extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView date,selectarea;
    Intent serviceIntent;
    EditText getmessage;
    String radii,radii1="";
    double radius;
    int length;
    String day,month,year,date1;
    Button ok,save;
    Dbhandler dbhandler;
    Spinner spinner,spinner1;
    ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> adapter1;
    CalendarView cal;
    Dialog dialog;
    double long1,lat1;
    String fulladdress,task,message="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addreminder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ok=(Button)findViewById(R.id.ok);
        dialog = new Dialog(addreminder.this);
        dialog.setContentView(R.layout.calendar);
        getmessage=(EditText)findViewById(R.id.message);
        Intent i=getIntent();
        lat1=i.getDoubleExtra("latitude",0);
        long1=i.getDoubleExtra("longitude",0);
        fulladdress=i.getStringExtra("fulladdress");
        selectarea=(TextView)findViewById(R.id.selectarea);
        serviceIntent = new Intent(this, alarm.class);

            startService(serviceIntent);



        if(fulladdress!=null)
           selectarea.setText(fulladdress);
        cal = (CalendarView) dialog.findViewById(R.id.calendarview);
        dbhandler=new Dbhandler(this,null,null,1);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
save=(Button)findViewById(R.id.save);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        spinner=(Spinner)findViewById(R.id.spinner);
        adapter=ArrayAdapter.createFromResource(this,R.array.tasks,R.layout.spinner);
        adapter.setDropDownViewResource(R.layout.spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                task=spinner.getSelectedItem().toString();
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
                radius=Double.parseDouble(radii1);
                Log.d("radi",Double.toString(radius));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        date=(TextView)findViewById(R.id.date);

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
      date1= df.format(c.getTime());
        date.setText(date1);


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

        int id = item.getItemId();

        if (id == R.id.exit) {
            this.finishAffinity();

        } else if (id == R.id.addreminder1) {


        } else if (id == R.id.stored1) {
            Intent start=new Intent(addreminder.this,stored.class);
            startActivity(start);

        }
        else if (id == R.id.stored2) {
            Intent start=new Intent(addreminder.this,storedspot.class);
            startActivity(start);
        }
        else if (id == R.id.addspot2) {
            Intent start=new Intent(addreminder.this,addspot.class);
            startActivity(start);
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
    public void save(View view)
    {
        message=getmessage.getText().toString();
if(fulladdress!=null&&message.isEmpty()==false)
{
    Reminders reminder=new Reminders(fulladdress,message,task,date1,lat1,long1,radius);
    dbhandler.addnewreminder(reminder);
    Toast.makeText(addreminder.this,"Saved Successfully",Toast.LENGTH_SHORT).show();
    Intent i=new Intent(addreminder.this,homescreen.class);
    startActivity(i);
}
        else
    Toast.makeText(addreminder.this,"Please Enter all details",Toast.LENGTH_SHORT).show();
    }
    public void ok(View view)
    {
        dialog.dismiss();
    }
    public void searchloc(View view)
    {
        Intent i1=new Intent(addreminder.this,Maps.class);
        startActivity(i1);


    }
    @Override
    protected void onDestroy() {
        stopService(serviceIntent);

        super.onDestroy();

    }

}
