package il.ac.shenkar.todolist.controllers.location;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.common.base.Supplier;

import il.ac.shenkar.todolist.ItemDetails;
import il.ac.shenkar.todolist.R;
import il.ac.shenkar.todolist.controllers.Extras;
import il.ac.shenkar.todolist.geocoder.GeocoderSearchButtonListenerFactory;
import il.ac.shenkar.todolist.startUp.activitys.RoboAnalyticActivity;
import roboguice.inject.InjectExtra;


public class LocationActivity extends RoboAnalyticActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.location);
        Button searchButton = (Button) findViewById(R.id.search_location_button);
        EditText locationText = (EditText) findViewById(R.id.location_text);

        searchButton.setOnClickListener(new GeocoderSearchButtonListenerFactory().createFrom(getApplicationContext(), locationText));
    }
}
