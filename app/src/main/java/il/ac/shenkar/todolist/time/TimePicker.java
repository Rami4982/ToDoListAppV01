package il.ac.shenkar.todolist.time;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;

import com.google.common.base.Supplier;

public class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener
{
    private final Activity activity;
    private final Supplier<Time> timeSupplier;

    TimePicker(Activity activity, Supplier<Time> timeSupplier)
    {
        this.activity = activity;
        this.timeSupplier = timeSupplier;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Time time = timeSupplier.get();

        return new TimePickerDialog(activity, this, time.hour, time.minute, DateFormat.is24HourFormat(activity));
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute)
    {
        Time time = timeSupplier.get();

        time.set(time.second, minute, hourOfDay, time.monthDay, time.month, time.year);
    }
}
