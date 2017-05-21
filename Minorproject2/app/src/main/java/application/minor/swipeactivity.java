package application.minor;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class swipeactivity extends FragmentActivity{
ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_swipeactivity);
        viewPager=(ViewPager)findViewById(R.id.view_pager);
        swipeadapter sa=new swipeadapter(getSupportFragmentManager());
        viewPager.setAdapter(sa);
        Log.d("go","gp");

    }
    public void ongo(View view)
    {
        Intent i=new Intent(this,homescreen.class);
        startActivity(i);
    }
    public void onskip3(View view)
    {
        Intent i=new Intent(this,homescreen.class);
        startActivity(i);
    }
    public void onskip2(View view)
    {
        Intent i=new Intent(this,homescreen.class);
        startActivity(i);
    }
    public void onskip1(View view)
    {
        Intent i=new Intent(this,homescreen.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
