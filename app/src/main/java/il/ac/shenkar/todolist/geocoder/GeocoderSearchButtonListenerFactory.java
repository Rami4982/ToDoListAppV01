package il.ac.shenkar.todolist.geocoder;

import android.content.Context;
import android.location.Geocoder;
import android.location.LocationManager;
import android.widget.EditText;

public class GeocoderSearchButtonListenerFactory
{
    public GeocoderSearchButtonListener createFrom(Context context, EditText locationText)
    {
        return new GeocoderSearchButtonListener(new Geocoder(context), locationText, context, (LocationManager)context.getSystemService(Context.LOCATION_SERVICE));
    }
}
