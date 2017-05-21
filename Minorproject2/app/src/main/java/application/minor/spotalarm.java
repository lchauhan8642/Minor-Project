package application.minor;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class  spotalarm extends Service implements LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    double latitude;
    ArrayList<spot1> storedspot = new ArrayList<spot1>();
    Dbhandler1 dbhandler1;
    double longitude;
    int b;
    private int PROXIMITY_RADIUS = 3000;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 *8;
    LocationRequest mLocationRequest;
    Location mCurrentLocation;
    String place1="";
    int size1=0;
    String pla;
    String message;
    int size;
    TextToSpeech tts;
    int i;
    String mess;

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();


        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public spotalarm(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
    }

    public spotalarm() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        dbhandler1 = new Dbhandler1(this, null, null, 1);
        storedspot = dbhandler1.givespot();


        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Intent broadcastIntent = new Intent("spot");
        sendBroadcast(broadcastIntent);

    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(spotalarm.this, "Wait For Connection", Toast.LENGTH_SHORT).show();

    }

    protected void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onLocationChanged(Location location) {
        String place;


        size = storedspot.size();


        if (size != 0) {

            for (i = 0; i<size; i++) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
               String date= df.format(c.getTime());

                if(date.compareTo(storedspot.get(i).getDate())==0) {
                    b = i;
                    mess = storedspot.get(i).getMessage();
                    pla = storedspot.get(i).getPlace();
                    Log.d("place", pla);
                    place1 = storedspot.get(i).getPlace().toLowerCase();
                    place1 = place1.replace(' ', '_');
                    String url = getUrl(location.getLatitude(), location.getLongitude(), place1, storedspot.get(i).getRadius());


                    Object[] DataTransfer = new Object[1];

                    DataTransfer[0] = url;
                    GetNearbyPlacesData1 getNearbyPlacesData = new GetNearbyPlacesData1();
                    getNearbyPlacesData.execute(DataTransfer);
                    break;

                }
            }
        }
    }
    public String giveplace()
    {

        return pla;
    }
    public String givemessage()
    {

        return mess;
    }
    public int givei()
    {
        return b;
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace,int rad) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + rad);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyATuUiZUkEc_UgHuqsBJa1oqaODI-3mLs0");



        return (googlePlacesUrl.toString());
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(spotalarm.this, "Wait For Connection", Toast.LENGTH_SHORT).show();

    }

    class GetNearbyPlacesData1 extends AsyncTask<Object, String, String>  {

        String googlePlacesData;
        GoogleMap mMap;
        String url;


        @Override
        protected String doInBackground(Object... params) {
            try {

                // mMap = (GoogleMap) params[0];
                url = (String) params[0];
                DownloadUrl downloadUrl = new DownloadUrl();
                googlePlacesData = downloadUrl.readUrl(url);
                Log.d("efgh",googlePlacesData);

            } catch (Exception e) {

            }
            return googlePlacesData;
        }

        @Override
        protected void onPostExecute(String result) {


            List<HashMap<String, String>> nearbyPlacesList = null;
            DataParser dataParser = new DataParser();

            if(result!="") {


                nearbyPlacesList = dataParser.parse(result);
                size1 = nearbyPlacesList.size();
            }

            if (size1==0) {

            } else {
             //   message=storedspot.get(spotalarm.this.givei()).getMessage();

                RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notifications);
                contentView.setImageViewResource(R.id.image, R.drawable.icon);
                contentView.setTextViewText(R.id.title, spotalarm.this.givemessage());
                contentView.setTextViewText(R.id.text, spotalarm.this.giveplace());




                Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationManager not = (NotificationManager) (getSystemService(NOTIFICATION_SERVICE));
                NotificationCompat.Builder notif = new NotificationCompat.Builder(spotalarm.this)
                        .setSound(soundUri)
                        .setSmallIcon(R.drawable.icon)
                        .setContent(contentView);

                Notification mNotification = notif.build();
                Intent notificationIntent = new Intent(spotalarm.this, showplaces.class);

                Log.d("i",Integer.toString(spotalarm.this.givei()));
                tts=new TextToSpeech(spotalarm.this, new TextToSpeech.OnInitListener() {

                    @Override
                    public void onInit(int status) {

                        if(status == TextToSpeech.SUCCESS){
                            int result=tts.setLanguage(Locale.US);
                            if(result==TextToSpeech.LANG_MISSING_DATA ||
                                    result==TextToSpeech.LANG_NOT_SUPPORTED){
                                Log.e("error", "This Language is not supported");
                            }
                            else{
                                tts.speak(spotalarm.this.givemessage(), TextToSpeech.QUEUE_FLUSH, null);
                            }
                        }
                        else
                            Log.e("error", "Initilization Failed!");
                    }
                });

                dbhandler1.delete(storedspot.get(spotalarm.this.givei()).getId());
                storedspot.remove(spotalarm.this.givei());


                notificationIntent.putExtra("place", result);
                int reqid=(int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
                PendingIntent contentIntent = PendingIntent.getActivity(spotalarm.this, reqid, notificationIntent, 0);
                mNotification.contentIntent = contentIntent;
                mNotification.flags |= Notification.FLAG_INSISTENT;
                mNotification.defaults |= Notification.DEFAULT_VIBRATE;


Log.d("abc",Integer.toString(size));


                not.notify(reqid, mNotification);

            }
        }
    }
}


