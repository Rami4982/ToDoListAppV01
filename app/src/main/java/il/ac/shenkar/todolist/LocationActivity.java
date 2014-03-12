package il.ac.shenkar.todolist;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class LocationActivity extends Activity
{
    private EditText locationText;
    private Button searchButton;
    public static String EXTRA_MESSAGE = "il.ac.shenkar.todolist.MESSAGE";
    private Address addressC;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;/*     * Define a request code to send to Google Play services     * This code is returned in Activity.onActivityResult     */
    private LocationManager locationManager;
    String titleTask="";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
        searchButton = (Button) this.findViewById(R.id.search_location_button);
        locationText = (EditText) this.findViewById(R.id.location_text);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);// Gets the Location manager
        titleTask = getIntent().getStringExtra(CreateTaskActivity.EXTRA_MESSAGE);
        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(); // Creating new result intent to hold results for the calling activity
                Geocoder gc = new Geocoder(getBaseContext());

                try
                {
                    List<Address> address = gc.getFromLocationName(locationText.getText().toString(), 1); // Getting the LatLng from the address field
//                    List<Address> address = gc.getFromLocationName("יהודה הלוי 5 חולון", 1); // Getting the LatLng from the address field
      //             Toast.makeText(getApplicationContext(), "Location co." + address.get(0).getLatitude(), Toast.LENGTH_LONG).show();

                    addressC = address.get(0);
                    createNotification(v);

                    Toast.makeText(getApplicationContext(), "Location was successfully retrieved to "+addressC.getThoroughfare(), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplication(), TaskListActivity.class));
                }
                catch ( IOException e )
                {
                    Toast.makeText(getApplicationContext(), "Location could not be found, Sorry.", Toast.LENGTH_LONG).show();
                }
            }

        });

    }

    public void createNotification(View view)
    {
        Context context = getApplicationContext();
        Intent intentSent = new Intent(this, MyBroadcastReceiver.class);
//        Toast.makeText(getApplicationContext(), "Location was successfully retrieved", Toast.LENGTH_LONG).show();
        intentSent.putExtra(EXTRA_MESSAGE, titleTask);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 0, intentSent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);//
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        locationManager.addProximityAlert(addressC.getLatitude(), addressC.getLongitude(), 50, -1, pendingIntent2);


    }

    @Override
    protected void onStart()
    {

        super.onStart();
        // Connect the client.
        //  Toast.makeText(this, "Disconnected. Connection lost.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop()
    {
        // Disconnecting the client invalidates it.
        //  mLocationClient.disconnect();
        super.onStop();
    }

}
//                    intent.putExtra("Lat", address.get(0).getLatitude()); // Adding the latitude to the IntentResult
//                    intent.putExtra("Lng", address.get(0).getLongitude()); // Adding the longitude to the IntentResult
//                    intent.putExtra("location", locationText.getText().toString()); // Adding the address string to the IntentResult
//
//                    setResult(Activity.RESULT_OK, intent); // Setting the results for the calling activity

//        String result_text=topicText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, result_text);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_ONE_SHOT);
//        addressC.getLatitude();
//        locationManager.addProximityAlert(addressC.getLatitude(), addressC.getLongitude(),500,-1,pendingIntent );
