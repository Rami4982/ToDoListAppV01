package il.ac.shenkar.todolist.time;

import android.app.Activity;
import android.text.format.Time;

import com.google.common.base.Supplier;

import javax.inject.Inject;

import roboguice.inject.ContextSingleton;

@ContextSingleton
public class TimePickerFactory
{
    private final Activity activity;

    @Inject
    TimePickerFactory(Activity activity)
    {
        this.activity = activity;
    }

    public TimePicker createFrom(Supplier<Time> timeSupplier)
    {
        return new TimePicker(activity, timeSupplier);
    }
}
