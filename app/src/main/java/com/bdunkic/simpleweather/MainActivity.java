package com.bdunkic.simpleweather;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.InputType;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
    private static final long MIN_TIME = 0;
    private static final float MIN_DISTANCE = 0;
    TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;

    Typeface weatherFont;
    LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();


        setContentView(R.layout.activity_main);

        initWidgets();
        initLocationManager();
        locationAlert();
        weatherFont = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/weathericons-regular-webfont.ttf");

        weatherIcon.setTypeface(weatherFont);


        getWeather();
        //asyncTask.execute("Latitude", "Longitude");





    }

    private void locationAlert() {

        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user

            DialogFragment df = new WarningDialog();
            df.show(getFragmentManager(),"WD");

        }
    }


    private void initLocationManager() {
        locationManager= (LocationManager)getSystemService(LOCATION_SERVICE);

    }
    private void getWeather() {

        setLatLng();



    }


    boolean locationChecked;
    private void setLatLng() {
        locationChecked = false;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                if (!locationChecked){
                     double latitude = location.getLatitude();
                     double longitude = location.getLongitude();
                    testRequest(latitude,longitude);
                    locationManager.removeUpdates(this);
                    locationChecked = true;

                }


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });

    }

    private void testRequest(double latitude, double longitude) {

        Function.placeIdTask asyncTask =new Function.placeIdTask(new Function.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description,
                                      String weather_temperature, String weather_humidity,
                                      String weather_pressure, String weather_updatedOn,
                                      String weather_iconText, String sun_rise) {

                cityField.setText(weather_city);
                updatedField.setText(weather_updatedOn);
                detailsField.setText(weather_description);
                currentTemperatureField.setText(weather_temperature);
                humidity_field.setText(getString(R.string.humidity)+weather_humidity);
                pressure_field.setText(getString(R.string.pressure)+weather_pressure);
                weatherIcon.setText(Html.fromHtml(weather_iconText));

            }
        });
        asyncTask.execute(latitude+"", longitude+"");
    }

    private void initWidgets() {
        cityField = (TextView)findViewById(R.id.city_field);
        updatedField = (TextView)findViewById(R.id.updated_field);
        detailsField = (TextView)findViewById(R.id.details_field);
        currentTemperatureField = (TextView)findViewById(R.id.current_temperature_field);
        humidity_field = (TextView)findViewById(R.id.humidity_field);
        pressure_field = (TextView)findViewById(R.id.pressure_field);
        weatherIcon = (TextView)findViewById(R.id.weather_icon);
    }


}

