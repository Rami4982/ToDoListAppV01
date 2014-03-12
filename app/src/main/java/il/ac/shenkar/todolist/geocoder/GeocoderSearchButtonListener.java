package il.ac.shenkar.todolist.geocoder;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import il.ac.shenkar.todolist.controllers.createTask.CreateTaskActivity;
import il.ac.shenkar.todolist.MyBroadcastReceiver;
import il.ac.shenkar.todolist.controllers.taskList.TaskListActivity;

public class GeocoderSearchButtonListener implements View.OnClickListener
{
    private final Geocoder geocoder;
    private final Context context;
    private final LocationManager locationManager;
    private final EditText locationText;
    private final static String EXTRA_MESSAGE = "il.ac.shenkar.todolist.MESSAGE";

    public GeocoderSearchButtonListener(Geocoder geocoder, EditText locationText, Context context, LocationManager locationManager)
    {
        this.geocoder = geocoder;
        this.locationText = locationText;
        this.context = context;
        this.locationManager = locationManager;
    }

    @Override
    public void onClick(View view)
    {
        try
        {
            Address address = geocoder.getFromLocationName(locationText.getText().toString(), 1).get(0);
            createNotification(address);

            /*context.startActivity(new Intent(context, TaskListActivity.class));
            Toast.makeText(context, "Location was successfully retrieved to " + address.getThoroughfare(), Toast.LENGTH_LONG).show();*/
        }
        catch (IOException e)
        {
            Toast.makeText(context, "Location could not be found, Sorry.", Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            //
        }
    }

    private void createNotification(Address address)
    {
        Intent intentSent = new Intent(context, MyBroadcastReceiver.class);
        //intentSent.putExtra(EXTRA_MESSAGE, intentSent.getStringExtra(CreateTaskActivity.EXTRA_MESSAGE));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentSent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);
        locationManager.addProximityAlert(address.getLatitude(), address.getLongitude(), 50, -1, pendingIntent);
    }
}
