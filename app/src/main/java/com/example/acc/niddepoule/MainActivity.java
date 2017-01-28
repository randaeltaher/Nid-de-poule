package com.example.acc.niddepoule;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.location.LocationListener;

public class MainActivity extends AppCompatActivity implements SensorEventListener, LocationListener {

    private Sensor mySensor;
    private SensorManager SM;
    private Double ZA, ZE;
    private int i = 0;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("potholes/pothole1");

    public LocationManager locationManager;
    private String msg;


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            i++;
            if (sensorEvent.values[2] < 5.0) {
                myRef.child("potehole" + String.valueOf(i)).child("value").setValue(String.valueOf(sensorEvent.values[2]));
                myRef.child("potehole" + String.valueOf(i)).child("Location").setValue(msg);


            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // Find the View that shows the weather category
        TextView weather = (TextView) findViewById(R.id.weather);

        // Set a click listener on that View
        weather.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the weather category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link Weather_Activity}
                Intent weatherIntent = new Intent(MainActivity.this, Weather_Activity.class);

                // Start the new activity
                startActivity(weatherIntent);
            }
        });

        // Find the View that shows the map
        final TextView map = (TextView) findViewById(R.id.map);

        // Set a click listener on that View
        map.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the map is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link Map activity}
                Intent mapIntent = new Intent(MainActivity.this, Map_Activity.class);

                // Start the new activity
                startActivity(mapIntent);
            }
        });


        SM = (SensorManager) getSystemService(SENSOR_SERVICE);
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
        //location manager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);


    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}


