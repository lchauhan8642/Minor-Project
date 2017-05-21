package application.minor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Maps extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMarkerDragListener{

    private GoogleMap mMap;
    String fulladdress;
    EditText location;
    MarkerOptions marker1;
    double lat1,long1;

    Button search;
    String loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        location = (EditText) findViewById(R.id.location);
        search=(Button)findViewById(R.id.search);



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setOnMarkerDragListener(this);
        marker1=new MarkerOptions();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true);


        }
        else
            Toast.makeText(Maps.this,"Please turn on device location",Toast.LENGTH_SHORT).show();
    }

    public void onsearch(View view) {
        mMap.clear();
        List<android.location.Address> addressList = null;

        loc = location.getText().toString();
        if(loc.isEmpty()==false) {
            if (loc != null || loc != "") {
                Geocoder geocoder = new Geocoder(this);
                try {
                    addressList = geocoder.getFromLocationName(loc, 1);
                } catch (IOException e) {

                    e.printStackTrace();
                }
                if (addressList.size()!=0) {
                    android.location.Address address = addressList.get(0);

                        LatLng latlng = new LatLng(address.getLatitude(), address.getLongitude());
                        lat1 = address.getLatitude();
                        long1 = address.getLongitude();


                        Geocoder geocoder1;

                        List<android.location.Address> addresses = null;
                        geocoder1 = new Geocoder(this, Locale.getDefault());
                        try {
                            addresses = geocoder1.getFromLocation(address.getLatitude(), address.getLongitude(), 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String address1 = addresses.get(0).getAddressLine(0);
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();

                    fulladdress=address1+","+city+","+country;
                    location.setText(fulladdress);


                    marker1.position(latlng);

                    marker1.title(fulladdress);
                    mMap.addMarker(marker1.draggable(true));



                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));


                }
            }
        }
        else
            Toast.makeText(Maps.this,"Enter Location",Toast.LENGTH_SHORT).show();

    }
    public void onsaveloc(View view)
    {   if(fulladdress==null) {
        Toast.makeText(Maps.this,"Enter Location",Toast.LENGTH_SHORT).show();
    }


    else

    {
        Intent i = new Intent(Maps.this, addreminder.class);

        i.putExtra("fulladdress", fulladdress);

        i.putExtra("latitude", lat1);
        i.putExtra("longitude", long1);
        startActivity(i);
    }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {


    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
      lat1=marker.getPosition().latitude;
        long1=marker.getPosition().longitude;

        Geocoder geocoder1;
        List<android.location.Address> addresses=null;
        geocoder1 = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder1.getFromLocation(lat1, long1, 1);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        String address1 = addresses.get(0).getAddressLine(0);
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();

        fulladdress=address1+","+city+","+country;
        location.setText(fulladdress);
        marker.setTitle(fulladdress);


                mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
    }
}
