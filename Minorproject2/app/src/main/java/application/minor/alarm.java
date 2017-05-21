package application.minor;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class alarm extends Service implements LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "LocationActivity";
    ArrayList<StoredReminders> rem;
    private static final long INTERVAL = 1000 * 3;
    private static final long FASTEST_INTERVAL = 1000 * 1;
    int i=0;
    Dbhandler dbhandler;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;
    TextToSpeech tts1;
    String message1;
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();


        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public alarm(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
    }

    public alarm() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        dbhandler=new Dbhandler(this,null,null,1);



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

        Intent broadcastIntent = new Intent("start");
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
        Toast.makeText(alarm.this,"Wait For Connection",Toast.LENGTH_SHORT).show();

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
        double latit=0,longi=0;
        int id;
        String task,message,date,address;
        Location loc1=new Location("");
        Location loc2=new Location("");
        loc1.setLatitude(location.getLatitude());
        loc1.setLongitude(location.getLongitude());
        AudioManager am;
        am= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        double dis=0.0;
        int i=0;
        mCurrentLocation = location;
        rem=dbhandler.givereminders();
        int size=rem.size();
        if(size!=0)
        {   for(i=0;i<size;i++) {
            message1=rem.get(i).getMessage();
            latit = rem.get(i).getLatitude();
            longi = rem.get(i).getLongitude();
            id=rem.get(i).getId();
            loc2.setLatitude(latit);
            loc2.setLongitude(longi);
            dis = loc2.distanceTo(loc1);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            date= df.format(c.getTime());
            if(date.compareTo(rem.get(i).getDate())==0) {
                if (dis <= rem.get(i).getRadius())
                {

                    dbhandler.delete(id);
                    if (rem.get(i).getTask().compareTo("Just Notify Me") == 0) {
                        tts1=new TextToSpeech(alarm.this, new TextToSpeech.OnInitListener() {

                            @Override
                            public void onInit(int status) {

                                if(status == TextToSpeech.SUCCESS){
                                    int result=tts1.setLanguage(Locale.US);
                                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                                        Log.e("error", "This Language is not supported");
                                    }
                                    else{
                                        tts1.speak(message1, TextToSpeech.QUEUE_FLUSH, null);
                                    }
                                }
                                else
                                    Log.e("error", "Initilization Failed!");
                            }
                        });
                        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notifications);
                        contentView.setImageViewResource(R.id.image, R.drawable.icon);
                        contentView.setTextViewText(R.id.title, rem.get(i).getAddress());
                        contentView.setTextViewText(R.id.text, rem.get(i).getMessage());


                        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        NotificationManager not = (NotificationManager) (getSystemService(NOTIFICATION_SERVICE));
                        NotificationCompat.Builder notif = new NotificationCompat.Builder(this)
                                .setSound(soundUri)
                                .setSmallIcon(R.drawable.icon)
                                .setContent(contentView);

                        Notification mNotification = notif.build();

                        mNotification.flags |= Notification.FLAG_INSISTENT;

                        mNotification.defaults |= Notification.DEFAULT_VIBRATE;
                        not.notify((int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE), mNotification);
                    }
                    else if(rem.get(i).getTask().compareTo("Turn OFF WIFI") == 0)
                    {
                        WifiManager wifiManager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
                        wifiManager.setWifiEnabled(false);
                    }

                    else if(rem.get(i).getTask().compareTo("Put Phone to Ringer Mode") == 0)
                    {
                        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                        am.setStreamVolume(AudioManager.STREAM_RING,am.getStreamMaxVolume(AudioManager.STREAM_RING),0);
                    }
                    else if(rem.get(i).getTask().compareTo("Put Phone to Vibration Mode") == 0)
                    {
                        am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    }
                   /* else if(rem.get(i).getTask().compareTo("Turn OFF Mobile Data") == 0)
                    {
                        ConnectivityManager dataManager;
                        dataManager  = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                        Method dataMtd = null;
                        try {
                            dataMtd = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        dataMtd.setAccessible(true);
                        try {
                            dataMtd.invoke(dataManager, false);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }*/
                    rem.remove(i);
                }
            }
        }
        }

        Log.d("ABC", Double.toString(location.getLatitude()));

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(alarm.this,"Wait For Connection",Toast.LENGTH_SHORT).show();

    }
}
